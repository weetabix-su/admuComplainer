package edu.ateneo.admucomplainer;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Map extends Activity {

	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		// enables the my location button on the upper right
		map.setMyLocationEnabled(true);

		// default to normal view
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private GoogleApiClient mGoogleApiClient;

	protected synchronized void buildGoogleApiClient()
	{

		MyGoogleClientListener listener = new MyGoogleClientListener();
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(listener)
				.addOnConnectionFailedListener(listener)
				.addApi(LocationServices.API)
				.build();
	}

	class MyGoogleClientListener implements ConnectionCallbacks, OnConnectionFailedListener
	{
		@Override
		public void onConnected(Bundle arg0)
		{
			System.out.println("CONNECTED");

			// GETTING LAST KNOW LOCATION
			Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
					mGoogleApiClient);

			if (mLastLocation != null)
			{
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 15));

				// update position
				updateMyPosition(mLastLocation);
			}


			// REQUESTING FOR UPDATES
			LocationRequest mLocationRequest = new LocationRequest();
			mLocationRequest.setInterval(10000);		
			mLocationRequest.setFastestInterval(5000);
			mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
					mLocationRequest,
					myLocationListener);

		}

		@Override
		public void onConnectionSuspended(int arg0)
		{

		}

		@Override
		public void onConnectionFailed(ConnectionResult arg0)
		{

			System.out.println("CONNECTION FAILED");
		}
	}
	
	public void onStart()
	{
		// initialize the GoogleClient every time the app starts
		if (mGoogleApiClient == null)
		{

			buildGoogleApiClient();
			mGoogleApiClient.connect();
			System.out.println("Connecting...");
		}

		super.onStart();

	}

	@Override
	public void onStop()
	{
		// disconnect the GoogleClient every time the app stops
		
		if (mGoogleApiClient != null)
		{
			// remove listener
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, myLocationListener);

			mGoogleApiClient.disconnect();
			mGoogleApiClient = null;
		}

		super.onStop();
	}

	// Map management
	
	
	private MyLocationListener myLocationListener = new MyLocationListener();

	class MyLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location arg0)
		{
			// on receiving a location update, update the pin location
			System.out.println("new location: " + arg0);
			updateMyPosition(arg0);
		}
	}

	private void updateMyPosition(Location loc)
	{
		System.out.println("Updating my position");

		try
		{
			// TODO: get the lat/lng of new position
			LatLng position = new LatLng(loc.getLatitude(), loc.getLongitude());

			// System.out.println(entry);
			addMarker(position, "Current Position", "This is you!");
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void addMarker(final LatLng pos, final String name, final String info)
	{
		// NOTE: in order to update the UI an operation must occur in the UI
		// thread, this will force the action to occur in the UI thread
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				// Use is diff icon to indicate if the data is sent or not
				MarkerOptions markerOptions = new MarkerOptions()
						.position(pos);

				markerOptions.title(name);
				markerOptions.snippet(info);

				// based on local profile
				//markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin));

				Marker marker = map.addMarker(markerOptions);
			}
		});

	}
}
