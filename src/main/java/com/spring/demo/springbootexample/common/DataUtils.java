package com.spring.demo.springbootexample.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DataUtils {
    private static final Logger logger = LoggerFactory.getLogger(DataUtils.class);

    public DataUtils() {
    }

    public static <E> List<E> copyTo(List<?> source, Class<E> destinationClass) {
        return copyTo(source, destinationClass, (String[])null);
    }

    public static <E> List<E> copyTo(List<?> source, Class<E> destinationClass, String... ignore) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        } else {
            List<E> res = new ArrayList(source.size());
            source.stream().forEach((o) -> {
                try {
                    E e = destinationClass.newInstance();
                    BeanUtils.copyProperties(o, e, ignore);
                    res.add(e);
                } catch (Exception var5) {
                    logger.error("copy数据异常失败", var5);
                }

            });
            return res;
        }
    }

    public static <E> E copyTo(Object source, Class<E> destinationClass) {
        return copyTo(source, destinationClass, (String[])null);
    }

    public static <E> E copyTo(Object source, Class<E> destinationClass, String... ignore) {
        try {
            E e = destinationClass.newInstance();
            BeanUtils.copyProperties(source, e, ignore);
            return e;
        } catch (Exception var4) {
            logger.error("copy数据异常失败", var4);
            return null;
        }
    }

    public static <E> E copyToKeyValue(List<?> sourceList, Class<E> destinationClass, String fieldKey, String fieldValue) {
        try {
            E e = destinationClass.newInstance();
            if (CollectionUtils.isEmpty(sourceList)) {
                return null;
            } else {
                Class target = e.getClass();
                PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target);
                PropertyDescriptor[] var7 = targetPds;
                int var8 = targetPds.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    PropertyDescriptor targetPd = var7[var9];
                    Method writeMethod = targetPd.getWriteMethod();
                    if (writeMethod != null) {
                        Iterator var12 = sourceList.iterator();

                        while(var12.hasNext()) {
                            Object obj = var12.next();
                            Class source = obj.getClass();
                            PropertyDescriptor sourceKeyPd = BeanUtils.getPropertyDescriptor(source, fieldKey);
                            if (sourceKeyPd != null) {
                                Method readMethod = sourceKeyPd.getReadMethod();
                                if (readMethod != null) {
                                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                        readMethod.setAccessible(true);
                                    }

                                    Object key = readMethod.invoke(obj);
                                    String setMethod = writeMethod.getName();
                                    setMethod = key.toString().charAt(0) + setMethod.substring(4);
                                    if (Objects.equals(key, setMethod)) {
                                        PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source, fieldValue);
                                        if (sourcePd != null) {
                                            Method readValueMethod = sourcePd.getReadMethod();
                                            if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readValueMethod.getReturnType())) {
                                                try {
                                                    if (!Modifier.isPublic(readValueMethod.getDeclaringClass().getModifiers())) {
                                                        readValueMethod.setAccessible(true);
                                                    }

                                                    Object value = readValueMethod.invoke(obj);
                                                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                                        writeMethod.setAccessible(true);
                                                    }

                                                    writeMethod.invoke(e, value);
                                                } catch (Throwable var22) {
                                                    throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var22);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                return e;
            }
        } catch (Exception var23) {
            logger.error("数据复制异常", var23);
            return null;
        }
    }

    public static void copyTo(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (Exception var3) {
            logger.error("copy数据异常失败", var3);
        }

    }
}
