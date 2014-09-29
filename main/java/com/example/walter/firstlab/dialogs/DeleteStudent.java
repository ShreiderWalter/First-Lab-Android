package com.example.walter.firstlab.dialogs;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.walter.firstlab.R;
import com.example.walter.firstlab.core.Student;
import com.example.walter.firstlab.database.DatabaseHandler;

import java.util.ArrayList;

/**
 * Created by walter on 29.09.14.
 */
public class DeleteStudent extends Dialog {

    private Activity activity;
    private Student student;
    private String item;
    private int position;

    private TextView personalLabel;
    private RelativeLayout mainFrame;
    private Button yesButton, noButton;
    private DatabaseHandler databaseHandler;

    private ObjectAnimator animY;
    private ArrayList<String> list;
    private ArrayAdapter adapter;
    private AdapterView parent;

    public DeleteStudent(Activity activity, ArrayList<String> list, ArrayAdapter adapter, Student student, AdapterView<?> parent, int position){
        super(activity);
        this.activity = activity;
        databaseHandler = new DatabaseHandler(activity);

        this.list = list;
        this.adapter = adapter;
        this.student = student;
        this.position = position;
        this.parent = parent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete);

        personalLabel = (TextView) findViewById(R.id.personal_label);
        mainFrame = (RelativeLayout) findViewById(R.id.main_frame);
        yesButton = (Button) findViewById(R.id.yes);
        noButton = (Button) findViewById(R.id.no);

        Display currentDisplay = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        currentDisplay.getSize(size);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/default.ttf");
        personalLabel.setTypeface(typeface);
        personalLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 35);

        yesButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 50);
        noButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 50);


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animY = ObjectAnimator.ofFloat(yesButton, "translationY", -100f, 0f);
                animY.setDuration(1500);
                animY.setInterpolator(new BounceInterpolator());
                animY.setRepeatCount(0);
                animY.start();

                final String item = (String) parent.getItemAtPosition(position);
                list.remove(item);
                adapter.notifyDataSetChanged();
                databaseHandler.deleteStudent(student);
                dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animY = ObjectAnimator.ofFloat(yesButton, "translationY", -100f, 0f);
                animY.setDuration(1500);
                animY.setInterpolator(new BounceInterpolator());
                animY.setRepeatCount(0);
                animY.start();
                dismiss();
            }
        });

    }
}
