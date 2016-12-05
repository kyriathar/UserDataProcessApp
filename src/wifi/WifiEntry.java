package wifi;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyriakos on 25/8/15.
 */
public class WifiEntry {
        private int id ;
        private String email ;
        private String SSID ;
        private String BSSID ;
        private int RSSI ;
        private int frequency ;
        private double latitude ;
        private double longtitude ;
        private Date timestamp  ;

        static DateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        public WifiEntry() {
            id = 0 ;
            email = null ;
            SSID = null ;
            BSSID = null ;
            RSSI = 0 ;
            frequency = 0 ;
            latitude = 0.0;
            longtitude = 0.0;
            timestamp = null ;
        }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSSID(String SSID) {
        this.SSID = SSID;
    }
    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }
    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setTimestamp(String timestamp) throws ParseException {
        this.timestamp = format.parse(timestamp);
        //System.out.println(this.timestamp);
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getSSID() {
        return SSID;
    }
    public String getBSSID() {
        return BSSID;
    }
    public int getRSSI() {
        return RSSI;
    }
    public int getFrequency() {
        return frequency;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongtitude() {
        return longtitude;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public String getStimestamp(){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(timestamp);
    }

    public void print(){
        String fs ;
        fs = id + " " + email + " " + SSID + " " + BSSID + " " +
                           RSSI + " " + frequency + " " + latitude + " " + longtitude + " " + timestamp;
        System.out.println(fs);
    }
}
