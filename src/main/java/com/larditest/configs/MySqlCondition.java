package com.larditest.configs;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by PC on 28.10.2016.
 */
public class MySqlCondition implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
       if(context.getEnvironment().containsProperty("spring.datasource.url")){
            return true;
        }else{
            return false;
        }

    }
}
