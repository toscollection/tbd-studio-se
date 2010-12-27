// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
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

import java.io.IOException;
import java.util.regex.Pattern;

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
import org.talend.help.perl.reader.DocParser;
import org.talend.help.perl.ui.PerlHelpComposite;

/**
 * DOC Administrator class global comment. Detailled comment <br/>
 * 
 * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (������, 29 ���� 2006) nrousseau $
 * 
 */
public class PlainSearcher extends Searcher {

    /**
     * DOC Administrator PlainSearcher constructor comment.
     */
    public PlainSearcher(TreeViewer viewer, PerlHelpComposite perHelpComp) {
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
            setMatchTextFlag(firstMatchNode == null ? false : true);
            return firstMatchNode;
        }
        Node node = null;
        while (searchFlag && startNode != null) {
            node = getFunctionNode(startNode);
            if (node.getType() == EType.FUNCTION) {
                // firstSelPageContent = searcherCache.get(node);
                if (searcherCache.containsKey(node)) {
                    setMatchTextFlag(true);
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
        String htmlFileContent = null;
        // Node tempNode = null;
        while (searchFlag && node != null) {
            node = getFunctionNode(node);
            if (node.getType() == EType.FUNCTION) {
                Node anchorNode = node.getChildren().get(0);
                String anchor = anchorNode.getProperties().get(EProperty.VALUE);
                try {
                    htmlFileContent = DocParser.getInstance().getDoc(anchor);
                    if (htmlFileContent.contains(searchStr)) {
                        currentPageContent = getHighLightStr(htmlFileContent, searchStr);
                        /*
                         * if the currentHtmlContent.equals(htmlFileContent)is true, the htmlFileContent is
                         * unchanged,because the searchString is key of html tag
                         */
                        if (!currentPageContent.equals(htmlFileContent)) {
                            // setMatchTextFlag(true);
                            node.setSearchMatchFlag(true);
                            firstMatchNode = firstMatchNode == null ? node : firstMatchNode;
                            searcherCache.put(node, currentPageContent);
                        }

                    }

                } catch (IOException e) {
                    openError(e);
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

    private static final String HEAD_REGEX = ">(([^>^<])*)("; //$NON-NLS-1$

    private static final String TAIL_REGEX = ")+(([^>^<])*)<"; //$NON-NLS-1$

    private static final String HEAD_REPLACE = ">$1<span style=\"background:#ff9000\">$3"; //$NON-NLS-1$

    private static final String TAIL_REPLACE = "</span>$4<"; //$NON-NLS-1$

    private String defaultStr = ""; //$NON-NLS-1$

    private Pattern pattern;

    protected String getHighLightStr(String htmlFileContent, String searchStr) {
        String regexStr;
        String replaceStr = HEAD_REPLACE + TAIL_REPLACE;
        if (!defaultStr.equals(searchStr)) {
            defaultStr = searchStr;
            regexStr = HEAD_REGEX + defaultStr + TAIL_REGEX;
            pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
        }
        // String regexStr = HEAD_REGEX + defaultStr + TAIL_REGEX;
        String resultStr = pattern.matcher(htmlFileContent).replaceAll(replaceStr);
        // String resultStr2 = htmlFileContent.replaceAll(regexStr, replaceStr);
        return resultStr;
    }

}
