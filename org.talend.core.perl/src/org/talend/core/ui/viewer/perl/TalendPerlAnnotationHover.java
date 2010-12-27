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

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.epic.perleditor.editors.util.MarkerUtil;

/**
 * Modificatition of the class from epic to be able to use only in a viewer. <br>
 */
public class TalendPerlAnnotationHover implements IAnnotationHover {

    static final int MAX_INFO_LENGTH = 80;

    private TalendPerlSourceViewer viewer;

    public TalendPerlAnnotationHover(TalendPerlSourceViewer viewer) {
        super();
        this.viewer = viewer;
    }

    /**
     * @see org.eclipse.jface.text.source.IAnnotationHover#getHoverInfo(org.eclipse.jface.text.source.ISourceViewer,
     * int)
     */

    public String getHoverInfo(ISourceViewer viewer, int line) {
        String info = null;

        IResource resource = this.viewer.getFile();

        List markers = MarkerUtil.getMarkersForLine(resource, line + 1);
        if (markers != null) {
            info = ""; //$NON-NLS-1$
            for (int i = 0; i < markers.size(); i++) {
                IMarker marker = (IMarker) markers.get(i);
                String message = marker.getAttribute(IMarker.MESSAGE, (String) null);
                if (message != null && message.trim().length() > 0) {

                    if (message.length() > MAX_INFO_LENGTH) {
                        message = splitMessage(message);
                    }
                    info += message;

                    if (i != markers.size() - 1) {
                        info += "\n"; //$NON-NLS-1$
                    }
                }
            }
        }
        return info;
    }

    private String splitMessage(String message) {
        String result = ""; //$NON-NLS-1$

        if (message.length() <= MAX_INFO_LENGTH) {
            return message;
        }

        String tmpStr = new String(message);

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

        return result;
    }

}
