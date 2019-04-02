package lewitanski.lisa.com.assignment3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * <b>MainActivity</b>
 * <p>
 *  This class creates a page containing a Start and Stop button.
 *  It's allows the user to record his trip.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    /**
     * This code allows to get the permission for the location.
     * @see MainActivity#TAG_CODE_PERMISSION_LOCATION
     *
     * This variable allows to get the LocationManager.
     * @see MainActivity#lm
     *
     * Data is an ArrayList of ActivityData. ActivityData contains the location and the time.
     * @see MainActivity#data
     *
     * It's the timer witch I launch at the beginning of the Activity.
     * @see MainActivity#chrono
     *
     * The ctx is the context.
     * @see MainActivity#ctx
     *
     * Directory is the forlder wich I want to create.
     * @see MainActivity#directory
     *
     * Directory is the file name wich I want to create.
     * @see MainActivity#filename
     *
     * Directory is the file wich I want to create.
     * @see MainActivity#file
     *
     * isStart is to know if the record is started
     * @see MainActivity#isStart
     *
     */
    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    private LocationManager lm;
    private ArrayList<ActivityData>  data = new ArrayList<ActivityData>();
    private long                chrono;
    private Context             ctx;
    private File                directory = null;
    private String              filename;
    private File                file = null;
    private boolean             isStart = false;

    /**
     * The onCreate method allows to create the GPX folder and the file to save the values of the Gps.
     * Then it starts to recording the gps when the user clicks on the 'Start' button.
     * To finish, when the user clicks on the 'Stop' button, the file will be saved with all
     * values and the next activity will be launch with all the values ​​that have been recovered
     * by the Gps.
     *
     * @see MainActivity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = getApplicationContext();
        // Create the folder
        createFolder();

        final ProgressBar prog = (ProgressBar) findViewById(R.id.progress);
        prog.setVisibility(View.GONE);
        lm = (LocationManager) getSystemService(ctx.LOCATION_SERVICE);

        // button to start recording gps
        FloatingActionButton start = (FloatingActionButton) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Values initialization
                data.clear();
                isStart = true;

                if (directory == null)
                    // Create the folder if it doesn't exist
                    createFolder();

                //create the name of the file
                filename = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()).replace(" ", "_");

                // create the file
                file = new File(directory, filename + ".gpx");
                // Launch the chronometer
                chrono = SystemClock.elapsedRealtime();
                // Begins to follow the position of the user
                addLocationListener();
                // Add the visibility of the loading bar
                prog.setVisibility(View.VISIBLE);
                prog.setIndeterminate(true);
            }
        });

        //button to stop and go to the second activity
        FloatingActionButton stop = (FloatingActionButton) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {
                    // Write in the file
                    try {
                        GPXFileWriter.writeGpxFile("track", data, file);
                    } catch (java.io.IOException e) {
                        Log.e("save to file", e.getLocalizedMessage());
                    }

                    // Pass to the next activity
                    prog.setVisibility(View.GONE);

                    // Set all values in the intent
                    Intent intent = new Intent(MainActivity.this, Gps.class);
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("data", data);
                    intent.putExtra("PARC", args);

                    // Start the activity
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**
     * This method allows to inflate the menu. It adds items to the action bar if it is present.
     *
     * @see MainActivity#onCreateOptionsMenu(Menu)
     * @return : true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method allows to recover the rights to the localisation and the storage.
     * Then it get the user's location and the time to save them.
     *
     * @see MainActivity#addLocationListener()
     */
    private void addLocationListener() {
        // recover rights
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    ActivityData elem = new ActivityData();

                    // Recover the localisation and the time
                    elem.location = new Location(location);
                    elem.time = SystemClock.elapsedRealtime() - chrono;
                    // Add the elements in the data
                    data.add(elem);
                }

                @Override
                public void onProviderDisabled(String provider) {
                // If the GPS has been disabled	then update	the	textviews to reflect this.
                }

                @Override
                public void onProviderEnabled(String provider) {
                    //	If there is	a last known location then set it on the textviews.
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
            });
        } else {
            // If the application haven't the correct rights, it ask again to the user.
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                            TAG_CODE_PERMISSION_LOCATION);
        }
    }

    /**
     * This method allows to create the GPStracks if the application has the corrects rights.
     *
     * @see MainActivity#createFolder()
     */
    private void createFolder() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            {
                try {
                    // Create the GPStracks folder
                    directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GPStracks");
                    if (!directory.mkdirs()) {
                        Log.e("MainActivity", "Directory not created");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(ctx, "Unable to write to external storage", Toast.LENGTH_LONG).show();
            }
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    TAG_CODE_PERMISSION_LOCATION);
        }
    }
}

