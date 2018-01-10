package com.nbvinas.movery.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by nvinas on 07/01/2018.
 */
@Module
public abstract class MoveryAppModule {

    @Binds
    @Singleton
    abstract Context bindContext(Application application);
}
