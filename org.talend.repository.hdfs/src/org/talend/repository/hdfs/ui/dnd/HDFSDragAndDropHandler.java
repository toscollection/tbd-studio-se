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
package org.talend.repository.hdfs.ui.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IComponentsService;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.designerproperties.ComponentToRepositoryProperty;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.model.utils.IDragAndDropServiceHandler;
import org.talend.core.repository.RepositoryComponentSetting;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.hdfsbrowse.util.EHDFSRepositoryToComponent;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.hdfs.node.model.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hdfs.HDFSConnection;
import org.talend.repository.model.hdfs.HDFSConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSDragAndDropHandler implements IDragAndDropServiceHandler {

    private static final String HDFS = "HDFS"; //$NON-NLS-1$

    private static final String MAP = "MAP"; //$NON-NLS-1$

    private static final String INPUT = "tHDFSInput"; //$NON-NLS-1$

    private static final String OUTPUT = "tHDFSOutput"; //$NON-NLS-1$

    @Override
    public boolean canHandle(Connection connection) {
        return connection instanceof HDFSConnection;
    }

    @Override
    public Object getComponentValue(Connection connection, String value, IMetadataTable table) {
        if (value != null && canHandle(connection)) {
            return getHDFSRepositoryValue((HDFSConnection) connection, value, table);
        }
        return null;
    }

    private Object getHDFSRepositoryValue(HDFSConnection connection, String value, IMetadataTable table) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return null;
        }

        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(value)) {
            return hcConnection.getDistribution();
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(value)) {
            return HCVersionUtil.getCompCustomJarsParamFromRep(hcConnection, ECustomVersionGroup.COMMON);
        } else if (EHDFSRepositoryToComponent.AUTHENTICATION_MODE.getRepositoryValue().equals(value)) {
            return hcConnection.getAuthMode();
        } else if (EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getNameNodeURI()));
        } else if (EHDFSRepositoryToComponent.USE_KRB.getRepositoryValue().equals(value)) {
            return hcConnection.isEnableKerberos();
        } else if (EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getPrincipal()));
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getUserName()));
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getGroup()));
        } else if (EHDFSRepositoryToComponent.FILENAME.getRepositoryValue().equals(value)) {
            // if (table != null) {
            // MetadataTable metaTable = HDFSSchemaUtil.getTableByName(connection, table.getLabel());
            // if (metaTable != null) {
            // EMap<String, String> properties = metaTable.getAdditionalProperties();
            // String hdfsPath = properties.get(HDFSConstants.HDFS_PATH);
            // if (StringUtils.isNotEmpty(hdfsPath)) {
            // return TalendQuoteUtils.addQuotesIfNotExist(hdfsPath);
            // }
            // }
            // }
        } else if (EHDFSRepositoryToComponent.ROWSEPARATOR.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getRowSeparator()));
        } else if (EHDFSRepositoryToComponent.FIELDSEPARATOR.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getFieldSeparator()));
        } else if (EHDFSRepositoryToComponent.LOCAL.getRepositoryValue().equals(value)) {
            return false;
        } else if (EHDFSRepositoryToComponent.MAPREDUCE.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHDFSRepositoryToComponent.PIG_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHDFSRepositoryToComponent.MAPRED_JOB_TRACKER.getRepositoryValue().equals(value)) {
            return hcConnection.getJobTrackerURI();
        } else if (EHDFSRepositoryToComponent.FIELD_SEPARATOR_CHAR.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getFieldSeparator()));
        } else if (EHDFSRepositoryToComponent.LOAD.getRepositoryValue().equals(value)) {
            return "PigStorage";
        }

        return null;
    }

    @Override
    public List<IComponent> filterNeededComponents(Item item, RepositoryNode seletetedNode, ERepositoryObjectType type) {
        List<IComponent> neededComponents = new ArrayList<IComponent>();
        if (!(item instanceof HDFSConnectionItem)) {
            return neededComponents;
        }
        IComponentsService service = (IComponentsService) GlobalServiceRegister.getDefault().getService(IComponentsService.class);
        Set<IComponent> components = service.getComponentsFactory().getComponents();
        for (IComponent component : components) {
            if (isValid(item, type, seletetedNode, component, HDFS) && !neededComponents.contains(component)) {
                neededComponents.add(component);
            }
        }

        return neededComponents;
    }

    private boolean isValid(Item item, ERepositoryObjectType type, RepositoryNode seletetedNode, IComponent component,
            String repositoryType) {
        if (component == null || repositoryType == null) {
            return false;
        }

        String componentProductname = component.getRepositoryType();
        if (componentProductname != null && componentProductname.contains(repositoryType)
                && isSubValid(item, type, seletetedNode, component, repositoryType)) {
            return true;
        }
        return false;
    }

    protected boolean isSubValid(Item item, ERepositoryObjectType type, RepositoryNode seletetedNode, IComponent component,
            String repositoryType) {
        boolean tableWithMap = true;
        if (type == ERepositoryObjectType.METADATA_CON_TABLE) {
            if (component.getName().toUpperCase().endsWith(MAP)) {
                tableWithMap = false;

            }
        }
        return tableWithMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public IComponentName getCorrespondingComponentName(Item item, ERepositoryObjectType type) {
        RepositoryComponentSetting setting = null;
        if (item instanceof HDFSConnectionItem) {
            setting = new RepositoryComponentSetting();
            setting.setName(HDFS);
            setting.setRepositoryType(HDFS);
            setting.setWithSchema(true);
            setting.setInputComponent(INPUT);
            setting.setOutputComponent(OUTPUT);
            List<Class<Item>> list = new ArrayList<Class<Item>>();
            Class clazz = null;
            try {
                clazz = Class.forName(HDFSConnectionItem.class.getName());
            } catch (ClassNotFoundException e) {
                ExceptionHandler.process(e);
            }
            list.add(clazz);
            setting.setClasses(list.toArray(new Class[0]));
        }

        return setting;
    }

    @Override
    public void setComponentValue(Connection connection, INode node, String repositoryValue) {
        if (node != null && canHandle(connection)) {
            setHDFSRepositoryValue((HDFSConnection) connection, node, repositoryValue);
        }
    }

    @SuppressWarnings("unchecked")
    private void setHDFSRepositoryValue(HDFSConnection connection, INode node, String repositoryValue) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return;
        }

        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node,
                    EHDFSRepositoryToComponent.DISTRIBUTION.getParameterName());
            if (value != null) {
                hcConnection.setDistribution(value);
            }
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node,
                    EHDFSRepositoryToComponent.DB_VERSION.getParameterName());
            if (value != null) {
                hcConnection.setDfVersion(value);
            }
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(repositoryValue)) {
            IElementParameter param = node.getElementParameter(EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS.getParameterName());
            if (param != null) {
                Object obj = param.getValue();
                if (obj != null) {
                    Map<String, Set<String>> customVersionMap = HCVersionUtil.getRepCustomJarsParamFromComp((String) obj,
                            ECustomVersionGroup.COMMON);
                    HCVersionUtil.injectCustomVersionMap(hcConnection, customVersionMap);
                }
            }
        } else if (EHDFSRepositoryToComponent.AUTHENTICATION_MODE.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node,
                    EHDFSRepositoryToComponent.AUTHENTICATION_MODE.getParameterName());
            if (value != null) {
                hcConnection.setAuthMode(value);
            }
        } else if (EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node,
                    EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getParameterName());
            if (value != null) {
                hcConnection.setNameNodeURI(value);
            }
        } else if (EHDFSRepositoryToComponent.USE_KRB.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node,
                    EHDFSRepositoryToComponent.USE_KRB.getParameterName());
            if (value != null) {
                hcConnection.setEnableKerberos(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node,
                    EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getParameterName());
            if (value != null) {
                hcConnection.setPrincipal(value);
            }
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.USERNAME.getParameterName());
            if (value != null) {
                connection.setUserName(value);
            }
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node,
                    EHDFSRepositoryToComponent.GROUP.getParameterName());
            if (value != null) {
                hcConnection.setGroup(value);
            }
        } else if (EHDFSRepositoryToComponent.ROWSEPARATOR.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.ROWSEPARATOR.getParameterName());
            if (value != null) {
                connection.setRowSeparator(value);
            }
        } else if (EHDFSRepositoryToComponent.FIELDSEPARATOR.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.FIELDSEPARATOR.getParameterName());
            if (value != null) {
                connection.setFieldSeparator(value);
            }
        }
    }

    @Override
    public ERepositoryObjectType getType(String repositoryType) {
        if (HDFS.equals(repositoryType)) {
            return HDFSRepositoryNodeType.HDFS;
        }
        return null;
    }

    @Override
    public void handleTableRelevantParameters(IElement ele, IMetadataTable metadataTable) {
        if (ele == null || metadataTable == null) {
            return;
        }
        IElementParameter fileNameParameter = ele.getElementParameter(EHDFSRepositoryToComponent.FILENAME.getParameterName());
        IElementParameter filePatheParameter = ele.getElementParameter(EHDFSRepositoryToComponent.INPUT_FILENAME
                .getParameterName());
        String hdfsPath = metadataTable.getAdditionalProperties().get(HDFSConstants.HDFS_PATH);
        if (hdfsPath != null) {
            if (fileNameParameter != null) {
                fileNameParameter.setValue(TalendQuoteUtils.addQuotesIfNotExist(hdfsPath));
            } else if (filePatheParameter != null) {
                filePatheParameter.setValue(TalendQuoteUtils.addQuotesIfNotExist(hdfsPath));
            }
        }
    }

}
