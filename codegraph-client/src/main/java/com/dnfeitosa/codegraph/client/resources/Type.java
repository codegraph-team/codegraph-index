package com.dnfeitosa.codegraph.client.resources;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Type {

    private String name;

    @SerializedName("package")
    private String packageName;
    private String type;
    private String usage;
    private List<Field> fields = new ArrayList<Field>();
    private List<Method> methods = new ArrayList<Method>();
    private Type superclass;
    private List<Type> interfaces = new ArrayList<Type>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public List<Field> getFields() {
        return fields;
    }

    public Type getSuperclass() {
        return superclass;
    }

    public void setSuperclass(Type superclass) {
        this.superclass = superclass;
    }

    public List<Type> getInterfaces() {
        return interfaces;
    }

    public void addInterface(Type interface_) {
        interfaces.add(interface_);
    }
}
