package net.dusterthefirst.lockedServers.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Checker {
	
	public static boolean needUpdate(String num, String ver){
		if(version(num).equals(ver)){
			return false;
		}
		return true;
	}

	public static String version(String resource){
		
		try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource).getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (version.length() <= 7) {
                return version;
            }
        } catch (Exception ex) {
           return ("Failed to check for a update on spigot.");
        }
		return "Error.. Nothing Hapened";
	}
	
	
}
