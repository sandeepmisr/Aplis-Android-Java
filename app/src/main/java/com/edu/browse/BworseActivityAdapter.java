package com.edu.browse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;
import com.edu.discover.ClickAdapter;

import java.util.List;

public class BworseActivityAdapter extends RecyclerView.Adapter<BworseActivityAdapter.SampleViewHolders>
{
    private List<BrowseModel> itemList;
    private Context context;
    private ClickBrowseAdapter clickAdapter;
    OnTouchPosition onTouchPosition;

    public BworseActivityAdapter(Context context, List<BrowseModel> itemList, ClickBrowseAdapter clickAdapter, OnTouchPosition onTouchPosition)
    {
        this.itemList = itemList;
        this.context = context;
        this.clickAdapter = clickAdapter;
        this.onTouchPosition = onTouchPosition;
    }

    @Override
    public SampleViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.editor_secondactivity_adapter, null);
        SampleViewHolders rcv = new SampleViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final SampleViewHolders holder, final int position)
    {

        Log.e("BROWSE_URL","donecatchholder");


        if (position == itemList.size()-1) {
            onTouchPosition.position(position);
        }



        Glide
                .with(context)
                .load(itemList.get(position).getImage()).into(holder.imageView);

        holder.text_name.setText(itemList.get(position).getTitle());
        holder.text_title.setText(itemList.get(position).getTitle());
        holder.text_subtitle.setText(itemList.get(position).getSub_title());



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("TAGEDITORbg_image","failed"+itemList.get(position).getColor_code());

                clickAdapter.clickoncard(holder.imageView,itemList.get(position).getId(),itemList.get(position).getImage());
//                        Toast.makeText(context,
//                                "Clicked Position = " + position+ " "+itemList.get(position).getId(), Toast.LENGTH_SHORT)
//                                .show();
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }


    public class SampleViewHolders extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView text_name;
        TextView text_title,text_subtitle;

        public LinearLayout circle_bg;

        public SampleViewHolders(View view)
        {
            super(view);

             imageView = view.findViewById(R.id.image);
             text_name = view.findViewById(R.id.text_name);
            text_title = view.findViewById(R.id.text_title);
            text_subtitle = view.findViewById(R.id.text_subtitle);


        }

    }

}
