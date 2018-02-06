package com.nbvinas.movery.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.nbvinas.movery.Constants;
import com.nbvinas.movery.Delivery;
import com.nbvinas.movery.R;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by nvinas on 08/01/2018.
 */
public class DetailsActivity extends DaggerAppCompatActivity {

    @Inject
    Lazy<DetailsFragment> detailsFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Delivery delivery = intent.getParcelableExtra(Constants.DELIVERY_KEY);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(delivery.getDescription());
        }

        DetailsFragment detailsFragment =
                (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (detailsFragment == null) {
            if (savedInstanceState != null) {
                return;
            }
            detailsFragment = detailsFragmentProvider.get();
            detailsFragment.setArguments(getIntent().getExtras());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, detailsFragment);
            transaction.commit();
        }

    }
}
