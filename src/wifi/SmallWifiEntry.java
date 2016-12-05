package wifi;

/**
 * Created by Kyriakos on 20/9/15.
 */
public class SmallWifiEntry {
    private double lat ;
    private double lng ;
    private String bssid ;
    private int rssi ;

    public SmallWifiEntry(double lat, double lng,String bssid,int rssi){
        this.lat = lat ;
        this.lng = lng;
        this.bssid = bssid;
        this.rssi = rssi;
    }

    public double getLat() {return lat;}

    public double getLng() {return lng;}

    public int getRssi() {return rssi;}

    public String getBssid() {return bssid;}
}
