package com.demasiadomexicano.demasiadomexaradio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;


public class MainActivity extends AppCompatActivity {

    private ImageView play;
    private ImageButton btnWhatsapp;
    private String url;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    NotificationCompat.Builder notificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearNotificacionChannel();
        crearNotificacion();

        ExoPlayer exoPlayer = new ExoPlayer.Builder(this).build();

        play = (ImageView) findViewById(R.id.playBtn);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!exoPlayer.isPlaying()){
                    //Toast.makeText(MainActivity.this, "Reproduciendo", Toast.LENGTH_SHORT).show();
                    play.setImageResource(R.drawable.ic_pause);
                    MediaItem url = MediaItem.fromUri("https://servidor22.brlogic.com:7216/live");
                    exoPlayer.setMediaItem(url);
                    exoPlayer.prepare();
                    exoPlayer.play();
                }else{
                    //Toast.makeText(MainActivity.this, "Pausando", Toast.LENGTH_SHORT).show();
                    play.setImageResource(R.drawable.ic_play);
                    exoPlayer.pause();
                }
            }
        });

        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                url = "https://wa.pe/DemasidoMx";
                Uri uri = Uri.parse(url);
                Intent abrir = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(abrir);

            }
        });
        }

    private void crearNotificacionChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void crearNotificacion() {
        notificacion = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Demasiado Mexicano")
                .setContentText("Radio");

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        notificacion.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, notificacion.build());
    }
}