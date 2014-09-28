package com.example.walter.firstlab;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walter.firstlab.core.Student;
import com.example.walter.firstlab.dialogs.CallDialog;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by walter on 28.09.14.
 */
public class CallActivity extends Activity {
    private TextView labCaller, labelName;
    private EditText fieldName;
    private Button find;
    private AlphaAnimation animation;
    private ArrayList<Student> list;
    private ArrayList<Student> matchedList;
    private ObjectAnimator animY;
    private CallDialog callDialog;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        labCaller = (TextView) findViewById(R.id.lab_caller);
        labelName = (TextView) findViewById(R.id.label_name);
        fieldName = (EditText) findViewById(R.id.field_name);
        find = (Button) findViewById(R.id.find_student);

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        currentDisplay.getSize(size);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/default.ttf");
        labCaller.setTypeface(typeface);

        ViewGroup.LayoutParams params = fieldName.getLayoutParams();
        params.width = size.x / 2;
        fieldName.setLayoutParams(params);

        RelativeLayout.LayoutParams marginParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        marginParams.setMargins(0, size.y / 4, 0, 0);
        marginParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        labCaller.setLayoutParams(marginParams);
        labCaller.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 30);

        animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(3500);
        labCaller.setAnimation(animation);
        find.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 50);

        Bundle b = getIntent().getExtras();
        list = b.getParcelableArrayList("Students");

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animY = ObjectAnimator.ofFloat(find, "translationY", -100f, 0f);
                animY.setDuration(1500);
                animY.setInterpolator(new BounceInterpolator());
                animY.setRepeatCount(0);
                animY.start();
                if(!fieldName.getText().toString().equals("")){
                    if(ifFound(fieldName.getText().toString())) {
                        callDialog = new CallDialog(CallActivity.this, matchedList);
                        callDialog.show();
                        Log.e("MATCHED STUDENTS : ", Arrays.deepToString(matchedList.toArray()));
                    } else {
                        Toast.makeText(CallActivity.this, "No such student found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CallActivity.this, "Fill field of search, before taping a button!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean ifFound(String name){
        matchedList = new ArrayList<Student>();
        for(Student student : list){
            if(name.equals(student.getName())){
                matchedList.add(student);
            }
        }
        return matchedList.size() != 0;
    }
}
