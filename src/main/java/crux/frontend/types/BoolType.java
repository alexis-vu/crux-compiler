package crux.frontend.types;

public final class BoolType extends Type {
    @Override
    public boolean equivalent(Type that) {
        return that.getClass() == BoolType.class;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Type and(Type that) {
        if(!equivalent(that)) 
            return super.and(that);
        else
            return this;
    }

    @Override
    public Type or(Type that) {
        if(!equivalent(that)) 
            return super.or(that); 
        return this;
    }

    @Override
    public Type not() {
        return this;
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
