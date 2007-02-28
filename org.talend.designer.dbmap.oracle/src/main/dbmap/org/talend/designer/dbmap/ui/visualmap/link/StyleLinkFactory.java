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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.talend.commons.ui.swt.drawing.link.BezierHorizontalLink;
import org.talend.commons.ui.swt.drawing.link.ExtremityEastArrow;
import org.talend.commons.ui.swt.drawing.link.ExtremityWestArrow;
import org.talend.commons.ui.swt.drawing.link.IDrawableLink;
import org.talend.commons.ui.swt.drawing.link.IStyleLink;
import org.talend.commons.ui.swt.drawing.link.StyleLink;
import org.talend.commons.ui.swt.drawing.link.VerticalRoundedCornerLink;
import org.talend.designer.dbmap.model.tableentry.FilterTableEntry;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.ui.color.ColorInfo;
import org.talend.designer.dbmap.ui.color.ColorProviderMapper;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class StyleLinkFactory {

    // private static DrawableLinkFactory instance;

    private IStyleLink selectedZoneToZoneStyle;

    private IStyleLink selectedFilterStyle;

    private IStyleLink unselectedFilterStyle;

    private IStyleLink selectedSameInputZoneStyle;

    private IStyleLink unselectedSameInputZoneStyle;

    private IStyleLink unselectedZoneToZoneStyle;

    private IStyleLink selectedSameVarsZoneStyle;

    private IStyleLink unselectedSameVarsZoneStyle;

    /**
     * DOC amaumont LinkFactory constructor comment.
     */
    public StyleLinkFactory() {
        super();
        init();
    }

    /**
     * DOC amaumont Comment method "init".
     */
    private void init() {

        selectedZoneToZoneStyle = getSelectedZoneToOtherZoneStyle(ColorProviderMapper.getColor(ColorInfo.COLOR_SELECTED_ZONE_TO_ZONE_LINK));
        unselectedZoneToZoneStyle = getUnselectedZoneToZoneStyle();

        selectedFilterStyle = getSelectedFilterStyle();
        unselectedFilterStyle = getUnselectedFilterStyle();

        selectedSameInputZoneStyle = getSelectedSameInputZoneStyle();
        unselectedSameInputZoneStyle = getUnselectedSameInputZoneStyle();

        selectedSameVarsZoneStyle = getSelectedSameVarsZoneStyle();
        unselectedSameVarsZoneStyle = getUnselectedSameVarsZoneStyle();

    }

    // public static DrawableLinkFactory getInstance() {
    // if (instance == null) {
    // instance = new DrawableLinkFactory();
    // }
    // return instance;
    // }
    //
    // public IDrawableLink getDrawableLink(IMapperLink link) {
    // IDrawableLink drawableLink = null;
    // PointLinkDescriptor pointLinkDescriptorSource = link.getPointLinkDescriptorSource();
    // PointLinkDescriptor pointLinkDescriptorTarget = link.getPointLinkDescriptorTarget();
    // if (pointLinkDescriptorSource.getZone() != pointLinkDescriptorTarget.getZone()) {
    // drawableLink = new HorizontalBezierLink();
    // } else if (pointLinkDescriptorSource.getZone() == pointLinkDescriptorTarget.getZone()) {
    // drawableLink = new VerticalRoundedCornerLink();
    // }
    // return drawableLink;
    // }
    //
    public IStyleLink getStyleLink(IMapperLink link) {
        LinkState linkState = link.getState();
        PointLinkDescriptor pointLinkDescriptorSource = link.getPointLinkDescriptor1();
        PointLinkDescriptor pointLinkDescriptorTarget = link.getPointLinkDescriptor2();
        ITableEntry targetTableEntry = pointLinkDescriptorTarget.getTableEntry();

        boolean targetIsConstraint = false;
        if (targetTableEntry instanceof FilterTableEntry) {
            targetIsConstraint = true;
        }

        IStyleLink style = null;
        if (pointLinkDescriptorSource.getZone() != pointLinkDescriptorTarget.getZone()) {
            if (linkState == LinkState.SELECTED) {
                if (targetIsConstraint) {
                    style = selectedFilterStyle;
                } else {
                    style = selectedZoneToZoneStyle;
                }
            } else if (linkState == LinkState.UNSELECTED) {
                if (targetIsConstraint) {
                    style = unselectedFilterStyle;
                } else {
                    style = unselectedZoneToZoneStyle;
                }
            }
        } else if (pointLinkDescriptorSource.getZone() == Zone.INPUTS
                && pointLinkDescriptorSource.getZone() == pointLinkDescriptorTarget.getZone()) {
            if (linkState == LinkState.SELECTED) {
                style = selectedSameInputZoneStyle;
            } else if (linkState == LinkState.UNSELECTED) {
                style = unselectedSameInputZoneStyle;
            }
        } else if (pointLinkDescriptorSource.getZone() == Zone.VARS
                && pointLinkDescriptorSource.getZone() == pointLinkDescriptorTarget.getZone()) {
            if (linkState == LinkState.SELECTED) {
                style = selectedSameVarsZoneStyle;
            } else if (linkState == LinkState.UNSELECTED) {
                style = unselectedSameVarsZoneStyle;
            }
        }
        return style;
    }

    /**
     * DOC amaumont Comment method "getSelectedFilterStyle".
     * 
     * @return
     */
    private IStyleLink getSelectedFilterStyle() {
        StyleLink style = new StyleLink();
        setCommonsStyleProperties(style);
        style.setDrawableLink(getZoneToZoneLink(style));
        ExtremityEastArrow eastArrowSource = new ExtremityEastArrow(style);
        style.setExtremity1(eastArrowSource);
        ExtremityEastArrow eastArrowTarget = new ExtremityEastArrow(style);
        eastArrowTarget.setXOffset(-eastArrowTarget.getSize().x);
        style.setExtremity2(eastArrowTarget);
        style.setForegroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_SELECTED_FILTER_LINK));
        return style;
    }

    /**
     * DOC amaumont Comment method "getSelectedFilterStyle".
     * 
     * @return
     */
    private IStyleLink getUnselectedFilterStyle() {
        StyleLink style = new StyleLink();
        setCommonsStyleProperties(style);
        style.setDrawableLink(getZoneToZoneLink(style));
        // ExtremityEastArrow eastArrowSource = new ExtremityEastArrow(style);
        // style.setExtremity1(eastArrowSource);
        ExtremityEastArrow eastArrowTarget = new ExtremityEastArrow(style);
        eastArrowTarget.setXOffset(-eastArrowTarget.getSize().x);
        style.setExtremity2(eastArrowTarget);
        style.setForegroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_UNSELECTED_FILTER_LINK));
        return style;
    }

    private IStyleLink getSelectedZoneToOtherZoneStyle(Color foregroundColor) {
        StyleLink style = new StyleLink();
        setCommonsStyleProperties(style);
        style.setDrawableLink(getZoneToZoneLink(style));
        ExtremityEastArrow eastArrowSource = new ExtremityEastArrow(style);
        style.setExtremity1(eastArrowSource);
        ExtremityEastArrow eastArrowTarget = new ExtremityEastArrow(style);
        eastArrowTarget.setXOffset(-eastArrowTarget.getSize().x);
        style.setExtremity2(eastArrowTarget);
        style.setForegroundColor(foregroundColor);
        return style;
    }

    /**
     * DOC amaumont Comment method "getNotSelectedSameZoneStyle".
     * 
     * @return
     */
    private IStyleLink getUnselectedZoneToZoneStyle() {
        StyleLink style = new StyleLink();
        setCommonsStyleProperties(style);
        style.setDrawableLink(getZoneToZoneLink(style));
        ExtremityEastArrow eastArrowTarget = new ExtremityEastArrow(style);
        eastArrowTarget.setXOffset(-eastArrowTarget.getSize().x);
        style.setExtremity2(eastArrowTarget);
        style.setForegroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_UNSELECTED_ZONE_TO_ZONE_LINK));
        return style;
    }

    /**
     * DOC amaumont Comment method "getNotSelectedSameZoneStyle".
     * 
     * @return
     */
    private IStyleLink getUnselectedSameInputZoneStyle() {
        StyleLink style = new StyleLink();
        setCommonsStyleProperties(style);
        style.setDrawableLink(getSameZoneLink(style));
        ExtremityEastArrow eastArrowTarget = new ExtremityEastArrow(style);
        eastArrowTarget.setXOffset(-eastArrowTarget.getSize().x);
        style.setExtremity2(eastArrowTarget);
        style.setForegroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_UNSELECTED_LOOKUP_LINKS));
        return style;
    }

    /**
     * DOC amaumont Comment method "getSelectedSameZoneStyle".
     * 
     * @param foregroundColor
     * @return
     */
    public IStyleLink getSelectedSameInputZoneStyle() {
        StyleLink style = new StyleLink();
        setCommonsStyleProperties(style);
        IDrawableLink sameZoneLink = getSameZoneLink(style);
        style.setDrawableLink(sameZoneLink);
        ExtremityWestArrow westArrow = new ExtremityWestArrow(style);
        style.setExtremity1(westArrow);
        ExtremityEastArrow eastArrowTarget = new ExtremityEastArrow(style);
        eastArrowTarget.setXOffset(-eastArrowTarget.getSize().x);
        style.setExtremity2(eastArrowTarget);
        style.setForegroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_SELECTED_LOOKUP_LINKS));
        return style;
    }

    /**
     * DOC amaumont Comment method "getNotSelectedSameZoneStyle".
     * 
     * @return
     */
    private IStyleLink getUnselectedSameVarsZoneStyle() {
        StyleLink style = new StyleLink();
        setCommonsStyleProperties(style);
        style.setDrawableLink(getSameZoneLink(style));
        ExtremityWestArrow westArrow = new ExtremityWestArrow(style);
        style.setExtremity1(westArrow);
        ExtremityEastArrow eastArrowTarget = new ExtremityEastArrow(style);
        eastArrowTarget.setXOffset(-eastArrowTarget.getSize().x);
        style.setExtremity2(eastArrowTarget);
        style.setForegroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_UNSELECTED_LOOKUP_LINKS));
        return style;
    }

    /**
     * DOC amaumont Comment method "getSelectedSameZoneStyle".
     * 
     * @param foregroundColor
     * @return
     */
    public IStyleLink getSelectedSameVarsZoneStyle() {
        StyleLink style = new StyleLink();
        setCommonsStyleProperties(style);
        IDrawableLink sameZoneLink = getSameZoneLink(style);
        style.setDrawableLink(sameZoneLink);
        ExtremityWestArrow westArrow = new ExtremityWestArrow(style);
        style.setExtremity1(westArrow);
        ExtremityEastArrow eastArrowTarget = new ExtremityEastArrow(style);
        eastArrowTarget.setXOffset(-eastArrowTarget.getSize().x);
        style.setExtremity2(eastArrowTarget);
        style.setForegroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_SELECTED_LOOKUP_LINKS));
        return style;
    }

    /**
     * DOC amaumont Comment method "setCommonsStyleProperties".
     * 
     * @param style
     */
    private void setCommonsStyleProperties(StyleLink style) {
        style.setLineWidth(2);
        style.setLineCap(SWT.CAP_SQUARE);
        style.setLineJoin(SWT.JOIN_ROUND);
        // style.setLineStyle(SWT.LINE_DASH);
        // style.setLineDash(SWT.JOIN_BEVEL);
    }

    /**
     * DOC amaumont Comment method "getSameZoneLink".
     * 
     * @param style
     * @return
     */
    private IDrawableLink getSameZoneLink(StyleLink style) {
        return new VerticalRoundedCornerLink(style);
    }

    /**
     * DOC amaumont Comment method "getZoneToZoneLink".
     * 
     * @return
     */
    private IDrawableLink getZoneToZoneLink(StyleLink style) {
        // return new HorizontalBezierLink(style);
        return new BezierHorizontalLink(style);
    }

}
