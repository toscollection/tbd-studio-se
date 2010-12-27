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
package org.talend.core.ui.viewer.perl;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.Point;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;

/**
 * Modificatition of the class from epic to be able to use only in a viewer. <br>
 */
public class TalendPerlTextHover implements ITextHover {

    private static final int MAX_INFO_LENGTH = 80;

    private TalendPerlSourceViewer viewer;

    public TalendPerlTextHover(TalendPerlSourceViewer viewer) {
        super();
        this.viewer = viewer;
    }

    public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
        String text = getTextForHover(textViewer, hoverRegion);
        if (text == null) {
            if (hoverRegion.getLength() == 0 && "true".equals(System.getProperty("org.epic.perleditor.hoverPartitionType"))) { //$NON-NLS-1$ //$NON-NLS-2$
                return getPartitionHover(textViewer, hoverRegion);
            } else
                return null;
        }

        try {
            ResourceBundle rb = ResourceBundle.getBundle("org.epic.perleditor.editors.quickreference"); //$NON-NLS-1$

            // Check if only a word (without spaces or tabs) has
            // been selected
            if (text.length() > 0 && text.indexOf(" ") < 0 && text.indexOf("\t") < 0) { //$NON-NLS-1$ //$NON-NLS-2$
                try {
                    String value = rb.getString(text);
                    return splitMessage(value);
                } catch (MissingResourceException e) {
                    // Can happen if key does not exist
                    return null;
                }
            } else {
                try {
                    // If no keyword description was found,
                    // try to show marker info
                    IAnnotationHover markerAnnotation = new TalendPerlAnnotationHover(viewer);
                    int line = textViewer.getDocument().getLineOfOffset(hoverRegion.getOffset());
                    return markerAnnotation.getHoverInfo((ISourceViewer) textViewer, line);
                } catch (BadLocationException e) {
                    // should never occur
                    return null;
                }
            }
        } catch (MissingResourceException e) {
            // Properties file not available
            // e.printStackTrace();
            ExceptionHandler.process(e);
            return null;
        }
    }

    public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
        Point selection = textViewer.getSelectedRange();
        if (selection.x <= offset && offset < selection.x + selection.y) {
            return new Region(selection.x, selection.y);
        } else {
            return new Region(offset, 0);
        }
    }

    private String getPartitionHover(ITextViewer textViewer, IRegion hoverRegion) {
        try {
            IDocument doc = textViewer.getDocument();
            ITypedRegion partition = doc.getPartition(hoverRegion.getOffset());

            return "@" + hoverRegion.getOffset() + ": " + partition.getOffset() + ":" + partition.getLength() + ":" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                    + partition.getType() + " {" + doc.get(partition.getOffset(), partition.getLength()) + "}"; //$NON-NLS-1$ //$NON-NLS-2$
        } catch (BadLocationException e) {
            return null;
        }
    }

    private String getTextForHover(ITextViewer textViewer, IRegion hoverRegion) {
        if (hoverRegion == null || hoverRegion.getLength() <= 0)
            return null;

        try {
            return textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
        } catch (BadLocationException x) {
            // should never occur
            return null;
        }
    }

    private String splitMessage(String message) {
        String result = ""; //$NON-NLS-1$

        if (message.length() <= MAX_INFO_LENGTH) {
            return message;
        }

        String tmpStr;

        // Index of \n
        int crIndex = message.indexOf("\n"); //$NON-NLS-1$

        if (crIndex != -1) {
            tmpStr = message.substring(0, crIndex);
        } else {
            tmpStr = new String(message);
        }

        while (tmpStr.length() > MAX_INFO_LENGTH) {

            int spacepos = tmpStr.indexOf(" ", MAX_INFO_LENGTH); //$NON-NLS-1$

            if (spacepos != -1) {
                result += tmpStr.substring(0, spacepos) + "\n"; //$NON-NLS-1$
                tmpStr = tmpStr.substring(spacepos);
            } else {
                result += tmpStr.substring(0, MAX_INFO_LENGTH) + "\n"; //$NON-NLS-1$
                tmpStr = tmpStr.substring(MAX_INFO_LENGTH);
            }

        }

        result += tmpStr;

        if (crIndex != -1) {
            result += message.substring(crIndex);
        }

        return result;
    }
}
