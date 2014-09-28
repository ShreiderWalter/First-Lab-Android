package com.example.walter.firstlab.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.walter.firstlab.R;
import com.example.walter.firstlab.core.Student;

import java.util.ArrayList;

/**
 * Created by walter on 28.09.14.
 */
public class CallDialog extends Dialog{

    private Activity activity;
    private ListView listView;
    private ArrayList<Student> list;
    private PersonalDialog personalDialog;

    public CallDialog(Activity activity, ArrayList<Student> list){
        super(activity);
        this.activity = activity;
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_call);
        listView = (ListView) findViewById(R.id.list);
        final ArrayList<String> listNames = new ArrayList<String>();
        for (Student student : list) {
            listNames.add(student.getName() + "'s handynummer : " + student.getPhoneNumber() );
        }
        final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, listNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        listNames.remove(item);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                        dismiss();
                        personalDialog = new PersonalDialog(activity, list.get(position));
                        personalDialog.show();
                    }
                });
            }

        });
    }

}
