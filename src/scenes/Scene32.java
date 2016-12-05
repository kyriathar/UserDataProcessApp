package scenes;

import gps.GpsEntriesHolder;
import gps.GpsEntry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxgooglemaps.GoogleMap;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import sample.Main;
import staypoints.Point;
import staypoints.StayPoint;
import staypoints.StayPointsFunctions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kyriakos on 17/9/15.
 */
public class Scene32 implements EventHandler<ActionEvent> {
    private Scene scene ;
    private TextField startDateText,endDateText,tminText,tmaxText,dmaxText,epsText,minPtsText;
    private Button buttonResults,buttonGetData ;
    private GoogleMap googleMapPoI ;

    public void display(){
        //Scene for GooglemapsSP
        googleMapPoI = new GoogleMap();

        //Scene for question 3.2
        Label label = new Label("Erwtima 3.2");
        Label labelstartDate = new Label("Arxiki Imerominia (yyyy-MM-dd hh:mm:ss) : ");
        startDateText = new TextField();
        startDateText.setText("2010-01-01 00:00:00");
        Label labelendDate = new Label("Teliki Imerominia (yyyy-MM-dd hh:mm:ss) : ");
        endDateText = new TextField();
        endDateText.setText("2020-01-01 00:00:00");
        Label labelTmin = new Label("Tmin : ");
        tminText = new TextField();
        tminText.setText("100");
        Label labelTmax = new Label("Tmax : ");
        tmaxText = new TextField();
        tmaxText.setText("300");
        Label labelDmax = new Label("Dmax : ");
        dmaxText = new TextField();
        dmaxText.setText("0.00003");
        Label labelEps = new Label("eps : ");
        epsText = new TextField();
        epsText.setText("0.005");
        Label labelMinPts = new Label("MinPoints : ");
        minPtsText = new TextField();
        minPtsText.setText("25");
        buttonGetData = new Button("Ok");
        buttonGetData.setOnAction(this);
        buttonResults = new Button("Anoikse Xarti (points of interest)");
        buttonResults.setOnAction(this);
        Button button = new Button("Pigene sto Erwtima 4");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Scene4 scene4 = new Scene4();
                scene4.display();
            }
        });

        GridPane layout = new GridPane();
        layout.add(label ,0,0);
        layout.add(labelstartDate, 0, 1);
        layout.add(startDateText ,1,1);
        layout.add(labelendDate ,0,2);
        layout.add(endDateText ,1,2);
        layout.add(labelTmin ,0,3);
        layout.add(tminText ,1,3);
        layout.add(labelTmax ,0,4);
        layout.add(tmaxText ,1,4);
        layout.add(labelDmax ,0,5);
        layout.add(dmaxText ,1,5);
        layout.add(labelEps,0,6);
        layout.add(epsText,1,6);
        layout.add(labelMinPts,0,7);
        layout.add(minPtsText,1,7);
        layout.add(buttonGetData ,1,8);
        layout.add(buttonResults ,0,9);
        layout.add(button ,1,10);
        layout.setVgap(5);
        scene = new Scene(layout,400,350);
        Main.window.setScene(scene);
        Main.window.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == buttonGetData) {
            double startTime = 0;
            double endTime = 0;
            double dMax = 0;
            double eps = 0;
            int minPts = 0;
            Date historyDate = null;
            Date futureDate = null;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                historyDate = format.parse(startDateText.getText());
                futureDate = format.parse(endDateText.getText());
                startTime = Double.parseDouble(tminText.getText());
                endTime = Double.parseDouble(tmaxText.getText());
                dMax = Double.parseDouble(dmaxText.getText());
                eps = Double.parseDouble(epsText.getText());
                minPts = Integer.parseInt(minPtsText.getText());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<DoublePoint> dbscanPoints = new ArrayList<DoublePoint>();

            for(GpsEntriesHolder gpsEntriesHolder : Main.GpsMap.values()){
                if (gpsEntriesHolder != null) {
                    List<GpsEntry> gpsEntries = gpsEntriesHolder.getGpsEntries();
                    List<Point> points = new ArrayList<>();
                    List<StayPoint> stayPoints;
                    for (GpsEntry e : gpsEntries) {
                        if (e.getTimestamp().after(historyDate) && e.getTimestamp().before(futureDate)) {
                            try {
                                points.add(new Point(e.getLatitude(), e.getLongtitude(), e.getTimestamp()));
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    stayPoints = StayPointsFunctions.Generator(points, startTime, endTime, dMax);

                    for (StayPoint p : stayPoints) {
                        //googleMapSP.addStayPoint(p.getLatitude(), p.getLongitude(), p.gettStart(), p.gettEnd());
                        double[] d = new double[2];
                        d[0] = p.getLatitude();
                        d[1] = p.getLongitude();
                        dbscanPoints.add(new DoublePoint(d));
                    }
                    stayPoints = null;
                    points = null;
                }
            }
            //DBSCAN...
            DBSCANClusterer dbscan = new DBSCANClusterer(eps, minPts);
            List<Cluster<DoublePoint>> cluster = dbscan.cluster(dbscanPoints);

            for(Cluster<DoublePoint> c: cluster){
                for(DoublePoint dp : c.getPoints()) {
                    googleMapPoI.addPointsOfInterest(dp.getPoint()[0],dp.getPoint()[1]);
                }
                googleMapPoI.createRectangle();
                googleMapPoI.deleteRectangle();
            }

        }
        if(actionEvent.getSource() == buttonResults) {
            final Stage stage = new Stage();
            stage.setTitle("Points of Interest GoogleMaps");
            Button buttonClearMarkers = new Button("Clear Rectangles");
            buttonClearMarkers.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    googleMapPoI.deleteAllRectangles();
                }
            });
            Button buttonBackToScene = new Button("Close");
            buttonBackToScene.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            VBox layoutGmaps = new VBox(20);
            layoutGmaps.getChildren().addAll(googleMapPoI,buttonClearMarkers,buttonBackToScene);
            Scene googleMapsSpScene = new Scene(layoutGmaps, 800, 700);
            stage.setScene(googleMapsSpScene);
            stage.show();
        }

    }
}
