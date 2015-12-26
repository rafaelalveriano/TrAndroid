package com.androix;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 17/10/15.
 */
public class ConexaoHttp {

    private static int timeout = 5000;
    public static HttpPost post;
    private static DefaultHttpClient http = new DefaultHttpClient();
    private static org.apache.http.client.methods.HttpGet get;
    public RespostaInterface resposta;
    private String URL;


    private void http_timeout()
    {
        HttpParams httpParams = http.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
    }




    public String get(String URL)
    {
        String response = null;

        get = new org.apache.http.client.methods.HttpGet(URL);

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


    public String post(String URL, String dados){
        String response = null;

        post = new HttpPost(URL);

        http_timeout();

        List<NameValuePair> pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("token", Config.TOKEN));
        pairs.add(new BasicNameValuePair("json",dados));

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


    public String upload(String URL, String arquivo){
        String response = null;
        post = new HttpPost(URL);

        File file = new File(arquivo);

        try{
            MultipartEntityBuilder form = MultipartEntityBuilder.create();
            form.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            form.addPart("token", new StringBody(Config.TOKEN, ContentType.MULTIPART_FORM_DATA));
            form.addBinaryBody("arquivo", file);

            HttpEntity httpEntity = form.build();
            post.setEntity(httpEntity);

            HttpResponse resp  = http.execute(post);

            HttpEntity entity = resp.getEntity();
            response = EntityUtils.toString(entity);
        }catch(Exception e){
            response = e.getMessage();
        }
        return response;
    }


}
