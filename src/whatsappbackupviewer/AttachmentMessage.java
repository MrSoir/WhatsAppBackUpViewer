/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatsappbackupviewer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for attachmentmessages
 * @author timfi
 */
public class AttachmentMessage extends Message {
    public static final Pattern PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{2}, \\d{1,2}:\\d{2}:\\d{2} (?:AM|PM)): (.+): (.+) (?:<‎attached>$)");
    private String path;
    
    public AttachmentMessage(String line) throws Exception {
        Matcher matcher = this.PATTERN.matcher(line);   
        if (matcher.find()) {
            this.timestamp = parse_date(matcher.group(1));
            this.actor = matcher.group(2);
            this.path = matcher.group(3);
        } else {
            throw new 	@Override
	public String toString(){
		return String.format("TextMessage:%n	date: %s%n	attachment: %s%n", timestamp, attachment);
	}Exception("Failed to parse attachmentmessage");
        }
    }
    
    public String get_path() { return this.path; }
    
    @Override
	public String toString(){
		return String.format("AttachmentMessage:%n	date: %s%n	attachment: %s%n", timestamp, path);
	}
}
