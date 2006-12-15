// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.codegen.perlmodule.ui.views;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ActionHandler;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.designer.codegen.perlmodule.ModuleNeeded;
import org.talend.designer.codegen.perlmodule.model.ModulesNeededProvider;
import org.talend.designer.codegen.perlmodule.ui.actions.CheckModulesAction;

/**
 * DOC nrousseau class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ModulesView extends ViewPart {

    private static Logger log = Logger.getLogger(ModulesView.class);

    protected static final String ID_STATUS = "status";

    private static TableViewerCreator tableViewerCreator;

    private CheckModulesAction checkAction;

    public ModulesView() {
    }

    public void refresh() {
        ModulesNeededProvider.check();
        tableViewerCreator.getTableViewer().refresh();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl(Composite parent) {

        parent.setLayout(new FormLayout());
        FormData formData = new FormData();

        Composite rightPartComposite = new Composite(parent, SWT.NONE);
        formData = new FormData();
        formData.top = new FormAttachment(0);
        formData.left = new FormAttachment(0);
        formData.right = new FormAttachment(100);
        formData.bottom = new FormAttachment(100);
        rightPartComposite.setLayoutData(formData);
        rightPartComposite.setLayout(new FillLayout());

        tableViewerCreator = new TableViewerCreator(rightPartComposite);
        tableViewerCreator.setCheckboxInFirstColumn(false);
        tableViewerCreator.setColumnsResizableByDefault(true);
        tableViewerCreator.setLayoutMode(LAYOUT_MODE.FILL_HORIZONTAL);
        tableViewerCreator.createTable();

        TableViewerCreatorColumn column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Status");
        column.setId(ID_STATUS);
        column.setSortable(true);
        column.setImageProvider(new StatusImageProvider());
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                String str = null;
                switch (bean.getStatus()) {
                case INSTALLED:
                    str = "Installed";
                    break;
                case NOT_INSTALLED:
                    str = "Not installed";
                    break;
                default:
                    str = "Unknown";
                }
                return str;
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setWeight(3);
        column.setModifiable(false);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Component");
        column.setSortable(true);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                return bean.getComponentName();
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setModifiable(false);
        column.setWeight(3);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Module");
        column.setSortable(true);
        tableViewerCreator.setDefaultSort(column, SORT.ASC);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                return bean.getModuleName();
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setModifiable(false);
        column.setWeight(3);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Required for");

        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                return bean.getInformationMsg();
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setModifiable(true);
        column.setWeight(12);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Required");
        column.setImageProvider(new RequiredImageProvider());
        column.setSortable(true);
        column.setDisplayedValue("");
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                return String.valueOf(bean.isRequired());
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setModifiable(false);
        column.setWeight(2);

        tableViewerCreator.init(ModulesNeededProvider.getModulesNeeded());

        makeActions();
        contributeToActionBars();

        FocusListener fl = new FocusListener() {

            public void focusGained(FocusEvent e) {
                log.trace("Modules gain focus");
                IContextService contextService = (IContextService) PlatformUI.getWorkbench().getAdapter(IContextService.class);
                ca = contextService.activateContext("talend.modules");
            }

            public void focusLost(FocusEvent e) {
                log.trace("Modules lost focus");
                if (ca != null) {
                    IContextService contextService = (IContextService) PlatformUI.getWorkbench()
                            .getAdapter(IContextService.class);
                    contextService.deactivateContext(ca);
                }
            }
        };

        parent.addFocusListener(fl);
        rightPartComposite.addFocusListener(fl);
        tableViewerCreator.getTableViewer().getTable().addFocusListener(fl);
    }

    private IContextActivation ca;

    private void makeActions() {
        checkAction = new CheckModulesAction(this);

        IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);

        IHandler handler1 = new ActionHandler(checkAction);
        handlerService.activateHandler(checkAction.getActionDefinitionId(), handler1);
    }

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();
        fillLocalToolBar(bars.getToolBarManager());
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.add(checkAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {
        tableViewerCreator.getTableViewer().getTable().setFocus();
    }

}
