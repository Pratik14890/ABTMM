package com.terapanth.abtmm.narilok.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.model.Magazine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by Suraj Prajapati on 1/5/2018.
 */

public class NarilokRecycleViewAdapter extends RecyclerView.Adapter<NarilokRecycleViewAdapter.NarilokViewHandler> {

    private List<Magazine> magazines;
    private Context context;

    public NarilokRecycleViewAdapter(Context context, List<Magazine> magazines){
        this.context = context;
        this.magazines = magazines;
    }

    @Override
    public NarilokViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.narilok_magazine_row, parent, false);

        return new NarilokViewHandler(itemView);
    }

    @Override
    public void onBindViewHolder(NarilokViewHandler holder, int position) {
        Magazine m = magazines.get(position);
        holder.title.setText(m.getName());
        //holder.img.setImageURI(Uri.parse(m.getCoverImageUrl()));
        /*try {
            URL url = new URL(m.getCoverImageUrl());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.img.setImageBitmap(bmp);
            holder.row.setTag(m);
        }catch (MalformedURLException ex) {
            Log.e("NarilokViewHandler", "Exception while showing img", ex);
        } catch (IOException ex) {
            Log.e("NarilokViewHandler", "Exception while showing img", ex);
        }*/

        Picasso.with(context).load(m.getCoverImageUrl()).resize(100, 100).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return magazines.size();
    }

    class NarilokViewHandler extends RecyclerView.ViewHolder {
        LinearLayout row;
        TextView title;
        ImageView img;

        NarilokViewHandler(View view){
            super(view);

            row  = (LinearLayout) view.findViewById(R.id.magazine_row);
            title  = (TextView) view.findViewById(R.id.magazine_name);
            img  = (ImageView) view.findViewById(R.id.magazine_img);
        }
    }
}
