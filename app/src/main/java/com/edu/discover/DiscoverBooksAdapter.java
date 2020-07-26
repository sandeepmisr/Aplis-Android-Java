package com.edu.discover;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.book.CourseDetail;
import com.edu.aplis.R;

import java.util.ArrayList;

public class DiscoverBooksAdapter extends RecyclerView.Adapter<DiscoverBooksAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<Books> booksAdapterArrayList;


    public DiscoverBooksAdapter(Context context, ArrayList<Books> booksAdapterArrayList) {
        this.mcontext = context;
        this.booksAdapterArrayList = booksAdapterArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookseries, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        final Books listModel = booksAdapterArrayList.get(position);


        viewHolder.tittle.setText(listModel.getBook_name());


        viewHolder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mcontext.startActivity(new Intent(mcontext, CourseDetail.class).putExtra("book_id",booksAdapterArrayList.get(position).getId()));
            }
        });

        Glide
                .with(mcontext)
                .load(listModel.getBook_cover())
                .thumbnail(0.05f)
                .centerCrop()
                .into(viewHolder.image);
       // viewHolder.image.setImageResource(listModel.getHeader_url());



//        arrow.setImageResource(listModel.getArrow());
    }

    @Override
    public int getItemCount() {
        return booksAdapterArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        LinearLayout text_layout;
        TextView tittle,text_body;
        TextView btn_view;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tittle = itemView.findViewById(R.id.text_name);

            this.image = itemView.findViewById(R.id.image);

            this.btn_view = itemView.findViewById(R.id.btn_view);

        }
    }
}