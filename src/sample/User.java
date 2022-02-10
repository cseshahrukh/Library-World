package sample;

import java.sql.Date;

public class User {
    private String user_name;
    private String password;

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCanBorrow() {
        return canBorrow;
    }

    public void setCanBorrow(String canBorrow) {
        this.canBorrow = canBorrow;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    //private String favChannel;
    private String isActive, canBorrow;
    private String expireDate;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //public String getFavChannel() {
        //return favChannel;
    //}

    //public void setFavChannel(String favChannel) {
        //this.favChannel = favChannel;
   // }
}
