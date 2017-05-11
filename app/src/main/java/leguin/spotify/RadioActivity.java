package leguin.spotify;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RadioActivity extends MainActivity {

    LinearLayout dynamicContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_matching);


        //dynamically include the  current activity      layout into  baseActivity layout.now all the view of baseactivity is   accessible in current activity.
        dynamicContent = (LinearLayout)  findViewById(R.id.dynamicContent);
        //container= (ConstraintLayout) findViewById(R.id.container);
        View wizard = getLayoutInflater().inflate(R.layout.activity_radio, null);
        dynamicContent.addView(wizard);


        //get the reference of RadioGroup.

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.radioButton_radio);

        // Change the corresponding icon and text color on nav button click.

        // rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_matching_clicked, 0,0);
        rb.setTextColor(Color.parseColor("#3F51B5"));
    }
}
