// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.ui;

import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.Element;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.properties.tab.IDynamicProperty;
import org.talend.designer.core.IMultiPageTalendEditor;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.oozie.scheduler.controller.ExecuteJobCompositeController;
import org.talend.oozie.scheduler.i18n.Messages;
import org.talend.oozie.scheduler.utils.EOozieSchedulerImages;

/**
 * Created by Marvin Wang on Mar. 30, 2012 for Execute Job tab composite.
 */
public class ExecuteJobComposite extends ScrolledComposite implements IDynamicProperty {

    private Button scheduleBtn;// Schedule button

    private Button runBtn;// Run button

    private Button killBtn;// Kill button

    private Button settingBtn;// Setup button

    private Text pathText;// Path text

    private Text outputTxt;// Output logs/status

    private ExecuteJobCompositeController executeJobCompController;

    private AbstractMultiPageTalendEditor multiPageTalendEditor;

    /**
     * 
     * @param parent
     * @param style
     */
    public ExecuteJobComposite(Composite parent, int style) {
        super(parent, style);
        parent.setLayout(new FillLayout());
        setExpandHorizontal(true);
        setExpandVertical(true);
        this.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
        Composite comp = new Composite(this, SWT.NONE);
        setContent(comp);

        executeJobCompController = new ExecuteJobCompositeController(this);
        createExecuteJobAreas(comp);
        this.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    protected void createExecuteJobAreas(Composite parent) {
        GridLayout gridLayout = new GridLayout(4, false);
        parent.setLayout(gridLayout);
        createButtons(parent);
        createCenterContents(parent);
        createBottomContents(parent);
    }

    /**
     * Create all button components including Schedul Button, Run Button, Kill Button and Setting Button.
     * 
     * @param parent
     */
    private void createButtons(Composite parent) {
        // Schedule button
        scheduleBtn = new Button(parent, SWT.NONE);
        scheduleBtn.setText(Messages.getString("Button_Schedule"));
        scheduleBtn.setImage(ImageProvider.getImage(EOozieSchedulerImages.IMG_SCHEDULE));
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).hint(computeBtnTxtSize(scheduleBtn).x + 50, 30)
                .applyTo(scheduleBtn);

        // Run button
        runBtn = new Button(parent, SWT.NONE);
        runBtn.setText(Messages.getString("Button_Run"));
        runBtn.setImage(ImageProvider.getImage(EOozieSchedulerImages.IMG_RUN));
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).hint(computeBtnTxtSize(runBtn).x + 70, 30)
                .applyTo(runBtn);

        // Kill button
        killBtn = new Button(parent, SWT.NONE);
        killBtn.setText(Messages.getString("Button_Kill"));
        killBtn.setImage(ImageProvider.getImage(EOozieSchedulerImages.IMG_KILL));
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).hint(computeBtnTxtSize(killBtn).x + 70, 30)
                .align(SWT.BEGINNING, SWT.CENTER).applyTo(killBtn);

        // Setting button
        settingBtn = new Button(parent, SWT.PUSH);
        settingBtn.setText(Messages.getString("Button_Setting"));
        GridDataFactory.fillDefaults().grab(false, false).indent(50, 10).hint(computeBtnTxtSize(settingBtn).x + 50, 30)
                .align(SWT.END, SWT.CENTER).applyTo(settingBtn);

        registerBtnListeners();
        checkBtnValid();
    }

    private void createCenterContents(Composite parent) {
        Label pathLbl = new Label(parent, SWT.NONE);
        pathLbl.setText(Messages.getString("Label_Path"));
        GridDataFactory.fillDefaults().grab(false, false).indent(SWT.DEFAULT, 10).align(SWT.BEGINNING, SWT.CENTER)
                .applyTo(pathLbl);

        pathText = new Text(parent, SWT.BORDER);
        pathText.setText(getPathValue() == null ? "" : getPathValue());
        GridDataFactory.fillDefaults().span(2, 1).grab(true, false).indent(-50, 10).applyTo(pathText);

        regPathTextListener();
    }

    private void createBottomContents(Composite parent) {
        outputTxt = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        GridDataFactory.fillDefaults().span(4, 2).indent(SWT.DEFAULT, 10).grab(true, true).applyTo(outputTxt);
    }

    /**
     * Registers the listener for all buttons.
     */
    protected void registerBtnListeners() {
        regScheduleBtnListener();
        regRunBtnListener();
        regKillBtnListener();
        regSettingBtnListener();
    }

    protected void checkBtnValid() {
        checkScheduleBtnValid();
        checkRunBtnValid();
        checkKillBtnValid();
    }

    protected void checkScheduleBtnValid() {
        if (scheduleBtn.isDisposed()) {
            return;
        }
        if (multiPageTalendEditor == null) {
            scheduleBtn.setEnabled(false);
        } else {
            boolean valid = executeJobCompController.isRunBtnValid();
            scheduleBtn.setEnabled(valid);
        }
    }

    protected void checkRunBtnValid() {
        if (runBtn.isDisposed()) {
            return;
        }
        if (multiPageTalendEditor == null) {
            runBtn.setEnabled(false);
        } else {
            boolean valid = executeJobCompController.isRunBtnValid();
            runBtn.setEnabled(valid);
        }
    }

    protected void checkKillBtnValid() {
        if (killBtn.isDisposed()) {
            return;
        }
        if (multiPageTalendEditor == null) {
            killBtn.setEnabled(false);
        }
    }

    protected void regScheduleBtnListener() {
        scheduleBtn.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doScheduleAction();
            }
        });
    }

    protected void regRunBtnListener() {
        runBtn.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doRunAction();
            }
        });
    }

    protected void regKillBtnListener() {
        killBtn.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doKillAction();
            }
        });
    }

    protected void regSettingBtnListener() {
        settingBtn.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                executeJobCompController.doSettingAction();
            }
        });

    }

    /**
     * Registers a listener for Path text.
     */
    protected void regPathTextListener() {
        pathText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                setPathValue(pathText.getText());
                checkRunBtnValid();
                checkScheduleBtnValid();
                executeJobCompController.doModifyPathAction();
            }
        });
    }

    private Point computeBtnTxtSize(Button btn) {
        GC gc = new GC(btn.getDisplay());
        final Point p = gc.textExtent(btn.getText());
        gc.dispose();
        return p;
    }

    private Point computeTextTxtSize(Label label) {
        GC gc = new GC(label.getDisplay());
        final Point p = gc.textExtent(label.getText());
        gc.dispose();
        return p;
    }

    public String getPathValue() {
        if (multiPageTalendEditor != null) {
            IProcess2 process = multiPageTalendEditor.getProcess();
            String appPath = (String) process.getElementParameter("HADOOP_APP_PATH").getValue();
            return appPath;
        }
        return "";
    }
    
    public CommandStack getCommandStack() {
        return multiPageTalendEditor == null ? null : (CommandStack) (multiPageTalendEditor.getTalendEditor().getAdapter(CommandStack.class));
    }

    public void setPathValue(String pathValue) {
        if (multiPageTalendEditor != null && !pathValue.equals(getPathValue())) {
            IProcess2 process = multiPageTalendEditor.getProcess();
            getCommandStack().execute(new PropertyChangeCommand(process,"HADOOP_APP_PATH", pathValue));
        }
    }

    public Button getRunBtn() {
        return this.runBtn;
    }

    public void setRunBtn(Button runBtn) {
        this.runBtn = runBtn;
    }

    public Button getKillBtn() {
        return this.killBtn;
    }

    public void setKillBtn(Button killBtn) {
        this.killBtn = killBtn;
    }

    public Text getOutputTxt() {
        return this.outputTxt;
    }

    public void setOutputTxt(Text outputTxt) {
        this.outputTxt = outputTxt;
    }

    public void setMultiPageTalendEditor(AbstractMultiPageTalendEditor multiPageTalendEditor) {
        this.multiPageTalendEditor = multiPageTalendEditor;
        executeJobCompController.setMultiPageTalendEditor(multiPageTalendEditor);
        checkScheduleBtnValid();
        checkRunBtnValid();
        checkKillBtnValid();
        if (multiPageTalendEditor != null) {
            pathText.setEnabled(true);
            pathText.setText(getPathValue());
            outputTxt.setEnabled(true);
        } else {
            pathText.setEnabled(false);
            pathText.setText("");
            outputTxt.setText("");
            outputTxt.setEnabled(false);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getHashCurControls()
     */
    @Override
    public BidiMap getHashCurControls() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getElement()
     */
    @Override
    public Element getElement() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getPart()
     */
    @Override
    public IMultiPageTalendEditor getPart() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getSection()
     */
    @Override
    public EComponentCategory getSection() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getComposite()
     */
    @Override
    public Composite getComposite() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#setCurRowSize(int)
     */
    @Override
    public void setCurRowSize(int i) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getCurRowSize()
     */
    @Override
    public int getCurRowSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getTableIdAndDbTypeMap()
     */
    @Override
    public Map<String, String> getTableIdAndDbTypeMap() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#getTableIdAndDbSchemaMap()
     */
    @Override
    public Map<String, String> getTableIdAndDbSchemaMap() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IDynamicProperty#refresh()
     */
    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.properties.tab.IDynamicProperty#getRepositoryAliasName(org.talend.core.properties.tab.ConnectionItem
     * )
     */
    @Override
    public String getRepositoryAliasName(ConnectionItem connectionItem) {
        return null;
    }

    public AbstractMultiPageTalendEditor getMultiPageTalendEditor() {
        return this.multiPageTalendEditor;
    }

    public ExecuteJobCompositeController getExecuteJobCompController() {
        return this.executeJobCompController;
    }

    public void setExecuteJobCompController(ExecuteJobCompositeController executeJobCompController) {
        this.executeJobCompController = executeJobCompController;
    }

}
