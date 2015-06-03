package org.talend.repository.hadoopcluster.ui.conf;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.AbstractCheckedComposite;

/**
 * 
 * created by ycbai on 2015年5月28日 Detailled comment
 *
 */
public class HadoopImportRemoteOptionPage extends AbstractHadoopImportConfsPage {

    private LabelledText connURLText;

    private LabelledText usernameText;

    private LabelledText passwordText;

    private Button connButton;

    private Combo clustersCombo;

    private Button selectClusterButton;

    public HadoopImportRemoteOptionPage() {
        super("HadoopImportRemoteOptionPage"); //$NON-NLS-1$
        setTitle(Messages.getString("HadoopImportRemoteOptionPage.title")); //$NON-NLS-1$
        setDescription(Messages.getString("HadoopImportRemoteOptionPage.desc")); //$NON-NLS-1$
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = createParentComposite(parent);

        addConnectionFields(container);
        addClusterFields(container);

        addListener();

        setControl(container);
        setPageComplete(false);
    }

    private void addListener() {
        connButton.addSelectionListener(new SelectionAdapter() {

        });
        selectClusterButton.addSelectionListener(new SelectionAdapter() {

        });
    }

    private void addConnectionFields(Composite parent) {
        Group connectionGroup = Form.createGroup(parent, 2, Messages.getString("HadoopImportRemoteOptionPage.group.connection")); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        connURLText = new LabelledText(connectionGroup, Messages.getString("HadoopImportRemoteOptionPage.text.connURL"), 1); //$NON-NLS-1$
        usernameText = new LabelledText(connectionGroup, Messages.getString("HadoopImportRemoteOptionPage.text.username"), 1); //$NON-NLS-1$
        passwordText = new LabelledText(connectionGroup, Messages.getString("HadoopImportRemoteOptionPage.text.password"), 1); //$NON-NLS-1$
        connButton = new Button(connectionGroup, SWT.PUSH);
        GridData connBtnGD = new GridData(SWT.END, SWT.CENTER, false, false);
        connBtnGD.horizontalSpan = 2;
        connButton.setLayoutData(connBtnGD);
        connButton.setText(Messages.getString("HadoopImportRemoteOptionPage.button.connect")); //$NON-NLS-1$
    }

    private void addClusterFields(Composite parent) {
        Group clusterGroup = Form.createGroup(parent, 2, Messages.getString("HadoopImportRemoteOptionPage.group.clusters")); //$NON-NLS-1$
        clusterGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

        clustersCombo = new Combo(clusterGroup, SWT.BORDER | SWT.READ_ONLY);
        clustersCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        selectClusterButton = new Button(clusterGroup, SWT.PUSH);
        GridData selectClusterBtnGD = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
        selectClusterButton.setLayoutData(selectClusterBtnGD);
        selectClusterButton.setText(Messages.getString("HadoopImportRemoteOptionPage.button.select")); //$NON-NLS-1$

        AbstractCheckedComposite servicesTableComp = new HadoopServicesTableComposite(clusterGroup, SWT.NONE);
        GridData servicesTableGD = new GridData(GridData.FILL_BOTH);
        servicesTableGD.horizontalSpan = 2;
        servicesTableComp.setLayoutData(servicesTableGD);
        addCheckListener(servicesTableComp);
    }

}
