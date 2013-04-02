package org.talend.repository.oozie.ui;

import org.apache.oozie.client.OozieClientException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.core.repository.ConnectionStatus;
import org.talend.designer.hdfsbrowse.manager.HadoopOperationManager;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.model.oozie.OozieConnection;
import org.talend.repository.oozie.i18n.Messages;
import org.talend.repository.oozie.util.OozieModelUtil;

public abstract class AbstractOozieForm extends AbstractHadoopForm<OozieConnection> {

    protected boolean oozieSettingIsValide = false;

    protected AbstractOozieForm(Composite parent, int style) {
        super(parent, style);
    }

    protected AbstractOozieForm(Composite parent, int style, String[] existingNames) {
        super(parent, style, existingNames);
    }

    protected ConnectionStatus checkConnection() {
        return checkConnection(true);
    }

    private ConnectionStatus checkConnection(boolean displayDialog) {
        HDFSConnectionBean connectionBean = getConnectionBean();
        ConnectionStatus connectionStatus = HadoopOperationManager.getInstance().testConnection(connectionBean);
        oozieSettingIsValide = connectionStatus.getResult();

        if (oozieSettingIsValide) {
            // try {
            // checkOozieConnection();
            // } catch (OozieClientException e) {
            //                String mainMsg = Messages.getString("AbstractOozieForm.endPointInvalid"); //$NON-NLS-1$
            // connectionStatus.setMessageException(e.toString());
            // new ErrorDialogWidthDetailArea(getShell(), Activator.PLUGIN_ID, mainMsg, e.toString());
            // return connectionStatus;
            // }
            if (!isReadOnly()) {
                updateStatus(IStatus.OK, null);
            }
            if (displayDialog) {
                MessageDialog
                        .openInformation(
                                getShell(),
                                Messages.getString("AbstractOozieForm.checkConnection"), Messages.getString("AbstractOozieForm.connectionSuccessful")); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } else {
            String connectException = connectionStatus.getMessageException();
            String mainMsg = Messages.getString("AbstractOozieForm.connectionFailure"); //$NON-NLS-1$
            if (!isReadOnly()) {
                updateStatus(IStatus.WARNING, mainMsg);
            }
            if (displayDialog) {
                new ErrorDialogWidthDetailArea(getShell(), "org.talend.repository.oozie", //$NON-NLS-1$
                        mainMsg, connectException);
            }
        }

        return connectionStatus;
    }

    private void checkOozieConnection() throws OozieClientException {
        // OozieClient oozieClient = new OozieClient(getConnection().getOozieEndPoind());
        // oozieClient.validateWSVersion();
    }

    protected HDFSConnectionBean getConnectionBean() {
        return OozieModelUtil.convert2HDFSConnectionBean(getConnection());
    }

}
