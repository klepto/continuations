package dev.klepto.continuation;

/**
 * @author <a href="http://github.com/klepto">Augustinas R.</a>
 */
public class Continuation {

    private static final ThreadLocal<Continuation> current = new ThreadLocal<>();

    private final Object scope;
    private final Object continuation;

    Continuation(Object scope, Runnable runnable) {
        this.scope = scope;
        this.continuation = ContinuationResolver.getAccessors()
                .getContinuationConstructor()
                .create(scope, runnable);
    }

    public void run() {
        var previous = current.get();
        current.set(this);
        ContinuationResolver.getAccessors()
                .getContinuationRunMethod()
                .bind(continuation)
                .invoke();
        current.set(previous);
    }

    public void yield() {
        ContinuationResolver.getAccessors()
                .getContinuationYieldMethod()
                .bind(continuation)
                .invoke(scope);
    }

    public static void yieldCurrent() {
        if (current.get() == null) {
            return;
        }

        current.get().yield();
    }

}
