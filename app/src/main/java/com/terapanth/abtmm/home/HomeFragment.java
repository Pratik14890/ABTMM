package com.terapanth.abtmm.home;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.terapanth.abtmm.MainActivity;
import com.terapanth.abtmm.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by MindstixSoftware on 31/12/17.
 */

public class HomeFragment extends Fragment {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.acharya, R.drawable.abtmm_logo, R.drawable.activity1_aao_chale_gao_ki_or, R.drawable.activity2_kanya_suraksha, R.drawable.activity3_swasth_parivaar_swasth_samaaj, R.drawable.activity4_tulsi_shiksha_pariyojana, R.drawable.jain_vidya_logo};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    Timer swipeTimer;
    CircleIndicator indicator;
    public static MainActivity mainActivity;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        this.context = mainActivity.getApplicationContext();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);

        init();

        return view;
    }

    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager.setAdapter(new ImageSliderAdapter(getActivity(),XMENArray));
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
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
    public void onPause() {
        super.onPause();
//        swipeTimer.cancel();
//        XMENArray.clear();
    }

}
