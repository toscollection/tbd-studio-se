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
package org.talend.help.perl.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.help.perl.model.EImage;
import org.talend.help.perl.model.Node;

/**
 * DOC Administrator class global comment. Detailled comment <br/>
 * 
 * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (������, 29 ���� 2006) nrousseau $
 * 
 */
public class BackForwardBar {

    private Label backLabel = null;

    private Label forwardLabel = null;

    private TreeViewer treeViewer = null;

    private boolean selectTreeFlag = true;

    public BackForwardBar(Composite parent) {

        initBackForwardLabel(parent);
    }

    public void setBrowser(Browser htmlBrowser) {
    }

    private static final Image ENTER_BACKIMAGE = ImageProvider.getImage(EImage.ENTER_BACKIMAGE);

    private static final Image OUT_BACKIMAGE = ImageProvider.getImage(EImage.OUT_BACKIMAGE);

    private static final Image DISABLE_BACKIMAGE = ImageProvider.getImage(EImage.DISABLE_BACKIMAGE);

    private static final Image ENTER_FORWARDIMAGE = ImageProvider.getImage(EImage.ENTER_FORWARDIMAGE);

    private static final Image OUT_FORWARDIMAGE = ImageProvider.getImage(EImage.OUT_FORWARDIMAGE);

    private static final Image DISABLE_FORWARDIMAGE = ImageProvider.getImage(EImage.DISABLE_FORWARDIMAGE);

    private void initBackForwardLabel(Composite parentComp) {
        backLabel = new Label(parentComp, SWT.None);
        backLabel.setImage(DISABLE_BACKIMAGE);
        backLabel.setLayoutData(new GridData());
        backLabel.addMouseTrackListener(new MouseTrackAdapter() {

            public void mouseEnter(MouseEvent e) {
                if (currentNavIndex > 0) {
                    backLabel.setImage(ENTER_BACKIMAGE);
                }
            }

            public void mouseExit(MouseEvent e) {
                if (currentNavIndex > 0) {
                    backLabel.setImage(OUT_BACKIMAGE);
                }
            }

        });
        backLabel.addMouseListener(new MouseAdapter() {

            public void mouseDown(MouseEvent e) {
                if (0 == globalNavigation.size()) {
                    return;
                }
                int backIndex = currentNavIndex > 0 ? --currentNavIndex : currentNavIndex;
                if (currentNavIndex == 0) {
                    backLabel.setImage(DISABLE_BACKIMAGE);
                }
                setSelectTreeFlag(false);
                fireTreeState(globalNavigation.get(backIndex));
                setSelectTreeFlag(true);
                fireNavItemState();
            }

        });
        forwardLabel = new Label(parentComp, SWT.None);
        forwardLabel.setImage(DISABLE_FORWARDIMAGE);
        forwardLabel.setLayoutData(new GridData());
        forwardLabel.addMouseTrackListener(new MouseTrackAdapter() {

            public void mouseEnter(MouseEvent e) {
                if (currentNavIndex < (globalNavigation.size() - 1)) {
                    forwardLabel.setImage(ENTER_FORWARDIMAGE);
                }
            }

            public void mouseExit(MouseEvent e) {
                if (currentNavIndex < (globalNavigation.size() - 1)) {
                    forwardLabel.setImage(OUT_FORWARDIMAGE);
                }
            }

        });
        forwardLabel.addMouseListener(new MouseAdapter() {

            public void mouseDown(MouseEvent e) {
                if (0 == globalNavigation.size()) {
                    return;
                }
                int forwardIndex = currentNavIndex < (globalNavigation.size() - 1) ? ++currentNavIndex
                        : currentNavIndex;
                if (currentNavIndex == globalNavigation.size() - 1) {
                    forwardLabel.setImage(DISABLE_FORWARDIMAGE);
                }
                setSelectTreeFlag(false);
                fireTreeState(globalNavigation.get(forwardIndex));
                setSelectTreeFlag(true);
                fireNavItemState();
            }

        });
    }

    private List<Node> globalNavigation = new ArrayList<Node>();

    private int currentNavIndex = -1;

    /**
     * add the selected html page to navigation list.
     * 
     * @param htmlContent the content of selected html page
     */
    public void addToNav(Node navNode) {
        int size = globalNavigation.size();
        for (int i = size - 1; i > currentNavIndex; i--) {
            globalNavigation.remove(i);
        }
        globalNavigation.add(navNode);
        currentNavIndex++;
        fireNavItemState();
    }

    /**
     * clear the history record of browser.
     */
    public void clearHistory() {
        globalNavigation.clear();
        currentNavIndex = -1;
        fireNavItemState();
        backLabel.setImage(DISABLE_BACKIMAGE);
        forwardLabel.setImage(DISABLE_FORWARDIMAGE);
    }

    public void setTreeViewer(TreeViewer viewer) {
        this.treeViewer = viewer;
    }

    /**
     * auto selected the given node.
     * 
     * @param node
     */
    private void fireTreeState(Node node) {
        treeViewer.setExpandedElements(getNodeArray(node));
        treeViewer.setSelection(new StructuredSelection(new Object[] { node }), true);
        treeViewer.refresh();
    }

    private Object[] getNodeArray(Node node) {
        Node parent = node;
        LinkedList<Node> elements = new LinkedList<Node>();
        while (parent != null) {
            elements.addFirst(parent);
            parent = parent.getParent();
        }
        return elements.toArray();
    }

    private void fireNavItemState() {
        if (currentNavIndex > 0) {
            backLabel.setImage(OUT_BACKIMAGE);
        }
        if (currentNavIndex < (globalNavigation.size() - 1)) {
            forwardLabel.setImage(OUT_FORWARDIMAGE);
        }
    }

    public void setSelectTreeFlag(boolean selectTreeFlag) {
        this.selectTreeFlag = selectTreeFlag;
    }

    public boolean isSelectTreeFlag() {
        return selectTreeFlag;
    }

}
