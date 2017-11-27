package com.dnfeitosa.codegraph.ant.tasks;

import com.dnfeitosa.codegraph.ant.converters.ModuleRevisionConverter;
import com.dnfeitosa.codegraph.ant.converters.ProjectConverter;
import com.dnfeitosa.codegraph.client.CodegraphClient;
import com.dnfeitosa.codegraph.client.resources.Artifact;
import org.apache.ivy.Ivy;
import org.apache.ivy.ant.IvyTask;
import org.apache.ivy.core.cache.ResolutionCacheManager;
import org.apache.ivy.core.module.id.ModuleId;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.report.XmlReportParser;
import org.apache.tools.ant.BuildException;

import java.io.File;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class CodegraphTask extends IvyTask {

    private String serverUrl;

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public void doExecute() throws BuildException {
        CodegraphClient client = new CodegraphClient(serverUrl);

        Artifact projectArtifact = new ProjectConverter().toArtifact(getProject());

        Ivy ivy = getIvyInstance();
        IvySettings settings = ivy.getSettings();
        String organisation = getProperty(settings, "ivy.organisation");
        String module = getProperty(settings, "ivy.module");

        String resolveId = ResolveOptions.getDefaultResolveId(new ModuleId(organisation, module));
        ResolutionCacheManager cache = ivy.getResolutionCacheManager();

        String[] confs = splitConfs(getProperty(settings, "ivy.resolved.configurations"));

        ModuleRevisionConverter converter = new ModuleRevisionConverter();
        Set<ModuleRevisionId> dependencies = getDependencies(resolveId, cache, confs);
        for (ModuleRevisionId dependency : dependencies) {
            projectArtifact.addDependency(converter.toDependency(dependency));
        }

        client.addArtifact(projectArtifact, Collections.<Artifact>emptySet());
    }

    private Set<ModuleRevisionId> getDependencies(String resolveId, ResolutionCacheManager cache, String[] confs) {
        Set<ModuleRevisionId> dependencies = new HashSet<ModuleRevisionId>();
        for (String configuration : confs) {
            File report = cache.getConfigurationResolveReportInCache(resolveId, configuration);
            List<ModuleRevisionId> c = asList(parseReport(report));
            dependencies.addAll(c);
        }
        return dependencies;
    }

    private ModuleRevisionId[] parseReport(File report) {
        try {
            XmlReportParser reportParser = new XmlReportParser();
            reportParser.parse(report);
            return reportParser.getDependencyRevisionIds();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ModuleRevisionId[0];
    }
}
