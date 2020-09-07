package crux.frontend.types;

import java.util.stream.Stream;

public final class AddressType extends Type {
    private final Type base;

    public AddressType(Type base) {
        this.base = base;
    }

    public Type getBaseType() { return base; }

    @Override
    public boolean equivalent(Type that) {
        if (that.getClass() != AddressType.class)
            return false;

        var aType = (AddressType) that;
        return base.equivalent(aType.base);
    }

    @Override
    public String toString() {
        return "Address(" + base + ")";
    }

    @Override
    public Type add(Type that) {
        return base.add(that);
    }

    @Override
    public Type sub(Type that) {
        return base.sub(that);
    }

    @Override
    public Type mul(Type that) {
        return base.mul(that);
    }

    @Override 
    public Type div(Type that) {
        return base.div(that);
    }

    @Override
    public Type and(Type that) {
        return base.and(that);
    }

    @Override
    public Type or(Type that) {
        return base.or(that);
    }

    @Override
    public Type not() {
        return base.not();
    }

    @Override
    public Type compare(Type that) {
        return base.compare(that);
    }

    @Override
    public Type deref() {
        if (base.equivalent(new VoidType()))
            return super.deref();
        else
            return base;
    }

    @Override
    public Type index(Type that) {
        if (!that.equivalent(new IntType()) || !(base instanceof ArrayType))
            return super.index(that);
        else
            return new AddressType(base.index(that));
    }

    @Override
    public Type assign(Type source) {
        if (!source.equivalent(base))
            return super.assign(source);
        else
            return base.assign(source);
    }
}

