package com.example.scolarapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ConfigActivity extends Activity {
	
	//UI
	private RadioGroup northRadioGroup, southRadioGroup;
	private RadioButton northSong1RadioButton, southSong1RadioButton;
	private Button storeButton;
	
	private int checkN, checkS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		checkN=0; checkS=0;
		storeButton = (Button)findViewById(R.id.storeSongButton);
		northRadioGroup = (RadioGroup)findViewById(R.id.NorthRadioGroup);
		southRadioGroup = (RadioGroup)findViewById(R.id.SouthRadioGroup);
		northSong1RadioButton= (RadioButton)findViewById(R.id.northSong1RadioButton);
		southSong1RadioButton= (RadioButton)findViewById(R.id.southSong1RadioButton);	
		northRadioGroup.check(northSong1RadioButton.getId());
		southRadioGroup.check(southSong1RadioButton.getId());
		
		storeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent mainIntent = new Intent(ConfigActivity.this, MainActivity.class);
            	if(checkN==0) mainIntent.putExtra("NORTH_SONG",1);
            	else mainIntent.putExtra("NORTH_SONG",2);
            	
            	if(checkS==0) mainIntent.putExtra("SOUTH_SONG",1);
            	else mainIntent.putExtra("SOUTH_SONG",2);
            	
            	mainIntent.putExtra("SOUTH_SONG",southRadioGroup.getCheckedRadioButtonId());
            	startActivity(mainIntent);
            }
        });
	}
	
	protected void onStart() {
		super.onStart();
		checkN=0; checkS=0;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
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
	
	public void onRadioButtonClicked(View view) {
	    boolean checked = ((RadioButton) view).isChecked();
	    switch(view.getId()) {
	        case R.id.northSong2RadioButton:
	            if (checked){
	            	checkN++;
	            	break;
	            }
	        case R.id.southSong2RadioButton:
	        	if (checked){
	            	checkS++;
	            	break;
	            }
	    }
	}
}
