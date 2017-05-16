/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappbackupviewer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author timfi
 */
// "^\d{2}\.\d{2}\.\d{2}\, \d{1,2}\:\\d{1,2}\\:\d{2} (AM|PM)%s"
public class TextMessage extends Message {
    private String message;
    
    public TextMessage(String a, String t, String m) {
        try {
            this.actor = a;
            
            this.message = m;
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
    public String get_message() { return this.message; }
}
