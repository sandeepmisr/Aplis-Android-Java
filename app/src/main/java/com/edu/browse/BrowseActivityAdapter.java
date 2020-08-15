package com.edu.browse;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;
import com.edu.editortemplatetwo.EditorSecondSubActivity;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivityAdapter extends RecyclerView.Adapter<BrowseActivityAdapter.SampleViewHolders>
{
    private Context context;
    private ClickBrowseAdapter clickAdapter;
    OnTouchPosition onTouchPosition;
    ArrayList<BrowseModel> browseBookModelArrayList;
    public BrowseActivityAdapter(Context context, ArrayList<BrowseModel> browseBookModelArrayList, ClickBrowseAdapter clickAdapter, OnTouchPosition onTouchPosition)
    {
        this.context = context;
        this.clickAdapter = clickAdapter;
        this.onTouchPosition = onTouchPosition;
        this.browseBookModelArrayList = browseBookModelArrayList;
    }

    @Override
    public SampleViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_recent_listadapter, null);
        SampleViewHolders rcv = new SampleViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final SampleViewHolders holder, final int position) {
        final BrowseModel browseModel = browseBookModelArrayList.get(position);

        Log.e("BROWSE_URL10","donecatchholdersize"+"size"+position+" "+browseBookModelArrayList.get(position).getBrowseModels().size());

        holder.text_name.setText(browseModel.getTitle());

        for (int i=0;i<browseModel.getBrowseModels().size();i++) {
            Log.e("BROWSE_URL101","donecatchholdersize"+"sizeloop " +browseBookModelArrayList.get(position).getBrowseModels().get(i).getImage()
            +"  "+browseBookModelArrayList.get(position).getBrowseModels().get(i).getId());

            inflateRecentlyAdded(browseBookModelArrayList.get(position).getBrowseModels(),holder.ll);
//            final LinearLayout linearLayout1=new LinearLayout(holder.ll.getContext());
//            linearLayout1.setOrientation(LinearLayout.VERTICAL);
//            linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ImageView imageview = new ImageView(linearLayout1.getContext());
//            final TextView textView=new TextView(linearLayout1.getContext());
//            imageview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));
//            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(10,2,10,2);
//            textView.setText(browseBookModelArrayList.get(position).getBrowseModels().get(i).getRecenttitle());
//            linearLayout1.setLayoutParams(layoutParams);
//            imageview.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
//            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            Glide.with(context).load(browseBookModelArrayList.get(position).getBrowseModels().get(i).getBg_image()).into(imageview);
//
//            linearLayout1.addView(imageview);
//            linearLayout1.addView(textView);

//            holder.horizontalScrollView.addView(holder.ll);

        }

        holder.text_seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAGEDITORbg_image","failed"+browseBookModelArrayList.get(position).getId()+"");

                onTouchPosition.position(Integer.parseInt(browseBookModelArrayList.get(position).getId()));
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
        public TextView text_seeall;
        public LinearLayout ll;
        public RelativeLayout llvertical;
        public HorizontalScrollView horizontalScrollView;
        public SampleViewHolders(View view)
        {
            super(view);

            text_name = view.findViewById(R.id.text_heading);
            text_seeall = view.findViewById(R.id.text_seeall);
            ll = view.findViewById(R.id.ll_adapter);
//            horizontalScrollView = view.findViewById(R.id.horizontalScrollView);


        }

    }

    public void inflateRecentlyAdded(final ArrayList<BrowseModel> browseBookModelArrayList, LinearLayout ll_scroll) {
        Log.e("VIEWS", "1 " + browseBookModelArrayList.size());
        ll_scroll.removeAllViews();
        for (int i = 0; i < browseBookModelArrayList.size(); i++) {
            try {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.browse_seeallrecenttopic_adapter, null);
              TextView  text_name = view.findViewById(R.id.text_title);
                TextView  text_subtitle = view.findViewById(R.id.text_subtitle);
               final ImageView image_book = view.findViewById(R.id.image);
                text_name.setText(browseBookModelArrayList.get(i).getRecenttitle());
                text_subtitle.setText(browseBookModelArrayList.get(i).getRecentsubtitle());
                Glide.with(context)
                        .load(browseBookModelArrayList.get(i).getBg_image())
                        .centerCrop()
                        .into(image_book);

                final int finalI = i;
                image_book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            context.startActivity(new Intent(context, EditorSecondSubActivity.class).putExtra("sub_id", browseBookModelArrayList.get(finalI).getRecentid())
                                    .putExtra("title_name", browseBookModelArrayList.get((finalI)).getRecenttitle())
                                    .putExtra("title_subname", browseBookModelArrayList.get((finalI)).getRecentsubtitle())
                                    .putExtra("title_description", browseBookModelArrayList.get((finalI)).getRecentdescriptior())
                                    .putExtra("title_bgimage", browseBookModelArrayList.get(finalI).getBg_image())
                            );
                        }
                        catch (Exception e){

                        }
//                        Log.e("TAGEDITORbg_image","failed"+browseBookModelArrayList.get(position).getId()+"");

//                        clickAdapter.clickoncard(image_book,finalI+"",browseBookModelArrayList.get(finalI).getBg_image(),"browse");
                    }
                });


                ll_scroll.addView(view);
            }

            catch (Exception e) {
                Log.e("VIEWS","1 "+browseBookModelArrayList.size()+" catch"+e.toString());
            }


        }
    }




}
