package dev.klepto.continuation;

import java.util.Objects;

/**
 * @author <a href="http://github.com/klepto">Augustinas R.</a>
 */
public class ContinuationScope {

    private final String name;
    private final Object scope;

    public ContinuationScope() {
        this(null);
    }

    public ContinuationScope(String name) {
        if (name == null) {
            name = super.toString();
        }

        this.name = name;
        this.scope = ContinuationResolver.getAccessors()
                .getScopeConstructor()
                .create(name);
    }

    public Continuation create(ContinuationRunnable runnable) {
        return new Continuation(scope, runnable.toRunnable());
    }

    public Continuation run(ContinuationRunnable runnable) {
        var continuation = create(runnable);
        continuation.run();
        return continuation;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scope);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other instanceof ContinuationScope otherScope) {
            return scope.equals(otherScope.scope);
        }

        return false;
    }

    @Override
    public String toString() {
        return scope.toString();
    }

}