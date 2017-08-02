package sboishtyan.spek_vs_jukito.counter;

import rx.Observable;

/**
 * @author Sergey Boishtyan
 */
final class ObservableCounterTransformer<T> implements Observable.Transformer<T, T> {

    private final SubscribersCounterConsumer subscribersCounter;

    public ObservableCounterTransformer(final SubscribersCounterConsumer subscribersCounterConsumer) {
        this.subscribersCounter = subscribersCounterConsumer;
    }

    @Override
    public Observable<T> call(final Observable<T> observable) {
        return observable.doOnSubscribe(subscribersCounter.increment()).doOnUnsubscribe(subscribersCounter.decrement());
    }
}
