package co.degraph.client.resources;

import java.util.Set;

public class Dependency {

    private String organization;
    private String name;
    private String version;
    private Set<String> configurations;

    public Dependency(String organization, String name, String version, Set<String> configurations) {
        this.organization = organization;
        this.name = name;
        this.version = version;
        this.configurations = configurations;
    }

    public String getOrganization() {
        return organization;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Set<String> getConfigurations() {
        return configurations;
    }
}
