package org.talend.repository.oozie.ui;

import java.util.HashMap;
import java.util.Iterator;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.advanced.dataeditor.HadoopPropertiesTableView;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterValidator;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.oozie.i18n.Messages;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

public class OozieForm extends AbstractOozieForm {

    private UtilsButton checkConnectionBtn;

    private LabelledText userNameText;

    private LabelledText endPonitText;

    private Button kerbBtn;

    private List<HashMap<String, Object>> properties;

    private HadoopPropertiesTableView propertiesTableView;

    protected OozieForm(Composite parent, ConnectionItem connectionItem, String[] existingNames) {
        super(parent, SWT.NONE, existingNames, connectionItem);
        GridLayout layout = (GridLayout) getLayout();
        layout.marginHeight = 0;
        setLayout(layout);
    }

    @Override
    protected void addFields() {
        addOozieAuthenticationFields();
        addConnectionFields();
        addHadoopPropertiesFields();
        addCheckFields();
    }

    // private void addHadoopPropertiesFields() {
    // // table view
    // Composite compositeTable = Form.startNewDimensionnedGridLayout(this, 1, this.getBorderWidth(), 150);
    // GridData gridData = new GridData(GridData.FILL_BOTH);
    // gridData.horizontalSpan = 4;
    // compositeTable.setLayoutData(gridData);
    // CommandStackForComposite commandStack = new CommandStackForComposite(compositeTable);
    // properties = new ArrayList<HashMap<String, Object>>();
    // initHadoopProperties();
    // HadoopPropertiesFieldModel model = new HadoopPropertiesFieldModel(properties, "Hadoop Properties");
    // propertiesTableView = new HadoopPropertiesTableView(model, compositeTable);
    // propertiesTableView.getExtendedTableViewer().setCommandStack(commandStack);
    // final Composite fieldTableEditorComposite = propertiesTableView.getMainComposite();
    // fieldTableEditorComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
    // fieldTableEditorComposite.setBackground(null);
    // }

    // private void updateModel() {
    // setProperties(propertiesTableView.getExtendedTableModel().getBeansList());
    // }

    private void initHadoopProperties() {
        String hadoopProperties = getConnection().getHadoopProperties();
        try {
            if (StringUtils.isNotEmpty(hadoopProperties)) {
                JSONArray jsonArr = new JSONArray(hadoopProperties);
                for (int i = 0; i < jsonArr.length(); i++) {
                    HashMap<String, Object> map = new HashMap();
                    JSONObject object = jsonArr.getJSONObject(i);
                    Iterator it = object.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        map.put(key, object.get(key));
                    }
                    properties.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addOozieAuthenticationFields() {
        Group authGroup = Form.createGroup(this, 1, "Authentication Settings");
        authGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        kerbBtn = new Button(authGroup, SWT.CHECK);
        kerbBtn.setText("Enable oozie kerberos security");
        kerbBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        userNameText = new LabelledText(authGroup, Messages.getString("OozieForm.userName"), 1); //$NON-NLS-1$
    }

    private void addConnectionFields() {
        Group connectionGroup = Form.createGroup(this, 1, Messages.getString("OozieForm.connectionSetting")); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite connectionPartComposite = new Composite(connectionGroup, SWT.NULL);
        connectionPartComposite.setLayout(new GridLayout(2, false));
        connectionPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        endPonitText = new LabelledText(connectionPartComposite, Messages.getString("OozieForm.endPoint"), 1); //$NON-NLS-1$
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
        kerbBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().setEnableKerberos(kerbBtn.getSelection());
            }
        });
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
        // if (propertiesTableView != null) {
        // propertiesTableView.getExtendedTableModel().addAfterOperationListListener(new IListenableListListener() {
        //
        // @Override
        // public void handleEvent(ListenableListEvent event) {
        // // checkFieldsValue();
        // updateModel();
        // }
        // });
        // propertiesTableView.getExtendedTableModel().addModifiedBeanListener(
        // new IModifiedBeanListener<HashMap<String, Object>>() {
        //
        // @Override
        // public void handleEvent(ModifiedBeanEvent<HashMap<String, Object>> event) {
        // // checkFieldsValue();
        // updateModel();
        // }
        // });
        // }
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
        kerbBtn.setSelection(getConnection().isEnableKerberos());
        userNameText.setText(StringUtils.trimToEmpty(getConnection().getUserName()));

        String endPointVal = getConnection().getOozieEndPoind();
        if (!StringUtils.isEmpty(endPointVal)) {
            endPonitText.setText(endPointVal);
        } else {
            String hostName = HadoopParameterUtil.getHostNameFromNameNodeURI(clusterConnection.getNameNodeURI());
            endPointVal = "http://" + hostName + ":11000/oozie"; //$NON-NLS-1$//$NON-NLS-2$ 
            endPonitText.setText(endPointVal);
            getConnection().setOozieEndPoind(endPointVal);
        }

        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    private void updateAuthenticationPart() {
        kerbBtn.setEnabled(enableKerberos);
    }

    private void updateConnectionPart() {
        userNameText.setEnabled(!enableKerberos);
    }

    @Override
    public boolean checkFieldsValue() {
        checkConnectionBtn.setEnabled(false);
        if (enableGroup) {
            if (!validText(userNameText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("OozieForm.nameInvalid")); //$NON-NLS-1$
                return false;
            }
        }
        if (validText(userNameText.getText()) && !HadoopParameterValidator.isValidUserName(userNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("OozieForm.nameInvalid.invalid")); //$NON-NLS-1$
            return false;
        }
        if (!validText(endPonitText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("OozieForm.endPointInvalid")); //$NON-NLS-1$
            return false;
        }
        if (!HadoopParameterValidator.isValidOozieEndPoint(endPonitText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("OozieForm.endPointInvalid.invalid")); //$NON-NLS-1$
            return false;
        }
        checkConnectionBtn.setEnabled(true);
        updateStatus(IStatus.OK, null);
        return true;
    }

    @Override
    protected void updateEditableStatus(boolean isEditable) {
        kerbBtn.setEnabled(isEditable && enableKerberos);
        userNameText.setEditable(isEditable && !enableKerberos);
        endPonitText.setEditable(isEditable);
        updateHadoopPropertiesFields(isEditable);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        updateAuthenticationPart();
        updateConnectionPart();
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
        if (visible) {
            adaptFormToEditable();
            updateStatus(getStatusLevel(), getStatus());
        }
    }

    public void setProperties(List<HashMap<String, Object>> properties) {
        this.properties = properties;
    }

    public List<HashMap<String, Object>> getProperties() {
        return properties;
    }

    @Override
    protected void collectConParameters() {
        collectAuthSetingParameters(!enableKerberos);
        collectConnSetingParameters(true);

    }

    private void collectAuthSetingParameters(boolean useKerberos) {
        addContextParams(EHadoopParamName.OozieUser, useKerberos);
    }

    private void collectConnSetingParameters(boolean isUse) {
        addContextParams(EHadoopParamName.OozieEndpoint, true);
    }

}
