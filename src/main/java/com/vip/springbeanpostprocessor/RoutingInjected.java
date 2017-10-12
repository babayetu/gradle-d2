package com.vip.springbeanpostprocessor;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;


@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Component
public @interface RoutingInjected {

}
