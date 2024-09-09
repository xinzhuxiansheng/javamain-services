package com.javamain.akka.stream;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.IOResult;
import akka.stream.javadsl.*;
import akka.util.ByteString;

import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.concurrent.CompletionStage;

/*
    source 复用

 */
public class Stream01 {
    public static void main(String[] argv) {
        final ActorSystem system = ActorSystem.create("QuickStart");
        // Code here
        final Source<Integer, NotUsed> source = Source.range(1, 100);
        source.runForeach(i -> System.out.println(i), system);

        final CompletionStage<Done> done = source.runForeach(i -> System.out.println(i), system);
        //done.thenRun(() -> system.terminate());

        final Source<BigInteger, NotUsed> factorials =
                source.scan(BigInteger.ONE, (acc, next) -> acc.multiply(BigInteger.valueOf(next)));

        final CompletionStage<IOResult> result =
                factorials
                        .map(num -> ByteString.fromString(num.toString() + "\n"))
                        .runWith(FileIO.toPath(Paths.get("/Users/a/Code/Java/javamain-services/doc/factorials.txt")), system);
        // result.thenRun(() -> system.terminate());
        factorials.map(BigInteger::toString).runWith(lineSink("/Users/a/Code/Java/javamain-services/doc/factorial2.txt"), system);
    }

    public static Sink<String, CompletionStage<IOResult>> lineSink(String filename) {
        return Flow.of(String.class)
                .map(s -> ByteString.fromString(s.toString() + "\n"))
                .toMat(FileIO.toPath(Paths.get(filename)), Keep.right());
    }
}
