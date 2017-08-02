package sboishtyan.spek_vs_jukito.counter;

import rx.Completable;

/**
 * @author Sergey Boishtyan
 */
public interface CompletableSubscribersCounterFactory extends SubscribersCounterProducerProvider {

    Completable.Transformer completableCounterTransformer();
}
