package org.talend.repository.hadoopcluster.ui.conf;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.runtime.maven.MavenArtifact;
import org.talend.core.runtime.maven.MavenConstants;
import org.talend.designer.maven.utils.PomUtil;
import org.talend.repository.hadoopcluster.conf.HadoopConfsManager;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl;

/**
 *
 * created by ycbai on 2015年5月29日 Detailled comment
 *
 */
public class HadoopImportConfsWizard extends Wizard {

    private AbstractHadoopForm parentForm;

    private HadoopClusterConnectionItem connectionItem;

    private String contextGroup;

    private boolean creation;

    private HadoopImportConfsOptionPage optionPage;

    private String confJarName;

    public HadoopImportConfsWizard(AbstractHadoopForm parentForm, HadoopClusterConnectionItem connectionItem, String contextGroup,
            boolean creation) {
        super();
        this.parentForm = parentForm;
        this.connectionItem = connectionItem;
        this.contextGroup = contextGroup;
        this.creation = creation;
        setNeedsProgressMonitor(true);
        setForcePreviousAndNextButtons(true);
    }

    @Override
    public String getWindowTitle() {
        return Messages.getString("HadoopImportConfsWizard.title"); //$NON-NLS-1$
    }

    @Override
    public void addPages() {
        optionPage = new HadoopImportConfsOptionPage();
        addPage(optionPage);
    }

    @Override
    public boolean canFinish() {
        IImportConfsWizardPage currentPage = getCurrentConfPage();
        if (currentPage != null) {
            return currentPage.isPageComplete() && currentPage.isLastPage();
        }
        return super.canFinish();
    }

    @Override
    public boolean performFinish() {
        IImportConfsWizardPage currentPage = getCurrentConfPage();
        if (currentPage != null) {
            final IRetrieveConfsService confsService = currentPage.getConfsService();
            final boolean isCreateConnectionFromConfs = currentPage.isSupportCreateServiceConnection();
            try {
                if (confsService != null) {
                    currentPage.applyFilter();
                     
                    List<String> selectedServices = currentPage.getSelectedServices(); 
                    final String confsDir = confsService.exportConfs(selectedServices);
                    if (confsDir != null) {
                        this.getContainer().run(true, true, new IRunnableWithProgress() {

                            @Override
                            public void run(final IProgressMonitor monitor)
                                    throws InvocationTargetException, InterruptedException {
                                monitor.beginTask(Messages.getString("HadoopImportConfsWizard.doingImport"), //$NON-NLS-1$
                                        IProgressMonitor.UNKNOWN);
                                try {
                                    if (contextGroup == null) {
                                        confJarName = HadoopConfsUtils.getConfsJarDefaultName(connectionItem, false);
                                    } else {
                                        confJarName = HadoopConfsUtils.getConfsJarDefaultName(connectionItem, false,
                                                contextGroup);
                                    }
                                    HadoopConfsUtils.buildAndDeployConfsJar(connectionItem, contextGroup, confsDir, confJarName);
                                } catch (Exception e) {
                                    confJarName = null;
                                    throw new InvocationTargetException(e);
                                } finally {
                                    monitor.done();
                                }
                            }
                        });
                    }
                    HadoopConfsManager confsManager = HadoopConfsManager.getInstance();
                    confsManager.setHadoopClusterId(connectionItem.getProperty().getId());
                    confsManager.setConfsMap(getSelectedConfsMap(selectedServices, confsService.getConfsMap()));
                    confsManager.setCreateConnectionFromConfs(isCreateConnectionFromConfs);
                }
                if (creation) {
                    HadoopConfsUtils.setConnectionParameters(connectionItem, optionPage.getDistribution(),
                            optionPage.getVersion(), confsService);
                    if ("SPARK".equals(((HadoopClusterConnectionImpl) this.connectionItem.getConnection()).getDistribution()) && confJarName != null) {
                    	MavenArtifact artifact = new MavenArtifact();
                		artifact.setGroupId("org.talend.libraries");
                        artifact.setArtifactId(confJarName.split(".jar")[0]);
                        artifact.setVersion("6.0.0-SNAPSHOT");
                        artifact.setType(MavenConstants.TYPE_JAR);
                        HadoopClusterConnection connection = (HadoopClusterConnection) connectionItem.getConnection();
                        connection.getParameters().put(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CONF_SPECIFIC_JAR, PomUtil.getArtifactFullPath(artifact));
                    }
                    if (parentForm != null) {
                        parentForm.reload();
                    }
                }
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }
        return true;
    }

    private Map<String, Map<String, String>> getSelectedConfsMap(List<String> selectedServices,
            Map<String, Map<String, String>> confsMap) {
        Map<String, Map<String, String>> selectedConfsMap = new HashMap<>();
        if (selectedServices == null || confsMap == null) {
            return selectedConfsMap;
        }
        Iterator<Entry<String, Map<String, String>>> confsMapIter = confsMap.entrySet().iterator();
        while (confsMapIter.hasNext()) {
            Entry<String, Map<String, String>> confsMapEntry = confsMapIter.next();
            String serviceKey = confsMapEntry.getKey();
            if (selectedServices.contains(serviceKey)) {
                selectedConfsMap.put(serviceKey, confsMapEntry.getValue());
            }
        }
        return selectedConfsMap;
    }

    private IImportConfsWizardPage getCurrentConfPage() {
        IWizardContainer container = getContainer();
        IWizardPage currentPage = container.getCurrentPage();
        if (currentPage instanceof IImportConfsWizardPage) {
            return (IImportConfsWizardPage) currentPage;
        }
        return null;
    }

    public String getConfJarName() {
        return this.confJarName;
    }

}
