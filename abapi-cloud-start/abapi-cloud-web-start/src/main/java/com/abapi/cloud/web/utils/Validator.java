package com.abapi.cloud.web.utils;

import com.abapi.cloud.web.exception.ValidatorException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Title: Validator.java
 * @Description:
 *               <p>
 *               JSR303 规范（Bean Validation 规范）提供了对 JavaEE 和 Java SE 中的 Java Bean
 *               进行验证的方式。 该规范主要使用注解的方式来实现对 Java Bean 的验证功能，并且这种方式会覆盖使用 XML
 *               形式的验证描述符， 从而使验证逻辑从业务代码中分离出来
 *               </p>
 * @author duxing
 * @date 2016年7月29日 下午11:35:53
 * @version V1.0
 */
public class Validator {

    private volatile static ValidatorFactory factory = null;
    //
    // @Override
    // public boolean supports(Class<?> clazz) {
    // throw new JsonBindException("this method must be override");
    // }
    //
    // @Override
    // public void validate(Object target, Errors errors) {
    // throw new JsonBindException("this method must be override");
    // }

    /**
     * Validates all constraints on {@code object}.
     *
     * @param object
     *            object to validate
     * @param groups
     *            the group or list of groups targeted for validation (defaults
     *            to {@link Default})
     * @throws IllegalArgumentException
     *             if object is {@code null} or if {@code null} is passed to the
     *             varargs groups
     * @throws ValidationException
     *             if a non recoverable error happens during the validation
     *             process
     */
    public static <T> void validate(T object, Class<?>... groups) {
        Assert.notNull(object,"object is null");

        if (null == factory) {
            synchronized (Validator.class) {
                if (null == factory) {
                    factory = Validation.buildDefaultValidatorFactory();
                }
            }
        }

        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violationSet = validator.validate(object, groups);
        if (CollectionUtils.isNotEmpty(violationSet)) {
            for (ConstraintViolation<T> violation : violationSet) {
                throw new ValidatorException(violation.getMessage());
            }
        }
    }

    public static void hasErrs(Errors errors) {
        if (errors.hasErrors()) {
            List<ObjectError> objectErrorList = errors.getAllErrors();
            String message = "";

            ObjectError objectError;
            for(Iterator var3 = objectErrorList.iterator(); var3.hasNext(); message = objectError.getDefaultMessage()) {
                objectError = (ObjectError)var3.next();
            }

            throw new ValidatorException(message);
        }
    }

}
