package com.terapanth.abtmm.narilok;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.google.gson.Gson;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.narilok.adapters.NarilokRecycleViewAdapter;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.Magazine;
import com.terapanth.abtmm.services.model.response.WS_MagazineResponse;

import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NarilokFragment extends Fragment implements ChangeFragment {

    private final static String WS_GET_MAGAZINE_LIST = "GetMagazineList";

    List<Magazine> magazines;
    RecyclerView recyclerView;
    NarilokRecycleViewAdapter adapter = null;


    public NarilokFragment() {
        magazines = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.fragment_narilok_main, container, false);
        init(view);
        return view;
    }

    public void init(final View view){
        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(WS_GET_MAGAZINE_LIST);
        ws.addAuth();
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object o) {
                try {
                    Log.i("narilokFragment", "Response Object : " + new Gson().toJson(o));

                    magazines.clear();

                    List<WS_MagazineResponse> list = Arrays.asList(WS_MagazineResponse[].class.cast(o));
                    for (WS_MagazineResponse a : list) {
                        magazines.add(a.getMagzine());
                    }
                    adapter.notifyDataSetChanged();
                }
                catch (ClassCastException ex){
                    Log.e("narilokFragment", "Exception in casting response object in expected Object", ex);
                }
            }
        });
        ws.execute(WS_MagazineResponse[].class);

        adapter = new NarilokRecycleViewAdapter(getActivity(), magazines, this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void changeFragmentWithInput(String param) {
        // Assuming here param will be the magazine id passed by adapter
        Bundle bundle = new Bundle();
        bundle.putString("magazineId", param);

        FragmentManager fragmentManager = getFragmentManager();
        NarilokSummaryFragment nextFragment = new NarilokSummaryFragment();
        nextFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .addToBackStack(NarilokFragment.class.getSimpleName())
                .commit();

    }
}
