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
package org.talend.administrator.common.util.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.talend.administrator.common.data.ArraysComparator;
import org.talend.administrator.common.data.MultipleValueHolder;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class BeanAccessorsInvoker {

    private static BeanAccessorsCacheContainer beanInfoAccessor = new BeanAccessorsCacheContainer();

    protected static Object[] getterParameters = new Object[0];

    protected static Class[] getterParametersTypes = new Class[0];

    protected static Class[] settersParametersTypes = { String.class, Integer.class, MultipleValueHolder.class,
            Date.class, null };

    private static DateFormat defaultDateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");

    private static DateFormat shortDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    private static DateFormat longDateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static DateFormat[] supportedDateFormats = { shortDateFormatter, longDateFormatter, defaultDateFormatter };

    public void setBeanPropertyValue(Object bean, String property, String value) {
        Object targetBean = getConcernedBean(bean, property);
        MethodAndType methodAndType = getSetterMethodAndType(targetBean, property);
        if (methodAndType == null) {
            throw new IllegalArgumentException("Bean " + bean.getClass() + " has no setter for property " + property
                    + " with one of the supported types.");
        }
        try {
            methodAndType.method.invoke(bean, new Object[] { translateToClass(value, methodAndType.type) });
        } catch (Exception e) {
            throw new RuntimeException("Exception occured while trying to acces to bean property: '" + property
                    + "' setter. Exception message: " + e);
        }
    }

    public void setBeanPropertyValue(Object bean, String property, Object value) {
        if (bean == null) {
            throw new NullPointerException("bean is null");
        }
        Object targetBean = getConcernedBean(bean, property);
        Method method = beanInfoAccessor.getPropertySetter(targetBean, getLastAccessor(property), value == null ? null
                : value.getClass());
        if (method == null) {
            throw new IllegalArgumentException("Bean "
                    + bean.getClass()
                    + " has no setter for property '"
                    + property
                    + "' with "
                    + (value != null ? "type: " + value.getClass().getName() + " (value: " + value + ")"
                            : "a null value"));
        }
        try {
            method.invoke(targetBean, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception occured while trying to acces to bean property: '" + property
                    + "' setter. Exception message: " + e);
        }
    }

    public void setBeanPropertyValue(Object bean, String property, Object value, Class setterClass) {
        Object targetBean = getConcernedBean(bean, property);
        Method method = beanInfoAccessor.getPropertySetter(targetBean, property, setterClass);
        if (method == null) {
            throw new IllegalArgumentException("Bean " + bean.getClass() + " has no setter for property " + property
                    + " with one of the supported types.");
        }
        try {
            method.invoke(bean, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception occured while trying to acces to bean property: '" + property
                    + "' setter. Exception message: " + e);
        }
    }

    protected String getConcernedBeanPath(String property) {
        int indexOfLastProperty = property.lastIndexOf(".");
        String lastBeanPath;
        if (indexOfLastProperty == -1) {
            lastBeanPath = "";
        } else {
            lastBeanPath = property.substring(0, indexOfLastProperty);
        }
        return lastBeanPath;
    }

    protected String getLastAccessor(String property) {
        int indexOfLastProperty = property.lastIndexOf(".");
        String lastAccessor;
        if (indexOfLastProperty == -1) {
            lastAccessor = property;
        } else {
            lastAccessor = property.substring(indexOfLastProperty + 1);
        }
        return lastAccessor;
    }

    protected Object getConcernedBean(Object bean, String property) {
        String beanPath = getConcernedBeanPath(property);
        if (beanPath.length() == 0) {
            return bean;
        }

        return getBeanPropertyValue(bean, beanPath);
    }

    public Object getBeanPropertyValue(Object bean, String property) {
        if (bean == null) {
            throw new IllegalArgumentException("Bean can't be null");
        }

        int indexOfLocalProperty = property.indexOf(".");
        String localProperty;
        if (indexOfLocalProperty == -1) {
            localProperty = property;
        } else {
            localProperty = property.substring(0, indexOfLocalProperty);
        }

        MethodAndType methodAndType = getGetterMethodAndType(bean, localProperty);
        if (methodAndType == null) {
            throw new IllegalArgumentException("Bean '" + bean.getClass() + "' has no getter for property '"
                    + localProperty + "' with one of the supported types. Bean value:\n\t" + bean);
        }
        try {
            Object result = methodAndType.method.invoke(bean, getterParameters);
            if (indexOfLocalProperty == -1) {
                return result;
            } else {
                String subProperties = property.substring(indexOfLocalProperty + 1);
                return getBeanPropertyValue(result, subProperties);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception occured while trying to acces to bean property: '" + property
                    + "' getter. Exception message: " + e);
        }
    }

    public static List<String> getPropertiesWithGetter(Class beanClass, boolean includeInherited) {
        return getPropertiesWithAccessor(beanClass, false, includeInherited);
    }

    public static List<String> getPropertiesWithSetter(Class beanClass, boolean includeInherited) {
        return getPropertiesWithAccessor(beanClass, false, includeInherited);
    }

    protected static List<String> getPropertiesWithAccessor(Class beanClass, boolean setter, boolean includeInherited) {
        if (beanClass == null) {
            throw new IllegalArgumentException("beanClass can't be null");
        }

        Method[] methods = beanClass.getMethods();
        ArrayList<String> toReturn = new ArrayList<String>(methods.length);
        for (int i = 0; i < methods.length; i++) {
            Method actualMethod = methods[i];
            String actualMethodName = actualMethod.getName();
            if (actualMethodName.startsWith(setter ? "set" : "get")) {
                if (!includeInherited && !actualMethod.getDeclaringClass().equals(beanClass)) {
                    continue;
                }

                boolean found = false;
                if (setter) {
                    for (int j = 0; j < settersParametersTypes.length; j++) {
                        if (ArraysComparator.areEqual(actualMethod.getParameterTypes(),
                                new Class[] { settersParametersTypes[i] })) {
                            found = true;
                        }
                    }
                } else {
                    if (ArraysComparator.areEqual(actualMethod.getParameterTypes(), getterParametersTypes)) {
                        found = true;
                    }

                }
                if (found) {
                    String beanName = actualMethodName.substring("Xet".length());
                    beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                    toReturn.add(beanName);
                }
            }
        }

        return toReturn;
    }

    public boolean hasSetter(Object bean, String property) {
        return getSetterMethodAndType(bean, property) != null;
    }

    public boolean hasGetter(Object bean, String property) {
        return getGetterMethodAndType(bean, property) != null;
    }

    public Object translateToClass(String value, Class type) {
        if (value == null) {
            return null;
        }
        if (type.equals(String.class)) {
            return value;
        } else if (type.equals(Date.class)) {
            Date toReturn = null;
            for (int i = 0; i < supportedDateFormats.length; i++) {
                try {
                    toReturn = supportedDateFormats[i].parse(value);
                } catch (ParseException ex) {
                    // do nothing
                }
                if (toReturn != null) {
                    return toReturn;
                }
            }
            if (toReturn == null) {
                throw new IllegalArgumentException("Date string representation: '" + value
                        + "' can't be parsed by any of the supported formatters.");
            }
        }

        Constructor constr;
        try {
            constr = type.getConstructor(new Class[] { String.class });
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Can't find a constructor with a string argument for class: "
                    + type.getName());
        }
        try {
            return constr.newInstance(new Object[] { value });
        } catch (Exception e) {
            throw new RuntimeException("Can't transform " + value + " form String to " + type.getName());
        }
    }

    public static boolean isInstanceof(Class toTest, Class against) {
        if (toTest == null || against == null) {
            throw new IllegalArgumentException("Can't pass null on neither arguments");
        }

        if (toTest.equals(against)) {
            return true;
        }
        if (toTest.getSuperclass().equals(against)) {
            return true;
        }
        Class[] interfaces = toTest.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].equals(against)) {
                return true;
            }
        }

        return false;
    }

    public static String getPropertyNameFromGetter(Method getter) {
        String methodName = getter.getName();
        if (methodName.startsWith("get")) {
            String toReturn = methodName.substring("get".length());
            return toReturn.substring(0, 1).toLowerCase() + toReturn.substring(1);
        }
        return null;
    }

    /**
     * DOC mhirt BeanAccessorsInvoker class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    private final class MethodAndType {

        private Method method;

        private Class type;

        private MethodAndType(Method method, Class type) {
            this.method = method;
            this.type = type;
        }
    }

    private MethodAndType getSetterMethodAndType(Object bean, String property) {
        return getAccessorDetail(bean, property, true);
    }

    private MethodAndType getGetterMethodAndType(Object bean, String property) {
        return getAccessorDetail(bean, property, false);
    }

    private MethodAndType getAccessorDetail(Object bean, String property, boolean setter) {
        try {
            Object concernedBean = getConcernedBean(bean, property);

            Method toInvoke = null;
            Class setterType = null;
            if (setter) {
                for (int i = 0; i < settersParametersTypes.length; i++) {
                    toInvoke = beanInfoAccessor.getPropertySetter(concernedBean, getLastAccessor(property),
                            settersParametersTypes[i]);
                    if (toInvoke != null) {
                        setterType = settersParametersTypes[i];
                        break;
                    }
                }
            } else {
                toInvoke = beanInfoAccessor.getPropertyGetter(concernedBean, getLastAccessor(property));
            }
            if (toInvoke == null) {
                return null;
            }

            return new MethodAndType(toInvoke, setterType);
        } catch (Exception e) {
            return null;
        }
    }

    public void copyValue(Object source, String sourceProperty, Object target, String targetProperty) {
        Object sourceValue = getBeanPropertyValue(source, sourceProperty);
        if (targetProperty == null) {
            targetProperty = sourceProperty;
        }
        setBeanPropertyValue(target, targetProperty, sourceValue);
    }

    public void copyValues(Object source, String[] sourceProperties, Object target, String[] targetProperties) {
        if (sourceProperties == null) {
            throw new IllegalArgumentException("sourceProperties can't be null");
        }

        if (targetProperties != null) {
            if (sourceProperties.length != targetProperties.length) {
                throw new IllegalArgumentException("sourceProperties and targetProperties must have the same length: "
                        + sourceProperties.length + "!=" + targetProperties.length);
            }
        } else {
            targetProperties = sourceProperties;
        }

        for (int i = 0; i < targetProperties.length; i++) {
            copyValue(source, sourceProperties[i], target, targetProperties[i]);
        }
    }

    public void copyValues(Object source, Object target, String[] properties) {
        copyValues(source, properties, target, properties);
    }

    public void copyAllPossibleValues(Object source, Object target, String[] properties) {
        if (properties == null || properties.length == 0) {
            throw new IllegalArgumentException("properties can't be null");
        }

        for (int i = 0; i < properties.length; i++) {
            try {
                copyValue(source, properties[i], target, properties[i]);
            } catch (Exception exc) {
                // do nothing
            }
        }
    }
}
