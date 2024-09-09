//package com.javamain.jline;
//
//import jdk.internal.jline.console.history.FileHistory;
//import org.jline.reader.History;
//import org.jline.reader.LineReader;
//import org.jline.reader.LineReaderBuilder;
//import org.jline.reader.impl.DefaultParser;
//import org.jline.reader.impl.history.DefaultHistory;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
///**
// * 历史记录
// */
//public class HistoryCommand {
//    public static void main(String[] args) throws IOException {
//        // 创建LineReader实例
//        LineReader reader = LineReaderBuilder.builder()
//                .parser(new DefaultParser())
//                .history(new DefaultHistory())
//                .build();
//
//        // 获取历史记录对象并将其附加到FileHistory对象
//        History history = reader.getHistory();
//        FileHistory fileHistory = new FileHistory(Paths.get(System.getProperty("user.home"), ".myapp_history").toFile());
//        history.attach(fileHistory);
//
//        // 添加历史记录
//        history.add("command 1");
//        history.add("command 2");
//
//        // 从历史记录中获取命令
//        String command = reader.readLine("prompt> ", null, (Completer) null, history).trim();
//        System.out.println("You entered: " + command);
//
//        // 将历史记录保存到文件中
//        fileHistory.flush();
//    }
//}
