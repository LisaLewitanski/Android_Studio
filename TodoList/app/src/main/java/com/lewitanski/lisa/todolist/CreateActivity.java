package com.lewitanski.lisa.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * <b>CreateActivity.</b>
 * <p>
 * This class creates a new task.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class CreateActivity extends AppCompatActivity {
    /**
     * The TestDBOpenHelper is a class which allows to interact with the database.
     * @see CreateActivity#tdb
     *
     * The EditText et_tile is a viariable which contains the title in the edit layout.
     * @see CreateActivity#et_title
     *
     * The EditText et_description is a viariable which contains the description in the edit layout.
     * @see CreateActivity#et_description
     *
     * The DatePicker dp_date is a viariable which contains the date in the edit layout.
     * @see  CreateActivity#dp_date
     *
     */
    private TestDBOpenHelper tdb;
    private EditText et_title;
    private EditText et_description;
    private DatePicker dp_date;

    /**
     * The onCreate method allows initialise parameters and display.
     * @see CreateActivity#onCreate(Bundle)
     * @param savedInstanceState Is the instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        tdb = new TestDBOpenHelper(this,	null,	1);

        et_title = (EditText) findViewById(R.id.txtTitle);
        et_description = (EditText) findViewById(R.id.txtDecription);
        dp_date = (DatePicker) findViewById(R.id.dtDate);
        setTitle("Create new task :");
    }

    /**
     * The getTitle allows to get the title in the create layout.
     *
     * @param v Is the view
     * @return The title
     * @see CreateActivity#getTile(View) CreateActivity#getTile(View)CreateActivity#getTile(View)
     */
    protected String getTile(View v) {
        String title = et_title.getText().toString();
        return (title);
    }

    /**
     * The getDescription allows to get the title in the create layout.
     *
     * @param v Is the view
     * @return The description
     * @see CreateActivity#getDescription(View) CreateActivity#getDescription(View)CreateActivity#getDescription(View)
     */
    protected String getDescription(View v) {
        String description = et_description.getText().toString();
        return (description);
    }

    /**
     * The getDate allows to get the date in the create layout.
     *
     * @param v Is the view
     * @return The date
     * @see CreateActivity#getDate(View) CreateActivity#getDate(View)CreateActivity#getDate(View)
     */
    protected String getDate(View v) {
        String date = "";
        date = date + String.valueOf(dp_date.getDayOfMonth());
        date = date + '/';
        date = date + String.valueOf(dp_date.getMonth());
        date = date + '/';
        date = date + String.valueOf(dp_date.getYear());
        return (date);
    }

    /**
     * The SaveClickHandler allows to create the new task with the information which are set in
     * create layout.
     * If the task is correctly set in the database, the code 'RESULT_OK' is returned
     * in the MainActivity.
     * Else the code 'RESULT_CANCELED' is return.
     *
     * @param v Is the view
     * @see CreateActivity#SaveClickHandler(View) CreateActivity#SaveClickHandler(View)CreateActivity#SaveClickHandler(View)
     */
    protected void SaveClickHandler(View v) {
        ContentValues cv = new ContentValues();
        cv.put("TITLE", getTile(v));
        cv.put("DESC", getDescription(v));
        cv.put("DCREATE", getDate(v));
        cv.put("ISDONE", "false");

        long result = tdb.insert(cv);
        Intent returnIntent = new Intent();

        if (result == -1) {
            Toast.makeText(this.getApplicationContext(), "Failed to create task, please try again", Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        else {
            Toast.makeText(this.getApplicationContext(), "Task successfully added", Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_OK, returnIntent);
        }
        tdb.closeSQL();
        this.finish();
    }

    /**
     * The CancelClickHandler allows to close the database.
     * The code 'RESULT_CANCELED' is return to the MainActivity.
     *
     * @param v the v
     * @see CreateActivity#CancelClickHandler(View) CreateActivity#CancelClickHandler(View)CreateActivity#CancelClickHandler(View)
     */
    protected void CancelClickHandler(View v) {
        tdb.closeSQL();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        this.finish();
    }

}
