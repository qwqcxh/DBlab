package application;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class bookTable {
	private final StringProperty bookid;
	private final StringProperty bookName;
	private final StringProperty type;
	private final StringProperty author;
	private final StringProperty press;
	private final StringProperty state;
	private final StringProperty address;
	private final BooleanProperty toborrow;
	private final StringProperty price;
	
	public bookTable(String bookid,String bookName,String type,String author,String press,String state,String address,String price) {
		this.bookid   = new SimpleStringProperty(this,"bookid",bookid);
        this.bookName = new SimpleStringProperty(this,"bookName", bookName);
        this.type 	  = new SimpleStringProperty(this,"type", type);
        this.author   = new SimpleStringProperty(this,"author",author);
        this.press    = new SimpleStringProperty(this,"press",press);
        this.state    = new SimpleStringProperty(this,"state",state);
        this.address  = new SimpleStringProperty(this,"address",address);
        this.toborrow = new SimpleBooleanProperty(this,"toborrow",false);
        this.price    = new SimpleStringProperty(this,"price",price);
    }

	public final String getBookid() { return this.bookid.get();}
    public final void setBookid(String value) {this.bookid.set(value);}
    public final StringProperty bookidProperty() {return this.bookid;}
    
    public final String getBookName() { return this.bookName.get();}
    public final void setBookName(String value) {this.bookName.set(value);}
    public final StringProperty bookNameProperty() {return this.bookName;}

    public final String getType() {return this.type.get();}
    public final void setType(String value) {this.type.set(value);}
    public final StringProperty typeProperty() {return this.type;}
    
    public final String getAuthor() {return this.author.get();}
    public final void setAuthor(String value) {this.author.set(value);}
    public final StringProperty authorProperty() {return this.author;}
    
    public final String getPress() {return this.press.get();}
    public final void setPress(String value) {this.press.set(value);}
    public final StringProperty pressProperty() {return this.press;}
    
    public final String getState() {return this.state.get();}
    public final void setState(String value) {this.state.set(value);}
    public final StringProperty stateProperty() {return this.state;}
    
    public final String getAddress() {return this.address.get();}
    public final void setAddress(String value) {this.address.set(value);}
    public final StringProperty addressProperty() {return this.address;}
    
    public final boolean getToBorrow() {return this.toborrow.get();}
    public final void setToBorrow(boolean value) {this.toborrow.set(value);}
    public final BooleanProperty toBorrowProperty() {return this.toborrow;} 
    
    public final String getPrice() {return this.price.get();}
    public final void setPrice(String value) {this.price.set(value);}
    public final StringProperty priceProperty() {return this.price;} 
}
