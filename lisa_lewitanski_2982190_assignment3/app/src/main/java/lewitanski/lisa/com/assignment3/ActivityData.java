package lewitanski.lisa.com.assignment3;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * <b>MainActivity</b>
 * <p>
 *  This class allows to save the recovered data by the Gps.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class ActivityData implements Parcelable {
    /**
     * This code allows to get the permission for the location.
     *
     * This is the location of the user.
     * @see ActivityData#location
     *
     * This is the time at which the value was recovered.
     * @see ActivityData#time
     *
     * Parcelable implementation to transfers data between activities.
     * @see ActivityData#CREATOR
     *
     **/

     public Location    location;
     public long        time;

    public static final Parcelable.Creator<ActivityData> CREATOR = new Creator<ActivityData>() {
        public ActivityData createFromParcel(Parcel source) {
            ActivityData mData = new ActivityData();
            // Recovered and store the data
            mData.location = source.readParcelable(getClass().getClassLoader());
            mData.time = source.readLong();
            // Return the activity
            return mData;
        }
        public ActivityData[] newArray(int size) {
            // Create and return a new activity with a given size.
            return new ActivityData[size];
        }
    };

    /**
     * This method return 0 (auto-generated).
     *
     * @see ActivityData#describeContents()
     * @return 0
     */
    public int describeContents() {
        return 0;
    }

    /**
     * This method allows to write variables to the parcel.
     *
     * @param parcel : The parcel
     * @param flags : The flags
     * @see ActivityData#writeToParcel(Parcel, int)
     */
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(location, flags);
        parcel.writeLong(time);
    }
}