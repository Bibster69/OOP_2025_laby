module org.example.kolo2powtjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.kolo2powtjavafx to javafx.fxml;
    exports org.example.kolo2powtjavafx;
}