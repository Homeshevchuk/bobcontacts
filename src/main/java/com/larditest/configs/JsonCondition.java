package com.larditest.configs;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by PC on 28.10.2016.
 */

public class JsonCondition implements Condition{
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        System.out.println("asdasdasdasd");

        if(context.getEnvironment().containsProperty("storePath")){
            return true;
        }else{
            return false;
        }
    }
}
