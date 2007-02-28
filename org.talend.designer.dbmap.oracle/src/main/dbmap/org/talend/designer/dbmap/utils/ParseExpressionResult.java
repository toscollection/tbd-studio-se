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
package org.talend.designer.dbmap.utils;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: ParseExpressionResult.java 968 2006-12-12 10:59:26Z amaumont $
 * 
 */
public class ParseExpressionResult {

    private boolean atLeastOneLinkAdded;

    private boolean atLeastOneLinkRemoved;

    /**
     * DOC amaumont ProcessExpressionResult constructor comment.
     * 
     * @param atLeastOneLinkAdded
     * @param atLeastOneLinkRemoved
     */
    public ParseExpressionResult(boolean atLeastOneLinkAdded, boolean atLeastOneLinkRemoved) {
        super();
        this.atLeastOneLinkAdded = atLeastOneLinkAdded;
        this.atLeastOneLinkRemoved = atLeastOneLinkRemoved;
    }

    public boolean isAtLeastOneLinkHasBeenAddedOrRemoved() {
        return atLeastOneLinkAdded || atLeastOneLinkRemoved;
    }

    public boolean isAtLeastOneLinkAdded() {
        return this.atLeastOneLinkAdded;
    }

    public boolean isAtLeastOneLinkRemoved() {
        return this.atLeastOneLinkRemoved;
    }

}
