package com.javamain.antlr.csvsql.antlr;// Generated from pockets.g4 by ANTLR 4.13.1

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class pocketsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, CREATE=2, SELECT=3, FROM=4, FILE=5, INSERT=6, INTO=7, ROWS=8, 
		UPDATE=9, SET=10, WHERE=11, DELETE=12, NULL=13, DOT=14, COMMA=15, ASTERISK=16, 
		LEFT_PARENTHESIS=17, RIGHT_PARENTHESIS=18, EQUALS=19, NOT=20, MINUS=21, 
		PLUS=22, GT=23, GE=24, LT=25, LE=26, NE=27, AND=28, OR=29, QUOTED_STRING=30, 
		INTEGER_VALUE=31, DECIMAL_VALUE=32, IDENTIFIER=33, WS=34;
	public static final int
		RULE_pocket = 0, RULE_statement = 1, RULE_create = 2, RULE_insert = 3, 
		RULE_delete = 4, RULE_select = 5, RULE_columnNames = 6, RULE_values = 7, 
		RULE_constants = 8, RULE_expressionList = 9, RULE_booleanExpression = 10, 
		RULE_compare = 11, RULE_expression = 12, RULE_identifier = 13, RULE_constant = 14, 
		RULE_fileIdentifier = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"pocket", "statement", "create", "insert", "delete", "select", "columnNames", 
			"values", "constants", "expressionList", "booleanExpression", "compare", 
			"expression", "identifier", "constant", "fileIdentifier"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", null, null, null, null, null, null, null, null, null, null, 
			null, null, "'.'", "','", "'*'", "'('", "')'", "'='", "'!'", "'-'", "'+'", 
			"'>'", "'>='", "'<'", "'<='", "'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, "CREATE", "SELECT", "FROM", "FILE", "INSERT", "INTO", "ROWS", 
			"UPDATE", "SET", "WHERE", "DELETE", "NULL", "DOT", "COMMA", "ASTERISK", 
			"LEFT_PARENTHESIS", "RIGHT_PARENTHESIS", "EQUALS", "NOT", "MINUS", "PLUS", 
			"GT", "GE", "LT", "LE", "NE", "AND", "OR", "QUOTED_STRING", "INTEGER_VALUE", 
			"DECIMAL_VALUE", "IDENTIFIER", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "pockets.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public pocketsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PocketContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(pocketsParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public PocketContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pocket; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterPocket(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitPocket(this);
		}
	}

	public final PocketContext pocket() throws RecognitionException {
		PocketContext _localctx = new PocketContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_pocket);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(34);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CREATE:
				case SELECT:
				case INSERT:
				case DELETE:
					{
					setState(32);
					statement();
					}
					break;
				case T__0:
					{
					setState(33);
					match(T__0);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(36); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 4174L) != 0) );
			setState(38);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public CreateContext create() {
			return getRuleContext(CreateContext.class,0);
		}
		public InsertContext insert() {
			return getRuleContext(InsertContext.class,0);
		}
		public DeleteContext delete() {
			return getRuleContext(DeleteContext.class,0);
		}
		public SelectContext select() {
			return getRuleContext(SelectContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CREATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				create();
				}
				break;
			case INSERT:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				insert();
				}
				break;
			case DELETE:
				enterOuterAlt(_localctx, 3);
				{
				setState(42);
				delete();
				}
				break;
			case SELECT:
				enterOuterAlt(_localctx, 4);
				{
				setState(43);
				select();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(pocketsParser.CREATE, 0); }
		public TerminalNode FILE() { return getToken(pocketsParser.FILE, 0); }
		public FileIdentifierContext fileIdentifier() {
			return getRuleContext(FileIdentifierContext.class,0);
		}
		public ColumnNamesContext columnNames() {
			return getRuleContext(ColumnNamesContext.class,0);
		}
		public CreateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_create; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterCreate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitCreate(this);
		}
	}

	public final CreateContext create() throws RecognitionException {
		CreateContext _localctx = new CreateContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_create);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(CREATE);
			setState(47);
			match(FILE);
			setState(48);
			fileIdentifier();
			setState(49);
			columnNames();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InsertContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(pocketsParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(pocketsParser.INTO, 0); }
		public TerminalNode FILE() { return getToken(pocketsParser.FILE, 0); }
		public FileIdentifierContext fileIdentifier() {
			return getRuleContext(FileIdentifierContext.class,0);
		}
		public ColumnNamesContext columnNames() {
			return getRuleContext(ColumnNamesContext.class,0);
		}
		public TerminalNode ROWS() { return getToken(pocketsParser.ROWS, 0); }
		public List<ValuesContext> values() {
			return getRuleContexts(ValuesContext.class);
		}
		public ValuesContext values(int i) {
			return getRuleContext(ValuesContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(pocketsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(pocketsParser.COMMA, i);
		}
		public InsertContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insert; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterInsert(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitInsert(this);
		}
	}

	public final InsertContext insert() throws RecognitionException {
		InsertContext _localctx = new InsertContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_insert);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(INSERT);
			setState(52);
			match(INTO);
			setState(53);
			match(FILE);
			setState(54);
			fileIdentifier();
			setState(55);
			columnNames();
			setState(56);
			match(ROWS);
			setState(57);
			values();
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(58);
				match(COMMA);
				setState(59);
				values();
				}
				}
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeleteContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(pocketsParser.DELETE, 0); }
		public TerminalNode FILE() { return getToken(pocketsParser.FILE, 0); }
		public FileIdentifierContext fileIdentifier() {
			return getRuleContext(FileIdentifierContext.class,0);
		}
		public DeleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delete; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterDelete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitDelete(this);
		}
	}

	public final DeleteContext delete() throws RecognitionException {
		DeleteContext _localctx = new DeleteContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_delete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(DELETE);
			setState(66);
			match(FILE);
			setState(67);
			fileIdentifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(pocketsParser.SELECT, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(pocketsParser.FROM, 0); }
		public TerminalNode FILE() { return getToken(pocketsParser.FILE, 0); }
		public FileIdentifierContext fileIdentifier() {
			return getRuleContext(FileIdentifierContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(pocketsParser.WHERE, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public SelectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterSelect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitSelect(this);
		}
	}

	public final SelectContext select() throws RecognitionException {
		SelectContext _localctx = new SelectContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_select);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(SELECT);
			setState(70);
			expressionList();
			setState(71);
			match(FROM);
			setState(72);
			match(FILE);
			setState(73);
			fileIdentifier();
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(74);
				match(WHERE);
				setState(75);
				booleanExpression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnNamesContext extends ParserRuleContext {
		public TerminalNode LEFT_PARENTHESIS() { return getToken(pocketsParser.LEFT_PARENTHESIS, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode RIGHT_PARENTHESIS() { return getToken(pocketsParser.RIGHT_PARENTHESIS, 0); }
		public List<TerminalNode> COMMA() { return getTokens(pocketsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(pocketsParser.COMMA, i);
		}
		public ColumnNamesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnNames; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterColumnNames(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitColumnNames(this);
		}
	}

	public final ColumnNamesContext columnNames() throws RecognitionException {
		ColumnNamesContext _localctx = new ColumnNamesContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_columnNames);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(LEFT_PARENTHESIS);
			setState(79);
			identifier();
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(80);
				match(COMMA);
				setState(81);
				identifier();
				}
				}
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(87);
			match(RIGHT_PARENTHESIS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValuesContext extends ParserRuleContext {
		public TerminalNode LEFT_PARENTHESIS() { return getToken(pocketsParser.LEFT_PARENTHESIS, 0); }
		public ConstantsContext constants() {
			return getRuleContext(ConstantsContext.class,0);
		}
		public TerminalNode RIGHT_PARENTHESIS() { return getToken(pocketsParser.RIGHT_PARENTHESIS, 0); }
		public ValuesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_values; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterValues(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitValues(this);
		}
	}

	public final ValuesContext values() throws RecognitionException {
		ValuesContext _localctx = new ValuesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_values);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(LEFT_PARENTHESIS);
			setState(90);
			constants();
			setState(91);
			match(RIGHT_PARENTHESIS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstantsContext extends ParserRuleContext {
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(pocketsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(pocketsParser.COMMA, i);
		}
		public ConstantsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constants; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterConstants(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitConstants(this);
		}
	}

	public final ConstantsContext constants() throws RecognitionException {
		ConstantsContext _localctx = new ConstantsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constants);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			constant();
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(94);
				match(COMMA);
				setState(95);
				constant();
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(pocketsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(pocketsParser.COMMA, i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitExpressionList(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			expression();
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(102);
				match(COMMA);
				setState(103);
				expression();
				}
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanExpressionContext extends ParserRuleContext {
		public BooleanExpressionContext left;
		public Token operator;
		public BooleanExpressionContext right;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public CompareContext compare() {
			return getRuleContext(CompareContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BooleanExpressionContext> booleanExpression() {
			return getRuleContexts(BooleanExpressionContext.class);
		}
		public BooleanExpressionContext booleanExpression(int i) {
			return getRuleContext(BooleanExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(pocketsParser.AND, 0); }
		public TerminalNode OR() { return getToken(pocketsParser.OR, 0); }
		public BooleanExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterBooleanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitBooleanExpression(this);
		}
	}

	public final BooleanExpressionContext booleanExpression() throws RecognitionException {
		return booleanExpression(0);
	}

	private BooleanExpressionContext booleanExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BooleanExpressionContext _localctx = new BooleanExpressionContext(_ctx, _parentState);
		BooleanExpressionContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_booleanExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(110);
			identifier();
			setState(111);
			compare();
			setState(112);
			expression();
			}
			_ctx.stop = _input.LT(-1);
			setState(122);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(120);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new BooleanExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExpression);
						setState(114);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(115);
						((BooleanExpressionContext)_localctx).operator = match(AND);
						setState(116);
						((BooleanExpressionContext)_localctx).right = booleanExpression(3);
						}
						break;
					case 2:
						{
						_localctx = new BooleanExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExpression);
						setState(117);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(118);
						((BooleanExpressionContext)_localctx).operator = match(OR);
						setState(119);
						((BooleanExpressionContext)_localctx).right = booleanExpression(2);
						}
						break;
					}
					} 
				}
				setState(124);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompareContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(pocketsParser.EQUALS, 0); }
		public TerminalNode GT() { return getToken(pocketsParser.GT, 0); }
		public TerminalNode GE() { return getToken(pocketsParser.GE, 0); }
		public TerminalNode LT() { return getToken(pocketsParser.LT, 0); }
		public TerminalNode LE() { return getToken(pocketsParser.LE, 0); }
		public TerminalNode NE() { return getToken(pocketsParser.NE, 0); }
		public CompareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitCompare(this);
		}
	}

	public final CompareContext compare() throws RecognitionException {
		CompareContext _localctx = new CompareContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_compare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 260571136L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode ASTERISK() { return getToken(pocketsParser.ASTERISK, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_expression);
		try {
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(127);
				match(ASTERISK);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				identifier();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				constant();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(pocketsParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(pocketsParser.IDENTIFIER, i);
		}
		public TerminalNode DOT() { return getToken(pocketsParser.DOT, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitIdentifier(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_identifier);
		try {
			setState(136);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				match(IDENTIFIER);
				setState(133);
				match(DOT);
				setState(134);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(135);
				match(IDENTIFIER);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstantContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(pocketsParser.NULL, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode INTEGER_VALUE() { return getToken(pocketsParser.INTEGER_VALUE, 0); }
		public TerminalNode MINUS() { return getToken(pocketsParser.MINUS, 0); }
		public TerminalNode PLUS() { return getToken(pocketsParser.PLUS, 0); }
		public TerminalNode DECIMAL_VALUE() { return getToken(pocketsParser.DECIMAL_VALUE, 0); }
		public List<TerminalNode> QUOTED_STRING() { return getTokens(pocketsParser.QUOTED_STRING); }
		public TerminalNode QUOTED_STRING(int i) {
			return getToken(pocketsParser.QUOTED_STRING, i);
		}
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitConstant(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_constant);
		int _la;
		try {
			int _alt;
			setState(153);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				match(NULL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				identifier();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS || _la==PLUS) {
					{
					setState(140);
					_la = _input.LA(1);
					if ( !(_la==MINUS || _la==PLUS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(143);
				match(INTEGER_VALUE);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS || _la==PLUS) {
					{
					setState(144);
					_la = _input.LA(1);
					if ( !(_la==MINUS || _la==PLUS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(147);
				match(DECIMAL_VALUE);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(149); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(148);
						match(QUOTED_STRING);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(151); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				} while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FileIdentifierContext extends ParserRuleContext {
		public IdentifierContext name;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public FileIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).enterFileIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pocketsListener ) ((pocketsListener)listener).exitFileIdentifier(this);
		}
	}

	public final FileIdentifierContext fileIdentifier() throws RecognitionException {
		FileIdentifierContext _localctx = new FileIdentifierContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_fileIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			((FileIdentifierContext)_localctx).name = identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return booleanExpression_sempred((BooleanExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean booleanExpression_sempred(BooleanExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\"\u009e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0001\u0000\u0001\u0000\u0004\u0000#\b\u0000\u000b\u0000\f\u0000$\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u0001-\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003=\b\u0003\n\u0003"+
		"\f\u0003@\t\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0003\u0005M\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0005\u0006S\b\u0006\n\u0006\f\u0006V\t\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0001\b\u0005\ba\b\b\n\b\f\bd\t\b\u0001\t\u0001\t\u0001\t\u0005\ti\b"+
		"\t\n\t\f\tl\t\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0005\ny\b\n\n\n\f\n|\t\n\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0003\f\u0083\b\f\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0003\r\u0089\b\r\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0003\u000e\u008e\b\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0092\b"+
		"\u000e\u0001\u000e\u0001\u000e\u0004\u000e\u0096\b\u000e\u000b\u000e\f"+
		"\u000e\u0097\u0003\u000e\u009a\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0000\u0001\u0014\u0010\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e\u0000\u0002\u0002\u0000\u0013\u0013"+
		"\u0017\u001b\u0001\u0000\u0015\u0016\u00a3\u0000\"\u0001\u0000\u0000\u0000"+
		"\u0002,\u0001\u0000\u0000\u0000\u0004.\u0001\u0000\u0000\u0000\u00063"+
		"\u0001\u0000\u0000\u0000\bA\u0001\u0000\u0000\u0000\nE\u0001\u0000\u0000"+
		"\u0000\fN\u0001\u0000\u0000\u0000\u000eY\u0001\u0000\u0000\u0000\u0010"+
		"]\u0001\u0000\u0000\u0000\u0012e\u0001\u0000\u0000\u0000\u0014m\u0001"+
		"\u0000\u0000\u0000\u0016}\u0001\u0000\u0000\u0000\u0018\u0082\u0001\u0000"+
		"\u0000\u0000\u001a\u0088\u0001\u0000\u0000\u0000\u001c\u0099\u0001\u0000"+
		"\u0000\u0000\u001e\u009b\u0001\u0000\u0000\u0000 #\u0003\u0002\u0001\u0000"+
		"!#\u0005\u0001\u0000\u0000\" \u0001\u0000\u0000\u0000\"!\u0001\u0000\u0000"+
		"\u0000#$\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000"+
		"\u0000\u0000%&\u0001\u0000\u0000\u0000&\'\u0005\u0000\u0000\u0001\'\u0001"+
		"\u0001\u0000\u0000\u0000(-\u0003\u0004\u0002\u0000)-\u0003\u0006\u0003"+
		"\u0000*-\u0003\b\u0004\u0000+-\u0003\n\u0005\u0000,(\u0001\u0000\u0000"+
		"\u0000,)\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000,+\u0001\u0000"+
		"\u0000\u0000-\u0003\u0001\u0000\u0000\u0000./\u0005\u0002\u0000\u0000"+
		"/0\u0005\u0005\u0000\u000001\u0003\u001e\u000f\u000012\u0003\f\u0006\u0000"+
		"2\u0005\u0001\u0000\u0000\u000034\u0005\u0006\u0000\u000045\u0005\u0007"+
		"\u0000\u000056\u0005\u0005\u0000\u000067\u0003\u001e\u000f\u000078\u0003"+
		"\f\u0006\u000089\u0005\b\u0000\u00009>\u0003\u000e\u0007\u0000:;\u0005"+
		"\u000f\u0000\u0000;=\u0003\u000e\u0007\u0000<:\u0001\u0000\u0000\u0000"+
		"=@\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000"+
		"\u0000?\u0007\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000AB\u0005"+
		"\f\u0000\u0000BC\u0005\u0005\u0000\u0000CD\u0003\u001e\u000f\u0000D\t"+
		"\u0001\u0000\u0000\u0000EF\u0005\u0003\u0000\u0000FG\u0003\u0012\t\u0000"+
		"GH\u0005\u0004\u0000\u0000HI\u0005\u0005\u0000\u0000IL\u0003\u001e\u000f"+
		"\u0000JK\u0005\u000b\u0000\u0000KM\u0003\u0014\n\u0000LJ\u0001\u0000\u0000"+
		"\u0000LM\u0001\u0000\u0000\u0000M\u000b\u0001\u0000\u0000\u0000NO\u0005"+
		"\u0011\u0000\u0000OT\u0003\u001a\r\u0000PQ\u0005\u000f\u0000\u0000QS\u0003"+
		"\u001a\r\u0000RP\u0001\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000TR\u0001"+
		"\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UW\u0001\u0000\u0000\u0000"+
		"VT\u0001\u0000\u0000\u0000WX\u0005\u0012\u0000\u0000X\r\u0001\u0000\u0000"+
		"\u0000YZ\u0005\u0011\u0000\u0000Z[\u0003\u0010\b\u0000[\\\u0005\u0012"+
		"\u0000\u0000\\\u000f\u0001\u0000\u0000\u0000]b\u0003\u001c\u000e\u0000"+
		"^_\u0005\u000f\u0000\u0000_a\u0003\u001c\u000e\u0000`^\u0001\u0000\u0000"+
		"\u0000ad\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000"+
		"\u0000\u0000c\u0011\u0001\u0000\u0000\u0000db\u0001\u0000\u0000\u0000"+
		"ej\u0003\u0018\f\u0000fg\u0005\u000f\u0000\u0000gi\u0003\u0018\f\u0000"+
		"hf\u0001\u0000\u0000\u0000il\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000"+
		"\u0000jk\u0001\u0000\u0000\u0000k\u0013\u0001\u0000\u0000\u0000lj\u0001"+
		"\u0000\u0000\u0000mn\u0006\n\uffff\uffff\u0000no\u0003\u001a\r\u0000o"+
		"p\u0003\u0016\u000b\u0000pq\u0003\u0018\f\u0000qz\u0001\u0000\u0000\u0000"+
		"rs\n\u0002\u0000\u0000st\u0005\u001c\u0000\u0000ty\u0003\u0014\n\u0003"+
		"uv\n\u0001\u0000\u0000vw\u0005\u001d\u0000\u0000wy\u0003\u0014\n\u0002"+
		"xr\u0001\u0000\u0000\u0000xu\u0001\u0000\u0000\u0000y|\u0001\u0000\u0000"+
		"\u0000zx\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{\u0015\u0001"+
		"\u0000\u0000\u0000|z\u0001\u0000\u0000\u0000}~\u0007\u0000\u0000\u0000"+
		"~\u0017\u0001\u0000\u0000\u0000\u007f\u0083\u0005\u0010\u0000\u0000\u0080"+
		"\u0083\u0003\u001a\r\u0000\u0081\u0083\u0003\u001c\u000e\u0000\u0082\u007f"+
		"\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082\u0081"+
		"\u0001\u0000\u0000\u0000\u0083\u0019\u0001\u0000\u0000\u0000\u0084\u0085"+
		"\u0005!\u0000\u0000\u0085\u0086\u0005\u000e\u0000\u0000\u0086\u0089\u0005"+
		"!\u0000\u0000\u0087\u0089\u0005!\u0000\u0000\u0088\u0084\u0001\u0000\u0000"+
		"\u0000\u0088\u0087\u0001\u0000\u0000\u0000\u0089\u001b\u0001\u0000\u0000"+
		"\u0000\u008a\u009a\u0005\r\u0000\u0000\u008b\u009a\u0003\u001a\r\u0000"+
		"\u008c\u008e\u0007\u0001\u0000\u0000\u008d\u008c\u0001\u0000\u0000\u0000"+
		"\u008d\u008e\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000"+
		"\u008f\u009a\u0005\u001f\u0000\u0000\u0090\u0092\u0007\u0001\u0000\u0000"+
		"\u0091\u0090\u0001\u0000\u0000\u0000\u0091\u0092\u0001\u0000\u0000\u0000"+
		"\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u009a\u0005 \u0000\u0000\u0094"+
		"\u0096\u0005\u001e\u0000\u0000\u0095\u0094\u0001\u0000\u0000\u0000\u0096"+
		"\u0097\u0001\u0000\u0000\u0000\u0097\u0095\u0001\u0000\u0000\u0000\u0097"+
		"\u0098\u0001\u0000\u0000\u0000\u0098\u009a\u0001\u0000\u0000\u0000\u0099"+
		"\u008a\u0001\u0000\u0000\u0000\u0099\u008b\u0001\u0000\u0000\u0000\u0099"+
		"\u008d\u0001\u0000\u0000\u0000\u0099\u0091\u0001\u0000\u0000\u0000\u0099"+
		"\u0095\u0001\u0000\u0000\u0000\u009a\u001d\u0001\u0000\u0000\u0000\u009b"+
		"\u009c\u0003\u001a\r\u0000\u009c\u001f\u0001\u0000\u0000\u0000\u0010\""+
		"$,>LTbjxz\u0082\u0088\u008d\u0091\u0097\u0099";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}