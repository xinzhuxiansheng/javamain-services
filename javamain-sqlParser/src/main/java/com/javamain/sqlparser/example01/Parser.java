package com.javamain.sqlparser.example01;

import com.javamain.sqlparser.example01.statement.Create;
import com.javamain.sqlparser.example01.statement.Insert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzhou
 * @date 2022/12/21
 */
public class Parser {

    public static Object Parse(byte[] statement) throws Exception {
        Tokenizer tokenizer = new Tokenizer(statement);
        String token = tokenizer.peek();
        tokenizer.pop();

        Object stat = null;
        Exception statErr = null;
        try {
            switch (token) {
                case "create":
                    stat = parseCreate(tokenizer);
                    break;

                default:
                    throw new RuntimeException("switch未匹配到有效值");
            }
        } catch (Exception e) {
            statErr = e;
        }

        return stat;

    }

    // create table student id int32, name string, uid int64, (index name id uid)
    private static Create parseCreate(Tokenizer tokenizer) throws Exception {
        if (!"table".equals(tokenizer.peek())) {
            throw new IllegalArgumentException();
        }
        tokenizer.pop(); // 可以读

        Create create = new Create();
        String name = tokenizer.peek();
        if (!isName(name)) {
            throw new IllegalArgumentException();
        }
        create.tableName = name;

        List<String> fNames = new ArrayList<>();
        List<String> fTypes = new ArrayList<>();
        while (true) {
            tokenizer.pop();
            String field = tokenizer.peek();
            if (",".equals(field)) {
                continue;
            }
            if ("(".equals(field)) {
                break;
            }

            if (!isName(field)) {
                throw new IllegalArgumentException();
            }

            tokenizer.pop();
            String fieldType = tokenizer.peek();
            if (!isType(fieldType)) {
                throw new IllegalArgumentException();
            }
            fNames.add(field);
            fTypes.add(fieldType);
            tokenizer.pop();
        }
        create.fieldName = fNames.toArray(new String[fNames.size()]);
        create.fieldType = fTypes.toArray(new String[fTypes.size()]);

        tokenizer.pop();
        if (!"index".equals(tokenizer.peek())) {
            throw new IllegalArgumentException();
        }

        List<String> indexes = new ArrayList<>();
        while (true) {
            tokenizer.pop();
            String field = tokenizer.peek();
            if (")".equals(field)) {
                break;
            }
            if (!isName(field)) {
                throw new IllegalArgumentException();
            } else {
                indexes.add(field);
            }
        }
        create.index = indexes.toArray(new String[indexes.size()]);
        tokenizer.pop();

        if (!"".equals(tokenizer.peek())) {
            throw new IllegalArgumentException();
        }
        return create;
    }

    // insert into test_table values 10 30
    private static Insert parseInsert(Tokenizer tokenizer) throws Exception {
        Insert insert = new Insert();

        if(!"into".equals(tokenizer.peek())) {
            throw new IllegalArgumentException();
        }
        tokenizer.pop();

        String tableName = tokenizer.peek();
        if(!isName(tableName)) {
            throw new IllegalArgumentException();
        }
        insert.tableName = tableName;
        tokenizer.pop();

        if(!"values".equals(tokenizer.peek())) {
            throw new IllegalArgumentException();
        }

        List<String> values = new ArrayList<>();
        while(true) {
            tokenizer.pop();
            String value = tokenizer.peek();
            if("".equals(value)) {
                break;
            } else {
                values.add(value);
            }
        }
        insert.values = values.toArray(new String[values.size()]);

        return insert;
    }

    private static boolean isName(String name) {
        if (name == null) return false;
        if (!Tokenizer.isAlphaBeta(name.getBytes()[0])) return false;
        return true;
    }

    private static boolean isType(String tp) {
        return ("int32".equals(tp) || "int64".equals(tp) ||
                "string".equals(tp));
    }
}
