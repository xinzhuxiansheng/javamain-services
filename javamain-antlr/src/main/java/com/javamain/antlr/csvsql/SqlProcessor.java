package com.javamain.antlr.csvsql;

import com.javamain.antlr.csvsql.antlr.pocketsLexer;
import com.javamain.antlr.csvsql.antlr.pocketsParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class SqlProcessor {
    public void processSql(String sql) {
        CharStream cs = CharStreams.fromString(sql);
        pocketsLexer lexer = new pocketsLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        pocketsParser parser = new pocketsParser(tokens);
        ParseTree tree = parser.pocket();

        CustomSqlListener listener = new CustomSqlListener();
        ParseTreeWalker.DEFAULT.walk(listener, tree);
    }
}
