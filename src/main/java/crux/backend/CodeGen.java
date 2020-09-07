package crux.backend;

import crux.midend.ir.core.*;
import crux.midend.ir.core.insts.*;
import crux.midend.ir.core.insts.CompareInst.Predicate;
import crux.printing.IRValueFormatter;

import java.util.*;

public final class CodeGen extends InstVisitor {
    private final IRValueFormatter irFormat = new IRValueFormatter();

    private final Program p;
    private final CodePrinter out;
    private HashMap<Instruction, String> currLabelMap;
    HashMap<Value, Long> offsetMap = new HashMap<>();
    private Function currentFunction;
    private long stackSize = 0;

    public CodeGen(Program p) {
        this.p = p;
        // Do not change the file name that is outputted or it will
        // break the grader!
        
        out = new CodePrinter("a.s");
    }

    public void genCode() { 
        // This function should generate code for the entire program.
        for (Iterator<GlobalDecl> glob_it = p.getGlobals(); glob_it.hasNext();) {
            // .comm VarName, Bytes, 8
            GlobalDecl globalDecl = glob_it.next();
            long size = ((IntegerConstant)globalDecl.getNumElement()).getValue() * 8;
            out.printCode(".comm " + globalDecl.getAllocatedAddress().getName().substring(1) + ", " + size + ", 8");    //TODO: change before turning in
        }
        // int size = Iterators.size(iterator);
        for (Iterator<Function> func_it = p.getFunctions(); func_it.hasNext();) {
            Function f = func_it.next();
            genCode(f);
        }
        out.close();
    }

    private int labelcount = 1;

    private String getNewLabel() {
        return "L" + (labelcount++);
    }

    private void genCode(Function f) {
        stackSize = 0;
        currLabelMap = assignLabels(f);

        // Move parameters into registers
        List<LocalVar> args = f.getArguments();
        int argIndex = 1;
        for (LocalVar var : args) {
            if (argIndex == 1) {
                out.bufferCode("movq %rdi, -8(%rbp)");
            } else if (argIndex == 2) {
                out.bufferCode("movq %rsi, -16(%rbp)");
            } else if (argIndex == 3) {
                out.bufferCode("movq %rdx, -24(%rbp)");
            } else if (argIndex == 4) {
                out.bufferCode("movq %rcx, -32(%rbp)");
            } else if (argIndex == 5) {
                out.bufferCode("movq %r8, -40(%rbp)");
            } else if (argIndex == 6) {
                out.bufferCode("movq %r9, -48(%rbp)");
            } else {
                // If more than 6 parameters, push to stack
                out.bufferCode("push " + var.getName());
            }
            argIndex++;
        }
        outputCodeBody(f);

        // Get label for function
        out.printCode(".globl " + f.getName());    //TODO: change before turning in
        out.printCode(f.getName() + ":");          //TODO: change before turning in
        // If stack size is not a multiple of 16, add to stack
        if (stackSize % 2 != 0) { stackSize++; }
        out.printCode("enter $(8 * " + stackSize + "), $0");
        // Output function body and clear buffer
        out.outputBuffer();
    }

    private void outputCodeBody(Function f) {
        currentFunction = f;
        Stack<Instruction> toVisit = new Stack<>();
        HashSet<Instruction> discovered = new HashSet<>();
        toVisit.push(f.getStart());
        while (!toVisit.isEmpty()) {
            Instruction inst = toVisit.pop();
            // Generate label for this instruction if there is one
            if (currLabelMap.containsKey(inst)) {
                out.bufferLabel(currLabelMap.get(inst) + ':');
            }
            // Generate code for this instruction
            inst.accept(this);

            Instruction first = inst.getNext(0);
            Instruction second = inst.getNext(1);
            if (second != null && !discovered.contains(second)) {
                toVisit.push(second);
                discovered.add(second);
            }
            if (first != null && !discovered.contains(first)) {
                toVisit.push(first);
                discovered.add(first);
            } else if (first != null && (toVisit.isEmpty() || first != toVisit.peek())) {
                // Handle loop and nested behavior
                out.bufferCode("jmp " + currLabelMap.get(first));
            } else {
                out.bufferCode("leave");
                out.bufferCode("ret");
            }
        }
    }


    /** Assigns Labels to any Instruction that might be the target of a
     * conditional or unconditional jump. */

    private HashMap<Instruction, String> assignLabels(Function f) {
        HashMap<Instruction, String> labelMap = new HashMap<>();
        Stack<Instruction> tovisit = new Stack<>();
        HashSet<Instruction> discovered = new HashSet<>();
        tovisit.push(f.getStart());
        while (!tovisit.isEmpty()) {
            Instruction inst = tovisit.pop();

            for (int childIdx = 0; childIdx < inst.numNext(); childIdx++) {
                Instruction child = inst.getNext(childIdx);
                if (discovered.contains(child)) {
                    //Found the node for a second time...need a label for merge points
                    if (!labelMap.containsKey(child)) {
                        labelMap.put(child, getNewLabel());
                    }
                } else {
                    discovered.add(child);
                    tovisit.push(child);
                    //Need a label for jump targets also
                    if (childIdx == 1 && !labelMap.containsKey(child)) {
                        labelMap.put(child, getNewLabel());
                    }
                }
            }
        }
        return labelMap;
    }

    public void visit(AddressAt i) {
        if (i.getOffset() == null) {
            // Global variable
            out.bufferCode("movq " + i.getBase().getName().substring(1) + "@GOTPCREL(%rip), %r11");    //TODO: change before turning in
            if (offsetMap.get(i.getDst()) == null) {
                offsetMap.put(i.getDst(), ++stackSize);
            }
            out.bufferCode("movq %r11, " + (-8 * offsetMap.get(i.getDst())) + "(%rbp)");
        } else {
            // Array access
            out.bufferCode("movq " + (-8 * offsetMap.get(i.getOffset())) + "(%rbp), %r11");
            out.bufferCode("movq $8, %r10");
            out.bufferCode("imul %r10, %r11");
            out.bufferCode("movq " + i.getBase().getName().substring(1) + "@GOTPCREL(%rip), %r10");    //TODO: change before turning in
            out.bufferCode("addq %r10, %r11");
            if (offsetMap.get(i.getDst()) == null) {
                offsetMap.put(i.getDst(), ++stackSize);
            }
            out.bufferCode("movq %r11, " + (-8 * offsetMap.get(i.getDst())) + "(%rbp)");
        }
    }


    public void visit(BinaryOperator i) {
        out.bufferCode("movq " + (-8 * offsetMap.get(i.getLeftOperand())) + "(%rbp), %r10");
        switch (i.getOperator()) {
            case Add:
                out.bufferCode("addq " + (-8 * offsetMap.get(i.getRightOperand())) + "(%rbp), %r10");
                break;
            case Sub:
                out.bufferCode("subq " + (-8 * offsetMap.get(i.getRightOperand())) + "(%rbp), %r10");
                break;
            case Mul:
                out.bufferCode("mulq " + (-8 * offsetMap.get(i.getRightOperand())) + "(%rbp), %r10");
                break;
            case Div:
                out.bufferCode("divq " + (-8 * offsetMap.get(i.getRightOperand())) + "(%rbp), %r10");
                break;
        }
        if (offsetMap.get(i.getDst()) == null) {
            offsetMap.put(i.getDst(), ++stackSize);
        }
        out.bufferCode("movq %r10, " + (-8 * offsetMap.get(i.getDst())) + "(%rbp)");
    }

    public void visit(CompareInst i) {
        out.bufferCode("movq $0, %r10");
        out.bufferCode("movq $1, %rax");
        out.bufferCode("movq " + (-8 * offsetMap.get(i.getLeftOperand())) + "(%rbp), %r11");
        out.bufferCode("cmp " + (-8 * offsetMap.get(i.getRightOperand())) + "(%rbp), %r11");

        switch(i.getPredicate()) {
            case GE: 
                out.bufferCode("cmovge %rax, %r10");
                break;
            case GT:
                out.bufferCode("cmovg %rax, %r10");
                break;
            case LE:
                out.bufferCode("cmovle %rax, %r10");
                break;
            case LT:
                out.bufferCode("cmovl %rax, %r10");
                break;
            case EQ:
                out.bufferCode("cmove %rax, %r10");       
                break;
            case NE:
                out.bufferCode("cmovne %rax, %r10");
                break;
        }

        if (offsetMap.get(i.getDst()) == null) {
            offsetMap.put(i.getDst(), ++stackSize);
        }

        out.bufferCode("movq %r10, " + (-8 * offsetMap.get(i.getDst())) + "(%rbp)");
    }

    public void visit(CopyInst i) {
        // Boolean Constant source value
        if (i.getSrcValue() instanceof BooleanConstant) {
            if (((BooleanConstant) i.getSrcValue()).getValue()) {
                out.bufferCode("movq $1, %r10");
            } else {
                out.bufferCode("movq $0, %r10");
            }
        // Integer Constant source value
        } else if (i.getSrcValue() instanceof IntegerConstant) {
            out.bufferCode("movq $" + ((IntegerConstant) i.getSrcValue()).getValue() + ", %r10");
        }
        // Local variable
        else {
            if (offsetMap.get(i.getSrcValue()) == null) {
                offsetMap.put(i.getSrcValue(), ++stackSize);
            }

            out.bufferCode("movq " + (-8 * offsetMap.get(i.getSrcValue())) + "(%rbp), %r10");
        }

        if (offsetMap.get(i.getDstVar()) == null) {
            offsetMap.put(i.getDstVar(), ++stackSize);
        }
        out.bufferCode("movq %r10, " + (-8 * offsetMap.get(i.getDstVar())) + "(%rbp)");
    }

    public void visit(JumpInst i) {
        out.bufferCode("movq " + (-8 * offsetMap.get(i.getPredicate())) + "(%rbp), %r10");
        out.bufferCode("cmp $1, %r10");
        out.bufferCode("je " + currLabelMap.get(i.getNext(1)));
    }

    public void visit(LoadInst i) {
        out.bufferCode("movq " + (-8 * offsetMap.get(i.getSrcAddress())) + "(%rbp), %r10");
        out.bufferCode("movq 0(%r10), %r10");
        if (offsetMap.get(i.getDst()) == null) {
            offsetMap.put(i.getDst(), ++stackSize);
        }
        out.bufferCode("movq %r10, " + (-8 * offsetMap.get(i.getDst())) + "(%rbp)");
    }

    public void visit(NopInst i) {
    }

    public void visit(StoreInst i) { 
        out.bufferCode("movq " + (-8 * offsetMap.get(i.getSrcValue())) + "(%rbp), %r10");
        out.bufferCode("movq " + (-8 * offsetMap.get(i.getDestAddress())) + "(%rbp), %r11");
        out.bufferCode("movq %r10, 0(%r11)");
    }

    public void visit(ReturnInst i) { 
        out.bufferCode("movq " + (-8 * offsetMap.get(i.getReturnValue())) + "(%rbp), %rax");
        out.bufferCode("leave");
        out.bufferCode("ret");
    }

    public void visit(CallInst i) {
        // Move parameters into registers
        int size = i.getParams().size();
        for (int j = 0; j < size; ++j) {
            long offset = offsetMap.get(i.getParams().get(j));
            if (j == 0) {
                out.bufferCode("movq " + (-8 * offset) + "(%rbp), %rdi");
            } else if (j == 1) {
                out.bufferCode("movq " + (-8 * offset) + "(%rbp), %rsi");
            } else if (j == 2) {
                out.bufferCode("movq " + (-8 * offset) + "(%rbp), %rdx");
            } else if (j == 3) {
                out.bufferCode("movq " + (-8 * offset) + "(%rbp), %rcx");
            } else if (j ==4) {
                out.bufferCode("movq " + (-8 * offset) + "(%rbp), %r8");
            } else if (j == 5) {
                out.bufferCode("movq " + (-8 * offset) + "(%rbp), %r9");
            } else {
                // TODO: more than 6 args
                //out.bufferCode("push " + ((Variable) i.getParams().get(j)).getName());
            }
         }
        out.bufferCode("call " + i.getCallee().getName().substring(1));        //TODO: change before turning in
        // Move return value if exists
        if (i.getDst() != null) {
            if (offsetMap.get(i.getDst()) == null) {
                offsetMap.put(i.getDst(), ++stackSize);
            }
            out.bufferCode("movq %rax, " + (-8 * offsetMap.get(i.getDst())) + "(%rbp)");
        }
    }

    public void visit(UnaryNotInst i) {
        out.bufferCode("movq " + (-8 * offsetMap.get(i.getInner())) + "(%rbp), %r10");
        out.bufferCode("not %r10");
        if (offsetMap.get(i.getDst()) == null) {
            offsetMap.put(i.getDst(), ++stackSize);
        }
        out.bufferCode("movq %r10, " + (-8 * offsetMap.get(i.getDst())) + "(%rbp)");
    }
}
