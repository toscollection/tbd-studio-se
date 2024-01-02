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
import org.talend.repository.hadoopcluster.conf.HadoopConfsManager;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * 
 * created by hwang on 2015年5月29日 Detailled comment
 *
 */
public class ClouderNavigatorWizard extends Wizard {

    private AbstractHadoopForm parentForm;

    private HadoopClusterConnectionItem connectionItem;

    private boolean creation;

    private ClouderaNavigatorOptionPage optionPage;

    public ClouderNavigatorWizard(AbstractHadoopForm parentForm, HadoopClusterConnectionItem connectionItem, boolean creation) {
        super();
        this.parentForm = parentForm;
        this.connectionItem = connectionItem;
        this.creation = creation;
        setNeedsProgressMonitor(true);
        setForcePreviousAndNextButtons(true);
    }

    @Override
    public String getWindowTitle() {
        return Messages.getString("ClouderNavigatorWizard.windowTitle"); //$NON-NLS-1$
    }

    @Override
    public void addPages() {
        optionPage = new ClouderaNavigatorOptionPage(connectionItem,creation);
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
        optionPage.finish();
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

}
