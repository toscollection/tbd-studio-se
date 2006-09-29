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
package org.talend.administrator.common.util.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.struts.util.MessageResourcesFactory;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class TalendMessageResourcesFactory extends MessageResourcesFactory {

    private static final long serialVersionUID = 1L;

    public TalendMessageResources createResources(String config) {
        List<String> fileNames = new ArrayList<String>();
        if (config.indexOf(",") != -1) {
            StringTokenizer tokenizer = new StringTokenizer(config, ",");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                fileNames.add(token.trim());
            }
            return new TalendMessageResources(fileNames);
        } else {
            return new TalendMessageResources(this, config, this.returnNull);
        }
    }
}
