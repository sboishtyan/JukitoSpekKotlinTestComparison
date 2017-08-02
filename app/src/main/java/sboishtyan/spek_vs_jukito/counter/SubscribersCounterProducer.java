package sboishtyan.spek_vs_jukito.counter;

import rx.Observable;

/**
 * @author Sergey Boishtyan
 */
public interface SubscribersCounterProducer {

    Observable<ZeroSubscribersEvent> zeroSubscribers();
}
