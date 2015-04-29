package app.project.musicsheets;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Jacob on 4/5/2015.
 */
public class ComposeActivity extends ActionBarActivity {

    private static final String LOGCAT = null;
    StringBuilder stringBuilder;
    FileOutputStream outputStream;
    int pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_activity);

        stringBuilder = new StringBuilder();
        String fileName = "testFile.txt";
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            //outputStream = getResources().openRawResource(R.raw.test);
            String pageNumber = "Page 1\n";
            pages++;
            outputStream.write(pageNumber.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set touch listeners for each image view
        // TODO: Fix so that each image view is progammatically assigned Touch Listener

        findViewById(R.id.imageView2).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView2).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView3).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView4).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView5).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView6).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView7).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView8).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView9).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView10).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView11).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView12).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView13).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView14).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView15).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView16).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView17).setOnLongClickListener(new myLongClickListener());
        findViewById(R.id.imageView18).setOnLongClickListener(new myLongClickListener());

        // Set drag listeners for each image view
        // TODO: Fix so that each image view is progammatically assigned Drag Listener

        findViewById(R.id.NoteContainer).setOnDragListener(new MyDragListener());
        findViewById(R.id.drawLayout).setOnDragListener(new MyDragListener());
    }

    class MyDragListener implements View.OnDragListener {

        //Drawable enterShape = getResources().getDrawable(R.drawable.quarter_note_up);
        //Drawable normalShape = getResources().getDrawable(R.drawable.quarter_note_down);

        @Override
        public boolean onDrag(View view, DragEvent event) {

            //LinearLayout drawLayout = (LinearLayout) v.findViewById(R.id.drawLayout);
            //drawLayout.addView(v.findViewById(R.id.imageView2));

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing

                    Log.i(LOGCAT, "Drag Action Started");


                    if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        Log.i(LOGCAT,"Data is acceptable");

                        return true;
                    }
                    else Log.i(LOGCAT,"Data unacceptable");
                    return false;

                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    //Dropped, reassign View to ViewGroup
                    if(view.getId() == R.id.drawLayout) {

                        ImageView draggedImageView = (ImageView) event.getLocalState();
                        ViewGroup draggedImageViewParentLayout = (ViewGroup) draggedImageView.getParent();
                        RelativeLayout targetLayout = (RelativeLayout) view;
                        ImageView copyImage = new ImageView(getBaseContext());
                        copyImage.setImageDrawable(draggedImageView.getDrawable());
                        copyImage.setOnLongClickListener(new myLongClickListener());
                        //draggedImageViewParentLayout.removeView(draggedImageView);

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.ALIGN_PARENT_LEFT);
                        params.leftMargin = (int) (event.getX() + copyImage.getWidth()/2);
                        params.topMargin  = (int) (event.getY() + copyImage.getHeight()/2);
                        Resources res = getResources();
                        if(draggedImageView.getParent() == findViewById(R.id.drawLayout)) {
                            draggedImageViewParentLayout.removeView(draggedImageView);
                        }

                        targetLayout.addView(copyImage,params);
                        AddImageViewToFile(copyImage,params);
                        sendViewToBack(copyImage);
                        copyImage.setVisibility(View.VISIBLE);
                        return true;
                    }
                    else Log.i(LOGCAT,"Invalid drop spot");
                    return false;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }
    }

    public void onSaveButtonClicked(View view)
    {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(LOGCAT,"File Closed");

        return;
    }

    public void onNewPageButtonClicked(View view)
    {

        stringBuilder.append("Page " + ++pages +"\n");
        try {
            outputStream.write(stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        RadioGroup buttons = (RadioGroup) findViewById(R.id.radioGroup);
        buttons.setVisibility(View.VISIBLE);

        RelativeLayout oldLayout = (RelativeLayout) findViewById(R.id.drawLayout);
        oldLayout.removeAllViews();

        Log.i(LOGCAT,stringBuilder.toString());
        stringBuilder.setLength(0);
    }
    public void onRadioButtonClicked(View view) {
        
        RadioGroup group = (RadioGroup) view.getParent();
        group.setEnabled(false);

        RelativeLayout drawableLayout = (RelativeLayout) findViewById(R.id.drawLayout);
        RelativeLayout.LayoutParams params =(new RelativeLayout.LayoutParams(1000,10));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.topMargin = 10;

        RelativeLayout.LayoutParams params2 =(new RelativeLayout.LayoutParams(1000,10));
        params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params2.topMargin = 50;

        RelativeLayout.LayoutParams params3 =(new RelativeLayout.LayoutParams(1000,10));
        params3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params3.topMargin = 90;

        RelativeLayout.LayoutParams params4 =(new RelativeLayout.LayoutParams(1000,10));
        params4.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params4.topMargin = 130;

        RelativeLayout.LayoutParams params5 =(new RelativeLayout.LayoutParams(1000,10));
        params5.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params5.topMargin = 170;

        RelativeLayout.LayoutParams params6 =(new RelativeLayout.LayoutParams(1000,10));
        params6.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params6.topMargin = 250;

        RelativeLayout.LayoutParams params7 =(new RelativeLayout.LayoutParams(1000,10));
        params7.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params7.topMargin = 290;

        RelativeLayout.LayoutParams params8 =(new RelativeLayout.LayoutParams(1000,10));
        params8.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params8.topMargin = 330;

        RelativeLayout.LayoutParams params9 =(new RelativeLayout.LayoutParams(1000,10));
        params9.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params9.topMargin = 370;

        RelativeLayout.LayoutParams params10 =(new RelativeLayout.LayoutParams(1000,10));
        params10.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params10.topMargin = 410;

        RelativeLayout.LayoutParams params11 =(new RelativeLayout.LayoutParams(1000,10));
        params11.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params11.topMargin = 490;

        RelativeLayout.LayoutParams params12 =(new RelativeLayout.LayoutParams(1000,10));
        params12.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params12.topMargin = 530;

        RelativeLayout.LayoutParams params13 =(new RelativeLayout.LayoutParams(1000,10));
        params13.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params13.topMargin = 570;

        RelativeLayout.LayoutParams params14 =(new RelativeLayout.LayoutParams(1000,10));
        params14.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params14.topMargin = 610;

        RelativeLayout.LayoutParams params15 =(new RelativeLayout.LayoutParams(1000,10));
        params15.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params15.topMargin = 650;

        RelativeLayout.LayoutParams params16 =(new RelativeLayout.LayoutParams(1000,10));
        params16.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params16.topMargin = 730;

        RelativeLayout.LayoutParams params17 =(new RelativeLayout.LayoutParams(1000,10));
        params17.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params17.topMargin = 770;

        RelativeLayout.LayoutParams params18 =(new RelativeLayout.LayoutParams(1000,10));
        params18.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params18.topMargin = 810;

        RelativeLayout.LayoutParams params19 =(new RelativeLayout.LayoutParams(1000,10));
        params19.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params19.topMargin = 850;

        RelativeLayout.LayoutParams params20 =(new RelativeLayout.LayoutParams(1000,10));
        params20.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params20.topMargin = 890;

        RelativeLayout.LayoutParams params21 =(new RelativeLayout.LayoutParams(1000,10));
        params21.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params21.topMargin = 970;

        RelativeLayout.LayoutParams params22 =(new RelativeLayout.LayoutParams(1000,10));
        params22.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params22.topMargin = 1010;

        RelativeLayout.LayoutParams params23 =(new RelativeLayout.LayoutParams(1000,10));
        params23.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params23.topMargin = 1050;

        RelativeLayout.LayoutParams params24 =(new RelativeLayout.LayoutParams(1000,10));
        params24.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params24.topMargin = 1090;

        RelativeLayout.LayoutParams params25 =(new RelativeLayout.LayoutParams(1000,10));
        params25.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params25.topMargin = 1130;

        boolean checked = ((RadioButton) view).isChecked();

        Log.i(LOGCAT, "Line 1 added.");
        View line = new View(this);
        line.setLayoutParams(params);
        line.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line, params);
        AddLineViewToFile(line,params);

        Log.i(LOGCAT, "Line 2 added.");
        View line2 = new View(this);
        line2.setLayoutParams(params2);
        line2.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line2, params2);
        AddLineViewToFile(line2,params2);
        
        Log.i(LOGCAT, "Line 3 added.");
        View line3 = new View(this);
        line3.setLayoutParams(params3);
        line3.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line3, params3);
        AddLineViewToFile(line3,params3);
        
        Log.i(LOGCAT, "Line 4 added.");
        View line4 = new View(this);
        line4.setLayoutParams(params2);
        line4.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line4, params4);
        AddLineViewToFile(line4,params4);
        
        Log.i(LOGCAT, "Line 5 added.");
        View line5 = new View(this);
        line5.setLayoutParams(params5);
        line5.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line5, params5);
        AddLineViewToFile(line5,params5);
        
        Log.i(LOGCAT, "Line 6 added.");
        View line6 = new View(this);
        line6.setLayoutParams(params);
        line6.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line6, params6);
        AddLineViewToFile(line6,params6);
        
        Log.i(LOGCAT, "Line 7 added.");
        View line7 = new View(this);
        line7.setLayoutParams(params7);
        line7.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line7, params7);
        AddLineViewToFile(line7,params7);
        
        Log.i(LOGCAT, "Line 8 added.");
        View line8 = new View(this);
        line8.setLayoutParams(params8);
        line8.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line8, params8);
        AddLineViewToFile(line8,params8);
        
        Log.i(LOGCAT, "Line 9 added.");
        View line9 = new View(this);
        line9.setLayoutParams(params9);
        line9.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line9, params9);
        AddLineViewToFile(line9,params9);
        
        Log.i(LOGCAT, "Line 10 added.");
        View line10 = new View(this);
        line10.setLayoutParams(params10);
        line10.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line10, params10);
        AddLineViewToFile(line10,params10);
        
        Log.i(LOGCAT, "Line 11 added.");
        View line11 = new View(this);
        line11.setLayoutParams(params11);
        line11.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line11, params11);
        AddLineViewToFile(line11,params11);
        
        Log.i(LOGCAT, "Line 12 added.");
        View line12 = new View(this);
        line12.setLayoutParams(params12);
        line12.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line12, params12);
        AddLineViewToFile(line12,params12);
        
        Log.i(LOGCAT, "Line 13 added.");
        View line13 = new View(this);
        line13.setLayoutParams(params13);
        line13.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line13, params13);
        AddLineViewToFile(line13,params13);
        
        Log.i(LOGCAT, "Line 14 added.");
        View line14 = new View(this);
        line14.setLayoutParams(params12);
        line14.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line14, params14);
        AddLineViewToFile(line14,params14);
        
        Log.i(LOGCAT, "Line 15 added.");
        View line15 = new View(this);
        line15.setLayoutParams(params15);
        line15.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line15, params15);
        AddLineViewToFile(line15,params15);
        
        Log.i(LOGCAT, "Line 16 added.");
        View line16 = new View(this);
        line16.setLayoutParams(params16);
        line16.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line16, params16);
        AddLineViewToFile(line16,params16);
        
        Log.i(LOGCAT, "Line 17 added.");
        View line17 = new View(this);
        line17.setLayoutParams(params17);
        line17.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line17, params17);
        AddLineViewToFile(line17,params17);
        
        Log.i(LOGCAT, "Line 18 added.");
        View line18 = new View(this);
        line18.setLayoutParams(params18);
        line18.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line18, params18);
        AddLineViewToFile(line18,params18);
        
        Log.i(LOGCAT, "Line 19 added.");
        View line19 = new View(this);
        line19.setLayoutParams(params19);
        line19.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line19, params19);
        AddLineViewToFile(line19,params19);
        
        Log.i(LOGCAT, "Line 20 added.");
        View line20 = new View(this);
        line20.setLayoutParams(params10);
        line20.setBackgroundColor(Color.rgb(0, 0, 0));
        drawableLayout.addView(line20, params20);
        AddLineViewToFile(line20,params20);
        
        switch(view.getId()) {

            case R.id.trebleButton:
                // Draw staff lines for treble
                if (checked)
                {
                    
                    Log.i(LOGCAT,"Line 21 added.");
                    View line21 = new View(this);
                    line21.setLayoutParams(params21);
                    line21.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line21,params21);
                    AddLineViewToFile(line21,params21);
                    
                    Log.i(LOGCAT,"Line 22 added.");
                    View line22 = new View(this);
                    line22.setLayoutParams(params22);
                    line22.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line22,params22);
                    AddLineViewToFile(line22,params22);
                    
                    Log.i(LOGCAT,"Line 23 added.");
                    View line23 = new View(this);
                    line23.setLayoutParams(params23);
                    line23.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line23,params23);
                    AddLineViewToFile(line23,params23);
                    
                    Log.i(LOGCAT,"Line 24 added.");
                    View line24 = new View(this);
                    line24.setLayoutParams(params22);
                    line24.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line24,params24);
                    AddLineViewToFile(line24,params24);
                    
                    Log.i(LOGCAT,"Line 25 added.");
                    View line25 = new View(this);
                    line25.setLayoutParams(params25);
                    line25.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line25,params25);
                    AddLineViewToFile(line25,params25);
                    
                    break;
                }

            case R.id.bassButton:
                if (checked) {
                    // Draw staff lines for bass                    

                    Log.i(LOGCAT, "Line 21 added.");
                    View line21 = new View(this);
                    line21.setLayoutParams(params21);
                    line21.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line21, params21);
                    AddLineViewToFile(line21,params21);
                    
                    Log.i(LOGCAT, "Line 22 added.");
                    View line22 = new View(this);
                    line22.setLayoutParams(params22);
                    line22.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line22, params22);
                    AddLineViewToFile(line22,params22);
                    
                    Log.i(LOGCAT, "Line 23 added.");
                    View line23 = new View(this);
                    line23.setLayoutParams(params23);
                    line23.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line23, params23);
                    AddLineViewToFile(line23,params23);
                    
                    Log.i(LOGCAT, "Line 24 added.");
                    View line24 = new View(this);
                    line24.setLayoutParams(params22);
                    line24.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line24, params24);
                    AddLineViewToFile(line24,params24);
                    
                    Log.i(LOGCAT, "Line 25 added.");
                    View line25 = new View(this);
                    line25.setLayoutParams(params25);
                    line25.setBackgroundColor(Color.rgb(0, 0, 0));
                    drawableLayout.addView(line25, params25);
                    AddLineViewToFile(line25,params25);
                    
                    break;
                }

            case R.id.dualButton:
                if (checked)
                {
                    // Draw staff lines for bass and treble
                    break;
                }
        }

        RadioGroup buttons = (RadioGroup) findViewById(R.id.radioGroup);
        buttons.setVisibility(View.INVISIBLE);

        return;
    }

    private final class myLongClickListener implements ImageView.OnLongClickListener {
        @Override
        public boolean onLongClick(View imageView) {
//            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
//            {
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
                imageView.startDrag(data,shadowBuilder,imageView,0);
                imageView.setVisibility(View.VISIBLE);
                return true;
//            else
//                return false;
        }
    }

    public static void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    private void AddLineViewToFile(View v,RelativeLayout.LayoutParams params)
    {
        stringBuilder.append("View,");
        stringBuilder.append(params.topMargin  + ",");
        stringBuilder.append(params.leftMargin + ",");
        stringBuilder.append("none\n");

        Log.i(LOGCAT,stringBuilder.toString());

        try {
            outputStream.write(stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringBuilder.setLength(0);
    }
    
    private void AddImageViewToFile(ImageView v,RelativeLayout.LayoutParams params)
    {
        stringBuilder.append("ImageView,");
        stringBuilder.append(params.topMargin  + ",");
        stringBuilder.append(params.leftMargin + ",");
        stringBuilder.append(v.getDrawable());

        Log.i(LOGCAT,stringBuilder.toString());

        try {
            outputStream.write(stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringBuilder.setLength(0);

    }
}
