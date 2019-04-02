package com.lewitanski.lisa.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>CustomItem.</b>
 * <p>
 * This class allows access to an existing task.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class CustomItem {
    /**
     * The title is the title of the task which we want to access.
     * @see CustomItem#title
     *
     * The date is the date of the task which we want to access.
     * @see CustomItem#date
     *
     * The done is the checkbox of the task which we want to access.
     * @see CustomItem#done
     *
     * The task_id is the id of the task which we want to access.
     * @see CustomItem#task_id
     *
     */
    private String	title;
    private String	date;
    private boolean done;
    private int task_id;

    /**
     * The constructor allows to create an item with the title, the description and the date set by the user
     *
     * @param Title    The title
     * @param Date     The date
     * @param Checkbox The boolean for know if the task is already done
     * @param Id       The task's iD
     * @see CustomItem#CustomItem(String, String, boolean, int) CustomItem#CustomItem(String, String, boolean, int)CustomItem#CustomItem(String, String, boolean, int)
     */
    CustomItem(String Title, String Date, boolean Checkbox, int Id)	{
        this.title =  Title;
        this.date = Date;
        this.done = Checkbox;
        this.task_id = Id;
    }

    /**
     * The method returns the Title.
     *
     * @return The title
     * @see CustomItem#getTitle() CustomItem#getTitle()CustomItem#getTitle()
     */
    public String   getTitle() {
        return this.title;
    }

    /**
     * The method returns the Date.
     *
     * @return The date
     * @see CustomItem#getDate() CustomItem#getDate()CustomItem#getDate()
     */
    public String	getDate()	{

        return this.date;
    }

    /**
     * The method returns the Status.
     * If a task is already done the status will be true, else it will be false.
     *
     * @return The task's status
     * @see CustomItem#getStatus() CustomItem#getStatus()CustomItem#getStatus()
     */
    public boolean	getStatus()	{

        return this.done;
    }

    /**
     * The method returns the task's id.
     *
     * @return The task id
     * @see CustomItem#getTaskID() CustomItem#getTaskID()CustomItem#getTaskID()
     */
    public int	    getTaskID() {

        return this.task_id;
    }
}
