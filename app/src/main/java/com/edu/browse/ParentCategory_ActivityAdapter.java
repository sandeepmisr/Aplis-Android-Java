package com.edu.browse;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;

import java.util.List;

public class ParentCategory_ActivityAdapter extends RecyclerView.Adapter<ParentCategory_ActivityAdapter.SampleViewHolders>
{
    private List<BrowseModel> itemList;
    private Context context;
    private ClickBrowseAdapter clickAdapter;
    OnTouchPosition onTouchPosition;

    private int selectedPosition = -1;

    public ParentCategory_ActivityAdapter(Context context, List<BrowseModel> itemList, ClickBrowseAdapter clickAdapter, OnTouchPosition onTouchPosition)
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
                R.layout.parentactivity_adapter, null);
        SampleViewHolders rcv = new SampleViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final SampleViewHolders holder, final int position)
    {

        Log.e("BROWSE_URL","donecatchholder");


//        if (position == itemList.size()-1) {
//            onTouchPosition.position(position);
//        }



        holder.text_name.setText(itemList.get(position).getTitle());

        holder.parentadapterroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickAdapter.clickoncard(null,itemList.get(position).getId()+"",itemList.get(position).getTitle(),"parent_category");
                selectedPosition=position;
                notifyDataSetChanged();
//                itemCheckChanged(view);
            }
        });

        if(selectedPosition==position){
            holder.parentadapterroot.setCardBackgroundColor(Color.parseColor("#010101"));
            holder.text_name.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.parentadapterroot.setCardBackgroundColor(Color.parseColor("#ffffff"));

//            holder.parentadapterroot.setBackgroundDrawable(context.getDrawable(R.drawable.customborder));
            holder.text_name.setTextColor(Color.parseColor("#000000"));
        }

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
        public CardView parentadapterroot;

        public SampleViewHolders(View view)
        {
            super(view);

            text_name = view.findViewById(R.id.text_name);
            parentadapterroot = view.findViewById(R.id.parentadapterroot);



        }

    }

}
