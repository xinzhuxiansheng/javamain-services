package com.javamain.sqlParserTest;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.AllValue;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author yzhou
 * @date 2022/11/4
 */
public class TestSQL {

    @Test
    public void limitSQLTest() throws JSQLParserException {
        String sqlStr = "SELECT * FROM tmp3 LIMIT 5 OFFSET 3";
        Select select = (Select) CCJSqlParserUtil.parse(sqlStr);

        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        LongValue longValue = plainSelect.getLimit().getRowCount(LongValue.class);
        Assertions.assertNotNull(longValue);
        Assertions.assertEquals(longValue, longValue);
        Assertions.assertNotEquals(new AllValue(), longValue);
        Assertions.assertNotEquals(new NullValue(), longValue);

        Assertions.assertNull(plainSelect.getLimit().getOffset(LongValue.class));
        Assertions.assertNotNull(plainSelect.getOffset().getOffset(LongValue.class));

        sqlStr = "SELECT * FROM tmp3 LIMIT ALL";
        select = (Select) CCJSqlParserUtil.parse(sqlStr);
        plainSelect = (PlainSelect) select.getSelectBody();

        AllValue allValue = plainSelect.getLimit().getRowCount(AllValue.class);
        allValue.accept(new ExpressionVisitorAdapter());
    }

}
