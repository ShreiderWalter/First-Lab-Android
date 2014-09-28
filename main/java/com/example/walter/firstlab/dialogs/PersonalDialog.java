package com.example.walter.firstlab.dialogs;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.walter.firstlab.R;
import com.example.walter.firstlab.core.Student;

import java.util.ArrayList;

/**
 * Created by walter on 28.09.14.
 */
public class PersonalDialog extends Dialog {

    private Activity activity;
    private Student student;

    private TextView personalLabel, nameLabel, phoneLabel;
    private RelativeLayout mainFrame;
    private Button callButton;

    private ObjectAnimator animY;

    public PersonalDialog(Activity activity, Student student){
        super(activity);
        this.activity = activity;
        this.student = student;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_personal);

        personalLabel = (TextView) findViewById(R.id.personal_label);
        mainFrame = (RelativeLayout) findViewById(R.id.main_frame);
        callButton = (Button) findViewById(R.id.send_data);
        nameLabel = (TextView) findViewById(R.id.label_name);
        phoneLabel = (TextView) findViewById(R.id.label_phone_number);

        Display currentDisplay = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        currentDisplay.getSize(size);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/default.ttf");
        personalLabel.setTypeface(typeface);
        personalLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 30);
        ViewGroup.LayoutParams params = mainFrame.getLayoutParams();
        params.height = size.y / 2;
        mainFrame.setLayoutParams(params);

        callButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 50);

        nameLabel.setText(nameLabel.getText().toString() + " : " + student.getName());
        phoneLabel.setText(phoneLabel.getText().toString() + " : " + student.getPhoneNumber());

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animY = ObjectAnimator.ofFloat(callButton, "translationY", -100f, 0f);
                animY.setDuration(1500);
                animY.setInterpolator(new BounceInterpolator());
                animY.setRepeatCount(0);
                animY.start();

                String localNumber = student.getPhoneNumber();
                String buffer = "";
                for(int i = 0; i < localNumber.length(); ++i){
                    if(localNumber.charAt(i) != '(' || localNumber.charAt(i) != ')'){
                        buffer += localNumber.charAt(i);
                    }
                }
                String number = "tel:" + buffer.trim();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                activity.startActivity(callIntent);
            }
        });

    }
}
