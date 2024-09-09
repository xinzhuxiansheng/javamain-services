package com.javamain.db.sqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;
import net.sf.jsqlparser.util.validation.Validation;
import net.sf.jsqlparser.util.validation.ValidationError;
import net.sf.jsqlparser.util.validation.feature.FeaturesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author yzhou
 * @date 2022/11/4
 */
public class SQLTest01 {
    private static final Logger logger = LoggerFactory.getLogger(SQLTest01.class);

    public static void main(String[] args) throws JSQLParserException {
//        test01();
//        test02();
        limitSQLTest();
//        test03();
    }

    public static void test01() throws JSQLParserException {
        String sql = "SELECT NAME, ADDRESS, COL1 FROM USER WHERE SSN IN ('11111111111111', '22222222222222') limit 10;";
        Select select = (Select) CCJSqlParserUtil.parse(sql);

//Start of value modification
        StringBuilder buffer = new StringBuilder();
        ExpressionDeParser expressionDeParser = new ExpressionDeParser() {

            @Override
            public void visit(StringValue stringValue) {
                this.getBuffer().append("XXXX");
            }

        };
        SelectDeParser deparser = new SelectDeParser(expressionDeParser, buffer);
        expressionDeParser.setSelectVisitor(deparser);
        expressionDeParser.setBuffer(buffer);
        select.getSelectBody().accept(deparser);
//End of value modification


        System.out.println(buffer.toString());
//Result is: SELECT NAME, ADDRESS, COL1 FROM USER WHERE SSN IN (XXXX, XXXX)
    }

    public static void test02() throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse("SELECT * FROM tab1");
        System.out.println(stmt);
    }

    /*
        limit number eg: limit 3 -> 取前3条
        limit number01 , number02 eg: limit 2,3 -> 从第二行的下一行开始向下取3条数据（3，,4 ,5）
        limit number01 offset number02 eg: limit 3 offset 2 -> 从第二行的下一行开始向下取3条数据(3 ,4, 5)


        limit 5,10 -> RowCount = 10, Offset = 5
     */
    public static void limitSQLTest() throws JSQLParserException {


    }

    public static void test03() {
//        String sql = "SELECT * FROM myview v JOIN secondview v2 ON v.id = v2.ref";
        String sql = "show databases;";
        Validation validation = new Validation(Arrays.asList(FeaturesAllowed.SELECT), sql);
        List<ValidationError> errors = validation.validate();
// no errors, select - statement is allowed
        if (errors.isEmpty()) {
            // do something else with the parsed statements
            Statements statements = validation.getParsedStatements();
        }

        logger.info("{}", errors);
    }


}



