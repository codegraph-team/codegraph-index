package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.JarNode;
import com.dnfeitosa.codegraph.db.graph.repositories.JarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.dnfeitosa.coollections.Coollections.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-db.xml", "classpath:/codegraph-db-test.xml" })
@ActiveProfiles("test")
@Transactional
public class JarNodeRepositoryTest {

    @Autowired
    private JarRepository repository;

    @Test
    public void shouldSaveASingleJarInstanceBasedOnTheCombinationOfFields() {
        repository.save(commonsLogging());
        repository.save(commonsLogging());

        Iterable<JarNode> all = repository.findAll();

        assertThat($(all).toList().size(), is(1));
    }

    private JarNode commonsLogging() {
        JarNode commonsLogging = new JarNode();
        commonsLogging.setOrganization("apache");
        commonsLogging.setName("commons-logging");
        commonsLogging.setVersion("1.0");
        commonsLogging.prepare();
        return commonsLogging;
    }
}