package br.inhonhas.feriapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import br.inhonhas.feriapp.Event;
import br.inhonhas.feriapp.R;
import br.inhonhas.feriapp.service.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    RecyclerView list;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        list = (RecyclerView) view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getEvents();
    }

    private void getEvents() {
        Service.getInstance(true)
                .getEvents()
                .enqueue(new Callback<Event[]>() {
                    @Override
                    public void onResponse(Call<Event[]> call, Response<Event[]> response) {
                        if(response.errorBody() == null) {
                            OutAdapter adapter = new OutAdapter(Arrays.asList(response.body()));
                            list.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<Event[]> call, Throwable t) {

                    }
                });
    }
}
