package com.nbvinas.movery.di;

import com.nbvinas.movery.deliveries.DeliveriesFragment;
import com.nbvinas.movery.details.DetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nvinas on 07/01/2018.
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract DeliveriesFragment deliveriesFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment detailsFragment();

}
