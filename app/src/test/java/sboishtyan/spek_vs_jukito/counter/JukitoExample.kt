package sboishtyan.spek_vs_jukito.counter

import org.jukito.All
import org.jukito.JukitoModule
import org.jukito.JukitoRunner
import org.junit.Test
import org.junit.runner.RunWith
import rx.Observable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@RunWith(JukitoRunner::class)
class JukitoExample {
    companion object {
        const val increment = "incrementCount"
        const val decrement = "decrementCount"
    }
    private val counterImpl = SubscribersCounterImpl(AtomicInteger())

    @Test
    fun `given subscribersCounter on incremen incrementCount times and decrement decrementCount times`(@All(increment) incrementCount: Int, @All(decrement) decrementCount: Int) {
        val zeroSubscribers = this.counterImpl.zeroSubscribers().test()
        incrementDecrementTimesConcurrently(incrementCount, decrementCount, this.counterImpl)

        assert(increment to incrementCount, decrement to decrementCount) {
            if (incrementCount > decrementCount) {
                zeroSubscribers.assertNoValues()
            } else {
                zeroSubscribers.assertValueCount(1)
            }
        }
    }

    private fun incrementDecrementTimesConcurrently(incrementCount: Int, decrementCount: Int, counter: SubscribersCounterImpl) {
        val test = Observable.concat(
                Observable.interval(1L, TimeUnit.NANOSECONDS).take(incrementCount).doOnNext { counter.increment().call() },
                Observable.interval(1L, TimeUnit.NANOSECONDS).take(decrementCount).doOnNext { counter.decrement().call() })
                .test()
        test.awaitTerminalEvent()
    }

    class Module : JukitoModule() {

        override fun configureTest() {
            bindManyNamedInstances(Int::class.java, "incrementCount", 1, 5, 10)
            bindManyNamedInstances(Int::class.java, "decrementCount", 1, 5, 10)
        }
    }
}
