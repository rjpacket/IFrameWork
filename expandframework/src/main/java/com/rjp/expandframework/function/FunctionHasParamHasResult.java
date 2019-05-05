package com.rjp.expandframework.function;

public abstract class FunctionHasParamHasResult<T, P> extends Function {

    public FunctionHasParamHasResult(String functionName) {
        super(functionName);
    }

    public abstract T function(P p);
}
