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
package org.talend.designer.dbmap.external.data;

import java.io.Serializable;

import org.eclipse.swt.graphics.Rectangle;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: ExternalMapperUiProperties.java 34 2006-10-02 15:08:32Z amaumont $
 * 
 */
public class ExternalDbMapUiProperties implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6720565045409231632L;

    // must be null by default to unmarshal correctly
    private int[] weightsMainSashForm = new int[0];

    public static final int[] DEFAULT_WEIGHTS_MAIN_SASH_FORM = new int[] { 70, 30 };

    // must be null by default to unmarshal correctly
    private int[] weightsDatasFlowViewSashForm = new int[0];

    public static final int[] DEFAULT_WEIGHTS_DATAS_FLOW_SASH_FORM = new int[] { 45, 10, 45 };

    private Rectangle boundsMapper = new Rectangle(50, 50, 800, 600);

    private boolean shellMaximized = false;

    public Rectangle getBoundsMapper() {
        return this.boundsMapper;
    }

    public void setBoundsMapper(Rectangle boundsMapper) {
        this.boundsMapper = boundsMapper;
    }

    public boolean isShellMaximized() {
        return this.shellMaximized;
    }

    public void setShellMaximized(boolean shellMaximized) {
        this.shellMaximized = shellMaximized;
    }

    public int[] getWeightsDatasFlowViewSashForm() {
        return this.weightsDatasFlowViewSashForm;
    }

    /**
     * Setter for array of exactly three values which represents width pixels of each zone (input, var and output).
     * 
     * @param weightsDatasFlowViewSashForm array of three values which represents width pixels of each zone (input, var
     * and output)
     */
    public void setWeightsDatasFlowViewSashForm(int[] weightsDatasFlowViewSashForm) {
        // if (weightsDatasFlowViewSashForm.length != 3) {
        // throw new IllegalArgumentException("weightsDatasFlowViewSashForm must be an array of exactly 3 values");
        // }
        this.weightsDatasFlowViewSashForm = weightsDatasFlowViewSashForm;
    }

    public int[] getWeightsMainSashForm() {
        return this.weightsMainSashForm;
    }

    /**
     * Setter for array of exactly two values which represents height pixels of each zone (flow view, tabs view).
     * 
     * @param weightsMainSashForm
     */
    public void setWeightsMainSashForm(int[] weightsMainSashForm) {
        // if (weightsMainSashForm.length != 2) {
        // throw new IllegalArgumentException("weightsMainSashForm must be an array of exactly 2 values");
        // }
        this.weightsMainSashForm = weightsMainSashForm;
    }

}
