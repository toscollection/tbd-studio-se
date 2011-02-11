// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.EParameterFieldType;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public abstract class AbstractElement extends EObjectImpl implements Cloneable, IElement {

    public static final int ALPHA_VALUE = 50;

    private List<IElementParameter> listParam = new ArrayList<IElementParameter>();

    // property change listeners
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    protected void firePropertyChange(String prop, Object old, Object newValue) {
        listeners.firePropertyChange(prop, old, newValue);
    }

    protected void fireStructureChange(String prop, Object child) {
        listeners.firePropertyChange(prop, null, child);
    }

    // implemented in order to create listeners field
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        listeners = new PropertyChangeSupport(this);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        listeners.removePropertyChangeListener(l);
    }

    /**
     * Gives the value of the given property.
     * 
     * @param id
     * @return Object
     */
    public Object getPropertyValue(final String id) {
        IElementParameter param = this.getElementParameter(id);
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    /**
     * 
     * DOC xye Comment method "getPropertyValue".
     * 
     * @param id
     * @param paramName
     * @return
     */
    public Object getPropertyValue(final String id, final String paramName) {
        IElementParameter param = this.getElementParameter(id, paramName);
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    /**
     * Set the property of the object.
     * 
     * @param id
     * @param value
     */
    public void setPropertyValue(final String id, final Object value) {
        if (listParam == null) {
            return;
        }
        IElementParameter param = this.getElementParameter(id);
        if (param != null) {
            param.setValue(value);
        }
    }

    public void addElementParameter(IElementParameter parameter) {
        listParam.add(parameter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.ui.editor.IElement#getElementParameters()
     */
    public List<? extends IElementParameter> getElementParameters() {
        return listParam;
    }

    /**
     * The returned list can not be modified.
     * 
     * @return
     */
    public List<? extends IElementParameter> getElementParametersWithChildrens() {
        List<IElementParameter> fullListParam = new ArrayList<IElementParameter>(listParam);

        for (IElementParameter curParam : listParam) {
            for (String key : curParam.getChildParameters().keySet()) {
                IElementParameter childParam = curParam.getChildParameters().get(key);
                fullListParam.add(childParam);
            }
        }
        return fullListParam;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public void setElementParameters(List<? extends IElementParameter> parameters) {
        this.listParam = (List<IElementParameter>) parameters;
    }

    public IElementParameter getElementParameter(String name) {
        return findElementParameter(name, null);
    }

    /**
     * 
     * DOC xye Comment method "getElementParameter".
     * 
     * @param typeName
     * @param paramName
     * @return
     */
    public IElementParameter getElementParameter(String typeName, String paramName) {
        return findElementParameter(typeName, paramName);
    }

    private IElementParameter findElementParameter(String name, String paramName) {
        if (name.contains(":")) { // look for the parent first, then will retrieve the children //$NON-NLS-1$
            StringTokenizer token = new StringTokenizer(name, ":"); //$NON-NLS-1$
            String parentId = token.nextToken();
            String childId = token.nextToken();
            for (int i = 0; i < listParam.size(); i++) {
                if (listParam.get(i).getName().equals(parentId)) {
                    IElementParameter parent = listParam.get(i);
                    return parent.getChildParameters().get(childId);
                }
            }
        } else {
            for (IElementParameter elementParam : listParam) {
                if (elementParam.getName().equals(name)) {
                    return elementParam;
                }
            }
        }

        // if not found, look for the name if it's the name of a children
        // this code is added only for compatibility and will be executed only one time
        // to initialize the child.
        // The parameters name are unique, so we just take the first one.
        for (IElementParameter elementParam : listParam) {
            for (String key : elementParam.getChildParameters().keySet()) {
                IElementParameter param = elementParam.getChildParameters().get(key);
                if (paramName == null || paramName.equals("")) { //$NON-NLS-1$
                    if (param.getName().equals(name)) {
                        return param;
                    }
                } else {
                    if (param.getName().equals(name) && (elementParam.getName().equals(paramName))) {
                        return param;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Note that this will return only the first element parameter only.
     * 
     * @param fieldType
     * @return
     */
    public IElementParameter getElementParameterFromField(EParameterFieldType fieldType) {
        for (IElementParameter elementParam : listParam) {
            if (elementParam.getField().equals(fieldType)) {
                return elementParam;
            }
        }
        return null;
    }

    public List<IElementParameter> getElementParametersFromField(EParameterFieldType fieldType) {
        List<IElementParameter> params = new ArrayList<IElementParameter>();
        for (IElementParameter elementParam : listParam) {
            if (elementParam.getField().equals(fieldType)) {
                params.add(elementParam);
            }
        }
        return params;
    }

    public IElementParameter getElementParameterFromField(EParameterFieldType fieldType, EComponentCategory category) {
        for (IElementParameter elementParam : listParam) {
            if (elementParam.getCategory().equals(category) && elementParam.getField().equals(fieldType)) {
                return elementParam;
            }
        }
        // in case there is only one field of this type in all the element (all category) take the field type by default
        return getElementParameterFromField(fieldType);
    }

    public abstract String getElementName();

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElement#isReadOnly()
     */
    public boolean isReadOnly() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IElement#setReadOnly(boolean)
     */
    public void setReadOnly(boolean readOnly) {
        // not used for model, only for job designer
    }

}
