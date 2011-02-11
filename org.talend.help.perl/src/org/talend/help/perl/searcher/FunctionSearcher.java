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

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.talend.help.perl.PerlHelpConstant;
import org.talend.help.perl.i18n.Messages;
import org.talend.help.perl.model.EProperty;
import org.talend.help.perl.model.EType;
import org.talend.help.perl.model.Node;
import org.talend.help.perl.ui.PerlHelpComposite;

/**
 * DOC Administrator class global comment. Detailled comment <br/>
 * 
 * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (������, 29 ���� 2006) nrousseau $
 * 
 */
public class FunctionSearcher extends Searcher {

    public FunctionSearcher(TreeViewer viewer, PerlHelpComposite perHelpComp) {
        super(viewer, perHelpComp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.help.perl.searcher.Searcher#processSearch()
     */
    public void processSearch() {
        currentNode = null;
        if (PerlHelpConstant.NEXT_ACTIONTYPE == perlHelpComposite.getActionType()) {
            TreeSelection selection = (TreeSelection) treeViewer.getSelection();
            currentNode = (Node) selection.getFirstElement();
            if (!isEndOfSearch(currentNode)) {
                currentNode = startSearch(nextSiblingNode, searchText.getText());
            } else {
                // currentNode = repeatToStart();
            }
        } else {
            if ("".equals(searchText.getText())) { //$NON-NLS-1$
                return;
            }
            currentNode = (Node) treeViewer.getInput();
            BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {

                public void run() {
                    currentNode = startSearch(currentNode, searchText.getText());
                }
            });
            if (searcherCache.size() != 0) {
                bfBar.clearHistory();
            }
            if (currentNode != null) {
                searchBtn.setText(Messages.getString("PlainSearcher.nextButton")); //$NON-NLS-1$
                perlHelpComposite.setActionType(PerlHelpConstant.NEXT_ACTIONTYPE);
            }
        }
        treeViewer.setExpandedElements(getNodeArray(currentNode));
        treeViewer.setSelection(new StructuredSelection(new Object[] { currentNode }), true);
        treeViewer.refresh();
    }

    /**
     * search the result which can match with searchString from the starNode.
     * 
     * @param startNode the start node on the tree
     * @param searchString the string which need to match
     * @return the match node on the tree
     */
    private Node startSearch(Node startNode, String searchStr) {

        boolean searchFlag = true;
        if (searcherCache.size() == 0) {
            preSetSearchCache(startNode, searchStr);
            return firstMatchNode;
        }
        Node node = null;
        while (searchFlag && startNode != null) {
            node = getFunctionNode(startNode);
            if (node.getType() == EType.FUNCTION) {
                if (searcherCache.containsKey(node)) {
                    return node;
                }
            }
            if (!isEndOfSearch(node)) {
                startNode = nextSiblingNode;
            } else {
                searchFlag = false;
            }
        }
        if (searcherCache.size() != 0) {
            Node repeatNode = repeatToStart();
            return startSearch(repeatNode, searchStr);
        }
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.help.perl.searcher.Searcher#preSetSearchCache(org.talend.help.perl.model.Node, java.lang.String)
     */
    protected void preSetSearchCache(Node startNode, String searchStr) {
        boolean searchFlag = true;
        firstMatchNode = null;
        Node node = startNode;
        while (searchFlag && node != null) {
            node = getFunctionNode(node);
            if (node.getType() == EType.FUNCTION) {
                String anchorLabel = node.getProperties().get(EProperty.LABEL);
                if (anchorLabel.startsWith(searchStr.toLowerCase())) {
                    node.setSearchMatchFlag(true);
                    firstMatchNode = firstMatchNode == null ? node : firstMatchNode;
                    searcherCache.put(node, currentPageContent);

                }
            }
            if (!isEndOfSearch(node)) {
                node = nextSiblingNode;
            } else {
                searchFlag = false;
            }
        }
        setMatchTextFlag(false);
    }

    public String getHtmlByKey(Node node) {
        return searcherCache.get(node);
    }

}
