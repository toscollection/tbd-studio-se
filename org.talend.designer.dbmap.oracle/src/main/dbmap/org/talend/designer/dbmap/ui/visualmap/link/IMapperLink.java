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
package org.talend.designer.dbmap.ui.visualmap.link;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: IMapperLink.java 1157 2006-12-21 09:53:10Z amaumont $
 * 
 */
public interface IMapperLink {

    public void draw(GC gc, Rectangle boundsOfDrawing);

    public void calculate();

    public PointLinkDescriptor getPointLinkDescriptor1();

    public PointLinkDescriptor getPointLinkDescriptor2();

    /**
     * DOC amaumont Comment method "setSate".
     * 
     * @param linkState
     */
    public void setState(LinkState linkState);

    /**
     * DOC amaumont Comment method "getSate".
     * 
     * @return stateLink
     */
    public LinkState getState();

    /**
     * DOC amaumont Comment method "setLevel".
     * 
     * @param level
     */
    public void setLevel(Integer level);

    public Integer getLevel();

}
