package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SellqtyminiwindowController {
    public TextField bookquantityfld;
    public int quantity;
    public SellwithoutreservController sellcon;

    public void okbtnclicked(ActionEvent actionEvent) {
        quantity=Integer.parseInt(bookquantityfld.getText());
        Stage stage=(Stage) bookquantityfld.getScene().getWindow();
        stage.hide();
        sellcon.addcart(quantity);

    }
}
