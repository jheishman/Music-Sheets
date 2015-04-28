package app.project.musicsheets;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Jacob on 4/28/2015.
 */
public class DisplayActivity extends ActionBarActivity{

    FileInputStream inputStream;
    InputStreamReader streamReader;
    BufferedReader reader;

    protected void onCreate(Bundle savedInstanceState) {

        AssetManager assetManager = getAssets();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
        RelativeLayout drawScreen = (RelativeLayout) findViewById(R.id.drawScreen);
        String fileName = "/data/data/app.project.musicsheets/files/testFile.txt";
        try {
            AssetFileDescriptor fd = assetManager.openFd("testFile.txt");
            inputStream = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(inputStream));

        } catch(Exception e){
            e.printStackTrace();
        }

        byte[] buffer;
        //StringBuilder stringBuilder = new StringBuilder();
        String line;
        String[] tokens = new String[5];
        try {
            while((line = reader.readLine()) != null) {
                tokens = line.split(",");
                Log.i(null, tokens[0]);
                if(tokens[0] == "View")
                {
                    View newLine = new View(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.ALIGN_PARENT_LEFT);
                    params.topMargin = Integer.parseInt(tokens[1].toString());
                    params.leftMargin = Integer.parseInt(tokens[2].toString());

                    newLine.setLayoutParams(params);
                    newLine.setBackgroundColor(Color.rgb(0, 0, 0));

                    drawScreen.addView(newLine,params);
                }
                if(tokens[0] == "ImageView")
                {
                    ImageView newImage = new ImageView(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.ALIGN_PARENT_LEFT);
                    params.topMargin = Integer.parseInt(tokens[1].toString());
                    params.leftMargin = Integer.parseInt(tokens[2].toString());


                    drawScreen.addView(newImage,params);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
