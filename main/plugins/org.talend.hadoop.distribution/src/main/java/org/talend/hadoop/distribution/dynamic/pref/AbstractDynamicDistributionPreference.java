// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.pref;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.talend.core.nexus.TalendLibsServerManager;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.core.runtime.projectsetting.ProjectPreferenceManager;
import org.talend.utils.security.StudioEncryption;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDynamicDistributionPreference implements IDynamicDistributionPreference {

    private IPreferenceStore prefStore;

    private TalendLibsServerManager libsManager = TalendLibsServerManager.getInstance();

    abstract protected String getPrefKeyOverrideDefaultSetup();

    abstract protected String getPrefKeyRepository();

    abstract protected String getPrefKeyAnonymous();

    abstract protected String getPrefKeyUsername();

    abstract protected String getPrefKeyPassword();

    abstract protected boolean getPrefDefaultOverrideDefaultSetup();

    abstract protected String getPrefDefaultRepository();

    abstract protected boolean getPrefDefaultAnonymous();

    abstract protected String getPrefDefaultUsername();

    abstract protected String getPrefDefaultPassword();

    protected AbstractDynamicDistributionPreference(ScopedPreferenceStore store) {
        prefStore = store;
        initDefaultPreference();
    }

    @Override
    public void initDefaultPreference() {
        prefStore.setDefault(getPrefKeyOverrideDefaultSetup(), getDefaultOverrideDefaultSetup());
        prefStore.setDefault(getPrefKeyRepository(), getDefaultRepository());
        prefStore.setDefault(getPrefKeyAnonymous(), getDefaultIsAnonymous());
        prefStore.setDefault(getPrefKeyUsername(), getDefaultUsername());
        prefStore.setDefault(getPrefKeyPassword(), encrypt(getDefaultPassword()));
    }

    @Override
    public boolean overrideDefaultSetup() {
        return prefStore.getBoolean(getPrefKeyOverrideDefaultSetup());
    }

    @Override
    public boolean getDefaultOverrideDefaultSetup() {
        return getPrefDefaultOverrideDefaultSetup();
    }

    @Override
    public void setOverrideDefaultSetup(boolean override) {
        prefStore.setValue(getPrefKeyOverrideDefaultSetup(), override);
    }

    @Override
    public String getRepository() {
        return prefStore.getString(getPrefKeyRepository());
    }

    @Override
    public String getDefaultRepository() {
        return getPrefDefaultRepository();
    }

    @Override
    public void setRepository(String repository) {
        prefStore.setValue(getPrefKeyRepository(), repository);
    }

    @Override
    public boolean isAnonymous() {
        return prefStore.getBoolean(getPrefKeyAnonymous());
    }

    @Override
    public boolean getDefaultIsAnonymous() {
        return getPrefDefaultAnonymous();
    }

    @Override
    public void setAnonymous(boolean anonymous) {
        prefStore.setValue(getPrefKeyAnonymous(), anonymous);
    }

    @Override
    public String getUsername() {
        String[] credentials = getCredentialsFromSecureStorage();
        if (credentials != null && StringUtils.isNotBlank(credentials[0])) {
            return credentials[0];
        }
        if (getDefaultRepository().equals(getRepository())) {
            return getDefaultUsername();
        }
        return "";
    }

    @Override
    public String getDefaultUsername() {
        return getPrefDefaultUsername();
    }

    @Override
    public void setUsername(String username) {
        if (StringUtils.isNotBlank(username)) {
            setUsernameToSecureStorage(username);
        }
    }

    @Override
    public String getPassword() {
        String[] credentials = getCredentialsFromSecureStorage();
        if (credentials != null && StringUtils.isNotBlank(credentials[0])) {
            // username exist in storage, return pair password
            return credentials[1];
        }
        if (getDefaultRepository().equals(getRepository())) {
            return decrypt(getDefaultPassword());
        }
        return "";
    }

    @Override
    public String getDefaultPassword() {
        return getPrefDefaultPassword();
    }

    @Override
    public void setPassword(String password) {
        if (password == null) {
            password = ""; //$NON-NLS-1$
        }
        setPasswordToSecureStorage(password);
    }

    @Override
    public void save() throws Exception {
        ((IPersistentPreferenceStore) prefStore).save();
        saveCredentialsToSecureStorage();
    }

    @Override
    public String getPreferencePath() {
        ProjectPreferenceManager projectPreferenceManager = CoreRuntimePlugin.getInstance().getProjectPreferenceManager();
        IPath path = projectPreferenceManager.getLocation();
        IProject project = projectPreferenceManager.getProject();
        path = path.makeRelativeTo(project.getLocation());
        return path.toPortableString();
    }

    @Override
    public void clearCache() throws Exception {
        /**
         * DON'T save here, it may bring bugs when switching branches(and even for reference projects), we SHOULD save
         * preference immediately after changed instead of saving here;
         */
        // save();
    }

    public String[] getCredentialsFromSecureStorage() {
        return libsManager.getProxyArtifactCredentials(getRepository(), null, getPrefKeyUsername(), getPrefKeyPassword());
    }

    public void setUsernameToSecureStorage(String username) {
        libsManager.saveProxyArtifactCredentialsUserName(getRepository(), null, getPrefKeyUsername(), username, false);
    }

    public void setPasswordToSecureStorage(String password) {
        libsManager.saveProxyArtifactCredentialsPassword(getRepository(), null, getPrefKeyPassword(), password, false);
    }

    public void saveCredentialsToSecureStorage() {
        libsManager.flushSecurityStorage();
    }

    public IPreferenceStore getPrefStore() {
        return prefStore;
    }

    public String encrypt(String str) {
        return StudioEncryption.getStudioEncryption(StudioEncryption.EncryptionKeyName.SYSTEM).encrypt(str);
    }


    public String decrypt(String str) {
        return StudioEncryption.getStudioEncryption(StudioEncryption.EncryptionKeyName.SYSTEM).decrypt(str);
    }
}
