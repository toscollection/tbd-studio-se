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
package org.talend.designer.dbmap.external.converter;

import java.util.List;

import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.components.IODataComponentContainer;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IExternalNode;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: ExternalUtilities.java 1366 2007-01-10 08:40:28Z nrousseau $
 * 
 */
public class ExternalNodeUtils {

    /**
     * DOC amaumont Comment method "prepareExternalNodeReadyToOpen".
     * 
     * @param node
     * @param externalNode
     * @return
     */
    public static IExternalNode prepareExternalNodeReadyToOpen(IExternalNode externalNode) {

        IODataComponentContainer inAndOut = new IODataComponentContainer();

        List<IODataComponent> inputs = inAndOut.getInputs();
        if (externalNode.getIncomingConnections() != null) {
            for (IConnection currentConnection : externalNode.getIncomingConnections()) {
                IODataComponent component = new IODataComponent(currentConnection);
                inputs.add(component);
            }
        }

        List<IODataComponent> outputs = inAndOut.getOuputs();
        if (externalNode.getOutgoingConnections() != null) {

            for (IConnection currentConnection : externalNode.getOutgoingConnections()) {
                IODataComponent component = new IODataComponent(currentConnection);
                outputs.add(component);
            }
        }

        externalNode.setIODataComponents(inAndOut);

        return externalNode;
    }
}
