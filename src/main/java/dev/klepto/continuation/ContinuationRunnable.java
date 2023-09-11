package dev.klepto.continuation;

/**
 * @author <a href="http://github.com/klepto">Augustinas R.</a>
 */
@FunctionalInterface
public interface ContinuationRunnable {

    void run() throws Throwable;

    default Runnable toRunnable() {
        return () -> {
            try {
                run();
            } catch (Throwable error) {
                throw new RuntimeException(error);
            }
        };
    }

}
