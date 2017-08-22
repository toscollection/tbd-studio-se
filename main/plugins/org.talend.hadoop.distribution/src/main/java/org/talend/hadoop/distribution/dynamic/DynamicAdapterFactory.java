// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.talend.core.runtime.dynamic.IDynamicAttribute;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.core.runtime.dynamic.IDynamicPlugin;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicAdapterFactory {

    private static DynamicAdapterFactory instance;

    private Map<String, IAdapterCreator> adapterCreatorMap;

    public static DynamicAdapterFactory getInstance() {
        if (instance == null) {
            instance = new DynamicAdapterFactory();
        }
        return instance;
    }

    public DynamicAdapterFactory() {
        adapterCreatorMap = new HashMap<>();
        init();
    }

    public void regist(String key, IAdapterCreator creator) {
        adapterCreatorMap.put(key, creator);
    }

    public IAdapterCreator getCreator(String key) {
        return adapterCreatorMap.get(key);
    }

    public IAdapterCreator unregist(String key) {
        return adapterCreatorMap.remove(key);
    }

    public AbstractDynamicAdapter create(String key, IDynamicAttribute attr, String dynamicId) {
        AbstractDynamicAdapter adapter = null;
        IAdapterCreator creator = adapterCreatorMap.get(key);
        if (creator != null) {
            adapter = creator.create(attr, dynamicId);
        }
        return adapter;
    }

    private void init() {
        adapterCreatorMap.put(DynamicPluginAdapter.TAG_NAME, new IAdapterCreator<DynamicPluginAdapter>() {

            @Override
            public DynamicPluginAdapter create(IDynamicAttribute attr, String dynamicId) {
                return new DynamicPluginAdapter((IDynamicPlugin) attr, dynamicId);
            }

        });

        adapterCreatorMap.put(DynamicClassLoaderExtensionAdaper.TAG_NAME,
                new IAdapterCreator<DynamicClassLoaderExtensionAdaper>() {

                    @Override
                    public DynamicClassLoaderExtensionAdaper create(IDynamicAttribute attr, String dynamicId) {
                        return new DynamicClassLoaderExtensionAdaper((IDynamicExtension) attr, dynamicId);
                    }

                });

        adapterCreatorMap.put(DynamicClassloaderAdapter.TAG_NAME, new IAdapterCreator<DynamicClassloaderAdapter>() {

            @Override
            public DynamicClassloaderAdapter create(IDynamicAttribute attr, String dynamicId) {
                return new DynamicClassloaderAdapter((IDynamicConfiguration) attr, dynamicId);
            }

        });

        adapterCreatorMap.put(DynamicLibraryNeededExtensionAdaper.TAG_NAME,
                new IAdapterCreator<DynamicLibraryNeededExtensionAdaper>() {

                    @Override
                    public DynamicLibraryNeededExtensionAdaper create(IDynamicAttribute attr, String dynamicId) {
                        return new DynamicLibraryNeededExtensionAdaper((IDynamicExtension) attr, dynamicId);
                    }

                });

        adapterCreatorMap.put(DynamicLibraryNeededAdapter.TAG_NAME, new IAdapterCreator<DynamicLibraryNeededAdapter>() {

            @Override
            public DynamicLibraryNeededAdapter create(IDynamicAttribute attr, String dynamicId) {
                return new DynamicLibraryNeededAdapter((IDynamicConfiguration) attr, dynamicId);
            }

        });

        adapterCreatorMap.put(DynamicLibraryNeededGroupAdapter.TAG_NAME, new IAdapterCreator<DynamicLibraryNeededGroupAdapter>() {

            @Override
            public DynamicLibraryNeededGroupAdapter create(IDynamicAttribute attr, String dynamicId) {
                return new DynamicLibraryNeededGroupAdapter((IDynamicConfiguration) attr, dynamicId);
            }

        });

        adapterCreatorMap.put(DynamicLibraryAdapter.TAG_NAME, new IAdapterCreator<DynamicLibraryAdapter>() {

            @Override
            public DynamicLibraryAdapter create(IDynamicAttribute attr, String dynamicId) {
                return new DynamicLibraryAdapter((IDynamicConfiguration) attr, dynamicId);
            }

        });

    }
}
