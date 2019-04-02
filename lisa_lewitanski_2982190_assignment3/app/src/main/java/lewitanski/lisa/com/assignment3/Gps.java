package lewitanski.lisa.com.assignment3;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;


/**
 * <b>Gps</b>
 * <p>
 *  This class allows to get, calculate and display the gps informations in a second activity.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class Gps extends AppCompatActivity {

    /**
     * Data it's an arrayList of ActivityData. ActivityData is a class witch contains the location
     * and the time.
     * @see Gps#data
     *
     **/
    private ArrayList<ActivityData> data = null;
    /**
     * The onCreate method allows to get the bundle instance witch contains all data to save it.
     * Then it recovered all TextView to set all values after treatment.
     * To finish, it give the data to the graph.
     *
     * @param savedInstanceState It's the bundle instance
     * @see Gps#onCreate(Bundle)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        // Initialize all values
        Float speedMaxValue = 0.0f;
        Float speedMinValue = 0.0f;
        Float speedAverageValue = 0.0f;
        Float fspeed = 0.0f;
        Float distanceValue = 0.0f;
        Long timeValue = 0L;
        Double altitudeMaxValue = 0.0;
        Double altitudeMinValue = 0.0;
        Double altitudeGainValue = 0.0;
        Double altitudeLossValue = 0.0;
        Double faltitude = 0.0;
        Double tmp_altitude = 0.0;
        Location tmp_location = null;

        // Get the bundle witch contains all data
        Bundle extras = getIntent().getBundleExtra("PARC");
        if (extras != null)
        {
            // Recovered all data
            data = extras.getParcelableArrayList("data");
            Toast.makeText(getApplicationContext(), "Data size transferred: " + data.size(), Toast.LENGTH_LONG).show();

            int tablen = data.size();
            double[] x = new double[tablen];
            double[] y = new double[tablen];

            boolean tmp =  true;
            int idx = 0;
            // Recovered and save all values
            for (ActivityData info : data) {
                if (tmp) {
                    faltitude = info.location.getAltitude();
                    fspeed = info.location.getSpeed();
                    altitudeMinValue = faltitude;
                    altitudeMaxValue = faltitude;
                    speedMaxValue = fspeed;
                    speedMinValue = fspeed;
                    tmp = false;
                }
                else {
                    if (tmp_altitude != 0.0 && tmp_altitude > info.location.getAltitude()){
                        altitudeLossValue += (info.location.getAltitude() - tmp_altitude);
                    }
                    if (tmp_altitude != 0.0 && tmp_altitude < info.location.getAltitude()){
                        altitudeGainValue += (info.location.getAltitude() - tmp_altitude);
                    }
                    if (speedMaxValue < info.location.getSpeed()) {
                        speedMaxValue = info.location.getSpeed();
                    }
                    if (speedMinValue > info.location.getSpeed()) {
                        speedMinValue = info.location.getSpeed();
                    }
                    if (altitudeMaxValue < info.location.getAltitude()) {
                        altitudeMaxValue = info.location.getAltitude();
                    }
                    if (altitudeMinValue > info.location.getAltitude()) {
                        altitudeMinValue = info.location.getAltitude();
                    }
                    distanceValue += info.location.distanceTo(tmp_location);
                    Log.e("Location point: ", "loc: " + info.location.toString());
                }
                speedAverageValue += info.location.getSpeed();
                tmp_altitude = info.location.getAltitude();
                tmp_location = info.location;
                x[idx] = info.time;
                y[idx] = tmp_altitude;
                idx += 1;
                timeValue = info.time;
            }
            speedAverageValue = (speedAverageValue / data.size());

            // Recovered all TextView
            TextView tdistanceValue = (TextView) findViewById(R.id.distance_value);
            TextView ttimeValue = (TextView) findViewById(R.id.time_value);
            TextView tAltitudeGainValue = (TextView) findViewById(R.id.altitude_gain_value);
            TextView tAltitudeLossValue = (TextView) findViewById(R.id.altitude_loss_value);
            TextView tAltitudeMaxValue = (TextView) findViewById(R.id.altitude_max_value);
            TextView tAltitudeMinValue = (TextView) findViewById(R.id.altitude_min_value);
            TextView tSpeedMinValue = (TextView) findViewById(R.id.speed_min_value);
            TextView tSpeedMaxValue = (TextView) findViewById(R.id.speed_max_value);
            TextView tSpeedAverageValue = (TextView) findViewById(R.id.speed_average_value);
            Graph    graph = (Graph) findViewById(R.id.grapheuh);

            // Set all TextView with the new values
            tdistanceValue.setText(String.format(Locale.getDefault(), "%.2f",(float)distanceValue) + " m");
            ttimeValue.setText(String.format(Locale.getDefault(), "%d",timeValue / 1000) + " s");
            tAltitudeMaxValue.setText(String.format(Locale.getDefault(), "%.2f",(double)altitudeMaxValue) + " m");
            tAltitudeMinValue.setText(String.format(Locale.getDefault(), "%.2f",(double)altitudeMinValue) + " m");
            tAltitudeGainValue.setText(String.format(Locale.getDefault(), "%.2f",(double)altitudeGainValue) + " m");
            tAltitudeLossValue.setText(String.format(Locale.getDefault(), "%.2f",(double)altitudeLossValue) + " m");
            tSpeedAverageValue.setText(String.format(Locale.getDefault(), "%.2f",(float)speedAverageValue * 3.6f) + " Km/h");
            tSpeedMaxValue.setText(String.format(Locale.getDefault(), "%.2f",(float)speedMaxValue * 3.6f) + " Km/h");
            tSpeedMinValue.setText(String.format(Locale.getDefault(), "%.2f",(float)speedMinValue * 3.6f) + " Km/h");

            // Give the data to the graph
            graph.setGraphData(x, y);
            graph.invalidate();
        }
        else {
            Toast.makeText(getApplicationContext(), "Unable to transfer data", Toast.LENGTH_LONG).show();
        }
    }
}