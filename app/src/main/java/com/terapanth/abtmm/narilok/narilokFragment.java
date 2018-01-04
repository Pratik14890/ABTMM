package com.terapanth.abtmm.narilok;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.Magazine;
import com.terapanth.abtmm.services.model.response.WS_MagazineResponse;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NarilokFragment extends Fragment {

    private final static String WS_GET_MAGAZINE_LIST = "GetMagazineList";

    List<Magazine> magazines;

    public NarilokFragment() {
        magazines = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(WS_GET_MAGAZINE_LIST);
        ws.addAuth();
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object o) {
                List<WS_MagazineResponse> list = Arrays.asList(WS_MagazineResponse[].class.cast(o));
                for(WS_MagazineResponse a : list)
                    magazines.add(a.getMagzine());
            }
        });
        ws.execute(WS_MagazineResponse[].class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_narilok_main, container, false);
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
