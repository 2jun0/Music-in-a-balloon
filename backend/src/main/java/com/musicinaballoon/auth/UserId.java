package com.musicinaballoon.auth;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Parameter(name = "userId", description = "user id", in = ParameterIn.COOKIE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
}
