module be.ac.umons.firstg.segmentintersector {
    requires javafx.controls;
    requires javafx.fxml;


    opens be.ac.umons.firstg.segmentintersector to javafx.fxml;
    exports be.ac.umons.firstg.segmentintersector;
}