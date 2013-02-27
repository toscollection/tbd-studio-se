package org.talend.repository.oozie.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.util.EHadoopVersion4Drivers;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.oozie.i18n.Messages;

public class OozieForm extends AbstractOozieForm {

    private static final String DEFAULT_END_POINT = "http://localhost:11000/oozie"; //$NON-NLS-1$

    private UtilsButton checkConnectionBtn;

    private LabelledText userNameText;

    private LabelledText endPonitText;

    private LabelledCombo versionCombo;

    private List<String> versions;

    protected OozieForm(Composite parent, ConnectionItem connectionItem, String[] existingNames) {
        super(parent, SWT.NONE, existingNames);
        this.connectionItem = connectionItem;
        setConnectionItem(connectionItem);
        setupForm();
        GridLayout layout = (GridLayout) getLayout();
        layout.marginHeight = 0;
        setLayout(layout);
    }

    @Override
    protected void addFields() {
        // Wait for oozie version complete
        // addVersionFields();
        addConnectionFields();
        addCheckFields();
    }

    private void addVersionFields() {
        Group connectionGroup = Form.createGroup(this, 1, Messages.getString("OozieForm.versionSetting")); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite connectionPartComposite = new Composite(connectionGroup, SWT.NULL);
        connectionPartComposite.setLayout(new GridLayout(2, false));
        connectionPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // TODO need add versions
        if (versions == null) {
            versions = new ArrayList<String>();
        }

        versionCombo = new LabelledCombo(connectionPartComposite,
                Messages.getString("OozieForm.oozieVersion"), Messages.getString("OozieForm.versionTip"), versions); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private void addConnectionFields() {
        Group connectionGroup = Form.createGroup(this, 1, Messages.getString("OozieForm.connectionSetting")); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite connectionPartComposite = new Composite(connectionGroup, SWT.NULL);
        connectionPartComposite.setLayout(new GridLayout(2, false));
        connectionPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        endPonitText = new LabelledText(connectionPartComposite, Messages.getString("OozieForm.endPoint"), 1); //$NON-NLS-1$
        userNameText = new LabelledText(connectionPartComposite, Messages.getString("OozieForm.userName"), 1); //$NON-NLS-1$
    }

    private void addCheckFields() {
        Composite checkGroup = new Composite(this, SWT.NONE);
        GridLayout checkGridLayout = new GridLayout(1, false);
        checkGroup.setLayout(checkGridLayout);
        GridData checkGridData = new GridData(GridData.FILL_HORIZONTAL);
        checkGridData.minimumHeight = 5;
        checkGroup.setLayoutData(checkGridData);
        Composite checkButtonComposite = Form.startNewGridLayout(checkGroup, 1, false, SWT.CENTER, SWT.BOTTOM);
        GridLayout checkButtonLayout = (GridLayout) checkButtonComposite.getLayout();
        checkButtonLayout.marginHeight = 0;
        checkButtonLayout.marginTop = 0;
        checkButtonLayout.marginBottom = 0;
        checkButtonLayout.marginLeft = 0;
        checkButtonLayout.marginRight = 0;
        checkButtonLayout.marginWidth = 0;
        checkConnectionBtn = new UtilsButton(checkButtonComposite,
                Messages.getString("OozieForm.check"), WIDTH_BUTTON_PIXEL, HEIGHT_BUTTON_PIXEL); //$NON-NLS-1$
        checkConnectionBtn.setEnabled(false);
    }

    @Override
    protected void addFieldsListeners() {
        userNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setUserName(userNameText.getText());
                checkFieldsValue();
            }
        });
        endPonitText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                getConnection().setOozieEndPoind(endPonitText.getText());
                checkFieldsValue();
            }
        });
        // versionCombo.addModifyListener(new ModifyListener() {
        //
        // @Override
        // public void modifyText(ModifyEvent e) {
        // getConnection().setOozieVersion(versionCombo.getText());
        // }
        // });
    }

    @Override
    protected void addUtilsButtonListeners() {
        checkConnectionBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkConnection();
            }

        });
    }

    @Override
    protected void initialize() {
        userNameText.setText(StringUtils.trimToEmpty(getConnection().getUserName()));
        // TODO version for oozie
        //        versionCombo.setText(""); //$NON-NLS-1$

        String endPointVal = getConnection().getOozieEndPoind();
        if (!StringUtils.isEmpty(endPointVal)) {
            endPonitText.setText(endPointVal);
        } else {
            // Need to redo
            HadoopClusterConnection clusterConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(getConnection());
            String jobTrackerURI = clusterConnection.getJobTrackerURI();
            endPointVal = "http://" + StringUtils.substringBefore(jobTrackerURI, ":") + ":11000/oozie"; //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            endPonitText.setText(endPointVal);
            getConnection().setOozieEndPoind(endPointVal);
        }

        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    private void updateConnectionPart() {
        HadoopClusterConnection clusterConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(getConnection());
        if (clusterConnection != null) {
            boolean enableKerberos = clusterConnection.isEnableKerberos();
            userNameText.setEnabled(!enableKerberos);
        }
    }

    @Override
    public boolean checkFieldsValue() {
        checkConnectionBtn.setEnabled(false);

        HadoopClusterConnection clusterConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(getConnection());
        if (clusterConnection != null) {
            String dfVersion = clusterConnection.getDfVersion();
            EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(dfVersion);
            boolean supportGroup = isSupportGroup(version4Drivers);
            if (supportGroup) {
                if (!validText(userNameText.getText())) {
                    updateStatus(IStatus.ERROR, Messages.getString("OozieForm.nameInvalid")); //$NON-NLS-1$
                    return false;
                }
            }
        }
        if (!validText(endPonitText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("OozieForm.endPointInvalid")); //$NON-NLS-1$
            return false;
        }
        // TODO check version if is specified
        checkConnectionBtn.setEnabled(true);
        updateStatus(IStatus.OK, null);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        updateConnectionPart();
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
        if (visible) {
            updateStatus(getStatusLevel(), getStatus());
        }
    }

}
