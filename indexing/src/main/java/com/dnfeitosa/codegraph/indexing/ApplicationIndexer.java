package com.dnfeitosa.codegraph.indexing;

import java.util.List;

import com.dnfeitosa.codegraph.concurrency.Executor;
import com.dnfeitosa.codegraph.concurrency.ParallelProcessor;
import com.dnfeitosa.codegraph.loaders.ApplicationsLoader;
import com.dnfeitosa.codegraph.model.Application;
import com.dnfeitosa.codegraph.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationIndexer {

    private final ApplicationsLoader applicationsLoader;
    private final ApplicationService applicationRepository;

    @Autowired
    public ApplicationIndexer(ApplicationsLoader applicationsLoader, ApplicationService applicationRepository) {
        this.applicationsLoader = applicationsLoader;
        this.applicationRepository = applicationRepository;
    }

    public void index(String codebaseRoot) {
        List<Application> applications = applicationsLoader.loadFrom(codebaseRoot);

        ParallelProcessor processor = new ParallelProcessor(10, 10);
        processor.process(applications, new Executor<Application, Void>() {

			@Override
			public Void execute(Application application) {
				applicationRepository.save(application);
				return null;
			}
        });
    }
}