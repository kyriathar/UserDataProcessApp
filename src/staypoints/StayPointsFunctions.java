package staypoints;

import java.util.ArrayList;
import java.util.List;



class LatLng{
    private double lat ;
    private double lng ;

    public LatLng(double lat,double lng){
        this.lat = lat ;
        this.lng = lng ;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String toString(){
        return String.valueOf(this.lat)+ " " + String.valueOf(this.lng);
    }
}


public class StayPointsFunctions {

    private static LatLng getCentroid(List<Point> points ,int i ,int j) {
        double centroidlat = 0, centroidlng = 0;

        for(int k = i;k<=j;k++){
            centroidlat +=points.get(k).getLatitude();
            centroidlng +=points.get(k).getLongitude();
        }

        centroidlat = centroidlat /(j-i+1);
        centroidlng = centroidlng /(j-i+1);

        return new LatLng(centroidlat,centroidlng);
    }

    public static StayPoint estimateStayPoint(List<Point> points,int i ,int j) {
        LatLng latLng = getCentroid(points,i,j);
        StayPoint sp = new StayPoint(latLng.getLat(),latLng.getLng(),points.get(i).getTimestamp(),points.get(j).getTimestamp());
        return sp;
    }

    public static double timeDiff(Point p1 ,Point p2){
        return Math.abs((p2.getTimestamp().getTime() - p1.getTimestamp().getTime())/1000);        //se seconds
    }

    public static double Distance(Point p1,Point p2){
        return Math.sqrt((p1.getLatitude()-p2.getLatitude())*(p1.getLatitude()-p2.getLatitude()) + (p1.getLongitude()-p2.getLongitude())*(p1.getLongitude()-p2.getLongitude()));
    }

    public static List<StayPoint> Generator(List<Point> points,double Tmin ,double Tmax ,double Dmax){
        int i =0 ;
        int j ;
        List<StayPoint> stayPoints = new ArrayList<>();
        while( i < points.size() -1 ) {
            j = i +1 ;

            while( j < points.size() ){
                if(timeDiff( points.get(j) , points.get(j-1)) > Tmax){
                    if(timeDiff( points.get(i) , points.get(j-1)) > Tmin){
                        stayPoints.add(estimateStayPoint(points,i,j-1));
                    }
                    i=j;
                    break;
                } else if(Distance(points.get(i),points.get(j)) > Dmax){
                    if(timeDiff( points.get(i) , points.get(j-1)) > Tmin){
                        stayPoints.add(estimateStayPoint(points,i,j-1));
                        i=j;
                        break;
                    }
                    i++;
                    break;
                }
                else if(j== points.size() -1){
                    if(timeDiff( points.get(i) , points.get(j)) > Tmin){
                        stayPoints.add(estimateStayPoint(points,i,j));
                    }
                    i=j;
                    break;
                }

                j++;
            }

        }
        return stayPoints;
    }
}
