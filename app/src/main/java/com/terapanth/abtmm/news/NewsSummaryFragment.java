package com.terapanth.abtmm.news;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.news.NewsSummary;
import com.terapanth.abtmm.services.model.response.WS_NewsSummaryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsSummaryFragment extends Fragment implements View.OnClickListener{

    public static MainActivity mainActivity;
    private Context context;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageButton btnBack;
    private TextView txtBack;
    private ImageButton btnShare;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<NewsSummary> newsSummaryList;
    private NewsSummaryAdapter newsSummaryAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TextView postButton;

    private final static String WS_GET_NEWS_SUMMARY_LIST = "GetLiveAnnouncementList";

    public NewsSummaryFragment() {
        newsSummaryList = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TestNewsSummaryFragment", "onCreate");
        mainActivity = (MainActivity) getActivity();
        this.context = mainActivity.getApplicationContext();

        toolbar = (Toolbar) mainActivity.findViewById(R.id.tool_bar);
        toolbar.setVisibility(View.VISIBLE);

        if(mainActivity.isNetworkConnected()) {
            getNewsSummaryList();
        } else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        Log.d("TestNewsSummaryFragment", "onCreateView");

        View newsSummaryLayout = inflater.inflate(R.layout.fragment_news_main, container, false);

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("News");
        toolbarTitle.setVisibility(View.VISIBLE);

        btnBack = (ImageButton) toolbar.findViewById(R.id.toolbar_back_button);
        btnBack.setVisibility(View.VISIBLE);

        txtBack = (TextView) toolbar.findViewById(R.id.toolbar_back_text);
        txtBack.setVisibility(View.VISIBLE);

        btnShare = (ImageButton) toolbar.findViewById(R.id.toolbar_share_button);
        btnShare.setVisibility(View.INVISIBLE);

        retriveReferencesFromXml(newsSummaryLayout);
        setOnClickListeners();

        if(null != newsSummaryList || newsSummaryList.size() > 0) {
            setNewsSummaryData();
        }

        return newsSummaryLayout;
    }

    private void retriveReferencesFromXml(View newsSummaryLayout) {

        recyclerView = (RecyclerView)newsSummaryLayout.findViewById(R.id.newsRecyclerView);

        layoutManager = new LinearLayoutManager(
                mainActivity,
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);

        postButton = (TextView)newsSummaryLayout.findViewById(R.id.txt_bottom_view);
    }

    private void setOnClickListeners() {
        btnBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        postButton.setOnClickListener(this);
    }


    private void getNewsSummaryList() {
        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(WS_GET_NEWS_SUMMARY_LIST);
        ws.addAuth();
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object obj) {
                List<WS_NewsSummaryResponse> newsSummaryList = Arrays.asList(WS_NewsSummaryResponse[].class.cast(obj));
                for (WS_NewsSummaryResponse ws_newsSummaryResponse : newsSummaryList)
                    NewsSummaryFragment.this.newsSummaryList.add(ws_newsSummaryResponse.getNews());

                setNewsSummaryData();
            }
        });
        ws.execute(WS_NewsSummaryResponse[].class);
    }

    private void setNewsSummaryData() {

        if(null == newsSummaryAdapter) {
            newsSummaryAdapter = new NewsSummaryAdapter(context, NewsSummaryFragment.this);
        }
        newsSummaryAdapter.setData(newsSummaryList);
        recyclerView.setAdapter(newsSummaryAdapter);

        mainActivity.showProgressDialog(false, "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view_news_summary:
                NewsSummary newsSummary = (NewsSummary) v.getTag();
                gotoNewsDetailsFragment(newsSummary);
                break;

            case R.id.toolbar_back_button:
                mainActivity.onBackPressed();
                break;

            case R.id.toolbar_back_text:
                mainActivity.onBackPressed();
                break;
            case R.id.txt_bottom_view:
                fragmentManager = getFragmentManager();
                PostNewsFragment postNewsFragment = new PostNewsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, postNewsFragment);
                fragmentTransaction.addToBackStack(PostNewsFragment.class.getSimpleName());
                fragmentTransaction.commit();
                break;
        }
    }

    private void gotoNewsDetailsFragment(NewsSummary newsSummary) {
        fragmentManager = getFragmentManager();
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();

        Bundle newsSummaryBundle = new Bundle();
        newsSummaryBundle.putSerializable("NewsSummary", newsSummary);
        newsDetailsFragment.setArguments(newsSummaryBundle);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newsDetailsFragment);
        fragmentTransaction.addToBackStack(NewsDetailsFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TestNewsSummaryFragment", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TestNewsSummaryFragment", "onResume");
    }
}
