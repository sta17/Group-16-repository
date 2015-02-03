package uk.ac.aber.dcs.cs221.group16.util;

import android.app.Service;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Fredric(),
 * @author Steven(Sta17),
 * @author Dan(Daf16)
 * @since 1.2
 * @version 2.2
 * @see Location
 * 
 */
public class GPSLocation extends Service 
implements LocationListener {

	// Minimum distance to move before updating location
	private static final long MIN_DISTANCE = 10; // 10 meters
	// Time between updating location info
	private static final long UPDATE_TIME = 1000 * 60; // 1 minutte

	private Location lastLocation;
	double latitude; // latitude
	double longitude; // longitude
	protected LocationManager locationManager;
	private Context mContext;
    boolean GPSPossible = false; // flag for GPS status

	/*
	 * Initialize a new locationManager
	 */
	public GPSLocation(Context context) {
		this.mContext = context;
		getLocation();
	}

	/**
	 * Start searching for locations
	 * Return latest location Returns null if no location has been found
	 */
	public Location getLocation() {
		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);
		boolean GPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean NetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!GPSEnabled && !NetworkEnabled) {
			// no network provider is enabled
		} else {
			this.GPSPossible = true;
			if (NetworkEnabled) {
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, UPDATE_TIME,
						MIN_DISTANCE, this);
				Log.d("Network", "Network");
				if (locationManager != null) {
					lastLocation = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				}
			}
			if (GPSEnabled) {
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, UPDATE_TIME,
						MIN_DISTANCE, this);
				Log.d("GPS Enabled", "GPS Enabled");
				if (locationManager != null) {
					lastLocation = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}
			}
		}
		return lastLocation;
	}

	/*
	 * Tell locationListener to stop searching for locations
	 */
	public void stopGPS() {
		locationManager.removeUpdates(this);
	}

	/**
     * Checks if it is possible to get a GPS Location
     * @return boolean
     * */
    public boolean getGPSPossible() {
		return this.GPSPossible;
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


}