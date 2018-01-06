package com.terapanth.abtmm.narilok;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.narilok.adapters.NarilokAdapter;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.Magazine;
import com.terapanth.abtmm.services.model.response.WS_MagazineResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NarilokFragment extends Fragment implements View.OnClickListener {

    public static MainActivity mainActivity;
    private Context context;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageButton btnBack;
    private TextView txtBack;
    private ImageButton btnShare;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Magazine> magazines;
    private NarilokAdapter adapter;

    private final static String WS_GET_MAGAZINE_LIST = "GetMagazineList";

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

        mainActivity = (MainActivity) getActivity();
        this.context = mainActivity.getApplicationContext();

        toolbar = (Toolbar) mainActivity.findViewById(R.id.tool_bar);
        toolbar.setVisibility(View.VISIBLE);

        if(mainActivity.isNetworkConnected()) {
            getMagazineList();
        } else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View narilokLayout = inflater.inflate(R.layout.fragment_narilok_main, container, false);

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Narilok");
        toolbarTitle.setVisibility(View.VISIBLE);

        btnBack = (ImageButton) toolbar.findViewById(R.id.toolbar_back_button);
        btnBack.setVisibility(View.VISIBLE);

        txtBack = (TextView) toolbar.findViewById(R.id.toolbar_back_text);
        txtBack.setVisibility(View.VISIBLE);

        btnShare = (ImageButton) toolbar.findViewById(R.id.toolbar_share_button);
        btnShare.setVisibility(View.INVISIBLE);

        retriveReferencesFromXml(narilokLayout);
        setOnClickListeners();

        if(null != magazines || magazines.size() > 0) {
            setNarilokData();
        }

        return narilokLayout;
    }

    private void retriveReferencesFromXml(View newsSummaryLayout) {
        recyclerView = (RecyclerView)newsSummaryLayout.findViewById(R.id.narilokRecyclerView);
        layoutManager = new LinearLayoutManager(
                mainActivity,
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setOnClickListeners() {
        btnBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
    }

    public void getMagazineList(){
        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(WS_GET_MAGAZINE_LIST);
        ws.addAuth();
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object o) {
                List<WS_MagazineResponse> magazineResponseList = Arrays.asList(WS_MagazineResponse[].class.cast(o));
                for (WS_MagazineResponse magazineResponse : magazineResponseList) {
                    NarilokFragment.this.magazines.add(magazineResponse.getMagzine());
                }
                setNarilokData();
            }
        });
        ws.execute(WS_MagazineResponse[].class);
    }

    private void setNarilokData() {

        if(null == adapter) {
            adapter = new NarilokAdapter(context, NarilokFragment.this);
        }
        adapter.setData(magazines);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view_narilok:
                String id = String.valueOf(v.getTag());
                gotoNarilokSummaryFragment(id);
                break;

            case R.id.toolbar_back_button:
                mainActivity.onBackPressed();
                break;

            case R.id.toolbar_back_text:
                mainActivity.onBackPressed();
                break;
        }
    }

    private void gotoNarilokSummaryFragment(String id) {
        fragmentManager = getFragmentManager();
        NarilokSummaryFragment narilokSummaryFragment = new NarilokSummaryFragment();
        Bundle narilokBundle = new Bundle();
        narilokBundle.putString("magazineId", id);
        narilokSummaryFragment.setArguments(narilokBundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, narilokSummaryFragment);
        fragmentTransaction.addToBackStack(NarilokSummaryFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
