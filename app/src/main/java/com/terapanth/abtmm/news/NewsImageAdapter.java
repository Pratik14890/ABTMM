package com.terapanth.abtmm.news;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.terapanth.abtmm.R;

import java.util.ArrayList;

/**
 * Created by MindstixSoftware on 05/01/18.
 */

public class NewsImageAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> images;
    private LayoutInflater inflater;

    public NewsImageAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);

        Picasso.with(context).load(images.get(position)).error(R.drawable.no_image).into(myImage);

        view.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);    }
}
