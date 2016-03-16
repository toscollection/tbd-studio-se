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

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class DistributionVersion {

    public final DistributionBean distribution;

    public final HadoopComponent hadoopComponent;

    /**
     * Version.
     */
    public final String version, displayVersion;

    public DistributionVersion(HadoopComponent hadoopComponent, DistributionBean distribution, String version,
            String displayVersion) {
        super();
        this.hadoopComponent = hadoopComponent;
        this.distribution = distribution;
        this.version = version;
        this.displayVersion = displayVersion;
    }

    /**
     * Modules
     */
    private Set<DistributionVersionModule> modules = new LinkedHashSet<DistributionVersionModule>();

    public DistributionVersionModule[] getVersionModules() {
        return modules.toArray(new DistributionVersionModule[0]);
    }

    public void addModuleGroup(DistributionModuleGroup g) {
        if (g != null) {
            DistributionVersionModule vm = new DistributionVersionModule(this);
            vm.moduleGrop = g;
            modules.add(vm);
        }
    }

    public void addModuleGroups(Collection<DistributionModuleGroup> groups) {
        if (groups != null) {
            for (DistributionModuleGroup g : groups) {
                addModuleGroup(g);
            }
        }
    }

    /**
     * Display condition.
     */
    public ComponentCondition displayCondition;

    public String getDisplayShowIf() {
        ComponentCondition additionalCondition = displayCondition;
        if (additionalCondition != null
                && ("(true)".equals(additionalCondition.getConditionString()) || "(false)".equals(additionalCondition //$NON-NLS-1$ //$NON-NLS-2$
                        .getConditionString()))) {
            // Don't show a version if it's display condition is a BooleanCondition.
            return "(true)".equals(additionalCondition.getConditionString()) ? "true" : "false"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        } else {
            // Compose the ComponentCondition to display a version.
            ComponentCondition condition;
            org.talend.hadoop.distribution.condition.Expression e = new BasicExpression(
                    distribution.componentType.getDistributionParameter(), EqualityOperator.EQ, distribution.name);
            if (additionalCondition != null) {
                condition = new MultiComponentCondition(new SimpleComponentCondition(e), BooleanOperator.AND,
                        new NestedComponentCondition(additionalCondition));
            } else {
                condition = new SimpleComponentCondition(e);
            }

            return condition.getConditionString();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((version == null) ? 0 : version.hashCode());
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
        if (!(obj instanceof DistributionVersion)) {
            return false;
        }
        DistributionVersion other = (DistributionVersion) obj;
        if (version == null) {
            if (other.version != null) {
                return false;
            }
        } else if (!version.equals(other.version)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return version + ',' + displayVersion;
    }

}
