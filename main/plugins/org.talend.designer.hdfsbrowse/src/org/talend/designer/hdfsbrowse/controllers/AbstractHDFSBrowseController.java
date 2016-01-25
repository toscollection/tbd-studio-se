// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.hdfsbrowse.controllers;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.core.CorePlugin;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.properties.tab.IDynamicProperty;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.process.DataNode;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.editor.properties.controllers.AbstractElementPropertySectionController;
import org.talend.designer.core.ui.editor.properties.controllers.creator.SelectAllTextControlCreator;
import org.talend.designer.hdfsbrowse.HDFSPlugin;
import org.talend.designer.hdfsbrowse.i18n.Messages;
import org.talend.designer.hdfsbrowse.manager.EHadoopParameter;
import org.talend.designer.hdfsbrowse.manager.HadoopMappingManager;
import org.talend.designer.hdfsbrowse.manager.HadoopOperationManager;
import org.talend.designer.hdfsbrowse.manager.HadoopServerUtil;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public abstract class AbstractHDFSBrowseController extends AbstractElementPropertySectionController {

    protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

    public AbstractHDFSBrowseController(IDynamicProperty dp) {
        super(dp);
    }

    protected Command createCommand(SelectionEvent event, HDFSConnectionBean connection) {
        HDFSBrowseDialog dial = new HDFSBrowseDialog(composite.getShell(), getHDFSType(), connection);
        Button btn = (Button) event.getSource();
        String propertyName = (String) btn.getData(PARAMETER_NAME);
        Text filePathText = (Text) hashCurControls.get(propertyName);
        if (dial.open() == Window.OK) {
            IHDFSNode result = dial.getResult();
            String path = result.getPath();
            path = TalendQuoteUtils.addQuotesIfNotExist(path);
            if (!elem.getPropertyValue(propertyName).equals(path)) {
                filePathText.setText(path);
                return new PropertyChangeCommand(elem, propertyName, path);
            }

        }
        return null;
    }

    protected abstract EHadoopFileTypes getHDFSType();

    protected abstract String getControllerName();

    protected HDFSConnectionBean getHDFSConnectionBean() {
        INode node = (INode) elem;
        boolean isMr = false;
        HDFSConnectionBean connectionBean = new HDFSConnectionBean();
        String useExistingConnection = ElementParameterParser.getValue(elem, "__USE_EXISTING_CONNECTION__"); //$NON-NLS-1$
        if ("true".equalsIgnoreCase(useExistingConnection)) { //$NON-NLS-1$
            String connectionName = ElementParameterParser.getValue(node, "__CONNECTION__"); //$NON-NLS-1$
            List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
            for (INode iNode : nodes) {
                if (iNode.getUniqueName().equals(connectionName)) {
                    node = iNode;
                    break;
                }
            }
        } else if (node != null && node.getComponent() != null && node.getComponent().getPaletteType() != null
                && node.getComponent().getPaletteType().equals("MR")) {
            List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
            for (INode iNode : nodes) {
                IComponent component = iNode.getComponent();
                if (component != null && component.getName() != null && component.getName().equals("tMRConfiguration")) {
                    node = iNode;
                    if (node instanceof DataNode) {
                        DataNode dataNode = (DataNode) node;
                        IElementParameter versionParameter = dataNode.getElementParameter(EHadoopParameter.MR_VERSION.getName());
                        if (versionParameter != null) {
                            connectionBean.setDfVersion((String) versionParameter.getValue());
                            isMr = true;
                        }
                        IElementParameter nameNodeParameter = dataNode.getElementParameter(EHadoopParameter.NAMENODE.getName());
                        if (nameNodeParameter != null) {
                            connectionBean.setNameNodeURI((String) nameNodeParameter.getValue());
                            isMr = true;
                        }
                    }
                }
            }
        }

        String distribution = (String) getParameterValue(node, EHadoopParameter.DISTRIBUTION.getName());
        if (!isMr) {
            String version = (String) getParameterValue(node, EHadoopParameter.VERSION.getName());
            String nameNodeUri = (String) getParameterValue(node, EHadoopParameter.NAMENODE_URI.getName());
            connectionBean.setDfVersion(version);
            connectionBean.setNameNodeURI(nameNodeUri);
        }
        // check support the group or not
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(connectionBean.getDfVersion());
        if (HadoopServerUtil.isSupportGroup(version4Drivers)) {
            String group = (String) getParameterValue(node, EHadoopParameter.GROUP.getName());
            connectionBean.setGroup(group);
        }
        String userName = (String) getParameterValue(node, EHadoopParameter.USERNAME.getName());
        Boolean useKrb = (Boolean) getParameterValue(node, EHadoopParameter.USE_KRB.getName());
        String principal = (String) getParameterValue(node, EHadoopParameter.NAMENODE_PRINCIPAL.getName());

        connectionBean.setDistribution(distribution);
        connectionBean.setUserName(userName);
        connectionBean.setEnableKerberos(useKrb != null ? useKrb : false);
        connectionBean.setPrincipal(principal);
        return connectionBean;
    }

    private Object getParameterValue(INode node, String paramName) {
        Map<String, List<String>> componentParamsMap = HadoopMappingManager.getInstance().getComponentParamsMap();
        List<String> paramslist = componentParamsMap.get(paramName);
        if (paramslist != null && paramslist.size() > 0) {
            for (String param : paramslist) {
                Object value = null;
                if (node instanceof DataNode) {
                    DataNode dataNode = (DataNode) node;
                    IElementParameter parameter = dataNode.getElementParameter(param);
                    if (parameter != null) {
                        value = parameter.getValue();
                    }
                } else {
                    value = node.getPropertyValue(param);
                }
                if (value != null) {
                    return value;
                }
            }
        }

        return null;
    }

    protected boolean checkHDFSConnection(final HDFSConnectionBean connection) {
        final boolean[] result = new boolean[] { true };
        IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(Messages.getString("AbstractHDFSBrowseController.checkConnection"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                Object dfs = null;
                try {
                    dfs = HadoopOperationManager.getInstance().getDFS(connection);
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                } finally {
                    monitor.done();
                }
                if (dfs == null) {
                    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                        @Override
                        public void run() {
                            String mainMsg = Messages.getString("AbstractHDFSBrowseController.connectionFailure.mainMsg"); //$NON-NLS-1$
                            String detailMsg = Messages.getString("AbstractHDFSBrowseController.connectionFailure.detailMsg", //$NON-NLS-1$
                                    connection.getNameNodeURI());
                            new ErrorDialogWidthDetailArea(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
                                    HDFSPlugin.PLUGIN_ID, mainMsg, detailMsg);
                            result[0] = false;
                            return;
                        }
                    });
                }
            }
        };
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
        try {
            dialog.run(true, true, runnableWithProgress);
        } catch (Exception e) {
            result[0] = false;
            ExceptionHandler.process(e);
        }

        return result[0];
    }

    @Override
    public Control createControl(final Composite subComposite, final IElementParameter param, final int numInRow,
            final int nbInRow, final int top, final Control lastControl) {
        this.curParameter = param;
        Button btnEdit = getWidgetFactory().createButton(subComposite, EMPTY_STRING, SWT.PUSH);
        FormData data;

        btnEdit.setImage(ImageProvider.getImage(CorePlugin.getImageDescriptor(DOTS_BUTTON)));

        data = new FormData();
        data.left = new FormAttachment(((numInRow * MAX_PERCENT) / nbInRow), -STANDARD_BUTTON_WIDTH);
        data.right = new FormAttachment(((numInRow * MAX_PERCENT) / nbInRow), 0);
        data.top = new FormAttachment(0, top);
        data.height = STANDARD_HEIGHT - 2;
        btnEdit.setLayoutData(data);
        btnEdit.setData(NAME, getControllerName());
        btnEdit.setData(PARAMETER_NAME, param.getName());
        btnEdit.setEnabled(!param.isReadOnly());
        btnEdit.addSelectionListener(listenerSelection);

        DecoratedField dField = new DecoratedField(subComposite, SWT.BORDER, new SelectAllTextControlCreator());
        if (param.isRequired()) {
            FieldDecoration decoration = FieldDecorationRegistry.getDefault().getFieldDecoration(
                    FieldDecorationRegistry.DEC_REQUIRED);
            dField.addFieldDecoration(decoration, SWT.RIGHT | SWT.TOP, false);
        }

        Control cLayout = dField.getLayoutControl();
        Text labelText = (Text) dField.getControl();

        cLayout.setBackground(subComposite.getBackground());
        if (!elem.isReadOnly()) {
            labelText.setEditable(!param.isReadOnly() && !param.isRepositoryValueUsed());
        } else {
            labelText.setEditable(false);
        }
        labelText.setData(PARAMETER_NAME, param.getName());

        editionControlHelper.register(param.getName(), labelText);

        addDragAndDropTarget(labelText);
        if (elem instanceof Node) {
            labelText.setToolTipText(VARIABLE_TOOLTIP + param.getVariableName());
        }

        CLabel labelLabel = getWidgetFactory().createCLabel(subComposite, param.getDisplayName());
        data = new FormData();
        if (lastControl != null) {
            data.left = new FormAttachment(lastControl, 0);
        } else {
            data.left = new FormAttachment((((numInRow - 1) * MAX_PERCENT) / nbInRow), 0);
        }
        data.top = new FormAttachment(0, top);
        labelLabel.setLayoutData(data);
        if (numInRow != 1) {
            labelLabel.setAlignment(SWT.RIGHT);
        }
        // **************************
        data = new FormData();
        int currentLabelWidth = STANDARD_LABEL_WIDTH;
        GC gc = new GC(labelLabel);
        Point labelSize = gc.stringExtent(param.getDisplayName());
        gc.dispose();

        if ((labelSize.x + ITabbedPropertyConstants.HSPACE) > currentLabelWidth) {
            currentLabelWidth = labelSize.x + ITabbedPropertyConstants.HSPACE;
        }

        if (numInRow == 1) {
            if (lastControl != null) {
                data.left = new FormAttachment(lastControl, currentLabelWidth);
            } else {
                data.left = new FormAttachment(0, currentLabelWidth);
            }
        } else {
            data.left = new FormAttachment(labelLabel, 0, SWT.RIGHT);
        }
        data.right = new FormAttachment(btnEdit, 0);
        data.top = new FormAttachment(btnEdit, 0, SWT.CENTER);
        cLayout.setLayoutData(data);

        hashCurControls.put(param.getName(), labelText);

        Point initialSize = dField.getLayoutControl().computeSize(SWT.DEFAULT, SWT.DEFAULT);

        dynamicProperty.setCurRowSize(initialSize.y + ITabbedPropertyConstants.VSPACE);

        return btnEdit;
    }

    @Override
    public int estimateRowSize(Composite subComposite, IElementParameter param) {
        DecoratedField dField = new DecoratedField(subComposite, SWT.BORDER, new TextControlCreator());
        Point initialSize = dField.getLayoutControl().computeSize(SWT.DEFAULT, SWT.DEFAULT);
        dField.getLayoutControl().dispose();

        return initialSize.y + ITabbedPropertyConstants.VSPACE;
    }

    private SelectionListener listenerSelection = new SelectionListener() {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {

        }

        @Override
        public void widgetSelected(SelectionEvent event) {
            HDFSConnectionBean connection = getHDFSConnectionBean();
            if (checkHDFSConnection(connection)) {
                Command command = createCommand(event, connection);
                executeCommand(command);
            }
        }

    };

    @Override
    public void refresh(IElementParameter param, boolean checkErrorsWhenViewRefreshed) {
        Text labelText = (Text) hashCurControls.get(param.getName());
        Object value = param.getValue();
        if (labelText == null || labelText.isDisposed()) {
            return;
        }
        boolean valueChanged = false;
        if (value == null) {
            labelText.setText(EMPTY_STRING);
        } else {
            if (!value.equals(labelText.getText())) {
                labelText.setText((String) value);
                valueChanged = true;
            }
        }
        if (checkErrorsWhenViewRefreshed || valueChanged) {
            checkErrorsForPropertiesOnly(labelText);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
    }

}
