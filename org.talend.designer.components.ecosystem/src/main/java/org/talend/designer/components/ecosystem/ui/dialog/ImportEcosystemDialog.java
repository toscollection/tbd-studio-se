// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.ui.dialog;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.image.EImage;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.i18n.Messages;
import org.talend.designer.components.ecosystem.jobs.ComponentDownloader;
import org.talend.designer.components.ecosystem.jobs.ComponentSearcher;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.ui.views.CompatibleEcoComponentsComposite;

/**
 * gcui A dialog for user to select filters that will be used to search components.
 * 
 * This dialog will open when Browse Talend Exchange button push.
 */
public class ImportEcosystemDialog extends Dialog {

    private ImportEcosystemProperty downloadproperty;

    private Button jobButton;

    private Button templatesButton;

    private Button routinesButton;

    private Button refresh;

    private static String type;

    private static String version;

    private URL url;

    private String selectFile;

    private String progressBarMessage;

    private File tempFile;

    public String getSelectFile() {
        return selectFile;
    }

    public void setSelectFile(String selectFile) {
        this.selectFile = selectFile;
    }

    protected TableViewerCreator<ComponentExtension> fTableViewerCreator;

    protected TableViewerCreatorColumn<ComponentExtension, String> fNameColumn;

    protected ImportEcosystemDialog(Shell shell) {
        super(shell);
        this.setShellStyle(this.getShellStyle() | SWT.MIN | SWT.MAX | SWT.RESIZE);
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(Messages.getString("ImportEcosystemDialog.dialogTitle")); //$NON-NLS-1$
    }

    protected Control createDialogArea(Composite parent) {
        Composite ecosystemDialogCom = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.makeColumnsEqualWidth = false;
        layout.marginWidth = 0;
        ecosystemDialogCom.setLayout(layout);
        GridData gridData = new GridData(GridData.FILL_BOTH);

        ecosystemDialogCom.setLayoutData(gridData);

        ImportCompatibleEcoComponentsComposite eco = new ImportCompatibleEcoComponentsComposite(ecosystemDialogCom,
                ecosystemDialogCom.getShell(), false);

        return parent;

    }

    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.FINISH_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);

    }

    @Override
    protected void okPressed() {

        IPath tempPath = new Path(System.getProperty("user.dir")).append("temp"); //$NON-NLS-1$ //$NON-NLS-2$
        File pathFile = tempPath.toFile();
        if (downloadproperty.getFileName() == null || downloadproperty.getFileName() == null) {
            MessageBox box = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
            box.setText(Messages.getString("ImportEcosystemDialog.WARNING")); //$NON-NLS-1$
            box.setMessage(Messages.getString("ImportEcosystemDialog.NOTSELECTWARNING")); //$NON-NLS-1$
            box.open();
            return;
        }

        tempFile = new File(pathFile, downloadproperty.getFileName());
        try {
            url = new URL(downloadproperty.getDownloadUrl());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell()
                .getShell());
        IRunnableWithProgress runnable = new IRunnableWithProgress() {

            public void run(IProgressMonitor monitor) {
                monitor.beginTask(Messages.getString("ImportEcosystemDialog.downloadProgressBar"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                Display.getDefault().syncExec(new Runnable() {

                    public void run() {
                        try {
                            new ComponentDownloader().download(url, tempFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                monitor.done();
            }
        };
        try {
            progressDialog.run(true, true, runnable);
        } catch (InvocationTargetException e1) {
            ExceptionHandler.process(e1);
        } catch (InterruptedException e1) {
            ExceptionHandler.process(e1);
        }

        selectFile = tempFile.toString();

        super.okPressed();
    }

    /**
     * 
     * gcui ImportCompatibleEcoComponentsComposite class global comment. Detailled comment
     * 
     * ImportCompatibleEcoComponentsComposite it work as CompatibleEcoComponentsComposite.
     */
    class ImportCompatibleEcoComponentsComposite extends CompatibleEcoComponentsComposite {

        private static final int HEIGHT = 300;

        private static final int WIDTH = 500;

        private List<ComponentExtension> compatible;

        public ImportCompatibleEcoComponentsComposite(Composite parent, Shell shell, boolean init) {
            super(parent, shell, init);
        }

        @Override
        protected void createControls(Composite parent) {
            setLayout(clearGridLayoutSpace(new GridLayout(1, false)));
            setLayoutData(new GridData(GridData.FILL_BOTH));
            creatOptions(parent);

            Composite tableComposite = new Composite(parent, SWT.NONE);
            tableComposite.setLayout(new GridLayout());

            GridData layoutData = new GridData(GridData.FILL_BOTH);
            // layoutData.widthHint = WIDTH;
            // layoutData.minimumWidth = WIDTH;
            layoutData.heightHint = HEIGHT;
            layoutData.minimumHeight = HEIGHT;
            tableComposite.setLayoutData(layoutData);

            createTable(tableComposite);

            fTableViewerCreator.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

        }

        protected void createTable(Composite tableComposite) {

            fTableViewerCreator = new TableViewerCreator<ComponentExtension>(tableComposite);
            fTableViewerCreator.setCheckboxInFirstColumn(false);
            fTableViewerCreator.setColumnsResizableByDefault(true);
            fTableViewerCreator.setLayoutMode(LAYOUT_MODE.FILL_HORIZONTAL);
            fTableViewerCreator.setHorizontalScroll(true);
            fTableViewerCreator.setVerticalScroll(true);
            fTableViewerCreator.createTable();

            createActionColumn(0);

            // this column is extension name column
            fNameColumn = createTableColumn(
                    Messages.getString("ImportEcosystemDialog.EXTENSION_NAME"), true, false, 120, NAME_ACCESSOR); //$NON-NLS-1$
            // this column is authorColumn
            createTableColumn(Messages.getString("ImportEcosystemDialog.AUTHOR_NAME"), true, false, 70, AUTHOR_ACCESSOR); //$NON-NLS-1$
            // this column is lastest revision column
            createTableColumn(
                    Messages.getString("ImportEcosystemDialog.LATEST_REVISION"), true, false, 110, LATEST_REVISION_ACCESSOR); //$NON-NLS-1$
            // this column is extension description column
            TableViewerCreatorColumn<ComponentExtension, String> descriptionColumn = createTableColumn(Messages
                    .getString("ImportEcosystemDialog.EXTENSION_DESCRIPTION"), //$NON-NLS-1$
                    true, false, 1300, DESCRIPTION_ACCESSOR);

            fTableViewerCreator.setDefaultSort(fNameColumn, SORT.ASC);

            final Table table = fTableViewerCreator.getTable();

            downloadproperty = new ImportEcosystemProperty();
            table.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {

                    downloadproperty.setDownloadUrl(compatible.get(table.getSelectionIndex()).getLatestRevision().getUrl());

                    downloadproperty.setFileName(compatible.get(table.getSelectionIndex()).getLatestRevision().getFileName());
                }
            });
        }

        public void updateTable(List<ComponentExtension> extensions) {
            fTableViewerCreator.setInputList(extensions);
            fTableViewerCreator.setSort(fNameColumn, SORT.ASC);

            addButtons();
        }

        void addButtons() {
            fTableViewerCreator.getTableViewer().getControl().setRedraw(false);
            fTableViewerCreator.getTableViewer().getTable().layout();
            fTableViewerCreator.getTableViewer().refresh(true);
            fTableViewerCreator.getTableViewer().getControl().setRedraw(true);
        }

        @Override
        protected void openFilterDialog() {
            //
        }

        /**
         * 
         * gcui There will create three radio button.
         */
        private void creatOptions(Composite ecosystemDialogCom) {
            Group group = new Group(ecosystemDialogCom, SWT.NONE);
            GridLayout layout = new GridLayout();
            layout.numColumns = 4;
            layout.makeColumnsEqualWidth = false;
            layout.marginWidth = 5;
            group.setLayout(layout);
            group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            jobButton = new Button(group, SWT.RADIO);
            jobButton.setText(Messages.getString("ImportEcosystemDialog.JOB_BUTTON")); //$NON-NLS-1$
            jobButton.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    type = "7"; //$NON-NLS-1$
                    version = "35,36,37"; //$NON-NLS-1$
                    progressBarMessage = Messages.getString("ImportEcosystemDialog.DOWNLOAD_JOB"); //$NON-NLS-1$
                    findChoiceExchange();

                }
            });

            templatesButton = new Button(group, SWT.RADIO);
            templatesButton.setText(Messages.getString("ImportEcosystemDialog.TEMPLATES_BUTTON")); //$NON-NLS-1$
            templatesButton.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    type = "8"; //$NON-NLS-1$
                    version = "35,36,37"; //$NON-NLS-1$
                    progressBarMessage = Messages.getString("ImportEcosystemDialog.TEMPLATES_PROGRESSBAR"); //$NON-NLS-1$
                    findChoiceExchange();
                }
            });

            routinesButton = new Button(group, SWT.RADIO);
            routinesButton.setText(Messages.getString("ImportEcosystemDialog.ROUTINES_BUTTON")); //$NON-NLS-1$
            routinesButton.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    type = "9"; //$NON-NLS-1$
                    version = "35,36,37"; //$NON-NLS-1$
                    progressBarMessage = Messages.getString("ImportEcosystemDialog.ROUTINES_PROGRESSBAR"); //$NON-NLS-1$
                    findChoiceExchange();
                }
            });

            refresh = new Button(group, SWT.PUSH);
            refresh.setImage(ImageProvider.getImage(EImage.REFRESH_ICON));
            refresh.setToolTipText(Messages.getString("ImportEcosystemDialog.REFRESH_BUTTON"));
            //refresh.setText(Messages.getString("ImportEcosystemDialog.REFRESH_BUTTON")); //$NON-NLS-1$
            refresh.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    if (version == null || type == null) {
                        MessageBox box = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
                        box.setText(Messages.getString("ImportEcosystemDialog.WARNING")); //$NON-NLS-1$
                        box.setMessage(Messages.getString("ImportEcosystemDialog.NOT_SELECT_BUTTON")); //$NON-NLS-1$
                        box.open();
                        return;
                    }
                    if (type != null && !jobButton.getSelection() && !templatesButton.getSelection()
                            && !routinesButton.getSelection()) {
                        if (type == "7") { //$NON-NLS-1$
                            jobButton.setSelection(true);
                        } else if (type == "8") { //$NON-NLS-1$
                            templatesButton.setSelection(true);
                        } else if (type == "9") { //$NON-NLS-1$
                            routinesButton.setSelection(true);
                        }
                    }
                    progressBarMessage = Messages.getString("ImportEcosystemDialog.REFRESHING_PROGRESSBAR"); //$NON-NLS-1$
                    findChoiceExchange();
                }
            });
        }

        /**
         * 
         * gcui This is a progressBar.when this progressBar run it will download some jobs/templates/routines from net.
         */
        private void findChoiceExchange() {
            ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getDisplay()
                    .getActiveShell().getShell());
            IRunnableWithProgress runnable = new IRunnableWithProgress() {

                public void run(IProgressMonitor monitor) {
                    monitor.beginTask(progressBarMessage, IProgressMonitor.UNKNOWN);
                    Display.getDefault().syncExec(new Runnable() {

                        public void run() {
                            compatible = ComponentSearcher.getAvailableComponentExtensions(version, EcosystemUtils
                                    .getCurrentLanguage(), type);
                            updateTable(compatible);
                        }
                    });
                    monitor.done();
                    if (monitor.isCanceled()) {
                        try {
                            throw new InterruptedException(Messages.getString("ImportEcosystemDialog.OPERATION_CANCELLED")); //$NON-NLS-1$
                        } catch (InterruptedException e) {
                            ExceptionHandler.process(e);
                        }
                    }
                }
            };
            try {
                progressDialog.run(true, true, runnable);
            } catch (InvocationTargetException e1) {
                ExceptionHandler.process(e1);
            } catch (InterruptedException e1) {
                ExceptionHandler.process(e1);
            }

        }
    }
}
