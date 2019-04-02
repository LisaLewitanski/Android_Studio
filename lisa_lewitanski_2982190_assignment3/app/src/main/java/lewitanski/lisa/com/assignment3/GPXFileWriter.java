package lewitanski.lisa.com.assignment3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <b>GPXFileWriter</b>
 * <p>
 *  This class allows to write in the correct file all data recovered by the Gps.
 * <p>
 *
 * @author Lisa Lewitanski
 * @version 1.0
 */
public class GPXFileWriter {
    /**
     * This is the xml header of the gpx file.
     * @see GPXFileWriter#XML_HEADER
     *
     * This is the tag of the gpx file.
     * @see GPXFileWriter#TAG_GPX
     *
     * The POINT_DATE_FORMATTER it used to set the date with a given format.
     * @see GPXFileWriter#POINT_DATE_FORMATTER
     */
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    private static final String TAG_GPX = "<gpx"
            + " xmlns=\"http://www.topografix.com/GPX/1/1\""
            + " version=\"1.1\""
            + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">";
    private static final SimpleDateFormat POINT_DATE_FORMATTER = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

    /**
     * This method allows to write the header, the tag and the data recovered by the Gps on a given file.
     *
     * @param trackName The filename
     * @param activities Recovered data
     * @param target The file
     * @see GPXFileWriter#writeGpxFile(String, List, File)
     *
     */
    public static void writeGpxFile(String trackName,
                                    List<ActivityData> activities, File target) throws IOException {
        FileWriter fw = new FileWriter(target);
        fw.write(XML_HEADER + "\n");
        fw.write(TAG_GPX + "\n");
        writeTrackPoints(trackName, fw, activities);
        fw.write("</gpx>");
        fw.close();
    }

    /**
     * This method allows to write the data recovered by the Gps on a given file.
     *
     * @param trackName The filename
     * @param fw        The file
     * @param listActivity The list of all data
     * @see GPXFileWriter#writeGpxFile(String, List, File)
     *
     */
    private static void writeTrackPoints(String trackName, FileWriter fw,
                                         List<ActivityData> listActivity) throws IOException {
        fw.write("\t" + "<trk>" + "\n");
        fw.write("\t\t" + "<name>" + trackName + "</name>" + "\n");
        fw.write("\t\t" + "<trkseg>" + "\n");

        for (ActivityData data : listActivity) {
            String out = "";
            out += "\t\t\t" + "<trkpt lat=\""
                    + data.location.getLatitude() + "\" " + "lon=\""
                    + data.location.getLongitude() + "\">" + "\n";
            out += "\t\t\t\t" + "<ele>" + data.location.getAltitude()
                    + "</ele>" + "\n";
            out += "\t\t\t\t" + "<time>"
                    + POINT_DATE_FORMATTER.format(new Date(data.time))
                    + "</time>" + "\n";
            out += "\t\t\t\t" + "<cmt>speed="
                    + data.location.getSpeed() + "</cmt>" + "\n";
            out += "\t\t\t\t" + "<hdop>" + data.location.getAccuracy()
                    + "</hdop>" + "\n";
            out += "\t\t\t" + "</trkpt>" + "\n";

            fw.write(out);
        }
        fw.write("\t\t" + "</trkseg>" + "\n");
        fw.write("\t" + "</trk>" + "\n");
    }
}
