package crux.frontend.types;

public final class ArrayType extends Type {
    private final Type base;
    private final long extent;

    public ArrayType(long extent, Type base) {
        this.extent = extent;
        this.base = base;
    }

    public Type getBase() {
        return base;
    }

    public long getExtent() { return extent; }

    @Override
    public String toString() {
        return String.format("array[%d,%s]", extent, base);
    }

    @Override
    public boolean equivalent(Type that) {
        if(that == null) return false;
        if (!(that instanceof ArrayType)) return false;
        if(that.getClass() == IntType.class) {
            ArrayType arrayType = (ArrayType) that;
            return this.extent == arrayType.getExtent() && this.base.equivalent(arrayType.getBase());
        } else
            return false;
    }

    @Override
    public Type index(Type that) {  
        if(!that.equivalent(new IntType()))
            return super.index(that);
        else
            return this.base;
    }

    @Override
    public Type assign(Type source) {
        return this.base.assign(source);
    }
}
