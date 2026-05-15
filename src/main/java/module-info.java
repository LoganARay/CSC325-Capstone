module edu.farmingdale.csc325capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires firebase.admin;
    requires google.cloud.firestore;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.api.apicommon;
    requires google.cloud.core;
    requires javafx.graphics;
    requires java.desktop;
    requires okhttp3;
    requires org.json;

    opens edu.farmingdale.csc325capstone.model to com.fasterxml.jackson.databind, google.cloud.firestore;



    opens edu.farmingdale.csc325capstone to javafx.fxml;
    exports edu.farmingdale.csc325capstone;
    exports edu.farmingdale.csc325capstone.ViewModel;
    opens edu.farmingdale.csc325capstone.ViewModel to javafx.fxml;
}