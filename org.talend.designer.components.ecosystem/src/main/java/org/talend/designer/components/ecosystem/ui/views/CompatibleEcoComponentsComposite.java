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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.ui.ws.WindowSystem;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.EcosystemPlugin;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.jobs.ComponentSearcher;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Revision;
import org.talend.designer.components.ecosystem.model.util.ActionHelper;
import org.talend.designer.components.ecosystem.ui.actions.RefreshComponenentsAction;
import org.talend.designer.components.ecosystem.ui.actions.RefreshJob;

/**
 * Composite that contains a table viewer and display in the ecosystem view.
 */
public class CompatibleEcoComponentsComposite extends
		AbstractEcoComponentsComposite {

	private static final int INSTALL_BUTTON_COLUMN = 0;	

	private List<TableEditor> installEditors = new ArrayList<TableEditor>();

	private static final String REFRESH_COMPONENTS = "refreshComponents";

	private static final String[] AVAILABLE_FILTERS = new String[] {
			"Name", "Description" }; //$NON-NLS-1$ //$NON-NLS-2$

	private static final Map<String, Integer> FILTER_MAP = new HashMap<String, Integer>();

	private List<ComponentExtension> fAvailableExtensions;
	
	private String[] fFilters = AVAILABLE_FILTERS;

	private Text fFilterText;		

	static {
		FILTER_MAP.put(AVAILABLE_FILTERS[0],
				EcosystemPackage.COMPONENT_EXTENSION__NAME);
		FILTER_MAP.put(AVAILABLE_FILTERS[1],
				EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION);
	}

	/**
	 * EcosystemViewComposite constructor.
	 * 
	 * @param parent
	 * @param shell
	 * @param style
	 */
	public CompatibleEcoComponentsComposite(Composite parent, Shell shell) {
		super(parent, shell);	
		createControls(this);
		init();
	}

	/**
	 * DOC chuang Comment method "createControls".
	 */
	private void createControls(Composite parent) {
		setLayout(clearGridLayoutSpace(new GridLayout(4, false)));

		Label filterLabel = new Label(parent, SWT.NONE);
		filterLabel.setText(EcosystemConstants.FILTER_LABEL_TEXT);
		filterLabel.setLayoutData(new GridData());

		fFilterText = new Text(parent, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gd.widthHint = 200;
		fFilterText.setLayoutData(gd);
		fFilterText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == EcosystemConstants.KEY_ENTER) {
					// press enter key
					applyFilters();
				}
			}

		});

		final Link filterLink = new Link(parent, SWT.NONE);
		filterLink.setText("<a href=\"\">"
				+ EcosystemConstants.FILTER_LINK_TEXT + "</a>");
		filterLink.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				openFilterDialog();
			}
		});

		creatTosVersionFilter(parent);

		Composite tableComposite = new Composite(this, SWT.NONE);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 4, 1));
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

		// install / update / remove actions, see 0005053: [ecosystem view]
		// update and remove components
		int width = ICON_WIDTH;
		if (WindowSystem.isGTK()) {
			// fix button icon size in linux
			width = 32;
		}
		createActionColumn(width + 6);

		// Status | Component Name | Author | Revision | Released Date |
		// Description
		// TableViewerCreatorColumn<ComponentExtension, String> statusColumn =
		// createTableColumn(EcosystemConstants.STATUS_TITLE,
		// true, false, 4, STATUS_ACCESSOR);
		// statusColumn.setImageProvider(new StatusImageProvider());

		fNameColumn = createTableColumn(
				EcosystemConstants.COMPONENT_NAME_TITLE, true, false, 100,
				NAME_ACCESSOR);
		createTableColumn(EcosystemConstants.AUTHOR_TITLE, true, false, 70,
				AUTHOR_ACCESSOR); // authorColumn
		// latest revision column
		createTableColumn(EcosystemConstants.LATEST_REVISION_TITLE, true,
				false, 110, LATEST_REVISION_ACCESSOR);
		// install revision column
		// createTableColumn(EcosystemConstants.INSTALLED_REVISION_TITLE, true,
		// false, 110, INSTALLED_REVISION_ACCESSOR);
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

	/**
	 * DOC chuang Comment method "onVersionFilterChanged".
	 * 
	 * @param e
	 */
	@Override
	protected void onVersionFilterChanged(SelectionEvent e) {
		Combo comboControl = (Combo) e.getSource();
		String value = comboControl.getText();
		IPreferenceStore preferenceStore = EcosystemPlugin.getDefault()
				.getPreferenceStore();
		preferenceStore.setValue(EcosystemView.TOS_VERSION_FILTER, value);

		getRefreshComponentsAction().run();
	}
	
	/**
	 * bqian Comment method "getRefreshComponentsAction".
	 */
	private IAction getRefreshComponentsAction() {
		IAction action = (IAction) versionCombo.getData(REFRESH_COMPONENTS);
		if (action != null) {
			return action;
		}

		action = EcosystemUtils.findViewAction(RefreshComponenentsAction.ID);

		if (action == null) {
			throw new IllegalStateException(
					"RefreshComponenentsAction not found, errors in the plugin.xml");
		}

		versionCombo.setData(REFRESH_COMPONENTS, action);
		action.addPropertyChangeListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				if (!event.getProperty().endsWith(IAction.ENABLED)) {
					return;
				}
				Boolean enable = (Boolean) event.getNewValue();
				versionCombo.setEnabled(enable);
			}
		});
		return action;

	}

	/**
	 * Initialize the contents that will display on this view.
	 */
	private void init() {
		// load installed component information from file
		loadFromFile();
		findAvailableComponentExtensions();
		refresh();
	}

	/**
	 * Search components according to the user input and selected filters.
	 */
	private void applyFilters() {
		if (ArrayUtils.isEmpty(fFilters)
				|| StringUtils.isEmpty(fFilterText.getText())) {
			updateTable(fAvailableExtensions);
			return;
		}
		int[] featureId = new int[fFilters.length];
		for (int i = 0; i < fFilters.length; i++) {
			featureId[i] = FILTER_MAP.get(fFilters[i]);
		}
		List<ComponentExtension> found;
		try {
			found = ComponentSearcher.filterComponentExtensions(
					fAvailableExtensions, fFilterText.getText(), featureId);
			updateTable(found);
		} catch (Exception e) {
			ExceptionHandler.process(e);
		}
	}	

	/**
	 * Open a dialog for user to select filters.
	 */
	protected void openFilterDialog() {
		FilterDialog dialog = new FilterDialog(shell, AVAILABLE_FILTERS,
				fFilters);
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			fFilters = new String[result.length];
			for (int i = 0; i < result.length; i++) {
				fFilters[i] = result[i].toString();
			}
		}
	}

	private void findAvailableComponentExtensions() {
		final RefreshJob job = new RefreshJob();
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(
					new IRunnableWithProgress() {

						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							job.run(monitor);
						}

					});
		} catch (Exception e) {
			ExceptionHandler.process(e);
		}

		fAvailableExtensions = job.getAvailableExtensions();
		// update status of installed extensions
		checkInstalledExtensions();
		initTable(fAvailableExtensions);
	}

	public void updateAvailableExtensions(List<ComponentExtension> extensions) {
		fAvailableExtensions = extensions;
		// update status of installed extensions
		checkInstalledExtensions();
		updateTable(fAvailableExtensions);
	}	

	public void removeInstalledExtension(ComponentExtension extension) {
		fInstalledExtensions.remove(extension.getName());
		
		extension.setInstalledLocation(null);
		extension.setInstalledRevision(null);
		fAvailableExtensions.add(extension);		
	}
	
	@Override
	public void addInstalledExtension(ComponentExtension extension) {
		fInstalledExtensions.put(extension.getName(), extension);
		
		for (int i = 0; i < fAvailableExtensions.size(); i++) {
			if (fAvailableExtensions.get(i).getName().equals(
					extension.getName())) {
				fAvailableExtensions.remove(i);
				break;
			}
		}
	}

	/**
	 * Update the component status if we have already installed.
	 */
	private void checkInstalledExtensions() {
		if (fInstalledExtensions == null || fAvailableExtensions == null) {
			return;
		}

		boolean modify = false;
		List<ComponentExtension> compatible = new ArrayList<ComponentExtension>();
		for (ComponentExtension available : fAvailableExtensions) {
			ComponentExtension installed = fInstalledExtensions.get(available
					.getName());
			if (installed == null) {
				compatible.add(available);
			} else {
				// add new revision for an install extension
				if (installed.getRevisions().size() < available.getRevisions()
						.size()) {
					Set<Integer> idList = new HashSet<Integer>();
					for (Revision rev : installed.getRevisions()) {
						idList.add(rev.getId());
					}

					for (Revision rev : available.getRevisions()) {
						if (!idList.contains(rev.getId())) {
							installed.getRevisions().add(rev);
						}
					}
					modify = true;
				}
			}
		}
		// if (modify) {
			saveToFile();
	// }
		fAvailableExtensions = compatible;
	}

	/**
	 * 
	 * update the content of table.
	 * 
	 * @param extensions
	 */
	public void updateTable(List<ComponentExtension> extensions) {
		fTableViewerCreator.setInputList(extensions);
		fTableViewerCreator.setSort(fNameColumn, SORT.ASC);

		addButtons();
	}

	
	@Override
	void addButtons() {
		fTableViewerCreator.getTableViewer().getControl().setRedraw(false);
		addInstallButtons();
		fTableViewerCreator.getTableViewer().getTable().layout();
		fTableViewerCreator.getTableViewer().refresh(true);

		fTableViewerCreator.getTableViewer().getControl().setRedraw(true);
	}	

	/**
	 * 
	 * DOC YeXiaowei Comment method "addInstallButtonToFirstColumn".
	 */
	private void addInstallButtons() {
		disposeEditors(installEditors);
		final Table table = fTableViewerCreator.getTable();
		for (final TableItem item : table.getItems()) {
			TableEditor editor = new TableEditor(table);
			installEditors.add(editor);
			final Button button = new Button(table, SWT.FLAT);
			button.setImage(StatusImageProvider
					.getInstallImage((ComponentExtension) item.getData()));
			button.setToolTipText("Install Component");
			button.setData(item);
			if (button.getImage() == StatusImageProvider.INSTALL_ICON) {
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
			editor.setEditor(button, item, INSTALL_BUTTON_COLUMN);
			editor.layout();
		}
	}	

	public void refresh() {
		fTableViewerCreator.setInputList(fAvailableExtensions);
		fTableViewerCreator.getTableViewer().refresh();
		// saveToFile();
		addButtons();
	}

	public List<ComponentExtension> getSelectedExtensions() {
		return getExtensionsFromTableItems(fTableViewerCreator.getTable()
				.getSelection());
	}
	
	 /**
	 * Get selected components from table items.
	 * 
	 * @return
	 */
	private List<ComponentExtension> getExtensionsFromTableItems(
			TableItem[] items) {
		List<ComponentExtension> selected = new ArrayList<ComponentExtension>();
		for (TableItem item : items) {
			selected.add((ComponentExtension) item.getData());
		}
		return selected;
	}	
}
