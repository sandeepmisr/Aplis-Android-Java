package com.edu.browse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;

import java.util.ArrayList;

public class BrowseSeeAllAdapter extends RecyclerView.Adapter<BrowseSeeAllAdapter.SampleViewHolders>
{
    private Context context;
    private ClickBrowseAdapter clickAdapter;
    OnTouchPosition onTouchPosition;
    ArrayList<BrowseModel> browseBookModelArrayList;
    public BrowseSeeAllAdapter(Context context, ArrayList<BrowseModel> browseBookModelArrayList, ClickBrowseAdapter clickAdapter, OnTouchPosition onTouchPosition)
    {
        this.context = context;
        this.clickAdapter = clickAdapter;
        this.onTouchPosition = onTouchPosition;
        this.browseBookModelArrayList = browseBookModelArrayList;
    }

    @Override
    public SampleViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_seeall_adapter, null);
        SampleViewHolders rcv = new SampleViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final SampleViewHolders holder, final int position) {
        final BrowseModel browseModel = browseBookModelArrayList.get(position);

        Log.e("BROWSE_URL10SEEALL","donecatchholdersize"+"size"+position+" "+browseBookModelArrayList.get(position).getSub_title());

        holder.text_name.setText(browseModel.getTitle());
        holder.text_subtitle.setText(browseModel.getSub_title());
        Glide.with(context)
                .load(browseModel.getBg_image())
                .centerCrop()
                .into(holder.image_book);



        holder.image_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAGEDITORbg_image","failed"+browseBookModelArrayList.get(position).getId()+"");

                clickAdapter.clickoncard(holder.image_book,position+"",browseModel.getBg_image(),"browse");
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return browseBookModelArrayList.size();
    }


    public class SampleViewHolders extends RecyclerView.ViewHolder
    {

        public ImageView image_book;
        public TextView text_name;
        public TextView text_subtitle;
        public LinearLayout ll;
        public RelativeLayout llvertical;
        public HorizontalScrollView horizontalScrollView;
        public SampleViewHolders(View view)
        {
            super(view);

            text_name = view.findViewById(R.id.text_title);
            text_subtitle = view.findViewById(R.id.text_subtitle);
            image_book = view.findViewById(R.id.image);
            ll = view.findViewById(R.id.ll_adapter);
//            horizontalScrollView = view.findViewById(R.id.horizontalScrollView);


        }

    }

}
