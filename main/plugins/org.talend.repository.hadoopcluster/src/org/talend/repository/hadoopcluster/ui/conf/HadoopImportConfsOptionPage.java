package org.talend.repository.hadoopcluster.ui.conf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.repository.hadoopcluster.i18n.Messages;

/**
 * 
 * created by ycbai on 2015年5月28日 Detailled comment
 *
 */
public class HadoopImportConfsOptionPage extends AbstractHadoopImportConfsPage {

    private LabelledCombo distributionCombo;

    private LabelledCombo versionCombo;

    private Button remoteBtn;

    private Button localBtn;

    private Button manualBtn;

    public HadoopImportConfsOptionPage() {
        super("HadoopImportConfsOptionPage"); //$NON-NLS-1$
        setTitle(Messages.getString("HadoopImportConfsOptionPage.title")); //$NON-NLS-1$
        setDescription(Messages.getString("HadoopImportConfsOptionPage.desc")); //$NON-NLS-1$
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = createParentComposite(parent);

        addDistributionFields(container);
        addOptionFields(container);

        addListener();
        init();

        setControl(container);
        setPageComplete(true);
    }

    private void addListener() {
        distributionCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                updateVersionPart();
                updateOptionPart();
            }
        });
        ButtonSelectionListener buttonSelectionListener = new ButtonSelectionListener();
        remoteBtn.addSelectionListener(buttonSelectionListener);
        localBtn.addSelectionListener(buttonSelectionListener);
        manualBtn.addSelectionListener(buttonSelectionListener);
    }

    class ButtonSelectionListener extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            updateWizardButtons();
        }
    }

    private void init() {
        updateDistributionPart();
        updateVersionPart();
    }

    private void updateDistributionPart() {
        List<String> distributions = EHadoopDistributions.getAllDistributionDisplayNames();
        distributions.remove(EHadoopDistributions.CUSTOM.getDisplayName());
        distributionCombo.getCombo().setItems(distributions.toArray(new String[0]));
        distributionCombo.select(0);
    }

    private void updateVersionPart() {
        EHadoopDistributions distribution = EHadoopDistributions.getDistributionByDisplayName(distributionCombo.getText());
        List<String> items = getDistributionVersions(distribution);
        versionCombo.getCombo().setItems(items.toArray(new String[0]));
        versionCombo.getCombo().select(0);
    }

    private void updateOptionPart() {
        EHadoopDistributions distribution = EHadoopDistributions.getDistributionByDisplayName(distributionCombo.getText());
        boolean supportRemote = EHadoopDistributions.HORTONWORKS.equals(distribution)
                || EHadoopDistributions.CLOUDERA.equals(distribution);
        remoteBtn.setEnabled(supportRemote);
    }

    private List<String> getDistributionVersions(EHadoopDistributions distribution) {
        List<String> result = new ArrayList<>();
        List<EHadoopVersion4Drivers> v4dList = EHadoopVersion4Drivers.indexOfByDistribution(distribution);
        for (EHadoopVersion4Drivers v4d : v4dList) {
            result.add(v4d.getVersionDisplay());
        }
        return result;
    }

    private void addDistributionFields(Composite parent) {
        Group distributionGroup = Form.createGroup(parent, 4,
                Messages.getString("HadoopImportConfsOptionPage.group.distribution")); //$NON-NLS-1$
        distributionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        distributionCombo = new LabelledCombo(distributionGroup,
                Messages.getString("HadoopClusterForm.distribution"), //$NON-NLS-1$
                Messages.getString("HadoopClusterForm.distribution.tooltip"), EHadoopDistributions.getAllDistributionDisplayNames() //$NON-NLS-1$
                        .toArray(new String[0]), 1, true);
        versionCombo = new LabelledCombo(distributionGroup, Messages.getString("HadoopClusterForm.version"), //$NON-NLS-1$
                Messages.getString("HadoopClusterForm.version.tooltip"), new String[0], 1, true); //$NON-NLS-1$
    }

    private void addOptionFields(Composite parent) {
        Group optionGroup = Form.createGroup(parent, 1, Messages.getString("HadoopImportConfsOptionPage.group.option")); //$NON-NLS-1$
        optionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        remoteBtn = new Button(optionGroup, SWT.RADIO);
        remoteBtn.setText(Messages.getString("HadoopImportConfsOptionPage.option.remote")); //$NON-NLS-1$
        localBtn = new Button(optionGroup, SWT.RADIO);
        localBtn.setText(Messages.getString("HadoopImportConfsOptionPage.option.local")); //$NON-NLS-1$
        localBtn.setSelection(true);
        manualBtn = new Button(optionGroup, SWT.RADIO);
        manualBtn.setText(Messages.getString("HadoopImportConfsOptionPage.option.manual")); //$NON-NLS-1$
    }

    @Override
    public IWizardPage getNextPage() {
        IWizardPage nextPage = super.getNextPage();
        if (nextPage == null) {
            nextPage = getOptionPage();
        }
        if (nextPage != null) {
            nextPage.setWizard(getWizard());
        }
        return nextPage;
    }

    private IWizardPage getOptionPage() {
        IWizardPage optionPage = null;
        if (remoteBtn.getSelection()) {
            optionPage = new HadoopImportRemoteOptionPage();
        } else if (localBtn.getSelection()) {
            optionPage = new HadoopImportLocalOptionPage();
        }
        return optionPage;
    }

    private void updateWizardButtons() {
        IWizardContainer container = getContainer();
        container.updateButtons();
    }

    @Override
    public boolean isLastPage() {
        return manualBtn.getSelection();
    }

    public String getDistribution() {
        return distributionCombo.getText();
    }

    public String getVersion() {
        return versionCombo.getText();
    }

}
