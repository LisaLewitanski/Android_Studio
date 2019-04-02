package com.lewitanski.lisa.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * <b>TestDBOpenHelper.</b>
 * <p>
 * This class allows interactions with the database.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class TestDBOpenHelper extends SQLiteOpenHelper{
    /**
     * The Context .
     * @see TestDBOpenHelper#ctx
     *
     * The SQLiteDatabase sdb is the database.
     * @see TestDBOpenHelper#sdb
     *
     */
    private Context ctx;
    /**
     * The Sdb.
     */
    public SQLiteDatabase sdb;

    /**
     * The TestDBOpenHelper method allows to access to the "tasklist" database.
     *
     * @param context is the context
     * @param factory the factory
     * @param version is the database's version
     * @see TestDBOpenHelper#TestDBOpenHelper TestDBOpenHelper#TestDBOpenHelperTestDBOpenHelper#TestDBOpenHelper
     */
    TestDBOpenHelper(Context context, CursorFactory factory, int version) {
        super(context, "todolistdb", factory, version);

        ctx = context;
        sdb = this.getWritableDatabase();
    }

    /**
     * The onCreate method create the database.
     * @see TestDBOpenHelper#onCreate(SQLiteDatabase)
     * @param db is the database
     */
    public void onCreate(SQLiteDatabase	db)	{
        db.execSQL(create_table);
    }

    /**
     * The updateTaskSQL method update an existing task in the database.
     *
     * @param title       is the new title
     * @param description is the new description
     * @param date        is the new date
     * @param task_id     is the task's id
     * @return the long
     * @see TestDBOpenHelper#updateTaskSQL(String, String, String, int) TestDBOpenHelper#updateTaskSQL(String, String, String, int)TestDBOpenHelper#updateTaskSQL(String, String, String, int)
     */
    protected long updateTaskSQL(String title, String description, String date, int task_id) {
        ContentValues update = new ContentValues();
        update.put("TITLE", title);
        update.put("DESC", description);
        update.put("DCREATE", date);

        return sdb.update("tasklist",	update, "id=" + task_id, null);
    }

    /**
     * The updateTaskStatusSQL method update the task status in the database.
     *
     * @param status  is a string for know if a task is already done.               if it's already done the status will be true, else it will be false
     * @param task_id is the task's id
     * @return the long
     * @see TestDBOpenHelper#updateTaskStatusSQL(String, int) TestDBOpenHelper#updateTaskStatusSQL(String, int)TestDBOpenHelper#updateTaskStatusSQL(String, int)
     */
    protected long updateTaskStatusSQL(String status, int task_id) {
        ContentValues update = new ContentValues();
        update.put("ISDONE", status);

        return sdb.update("tasklist",	update, "id=" + task_id, null);
    }

    /**
     * The method delete a task in the database.
     *
     * @param task_id is the task's id
     * @return the long
     * @see TestDBOpenHelper#deleteTaskSQL(int) TestDBOpenHelper#deleteTaskSQL(int)TestDBOpenHelper#deleteTaskSQL(int)
     */
    protected long deleteTaskSQL(int task_id) {
        return sdb.delete("tasklist", "id=" + task_id, null);
    }

    /**
     * The method close the database.
     *
     * @see TestDBOpenHelper#closeSQL() TestDBOpenHelper#closeSQL()TestDBOpenHelper#closeSQL()
     */
    protected void closeSQL() {
        sdb.close();
    }

    /**
     * The method getAllValues get all values of each tasks in the database.
     *
     * @return all_values. It is a dictionary of all information(task id, title, description , status and the creation date of the task) of each task
     * @see TestDBOpenHelper#getAllValues() TestDBOpenHelper#getAllValues()TestDBOpenHelper#getAllValues()
     */
    public List<Hashtable<String, String>> getAllValues() {
        String request = "SELECT * FROM tasklist";
        Cursor cursor = sdb.rawQuery(request, null);
        List<Hashtable<String, String>> all_values = new ArrayList<Hashtable<String, String>>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Hashtable<String, String> tmp = new Hashtable<String, String>(5);
                tmp.put("ID", cursor.getString(cursor.getColumnIndex("ID")));
                tmp.put("TITLE", cursor.getString(cursor.getColumnIndex("TITLE")));
                tmp.put("DESC", cursor.getString(cursor.getColumnIndex("DESC")));
                tmp.put("ISDONE", cursor.getString(cursor.getColumnIndex("ISDONE")));
                tmp.put("DCREATE", cursor.getString(cursor.getColumnIndex("DCREATE")));
                all_values.add(tmp);
                cursor.moveToNext();
            }
        }
        return (all_values);
    }

    /**
     * The method onUpgrade delete and recreate the database.
     * @see TestDBOpenHelper#onUpgrade(SQLiteDatabase, int, int)
     * @param db            It is the database.
     * @param version_old   It is the old version
     * @param version_new   It is the new version
     */
    public void onUpgrade(SQLiteDatabase db, int version_old, int version_new)	{
        db.execSQL(drop_table);
        db.execSQL(create_table);
    }

    /**
     * The method getAllValues get all values of an existing task in the database.
     *
     * @param task_id The task's id
     * @return all_values. It is a dictionary of all information(task id, title, description and the creation date of the task) of an existing task
     * @see TestDBOpenHelper#deleteTaskSQL(int) TestDBOpenHelper#deleteTaskSQL(int)TestDBOpenHelper#deleteTaskSQL(int)
     */
    public Hashtable<String, String> getValueByID(int task_id) {
        String request = "SELECT * FROM tasklist WHERE id=" + task_id;
        Cursor cursor = sdb.rawQuery(request, null);
        Hashtable<String, String> all_values = new Hashtable<String, String>(3);

        if (cursor.moveToFirst()) {
            all_values.put("TITLE", cursor.getString(cursor.getColumnIndex("TITLE")));
            all_values.put("DESC", cursor.getString(cursor.getColumnIndex("DESC")));
            all_values.put("DCREATE", cursor.getString(cursor.getColumnIndex("DCREATE")));
        }
        return (all_values);
    }

    /**
     * This method insert some data in the database.
     *
     * @param cv This variable contains all data
     * @return the long
     * @see TestDBOpenHelper#insert(ContentValues) TestDBOpenHelper#insert(ContentValues)TestDBOpenHelper#insert(ContentValues)
     */
    public long insert(ContentValues cv) {
        return sdb.insert("tasklist", null, cv);
    }

    /**
     * The create_table is the request to create the database.
     * @see TestDBOpenHelper#create_table
     */
    private static final String create_table = "create table tasklist(TITLE string, DESC string, ISDONE string, DCREATE string, ID integer primary key autoincrement)";

    /**
     * The drop_table sdb is the request to delete the database.
     * @see TestDBOpenHelper#drop_table
     */
    private static final String	drop_table = "drop table tasklist";
}