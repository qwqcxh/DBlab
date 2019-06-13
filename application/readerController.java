package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class readerController {
	@FXML private JFXTextField readerid;
	@FXML private JFXTextField readertype;
	@FXML private JFXTextField readername;
	@FXML private JFXTextField sex;
	@FXML private JFXTextField fine;
	@FXML private JFXCheckBox  onlyInborrow;
	
	@FXML private TableView<borrowTable> borrowtable;
	@FXML private TableColumn<borrowTable,String> borrowBookId;
	@FXML private TableColumn<borrowTable,String> borrowBookName;
	@FXML private TableColumn<borrowTable,String> borrowDeadline;
	@FXML private TableColumn<borrowTable,String> borrowState;
	@FXML private TableColumn<borrowTable,String> renew;
	ObservableList<borrowTable> borrowList;
	
	@FXML private TableView<bookTable> booktable;
	@FXML private TableColumn<bookTable,String> bookName;
	@FXML private TableColumn<bookTable,String> type;
	@FXML private TableColumn<bookTable,String> author;
	@FXML private TableColumn<bookTable,String> press;
	@FXML private TableColumn<bookTable,String> state;
	@FXML private TableColumn<bookTable,String> address;
	ObservableList<bookTable> bookList;
	
	@FXML private TableView<fineTable> finetable;
	@FXML private TableColumn<fineTable,String> fineid;
	@FXML private TableColumn<fineTable,String> finebookname;
	@FXML private TableColumn<fineTable,String> fineBookid;
	@FXML private TableColumn<fineTable,String> fineMoney;
	@FXML private TableColumn<fineTable,String> fineReason;
	@FXML private TableColumn<fineTable,String> finePay;
	ObservableList<fineTable> fineList;
	
	@FXML private TextField searchByName;
	@FXML private TextField searchByType;
	@FXML private TextField searchByAuthor;
	@FXML private TextField searchByPress;
	@FXML private JFXTextField  searchRes;
	
	@FXML private JFXCheckBox filterBorrow;
	@FXML private JFXToggleButton searchButton;
	
	@FXML private JFXCheckBox onlynotpay;
	@FXML private JFXTextField fineHelp;
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet resSet;
	
	public void initialize() {
		// ��ʼ�����߻�����Ϣ
		try {
			pstmt = loginController.conn.prepareStatement(Util.readerInfoStmt);
			pstmt.setString(1, loginController.id);
			resSet = pstmt.executeQuery();
			while(resSet.next()) {
				readerid.setText(resSet.getString("readerid"));
				readername.setText(resSet.getString("name"));
				readertype.setText(resSet.getString("type"));
				sex.setText(resSet.getString("sex"));
			}
			pstmt = loginController.conn.prepareStatement(Util.readerFineStmt);
			pstmt.setString(1, loginController.id);
			resSet = pstmt.executeQuery();
			if(resSet.next()) {
				if(resSet.getString(1)==null||resSet.getString(1).isEmpty())
					fine.setText("�޷���");
				else fine.setText(resSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//�󶨽��ı���borrowTable ������
		borrowBookId.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("bookid"));
		borrowBookName.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("bookname"));
		borrowDeadline.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("deadline"));
		borrowState.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("borrowState"));
		borrowList = FXCollections.observableArrayList();
		//���ý��ı�һ��Ϊ���谴ť
		Callback<TableColumn<borrowTable,String>, TableCell<borrowTable,String>> cellFactory
		= new Callback<TableColumn<borrowTable, String>, TableCell<borrowTable, String>>() {
			@Override
			public TableCell<borrowTable, String> call(final TableColumn<borrowTable, String> param) {
				final TableCell<borrowTable, String> cell = new TableCell<borrowTable, String>() {	
					final Button btn = new Button("����");
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							String borrowstate = getTableView().getItems().get(getIndex()).getBorrowState();
							btn.setOnAction(event -> {
								borrowTable borrow = getTableView().getItems().get(getIndex());
								onRenew(borrow,getIndex());
							});
							if(borrowstate.equals("�ѻ�")) setGraphic(null);
							else setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		renew.setCellFactory(cellFactory);
		
		//��д���ı�Ľ�����Ϣ				 
		try {
			pstmt = loginController.conn.prepareStatement(Util.borrowInfoStmt);
			pstmt.setString(1, loginController.id);
			resSet = pstmt.executeQuery();
			while(resSet.next()) {
				borrowTable borrowT = new borrowTable(resSet.getString("bookid"),
						resSet.getString("name"),resSet.getString("deadline"),
						resSet.getInt("renewtime"),resSet.getInt("returned"));
				borrowList.add(borrowT);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		borrowtable.setItems(borrowList);
		
		//�鼮������
		bookName.setCellValueFactory(new PropertyValueFactory<bookTable,String>("bookName"));
		type.setCellValueFactory(new PropertyValueFactory<bookTable,String>("type"));
		author.setCellValueFactory(new PropertyValueFactory<bookTable,String>("author"));
		press.setCellValueFactory(new PropertyValueFactory<bookTable,String>("press"));
		state.setCellValueFactory(new PropertyValueFactory<bookTable,String>("state"));
		address.setCellValueFactory(new PropertyValueFactory<bookTable,String>("address"));
		bookList = FXCollections.observableArrayList();
		
		//������Ϣ��
		fineid.setCellValueFactory(new PropertyValueFactory<fineTable,String>("borrowid"));
		finebookname.setCellValueFactory(new PropertyValueFactory<fineTable,String>("bookname"));
		fineBookid.setCellValueFactory(new PropertyValueFactory<fineTable,String>("bookid"));
		fineMoney.setCellValueFactory(new PropertyValueFactory<fineTable,String>("fine"));
		fineReason.setCellValueFactory(new PropertyValueFactory<fineTable,String>("reason"));
		finePay.setCellValueFactory(new PropertyValueFactory<fineTable,String>("pay"));
		fineList = FXCollections.observableArrayList();
		try {
			pstmt = loginController.conn.prepareStatement(Util.fineInfoStmt);
			pstmt.setString(1, loginController.id);
			resSet = pstmt.executeQuery();
			while(resSet.next()) {
				fineTable fb = new fineTable(resSet.getString("borrowid"),resSet.getString("name"),
						resSet.getString("bookid"),resSet.getString("fine"),resSet.getString("reason"),
						resSet.getInt("pay"));
				fineList.add(fb);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finetable.setItems(fineList);
		fineHelp.setText("��"+fineList.size()+"�������¼");
	}
	//��ʾȫ�����Ļ����ڽ��¼
	public void onBorrowShow() {
		try {
			if(onlyInborrow.isSelected())
				pstmt = loginController.conn.prepareStatement(Util.inborrowInfoStmt);
			else pstmt = loginController.conn.prepareStatement(Util.borrowInfoStmt);
			pstmt.setString(1, loginController.id);
			resSet = pstmt.executeQuery();
			borrowList.clear();
			while(resSet.next()) {
				borrowTable borrowT = new borrowTable(resSet.getString("bookid"),
						resSet.getString("name"),resSet.getString("deadline"),
						resSet.getInt("renewtime"),resSet.getInt("returned"));
				borrowList.add(borrowT);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//������蹦��
	public void onRenew(borrowTable borrow,int borrowListIndex) {
		int type=readertype.equals("������")?0:1; //ȷ��ѧ�����
		boolean canBorrow = Integer.valueOf(borrow.getRenew()) < (1+type); //��������������ж��Ƿ��ܼ�������
		if(canBorrow) {
			String borrowTime = borrow.getDeadline();
			LocalDateTime datetime = LocalDateTime.parse(borrowTime,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			datetime = datetime.plusDays(30);
			String newDDL = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); //�µĻ�������
			//���½��ı�
			borrowList.get(borrowListIndex).setDeadline(newDDL);
			borrowList.get(borrowListIndex).setRenew(borrow.getRenew()+1);
			//������д�ص����ݿ�
			try {
				pstmt = loginController.conn.prepareStatement(Util.updateRenewStmt);
				pstmt.setString(1, loginController.id);
				pstmt.setString(2, borrow.getBookid());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			Alert borrowAlert = new Alert(Alert.AlertType.INFORMATION,"����������Ѵ�����!\n",ButtonType.OK);
			borrowAlert.setTitle("����ʧ��");
			borrowAlert.show();	
		}
	}
	// ���߻�����Ϣѡ��ķ��ذ�ť
	public void onReturn() {
		Main.primaryStage.setScene(Main.scene);
	}
	//�鼮����
	public void onSearch() {	
		String condition="";
		String partCondition = searchByName.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "name like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = searchByType.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "type like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = searchByAuthor.getText(); 
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "author like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = searchByPress.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "press like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = "state='�ڼ���' ";
		if(filterBorrow.isSelected()) 
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		if(searchButton.isSelected()) {
			try {
				//debug
				System.out.println(condition);
				stmt = loginController.conn.createStatement();
				resSet = stmt.executeQuery(Util.searchBookStmt+condition);
				while(resSet.next()) {
					bookTable btable = new bookTable(resSet.getString("bookid"),
						resSet.getString("name"),resSet.getString("type"),
						resSet.getString("author"),resSet.getString("press"),
						resSet.getString("state"),resSet.getString("address"),
						resSet.getString("price"));
					bookList.add(btable);
				}
				booktable.setItems(bookList);
				searchRes.setText("��������"+Integer.toString(bookList.size())+"����¼");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else onClear();
	}
	//����鼮������Ϣ
	public void onClear() {
		searchByName.clear();
		searchByType.clear();
		searchByAuthor.clear();
		searchByPress.clear();
		searchRes.clear();
		bookList.clear();
	}
	//ѡ����ȫ�������δ������
	public void onFineShow() {
		fineList.clear();
		try {
			if(onlynotpay.isSelected())
				pstmt = loginController.conn.prepareStatement(Util.notpayFineInfoStmt);
			else pstmt = loginController.conn.prepareStatement(Util.fineInfoStmt);
			pstmt.setString(1, loginController.id);
			resSet = pstmt.executeQuery();
			while(resSet.next()) {
				fineTable fb = new fineTable(resSet.getString("borrowid"),resSet.getString("name"),
						resSet.getString("bookid"),resSet.getString("fine"),resSet.getString("reason"),
						resSet.getInt("pay"));
				fineList.add(fb);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finetable.setItems(fineList);
		fineHelp.setText("��"+fineList.size()+"�������¼");
	}
}
