package org.talend.oozie.scheduler.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.talend.commons.ui.swt.advanced.dataeditor.AbstractDataTableEditorView;
import org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView;
import org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButtonForExtendedTable;
import org.talend.commons.ui.swt.advanced.dataeditor.button.PastePushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.PastePushButtonForExtendedTable;
import org.talend.commons.ui.swt.advanced.dataeditor.commands.ExtendedTablePasteCommand;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.commons.utils.data.list.ListenableListEvent;
import org.talend.core.model.metadata.builder.connection.ConditionType;
import org.talend.oozie.scheduler.ui.model.HadoopPropertiesFieldModel;
import org.talend.oozie.scheduler.ui.model.HadoopPropertiesType;

public class HadoopPropertiesTableView extends AbstractDataTableEditorView<HadoopPropertiesType> {

    public HadoopPropertiesTableView(HadoopPropertiesFieldModel model, Composite parent, int styleChild) {
        this(model, parent, styleChild, false);
    }

    public HadoopPropertiesTableView(HadoopPropertiesFieldModel model, Composite parent, int styleChild, boolean showDbTypeColumn) {
        super(parent, styleChild, model);
    }

    public HadoopPropertiesTableView(HadoopPropertiesFieldModel model, Composite parent) {
        this(model, parent, SWT.NONE, false);
    }

    @Override
    protected void handleBeforeListenableListOperationEvent(ListenableListEvent<HadoopPropertiesType> event) {
        super.handleBeforeListenableListOperationEvent(event);
    }

    @Override
    protected void handleAfterListenableListOperationEvent(ListenableListEvent<HadoopPropertiesType> event) {
        super.handleAfterListenableListOperationEvent(event);
    }

    @Override
    protected void setTableViewerCreatorOptions(TableViewerCreator<HadoopPropertiesType> newTableViewerCreator) {
        super.setTableViewerCreatorOptions(newTableViewerCreator);
    }

    @Override
    protected void createColumns(TableViewerCreator<HadoopPropertiesType> tableViewerCreator, Table table) {
        createKeyColumn(tableViewerCreator);
        createValueColumn(tableViewerCreator);
    }

    private TableViewerCreatorColumn createValueColumn(TableViewerCreator<HadoopPropertiesType> tableViewerCreator) {
        TableViewerCreatorColumn column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Value"); //$NON-NLS-1$
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<HadoopPropertiesType, String>() {

            @Override
            public String get(HadoopPropertiesType bean) {
                return bean.getValue();
            }

            @Override
            public void set(HadoopPropertiesType bean, String value) {
                bean.setValue(value);
            }

        });
        column.setCellEditor(new TextCellEditor(tableViewerCreator.getTable()));

        column.setModifiable(true);
        column.setWeight(30);
        column.setMinimumWidth(50);
        column.setDefaultInternalValue(""); //$NON-NLS-1$
        return column;
    }

    private TableViewerCreatorColumn createKeyColumn(TableViewerCreator<HadoopPropertiesType> tableViewerCreator) {
        TableViewerCreatorColumn column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Key"); //$NON-NLS-1$
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<HadoopPropertiesType, String>() {

            @Override
            public String get(HadoopPropertiesType bean) {
                return bean.getKey();

            }

            @Override
            public void set(HadoopPropertiesType bean, String value) {
                bean.setKey(value);
            }

        });
        column.setCellEditor(new TextCellEditor(tableViewerCreator.getTable()));

        column.setModifiable(true);
        column.setWeight(30);
        column.setMinimumWidth(50);
        column.setDefaultInternalValue(""); //$NON-NLS-1$
        return column;
    }

    public HadoopPropertiesFieldModel getModel() {
        return (HadoopPropertiesFieldModel) getExtendedTableModel();
    }

    @Override
    protected ExtendedToolbarView initToolBar() {
        return new ExtendedToolbarView(getMainComposite(), SWT.NONE, getExtendedTableViewer()) {

            @Override
            protected AddPushButtonForExtendedTable createAddPushButton() {
                return new AddPushButtonForExtendedTable(this.toolbar, getExtendedTableViewer()) {

                    @Override
                    protected Object getObjectToAdd() {
                        HadoopPropertiesType hpt = getModel().createHadoopPropertiesType();
                        hpt.setKey("new line1");
                        return hpt;
                    }

                };
            }

            @Override
            protected PastePushButton createPastePushButton() {
                return new PastePushButtonForExtendedTable(toolbar, extendedTableViewer) {

                    @Override
                    protected Command getCommandToExecute(ExtendedTableModel extendedTableModel, Integer indexWhereInsert) {
                        return new ExtendedTablePasteCommand(extendedTableModel, indexWhereInsert) {

                            @Override
                            public List<ConditionType> createPastableBeansList(ExtendedTableModel extendedTableModel,
                                    List copiedObjectsList) {
                                List list = new ArrayList();
                                HadoopPropertiesFieldModel fieldsModel = (HadoopPropertiesFieldModel) extendedTableModel;
                                for (Object current : copiedObjectsList) {
                                    // if (current instanceof ConditionType) {
                                    // HadoopPropertiesType original = (ConditionType) current;
                                    // HadoopPropertiesType copy = fieldsModel.createHadoopPropertiesType();
                                    // copy.setInputColumn(original.getInputColumn());
                                    // copy.setFunction(original.getFunction());
                                    // copy.setOperator(original.getOperator());
                                    // copy.setValue(original.getValue());
                                    // list.add(copy);
                                    // }
                                }
                                return list;
                            }
                        };
                    }

                };
            }
        };
    }
}
