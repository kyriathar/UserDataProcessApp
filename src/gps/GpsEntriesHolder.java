package gps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyriakos on 5/9/15.
 */
public class GpsEntriesHolder {
    private List<GpsEntry> gpsEntries ;

    public GpsEntriesHolder(GpsEntry gpsEntry){
        gpsEntries = new ArrayList<>();
        gpsEntries.add(gpsEntry);
    }

    public void addGpsEntry(GpsEntry gpsEntry)throws NullPointerException{
        gpsEntries.add(gpsEntry);
    }

    public List<GpsEntry> getGpsEntries() {
        return gpsEntries;
    }
}
