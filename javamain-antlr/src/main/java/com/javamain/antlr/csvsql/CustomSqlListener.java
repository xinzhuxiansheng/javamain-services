package com.javamain.antlr.csvsql;

import com.javamain.antlr.csvsql.antlr.pocketsBaseListener;
import com.javamain.antlr.csvsql.antlr.pocketsParser;
import com.javamain.antlr.util.CSVUtils;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomSqlListener extends pocketsBaseListener {

    @Override
    public void enterCreate(pocketsParser.CreateContext ctx) {
        // 获取文件名
        String fileName = ctx.fileIdentifier().getText();

        // 获取列名
        List<String> columnNames = ctx.columnNames().identifier().stream()
                .map(RuleContext::getText)
                .collect(Collectors.toList());

        // 创建CSV文件
        CSVUtils.createCSV(fileName, columnNames.toArray(new String[0]));
    }

    @Override
    public void enterInsert(pocketsParser.InsertContext ctx) {
        // 获取文件名
        String fileName = ctx.fileIdentifier().getText();

        List<List<String>> rowsValues = ctx.values().stream()
                .map(valuesContext -> valuesContext.constants().constant().stream()
                        .map(ParseTree::getText)
                        .map(this::removeQuotes) // 去除引号
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        // 写入
        rowsValues.forEach(r -> {
            CSVUtils.insertData(fileName, r.toArray(new String[0]));
        });
    }

    @Override
    public void enterSelect(pocketsParser.SelectContext ctx) {
        // 获取文件名
        String fileName = ctx.fileIdentifier().getText();

        // 获取要选择的列名
        List<String> columnNames = ctx.expressionList().expression().stream()
                .map(ParseTree::getText)
                .collect(Collectors.toList());

        // 检查是否有 WHERE 子句
        BooleanExpressionInfo whereClause = null;
        if (ctx.booleanExpression() != null) {
            whereClause = parseBooleanExpression(ctx.booleanExpression());
        }

        // 读取并处理CSV文件的逻辑
        List<Map<String, Object>> rows = CSVUtils.readFromCsv(fileName, columnNames, whereClause);

        rows.forEach(row -> {
            row.forEach((key, value) -> System.out.println(key + ": " + value));
            System.out.println("---------");
        });
    }

    @Override
    public void enterDelete(pocketsParser.DeleteContext ctx) {
        String fileName = ctx.fileIdentifier().getText();
        try {
            CSVUtils.deleteFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BooleanExpressionInfo parseBooleanExpression(pocketsParser.BooleanExpressionContext ctx) {
        BooleanExpressionInfo expression = new BooleanExpressionInfo();

        if (ctx.compare() != null) {
            // 简单的比较表达式
            expression.leftOperand = ctx.identifier().getText();
            expression.operator = ctx.compare().getText();
            expression.rightOperand = ctx.expression().getText();
        } else {
            // 复合表达式
            expression.left = parseBooleanExpression(ctx.left);
            expression.right = parseBooleanExpression(ctx.right);
            expression.operator = ctx.operator.getText();
        }

        return expression;
    }


    /**
     * 移除 字符串的双引号
     *
     * @param text
     * @return
     */
    private String removeQuotes(String text) {
        if (text != null && text.startsWith("\"") && text.endsWith("\"")) {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }
}
