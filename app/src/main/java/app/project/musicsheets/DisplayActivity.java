package app.project.musicsheets;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
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
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jacob on 4/28/2015.
 */
public class DisplayActivity extends ActionBarActivity{

    int base = 45;
    InputStreamReader streamReader;
    BufferedReader reader;
    int numViews;
    int numImageViews;

    protected void onCreate(Bundle savedInstanceState) {

        numViews = 0;
        numImageViews = 0;

        AssetManager assetManager = getAssets();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
        RelativeLayout drawScreen = (RelativeLayout) findViewById(R.id.drawScreen);
        String fileName = "testFile.txt";
        try {
            //AssetFileDescriptor fd = assetManager.openFd(fileName);
            InputStream is = getResources().openRawResource(R.raw.test);
            reader = new BufferedReader(new InputStreamReader(is));

        } catch(Exception e){
            e.printStackTrace();
        }

        byte[] buffer;
        //StringBuilder stringBuilder = new StringBuilder();
        String line;
        String[] tokens = new String[5];
        View[] views = new View[30];
        ImageView[] imageViews = new ImageView[50];
        RelativeLayout.LayoutParams[] viewParams = new RelativeLayout.LayoutParams[30];
        RelativeLayout.LayoutParams[] imageParams = new RelativeLayout.LayoutParams[50];

        try {
            while((line = reader.readLine()) != null) {
                tokens = line.split(",");
                Log.i(null, tokens[0]);
                if(tokens[0].compareTo("View") == 0)
                {
                    Log.i(null,"Here is a view");
                    views[numViews] = new View(this);
                    viewParams[numViews] = new RelativeLayout.LayoutParams(1200,10);
                    viewParams[numViews].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT);
                    viewParams[numViews].topMargin = Integer.parseInt(tokens[1].toString());
                    viewParams[numViews].leftMargin = Integer.parseInt(tokens[2].toString());

                    Log.i(null,String.valueOf(viewParams[numViews].topMargin));
                    Log.i(null,String.valueOf(viewParams[numViews].leftMargin));
                    views[numViews].setLayoutParams(viewParams[numViews]);
                    views[numViews].setBackgroundColor(Color.rgb(0, 0, 0));

                    drawScreen.addView(views[numViews],viewParams[numViews]);
                    numViews++;
                }
                if(tokens[0].compareTo("ImageView") == 0)
                {
                    Log.i(null,"Here is an image");
                    imageViews[numImageViews] = new ImageView(this);
                    Log.i(null,tokens[3]);
                    imageParams[numImageViews] = new RelativeLayout.LayoutParams(60,120);
                    imageParams[numImageViews].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT);
                    imageParams[numImageViews].topMargin = Integer.parseInt(tokens[1].toString());
                    //imageParams[numImageViews].leftMargin = Integer.parseInt(tokens[1].toString());
                    imageParams[numImageViews].leftMargin = base;
                    base += 80;
                    imageViews[numImageViews].setImageResource(R.drawable.quarter_note_down);
                    drawScreen.addView(imageViews[numImageViews],imageParams[numImageViews]);
                    numImageViews++;
                }
                drawScreen.removeAllViews();
                for(int i = 0; i < numViews; i++)
                {
                    drawScreen.addView(views[i],viewParams[i]);
                }
                for(int i = 0; i < numImageViews; i++)
                {
                    drawScreen.addView(imageViews[i],imageParams[i]);
                    sendViewToBack(imageViews[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }
}
