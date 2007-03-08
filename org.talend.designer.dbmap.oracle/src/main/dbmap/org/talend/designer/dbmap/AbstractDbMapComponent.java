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
package org.talend.designer.dbmap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.SystemException;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.AbstractExternalNode;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.Problem;
import org.talend.core.model.temp.ECodePart;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.designer.dbmap.external.data.ExternalDbMapData;
import org.talend.designer.dbmap.external.data.ExternalDbMapEntry;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.model.tableentry.TableEntryLocation;
import org.talend.designer.dbmap.utils.DataMapExpressionParser;
import org.talend.designer.dbmap.utils.problems.ProblemsAnalyser;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: MapperComponent.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public abstract class AbstractDbMapComponent extends AbstractExternalNode {

    private MapperMain mapperMain;

    private List<IMetadataTable> metadataListOut;

    private ExternalDbMapData externalData;

    /**
     * DOC amaumont MapperComponent constructor comment.
     */
    public AbstractDbMapComponent() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.AbstractExternalNode#initialize()
     */
    public void initialize() {
        initMapperMain();
        mapperMain.loadInitialParamters();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IExternalComponent#getPersistentData()
     */
    public Object getExternalData() {
        if (this.externalData == null) {
            this.externalData = new ExternalDbMapData();
        }
        return this.externalData;
    }

    private void initMapperMain() {
        mapperMain = new MapperMain(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IExternalComponent#open(org.eclipse.swt.widgets.Display)
     */
    public int open(final Display display) {
        // TimeMeasure.start("Total open");
        // TimeMeasure.display = false;
        initMapperMain();
        mapperMain.createModelFromExternalData(getIODataComponents(), getMetadataList(), externalData, true);
        Shell shell = mapperMain.createUI(display);
        // TimeMeasure.display = true;
        // TimeMeasure.end("Total open");
        while (!shell.isDisposed()) {
            try {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            } catch (Throwable e) {
                if (MapperMain.isStandAloneMode()) {
                    e.printStackTrace();
                } else {
                    ExceptionHandler.process(e);
                }
            }
        }
        if (MapperMain.isStandAloneMode()) {
            display.dispose();
        }
        refreshMapperConnectorData();
        return mapperMain.getMapperDialogResponse();
    }

    /**
     * DOC amaumont Comment method "refreshMapperConnectorData".
     */
    public void refreshMapperConnectorData() {
        mapperMain.loadModelFromInternalData();
        metadataListOut = mapperMain.getMetadataListOut();
        externalData = mapperMain.buildExternalData();
        sortOutputsConnectionsLikeVisualOrder();
    }

    /**
     * Sort outgoingConnections for code generation as visible output zone of tMap.
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private void sortOutputsConnectionsLikeVisualOrder() {

        if (!MapperMain.isStandAloneMode()) {

            List<IConnection> outgoingConnections = (List<IConnection>) getOutgoingConnections();
            Map<String, IConnection> connectionNameToOutgoingConnection = new HashMap<String, IConnection>();
            for (IConnection connection : outgoingConnections) {
                connectionNameToOutgoingConnection.put(connection.getName(), connection);
            }

            List<ExternalDbMapTable> outputTables = externalData.getOutputTables();
            outgoingConnections.clear();

            int lstSize = outputTables.size();
            for (int i = 0; i < lstSize; i++) {
                ExternalDbMapTable table = outputTables.get(i);
                String tableName = table.getName();

                IConnection connection = connectionNameToOutgoingConnection.get(tableName);
                if (connection != null) {
                    outgoingConnections.add(connection);
                }
            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IExternalComponent#open()
     */
    public int open(final Composite parent) {
        initMapperMain();
        mapperMain.createModelFromExternalData(getIODataComponents(), getMetadataList(), externalData, true);
        mapperMain.createUI(parent);
        return mapperMain.getMapperDialogResponse();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IExternalComponent#setPersistentData(java.lang.Object)
     */
    public void setExternalData(Object externalData) {
        this.externalData = (ExternalDbMapData) externalData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.INode#getGeneratedCode()
     */
    public String getGeneratedCode() {
        try {
            ICodeGeneratorService service = ActivatorForDbMap.getDefault().getCodeGeneratorService();

            return service.createCodeGenerator().generateComponentCode(this, ECodePart.MAIN);
        } catch (SystemException e) {
            ExceptionHandler.process(e);
        }
        return ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.INode#getMetadataList()
     */
    public List<IMetadataTable> getMetadataList() {
        return this.metadataListOut;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.INode#setMetadataList(java.util.List)
     */
    public void setMetadataList(List<IMetadataTable> metadataTablesOut) {
        this.metadataListOut = metadataTablesOut;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.AbstractExternalNode#setExternalXmlData(java.io.InputStream)
     */
    public void loadDataIn(InputStream in, Reader stringReader) throws IOException, ClassNotFoundException {
        // if (in.available() > 0) {
        // ObjectInputStream oin = null;
        // try {
        // oin = new ObjectInputStream(in);
        // externalData = (ExternalMapperData) oin.readObject();
        //
        // } finally {
        // if (oin != null) {
        // oin.close();
        // }
        // }
        // }

        if (stringReader != null) {
            Unmarshaller unmarshaller = new Unmarshaller(ExternalDbMapData.class);
            try {
                externalData = (ExternalDbMapData) unmarshaller.unmarshal(stringReader);
            } catch (MarshalException e) {
                ExceptionHandler.process(e);
            } catch (ValidationException e) {
                ExceptionHandler.process(e);
            } finally {
                if (stringReader != null) {
                    stringReader.close();
                }
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IExternalNode#loadDataOut(java.io.OutputStream, java.io.Writer)
     */
    public void loadDataOut(final OutputStream out, Writer writer) throws IOException {

        initMapperMain();
        mapperMain.createModelFromExternalData(getIncomingConnections(), getOutgoingConnections(), externalData, getMetadataList(), false);
        ExternalDbMapData data = mapperMain.buildExternalData();
        if (mapperMain != null && data != null) {

            try {
                Marshaller marshaller = new Marshaller(writer);
                marshaller.marshal(externalData);
            } catch (MarshalException e) {
                ExceptionHandler.process(e);
            } catch (ValidationException e) {
                ExceptionHandler.process(e);
            } catch (IOException e) {
                ExceptionHandler.process(e);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }

            // ObjectOutputStream objectOut = null;
            // try {
            // objectOut = new ObjectOutputStream(out);
            // objectOut.writeObject(data);
            // } catch (IOException e) {
            // ExceptionHandler.process(e);
            // } finally {
            // if (objectOut != null) {
            // objectOut.close();
            // }
            // }
        }
    }

    public void renameInputConnection(String oldConnectionName, String newConnectionName) {
        if (oldConnectionName == null || newConnectionName == null) {
            throw new NullPointerException();
        }
        if (externalData != null) {
            List<ExternalDbMapTable> inputTables = externalData.getInputTables();
            for (ExternalDbMapTable table : inputTables) {
                if (table.getTableName() != null
                        && (table.getTableName().equals(oldConnectionName) || table.getName().equals(oldConnectionName))) {
                    table.setTableName(newConnectionName);
                    table.setName(newConnectionName);
                    TableEntryLocation oldLocation = new TableEntryLocation(oldConnectionName, null);
                    TableEntryLocation newLocation = new TableEntryLocation(newConnectionName, null);
                    replaceLocationsInAllExpressions(oldLocation, newLocation, true);
                }
            }
        }
    }

    public void renameOutputConnection(String oldName, String newName) {
        if (oldName == null || newName == null) {
            throw new NullPointerException();
        }
        if (externalData != null) {
            List<ExternalDbMapTable> outputTables = externalData.getOutputTables();
            for (ExternalDbMapTable table : outputTables) {
                if (table.getName().equals(oldName)) {
                    table.setName(newName);
                    break;
                }
            }
        }
    }

    protected void renameMetadataColumnName(String conectionName, String oldColumnName, String newColumnName) {
        if (conectionName == null || oldColumnName == null || newColumnName == null) {
            throw new NullPointerException();
        }
        if (externalData != null) {
            // rename metadata column name
            List<ExternalDbMapTable> tables = new ArrayList<ExternalDbMapTable>(externalData.getInputTables());
            tables.addAll(externalData.getOutputTables());
            ExternalDbMapTable tableFound = null;
            for (ExternalDbMapTable table : tables) {
                if (table.getName().equals(conectionName)) {
                    List<ExternalDbMapEntry> metadataTableEntries = table.getMetadataTableEntries();
                    for (ExternalDbMapEntry entry : metadataTableEntries) {
                        if (entry.getName().equals(oldColumnName)) {
                            entry.setName(newColumnName);
                            tableFound = table;
                            break;
                        }
                    }
                    break;
                }
            }

            // it is necessary to update expressions only if renamed column come from input table
            if (tableFound != null && externalData.getInputTables().indexOf(tableFound) != -1) {
                TableEntryLocation oldLocation = new TableEntryLocation(conectionName, oldColumnName);
                TableEntryLocation newLocation = new TableEntryLocation(conectionName, newColumnName);
                replaceLocationsInAllExpressions(oldLocation, newLocation, false);
            }

        }
    }

    /**
     * DOC amaumont Comment method "replaceLocations".
     * 
     * @param oldLocation
     * @param newLocation
     * @param tableRenamed TODO
     * @param newTableName
     * @param newColumnName
     */
    private void replaceLocationsInAllExpressions(TableEntryLocation oldLocation, TableEntryLocation newLocation, boolean tableRenamed) {
        // replace old location by new location for all expressions in mapper
        List<ExternalDbMapTable> tables = new ArrayList<ExternalDbMapTable>(externalData.getInputTables());
        tables.addAll(new ArrayList<ExternalDbMapTable>(externalData.getVarsTables()));
        tables.addAll(new ArrayList<ExternalDbMapTable>(externalData.getOutputTables()));
        DataMapExpressionParser dataMapExpressionParser = new DataMapExpressionParser(getGenerationManager().getLanguage());
        // loop on all tables
        for (ExternalDbMapTable table : tables) {
            List<ExternalDbMapEntry> metadataTableEntries = table.getMetadataTableEntries();
            if (metadataTableEntries != null) {
                // loop on all entries of current table
                for (ExternalDbMapEntry entry : metadataTableEntries) {
                    replaceLocation(oldLocation, newLocation, entry, dataMapExpressionParser, tableRenamed);
                } // for (ExternalMapperTableEntry entry : metadataTableEntries) {
            }
            if (table.getCustomConditionsEntries() != null) {
                for (ExternalDbMapEntry entry : table.getCustomConditionsEntries()) {
                    replaceLocation(oldLocation, newLocation, entry, dataMapExpressionParser, tableRenamed);
                }
            }
        } // for (ExternalMapperTable table : tables) {
    }

    public void replaceLocation(TableEntryLocation oldLocation, TableEntryLocation newLocation, ExternalDbMapEntry entry,
            DataMapExpressionParser dataMapExpressionParser, boolean tableRenamed) {
        String currentExpression = entry.getExpression();
        if (currentExpression == null || currentExpression.length() == 0) {
            return;
        }
        TableEntryLocation[] tableEntryLocations = dataMapExpressionParser.parseTableEntryLocations(currentExpression);
        // loop on all locations of current expression
        for (int i = 0; i < tableEntryLocations.length; i++) {
            TableEntryLocation currentLocation = tableEntryLocations[i];
            if (tableRenamed && oldLocation.tableName.equals(currentLocation.tableName)) {
                oldLocation.columnName = currentLocation.columnName;
                newLocation.columnName = currentLocation.columnName;
            }
            if (currentLocation.equals(oldLocation)) {
                currentExpression = dataMapExpressionParser.replaceLocation(currentExpression, currentLocation, newLocation);
            }
        } // for (int i = 0; i < tableEntryLocations.length; i++) {
        entry.setExpression(currentExpression);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.AbstractExternalNode#getProblems()
     */
    @Override
    public List<Problem> getProblems() {
        initMapperMain();
        ProblemsAnalyser problemsAnalyser = new ProblemsAnalyser(mapperMain.getMapperManager());
        return problemsAnalyser.checkProblems(externalData);
    }

    /**
     * Getter for mapperMain.
     * 
     * @return the mapperMain
     */
    public MapperMain getMapperMain() {
        return this.mapperMain;
    }

    public abstract DbGenerationManager getGenerationManager();

}
