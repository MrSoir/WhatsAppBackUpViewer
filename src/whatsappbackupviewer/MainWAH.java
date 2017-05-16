package whats_app_main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MainWAH {
	
	private static String dateRegexIsolated = String.format("^\\d{2}\\.\\d{2}\\.\\d{2}\\, \\d{1,2}\\:\\d{1,2}\\:\\d{2} (AM|PM)%s", Pattern.quote(":"));
	private static String dateRegex = String.format("%s.*", dateRegexIsolated);		
	
	private static String actorRegexIsolated = String.format("%s(\\d){2}(([\\s0-9])?)*([%s])", Pattern.quote("+"), Pattern.quote(":"));
	private static String actorRegex = String.format("%s.*", actorRegexIsolated);

	public static void main(String[] args) {
		
		String zipPath =  "/home/bigdaddy/Documents/Uni Tübingen - "
						+ "Accounting and Finance/Info_II/WhatsApp/WhatsApp Chat - "
						+ "Informatik SS 2017 (15.05.17).zip";
		try (ZipFile zf = new ZipFile( zipPath )){
			for ( Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements(); ){
			  ZipEntry entry = e.nextElement();
//			  System.out.println( entry.getName() );
			  
			  if (entry.getName().equals("_chat.txt")){
				  InputStream is = zf.getInputStream( entry );
				  
				  BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				  
				  int counter = 0;
				  
				  List<Message> messages = new ArrayList<>();
				  
				  Message msg = null;
				  
				  String line;
				  
				  while((line = reader.readLine()) != null){
					  
					    line = processString(line);
					  
					    System.out.println(line);
					  						
						if ( lineIsAMessage(line) ){
							msg = filterMessageFromLine(line);
							messages.add( msg );
						}else{
							// falls die derzeitige zeile keine message ist, kann sie (nach derzeitigem verstaendnis)
							// nur zusaetzlicher text des vorangeganegnen message-objekts sein -> damit muss das vorangegangene 
							// message objekt vom typ TextMessage sein:
							if (msg != null && msg.getClass() == TextMessage.class){
								TextMessage txtMsg = (TextMessage)msg;
								txtMsg.setMessage( txtMsg.getMessage() + line );
							}else{
								System.err.println("Derzeitige line ist keine message, aber zuvorige line was auch keine TextMessage -> was also ist diese zeile???");
							}
						}
						
					  
//					    if (counter++ > 10){break;} // schleifenzaehler, umd nicht jede zeile auszudrucken... muss spaeter wieder entfernt werden
				  }		
				  
				  for(int i=0; i < messages.size(); i++){
					  System.out.printf("message[%s]:	%s%n", i, messages.get(i));
				  }
			  }
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.printf("%n%n");
	}
	
	private static Message filterMessageFromLine(String line){
		String actor = filterActor(line);
		String date  = filterDate (line);
		
		if (date != null){
			date = date.replaceAll(":", "");
			if (actor != null){
				String message = line.substring( line.indexOf( actor ) + actor.length(), line.length() );
				
				actor = actor.replaceAll(":", "");
				return new TextMessage(date, actor, message);
			}else{
				String message = line.substring( line.indexOf( date ) + date.length(), line.length() );
				
				return new ServerMessage(date, message);
			}
		}
		return null;
	}
	private static String filterDate(String line){
		return filterRegex(dateRegexIsolated, line);
	}
	private static String filterActor(String line){
		return filterRegex(actorRegexIsolated, line);
	}
	private static String filterRegex(String regex, String matcherStr){
		Matcher matcher = Pattern.compile( regex ).matcher( matcherStr );
		if ( matcher.find() ){
			return matcher.group();
		}
		return null;
	}
	
	// checkt, ob eine zeile der anfang einer TextMessage ist -> die zeile muss somit mit einem datum beginnen und danach
	// muss eine telefonnummer kommen:
	private static boolean lineIsATextMessage(String line){
		String sringWithoutDate = line.replaceFirst(dateRegexIsolated, "").trim();
		
		boolean startsWithDate = line.matches(dateRegex),
				followedByPhone = sringWithoutDate.matches(actorRegex);
		
		return startsWithDate && followedByPhone;
	}
	// checkt, ob eine zeile der anfang einer message ist (textmessage, servermessage etc.)
	private static boolean lineIsAMessage(String line){
		return line.matches(dateRegex);
	}
	
	// in dem WhatsApp-backup-text-file ("_chat.txt") stecken einige schraege characters drin, dieser wird sich hier
	// entledigt:
	private static String processString(String str){
		return str.replaceAll(String.format("%s", (char)160), " ")
				  .replaceAll(String.format("%s", (char)8234), "")
				  .replaceAll(String.format("%s", (char)8236), "");
	}
	
	
	//--------------------------------------------------------------------------------------
	// von mir implementierte klassen, damit ich das programm schonmal testen kann
	private static abstract class Message{
		private String message;
		void setMessage(String message){
			this.message = message;
		}
		String getMessage(){
			return message;
		}
	}
	private static class TextMessage extends Message{
		private String date,
					   actor;
		public TextMessage(String date, String actor){
			this.date = date;
			this.actor = actor;
		}
		public TextMessage(String date, String actor, String message){
			this.date = date;
			this.actor = actor;
			this.setMessage(message);
		}
		@Override
		public String toString(){
			return String.format("TextMessage:%n	date: %s%n	actor: %s%n	message: %s%n", date, actor, getMessage());
		}
	}
	private static class ServerMessage extends Message{
		private String date;
		public ServerMessage(String date){
			this.date = date;
		}
		public ServerMessage(String date, String message){
			this.date = date;
			this.setMessage(message);
		}
		@Override
		public String toString(){
			return String.format("TextMessage:%n	date: %s%n	message: %s%n", date, getMessage());
		}
	}
	//--------------------------------------------------------------------------------------


}
