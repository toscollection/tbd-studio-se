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
package org.talend.designer.hdfsbrowse.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import orgomg.cwm.resource.relational.Table;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSNode implements IHDFSNode {

    protected static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    protected ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();

    private String value;

    private Table table;

    private EHadoopFileTypes type;

    private IHDFSNode parent;

    private String path = PATH_SEPARATOR;

    private List<IHDFSNode> children = new ArrayList<IHDFSNode>();

    protected boolean hasFetchedChildren = false;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EHadoopFileTypes getType() {
        return this.type;
    }

    public void setType(EHadoopFileTypes type) {
        this.type = type;
    }

    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Image getImage() {
        return null;
    }

    public IHDFSNode getParent() {
        return this.parent;
    }

    public void setParent(IHDFSNode parent) {
        this.parent = parent;
    }

    public List<IHDFSNode> getChildren() {
        return this.children;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public void addChild(IHDFSNode child) {
        this.children.add(child);
    }

    public void addChildren(List<IHDFSNode> children) {
        this.children.addAll(children);
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void forceFetchChildren() {
        this.hasFetchedChildren = false;
    }

    public void refresh() {
    }
}
