package com.nbvinas.movery.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by nvinas on 06/01/2018.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped {
}
