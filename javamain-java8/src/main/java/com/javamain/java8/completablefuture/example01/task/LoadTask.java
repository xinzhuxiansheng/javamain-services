package com.javamain.java8.completablefuture.example01.task;

import com.javamain.java8.completablefuture.example01.Product;
import com.javamain.java8.completablefuture.example01.ProductLoader;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LoadTask implements Supplier<List<Product>> {
    private Path path;

    public LoadTask(Path path) {
        this.path = path;
    }

    @Override
    public List<Product> get() {
        System.out.println(new Date() + ": LoadTast: starting....");
        List<Product> productList = null;
        try {
            productList = Files.walk(path, FileVisitOption.FOLLOW_LINKS).parallel()
                    .filter(f -> f.toString().endsWith(".txt")).map(ProductLoader::load).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new Date() + ": LoadTast: end");
        return productList;
    }
}
