package sboishtyan.spek_vs_jukito.counter

import io.kotlintest.specs.BehaviorSpec
import rx.Observable
import java.util.concurrent.TimeUnit

class KotlinTestExample : BehaviorSpec() {
    init {
        given("SubscribersCounter") {
            val counterImpl = SubscribersCounterImpl()
            val zeroSubscribers = counterImpl.zeroSubscribers().test()
            listOf(1, 5, 10).forEach { incrementCount ->
                listOf(1, 5, 10).forEach { decrementCount ->

                    `when`("increment $incrementCount times and decrement $decrementCount times") {
                        incrementDecrementTimesConcurrently(incrementCount, decrementCount, counterImpl)
                        if (incrementCount > decrementCount) {
                            then("zeroSubscribers emit no events") {
                                zeroSubscribers.assertNoValues()

                            }
                        } else {
                            then("zeroSubscribers emit one event") {
                                zeroSubscribers.assertValueCount(1)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun incrementDecrementTimesConcurrently(incrementCount: Int, decrementCount: Int, counter: SubscribersCounterImpl) {
    val test = Observable.concat(
            Observable.interval(1, TimeUnit.NANOSECONDS).take(incrementCount).doOnNext { counter.increment().call() },
            Observable.interval(1, TimeUnit.NANOSECONDS).take(decrementCount).doOnNext { counter.decrement().call() })
            .test()
    test.awaitTerminalEvent()
}