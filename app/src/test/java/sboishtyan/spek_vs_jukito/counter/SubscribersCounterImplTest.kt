package sboishtyan.spek_vs_jukito.counter

import org.junit.Test
import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * @author Sergey Boishtyan
 */
class SubscribersCounterImplTest {
    private val counterImpl = SubscribersCounterImpl()

    @Test
    fun `given decrement calls less than increment calls then no emit ZeroSubscribersEvent`() {
        val zeroSubscribers = counterImpl.zeroSubscribers().test()
        val incrementCount = 10
        val decrementCount = 9
        incrementDecrementTimesConcurrently(incrementCount, decrementCount, counterImpl)

        zeroSubscribers.assertNoValues()
    }

    @Test
    fun `given decrement calls equals increment calls then emit one ZeroSubscribersEvent`() {
        val zeroSubscribers = counterImpl.zeroSubscribers().test()
        val incrementCount = 10
        val decrementCount = 10
        incrementDecrementTimesConcurrently(incrementCount, decrementCount, counterImpl)

        zeroSubscribers.assertValueCount(1)
    }

    @Test
    fun `given decrement calls greater than increment calls then emit one ZeroSubscribersEvent`() {
        val zeroSubscribers = counterImpl.zeroSubscribers().test()
        val incrementCount = 10
        val decrementCount = 15
        incrementDecrementTimesConcurrently(incrementCount, decrementCount, counterImpl)

        zeroSubscribers.assertValueCount(1)
    }

    @Test
    fun `given decrement calls greater than increment calls when one more time increment and decrement with equals count then emit one ZeroSubscribersEvent`() {
        var incrementCount = 10
        var decrementCount = 15
        incrementDecrementTimesConcurrently(incrementCount, decrementCount, counterImpl)

        val zeroSubscribers = counterImpl.zeroSubscribers().test()
        incrementCount = 4
        decrementCount = 4
        incrementDecrementTimesConcurrently(incrementCount, decrementCount, counterImpl)

        zeroSubscribers.assertValueCount(1)
    }

    private fun incrementDecrementTimesConcurrently(incrementCount: Int, decrementCount: Int, counter: SubscribersCounterImpl) {
        val test = Observable.concat(
                Observable.interval(1, TimeUnit.NANOSECONDS).take(incrementCount).doOnNext { counter.increment().call() },
                Observable.interval(1, TimeUnit.NANOSECONDS).take(decrementCount).doOnNext { counter.decrement().call() })
                .test()
        test.awaitTerminalEvent()
    }
}