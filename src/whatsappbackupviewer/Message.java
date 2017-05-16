/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappbackupviewer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author timfi
 */
public class Message {
    private String sender;
    private Date timestamp;
    private String message;
    private Attachment attachment;
    private DateFormat format;
    
    public Message(String s, String t, String m, String a) {
        try {
            this.format = new SimpleDateFormat("dd.MM.yy, hh.mm.ss aa", Locale.ENGLISH);
            this.sender = s;
            this.timestamp = format.parse(t);
            this.message = m;
            this.attachment = (a != null) ? new Attachment(a) : null;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public String get_sender() { return this.sender; }
    public Date get_timestamp() { return this.timestamp; }
    public String get_message() { return this.message; }
    public Attachment get_attachment() { return this.attachment; }
}
