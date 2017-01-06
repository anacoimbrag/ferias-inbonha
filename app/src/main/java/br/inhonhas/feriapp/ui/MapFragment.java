package br.inhonhas.feriapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.inhonhas.feriapp.Event;
import br.inhonhas.feriapp.R;
import br.inhonhas.feriapp.service.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anacoimbra on 05/01/17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MapFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapFragment newInstance(int sectionNumber) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(googleMap != null) getEvents(googleMap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        getEvents(googleMap);
    }

    private void getEvents(final GoogleMap googleMap) {
        Service.getInstance(true)
                .getEvents()
                .enqueue(new Callback<Event[]>() {
                    @Override
                    public void onResponse(Call<Event[]> call, Response<Event[]> response) {
                        if(response.errorBody() == null) {
                            LatLngBounds.Builder bounds = LatLngBounds.builder();
                            BitmapDescriptor ana = BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_ana);
                            BitmapDescriptor matheus = BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_matheus);
                            for (Event event : response.body()) {
                                LatLng position = new LatLng(event.getLat(), event.getLng());
                                Marker marker = googleMap.addMarker(new MarkerOptions()
                                        .position(position)
                                        .icon(event.getWhen() == null || event.getWhen().isEmpty() ? ana : matheus));
                                bounds.include(position);
                            }

                            if(response.body().length > 0) {
                                googleMap.animateCamera(CameraUpdateFactory
                                        .newLatLngBounds(bounds.build(), 50));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Event[]> call, Throwable t) {

                    }
                });
    }
}
