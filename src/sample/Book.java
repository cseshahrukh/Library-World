package sample;

public class Book {
    String book_id;
    String name;
    String writer1, writer2, writer3;

    public String getWriter1() {
        return writer1;
    }

    public String getWriter2() {
        return writer2;
    }

    public String getWriter3() {
        return writer3;
    }

    String shelfNo;
    String roomNo;
    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }



    public void setWriter1(String writer1) {
        this.writer1 = writer1;
    }

    public void setWriter2(String writer2) {
        this.writer2 = writer2;
    }

    public void setWriter3(String writer3) {
        this.writer3 = writer3;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    String publisher;
    int price;
    int pages;
    String language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
