package com.cont96roller.weatherdiary;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.content.ContextCompat;
import android.util.Log;



public class GpsTracker extends Service implements LocationListener {

    private final Context mContext;
    private Location mLocation;
    private double mLatitude;
    private double mLongitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;


    public GpsTracker(Context context) {
        this.mContext = context;
        getmLocation();
    }


    public Location getmLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {

                int hasFineLocationPermission = ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


                if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                        hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

                    ;
                } else
                    return null;


                if (isNetworkEnabled) {


                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null)
                    {
                        mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (mLocation != null)
                        {
                            mLatitude = mLocation.getLatitude();
                            mLongitude = mLocation.getLongitude();
                        }
                    }
                }


                if (isGPSEnabled)
                {
                    if (mLocation == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null)
                        {
                            mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (mLocation != null)
                            {
                                mLatitude = mLocation.getLatitude();
                                mLongitude = mLocation.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Log.d("@@@", ""+e.toString());
        }

        return mLocation;
    }

    public double getmLatitude()
    {
        if(mLocation != null)
        {
            mLatitude = mLocation.getLatitude();
        }

        return mLatitude;
    }

    public double getmLongitude()
    {
        if(mLocation != null)
        {
            mLongitude = mLocation.getLongitude();
        }

        return mLongitude;
    }

    @Override
    public void onLocationChanged(Location location)
    {
    }

    @Override
    public void onProviderDisabled(String provider)
    {
    }

    @Override
    public void onProviderEnabled(String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }


    public void stopUsingGPS()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(GpsTracker.this);
        }
    }


}