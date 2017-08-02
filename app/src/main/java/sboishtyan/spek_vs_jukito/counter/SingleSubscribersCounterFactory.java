package sboishtyan.spek_vs_jukito.counter;

import rx.Single;

/**
 * @author Sergey Boishtyan
 */
public interface SingleSubscribersCounterFactory extends SubscribersCounterProducerProvider {

    <T> Single.Transformer<T, T> singleCounterTransformer();
}
