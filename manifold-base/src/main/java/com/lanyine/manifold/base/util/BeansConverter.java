package com.lanyine.manifold.base.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象复制工具
 *
 * @author shadow
 */
public class BeansConverter {
    /**
     * 缓存已经做过转换的Bean的key-value
     */
    private static Map<String, Map<String, PropertyDescriptor>> cache = new ConcurrentHashMap<>();

    /**
     * 单个对象转换
     *
     * @param src        源对象
     * @param desctClazz 目标对象的Class
     * @param <T>
     * @return
     */
    public static <T> T copy(Object src, Class<T> desctClazz) {
        return convert(src, desctClazz, false);
    }

    /**
     * List 对象转换
     *
     * @param srcs       源对象
     * @param desctClazz 目标对象的class
     * @return
     */
    public static <T> List<T> copy(List<?> srcs, Class<T> desctClazz) {
        if (srcs == null)
            return new ArrayList<T>();

        List<T> dests = new ArrayList<T>();
        for (Object src : srcs) {
            dests.add(copy(src, desctClazz));
        }
        return dests;
    }

    /**
     * 将对象的属性转化为MAP集合，如果属性为空，将过滤此属性
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, true);

    }

    /**
     * 将对象的属性转化为MAP集合
     *
     * @param bean      需要转化的对象
     * @param ignorNull true:空的属性将不添加到MAP中，false：全部添加
     * @return
     */
    public static Map<String, Object> beanToMap(Object bean, boolean ignorNull) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (bean == null)
            return returnMap;
        try {
            Map<String, PropertyDescriptor> descriptors = getCachePropertyDescriptors(bean.getClass());
            for (PropertyDescriptor descriptor : descriptors.values()) {
                String propertyName = descriptor.getName();
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (ignorNull) {
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    }
                } else {
                    returnMap.put(propertyName, result);
                }
            }
        } catch (Exception e) {
            throw new BeanConverterException(e);
        }
        return returnMap;
    }

    private static synchronized Map<String, PropertyDescriptor> getCachePropertyDescriptors(Class<?> clazz)
            throws IntrospectionException {
        String canonicalName = clazz.getCanonicalName();

        Map<String, PropertyDescriptor> map = cache.get(canonicalName);

        if (map == null) {
            map = new ConcurrentHashMap<>();

            BeanInfo srcBeanInfo = Introspector.getBeanInfo(clazz);

            PropertyDescriptor[] descriptors = srcBeanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                Method readMethod = descriptor.getReadMethod();
                Method writeMethod = descriptor.getWriteMethod();

                String name = descriptor.getName();

                if (readMethod == null)
                    try {
                        readMethod = clazz.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));

                        descriptor.setReadMethod(readMethod);
                    } catch (NoSuchMethodException | SecurityException e) {
                    }

                if (writeMethod == null)
                    try {
                        writeMethod = clazz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1),
                                descriptor.getPropertyType());

                        descriptor.setWriteMethod(writeMethod);
                    } catch (NoSuchMethodException | SecurityException e) {
                    }

                if (readMethod != null && writeMethod != null) {
                    map.put(descriptor.getName(), descriptor);
                }
            }

            cache.put(canonicalName, map);
        }

        return map;
    }

    public static class BeanConverterException extends RuntimeException {
        private static final long serialVersionUID = 152873897614690397L;

        public BeanConverterException(Throwable cause) {
            super(cause);
        }
    }

    protected static <T> T convert(Object src, Class<T> destClass, boolean setDefaultValForNull)
            throws BeanConverterException {
        if (src == null)
            return null;

        try {
            T dest = destClass.newInstance();
            convert(src, dest, setDefaultValForNull);
            return dest;
        } catch (Exception e) {
            throw new BeanConverterException(e);
        }
    }

    /**
     * @param src
     * @param dest
     * @param setDefaultValForNull 是否为null,设置默认值（null=>0,null=>""）
     * @return
     * @throws BeanConverterException
     */
    protected static <T> T convert(Object src, T dest, boolean setDefaultValForNull) throws BeanConverterException {
        if (src == null)
            return null;

        try {
            Class<? extends Object> destClass = dest.getClass();
            Map<String, PropertyDescriptor> srcDescriptors = getCachePropertyDescriptors(src.getClass());
            Map<String, PropertyDescriptor> destDescriptors = getCachePropertyDescriptors(destClass);

            Set<String> keys = destDescriptors.keySet();
            for (String key : keys) {
                PropertyDescriptor srcDescriptor = srcDescriptors.get(key);

                if (srcDescriptor == null)
                    continue;

                PropertyDescriptor destDescriptor = destDescriptors.get(key);

                Object value = srcDescriptor.getReadMethod().invoke(src);

                Class<?> propertyType = destDescriptor.getPropertyType();

                Method writeMethod = destDescriptor.getWriteMethod();
                if (writeMethod == null) {
                    String name = destDescriptor.getName();
                    try {
                        writeMethod = destClass.getMethod(
                                "set" + name.substring(0, 1).toUpperCase() + name.substring(1),
                                destDescriptor.getPropertyType());

                        destDescriptor.setWriteMethod(writeMethod);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (writeMethod != null) {
                    // 类型匹配
                    boolean matched = propertyType == srcDescriptor.getPropertyType();
                    if (!matched) {
                        if (value != null || setDefaultValForNull) {
                            value = toValue(srcDescriptor, value, propertyType);
                        }
                    }
                    // 设置默认值
                    if (value == null && setDefaultValForNull) {
                        if (destDescriptor.getPropertyType() == Long.class
                                || destDescriptor.getPropertyType() == Integer.class
                                || destDescriptor.getPropertyType() == Short.class
                                || destDescriptor.getPropertyType() == Double.class
                                || destDescriptor.getPropertyType() == Float.class) {
                            value = 0;
                        } else if (destDescriptor.getPropertyType() == String.class) {
                            value = "";
                        } else if (destDescriptor.getPropertyType() == BigDecimal.class) {
                            value = new BigDecimal("0");
                        }
                    }

                    if (value != null) {
                        writeMethod.invoke(dest, value);
                    }
                }
            }

            return dest;
        } catch (Exception e) {
            throw new BeanConverterException(e);
        }
    }

    //
    private static Object toValue(PropertyDescriptor srcDescriptor, Object value, Class<?> propertyType) {
        if (value instanceof Enum) {
            if (propertyType == String.class) {
                value = ((Enum<?>) value).name();
            } else {
                value = value.toString();
            }
        }

        if (propertyType == BigDecimal.class) {// 对BigDecimal类型的处理
            value = (value == null) ? new BigDecimal("0") : new BigDecimal(value.toString());
        } else if (propertyType == byte.class || propertyType == Byte.class) {// Byte
            value = (value == null) ? Byte.valueOf("0") : Byte.valueOf(value.toString());
        } else if (propertyType == short.class || propertyType == Short.class) {// Short
            value = (value == null) ? Short.valueOf("0") : Short.valueOf(value.toString());
        } else if (propertyType == int.class || propertyType == Integer.class) {// Integer
            if (srcDescriptor.getPropertyType() == Date.class) {
                value = (value == null) ? null : (int) ((Date) value).getTime();
            } else if (srcDescriptor.getPropertyType() == boolean.class
                    || srcDescriptor.getPropertyType() == Boolean.class) {
                value = Boolean.parseBoolean(value.toString()) ? 1 : 0;
            } else {
                value = (value == null) ? Integer.valueOf("0") : Integer.valueOf(value.toString());
            }
        } else if (propertyType == long.class || propertyType == Long.class) {// Long
            if (srcDescriptor.getPropertyType() == Date.class) {
                value = (value == null) ? null : ((Date) value).getTime();
            } else {
                value = (value == null) ? Long.valueOf("0") : Long.valueOf(value.toString());
            }
        } else if (propertyType == double.class || propertyType == Double.class) {
            value = (value == null) ? Double.valueOf("0") : Double.valueOf(value.toString());
        } else if (propertyType == Date.class) {
            if (value != null && (srcDescriptor.getPropertyType() == Long.class
                    || srcDescriptor.getPropertyType() == Integer.class || srcDescriptor.getPropertyType() == long.class
                    || srcDescriptor.getPropertyType() == int.class)) {
                Long val = Long.valueOf(value.toString());

                if (val.longValue() != 0)
                    value = new Date(val);
                else
                    value = null;
            }
        } else if (propertyType == String.class && srcDescriptor.getPropertyType() != String.class) {
            if (value != null) {
                value = value.toString();
            }
        } else if (propertyType == boolean.class || propertyType == Boolean.class) {
            if (value.toString().matches("[0|1]")) {
                value = "1".equals(value.toString());
            }
        } else if (propertyType.isEnum() && value != null) {
            Object[] constants = propertyType.getEnumConstants();
            for (Object constant : constants) {
                if (value instanceof String) {
                    if (((Enum<?>) constant).name().equals(value.toString())) {
                        value = constant;
                        break;
                    }
                } else if (NumberUtils.isNumber(value.toString())) {
                    if (constant instanceof Enum) {
                        if ((Integer.valueOf(((Enum<?>) constant).toString())) == NumberUtils.toInt(value.toString())) {
                            value = constant;
                            break;
                        }
                    } else {
                        if (((Enum<?>) constant).ordinal() == NumberUtils.toInt(value.toString())) {
                            value = constant;
                            break;
                        }
                    }
                }
            }
        }
        return value;
    }
}