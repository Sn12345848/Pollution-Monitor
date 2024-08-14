import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;
public class Pollution_Monitor {
    public static void Monitor(){
        try{
            @SuppressWarnings("deprecation")
            URL url=new URL("https://api.thingspeak.com/channels/1906853/feeds.json?results=1");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response=connection.getResponseCode();
            if(response!=200){
                System.out.print("Response Code:"+response);
            }
            else{
                Scanner sc1=new Scanner(url.openStream());
                String input="";
                while(sc1.hasNext()){
                    input+=sc1.nextLine();
                }
                sc1.close();
                JSONParser pr=new JSONParser();
                JSONObject obj1=(JSONObject) pr.parse(input);
                JSONArray arr=(JSONArray) obj1.get("feeds");
                JSONObject obj2=(JSONObject) arr.get(0);
                var PM10_0=obj2.get("field1");
                var PM2_5=obj2.get("field2");
                var PM1_0=obj2.get("field3");
                var PM4_0=obj2.get("field4");
                System.out.println("PM 10.0: "+PM10_0);
                System.out.println("PM 2.5: "+PM2_5);
                System.out.println("PM 1.0: "+PM1_0);
                System.out.println("PM 4.0: "+PM4_0);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        Monitor();
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            public void run(){
                    System.out.println("---------------------------------");
                    System.out.println("After 2 minute:");
                    Monitor();
                }
        };
        timer.scheduleAtFixedRate(task, 120000, 120000);
    }
}
