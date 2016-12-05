package sample;

import basestation.BaseStatEntriesHolder;
import battery.BatteryEntriesHolder;
import battery.LowBattery;
import gps.GpsEntriesHolder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scenes.Scene2;
import scenes.Scene31;
import wifi.*;

import java.util.*;


public class Main extends Application implements EventHandler<ActionEvent>{

    public static MapsHolder mapsHolder ;
    public static Map<String, GpsEntriesHolder> GpsMap ;
    public static Map<String, BatteryEntriesHolder> BatteryMap;
    public static Map<String, BaseStatEntriesHolder> BaseMap;
    public static LowBattery lowBattery;

    public static Stage window ;

    Scene scene1,scene2,scene4 ;
    Scene userDataScene ;
    Button button1,button2,button3,button4 ;
    Button buttonHash ;
    Button buttonInputScreen,buttonBackToScene2Gps;
    Boolean proceedToQ2,proceedToQ3,proceedToQ4;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("JavaFX - Diaxeirisi Diktuwn");

        proceedToQ2 = false;
        proceedToQ3 = false;
        proceedToQ4 = false;

        //Scene for question 1
        Label label1 = new Label("Erwtima 1");
        buttonHash = new Button("data to HashMap");
        buttonHash.setOnAction(this);
        button1 = new Button("Pigene sto Erwtima 2");
        button1.setOnAction(this);
        GridPane layout1 = new GridPane();
        layout1.add(label1 ,0,0);
        layout1.add(buttonHash, 0, 1);
        layout1.add(button1, 0, 2);
        layout1.setVgap(5);
        scene1 = new Scene(layout1,400,300);


        window.setScene(scene1);
        window.show();
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource()==button1 && proceedToQ2) {
            Scene2 scene2 = new Scene2();
            scene2.display();
        }
        if(actionEvent.getSource()==button2 && proceedToQ3) {
            Scene31 scene31 = new Scene31();
            scene31.display();
        }
        if(actionEvent.getSource()==button3 && proceedToQ4) {
            window.setScene(scene4);
        }
        if(actionEvent.getSource()==button4) {
            window.close();
        }

        /* nimata... */
        if(actionEvent.getSource()== buttonHash) {
            Threads threads = new Threads();
            threads.runAll();
            try {
                threads.joinAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            proceedToQ2 = true ;
        }

        if(actionEvent.getSource()== buttonInputScreen) {
            window.setScene(userDataScene);
        }

        if(actionEvent.getSource()==  buttonBackToScene2Gps) {
            window.setScene(scene2);
        }

    }
}
