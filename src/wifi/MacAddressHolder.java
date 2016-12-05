package wifi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyriakos on 27/8/15.
 */

public class MacAddressHolder {
    private String BSSID ;
    private double lat ;
    private double lon ;
    private List<WifiEntry> entries ;

    public MacAddressHolder(String BSSID, WifiEntry wifiEntry){
        this.BSSID = BSSID;
        entries = new ArrayList<>();
        entries.add(wifiEntry);
        lat = 0.0;
        lon = 0.0;
    }

    public void addEntry(WifiEntry wifiEntry)throws NullPointerException{
        entries.add(wifiEntry);
    }

    public void calcLatLon(){
        double w =0.0;
        double totalweight =0.0;

        for(WifiEntry wifiEntry : entries){
            w = Math.pow(10, (double) wifiEntry.getRSSI()/10);
            totalweight += w ;
            lat+= wifiEntry.getLatitude() *w;
            lon+= wifiEntry.getLongtitude() *w;
        }
        lat = lat /totalweight ;
        lon = lon /totalweight;
        //No longer need entries ?
        entries = null ;
        //System.out.println("BSSID ="+BSSID +"\t"+ "lat =" + lat+"\t" +"lon =" + lon);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void printContainer(){
        for(WifiEntry wifiEntry : entries )
            System.out.println(wifiEntry.getSSID());
    }

    public void printContainerNo(){
        System.out.println(entries.size());
    }

    public void printLatLon(){
        System.out.println("lat =" + lat+"\t" +"lon =" + lon);
    }
}
