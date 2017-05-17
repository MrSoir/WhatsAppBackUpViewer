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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
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
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        //launch(args);
        List<Message> ml = get_data("G:\\files\\Downloads\\WhatsApp Chat - Informatik SS 2017 (15.05.17)\\_chat.txt");
        System.out.println(ml);
        for (int i = 0; i < 11; i++) {
            if (ml.get(i) != null) {
                System.out.println(ml.get(i));
                System.out.println(ml.get(i).get_timestamp());
                System.out.println(ml.get(i).get_content());
                Date date = new Date(ml.get(i).get_timestamp());
                SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
                String dateStr = df2.format(date);
                System.out.println(dateStr);  
            }                
        }
    }
    
    /**
     * Takes the _chat.txt file and processes it line by line
     * @param logpath the path of the _chat.txt file
     * @return a list of message objects
     * @throws FileNotFoundException 
     */    
    public static List<Message> get_data(String logpath) throws FileNotFoundException {
        // temporary list to hold the processed message objects
        List<Message> temp = new LinkedList<>();
        
        // try-with-resource to easily handle the opening and closing of buff
        try(BufferedReader buff = new BufferedReader(new FileReader(logpath))) {
            // temporary string to hold the current line
            int c;
            StringBuilder sb = new StringBuilder();
            // cycle through the characters until the last one is processed
            while ((c = buff.read()) >= 0) {
                // check if current character is lineending (carrige return "CR")
                if (c == 0x0D) {          
                    // add processed line to list
                    temp.add(process_data(sb.toString())); 
                    // clear string builder
                    sb.delete(0, sb.length());
                    // skip the next character (is going to be (line feed "LF"))
                    buff.read();
                } else {
                    sb.append((char)c);
                }                    
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
    public static Message process_data(String line) {
        try {
            // if the message fits the server message regex make servermessageOBJ
            if (Message.isServerevent(line)) {
                return new ServerMessage(line);                
            }
            // if the message fits the attachment message regex make attachmentmessageOBJ
            else if (Message.isAttachment(line)) {
                return new AttachmentMessage(line);
            } 
            // if the message fits the text message regex make textmessageOBJ
            else if (Message.isText(line)) {
                return new TextMessage(line);
            } else {
                throw new Exception("Message [" + line + "] doesn't conform to known patterns.");
            }           
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
