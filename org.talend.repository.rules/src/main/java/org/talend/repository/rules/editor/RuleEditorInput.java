// ============================================================================
//
// Copyright (C) 2006-2008 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.rules.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.part.FileEditorInput;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.RulesItem;
import org.talend.core.ui.images.ECoreImage;
import org.talend.repository.editor.RepositoryEditorInput;

/**
 * DOC hyWang class global comment. Detailled comment
 */
public class RuleEditorInput extends RepositoryEditorInput {

    /**
     * DOC hyWang RuleEditorInput constructor comment.
     * 
     * @param file
     * @param item
     */
    private RulesItem item;

    public RuleEditorInput(IFile file, Item item) {
        super(file, item);
        this.item = (RulesItem) item;
    }

    public RulesItem getRuleItem() {
        return (RulesItem) this.getItem();
    }

    /**
     * DOC hyWang RuleEditorInput constructor comment.
     * 
     * @param item
     */

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return true;
        if (obj instanceof FileEditorInput) {
            FileEditorInput input = (FileEditorInput) obj;
            if (input.getFile().equals(this.getFile())) {
                return true;
            }

        }
        if (!(obj instanceof RuleEditorInput))
            return false;

        RuleEditorInput other = (RuleEditorInput) obj;
        return this.getRuleItem().equals(other.getRuleItem());
    }

    @Override
    public int hashCode() {
        return getRuleItem().hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#exists()
     */
    public boolean exists() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
     */
    public ImageDescriptor getImageDescriptor() {
        ImageDescriptor defaultImage = null;
        return defaultImage = ImageProvider.getImageDesc(ECoreImage.METADATA_RULES_ICON);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#getName()
     */
    public String getName() {
        return getRuleItem().getProperty().getLabel() + " " + getRuleItem().getProperty().getVersion();//$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     */
    public IPersistableElement getPersistable() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     */
    public String getToolTipText() {
        return getRuleItem().getProperty().getLabel() + getRuleItem().getExtension();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
        return null;
    }

}
