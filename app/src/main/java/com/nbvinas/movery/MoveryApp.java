package com.nbvinas.movery;


import android.support.annotation.NonNull;

import com.nbvinas.movery.di.DaggerMoveryAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by nvinas on 05/01/2018.
 */

public class MoveryApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerMoveryAppComponent.builder().app(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(@NonNull StackTraceElement element) {
                return super.createStackElementTag(element) + ":L" + element.getLineNumber();
            }
        });
    }
}
