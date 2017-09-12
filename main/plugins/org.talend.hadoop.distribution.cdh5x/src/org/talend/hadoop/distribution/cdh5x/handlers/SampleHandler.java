package org.talend.hadoop.distribution.cdh5x.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
@SuppressWarnings("nls")
public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        Shell shell = null;
        shell = window.getShell();
        TestDialog td = new TestDialog(shell);
        td.open();
		return null;
	}
	
    public static void main(String args[]) throws Exception {
        resolve();
    }

    private static void resolve() throws Exception {
        String remoteUrl = "https://repository.cloudera.com/cloudera/cdh-releases-rcs";
        String groupId = "org.apache.avro";
        String artifactId = "avro-mapred";
        String version = "1.7.6-cdh5.10.1";
        String classifier = "hadoop2";
        String scope = "compile";

        RepositorySystem repoSystem = newRepositorySystem();

        RepositorySystemSession session = newSession(repoSystem);

        org.eclipse.aether.graph.Dependency dependency = new org.eclipse.aether.graph.Dependency(
                new DefaultArtifact(groupId, artifactId, classifier, null, version), scope);
        // RemoteRepository central = new RemoteRepository.Builder("central", "default",
        // "http://localhost:8081/nexus/content/repositories/central/").build();
        RemoteRepository central = new RemoteRepository.Builder("central", "default",
                remoteUrl).build();
        // org.eclipse.aether.graph.Dependency dependency = new org.eclipse.aether.graph.Dependency(
        // new DefaultArtifact("org.apache.maven:maven-profile:2.2.1"), "compile");
        // RemoteRepository central = new RemoteRepository.Builder("central", "default",
        // "http://repo1.maven.org/maven2/").build();

        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot(dependency);
        collectRequest.addRepository(central);
        DependencyNode node = repoSystem.collectDependencies(session, collectRequest).getRoot();

        // System.out.println("name:" + node.getArtifact().getFile().getName());

        List<Artifact> list = new ArrayList<>();
        getAllArtifact(node, list);
        
        for (Artifact art : list) {
            // System.out.println(art.toString());
            System.out.println(art.getArtifactId());
        }

        // DependencyRequest dependencyRequest = new DependencyRequest();
        // dependencyRequest.setRoot(node);
        //
        // repoSystem.resolveDependencies(session, dependencyRequest);
        //
        // PreorderNodeListGenerator nlg = new PreorderNodeListGenerator();
        // node.accept(nlg);
        // System.out.println(nlg.getClassPath());
    }

    private static void getAllArtifact(DependencyNode node, List<Artifact> list) {
        if (node == null) {
            return;
        }
        list.add(node.getArtifact());
        List<DependencyNode> children = node.getChildren();
        for (DependencyNode dn : children) {
            getAllArtifact(dn, list);
        }
    }

    private static RepositorySystem newRepositorySystem() {
        DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        locator.addService(TransporterFactory.class, FileTransporterFactory.class);
        locator.addService(TransporterFactory.class, HttpTransporterFactory.class);

        return locator.getService(RepositorySystem.class);
    }

    private static RepositorySystemSession newSession(RepositorySystem system) throws CoreException {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

        LocalRepository localRepo = new LocalRepository(MavenPlugin.getMaven().getLocalRepositoryPath());
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));

        return session;
    }
}
