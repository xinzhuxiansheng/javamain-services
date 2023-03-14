package com.javamain.jline;

import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.List;

/**
 * 命令补全
 */
public class CompleterCommand03 {

    public static void main(String[] args) throws Exception {
        TerminalBuilder builder = TerminalBuilder.builder();
        Terminal terminal = builder.build();
        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new MyCompleter())
                .build();

        String line;
        while ((line = reader.readLine("> ")) != null) {
            switch (line.trim()) {
                case "hello":
                    System.out.println("Hello, World!");
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command: " + line);
                    break;
            }
        }
    }

    private static class MyCompleter implements Completer {
        @Override
        public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
            String buffer = line.line().toLowerCase();
            if (buffer.startsWith("he")) {
                candidates.add(new Candidate("hello"));
            } else if (buffer.startsWith("ex")) {
                candidates.add(new Candidate("exit"));
            }
        }
    }

}
