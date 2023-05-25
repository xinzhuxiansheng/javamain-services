package com.javamain.io.file;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FindCommonUrls {
    public static final int NUM_SUB_FILES = 1000;

    public static void main(String[] args) throws IOException {
        splitAndHashUrls("file_a.txt", "hashed_a_");
        splitAndHashUrls("file_b.txt", "hashed_b_");

        findCommonUrls("hashed_a_", "hashed_b_");
    }

    public static void splitAndHashUrls(String inputFileName, String outputFilePrefix) throws IOException {
        Map<Integer, BufferedWriter> writers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int hashCode = line.hashCode() % NUM_SUB_FILES;
                BufferedWriter writer = writers.computeIfAbsent(hashCode, k -> {
                    try {
                        return new BufferedWriter(new FileWriter(outputFilePrefix + k + ".txt", true));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.write(line);
                writer.newLine();
            }
        }

        // 关闭所有打开的BufferedWriter
        for (BufferedWriter writer : writers.values()) {
            writer.close();
        }
    }

    public static void findCommonUrls(String fileAPrefix, String fileBPrefix) throws IOException {
        for (int i = 0; i < NUM_SUB_FILES; i++) {
            Set<String> urlSetA = loadUrlsIntoSet(fileAPrefix + i + ".txt");
            Set<String> urlSetB = loadUrlsIntoSet(fileBPrefix + i + ".txt");

            urlSetA.retainAll(urlSetB);

            System.out.println("共同的URL（子文件" + i + "）：");
            for (String url : urlSetA) {
                System.out.println(url);
            }
        }
    }

    public static Set<String> loadUrlsIntoSet(String fileName) throws IOException {
        Set<String> urlSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                urlSet.add(line);
            }
        }
        return urlSet;
    }
}
