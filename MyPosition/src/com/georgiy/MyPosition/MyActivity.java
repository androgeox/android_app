package com.georgiy.MyPosition;

        import java.io.IOException;
        import java.util.List;

        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.app.Activity;
        import android.content.Context;
        import android.widget.TextView;
        import com.georgiy.MyPosition.R; 

/**
 * Main activity in this application.
 * */
public class MyActivity extends Activity {

    /**
     * Private fields for store links to UI components
     *  */
    private TextView tvGPS = null;
    private TextView tvNet = null;
    private TextView tvPas = null;
    private TextView tvAdr = null;

    /**
     * Private field for store a link to LocationManager object
     * */
    private LocationManager locManager = null;

    /**
     * Private field for store a link to the Location Listener object
     * */
    private LocListener locListener = null;

    /**
     * Private method that contain initialization code
     * for user interface (UI) components.
     * */
    private void initUI() {
        tvGPS = (TextView) this.findViewById(R.id.tvGPS);
        tvNet = (TextView) this.findViewById(R.id.tvNet);
        tvPas = (TextView) this.findViewById(R.id.tvPas);
        tvAdr = (TextView) this.findViewById(R.id.tvAdr);
    }

    /**
     * Called when the activity is starting (for more details,
     * please see description into super class).
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

		/* Invoke a parent method */
        super.onCreate(savedInstanceState);

		/* Load User Interface from resources */
        setContentView(R.layout.act_main);

		/* Initialize UI components (see initUI() method */
        this.initUI();

		/* Get a link to the Location Manager */
        locManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);

		/* Define Location variable */
        Location loc = null;

		/* Get information from GPS location provider */
        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc != null) {
            tvGPS.setText(this.getLocStr(loc));
        }

		/* Get information from Network location provider */
        loc = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (loc != null) {

			/* Show current location */
            tvNet.setText(this.getLocStr(loc));

			/* Get Address by location */
            String str = this.getAddressStr(loc);

			/* Show address */
            if (str != null)
                tvAdr.setText(str);

        }

		/* Get information from Passive location provider */
        loc = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if (loc != null) {
            tvPas.setText(this.getLocStr(loc));

			/* Get Address by location */
            String str = this.getAddressStr(loc);

			/* Show address */
            if (str != null)
                tvAdr.setText(str);


        }

    }

    @Override
    protected void onResume() {

		/* Invoke a parent method */
        super.onResume();

		/* Create Location Listener object (if needed) */
        if (locListener == null)
            locListener = new LocListener();

		/* Setting up Location Listener */
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000L, 1.0F, locListener);
    }

    @Override
    protected void onPause() {

		/* Remove Location Listener */
        locManager.removeUpdates(locListener);

		/* Invoke a parent method */
        super.onPause();

    }

    /**
     * Get location string by location value
     * */
    private String getLocStr(Location loc) {
        return "Latitude: " + String.valueOf(loc.getLatitude()) + "; " +
                "Longitude: " + String.valueOf(loc.getLongitude());
    }

    /**
     * Get address string by location value
     * */
    private String getAddressStr(Location loc) {

		/* Define variable for store result */
        String str = "";

		/* Create Geocoder object */
        Geocoder geo = new Geocoder(this);

		/* Get addresses list by location and prepare result */
        try {

			/* Get addresses list by location and prepare result */
            List<Address> aList = geo.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 5);

			/* Get address */
            if (aList.size() > 0) {

				/* Get first element from List */
                Address a = aList.get(0);

				/* Get a Postal Code */
                int maxAddrLine = a.getMaxAddressLineIndex();
                if (maxAddrLine >= 0) {
                    str = a.getAddressLine(maxAddrLine);
                    if (!str.isEmpty()) str += ", ";
                }

				/* Prepare a result */
                str += a.getCountryName() + ", " + a.getAdminArea() + ", " +
                        a.getThoroughfare() + " " + a.getSubThoroughfare();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }

		/* Return a value */
        return str;

    }

    /**
     * Class that implements Location Listener interface
     * */
    private class LocListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

			/* Show current location */
            tvGPS.setText(getLocStr(location));

			/* Get Address by location */
            String str = getAddressStr(location);

			/* Show address */
            if (str != null) tvAdr.setText(str);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}

    }

}
