package com.terapanth.abtmm.home;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.OnExecuteComplete;
import com.terapanth.abtmm.services.WebServiceHandler;
import com.terapanth.abtmm.services.model.news.SlideShowImage;
import com.terapanth.abtmm.services.model.response.WS_SlideShowImageResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private static ViewPager mPager;
    private static int currentPage = 0;
//    private static final Integer[] systemImages = {R.drawable.acharya, R.drawable.abtmm_logo, R.drawable.activity1_aao_chale_gao_ki_or, R.drawable.activity2_kanya_suraksha, R.drawable.activity3_swasth_parivaar_swasth_samaaj, R.drawable.activity4_tulsi_shiksha_pariyojana, R.drawable.jain_vidya_logo};
    private static final Integer[] systemImages = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven};
    private ArrayList<Integer> SystemImagesArray = new ArrayList<Integer>();
    private Timer swipeTimer;
    private CircleIndicator indicator;
    public static MainActivity mainActivity;
    private Context context;
    private Toolbar toolbar;
    List<SlideShowImage> slideShowImageList;

    private final static String WS_GET_SLIDE_SHOW_IMAGE_LIST = "GetSlideshowImages";

    public HomeFragment() {
        slideShowImageList = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Test - HomeFragment", "onCreate");

        mainActivity = (MainActivity) getActivity();
        this.context = mainActivity.getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {

        Log.d("Test - HomeFragment", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = (Toolbar) mainActivity.findViewById(R.id.tool_bar);
        toolbar.setVisibility(View.GONE);

        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);

        init();

        return view;
    }

    private void init() {

        if(swipeTimer != null)
        swipeTimer.cancel();
        SystemImagesArray.clear();

        for(int i = 0; i< systemImages.length; i++)
            SystemImagesArray.add(systemImages[i]);

        mPager.setAdapter(new ImageSliderAdapter(getActivity(),SystemImagesArray));
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == systemImages.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
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

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Test - HomeFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("Test - HomeFragment", "onPause");

        swipeTimer.cancel();
        SystemImagesArray.clear();
    }

    private void getSlideShowImagesList() {
        WebServiceHandler ws = new WebServiceHandler();
        ws.setMethodName(WS_GET_SLIDE_SHOW_IMAGE_LIST);
        ws.addAuth();
        ws.setOnExecuteComplete(new OnExecuteComplete() {
            @Override
            public void onComplete(Object obj) {
                List<WS_SlideShowImageResponse> slideShowImageResponseList = Arrays.asList(WS_SlideShowImageResponse[].class.cast(obj));
                for(WS_SlideShowImageResponse slideShowImageResponse : slideShowImageResponseList)
                    HomeFragment.this.slideShowImageList.add(slideShowImageResponse.getImages());

//                setNewsSummaryData();
            }
        });
        ws.execute(WS_SlideShowImageResponse[].class);
    }

}
