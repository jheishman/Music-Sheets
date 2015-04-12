package app.project.musicsheets;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    Button button2, button, button3, button4 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2 = (Button) findViewById(R.id.button2);
        button = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button2);
        button4 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if(v == button2) {
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivity(intent);
        }
        if(v==button3)
        {
            Intent intent2 = new Intent(this, SettingsActivity.class);
            startActivity(intent2);
        }
        if(v==button4)
        {
            Intent intent3 = new Intent(this, OtherActivity.class);
            startActivity(intent3);
        }

    }
}
