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
    
    public TextMessage(String a, String t, String m) throws Exception {
        this.actor = a;
        this.timestamp = parse_date(t);
        this.message = m;
    }
        
    public String get_message() { return this.message; }
}
