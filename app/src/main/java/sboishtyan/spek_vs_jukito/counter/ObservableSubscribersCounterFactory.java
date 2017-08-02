package sboishtyan.spek_vs_jukito.counter;

import rx.Observable;

/**
 * @author Sergey Boishtyan
 */
public interface ObservableSubscribersCounterFactory extends SubscribersCounterProducerProvider {

    <T> Observable.Transformer<T, T> observableCounterTransformer();
}
