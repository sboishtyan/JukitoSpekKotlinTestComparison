package sboishtyan.spek_vs_jukito.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sergey Boishtyan
 */
final class DecrementIfGreaterThanZero {

    DecrementResult decrement(final AtomicInteger atomicInteger) {
        int oldValue;
        int newValue;
        do {
            oldValue = atomicInteger.get();
            if (oldValue > 0) {
                newValue = oldValue - 1;
            } else {
                return DecrementResult.NOT_DECREMENTED;
            }
        } while (!atomicInteger.compareAndSet(oldValue, newValue));
        return DecrementResultImpl.withValueAfterDecrement(newValue);
    }

    interface DecrementResult {

        DecrementResult NOT_DECREMENTED = (value, runnable) -> {/*empty*/};

        void runIfDecrementToValue(int value, Runnable runnable);
    }

    private final static class DecrementResultImpl implements DecrementResult {

        private final int valueAfterDecrement;

        private DecrementResultImpl(final int valueAfterDecrement) {
            this.valueAfterDecrement = valueAfterDecrement;
        }

        static DecrementResultImpl withValueAfterDecrement(int value) {
            return new DecrementResultImpl(value);
        }

        @Override
        public void runIfDecrementToValue(final int value, final Runnable runnable) {
            if (valueAfterDecrement == value) {
                runnable.run();
            }
        }
    }
}
