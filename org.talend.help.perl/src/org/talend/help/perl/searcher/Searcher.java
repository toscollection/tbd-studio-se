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
package org.talend.help.perl.searcher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.talend.help.perl.i18n.Messages;
import org.talend.help.perl.model.EType;
import org.talend.help.perl.model.Node;
import org.talend.help.perl.ui.BackForwardBar;
import org.talend.help.perl.ui.PerlHelpComposite;

/**
 * DOC . Detailled comment <br/>
 * 
 * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (������, 29 ���� 2006) nrousseau $
 * 
 */
public abstract class Searcher {

    protected TreeViewer treeViewer;

    protected Button searchBtn;

    protected Text searchText;

    protected BackForwardBar bfBar;

    protected static Node currentNode = null;

    protected boolean matchTextFlag = false;

    protected String currentPageContent = ""; //$NON-NLS-1$

    protected boolean funcSearchFlag = true;

    protected Map<Node, String> searcherCache = new HashMap<Node, String>();

    protected Node firstMatchNode = null;

    protected Node nextSiblingNode = null;

    protected PerlHelpComposite perlHelpComposite;

    public Searcher(TreeViewer viewer, PerlHelpComposite perlHelpComp) {
        this.treeViewer = viewer;
        this.perlHelpComposite = perlHelpComp;
        this.searchBtn = perlHelpComp.getSearchButton();
        this.searchText = perlHelpComp.getSearchText();
        this.bfBar = perlHelpComp.getBackForwardBar();
    }

    /**
     * all the process of searching.
     */
    public abstract void processSearch();

    /**
     * get the search result,put the search result on the cache.
     * 
     * @param startNode the start node
     * @param searchStr the string which need to search
     */
    protected abstract void preSetSearchCache(Node startNode, String searchStr);

    protected Object[] getNodeArray(Node node) {
        Node parent = node;
        LinkedList<Node> elements = new LinkedList<Node>();
        while (parent != null) {
            elements.addFirst(parent);
            parent = parent.getParent();
        }
        return elements.toArray();
    }

    /**
     * according the given node to get the function type node,if the parent node hasn't children which is function tye
     * and the return value is null.
     * 
     * @param typeNode the given node
     * @return function type node
     */
    protected Node getFunctionNode(Node typeNode) {
        Node childrenNode = null;
        if (typeNode.getType() != EType.FUNCTION) {
            if (typeNode.hasChildren()) {
                childrenNode = typeNode.getChildren().get(0);
                return getFunctionNode(childrenNode);
            }
        }
        return typeNode;
    }

    /**
     * judge the search wether achive the end.
     * 
     * @param node the node on the tree
     * @return the search achive the end,return true;else,return false
     */
    protected boolean isEndOfSearch(Node node) {
        if (node == null) {
            return true;
        }
        Node tempNode = node.getNextSibling();
        if (node == tempNode) {
            node = node.getParent();
            return node == null ? true : isEndOfSearch(node);
        } else {
            nextSiblingNode = tempNode;
            return false;
        }

    }

    /**
     * when click the next button util to the last,then locate to the top.
     * 
     * @return
     */
    protected Node repeatToStart() {
        if (searcherCache.size() != 0) {
            return (Node) treeViewer.getInput();
        } else {
            return null;
        }
    }

    public String getHtmlByKey(Node node) {
        return searcherCache.get(node);
    }

    /**
     * replace to the match htmlFileContent with the form of <span style=\"background:#ff9000\">htmlFileContent</span>
     * via the regex. For example: String str = "<a name="item_index">index STR,SUBSTR,POSITION</a>" String str2 =
     * getHighLightStr(str,index); and the str2 result is: "<a name="item_index"><span
     * style="background:#ff9000">index</span> STR,SUBSTR,POSITION</a>" *
     * 
     * @param htmlFileContent
     * @param searchStr
     * @return the string with highLine tag
     */
    protected String getHighLightStr(String htmlFileContent, String searchStr) {
        return ""; //$NON-NLS-1$
    }

    public void clearSearchCache() {
        Set<Node> nodeList = searcherCache.keySet();
        for (Node node : nodeList) {
            node.setSearchMatchFlag(false);
        }
        searcherCache.clear();
    }

    public static void openError(Exception e1) {
        Display workbenchDisplay = PlatformUI.getWorkbench().getDisplay();
        ErrorDialog.openError(workbenchDisplay.getActiveShell(),
                Messages.getString("Searcher.occuredError"), e1.getMessage(), null); //$NON-NLS-1$
    }

    /**
     * Sets the matchTextFlag.
     * 
     * @param matchTextFlag the matchTextFlag to set
     */
    public void setMatchTextFlag(boolean flag) {
        matchTextFlag = flag;
    }

    /**
     * Getter for matchTextFlag.
     * 
     * @return the matchTextFlag
     */
    public boolean getMatchTextFlag() {
        return matchTextFlag;
    }
}
