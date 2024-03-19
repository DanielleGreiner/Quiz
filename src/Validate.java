import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validate {

	public static boolean name(String input) {
	
		if(input.length() < 2 || input.length() > 64)
			return false;
		
		Pattern p = Pattern.compile("[a-zA-Z ]");
		Matcher m = p.matcher(input);
		return m.find();

	}
	public static boolean age(String input) {
		
		if(input.length() < 1 || input.length() > 3)
			return false;
		
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(input);
		return m.find();
		
		
	}
	public static boolean phone(String input) {
		
		if(input.length() < 7 || input.length() > 11)
			return false;
		
		Pattern p = Pattern.compile("[0-9]{3}-[0-9]{4}$");
		Matcher m = p.matcher(input);
		return m.find();
		
	}
	public static boolean email(String input) {
	
		if(input.length() < 6 || input.length() > 200)
			return false;
		
		Pattern p = Pattern.compile("^[a-z0-9-_.]{1,64}@[a-z0-9]{1,63}\\.[a-z]{2,3}$");
		Matcher m = p.matcher(input);
		return m.find();
	}

}
