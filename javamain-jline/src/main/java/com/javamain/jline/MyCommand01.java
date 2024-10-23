package com.javamain.jline;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * @author yzhou
 * @date 2023/3/14
 */
public class MyCommand01 {

    public static void main(String[] args) throws Exception {
        TerminalBuilder builder = TerminalBuilder.builder();
        Terminal terminal = builder.build();
        LineReaderBuilder readerBuilder = LineReaderBuilder.builder();
        LineReader lineReader = readerBuilder.terminal(terminal).completer(new CompleterDemo()).build();

        String prompt = "MyCommand> ";
        while (true) {
            String line = lineReader.readLine(prompt);
            if (line == null || line.equalsIgnoreCase("exit")) {
                break;
            } else {
                System.out.println("You entered: " + line);
            }
        }
    }
}
