// ============================================================================
//
// Copyright (C) 2006-2008 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.rules.wizard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.drools.eclipse.DroolsEclipsePlugin;
import org.drools.eclipse.wizard.rule.DRLGenerator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.osgi.framework.Bundle;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.exception.SystemException;
import org.talend.core.model.properties.ByteArray;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.RulesItem;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.ProxyRepositoryFactory;
import org.talend.repository.rules.RulesPlugin;
import org.talend.repository.rules.editor.RuleEditor;
import org.talend.repository.rules.editor.RuleEditorInput;


/**
 * DOC hyWang class global comment. Detailled comment
 */
public class NewRulesWizardPage2 extends ReuseWizardFileCreationPage {

    private static final int TYPE_RULE = 1;

    private static final int TYPE_PACKAGE = 0;

    private IWorkbench workbench;

    private Combo ruleFileType;

    private Button expander;

    private Button function;

    private Text packageName;

    private Property property;

    private RulesItem ruleItem;

    private IPath destinationPath;

    private String extension;

    public IPath getDestinationPath() {
        return this.destinationPath;
    }

    public NewRulesWizardPage2(IWorkbench workbench, IStructuredSelection selection, IPath destinationPath, Property property,
            RulesItem ruleItem) {
        super("createDRLFilePage", selection, ruleItem);
        setTitle("New Rules File");
        setDescription("Create a new rules file (drl)");
        this.workbench = workbench;
        this.destinationPath = destinationPath;
        this.property = property;
        this.ruleItem = ruleItem;
    }

    protected void createAdvancedControls(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        final GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        container.setLayout(layout);
        setControl(container);

        // setup the controls.
        createType(container);
        createDSL(container);
        createFunctions(container);
        createPackageName(container);
        super.createAdvancedControls(parent);
    }

    protected boolean validatePage() {
        // return super.validatePage() && validate();
        return true;
    }

    private void createPackageName(Composite container) {
        // package name
        Label pack = new Label(container, SWT.NONE);
        pack.setText("Rule package name:");
        pack.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        pack.setFont(this.getFont());
        packageName = new Text(container, SWT.BORDER);
        packageName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        packageName.setToolTipText("Rules require a namespace.");
        packageName.setFont(this.getFont());
        packageName.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });
    }

    private void createFunctions(Composite container) {
        // function
        Label func = new Label(container, SWT.NONE);
        func.setText("Use functions:");
        func.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        func.setFont(this.getFont());
        function = new Button(container, SWT.CHECK);
        function.setSelection(false);
        function.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
        function.setToolTipText("Functions are methods you embed in your rule source.");
    }

    private void createDSL(Composite container) {
        // expander
        Label exp = new Label(container, SWT.NONE);
        exp.setText("Use a DSL:");
        exp.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        exp.setFont(this.getFont());
        expander = new Button(container, SWT.CHECK);
        expander.setSelection(false);
        expander.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
        expander
                .setToolTipText("Domain Specific Language: allows you to create your own domain specific languages\n for use in rules.");
    }

    private void createType(Composite container) {
        // type
        Label type = new Label(container, SWT.NONE);
        type.setText("Type of rule resource:");
        type.setFont(this.getFont());
        type.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        ruleFileType = new Combo(container, SWT.READ_ONLY);
        ruleFileType.add("New DRL (rule package)", TYPE_PACKAGE);
        ruleFileType.add("New Rule (individual rule)", TYPE_RULE);
        ruleFileType.select(0);
        ruleFileType.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        ruleFileType.setFont(this.getFont());

    }

    public boolean finish() {
        if (!validate()) {
            return false;
        }
        // String fileName = getFileName();
        extension = expander.getSelection() ? ".dslr" : ".drl";
        // if (!fileName.endsWith(extension)) {
        // setFileName(fileName + extension);
        // }

        try {
            IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
            org.eclipse.ui.IWorkbenchPage page = dwindow.getActivePage();
            if (page != null) {
                StringBuffer buffer = new StringBuffer();
                InputStream in = this.getInitialContents();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
                String content = "";

                while ((content = bufferReader.readLine()) != null) {
                    buffer.append(content + "\n");
                }
                content = buffer.toString();
                createItem(content);
                org.eclipse.core.resources.IFile newFile = createNewFile(extension);
                IEditorInput input = new RuleEditorInput(newFile, ruleItem);
                IEditorPart findEditor = page.findEditor(input);
                if (findEditor == null) {
                    findEditor = page.openEditor(input, RuleEditor.ID);
                }
                // IDE.openEditor(page, newFile, true);
            }
        } catch (IOException e) {
            ExceptionHandler.process(e);
            return false;
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
            return false;
        } catch (PartInitException e) {
            ExceptionHandler.process(e);
            return false;
        } catch (SystemException e) {
            ExceptionHandler.process(e);
            return false;
        }
        return true;
    }

    private boolean validate() {
        if (this.packageName.getText() == null || packageName.getText().equals("")) {
            setErrorMessage("You must provide a rule package name");
            return false;
        } else {
            return true;
        }
    }

    protected InputStream getInitialContents() {
        try {
            DRLGenerator gen = new DRLGenerator();
            if (this.ruleFileType.getSelectionIndex() == TYPE_RULE) {
                InputStream template = getTemplate("/resources/new_rule.drl.template");
                return gen.generateRule(this.packageName.getText(), template);
            } else {
                InputStream template = getTemplate("/resources/new_package.drl.template");
                return gen
                        .generatePackage(this.packageName.getText(), function.getSelection(), expander.getSelection(), template);
            }
        } catch (IOException e) {
            DroolsEclipsePlugin.log(e);
            return null;
        } catch (NullPointerException e) {
            DroolsEclipsePlugin.log(e);
            return null;
        }
    }

    private InputStream getTemplate(String templatePath) throws IOException {
        Bundle b = Platform.getBundle(RulesPlugin.PLUGIN_ID);
        URL resource = b.getResource(templatePath);
        return resource.openStream();
    }

    private void createItem(String content) throws PersistenceException {
        byte[] byteContent = content.getBytes();
        ByteArray byteArray = PropertiesFactory.eINSTANCE.createByteArray();
        byteArray.setInnerContent(byteContent);
        IProxyRepositoryFactory repositoryFactory = ProxyRepositoryFactory.getInstance();
        property.setId(repositoryFactory.getNextId());
        ruleItem.setContent(byteArray);
        ruleItem.setProperty(property);
        ruleItem.setExtension(extension);
        repositoryFactory.create(ruleItem, this.getDestinationPath());
    }
}
