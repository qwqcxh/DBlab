package application;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class readerTable {
	private final StringProperty readerid;
	private final StringProperty name;
	private final StringProperty sex;
	private final StringProperty type;
	private final StringProperty pwd;
	private final BooleanProperty toDelete;
	
	public readerTable(String readerid,String name,String sex,String type,String pwd) {
		this.readerid   = new SimpleStringProperty(this,"readerid",readerid);
        this.name 	    = new SimpleStringProperty(this,"name",name);
        this.type 	  = new SimpleStringProperty(this,"type", type);
        this.sex   = new SimpleStringProperty(this,"sex",sex);
        this.pwd    = new SimpleStringProperty(this,"pwd",pwd);
        this.toDelete    = new SimpleBooleanProperty(this,"toDelete",false);
    }

	public final String getReaderid() { return this.readerid.get();}
    public final void setReaderid(String value) {this.readerid.set(value);}
    public final StringProperty readeridProperty() {return this.readerid;}
    
    public final String getName() { return this.name.get();}
    public final void setName(String value) {this.name.set(value);}
    public final StringProperty nameProperty() {return this.name;}

    public final String getType() {return this.type.get();}
    public final void setType(String value) {this.type.set(value);}
    public final StringProperty typeProperty() {return this.type;}
    
    public final String getSex() {return this.sex.get();}
    public final void setSex(String value) {this.sex.set(value);}
    public final StringProperty sexProperty() {return this.sex;}
    
    public final String getPwd() {return this.pwd.get();}
    public final void setPwd(String value) {this.pwd.set(value);}
    public final StringProperty pwdProperty() {return this.pwd;}
        
    public final boolean getToDelete() {return this.toDelete.get();}
    public final void setToDelete(boolean value) {this.toDelete.set(value);}
    public final BooleanProperty toDeleteProperty() {return this.toDelete;} 
}
