package com.edu.discover;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;

import java.util.ArrayList;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<DiscoverModel> discoverArrayList;
    ClickAdapter clickAdapter;


    public DiscoverAdapter(Context context, ArrayList<DiscoverModel> discoverAdapterArrayList,ClickAdapter clickAdapter) {
        this.mcontext = context;
        this.discoverArrayList = discoverAdapterArrayList;
        this.clickAdapter = clickAdapter;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        final DiscoverModel listModel = discoverArrayList.get(position);

        viewHolder.text_layout.setVisibility(listModel.getVisible());
        viewHolder.tittle.setVisibility(listModel.getTitlecard_visible());
        viewHolder.text_body.setVisibility(listModel.getTitldesecard_visible());
        viewHolder.tittle.setText(listModel.getTitle());
        String title_Des = "";
        if (listModel.getTitle_description().equalsIgnoreCase("null")){
            title_Des = listModel.getLong_description();
        }
        else{
            title_Des = listModel.getTitle_description();

        }
        viewHolder.text_body.setText(title_Des);
        viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAdapter.clickoncard(viewHolder.image,position,listModel.getMime_type(),listModel.getHeader_url(),listModel.getTitle(),listModel.getLong_description(),null,listModel.getImage_Ar()
                ,listModel.getId());
            }
        });

        Glide
                .with(mcontext)
                .load(listModel.getCard_url())
                .thumbnail(0.05f)
                .centerCrop()
                .into(viewHolder.image);
       // viewHolder.image.setImageResource(listModel.getHeader_url());



//        arrow.setImageResource(listModel.getArrow());
    }

    @Override
    public int getItemCount() {
        return discoverArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        LinearLayout text_layout;
        TextView tittle,text_body;
        CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);


            this.tittle = itemView.findViewById(R.id.tittle);
            this.text_body = itemView.findViewById(R.id.text_body);
            this.image = itemView.findViewById(R.id.image);
            this.text_layout = itemView.findViewById(R.id.text_layout);
            this.card_view = itemView.findViewById(R.id.card_view);

        }
    }
}