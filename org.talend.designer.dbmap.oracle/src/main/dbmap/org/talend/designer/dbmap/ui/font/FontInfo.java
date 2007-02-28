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
package org.talend.designer.dbmap.ui.font;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

/**
 * DOC mhelleboid class global comment. Detailled comment <br/>
 * 
 * $Id: FontInfo.java 1157 2006-12-21 09:53:10Z amaumont $
 * 
 */
public enum FontInfo {

    FONT_SYSTEM_BOLD(SWT.BOLD); // system font bold

    private FontData[] fontDatas;

    // private FontInfo(String name) {
    // fontDatas = getSystemFontDatas();
    // fontDatas[0].setName(name);
    // }

    /**
     * DOC amaumont Comment method "getSystemFontDatas".
     * 
     * @return
     */
    private FontData[] getSystemFontDatas() {
        return Display.getCurrent().getSystemFont().getFontData();
    }

    // private FontInfo(Integer height) {
    // fontDatas = getSystemFontDatas();
    // fontDatas[0].setHeight(height);
    // }

    private FontInfo(int style) {
        fontDatas = getSystemFontDatas();
        fontDatas[0].setStyle(style);
    }

    // private FontInfo(String name, int style) {
    // this(name);
    // fontDatas[0].setStyle(style);
    // }
    //
    // private FontInfo(int height, String name) {
    // this(name);
    // fontDatas[0].setHeight(height);
    // }
    //
    // private FontInfo(Integer height, int style) {
    // this(style);
    // fontDatas[0].setHeight(height);
    // }
    //
    // private FontInfo(FontData fontData) {
    // fontDatas = new FontData[] { fontData };
    // }

    public FontData[] getFontDatas() {
        return this.fontDatas;
    }

}
