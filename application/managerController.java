package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class managerController {
	@FXML private JFXTextField borrowreaderid;
	@FXML private JFXTextField nameshow;
	@FXML private JFXTextField typeshow;
	@FXML private JFXCheckBox  readerSearchBtn;
	@FXML private JFXTextField borrowRes;
	@FXML private TableView<bookTable> booktable;
	@FXML private TableColumn<bookTable,String> borrowbookid;
	@FXML private TableColumn<bookTable,String> borrowname;
	@FXML private TableColumn<bookTable,String> borrowtype;
	@FXML private TableColumn<bookTable,String> borrowauthor;
	@FXML private TableColumn<bookTable,String> borrowstate;
	@FXML private TableColumn<bookTable,String> borrowpress;
	@FXML private TableColumn<bookTable,Boolean> toBorrow;
	ObservableList<bookTable> bookList;
	
	@FXML private TextField lookbyid;
	@FXML private TextField lookbyname;
	@FXML private TextField lookbytype;
	@FXML private TextField lookbyauthor;
	@FXML private TextField lookbypress;
	
	@FXML private JFXTextField returnReaderid;
	@FXML private JFXTextField returnReaderName;
	@FXML private JFXCheckBox  returnSearchBtn;
	
	@FXML private TableView<borrowTable> returnTable;
	@FXML private TableColumn<borrowTable,String> rborrowid;
	@FXML private TableColumn<borrowTable,String> rbookid;
	@FXML private TableColumn<borrowTable,String> rbookname;
	@FXML private TableColumn<borrowTable,String> rdeadline;
	@FXML private TableColumn<borrowTable,String> rexceedday;
	@FXML private TableColumn<borrowTable,Boolean> toReturn;
	@FXML private TableColumn<borrowTable,String> destroy;
	ObservableList<borrowTable> borrowList;
	
	@FXML private JFXTextField returnRes;
	
	@FXML private TextField fineReaderid;
	@FXML private JFXTextField fineReaderName;
	@FXML private JFXTextField fineReaderType;
	@FXML private JFXTextField shouldPay;
	@FXML private JFXCheckBox  onlyNotPay;
	@FXML private Button 	   onFineSearch;
	@FXML private JFXButton    payBtn;
	@FXML private JFXButton    fineClearBtn;
	@FXML private Label 	   fineRes;
	@FXML private TableView<fineTable>  finetable;
	@FXML private TableColumn<fineTable,String> fborrowid;
	@FXML private TableColumn<fineTable,String> fbookname;
	@FXML private TableColumn<fineTable,String> finemoney;
	@FXML private TableColumn<fineTable,String> finereason;
	@FXML private TableColumn<fineTable,String> paied;
	ObservableList<fineTable> fineList;
	
	@FXML private TableView<readerTable> readertable;
	@FXML private TableColumn<readerTable,String>  readeridInfo;
	@FXML private TableColumn<readerTable,String>  readernameInfo;
	@FXML private TableColumn<readerTable,String>  readersex;
	@FXML private TableColumn<readerTable,String>  readertype;
	@FXML private TableColumn<readerTable,String>  readerpwd;
	@FXML private TableColumn<readerTable,String>  readerdel;
	ObservableList<readerTable> readerList;
	
	@FXML private TextField rlookbyid;
	@FXML private TextField rlookbyname;
	@FXML private JFXComboBox<String> rlookbysex;
	@FXML private JFXComboBox<String> rlookbytype;
	@FXML private TextField rlookbypwd;
	
	@FXML private TableView<bookTable> bookInfotable;
	@FXML private TableColumn<bookTable,String> ibookid;
	@FXML private TableColumn<bookTable,String> ibookname;
	@FXML private TableColumn<bookTable,String> iauthor;
	@FXML private TableColumn<bookTable,String> itype;
	@FXML private TableColumn<bookTable,String> ipress;
	@FXML private TableColumn<bookTable,String> iprice;
	@FXML private TableColumn<bookTable,String> istate;
	@FXML private TableColumn<bookTable,String> ibookaddr;
	@FXML private TableColumn<bookTable,String> idelete;
	ObservableList<bookTable> bookInfoList;
	
	@FXML private TextField blookbyid;
	@FXML private TextField blookbyname;
	@FXML private TextField blookbyauthor;
	@FXML private TextField blookbytype;
	@FXML private TextField blookbypress;
	@FXML private TextField blookbyprice;
	@FXML private JFXComboBox<String> blookbystate;
	@FXML private TextField blookbyaddr;
	@FXML private TextField amount;
	
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resSet;
	
	private String borrowReaderidInput = null;
	private String returnReaderidInput = null;
	private String fineReader = null;
	public void initialize() {
		borrowbookid.setCellValueFactory(new PropertyValueFactory<bookTable,String>("bookid"));
		borrowname.setCellValueFactory(new PropertyValueFactory<bookTable,String>("bookName"));
		borrowtype.setCellValueFactory(new PropertyValueFactory<bookTable,String>("type"));
		borrowauthor.setCellValueFactory(new PropertyValueFactory<bookTable,String>("author"));
		borrowpress.setCellValueFactory(new PropertyValueFactory<bookTable,String>("press"));
		borrowstate.setCellValueFactory(new PropertyValueFactory<bookTable,String>("state"));
		toBorrow.setCellFactory(p -> {
		    TableCell<bookTable, Boolean> cell = new TableCell<bookTable, Boolean>() {
		        @Override
		        public void updateItem(Boolean item, boolean empty) {
		            if (empty) {
		                setGraphic(null);
		            } else {
		            	JFXCheckBox box = new JFXCheckBox();
		            	box.selectedProperty().addListener((obs, wasSelected, isSelected) -> 
		            	getTableView().getItems().get(getIndex()).setToBorrow(isSelected));
		            	if(getTableView().getItems().get(getIndex()).getToBorrow())
		            		box.setSelected(true);
		            	if(getTableView().getItems().get(getIndex()).getState().equals("已借出"))
		            		setGraphic(null);
		            	else setGraphic(box);
		            }
		        }
		    };
		    return cell ;
		});
		bookList = FXCollections.observableArrayList();
		
		rborrowid.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("borrowid"));
		rbookid.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("bookid"));
		rbookname.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("bookname"));
		rdeadline.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("deadline"));
		rexceedday.setCellValueFactory(new PropertyValueFactory<borrowTable,String>("exceedday"));
		toReturn.setCellFactory(p -> {
		    TableCell<borrowTable, Boolean> cell = new TableCell<borrowTable, Boolean>() {
		        @Override
		        public void updateItem(Boolean item, boolean empty) {
		            if (empty) {
		                setGraphic(null);
		            } else {
		            	JFXCheckBox box = new JFXCheckBox();
		            	box.selectedProperty().addListener((obs, wasSelected, isSelected) -> 
		            	getTableView().getItems().get(getIndex()).setToReturn(isSelected));
		            	if(getTableView().getItems().get(getIndex()).getToReturn())
		            		box.setSelected(true);
		        		else setGraphic(box);
		            }
		        }
		    };
		    return cell ;
		});
		destroy.setCellFactory(p -> {
		    TableCell<borrowTable,String> cell = new TableCell<borrowTable,String>() {
		        @Override
		        public void updateItem(String item, boolean empty) {
		            if (empty) {
		                setGraphic(null);
		                setText(null);
		            } else {
		            	JFXComboBox<String> box = new JFXComboBox<String>();
		            	box.getItems().add("无损");
		            	box.getItems().add("轻微损坏");
		            	box.getItems().add("重度损坏");
		            	box.setPrefWidth(getWidth()-1);
		            	box.getSelectionModel().select(0);
		            	box.getSelectionModel().selectedItemProperty().addListener((obs,oldv,newv) -> 
		            	getTableView().getItems().get(getIndex()).setDestroy(newv));
		        		setGraphic(box);
		        		setText(null);
		            }
		        }
		    };
		    return cell ;
		});
		borrowList= FXCollections.observableArrayList();
		if(fborrowid==null) System.out.println("error");
		fborrowid.setCellValueFactory(new PropertyValueFactory<fineTable,String>("borrowid"));
		fbookname.setCellValueFactory(new PropertyValueFactory<fineTable,String>("bookname"));
		finemoney.setCellValueFactory(new PropertyValueFactory<fineTable,String>("fine"));
		finereason.setCellValueFactory(new PropertyValueFactory<fineTable,String>("reason"));
		paied.setCellValueFactory(new PropertyValueFactory<fineTable,String>("pay"));
		fineList = FXCollections.observableArrayList();
		
		readeridInfo.setCellValueFactory(new PropertyValueFactory<readerTable,String>("readerid"));
		readernameInfo.setCellValueFactory(new PropertyValueFactory<readerTable,String>("name"));
		readertype.setCellValueFactory(new PropertyValueFactory<readerTable,String>("type"));
		readersex.setCellValueFactory(new PropertyValueFactory<readerTable,String>("sex"));
		readerpwd.setCellValueFactory(new PropertyValueFactory<readerTable,String>("pwd"));
		readernameInfo.setCellFactory(TextFieldTableCell.forTableColumn());
		readertype.setCellFactory(TextFieldTableCell.forTableColumn());
		readersex.setCellFactory(TextFieldTableCell.forTableColumn());
		readerpwd.setCellFactory(TextFieldTableCell.forTableColumn());
		readerdel.setCellFactory(p -> {
		    TableCell<readerTable, String> cell = new TableCell<readerTable, String>() {
		        @Override
		        public void updateItem(String item, boolean empty) {
		            if (empty) {
		                setGraphic(null);
		                setText(null);
		            } else {
		            	Button btn = new Button("删除");
		            	btn.setOnAction(e->{
		            		boolean res = deleteReader(getTableView().getItems().get(getIndex()).getReaderid());
		            		if(res) getTableView().getItems().remove(getIndex());});
		            	setGraphic(btn);
		            }
		        }
		    };
		    return cell ;
		});
		readerList = FXCollections.observableArrayList();
		rlookbytype.getItems().add("本科生");
		rlookbytype.getItems().add("研究生");
		rlookbysex.getItems().add("男");
		rlookbysex.getItems().add("女");
		
		ibookid.setCellValueFactory(new PropertyValueFactory<bookTable,String>("bookid"));
		ibookname.setCellValueFactory(new PropertyValueFactory<bookTable,String>("bookName"));
		iauthor.setCellValueFactory(new PropertyValueFactory<bookTable,String>("author"));
		itype.setCellValueFactory(new PropertyValueFactory<bookTable,String>("type"));
		ipress.setCellValueFactory(new PropertyValueFactory<bookTable,String>("press"));
		iprice.setCellValueFactory(new PropertyValueFactory<bookTable,String>("price"));
		istate.setCellValueFactory(new PropertyValueFactory<bookTable,String>("state"));
		ibookaddr.setCellValueFactory(new PropertyValueFactory<bookTable,String>("address"));
		idelete.setCellFactory(p -> {
		    TableCell<bookTable, String> cell = new TableCell<bookTable, String>() {
		        @Override
		        public void updateItem(String item, boolean empty) {
		            if (empty) {
		                setGraphic(null);
		                setText(null);
		            } else {
		            	Button btn = new Button("删除");
		            	btn.setOnAction(e->{
		            		deleteBook(getTableView().getItems().get(getIndex()).getBookid());
		            		getTableView().getItems().remove(getIndex());});
		            	if(getTableView().getItems().get(getIndex()).getState().equals("在架上"))
		            		setGraphic(btn);
		            	else setGraphic(null);
		            }
		        }
		    };
		    return cell ;
		});
		ibookname.setCellFactory(TextFieldTableCell.forTableColumn());
		iauthor.setCellFactory(TextFieldTableCell.forTableColumn());
		itype.setCellFactory(TextFieldTableCell.forTableColumn());
		ipress.setCellFactory(TextFieldTableCell.forTableColumn());
		iprice.setCellFactory(TextFieldTableCell.forTableColumn());
		istate.setCellFactory(TextFieldTableCell.forTableColumn());
		ibookaddr.setCellFactory(TextFieldTableCell.forTableColumn());
		bookInfoList = FXCollections.observableArrayList();
		blookbystate.getItems().add("在架上");
		blookbystate.getItems().add("已借出");
	}
	
	public void onSearchReader() {
		if(readerSearchBtn.isSelected()) {
			String readerid = borrowreaderid.getText();
			//DEBUG
			System.out.println(readerid);
			if(readerid==null||readerid.isEmpty()) {
				nameshow.setStyle("-fx-background-color: pink");
				typeshow.setStyle("-fx-background-color: pink");
				return;
			}
			try {
				pstmt = loginController.conn.prepareStatement(Util.readerInfoStmt);
				pstmt.setString(1,readerid);
				resSet = pstmt.executeQuery();
				if(resSet.next()) {
					nameshow.setText(resSet.getString("name"));
					typeshow.setText(resSet.getString("type"));
					borrowReaderidInput = readerid;
				}
				else {
					nameshow.setStyle("-fx-background-color: pink");
					typeshow.setStyle("-fx-background-color: pink");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		else {
			borrowreaderid.clear();
			nameshow.clear();
			typeshow.clear();
			nameshow.setStyle(null);
			typeshow.setStyle(null);
			borrowReaderidInput = null;
		}
	}	
	
	public void onLook() {
		String condition="";
		String partCondition = lookbyid.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "bookid like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = lookbyname.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "name like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = lookbytype.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "type like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = lookbyauthor.getText(); 
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "author like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = lookbypress.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "press like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		//DEBUG
		System.out.println(condition);
		bookList.clear();
		try {
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
			booktable.getItems().setAll(bookList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void onBookClear() {
		lookbyid.clear();
		lookbyname.clear();
		lookbyauthor.clear();
		lookbypress.clear();
		lookbytype.clear();
		bookList.clear();
		booktable.getItems().clear();
		borrowRes.clear();
	}
	public void onExit() {
		Main.primaryStage.setScene(Main.scene);
	}
	public void onBorrow() {
		if(borrowReaderidInput==null||borrowReaderidInput.isEmpty())
			borrowRes.setText("请先输入有效的读者ID。");
		else {
			//检查是否有过期未交书籍以及罚款未交
			try {
				pstmt = loginController.conn.prepareStatement(Util.overDayCheckStmt);
				resSet = pstmt.executeQuery();
				if(resSet.next()&&resSet.getInt(1)>0) {
					borrowRes.setText("请先还回将过期书籍然后借书。");
					return;
				}
				pstmt = loginController.conn.prepareStatement(Util.notpayFineInfoStmt);
				pstmt.setString(1, borrowReaderidInput);
				resSet = pstmt.executeQuery();
				if(resSet.next()) {
					borrowRes.setText("请先将罚款交付之后再借书。");
					return;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			int borrownum=0;
			for(bookTable book:bookList) {
				if(book.getToBorrow())
					borrownum++;
			}
			//检查是否超过数额
			int borrowlimit = typeshow.getText().equals("本科生")?5:10;
			try {
				pstmt = loginController.conn.prepareStatement(Util.inborrowNumStmt);
				pstmt.setString(1, borrowReaderidInput);
				resSet = pstmt.executeQuery();
				if(resSet.next()) {
					borrownum+=resSet.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		   
			if(borrownum==0)
				borrowRes.setText("请选择要借的书籍。");
			else if(borrownum>borrowlimit)
				borrowRes.setText("所借书数目超标。");
			else {
				borrowRes.setText("该读者总计借书"+borrownum+"本");
				for(bookTable book:bookList) {
					if(book.getToBorrow()) {
						try {
							pstmt = loginController.conn.prepareStatement(Util.addBorrowStmt);
							pstmt.setString(1, borrowReaderidInput);
							pstmt.setString(2, book.getBookid());
							pstmt.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				onLook();
			}
		}
	}
	
	public void onRreaderSearch() {
		if(returnSearchBtn.isSelected()) {
			String readerid = returnReaderid.getText();
			//DEBUG
			System.out.println(readerid);
			if(readerid==null||readerid.isEmpty()) {
				returnReaderName.setStyle("-fx-background-color: pink");
				return;
			}
			try {
				pstmt = loginController.conn.prepareStatement(Util.readerInfoStmt);
				pstmt.setString(1,readerid);
				resSet = pstmt.executeQuery();
				if(resSet.next()) {
					returnReaderName.setText(resSet.getString("name"));
					returnReaderidInput = readerid;
					borrowShow();
				}
				else 
					returnReaderName.setStyle("-fx-background-color: pink");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		else {
			returnReaderid.clear();
			returnReaderName.clear();
			returnReaderName.setStyle(null);
			returnReaderidInput = null;
			returnRes.clear();
			borrowList.clear();
			returnTable.getItems().clear();
			System.out.println("now borrowlist is empty");
		}
	}
	public void borrowShow() {
		try {
			pstmt = loginController.conn.prepareStatement(Util.inborrowInfoStmt);
			pstmt.setString(1, returnReaderidInput);
			resSet = pstmt.executeQuery();
			if(borrowList!=null) borrowList.clear();
			while(resSet.next()) {
				borrowTable borrowT = new borrowTable(resSet.getString("bookid"),
						resSet.getString("name"),resSet.getString("deadline"),
						resSet.getInt("renewtime"),resSet.getInt("returned"));
				borrowT.setBorrowid(resSet.getString("borrowid"));
				borrowT.setPrice(resSet.getDouble("price"));
				//计算超期天数
				String deadlineday = resSet.getString("deadline").substring(0,10);
				LocalDate endDay = LocalDate.parse(deadlineday);
				LocalDate toDay = LocalDate.now();
				int exceedDay = endDay.isAfter(toDay)?0:
					(int)Duration.between(endDay.atStartOfDay(),toDay.atStartOfDay()).toDays();
				//设置超期天数
				borrowT.setExceedday(String.valueOf(exceedDay));
				borrowList.add(borrowT);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		returnTable.getItems().setAll(borrowList);
	}
	public void onReturnBook() {
		boolean shouldfine=false;
		int cnt=0;
		for(borrowTable borrow:borrowList) {
			if(borrow.getToReturn()) {
				cnt++;
				double fineMoney = 0;
				double price = borrow.getPrice();
				int    exceedday = Integer.valueOf(borrow.getExceedday());
				String destroy = borrow.getDestroy();
				String fineReason = "";
				System.out.println(destroy); //debug
				if(destroy.equals("轻微损坏")) 
					{shouldfine=true;fineMoney+=price/5;fineReason+="轻微损坏";}
				else if(destroy.equals("重度损坏")) 
					{shouldfine=true;fineMoney+=price;fineReason+="重度损坏";}
				else if(exceedday>0) 
					{shouldfine=true;fineMoney+=exceedday;fineReason+="超期";}
				try {
					pstmt = loginController.conn.prepareStatement(Util.bookReturnStmt);
					pstmt.setString(1, borrow.getBorrowid());
					pstmt.executeUpdate();
					if(fineMoney>0) {
						pstmt = loginController.conn.prepareStatement(Util.addFineStmt);
						pstmt.setString(1, borrow.getBorrowid());
						pstmt.setString(2, String.valueOf(fineMoney));
						pstmt.setString(3, fineReason);
						pstmt.setString(4, "0");
						//debug
						System.out.println(borrow.getBorrowid());
						pstmt.executeUpdate();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(cnt==0)
			returnRes.setText("请选择要还的书籍");
		else if(shouldfine)
			returnRes.setText("还书成功，请尽快缴纳罚款。");
		else returnRes.setText("还书成功。");
		borrowShow();
	}
	
	public void onFineSearch() {
		fineReader = fineReaderid.getText();
		if(fineReader==null||fineReader.isEmpty())
			fineRes.setText("请先输入读者编号");
		else {
			fineList.clear();
			try {
				pstmt = loginController.conn.prepareStatement(Util.readerInfoStmt);
				pstmt.setString(1, fineReader);
				resSet = pstmt.executeQuery();
				if(resSet.next()) {
					fineReaderName.setText(resSet.getString("name"));
					fineReaderType.setText(resSet.getString("type"));
				}
				else {fineRes.setText("该读者不存在，请重新输入。");return;}
				//计算总的罚款
				pstmt = loginController.conn.prepareStatement(Util.readerFineStmt);
				pstmt.setString(1, fineReader);
				resSet = pstmt.executeQuery();
				if(resSet.next()&&resSet.getDouble(1)>0) 
					shouldPay.setText(String.valueOf(resSet.getDouble(1)));
				else shouldPay.setText("无罚款");
				if(onlyNotPay.isSelected())
					pstmt = loginController.conn.prepareStatement(Util.notpayFineInfoStmt);
				else pstmt = loginController.conn.prepareStatement(Util.fineInfoStmt);
				pstmt.setString(1, fineReader);
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
			fineRes.setText("共"+fineList.size()+"条罚款记录");
		}
	}
	
	public void onFineClear() {
		fineReaderid.clear();
		fineReaderName.clear();
		fineReaderType.clear();
		shouldPay.clear();
		fineRes.setText(null);
		fineReader = null;
	}
	public void onPay() {
		if(fineList.isEmpty())
			fineRes.setText("用户不存在或该用户无需缴纳罚款");
		else {
			try {
				pstmt = loginController.conn.prepareStatement(Util.finePayStmt);
				pstmt.setString(1, fineReader);
				pstmt.executeUpdate();
				fineRes.setText("缴纳成功。");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			onFineSearch();
		}
	}
	//删除一个读者
	private boolean deleteReader(String readerid) {
		try {
			pstmt = loginController.conn.prepareStatement(Util.readerDeleteStmt);
			pstmt.setString(1, readerid);
			System.out.println(pstmt.executeUpdate());
			return true;
		} catch (SQLException e) {
			Alert delAlert = new Alert(Alert.AlertType.INFORMATION,"该读者还有书籍未还或罚款未交，不予删除!\n",ButtonType.OK);
			delAlert.setTitle("删除失败");
			delAlert.show();
			return false;
		}
	}
	//查找读者
	public void onReaderSearch() {
		String condition="";
		String partCondition = rlookbyid.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "readerid like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = rlookbyname.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "name like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = rlookbytype.getSelectionModel().getSelectedItem();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "type like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = rlookbysex.getSelectionModel().getSelectedItem();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "sex like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = rlookbypwd.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "pwd like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		//debug
		System.out.println(condition);
		readerList.clear();
		try {
			pstmt  = loginController.conn.prepareStatement(Util.readerFilterStmt+condition);
			resSet = pstmt.executeQuery();
			while(resSet.next()) {
				readerTable rt = new readerTable(resSet.getString("readerid"),
				resSet.getString("name"),resSet.getString("sex"),resSet.getString("type"),
				resSet.getString("pwd"));
				readerList.add(rt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		readertable.getItems().setAll(readerList);
	}
	//清空输入
	public void onReaderClear() {
		rlookbyname.clear();
		rlookbyid.clear();
		rlookbytype.getSelectionModel().clearAndSelect(-1);
		rlookbysex.getSelectionModel().clearAndSelect(-1);
		rlookbypwd.clear();
	}
	//清空所有读者
	public void onReaderDelAll() {
		boolean res = true;
		for(readerTable reader:readerList) {
			try {
				pstmt = loginController.conn.prepareStatement(Util.readerDeleteStmt);
				pstmt.setString(1, reader.getReaderid());
				System.out.println(pstmt.executeUpdate());
			} catch (SQLException e) {
				res = false;
			}
			//debug
			System.out.println(reader.getName());
		}
		if(res==false) {
			Alert delAlert = new Alert(Alert.AlertType.INFORMATION,"部分读者删除失败!\n",ButtonType.OK);
			delAlert.setTitle("删除失败");
			delAlert.show();
		}
		else onReaderSearch();
	}
	//添加一个读者
	public void onReaderAdd() {
		String id = rlookbyid.getText();
		String name = rlookbyname.getText();
		String type = rlookbytype.getSelectionModel().getSelectedItem();
		String sex = rlookbysex.getSelectionModel().getSelectedItem();
		String pwd = rlookbypwd.getText();
		if(id==null||name==null||type==null||sex==null||pwd==null
		   ||id.isEmpty()||name.isEmpty()||type.isEmpty()||sex.isEmpty()||pwd.isEmpty()) {
			Alert addAlert = new Alert(Alert.AlertType.INFORMATION,"输入信息不完善!\n",ButtonType.OK);
			addAlert.setTitle("添加失败");
			addAlert.show();	
			return ;
		}
		try {
			pstmt = loginController.conn.prepareStatement(Util.readerInsertStmt);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, sex);
			pstmt.setString(5, type);
			System.out.println(pstmt.executeUpdate());
		} catch (SQLException e) {
			Alert borrowAlert = new Alert(Alert.AlertType.INFORMATION,"读者添加失败，请检查输入数据的合法性。!\n",ButtonType.OK);
			borrowAlert.setTitle("添加失败");
			borrowAlert.show();	
		}
		onReaderSearch();
	}
	//修改一个读者
	public void onReaderMod(CellEditEvent<readerTable, String> t) {
		readerTable rt = t.getTableView().getItems().get(t.getTablePosition().getRow());
		int col = t.getTablePosition().getColumn();
		switch(col) {
		case 1:rt.setName(t.getNewValue());break;
		case 2:rt.setSex(t.getNewValue());break;
		case 3:rt.setType(t.getNewValue());break;
		case 4:rt.setPwd(t.getNewValue());break;
		}
		try {
			pstmt = loginController.conn.prepareStatement(Util.readerUpdateStmt);
			pstmt.setString(1, rt.getName());
			pstmt.setString(2, rt.getType());
			pstmt.setString(3, rt.getSex());
			pstmt.setString(4, rt.getPwd());
			pstmt.setString(5, rt.getReaderid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Alert borrowAlert = new Alert(Alert.AlertType.INFORMATION,"读者修改失败，请检查修改数据的合法性。!\n",ButtonType.OK);
			borrowAlert.setTitle("修改失败");
			borrowAlert.show();
		}
	}
	
	//查询书
	public void onBookSearch() {
		String condition="";
//		String partCondition = blookbyid.getText();
//		if(partCondition!=null&&!partCondition.isEmpty()) {
//			partCondition = "bookid like '%"+partCondition+"%'";
//			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
//		}
		String partCondition = blookbyname.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "name like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = blookbytype.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "type like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = blookbyauthor.getText(); 
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "author like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = blookbypress.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "press like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = blookbystate.getSelectionModel().getSelectedItem();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "state like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		partCondition = blookbyaddr.getText();
		if(partCondition!=null&&!partCondition.isEmpty()) {
			partCondition = "address like '%"+partCondition+"%'";
			condition+=condition.isEmpty()?" where "+partCondition:" and "+partCondition;
		}
		bookInfoList.clear();
		try {
			stmt = loginController.conn.createStatement();
			resSet = stmt.executeQuery(Util.searchBookStmt+condition);
			//debug
			System.out.println(Util.searchBookStmt+condition);
			while(resSet.next()) {
				bookTable btable = new bookTable(resSet.getString("bookid"),
					resSet.getString("name"),resSet.getString("type"),
					resSet.getString("author"),resSet.getString("press"),
					resSet.getString("state"),resSet.getString("address"),
					resSet.getString("price"));
				bookInfoList.add(btable);
			}
			bookInfotable.getItems().setAll(bookInfoList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//删除一本书
	private void deleteBook(String bookid) {
		try {
			pstmt = loginController.conn.prepareStatement(Util.bookDeleteStmt);
			pstmt.setString(1, bookid);
			System.out.println(pstmt.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//删除所有书籍
	public void onDeleteAllBook() {
		boolean ok = false;
		for(bookTable book:bookInfoList) {
			if(book.getState().equals("在架上"))
			try {
				pstmt = loginController.conn.prepareStatement(Util.bookDeleteStmt);
				pstmt.setString(1, book.getBookid());
				pstmt.executeUpdate();
				ok=true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ok) onBookSearch();
	}
	//清除输入
	public void onBookInputClear() {
		blookbyid.clear();
		blookbyname.clear();
		blookbytype.clear();
		blookbyauthor.clear();
		blookbyprice.clear();
		amount.clear();
		blookbystate.getSelectionModel().clearAndSelect(-1);
		blookbypress.clear();
		blookbyaddr.clear();
	}
	//新书录入
	public void onBookAdd() {
		int booknum=1;
		String id = blookbyid.getText();
		String name = blookbyname.getText();
		String type = blookbytype.getText();
		String author = blookbyauthor.getText();
		String price = blookbyprice.getText();
		String press = blookbypress.getText();
		String addr = blookbyaddr.getText();
		String state = blookbystate.getSelectionModel().getSelectedItem();
		String num = amount.getText();
		if(id==null||name==null||type==null||author==null||
			price==null||press==null||addr==null||state==null||
			id.isEmpty()||name.isEmpty()||type.isEmpty()||author.isEmpty()||
			price.isEmpty()||press.isEmpty()||addr.isEmpty()||state.isEmpty()) {
			Alert addAlert = new Alert(Alert.AlertType.INFORMATION,"输入信息不完善\n",ButtonType.OK);
			addAlert.setTitle("录入失败");
			addAlert.show();
			return;
		}
		if(num!=null&&!num.isEmpty()) {
			try {
				booknum = Integer.valueOf(num);
			}catch(NumberFormatException e) {
				Alert addAlert = new Alert(Alert.AlertType.INFORMATION,"数量需要是整数形式\n",ButtonType.OK);
				addAlert.setTitle("录入失败");
				addAlert.show();
				return;
			}
		}
		int idnum;
		try {
			idnum = Integer.valueOf(id);
		}catch(NumberFormatException e) {
			Alert addAlert = new Alert(Alert.AlertType.INFORMATION,"编号需要是整数形式\n",ButtonType.OK);
			addAlert.setTitle("录入失败");
			addAlert.show();
			return;
		}
		while(booknum>0) {
			try {
				pstmt  = loginController.conn.prepareStatement(Util.bookInsertStmt);
				pstmt.setString(1, String.format("%06d", idnum++));
				pstmt.setString(2, name);
				pstmt.setString(3, type);
				pstmt.setString(4, author);
				pstmt.setString(5, press);
				pstmt.setString(6, price);
				pstmt.setString(7, addr);
				pstmt.setString(8, state);
				pstmt.executeUpdate();
				booknum--;
			} catch (SQLException e) {
				Alert addAlert = new Alert(Alert.AlertType.INFORMATION,booknum+"本书尚未插入，请检查数据如id的合法性。\n",ButtonType.OK);
				addAlert.setTitle("录入失败");
				addAlert.show();
				return;
			}
		}
		onBookSearch();
	}
	
	//修改一本书
	public void onBookMod(CellEditEvent<bookTable, String> t) {
		bookTable rt = t.getTableView().getItems().get(t.getTablePosition().getRow());
		int col = t.getTablePosition().getColumn();
		switch(col) {
		case 1:rt.setBookName(t.getNewValue());break;
		case 2:rt.setAuthor(t.getNewValue());break;
		case 3:rt.setType(t.getNewValue());break;
		case 4:rt.setPress(t.getNewValue());break;
		case 5:rt.setPrice(t.getNewValue());break;
		case 6:rt.setState(t.getNewValue());break;
		case 7:rt.setAddress(t.getNewValue());break;
		}
		try {
			pstmt = loginController.conn.prepareStatement(Util.bookUpdateStmt);
			pstmt.setString(1, rt.getBookName());
			pstmt.setString(2, rt.getType());
			pstmt.setString(3, rt.getAuthor());
			pstmt.setString(4, rt.getPress());
			pstmt.setString(5, rt.getPrice());
			pstmt.setString(6, rt.getState());
			pstmt.setString(7, rt.getAddress());
			pstmt.setString(8, rt.getBookid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			Alert borrowAlert = new Alert(Alert.AlertType.INFORMATION,"图书修改失败，请检查修改数据的合法性。!\n",ButtonType.OK);
			borrowAlert.setTitle("修改失败");
			borrowAlert.show();
		}
	}
}
