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
package org.talend.administrator.common.view.displaytag.decorator;

import org.displaytag.util.HtmlAttributeMap;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class Link extends org.displaytag.decorator.data.Link {

    private HtmlAttributeMap attrValToTranslateMap = new HtmlAttributeMap();

    private boolean contentToTranslate;

    public Link() {
        super();
        addDefaultTranslatedAttributes();
    }

    public Link(Object content, String href, HtmlAttributeMap othersAttrValMap) {
        super(content, href, othersAttrValMap);
        addDefaultTranslatedAttributes();
    }

    public Link(Object content, String href) {
        super(content, href);
        addDefaultTranslatedAttributes();
    }

    public HtmlAttributeMap getAttrValToTranslateMap() {
        return attrValToTranslateMap;
    }

    public void setAttrValToTranslateMap(HtmlAttributeMap attrValToTranslateMap) {
        this.attrValToTranslateMap = attrValToTranslateMap;
    }

    public boolean isContentToTranslate() {
        return contentToTranslate;
    }

    public void setContentToTranslate(boolean contentToTranslate) {
        this.contentToTranslate = contentToTranslate;
    }

    @SuppressWarnings("unchecked")
    private void addDefaultTranslatedAttributes() {
        this.attrValToTranslateMap.put("title", "i-common.action.display");
    }
}
