package com.example.justflip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;

public class GameActivity extends Activity {
	
	TableLayout tablelayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		Intent intent = getIntent();
		int gridsize = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
		
		// withdraw table layout
	    tablelayout = (TableLayout) findViewById(R.id.tableLayout);
	    
	    // get current context
	    Context context = (Context) this;
	    
	    // get instance of game handler
	    GameHandler gamehandler = new GameHandler();
	    gamehandler.n = gridsize;
	    
	    // create custom layout
	    gamehandler.createLayout(tablelayout, context);
	    
	    // launch the game
	    gamehandler.startGame(this);
	    
	}

}
