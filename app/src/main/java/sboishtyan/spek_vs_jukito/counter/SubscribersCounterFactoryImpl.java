package sboishtyan.spek_vs_jukito.counter;

import java.util.concurrent.atomic.AtomicInteger;

import rx.Completable;
import rx.Observable;
import rx.Single;

/**
 * @author Sergey Boishtyan
 */
final class SubscribersCounterFactoryImpl implements ObservableSubscribersCounterFactory, SingleSubscribersCounterFactory, CompletableSubscribersCounterFactory {

    private final SubscribersCounterImpl subscribersCounter = new SubscribersCounterImpl(new AtomicInteger());

    @Override
    public SubscribersCounterProducer producer() {
        return subscribersCounter;
    }

    @Override
    public <T> Observable.Transformer<T, T> observableCounterTransformer() {
        return new ObservableCounterTransformer<>(subscribersCounter);
    }

    @Override
    public <T> Single.Transformer<T, T> singleCounterTransformer() {
        return new SingleCounterTransformer<>(subscribersCounter);
    }

    @Override
    public Completable.Transformer completableCounterTransformer() {
        return new CompletableCounterTransformer(subscribersCounter);
    }
}
