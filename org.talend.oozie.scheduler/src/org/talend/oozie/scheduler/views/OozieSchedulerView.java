package org.talend.oozie.scheduler.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.ViewPart;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.Element;
import org.talend.core.properties.tab.HorizontalTabFactory;
import org.talend.core.properties.tab.IDynamicProperty;
import org.talend.core.properties.tab.TalendPropertyTabDescriptor;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.views.properties.EElementType;
import org.talend.designer.runprocess.RunProcessContext;
import org.talend.designer.runprocess.RunProcessPlugin;
import org.talend.designer.runprocess.ui.ProcessContextComposite;
import org.talend.oozie.scheduler.i18n.Messages;
import org.talend.oozie.scheduler.ui.ExecuteJobComposite;
import org.talend.oozie.scheduler.ui.OozieMonitoringComposite;

public class OozieSchedulerView extends ViewPart {

    private HorizontalTabFactory tabFactory;

    private TalendPropertyTabDescriptor currentSelectedTab;

    private boolean selectedPrimary = true;

    private ExecuteJobComposite executeJobComposite;

    private OozieMonitoringComposite monitoringComposite;

    private IDynamicProperty dc = null;

    private AbstractMultiPageTalendEditor part;

    private SashForm sash;

    private Composite parent;

    private Button moveButton;

    private ProcessContextComposite contextComposite;

    public OozieSchedulerView() {
        tabFactory = new HorizontalTabFactory();
    }

    @Override
    public void createPartControl(Composite parent) {
        this.parent = parent;
        parent.setLayout(new FillLayout());

        sash = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
        sash.setLayoutData(new GridData(GridData.FILL_BOTH));
        sash.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        GridLayout layout = new GridLayout();
        sash.setLayout(layout);

        createLeftContents(sash);
        createRightContents(sash);

        tabFactory.getTabComposite().layout();
        tabFactory.getTabbedPropertyComposite().getComposite().dispose();
        tabFactory.getTabbedPropertyComposite().pack();
        tabFactory.addSelectionChangedListener(new ISelectionChangedListener() {

            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                TalendPropertyTabDescriptor descriptor = (TalendPropertyTabDescriptor) selection.getFirstElement();

                if (descriptor == null) {
                    return;
                }

                if (currentSelectedTab != null && (currentSelectedTab.getCategory() != descriptor.getCategory())) {
                    for (Control curControl : tabFactory.getTabComposite().getChildren()) {
                        curControl.dispose();
                    }
                }

                if (currentSelectedTab == null || currentSelectedTab.getCategory() != descriptor.getCategory() || selectedPrimary) {

                    currentSelectedTab = descriptor;
                    createDynamicComposite(tabFactory.getTabComposite(), (Element) descriptor.getData(), descriptor.getCategory());
                    selectedPrimary = false;
                }
            }
        });
        setElement();

        // IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
        // IHandler handler1;
        // IBrandingService brandingService = (IBrandingService) GlobalServiceRegister.getDefault().getService(
        // IBrandingService.class);
    }

    protected void createLeftContents(Composite parent) {
        Composite left = new Composite(parent, SWT.NONE);
        left.setLayout(new FillLayout());

        tabFactory.initComposite(left, false);
    }

    protected void createCenterContents(Composite parent) {
        FormData layouDatag = new FormData();
        layouDatag.left = new FormAttachment(0, 0);
        layouDatag.width = 32;
        layouDatag.top = new FormAttachment(0, 0);
        layouDatag.bottom = new FormAttachment(100, 0);

        final Composite buttonComposite = new Composite(parent, SWT.ERROR);
        buttonComposite.setLayoutData(layouDatag);
        buttonComposite.setLayout(new GridLayout());

        moveButton = new Button(buttonComposite, SWT.PUSH);
        moveButton.setText(">>"); //$NON-NLS-1$
        moveButton.setToolTipText(Messages.getString("ProcessComposite.hideContext"));
        final GridData layoutData = new GridData();
        layoutData.verticalAlignment = GridData.CENTER;
        layoutData.horizontalAlignment = GridData.CENTER;
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.grabExcessVerticalSpace = true;
        moveButton.setLayoutData(layoutData);
        regMoveButtonListener();
    }

    protected void createRightContents(Composite parent) {
        Composite right = new Composite(sash, SWT.NONE);
        right.setLayout(new FormLayout());

        createCenterContents(right);

        sash.setSashWidth(5);
        sash.setWeights(new int[] { 18, 5 });

        Composite cotextCom = new Composite(right, SWT.NONE);
        FormData layouDatag = new FormData();
        layouDatag.left = new FormAttachment(0, 32);
        layouDatag.right = new FormAttachment(100, 0);
        layouDatag.top = new FormAttachment(0, 0);
        layouDatag.bottom = new FormAttachment(100, 0);
        cotextCom.setLayoutData(layouDatag);
        cotextCom.setLayout(new GridLayout());
        contextComposite = new ProcessContextComposite(cotextCom, SWT.NONE);
        contextComposite.setBackground(right.getDisplay().getSystemColor(SWT.COLOR_WHITE));
    }

    private void regMoveButtonListener() {
        moveButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(final SelectionEvent e) {
                if (moveButton.getText().equals(">>")) { //$NON-NLS-1$
                    sash.setWeights(new int[] { 23, 1 });
                    moveButton.setToolTipText(Messages.getString("ProcessComposite.showContext"));
                    moveButton.setText("<<");
                } else if (moveButton.getText().equals("<<")) { //$NON-NLS-1$
                    sash.setWeights(new int[] { 18, 5 });
                    moveButton.setToolTipText(Messages.getString("ProcessComposite.hideContext"));//$NON-NLS-1$
                    moveButton.setText(">>");
                }
            }
        });
    }

    public void createDynamicComposite(Composite parent, Element element, EComponentCategory category) {
        // jcomposite = this.processViewHelper.getProcessComposite(parent.getShell());
        if (category == EComponentCategory.SCHEDULE_4_HADOOP_EXECUTE_JOB) {
            executeJobComposite = new ExecuteJobComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.NO_FOCUS);
            dc = executeJobComposite;
        } else if (category == EComponentCategory.SCHEDULE_4_HADOOP_MONITORING) {
            // debugTisProcessComposite = this.debugViewHelper.getDebugComposite(parent);
            // dc = debugTisProcessComposite;
            monitoringComposite = new OozieMonitoringComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
            dc = monitoringComposite;
        }
        refresh();
        if (dc != null) {
            dc.refresh();
        }
    }

    public void refresh() {
        getPart();
        if (part != null) {
            executeJobComposite.setMultiPageTalendEditor(part);

        } else {
            tabFactory.setTitle(Messages.getString("Title_name"), null);

        }
        // setPartName("PartName");
        // tabFactory.setTitle("Hello Marvin", null);
    }

    private void getPart() {
        final IEditorPart activeEditor = getSite().getPage().getActiveEditor();
        if (activeEditor instanceof AbstractMultiPageTalendEditor) {
            part = (AbstractMultiPageTalendEditor) activeEditor;
        } else {
            part = null;
        }
    }

    public void refresh11() {
        RunProcessContext activeContext = RunProcessPlugin.getDefault().getRunProcessContextManager().getActiveContext();
        boolean disableAll = false;
        if (activeContext != null) {
            disableAll = activeContext.getProcess().disableRunJobView();
        }
        // this.processContext = activeContext;
        // rubjobManager.setProcessContext(processContext);
        // if (contextComposite.isDisposed()) {
        // return;
        // }
        // contextComposite.setProcess(((activeContext != null) && !disableAll ? activeContext.getProcess() : null));
        // clearPerfAction.setProcess(activeContext != null ? activeContext.getProcess() : null);
        // if (dc == processComposite) {
        // processComposite.setProcessContext(activeContext);
        // }
        // rubjobManager.setSelectContext(contextComposite.getSelectedContext());
        // if (dc == debugTisProcessComposite) {
        // debugTisProcessComposite.setProcessContext(activeContext);
        // debugTisProcessComposite.setContextComposite(this.contextComposite);
        // }
        // if (dc == advanceComposite) {
        // advanceComposite.setProcessContext(activeContext);
        // }
        // if (dc == targetComposite)
        // targetComposite.setProcessContext(activeContext);
        if (activeContext != null) {
            String jobName = Messages.getString("ProcessView.jobName"); //$NON-NLS-1$
            if (activeContext.getProcess().disableRunJobView()) { // ?? joblet
                jobName = "Joblet"; //$NON-NLS-1$
            }
            jobName = jobName + " " + activeContext.getProcess().getLabel(); //$NON-NLS-1$
            setTitleToolTip(jobName);
            setPartName(Messages.getString("ProcessView.title", jobName)); //$NON-NLS-1$
            // processNameLab.setText(jobName);
            tabFactory.setTitle(jobName, null);
        } else {
            setPartName(Messages.getString("ProcessView.titleEmpty")); //$NON-NLS-1$
            //processNameLab.setText(Messages.getString("ProcessView.subtitleEmpty")); //$NON-NLS-1$
            tabFactory.setTitle(Messages.getString("ProcessView.subtitleEmpty"), null);
        }

        // processNameLab.getParent().layout(true, true);
    }

    private void setElement() {
        EComponentCategory[] categories = getCategories();
        final List<TalendPropertyTabDescriptor> descriptors = new ArrayList<TalendPropertyTabDescriptor>();
        for (EComponentCategory category : categories) {
            TalendPropertyTabDescriptor d = new TalendPropertyTabDescriptor(category);
            // d.setData(data);
            descriptors.add(d);
        }
        tabFactory.setInput(descriptors);
        tabFactory.setSelection(new IStructuredSelection() {

            public Object getFirstElement() {
                return null;
            }

            public Iterator iterator() {
                return null;
            }

            public int size() {
                return 0;
            }

            public Object[] toArray() {
                return null;
            }

            public List toList() {
                List<TalendPropertyTabDescriptor> d = new ArrayList<TalendPropertyTabDescriptor>();

                if (descriptors.size() > 0) {
                    if (currentSelectedTab != null) {
                        for (TalendPropertyTabDescriptor ds : descriptors) {
                            if (ds.getCategory() == currentSelectedTab.getCategory()) {
                                d.add(ds);
                                return d;
                            }
                        }
                    }
                    d.add(descriptors.get(0));
                }
                return d;
            }

            public boolean isEmpty() {
                return false;
            }

        });
    }

    private EComponentCategory[] getCategories() {
        EComponentCategory[] categories = EElementType.SCHEDULE_4_HADOOP.getCategories();
        final List<EComponentCategory> list = new ArrayList<EComponentCategory>(Arrays.asList(categories));
        return categories;
    }

    @Override
    public void setFocus() {

    }
}
