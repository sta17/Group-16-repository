package utilities;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.os.Bundle;

public class GPSLocation extends Activity implements LocationListener {

    //Minimum distance to move before updating location
    private static final long MIN_DISTANCE = 10; // 10 meters
    // Time between updating location info
    private static final long UPDATE_TIME = 10000; // 10 seconds

    private Location lastLocation;
    protected LocationManager locationManager;
    private Context mContext;

    /*
     *  Initialize a new locationManager
     */
    public GPSLocation(Context context) {
        this.mContext = context;
        locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);
    }

    /*
     *  Start searching for locations
     */
    public void startGPS() {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                UPDATE_TIME, MIN_DISTANCE, this);

        lastLocation = null;
    }

    /*
     *  Tell locationListener to stop searching for locations
     */
    public void stopGPS() {
        locationManager.removeUpdates(this);
    }

    /*
     * Return latest location
	 * Returns null if no location has been found
     */
    public Location getLocation() {
        return lastLocation;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onLocationChanged(Location location) {
        /*String str = "Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude();
        Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();*/

        lastLocation = location;    //Update location
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
    }
}
