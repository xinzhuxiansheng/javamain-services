package com.javamain.jline;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * @author yzhou
 * @date 2023/3/14
 */
public class MyCommand02 {
    public static void main(String[] args) throws Exception {
        Terminal terminal = TerminalBuilder.terminal();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
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
}
