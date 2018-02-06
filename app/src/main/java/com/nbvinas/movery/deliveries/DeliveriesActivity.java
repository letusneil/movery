package com.nbvinas.movery.deliveries;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.nbvinas.movery.Constants;
import com.nbvinas.movery.Delivery;
import com.nbvinas.movery.R;
import com.nbvinas.movery.details.DetailsActivity;
import com.nbvinas.movery.details.DetailsFragment;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class DeliveriesActivity extends DaggerAppCompatActivity implements DeliveriesFragment.DeliveryItemListener {

    @Inject
    Lazy<DeliveriesFragment> deliveriesFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveries);

        DeliveriesFragment deliveriesFragment =
                (DeliveriesFragment) getSupportFragmentManager().findFragmentById(R.id.deliveries_fragment);
        if (deliveriesFragment == null) {
            if (savedInstanceState != null) {
                return;
            }
            deliveriesFragment = deliveriesFragmentProvider.get();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, deliveriesFragment);
            transaction.commit();
        }
    }

    @Override
    public void onItemClick(@Nonnull Delivery clickedDelivery) {
        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.details_fragment);
        if (detailsFragment == null) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(Constants.DELIVERY_KEY, clickedDelivery);
            startActivity(intent);
        } else {
            detailsFragment.setDeliveryLocation(clickedDelivery);
        }
    }

    public DeliveriesFragment.DeliveryItemListener getListener() {
        return this;
    }
}
