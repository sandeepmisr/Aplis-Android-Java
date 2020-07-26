package com.edu.fav;

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
import com.edu.discover.Books;
import com.edu.discover.ClickAdapter;
import com.edu.search.BooksSearch;

import java.util.ArrayList;

public class FavBookAdapter extends RecyclerView.Adapter<FavBookAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<BooksSearch> booksArrayList;
    ClickAdapter clickAdapter;

    public FavBookAdapter(Context context, ArrayList<BooksSearch> booksArrayList, ClickAdapter clickAdapter) {
        this.mcontext = context;
        this.booksArrayList = booksArrayList;
        this.clickAdapter = clickAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_favadapter, null, false);
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
                clickAdapter.clickoncard(viewHolder.image,position,listModel.getProduct_id(),listModel.getPro_image(),"","",null,""
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


    public void removeItem(int position) {
        booksArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, booksArrayList.size());
    }
    public void restoreItem(BooksSearch model, int position) {
        booksArrayList.add(position, model);
        // notify item added by position
        notifyItemInserted(position);
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