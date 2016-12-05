package basestation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyriakos on 9/9/15.
 */
public class BaseStatEntry{
    private String email ;
    private String operator ;
    private short mcc ;
    private short mnc ;
    private int cid;
    private short lac;
    private double latitude;
    private double longitude;
    private Date timestamp  ;

    static DateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public BaseStatEntry(){
        email = null;
        operator = null;
        mcc = 0;
        mnc = 0;
        cid = 0;
        lac = 0;
        latitude = 0.0;
        longitude = 0.0;
        timestamp = null ;
    }
    //Setters
    public void setEmail(String email) {this.email = email;}
    public void setOperator(String operator) {this.operator = operator;}
    public void setMcc(short mcc) {this.mcc = mcc;}
    public void setMnc(short mnc) {this.mnc = mnc;}
    public void setCid(int cid) {this.cid = cid;}
    public void setLac(short lac) {this.lac = lac;}
    public void setLatitude(double latitude) {this.latitude = latitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public void setTimestamp(String timestamp) throws ParseException {
        this.timestamp = format.parse(timestamp);
    }
    //Getters
    public String getEmail() {return email;}
    public String getOperator() {return operator;}
    public short getMcc() {return mcc;}
    public short getMnc() {return mnc;}
    public int getCid() {return cid;}
    public short getLac() {return lac;}
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}
    public Date getTimestamp() {return timestamp;}

    //print Base Station Entry
    public void print(){
        String fs ;
        fs = email + " " + operator + " " + mcc + " " + mnc + " " + cid + " " + lac + " " +
                latitude + " " + longitude + " " + timestamp;
        System.out.println(fs);
    }
}