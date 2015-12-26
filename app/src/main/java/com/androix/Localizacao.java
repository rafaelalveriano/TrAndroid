package com.androix;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by Rafael on 05/09/15.
 */
public class Localizacao  implements LocationListener{

    private Context context;
    private LocationManager locationManager;
    private Location location;

    private static final int DINSTANCIA_MINIMA = 0;
    private static final int TEMPO_MINIMO = 0;

    public Localizacao(Context context) {
        this.context = context;

        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        //Verificando se o GPS está ligado
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(isGPSEnable) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TEMPO_MINIMO, DINSTANCIA_MINIMA, this);

            //Adquirindo o último location capturado pelo gps
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        enviaCoordenada(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }


    private void enviaCoordenada(Location location)
    {
        String lag = String.valueOf(location.getLatitude());
        String log = String.valueOf(location.getLongitude());

        String coordenada = null;

        JSONObject jsonCoord = new JSONObject();

        try{
            jsonCoord.put("coordenada",lag+","+log);
        }catch (Exception e){
            e.printStackTrace();
        }
        coordenada = jsonCoord.toString();

        new ConexaoHttp().post(Config.SEND_COORDENADAS, Config.encode(coordenada));
        Log.i("Acao:"," Enviando coordenadas");

        locationManager.removeUpdates(Localizacao.this);
    }


}

