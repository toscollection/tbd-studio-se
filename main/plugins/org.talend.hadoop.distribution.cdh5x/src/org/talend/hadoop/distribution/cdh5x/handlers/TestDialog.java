// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh5x.handlers;

import java.io.File;
import java.io.FileOutputStream;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.runtime.dynamic.DynamicServiceUtil;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.DynamicTemplateAdapter;
import org.talend.hadoop.distribution.dynamic.adapter.TemplateBean;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class TestDialog extends Dialog {

    private Text templateText;

    private Text generatedText;

    private Text hadoopVersionText;

    private Text hadoopVersionDisplayText;

    private Button generateButton;

    protected TestDialog(Shell parentShell) {
        super(parentShell);

        // this.getShell().setText("Test Dialog");
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        // create a composite with standard margins and spacing
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new FormLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Group group = new Group(composite, SWT.NONE);
        group.setText("Load from template:");
        group.setLayout(new FormLayout());
        FormData groupFormData = new FormData();
        groupFormData.left = new FormAttachment(0, 10);
        groupFormData.right = new FormAttachment(100, -10);
        groupFormData.top = new FormAttachment(0, 10);
        group.setLayoutData(groupFormData);
        
        Label templateLabel = new Label(group, SWT.NONE);
        templateLabel.setText("Template location:");
        FormData templateLabelFD = new FormData();
        templateLabelFD.top = new FormAttachment(0, 10);
        templateLabelFD.left = new FormAttachment(0, 10);
        templateLabel.setLayoutData(templateLabelFD);
        
        templateText = new Text(group, SWT.BORDER);
        FormData templateTextFD = new FormData();
        templateTextFD.top = new FormAttachment(templateLabel, 0, SWT.CENTER);
        templateTextFD.left = new FormAttachment(templateLabel, 10, SWT.RIGHT);
        templateTextFD.right = new FormAttachment(100, -10);
        templateText.setLayoutData(templateTextFD);
        
        Label generatedLabel = new Label(group, SWT.NONE);
        generatedLabel.setText("Generated json path:");
        FormData generatedLabelFD = new FormData();
        generatedLabelFD.top = new FormAttachment(templateLabel, 10, SWT.BOTTOM);
        generatedLabelFD.left = new FormAttachment(templateLabel, 0, SWT.LEFT);
        generatedLabel.setLayoutData(generatedLabelFD);
        
        generatedText = new Text(group, SWT.BORDER);
        FormData generatedTextFD = new FormData();
        generatedTextFD.top = new FormAttachment(generatedLabel, 0, SWT.CENTER);
        generatedTextFD.left = new FormAttachment(generatedLabel, 10, SWT.RIGHT);
        generatedTextFD.right = new FormAttachment(100, -10);
        generatedText.setLayoutData(generatedTextFD);

        Label hadoopVersionLabel = new Label(group, SWT.NONE);
        hadoopVersionLabel.setText("Cloudera version:");
        FormData hadoopVersionLabelFD = new FormData();
        hadoopVersionLabelFD.left = new FormAttachment(generatedLabel, 0, SWT.LEFT);
        hadoopVersionLabelFD.top = new FormAttachment(generatedLabel, 10, SWT.BOTTOM);
        hadoopVersionLabel.setLayoutData(hadoopVersionLabelFD);

        hadoopVersionText = new Text(group, SWT.BORDER);
        FormData hadoopVersionTextFD = new FormData();
        hadoopVersionTextFD.top = new FormAttachment(hadoopVersionLabel, 0, SWT.CENTER);
        hadoopVersionTextFD.left = new FormAttachment(hadoopVersionLabel, 10, SWT.RIGHT);
        hadoopVersionTextFD.right = new FormAttachment(100, -10);
        hadoopVersionText.setLayoutData(hadoopVersionTextFD);

        Label hadoopVersionDisplayLabel = new Label(group, SWT.NONE);
        hadoopVersionDisplayLabel.setText("Cloudera version display:");
        FormData hadoopVersionDisplayLabelFD = new FormData();
        hadoopVersionDisplayLabelFD.left = new FormAttachment(hadoopVersionLabel, 0, SWT.LEFT);
        hadoopVersionDisplayLabelFD.top = new FormAttachment(hadoopVersionLabel, 10, SWT.BOTTOM);
        hadoopVersionDisplayLabel.setLayoutData(hadoopVersionDisplayLabelFD);

        hadoopVersionDisplayText = new Text(group, SWT.BORDER);
        FormData hadoopVersionDisplayTextFD = new FormData();
        hadoopVersionDisplayTextFD.top = new FormAttachment(hadoopVersionDisplayLabel, 0, SWT.CENTER);
        hadoopVersionDisplayTextFD.left = new FormAttachment(hadoopVersionDisplayLabel, 10, SWT.RIGHT);
        hadoopVersionDisplayTextFD.right = new FormAttachment(100, -10);
        hadoopVersionDisplayText.setLayoutData(hadoopVersionDisplayTextFD);
        
        generateButton = new Button(group, SWT.PUSH);
        generateButton.setText("Generate");
        FormData generateButtonFD = new FormData();
        generateButtonFD.top = new FormAttachment(hadoopVersionDisplayLabel, 10, SWT.BOTTOM);
        generateButtonFD.left = new FormAttachment(hadoopVersionDisplayLabel, 0, SWT.LEFT);
        generateButtonFD.bottom = new FormAttachment(100, -10);
        generateButton.setLayoutData(generateButtonFD);

        applyDialogFont(composite);
        addListeners();
        return composite;
    }
    
    protected void addListeners() {
        generateButton.addSelectionListener(new SelectionListener() {
            
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileOutputStream outStream = null;
                try {
                    ObjectMapper om = new ObjectMapper();
                    TemplateBean bean = om.readValue(new File(templateText.getText()), TemplateBean.class);
                    DynamicConfiguration configuration = new DynamicConfiguration();
                    configuration.setDistribution(IClouderaDistribution.DISTRIBUTION_NAME);
                    configuration.setVersion(hadoopVersionText.getText());
                    configuration.setName(hadoopVersionDisplayText.getText());
                    DynamicTemplateAdapter templateAdapter = new DynamicTemplateAdapter(bean, configuration);
                    templateAdapter.adapt(new NullProgressMonitor());
                    IDynamicPlugin dynamicPlugin = templateAdapter.getDynamicPlugin();
                    String content = DynamicServiceUtil.formatJsonString(dynamicPlugin.toXmlJson().toString());
                    File outFile = new File(generatedText.getText());
                    outStream = new FileOutputStream(outFile);
                    outStream.write(content.getBytes("UTF-8"));
                    outStream.flush();
                } catch (Exception ex) {
                    ExceptionHandler.process(ex);
                } finally {
                    if (outStream != null) {
                        try {
                            outStream.close();
                        } catch (Exception e1) {
                            ExceptionHandler.process(e1);
                        }
                    }
                }
            }
            
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // nothing to do
            }
        });
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Test Dialog");
        shell.setSize(300, 300);
    }
}
