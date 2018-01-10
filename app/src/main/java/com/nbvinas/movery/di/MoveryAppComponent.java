package com.nbvinas.movery.di;

import android.app.Application;

import com.nbvinas.movery.MoveryApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by nvinas on 06/01/2018.
 */
@Singleton
@Component(modules = {NetworkModule.class,
        MoveryAppModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface MoveryAppComponent extends AndroidInjector<MoveryApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        MoveryAppComponent.Builder app(Application app);

        MoveryAppComponent build();
    }
}
