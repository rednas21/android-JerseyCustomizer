package com.sanderp.jerseycustomizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;


public class JerseyInfoActivity extends Activity implements View.OnClickListener {

    private String  playerName;
    private int     playerNumber;
    private boolean jerseyIsRed;

    private EditText        mEditName;
    private EditText        mEditNumber;
    private ToggleButton    mToggleColor;
    private Button          mCommitChanges;
    private Button          mCancelChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jersey_info);

        Intent data = this.getIntent();
        playerName = data.getStringExtra(JerseyDisplayActivity.PLAYER_NAME);
        playerNumber = data.getIntExtra(JerseyDisplayActivity.PLAYER_NUMBER, 0);
        jerseyIsRed = data.getBooleanExtra(JerseyDisplayActivity.RED_JERSEY, false);

        mEditName = (EditText) findViewById(R.id.editName);
        mEditName.setText(playerName);
        mEditNumber = (EditText) findViewById(R.id.editNumber);
        mEditNumber.setText(playerNumber + "");
        mToggleColor = (ToggleButton) findViewById(R.id.toggleColor);
        mToggleColor.setChecked(jerseyIsRed);
        mToggleColor.setOnClickListener(this);
        mCancelChanges = (Button) findViewById(R.id.cancelChanges);
        mCancelChanges.setOnClickListener(this);
        mCommitChanges = (Button) findViewById(R.id.commitChanges);
        mCommitChanges.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jersey_info, menu);
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
        Intent result = new Intent();
        switch (v.getId()) {
            case R.id.toggleColor:
                Log.d(JerseyDisplayActivity.LLOM, "You clicked the toggle button");
                mToggleColor.setChecked(mToggleColor.isChecked());
                break;
            case R.id.cancelChanges:
                Log.d(JerseyDisplayActivity.LLOM, "You clicked the cancel button");
                result.putExtra(JerseyDisplayActivity.PLAYER_NAME, playerName);
                result.putExtra(JerseyDisplayActivity.PLAYER_NUMBER, playerNumber);
                result.putExtra(JerseyDisplayActivity.RED_JERSEY, jerseyIsRed);
                setResult(JerseyDisplayActivity.RESULT_OK, result);
                finish();
                break;
            case R.id.commitChanges:
                Log.d(JerseyDisplayActivity.LLOM, "You clicked the commit button");
                result.putExtra(JerseyDisplayActivity.PLAYER_NAME, mEditName.getText().toString());
                int number = 0;
                try {
                    number = Integer.parseInt(mEditNumber.getText().toString());
                } catch (NumberFormatException e) {
                    Log.e(JerseyDisplayActivity.LLOM, "Invalid number format");
                }
                result.putExtra(JerseyDisplayActivity.PLAYER_NUMBER, number);
                result.putExtra(JerseyDisplayActivity.RED_JERSEY, mToggleColor.isChecked());
                setResult(JerseyDisplayActivity.RESULT_OK, result);
                finish();
                break;
        }
    }
}
