package com.hack.swachhshauchalaya;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.firebase.client.Firebase;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

/**
 * Created by Sharath_Mk on 3/19/2016.
 */
public class Application extends android.app.Application implements BeaconConsumer {
    BeaconManager beaconManager;
    NotificationManager mNotificationManager;
    private RegionBootstrap regionBootstrap;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                for (Beacon beacon : collection) {
                    Log.v("qwe", beacon.getId1().toHexString());
                    if (beacon.getId1().toHexString().startsWith("0x52192")) {
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                                Application.this);
                        mBuilder.setStyle(
                                new NotificationCompat.BigTextStyle(mBuilder)
                                        .bigText("Please share your valuable feedback to improve our service")
                                        .setSummaryText("Thank you."))
                                .setContentTitle("Welcome To Your ----!").setContentText("Welcome To Your ----!")
                                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                                .setTicker("New Notification Alert!");
                        Intent resultIntent = new Intent(Application.this, RatingActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(Application.this);
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(resultPendingIntent);

                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(0, mBuilder.build());
                    }
                }
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("uinqueRangeId", null, null, null));
        } catch (RemoteException e) {
        }
    }

}
