// Generated from pockets.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link pocketsParser}.
 */
public interface pocketsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link pocketsParser#pocket}.
	 * @param ctx the parse tree
	 */
	void enterPocket(pocketsParser.PocketContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#pocket}.
	 * @param ctx the parse tree
	 */
	void exitPocket(pocketsParser.PocketContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(pocketsParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(pocketsParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#create}.
	 * @param ctx the parse tree
	 */
	void enterCreate(pocketsParser.CreateContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#create}.
	 * @param ctx the parse tree
	 */
	void exitCreate(pocketsParser.CreateContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#insert}.
	 * @param ctx the parse tree
	 */
	void enterInsert(pocketsParser.InsertContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#insert}.
	 * @param ctx the parse tree
	 */
	void exitInsert(pocketsParser.InsertContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#delete}.
	 * @param ctx the parse tree
	 */
	void enterDelete(pocketsParser.DeleteContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#delete}.
	 * @param ctx the parse tree
	 */
	void exitDelete(pocketsParser.DeleteContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#select}.
	 * @param ctx the parse tree
	 */
	void enterSelect(pocketsParser.SelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#select}.
	 * @param ctx the parse tree
	 */
	void exitSelect(pocketsParser.SelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#columnNames}.
	 * @param ctx the parse tree
	 */
	void enterColumnNames(pocketsParser.ColumnNamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#columnNames}.
	 * @param ctx the parse tree
	 */
	void exitColumnNames(pocketsParser.ColumnNamesContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#values}.
	 * @param ctx the parse tree
	 */
	void enterValues(pocketsParser.ValuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#values}.
	 * @param ctx the parse tree
	 */
	void exitValues(pocketsParser.ValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#constants}.
	 * @param ctx the parse tree
	 */
	void enterConstants(pocketsParser.ConstantsContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#constants}.
	 * @param ctx the parse tree
	 */
	void exitConstants(pocketsParser.ConstantsContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(pocketsParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(pocketsParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpression(pocketsParser.BooleanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpression(pocketsParser.BooleanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterCompare(pocketsParser.CompareContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitCompare(pocketsParser.CompareContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(pocketsParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(pocketsParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(pocketsParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(pocketsParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(pocketsParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(pocketsParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link pocketsParser#fileIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterFileIdentifier(pocketsParser.FileIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link pocketsParser#fileIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitFileIdentifier(pocketsParser.FileIdentifierContext ctx);
}