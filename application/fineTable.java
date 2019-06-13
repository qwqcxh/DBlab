package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class fineTable {
	private final StringProperty borrowid;
	private final StringProperty bookid;
	private final StringProperty bookname;
	private final StringProperty fine;
	private final StringProperty reason;
	private final StringProperty pay  ;
	
	public fineTable(String borrowid,String finebookname,String finebookid,String fine,String reason,int pay) {
        this.borrowid = new SimpleStringProperty(this,"borrowid", borrowid);
        this.bookname = new SimpleStringProperty(this,"readerid",finebookname);
        this.bookid   = new SimpleStringProperty(this,"bookid",finebookid);
        this.fine     = new SimpleStringProperty(this,"fine",fine);
        this.reason   = new SimpleStringProperty(this,"reason",reason);
        this.pay      = new SimpleStringProperty(this,"pay",pay==1?"ÊÇ":"·ñ");
    }

    public final String getBorrowid() { return this.borrowid.get();}
    public final void setBorrowid(String value) {this.borrowid.set(value);}
    public final StringProperty borrowProperty() {return this.borrowid;}
    
    public final String getBookname() {return this.bookname.get();}
    public final void setReaderid(String value) {this.bookname.set(value);}
    public final StringProperty readeridProperty() {return this.bookname;}
    
    public final String getBookid() {return this.bookid.get();}
    public final void setBookid(String value) {this.bookid.set(value);}
    public final StringProperty bookidProperty() {return this.bookid;}
    
    public final String getFine() {return this.fine.get();}
    public final void setFine(String value) {this.fine.set(value);}
    public final StringProperty fineProperty() {return this.fine;}
    
    public final String getReason() {return this.reason.get();}
    public final void setReason(String value) {this.reason.set(value);}
    public final StringProperty reasonProperty() {return this.reason;}
    
    public final String getPay() {return this.pay.get();}
    public final void setPay(String value) {this.pay.set(value);}
    public final StringProperty payProperty() {return this.pay;}
}
