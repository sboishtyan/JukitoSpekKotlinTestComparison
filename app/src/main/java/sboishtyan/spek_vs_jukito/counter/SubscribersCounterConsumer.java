package sboishtyan.spek_vs_jukito.counter;

import rx.functions.Action0;

/**
 * @author Sergey Boishtyan
 */
interface SubscribersCounterConsumer {

    Action0 increment();

    Action0 decrement();
}
