// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap.figures.manager;

import java.util.List;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.designer.gefabstractmap.figures.treetools.ToolBarButtonImageFigure;
import org.talend.designer.gefabstractmap.figures.treetools.zone.OutputZoneToolBar;
import org.talend.designer.pigmap.PigMapComponent;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapDataEditPart;
import org.talend.designer.pigmap.parts.PigMapDataManager;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.ui.tabs.MapperManager;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapOutputZoneToolBar extends OutputZoneToolBar {

    private PigMapData externalData;

    private PigMapDataEditPart externalPart;

    private PigMapComponent mapperComponent;

    private MapperManager mapperManger;

    private PigMapGraphicViewer graphicViewer;

    /**
     * DOC Administrator XmlMapOutputZoneToolBar constructor comment.
     * 
     * @param rootModelManager
     */
    public PigMapOutputZoneToolBar(PigMapDataManager rootModelManager) {
        super(rootModelManager);
        externalData = rootModelManager.getModel();
        externalPart = rootModelManager.getEditPart();
        graphicViewer = (PigMapGraphicViewer) rootModelManager.getGraphicalViewer();
        mapperManger = (MapperManager) rootModelManager.getMapperManger();
        mapperComponent = mapperManger.getMapperComponent();
        createZoneContent();
    }

    private void removeMetadataTableByName(String name) {
        if (name == null) {
            return;
        }
        IMetadataTable found = null;
        if (mapperComponent != null && mapperComponent.getMetadataList() != null) {
            for (IMetadataTable table : mapperComponent.getMetadataList()) {
                if (name.equals(table.getTableName())) {
                    found = table;
                    break;
                }
            }
            if (found != null) {
                mapperComponent.getMetadataList().remove(found);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.figures.treetools.zone.OutputZoneToolBar#addTable()
     */
    @Override
    protected void addTable() {
        final OutputTable createOutputTable = PigmapFactory.eINSTANCE.createOutputTable();
        CommandStack commandStack = externalPart.getViewer().getEditDomain().getCommandStack();
        commandStack.execute(new Command() {

            @Override
            public void execute() {
                if (externalPart.getViewer() instanceof PigMapGraphicViewer) {
                    PigMapGraphicViewer viewer = (PigMapGraphicViewer) externalPart.getViewer();
                    MapperManager manager = viewer.getMapperManager();
                    String outputName = manager.getMapperUI().openNewOutputCreationDialog();
                    if (outputName == null) {
                        return;
                    }
                    createOutputTable.setName(outputName);
                    externalData.getOutputTables().add(createOutputTable);
                    MetadataTable metadataTable = new MetadataTable();
                    metadataTable.setLabel(outputName);
                    metadataTable.setTableName(outputName);
                    mapperComponent.getMetadataList().add(metadataTable);
                    mapperComponent.getProcess().addUniqueConnectionName(outputName);

                    int indexOf = externalPart.getModelChildren().indexOf(createOutputTable);
                    if (indexOf != -1) {
                        externalPart.getViewer().select((EditPart) externalPart.getChildren().get(indexOf));
                    }

                    if (!min_size.isEnabled()) {
                        min_size.setEnabled(true);
                    }
                    mapperManger.getMapperUI().updateStatusBar();
                }

            }
        });

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.figures.treetools.zone.OutputZoneToolBar#removeTable()
     */
    @Override
    protected void removeTable() {

        CommandStack commandStack = graphicViewer.getEditDomain().getCommandStack();
        commandStack.execute(new Command() {

            @Override
            public void execute() {
                PigMapOutputTablePart currentSelectedOutputTable = graphicViewer.getFiguresManager()
                        .getCurrentSelectedOutputTable();
                if (currentSelectedOutputTable != null) {
                    OutputTable outputTable = (OutputTable) currentSelectedOutputTable.getModel();
                    int indexOf = externalPart.getModelChildren().indexOf(outputTable);

                    mapperComponent.getProcess().removeUniqueConnectionName(outputTable.getName());
                    removeMetadataTableByName(outputTable.getName());
                    for (TableNode tableNode : outputTable.getNodes()) {
                        PigMapUtil.detachConnectionsSouce(tableNode, externalData);
                    }
                    externalData.getOutputTables().remove(outputTable);
                    PigMapUtil.detachFilterSource(outputTable, externalData);
                    //
                    indexOf = indexOf - 1;
                    if (indexOf > -1 && (EditPart) externalPart.getChildren().get(indexOf) instanceof PigMapInputTablePart) {
                        graphicViewer.select((EditPart) externalPart.getChildren().get(indexOf));
                    } else if (indexOf > -1 && indexOf + 1 < externalPart.getChildren().size()
                            && (EditPart) externalPart.getChildren().get(indexOf + 1) instanceof PigMapOutputTablePart) {
                        graphicViewer.select((EditPart) externalPart.getChildren().get(indexOf + 1));
                    } else {
                        mapperManger.getMapperUI().getTabFolderEditors().getOutputMetaEditorView().setMetadataTableEditor(null);
                    }
                    //
                    mapperManger.getMapperUI().updateStatusBar();
                }
                if (externalData.getOutputTables().isEmpty() && min_size.isEnabled()) {
                    min_size.setEnabled(false);
                }
            }
        });

    }

    class AutoMapButton extends ToolBarButtonImageFigure {

        public AutoMapButton() {
            super(null);
            setText("Auto Map");
        }

        @Override
        public void toolBarButtonPressed(MouseEvent me) {
            super.toolBarButtonPressed(me);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.xmlmap.figures.treetools.zone.ZoneToolBar#moveUp()
     */
    @Override
    public void moveUp() {
        List<OutputTable> outputTables = externalData.getOutputTables();
        PigMapOutputTablePart currentSelectedOutputTable = graphicViewer.getFiguresManager().getCurrentSelectedOutputTable();
        if (currentSelectedOutputTable != null) {
            OutputTable selectedTable = (OutputTable) currentSelectedOutputTable.getModel();
            int index = outputTables.indexOf(selectedTable);
            if (index != -1 && index - 1 >= 0) {
                outputTables.remove(selectedTable);
                outputTables.add(index - 1, selectedTable);

                // index of modelchildren is different from index of table
                int indexOf = externalPart.getModelChildren().indexOf(selectedTable);
                if (indexOf != -1) {
                    graphicViewer.appendSelection((EditPart) externalPart.getChildren().get(indexOf));
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.xmlmap.figures.treetools.zone.ZoneToolBar#moveDown()
     */
    @Override
    public void moveDown() {
        List<OutputTable> outputTables = externalData.getOutputTables();
        PigMapOutputTablePart currentSelectedOutputTable = graphicViewer.getFiguresManager().getCurrentSelectedOutputTable();
        if (currentSelectedOutputTable != null) {
            OutputTable selectedTable = (OutputTable) currentSelectedOutputTable.getModel();
            int index = outputTables.indexOf(selectedTable);
            if (index != -1 && index + 1 < outputTables.size()) {
                outputTables.remove(selectedTable);
                outputTables.add(index + 1, selectedTable);

                // index of modelchildren is different from index of table
                int indexOf = externalPart.getModelChildren().indexOf(selectedTable);
                if (indexOf != -1) {
                    graphicViewer.appendSelection((EditPart) externalPart.getChildren().get(indexOf));
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.xmlmap.figures.treetools.zone.ZoneToolBar#minSize()
     */
    @Override
    public void minSize() {
        minimized = !minimized;
        EList<OutputTable> outputTables = externalData.getOutputTables();
        for (OutputTable outputTable : outputTables) {
            if (minimized != outputTable.isMinimized()) {
                outputTable.setMinimized(minimized);
            }
        }
    }

    @Override
    protected boolean isMinSizeEnable() {
        return externalData.getOutputTables().isEmpty();
    }

    @Override
    public boolean getMinSizeStatus() {
        boolean min = true;
        for (OutputTable table : externalData.getOutputTables()) {
            if (!table.isMinimized()) {
                min = false;
            }
        }
        return min;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.figures.treetools.zone.OutputZoneToolBar#autoMap()
     */
    @Override
    public void autoMap() {
        // AutoMapper mapper = new AutoMapper(externalData);
        // mapper.map();
    }

}
