package tech28.alarme;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hackaton on 29/08/2016.
 */
public class TimeService extends Service {
    // constant
    public static final long NOTIFY_INTERVAL = 5 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Context c;

    @Override
    public void onCreate() {
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }

        c =  this;
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {




            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    URL url = null;
                    HttpURLConnection client = null;
                    try {
                        url = new URL("http://10.56.3.220/events/findEventsNearMe");
                        client = (HttpURLConnection) url.openConnection();

                        LocationManager locationManager = (LocationManager)
                                getSystemService(Context.LOCATION_SERVICE);

                        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                        }

                        Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                client.setRequestMethod("POST");
                        client.setRequestProperty("userTelNum", "0587070140");
                        client.setRequestProperty("x", String.valueOf(l.getLongitude()));
                        client.setRequestProperty("y", String.valueOf(l.getLatitude()));
                        client.setDoOutput(true);

                        OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
                        writeStream(outputPost);
                        outputPost.flush();
                        outputPost.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (client != null) // Make sure the connection is not null.
                            client.disconnect();
                    }
                    // go to server

                }
            });
        }

        private void writeStream(OutputStream out) throws IOException {
            String output = "Hello world";

            out.write(output.getBytes());
            //out.flush();
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }
    }
}