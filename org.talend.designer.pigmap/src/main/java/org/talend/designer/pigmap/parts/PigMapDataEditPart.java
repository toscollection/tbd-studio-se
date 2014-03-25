// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap.parts;

import java.util.ArrayList;
import java.util.List;

import org.talend.designer.gefabstractmap.figures.layout.ZoneContentLayout;
import org.talend.designer.gefabstractmap.figures.treetools.zone.InputZoneToolBar;
import org.talend.designer.gefabstractmap.figures.treetools.zone.OutputZoneToolBar;
import org.talend.designer.gefabstractmap.part.MapperRootEditPart;
import org.talend.designer.pigmap.figures.manager.PigMapContainerLayout;
import org.talend.designer.pigmap.figures.manager.PigMapInputZoneToolBar;
import org.talend.designer.pigmap.figures.manager.PigMapOutputZoneToolBar;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapDataEditPart extends MapperRootEditPart {

    @Override
    public List getModelChildren() {
        List children = new ArrayList();
        children.addAll(((PigMapData) getModel()).getInputTables());
        children.addAll(((PigMapData) getModel()).getOutputTables());
        return children;
    }

    @Override
    protected PigMapDataManager createRootModelManager() {
        return new PigMapDataManager((PigMapData) getModel(), this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.MapperRootEditPart#createOutputZoneToolBar()
     */
    @Override
    protected OutputZoneToolBar createOutputZoneToolBar() {
        return new PigMapOutputZoneToolBar((PigMapDataManager) getRootModelManager());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.MapperRootEditPart#createInputZoneToolBar()
     */
    @Override
    protected InputZoneToolBar createInputZoneToolBar() {
        return new PigMapInputZoneToolBar((PigMapDataManager) getRootModelManager());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.MapperRootEditPart#createZoneContentLayout()
     */
    @Override
    protected ZoneContentLayout createZoneContentLayout() {
        return new PigMapContainerLayout();
    }
}
