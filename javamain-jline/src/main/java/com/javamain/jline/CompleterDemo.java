package com.javamain.jline;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 命令补全
 * @author yzhou
 * @date 2023/3/14
 */
public class CompleterDemo implements Completer {
    private List<String> commands = Arrays.asList("help","version","quit");

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        String word = line.word();
        if (word == null || word.isEmpty()) {
            candidates.addAll(commands.stream().map(Candidate::new).collect(Collectors.toList()));
        } else {
            candidates.addAll(commands.stream().filter(c -> c.startsWith(word)).map(Candidate::new).collect(Collectors.toList()));
        }
    }
}
