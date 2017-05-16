/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappbackupviewer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author timfi
 */
public class WhatsAppBackUpViewer extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Takes the _chat.txt file and processes it line by line
     * @param logpath the path of the _chat.txt file
     * @return a list of message objects
     * @throws FileNotFoundException 
     */    
    public List<Message> get_data(String logpath) throws FileNotFoundException {
        // temporary list to hold the processed message objects
        List<Message> temp = new ArrayList<>();
        
        // try-with-resource to easily handle the opening and closing of buff
        try(BufferedReader buff = new BufferedReader(new FileReader(logpath))) {
            // temporary string to hold the current line
            String line = buff.readLine();
            // cycle through the lines until the last one is processed
            while (line != null) {
                // add processed line to list
                temp.add(process_data(line));
                // read in next line
                line = buff.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        // returning the list of message objects
        return temp;
    }
    
    /**
     * Processes a single line from the _chat.txt file
     * @param line the line to be processed
     * @return a "finished" message object
     */
    public Message process_data(String line) {
        
        return new Message();
    }
}
