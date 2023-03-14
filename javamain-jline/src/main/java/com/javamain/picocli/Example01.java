package com.javamain.picocli;

import picocli.CommandLine;

import java.io.File;

/**
 * @author yzhou
 * @date 2023/3/15
 */
@CommandLine.Command(name = "example", mixinStandardHelpOptions = true, version = "Picocli example 4.0")
public class Example01 implements Runnable {

    @CommandLine.Option(names = { "-v", "--verbose" },
            description = "Verbose mode. Helpful for troubleshooting. Multiple -v options increase the verbosity.")
    private boolean[] verbose = new boolean[0];

    @CommandLine.Parameters(arity = "1..*", paramLabel = "FILE", description = "File(s) to process.")
    private File[] inputFiles;

    public void run() {
        if (verbose.length > 0) {
            System.out.println(inputFiles.length + " files to process...");
        }
        if (verbose.length > 1) {
            for (File f : inputFiles) {
                System.out.println(f.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        // By implementing Runnable or Callable, parsing, error handling and handling user
        // requests for usage help or version help can be done with one line of code.

        int exitCode = new CommandLine(new Example01()).execute(args);
        System.exit(exitCode);
    }
}
