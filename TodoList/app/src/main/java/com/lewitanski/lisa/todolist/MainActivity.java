package com.lewitanski.lisa.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * <b>MainActivity.</b>
 * <p>
 * This class creates and updates the listview containing the user's tasks.
 * This is the main task which redirect the user either on the create or edit screen.
 * If there is no problem, the launched activities return result_ok to the main to refresh the listview.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * The CustomArrayAdapter allows to link the tasks list to the listview.
     * @see MainActivity#caa
     *
     * The Arraylist of CustomItem is the task list.
     * @see MainActivity#al_items
     *
     * The ListView allows to print all task in the main page.
     * @see MainActivity#lv_mainlist
     *
     * The TestDBOpenHelper is a class which allows to interact with the database.
     * @see MainActivity#tdb
     *
     */
    private CustomArrayAdapter      caa;
    private ArrayList<CustomItem>   al_items;
    private ListView                lv_mainlist;
    private TestDBOpenHelper        tdb;

    /**
     * The onCreate method allows to display the activity main's layout.
     * It's also allows to display the button to create a new task and launch the
     * activity when the user clicks.
     *
     * If the listView is not null, the list of the task is displayed on the main activity.
     * Then, if a user click a task, the method redirect the user to update the seleted task.
     * @see MainActivity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Task list :");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createActivity = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(createActivity, 1);
            }
        });

        tdb = new TestDBOpenHelper(this, null, 1);
        lv_mainlist = (ListView) findViewById(R.id.lv_mainlist);

        List<Hashtable<String, String>> al_values;
        al_values = tdb.getAllValues();

        al_items = new ArrayList<CustomItem>();
        for (Hashtable<String, String> elem : al_values)
        {
            CustomItem tmp = new CustomItem(elem.get("TITLE"), elem.get("DCREATE"), Boolean.parseBoolean(elem.get("ISDONE")), Integer.parseInt(elem.get("ID")));
            al_items.add(tmp);
        }

        caa = new CustomArrayAdapter(this, al_items);
        lv_mainlist.setAdapter(caa);

        lv_mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int pos, long id) {
                    Intent editActivity = new Intent(MainActivity.this, EditActivity.class);
                    editActivity.putExtra("elemID", al_items.get(pos).getTaskID());
                    startActivityForResult(editActivity, 1);
            }
        });
    }

    /**
     * The updateList method allows to update the listview when the user create or modify a task.
     * @see MainActivity#updateList()
     */
    private void updateList() {
        List<Hashtable<String, String>> al_values;
        al_values = tdb.getAllValues();

        al_items.clear();
        for (Hashtable<String, String> elem : al_values)
        {
            CustomItem tmp = new CustomItem(elem.get("TITLE"), elem.get("DCREATE"), Boolean.parseBoolean(elem.get("ISDONE")), Integer.parseInt(elem.get("ID")));
            al_items.add(tmp);
        }
        caa.notifyDataSetChanged();
    }

    /**
     * This method allow to know if a problem occurred or update the listview.
     * If nothing wrong happened, the method return result_ok to the main to refresh the listview.
     * @see MainActivity#updateList()
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK)
                updateList();
        }
    }
}