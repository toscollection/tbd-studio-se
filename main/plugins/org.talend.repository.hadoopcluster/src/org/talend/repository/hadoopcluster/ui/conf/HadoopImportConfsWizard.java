package org.talend.repository.hadoopcluster.ui.conf;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * 
 * created by ycbai on 2015年5月29日 Detailled comment
 *
 */
public class HadoopImportConfsWizard extends Wizard {

    private AbstractHadoopForm parentForm;

    private HadoopClusterConnectionItem connectionItem;

    private boolean creation;

    private HadoopImportConfsOptionPage optionPage;

    public HadoopImportConfsWizard(AbstractHadoopForm parentForm, HadoopClusterConnectionItem connectionItem, boolean creation) {
        super();
        this.parentForm = parentForm;
        this.connectionItem = connectionItem;
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
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(EHadoopClusterImage.HADOOPCLUSTER_WIZ));
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
            if (confsService != null) {
                try {
                    final String confsDir = confsService.exportConfs(currentPage.getSelectedServices());
                    if (confsDir != null) {

                        this.getContainer().run(true, true, new IRunnableWithProgress() {

                            @Override
                            public void run(final IProgressMonitor monitor) throws InvocationTargetException,
                                    InterruptedException {
                                monitor.beginTask(
                                        Messages.getString("HadoopImportConfsWizard.doingImport"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                                try {
                                    // HadoopConfsUtils.buildAndDeployConfsJar(confsDir,
                                    // HadoopConfsUtils.getConfsJarDefaultName(connectionItem));
                                    if (creation) {
                                        getShell().getDisplay().asyncExec(new Runnable() {

                                            @Override
                                            public void run() {
                                                try {
                                                    HadoopConfsUtils.setConnectionParameters(connectionItem,
                                                            optionPage.getDistribution(), optionPage.getVersion(), confsService);
                                                    if (parentForm != null) {
                                                        parentForm.reload();
                                                    }
                                                } catch (Exception e) {
                                                    ExceptionHandler.process(e);
                                                }
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    throw new InvocationTargetException(e);
                                } finally {
                                    monitor.done();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
        return true;
    }

    private IImportConfsWizardPage getCurrentConfPage() {
        IWizardContainer container = getContainer();
        IWizardPage currentPage = container.getCurrentPage();
        if (currentPage instanceof IImportConfsWizardPage) {
            return (IImportConfsWizardPage) currentPage;
        }
        return null;
    }

}
