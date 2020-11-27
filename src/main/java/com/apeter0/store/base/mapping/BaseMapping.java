package com.apeter0.store.base.mapping;

public abstract class BaseMapping <From, To> {
    public abstract To convert(From from);
}
