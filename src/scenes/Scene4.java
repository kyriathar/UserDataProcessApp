package scenes;

import basestation.Operator;
import basestation.OperatorWrapper;
import battery.Diagram;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kyriakos on 18/9/15.
 */
public class Scene4 implements EventHandler<ActionEvent> {
    Diagram batteryDiagram,operatorDiagram;
    TextField DateText;
    Button buttonGetData,buttonBatDiag ,buttonOperator,buttonWifisNear;
    Scene scene4 ;

    public void display() {
        //Scene for Battery Diagram
        batteryDiagram = new Diagram();
        //Scene for Operators
        operatorDiagram = new Diagram();


        //Scene for question 4
        Label label = new Label("Erwtima 4");
        Label labelDate = new Label("Imerominia (yyyy-MM-dd) : ");
        DateText = new TextField();
        DateText.setText("2015-03-27");
        buttonGetData = new Button("Ok");
        buttonGetData.setOnAction(this);
        buttonBatDiag = new Button("Anoikse Diagrama LowBattery");
        buttonBatDiag.setOnAction(this);
        buttonWifisNear = new Button("Anoikse Kontina Wifis");
        buttonWifisNear.setOnAction(this);
        buttonOperator = new Button("Statistika Operator");
        buttonOperator.setOnAction(this);
        GridPane layout = new GridPane();
        layout.add(label ,0,0);
        layout.add(labelDate, 0, 1);
        layout.add(DateText ,1,1);
        layout.add(buttonGetData, 1, 2);
        layout.add(buttonBatDiag ,0,3);
        layout.add(buttonWifisNear,0,4);
        layout.add(buttonOperator ,0,5);
        layout.setVgap(5);
        scene4 = new Scene(layout,400,300);
        Main.window.setScene(scene4);
        Main.window.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == buttonGetData) {
            List<Integer> list = null;

            Date date = null;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                date = format.parse(DateText.getText());
                list = Main.lowBattery.getArrayOfUsers(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(list != null) {
                for(int i=0; i<24;i++ ){
                    batteryDiagram.noOfPeoples(list.get(i));
                }
                batteryDiagram.createBarDiagram();
            }
        }
        if(actionEvent.getSource() == buttonBatDiag){
            final Stage stage = new Stage();
            stage.setTitle("Low Battery Bar Diagram");
            Button buttonClearBatDiag = new Button("Clear Diagram");
            buttonClearBatDiag.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    batteryDiagram.destroyChart();
                }
            });
            Button buttonBackToScene2 = new Button("Close");
            buttonBackToScene2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            VBox layoutGmaps = new VBox(5);
            layoutGmaps.getChildren().addAll(batteryDiagram,buttonClearBatDiag,buttonBackToScene2);
            Scene batDiagScene = new Scene(layoutGmaps, 800, 600);
            stage.setScene(batDiagScene);
            stage.show();
        }

        if(actionEvent.getSource()== buttonWifisNear){
            final Stage stage = new Stage();
            stage.setTitle("Wifis Near GoogleMaps");
            Button buttonClearMarkers = new Button("Clear Markers");
            buttonClearMarkers.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Scene2.googleMapNear.clearMarkers();
                }
            });
            Button buttonClearPoly = new Button("Clear Polyline");
            buttonClearPoly.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Scene2.googleMapNear.clearPolyline();
                }
            });
            Button buttonBackToScene2 = new Button("Close");
            buttonBackToScene2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            VBox layoutGmaps = new VBox(5);
            layoutGmaps.getChildren().addAll(Scene2.googleMapNear,buttonClearMarkers,buttonClearPoly,buttonBackToScene2);
            Scene scene = new Scene(layoutGmaps, 800, 300);
            stage.setScene(scene);
            stage.show();
        }

        if(actionEvent.getSource() == buttonOperator){
            try {
                OperatorWrapper.generateOperators("myFiles/base_station.csv");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for(Operator o : OperatorWrapper.operatorMap.values() ){
                operatorDiagram.addClients(o.getNoClients());
                operatorDiagram.addOperator(o.getName());
            }
            operatorDiagram.createOperatorDiagram();

            final Stage stage = new Stage();
            stage.setTitle("Operator Diagram");
            Button buttonClearBatDiag = new Button("Clear Diagram");
            buttonClearBatDiag.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    operatorDiagram.destroyChart();
                }
            });
            Button buttonBackToScene2 = new Button("Close");
            buttonBackToScene2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            VBox layoutGmaps = new VBox(5);
            layoutGmaps.getChildren().addAll(operatorDiagram,buttonClearBatDiag,buttonBackToScene2);
            Scene batDiagScene = new Scene(layoutGmaps, 700, 500);
            stage.setScene(batDiagScene);
            stage.show();
        }

    }
}
