package co.degraph.client.resources;

import java.util.ArrayList;
import java.util.List;

public class Artifact {

    private String name;
    private String organization;
    private String version;
    private String extension;
    private String type;
    private List<Dependency> dependencies = new ArrayList<Dependency>();
    private List<Type> types = new ArrayList<Type>();

    public void addType(Type type) {
        types.add(type);
    }

    public void addDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
