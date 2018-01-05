package com.terapanth.abtmm.news;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.terapanth.abtmm.R;
import com.terapanth.abtmm.services.model.news.NewsSummary;
import com.terapanth.abtmm.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NewsSummaryAdapter extends RecyclerView.Adapter<NewsSummaryAdapter.ViewHolder>{

    private Context context;
    private List<NewsSummary> newsSummaryList;
    private View.OnClickListener listener;

    public NewsSummaryAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.newsSummaryList = new ArrayList<>();
        this.listener = listener;
    }

    public void setData(List<NewsSummary> newsSummaryList){
        this.newsSummaryList = newsSummaryList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_row, parent, false);

        NewsSummaryAdapter.ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NewsSummary newsSummary = newsSummaryList.get(position);

        Picasso.with(context)
                .load(newsSummary.getImageUrl1())
                .error(R.drawable.no_image)       //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.imgNewsSumary);

        holder.txtTitle.setText(String.valueOf(newsSummary.getTitle()));
        holder.txtPostedBy.setText(String.valueOf(newsSummary.getPostedBy()));
        try {
            holder.txtDate.setText(DateUtils.getFormattedDateOrTime(newsSummary.getPostedDate(), "dd MMM yyyy h:mma", "dd/MM HH:mm"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cardView.setTag(newsSummary);
        holder.cardView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return newsSummaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView imgNewsSumary;
        public TextView txtTitle, txtPostedBy, txtDate;

        public ViewHolder(View v) {
            super(v);

            cardView = (CardView)v.findViewById(R.id.card_view_news_summary);
            imgNewsSumary = (ImageView) v.findViewById(R.id.imageview_news_summary);
            txtTitle = (TextView)v.findViewById(R.id.news_summary_title);
            txtPostedBy = (TextView)v.findViewById(R.id.news_summary_posted_by);
            txtDate = (TextView)v.findViewById(R.id.news_summary_date);
        }
    }
}
