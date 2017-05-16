/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappbackupviewer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for textmessages
 * @author timfi
 */
public class TextMessage extends Message {
    public static final Pattern PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{2}, \\d{1,2}:\\d{2}:\\d{2} (?:AM|PM)): (.+): (.+)");
    private final String message;
    
    public TextMessage(String line) throws Exception {
        Matcher matcher = PATTERN.matcher(line);   
        if (matcher.find()) {
            this.timestamp = parse_date(matcher.group(1));
            this.actor = matcher.group(3);
            this.message = matcher.group(4);
        } else {
            throw new Exception("Failed to parse textmessage");
        }
    }
        
    public String get_message() { return this.message; }
}
