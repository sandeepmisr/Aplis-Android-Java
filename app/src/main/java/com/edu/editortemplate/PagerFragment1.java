package com.edu.editortemplate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.edu.aplis.R;

public class PagerFragment1 extends Fragment {

    private String title;
    private int page;
    static Context mcontext;


    public static PagerFragment1 newInstance(String title,String subheading,String longtext,String mimetype,String Card_url,Context context) {
        PagerFragment1 fragment = new PagerFragment1();
        mcontext=context;
//      clickAdapter1=clickAdapter;
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("subheading", subheading);
        args.putString("longtext", longtext);
        args.putString("mimetype", mimetype);
        args.putString("Card_url", Card_url);
        fragment.setArguments(args);
        return fragment;
    }

    public PagerFragment1() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String title = this.getArguments().getString("title");
        String subheading = this.getArguments().getString("subheading");
        String longtext = this.getArguments().getString("longtext");
        String mimetype = this.getArguments().getString("mimetype");
        String Card_url = this.getArguments().getString("Card_url");
        View itemView = inflater.inflate(R.layout.editorone_adapter, container, false);


        TextView tittle = itemView.findViewById(R.id.heading);
        TextView text_body = itemView.findViewById(R.id.subheading);
        TextView text_longdescription = itemView.findViewById(R.id.longtext);
        LinearLayout view_mime = itemView.findViewById(R.id.mimeview);
        RelativeLayout frame = itemView.findViewById(R.id.frame);

        tittle.setText(title);
        text_body.setText(subheading);
        text_longdescription.setText(Html.fromHtml(longtext));


        Log.e("TAGADAPTER",mimetype);
        if (mimetype.equalsIgnoreCase("image")){
            ImageView imageView = new ImageView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vp);
            Glide
                    .with(mcontext)
                    .load(Card_url)
                    .thumbnail(0.05f)
                    .centerCrop()
                    .into(imageView);
            view_mime.addView(imageView);

        }
        else if (mimetype.equalsIgnoreCase("web")){
            WebView webView = new WebView(mcontext);
            webView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setAllowFileAccess(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webView.loadDataWithBaseURL(null, Card_url, "text/html", "UTF-8", null);

            view_mime.addView(webView);
        }

        else if (mimetype.equalsIgnoreCase("gif")){
            ImageView imageView = new ImageView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vp);
            Glide
                    .with(mcontext)
                    .load(Card_url)
                    .asGif()
                    .thumbnail(0.05f)
                    .centerCrop()
                    .into(imageView);
            view_mime.addView(imageView);

        }

        else if (mimetype.equalsIgnoreCase("video")){
            VideoView videoView = new VideoView(mcontext);
            RelativeLayout.LayoutParams vp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
            videoView.setLayoutParams(vp);
            videoView.setVideoURI(Uri.parse(Card_url));
            videoView.start();
            view_mime.addView(videoView);

        }
//        frame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                clickAdapter.clickoncard(position,listModel.getMime_type(),listModel.getHeader_url(),listModel.getTitle(),listModel.getLong_description(),null,listModel.getImage_Ar()
////                        ,listModel.getId());
//            }
//        });


        try {
//            Picasso.with(context1).load(aaya)
//                    .placeholder(context1.getResources().getDrawable(R.drawable.logoplaceholder)).into(imageView);
        }
        catch (Exception e){
            e.printStackTrace();
        }

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickAdapter1.click("","");
//            }
//        });
        return itemView;
    }

}
