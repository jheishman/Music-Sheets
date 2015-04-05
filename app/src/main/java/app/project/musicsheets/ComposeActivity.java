package app.project.musicsheets;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jacob on 4/5/2015.
 */
public class ComposeActivity extends ActionBarActivity implements View.OnDragListener, View.OnLongClickListener{

    ImageView imageView = new ImageView(this);
/*
    imageView.setOnLongClickListener(new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v){
            ClipData.Item item = new ClipData.Item((android.content.Intent) v.getTag());
            ClipData dragData = new ClipData(v.getTag(), ClipData.MIMETYPE_TEXT_PLAIN, item);

            // Instantiates the drag shadow builder.
            View.DragShadowBuilder myShadow = new MyDragShadowBuilder(imageView);
            return false;
        }
    });
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_activity);


    }



    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
