module edu.farmingdale.csc325capstone {
    requires javafx.controls;
    requires javafx.fxml;

    requires firebase.admin;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;
    requires java.net.http;

    opens edu.farmingdale.csc325capstone to javafx.fxml;
    exports edu.farmingdale.csc325capstone;
}