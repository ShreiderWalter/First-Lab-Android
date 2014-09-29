package com.example.walter.firstlab;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends Activity {
    private TextView labLabel, labelName, labelMail, labelPhoneNumber;
    private EditText fieldName, fieldMail, fieldPhoneNumber;
    private AlphaAnimation animation;
    private Button sendButton, goButton;
    private ArrayList<Student> list;
    private Intent goIntent;
    private ObjectAnimator animY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        labLabel = (TextView) findViewById(R.id.first_lab_label);
        labelName = (TextView) findViewById(R.id.label_name);
        labelMail = (TextView) findViewById(R.id.label_mail);
        labelPhoneNumber = (TextView) findViewById(R.id.label_phone_number);

        fieldName = (EditText) findViewById(R.id.field_name);
        fieldMail = (EditText) findViewById(R.id.field_mail);
        fieldPhoneNumber = (EditText) findViewById(R.id.field_phone_number);

        sendButton = (Button) findViewById(R.id.add_student);
        goButton = (Button) findViewById(R.id.send_data);
        goIntent = new Intent(MainActivity.this, CallActivity.class);

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        currentDisplay.getSize(size);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/default.ttf");
        labLabel.setTypeface(typeface);

        ViewGroup.LayoutParams params = fieldName.getLayoutParams();
        params.width = size.x / 2;
        fieldName.setLayoutParams(params);

        params = fieldMail.getLayoutParams();
        params.width = size.x / 2;
        fieldMail.setLayoutParams(params);

        params = fieldPhoneNumber.getLayoutParams();
        params.width = size.x / 2;
        fieldPhoneNumber.setLayoutParams(params);

        RelativeLayout.LayoutParams marginParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        marginParams.setMargins(0, size.y / 4, 0, 0);
        marginParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        labLabel.setLayoutParams(marginParams);
        labLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 30);

        animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(3000);
        labLabel.setAnimation(animation);

        sendButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 50);
        goButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y/ 54);

        list = new ArrayList<Student>();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldName.getText().toString().equals("") || fieldMail.getText().toString().equals("") || fieldPhoneNumber.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Don't leave empty bars!", Toast.LENGTH_SHORT).show();
                } else if(!fieldName.getText().toString().matches("[^0-9]{2,}")){
                    Toast.makeText(MainActivity.this, "Name of student is wrong format!", Toast.LENGTH_SHORT).show();
                } else if(!fieldMail.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
                    Toast.makeText(MainActivity.this, "Mail of student is wrong format!", Toast.LENGTH_SHORT).show();
                } else if(!fieldPhoneNumber.getText().toString().matches("\\+375\\([0-9]{2}\\)[0-9]{7}")){
                    Toast.makeText(MainActivity.this, "Phone number of student is wrong format!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean flag = true;
                    for(Student student : list){
                        if(new Student(fieldName.getText().toString(), fieldMail.getText().toString(), fieldPhoneNumber.getText().toString()).equals(student)){
                            Toast.makeText(MainActivity.this, "Such student already exists!", Toast.LENGTH_SHORT).show();
                            flag = false;
                        }
                    }
                    if(flag) {
                        list.add(new Student(fieldName.getText().toString(), fieldMail.getText().toString(), fieldPhoneNumber.getText().toString()));
                    }
                }
                animY = ObjectAnimator.ofFloat(sendButton, "translationY", -100f, 0f);
                animY.setDuration(1500);
                animY.setInterpolator(new BounceInterpolator());
                animY.setRepeatCount(0);
                animY.start();
                Log.e("MY CLASS ADDED : ", Arrays.deepToString(list.toArray()));
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animY = ObjectAnimator.ofFloat(goButton, "translationY", -100f, 0f);
                animY.setDuration(1500);
                animY.setInterpolator(new BounceInterpolator());
                animY.setRepeatCount(0);
                animY.start();
                goIntent.putExtra("Students", list);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(goIntent);
                        //finish();
                    }
                }, 1700);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK : finish(); break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
