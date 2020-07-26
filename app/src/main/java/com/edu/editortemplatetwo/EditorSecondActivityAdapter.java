package com.edu.editortemplatetwo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.edu.aplis.R;
import com.edu.discover.ClickAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditorSecondActivityAdapter extends RecyclerView.Adapter<EditorSecondActivityAdapter.SampleViewHolders>
{
    private List<EditorModel> itemList;
    private Context context;
    private ClickAdapter clickAdapter;
    public EditorSecondActivityAdapter(Context context, List<EditorModel> itemList, ClickAdapter clickAdapter)
    {
        this.itemList = itemList;
        this.context = context;
        this.clickAdapter = clickAdapter;
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


        Glide
                .with(context)
                .load(itemList.get(position).getImage()).into(holder.imageView);

        holder.text_name.setText(itemList.get(position).getTitle());
        holder.text_title.setText(itemList.get(position).getTitle());
        holder.text_subtitle.setText(itemList.get(position).getSub_title());



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAGEDITORbg_image","failed"+itemList.get(position).getColor_code());

                clickAdapter.clickoncard(holder.imageView,position,itemList.get(position).getTitle(),itemList.get(position).getImage(),itemList.get(position).getSub_title(),itemList.get(position).getDescription(),null,null,null);
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
