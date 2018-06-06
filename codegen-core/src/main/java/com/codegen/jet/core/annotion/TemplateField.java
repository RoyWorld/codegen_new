package com.codegen.jet.core.annotion;

import java.lang.annotation.*;

/**
 * 用于注解Domain的field，其对应于模板中的各个属性
 * Created by RoyChan on 2017/12/11.
 */
@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TemplateField {
    String value() default "";
}
