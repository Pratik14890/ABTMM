package com.terapanth.abtmm.narilok;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terapanth.abtmm.R;
import com.terapanth.abtmm.adapters.NarilokRecycleViewAdapter;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.Magazine;
import com.terapanth.abtmm.services.model.response.WS_MagazineResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NarilokFragment extends Fragment {

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

        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(WS_GET_MAGAZINE_LIST);
        ws.addAuth();
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object o) {
                List<WS_MagazineResponse> list = Arrays.asList(WS_MagazineResponse[].class.cast(o));
                for(WS_MagazineResponse a : list)
                    magazines.add(a.getMagzine());
                adapter = new NarilokRecycleViewAdapter(magazines);

                recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        });
        ws.execute(WS_MagazineResponse[].class);

        return view;
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
