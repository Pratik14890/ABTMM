package com.terapanth.abtmm.narilok.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.narilok.ChangeFragment;
import com.terapanth.abtmm.services.model.Magazine;
import com.terapanth.abtmm.services.model.MagazineSummary;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Suraj Prajapati on 1/5/2018.
 */

public class NarilokSummaryRecycleViewAdapter extends RecyclerView.Adapter<NarilokSummaryRecycleViewAdapter.NarilokViewHandler> {

    private List<MagazineSummary> magazines;
    private Context context;

    public NarilokSummaryRecycleViewAdapter(Context context, List<MagazineSummary> magazines) {
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
        final MagazineSummary m = magazines.get(position);
        holder.title.setText(m.getName());
        Picasso.with(context).load(m.getCoverImageUrl()).resize(75, 75).into(holder.img);

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Open pdf
            }
        });
    }

    @Override
    public int getItemCount() {
        if (magazines == null)
            return 0;
        return magazines.size();
    }

    class NarilokViewHandler extends RecyclerView.ViewHolder {
        LinearLayout row;
        TextView title;
        ImageView img;

        NarilokViewHandler(View view) {
            super(view);

            row = (LinearLayout) view.findViewById(R.id.magazine_row);
            title = (TextView) view.findViewById(R.id.magazine_name);
            img = (ImageView) view.findViewById(R.id.magazine_img);
        }
    }
}
