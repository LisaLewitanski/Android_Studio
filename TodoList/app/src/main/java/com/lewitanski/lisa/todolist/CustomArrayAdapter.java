package com.lewitanski.lisa.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * <b>CustomArrayAdapter.</b>
 * <p>
 * This class.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class CustomArrayAdapter extends BaseAdapter	{
    private Context               context;
    private ArrayList<CustomItem> al_items;

    /**
     * The type View holder.
     */
    static class ViewHolder	{
        /**
         * The Task title.
         */
        public TextView	task_title;
        /**
         * The Task date.
         */
        public TextView	task_date;
        /**
         * The Task status.
         */
        public CheckBox task_status;
    }

    /**
     * The constructor for the class that takes in references to a context and
     * an array list.
     *
     * @param c  the c
     * @param al the al
     * @see CustomArrayAdapter#CustomArrayAdapter(Context, ArrayList) CustomArrayAdapter#CustomArrayAdapter(Context, ArrayList)CustomArrayAdapter#CustomArrayAdapter(Context, ArrayList)
     */
    public CustomArrayAdapter(Context c, ArrayList<CustomItem> al)	{
        context = c;
        al_items = al;
    }

    /**
     * Overridden method that will construct a View for the listview out of the
     * item at the given position
     * This method also allows the update of the task's status
     * @see CustomArrayAdapter#getView(int, View, ViewGroup)
     * @param position The position
     * @param convert_view The view
     *
     */
    public View	getView(final int position, View convert_view, final ViewGroup parent)	{
        final ViewHolder	holder;
        if (convert_view ==	null)	{
            holder = new ViewHolder();
            LayoutInflater	inflator =	(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view =	inflator.inflate(R.layout.custom_item_layout, parent, false);
            holder.task_date = (TextView) convert_view.findViewById(R.id.txtDate);
            holder.task_title = (TextView) convert_view.findViewById(R.id.txtTitle);
            holder.task_status = (CheckBox) convert_view.findViewById(R.id.cbxTerminated);
            convert_view.setTag(holder);
        }
        else {
            holder = (ViewHolder) convert_view.getTag();
        }

       holder.task_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestDBOpenHelper tdb = new TestDBOpenHelper(context, null, 1);
                int pos = (Integer) v.getTag();
                CustomItem item = al_items.get(pos);
                long result = tdb.updateTaskStatusSQL(String.valueOf(holder.task_status.isChecked()), item.getTaskID());

                if (result == -1)
                    Toast.makeText(context, "Failed to update task, please try again", Toast.LENGTH_LONG).show();
                else
                {
                 //   Toast.makeText(context, "Task successfully updated", Toast.LENGTH_LONG).show();
                    List<Hashtable<String, String>> al_values;
                    al_values = tdb.getAllValues();

                    al_items.clear();
                    for (Hashtable<String, String> elem : al_values)
                    {
                        CustomItem tmp = new CustomItem(elem.get("TITLE"), elem.get("DCREATE"), Boolean.parseBoolean(elem.get("ISDONE")), Integer.parseInt(elem.get("ID")));
                        al_items.add(tmp);
                    }
                    notifyDataSetChanged();
                }
            }
        });
        holder.task_title.setText(al_items.get(position).getTitle());
        holder.task_date.setText(al_items.get(position).getDate());
        holder.task_status.setChecked(al_items.get(position).getStatus());
        convert_view.findViewById(R.id.cbxTerminated).setTag(position);
        return convert_view;
    }

    /**
     * The method allows to get the size of the ListView.
     * @see CustomArrayAdapter#getCount()
     * @return The ListView's size
     */
    public int getCount()	{
        return al_items.size();
    }

    /**
     * The method allows to get the position.
     * @see CustomArrayAdapter#getItemId(int)
     * @param position it's the position
     * @return The position
     */
    public long getItemId(int position)	{
        return position;
    }

    /**
     * The method allows to get the item by a position.
     * @see CustomArrayAdapter#getItem(int)
     * @param position it's the position
     * @return The item
     */
    public Object	getItem(int position)	{
        return al_items.get(position);
    }
}