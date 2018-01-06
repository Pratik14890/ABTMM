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
import com.terapanth.abtmm.services.model.Magazine;

import java.util.ArrayList;
import java.util.List;

public class NarilokAdapter extends RecyclerView.Adapter<NarilokAdapter.ViewHolder> {

    private List<Magazine> magazines;
    private Context context;
    private View.OnClickListener listener;

    public NarilokAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.magazines = new ArrayList<>();
    }

    public void setData(List<Magazine> magazines){
        this.magazines = magazines;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.narilok_magazine_row, parent, false);

        NarilokAdapter.ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Magazine magazine = magazines.get(position);

        Picasso.with(context)
                .load(magazine.getCoverImageUrl())
                .error(R.drawable.no_image)       //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.imgNarilokMagazineImage);

        holder.txtTitle.setText(String.valueOf(magazine.getName()));

        holder.cardView.setTag(magazine.getId());
        holder.cardView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return magazines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView imgNarilokMagazineImage;
        public TextView txtTitle, txtSrNo, txtPostedDate;

        public ViewHolder(View v) {
            super(v);

            cardView = (CardView)v.findViewById(R.id.card_view_narilok);
            imgNarilokMagazineImage = (ImageView) v.findViewById(R.id.imageview_narilok);
            txtTitle = (TextView)v.findViewById(R.id.narilok_magazine_title);
        }
    }
}
