package application;

public class Util {
	public static String userPwdStmt="select name,pwd from reader where readerid = ?";
	public static String managerPwdStmt="select name,pwd from manager where managerid = ?";
	public static String searchBookStmt = "select * from book ";
	public static String readerFilterStmt = "select * from reader ";
	public static String readerInfoStmt = "select * from reader where readerid = ?";
	public static String borrowInfoStmt = "select book.bookid,name,deadline,returned,renewtime"
			+ " from borrow inner join book on"
			+ " borrow.bookid = book.bookid where borrow.readerid = ?";
	public static String inborrowInfoStmt = "select borrowid,price,book.bookid,name,deadline,"
			+ " returned,renewtime from borrow inner join book on"
			+ " borrow.bookid = book.bookid where borrow.readerid = ? and borrow.returned = 0";
	public static String inborrowNumStmt = "select COUNT(*) NUM from borrow where borrow.returned"
			+ " = 0 and borrow.readerid = ? ";
	public static String fineInfoStmt = "select fine.borrowid,name,borrow.bookid,fine,reason,pay "
			+ " from fine inner join borrow on fine.borrowid = borrow.borrowid inner join book on "
			+ " book.bookid = borrow.bookid where borrow.readerid = ? ";
	public static String notpayFineInfoStmt = "select fine.borrowid,name,borrow.bookid,fine,reason,pay "
			+ " from fine inner join borrow on fine.borrowid = borrow.borrowid inner join book on "
			+ " book.bookid = borrow.bookid where borrow.readerid = ? and pay = 0 ";//USE
	public static String updateRenewStmt = "update borrow set renewtime=renewtime+1,"
			+ "deadline=DATE_ADD(deadline,INTERVAL 30 DAY) where readerid = ? and bookid = ?";
	public static String readerFineStmt = "select SUM(fine) from fine inner join borrow on "
			+ "fine.borrowid = borrow.borrowid where readerid = ? and fine.pay = 0 ";
	public static String bookInfoStmt = "select * from book where bookid = ";
//	public static String fineTotalStmt = "select SUM(fine) as totalfine from fine where readerid = ?" ;
	public static String addBorrowStmt = "insert into borrow values(NULL,?,?,NOW(),DATE_ADD(NOW(),INTERVAL 30 DAY),0,0)";
	public static String bookReturnStmt = "update borrow set returned = 1 where borrowid = ? ";
	public static String addFineStmt = "insert into fine values(?,?,?,?)" ;
	public static String getFineInfoStmt = "select * from fine where readerid = ";
//	public static String deleteFineStmt ="delete from fine where readerid = ";
	public static String readerUpdateStmt = "update reader set name = ?,type=?,sex=?,pwd=? where readerid = ?";
	public static String bookUpdateStmt = "update book set name = ?,type = ?,author = ?,press = ?,"
			+ "price = ?,state = ?,address = ? where bookid = ?";
	public static String readerInsertStmt = "insert into reader values(?,?,?,?,?)";
	public static String bookInsertStmt = "insert into book values(?,?,?,?,?,?,?,?)";
	public static String readerDeleteStmt = "delete from reader where readerid = ?";
	public static String bookDeleteStmt = "delete from book where bookid = ?";
	//
	public static String overDayCheckStmt =  "select COUNT(*) from borrow where "
			+ "unix_timestamp(deadline) < unix_timestamp(NOW()) and returned = 0";
	public static String finePayStmt = "update fine set pay = 1 where exists(select * from borrow where"
			+ " borrow.borrowid = fine.borrowid and borrow.readerid = ? )";
}
