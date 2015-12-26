package com.androix;


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;


/**
 * Created by Rafael on 15/10/15.
 */
public class Upload extends AsyncTask<String,String,String> {
    public static DefaultHttpClient http = new DefaultHttpClient();
    public static HttpPost post;
    private static Integer timeout = 3000;


    private void http_timeout()
    {
        HttpParams httpParams = http.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
    }


    private String envia(String URL, File ARQUIVO){
        String response = null;
        post = new HttpPost(URL);

        try{
            MultipartEntityBuilder form = MultipartEntityBuilder.create();
            form.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            form.addPart("token", new StringBody(Config.TOKEN,ContentType.MULTIPART_FORM_DATA));
            form.addBinaryBody("arquivo", ARQUIVO);

            HttpEntity httpEntity = form.build();
            post.setEntity(httpEntity);

            HttpResponse resp  = http.execute(post);

            HttpEntity entity = resp.getEntity();
            response = EntityUtils.toString(entity);
        }catch(Exception e){
            response = "Error: "+e.getMessage();
        }
        return response;
    }



    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        String arquivo = params[1];

        File file = new File(arquivo);
        String response = envia(url, file);
        return response;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.i("Resposta:", s);
    }
}

