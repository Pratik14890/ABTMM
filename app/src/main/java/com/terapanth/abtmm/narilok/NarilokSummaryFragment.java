package com.terapanth.abtmm.narilok;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.narilok.adapters.NarilokRecycleViewAdapter;
import com.terapanth.abtmm.narilok.adapters.NarilokSummaryRecycleViewAdapter;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.Magazine;
import com.terapanth.abtmm.services.model.MagazineSummary;
import com.terapanth.abtmm.services.model.response.WS_MagazineResponse;
import com.terapanth.abtmm.services.model.response.WS_MagazineSummaryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NarilokSummaryFragment extends Fragment {

    private final static String GET_MAGAZINE_SUMMARY_LIST = "GetMagazineSummaryList";

    String magazineId;

    List<MagazineSummary> magazines;
    RecyclerView recyclerView;
    NarilokSummaryRecycleViewAdapter adapter = null;


    public NarilokSummaryFragment() {
        magazines = new ArrayList<>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.magazineId = this.getArguments().getString("magazineId");

        View view = inflater.inflate(R.layout.fragment_narilok_summary_list, container, false);
        init(view);
        return view;
    }
    public void init(final View view){
        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(GET_MAGAZINE_SUMMARY_LIST);
        ws.addAuth();
        ws.addParameter("MagazineId", magazineId, String.class);
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object o) {
                try {
                    Log.i("narilokFragment", "Response Object : " + new Gson().toJson(o));

                    magazines.clear();

                    List<WS_MagazineSummaryResponse> list = Arrays.asList(WS_MagazineSummaryResponse[].class.cast(o));
                    for (WS_MagazineSummaryResponse a : list) {
                        magazines.add(a.getMagzineSuammary());
                    }
                    adapter.notifyDataSetChanged();
                }
                catch (ClassCastException ex){
                    Log.e("narilokFragment", "Exception in casting response object in expected Object", ex);
                }
            }
        });
        ws.execute(WS_MagazineSummaryResponse[].class);

        adapter = new NarilokSummaryRecycleViewAdapter(getActivity(), magazines);

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
}
