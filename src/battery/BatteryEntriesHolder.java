package battery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyriakos on 7/9/15.
 */
public class BatteryEntriesHolder {
    private List<BatteryEntry> batteryEntries ;

    public BatteryEntriesHolder(BatteryEntry batteryEntry){
        batteryEntries = new ArrayList<>();
        batteryEntries.add(batteryEntry);
    }

    public void addBatteryEntry(BatteryEntry batteryEntry)throws NullPointerException{
        batteryEntries.add(batteryEntry);
    }

    public List<BatteryEntry> getBatteryEntries() {
        return batteryEntries;
    }
}
