package co.degraph.client.resources;

import java.util.ArrayList;
import java.util.List;

public class Method {

    private String name;
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private List<Type> returnTypes = new ArrayList<Type>();

    public Method(String name) {
        this.name = name;
    }

    public void addParameter(Integer order, Type type) {
        parameters.add(new Parameter(order, type));
    }

    public void addReturnType(Type type) {
        returnTypes.add(type);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<Type> getReturnTypes() {
        return returnTypes;
    }

    public String getName() {
        return name;
    }
}
