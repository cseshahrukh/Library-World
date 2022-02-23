package sample;

//1-2 folder er sdk 11 er lib ta eikhane project er library te add korte hobe
/*
--module-path
"D:\BUET\1-2\CSE 108\Java\JavaFX\javafx-sdk-11.0.2\lib"
--add-modules javafx.sample.fxml,javafx.controls
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    // #FED700 Yellow
    // #887d6e grey
    public Stage stage;



    public Stage getStage() {
        return stage;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        //System.out.println("hello boss vaia");
        stage=primaryStage;


        home("sample.sample.fxml", null);



    }


    public static List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String query = "select * from users order by username";
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                User p = new User();
                p.setUser_name(rs.getString("username"));
                p.setPassword(rs.getString("password"));

                userList.add(p);
            }
        } catch (Exception e) {
            System.out.println("Exception in listProducts: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userList;
    }

    public static void listUsers() {
        List<User> productList = getUsers();
        for (User p : productList) {
            System.out.print(p.getUser_name());
            System.out.print("	");
            System.out.print(p.getPassword());
            System.out.print("	");
            //System.out.print(p.getFavChannel());
            //System.out.print("	");

        }

    }

    public static String getPassword(String user_id)
    {
        //System.out.println("Inside getPassword user_id is "+ user_id);
        String password="";
        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String query = String.format("select password from users where username = '%s'", user_id);
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                password=rs.getString("password");
            }
        } catch (Exception e) {
            System.out.println("Exception in password Search: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return password;
    }

    public static boolean validUser(String user_name)
    {
        System.out.println("inside valid user printing user_name "+user_name);
        String password; boolean flag=false;
        OracleConnect oc = null;
        try {
            //System.out.println("Inside try89");
            oc = new OracleConnect();
            //System.out.println("b");
            String query = String.format("select username from users");
            //System.out.println("c");
            ResultSet rs = oc.searchDB(query);
            //System.out.println("d");
            while (rs.next()) {
                System.out.println(rs.getString("username"));
                System.out.println("printing under valid user" +user_name);
                if (user_name.equals(rs.getString("username")))
                {
                    flag=true;
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in Oracle Connect: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (flag)
            System.out.println("this is valid user ");

        else
            System.out.println("this is not valid user ");
        return flag;
    }

    public static void addUser(String user_id, String password) {
        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String query = String.format("select * from users where username = '%s'", user_id);
            ResultSet rs = oc.searchDB(query);
            if (rs.next()) {
                //System.out.println("Password Not Found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("UserName Already in Database");
                alert.show();
            } else {
                System.out.println("insdie else of addUser");
                String insertQuery = String.format(
                        "insert into users (username, password) values ('%s', '%s')", user_id, password);

                oc.updateDB(insertQuery);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("UserName added");
                alert.show();
            }
            System.out.println("Product with this Id already exisits");
        } catch (Exception e) {
            System.out.println("Exception in addProduct: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void userLogged(String fxmlFile, String title, String username)
    {
        Parent root=null;
        if (username!=null )
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("logged-in.sample.fxml"));
                root = loader.load();

                // Loading the controller
                LoggedInController controller = loader.getController();
                controller.setMain(this);

                // Set the primary stage
                stage.setTitle("Login");
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Password Not Found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("UserName is Null");
            alert.show();
        }


    }

    public  void home(String fxmlFile, String user_name) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        // Loading the controller
        Controller controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();


    }

    public  void signUpHome() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sign-up.fxml"));
        Parent root = loader.load();

        // Loading the controller
        signUpController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 800, 650));
        stage.show();


    }


    public void logInUser(String username, String password) throws IOException {

        System.out.println("inside loginUser printing username"+ username);

        if (!validUser(username))
        {
            System.out.println("UserName Not Found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("UserNameNotFound");
            alert.show();
            return;

        }
        String actualPass=getPassword(username);
        if (actualPass.compareTo(password)!=0)
        {
            System.out.println("Password Not Found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PasswordNotFound");
            alert.show();
            return;
        }

        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("logged-in.fxml"));
            Parent root = loader.load();

            // Loading the controller
            LoggedInController controller = loader.getController();
            controller.username=username;
            controller.setMain(this);

            // Set the primary stage
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();

            //userLogged( "logged-in.sample.fxml", "Welcome!", username);
        }
    }

    public  void signUpUser(String username, String password) throws Exception {
            addUser(username, password);
    }




    public static void main(String[] args) {
        launch(args);
    }


}
