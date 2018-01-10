package com.nbvinas.movery.deliveries;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.nbvinas.movery.Constants;
import com.nbvinas.movery.Delivery;
import com.nbvinas.movery.R;
import com.nbvinas.movery.details.DetailsActivity;
import com.nbvinas.movery.details.DetailsFragment;

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

        // Set up the deliveries list screen
        DeliveriesFragment deliveriesFragment =
                (DeliveriesFragment) getSupportFragmentManager().findFragmentById(R.id.deliveries_fragment);
        if (deliveriesFragment == null) {
            if (savedInstanceState != null) {
                return;
            }
            // Get the fragment from dagger
            deliveriesFragment = deliveriesFragmentProvider.get();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, deliveriesFragment);
            transaction.commit();
        }
    }

    @Override
    public void onTaskClick(Delivery clickedDelivery) {
        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.details_fragment);
        if (detailsFragment == null) {
            // DisplayFragment (Fragment B) is not in the layout (handset layout),
            // so start DisplayActivity (Activity B)
            // and pass it the info about the selected item
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(Constants.DELIVERY_KEY, clickedDelivery);
            startActivity(intent);
        } else {
            // DisplayFragment (Fragment B) is in the layout (tablet layout),
            // so tell the fragment to update
            detailsFragment.setUpDetailsAndMap(clickedDelivery);
        }
    }

    public DeliveriesFragment.DeliveryItemListener getListener() {
        return this;
    }
}
