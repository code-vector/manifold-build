package com.lanyine.manifold.base.util;

import javax.validation.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 通过ValidatorFactory得到了一个 Validator 的实例. </br>
 * Validator是线程安全的,并且可以重复使用, 所以我们把它保存成一个类变量</br>
 *
 * @author shadow
 * @Description: 验证对象是否合法
 */
public class ValidatorTool {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    /**
     * 对目标对象的所有属性进行验证
     *
     * @param obj
     */
    public static void validate(Object obj) {
        if (obj == null)
            return;
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (violations != null && !violations.isEmpty()) {
            ConstraintViolation<Object> cv = violations.iterator().next();
            throw new ValidationException(obj.getClass().getName() + "[" + cv.getPropertyPath() + "]:" + cv.getMessage());
        }
    }

    /**
     * nspaces校验帮助方法，在ctrl中调用，配合hibernate的valid注解，ctrl中需要
     *
     * @param obj
     * @param ignoreFields 忽略字段列表，逗号分隔
     */
    public static void validate(Object obj, String ignoreFields) {
        if (obj == null) {
            return;
        }
        // 忽略列表
        Set<String> ignoreSet = new HashSet<>();
        if (ignoreFields != null) {
            String[] ignoreFieldArr = ignoreFields.replace(" ", "").split(",");
            for (String str : ignoreFieldArr) {
                ignoreSet.add(str);
            }
        }

        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (violations != null && !violations.isEmpty()) {
            Iterator<ConstraintViolation<Object>> it = violations.iterator();
            ConstraintViolation<Object> cv = null;
            while (it.hasNext()) {
                cv = it.next();
                if (ignoreSet.contains(cv.getPropertyPath().toString())) {
                    continue;
                }
                throw new ValidationException(
                        obj.getClass().getName() + "[" + cv.getPropertyPath() + "]:" + cv.getMessage());
            }
        }
    }

    /**
     * 对目标对象的非空属性进行验证
     *
     * @param obj
     */
    public static void validateNotNull(Object obj) {
        if (obj == null)
            return;
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (violations == null || violations.isEmpty()) {
            return;
        }

        for (ConstraintViolation<Object> cv : validator.validate(obj)) {
            if (cv.getInvalidValue() != null) {
                throw new ValidationException(
                        obj.getClass().getName() + "[" + cv.getPropertyPath() + "]:" + cv.getMessage());
            }
        }
    }
}
