package sboishtyan.spek_vs_jukito.counter;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Sergey Boishtyan
 */
interface CompletableSubscribersCounterConsumer {

    Action1<Subscription> incrementWithSubscriber();

    Action0 decrement();
}
