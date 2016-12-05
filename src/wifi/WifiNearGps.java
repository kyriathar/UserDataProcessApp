package wifi;

import sample.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyriakos on 20/9/15.
 */

public class WifiNearGps {
    //Map key : timestamp  value: strongest RSSI
    Map<String, SmallWifiEntry> wifiByTimeMap ;

    public WifiNearGps(){
        wifiByTimeMap = new HashMap<>();
    }

    public SmallWifiEntry find(String timeStamp){
        SmallWifiEntry smallWifiEntry ;
        if((smallWifiEntry = wifiByTimeMap.get(timeStamp))!=null){
            return smallWifiEntry;
        }
        else{
            return null;
        }
    }

    public void createMap(String userID){
        UserHolder itUser ;
        if((itUser = Main.mapsHolder.getUserMap().get(userID))!=null){
            for(WifiEntry e : itUser.getEntries()){
                //populate wifiByTimeMap
                SmallWifiEntry smallWifiEntry ;
                if((smallWifiEntry = wifiByTimeMap.get(e.getStimestamp()))!=null){
                    //Entry exists ->Update if needed e.RSSI > mappped.RSSI
                    if(e.getRSSI() > smallWifiEntry.getRssi()){
                        wifiByTimeMap.put(e.getStimestamp(),new SmallWifiEntry(e.getLatitude(),e.getLongtitude(),e.getBSSID(),e.getRSSI()));
                    }
                }
                else{
                    //create and put entry
                    wifiByTimeMap.put(e.getStimestamp(),new SmallWifiEntry(e.getLatitude(),e.getLongtitude(),e.getBSSID(),e.getRSSI()));
                }
            }
        }
    }
}



