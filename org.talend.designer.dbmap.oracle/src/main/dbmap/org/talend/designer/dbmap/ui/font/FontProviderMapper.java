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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: FontProviderMapper.java 1 2006-09-29 17:06:40Z nrousseau $
 * 
 */
public class FontProviderMapper {

    private static Map<FontInfo, Font> fontsCache = new HashMap<FontInfo, Font>();

    public static Font getFont(FontInfo fontInfo) {
        Font fontFromCache = fontsCache.get(fontInfo);
        if (fontFromCache != null) {
            return fontFromCache;
        }
        Font font = new Font(Display.getCurrent(), fontInfo.getFontDatas());
        fontsCache.put(fontInfo, font);
        return font;
    }

    /**
     * DOC amaumont Comment method "release".
     */
    public static void releaseFonts() {
        Collection<Font> fonts = fontsCache.values();
        for (Font font : fonts) {
            if (!font.isDisposed()) {
                font.dispose();
            }
        }
        fontsCache.clear();
    }

    public static void releaseFont(FontInfo fontInfo) {
        Font font = fontsCache.get(fontInfo);
        if (!font.isDisposed()) {
            font.dispose();
        }
        fontsCache.remove(fontInfo);
    }

}
