# continuations
Example code:
```java
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
```
Output:
```java
1
2
Done!
3
```