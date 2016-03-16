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
package org.talend.hadoop.distribution.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.utils.ComponentConditionUtil;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class DistributionBean {

    public final ComponentType componentType;

    /**
     * Distribution name and display name.
     */
    public final String name, displayName;

    public DistributionBean(ComponentType componentType, String name, String displayName) {
        super();
        this.componentType = componentType;
        this.name = name;
        this.displayName = displayName;
    }

    /**
     * All versions.
     */
    private List<DistributionVersion> versions = new ArrayList<DistributionVersion>();

    public void addVersion(DistributionVersion v) {
        if (v != null) {
            versions.add(v);
        }
    }

    public DistributionVersion[] getVersions() {
        Collections.sort(versions, new Comparator<DistributionVersion>() {

            @Override
            public int compare(DistributionVersion b1, DistributionVersion b2) {
                int cmp = b1.displayVersion.compareTo(b2.displayVersion);
                if (cmp == 0) {
                    cmp = b1.version.compareTo(b2.version);
                }
                return cmp;
            }
        });
        return versions.toArray(new DistributionVersion[versions.size()]);
    }

    public String[] getVersionsDisplay() {
        List<String> versionsDisplay = new ArrayList<String>();
        for (DistributionVersion v : versions) {
            versionsDisplay.add(v.displayVersion);
        }
        return versionsDisplay.toArray(new String[0]);
    }

    public DistributionVersion findVersionByDisplay(String display) {
        if (display != null) {
            for (DistributionVersion v : versions) {
                if (display.equals(v.displayVersion)) {
                    return v;
                }
            }
        }
        return null;
    }

    /**
     * Default version.
     */
    private DistributionVersion defaultVersion;

    public void setDefaultVersion(DistributionVersion v) {
        if (v != null) {
            defaultVersion = v;
        }
    }

    public DistributionVersion getDefaultVersion() {
        if (defaultVersion == null) {
            if (!versions.isEmpty()) {
                return versions.get(versions.size() - 1);// last one by default?
            }
            return null;
        } else {
            return defaultVersion;
        }
    }

    /**
     * Conditions
     */
    private Set<ComponentCondition> conditions = new HashSet<ComponentCondition>();

    public void addCondition(ComponentCondition c) {
        if (c != null) {
            conditions.add(c);
        }
    }

    public ComponentCondition[] getConditions() {
        return conditions.toArray(new ComponentCondition[conditions.size()]);
    }

    public String getDisplayShowIf() {
        // We compose a ShowIf parameter for a distribution, only if the list of its version all have a display
        // condition.
        ComponentCondition cc = ComponentConditionUtil.buildDistributionShowIf(conditions);
        return cc == null ? null : cc.getConditionString();
    }

    public boolean useCustom() {
        return name.equals(EHadoopDistributions.CUSTOM.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DistributionBean)) {
            return false;
        }
        DistributionBean other = (DistributionBean) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ',' + displayName;
    }

}
