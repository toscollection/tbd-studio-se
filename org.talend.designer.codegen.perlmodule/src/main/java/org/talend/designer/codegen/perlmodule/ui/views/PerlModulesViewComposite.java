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
import org.apache.log4j.jmx.LayoutDynamicMBean;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.designer.codegen.perlmodule.ModuleNeeded;
import org.talend.designer.codegen.perlmodule.model.ModulesNeededProvider;

/**
 * This is the composite filled in the ModulesView. So it implemented the inferface IModulesViewComposite. Know more see
 * interface IModulesViewComposite.
 * 
 * yzhang class global comment. Detailled comment <br/>
 * 
 * $Id: PerlModulesViewComposite.java PerlModulesViewComposite 2007-1-26 下午02:53:04 +0000 (下午02:53:04, 2007-1-26 2007)
 * yzhang $
 * 
 */
public class PerlModulesViewComposite extends Composite implements IModulesViewComposite {

    protected static final String ID_STATUS = "status";

    private static TableViewerCreator tableViewerCreator;

    private static Logger log = Logger.getLogger(ModulesView.class);

    private IContextActivation ca;

    /**
     * Construct a new Perl modules view composite.
     * 
     * yzhang PerlModulesViewComposite constructor comment.
     * 
     * @param parent
     * @param style
     */
    public PerlModulesViewComposite(Composite parent) {
        super(parent, SWT.NONE);

        this.setLayout(new FormLayout());
        FormData formData = new FormData();

        Composite rightPartComposite = new Composite(this, SWT.NONE);
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

    /*
     * Be called when the set focus of modules view was called.
     * 
     * @see org.eclipse.swt.widgets.Composite#setFocus()
     */
    public boolean setFocus() {
        return tableViewerCreator.getTableViewer().getTable().setFocus();
    }

    /*
     * Be called when the refresh of modules view was called.
     * 
     * @see org.talend.designer.codegen.perlmodule.ui.views.IModulesViewComposite#refresh()
     */
    public void refresh() {
        ModulesNeededProvider.check();
        tableViewerCreator.getTableViewer().refresh();
    }

}
