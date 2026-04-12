module edu.farmingdale.csc325capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires google.cloud.core;
    requires com.google.api.apicommon;
    requires com.google.auth;


    opens edu.farmingdale.csc325capstone to javafx.fxml;
    exports edu.farmingdale.csc325capstone;
}