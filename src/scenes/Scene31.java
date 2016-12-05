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
import java.util.Map;

/**
 * Created by Kyriakos on 15/9/15.
 */
public class Scene31 implements EventHandler<ActionEvent> {
    private Scene scene ;
    private Button buttonResults,buttonGetData ;
    private TextField startDateText,endDateText ,userIdText ,tminText ,tmaxText , dmaxText;

    private GoogleMap googleMapSP ;

    public void display() {
        //Scene for GooglemapsSP
        googleMapSP = new GoogleMap();

        //Scene for question 3.1
        Label label = new Label("Erwtima 3.1");
        Label labelstartDate = new Label("Arxiki Imerominia (yyyy-MM-dd hh:mm:ss) : ");
        startDateText = new TextField();
        startDateText.setText("2010-01-01 00:00:00");
        Label labelendDate = new Label("Teliki Imerominia (yyyy-MM-dd hh:mm:ss) : ");
        endDateText = new TextField();
        endDateText.setText("2020-01-01 00:00:00");
        Label labeluserId = new Label("ID Xristi : ");
        userIdText = new TextField();
        userIdText.setText("user56");
        Label labelTmin = new Label("Tmin : ");
        tminText = new TextField();
        tminText.setText("100");
        Label labelTmax = new Label("Tmax : ");
        tmaxText = new TextField();
        tmaxText.setText("300");
        Label labelDmax = new Label("Dmax : ");
        dmaxText = new TextField();
        dmaxText.setText("0.00003");
        buttonGetData = new Button("Ok");
        buttonGetData.setOnAction(this);
        buttonResults = new Button("Anoikse Xarti (stay points)");
        buttonResults.setOnAction(this);
        Button button = new Button("Pigene sto Erwtima 3.2");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Scene32 scene32 = new Scene32();
                scene32.display();
            }
        });
        GridPane layout = new GridPane();
        layout.add(label ,0,0);
        layout.add(labelstartDate, 0, 1);
        layout.add(startDateText ,1,1);
        layout.add(labelendDate ,0,2);
        layout.add(endDateText ,1,2);
        layout.add(labeluserId ,0,3);
        layout.add(userIdText ,1,3);
        layout.add(labelTmin ,0,4);
        layout.add(tminText ,1,4);
        layout.add(labelTmax ,0,5);
        layout.add(tmaxText ,1,5);
        layout.add(labelDmax ,0,6);
        layout.add(dmaxText ,1,6);
        layout.add(buttonGetData,1,7);
        layout.add(buttonResults ,0,8);
        layout.add(button ,1,9);
        layout.setVgap(5);
        scene = new Scene(layout,400,300);
        Main.window.setScene(scene);
        Main.window.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource() == buttonGetData) {
            double startTime = 0;
            double endTime = 0;
            double dMax = 0;
            Date historyDate = null;
            Date futureDate = null;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            try {
                historyDate = format.parse(startDateText.getText());
                futureDate = format.parse(endDateText.getText());
                startTime = Double.parseDouble(tminText.getText());
                endTime = Double.parseDouble(tmaxText.getText());
                dMax = Double.parseDouble(dmaxText.getText());
            } catch (ParseException e) {
                //e.printStackTrace();
                Main.window.setScene(scene);
            }
            //Gps code...
            GpsEntriesHolder gpsEntriesHolder;
            gpsEntriesHolder = Main.GpsMap.get(userIdText.getText());

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
                    googleMapSP.addStayPoint(p.getLatitude(), p.getLongitude(), p.gettStart(), p.gettEnd());
                }
            }
        }

        if(actionEvent.getSource() == buttonResults) {
                final Stage stage = new Stage();
                stage.setTitle("Stay points GoogleMaps");
                Button buttonClearMarkers = new Button("Clear Markers");
                buttonClearMarkers.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        googleMapSP.clearMarkers();
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
                layoutGmaps.getChildren().addAll(googleMapSP,buttonClearMarkers,buttonBackToScene);
                Scene googleMapsSpScene = new Scene(layoutGmaps, 800, 700);
                stage.setScene(googleMapsSpScene);
                stage.show();
        }

    }

}
