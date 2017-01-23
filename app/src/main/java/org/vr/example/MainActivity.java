package org.vr.example;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.vr.example.activities.several.screen.home.SeveralActivitiesHome;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] strings = {"Based on activities"};
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, strings));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                Intent intent = new Intent();
                intent.setClass(this, SeveralActivitiesHome.class);
                startActivity(intent);
                break;
        }
    }

}

