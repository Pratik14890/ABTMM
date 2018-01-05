package com.terapanth.abtmm.news;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.home.ImageSliderAdapter;
import com.terapanth.abtmm.services.model.news.NewsSummary;
import com.terapanth.abtmm.utils.ShareUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class NewsDetailsFragment extends Fragment implements View.OnClickListener {

    private static MainActivity mainActivity;
    private Context context;
    private NewsSummary newsSummary;
    private static ViewPager newsImagePager;
    private static int currentPage = 0;
    private ArrayList<String> newsImages;
    Timer swipeTimer;
    CircleIndicator newsImageIndicator;
    TextView txtDescription;
    TextView txtPostedBy;
    TextView txtPostedDate;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageButton btnBack;
    private TextView txtBack;
    private ImageButton btnShare;

    public NewsDetailsFragment() {
        newsImages = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TestNewsDetailsFragment", "onCreate");
        mainActivity = (MainActivity) getActivity();
        context = mainActivity.getApplicationContext();

        toolbar = (Toolbar) mainActivity.findViewById(R.id.tool_bar);
        toolbar.setVisibility(View.VISIBLE);

        Bundle newsSummaryBundle = getArguments();
        if(newsSummaryBundle != null) {
            if(newsSummaryBundle.containsKey("NewsSummary")) {
                this.newsSummary = (NewsSummary) newsSummaryBundle.getSerializable("NewsSummary");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        Log.d("TestNewsDetailsFragment", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setVisibility(View.VISIBLE);

        btnBack = (ImageButton) toolbar.findViewById(R.id.toolbar_back_button);
        btnBack.setVisibility(View.VISIBLE);

        txtBack = (TextView) toolbar.findViewById(R.id.toolbar_back_text);
        txtBack.setVisibility(View.VISIBLE);

        btnShare = (ImageButton) toolbar.findViewById(R.id.toolbar_share_button);
        btnShare.setVisibility(View.VISIBLE);

        retriveReferencesFromXml(view);
        setOnClickListeners();

        setNewsDetails();

        return view;
    }

    private void retriveReferencesFromXml(View view) {
        newsImagePager = (ViewPager) view.findViewById(R.id.news_pager);
        newsImageIndicator = (CircleIndicator) view.findViewById(R.id.news_indicator);
        txtDescription = (TextView) view.findViewById(R.id.txt_description);
        txtPostedBy = (TextView) view.findViewById(R.id.txt_posted_by);
        txtPostedDate = (TextView) view.findViewById(R.id.txt_posted_date);
    }

    private void init() {
        newsImagePager.setAdapter(new NewsImageAdapter(getActivity(),newsImages));
        newsImageIndicator.setViewPager(newsImagePager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == newsImages.size()-1) {
                    currentPage = 0;
                }
                newsImagePager.setCurrentItem(currentPage++, true);
            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    private void setNewsDetails() {

        if(null == newsSummary)
            return;

        if(TextUtils.isEmpty(newsSummary.getTitle())) {
            toolbarTitle.setText("News");
        } else {
            toolbarTitle.setText(newsSummary.getTitle());
        }

        addImageUrl(newsSummary.getImageUrl1());
        addImageUrl(newsSummary.getImageUrl2());
        addImageUrl(newsSummary.getImageUrl3());
        addImageUrl(newsSummary.getImageUrl4());
        addImageUrl(newsSummary.getImageUrl5());

        init();

        txtDescription.setText(String.valueOf(newsSummary.getDescription()));
        txtPostedBy.setText(String.valueOf(newsSummary.getPostedBy()));
        txtPostedDate.setText(String.valueOf(newsSummary.getPostedDate()));
    }

    private void addImageUrl(String url) {
        if(!TextUtils.isEmpty(url)) {
            newsImages.add(url);
        }
    }

    private void setOnClickListeners() {
        btnBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_share_button:
                String newsTitle = TextUtils.isEmpty(newsSummary.getTitle()) ? "" : newsSummary.getTitle();
                String desc = TextUtils.isEmpty(newsSummary.getDescription()) ? "" : newsSummary.getDescription();
                String author = TextUtils.isEmpty(newsSummary.getPostedBy()) ? "" : newsSummary.getPostedBy();
                String date = TextUtils.isEmpty(newsSummary.getPostedDate()) ? "" : newsSummary.getPostedDate();
                String shareNews = ShareUtils.getSharingStringForNews(newsTitle, desc, author, date);
                ShareUtils.shareMe(context, shareNews);
                break;

            case R.id.toolbar_back_button:
                mainActivity.onBackPressed();
                break;

            case R.id.toolbar_back_text:
                mainActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TestNewsDetailsFragment", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TestNewsDetailsFragment", "onResume");
    }
}
