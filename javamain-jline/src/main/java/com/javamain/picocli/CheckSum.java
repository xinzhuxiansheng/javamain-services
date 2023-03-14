package com.javamain.picocli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.concurrent.Callable;

//$ echo hi > hi.txt
//$ checksum -a md5 hi.txt
//764efa883dda1e11db47671c4a3bbd9e
//$ checksum -a sha1 hi.txt
//55ca6286e3e4f4fba5d0448333fa99fc5a404a73

@Command(name = "checksum", mixinStandardHelpOptions = true,
        version = "checksum 4.0",
        description = "Prints the checksum (MD5 by default) of a file to STDOUT.")
class CheckSum implements Callable {
    @Parameters(index = "0", arity = "1",
            description = "The file whose checksum to calculate.")
    private File file;
    @Option(names = {"-a", "--algorithm"},
            description = "MD5, SHA-1, SHA-256, ...")
    private String algorithm = "MD5";

    // 本样例实现了Callable，所以解析、错误处理以及对使用帮助或版本帮助的用户请求处理
// 都可以在一行代码中实现。
    public static void main(String... args) {
        int exitCode = new CommandLine(new CheckSum()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // the business logic...
        byte[] data = Files.readAllBytes(file.toPath());
        byte[] digest = MessageDigest.getInstance(algorithm).digest(data);
        String format = "%0" + (digest.length * 2) + "x%n";
        System.out.printf(format, new BigInteger(1, digest));
        return 0;
    }
}
