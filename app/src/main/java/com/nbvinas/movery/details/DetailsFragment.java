package com.nbvinas.movery.details;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nbvinas.movery.Constants;
import com.nbvinas.movery.Delivery;
import com.nbvinas.movery.Location;
import com.nbvinas.movery.R;
import com.nbvinas.movery.di.ActivityScoped;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

/**
 * Created by nvinas on 06/01/2018.
 */
@ActivityScoped
public class DetailsFragment extends DaggerFragment implements GoogleMap.OnMarkerClickListener,
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private GoogleMap googleMap;
    private LatLng locationLatLng;

    private Delivery delivery;
    private Location location;

    private CardView details;
    private TextView name;
    private TextView contact;
    private CircleImageView image;
    private TextView description;
    private TextView address;
    private TextView fee;
    private Button sendMessage;

    @Inject
    public DetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_details, container, false);
        details = root.findViewById(R.id.layout_details);
        name = root.findViewById(R.id.name);
        contact = root.findViewById(R.id.contact_no);
        image = root.findViewById(R.id.receiver_image);
        description = root.findViewById(R.id.description);
        address = root.findViewById(R.id.address);
        fee = root.findViewById(R.id.fee);
        sendMessage = root.findViewById(R.id.btn_send_message);

        setUpDetailsAndMap();

        return root;
    }

    private void setUpDetailsAndMap() {
        // Get delivery from bundle
        Bundle args = getArguments();
        if (args == null) {
            Timber.d("Bundle not available!");
            return;
        }
        delivery = args.getParcelable(Constants.DELIVERY_KEY);
        if (delivery == null) {
            Timber.d("Delivery not available!");
            return;
        }
        location = delivery.getLocation();
        if (location == null) {
            Timber.d("Location not available!");
            return;
        }
        locationLatLng = new LatLng(location.getLat(), location.getLng());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        new OnMapAndViewReadyListener(mapFragment, this);

        setUpDetails();
    }

    public void setUpDetailsAndMap(Delivery clickedDelivery) {
        // Get delivery from bundle
        delivery = clickedDelivery;
        if (delivery == null) {
            Timber.d("Delivery not available!");
            return;
        }
        location = delivery.getLocation();
        if (location == null) {
            Timber.d("Location not available!");
            return;
        }
        locationLatLng = new LatLng(location.getLat(), location.getLng());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        new OnMapAndViewReadyListener(mapFragment, this);

        setUpDetails();
    }

    private void setUpDetails() {
        details.setVisibility(View.VISIBLE);
        name.setText(delivery.getReceiverName());
        contact.setText(delivery.getContactNo());
        description.setText(delivery.getDescription());
        address.setText(location.getAddress());
        fee.setText(getString(R.string.total_fee,
                String.valueOf(delivery.getFees())));
        Picasso.with(getActivity())
                .load(delivery.getImageUrl())
                .into(image);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("smsto:" + delivery.getContactNo()));
                sendIntent.putExtra("sms_body", getString(
                        R.string.delivery_message, delivery.getReceiverName()));
                startActivity(sendIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Hide the zoom controls as the button panel will cover it.
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkerToMap();

        // Set listeners for marker events.
        googleMap.setOnMarkerClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        googleMap.setContentDescription("Map with lots of markers.");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(locationLatLng)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void addMarkerToMap() {
        googleMap.addMarker(new MarkerOptions()
                .position(locationLatLng)
                .title(location.getAddress())
                .snippet(delivery.getReceiverName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }
}
