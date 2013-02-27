// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.designer.hdfsbrowse.util.EHadoopDistributions;
import org.talend.designer.hdfsbrowse.util.EHadoopVersion4Drivers;
import org.talend.oozie.scheduler.constants.TOozieUIConstants;

/**
 * created by ycbai on 2013-2-26 Detailled comment
 * 
 */
public class OozieSettingComposite extends ScrolledComposite {

    private LabelledCombo hadoopDistributionCombo;

    private LabelledCombo hadoopVersionCombo;

    private Text nameNodeEndPointTxt;

    private Text jobTrackerEndPointTxt;

    private Text oozieEndPointTxt;

    private Text userNameTxt;

    private String hadoopDistributionValue;

    private String hadoopVersionValue;

    private String nameNodeEndPointValue;

    private String jobTrackerEndPointValue;

    private String oozieEndPointValue;

    private String userNameValue;

    /**
     * DOC ycbai OozieSettingComposite constructor comment.
     * 
     * @param parent
     * @param style
     */
    public OozieSettingComposite(Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout());
        setLayoutData(new GridData(GridData.FILL_BOTH));
        setExpandHorizontal(true);
        setExpandVertical(true);
        createContents(this);
    }

    private void createContents(Composite parent) {
        preInitialization();

        Composite comp = new Composite(parent, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        comp.setLayout(layout);
        setContent(comp);

        addVersionFields(comp);
        addConnectionFields(comp);

        initUI();
        addListeners();
    }

    protected void preInitialization() {
    }

    private void addVersionFields(Composite parent) {
        Group versionGroup = Form.createGroup(parent, 2, TOozieUIConstants.OOZIE_LBL_VERSION_GROUP);
        versionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        hadoopDistributionCombo = new LabelledCombo(versionGroup, TOozieUIConstants.OOZIE_LBL_HADOOP_DISTRIBUTION,
                "", EHadoopDistributions.getAllDistributionDisplayNames() //$NON-NLS-1$
                        .toArray(new String[0]), 1, true);
        hadoopVersionCombo = new LabelledCombo(versionGroup, TOozieUIConstants.OOZIE_LBL_HADOOP_VERSION,
                "", new String[0], 1, true); //$NON-NLS-1$
    }

    private void addConnectionFields(Composite parent) {
        Group connectionGroup = Form.createGroup(parent, 2, TOozieUIConstants.OOZIE_LBL_CONNECTION_GROUP);
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Name node end point
        Label nameNodeEPLbl = new Label(connectionGroup, SWT.NONE);
        nameNodeEPLbl.setText(TOozieUIConstants.OOZIE_LBL_NAME_NODE_EP);

        nameNodeEndPointTxt = new Text(connectionGroup, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(nameNodeEndPointTxt);

        // Job tracker end point
        Label jobTrackerEPLbl = new Label(connectionGroup, SWT.NONE);
        jobTrackerEPLbl.setText(TOozieUIConstants.OOZIE_LBL_JOB_TRACKER_EP);

        jobTrackerEndPointTxt = new Text(connectionGroup, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(jobTrackerEndPointTxt);

        // Oozie end point
        Label oozieEPLbl = new Label(connectionGroup, SWT.NONE);
        oozieEPLbl.setText(TOozieUIConstants.OOZIE_LBL_OOZIE_EP);

        oozieEndPointTxt = new Text(connectionGroup, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(oozieEndPointTxt);

        // UserName for hadoop
        Label userNameLbl = new Label(connectionGroup, SWT.NONE);
        userNameLbl.setText(TOozieUIConstants.OOZIE_LBL_USERNAME);

        userNameTxt = new Text(connectionGroup, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(userNameTxt);
    }

    protected void initUI() {
        EHadoopDistributions distribution = EHadoopDistributions.getDistributionByName(hadoopDistributionValue, false);
        if (distribution != null) {
            String distributionDisplayName = distribution.getDisplayName();
            hadoopDistributionCombo.setText(distributionDisplayName);
            updateVersionCombo(distributionDisplayName);
        }
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(hadoopVersionValue);
        if (version4Drivers != null) {
            hadoopVersionCombo.setText(version4Drivers.getVersionDisplay());
        }
        nameNodeEndPointTxt.setText(nameNodeEndPointValue == null ? "" : nameNodeEndPointValue); //$NON-NLS-1$
        jobTrackerEndPointTxt.setText(jobTrackerEndPointValue == null ? "" : jobTrackerEndPointValue); //$NON-NLS-1$
        oozieEndPointTxt.setText(oozieEndPointValue == null ? "" : oozieEndPointValue); //$NON-NLS-1$
        userNameTxt.setText(userNameValue == null ? "" : userNameValue); //$NON-NLS-1$
    }

    protected void addListeners() {
        hadoopDistributionCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String newDistributionDisplayName = hadoopDistributionCombo.getText();
                EHadoopDistributions distribution = EHadoopDistributions.getDistributionByName(newDistributionDisplayName, false);
                if (distribution != null) {
                    hadoopDistributionValue = distribution.getName();
                    updateVersionCombo(newDistributionDisplayName);
                }
            }
        });

        hadoopVersionCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String newVersionDisplayName = hadoopVersionCombo.getText();
                EHadoopVersion4Drivers newVersion4Drivers = EHadoopVersion4Drivers.indexOfByVersionDisplay(newVersionDisplayName);
                if (newVersion4Drivers != null) {
                    hadoopVersionValue = newVersion4Drivers.getVersionValue();
                }
            }
        });

        nameNodeEndPointTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                nameNodeEndPointValue = nameNodeEndPointTxt.getText();
            }
        });

        jobTrackerEndPointTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                jobTrackerEndPointValue = jobTrackerEndPointTxt.getText();
            }
        });

        oozieEndPointTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                oozieEndPointValue = oozieEndPointTxt.getText();
            }
        });

        userNameTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                userNameValue = userNameTxt.getText();
            }
        });
    }

    private void updateVersionCombo(String distribution) {
        List<String> items = getDistributionVersions(distribution);
        String[] versions = new String[items.size()];
        items.toArray(versions);
        hadoopVersionCombo.getCombo().setItems(versions);
        if (versions.length > 0) {
            hadoopVersionCombo.getCombo().select(0);
        }
    }

    private List<String> getDistributionVersions(String distribution) {
        List<String> result = new ArrayList<String>();
        List<EHadoopVersion4Drivers> v4dList = EHadoopVersion4Drivers.indexOfByDistribution(distribution);
        for (EHadoopVersion4Drivers v4d : v4dList) {
            result.add(v4d.getVersionDisplay());
        }
        return result;
    }

    public String getHadoopDistributionValue() {
        return this.hadoopDistributionValue;
    }

    public void setHadoopDistributionValue(String hadoopDistributionValue) {
        this.hadoopDistributionValue = hadoopDistributionValue;
    }

    public String getHadoopVersionValue() {
        return this.hadoopVersionValue;
    }

    public void setHadoopVersionValue(String hadoopVersionValue) {
        this.hadoopVersionValue = hadoopVersionValue;
    }

    public String getNameNodeEndPointValue() {
        return this.nameNodeEndPointValue;
    }

    public void setNameNodeEndPointValue(String nameNodeEndPointValue) {
        this.nameNodeEndPointValue = nameNodeEndPointValue;
    }

    public String getJobTrackerEndPointValue() {
        return this.jobTrackerEndPointValue;
    }

    public void setJobTrackerEndPointValue(String jobTrackerEndPointValue) {
        this.jobTrackerEndPointValue = jobTrackerEndPointValue;
    }

    public String getOozieEndPointValue() {
        return this.oozieEndPointValue;
    }

    public void setOozieEndPointValue(String oozieEndPointValue) {
        this.oozieEndPointValue = oozieEndPointValue;
    }

    public String getUserNameValue() {
        return this.userNameValue;
    }

    public void setUserNameValue(String userNameValue) {
        this.userNameValue = userNameValue;
    }

}
