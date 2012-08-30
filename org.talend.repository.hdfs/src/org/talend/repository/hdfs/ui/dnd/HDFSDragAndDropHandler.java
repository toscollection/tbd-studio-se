// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
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
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EMap;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IComponentsService;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.designerproperties.ComponentToRepositoryProperty;
import org.talend.core.model.process.INode;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.model.utils.IDragAndDropServiceHandler;
import org.talend.core.repository.RepositoryComponentSetting;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.hdfsbrowse.util.EHDFSRepositoryToComponent;
import org.talend.designer.hdfsbrowse.util.EHadoopVersion4Drivers;
import org.talend.repository.hdfs.node.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.hdfs.util.HDFSSchemaUtil;
import org.talend.repository.model.RepositoryNode;
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
        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(value)) {
            return connection.getDistribution();
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(value)) {
            return connection.getDfDrivers();
        } else if (EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getNameNodeURI()));
        } else if (EHDFSRepositoryToComponent.USE_KRB.getRepositoryValue().equals(value)) {
            return connection.isEnableKerberos();
        } else if (EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getPrincipal()));
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getUserName()));
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getGroup()));
        } else if (EHDFSRepositoryToComponent.FILENAME.getRepositoryValue().equals(value)) {
            if (table != null) {
                MetadataTable metaTable = HDFSSchemaUtil.getTableByName(connection, table.getLabel());
                if (metaTable != null) {
                    EMap<String, String> properties = metaTable.getAdditionalProperties();
                    String hdfsPath = properties.get(HDFSConstants.HDFS_PATH);
                    if (StringUtils.isNotEmpty(hdfsPath)) {
                        return TalendQuoteUtils.addQuotesIfNotExist(hdfsPath);
                    }
                }
            }
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
        if (componentProductname != null && repositoryType.endsWith(componentProductname)
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

    @Override
    public IComponentName getCorrespondingComponentName(Item item, ERepositoryObjectType type) {
        RepositoryComponentSetting setting = new RepositoryComponentSetting();
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

        return setting;
    }

    @Override
    public void setComponentValue(Connection connection, INode node, String repositoryValue) {
        if (node != null && canHandle(connection)) {
            setHDFSRepositoryValue((HDFSConnection) connection, node, repositoryValue);
        }
    }

    private void setHDFSRepositoryValue(HDFSConnection connection, INode node, String repositoryValue) {
        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.DISTRIBUTION.getParameterName());
            if (value != null) {
                connection.setDistribution(value);
            }
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.DB_VERSION.getParameterName());
            if (value != null) {
                connection.setDfVersion(EHadoopVersion4Drivers.getVersionByDriverStrs(value));
                connection.setDfDrivers(value);
            }
        } else if (EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getParameterName());
            if (value != null) {
                connection.setNameNodeURI(value);
            }
        } else if (EHDFSRepositoryToComponent.USE_KRB.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.USE_KRB.getParameterName());
            if (value != null) {
                connection.setEnableKerberos(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getParameterName());
            if (value != null) {
                connection.setPrincipal(value);
            }
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.USERNAME.getParameterName());
            if (value != null) {
                connection.setUserName(value);
            }
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHDFSRepositoryToComponent.GROUP.getParameterName());
            if (value != null) {
                connection.setGroup(value);
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

}
