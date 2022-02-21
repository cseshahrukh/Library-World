package startup;

public class Book {
    public String bookid,bookname,publisher;
    public String writer1,writer2,writer3,shelf,room,comment;
    public int pages,price,subtotal;
    public int quantity,star;
    public int requestid,borrowid;


    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public String getDatebuy() {
        return datebuy;
    }

    public void setDatebuy(String datebuy) {
        this.datebuy = datebuy;
    }

    public String dateborrow,datetoreturn;
    public String datereturn;
    public int fines=20;
    public String userid,paydate,datebuy;
    public int payamount;

    public int getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(int borrowid) {
        this.borrowid = borrowid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public int getPayamount() {
        return payamount;
    }

    public void setPayamount(int payamount) {
        this.payamount = payamount;
    }



    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
    }

    public String getDateborrow() {
        return dateborrow;
    }

    public void setDateborrow(String dateborrow) {
        this.dateborrow = dateborrow;
    }

    public String getDatetoreturn() {
        return datetoreturn;
    }

    public void setDatetoreturn(String datetoreturn) {
        this.datetoreturn = datetoreturn;
    }

    public String getDatereturn() {
        return datereturn;
    }

    public void setDatereturn(String datereturn) {
        this.datereturn = datereturn;
    }

    public int getFines() {
        return fines;
    }

    public void setFines(int fines) {
        this.fines = fines;
    }



    public Book(String bookid,String bookname,String writer1,String writer2,String writer3,String shelf,String room) {
        this.bookid=bookid;
        this.bookname=bookname;
        //this.publisher=publisher;
        this.writer1=writer1;
        this.writer2=writer2;
        this.writer3=writer3;
        this.shelf=shelf;
        this.room=room;
    }
    public Book()
    {

    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getWriter1() {
        return writer1;
    }

    public void setWriter1(String writer1) {
        this.writer1 = writer1;
    }

    public String getWriter2() {
        return writer2;
    }

    public void setWriter2(String writer2) {
        this.writer2 = writer2;
    }

    public String getWriter3() {
        return writer3;
    }

    public void setWriter3(String writer3) {
        this.writer3 = writer3;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
/*public String toString()
    {
        return (regno+","+year+","+colour1+","+colour2+","+colour3+","+carmake+","+carmodel+","+price);
    }*/

}
