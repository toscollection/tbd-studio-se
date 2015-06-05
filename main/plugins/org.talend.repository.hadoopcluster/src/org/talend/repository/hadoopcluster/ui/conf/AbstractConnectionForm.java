// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.ui.conf;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.repository.hadoopcluster.conf.IPropertyConstants;
import org.talend.repository.hadoopcluster.conf.model.HadoopConfsConnection;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurationManager;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;

/**
 * created by ycbai on 2015年6月4日 Detailled comment
 *
 */
public abstract class AbstractConnectionForm extends Composite {

    protected LabelledText connURLText;

    protected LabelledText usernameText;

    protected LabelledText passwordText;

    protected Button connButton;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public AbstractConnectionForm(Composite parent, int style) {
        super(parent, style);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        this.setLayout(layout);
        createControl();
        addListener();
        fillDefaultValues();
    }

    protected abstract void createControl();

    protected void addListener() {
        // do nothing by default.
    }

    protected void fillDefaultValues() {
        // do nothing by default.
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String property, Object oldValue, Object newValue) {
        if (pcs.hasListeners(property)) {
            pcs.firePropertyChange(property, oldValue, newValue);
        }
    }

    protected Composite createParentGroup(String title, Composite parent, int colNum) {
        Group connectionGroup = Form.createGroup(parent, colNum, title);
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return connectionGroup;
    }

    protected HadoopConfigurator getHadoopConfigurator() throws Exception {
        return HadoopConfsUtils.getHadoopConfigurator(getHadoopConfigurationManager(), getHadoopConfsConnection());
    }

    protected void checkConnection() {
        HadoopConfigurator hadoopConfigurator = null;
        try {
            hadoopConfigurator = getHadoopConfigurator();
        } catch (Exception e) {
            firePropertyChange(IPropertyConstants.PROPERTY_CONNECT, null, e);
        }
        firePropertyChange(IPropertyConstants.PROPERTY_CONNECT, null, hadoopConfigurator);
    }

    protected abstract HadoopConfigurationManager getHadoopConfigurationManager();

    protected abstract HadoopConfsConnection getHadoopConfsConnection();

    public String getConnURL() {
        if (connURLText != null) {
            return connURLText.getText();
        }
        return null;
    }

    public String getUsername() {
        if (usernameText != null) {
            return usernameText.getText();
        }
        return null;
    }

    public String getPassword() {
        if (passwordText != null) {
            return passwordText.getText();
        }
        return null;
    }

}
