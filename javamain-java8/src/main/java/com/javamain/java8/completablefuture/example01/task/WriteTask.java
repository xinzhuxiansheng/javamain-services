package com.javamain.java8.completablefuture.example01.task;

import com.javamain.java8.completablefuture.example01.Product;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class WriteTask implements Consumer<List<Product>> {
    @Override
    public void accept(List<Product> products) {
        Path path = Paths.get("output\\results.html");
        System.out.println(new Date()+": WriteTask: start");
        try (BufferedWriter fileWriter = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE)) {

            fileWriter.write("<HTML>");
            fileWriter.write("<HEAD>");
            fileWriter.write("<TITLE>");
            fileWriter.write("Search Results");
            fileWriter.write("</TITLE>");
            fileWriter.write("</HEAD>");
            fileWriter.write("<BODY>");
            fileWriter.newLine();
            fileWriter.write("<UL>");
            for (Product product : products) {
                fileWriter.write("<LI>"+product.getTitle()+"</LI>");
            }
            fileWriter.write("</UL>");
            fileWriter.write("</BODY>");
            fileWriter.write("</HTML>");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Testing error management");
        }
        System.out.println(new Date()+": WriteTask: end");
    }
}