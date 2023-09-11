package dev.klepto.continuation;

import dev.klepto.unreflect.ConstructorAccess;
import dev.klepto.unreflect.MethodAccess;
import dev.klepto.unreflect.Unreflect;

/**
 * @author <a href="http://github.com/klepto">Augustinas R.</a>
 */
public class ContinuationResolver {

    private static Accessors accessors;

    public static Accessors getAccessors() {
        if (accessors != null) {
            return accessors;
        }

        var scopeClass = Unreflect.reflect("jdk.internal.vm.ContinuationScope");
        var scopeConstructor = scopeClass.constructor();

        var continuationClass = Unreflect.reflect("jdk.internal.vm.Continuation");
        var continuationConstructor = continuationClass.constructor();
        var continuationRunMethod = continuationClass.method("run");
        var continuationYieldMethod = continuationClass.method("yield");

        if (scopeConstructor == null
                || continuationConstructor == null
                || continuationRunMethod == null
                || continuationYieldMethod == null) {
            throw new UnsupportedOperationException("Continuations not supported in current version of JDK.");
        }

        accessors = new Accessors(
                scopeConstructor.unreflect(),
                continuationConstructor.unreflect(),
                continuationRunMethod.unreflect(),
                continuationYieldMethod.unreflect()
        );
        
        return accessors;
    }

    public static class Accessors {
        private final ConstructorAccess<?> scopeConstructor;
        private final ConstructorAccess<?> continuationConstructor;
        private final MethodAccess continuationRunMethod;
        private final MethodAccess continuationYieldMethod;

        public Accessors(ConstructorAccess<?> scopeConstructor,
                         ConstructorAccess<?> continuationConstructor,
                         MethodAccess continuationRunMethod,
                         MethodAccess continuationYieldMethod) {
            this.scopeConstructor = scopeConstructor;
            this.continuationConstructor = continuationConstructor;
            this.continuationRunMethod = continuationRunMethod;
            this.continuationYieldMethod = continuationYieldMethod;
        }

        public ConstructorAccess<?> getScopeConstructor() {
            return scopeConstructor;
        }

        public ConstructorAccess<?> getContinuationConstructor() {
            return continuationConstructor;
        }

        public MethodAccess getContinuationRunMethod() {
            return continuationRunMethod;
        }

        public MethodAccess getContinuationYieldMethod() {
            return continuationYieldMethod;
        }
    }

}
