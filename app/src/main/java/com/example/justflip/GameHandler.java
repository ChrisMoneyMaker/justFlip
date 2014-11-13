package com.example.justflip;

import java.util.ArrayList;
import java.util.Timer;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class GameHandler {
	
	private Activity gameactivity;
	private int trycounter;
	private boolean isrunning;
    private boolean chronoIsRunning;
	public int n; // gridsize
	
	// array list for buttons
	public ArrayList<FlipButton> buttonlist = new ArrayList<FlipButton>();
	
	// OnClickListener instance variable
	public OnClickListener listener = new OnClickListener() {
		
		public void onClick(View v) {

			if (isrunning == true) {

                if (!chronoIsRunning) {
                    startChronometer();
                }

				int buttonindex = 0;
				
				for (FlipButton button : buttonlist) {

					if (button.getId() == v.getId()) {
					
						buttonindex = buttonlist.indexOf(button);
						break;
						
					}
					
				}
			
				// corners
				if (buttonindex == 0) {
					buttonlist.get(0).flipButton();
					buttonlist.get(1).flipButton();
					buttonlist.get(n).flipButton();
				} else if (buttonindex == n - 1) {
					buttonlist.get(n - 2).flipButton();
					buttonlist.get(n - 1).flipButton();
					buttonlist.get(2 * n - 1).flipButton();
				} else if (buttonindex == n * (n - 1)) {
					buttonlist.get(n * (n - 2)).flipButton();
					buttonlist.get(n * (n - 1)).flipButton();
					buttonlist.get(n * (n - 1) + 1).flipButton();
				} else if (buttonindex == n * n - 1) {
					buttonlist.get(n * n - 2).flipButton();
					buttonlist.get(n * n - 1).flipButton();
					buttonlist.get(n * (n - 1) - 1).flipButton();
				} 
				// left edge
				else if ((buttonindex % n) == 0) {
					buttonlist.get(buttonindex - n).flipButton();
					buttonlist.get(buttonindex).flipButton();
					buttonlist.get(buttonindex + 1).flipButton();
					buttonlist.get(buttonindex + n).flipButton();
				}
				// right edge
				else if ((buttonindex % n) == n - 1) {
					buttonlist.get(buttonindex - n).flipButton();
					buttonlist.get(buttonindex).flipButton();
					buttonlist.get(buttonindex - 1).flipButton();
					buttonlist.get(buttonindex + n).flipButton();
				}
				// top edge
				else if (buttonindex > 0 && buttonindex < n - 1) {
					buttonlist.get(buttonindex - 1).flipButton();
					buttonlist.get(buttonindex).flipButton();
					buttonlist.get(buttonindex + 1).flipButton();
					buttonlist.get(buttonindex + n).flipButton();
				}
				// bottom edge
				else if (buttonindex > n * (n - 1) && buttonindex < n * n - 1) {
					buttonlist.get(buttonindex - 1).flipButton();
					buttonlist.get(buttonindex).flipButton();
					buttonlist.get(buttonindex + 1).flipButton();
					buttonlist.get(buttonindex - n).flipButton();
				}
				// inner tiles
				else {
					buttonlist.get(buttonindex - 1).flipButton();
					buttonlist.get(buttonindex).flipButton();
					buttonlist.get(buttonindex + 1).flipButton();
					buttonlist.get(buttonindex - n).flipButton();
					buttonlist.get(buttonindex + n).flipButton();
				}
				
				trycounter++;
				displayTryCounter();
				
				// check whether all tiles are flipped correctly
				if (checkForFinish() == true) {
					endGame();
				}
				
			}

		}
		
	};
	
	public void startGame(Activity activity) {
		
		gameactivity = activity;
		isrunning = true;
		trycounter = 0;
        chronoIsRunning = false;
		displayTryCounter();
		/*displayChronometer();*/
		
	}
	
	public void endGame() {
		
		// disable usability of UI
		isrunning = false;
        stopChronometer();
		// check whether user achieved a new highscore
		this.compareScores(trycounter, n);
		/*displayChronometer();*/
		
	}
	
	public void createLayout(TableLayout tablelayout, Context context) {
		
		int id = 0;

		// loop over rows
		for (int i = 1; i <= n; i++) {
	
			TableRow row = new TableRow(context);
			LayoutParams rowparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(rowparams);
	
			// loop over columns
			for (int j = 1; j <= n; j++) {
	
				FlipButton button = new FlipButton(context);
				button.setId(id);
				button.setOnClickListener(listener);
				buttonlist.add(button);
				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(3, 3, 3, 3);
				button.setLayoutParams(params);
					
				row.addView(button);
				
				id++;
					
			}
					
			tablelayout.addView(row);
	
		}
		  
	}
	
	private void displayTryCounter() {
		
		TextView textview = (TextView) gameactivity.findViewById(R.id.trycounter);
		textview.setText(String.valueOf(trycounter));

        //Animation
        //TODO Missbraeuchliche Verwendung von chronoIsRunning beheben
        if (chronoIsRunning) {
            Animation anim = AnimationUtils.loadAnimation(gameactivity, R.anim.trycounter_animation);
            textview.startAnimation(anim);
        }

	}

    public void startChronometer() {
        chronoIsRunning = true;
        final Chronometer chrono =  (Chronometer) gameactivity.findViewById(R.id.chronometer1);
        chrono.start();
        chrono.setBase(SystemClock.elapsedRealtime());

        //eigener Timer
        final TextView timer = (TextView) gameactivity.findViewById(R.id.timer);

        //Animation
        //TODO Missbraeuchliche Verwendung von chronoIsRunning beheben

           final Animation anim = AnimationUtils.loadAnimation(gameactivity, R.anim.trycounter_animation);



        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
        {
            private long counter = 1;
            @Override
            public void onChronometerTick(Chronometer chronometer)
            {
                timer.setText("0" + String.valueOf(counter));
                counter ++;
                timer.startAnimation(anim);
            }

        });

    }

    public void stopChronometer() {
        ((Chronometer) gameactivity.findViewById(R.id.chronometer1)).stop();
    }
		
	private boolean checkForFinish() {
		
		int countUpside = 0;
		
		for (FlipButton button : buttonlist) {

			if (button.upside == 1) {
			
				countUpside++;
				
			}
			
		}
		
		if (countUpside == n * n) {
			return(true);
		} else {
			return(false);
		}
		
	}
	
	public int readHighscore(int gridsize) {
		
        SharedPreferences pref = gameactivity.getSharedPreferences("GAME", 0);
        
        // retrieve highscore in dependence of the current field size
        return pref.getInt("HIGHSCORE" + String.valueOf(gridsize), 1000);
        
    }
	
	private void writeHighscore(int highscore, int gridsize) {
		
        SharedPreferences pref = gameactivity.getSharedPreferences("GAME", 0);
        Editor editor = pref.edit();
        editor.putInt("HIGHSCORE" + String.valueOf(gridsize), highscore);
        editor.commit();
        
    }
	
	private void compareScores(int currentScore, int gridsize) {
		
		// check whether current score exceeds previous highscore
        if (currentScore > this.readHighscore(gridsize)) {
        	
            this.writeHighscore(currentScore, gridsize);
            
        }
        
    }
	
}
