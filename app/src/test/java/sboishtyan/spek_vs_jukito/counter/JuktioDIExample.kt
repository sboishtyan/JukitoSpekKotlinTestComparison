package sboishtyan.spek_vs_jukito.counter

import org.jukito.JukitoRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import javax.inject.Inject

@RunWith(JukitoRunner::class)
class JuktioDIExample {
    var producer: SubscribersCounterProducer? = null

    @JvmField
    @Inject
    var producer1: SubscribersCounterProducer? = null

    @Inject
    internal lateinit var counterImpl: SubscribersCounterImpl

    @Test
    fun `assert producer null when simple field in test`() {
        Assert.assertNull(producer)
    }

    @Test
    fun `assert producer non null when field with inject annotation in test`() {
        Assert.assertNotNull(producer1)
    }

    @Test
    fun `assert producer in field and in method param equals`(producer2: SubscribersCounterProducer) {
        Assert.assertEquals(producer2, producer1)
    }

    @Test
    fun `assert impl is not mock`() {
        Assert.assertTrue(counterImpl::class.java.isAssignableFrom(SubscribersCounterImpl::class.java))
    }
}
