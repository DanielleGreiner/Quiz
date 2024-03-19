
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	
	   
  public static void errorName()
  {
	  Alert a = new Alert(AlertType.ERROR);
	  a.setContentText("Cannot Validate Name");
      a.show();
      
  }
  public static void errorAge()
  {
	  Alert a = new Alert(AlertType.ERROR);
	  a.setContentText("Cannot Validate Age");
      a.show();
      
  }
  public static void errorPhone()
  {
	  Alert a = new Alert(AlertType.ERROR);
	  a.setContentText("Cannot Validate Phone");
      a.show();
      
  }
  public static void errorEmail()
  {
	  Alert a = new Alert(AlertType.ERROR);
	  a.setContentText("Cannot Validate Email");
      a.show();
      
  }
};
