package com.martinbordon.tp6;

import android.app.Service;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

public class servicio extends Service {
    private Thread hilo;
    private boolean flag = true;

    int contador = 0;
    public servicio() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        acceder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        acceder();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
    }


    public void acceder() {

        Uri llamadas = Uri.parse("content://sms/inbox");
        ContentResolver cr = this.getContentResolver();

        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while(flag) {
                   // Cursor cursor = cr.query(llamadas, null, null, null, null);
                    Cursor cursor = cr.query(llamadas, null, null, null, "date DEESC LIMIT 5");
                    String fechaMensaje = null;
                    String textoMensaje = null;
                    String contactoMensaje = null;


                    StringBuilder resultado = new StringBuilder();
                    if (cursor.getCount() > 0) {

                        while (cursor.moveToNext()) {
                            int fecha = cursor.getColumnIndex(Telephony.Sms.DATE);
                            int contacto = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
                            int mensaje = cursor.getColumnIndex(Telephony.Sms.BODY);

                            fechaMensaje = cursor.getString(fecha);
                            textoMensaje = cursor.getString(mensaje);
                            contactoMensaje = cursor.getString(contacto);
                            resultado.append("fecha " + fechaMensaje + " contacto " + contactoMensaje + " mensaje " + textoMensaje);
                        }
                        Log.d("salida", resultado.toString());
                    }

                    contador++;
                    Log.d("contador", "resultado: " + contador);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        hilo.start();


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
