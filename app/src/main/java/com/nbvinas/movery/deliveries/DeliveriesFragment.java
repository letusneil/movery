package com.nbvinas.movery.deliveries;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nbvinas.movery.BuildConfig;
import com.nbvinas.movery.Delivery;
import com.nbvinas.movery.R;
import com.nbvinas.movery.di.ActivityScoped;
import com.nbvinas.movery.network.DeliveriesResponse;
import com.nbvinas.movery.network.api.DeliveriesApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;


/**
 * Created by nvinas on 06/01/2018.
 */
@ActivityScoped
public class DeliveriesFragment extends DaggerFragment {

    private DeliveriesAdapter adapter;

    private boolean forceUpdate = true;

    private DeliveryItemListener deliveryItemListener;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    Retrofit retrofit;

    @Inject
    public DeliveriesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_deliveries, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rv_deliveries);
        adapter = new DeliveriesAdapter(new ArrayList<Delivery>(), deliveryItemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                forceUpdate = true;
                loadDeliveries();
            }
        });

        loadDeliveries();

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DeliveryItemListener) {
            deliveryItemListener = (DeliveryItemListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implemenent DeliveriesFragment.DeliveryItemListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        deliveryItemListener = null;
    }

    private void loadDeliveries() {
        if (forceUpdate) {
            loadDeliveriesFromRemote();
        } else {
            loadDeliveriesFromLocal();
        }
    }

    private void loadDeliveriesFromRemote() {
        swipeRefreshLayout.setRefreshing(true);

        DeliveriesApi deliveriesApi = retrofit.create(DeliveriesApi.class);
        deliveriesApi.deliveries("application/json",
                getString(R.string.json_stub_user_key),
                getString(R.string.json_stub_project_key))
                .enqueue(new Callback<DeliveriesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DeliveriesResponse> call, @NonNull Response<DeliveriesResponse> response) {

                        switch (response.code()) {
                            case 200:
                                if (response.body() != null) {
                                    DeliveriesResponse deliveriesResponse = response.body();
                                    if (deliveriesResponse != null) {

                                        List<Delivery> deliveries = deliveriesResponse.getDeliveries();
                                        if (deliveries != null && deliveries.size() > 0) {
                                            // Save response to db
                                            Realm realm = Realm.getDefaultInstance();
                                            realm.beginTransaction();
                                            realm.copyToRealmOrUpdate(deliveries);
                                            realm.commitTransaction();

                                            forceUpdate = false;
                                            swipeRefreshLayout.setRefreshing(false);
                                            loadDeliveriesFromLocal();
                                        }
                                    }
                                }
                                break;
                            default:
                                swipeRefreshLayout.setRefreshing(false);
                                loadDeliveriesFromLocal();
                                Timber.d(response.message());
                                Toast.makeText(getActivity(), response.message(),
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<DeliveriesResponse> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        Timber.d(t.getMessage());
                        Toast.makeText(getActivity(), t.getMessage(),
                                Toast.LENGTH_LONG).show();
                        loadDeliveriesFromLocal();
                    }
                });
    }

    private void loadDeliveriesFromLocal() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Delivery> deliveries = realm.where(Delivery.class).findAll();
        adapter.replaceData(deliveries);
        realm.commitTransaction();
    }

    public interface DeliveryItemListener {
        void onTaskClick(Delivery clickedDelivery);
    }

    private class DeliveriesAdapter extends RecyclerView.Adapter<DeliveriesAdapter.ViewHolder> {

        private List<Delivery> deliveries;
        private DeliveryItemListener itemListener;

        private DeliveriesAdapter(List<Delivery> deliveries, DeliveryItemListener itemListener) {
            this.deliveries = deliveries;
            this.itemListener = itemListener;
        }

        private void replaceData(List<Delivery> deliveries) {
            setList(deliveries);
            notifyDataSetChanged();
        }

        private void setList(List<Delivery> deliveries) {
            this.deliveries = deliveries;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Delivery delivery = deliveries.get(position);
            holder.description.setText(delivery.getDescription());
            holder.address.setText(delivery.getLocation().getAddress());
            Picasso.with(getActivity())
                    .load(delivery.getImageUrl())
                    .into(holder.image);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemListener != null) {
                        itemListener.onTaskClick(delivery);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return deliveries.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView description;
            TextView address;
            CircleImageView image;

            ViewHolder(View itemView) {
                super(itemView);

                description = itemView.findViewById(R.id.tv_item_description);
                address = itemView.findViewById(R.id.tv_item_address);
                image = itemView.findViewById(R.id.image_item);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.close();
        }
    }
}


