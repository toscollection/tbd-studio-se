// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.dbmap.ui.tabs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.colorstyledtext.ColorManager;
import org.talend.commons.ui.swt.colorstyledtext.ColorStyledText;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: MapperColorStyledText.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class MapperColorStyledText extends ColorStyledText {

    public MapperColorStyledText(Composite parent, int style, ColorManager colorManager, String languageMode) {
        super(parent, style, colorManager, languageMode);
    }

    /**
     * This implementation is needed because synchronisation with TableEntries does not work using standard
     * implementation : <BR> - focus of TableEntry is lost when a character is typed <BR> - coloration does'nt work when
     * a character is typed in a TableEntry
     * 
     * <BR>
     * <BR>
     * <b>WARNING: listeners will not be notified in this implementation.</b>
     * 
     * @param text new widget content. Replaces existing content. Line styles that were set using StyledText API are
     * discarded. The current selection is also discarded.
     * @exception SWTException
     * <ul>
     * <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
     * <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
     * </ul>
     * @exception IllegalArgumentException
     * <ul>
     * <li>ERROR_NULL_ARGUMENT when string is null</li>
     * </ul>
     */
    public void setTextWithoutNotifyListeners(String text) {
        checkWidget();
        if (text == null) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        getContent().setText(text);
        colorize(getScanner());
    }

    public void replaceTextRangeWithoutNotifyListeners(int start, int length, String text) {
        checkWidget();
        if (text == null) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        int contentLength = getCharCount();
        // if(start < 0) {
        // start = 0;
        // }
        int end = start + length;
        // if(end > contentLength) {
        // end = contentLength-1;
        // }
        // System.out.println(start + " " + end + " " + contentLength + " '" + getText()+"'");
        if (start > end || start < 0 || end > contentLength) {
            SWT.error(SWT.ERROR_INVALID_RANGE);
        }
        getContent().replaceTextRange(start, length, text);
        colorize(getScanner());
    }

    // public void insertText(String text, int start) {
    // checkWidget();
    // if (text == null) {
    // SWT.error(SWT.ERROR_NULL_ARGUMENT);
    // }
    // getContent().replaceTextRange(start, 0, text);
    // colorize(getScanner());
    // }

}
