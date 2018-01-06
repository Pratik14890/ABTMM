package com.terapanth.abtmm.narilok.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.model.MagazineSummary;
import com.terapanth.abtmm.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class NarilokSummaryAdapter extends RecyclerView.Adapter<NarilokSummaryAdapter.ViewHolder>{

    private Context context;
    private List<MagazineSummary> magazineSummaryList;
    private View.OnClickListener listener;

    public NarilokSummaryAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.magazineSummaryList = new ArrayList<>();
        this.listener = listener;
    }

    public void setData(List<MagazineSummary> magazineSummaryList){
        this.magazineSummaryList = magazineSummaryList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.narilok_magazine_summary_row, parent, false);

        NarilokSummaryAdapter.ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MagazineSummary magazineSummary = magazineSummaryList.get(position);

        Picasso.with(context)
                .load(magazineSummary.getCoverImageUrl())
                .error(R.drawable.no_image)       //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.imgMagazineSumary);

        holder.txtTitle.setText(String.valueOf(magazineSummary.getTitle()));
        holder.txtSerialnumber.setText("Serial No. " + String.valueOf(position + 1));
        try {
            holder.txtDate.setText(DateUtils.getFormattedDateOrTime(magazineSummary.getPostedDate(), "dd MMM yyyy", "dd/MM"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cardView.setTag(magazineSummary.getType());
        holder.cardView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return magazineSummaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView imgMagazineSumary;
        public TextView txtTitle, txtSerialnumber, txtDate;

        public ViewHolder(View v) {
            super(v);

            cardView = (CardView)v.findViewById(R.id.card_view_narilok_summary);
            imgMagazineSumary = (ImageView) v.findViewById(R.id.imageview_narilok_summary);
            txtTitle = (TextView)v.findViewById(R.id.narilok_magazine_summary_title);
            txtSerialnumber = (TextView)v.findViewById(R.id.narilok_magazine_summary_posted_by);
            txtDate = (TextView)v.findViewById(R.id.narilok_magazine_summary_date);
        }
    }
}
