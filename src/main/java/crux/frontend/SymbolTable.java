package crux.frontend;

import crux.frontend.ast.Position;
import crux.frontend.types.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class SymbolTable {
    private final PrintStream err;
    private final List<Map<String, Symbol>> symbolScopes = new ArrayList<>();

    private boolean encounteredError = false;

    SymbolTable(PrintStream err) {
        this.err = err;
        this.enter();
        Map<String, Symbol> globalScope = symbolScopes.get(0);

        // Add predefined functions in global scope
        globalScope.put("readInt", new Symbol("readInt", new FuncType(null, new IntType())));

        List<Type> printBoolArgs = new ArrayList<>();
        printBoolArgs.add(new BoolType());
        globalScope.put("printBool", new Symbol("printBool", new FuncType(new TypeList(printBoolArgs), new VoidType())));

        List<Type> printIntArgs = new ArrayList<>();
        printIntArgs.add(new IntType());
        globalScope.put("printInt", new Symbol("printInt", new FuncType(new TypeList(printIntArgs), new VoidType())));

        globalScope.put("println", new Symbol("println", new FuncType(null, new VoidType())));
    }

    boolean hasEncounteredError() {
        return encounteredError;
    }

    void enter() {
        Map<String, Symbol> scope = new HashMap<>();
        symbolScopes.add(scope); 
    }

    void exit() {
        symbolScopes.remove(symbolScopes.size() - 1);
    }

    Symbol add(Position pos, String name) {
        Symbol symbol;
        Map<String, Symbol> symbolMap = symbolScopes.get(symbolScopes.size()-1);

        if (symbolMap.containsKey(name)) {
            err.printf("DeclareSymbolError%s[%s already exists.]%n", pos, name);
            encounteredError = true;
            symbol = new Symbol(name, "DeclareSymbolError");
        } else {
            symbol = new Symbol(name);
            symbolMap.put(name, symbol);
        }

        return symbol;
    }

    Symbol add(Position pos, String name, Type type) {
        Symbol symbol;
        Map<String, Symbol> symbolMap = symbolScopes.get(symbolScopes.size() - 1);
      
        if (symbolMap.containsKey(name)) {
            err.printf("DeclareSymbolError%s[%s already exists.]%n", pos, name);
            encounteredError = true;
            symbol = new Symbol(name, "DeclareSymbolError");
        } else {
            symbol = new Symbol(name, type);
            symbolMap.put(name, symbol);
        }

        return symbol;
    }

    Symbol lookup(Position pos, String name) {
        var symbol = find(name);
        if (symbol == null) {
            err.printf("ResolveSymbolError%s[Could not find %s.]%n", pos, name);
            encounteredError = true;
            return new Symbol(name, "ResolveSymbolError");
        } else {
            return symbol;
        }
    }

    private Symbol find(String name) {
        // Iterate through map backwards
        // Start at innermost scope and end at global scope
        for (int i = symbolScopes.size()-1; i >=0; --i) {
            Map<String, Symbol> scope = symbolScopes.get(i);
            if (scope.containsKey(name)) { return scope.get(name); }
        }
        return null;
    }
}
