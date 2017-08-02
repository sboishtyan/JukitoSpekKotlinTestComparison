package sboishtyan.spek_vs_jukito.counter;

import rx.Single;

/**
 * @author Sergey Boishtyan
 */
final class SingleCounterTransformer<T> implements Single.Transformer<T, T> {

    private final SubscribersCounterConsumer subscribersCounter;

    public SingleCounterTransformer(final SubscribersCounterConsumer counter) {
        subscribersCounter = counter;
    }

    @Override
    public Single<T> call(final Single<T> single) {
        return single.doOnSubscribe(subscribersCounter.increment()).doOnUnsubscribe(subscribersCounter.decrement());
    }
}
