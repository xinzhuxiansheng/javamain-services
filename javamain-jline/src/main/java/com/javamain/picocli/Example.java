//package com.javamain.picocli;
//
//import org.jline.builtins.Options.HelpException;
//import org.jline.console.CmdDesc;
//import org.jline.console.CmdLine;
//import org.jline.console.impl.Builtins;
//import org.jline.reader.*;
//import org.jline.reader.impl.DefaultParser;
//import org.jline.reader.impl.LineReaderImpl;
//import org.jline.reader.impl.completer.SystemCompleter;
//import org.jline.terminal.Terminal;
//import org.jline.terminal.TerminalBuilder;
//import org.jline.utils.AttributedStringBuilder;
//import org.jline.utils.AttributedStyle;
//import org.jline.widget.TailTipWidgets;
//import picocli.CommandLine;
//import picocli.CommandLine.ArgGroup;
//import picocli.CommandLine.Command;
//import picocli.CommandLine.Option;
//import picocli.CommandLine.ParentCommand;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.concurrent.Callable;
//import java.util.concurrent.TimeUnit;
//
///**
// * Example that demonstrates how to build an interactive shell with JLine3 and picocli.
// * @since 4.1.2
// */
//public class Example {
//
//    /**
//     * Top-level command that just prints help.
//     */
//    @Command(name = "",
//            description = {
//                    "Example interactive shell with completion. " +
//                            "Hit @|magenta <TAB>|@ to see available commands.",
//                    "Type `@|bold,yellow keymap ^[s tailtip-toggle|@`, " +
//                            "then hit @|magenta ALT-S|@ to toggle tailtips.",
//                    ""},
//            footer = {"", "Press Ctl-D to exit."},
//            subcommands = {
//                    MyCommand.class, ClearScreen.class, CommandLine.HelpCommand.class})
//    static class CliCommands implements Runnable {
//        LineReaderImpl reader;
//        PrintWriter out;
//
//        CliCommands() {}
//
//        public void setReader(LineReader reader){
//            this.reader = (LineReaderImpl)reader;
//            out = reader.getTerminal().writer();
//        }
//
//        public void run() {
//            out.println(new CommandLine(this).getUsageMessage());
//        }
//    }
//
//    /**
//     * A command with some options to demonstrate completion.
//     */
//    @Command(name = "cmd", mixinStandardHelpOptions = true, version = "1.0",
//            description = "Command with some options to demonstrate TAB-completion" +
//                    " (note that enum values also get completed)",
//            subcommands = CommandLine.HelpCommand.class)
//    static class MyCommand implements Runnable {
//        @Option(names = {"-v", "--verbose"},
//                description = { "Specify multiple -v options to increase verbosity.",
//                        "For example, `-v -v -v` or `-vvv`"})
//        private boolean[] verbosity = {};
//
//        @ArgGroup(exclusive = false)
//        private MyDuration myDuration = new MyDuration();
//
//        static class MyDuration {
//            @Option(names = {"-d", "--duration"},
//                    description = "The duration quantity.",
//                    required = true)
//            private int amount;
//
//            @Option(names = {"-u", "--timeUnit"},
//                    description = "The duration time unit.",
//                    required = true)
//            private TimeUnit unit;
//        }
//
//        @ParentCommand CliCommands parent;
//
//        public void run() {
//            if (verbosity.length > 0) {
//                parent.out.printf("Hi there. You asked for %d %s.%n",
//                        myDuration.amount, myDuration.unit);
//            } else {
//                parent.out.println("hi!");
//            }
//        }
//    }
//
//    /**
//     * Command that clears the screen.
//     */
//    @Command(name = "cls", aliases = "clear", mixinStandardHelpOptions = true,
//            description = "Clears the screen", version = "1.0")
//    static class ClearScreen implements Callable<Void> {
//
//        @ParentCommand CliCommands parent;
//
//        public Void call() throws IOException {
//            parent.reader.clearScreen();
//            return null;
//        }
//    }
//
//    /**
//     * Provide command descriptions for JLine TailTipWidgets
//     * to be displayed in the status bar.
//     */
//    private static class DescriptionGenerator {
//        Builtins builtins;
//        PicocliCommands picocli;
//
//        public DescriptionGenerator(Builtins builtins, PicocliCommands picocli) {
//            this.builtins = builtins;
//            this.picocli = picocli;
//        }
//
//        CmdDesc commandDescription(CmdLine line) {
//            CmdDesc out = null;
//            switch (line.getDescriptionType()) {
//                case COMMAND:
//                    String cmd = Parser.getCommand(line.getArgs().get(0));
//                    if (builtins.hasCommand(cmd)) {
//                        out = builtins.commandDescription(cmd);
//                    } else if (picocli.hasCommand(cmd)) {
//                        out = picocli.commandDescription(cmd);
//                    }
//                    break;
//                default:
//                    break;
//            }
//            return out;
//        }
//    }
//
//    public static void main(String[] args) {
//        AnsiConsole.systemInstall();
//        try {
//            // set up JLine built-in commands
//            Path workDir = Paths.get("");
//            Builtins builtins = new Builtins(workDir, null, null);
//            builtins.rename(org.jline.builtins.Builtins.Command.TTOP, "top");
//            builtins.alias("zle", "widget");
//            builtins.alias("bindkey", "keymap");
//            SystemCompleter systemCompleter = builtins.compileCompleters();
//            // set up picocli commands
//            CliCommands commands = new CliCommands();
//            CommandLine cmd = new CommandLine(commands);
//            PicocliCommands picocliCommands = new PicocliCommands(workDir, cmd);
//            systemCompleter.add(picocliCommands.compileCompleters());
//            systemCompleter.compile();
//            Terminal terminal = TerminalBuilder.builder().build();
//            LineReader reader = LineReaderBuilder.builder()
//                    .terminal(terminal)
//                    .completer(systemCompleter)
//                    .parser(new DefaultParser())
//                    .variable(LineReader.LIST_MAX, 50)   // max tab completion candidates
//                    .build();
//            builtins.setLineReader(reader);
//            commands.setReader(reader);
//            DescriptionGenerator descriptionGenerator = new DescriptionGenerator(builtins, picocliCommands);
//            new TailTipWidgets(reader, descriptionGenerator::commandDescription, 5, TipType.COMPLETER);
//
//            String prompt = "prompt> ";
//            String rightPrompt = null;
//
//            // start the shell and process input until the user quits with Ctl-D
//            String line;
//            while (true) {
//                try {
//                    line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
//                    if (line.matches("^\\s*#.*")) {
//                        continue;
//                    }
//                    ParsedLine pl = reader.getParser().parse(line, 0);
//                    String[] arguments = pl.words().toArray(new String[0]);
//                    String command = Parser.getCommand(pl.word());
//                    if (builtins.hasCommand(command)) {
//                        builtins.execute(command, Arrays.copyOfRange(arguments, 1, arguments.length)
//                                , System.in, System.out, System.err);
//                    } else {
//                        new CommandLine(commands).execute(arguments);
//                    }
//                } catch (HelpException e) {
//                    HelpException.highlight(e.getMessage(), HelpException.defaultStyle()).print(terminal);
//                } catch (UserInterruptException e) {
//                    // Ignore
//                } catch (EndOfFileException e) {
//                    return;
//                } catch (Exception e) {
//                    AttributedStringBuilder asb = new AttributedStringBuilder();
//                    asb.append(e.getMessage(), AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
//                    asb.toAttributedString().println(terminal);
//                }
//            }
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//    }
//}
