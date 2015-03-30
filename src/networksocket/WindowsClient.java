/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksocket;


import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 *
 * @author Camille
 */
public class WindowsClient extends Application {
    
    

private VBox vbox;
private VBox messages;
private HBox newMessage;
private TextField text;
 private BorderPane win;
 

    
    @Override
    public void start(Stage primaryStage) {
        
        Button send = new Button("Send");
        vbox = new VBox();
        messages = new VBox();
        newMessage = new HBox();
        text = new TextField();
        win = new BorderPane();
        Label title = new Label("Welcome in the tchat !");
        messages.setMinHeight(450);
        messages.setMinWidth(550);
        title.setFont(Font.font("Calibri Light", 40));
        primaryStage.setTitle("Java Tchat");
        vbox.getChildren().addAll(messages, newMessage);
        newMessage.getChildren().addAll(text,send);
        newMessage.setStyle("-fx-background-color: #66CC99;");
        newMessage.setPadding(new Insets(10,10,10,10));
        vbox.setPadding(new Insets(10,10,10,10));
        text.setMinSize(500, 50);
        // ADD MESSAGE
        HBox h1 = new HBox();
        VBox v1 = new VBox();
        Image im1 = new Image("user.png");
        Label pseudo = new Label("pseudo");
        pseudo.setFont(Font.font("Calibri Light", 20));
      
        ImageView iv1 = new ImageView();
        iv1.setImage(im1);
        iv1.setFitWidth(50);
        iv1.setFitHeight(50);
        Label message = new Label("Coucou toi!");
        message.setFont(Font.font("Calibri Light", 10));
        v1.getChildren().addAll(pseudo, message);
        h1.getChildren().addAll(iv1, v1);
        h1.setPadding(new Insets(5,5,5,5));
        messages.getChildren().add(h1);
        //
        
        win.setCenter(vbox);
        win.setTop(title);
        
        
        
        primaryStage.setScene(new Scene(win, 600, 600));
    primaryStage.show();
  
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
