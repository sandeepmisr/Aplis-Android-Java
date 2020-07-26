package com.edu.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.edu.preference.PrefrenceUtils;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class ApiService extends AsyncTask<Void, Void, String> {
    private String url;
    Context context;
    private int posttype;
    private String xid;
    private String token;
    ProgressDialog progressDialog;
    ResponceQueues responceQueues;
    Httpcall httpCallPost;
    HashMap<String, String> hashMap;

    public ApiService(Context context, ResponceQueues responceQueues, String url, HashMap<String, String> hashMap, int posttype){
        this.url = url;
        this.context = context;
        this.responceQueues = responceQueues;
        this.hashMap = hashMap;
        this.posttype = posttype;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.hide();
        }catch (Exception e){

        }
//        trustEveryone();

    }

    @Override
    protected String doInBackground(Void... params) {
        httpCallPost = new Httpcall();
        httpCallPost.setMethodtype(posttype);

        httpCallPost.setUrl(url);
//        HashMap<String,String> paramsPost = new HashMap<>();
        httpCallPost.setParams(hashMap);
        return "";
    }

    @Override
    protected void onPostExecute(String aVoid) {
        new HttpRequest(PrefrenceUtils.readString(context, PrefrenceUtils.PREF_DEVIC_TOKEN,""),context) {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                try {
//                    Log.e("REPONSE", response);
                    progressDialog.dismiss();
                }
                catch (Exception e){

                }


                responceQueues.responceQue(response,url,posttype+"");
            }
    }.execute(httpCallPost);


    }




}
