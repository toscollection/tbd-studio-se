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
package org.talend.designer.dbmap.ui.color;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: ColorProviderMapper.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class ColorProviderMapper {

    private static Map<ColorInfo, Color> colorsCache = new HashMap<ColorInfo, Color>();

    public static Color getColor(ColorInfo colorInfo) {
        Color colorFromCache = colorsCache.get(colorInfo);
        if (colorFromCache != null) {
            return colorFromCache;
        }
        Color color = new Color(Display.getCurrent(), colorInfo.getRed(), colorInfo.getGreen(), colorInfo.getBlue());
        colorsCache.put(colorInfo, color);
        return color;
    }

    public static Color getColor(Display display, int swtColor) {
        return display.getSystemColor(swtColor);
    }

    /**
     * DOC amaumont Comment method "release".
     */
    public static void releaseColors() {
        Collection<Color> colors = colorsCache.values();
        for (Color color : colors) {
            if (!color.isDisposed()) {
                color.dispose();
            }
        }
        colorsCache.clear();
    }

    public static void releaseColor(ColorInfo colorInfo) {
        Color color = colorsCache.get(colorInfo);
        if (!color.isDisposed()) {
            color.dispose();
        }
        colorsCache.remove(colorInfo);
    }

}
