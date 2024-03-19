import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.Cipher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;



public class Main extends Application {
	
	private Label label_registration,
	label_mf,
	q_1,
	q_2,
	q_3,
	q_4,
	q_5,
	label_name,
	label_age,
	label_phone,
	label_email;
	private TextField text_name;
	private TextField text_phone;
	private TextField text_age;
	private TextField text_email;
	private Button btn_submit;
	private RadioButton btn_1,
	btn_2,
	btn_3,
	btn_4,
	btn_5,
	btn_6,
	btn_7,
	btn_8,
	btn_9,
	btn_10,
	btn_11,
	btn_12,
	btn_13,
	btn_14,
	btn_15,
	btn_16,
	btn_17,
	btn_18,
	btn_19,
	btn_20;
	private int score = 1;
	private String name ;
	private String phone;
	private String age;
	private String email;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
	
		Log.log(LogType.INFO, " Starting Registration Page...");
		   
		Scene scene = initReg();
		
		initActions(stage);
		
		stage.setTitle("Quiz Game");
		stage.setScene(scene);
		stage.setWidth(800);
		stage.setHeight(600);
		stage.show();

	}
	private void initActions(Stage stage) {
		
		btn_submit.setOnAction(event -> {
		
			name = text_name.getText();
			phone = text_phone.getText();
			age = text_age.getText();
			email = text_email.getText(); 
			
			Log.log(LogType.INFO, "Initializing name to " + name);
			Log.log(LogType.INFO, "Initializing phone to " + phone);
			Log.log(LogType.INFO, "Initializing age to " + age);
			Log.log(LogType.INFO, "Initializing email to " + email);

			if(!Validate.name(name)) {
				Log.log(LogType.ERROR, "cannot validate name as " + name);
				Alerts.errorName();
				return;
				
				
			}
			if(!Validate.phone(phone)) {
				Log.log(LogType.ERROR, "cannot validate phone as " + phone);
				Alerts.errorPhone();
				return;
				
				
			}
			if(!Validate.age(age)) {
				Log.log(LogType.ERROR, "cannot validate age as " + age);
				Alerts.errorAge();
				return;
				
				
			}
			if(!Validate.email(email)) {
				Log.log(LogType.ERROR, "cannot validate email as " + email);
				Alerts.errorEmail();
				return;
				
				
			}

			Log.log(LogType.INFO, "Successfully initialized name to " + name);
			Log.log(LogType.INFO, "Successfully initialized phone to " + phone);
			Log.log(LogType.INFO, "Successfully initialized age to " + age);
			Log.log(LogType.INFO, "Successfully initialized email to " + email);
			
			 Connection C = null;
		     try 
		     {  
		    	 Secret.generateKey();
		    	 Log.log(LogType.INFO, "Successfully generated encryption keys");
		
			      ObjectInputStream inputStream = null;
			       inputStream = new ObjectInputStream(new FileInputStream(Secret.PUBLIC_KEY_FILE));
				      final PublicKey publicKey = (PublicKey) inputStream.readObject();
				      final byte[] cipherText = Secret.encrypt(name, publicKey);
				      
				      ObjectInputStream inputStream2 = null;
				       inputStream2 = new ObjectInputStream(new FileInputStream(Secret.PUBLIC_KEY_FILE));
					      final PublicKey publicKey2 = (PublicKey) inputStream2.readObject();
					      final byte[] cipherText2 = Secret.encrypt(phone, publicKey2); 
			      
					      ObjectInputStream inputStream3 = null;
					       inputStream3 = new ObjectInputStream(new FileInputStream(Secret.PUBLIC_KEY_FILE));
						      final PublicKey publicKey3 = (PublicKey) inputStream3.readObject();
						      final byte[] cipherText3 = Secret.encrypt(age, publicKey3);
						      
						      ObjectInputStream inputStream4 = null;
						       inputStream4 = new ObjectInputStream(new FileInputStream(Secret.PUBLIC_KEY_FILE));
							      final PublicKey publicKey4 = (PublicKey) inputStream4.readObject();
							      final byte[] cipherText4 = Secret.encrypt(email, publicKey4);
							 	 Log.log(LogType.INFO, "Successfully encrypted name, age, phone and email");
							      
							      
		        Class.forName("org.sqlite.JDBC");  
		        C = DriverManager.getConnection("jdbc:sqlite:test.db");
		       Statement statement = C.createStatement(); 
		       Log.log(LogType.INFO, "Successfully connected to database test.db");   
		       
		      statement.executeUpdate("drop table if exists person");
		      statement.executeUpdate("CREATE TABLE person (id INT, name VARCHAR(255), age VARCHAR(255), phone VARCHAR(255), email VARCHAR(255))");
		      Log.log(LogType.INFO, "Successfully created database table person");
		      String query = ("INSERT INTO person (id, name, age, phone, email) VALUES(1, '" + cipherText + "', '" + cipherText3 + "', '" + cipherText2 + "', '" + cipherText4 + "')");
		     statement.executeUpdate(query);
		     Log.log(LogType.INFO, "Successfully inserted data into database person");
		    
		     
 		      String sql = "SELECT id, name, age, phone, email FROM person";
	            ResultSet rs = statement.executeQuery(sql);
	            Log.log(LogType.INFO, "Successfully selected row 1 from database person");
	          

	         while (rs.next()) 
		         {  
		        	int id = rs.getInt("id");
		        	name = rs.getString("name");
	                age = rs.getString("age");
	                phone = rs.getString("phone");
	                email = rs.getString("email"); 
	                
	                ObjectInputStream inputStream5 = null;
		 		      inputStream5 = new ObjectInputStream(new FileInputStream(Secret.PRIVATE_KEY_FILE));
		              final PrivateKey privateKey = (PrivateKey) inputStream5.readObject();
		              name = Secret.decrypt(cipherText, privateKey);   
		          	
		              Scene newPageScene = initGame();
		              stage.setScene(newPageScene);
		             
		              
		              	btn_4.selectedProperty().addListener((observable, wasPreviouslySelected, isNowSelected) -> {
		              	    if (isNowSelected) {
		              	    	score++;	
		              	    } 
		              });
		              	btn_5.selectedProperty().addListener((observable, wasPreviouslySelected, isNowSelected) -> {
		              	    if (isNowSelected) {
		              	    	score++;
		              	    }
		              	    });
		              
		              	btn_9.selectedProperty().addListener((observable, wasPreviouslySelected, isNowSelected) -> {
		              	    if (isNowSelected) {
		              	    	score++;
		              	    
		              	    }
		              	    });
		              	btn_13.selectedProperty().addListener((observable, wasPreviouslySelected, isNowSelected) -> {
		              	    if (isNowSelected) {
		              	    	score++;
		              	    
		              	    }
		              	    });
		              	btn_17.selectedProperty().addListener((observable, wasPreviouslySelected, isNowSelected) -> {
		              	    if (isNowSelected) {
		              	    	score++;
		              	    
		              	    }
		              	    });
		              	 btn_submit.setOnAction(event2 -> {
		              		
		              		 Scene newPageScene2 = scoreBoard();
		                       stage.setScene(newPageScene2);	
		              	});
		         }  
            	 
             } catch (Exception e) 
		     {  
            	 
		    	 System.out.println(e);
		     }	
		});
	}

	private Scene initReg() {
		label_registration = new Label("Register");
		label_name = new Label("Enter Name:");
		text_name = new TextField();
		label_age = new Label("Enter Age:");
		text_age = new TextField();
		label_phone = new Label("Enter Phone Number:");
		text_phone = new TextField();
		label_email = new Label("Enter Email:");
		text_email = new TextField();
		
		VBox form = new VBox(label_registration,label_name, text_name,label_age, text_age, label_phone, text_phone,label_email, text_email);
		form.setPadding(new Insets(100));
		form.maxHeight(900);
		form.minWidth(900);

		btn_submit = new Button("Submit");
		VBox action = new VBox(btn_submit);
		action.setPadding(new Insets(100));
		action.setAlignment(Pos.CENTER);
		action.setMaxWidth(600);
		action.setMinWidth(600);
		

		BorderPane b = new BorderPane();
		b.setCenter(form);
		b.setBottom(action);
	
		return new Scene(b);
	}
	private Scene initGame() { 

			label_mf = new Label("Memory Forensic Quiz");
			q_1 = new Label("Question 1: What Memory Dump format is the largest? ");

			btn_1 = new RadioButton("Complete");
			btn_2 = new RadioButton("Small");
			btn_3 = new RadioButton("Kernel");
			btn_4 = new RadioButton("Raw");
		
			ToggleGroup group = new ToggleGroup();
			btn_1.setToggleGroup(group);
			btn_2.setToggleGroup(group);
			btn_3.setToggleGroup(group);
			btn_4.setToggleGroup(group);
			
			q_2 = new Label("Question 2: What Memory Dump format has the most integrity? ");

			btn_5 = new RadioButton("Raw");
			btn_6 = new RadioButton("Small");
			btn_7 = new RadioButton("Kernel");
			btn_8 = new RadioButton("Complete");
		
			ToggleGroup group2 = new ToggleGroup();
			btn_5.setToggleGroup(group2);
			btn_6.setToggleGroup(group2);
			btn_7.setToggleGroup(group2);
			btn_8.setToggleGroup(group2);
			
			q_3 = new Label("Question 3: What Memory Dump format is not native to Window Systems?");

			btn_9 = new RadioButton("Raw");
			btn_10 = new RadioButton("Kernel");
			btn_11 = new RadioButton("Complete");
			btn_12 = new RadioButton("Small");
		
			ToggleGroup group3 = new ToggleGroup();
			btn_9.setToggleGroup(group3);
			btn_10.setToggleGroup(group3);
			btn_11.setToggleGroup(group3);
			btn_12.setToggleGroup(group3);
		
			q_4 = new Label("Question 4: Which Memory dump is not apart of kernel-mode?");

			btn_13 = new RadioButton("Minidump");
			btn_14 = new RadioButton("Small");
			btn_15 = new RadioButton("Kernel");
			btn_16 = new RadioButton("Automatic");
		
			ToggleGroup group4 = new ToggleGroup();
			btn_13.setToggleGroup(group4);
			btn_14.setToggleGroup(group4);
			btn_15.setToggleGroup(group4);
			btn_16.setToggleGroup(group4);
			
			q_5 = new Label("Question 5: Which Memory dump is apart of user-mode?");

			btn_17 = new RadioButton("Minidumps");
			btn_18 = new RadioButton("Active");
			btn_19 = new RadioButton("Automatic");
			btn_20 = new RadioButton("Small");
		
			ToggleGroup group5 = new ToggleGroup();
			btn_17.setToggleGroup(group5);
			btn_18.setToggleGroup(group5);
			btn_19.setToggleGroup(group5);
			btn_20.setToggleGroup(group5);

			VBox form = new VBox(label_mf,q_1,btn_1,btn_2,btn_3,btn_4,q_2,btn_5,btn_6,btn_7,btn_8,q_3,btn_9,btn_10,btn_11,btn_12,q_4,btn_13,btn_14,btn_15,btn_16,q_5,btn_17,btn_18,btn_19,btn_20);
			form.setPadding(new Insets(100));
			form.maxHeight(900);
			form.minWidth(900);

			btn_submit = new Button("Submit");
			VBox action = new VBox(btn_submit);
			action.setPadding(new Insets(100));
			action.setAlignment(Pos.CENTER);
			action.setMaxWidth(600);
			action.setMinWidth(600);	
			//left this here in case a scroll bar would need to be implemented later
//			ScrollBar sc = new ScrollBar();
//			 sc.setMin(0);
//			 sc.setOrientation(Orientation.VERTICAL);
//			  ScrollPane scrollPane = new ScrollPane(action);
//		        scrollPane.setFitToHeight(true);

			BorderPane b = new BorderPane();
			b.setCenter(form);
			b.setBottom(action);
			//b.setRight(sc);
			return new Scene(b);
				
	}
	private Scene scoreBoard() {
		
		String scorescore = new String();
		scorescore = Integer.toString(score);
		Label scc = new Label(scorescore);
		Label score_label = new Label("Your Score out of 5 is: ");
		 Label player = new Label(name);
		
		VBox form = new VBox(player,score_label, scc);
		form.setPadding(new Insets(100));
		form.maxHeight(900);
		form.minWidth(900);
		 
		BorderPane b = new BorderPane();
		b.setCenter(form);

	
		return new Scene(b);
		
	}
		 

}
