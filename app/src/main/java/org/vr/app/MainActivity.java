package org.vr.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.vr.app.activities.multi.MultiActivity;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setFitsSystemWindows(true);
        String[] strings = {"Based on activities"};
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, strings));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                Intent intent = new Intent(this, MultiActivity.class);
                startActivity(intent);
                break;
        }
    }

}

