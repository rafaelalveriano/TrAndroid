package com.androix;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rafael on 10/02/15.
 */

public class Post extends AsyncTask<String, String, String> {

    public static DefaultHttpClient http = new DefaultHttpClient();
    public static HttpPost post;
    private static Integer timeout = 3000;


    private void http_timeout()
    {
        HttpParams httpParams = http.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
    }


    private String envia(String URL, List<NameValuePair>pairs)
    {
        String response = null;

        post = new HttpPost(URL);

        http_timeout();

        try{
            post.setEntity(new UrlEncodedFormEntity(pairs , HTTP.UTF_8));

            HttpResponse resp = http.execute(post);

            HttpEntity entity = resp.getEntity();

            response = EntityUtils.toString(entity);

        }catch(Exception e){
            response = "http_error";
        }

        return response;
    }



    /* ASYNC TASK */

    @Override
    protected String doInBackground(String... params)
    {

        String url = params[0];

        List<NameValuePair> pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("token", Config.TOKEN));
        pairs.add(new BasicNameValuePair("json",params[1]));

        String resp = envia(url, pairs);

        return resp;
    }


    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        Log.i("Response:", result);
    }


}
