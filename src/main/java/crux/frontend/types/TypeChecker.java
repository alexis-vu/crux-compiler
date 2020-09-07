package crux.frontend.types;

import crux.frontend.Symbol;
import crux.frontend.ast.*;
import crux.frontend.ast.traversal.NullNodeVisitor;
import crux.midend.ir.core.insts.BinaryOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TypeChecker {
    private final HashMap<Node, Type> typeMap = new HashMap<>();
    private final ArrayList<String> errors = new ArrayList<>();

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void check(DeclarationList ast) {
        var inferenceVisitor = new TypeInferenceVisitor();
        inferenceVisitor.visit(ast);
    }

    private void addTypeError(Node n, String message) {
        errors.add(String.format("TypeError%s[%s]", n.getPosition(), message));
    }

    private void setNodeType(Node n, Type ty) {
        typeMap.put(n, ty);
        if (ty.getClass() == ErrorType.class) {
            var error = (ErrorType) ty;
            addTypeError(n, error.getMessage());
        }
    }

    /** 
      *  Returns type of given AST Node.
    **/
  
    public Type getType(Node n) {
        return typeMap.get(n);
    }

    private final class TypeInferenceVisitor extends NullNodeVisitor {
        private Symbol currentFunctionSymbol;
        private Type currentFunctionReturnType;

        private boolean lastStatementReturns;

        @Override
        public void visit(Name name) {
            setNodeType(name, new AddressType(name.getSymbol().getType()));
        }

        @Override
        public void visit(ArrayDeclaration arrayDeclaration) {
            ArrayType arrayType = (ArrayType) arrayDeclaration.getSymbol().getType();
            if(arrayType.getBase().equivalent(new VoidType()))
                setNodeType(arrayDeclaration, new ErrorType("Array " + arrayDeclaration.getSymbol().getName() + " has invalid base type void."));
            else
                setNodeType(arrayDeclaration, arrayType);
        }

        @Override
        public void visit(Assignment assignment) {
            assignment.getLocation().accept(this);
            assignment.getValue().accept(this);
            
            Type locType = getType(assignment.getLocation());
            Type valType = getType(assignment.getValue());
            
            setNodeType(assignment, locType.assign(valType));
        }

        @Override
        public void visit(Call call) {
            TypeList argList = new TypeList();
            for (Expression exp : call.getArguments()) {
                exp.accept(this);
                argList.append(getType(exp));
            }
            Type calleeType = call.getCallee().getType();
            setNodeType(call, calleeType.call(argList));
        }

        @Override
        public void visit(DeclarationList declarationList) {
            for(Node declaration : declarationList.getChildren()) {
                declaration.accept(this);
            }
        }

        @Override
        public void visit(Dereference dereference) {
            dereference.getAddress().accept(this);
            Type type =  getType(dereference.getAddress());
            setNodeType(dereference, type.deref());
        }

        @Override
        public void visit(FunctionDefinition functionDefinition) {
            currentFunctionSymbol = functionDefinition.getSymbol();
            List<Symbol> parameters = functionDefinition.getParameters();
            FuncType funcType = (FuncType) currentFunctionSymbol.getType();
            currentFunctionReturnType = funcType.getRet();

            // Check if main
            if(currentFunctionSymbol.getName().equals("main")) {
                // Check if main signature correct
                if (!(currentFunctionReturnType instanceof VoidType) || (parameters != null && parameters.size() != 0))
                    setNodeType(functionDefinition, new ErrorType("Function main has invalid signature."));
            } else {
                // Check if parameters are void or errors
                int position = 0;
                for (Symbol param : parameters) {
                    if (param.getType() instanceof VoidType)
                        setNodeType(functionDefinition, new ErrorType("Function " + currentFunctionSymbol.getName() + " has a void argument in position " + position + "."));
                    else if (param.getType() == null)
                        setNodeType(functionDefinition, new ErrorType("Function " + currentFunctionSymbol.getName() + " has an error in argument in position " + position + ": Unknown type: error."));
                    ++position;
                }
            }
            // Visit body of function
            functionDefinition.getStatements().accept(this);
            // Check if function returns proper type and that all paths return
            if (lastStatementReturns && !(currentFunctionReturnType instanceof VoidType))
                setNodeType(functionDefinition, new ErrorType("Not all code paths in function " + currentFunctionSymbol.getName() + " return a value."));
            else
                setNodeType(functionDefinition, currentFunctionReturnType);
        }

        @Override
        public void visit(IfElseBranch ifElseBranch) {
            ifElseBranch.getCondition().accept(this);
            Type conditionType = getType(ifElseBranch.getCondition());
            if (!conditionType.equivalent(new BoolType()))
                setNodeType(ifElseBranch, new ErrorType("IfElseBranch requires bool condition not " + conditionType.toString() + "."));
            else {
                lastStatementReturns = true;
                ifElseBranch.getThenBlock().accept(this);
                // Check if there is a return in then and else block
                boolean thenBlockReturns = lastStatementReturns;
                boolean elseBlockReturns = false;
                if (ifElseBranch.getElseBlock() != null) {
                    lastStatementReturns = true;
                    ifElseBranch.getElseBlock().accept(this);
                    elseBlockReturns = lastStatementReturns;
                }
                boolean bothBlocksReturn = thenBlockReturns && elseBlockReturns;
                boolean oneBlockReturn = thenBlockReturns ^ elseBlockReturns;

                lastStatementReturns = bothBlocksReturn || oneBlockReturn;
            }         
        }

        @Override
        public void visit(ArrayAccess access) {
            access.getBase().accept(this);
            access.getOffset().accept(this);
            
            ArrayType arrayType = (ArrayType) access.getBase().getSymbol().getType();
            Type offsetType = getType(access.getOffset());
            Type resultType = arrayType.index(offsetType);
            setNodeType(access, resultType);
        }

        @Override
        public void visit(LiteralBool literalBool) {
            setNodeType(literalBool, new BoolType());
        }

        @Override
        public void visit(LiteralInt literalInt) {
            setNodeType(literalInt, new IntType());
        }

        @Override
        public void visit(OpExpr op) {
            // Comparison Operator
            if (op.getOp() == OpExpr.Operation.GE ||
                op.getOp() == OpExpr.Operation.GT ||
                op.getOp() == OpExpr.Operation.LE ||
                op.getOp() == OpExpr.Operation.LT ||
                op.getOp() == OpExpr.Operation.EQ ||
                op.getOp() == OpExpr.Operation.NE) {

                // Get left hand side type
                op.getLeft().accept(this);
                Type left = getType(op.getLeft());

                // Get right hand side type
                op.getRight().accept(this);
                Type right = getType(op.getRight());

                setNodeType(op, left.compare(right));

            // Addition Operator
            } else if (op.getOp() == OpExpr.Operation.ADD) {
                // Get left hand side type
                op.getLeft().accept(this);
                Type left = getType(op.getLeft());

                // Get right hand side type
                op.getRight().accept(this);
                Type right = getType(op.getRight());

                setNodeType(op, left.add(right));

            // Subtraction Operator
            } else if (op.getOp() == OpExpr.Operation.SUB) {
                // Get left hand side type
                op.getLeft().accept(this);
                Type left = getType(op.getLeft());

                // Get right hand side type
                op.getRight().accept(this);
                Type right = getType(op.getRight());

                setNodeType(op, left.sub(right));

            // Multiplication Operator
            } else if (op.getOp() == OpExpr.Operation.MULT) {
                // Get left hand side type
                op.getLeft().accept(this);
                Type left = getType(op.getLeft());

                // Get right hand side type
                op.getRight().accept(this);
                Type right = getType(op.getRight());

                setNodeType(op, left.mul(right));

            // Division Operator
            } else if (op.getOp() == OpExpr.Operation.DIV) {
                // Get left hand side type
                op.getLeft().accept(this);
                Type left = getType(op.getLeft());

                // Get right hand side type
                op.getRight().accept(this);
                Type right = getType(op.getRight());

                setNodeType(op, left.div(right));

            // And Operator
            } else if (op.getOp() == OpExpr.Operation.LOGIC_AND) {
                // Get left hand side type
                op.getLeft().accept(this);
                Type left = getType(op.getLeft());

                // Get right hand side type
                op.getRight().accept(this);
                Type right = getType(op.getRight());

                setNodeType(op, left.and(right));

            // Or Operator
            } else if (op.getOp() == OpExpr.Operation.LOGIC_OR) {
                // Get left hand side type
                op.getLeft().accept(this);
                Type left = getType(op.getLeft());

                // Get right hand side type
                op.getRight().accept(this);
                Type right = getType(op.getRight());

                setNodeType(op, left.or(right));

            // Not Operator
            } else if (op.getOp() == OpExpr.Operation.LOGIC_NOT) {
                op.getLeft().accept(this);
                Type left = getType(op.getLeft());
                setNodeType(op, left.not());
            }
        }

        @Override
        public void visit(Return ret) {
            ret.getValue().accept(this);
            Type retType = getType(ret.getValue());

            if (retType.equivalent(currentFunctionReturnType))
                setNodeType(ret, currentFunctionReturnType);
            else
                setNodeType(ret, new ErrorType("Function " + currentFunctionSymbol.getName() + " returns " + currentFunctionReturnType.toString() + " not " + retType.toString() + "."));
            lastStatementReturns = false;
        }

        @Override
        public void visit(StatementList statementList) {
            for(Node statement : statementList.getChildren()) {
                statement.accept(this);
            }
        }

        @Override
        public void visit(VariableDeclaration variableDeclaration) {
            Type type = variableDeclaration.getSymbol().getType();
            if(type.equivalent(new VoidType()))
                setNodeType(variableDeclaration, new ErrorType("Variable " + variableDeclaration.getSymbol().getName() + " has invalid type void."));
            else
                setNodeType(variableDeclaration, type);
        }

        @Override
        public void visit(WhileLoop whileLoop) {
            whileLoop.getCondition().accept(this);
            Type conditionType = getType(whileLoop.getCondition());
            if (!conditionType.equivalent(new BoolType()))
                setNodeType(whileLoop, new ErrorType("WhileLoop requires bool condition not " + conditionType.toString() + "."));
            else {
                whileLoop.getBody().accept(this);
                lastStatementReturns = true;
            }
        }
    }
}
