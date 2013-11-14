package org.talend.repository.hcatalog.ui.dnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.talend.core.model.utils.AbstractDragAndDropServiceHandler;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.repository.RepositoryComponentSetting;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.hcatalog.util.EHCatalogRepositoryToComponent;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hcatalog.HCatalogConnection;
import org.talend.repository.model.hcatalog.HCatalogConnectionItem;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogDragAndDropHandler extends AbstractDragAndDropServiceHandler {

    private static final String HCATALOG = "HCATALOG"; //$NON-NLS-1$

    private static final String MAP = "MAP"; //$NON-NLS-1$

    private static final String INPUT = "tHCatalogInput"; //$NON-NLS-1$

    private static final String OUTPUT = "tHCatalogOutput"; //$NON-NLS-1$

    private static final String HCATALOG_LOAD = "HCatLoader"; //$NON-NLS-1$

    private static final String HCATALOG_STORE = "HCatStorer"; //$NON-NLS-1$

    @Override
    public boolean canHandle(Connection connection) {
        return connection instanceof HCatalogConnection;
    }

    @Override
    public Object getComponentValue(Connection connection, String value, IMetadataTable table, String targetComponent) {
        if (value != null && canHandle(connection)) {
            return getHCatalogRepositoryValue((HCatalogConnection) connection, value, table, targetComponent);
        }
        return null;
    }

    private Object getHCatalogRepositoryValue(HCatalogConnection connection, String value, IMetadataTable table,
            String targetComponent) {
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        if (hcConnection == null) {
            return null;
        }

        if (EHCatalogRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(value)) {
            return hcConnection.getDistribution();
        } else if (EHCatalogRepositoryToComponent.HCAT_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHCatalogRepositoryToComponent.TEMPLETON_HOST.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getHostName()));
        } else if (EHCatalogRepositoryToComponent.TEMPLETON_PORT.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getPort()));
        } else if (EHCatalogRepositoryToComponent.USE_KRB.getRepositoryValue().equals(value)) {
            return hcConnection.isEnableKerberos();
        } else if (EHCatalogRepositoryToComponent.KRB_PRINC.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getKrbPrincipal()));
        } else if (EHCatalogRepositoryToComponent.KRB_REALM.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getKrbRealm()));
        } else if (EHCatalogRepositoryToComponent.NAMENODE_PRINCIPAL.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getPrincipal()));
        } else if (EHCatalogRepositoryToComponent.USE_KEYTAB.getRepositoryValue().equals(value)) {
            return hcConnection.isUseKeytab();
        } else if (EHCatalogRepositoryToComponent.KEYTAB_PRINCIPAL.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getKeytabPrincipal()));
        } else if (EHCatalogRepositoryToComponent.KEYTAB_PATH.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getKeytab()));
        } else if (EHCatalogRepositoryToComponent.DATABASE_NAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getDatabase()));
        } else if (EHCatalogRepositoryToComponent.TABLE_NAME.getRepositoryValue().equals(value)) {
            // if (table != null) {
            // return TalendQuoteUtils.addQuotesIfNotExist(table.getLabel());
            // }
        } else if (EHCatalogRepositoryToComponent.PARTITION_NAME.getRepositoryValue().equals(value)) {
            // TODO: need to do it in the future.
            // if (table != null) {
            // MetadataTable metaTable = HCatalogSchemaUtil.getTableByName(connection, table.getLabel());
            // if (metaTable != null) {
            // EMap<String, String> properties = metaTable.getAdditionalProperties();
            // String partitions = properties.get(HCatalogConstants.PARTITIONS);
            // if (partitions != null) {
            // return TalendQuoteUtils.addQuotesIfNotExist(partitions);
            // }
            // }
            // }
        } else if (EHCatalogRepositoryToComponent.USERNAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getUserName()));
        } else if (EHCatalogRepositoryToComponent.LOCAL.getRepositoryValue().equals(value)) {
            return false;
        } else if (EHCatalogRepositoryToComponent.MAPREDUCE.getRepositoryValue().equals(value)) {
            return true;
        } else if (EHCatalogRepositoryToComponent.PIG_VERSION.getRepositoryValue().equals(value)) {
            return hcConnection.getDfVersion();
        } else if (EHCatalogRepositoryToComponent.MAPRED_JOB_TRACKER.getRepositoryValue().equals(value)
                || EHCatalogRepositoryToComponent.MAPRED_RESOURCE_MANAGER.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getJobTrackerURI()));
        } else if (EHCatalogRepositoryToComponent.FS_DEFAULT_NAME.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(hcConnection.getNameNodeURI()));
        } else if (EHCatalogRepositoryToComponent.JOBTRACKER_PRINCIPAL.getRepositoryValue().equals(value)) {
            return TalendQuoteUtils.addQuotesIfNotExist(StringUtils.trimToNull(connection.getKrbPrincipal()));
        } else if (EHCatalogRepositoryToComponent.LOAD.getRepositoryValue().equals(value)) {
            return HCATALOG_LOAD;
        } else if (EHCatalogRepositoryToComponent.STORE.getRepositoryValue().equals(value)) {
            return HCATALOG_STORE;
        } else if (EHCatalogRepositoryToComponent.HADOOP_CUSTOM_JARS.getRepositoryValue().equals(value)) {
            if (targetComponent != null && targetComponent.startsWith("tPig")) {
                return hcConnection.getParameters().get(ECustomVersionGroup.PIG_HCATALOG.getName());
            }
        } else if (EHCatalogRepositoryToComponent.HADOOP_ADVANCED_PROPERTIES.getRepositoryValue().equals(value)) {
            return getHadoopProperties(connection.getHadoopProperties());
        }

        return null;
    }

    private List<HashMap<String, Object>> getHadoopProperties(String message) {
        List<HashMap<String, Object>> properties = new ArrayList<HashMap<String, Object>>();
        try {
            if (StringUtils.isNotEmpty(message)) {
                JSONArray jsonArr = new JSONArray(message);
                for (int i = 0; i < jsonArr.length(); i++) {
                    HashMap<String, Object> map = new HashMap();
                    JSONObject object = jsonArr.getJSONObject(i);
                    Iterator it = object.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        map.put(key, object.get(key));
                    }
                    properties.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Override
    public List<IComponent> filterNeededComponents(Item item, RepositoryNode seletetedNode, ERepositoryObjectType type) {
        List<IComponent> neededComponents = new ArrayList<IComponent>();
        if (!(item instanceof HCatalogConnectionItem)) {
            return neededComponents;
        }
        IComponentsService service = (IComponentsService) GlobalServiceRegister.getDefault().getService(IComponentsService.class);
        Set<IComponent> components = service.getComponentsFactory().getComponents();
        for (IComponent component : components) {
            if (isValid(item, type, seletetedNode, component, HCATALOG) && !neededComponents.contains(component)) {
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

    @Override
    public IComponentName getCorrespondingComponentName(Item item, ERepositoryObjectType type) {
        RepositoryComponentSetting setting = null;
        if (item instanceof HCatalogConnectionItem) {
            setting = new RepositoryComponentSetting();
            setting.setName(HCATALOG);
            setting.setRepositoryType(HCATALOG);
            setting.setWithSchema(true);
            setting.setInputComponent(INPUT);
            setting.setOutputComponent(OUTPUT);
            List<Class<Item>> list = new ArrayList<Class<Item>>();
            Class clazz = null;
            try {
                clazz = Class.forName(HCatalogConnectionItem.class.getName());
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
            setHCatalogRepositoryValue((HCatalogConnection) connection, node, repositoryValue);
        }
    }

    private void setHCatalogRepositoryValue(HCatalogConnection connection, INode node, String repositoryValue) {
        if (EHCatalogRepositoryToComponent.DISTRIBUTION.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHCatalogRepositoryToComponent.DISTRIBUTION.getParameterName());
            if (value != null) {
                connection.setDistribution(value);
            }
        } else if (EHCatalogRepositoryToComponent.HCAT_VERSION.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHCatalogRepositoryToComponent.HCAT_VERSION.getParameterName());
            if (value != null) {
                connection.setHcatVersion(value);
            }
        } else if (EHCatalogRepositoryToComponent.TEMPLETON_HOST.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHCatalogRepositoryToComponent.TEMPLETON_HOST.getParameterName());
            if (value != null) {
                connection.setHostName(value);
            }
        } else if (EHCatalogRepositoryToComponent.TEMPLETON_PORT.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHCatalogRepositoryToComponent.TEMPLETON_PORT.getParameterName());
            if (value != null) {
                connection.setPort(value);
            }
        } else if (EHCatalogRepositoryToComponent.DATABASE_NAME.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHCatalogRepositoryToComponent.DATABASE_NAME.getParameterName());
            if (value != null) {
                connection.setDatabase(value);
            }
        } else if (EHCatalogRepositoryToComponent.USERNAME.getRepositoryValue().equals(repositoryValue)) {
            String value = ComponentToRepositoryProperty.getParameterValue(connection, node,
                    EHCatalogRepositoryToComponent.USERNAME.getParameterName());
            if (value != null) {
                connection.setUserName(value);
            }
        }
    }

    @Override
    public ERepositoryObjectType getType(String repositoryType) {
        if (HCATALOG.equals(repositoryType)) {
            return HCatalogRepositoryNodeType.HCATALOG;
        }
        return null;
    }

    @Override
    public void handleTableRelevantParameters(IElement ele, IMetadataTable metadataTable) {
        if (ele == null || metadataTable == null) {
            return;
        }
        String tableName = metadataTable.getTableName();
        IElementParameter tableNameParameter = ele.getElementParameter(EHCatalogRepositoryToComponent.TABLE_NAME
                .getParameterName());
        if (tableNameParameter != null) {
            tableNameParameter.setValue(TalendQuoteUtils.addQuotesIfNotExist(tableName));
        }
    }

}
