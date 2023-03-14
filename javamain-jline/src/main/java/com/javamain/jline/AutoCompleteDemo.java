package com.javamain.jline;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzhou
 * @date 2023/3/14
 */
public class AutoCompleteDemo {
    public static void main(String[] args) {
        // 添加自动补全候选项
        List<String> candidates = new ArrayList<>();
        candidates.add("apple");
        candidates.add("banana");
        candidates.add("cherry");
        Completer completer = new StringsCompleter(candidates);

        // 创建 LineReader 实例并设置自动补全器
        LineReader reader = LineReaderBuilder.builder().completer(completer).build();

        // 循环读取用户输入并显示自动补全建议
        String line;
        while ((line = reader.readLine(">>> ")) != null) {
            System.out.println("输入内容为：" + line);
        }
    }
}
