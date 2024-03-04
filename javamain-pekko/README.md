## Akka通过Props实例创建Actor
通过使用基于消息的方法，我们可以相当完整地将 Actor 的实例封装起来。如果只通过消息进行相互通信的话，那么永远都不会需要获取 Actor 的实例。我们只需要一种机制来支持向 Actor 发送消息并接收响应。

在 Akka 中，这个指向 Actor 实例的引用叫做 ActorRef。ActorRef 是一个无类型的引用，将其指向的 Actor 封装起来，提供了更高层的抽象，并且给用户提供了一种与 Actor 进行通信的机制。

上文已经介绍过，Actor 系统就是包含所有 Actor 的地方。有一点可能相当明显：我们也正是在 Actor 系统中创建新的 Actor 并获取指向 Actor 的引用。actorOf 方法会生成一个新的 Actor，并返回指向该 Actor 的引用
```
ActorRef actor = actorSystem.actorOf(Props.create(JavaPongActor.class));
```

**Props**
为了保证能够将 Actor 的实例封装起来，不让其被外部直接访问，我们将所有构造函数的参数传给一个 Props 的实例。Props 允许我们传入 Actor 的类型以及一个变长的参数列表.
```
Props.create(PongActor.class, arg1, arg2, argn);
```
如果 Actor 的构造函数有参数，那么推荐的做法是通过一个工厂方法来创建 Props。
假如我们不希望 Pong Actor 返回“Pong”，而是希望其返回另一条消息，那么可能就会需要这样的构造参数。我们可以创建一个工厂方法，用于生成这样的 Props 示例：
```
public static Props props(String response) {
		return Props.create(this.class, response);
	}
```
然后就可以使用 Props 的工厂方法来创建 Actor：
```
ActorRef actor = actorSystem.actorOf(JavaPongActor.props("PongFoo"));
```
虽然创建 Props 的工厂方法并非必须，但是能够在同一个地方管理对 Props 对象的创建，因此所有对 Actor 构造参数的修改都可以与其他代码隔离，防止在代码修改的过程中引起其他模块的错误。


**AbstractActor**   
首先，我们继承了 AbstractActor。这是一个 Java 8 特有的 API，利用了 Java 8 的匿名函数（lambda）的特性。如果查看文档的话，可以发现还有另一个 Actor API 可以作为基类来继承：UntypedActor。UntypedActor 是较老版本的 API，在本书中并不会多做介绍。在 UntypedActor 的 API 中，会得到一个对象，然后必须使用 if 语句对其进行条件判断。由于 Java 8 的 API 通过匿名函数来实现模式匹配，表达能力更强，因此我们将着重介绍这个 Java 8 特有的 API。

**Receive** 
Receive：AbstractActor 类有一个 createReceive 方法，其子类必须实现这个方法或是通过构造函数调用该方法。createReceive 方法返回的类型是 Receive，这个类型来自 Scala API。在 Java 中，并没有提供任何原生方法来构造 Scala 的 Receive（并不对所有可能输入进行处理的函数），因此 Akka 为我们提供了一个抽象的构造方法类receiveBuilder，用于生成 Receive 作为返回值。不用担心，使用Akka 并不要求理解 Scala 的 Receive！
receiveBuilder：连续调用 receiveBuilder 的方法，为所有需要匹配处理的输入消息类型提供响应方法的描述，然后调用 build() 方法生成需要返回的Receive

**Match**   
Match：ReceiveBuilder 提供了一些值得一提的 match 方法，我们将提供一些示例，展示如何分别使用这些方法来匹配“ping”消息。
    match(class, function)：描述了对于任何尚未匹配的该类型的示例，应该如何响应。
    match(String.class, s -> {if(s.equals(“Ping”)) respondToPing(s);})
    match(class, predicate, function)：描述了对于 predicate 条件函数为真的某特定类型的消息，应该如何响应。
    match(String.class, s -> s.equals(“Ping”), s -> respondToPing(s))
    matchEquals(object, function)：描述了对于和传入的第一个参数相等的消息，应该如何响应。
    matchEquals(“Ping”, s -> respondToPing(s))
    matchAny(function)：该函数匹配所有尚未匹配的消息。通常来说，最佳实践是返回错误信息，或者至少将错误信息记录到日志，帮助开发过程中的错误调试

match 函数从上至下进行模式匹配。所以可以先定义特殊情况，最后定义一般情况。
```
return receiveBuilder()
        .matchEquals("Ping", s -> System.out.println("It's Ping: " + s))
        .match(String.class, s -> System.out.println("It's a string: " + s))
        .matchAny(x -> System.out.println("It's something else: " + x))
        .build();
}
```

**sender()**
向 sender()返回消息：调用了sender()方法后，我们就可以返回所收到的消息的响应了。响应的对象既可能是一个 Actor，也可能是来自于 Actor 系统外部的请求。第一种情况相当直接：返回的消息会直接发送到该 Actor 的收件信箱中。对于第二种情况，我们将在后面详细介绍

**tell()**
tell()：sender()函数会返回一个 ActorRef。在上面的例子中，我们调用了 sender().tell()。 tell()是最基本的单向消息传输模式。第一个参数是我们想要发送至对方信箱的消息。第二个参数则是希望对方 Actor 看到的发送者。我们描述了接收到的消息是 String 时应该做出的响应。由于需要检查接收到的字符串是否为“Ping”，因此需要进行判断，所以这里使用的 match 方法略有不同。然后描述响应行为：通过 tell()方法向 sender()返回一条消息。我们返回的消息是字符串“Pong”。Java 的 tell方法要求提供消息发送者的身份：这里使用 ActorRef.noSender()表示没有返回地址

**Status.Failure**
返回 akka.actor.Status.Failure：为了向发送方报告错误信息，需要向其发送一条消息。如果 Actor 中抛出了异常，就会通知对其进行监督的Actor。不过无论如何，如果想要报告错误消息，需要将错误发送给发送方。如果发送方使用 Future 来接收响应，那么返回错误消息会导致
Future 的结果为失败。我们将之后对此进行简要介绍





