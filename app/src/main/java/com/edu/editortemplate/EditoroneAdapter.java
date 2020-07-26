package com.edu.editortemplate;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;
import com.edu.discover.ClickAdapter;
import com.edu.discover.DiscoverModel;

import java.util.ArrayList;

public class EditoroneAdapter extends RecyclerView.Adapter<EditoroneAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<DiscoverModel> discoverArrayList;
    ClickAdapter clickAdapter;


    public EditoroneAdapter(Context context, ArrayList<DiscoverModel> discoverAdapterArrayList, ClickAdapter clickAdapter) {
        this.mcontext = context;
        this.discoverArrayList = discoverAdapterArrayList;
        this.clickAdapter = clickAdapter;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.editorone_adapter, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        final DiscoverModel listModel = discoverArrayList.get(position);

        viewHolder.tittle.setText(listModel.getTitle());
        viewHolder.text_body.setText(listModel.getStatus());
        viewHolder.text_longdescription.setText(Html.fromHtml(listModel.getLong_description()));

        Log.e("TAGADAPTER",listModel.getMime_type());
        if (listModel.getMime_type().equalsIgnoreCase("image")){
            ImageView imageView = new ImageView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vp);
            Glide
                .with(mcontext)
                .load(listModel.getCard_url())
                .thumbnail(0.05f)
                .centerCrop()
                .into(imageView);
            viewHolder.view_mime.addView(imageView);

        }
        else if (listModel.getMime_type().equalsIgnoreCase("web")){

            WebView webView = new WebView(mcontext);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(listModel.getCard_url());
            WebSettings webSettings = webView.getSettings();
            webSettings.setAllowFileAccess(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            viewHolder.view_mime.addView(webView);
        }

        else if (listModel.getMime_type().equalsIgnoreCase("gif")){
            ImageView imageView = new ImageView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vp);
            Glide
                    .with(mcontext)
                    .load(listModel.getCard_url())
                    .asGif()
                    .thumbnail(0.05f)
                    .centerCrop()
                    .into(imageView);
            viewHolder.view_mime.addView(imageView);

        }

        else if (listModel.getMime_type().equalsIgnoreCase("video")){
            VideoView videoView = new VideoView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            videoView.setLayoutParams(vp);
            viewHolder.view_mime.addView(videoView);

        }
//        viewHolder.frame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickAdapter.clickoncard(position,listModel.getMime_type(),listModel.getHeader_url(),listModel.getTitle(),listModel.getLong_description(),null,listModel.getImage_Ar()
//                ,listModel.getId());
//            }
//        });

//        Glide
//                .with(mcontext)
//                .load(listModel.getCard_url())
//                .thumbnail(0.05f)
//                .centerCrop()
//                .into(viewHolder.view_mime);
       // viewHolder.image.setImageResource(listModel.getHeader_url());



//        arrow.setImageResource(listModel.getArrow());
    }

    @Override
    public int getItemCount() {
        return discoverArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout view_mime;
//        RelativeLayout frame;
        TextView tittle,text_body,text_longdescription;

        public ViewHolder(View itemView) {
            super(itemView);


            this.tittle = itemView.findViewById(R.id.heading);
            this.text_body = itemView.findViewById(R.id.subheading);
            this.text_longdescription = itemView.findViewById(R.id.longtext);
            this.view_mime = itemView.findViewById(R.id.mimeview);
//            this.frame = itemView.findViewById(R.id.frame);

        }
    }
}