package staypoints;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StayPoint extends Point{
    private Date tStart  ;
    private Date tEnd  ;

    static Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public StayPoint(double latitude ,double longitude,Date tStart,Date tEnd) {
        super(latitude,longitude);
        this.tStart =tStart;
        this.tEnd =tEnd;
    }

    public void settStart(Date tStart) {this.tStart = tStart;}
    public void settEnd(Date tEnd) {this.tEnd = tEnd;}

    public Date gettStart() {return tStart;}
    public Date gettEnd() {return tEnd;}

    public String toString() {

        String sTstart = formatter.format(this.tStart);
        String sTend = formatter.format(this.tEnd);
        return String.valueOf(this.latitude) + " " + String.valueOf(this.longitude) + " Start time: " + sTstart + " End time: " + sTend ;
    }
}
