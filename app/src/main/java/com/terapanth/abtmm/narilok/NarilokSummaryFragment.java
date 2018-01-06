package com.terapanth.abtmm.narilok;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.narilok.adapters.NarilokSummaryAdapter;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.MagazineSummary;
import com.terapanth.abtmm.services.model.response.WS_MagazineSummaryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NarilokSummaryFragment extends Fragment implements View.OnClickListener {

    private final static String WS_GET_MAGAZINE_SUMMARY_LIST = "GetMagazineSummaryList";
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
    private List<MagazineSummary> magazineSummaryList;
    private NarilokSummaryAdapter adapter;

    String magazineId;

    public NarilokSummaryFragment() {
        magazineSummaryList = new ArrayList<>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        this.context = mainActivity.getApplicationContext();

        toolbar = (Toolbar) mainActivity.findViewById(R.id.tool_bar);
        toolbar.setVisibility(View.VISIBLE);

        Bundle narilokSummaryBundle = getArguments();

        if(narilokSummaryBundle != null) {
            if(narilokSummaryBundle.containsKey("magazineId")) {
                this.magazineId = narilokSummaryBundle.getString("magazineId");
            }
        }

        if(mainActivity.isNetworkConnected() && !TextUtils.isEmpty(magazineId)) {
            getMagazineSummaryList();
        } else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View narilokSummaryLayout = inflater.inflate(R.layout.fragment_narilok_summary_list, container, false);

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Narilok");
        toolbarTitle.setVisibility(View.VISIBLE);

        btnBack = (ImageButton) toolbar.findViewById(R.id.toolbar_back_button);
        btnBack.setVisibility(View.VISIBLE);

        txtBack = (TextView) toolbar.findViewById(R.id.toolbar_back_text);
        txtBack.setVisibility(View.VISIBLE);

        btnShare = (ImageButton) toolbar.findViewById(R.id.toolbar_share_button);
        btnShare.setVisibility(View.INVISIBLE);

        retriveReferencesFromXml(narilokSummaryLayout);
        setOnClickListeners();

        if(null != magazineSummaryList || magazineSummaryList.size() > 0) {
            setNarilokSummaryData();
        }

        return narilokSummaryLayout;
    }

    private void retriveReferencesFromXml(View newsSummaryLayout) {
        recyclerView = (RecyclerView)newsSummaryLayout.findViewById(R.id.narilokSummaryRecyclerView);
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

    public void getMagazineSummaryList(){
        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(WS_GET_MAGAZINE_SUMMARY_LIST);
        ws.addAuth();
        ws.addParameter("MagazineId", magazineId, String.class);
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object o) {
                List<WS_MagazineSummaryResponse> magazineSummaryResponseList = Arrays.asList(WS_MagazineSummaryResponse[].class.cast(o));
                for (WS_MagazineSummaryResponse magazineSummaryResponse : magazineSummaryResponseList) {
                    NarilokSummaryFragment.this.magazineSummaryList.add(magazineSummaryResponse.getMagzineSuammary());
                }
                setNarilokSummaryData();
            }
        });
        ws.execute(WS_MagazineSummaryResponse[].class);
    }

    private void setNarilokSummaryData() {

        if(null == adapter) {
            adapter = new NarilokSummaryAdapter(context, NarilokSummaryFragment.this);
        }
        adapter.setData(magazineSummaryList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view_narilok:
                int id = Integer.parseInt(String.valueOf(v.getTag()));
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

    private void gotoNarilokSummaryFragment(int id) {
        fragmentManager = getFragmentManager();
        NarilokSummaryFragment narilokSummaryFragment = new NarilokSummaryFragment();
        Bundle narilokBundle = new Bundle();
        narilokBundle.putInt("magazineId", id);
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
