module se233.chapter5part2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;

    opens se233.chapter5part2 to javafx.fxml, org.junit.platform.commons;
    exports se233.chapter5part2;
}