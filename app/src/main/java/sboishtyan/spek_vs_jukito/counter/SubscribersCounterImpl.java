package sboishtyan.spek_vs_jukito.counter;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * @author Sergey Boishtyan
 */
final class SubscribersCounterImpl implements SubscribersCounterConsumer, SubscribersCounterProducer, CompletableSubscribersCounterConsumer {

    private final AtomicInteger counter;
    private final Subject<ZeroSubscribersEvent, ZeroSubscribersEvent> zeroSubscribersLeft = PublishSubject.<ZeroSubscribersEvent>create().toSerialized();
    private final DecrementIfGreaterThanZero decrementer = new DecrementIfGreaterThanZero();
    private final Action0 incrementAction;
    private final Action1<Subscription> incrementWithSubscriptionAction;
    private final Action0 decrementAction;

    @Inject
    public SubscribersCounterImpl(AtomicInteger counter) {
        this.counter = counter;
        incrementAction = counter::incrementAndGet;
        incrementWithSubscriptionAction = unused -> incrementAction.call();
        decrementAction = () -> decrementer.decrement(counter).runIfDecrementToValue(0, () -> zeroSubscribersLeft.onNext(ZeroSubscribersEvent.event()));
    }

    @Override
    public Action1<Subscription> incrementWithSubscriber() {
        return incrementWithSubscriptionAction;
    }

    @Override
    public Action0 increment() {
        return incrementAction;
    }

    @Override
    public Action0 decrement() {
        return decrementAction;
    }

    @Override
    public Observable<ZeroSubscribersEvent> zeroSubscribers() {
        return zeroSubscribersLeft;
    }
}
