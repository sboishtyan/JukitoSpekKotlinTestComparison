package sboishtyan.spek_vs_jukito.counter;

import rx.Completable;

/**
 * @author Sergey Boishtyan
 */
final class CompletableCounterTransformer implements Completable.Transformer {

    private final CompletableSubscribersCounterConsumer consumer;

    CompletableCounterTransformer(final CompletableSubscribersCounterConsumer counterConsumer) {
        consumer = counterConsumer;
    }

    @Override
    public Completable call(final Completable completable) {
        return completable.doOnSubscribe(consumer.incrementWithSubscriber()).doOnUnsubscribe(consumer.decrement());
    }
}
