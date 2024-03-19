import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Log {
	public static void log(LogType type, String message) {
		if(!Config.LOGGING) {
			return;
		}
		
		String entry = Log.getTimestamp() + ":" + type + ":" + message + "\n";
		
		logf(entry);
		logc(entry);
	}
	public static void log(LogType type, String message, String className, String methodName) {
		if(!Config.LOGGING) {
			return;
		}
		
		
		String entry = Log.getTimestamp() + ":" + type + ":" + className + ":" + methodName + ":" + message + "\n";
		
		logf(entry);
		logc(entry);
	}
	private static void logf(String entry) {
		FileWriter fw;
		
		try {
			fw = new FileWriter(Config.LOG_FILENAME, true);
			fw.write(entry);
			fw.close();
		} catch (IOException e) {
			if(Config.DEBUG) {
				e.printStackTrace();
			}
		}
	}
	public static void logc(String entry) {
		System.out.println(entry);
	}
	
	private static String getTimestamp() {
		Calendar now = Calendar.getInstance();
		
		String timestamp = now.get(Calendar.YEAR) + "" +
	    		(now.get(Calendar.MONTH) + 1) + "" +
	    		now.get(Calendar.DAY_OF_MONTH) + "" +
	    		"-" + 
	    		now.get(Calendar.HOUR_OF_DAY) + "" +
	    		now.get(Calendar.MINUTE) + "" + 
	    		now.get(Calendar.SECOND) + "";
		
		return timestamp;
	}
}
