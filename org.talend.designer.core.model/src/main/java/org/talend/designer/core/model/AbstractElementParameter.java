// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.EParameterFieldType;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IElementParameterDefaultValue;
import org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public class AbstractElementParameter extends EObjectImpl implements ElementParameterType {

    protected boolean contextMode;

    protected String labelFromRepository;

    protected String filter = null;

    protected boolean noCheck = false;

    protected int currentRow; // for Table only

    protected EComponentCategory category;

    protected IElement element;

    protected boolean show = true, required = false, readOnly = false;

    protected EParameterFieldType parameterFieldType;

    protected String context, groupName, groupDisplayName;

    protected IElementParameter parentParameter;

    protected String showIf = null;

    protected String notShowIf = null;

    protected String javaClass; // for JAVA_COMMAND

    protected String jar; // for JAVA_COMMAND

    protected String[] args;

    // hshen 6930
    protected String readonlyIf = null;

    protected String notReadonlyIf = null;

    protected String javaFunction; // for JAVA_COMMAND

    protected String repositoryValue;

    protected String name, displayName;

    protected boolean basedOnSchema = false;

    protected boolean basedOnSubjobStarts = false;

    protected boolean columnsBasedOnSchema = false;

    protected boolean dynamicSettings = false;

    protected boolean noContextAssist;

    // achen add
    protected boolean basedOnInputSchema = false;

    protected boolean repositoryValueUsed = false;

    protected static final int NB_LINES_DEFAULT = 3;

    protected int nbLines = NB_LINES_DEFAULT, numRow = 0; // Default values

    protected Map<String, IElementParameter> childParameters;

    protected List<IElementParameterDefaultValue> defaultValues = new ArrayList<IElementParameterDefaultValue>();

    // used for CLOSED_LIST / TABLE
    protected String[] itemsDisplayName;

    // used for CLOSED_LIST / TABLE
    protected String[] itemsDisplayCodeName;

    // used for CLOSED_LIST / TABLE
    protected String[] itemsShowIf;

    // used for CLOSED_LIST / TABLE
    protected String[] itemsNotShowIf;

    // used for CLOSED_LIST
    protected Object[] itemsValue;

    // used for CLOSED_LIST / TABLE
    protected String[] itemsRepository;

    // used for CLOSED_LIST
    protected Object defaultClosedListValue;

    // hshen 6930
    protected String[] itemsNotReadOnlyIf;

    protected String[] itemsReadOnlyIf;

    public int getNbLines() {
        return this.nbLines;
    }

    public void setNbLines(final int nbLines) {
        this.nbLines = nbLines;
    }

    /**
     * Getter for javaFunction.
     * 
     * @return the javaFunction
     */
    public String getJavaFunction() {
        return this.javaFunction;
    }

    /**
     * Sets the javaFunction.
     * 
     * @param javaFunction the javaFunction to set
     */
    public void setJavaFunction(String javaFunction) {
        this.javaFunction = javaFunction;
    }

    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IDesignerElementParameter#setName(java.lang.String)
     */
    public void setName(final String s) {
        name = s;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IDesignerElementParameter#setDisplayName(java.lang.String)
     */
    public void setDisplayName(final String s) {
        displayName = s;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IDesignerElementParameter#getDisplayName()
     */
    public String getDisplayName() {
        return displayName;
    }

    public int getNumRow() {
        return this.numRow;
    }

    public void setNumRow(final int numRow) {
        this.numRow = numRow;
    }

    public String getRepositoryValue() {
        return this.repositoryValue;
    }

    public void setRepositoryValue(String repositoryValue) {
        this.repositoryValue = repositoryValue;
    }

    public void setListItemsDisplayName(final String[] list) {
        itemsDisplayName = list;
    }

    public String[] getListItemsDisplayName() {
        return itemsDisplayName;
    }

    public void setListItemsDisplayCodeName(final String[] list) {
        itemsDisplayCodeName = list;
    }

    public String[] getListItemsDisplayCodeName() {
        return itemsDisplayCodeName;
    }

    public void setListItemsValue(final Object[] list) {
        if (this.getParameterFieldType() == EParameterFieldType.TABLE) {
            EParameterFieldType.AS400_CHECK.getClass();
        }
        itemsValue = list;
    }

    public Object[] getListItemsValue() {
        return itemsValue;
    }

    public void setDefaultClosedListValue(Object o) {
        defaultClosedListValue = o;
    }

    public Object getDefaultClosedListValue() {
        return defaultClosedListValue;
    }

    public void setListRepositoryItems(final String[] list) {
        itemsRepository = list;
    }

    public String[] getListRepositoryItems() {
        return itemsRepository;
    }

    public void setListItemsShowIf(String[] list) {
        itemsShowIf = list;
    }

    public String[] getListItemsShowIf() {
        return itemsShowIf;
    }

    public void setListItemsNotShowIf(String[] list) {
        itemsNotShowIf = list;
    }

    public String[] getListItemsNotShowIf() {
        return itemsNotShowIf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IDesignerElementParameter#getField()
     */
    public EParameterFieldType getParameterFieldType() {
        return parameterFieldType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#getListItemsNotReadOnlyIf()
     */
    public String[] getListItemsNotReadOnlyIf() {
        return this.itemsNotReadOnlyIf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#getListItemsReadOnlyIf()
     */
    public String[] getListItemsReadOnlyIf() {
        return this.itemsReadOnlyIf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#getNotReadOnlyIf()
     */
    public String getNotReadOnlyIf() {
        // TODO Auto-generated method stub
        return this.notReadonlyIf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#getReadOnlyIf()
     */
    public String getReadOnlyIf() {
        // TODO Auto-generated method stub
        return this.readonlyIf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setListItemsNotReadOnlyIf(java.lang.String[])
     */
    public void setListItemsNotReadOnlyIf(String[] list) {
        itemsNotReadOnlyIf = list;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setListItemsReadOnlyIf(java.lang.String[])
     */
    public void setListItemsReadOnlyIf(String[] list) {
        itemsReadOnlyIf = list;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setNotReadOnlyIf(java.lang.String)
     */
    public void setNotReadOnlyIf(String notReadOnly) {
        this.notReadonlyIf = notReadOnly;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setReadOnlyIf(java.lang.String)
     */
    public void setReadOnlyIf(String readOnly) {
        this.readonlyIf = readOnly;
    }

    public String getShowIf() {
        return showIf;
    }

    public void setShowIf(String showIf) {
        this.showIf = showIf;
    }

    public String getNotShowIf() {
        return notShowIf;
    }

    public void setNotShowIf(String notShowIf) {
        this.notShowIf = notShowIf;
    }

    public int getIndexOfItemFromList(String item) {
        int index = -1;
        boolean found = false;
        if (itemsDisplayCodeName != null) {
            for (int i = 0; i < itemsDisplayCodeName.length && !found; i++) {
                String string = itemsDisplayCodeName[i];
                if (string.equals(item)) {
                    found = true;
                    index = i;
                }
            }
        }
        for (int i = 0; i < itemsValue.length && !found; i++) {
            String string = (String) itemsValue[i];
            if (string.equals(item)) {
                found = true;
                index = i;
            }
        }
        return index;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.talend.designer.core.model.components.IDesignerElementParameter#setField(org.talend.core.model.designer.
     * EParameterFieldType)
     */
    public void setParameterFieldType(final EParameterFieldType type) {
        parameterFieldType = type;
    }

    public void setCategory(final EComponentCategory cat) {
        category = cat;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.components.IDesignerElementParameter#getCategory()
     */
    public EComponentCategory getCategory() {
        return this.category;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#getGroup()
     */
    public String getGroup() {
        return this.groupName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setGroup(java.lang.String)
     */
    public void setGroup(String groupName) {
        this.groupName = groupName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#getGroupDisplayName()
     */
    public String getGroupDisplayName() {
        return this.groupDisplayName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setGroupDisplayName(java.lang.String)
     */
    public void setGroupDisplayName(String groupDisplayName) {
        this.groupDisplayName = groupDisplayName;
    }

    /**
     * Getter for context.
     * 
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the context.
     * 
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    public boolean getOriginalityReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isReadOnly() {
        if (element != null) {
            return (this.readOnly || element.isReadOnly());
        }
        return this.readOnly;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public void setShow(final boolean show) {
        this.show = show;
    }

    public IElement getElement() {
        return element;
    }

    public void setElement(IElement element) {
        this.element = element;
    }

    public boolean isDisplayedByDefault() {
        return this.show;
    }

    /**
     * Getter for javaMethod.
     * 
     * @return the javaMethod
     */
    public String getJavaClass() {
        return this.javaClass;
    }

    /**
     * Sets the javaMethod.
     * 
     * @param javaMethod the javaMethod to set
     */
    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    /**
     * Getter for jar.
     * 
     * @return the jar
     */
    public String getJar() {
        return this.jar;
    }

    /**
     * Sets the jar.
     * 
     * @param jar the jar to set
     */
    public void setJar(String jar) {
        this.jar = jar;
    }

    /**
     * Getter for args.
     * 
     * @return the args
     */
    public String[] getArgs() {
        return this.args;
    }

    /**
     * Sets the args.
     * 
     * @param args the args to set
     */
    public void setArgs(String[] args) {
        this.args = args;
    }

    public boolean isRepositoryValueUsed() {
        return this.repositoryValueUsed;
    }

    public void setRepositoryValueUsed(boolean repositoryUsed) {
        this.repositoryValueUsed = repositoryUsed;
    }

    public boolean isBasedOnInputSchema() {
        return this.basedOnInputSchema;
    }

    public void setBasedOnInputSchema(boolean basedOnInputSchema) {
        this.basedOnInputSchema = basedOnInputSchema;
    }

    public boolean isBasedOnSchema() {
        return basedOnSchema;
    }

    public void setBasedOnSchema(boolean basedOnSchema) {
        this.basedOnSchema = basedOnSchema;
    }

    public boolean isColumnsBasedOnSchema() {
        return this.columnsBasedOnSchema;
    }

    public void setColumnsBasedOnSchema(boolean columnsBasedOnSchema) {
        this.columnsBasedOnSchema = columnsBasedOnSchema;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#isBasedOnSubjobStarts()
     */
    public boolean isBasedOnSubjobStarts() {
        return basedOnSubjobStarts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setBasedOnSubjobStarts(boolean)
     */
    public void setBasedOnSubjobStarts(boolean basedOnSubjobStarts) {
        this.basedOnSubjobStarts = basedOnSubjobStarts;
    }

    public boolean isNoContextAssist() {
        return this.noContextAssist;
    }

    public void setNoContextAssist(boolean enable) {
        this.noContextAssist = enable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#isDynamicSettings()
     */
    public boolean isDynamicSettings() {
        return this.dynamicSettings;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setDynamicSettings(java.lang.Boolean)
     */
    public void setDynamicSettings(boolean dynamicSettings) {
        this.dynamicSettings = dynamicSettings;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Sets the noCheck.
     * 
     * @param noCheck the noCheck to set
     */
    public void setNoCheck(boolean noCheck) {
        this.noCheck = noCheck;
    }

    /**
     * Getter for currentRow.
     * 
     * @return the currentRow
     */
    public int getCurrentRow() {
        return this.currentRow;
    }

    /**
     * Sets the currentRow.
     * 
     * @param currentRow the currentRow to set
     */
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#getContextMode()
     */
    public boolean isContextMode() {
        return this.contextMode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setContextMode(java.lang.String)
     */
    public void setContextMode(boolean mode) {
        this.contextMode = mode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#getLabelFromRepository()
     */
    public String getLabelFromRepository() {
        return this.labelFromRepository;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElementParameter#setLabelFromRepository(java.lang.String)
     */
    public void setLabelFromRepository(String label) {
        this.labelFromRepository = label;

    }

    public List<IElementParameterDefaultValue> getDefaultValues() {
        return this.defaultValues;
    }

    public void setDefaultValues(List<IElementParameterDefaultValue> defaultValues) {
        this.defaultValues = defaultValues;
    }

    /**
     * Getter for childParameters.
     * 
     * @return the childParameters
     */
    public Map<String, IElementParameter> getChildParameters() {
        if (childParameters == null) {
            childParameters = new HashMap<String, IElementParameter>();
        }
        return childParameters;
    }

    public IElementParameter getParentParameter() {
        return parentParameter;
    }

    public void setParentParameter(IElementParameter parentParameter) {
        this.parentParameter = parentParameter;
        // keep the same category with parent.
        this.setCategory(parentParameter.getCategory());
        parentParameter.getChildParameters().put(this.getName(), this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType#getElementValue()
     */
    public EList getElementValue() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType#getValue()
     */
    public String getValue() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType#isSetContextMode()
     */
    public boolean isSetContextMode() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType#setField(java.lang.String)
     */
    public void setField(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType#setValue(java.lang.String)
     */
    public void setValue(String value) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType#unsetContextMode()
     */
    public void unsetContextMode() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eAllContents()
     */
    public TreeIterator<EObject> eAllContents() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eClass()
     */
    public EClass eClass() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eContainer()
     */
    public EObject eContainer() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eContainingFeature()
     */
    public EStructuralFeature eContainingFeature() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eContainmentFeature()
     */
    public EReference eContainmentFeature() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eContents()
     */
    public EList<EObject> eContents() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eCrossReferences()
     */
    public EList<EObject> eCrossReferences() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
     */
    public Object eGet(EStructuralFeature feature) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature, boolean)
     */
    public Object eGet(EStructuralFeature feature, boolean resolve) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eIsProxy()
     */
    public boolean eIsProxy() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature)
     */
    public boolean eIsSet(EStructuralFeature feature) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eResource()
     */
    public Resource eResource() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eSet(org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     */
    public void eSet(EStructuralFeature feature, Object newValue) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.ecore.EObject#eUnset(org.eclipse.emf.ecore.EStructuralFeature)
     */
    public void eUnset(EStructuralFeature feature) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.notify.Notifier#eAdapters()
     */
    public EList<Adapter> eAdapters() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.notify.Notifier#eDeliver()
     */
    public boolean eDeliver() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.notify.Notifier#eNotify(org.eclipse.emf.common.notify.Notification)
     */
    public void eNotify(Notification notification) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.notify.Notifier#eSetDeliver(boolean)
     */
    public void eSetDeliver(boolean deliver) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType#getField()
     */
    public String getField() {
        // TODO Auto-generated method stub
        return null;
    }

}
