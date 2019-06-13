package application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class borrowTable {
	private StringProperty borrowid;
	private StringProperty exceedday;
	private final StringProperty bookid;
	private final StringProperty bookname;
	private final StringProperty deadline;
	private final StringProperty borrowState;
	private final BooleanProperty toReturn;
	private final StringProperty destroy;
	private int renew;
	private double price;
	
	public borrowTable(String bookid,String bookname,String deadline,int renew,int returned) {
        this.bookid   = new SimpleStringProperty(this,"bokid", bookid);
        this.bookname = new SimpleStringProperty(this,"bookname",bookname);
        this.deadline = new SimpleStringProperty(this,"deadline",deadline);
        this.borrowState = new SimpleStringProperty(this,"returned",returned==1?"已还":"在借");
        this.toReturn = new SimpleBooleanProperty(this,"toReturn",false);
        this.destroy = new SimpleStringProperty(this,"destroy","无损");
        this.borrowid = new SimpleStringProperty(this,"borrowid","0");
        this.exceedday = new SimpleStringProperty(this,"exceedday","0");
        this.renew    = renew;
    }
	public final String getExceedday() { return this.exceedday.get();}
    public final void setExceedday(String value) {this.exceedday.set(value);}
    public final StringProperty exceeddayProperty() {return this.exceedday;}
    
	public final String getBorrowid() { return this.borrowid.get();}
    public final void setBorrowid(String value) {this.borrowid.set(value);}
    public final StringProperty borrowidProperty() {return this.borrowid;}
	
    public final String getBookid() { return this.bookid.get();}
    public final void setBookid(String value) {this.bookid.set(value);}
    public final StringProperty bookidProperty() {return this.bookid;}

    public final String getBookname() {return this.bookname.get();}
    public final void setType(String value) {this.bookname.set(value);}
    public final StringProperty booknameProperty() {return this.bookname;}
    
    public final String getDeadline() {return this.deadline.get();}
    public final void setDeadline(String value) {this.deadline.set(value);}
    public final StringProperty deadlineProperty() {return this.deadline;}
    
    public final String getBorrowState() {return this.borrowState.get();}
    public final void setBorrowState(String value) {this.borrowState.set(value);}
    public final StringProperty borrowStateProperty() {return this.borrowState;}
    
    public final boolean getToReturn() {return this.toReturn.get();}
    public final void setToReturn(boolean value) {this.toReturn.set(value);}
    public final BooleanProperty toReturnProperty() {return this.toReturn;}
    
    public final String getDestroy() {return this.destroy.get();}
    public final void setDestroy(String value) {this.destroy.set(value);}
    public final StringProperty destroyProperty() {return this.destroy;}
    
    public int getRenew() {return this.renew;}
    public void setRenew(int value) {this.renew = value;}
    
    public double getPrice() {return this.price;}
    public void setPrice(double price) {this.price = price;}
}
