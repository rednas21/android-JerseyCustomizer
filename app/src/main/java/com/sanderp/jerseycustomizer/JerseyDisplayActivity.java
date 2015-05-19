package com.sanderp.jerseycustomizer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class JerseyDisplayActivity extends Activity implements View.OnClickListener {

    public static final String  LLOM = "LLOM";
    public static final String  PLAYER_NAME = "PLAYER_NAME";
    public static final String  PLAYER_NUMBER = "PLAYER_NUMBER";
    public static final String  RED_JERSEY = "RED_JERSEY";
    private static final String PREFS = "PREFS";
    private static final int    REQUEST_CODE_CHANGE_BUTTON = 1;

    private String      playerName;
    private int         playerNumber;
    private boolean     jerseyIsRed;

    private TextView    mNameView;
    private TextView    mNumberView;
    private ImageView   mJerseyView;
    private Button      mEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jersey_display);

        SharedPreferences prefs = getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
        playerName = prefs.getString(PLAYER_NAME, "Default");
        playerNumber = prefs.getInt(PLAYER_NUMBER, 0);
        jerseyIsRed = prefs.getBoolean(RED_JERSEY, true);

        mNameView = (TextView) findViewById(R.id.nameView);
        mNumberView = (TextView) findViewById(R.id.numberView);
        mJerseyView = (ImageView) findViewById(R.id.jerseyView);

        mNameView.setText(playerName);
        mNumberView.setText(playerNumber + "");
        if (jerseyIsRed) mJerseyView.setBackgroundResource(R.drawable.red_jersey);
        else             mJerseyView.setBackgroundResource(R.drawable.blue_jersey);

        mEditView = (Button) findViewById(R.id.editButton);
        mEditView.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jersey_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.editButton) {
            Log.d(LLOM, "Edit button clicked");
            Intent editInfo = new Intent(this, JerseyInfoActivity.class);
            editInfo.putExtra(PLAYER_NAME, playerName);
            editInfo.putExtra(PLAYER_NUMBER, playerNumber);
            editInfo.putExtra(RED_JERSEY, jerseyIsRed);
            this.startActivityForResult(editInfo, REQUEST_CODE_CHANGE_BUTTON);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CHANGE_BUTTON:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(LLOM, "Result ok!");

                    playerName = data.getStringExtra(PLAYER_NAME);
                    Log.d(LLOM, "playerName = " + playerName);

                    playerNumber = data.getIntExtra(PLAYER_NUMBER, 0);
                    Log.d(LLOM, "playerNumber = " + playerNumber);

                    jerseyIsRed = data.getBooleanExtra(RED_JERSEY, true);
                    Log.d(LLOM, "jerseyIsRed = " + jerseyIsRed);

                    updateJerseyInfo();
                } else {
                    Log.d(LLOM, "Result not okay. User hit back without player name and number");
                }
                break;
            default:
                Log.d(LLOM, "Unknown result code");
                break;
        }
    }

    protected void updateJerseyInfo() {
        mNameView.setText(playerName);
        mNumberView.setText(playerNumber + "");
        if (jerseyIsRed) mJerseyView.setBackgroundResource(R.drawable.red_jersey);
        else             mJerseyView.setBackgroundResource(R.drawable.blue_jersey);
    }
}
