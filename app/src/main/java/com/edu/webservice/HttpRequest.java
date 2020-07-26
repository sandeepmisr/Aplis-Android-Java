package com.edu.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.edu.aplis.R;
import com.edu.preference.PrefrenceUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import preference.PrefrenceUtils;

public class HttpRequest extends AsyncTask<Httpcall, String, String> {

    private static final String UTF_8 = "UTF-8";
    String token="";
    static Context ctx;
    public HttpRequest(String token,Context context) {
        ctx=context;
        this.token=token;
    }
    @Override
    protected String doInBackground(Httpcall... params) {
        HttpsURLConnection urlConnection = null;
        Httpcall httpCall = params[0];
        StringBuilder response = new StringBuilder();
        try{
            String dataParams = getDataString(httpCall.getParams(), httpCall.getMethodtype());
            URL url = new URL(httpCall.getMethodtype() == Httpcall.GET ? httpCall.getUrl() + dataParams : httpCall.getUrl());
            urlConnection = (HttpsURLConnection) url.openConnection();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] tmlist = {new MyTrustManager()};
            sslContext.init(null, tmlist, null);
            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            urlConnection.setRequestMethod(httpCall.getMethodtype() == Httpcall.GET ? "GET":"POST");
//            urlConnection.setRequestProperty ("User-agent", "mozilla");
//            urlConnection.setRequestMethod(httpCall.getMethodtype() == Httpcall.GET ? "GET":"POST");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
//            urlConnection.setRequestProperty("app_id","c6817759");
//            urlConnection.setRequestProperty("app_key","ba32ed8e690231157e208b6ad4a24fad");
//            urlConnection.setRequestProperty("Authorization","Bearer"+" "+token);
            if(httpCall.getParams() != null && httpCall.getMethodtype() == Httpcall.POST){
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, UTF_8));
                writer.append(dataParams);
                writer.flush();
                writer.close();
                os.close();
            }

            int responseCode = urlConnection.getResponseCode();
            Log.e("RESPONSETIMESLOT","response"+response+" "+responseCode + " "+token);
            if(responseCode == HttpURLConnection.HTTP_OK){
                String line ;
                BufferedReader br = new BufferedReader( new InputStreamReader(urlConnection.getInputStream()));
                while ((line = br.readLine()) != null){
                    response.append(line);
                }
            }

            else if (responseCode ==401){
//                PrefrenceUtils.writeBoolean(ctx, PrefrenceUtils.PREF_REGISTRED,false);
                Log.e("RESPONSETIMESLOT1",responseCode+"");
//                ctx.startActivity(new Intent(ctx,ActivityLogin.class));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onResponse(s);
    }

    public void onResponse(String response){

    }

    private String getDataString(HashMap<String, String> params, int methodType) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (isFirst){
                isFirst = false;
                if(methodType == Httpcall.GET){
                    result.append("?");
                }
            }else{
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), UTF_8));
        }
        return result.toString();
    }


    private static class MyTrustManager implements X509TrustManager
    {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException
        {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException
        {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }

    }



}
