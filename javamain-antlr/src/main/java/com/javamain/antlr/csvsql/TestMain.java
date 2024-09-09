package com.javamain.antlr.csvsql;

import com.javamain.antlr.csvsql.antlr.pocketsLexer;
import com.javamain.antlr.csvsql.antlr.pocketsParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class TestMain {
    public static void main(String[] args) {
        try {
            // 创建
//            String sql = "CREATE FILE product (id, name, desc , price);";

            // 插入
            // String sql = "insert into file product (id, name, desc, price) rows (1, \"pen\", \"Ballpoint Pen\", 1), (2, \"pencil\", \"Drawing Pen\", 2), (3, \"book\", \"Ruled Notebook for kids\", 4);";

            // 查询
            // String sql = "select * from file product;";

            // 查询 where id > 3
            // String sql = "select * from file product where price > 3;";

            // 删除
            String sql =  "delete file product;";

            // 创建一个 CharStream 用于读取 SQL 语句
            CharStream stream = CharStreams.fromString(sql);

            // 创建词法分析器和解析器
            pocketsLexer lexer = new pocketsLexer(stream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            pocketsParser parser = new pocketsParser(tokens);

            // 解析 SQL 语句并获取解析树的根节点
            ParseTree tree = parser.pocket();

            // 创建并应用 CustomSqlListener
            ParseTreeWalker walker = new ParseTreeWalker();
            CustomSqlListener listener = new CustomSqlListener();
            walker.walk(listener, tree);

            System.out.println("SQL 解析完成。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
