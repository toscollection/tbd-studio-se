package org.talend.internal.utilities.actions;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.designer.core.ui.MultiPageTalendEditor;
import org.talend.designer.core.ui.editor.ProcessEditorInput;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.views.properties.ComponentSettingsView;
import org.talend.designer.core.ui.views.properties.MultipleThreadDynamicComposite;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.IRepositoryNode.ENodeType;
import org.talend.repository.model.IRepositoryNode.EProperties;
import org.talend.repository.ui.views.IRepositoryView;
import org.talend.repository.ui.views.RepositoryView;

/**
 * Our sample action implements workbench action delegate. The action proxy will be created by the workbench and shown
 * in the UI. When the user tries to use the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class BatchGenJobScreenshotAction extends Action {

    private boolean debug = false;

    private void trace(String s) {
        if (debug) {
            System.out.println(s);
        }
    }

    /**
     * 
     */

    private static final int TIME_DELAY = 400;

    private IPath destination = new Path("c:/"); //$NON-NLS-1$

    private Point mouseLocationBackup;

    /**
     * The constructor.
     */
    public BatchGenJobScreenshotAction() {
        super();
        this.setActionDefinitionId("batch.generate.job.screenshot"); //$NON-NLS-1$
        destination = destination.append("Screenshots").append("ProjectName").append("ComponentSettings").append("BasicSettings"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    /**
     * The action has been activated. The argument of the method represents the 'real' action sitting in the workbench
     * UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run() {
        try {
            execute();
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }

        // takeSnapForComponentSettingView("testpicName");
    }

    /**
     * bqian Comment method "execute".
     */
    private void execute() throws Exception {
        if (!askForDestination()) {
            return;
        }
        trace("Move the mouse outside Component Setting View"); //$NON-NLS-1$
        moveMouse();

        trace("close all editors"); //$NON-NLS-1$
        closeAllEditor();
        trace("show component setting view"); //$NON-NLS-1$
        showComponentSettingView();
        trace("get all jobs"); //$NON-NLS-1$
        final List<IRepositoryViewObject> jobs = getAllJobs();

        Thread t = new Thread() {

            public void run() {
                for (IRepositoryViewObject repositoryObject : jobs) {
                    takeScreenShotForJobComponent(repositoryObject);
                }

                Display.getDefault().syncExec(new Runnable() {

                    public void run() {
                        restoreMousePosition();
                        String msg = "Operation is successful, all the screenshots are stored in the directory <a>" //$NON-NLS-1$
                                + destination.toPortableString() + "</a>."; //$NON-NLS-1$
                        ResultrDialog d = new ResultrDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), msg);
                        d.open();
                    }
                });
            }
        };
        t.start();
    }

    /**
     * DOC bqian Comment method "moveMouse".
     */
    private void moveMouse() {
        try {
            mouseLocationBackup = Display.getCurrent().getCursorLocation();
            ComponentSettingsView view = (ComponentSettingsView) getActivePage().showView(ComponentSettingsView.ID);
            Point p = view.getParent().toDisplay(view.getParent().getLocation());
            Robot r = new Robot();
            r.mouseMove(p.x - 50, p.y - 50);
        } catch (Exception e) {
        }

    }

    private void restoreMousePosition() {
        try {
            Robot r = new Robot();
            r.mouseMove(mouseLocationBackup.x, mouseLocationBackup.y);
        } catch (Exception e) {
        }
    }

    /**
     * bqian Comment method "showComponentSettingView".
     */
    private void showComponentSettingView() {
        try {
            getActivePage().showView(ComponentSettingsView.ID);
        } catch (Exception e) {
        }
    }

    private boolean askForDestination() {
        DirectoryInputDialog d = new DirectoryInputDialog(null, "Select Directory", //$NON-NLS-1$
                "Please select the directory to save the screenshots."); //$NON-NLS-1$
        if (IDialogConstants.OK_ID == d.open()) {
            String result = d.getResult();
            destination = new Path(result);
            RepositoryContext repositoryContext = (RepositoryContext) CorePlugin.getContext().getProperty(
                    Context.REPOSITORY_CONTEXT_KEY);
            String projectName = repositoryContext.getProject().getLabel();
            // "Screenshots/<technical_project_name>/ComponentSettings/<folders?>/<jobName>_<componentUniqueName>.png"
            destination = destination.append("Screenshots").append(projectName).append("ComponentSettings"); //$NON-NLS-1$ //$NON-NLS-2$
            return true;
        }
        return false;
    }

    private void takeSnapForComponentSettingView(String jobName, String picName, EComponentCategory category) {
        try {
            final Image image = createImage(category);
            saveImageToFile(image, destination.append(jobName), picName);
            image.dispose();
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    /**
     * DOC bqian Comment method "selectBasicTab".
     */
    private void selectBasicTab() {
        try {
            ComponentSettingsView view = (ComponentSettingsView) getActivePage().showView(ComponentSettingsView.ID);
            view.selectTab(EComponentCategory.BASIC);

        } catch (Exception e) {
        }

    }

    private void selectadvancedTab() {
        try {
            ComponentSettingsView view = (ComponentSettingsView) getActivePage().showView(ComponentSettingsView.ID);
            view.selectTab(EComponentCategory.ADVANCED);

        } catch (Exception e) {
        }
    }

    /**
     * bqian Comment method "createImage".
     * 
     * @param category
     * 
     * @return
     * @throws PartInitException
     */
    private Image createImage(EComponentCategory category) throws PartInitException {
        ComponentSettingsView view = (ComponentSettingsView) getActivePage().showView(ComponentSettingsView.ID);
        view.selectTab(category);
        Composite container = view.getParent();
        Point tableSize = container.getSize();
        GC gc = new GC(container);
        final Image image = new Image(Display.getDefault(), tableSize.x, tableSize.y);
        gc.copyArea(image, 0, 0);
        gc.dispose();
        return image;
    }

    private void saveImageToFile(Image image, IPath directory, String picName) throws IOException {
        // save image to file
        ImageLoader il = new ImageLoader();
        il.data = new ImageData[] { image.getImageData() };
        File folder = directory.toFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        IPath path = directory.append(picName + ".png"); //$NON-NLS-1$
        il.save(path.toPortableString(), SWT.IMAGE_PNG);
    }

    /**
     * bqian Comment method "openEditor".
     * 
     * @param repositoryObject
     */
    private void takeScreenShotForJobComponent(IRepositoryViewObject repositoryObject) {
        final IRepositoryViewObject o1 = repositoryObject;
        final MultiPageTalendEditor[] editor = new MultiPageTalendEditor[1];

        trace("open job " + repositoryObject.getProperty().getLabel()); //$NON-NLS-1$
        Display.getDefault().syncExec(new Runnable() {

            public void run() {
                try {
                    trace("start open job "); //$NON-NLS-1$
                    editor[0] = openJob(o1);
                    trace(" open job over"); //$NON-NLS-1$
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        });

        // try {
        //
        // Thread.sleep(TIME_DELAY * 20);
        //
        // } catch (Exception e) {
        // ExceptionHandler.process(e);
        // }

        takeScreenshot(editor[0]);
        // close editor
        Display.getDefault().syncExec(new Runnable() {

            public void run() {
                getActivePage().closeEditor(editor[0], false);
            }
        });

    }

    /**
     * bqian Comment method "takeScreenshot".
     * 
     * @param editor
     */
    @SuppressWarnings("unchecked")
    private void takeScreenshot(final MultiPageTalendEditor editor) {
        final String jobName = editor.getProcess().getLabel();
        List<Node> nodes = getAllNodes(editor.getTalendEditor().getProcess());

        // save pic for each node's basic tab
        for (final Node node : nodes) {
            trace("select node " + node.getUniqueName()); //$NON-NLS-1$
            Display.getDefault().syncExec(new Runnable() {

                public void run() {
                    MultipleThreadDynamicComposite.isRefreshing = true;
                    select(editor, node);
                    selectBasicTab();
                }
            });

            do {
                try {
                    Thread.sleep(TIME_DELAY);
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            } while (MultipleThreadDynamicComposite.isRefreshing);

            Display.getDefault().syncExec(new Runnable() {

                public void run() {
                    String picName = node.getUniqueName() + "_" + "BasicSettings"; //$NON-NLS-1$ //$NON-NLS-2$
                    takeSnapForComponentSettingView(jobName, picName, EComponentCategory.BASIC);
                }
            });
        }

        // save pic for each node's advanced tab
        for (final Node node : nodes) {
            Display.getDefault().syncExec(new Runnable() {

                public void run() {
                    MultipleThreadDynamicComposite.isRefreshing = true;
                    select(editor, node);
                    selectadvancedTab();
                }
            });

            do {
                try {
                    Thread.sleep(TIME_DELAY);
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            } while (MultipleThreadDynamicComposite.isRefreshing);

            Display.getDefault().syncExec(new Runnable() {

                public void run() {
                    String picName = node.getUniqueName() + "_" + "AdvancedSettings"; //$NON-NLS-1$ //$NON-NLS-2$
                    takeSnapForComponentSettingView(jobName, picName, EComponentCategory.ADVANCED);
                }
            });
        }
    }

    /**
     * bqian Comment method "getAllNodes".
     * 
     * @param process
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Node> getAllNodes(IProcess2 process) {
        List<Node> list = new ArrayList<Node>();
        for (INode node : process.getGraphicalNodes()) {
            if (node instanceof Node) {
                list.add((Node) node);
            }
        }
        return list;
    }

    /**
     * bqian Comment method "select".
     * 
     * @param editor
     * @param element
     */
    private void select(MultiPageTalendEditor editor, Node element) {
        editor.selectNode(element);
    }

    /**
     * bqian Comment method "openJob".
     */
    private MultiPageTalendEditor openJob(IRepositoryViewObject object) throws Exception {
        IWorkbenchPage page = getActivePage();
        ProcessItem item = (ProcessItem) object.getProperty().getItem();

        RepositoryNode repositoryNode = new RepositoryNode(object, null, ENodeType.REPOSITORY_ELEMENT);
        ERepositoryObjectType itemType = ERepositoryObjectType.getItemType(item);
        repositoryNode.setProperties(EProperties.CONTENT_TYPE, itemType);

        final ProcessEditorInput fileEditorInput = new ProcessEditorInput(item, true);
        fileEditorInput.setView(getViewPart());
        fileEditorInput.setRepositoryNode(repositoryNode);
        MultiPageTalendEditor openEditor = (MultiPageTalendEditor) page.openEditor(fileEditorInput, FakeMultiPageTalendEditor.ID,
                true);
        return openEditor;
    }

    /**
     * bqian Comment method "getAllJobs".
     * 
     * @return
     */
    private List<IRepositoryViewObject> getAllJobs() {
        try {
            return CorePlugin.getDefault().getRepositoryService().getProxyRepositoryFactory().getAll(
                    ERepositoryObjectType.PROCESS, false);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 
     * Returns the repository view..
     * 
     * @return - the repository biew
     */
    public IRepositoryView getViewPart() {
        return RepositoryView.show();
    }

    /**
     * bqian Comment method "closeAllEditor".
     */
    private void closeAllEditor() {
        IEditorReference[] editors = getActivePage().getEditorReferences();
        if (editors == null || editors.length == 0) {
            return;
        }

        ActionFactory.CLOSE_ALL.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow()).run();
    }

    public IWorkbenchPage getActivePage() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }
}
