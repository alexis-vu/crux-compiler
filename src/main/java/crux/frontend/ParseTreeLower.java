package crux.frontend;

import crux.frontend.ast.*;
import crux.frontend.ast.OpExpr.Operation;
import crux.frontend.pt.CruxBaseVisitor;
import crux.frontend.pt.CruxParser;
import crux.frontend.types.*;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * In this class, you're going to implement functionality that transform a input ParseTree
 * into an AST tree.
 *
 * The lowering process would start with {@link #lower(CruxParser.ProgramContext)}. Which take top-level
 * parse tree as input and process its children, function definitions and array declarations for example,
 * using other utilities functions or classes, like {@link #lower(CruxParser.StatementListContext)} or {@link DeclarationVisitor},
 * recursively.
 * */
public final class ParseTreeLower {
    private final DeclarationVisitor declarationVisitor = new DeclarationVisitor();
    private final StatementVisitor statementVisitor = new StatementVisitor();
    private final ExpressionVisitor expressionVisitor = new ExpressionVisitor(true);
    private final ExpressionVisitor locationVisitor = new ExpressionVisitor(false);

    private final SymbolTable symTab;

    public ParseTreeLower(PrintStream err) {
        symTab = new SymbolTable(err);
    }

    private static Position makePosition(ParserRuleContext ctx) {
        var start = ctx.start;
        return new Position(start.getLine(), start.getCharPositionInLine());
    }

    private Type getType(CruxParser.TypeContext typeContext) {
        if (typeContext.Int() != null) {
            return new IntType();
        } else if (typeContext.Void() != null) {
            return new VoidType();
        } else if (typeContext.Bool() != null) {
            return new BoolType();
        } else return null;
    }

    private Operation getOp0(CruxParser.Op0Context op0) {
        var op = Operation.EQ;
        if (op0.Greater_Equal() != null) op = Operation.GE;
        else if (op0.Lesser_Equal() != null) op = Operation.LE;
        else if (op0.Greater_Than() != null) op = Operation.GT;
        else if (op0.Less_Than() != null) op = Operation.LT;
        else if (op0.Not_Equal() != null) op = Operation.NE;
        return op;
    }

    private Operation getOp1(CruxParser.Op1Context op1) {
        var op = Operation.ADD;
        if (op1.Or() != null) op = Operation.LOGIC_OR;
        else if (op1.Sub() != null) op = Operation.SUB;
        return op;
    }

    private Operation getOp2(CruxParser.Op2Context op2) {
        var op = Operation.LOGIC_AND;
        if (op2.Mul() != null) op = Operation.MULT;
        else if (op2.Div() != null) op = Operation.DIV;
        return op;
    }

    /**
     * Should returns true if we have encountered an error.
     */
  
    public boolean hasEncounteredError() {
        return symTab.hasEncounteredError();
    }
  
    /**
     * Lower top-level parse tree to AST
     * @return a {@link DeclarationList} object representing the top-level AST.
     * */
    public DeclarationList lower(CruxParser.ProgramContext program) {
        List<Declaration> declarations = new ArrayList<>();
        for (CruxParser.DeclarationContext declaration : program.declarationList().declaration()) {
            declarations.add(declaration.accept(declarationVisitor));
        }
        if (hasEncounteredError()) return null;
        return new DeclarationList(makePosition(program), declarations);
    }

    /**
     * Lower statement list by lower individual statement into AST.
     * @return a {@link StatementList} AST object.
     * */


    private StatementList lower(CruxParser.StatementListContext statementList) {
        List<Statement> statements = new ArrayList<>();
        for (CruxParser.StatementContext statement : statementList.statement()) {
            statements.add(statement.accept(statementVisitor));
        }
        return new StatementList(makePosition(statementList), statements);
    }

  
    /**
     * Similar to {@link #lower(CruxParser.StatementListContext)}, but handling symbol table
     * as well.
     * @return a {@link StatementList} AST object.
     * */


    private StatementList lower(CruxParser.StatementBlockContext statementBlock) {
        symTab.enter();
        StatementList statementList = lower(statementBlock.statementList());
        symTab.exit();
        return statementList;
    }


    /**
     * A parse tree visitor to create AST nodes derived from {@link Declaration}
     * */
    private final class DeclarationVisitor extends CruxBaseVisitor<Declaration> {
        /**
         * Visit a parse tree variable declaration and create an AST {@link VariableDeclaration}
         * @return an AST {@link VariableDeclaration}
         * */


        @Override
        public VariableDeclaration visitVariableDeclaration(CruxParser.VariableDeclarationContext ctx) {
            var position = makePosition(ctx);
            Symbol symbol = symTab.add(position, ctx.Identifier().toString(), getType(ctx.type()));
            return new VariableDeclaration(position, symbol);
        }


        /**
         * Visit a parse tree array declaration and create an AST {@link ArrayDeclaration}
         * @return an AST {@link ArrayDeclaration}
         * */


        @Override
        public Declaration visitArrayDeclaration(CruxParser.ArrayDeclarationContext ctx) {
            var position = makePosition(ctx);
            var arrayType = new ArrayType(Long.parseLong(ctx.Integer().toString()), getType(ctx.type()));
            return new ArrayDeclaration(position, symTab.add(position, ctx.Identifier().toString(), arrayType));
        }


        /**
         * Visit a parse tree function definition and create an AST {@link FunctionDefinition}
         * @return an AST {@link FunctionDefinition}
         * */

        @Override
        public Declaration visitFunctionDefinition(CruxParser.FunctionDefinitionContext ctx) {
            //List<Type> types = new ArrayList<>();
            List<Symbol> parameters = new ArrayList<>();
            TypeList types = new TypeList();

            // Get parameter types
            for (CruxParser.ParameterContext parameter : ctx.parameterList().parameter()) {
                types.append(getType(parameter.type()));
            }

            // Add function to scope and enter
            FuncType funcType = new FuncType(types, getType(ctx.type()));
            symTab.add(makePosition(ctx), ctx.Identifier().toString(), funcType);
            symTab.enter();

            // Add Parameters into Function scope
            for (CruxParser.ParameterContext parameter : ctx.parameterList().parameter()) {
                parameters.add(symTab.add(makePosition(parameter), parameter.Identifier().toString(), getType(parameter.type())));
            }

            // Get function statements
            StatementList statementList = lower(ctx.statementBlock().statementList());
            symTab.exit();

            return new FunctionDefinition(makePosition(ctx), new Symbol(ctx.Identifier().toString(), funcType), parameters, statementList);
        }

    }

    /**
     * A parse tree visitor to create AST nodes derived from {@link Statement}
     * */
    private final class StatementVisitor extends CruxBaseVisitor<Statement> {
        /**
         * Visit a parse tree variable declaration and create an AST {@link VariableDeclaration}.
         * Since {@link VariableDeclaration} is both {@link Declaration} and {@link Statement},
         * we simply delegate this to {@link DeclarationVisitor#visitArrayDeclaration(CruxParser.ArrayDeclarationContext)}
         * which we implement earlier.
         * @return an AST {@link VariableDeclaration}
         * */


        @Override
        public Statement visitVariableDeclaration(CruxParser.VariableDeclarationContext ctx) {
            return declarationVisitor.visitVariableDeclaration(ctx);
        }

      
        /**
         * Visit a parse tree assignment statement and create an AST {@link Assignment}
         * @return an AST {@link Assignment}
         * */


        @Override
        public Statement visitAssignmentStatement(CruxParser.AssignmentStatementContext ctx) {
            return new Assignment(makePosition(ctx), ctx.designator().accept(locationVisitor), ctx.expression0().accept(expressionVisitor));
        }


        /**
         * Visit a parse tree call statement and create an AST {@link Call}.
         * Since {@link Call} is both {@link Expression} and {@link Statement},
         * we simply delegate this to {@link ExpressionVisitor#visitCallExpression(CruxParser.CallExpressionContext)}
         * that we will implement later.
         * @return an AST {@link Call}
         * */

        @Override
        public Statement visitCallStatement(CruxParser.CallStatementContext ctx) {
            Statement statement = expressionVisitor.visitCallExpression(ctx.callExpression());
            return statement;
        }

          
        /**
         * Visit a parse tree if-else branch and create an AST {@link IfElseBranch}.
         * The template code shows partial implementations that visit the then block and else block
         * recursively before using those returned AST nodes to construct {@link IfElseBranch} object.
         * @return an AST {@link IfElseBranch}
         * */

        @Override
        public Statement visitIfStatement(CruxParser.IfStatementContext ctx) {
            Expression condition = ctx.expression0().accept(expressionVisitor);
            // Get statements in If block
            StatementList thenBlock = lower(ctx.statementBlock(0));

            // Get statements in Else block if it exists
            if (ctx.statementBlock().size() > 1) {
                StatementList elseBlock = lower(ctx.statementBlock(1));
                return new IfElseBranch(makePosition(ctx), condition, thenBlock, elseBlock);
            }
            List<Statement> noElseBlock = new ArrayList<>();
            return new IfElseBranch(makePosition(ctx), condition, thenBlock, new StatementList(makePosition(ctx), noElseBlock));
        }


        /**
         * Visit a parse tree while loop and create an AST {@link WhileLoop}.
         * You'll going to use a similar techniques as {@link #visitIfStatement(CruxParser.IfStatementContext)}
         * to decompose this construction.
         * @return an AST {@link WhileLoop}
         * */

        @Override
        public Statement visitWhileStatement(CruxParser.WhileStatementContext ctx) {
            Expression condition = ctx.expression0().accept(expressionVisitor);
            // Create new scope for While loop and get statement body
            StatementList body = lower(ctx.statementBlock());
            return new WhileLoop(makePosition(ctx), condition, body);
        }


        /**
         * Visit a parse tree return statement and create an AST {@link Return}.
         * Here we show a simple example of how to lower a simple parse tree construction.
         * @return an AST {@link Return}
         * */

        @Override
        public Statement visitReturnStatement(CruxParser.ReturnStatementContext ctx) {
            return new Return(makePosition(ctx), ctx.expression0().accept(expressionVisitor));
        }

    }

    private final class ExpressionVisitor extends CruxBaseVisitor<Expression> {
        private final boolean dereferenceDesignator;

        private ExpressionVisitor(boolean dereferenceDesignator) {
            this.dereferenceDesignator = dereferenceDesignator;
        }


        @Override
        public Expression visitExpression0(CruxParser.Expression0Context ctx) {
            if (ctx.expression1().size() > 1) {
                Expression left = ctx.expression1().get(0).accept(expressionVisitor);
                Expression right = ctx.expression1().get(1).accept(expressionVisitor);
                return new OpExpr(makePosition(ctx.op0()), getOp0(ctx.op0()), left, right);
            }
            return ctx.expression1().get(0).accept(expressionVisitor);
        }


        @Override
        public Expression visitExpression1(CruxParser.Expression1Context ctx) {
            var left = ctx.expression2().get(0).accept(expressionVisitor);
            if (ctx.expression2().size() > 1) {
                for (int i = 1; i < ctx.expression2().size(); ++i) {
                    var right = ctx.expression2().get(i).accept(expressionVisitor);
                    left = new OpExpr(makePosition(ctx.op1(i-1)), getOp1(ctx.op1(i-1)), left, right);
                }
            }
            return left;
        }

      

        @Override
        public Expression visitExpression2(CruxParser.Expression2Context ctx) {
            var left = ctx.expression3().get(0).accept(expressionVisitor);
            if (ctx.expression3().size() > 1) {
                for (int i = 1; i < ctx.expression3().size(); ++i) {
                    var right = ctx.expression3().get(i).accept(expressionVisitor);
                    left = new OpExpr(makePosition(ctx.op2(i-1)), getOp2(ctx.op2(i-1)), left, right);
                }
            }
            return left;
        }

        @Override
        public Expression visitExpression3(CruxParser.Expression3Context ctx) {
            Position position = makePosition(ctx);

            if (ctx.Not() != null) {
                Expression left = ctx.expression3().accept(expressionVisitor);
                OpExpr opExpr = new OpExpr(position, Operation.LOGIC_NOT, left, null);
                return opExpr;
            }
            else if (ctx.Open_Paren() != null) {
                Expression expr0 = ctx.expression0().accept(expressionVisitor);
                return expr0;
            }
            else if (ctx.designator() != null) {
                return ctx.designator().accept(expressionVisitor);
            }
            else if (ctx.callExpression() != null) {
                return ctx.callExpression().accept(expressionVisitor);
            }
            else if (ctx.literal() != null) {
                return ctx.literal().accept(expressionVisitor);
            }
            return null;
        }

      

        @Override
        public Call visitCallExpression(CruxParser.CallExpressionContext ctx) {
            Position position = makePosition(ctx);
            String calleeName = ctx.Identifier().toString();
            Symbol callee = symTab.lookup(position, calleeName);
            List<Expression> arguments = new ArrayList<>();

            // Get expression in expression list for arguments array
            for (CruxParser.Expression0Context expression : ctx.expressionList().expression0()) {
                arguments.add(expression.accept(expressionVisitor));
            }
            Call callExpr = new Call(position, callee, arguments);
            return callExpr;
        }



        @Override
        public Expression visitDesignator(CruxParser.DesignatorContext ctx) {
            String identifier = ctx.Identifier().toString();
            Symbol symbol = symTab.lookup(makePosition(ctx), identifier);   // Check if variable has been declared already
            Position position = makePosition(ctx);
            Name name = new Name(position, symbol);

            // If array then access array and dereference
            if(ctx.expression0() != null) {
                ArrayAccess arrayAccess = new ArrayAccess(makePosition(ctx.expression0()), name, ctx.expression0().accept(expressionVisitor));
                if (dereferenceDesignator) return new Dereference(position, arrayAccess);
                else return arrayAccess;
            }

            // Else dereference name of variable
            if (dereferenceDesignator) return new Dereference(position, name);
            else return name;
        }



        @Override
        public Expression visitLiteral(CruxParser.LiteralContext ctx) {
            var position = makePosition(ctx);
            if (ctx.Integer() != null) {
                long value = Long.parseLong(ctx.Integer().toString());
                LiteralInt literal = new LiteralInt(position, value);
                return literal;
            }
            else if (ctx.True() != null) {
                LiteralBool literal = new LiteralBool(position, true);
                return literal;
            }
            else if (ctx.False() != null) {
                LiteralBool literal = new LiteralBool(position, false);
                return literal;
            }
            return null;
        }

    }
}
