/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappbackupviewer;

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
    
    protected long parse_date(String S) {
        Matcher matcher = date_pattern.matcher(S);
        int Y, M, D ,h, m, s;
        if (matcher.find()) {
            Y = Integer.parseInt(matcher.group(1));
            M = Integer.parseInt(matcher.group(2));
            D = Integer.parseInt(matcher.group(3));
            h = Integer.parseInt(matcher.group(4));
            m = Integer.parseInt(matcher.group(5));
            s = Integer.parseInt(matcher.group(6));
        }
    }
}
