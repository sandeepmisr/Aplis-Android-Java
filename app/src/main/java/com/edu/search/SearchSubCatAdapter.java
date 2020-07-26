package com.edu.search;

import android.content.Context;
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
import com.edu.discover.ClickAdapter;
import com.edu.webservice.Cons;

import java.util.ArrayList;

public class SearchSubCatAdapter extends RecyclerView.Adapter<SearchSubCatAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<BooksSearch> booksArrayList;
    ClickAdapter clickAdapter;

    public SearchSubCatAdapter(Context context, ArrayList<BooksSearch> booksArrayList, ClickAdapter clickAdapter) {
        this.mcontext = context;
        this.booksArrayList = booksArrayList;
        this.clickAdapter = clickAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_adapter, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        final BooksSearch listModel = booksArrayList.get(position);

        viewHolder.text_body.setText(listModel.getMsg());
        viewHolder.tittle.setText(listModel.getName_title());
        String title_Des = "";
        viewHolder.eventslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickAdapter.clickoncard(viewHolder.image,position,listModel.getProduct_id(),listModel.getDescription(),listModel.getName_title(),Cons.SUBCATTOBOOK_URL,null,""
                ,"");
            }
        });

        Glide
                .with(mcontext)
                .load(listModel.getPro_image())
                .thumbnail(0.05f)
                .centerCrop()
                .into(viewHolder.image);
       // viewHolder.image.setImageResource(listModel.getHeader_url());



//        arrow.setImageResource(listModel.getArrow());
    }

    @Override
    public int getItemCount() {
        return booksArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        LinearLayout eventslayout;
        TextView tittle,text_body;
        CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tittle = itemView.findViewById(R.id.text_title);
            this.text_body = itemView.findViewById(R.id.text_subtitle);
            this.image = itemView.findViewById(R.id.image1);
            this.eventslayout = itemView.findViewById(R.id.eventslayout);

        }
    }
}