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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.core.hadoop.custom.ECustomVersionGroup;
import org.talend.core.hadoop.custom.ECustomVersionType;
import org.talend.core.hadoop.custom.HadoopCustomVersionDefineDialog;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.oozie.scheduler.constants.TOozieUIConstants;
import org.talend.oozie.scheduler.utils.TOozieParamUtils;

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

    private Button customButton;

    private String hadoopDistributionValue;

    private String hadoopVersionValue;

    private String nameNodeEndPointValue;

    private String jobTrackerEndPointValue;

    private String oozieEndPointValue;

    private String userNameValue;

    private String customJars;

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
        Group versionGroup = Form.createGroup(parent, 3, TOozieUIConstants.OOZIE_LBL_VERSION_GROUP);
        versionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        hadoopDistributionCombo = new LabelledCombo(versionGroup, TOozieUIConstants.OOZIE_LBL_HADOOP_DISTRIBUTION,
                "", EHadoopDistributions.getAllDistributionDisplayNames() //$NON-NLS-1$
                        .toArray(new String[0]), 2, true);
        hadoopVersionCombo = new LabelledCombo(versionGroup, TOozieUIConstants.OOZIE_LBL_HADOOP_VERSION,
                "", new String[0], 2, true); //$NON-NLS-1$
        customButton = new Button(versionGroup, SWT.NULL);
        customButton.setImage(ImageProvider.getImage(EImage.THREE_DOTS_ICON));
        customButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 1, 1));
        EHadoopDistributions distribution = EHadoopDistributions.getDistributionByName(hadoopDistributionValue, false);
        updateVersionPart(distribution);
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
            updateVersionPart(distribution);
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
                EHadoopDistributions distribution = EHadoopDistributions.getDistributionByDisplayName(newDistributionDisplayName);
                if (distribution != null) {
                    hadoopDistributionValue = distribution.getName();
                    updateVersionPart(distribution);
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

        customButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                HadoopCustomVersionDefineDialog customVersionDialog = new HadoopCustomVersionDefineDialog(getShell(),
                        getCustomVersionMap()) {

                    @Override
                    protected ECustomVersionType[] getDisplayTypes() {
                        return new ECustomVersionType[] { ECustomVersionType.OOZIE };
                    }
                };
                if (customVersionDialog.open() == Window.OK) {
                    customJars = customVersionDialog.getLibListStr(ECustomVersionGroup.COMMON);
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

    private void updateVersionPart(EHadoopDistributions distribution) {
        GridData distriData = (GridData) hadoopDistributionCombo.getCombo().getLayoutData();
        if (distribution == EHadoopDistributions.CUSTOM) {
            hadoopVersionCombo.setHideWidgets(true);
            distriData.horizontalSpan = 1;
            hideControl(customButton, false);
        } else {
            hadoopVersionCombo.setHideWidgets(false);
            distriData.horizontalSpan = 2;
            hideControl(customButton, true);
            List<String> items = getDistributionVersions(distribution);
            String[] versions = new String[items.size()];
            items.toArray(versions);
            hadoopVersionCombo.getCombo().setItems(versions);
            if (versions.length > 0) {
                hadoopVersionCombo.getCombo().select(0);
            }
        }
    }

    private Map<String, Set<String>> getCustomVersionMap() {
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();

        if (customJars == null) {
            customJars = TOozieParamUtils.getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS);
        }
        if (StringUtils.isNotEmpty(customJars)) {
            Set<String> jarSet = new HashSet<String>();
            String[] jarArray = customJars.split(";"); //$NON-NLS-1$
            for (String jar : jarArray) {
                jarSet.add(jar);
            }
            map.put(ECustomVersionGroup.COMMON.getName(), jarSet);
        }

        return map;
    }

    private List<String> getDistributionVersions(EHadoopDistributions distribution) {
        List<String> result = new ArrayList<String>();
        List<EHadoopVersion4Drivers> v4dList = EHadoopVersion4Drivers.indexOfByDistribution(distribution);
        for (EHadoopVersion4Drivers v4d : v4dList) {
            result.add(v4d.getVersionDisplay());
        }
        return result;
    }

    protected void hideControl(Control control, boolean hide) {
        GridData dataBtn = (GridData) control.getLayoutData();
        dataBtn.exclude = hide;
        control.setLayoutData(dataBtn);
        control.setVisible(!hide);
        Composite parent = control.getParent();
        if (parent != null) {
            parent.layout();
            Composite pParent = parent.getParent();
            if (pParent != null) {
                pParent.layout();
            }
        }
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

    public String getCustomJars() {
        return this.customJars;
    }

    public void setCustomJars(String customJars) {
        this.customJars = customJars;
    }

}
