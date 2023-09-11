package dev.klepto.continuation;

/**
 * @author <a href="http://github.com/klepto">Augustinas R.</a>
 */
public class Main {

    public static void main(String[] args) {
        var scope = new ContinuationScope();
        var continuation = scope.create(() -> {
            System.out.println("1");
            Continuation.yieldCurrent();
            System.out.println("2");
            Continuation.yieldCurrent();
            System.out.println("3");
        });

        continuation.run();
        continuation.run();
        System.out.println("Done!");
        continuation.run();
    }

}