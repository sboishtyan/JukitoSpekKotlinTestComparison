package sboishtyan.spek_vs_jukito.counter

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import rx.Observable
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class SpekExample : Spek({
    given("SubscribersCounter and zeroSubscribers") {
        println("given SubscribersCounter and zeroSubscribers\n")
        var subscribersCounter = SubscribersCounterImpl()
        var zeroSubscribers = subscribersCounter.zeroSubscribers().test()

        beforeEachTest {
            println("beforeEachTest\n")
            subscribersCounter = SubscribersCounterImpl()
            zeroSubscribers = subscribersCounter.zeroSubscribers().test()
        }

        val incrementsCount = listOf(1, 5, 10)
        val decrementCount = listOf(1, 5, 10)
        incrementsCount.forEach { incrementsCount ->
            decrementCount.forEach { decrementCount ->

                on("increment $incrementsCount times and decrement $decrementCount times") {
                    println("on increment $incrementsCount times and decrement $decrementCount times\n")
                    incrementDecrementTimesConcurrently(incrementsCount, decrementCount, subscribersCounter)

                    it("zeroSubscribers emit value if $incrementsCount <= $decrementCount") {
                        println("it zeroSubscribers emit value if $incrementsCount <= $decrementCount\n")
                        val valueCount = if (incrementsCount <= decrementCount) 1 else 0
                        zeroSubscribers.assertValueCount(valueCount)
                    }

                    if (incrementsCount > decrementCount) {
                        it("zeroSubscribers emit no value if $incrementsCount > $decrementCount") {
                            println("it zeroSubscribers emit no value if $incrementsCount > $decrementCount\n")
                            zeroSubscribers.assertNoValues()
                        }
                    }
                }

                on("yet another on $decrementCount and $incrementsCount") {
                    println("on yet another on\n")
                    it("it yet another it"){
                        println("it yet another it\n")
                        assert(true)
                    }
                }
            }
        }
    }
})

private fun incrementDecrementTimesConcurrently(incrementCount: Int, decrementCount: Int, counter: SubscribersCounterImpl) {
    val test = Observable.concat(
            Observable.interval(1, TimeUnit.NANOSECONDS).take(incrementCount).doOnNext { counter.increment().call() },
            Observable.interval(1, TimeUnit.NANOSECONDS).take(decrementCount).doOnNext { counter.decrement().call() })
            .test()
    test.awaitTerminalEvent()
}