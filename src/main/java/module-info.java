module edu.farmingdale.csc325capstone {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.farmingdale.csc325capstone to javafx.fxml;
    exports edu.farmingdale.csc325capstone;
}