package wifi;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


public class UserHolder {
    private String email;
    private List<WifiEntry> entries ;
    private Map<String,RssiFrequencyHolder> rssiFrequencyMap ;

    public UserHolder(String id, WifiEntry wifiEntry) {
        this.email = id;
        entries = new ArrayList<>();
        entries.add(wifiEntry);
        rssiFrequencyMap = new HashMap<>();

        //enter to hashmap
        rssiFrequencyMap.put(wifiEntry.getBSSID(),new RssiFrequencyHolder(wifiEntry.getRSSI(), wifiEntry.getFrequency()));
    }

    public void addEntry(WifiEntry wifiEntry)throws NullPointerException{
        entries.add(wifiEntry);

        RssiFrequencyHolder rssiFrequencyHolder ;
        if((rssiFrequencyHolder = rssiFrequencyMap.get(wifiEntry.getBSSID()))!=null){
            rssiFrequencyHolder.update(wifiEntry.getRSSI(), wifiEntry.getFrequency());           //Update
        }
        else {
            rssiFrequencyMap.put(wifiEntry.getBSSID(), new RssiFrequencyHolder(wifiEntry.getRSSI(), wifiEntry.getFrequency()));
        }

    }

    public String getEmail() {
        return email;
    }

    public List<WifiEntry> getEntries() {
        return entries;
    }

    public Map<String, RssiFrequencyHolder> getRssiFrequencyMap() {
        return rssiFrequencyMap;
    }
}
