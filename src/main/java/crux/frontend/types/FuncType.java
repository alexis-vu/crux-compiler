package crux.frontend.types;

public final class FuncType extends Type {
    private TypeList args;
    private Type ret;

    public FuncType(TypeList args, Type returnType) {
        this.args = args;
        this.ret = returnType;
    }

    public Type getRet() {
        return ret;
    }

    public TypeList getArgs() {
        return args;
    }

    @Override
    public boolean equivalent(Type that) {
        if (that == null) return false;
        if (!(that instanceof FuncType)) return false;
        else {
            FuncType func = (FuncType) that;
            return this.ret.equivalent(func.getRet()) && this.args.equivalent(func.getArgs());
        }
    }

    public Type call(Type args) {
        TypeList typeArgs = (TypeList) args;
        if (this.args == null && typeArgs.isEmpty())
            return this.ret;
        if (!(args instanceof TypeList) || !(this.args.equivalent(args)))
            return super.call(args);
        return this.ret;
    }

    @Override
    public String toString() {
        return "func(" + args + "):" + ret;
    }
}
