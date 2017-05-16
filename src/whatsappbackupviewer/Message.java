/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappbackupviewer;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author timfi
 */
public abstract class Message {
    private Pattern date_pattern = Pattern.compile("^(\\d{2})\\.(\\d{2})\\.(\\d{2})\\, (\\d{1,2})\\:(\\d{2})\\:(\\d{2}) (AM|PM)%s");
    protected String actor;
    protected long timestamp;
    
    public String get_actor() { return this.actor; }
    public long get_timestamp() { return this.timestamp; }
    
    protected long parse_date(String S) throws Exception {
        Matcher matcher = date_pattern.matcher(S);
        // temporary variables to store the results
        String Y, M, D ,h, m, s;
        // if the matcher found the results extract the data
        if (matcher.find()) {
            Y = matcher.group(1);
            M = matcher.group(2);
            D = matcher.group(3);
            // sorgt dafür, dass die Stunden mit dem AM/PM Bullshit noch stimmen und gleichzeitig dafür, dass die Zeitzone auf UTC gewechselt wird
            h = ("PM".equals(matcher.group(7)) ? Integer.toString(Integer.parseInt(matcher.group(4)) + 11) :Integer.toString(Integer.parseInt(matcher.group(4)) -1 ));
            m = matcher.group(5);
            s = matcher.group(6);
        // else trow an exception so everyone knows some one fucked with the timestamps
        } else {
            throw new Exception("Illegal timestamp format.");
        }
        // Formatiert die werte zu einem von Instant.parse lesbaren string (YYYY-MM-DDThh:mm:ssZ)
        String temp_timestamp = "20" + Y + "-" + M + "-" + D + "T" + (h.length() < 2 ? "0" + h : h) + ":" + m + ":" + s + "Z";
        
        return Instant.parse(temp_timestamp).toEpochMilli();
    }
}
