package com.example.scolarapp;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.common.GooglePlayServicesUtil;

//import android.app.Dialog;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements
								GooglePlayServicesClient.ConnectionCallbacks,
								GooglePlayServicesClient.OnConnectionFailedListener
								{
	
	//UI
	private Button myProfileButton;
	private Button verifyButton;
	private TextView coordinatesTextView;
	private Button playButton;
	private TextView playTextView;

	//Location
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private LocationClient mLocationClient;
    private Location mCurrentLocation;

    //MEDIA
    private MediaPlayer mPlayer; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myProfileButton =(Button)findViewById(R.id.myProfileButton);
        verifyButton =(Button)findViewById(R.id.verifyButton);
        playButton =(Button)findViewById(R.id.playMusicButton);
        playTextView = (TextView)findViewById(R.id.playTextView);
        coordinatesTextView = (TextView)findViewById(R.id.coordinatesTextView);
        
        mLocationClient = new LocationClient(MainActivity.this, MainActivity.this, MainActivity.this);
        mLocationClient.connect();
        
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Explicit Intent for Configuration Activity 
                Intent configIntent = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(configIntent);
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	/*
                 * Create a new location client, using the enclosing class to handle callbacks.
                 */
                mCurrentLocation = mLocationClient.getLastLocation();	
                coordinatesTextView.setText("");
                coordinatesTextView.setText("\nLat ==> "+mCurrentLocation.getLatitude()+" ;\nLon ===> " 
                					+mCurrentLocation.getLongitude()+" .\n");
            }
        });
        
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle config = getIntent().getExtras();
                playTextView.setText("");

                if(config != null){
	                int northSong = config.getInt("NORTH_SONG");
	                int southSong = config.getInt("SOUTH_SONG");
	                mCurrentLocation = mLocationClient.getLastLocation();	
	                if(mCurrentLocation.getLatitude()<0){
		            	//Emisfero Australe [Lat<0]
		            	playTextView.setText("\n\temisfero australe--"+southSong+"--\n");
		            	if (southSong==1)
		            		mPlayer = MediaPlayer.create(MainActivity.this, R.raw.germany);
		            	else
		            		mPlayer = MediaPlayer.create(MainActivity.this, R.raw.italy);
	                }
	                else {
	                	//Emisfero Boreale[Lat>0]
	                	playTextView.setText("\n\temisfero Boreale--"+northSong+"--\n");
	                	if (northSong==1)
		            		mPlayer = MediaPlayer.create(MainActivity.this, R.raw.germany);
		            	else
		            		mPlayer = MediaPlayer.create(MainActivity.this, R.raw.italy);
	                }
	                mPlayer.start();
                }
                else{
                	playTextView.setText("");
                	playTextView.setText("Use My_Profile Button ");
                }

            }
        });
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.connect();
    }

    @Override
    protected void onStop() {
        mLocationClient.disconnect(); // Disconnecting the client invalidates it!
        if(mPlayer!=null) {mPlayer.stop(); mPlayer.reset(); mPlayer.release(); mPlayer = null;}
        super.onStop();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    /**
     * Close with back button
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
	
//_____________LOCATION________
    
//    private boolean servicesConnected() {
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (ConnectionResult.SUCCESS == resultCode) {
//            return true;
//        }
//        else {
//            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
//            return false;
//        }
//    }
    
	/**
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     **/
	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();		
	}
	
	/**
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",Toast.LENGTH_SHORT).show();
	}
	
	/**
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this,CONNECTION_FAILURE_RESOLUTION_REQUEST);
            }
            catch (IntentSender.SendIntentException e) {e.printStackTrace();}
        } 
		else {
			Toast.makeText(this, "Connection Failed with error code --> " + connectionResult.getErrorCode(),Toast.LENGTH_SHORT).show();
        }
	}
}
