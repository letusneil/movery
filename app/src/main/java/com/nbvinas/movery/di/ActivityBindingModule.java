package com.nbvinas.movery.di;

import com.nbvinas.movery.deliveries.DeliveriesActivity;
import com.nbvinas.movery.details.DetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nvinas on 06/01/2018.
 */
@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = ActivityModule.class)
    abstract DeliveriesActivity deliveriesActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ActivityModule.class)
    abstract DetailsActivity detailsActivity();

}
