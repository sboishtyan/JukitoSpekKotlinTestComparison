package sboishtyan.spek_vs_jukito.counter

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import rx.Observable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@RunWith(JUnitPlatform::class)
class SpekExample : Spek({
    given("SubscribersCounter and zeroSubscribers") {
        val subscribersCounter = SubscribersCounterImpl(AtomicInteger())
        val zeroSubscribers = subscribersCounter.zeroSubscribers().test()
        val incrementsCount = listOf(1, 5, 10)
        val decrementCount = listOf(1, 5, 10)
        incrementsCount.forEach { incrementsCount ->
            decrementCount.forEach { decrementCount ->
                on("increment $incrementsCount times and decrement $decrementCount times") {
                    incrementDecrementTimesConcurrently(incrementsCount, decrementCount, subscribersCounter)
                    it("zeroSubscribers emit value if $incrementsCount <= $decrementCount") {
                        val valueCount = if (incrementsCount <= decrementCount) 1 else 0
                        zeroSubscribers.assertValueCount(valueCount)
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