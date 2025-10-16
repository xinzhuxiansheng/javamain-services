package com.javamain.calcite.csv01;

import org.apache.calcite.linq4j.Enumerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvEnumerator implements Enumerator<Object[]> {
    
    private final BufferedReader reader;
    private Object[] current;
    private boolean hasNext;

    public CsvEnumerator(File file) {
        try {
            this.reader = new BufferedReader(new FileReader(file));
            // 跳过标题行
            reader.readLine();
            this.hasNext = moveNext();
        } catch (IOException e) {
            throw new RuntimeException("Error opening CSV file: " + file.getName(), e);
        }
    }

    @Override
    public Object[] current() {
        return current;
    }

    @Override
    public boolean moveNext() {
        try {
            String line = reader.readLine();
            if (line != null) {
                current = parseCSVLine(line);
                return true;
            } else {
                current = null;
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV line", e);
        }
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset not supported for CSV enumerator");
    }

    @Override
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing CSV file", e);
        }
    }

    private Object[] parseCSVLine(String line) {
        List<Object> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(field.toString().trim());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        fields.add(field.toString().trim());
        
        return fields.toArray(new Object[0]);
    }
}