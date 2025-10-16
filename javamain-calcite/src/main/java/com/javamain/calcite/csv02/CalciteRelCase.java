package com.javamain.calcite.csv02;

import org.apache.calcite.adapter.csv.CsvSchema;
import org.apache.calcite.adapter.csv.CsvTable;
import org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.config.CalciteConnectionProperty;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.plan.ConventionTraitDef;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.hep.HepPlanner;
import org.apache.calcite.plan.hep.HepProgramBuilder;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.rules.CoreRules;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlExplainFormat;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorUtil;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.sql2rel.StandardConvertletTable;
import org.apache.calcite.tools.Frameworks;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class CalciteRelCase {
  public static void main(String[] args) throws SqlParseException {
    // Convert query to SqlNode
    String sql = "SELECT o.id, o.goods, o.price, o.amount, c.firstname, c.lastname FROM orders AS o LEFT OUTER JOIN consumers c ON o.user_id = c.id WHERE o.amount > 30 ORDER BY o.id LIMIT 5";
    SqlParser.Config config = SqlParser.configBuilder().setCaseSensitive(false).build();
    SqlParser parser = SqlParser.create(sql, config);

//      - 返回 SqlNode 对象，这是 Calcite 中表示 SQL 语句的抽象语法树节点
//              - 这个 AST 包含了 SQL 语句的结构化表示，后续可以用于：
//              - SQL 验证（validate）
//              - 逻辑计划优化
//              - 物理计划生成
//              - 最终执行

    SqlNode sqlNodeParsed = parser.parseQuery(); //  SqlNode 对象，这是 Calcite 中表示 SQL 语句的抽象语法树节点
    System.out.println("[parsed sqlNode]");
    System.out.println(sqlNodeParsed);

    SchemaPlus rootSchema = Frameworks.createRootSchema(true);
    String csvPath = "javamain-calcite\\src\\main\\resources\\csv02";
    CsvSchema csvSchema = new CsvSchema(new File(csvPath), CsvTable.Flavor.SCANNABLE);

    rootSchema.add("orders", csvSchema.getTable("orders"));
    rootSchema.add("consumers", csvSchema.getTable("consumers"));

    JavaTypeFactoryImpl sqlTypeFactory = new JavaTypeFactoryImpl();
    Properties properties = new Properties();
    properties.setProperty(CalciteConnectionProperty.CASE_SENSITIVE.camelName(), "false");
    // reader 接收 schema，用于检测字段名、字段类型、表名等是否存在和一致
    CalciteCatalogReader catalogReader = new CalciteCatalogReader(
            CalciteSchema.from(rootSchema),
            CalciteSchema.from(rootSchema).path(null),
            sqlTypeFactory,
            new CalciteConnectionConfigImpl(properties));
    // 简单示例，大部分参数采用默认值即可
    SqlValidator validator = SqlValidatorUtil.newValidator(
            SqlStdOperatorTable.instance(),
            catalogReader,
            sqlTypeFactory,
            SqlValidator.Config.DEFAULT);
    // validate: SqlNode -> SqlNode
    SqlNode sqlNodeValidated = validator.validate(sqlNodeParsed);
    System.out.println();
    System.out.println("[validated sqlNode]");
    System.out.println(sqlNodeValidated);


    RexBuilder rexBuilder = new RexBuilder(sqlTypeFactory);
    HepProgramBuilder hepProgramBuilder = new HepProgramBuilder();
    hepProgramBuilder.addRuleInstance(CoreRules.FILTER_INTO_JOIN);

    HepPlanner hepPlanner = new HepPlanner(hepProgramBuilder.build());
    hepPlanner.addRelTraitDef(ConventionTraitDef.INSTANCE);

    RelOptCluster relOptCluster = RelOptCluster.create(hepPlanner, rexBuilder);
    SqlToRelConverter sqlToRelConverter = new SqlToRelConverter(
            // 没有使用 view
            new RelOptTable.ViewExpander() {
              @Override
              public RelRoot expandView(RelDataType rowType, String queryString, List<String> schemaPath, List<String> viewPath) {
                return null;
              }
            },
            validator,
            catalogReader,
            relOptCluster,
            // 均使用标准定义即可
            StandardConvertletTable.INSTANCE,
            SqlToRelConverter.config());
    RelRoot logicalPlan = sqlToRelConverter.convertQuery(sqlNodeValidated, false, true);

    System.out.println();
    System.out.println(RelOptUtil.dumpPlan("[Logical plan]", logicalPlan.rel, SqlExplainFormat.TEXT, SqlExplainLevel.NON_COST_ATTRIBUTES));

    hepPlanner.setRoot(logicalPlan.rel);
    RelNode phyPlan = hepPlanner.findBestExp();
    System.out.println(RelOptUtil.dumpPlan("[Physical plan]", phyPlan, SqlExplainFormat.TEXT, SqlExplainLevel.NON_COST_ATTRIBUTES));


  }
}
