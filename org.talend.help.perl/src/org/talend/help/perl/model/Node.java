// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.help.perl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Node.java.
 * 
 */
public class Node {

    private final EType type;

    private Node parent;

    private boolean searchMatchFlag = false;

    private List<Node> children = new ArrayList<Node>();

    private Map<EProperty, String> properties = new HashMap<EProperty, String>();

    public Node(final EType type, Node parent) {
        super();
        this.type = type;
        this.parent = parent;
        if (hasParent()) {
            parent.getChildren().add(this);
        }
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getNextSibling() {
        Node node = null;
        if (hasParent()) {
            int index = parent.getChildren().indexOf(this);
            node = (++index) >= parent.getChildren().size() ? this : parent.getChildren().get(index);
            return node;
        } else {
            return this;
        }
    }

    public Node getPreSibling() {
        Node node = null;
        if (hasParent()) {
            int index = parent.getChildren().indexOf(this);
            node = (--index) >= parent.getChildren().size() ? this : parent.getChildren().get(index);
            return node;
        } else {
            return this;
        }
    }

    public boolean hasParent() {
        return parent == null ? false : true;
    }

    public boolean hasChildren() {
        return children.size() == 0 ? false : true;
    }

    public Map<EProperty, String> getProperties() {
        return properties;
    }

    public EType getType() {
        return type;
    }

    public String toString() {
        return "[Node] " + type.getElementName(); //$NON-NLS-1$
    }

    /**
     * Sets the searchMatcher.
     * 
     * @param searchMatcher the searchMatcher to set
     */
    public void setSearchMatchFlag(boolean searchMatcher) {
        this.searchMatchFlag = searchMatcher;
        setParentMatchFlag(this, searchMatcher);
    }

    private void setParentMatchFlag(Node childNode, boolean flag) {
        if (childNode.hasParent()) {
            Node tempNode = childNode.parent;
            if (!(tempNode.isSearchMatchFlag() == flag)) {
                tempNode.setSearchMatchFlag(flag);
            } else {
                return;
            }
        } else {
            return;
        }
    }

    /**
     * Getter for searchMatcher.
     * 
     * @return the searchMatcher
     */
    public boolean isSearchMatchFlag() {
        return searchMatchFlag;
    }

}
