ENVIRONMENT:
1) download Eclipse adt bundle.
2) configure SDK manager.
3) installare USB driver:
	a. NEXUS --> download google USB driver [http://developer.android.com/sdk/win-usb.html#top].
	b. Other --> sito vendor.
4) settare SDK Manager:
	a. creare piattaforma.
	b. scaricare Play Services se servono.
5) prima di usare location API assicurarsi che dispositivo sia collegato alla rete e GPS ON.
6) occorre avere nel workspace google-play-services_lib:
	a. scaricare tramite SDK manager,
	b. copiare nella cartella workspace,
	c. importare (Android Existing Code) all'interno di Eclipse.
7) nel progetto dipendente, settare la dipendenza alle librerie importate.

CODE 	ACTIVITY
1) MainActivity extends Activity
2) onCreate(Bundle) - onStart - onStop - onCreateOptionsMenu - 
3) Button myButton=(Button)findViewById(R.id.myButtonID);
	
	BUTTON+EXPLICIT INTENT
4) myButton.setOnClickListener(new View.OnClickListener() {
	a. public void onClick(View v) {
        b. Intent configIntent = new Intent(MainActivity.this, ConfigActivity.class);
	c. startActivity(configIntent);}

	INTENT EXTRAS
5) Intent mainIntent = new Intent(this, Activity.class);
	a.mainIntent.putExtra("KEY",value).
6) Bundle config = getIntent().getExtras();
	a. config.getInt("Key").

	BROADCAST RECEIVER
7) public class MyBroadcastReceiver extends BroadcastReceiver.
8) @Override public void onReceive (ctx, intent){...}
9) all'interno dell'activity che vuole essere notificata:
	a. Intent (this, MyBroadcastReceiver)
	b. PendingIntent.getBroadcast (this.appCtx, ..., intent, ...)
	c. alarmManager.set(type, millis, PendingIntent)

	LOCATION
10) LocationClient mLocationClient; Location mCurrentLocation.
11) new LocationClient(MainActivity.this, MainActivity.this, MainActivity.this);
12) mCurrentLocation=mLocationClient.getLastLocation();mCurrentLocation.getLatitude().

	MEDIA
13) MediaPlayer mPlayer=MediaPlayer.create(Activity.this, R.raw.media);
14) mPlayer.start()

MANIFEST
1) [permessi Location] <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
2) [Broadcast Receiver] [figlio del tag application] <receiver android:name="MyBroadcastReceiver"/>

BIBLIO:
a. [developer google guide] http://developer.android.com/sdk.
b. [Google Play services APIs] http://developer.android.com/google/play-services/setup.html
c. [free anthem mp3] http://www.lengua.com/hymnen.html
