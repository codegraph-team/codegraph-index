package co.degraph.ant.converters;

import co.degraph.client.resources.Dependency;
import org.apache.ivy.core.module.id.ModuleRevisionId;

import java.util.HashSet;

public class ModuleRevisionConverter {

    public Dependency toDependency(ModuleRevisionId module) {
        Dependency artifact = new Dependency(module.getOrganisation(), module.getName(), module.getRevision(), new HashSet<String>());
//        artifact.setType("jar");
//        artifact.setExtension("jar");
        return artifact;
    }
}
