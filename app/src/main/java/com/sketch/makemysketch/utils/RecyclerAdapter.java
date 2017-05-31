package com.sketch.makemysketch.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sketch.makemysketch.ItemDisplay;
import com.sketch.makemysketch.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by prateek on 14-05-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Object> items;
    public RecyclerAdapter(List<Object> items){
        this.items = items;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.painting_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object item = items.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mItemImage;
        private TextView mItemText;
        private LinkedHashMap map;
        private ImageView sold_label;

        private static final String MAP_KEY = "map";

        public ViewHolder(View view) {
            super(view);
            this.mItemImage = (ImageView) view.findViewById(R.id.card_image);
            this.mItemText = (TextView) view.findViewById(R.id.info_text);
            this.sold_label = (ImageView) view.findViewById(R.id.outside_imageview);
            view.setOnClickListener(this);
        }

        public void bindData(Object item) {
            this.map = (LinkedHashMap) item;
            Integer quantity = (Integer) map.get("quantity");
            if (quantity > 0) {
                sold_label.setVisibility(View.INVISIBLE);
            } else {
                sold_label.setVisibility(View.VISIBLE);
            }
            Picasso.with(mItemImage.getContext()).load((String)map.get("imageLocation")).into(mItemImage);
            mItemText.setText(map.get("name").toString());
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, ItemDisplay.class);
            intent.putExtra(MAP_KEY, map);
            context.startActivity(intent);
        }
    }
}