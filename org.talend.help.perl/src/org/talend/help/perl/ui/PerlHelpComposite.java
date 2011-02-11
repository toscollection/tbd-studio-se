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

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.talend.help.perl.PerlHelpConstant;
import org.talend.help.perl.i18n.Messages;
import org.talend.help.perl.model.EProperty;
import org.talend.help.perl.model.EType;
import org.talend.help.perl.model.Node;
import org.talend.help.perl.reader.DocParser;
import org.talend.help.perl.reader.IndexParser;
import org.talend.help.perl.searcher.FunctionSearcher;
import org.talend.help.perl.searcher.PlainSearcher;
import org.talend.help.perl.searcher.Searcher;

/**
 * PerlHelpComposite.java.
 * 
 */
/**
 * @author xue
 * 
 */
public class PerlHelpComposite extends Composite {

    /**
     * FunctionFilter.
     * 
     */
    private final class FunctionFilter extends ViewerFilter {

        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
            Node node = (Node) element;
            boolean flag = (PerlHelpConstant.SEARCH_ACTIONTYPE == getActionType()) ? true : node.isSearchMatchFlag();
            return flag;
        }
    }

    private TreeViewer viewer;

    private Text searchText;

    private Button searchButton;

    private static final String LABEL_BLANK = ""; //$NON-NLS-1$

    private int actionType = PerlHelpConstant.SEARCH_ACTIONTYPE;

    private static final String HEADTAG_HTML = "<html><head></head><body>"; //$NON-NLS-1$

    private static final String TAILTAG_HTML = "</body></html>"; //$NON-NLS-1$

    private Searcher searcher = null;

    private Searcher funcSearcher = null;

    private Searcher plainSearcher = null;

    private Browser htmlBrowser;

    private BackForwardBar backForwardBar;

    private Button funcBtn = null;

    private Button plainBtn = null;

    public PerlHelpComposite(Composite parent, int style) {
        super(parent, style);
        init();
    }

    /**
     * DOC Administrator PerlHelpComposite class global comment. Detailled comment <br/>
     * 
     * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (������, 29 ���� 2006) nrousseau $
     * 
     */
    class RadioSelection extends SelectionAdapter {

        public void widgetSelected(SelectionEvent e) {
            if (searcher != null) {
                searcherReset();
            }
            searcher = funcBtn.getSelection() ? funcSearcher : plainSearcher;
            viewer.refresh();
        }
    }

    private Composite creatSearchRadioComp(Composite parentComp) {
        Composite searchRadioComp = new Composite(parentComp, SWT.None);
        searchRadioComp.setLayout(new RowLayout());
        funcBtn = new Button(searchRadioComp, SWT.RADIO);
        funcBtn.setText(Messages.getString("PerlHelpComposite.functionButton")); //$NON-NLS-1$
        funcBtn.setSelection(true);
        funcBtn.addSelectionListener(new RadioSelection());
        plainBtn = new Button(searchRadioComp, SWT.RADIO);
        plainBtn.setText(Messages.getString("PerlHelpComposite.plainTextButton")); //$NON-NLS-1$
        plainBtn.addSelectionListener(new RadioSelection());
        return searchRadioComp;
    }

    private Composite createLeftComponent() {
        Composite treeComposite = new Composite(this, SWT.None);
        treeComposite.setLayout(new GridLayout());
        Composite searchTextComp = new Composite(treeComposite, SWT.None);
        searchTextComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        searchTextComp.setLayout(new GridLayout(4, false));
        searchText = new Text(searchTextComp, SWT.BORDER);
        GridData textGd = new GridData();
        textGd.widthHint = 150;
        searchText.setLayoutData(textGd);
        searchButton = new Button(searchTextComp, SWT.None);
        GridData buttonGd = new GridData();
        // buttonGd.horizontalSpan = 1;
        searchButton.setLayoutData(buttonGd);
        searchButton.setText(Messages.getString("PerlHelpComposite.searchText")); //$NON-NLS-1$
        backForwardBar = new BackForwardBar(searchTextComp);
        creatSearchRadioComp(treeComposite).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        viewer = new TreeViewer(treeComposite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));

        // viewer.setUseHashlookup(true);
        viewer.setLabelProvider(new TreeLabelProvider());
        viewer.setContentProvider(new TreeContentProvider());
        viewer.addFilter(new FunctionFilter());
        backForwardBar.setTreeViewer(viewer);
        viewer.addSelectionChangedListener(new ISelectionChangedListener() {

            public void selectionChanged(SelectionChangedEvent event) {
                StructuredSelection selection = (StructuredSelection) event.getSelection();
                Node node = (Node) selection.getFirstElement();
                if (node != null && node.getType() == EType.FUNCTION) {
                    Node anchorNode = node.getChildren().get(0);
                    String anchor = anchorNode.getProperties().get(EProperty.VALUE);
                    String htmlContent;
                    String midContent = ""; //$NON-NLS-1$
                    try {
                        midContent = DocParser.getInstance().getDoc(anchor);
                    } catch (IOException e) {
                        openError(e);
                    }
                    if (searcher.getMatchTextFlag()) {
                        htmlContent = HEADTAG_HTML + searcher.getHtmlByKey(node) + TAILTAG_HTML;
                        htmlBrowser.setText(htmlContent);
                        // searchAdapter.setMatchTextFlag(false);
                    } else {

                        htmlContent = HEADTAG_HTML + midContent + TAILTAG_HTML;
                        htmlBrowser.setText(htmlContent);
                    }
                    setFLText(midContent);
                    // NavNode navNode = getNavNode(node, htmlContent);
                    if (backForwardBar.isSelectTreeFlag()) {
                        backForwardBar.addToNav(node);
                        backForwardBar.setSelectTreeFlag(true);
                    }
                    return;
                }
                htmlBrowser.setText(""); //$NON-NLS-1$
                setFLText(""); //$NON-NLS-1$
            }
        });
        try {
            viewer.setInput(IndexParser.parse());
        } catch (Exception e1) {
            openError(e1);
        }
        searchText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                searcherReset();
                viewer.refresh();
            }
        });
        searchButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                searcher.processSearch();
            }
        });
        this.getShell().setDefaultButton(searchButton);
        funcSearcher = new FunctionSearcher(viewer, this);
        plainSearcher = new PlainSearcher(viewer, this);
        searcher = funcSearcher;
        return treeComposite;
    }

    public BackForwardBar getBackForwardBar() {
        return this.backForwardBar;
    }

    public Button getSearchButton() {
        return this.searchButton;
    }

    public Text getSearchText() {
        return this.searchText;
    }

    /**
     * reset the search result.
     */
    private void searcherReset() {
        searchButton.setText(Messages.getString("PerlHelpComposite.searchText")); //$NON-NLS-1$
        this.setActionType(PerlHelpConstant.SEARCH_ACTIONTYPE);
        searcher.setMatchTextFlag(false);
        searcher.clearSearchCache();
    }

    private static void openError(Exception e1) {
        Display workbenchDisplay = PlatformUI.getWorkbench().getDisplay();
        ErrorDialog.openError(workbenchDisplay.getActiveShell(),
                Messages.getString("Searcher.occuredError"), e1.getMessage(), null); //$NON-NLS-1$
    }

    private void init() {

        Composite treeComposite = createLeftComponent();
        final Sash sash = new Sash(this, SWT.VERTICAL);
        Composite rihtComposite = creatRightComp();

        final FormLayout form = new FormLayout();
        this.setLayout(form);

        FormData viewerData = new FormData();
        viewerData.left = new FormAttachment(0, 0);
        viewerData.right = new FormAttachment(sash, 0);
        viewerData.top = new FormAttachment(0, 0);
        viewerData.bottom = new FormAttachment(100, 0);
        treeComposite.setLayoutData(viewerData);

        final int limit = 20, percent = 45;
        final FormData sashData = new FormData();
        sashData.left = new FormAttachment(percent, 0);
        sashData.top = new FormAttachment(0, 0);
        sashData.bottom = new FormAttachment(100, 0);
        sash.setLayoutData(sashData);
        sash.addListener(SWT.Selection, new Listener() {

            public void handleEvent(Event e) {
                Rectangle sashRect = sash.getBounds();
                Rectangle shellRect = getClientArea();
                int right = shellRect.width - sashRect.width - limit;
                e.x = Math.max(Math.min(e.x, right), limit);
                if (e.x != sashRect.x) {
                    sashData.left = new FormAttachment(0, e.x);
                    layout();
                }
            }
        });

        FormData textData = new FormData();
        textData.left = new FormAttachment(sash, 0);
        textData.right = new FormAttachment(100, 0);
        textData.top = new FormAttachment(0, 0);
        textData.bottom = new FormAttachment(100, 0);
        rihtComposite.setLayoutData(textData);

    }

    private Composite creatRightComp() {
        Composite rightComp = new Composite(this, SWT.None);
        GridLayout layout = new GridLayout();
        rightComp.setLayout(layout);
        htmlBrowser = new Browser(rightComp, SWT.BORDER);
        htmlBrowser.setText(""); //$NON-NLS-1$
        htmlBrowser.setLayoutData(new GridData(GridData.FILL_BOTH));
        backForwardBar.setBrowser(htmlBrowser);
        creatFLCopyComp(rightComp).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return rightComp;

    }

    private Text flText = null;

    private Composite creatFLCopyComp(Composite parentComp) {
        Composite flCopyComp = new Composite(parentComp, SWT.None);
        GridLayout layout = new GridLayout(3, false);
        flCopyComp.setLayout(layout);
        Label codeLabel = new Label(flCopyComp, SWT.None);
        codeLabel.setText(Messages.getString("PerlHelpComposite.codeKey")); //$NON-NLS-1$
        codeLabel.setLayoutData(new GridData());
        flText = new Text(flCopyComp, SWT.BORDER);
        flText.setText(""); //$NON-NLS-1$
        GridData textGD = new GridData();
        textGD.widthHint = 180;
        flText.setLayoutData(textGD);
        Button copyBtn = new Button(flCopyComp, SWT.None);
        copyBtn.setText(Messages.getString("PerlHelpComposite.copy")); //$NON-NLS-1$
        copyBtn.setLayoutData(new GridData());
        copyBtn.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                Clipboard clipboard = new Clipboard(null);
                String plainText = flText.getText();
                String rtfText = "{\\rtf1\\b " + plainText + "}"; //$NON-NLS-1$ //$NON-NLS-2$
                TextTransfer textTransfer = TextTransfer.getInstance();
                RTFTransfer rftTransfer = RTFTransfer.getInstance();
                clipboard.setContents(new String[] { plainText, rtfText }, new Transfer[] { textTransfer, rftTransfer });
                clipboard.dispose();
            }
        });
        return flCopyComp;
    }

    private static final String TEXT_REGEX = "<a name=.*>(.*)</a>"; //$NON-NLS-1$

    /**
     * get the first line text content according the content of html page.
     * 
     * @param htmlContent the content of html page
     */
    private void setFLText(String htmlContent) {
        if ("".equals(htmlContent)) { //$NON-NLS-1$
            flText.setText(""); //$NON-NLS-1$
        } else {
            Matcher matcher = Pattern.compile(TEXT_REGEX).matcher(htmlContent);
            matcher.find();
            String tempText = matcher.group(0);
            String textStr = tempText.replaceAll(TEXT_REGEX, "$1"); //$NON-NLS-1$
            flText.setText(textStr);
        }

    }

    @Override
    public void dispose() {
        htmlBrowser = null;
        searchText = null;
        super.dispose();
    }

    /**
     * Sets the actionType.
     * 
     * @param actionType the actionType to set
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    /**
     * Getter for actionType.
     * 
     * @return the actionType
     */
    public int getActionType() {
        return actionType;
    }

}
