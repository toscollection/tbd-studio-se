// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.ui.views;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.ui.ws.WindowSystem;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.util.ActionHelper;

/**
 * DOC chuang class global comment. Detailled comment
 */
public class InstalledEcoComponentsComposite extends
		AbstractEcoComponentsComposite {
	static final int UPDATE_BUTTON_COLUMN = 0;

	static final int REMOVE_BUTTON_COLUMN = 1;

	List<TableEditor> updateEditors = new ArrayList<TableEditor>();

	List<TableEditor> removeEditors = new ArrayList<TableEditor>();
	
	ViewerFilter filter = new ViewerFilter() {

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			// ComponentExtension ext = (ComponentExtension) element;
			return true;
		}

	};

	/**
	 * DOC chuang InstalledEcoComponentsComposite constructor comment.
	 */
	InstalledEcoComponentsComposite(Composite parent, Shell shell) {
		super(parent, shell);
		createControls(this);
		init();
	}

	/**
	 * DOC chuang Comment method "init".
	 */
	public void init() {
		// load installed component information from file
		loadFromFile();
		initTable(new ArrayList<ComponentExtension>(fInstalledExtensions
				.values()));
		refresh();
	}

	/**
	 * DOC chuang Comment method "createControls".
	 * 
	 * @param installedEcoComponentsComposite
	 */
	private void createControls(Composite parent) {
		setLayout(clearGridLayoutSpace(new GridLayout(2, false)));
		// creatTosVersionFilter(parent);

		Composite tableComposite = new Composite(this, SWT.NONE);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 2, 1));
		tableComposite.setLayout(new FillLayout());
		createTable(tableComposite);
	}

	/**
	 * Create the table viewer to display component information.
	 * 
	 * @param tableComposite
	 */
	private void createTable(Composite tableComposite) {
		fTableViewerCreator = new TableViewerCreator<ComponentExtension>(
				tableComposite);
		fTableViewerCreator.setCheckboxInFirstColumn(false);
		fTableViewerCreator.setColumnsResizableByDefault(true);
		fTableViewerCreator.setLayoutMode(LAYOUT_MODE.FILL_HORIZONTAL);
		fTableViewerCreator.setHorizontalScroll(true);
		fTableViewerCreator.setVerticalScroll(true);
		fTableViewerCreator.createTable();
		// fTableViewerCreator.getTableViewer().addFilter(filter);

		// install / update / remove actions, see 0005053: [ecosystem view]
		// update and remove components
		int width = ICON_WIDTH;
		if (WindowSystem.isGTK()) {
			// fix button icon size in linux
			width = 32;
		}
		createActionColumn(width + 6);
		createActionColumn(width + 4);

		fNameColumn = createTableColumn(
				EcosystemConstants.COMPONENT_NAME_TITLE, true, false, 100,
				NAME_ACCESSOR);
		createTableColumn(EcosystemConstants.AUTHOR_TITLE, true, false, 70,
				AUTHOR_ACCESSOR); // authorColumn

		// install revision column
		createTableColumn(EcosystemConstants.INSTALLED_REVISION_TITLE, true,
				false, 110, INSTALLED_REVISION_ACCESSOR);
		TableViewerCreatorColumn<ComponentExtension, String> descriptionColumn = createTableColumn(
				EcosystemConstants.DESCRIPTION_TITLE, true, false, 1300,
				DESCRIPTION_ACCESSOR); // descriptionColumn
		// descriptionColumn.setMinimumWidth(1300);
		fTableViewerCreator.setDefaultSort(fNameColumn, SORT.ASC);

		final Table table = fTableViewerCreator.getTable();
		table.addListener(SWT.MeasureItem, new Listener() {

			public void handleEvent(Event event) {
				TableItem item = (TableItem) event.item;
				String text = item.getText(event.index);
				Point size = event.gc.textExtent(text);
				if (event.index < 2) {
					// event.width = 40;
					if (WindowSystem.isGTK()) {
						event.height = ICON_HEIGHT + 10;
					} else {
						event.height = ICON_HEIGHT + 6;
					}
				}
			}
		});
	}

	@Override
	void addButtons() {
		fTableViewerCreator.getTableViewer().getControl().setRedraw(false);

		addUpdateButtons();
		addRemoveButtons();
		fTableViewerCreator.getTableViewer().getTable().layout();
		fTableViewerCreator.getTableViewer().refresh(true);

		fTableViewerCreator.getTableViewer().getControl().setRedraw(true);
	}

	/**
	 * DOC chuang Comment method "addRemoveButtons".
	 */
	private void addRemoveButtons() {
		disposeEditors(removeEditors);
		final Table table = fTableViewerCreator.getTable();
		for (final TableItem item : table.getItems()) {
			TableEditor editor = new TableEditor(table);
			removeEditors.add(editor);
			final Button button = new Button(table, SWT.FLAT);
			button.setImage(StatusImageProvider
					.getRemoveImage((ComponentExtension) item.getData()));
			button.setToolTipText("Remove Component");
			button.setData(item);
			if (button.getImage() == StatusImageProvider.REMOVE_ICON) {
				button.addSelectionListener(new SelectionAdapter() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.swt.events.SelectionAdapter#widgetSelected
					 * (org.eclipse.swt.events.SelectionEvent)
					 */
					@Override
					public void widgetSelected(SelectionEvent e) {
						button.setEnabled(false);
						ComponentExtension component = (ComponentExtension) item
								.getData();
						
						onRemoveButtonClick(button, component);
					}					

				});
			}
			// button.pack();
			editor.grabHorizontal = true;
			editor.minimumHeight = ICON_HEIGHT;
			editor.setEditor(button, item, REMOVE_BUTTON_COLUMN);
			editor.layout();
		}

	}
	
	/**
	 * DOC chuang Comment method "onRemoveButtonClick".
	 * 
	 * @param button
	 * @param component
	 */
	private void onRemoveButtonClick(final Button button,
			final ComponentExtension component) {

		IRunnableWithProgress op = new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Remove component " + component.getName(),
						100);
				monitor.worked(10);
				EcosystemUtils.deleteComponent(component);
				monitor.worked(70);
				// update view
				EcosystemView view = EcosystemUtils.getEcosystemView();
				view.removeInstalledExtension(component);
				monitor.worked(10);
				saveToFile();
				button.setEnabled(true);
				view.refresh();		
				monitor.done();
								
			}

		};
		try {
			new ProgressMonitorDialog(getShell()).run(false, false, op);
		} catch (Exception e) {
			// ignore me
		}
		StringBuilder message = new StringBuilder();
		message.append("Component ").append(component.getName()).append(
				" is successfully removed.");
		MessageDialog.openInformation(getShell(),
				"Remove Ecosystem Components", message.toString());

	}
	
	public void removeInstalledExtension(ComponentExtension extension) {
		fInstalledExtensions.remove(extension.getName());
	}

	/**
	 * DOC chuang Comment method "refresh".
	 */
	protected void refresh() {
		fTableViewerCreator.setInputList(new ArrayList<ComponentExtension>(
				fInstalledExtensions.values()));
		// fTableViewerCreator.getTableViewer().refresh();
		addButtons();		
	}

	/**
	 * DOC chuang Comment method "addUpdateButtons".
	 */
	private void addUpdateButtons() {
		disposeEditors(updateEditors);
		final Table table = fTableViewerCreator.getTable();
		for (final TableItem item : table.getItems()) {
			TableEditor editor = new TableEditor(table);
			updateEditors.add(editor);
			final Button button = new Button(table, SWT.FLAT);
			button.setImage(StatusImageProvider
					.getUpdateImage((ComponentExtension) item.getData()));
			button.setToolTipText("Update Component");
			button.setData(item);
			if (button.getImage() == StatusImageProvider.UPDATE_ICON) {
				button.addSelectionListener(new SelectionAdapter() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.swt.events.SelectionAdapter#widgetSelected
					 * (org.eclipse.swt.events.SelectionEvent)
					 */
					@Override
					public void widgetSelected(SelectionEvent e) {
						table.select(table.indexOf(item));
						IAction action = ActionHelper.getDownloadAction();
						if (action != null) {
							addButtonStateListener(button, action);
							action.run();
						}
					}

				});
			}
			// button.pack();
			editor.grabHorizontal = true;
			editor.minimumHeight = ICON_HEIGHT;
			editor.setEditor(button, item, UPDATE_BUTTON_COLUMN);
			editor.layout();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.talend.designer.components.ecosystem.ui.views.
	 * AbstractEcoComponentsComposite
	 * #onVersionFilterChanged(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	protected void onVersionFilterChanged(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

}
