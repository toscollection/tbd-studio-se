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
package org.talend.designer.dbmap.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.model.tableentry.InputColumnTableEntry;
import org.talend.designer.dbmap.model.tableentry.VarTableEntry;
import org.talend.designer.dbmap.ui.visualmap.link.IMapperLink;
import org.talend.designer.dbmap.ui.visualmap.link.LinkState;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: LinkManager.java 1309 2007-01-05 17:04:10Z amaumont $
 * 
 */
public class LinkManager {

    private static final Comparator<IMapperLink> COMPARATOR = new Comparator<IMapperLink>() {

        public int compare(IMapperLink link1, IMapperLink link2) {
            if (link1.getState() == link2.getState()) {
                return 0;
            }
            if (link1.getState() == LinkState.SELECTED) {
                return 1;
            }
            return -1;
        }

    };

    private List<IMapperLink> links = new ArrayList<IMapperLink>();

    private int currentNumberLinks = 0;

    private Map<ITableEntry, Set<ITableEntry>> targetToSources = new HashMap<ITableEntry, Set<ITableEntry>>();

    private Map<ITableEntry, Set<IMapperLink>> sourceTableEntryToLinks = new HashMap<ITableEntry, Set<IMapperLink>>();

    private Map<ITableEntry, Set<IMapperLink>> targetTableEntryToLinks = new HashMap<ITableEntry, Set<IMapperLink>>();

    // levels

    private Map<IMapperLink, Integer> linkToIndexLevel = new HashMap<IMapperLink, Integer>();

    private List<List<IMapperLink>> inputLinksForLevels = new ArrayList<List<IMapperLink>>();

    private List<List<IMapperLink>> varLinksForLevels = new ArrayList<List<IMapperLink>>();

    public LinkManager() {
        super();
        currentNumberLinks = 0;
    }

    /**
     * DOC amaumont Comment method "addLink".
     * 
     * @param link
     */
    public void addLink(IMapperLink link) {
        currentNumberLinks++;
        // System.out.println(currentNumberLinks + " links");

        links.add(link);
        ITableEntry sourceITableEntry = link.getPointLinkDescriptor1().getTableEntry();
        ITableEntry targetITableEntry = link.getPointLinkDescriptor2().getTableEntry();

        Set<ITableEntry> sourcesDataMapTableEntries = getSourcesCollection(targetITableEntry);
        sourcesDataMapTableEntries.add(sourceITableEntry);

        Set<IMapperLink> graphicalLinksFromTarget = getGraphicalLinksFromTarget(targetITableEntry);
        registerLevelForNewLink(link, graphicalLinksFromTarget);

        graphicalLinksFromTarget.add(link);

        Set<IMapperLink> graphicalLinksFromSources = getGraphicalLinksFromSource(sourceITableEntry);
        graphicalLinksFromSources.add(link);
    }

    /**
     * DOC amaumont Comment method "registerLevelForNewLink".
     * 
     * @param link
     * @param graphicalLinksFromTarget
     */
    private void registerLevelForNewLink(IMapperLink link, Set<IMapperLink> graphicalLinksFromTarget) {
        boolean hasAlreadyInputTarget = false;
        boolean hasAlreadyVarTarget = false;
        ITableEntry targetEntry = link.getPointLinkDescriptor2().getTableEntry();
        boolean hasSameZone = link.getPointLinkDescriptor1().getTableEntry().getClass() == targetEntry.getClass();

        if (hasSameZone) {
            boolean isInput = targetEntry instanceof InputColumnTableEntry;
            boolean isVar = targetEntry instanceof VarTableEntry;

            List<List<IMapperLink>> leveledLinks = null;
            if (isInput) {
                leveledLinks = inputLinksForLevels;
            }

            if (isVar) {
                leveledLinks = varLinksForLevels;
            }

            int lstSize = leveledLinks.size();
            for (int indexOfLeveledLink = 0; indexOfLeveledLink < lstSize; indexOfLeveledLink++) {
                List<IMapperLink> linksFromLevelsList = leveledLinks.get(indexOfLeveledLink);
                if (linksFromLevelsList != null && linksFromLevelsList.size() > 0) {
                    IMapperLink linkFromLevelsList = linksFromLevelsList.get(0);
                    ITableEntry sourceTableEntry = linkFromLevelsList.getPointLinkDescriptor1().getTableEntry();
                    ITableEntry targetTableEntry = linkFromLevelsList.getPointLinkDescriptor2().getTableEntry();
                    if (targetEntry == targetTableEntry) {

                        if (sourceTableEntry instanceof InputColumnTableEntry && targetTableEntry instanceof InputColumnTableEntry) {
                            hasAlreadyInputTarget = true;
                        }
                        if (sourceTableEntry instanceof VarTableEntry && targetTableEntry instanceof VarTableEntry) {
                            hasAlreadyVarTarget = true;
                        }
                        if (hasAlreadyInputTarget || hasAlreadyVarTarget) {
                            linksFromLevelsList.add(link);
                            link.setLevel(indexOfLeveledLink + 1);
                            break;
                        }
                    }
                }
            }

            if (isInput && !hasAlreadyInputTarget || isVar && !hasAlreadyVarTarget) {
                ArrayList<IMapperLink> list = new ArrayList<IMapperLink>();
                int firstEmptyIndex = searchFirstEmptyIndexLeveledList(leveledLinks);
                link.setLevel(firstEmptyIndex + 1);
                list.add(link);
                if (firstEmptyIndex < leveledLinks.size()) {
                    leveledLinks.set(firstEmptyIndex, list);
                } else {
                    leveledLinks.add(list);
                }
            }
        }
    }

    /**
     * DOC amaumont Comment method "searchFirstFreeIndexLeveledList".
     * 
     * @param leveledLinks
     */
    private int searchFirstEmptyIndexLeveledList(List<List<IMapperLink>> leveledLinks) {

        int freeIndex = leveledLinks.size();

        int lstSize = leveledLinks.size();
        for (int i = 0; i < lstSize; i++) {
            if (leveledLinks.get(i) == null) {
                freeIndex = i;
                break;
            }

        }
        return freeIndex;

    }

    /**
     * DOC amaumont Comment method "addLink".
     * 
     * @param link
     */
    public void removeLink(IMapperLink link) {
        currentNumberLinks--;

        links.remove(link);
        ITableEntry sourceITableEntry = link.getPointLinkDescriptor1().getTableEntry();
        ITableEntry targetITableEntry = link.getPointLinkDescriptor2().getTableEntry();
        Set<ITableEntry> targetDataMapTableEntries = getSourcesCollection(targetITableEntry);
        targetDataMapTableEntries.remove(sourceITableEntry);
        Set<IMapperLink> sourceGraphicalLinks = getGraphicalLinksFromSource(sourceITableEntry);
        sourceGraphicalLinks.remove(link);
        getGraphicalLinksFromTarget(targetITableEntry).remove(link);

        unregisterLevelForRemovedLink(link, sourceGraphicalLinks);

    }

    /**
     * DOC amaumont Comment method "unregisterLevelForRemovedLink".
     * 
     * @param link
     * @param sourceGraphicalLinks
     */
    private void unregisterLevelForRemovedLink(IMapperLink link, Set<IMapperLink> sourceGraphicalLinks) {
        ITableEntry targetEntry = link.getPointLinkDescriptor2().getTableEntry();
        boolean hasSameZone = link.getPointLinkDescriptor1().getTableEntry().getClass() == targetEntry.getClass();

        if (hasSameZone) {
            boolean isInput = targetEntry instanceof InputColumnTableEntry;
            boolean isVar = targetEntry instanceof VarTableEntry;

            List<List<IMapperLink>> leveledLinks = null;
            if (isInput) {
                leveledLinks = inputLinksForLevels;
            }

            if (isVar) {
                leveledLinks = varLinksForLevels;
            }

            boolean breakAll = false;

            int lstSize = leveledLinks.size();
            for (int indexOfLeveledLink = 0; indexOfLeveledLink < lstSize; indexOfLeveledLink++) {
                List<IMapperLink> linksFromLevelsList = leveledLinks.get(indexOfLeveledLink);
                if (linksFromLevelsList != null && linksFromLevelsList.size() > 0) {

                    int lstSizeInternal = linksFromLevelsList.size();
                    for (int i = 0; i < lstSizeInternal; i++) {
                        IMapperLink currentLink = linksFromLevelsList.get(i);
                        if (currentLink == link) {
                            linksFromLevelsList.remove(i);
                            if (linksFromLevelsList.size() == 0) {
                                leveledLinks.set(indexOfLeveledLink, null);
                            }
                            breakAll = true;
                            break;
                        }
                    } // for (int i = 0; i < lstSizeInternal; i++) {
                    if (breakAll) {
                        break;
                    }
                }
            } // for (int indexOfLeveledLink = 0; indexOfLeveledLink < lstSize; indexOfLeveledLink++) {
        }

    } // method

    /**
     * DOC amaumont Comment method "getGraphicalLinks".
     * 
     * @param targetTableEntry
     * @return
     */
    private Set<IMapperLink> getGraphicalLinksFromTarget(ITableEntry dataMapTableEntry) {
        Set<IMapperLink> graphicalLinks = targetTableEntryToLinks.get(dataMapTableEntry);
        if (graphicalLinks == null) {
            graphicalLinks = new HashSet<IMapperLink>();
            targetTableEntryToLinks.put(dataMapTableEntry, graphicalLinks);
        }
        return graphicalLinks;
    }

    /**
     * DOC amaumont Comment method "getGraphicalLinks".
     * 
     * @param targetTableEntry
     * @return
     */
    public Set<IMapperLink> getLinksFromTarget(ITableEntry dataMapTableEntry) {
        return new HashSet<IMapperLink>(getGraphicalLinksFromTarget(dataMapTableEntry));
    }

    /**
     * DOC amaumont Comment method "getGraphicalLinks".
     * 
     * @param targetTableEntry
     * @return
     */
    private Set<IMapperLink> getGraphicalLinksFromSource(ITableEntry dataMapTableEntry) {
        Set<IMapperLink> graphicalLinks = sourceTableEntryToLinks.get(dataMapTableEntry);
        if (graphicalLinks == null) {
            graphicalLinks = new HashSet<IMapperLink>();
            sourceTableEntryToLinks.put(dataMapTableEntry, graphicalLinks);
        }
        return graphicalLinks;
    }

    public Set<IMapperLink> getLinksFromSource(ITableEntry dataMapTableEntry) {
        return new HashSet<IMapperLink>(getGraphicalLinksFromSource(dataMapTableEntry));
    }

    /**
     * DOC amaumont Comment method "getSourcesCollection".
     * 
     * @param targetITableEntry
     * @return
     */
    private Set<ITableEntry> getSourcesCollection(ITableEntry targetITableEntry) {
        Set<ITableEntry> targetDataMapTableEntries = targetToSources.get(targetITableEntry);
        if (targetDataMapTableEntries == null) {
            targetDataMapTableEntries = new HashSet<ITableEntry>();
            targetToSources.put(targetITableEntry, targetDataMapTableEntries);
        }
        return targetDataMapTableEntries;
    }

    /**
     * DOC amaumont Comment method "clearLinks".
     */
    public void clearLinks() {
        links.clear();
        targetToSources.clear();
        linkToIndexLevel.clear();
    }

    /**
     * DOC amaumont Comment method "getLinks".
     * 
     * @return
     */
    public List<IMapperLink> getLinks() {
        return this.links;
    }

    /**
     * DOC amaumont Comment method "getSourcesForTarget".
     * 
     * @param dataMapTableEntry
     */
    public Set<ITableEntry> getSourcesForTarget(ITableEntry dataMapTableEntry) {
        return Collections.unmodifiableSet(getSourcesCollection(dataMapTableEntry));

    }

    /**
     * DOC amaumont Comment method "orderLinks".
     */
    public void orderLinks() {
        Collections.sort(links, COMPARATOR);
    }

    /**
     * Getter for currentNumberLinks.
     * 
     * @return the currentNumberLinks
     */
    public int getCurrentNumberLinks() {
        return this.currentNumberLinks;
    }

    /**
     * Get the count of inputs levels.
     * 
     * @return the count of inputs levels
     */
    public int getCountOfInputLevels() {
        return this.inputLinksForLevels.size();
    }

}
