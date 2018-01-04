package com.terapanth.abtmm.adapters;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.model.Magazine;

import java.util.List;


/**
 * Created by Suraj Prajapati on 1/5/2018.
 */

public class NarilokRecycleViewAdapter extends Adapter<NarilokRecycleViewAdapter.NarilokViewHandler> {

    private List<Magazine> magazines;

    public NarilokRecycleViewAdapter(List<Magazine> magazines){
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
        holder.img.setImageURI(Uri.parse(m.getCoverImageUrl()));
        holder.row.setTag(m);
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
