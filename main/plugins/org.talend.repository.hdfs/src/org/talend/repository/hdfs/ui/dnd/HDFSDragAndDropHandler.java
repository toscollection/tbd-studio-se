// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
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
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
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
import org.talend.core.model.utils.AbstractDragAndDropServiceHandler;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.repository.RepositoryComponentSetting;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.hdfsbrowse.model.EHDFSFileTypes;
import org.talend.designer.hdfsbrowse.util.EHDFSRepositoryToComponent;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.hdfs.node.model.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hdfs.HDFSConnection;
import org.talend.repository.model.hdfs.HDFSConnectionItem;
import org.talend.utils.json.JSONException;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSDragAndDropHandler extends AbstractDragAndDropServiceHandler {

    private static final String HDFS = "HDFS"; //$NON-NLS-1$

    private static final String MAP = "MAP"; //$NON-NLS-1$

    private static final String INPUT = "tHDFSInput"; //$NON-NLS-1$

    private static final String OUTPUT = "tHDFSOutput"; //$NON-NLS-1$

    @Override
    public boolean canHandle(Connection connection) {
        return connection instanceof HDFSConnection;
    }

    @Override
    public Object getComponentValue(Connection connection, String value, IMetadataTable table, String targetComponent) {
        if (value != null && canHandle(connection)) {
            return getHDFSRepositoryValue((HDFSConnection) connection, value, table, targetComponent);
        }
        return null;
    }

    private Object getHDFSRepositoryValue(HDFSConnection connection, String value, IMetadataTable table, String targetComponent) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return null;
        }

        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(value)) {
            return hcConnection.getDistribution();
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(value)) {
            if (targetComponent != null && targetComponent.startsWith("tPig")) { //$NON-NLS-1$
                return hcConnection.getParameters().get(ECustomVersionGroup.PIG.getName());
            }
            return HCVersionUtil.getCompCustomJarsParamFromRep(hcConnection, ECustomVersionGroup.COMMON);
        } else if (EHDFSRepositoryToComponent.AUTHENTICATION_MODE.getRepositoryValue().equals(value)) {
            return hcConnection.getAuthMode();
        } else if (EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getNameNodeURI()));
        } else if (EHDFSRepositoryToComponent.USE_KRB.getRepositoryValue().equals(value)) {
            return hcConnection.isEnableKerberos();
        } else if (EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getPrincipal()));
        } else if (EHDFSRepositoryToComponent.USE_KEYTAB.getRepositoryValue().equals(value)) {
            return hcConnection.isUseKeytab();
        } else if (EHDFSRepositoryToComponent.KEYTAB_PRINCIPAL.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getKeytabPrincipal()));
        } else if (EHDFSRepositoryToComponent.KEYTAB_PATH.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getKeytab()));
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getUserName()));
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getGroup()));
        } else if (EHDFSRepositoryToComponent.ROWSEPARATOR.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getRowSeparator()));
        } else if (EHDFSRepositoryToComponent.FIELDSEPARATOR.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getFieldSeparator()));
        } else if (EHDFSRepositoryToComponent.HEADER.getRepositoryValue().equals(value)) {
            return StringUtils.trimToNull(connection.getHeaderValue());
        } else if (EHDFSRepositoryToComponent.LOCAL.getRepositoryValue().equals(value)) {
            return false;
        } else if (EHDFSRepositoryToComponent.MAPREDUCE.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHDFSRepositoryToComponent.PIG_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHDFSRepositoryToComponent.MAPRED_JOB_TRACKER.getRepositoryValue().equals(value)
                || EHDFSRepositoryToComponent.MAPRED_RESOURCE_MANAGER.getRepositoryValue().equals(value)
                || EHDFSRepositoryToComponent.RESOURCE_MANAGER.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getJobTrackerURI()));
        } else if (EHDFSRepositoryToComponent.FIELD_SEPARATOR_CHAR.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getFieldSeparator()));
        } else if (EHDFSRepositoryToComponent.LOAD.getRepositoryValue().equals(value)) {
            return "PigStorage"; //$NON-NLS-1$
        } else if (EHDFSRepositoryToComponent.HADOOP_ADVANCED_PROPERTIES.getRepositoryValue().equals(value)) {
            try {
                return HadoopRepositoryUtil.getHadoopPropertiesList(connection.getHadoopProperties(), true);
            } catch (JSONException e) {
                ExceptionHandler.process(e);
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
    public void setComponentValue(Connection connection, INode node, IElementParameter param) {
        if (node != null && canHandle(connection)) {
            setHDFSRepositoryValue((HDFSConnection) connection, node, param);
        }
    }

    private void setHDFSRepositoryValue(HDFSConnection connection, INode node, IElementParameter param) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return;
        }

        if (EHDFSRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setDistribution(value);
            }
        } else if (EHDFSRepositoryToComponent.DB_VERSION.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setDfVersion(value);
            }
        } else if (EHDFSRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(param.getRepositoryValue())) {
            Object obj = param.getValue();
            if (obj != null) {
                Map<String, Set<String>> customVersionMap = HCVersionUtil.getRepCustomJarsParamFromComp((String) obj,
                        ECustomVersionGroup.COMMON);
                HCVersionUtil.injectCustomVersionMap(hcConnection, customVersionMap);
            }
        } else if (EHDFSRepositoryToComponent.AUTHENTICATION_MODE.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setAuthMode(value);
            }
        } else if (EHDFSRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setNameNodeURI(value);
            }
        } else if (EHDFSRepositoryToComponent.USE_KRB.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setEnableKerberos(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setPrincipal(value);
            }
        } else if (EHDFSRepositoryToComponent.USE_KEYTAB.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setUseKeytab(Boolean.valueOf(value));
            }
        } else if (EHDFSRepositoryToComponent.KEYTAB_PRINCIPAL.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setKeytabPrincipal(value);
            }
        } else if (EHDFSRepositoryToComponent.KEYTAB_PATH.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setKeytab(value);
            }
        } else if (EHDFSRepositoryToComponent.USERNAME.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setUserName(value);
            }
        } else if (EHDFSRepositoryToComponent.GROUP.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(hcConnection, node, param);
            if (value != null) {
                hcConnection.setGroup(value);
            }
        } else if (EHDFSRepositoryToComponent.ROWSEPARATOR.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setRowSeparator(value);
            }
        } else if (EHDFSRepositoryToComponent.FIELDSEPARATOR.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setFieldSeparator(value);
            }
        } else if (EHDFSRepositoryToComponent.HEADER.getRepositoryValue().equals(param.getRepositoryValue())) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node, param);
            if (value != null) {
                connection.setHeaderValue(value);
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
    public void handleTableRelevantParameters(Connection connection, IElement ele, IMetadataTable metadataTable) {
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
        IElementParameter fileTypeParameter = ele.getElementParameter(EHDFSRepositoryToComponent.FILETYPE.getParameterName());
        if (fileTypeParameter != null) {
            String hdfsFileType = metadataTable.getAdditionalProperties().get(HDFSConstants.HDFS_FILE_TYPE);
            if (StringUtils.isNotBlank(hdfsFileType)) {
                fileTypeParameter.setValue(hdfsFileType);
            } else {
                fileTypeParameter.setValue(EHDFSFileTypes.TEXT.getName());
            }
            IElementParameter keyColumnParameter = ele.getElementParameter(EHDFSRepositoryToComponent.KEYCOLUMN
                    .getParameterName());
            IElementParameter valColumnParameter = ele.getElementParameter(EHDFSRepositoryToComponent.VALUECOLUMN
                    .getParameterName());
            if (keyColumnParameter != null) {
                keyColumnParameter.setValue("Key"); //$NON-NLS-1$
            }
            if (valColumnParameter != null) {
                valColumnParameter.setValue("Value"); //$NON-NLS-1$
            }
        }
    }

}
