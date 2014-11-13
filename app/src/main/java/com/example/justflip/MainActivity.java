package com.example.justflip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.justflip.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	@Override
	protected void onResume () {
		
		super.onResume();
		
		// retrieve the four highscores
		this.displayHighscores();
		
	}
	
	public void sendGridSize3(View view) {
		this.loadGameActivity("3");	
	}
	
	public void sendGridSize4(View view) {
		this.loadGameActivity("4");	
	}
	
	public void sendGridSize5(View view) {
		this.loadGameActivity("5");	
	}
	
	public void sendGridSize6(View view) {
		this.loadGameActivity("6");	
	}
	
	public void loadGameActivity (String gridsize) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(EXTRA_MESSAGE, gridsize);
		startActivity(intent);
	}
	
	private void displayHighscores() {
		
		SharedPreferences pref = this.getSharedPreferences("GAME", 0);
		Button highscorebutton = (Button) findViewById(R.id.highscore);
		highscorebutton.setText("highscore 3x3: " + String.valueOf(pref.getInt("HIGHSCORE3", 1000)));
		pref = null;
		
	}
	
}
