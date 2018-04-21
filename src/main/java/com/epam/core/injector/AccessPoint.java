package com.epam.core.injector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AccessPoint {
    String portal();

    String credentials();
}
