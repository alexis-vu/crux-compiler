package crux.frontend.types;

public final class IntType extends Type {
    @Override
    public boolean equivalent(Type that) { return that.getClass() == IntType.class; }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Type add(Type that) {
        if(!equivalent(that)) 
            return super.add(that);
        else
            return this;
    }

    @Override
    public Type sub(Type that) {
        if(!equivalent(that))
            return super.sub(that);
        else
            return this;
    }

    @Override
    public Type mul(Type that) {
        if(!equivalent(that))
            return super.mul(that);
        else
            return this;
    }

    @Override
    public Type div(Type that) {
        if(!equivalent(that))
            return super.div(that);
        else
            return this;
    }

    @Override
    public Type compare(Type that) {
        if(!equivalent(that))
            return super.compare(that);
        else
            return new BoolType();
    }

    @Override
    public Type assign(Type source) {
        if (!equivalent(source))
            return super.assign(source);
        else
            return this;
    }

    @Override
    public Type deref() {
        return this;
    }
}
