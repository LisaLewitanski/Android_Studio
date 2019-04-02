package com.lewitanski.lisa.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * <b>EditActivity.</b>
 * <p>
 * This class allows to update an existing task.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class EditActivity extends AppCompatActivity {
    /**
     * The TestDBOpenHelper is a class which allows to interact with the database.
     * @see EditActivity#tdb
     *
     * The EditText et_tile is a viariable which contains the title in the edit layout.
     * @see EditActivity#et_title
     *
     * The EditText et_description is a viariable which contains the description in the edit layout.
     * @see EditActivity#et_description
     *
     * The DatePicker dp_date is a viariable which contains the date in the edit layout.
     * @see  EditActivity#dp_date
     *
     * The elemID contains the task's id.
     * @see EditActivity#elemID
     * @param savedInstanceState Is the instance state

     */
    private TestDBOpenHelper tdb;
    private EditText et_title;
    private EditText et_description;
    private DatePicker dp_date;
    private int elemID;

    /**
     * The onCreate method get the content in the database and initialise the display.
     * @see EditActivity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tdb = new TestDBOpenHelper(this, null, 1);
        Bundle b = getIntent().getExtras();
        elemID = b.getInt("elemID");

        et_title = (EditText) findViewById(R.id.txtTitle);
        et_description = (EditText) findViewById(R.id.txtDecription);
        dp_date = (DatePicker) findViewById(R.id.dtDate);

        Hashtable<String, String> values = tdb.getValueByID(elemID);
        et_title.setText(values.get("TITLE"));
        et_description.setText(values.get("DESC"));

        String date = values.get("DCREATE");
        String[] dates = null;

        if (date != null)
            dates = date.split("/");

        if (dates != null)
            dp_date.init(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]), null);
        setTitle("Edit task " + et_title.getText() + " :");
    }

    /**
     * The getTitle allows to get the title in the edit layout.
     *
     * @param v Is the view
     * @return The title
     * @see EditActivity#getTile(View) EditActivity#getTile(View)EditActivity#getTile(View)
     */
    protected String getTile(View v) {
        String title = et_title.getText().toString();
        return (title);
    }

    /**
     * The getDescription allows to get the description in the edit layout.
     *
     * @param v Is the view
     * @return The description
     * @see EditActivity#getDescription(View) EditActivity#getDescription(View)EditActivity#getDescription(View)
     */
    protected String getDescription(View v) {
        String description = et_description.getText().toString();
        return (description);
    }

    /**
     * The  getDate allows to get the date in the edit layout.
     *
     * @param v Is the view
     * @return The date
     * @see EditActivity#getDate(View) EditActivity#getDate(View)EditActivity#getDate(View)
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
     * The SaveClickHandler allows to update the database with the user's modifications.
     * If the database is correctly updated, the code 'RESULT_OK' is sent to the MainActivity.
     * Else the code 'RESULT_CANCELED' is sent to the activity.
     *
     * @param v Is the view
     * @see EditActivity#SaveClickHandler(View) EditActivity#SaveClickHandler(View)EditActivity#SaveClickHandler(View)
     */
    protected void SaveClickHandler(View v) {
        long result = tdb.updateTaskSQL(getTile(v), getDescription(v), getDate(v), elemID);
        tdb.closeSQL();
        Intent returnIntent = new Intent();

        if (result == -1) {
            Toast.makeText(this.getApplicationContext(), "Failed to update task, please try again", Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        else {
            Toast.makeText(this.getApplicationContext(), "Task successfully updated", Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_OK, returnIntent);
        }
        this.finish();
    }

    /**
     * The CancelClickHandler allows to close the database and return the 'RESULT_CANCELED' error
     * code in the MainActivity.
     *
     * @param v Is the view
     * @see EditActivity#CancelClickHandler(View) EditActivity#CancelClickHandler(View)EditActivity#CancelClickHandler(View)
     */
    protected void CancelClickHandler(View v) {
        tdb.closeSQL();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        this.finish();
    }

    /**
     * The DeleteClickHandler allows to delete a task.
     * It close the database and return.
     * If the method could delete the task, the code 'RESULT_OK' is return to the MainActivity.
     * Else, the code 'RESULT_CANCELED' is return.
     *
     * @param v Is the view
     * @see EditActivity#DeleteClickHandler(View) EditActivity#DeleteClickHandler(View)EditActivity#DeleteClickHandler(View)
     */
    protected void DeleteClickHandler(View v) {
        long result = tdb.deleteTaskSQL(elemID);
        tdb.closeSQL();
        Intent returnIntent = new Intent();

        if (result == -1) {
            setResult(Activity.RESULT_CANCELED, returnIntent);
            Toast.makeText(this.getApplicationContext(), "Failed to delete task, please try again", Toast.LENGTH_LONG).show();
        }
        else {
            setResult(Activity.RESULT_OK, returnIntent);
            Toast.makeText(this.getApplicationContext(), "Task successfully updated", Toast.LENGTH_LONG).show();
        }
        this.finish();
    }
}
