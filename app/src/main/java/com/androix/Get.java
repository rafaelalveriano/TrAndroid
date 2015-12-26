package com.androix;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class Get extends AsyncTask<String, String, String> {

    private static int timeout = 5000;
    private static DefaultHttpClient http = new DefaultHttpClient();
    private static HttpGet get;
    public RespostaInterface resposta;


    private void http_timeout()
    {
        HttpParams httpParams = http.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
    }


    private String get(String URL)
    {
        String response = null;

        get = new HttpGet(URL);

        http_timeout();

        try{
            HttpResponse resp = http.execute(get);

            HttpEntity entity = resp.getEntity();

            response = EntityUtils.toString(entity);

        }catch(Exception e){
            response = "404";
        }

        return response;
    }

    @Override
    protected String doInBackground(String... params) {
//        String URL = params[0];
//
//        String response = get(URL);

        return "get efetuado!";
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        resposta.Resposta(s);
    }
}
