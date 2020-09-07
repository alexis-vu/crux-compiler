// Generated from /Users/torispiegel/CS142/crux/src/main/antlr4/crux/frontend/pt/Crux.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CruxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, SemiColon=2, Colon=3, Open_Paren=4, Close_Paren=5, Open_Brace=6, 
		Close_Brace=7, Open_Bracket=8, Close_Bracket=9, Add=10, Sub=11, Mul=12, 
		Div=13, Greater_Equal=14, Lesser_Equal=15, Not_Equal=16, Equal=17, Greater_Than=18, 
		Less_Than=19, Assign=20, Comma=21, Call=22, Integer=23, True=24, False=25, 
		Let=26, Var=27, Void=28, Func=29, Return=30, Bool=31, Int=32, Array=33, 
		And=34, Or=35, Not=36, If=37, Else=38, While=39, Identifier=40, WhiteSpaces=41, 
		Comment=42, DIGIT=43, LOWERCASE_LETTER=44, UPPERCASE_LETTER=45, LETTER=46;
	public static final int
		RULE_program = 0, RULE_declarationList = 1, RULE_declaration = 2, RULE_variableDeclaration = 3, 
		RULE_type = 4, RULE_literal = 5, RULE_reserved = 6, RULE_identifier = 7, 
		RULE_designator = 8, RULE_op0 = 9, RULE_op1 = 10, RULE_op2 = 11, RULE_expression0 = 12, 
		RULE_expression1 = 13, RULE_expression2 = 14, RULE_expression3 = 15, RULE_callExpression = 16, 
		RULE_expressionList = 17, RULE_parameter = 18, RULE_parameterList = 19, 
		RULE_arrayDeclaration = 20, RULE_functionDefinition = 21, RULE_assignmentStatement = 22, 
		RULE_callStatement = 23, RULE_ifStatement = 24, RULE_whileStatement = 25, 
		RULE_returnStatement = 26, RULE_statement = 27, RULE_statementList = 28, 
		RULE_statementBlock = 29;
	public static final String[] ruleNames = {
		"program", "declarationList", "declaration", "variableDeclaration", "type", 
		"literal", "reserved", "identifier", "designator", "op0", "op1", "op2", 
		"expression0", "expression1", "expression2", "expression3", "callExpression", 
		"expressionList", "parameter", "parameterList", "arrayDeclaration", "functionDefinition", 
		"assignmentStatement", "callStatement", "ifStatement", "whileStatement", 
		"returnStatement", "statement", "statementList", "statementBlock"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'_'", "';'", "':'", "'('", "')'", "'{'", "'}'", "'['", "']'", "'+'", 
		"'-'", "'*'", "'/'", "'>='", "'<='", "'!='", "'=='", "'>'", "'<'", "'='", 
		"','", "'::'", null, "'true'", "'false'", "'let'", "'var'", "'void'", 
		"'func'", "'return'", "'bool'", "'int'", "'array'", "'and'", "'or'", "'not'", 
		"'if'", "'else'", "'while'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "SemiColon", "Colon", "Open_Paren", "Close_Paren", "Open_Brace", 
		"Close_Brace", "Open_Bracket", "Close_Bracket", "Add", "Sub", "Mul", "Div", 
		"Greater_Equal", "Lesser_Equal", "Not_Equal", "Equal", "Greater_Than", 
		"Less_Than", "Assign", "Comma", "Call", "Integer", "True", "False", "Let", 
		"Var", "Void", "Func", "Return", "Bool", "Int", "Array", "And", "Or", 
		"Not", "If", "Else", "While", "Identifier", "WhiteSpaces", "Comment", 
		"DIGIT", "LOWERCASE_LETTER", "UPPERCASE_LETTER", "LETTER"
	};
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
	public String getGrammarFileName() { return "Crux.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CruxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public DeclarationListContext declarationList() {
			return getRuleContext(DeclarationListContext.class,0);
		}
		public TerminalNode EOF() { return getToken(CruxParser.EOF, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			declarationList();
			setState(61);
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

	public static class DeclarationListContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public DeclarationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationList; }
	}

	public final DeclarationListContext declarationList() throws RecognitionException {
		DeclarationListContext _localctx = new DeclarationListContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declarationList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Var) | (1L << Func) | (1L << Array))) != 0)) {
				{
				{
				setState(63);
				declaration();
				}
				}
				setState(68);
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

	public static class DeclarationContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ArrayDeclarationContext arrayDeclaration() {
			return getRuleContext(ArrayDeclarationContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		try {
			setState(72);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Var:
				enterOuterAlt(_localctx, 1);
				{
				setState(69);
				variableDeclaration();
				}
				break;
			case Array:
				enterOuterAlt(_localctx, 2);
				{
				setState(70);
				arrayDeclaration();
				}
				break;
			case Func:
				enterOuterAlt(_localctx, 3);
				{
				setState(71);
				functionDefinition();
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

	public static class VariableDeclarationContext extends ParserRuleContext {
		public TerminalNode Var() { return getToken(CruxParser.Var, 0); }
		public TerminalNode Identifier() { return getToken(CruxParser.Identifier, 0); }
		public TerminalNode Colon() { return getToken(CruxParser.Colon, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(CruxParser.SemiColon, 0); }
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(Var);
			setState(75);
			match(Identifier);
			setState(76);
			match(Colon);
			setState(77);
			type();
			setState(78);
			match(SemiColon);
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

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(CruxParser.Identifier, 0); }
		public TerminalNode Void() { return getToken(CruxParser.Void, 0); }
		public TerminalNode Bool() { return getToken(CruxParser.Bool, 0); }
		public TerminalNode Int() { return getToken(CruxParser.Int, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Void) | (1L << Bool) | (1L << Int) | (1L << Identifier))) != 0)) ) {
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

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode Integer() { return getToken(CruxParser.Integer, 0); }
		public TerminalNode True() { return getToken(CruxParser.True, 0); }
		public TerminalNode False() { return getToken(CruxParser.False, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Integer) | (1L << True) | (1L << False))) != 0)) ) {
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

	public static class ReservedContext extends ParserRuleContext {
		public TerminalNode And() { return getToken(CruxParser.And, 0); }
		public TerminalNode Or() { return getToken(CruxParser.Or, 0); }
		public TerminalNode Not() { return getToken(CruxParser.Not, 0); }
		public TerminalNode Let() { return getToken(CruxParser.Let, 0); }
		public TerminalNode Var() { return getToken(CruxParser.Var, 0); }
		public TerminalNode Array() { return getToken(CruxParser.Array, 0); }
		public TerminalNode Func() { return getToken(CruxParser.Func, 0); }
		public TerminalNode If() { return getToken(CruxParser.If, 0); }
		public TerminalNode Else() { return getToken(CruxParser.Else, 0); }
		public TerminalNode While() { return getToken(CruxParser.While, 0); }
		public TerminalNode True() { return getToken(CruxParser.True, 0); }
		public TerminalNode False() { return getToken(CruxParser.False, 0); }
		public TerminalNode Return() { return getToken(CruxParser.Return, 0); }
		public TerminalNode Void() { return getToken(CruxParser.Void, 0); }
		public TerminalNode Bool() { return getToken(CruxParser.Bool, 0); }
		public TerminalNode Int() { return getToken(CruxParser.Int, 0); }
		public ReservedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reserved; }
	}

	public final ReservedContext reserved() throws RecognitionException {
		ReservedContext _localctx = new ReservedContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_reserved);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << True) | (1L << False) | (1L << Let) | (1L << Var) | (1L << Void) | (1L << Func) | (1L << Return) | (1L << Bool) | (1L << Int) | (1L << Array) | (1L << And) | (1L << Or) | (1L << Not) | (1L << If) | (1L << Else) | (1L << While))) != 0)) ) {
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

	public static class IdentifierContext extends ParserRuleContext {
		public List<TerminalNode> LETTER() { return getTokens(CruxParser.LETTER); }
		public TerminalNode LETTER(int i) {
			return getToken(CruxParser.LETTER, i);
		}
		public List<TerminalNode> DIGIT() { return getTokens(CruxParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(CruxParser.DIGIT, i);
		}
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			_la = _input.LA(1);
			if ( !(_la==T__0 || _la==LETTER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << DIGIT) | (1L << LETTER))) != 0)) {
				{
				{
				setState(87);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << DIGIT) | (1L << LETTER))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(92);
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

	public static class DesignatorContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(CruxParser.Identifier, 0); }
		public List<TerminalNode> Open_Bracket() { return getTokens(CruxParser.Open_Bracket); }
		public TerminalNode Open_Bracket(int i) {
			return getToken(CruxParser.Open_Bracket, i);
		}
		public List<Expression0Context> expression0() {
			return getRuleContexts(Expression0Context.class);
		}
		public Expression0Context expression0(int i) {
			return getRuleContext(Expression0Context.class,i);
		}
		public List<TerminalNode> Close_Bracket() { return getTokens(CruxParser.Close_Bracket); }
		public TerminalNode Close_Bracket(int i) {
			return getToken(CruxParser.Close_Bracket, i);
		}
		public DesignatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_designator; }
	}

	public final DesignatorContext designator() throws RecognitionException {
		DesignatorContext _localctx = new DesignatorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_designator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(Identifier);
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Open_Bracket) {
				{
				{
				setState(94);
				match(Open_Bracket);
				setState(95);
				expression0();
				setState(96);
				match(Close_Bracket);
				}
				}
				setState(102);
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

	public static class Op0Context extends ParserRuleContext {
		public TerminalNode Greater_Equal() { return getToken(CruxParser.Greater_Equal, 0); }
		public TerminalNode Lesser_Equal() { return getToken(CruxParser.Lesser_Equal, 0); }
		public TerminalNode Not_Equal() { return getToken(CruxParser.Not_Equal, 0); }
		public TerminalNode Equal() { return getToken(CruxParser.Equal, 0); }
		public TerminalNode Greater_Than() { return getToken(CruxParser.Greater_Than, 0); }
		public TerminalNode Less_Than() { return getToken(CruxParser.Less_Than, 0); }
		public Op0Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_op0; }
	}

	public final Op0Context op0() throws RecognitionException {
		Op0Context _localctx = new Op0Context(_ctx, getState());
		enterRule(_localctx, 18, RULE_op0);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Greater_Equal) | (1L << Lesser_Equal) | (1L << Not_Equal) | (1L << Equal) | (1L << Greater_Than) | (1L << Less_Than))) != 0)) ) {
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

	public static class Op1Context extends ParserRuleContext {
		public TerminalNode Add() { return getToken(CruxParser.Add, 0); }
		public TerminalNode Sub() { return getToken(CruxParser.Sub, 0); }
		public TerminalNode Or() { return getToken(CruxParser.Or, 0); }
		public Op1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_op1; }
	}

	public final Op1Context op1() throws RecognitionException {
		Op1Context _localctx = new Op1Context(_ctx, getState());
		enterRule(_localctx, 20, RULE_op1);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << Or))) != 0)) ) {
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

	public static class Op2Context extends ParserRuleContext {
		public TerminalNode Mul() { return getToken(CruxParser.Mul, 0); }
		public TerminalNode Div() { return getToken(CruxParser.Div, 0); }
		public TerminalNode And() { return getToken(CruxParser.And, 0); }
		public Op2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_op2; }
	}

	public final Op2Context op2() throws RecognitionException {
		Op2Context _localctx = new Op2Context(_ctx, getState());
		enterRule(_localctx, 22, RULE_op2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Mul) | (1L << Div) | (1L << And))) != 0)) ) {
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

	public static class Expression0Context extends ParserRuleContext {
		public List<Expression1Context> expression1() {
			return getRuleContexts(Expression1Context.class);
		}
		public Expression1Context expression1(int i) {
			return getRuleContext(Expression1Context.class,i);
		}
		public Op0Context op0() {
			return getRuleContext(Op0Context.class,0);
		}
		public Expression0Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression0; }
	}

	public final Expression0Context expression0() throws RecognitionException {
		Expression0Context _localctx = new Expression0Context(_ctx, getState());
		enterRule(_localctx, 24, RULE_expression0);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			expression1();
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Greater_Equal) | (1L << Lesser_Equal) | (1L << Not_Equal) | (1L << Equal) | (1L << Greater_Than) | (1L << Less_Than))) != 0)) {
				{
				setState(110);
				op0();
				setState(111);
				expression1();
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

	public static class Expression1Context extends ParserRuleContext {
		public List<Expression2Context> expression2() {
			return getRuleContexts(Expression2Context.class);
		}
		public Expression2Context expression2(int i) {
			return getRuleContext(Expression2Context.class,i);
		}
		public List<Op1Context> op1() {
			return getRuleContexts(Op1Context.class);
		}
		public Op1Context op1(int i) {
			return getRuleContext(Op1Context.class,i);
		}
		public Expression1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression1; }
	}

	public final Expression1Context expression1() throws RecognitionException {
		Expression1Context _localctx = new Expression1Context(_ctx, getState());
		enterRule(_localctx, 26, RULE_expression1);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			expression2();
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << Or))) != 0)) {
				{
				{
				setState(116);
				op1();
				setState(117);
				expression2();
				}
				}
				setState(123);
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

	public static class Expression2Context extends ParserRuleContext {
		public List<Expression3Context> expression3() {
			return getRuleContexts(Expression3Context.class);
		}
		public Expression3Context expression3(int i) {
			return getRuleContext(Expression3Context.class,i);
		}
		public List<Op2Context> op2() {
			return getRuleContexts(Op2Context.class);
		}
		public Op2Context op2(int i) {
			return getRuleContext(Op2Context.class,i);
		}
		public Expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression2; }
	}

	public final Expression2Context expression2() throws RecognitionException {
		Expression2Context _localctx = new Expression2Context(_ctx, getState());
		enterRule(_localctx, 28, RULE_expression2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			expression3();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Mul) | (1L << Div) | (1L << And))) != 0)) {
				{
				{
				setState(125);
				op2();
				setState(126);
				expression3();
				}
				}
				setState(132);
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

	public static class Expression3Context extends ParserRuleContext {
		public TerminalNode Not() { return getToken(CruxParser.Not, 0); }
		public Expression3Context expression3() {
			return getRuleContext(Expression3Context.class,0);
		}
		public TerminalNode Open_Paren() { return getToken(CruxParser.Open_Paren, 0); }
		public Expression0Context expression0() {
			return getRuleContext(Expression0Context.class,0);
		}
		public TerminalNode Close_Paren() { return getToken(CruxParser.Close_Paren, 0); }
		public DesignatorContext designator() {
			return getRuleContext(DesignatorContext.class,0);
		}
		public CallExpressionContext callExpression() {
			return getRuleContext(CallExpressionContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public Expression3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression3; }
	}

	public final Expression3Context expression3() throws RecognitionException {
		Expression3Context _localctx = new Expression3Context(_ctx, getState());
		enterRule(_localctx, 30, RULE_expression3);
		try {
			setState(142);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Not:
				enterOuterAlt(_localctx, 1);
				{
				setState(133);
				match(Not);
				setState(134);
				expression3();
				}
				break;
			case Open_Paren:
				enterOuterAlt(_localctx, 2);
				{
				setState(135);
				match(Open_Paren);
				setState(136);
				expression0();
				setState(137);
				match(Close_Paren);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 3);
				{
				setState(139);
				designator();
				}
				break;
			case Call:
				enterOuterAlt(_localctx, 4);
				{
				setState(140);
				callExpression();
				}
				break;
			case Integer:
			case True:
			case False:
				enterOuterAlt(_localctx, 5);
				{
				setState(141);
				literal();
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

	public static class CallExpressionContext extends ParserRuleContext {
		public TerminalNode Call() { return getToken(CruxParser.Call, 0); }
		public TerminalNode Identifier() { return getToken(CruxParser.Identifier, 0); }
		public TerminalNode Open_Paren() { return getToken(CruxParser.Open_Paren, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TerminalNode Close_Paren() { return getToken(CruxParser.Close_Paren, 0); }
		public CallExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callExpression; }
	}

	public final CallExpressionContext callExpression() throws RecognitionException {
		CallExpressionContext _localctx = new CallExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_callExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(Call);
			setState(145);
			match(Identifier);
			setState(146);
			match(Open_Paren);
			setState(147);
			expressionList();
			setState(148);
			match(Close_Paren);
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

	public static class ExpressionListContext extends ParserRuleContext {
		public List<Expression0Context> expression0() {
			return getRuleContexts(Expression0Context.class);
		}
		public Expression0Context expression0(int i) {
			return getRuleContext(Expression0Context.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(CruxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(CruxParser.Comma, i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Open_Paren) | (1L << Call) | (1L << Integer) | (1L << True) | (1L << False) | (1L << Not) | (1L << Identifier))) != 0)) {
				{
				setState(150);
				expression0();
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Comma) {
					{
					{
					setState(151);
					match(Comma);
					setState(152);
					expression0();
					}
					}
					setState(157);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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

	public static class ParameterContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(CruxParser.Identifier, 0); }
		public TerminalNode Colon() { return getToken(CruxParser.Colon, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(Identifier);
			setState(161);
			match(Colon);
			setState(162);
			type();
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

	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(CruxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(CruxParser.Comma, i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(164);
				parameter();
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Comma) {
					{
					{
					setState(165);
					match(Comma);
					setState(166);
					parameter();
					}
					}
					setState(171);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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

	public static class ArrayDeclarationContext extends ParserRuleContext {
		public TerminalNode Array() { return getToken(CruxParser.Array, 0); }
		public TerminalNode Identifier() { return getToken(CruxParser.Identifier, 0); }
		public TerminalNode Colon() { return getToken(CruxParser.Colon, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Open_Bracket() { return getToken(CruxParser.Open_Bracket, 0); }
		public TerminalNode Integer() { return getToken(CruxParser.Integer, 0); }
		public TerminalNode Close_Bracket() { return getToken(CruxParser.Close_Bracket, 0); }
		public TerminalNode SemiColon() { return getToken(CruxParser.SemiColon, 0); }
		public ArrayDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayDeclaration; }
	}

	public final ArrayDeclarationContext arrayDeclaration() throws RecognitionException {
		ArrayDeclarationContext _localctx = new ArrayDeclarationContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_arrayDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(Array);
			setState(175);
			match(Identifier);
			setState(176);
			match(Colon);
			setState(177);
			type();
			setState(178);
			match(Open_Bracket);
			setState(179);
			match(Integer);
			setState(180);
			match(Close_Bracket);
			setState(181);
			match(SemiColon);
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

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public TerminalNode Func() { return getToken(CruxParser.Func, 0); }
		public TerminalNode Identifier() { return getToken(CruxParser.Identifier, 0); }
		public TerminalNode Open_Paren() { return getToken(CruxParser.Open_Paren, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public TerminalNode Close_Paren() { return getToken(CruxParser.Close_Paren, 0); }
		public TerminalNode Colon() { return getToken(CruxParser.Colon, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public StatementBlockContext statementBlock() {
			return getRuleContext(StatementBlockContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_functionDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(Func);
			setState(184);
			match(Identifier);
			setState(185);
			match(Open_Paren);
			setState(186);
			parameterList();
			setState(187);
			match(Close_Paren);
			setState(188);
			match(Colon);
			setState(189);
			type();
			setState(190);
			statementBlock();
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

	public static class AssignmentStatementContext extends ParserRuleContext {
		public TerminalNode Let() { return getToken(CruxParser.Let, 0); }
		public DesignatorContext designator() {
			return getRuleContext(DesignatorContext.class,0);
		}
		public TerminalNode Assign() { return getToken(CruxParser.Assign, 0); }
		public Expression0Context expression0() {
			return getRuleContext(Expression0Context.class,0);
		}
		public TerminalNode SemiColon() { return getToken(CruxParser.SemiColon, 0); }
		public AssignmentStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentStatement; }
	}

	public final AssignmentStatementContext assignmentStatement() throws RecognitionException {
		AssignmentStatementContext _localctx = new AssignmentStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_assignmentStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(Let);
			setState(193);
			designator();
			setState(194);
			match(Assign);
			setState(195);
			expression0();
			setState(196);
			match(SemiColon);
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

	public static class CallStatementContext extends ParserRuleContext {
		public CallExpressionContext callExpression() {
			return getRuleContext(CallExpressionContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(CruxParser.SemiColon, 0); }
		public CallStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callStatement; }
	}

	public final CallStatementContext callStatement() throws RecognitionException {
		CallStatementContext _localctx = new CallStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_callStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			callExpression();
			setState(199);
			match(SemiColon);
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

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode If() { return getToken(CruxParser.If, 0); }
		public Expression0Context expression0() {
			return getRuleContext(Expression0Context.class,0);
		}
		public List<StatementBlockContext> statementBlock() {
			return getRuleContexts(StatementBlockContext.class);
		}
		public StatementBlockContext statementBlock(int i) {
			return getRuleContext(StatementBlockContext.class,i);
		}
		public TerminalNode Else() { return getToken(CruxParser.Else, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(If);
			setState(202);
			expression0();
			setState(203);
			statementBlock();
			setState(206);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Else) {
				{
				setState(204);
				match(Else);
				setState(205);
				statementBlock();
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

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode While() { return getToken(CruxParser.While, 0); }
		public Expression0Context expression0() {
			return getRuleContext(Expression0Context.class,0);
		}
		public StatementBlockContext statementBlock() {
			return getRuleContext(StatementBlockContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			match(While);
			setState(209);
			expression0();
			setState(210);
			statementBlock();
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

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode Return() { return getToken(CruxParser.Return, 0); }
		public Expression0Context expression0() {
			return getRuleContext(Expression0Context.class,0);
		}
		public TerminalNode SemiColon() { return getToken(CruxParser.SemiColon, 0); }
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(Return);
			setState(213);
			expression0();
			setState(214);
			match(SemiColon);
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

	public static class StatementContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public CallStatementContext callStatement() {
			return getRuleContext(CallStatementContext.class,0);
		}
		public AssignmentStatementContext assignmentStatement() {
			return getRuleContext(AssignmentStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_statement);
		try {
			setState(222);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Var:
				enterOuterAlt(_localctx, 1);
				{
				setState(216);
				variableDeclaration();
				}
				break;
			case Call:
				enterOuterAlt(_localctx, 2);
				{
				setState(217);
				callStatement();
				}
				break;
			case Let:
				enterOuterAlt(_localctx, 3);
				{
				setState(218);
				assignmentStatement();
				}
				break;
			case If:
				enterOuterAlt(_localctx, 4);
				{
				setState(219);
				ifStatement();
				}
				break;
			case While:
				enterOuterAlt(_localctx, 5);
				{
				setState(220);
				whileStatement();
				}
				break;
			case Return:
				enterOuterAlt(_localctx, 6);
				{
				setState(221);
				returnStatement();
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

	public static class StatementListContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementList; }
	}

	public final StatementListContext statementList() throws RecognitionException {
		StatementListContext _localctx = new StatementListContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_statementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Call) | (1L << Let) | (1L << Var) | (1L << Return) | (1L << If) | (1L << While))) != 0)) {
				{
				{
				setState(224);
				statement();
				}
				}
				setState(229);
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

	public static class StatementBlockContext extends ParserRuleContext {
		public TerminalNode Open_Brace() { return getToken(CruxParser.Open_Brace, 0); }
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public TerminalNode Close_Brace() { return getToken(CruxParser.Close_Brace, 0); }
		public StatementBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementBlock; }
	}

	public final StatementBlockContext statementBlock() throws RecognitionException {
		StatementBlockContext _localctx = new StatementBlockContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_statementBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(Open_Brace);
			setState(231);
			statementList();
			setState(232);
			match(Close_Brace);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\60\u00ed\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2\3\2\3"+
		"\2\3\3\7\3C\n\3\f\3\16\3F\13\3\3\4\3\4\3\4\5\4K\n\4\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\7\t[\n\t\f\t\16\t^\13\t\3\n\3\n"+
		"\3\n\3\n\3\n\7\ne\n\n\f\n\16\nh\13\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\5\16t\n\16\3\17\3\17\3\17\3\17\7\17z\n\17\f\17\16\17}\13"+
		"\17\3\20\3\20\3\20\3\20\7\20\u0083\n\20\f\20\16\20\u0086\13\20\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u0091\n\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\7\23\u009c\n\23\f\23\16\23\u009f\13\23"+
		"\5\23\u00a1\n\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\7\25\u00aa\n\25\f"+
		"\25\16\25\u00ad\13\25\5\25\u00af\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\5\32\u00d1"+
		"\n\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\5\35\u00e1\n\35\3\36\7\36\u00e4\n\36\f\36\16\36\u00e7\13\36\3\37"+
		"\3\37\3\37\3\37\3\37\2\2 \2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&"+
		"(*,.\60\62\64\668:<\2\n\5\2\36\36!\"**\3\2\31\33\3\2\32)\4\2\3\3\60\60"+
		"\5\2\3\3--\60\60\3\2\20\25\4\2\f\r%%\4\2\16\17$$\2\u00e5\2>\3\2\2\2\4"+
		"D\3\2\2\2\6J\3\2\2\2\bL\3\2\2\2\nR\3\2\2\2\fT\3\2\2\2\16V\3\2\2\2\20X"+
		"\3\2\2\2\22_\3\2\2\2\24i\3\2\2\2\26k\3\2\2\2\30m\3\2\2\2\32o\3\2\2\2\34"+
		"u\3\2\2\2\36~\3\2\2\2 \u0090\3\2\2\2\"\u0092\3\2\2\2$\u00a0\3\2\2\2&\u00a2"+
		"\3\2\2\2(\u00ae\3\2\2\2*\u00b0\3\2\2\2,\u00b9\3\2\2\2.\u00c2\3\2\2\2\60"+
		"\u00c8\3\2\2\2\62\u00cb\3\2\2\2\64\u00d2\3\2\2\2\66\u00d6\3\2\2\28\u00e0"+
		"\3\2\2\2:\u00e5\3\2\2\2<\u00e8\3\2\2\2>?\5\4\3\2?@\7\2\2\3@\3\3\2\2\2"+
		"AC\5\6\4\2BA\3\2\2\2CF\3\2\2\2DB\3\2\2\2DE\3\2\2\2E\5\3\2\2\2FD\3\2\2"+
		"\2GK\5\b\5\2HK\5*\26\2IK\5,\27\2JG\3\2\2\2JH\3\2\2\2JI\3\2\2\2K\7\3\2"+
		"\2\2LM\7\35\2\2MN\7*\2\2NO\7\5\2\2OP\5\n\6\2PQ\7\4\2\2Q\t\3\2\2\2RS\t"+
		"\2\2\2S\13\3\2\2\2TU\t\3\2\2U\r\3\2\2\2VW\t\4\2\2W\17\3\2\2\2X\\\t\5\2"+
		"\2Y[\t\6\2\2ZY\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]\21\3\2\2\2^\\"+
		"\3\2\2\2_f\7*\2\2`a\7\n\2\2ab\5\32\16\2bc\7\13\2\2ce\3\2\2\2d`\3\2\2\2"+
		"eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2g\23\3\2\2\2hf\3\2\2\2ij\t\7\2\2j\25\3\2"+
		"\2\2kl\t\b\2\2l\27\3\2\2\2mn\t\t\2\2n\31\3\2\2\2os\5\34\17\2pq\5\24\13"+
		"\2qr\5\34\17\2rt\3\2\2\2sp\3\2\2\2st\3\2\2\2t\33\3\2\2\2u{\5\36\20\2v"+
		"w\5\26\f\2wx\5\36\20\2xz\3\2\2\2yv\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2"+
		"\2|\35\3\2\2\2}{\3\2\2\2~\u0084\5 \21\2\177\u0080\5\30\r\2\u0080\u0081"+
		"\5 \21\2\u0081\u0083\3\2\2\2\u0082\177\3\2\2\2\u0083\u0086\3\2\2\2\u0084"+
		"\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\37\3\2\2\2\u0086\u0084\3\2\2"+
		"\2\u0087\u0088\7&\2\2\u0088\u0091\5 \21\2\u0089\u008a\7\6\2\2\u008a\u008b"+
		"\5\32\16\2\u008b\u008c\7\7\2\2\u008c\u0091\3\2\2\2\u008d\u0091\5\22\n"+
		"\2\u008e\u0091\5\"\22\2\u008f\u0091\5\f\7\2\u0090\u0087\3\2\2\2\u0090"+
		"\u0089\3\2\2\2\u0090\u008d\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2"+
		"\2\2\u0091!\3\2\2\2\u0092\u0093\7\30\2\2\u0093\u0094\7*\2\2\u0094\u0095"+
		"\7\6\2\2\u0095\u0096\5$\23\2\u0096\u0097\7\7\2\2\u0097#\3\2\2\2\u0098"+
		"\u009d\5\32\16\2\u0099\u009a\7\27\2\2\u009a\u009c\5\32\16\2\u009b\u0099"+
		"\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u0098\3\2\2\2\u00a0\u00a1\3\2"+
		"\2\2\u00a1%\3\2\2\2\u00a2\u00a3\7*\2\2\u00a3\u00a4\7\5\2\2\u00a4\u00a5"+
		"\5\n\6\2\u00a5\'\3\2\2\2\u00a6\u00ab\5&\24\2\u00a7\u00a8\7\27\2\2\u00a8"+
		"\u00aa\5&\24\2\u00a9\u00a7\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2"+
		"\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae"+
		"\u00a6\3\2\2\2\u00ae\u00af\3\2\2\2\u00af)\3\2\2\2\u00b0\u00b1\7#\2\2\u00b1"+
		"\u00b2\7*\2\2\u00b2\u00b3\7\5\2\2\u00b3\u00b4\5\n\6\2\u00b4\u00b5\7\n"+
		"\2\2\u00b5\u00b6\7\31\2\2\u00b6\u00b7\7\13\2\2\u00b7\u00b8\7\4\2\2\u00b8"+
		"+\3\2\2\2\u00b9\u00ba\7\37\2\2\u00ba\u00bb\7*\2\2\u00bb\u00bc\7\6\2\2"+
		"\u00bc\u00bd\5(\25\2\u00bd\u00be\7\7\2\2\u00be\u00bf\7\5\2\2\u00bf\u00c0"+
		"\5\n\6\2\u00c0\u00c1\5<\37\2\u00c1-\3\2\2\2\u00c2\u00c3\7\34\2\2\u00c3"+
		"\u00c4\5\22\n\2\u00c4\u00c5\7\26\2\2\u00c5\u00c6\5\32\16\2\u00c6\u00c7"+
		"\7\4\2\2\u00c7/\3\2\2\2\u00c8\u00c9\5\"\22\2\u00c9\u00ca\7\4\2\2\u00ca"+
		"\61\3\2\2\2\u00cb\u00cc\7\'\2\2\u00cc\u00cd\5\32\16\2\u00cd\u00d0\5<\37"+
		"\2\u00ce\u00cf\7(\2\2\u00cf\u00d1\5<\37\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1"+
		"\3\2\2\2\u00d1\63\3\2\2\2\u00d2\u00d3\7)\2\2\u00d3\u00d4\5\32\16\2\u00d4"+
		"\u00d5\5<\37\2\u00d5\65\3\2\2\2\u00d6\u00d7\7 \2\2\u00d7\u00d8\5\32\16"+
		"\2\u00d8\u00d9\7\4\2\2\u00d9\67\3\2\2\2\u00da\u00e1\5\b\5\2\u00db\u00e1"+
		"\5\60\31\2\u00dc\u00e1\5.\30\2\u00dd\u00e1\5\62\32\2\u00de\u00e1\5\64"+
		"\33\2\u00df\u00e1\5\66\34\2\u00e0\u00da\3\2\2\2\u00e0\u00db\3\2\2\2\u00e0"+
		"\u00dc\3\2\2\2\u00e0\u00dd\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00df\3\2"+
		"\2\2\u00e19\3\2\2\2\u00e2\u00e4\58\35\2\u00e3\u00e2\3\2\2\2\u00e4\u00e7"+
		"\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6;\3\2\2\2\u00e7"+
		"\u00e5\3\2\2\2\u00e8\u00e9\7\b\2\2\u00e9\u00ea\5:\36\2\u00ea\u00eb\7\t"+
		"\2\2\u00eb=\3\2\2\2\21DJ\\fs{\u0084\u0090\u009d\u00a0\u00ab\u00ae\u00d0"+
		"\u00e0\u00e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}