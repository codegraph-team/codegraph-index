package com.dnfeitosa.codegraph.client.resources;

public class Parameter {
    private Integer order;
    private Type type;

    public Parameter(Integer order, Type type) {
        this.order = order;
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public Type getType() {
        return type;
    }
}
