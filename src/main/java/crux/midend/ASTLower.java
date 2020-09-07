package crux.midend;

import crux.frontend.Symbol;
import crux.frontend.ast.*;
import crux.frontend.ast.OpExpr.Operation;
import crux.frontend.ast.traversal.NodeVisitor;
import crux.frontend.types.*;
import crux.midend.ir.core.*;
import crux.midend.ir.core.insts.*;
import crux.midend.ir.core.insts.BinaryOperator.Op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO make sure each function uses: mExpressionValue, copyInst, addEdge, visit if children
// TODO override mLastControlInstruction and mExpressionValue

/**
 * Lower from AST to IR
 * */
public final class ASTLower implements NodeVisitor {
    private Program mCurrentProgram = null;
    private Function mCurrentFunction = null;

    private Value mExpressionValue = null;
    private Instruction mLastControlInstruction = null;
    private Map<Symbol, AddressVar> mCurrentGlobalSymMap = null;
    private Map<String, Symbol> mCurrentGlobalStringMap = null;
    private Map<Symbol, Variable> mCurrentLocalVarMap = null;
    private Map<String, AddressVar> mBuiltInFuncMap = null;
    private TypeChecker mTypeChecker;

    private boolean dereferenceArray = false;

    // If predicate is true, childNum = 1
    // If predicate is false, childNum = 0
    private void addEdge(Instruction src, Instruction dst) {
        if (src == null) {
            mCurrentFunction.setStart(dst);
        } else if (!(src instanceof JumpInst)){
            src.setNext(0, dst);
        } else {
            src.setNext(src.numNext(), dst);
        }
    }

    public ASTLower(TypeChecker checker) {
        mTypeChecker = checker;
    }
  
    public Program lower(DeclarationList ast) {
        visit(ast);
        return mCurrentProgram;
    }

    /**
     * The top level Program
     * */
    private void initBuiltInFunctions() {
        // Add built-in function symbols
        mBuiltInFuncMap = new HashMap<>();
        mBuiltInFuncMap.put("readInt", new AddressVar(new FuncType(null, new IntType()), "readInt"));
        List<Type> printBoolArgs = new ArrayList<>();
        printBoolArgs.add(new BoolType());
        mBuiltInFuncMap.put("printBool", new AddressVar(new FuncType(new TypeList(printBoolArgs), new VoidType()), "printBool"));
        List<Type> printIntArgs = new ArrayList<>();
        printIntArgs.add(new IntType());
        mBuiltInFuncMap.put("printInt", new AddressVar(new FuncType(new TypeList(printIntArgs), new VoidType()), "printInt"));
        mBuiltInFuncMap.put("println", new AddressVar(new FuncType(null, new VoidType()), "println"));
    }

    @Override
    public void visit(DeclarationList declarationList) {
        mCurrentProgram = new Program();
        initBuiltInFunctions();
        mCurrentGlobalSymMap = new HashMap<Symbol, AddressVar>();
        mCurrentGlobalStringMap = new HashMap<String, Symbol>();
        for(Node declaration : declarationList.getChildren()) {
            declaration.accept(this);
        }
    }

    /**
     * Function
     * */
    @Override
    public void visit(FunctionDefinition functionDefinition) {
        String name = functionDefinition.getSymbol().getName();

        // Get function parameters
        mCurrentLocalVarMap = new HashMap<>();
        List<LocalVar> args = new ArrayList<>();
        for (Symbol param : functionDefinition.getParameters()) {
            LocalVar arg = new LocalVar(param.getType(), param.getName());
            args.add(arg);
            mCurrentLocalVarMap.put(param, arg);
        }
        mCurrentFunction = new Function(name, args, (FuncType) functionDefinition.getSymbol().getType());
        mCurrentGlobalSymMap.put(functionDefinition.getSymbol(), new AddressVar(functionDefinition.getSymbol().getType(), name));
        mCurrentGlobalStringMap.put(functionDefinition.getSymbol().getName(), functionDefinition.getSymbol());
        mCurrentProgram.addFunction(mCurrentFunction);

        // Visit statement list
        visit(functionDefinition.getStatements());

        mCurrentLocalVarMap = null;
        mExpressionValue = null;
        mLastControlInstruction = null;
    }

    @Override
    public void visit(StatementList statementList) {
        for (Node statement : statementList.getChildren()) {
            statement.accept(this);
        }
    }

    /**
     * Declarations
     * */
    @Override
    public void visit(VariableDeclaration variableDeclaration) {
        Symbol symbol = variableDeclaration.getSymbol();
        // Check if local or global variable
        if (mCurrentLocalVarMap != null) {
            LocalVar destVar =  new LocalVar(symbol.getType(), symbol.getName());
            mCurrentLocalVarMap.put(symbol, destVar);
        } else {
            AddressVar destVar = new AddressVar(symbol.getType(), symbol.getName());
            mCurrentGlobalSymMap.put(symbol, destVar);
            Constant numElement;
            if (symbol.getType().equivalent(new IntType())) {
                numElement = IntegerConstant.get(mCurrentProgram, 1);
            } else {
                numElement = BooleanConstant.get(mCurrentProgram, false);
            }
            GlobalDecl globalDecl = new GlobalDecl(destVar, numElement);
            mCurrentProgram.addGlobalVar(globalDecl);
        }
    }
  
    @Override
    public void visit(ArrayDeclaration arrayDeclaration) {
        Symbol symbol = arrayDeclaration.getSymbol();
        ArrayType arrayType = (ArrayType) symbol.getType();
        // Check if local or global array
        if (mCurrentLocalVarMap != null) {
            AddressVar destVar = mCurrentFunction.getTempAddressVar(arrayType);
            mCurrentLocalVarMap.put(symbol, destVar);
        } else {
            // Check base type of array
            AddressVar destVar = new AddressVar(arrayType, arrayDeclaration.getSymbol().getName());
            Constant numElement;
            if (arrayType.getBase().equivalent(new IntType())) {
                numElement = IntegerConstant.get(mCurrentProgram, arrayType.getExtent());
            } else {
                numElement = BooleanConstant.get(mCurrentProgram, false);
            }
            GlobalDecl globalDecl = new GlobalDecl(destVar, numElement);
            mCurrentProgram.addGlobalVar(globalDecl);
            mCurrentGlobalSymMap.put(symbol, destVar);
        }
    }

    @Override 
    public void visit(Name name) {
        // If variable is global
        if (mCurrentGlobalSymMap.containsKey(name.getSymbol())) {
            AddressVar base = mCurrentGlobalSymMap.get(name.getSymbol());
            // If variable
            if (base.getType().equivalent(new IntType()) || base.getType().equivalent(new BoolType())) {
                AddressVar destVar = mCurrentFunction.getTempAddressVar(base.getType());
                AddressAt addressAt = new AddressAt(destVar, base);
                addEdge(mLastControlInstruction, addressAt);
                mLastControlInstruction = addressAt;
                mExpressionValue = destVar;
            } else {
                mExpressionValue = base;
            }

        // If variable is local
        } else if (mCurrentLocalVarMap.containsKey(name.getSymbol())){
            Variable destVar = mCurrentLocalVarMap.get(name.getSymbol());
            mExpressionValue = destVar;  
        }
    }

    @Override
    public void visit(Assignment assignment) {
        // Visit value
        visit(assignment.getValue());
        Value value = mExpressionValue;
        LocalVar srcValue = (LocalVar) value;
        // Visit location
        visit(assignment.getLocation());
        Value locationVal = mExpressionValue;

        // Local Variable
        if (mExpressionValue instanceof LocalVar) {
            LocalVar destVar = (LocalVar) locationVal;
            CopyInst copyInst = new CopyInst(destVar, value);
            addEdge(mLastControlInstruction, copyInst);
            mLastControlInstruction = copyInst;
            mExpressionValue = destVar;
        // Global Variable
        } else {
            AddressVar destAddress = (AddressVar) locationVal;
            StoreInst storeInst = new StoreInst(srcValue, destAddress);
            addEdge(mLastControlInstruction, storeInst);
            mLastControlInstruction = storeInst;
            mExpressionValue = destAddress;
        }      
    }

    @Override
    public void visit(Call call) {
        Symbol calleeSym = call.getCallee();
        AddressVar calleeVar;
        // Check if built in or user defined function
        if (mBuiltInFuncMap.containsKey(calleeSym.getName())) {
            calleeVar = mBuiltInFuncMap.get(calleeSym.getName());
        } else {
            calleeVar = mCurrentGlobalSymMap.get(mCurrentGlobalStringMap.get(calleeSym.getName()));
        }
        // Get arguments for function call
        List<LocalVar> params = new ArrayList<>();
        for (Expression expression : call.getArguments()) {
            visit(expression);
            Value expressionVal = mExpressionValue;
            LocalVar param = (LocalVar) expressionVal;
            params.add(param);
        }
        // If return type is void destination is null
        CallInst callInst;
        FuncType funcType = (FuncType) calleeSym.getType();
        Type returnType = funcType.getRet();
        if (returnType.equivalent(new VoidType())) {
            callInst = new CallInst(calleeVar, params);
        } else {
            LocalVar destVar = mCurrentFunction.getTempVar(returnType);
            callInst = new CallInst(destVar, calleeVar, params);
            mExpressionValue = destVar;
        }
        addEdge(mLastControlInstruction, callInst);
        mLastControlInstruction = callInst;
    }

    @Override
    public void visit(OpExpr operation) {
        if (operation.getOp().equals(Operation.LOGIC_AND) ||
            operation.getOp().equals(Operation.LOGIC_OR)) {

            // Logical And Operator
            if (operation.getOp().equals(Operation.LOGIC_AND)) {
                // Make temp variable for predicate
                LocalVar predicate = mCurrentFunction.getTempVar(new BoolType());
                // Visit left operand
                visit(operation.getLeft());
                // Copy left operand value to predicate
                CopyInst copyInstLeft = new CopyInst(predicate, mExpressionValue);
                addEdge(mLastControlInstruction, copyInstLeft);
                // Create jump instruction
                JumpInst jumpInst = new JumpInst(predicate);
                addEdge(copyInstLeft, jumpInst);
                // Connect NOP first because if predicate false go to NOP
                NopInst nopInst = new NopInst();
                addEdge(jumpInst, nopInst);
                // Visit right operand
                mLastControlInstruction = jumpInst;
                visit(operation.getRight());
                // Copy right operand value into predicate
                CopyInst copyInstRight = new CopyInst(predicate, mExpressionValue);
                addEdge(mLastControlInstruction, copyInstRight);
                addEdge(copyInstRight, nopInst);
                // Make last instruction NOP
                mLastControlInstruction = nopInst;
                mExpressionValue = predicate;

            // Logical Or Operator
            } else {
                // Make temp variable for predicate
                LocalVar predicate = mCurrentFunction.getTempVar(new BoolType());
                // Visit left operand
                visit(operation.getLeft());
                // Copy left operand value to predicate
                CopyInst copyInstLeft = new CopyInst(predicate, mExpressionValue);
                addEdge(mLastControlInstruction, copyInstLeft);
                // Create jump instruction
                JumpInst jumpInst = new JumpInst(predicate);
                addEdge(copyInstLeft, jumpInst);
                // Visit right first because if predicate false go to next operand
                mLastControlInstruction = jumpInst;
                visit(operation.getRight());
                // Copy right operand value into predicate
                CopyInst copyInstRight = new CopyInst(predicate, mExpressionValue);
                addEdge(mLastControlInstruction, copyInstRight);
                // Connect right operand and jump to NOP
                NopInst nopInst = new NopInst();
                addEdge(copyInstRight, nopInst);
                addEdge(jumpInst, nopInst);
                // Make last instruction NOP
                mLastControlInstruction = nopInst;
                mExpressionValue = predicate;
            }
        } else {
            visit(operation.getLeft());
            LocalVar lhsVar = (LocalVar) mExpressionValue;

            if (operation.getRight() != null) {
                visit(operation.getRight());
                LocalVar rhsVar = (LocalVar) mExpressionValue;

                // Binary Operations
                if (operation.getOp().equals(Operation.ADD)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new IntType());
                    BinaryOperator binaryOperator = new BinaryOperator(Op.Add, destVar, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, binaryOperator);
                    mLastControlInstruction = binaryOperator;
                    mExpressionValue = destVar;
                } else if (operation.getOp().equals(Operation.SUB)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new IntType());
                    BinaryOperator binaryOperator = new BinaryOperator(Op.Sub, destVar, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, binaryOperator);
                    mLastControlInstruction = binaryOperator;
                    mExpressionValue = destVar;
                } else if (operation.getOp().equals(Operation.MULT)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new IntType());
                    BinaryOperator binaryOperator = new BinaryOperator(Op.Mul, destVar, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, binaryOperator);
                    mLastControlInstruction = binaryOperator;
                    mExpressionValue = destVar;
                } else if (operation.getOp().equals(Operation.DIV)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new IntType());
                    BinaryOperator binaryOperator = new BinaryOperator(Op.Div, destVar, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, binaryOperator);
                    mLastControlInstruction = binaryOperator;
                    mExpressionValue = destVar;
                }
                
                // Compare Instructions
                if (operation.getOp().equals(Operation.GE)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new BoolType());
                    CompareInst compareInst = new CompareInst(destVar, CompareInst.Predicate.GE, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, compareInst);
                    mLastControlInstruction = compareInst;
                    mExpressionValue = destVar;
                } else if (operation.getOp().equals(Operation.GT)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new BoolType());
                    CompareInst compareInst = new CompareInst(destVar, CompareInst.Predicate.GT, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, compareInst);
                    mLastControlInstruction = compareInst;
                    mExpressionValue = destVar;
                } else if (operation.getOp().equals(Operation.LE)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new BoolType());
                    CompareInst compareInst = new CompareInst(destVar, CompareInst.Predicate.LE, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, compareInst);
                    mLastControlInstruction = compareInst;
                    mExpressionValue = destVar;
                } else if (operation.getOp().equals(Operation.LT)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new BoolType());
                    CompareInst compareInst = new CompareInst(destVar, CompareInst.Predicate.LT, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, compareInst);
                    mLastControlInstruction = compareInst;
                    mExpressionValue = destVar;
                } else if (operation.getOp().equals(Operation.NE)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new BoolType());
                    CompareInst compareInst = new CompareInst(destVar, CompareInst.Predicate.NE, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, compareInst);
                    mLastControlInstruction = compareInst;
                    mExpressionValue = destVar;
                } else if (operation.getOp().equals(Operation.EQ)) {
                    LocalVar destVar = mCurrentFunction.getTempVar(new BoolType());
                    CompareInst compareInst = new CompareInst(destVar, CompareInst.Predicate.EQ, lhsVar, rhsVar);
                    addEdge(mLastControlInstruction, compareInst);
                    mLastControlInstruction = compareInst;
                    mExpressionValue = destVar;
                }
            // Unary Operation
            } else {
                LocalVar destVar = mCurrentFunction.getTempVar(new BoolType());
                UnaryNotInst unaryNotInst = new UnaryNotInst(destVar, lhsVar);
                addEdge(mLastControlInstruction, unaryNotInst);
                mLastControlInstruction = unaryNotInst;
                mExpressionValue = destVar;
            }
        }
    }

    @Override
    public void visit(Dereference dereference) {
        dereferenceArray = true;
        visit(dereference.getAddress());
        dereferenceArray = false;
        // Global Variable
        if (mExpressionValue instanceof AddressVar) {
            Value value = mExpressionValue;
            AddressVar srcAddress = (AddressVar) value;

            LocalVar loadDestVar;
            if (value.getType() instanceof ArrayType) {
                ArrayType arrayType = (ArrayType) value.getType();
                loadDestVar = mCurrentFunction.getTempVar(arrayType.getBase());
            } else {
                loadDestVar = mCurrentFunction.getTempVar(value.getType());
            }
            LoadInst loadInst = new LoadInst(loadDestVar, srcAddress);
            addEdge(mLastControlInstruction, loadInst);
            mLastControlInstruction = loadInst;
            mExpressionValue = loadDestVar;

        // Local Variable
        } else {
            Value source = mExpressionValue;
            LocalVar destVar = mCurrentFunction.getTempVar(source.getType());
            CopyInst copyInst = new CopyInst(destVar, source);
            addEdge(mLastControlInstruction, copyInst);
            mLastControlInstruction = copyInst;
            mExpressionValue = destVar;
        }
    }

    private void visit(Expression expression) {
        expression.accept(this);
    }

    @Override
    public void visit(ArrayAccess access) {
        Symbol symbol = access.getBase().getSymbol();
        Variable destVar;
        
        if (mCurrentGlobalSymMap.containsKey(symbol)) {
            if (symbol.getType() instanceof ArrayType && dereferenceArray) {
                ArrayType arrayType = (ArrayType) symbol.getType();
                destVar = mCurrentFunction.getTempAddressVar(arrayType.getBase());
                dereferenceArray = false;
            } else {
                destVar = mCurrentFunction.getTempAddressVar(symbol.getType());
            }
        } else {
            destVar = mCurrentFunction.getTempVar(symbol.getType());
        }           
        // Visit offset
        visit(access.getOffset());
        Value offsetVal = mExpressionValue;
        LocalVar offset = (LocalVar) offsetVal;
        // Visit base
        visit(access.getBase());
        Value baseVal = mExpressionValue;
        AddressVar base = (AddressVar) baseVal;
        // AddressAt
        AddressAt addressAt = new AddressAt(destVar, base, offset);
        addEdge(mLastControlInstruction, addressAt);
        mLastControlInstruction = addressAt;
        mExpressionValue = destVar;
    }

    @Override
    public void visit(LiteralBool literalBool) {
        Value boolValue = BooleanConstant.get(mCurrentProgram, literalBool.getValue());
        LocalVar destVar = mCurrentFunction.getTempVar(new BoolType());
        CopyInst copyInst = new CopyInst(destVar, boolValue);
        addEdge(mLastControlInstruction, copyInst);
        mLastControlInstruction = copyInst;
        mExpressionValue = destVar;
    }

    @Override
    public void visit(LiteralInt literalInt) {
        Value intValue = IntegerConstant.get(mCurrentProgram, literalInt.getValue());
        LocalVar destVar = mCurrentFunction.getTempVar(new IntType());
        CopyInst copyInst = new CopyInst(destVar, intValue);
        addEdge(mLastControlInstruction, copyInst);
        mLastControlInstruction = copyInst;
        mExpressionValue = destVar;
    }

    @Override
    public void visit(Return ret) {
        visit(ret.getValue());
        Value value = mExpressionValue;
        LocalVar retValue = (LocalVar) value;
        ReturnInst returnInst = new ReturnInst(retValue);
        addEdge(mLastControlInstruction, returnInst);
        mLastControlInstruction = returnInst;
        mExpressionValue = retValue;
    }

    /**
     * Control Structures
     * */
    @Override
    public void visit(IfElseBranch ifElseBranch) {
        // Visit condition
        visit(ifElseBranch.getCondition());
        LocalVar predicate = (LocalVar) mExpressionValue;
        JumpInst jumpInst = new JumpInst(predicate);
        addEdge(mLastControlInstruction, jumpInst);
        mLastControlInstruction = jumpInst;
        
        // Visit else block if exists - first jump child
        NopInst nopInst = new NopInst();
        if (ifElseBranch.getElseBlock().getChildren().size() != 0) {
            visit(ifElseBranch.getElseBlock());
            addEdge(mLastControlInstruction, nopInst);
        } else {
            addEdge(jumpInst, nopInst);
        }
        // Visit then block - second jump child
        mLastControlInstruction = jumpInst;
        visit(ifElseBranch.getThenBlock());
        addEdge(mLastControlInstruction, nopInst);
        
        mLastControlInstruction = nopInst;
    }

    @Override
    public void visit(WhileLoop whileLoop) {
        // Visit condition
        Instruction beforeConditional = mLastControlInstruction;
        visit(whileLoop.getCondition());
        LocalVar predicate = (LocalVar) mExpressionValue;
        JumpInst jumpInst = new JumpInst(predicate);
        addEdge(mLastControlInstruction, jumpInst);

        // Add jump instruction to NOP - first jump child
        NopInst nopInst1 = new NopInst();
        addEdge(jumpInst, nopInst1);

        // Visit body - second jump child
        NopInst nopInst2 = new NopInst();
        addEdge(jumpInst, nopInst2);
        mLastControlInstruction = nopInst2;
        visit(whileLoop.getBody());
        addEdge(mLastControlInstruction, beforeConditional.getNext(0));

        mLastControlInstruction = nopInst1;
    }
}
