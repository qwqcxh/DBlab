package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;

public class loginController {
	@FXML private JFXButton Exit;
	@FXML private JFXButton Login;
	@FXML private JFXTextField username;
	@FXML private JFXPasswordField password;
	@FXML private RadioButton readerBt;
	@FXML private RadioButton managerBt;
	
	public static Connection conn;
	public static String id;
	private PreparedStatement pstmt; 
	public void initialize() {
		Connect connector = new Connect();
		try {conn = connector.getConnect();} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void onInput() {
		username.setStyle(null);
		password.setStyle(null);
	}
	public void onExit() {
		Main.primaryStage.close();
		System.exit(0);
	}
	public void onLog() {
		id=username.getText();
		String pwd =password.getText();
		String name = null;
		String correctPwd=null;
		ResultSet pwdRes=null;
		boolean isReader = readerBt.isSelected();
		if(id==null||id.length()==0) { //未输入用户名
			username.setStyle("-fx-background-color: pink");
			return ;
		}
		if(pwd==null||pwd.length()==0) {  //未输入密码
			password.setStyle("-fx-background-color: pink");
			return ;
		}
		try{
			if(isReader) pstmt = conn.prepareStatement(Util.userPwdStmt);
			else pstmt=conn.prepareStatement(Util.managerPwdStmt);
			pstmt.setString(1, id);
        	pwdRes = pstmt.executeQuery();
        	while(pwdRes.next()) {
        		correctPwd=pwdRes.getString("pwd");
        		name = pwdRes.getString("name"); 
        	}
        }catch(SQLException e) {
	        e.printStackTrace();
        }
		if(pwd.equals(correctPwd)) {
			if(isReader) {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("reader.fxml"));
					Scene scene = new Scene(root,600,400);
					Main.primaryStage.setScene(scene);
					Main.primaryStage.setResizable(false);
					Main.primaryStage.setTitle("欢迎！"+name);
					Main.primaryStage.show();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
			else {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("manager.fxml"));
					Scene scene = new Scene(root,600,400);
					Main.primaryStage.setScene(scene);
					Main.primaryStage.setResizable(false);
					Main.primaryStage.setTitle(name+"管理员，欢迎！");
					Main.primaryStage.show();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else {
			Alert logFailAlert = new Alert(Alert.AlertType.INFORMATION,"用户不存在或者密码错误!\n",ButtonType.OK);
			logFailAlert.setTitle("登陆失败");
			logFailAlert.show();	
		}
	}
}
