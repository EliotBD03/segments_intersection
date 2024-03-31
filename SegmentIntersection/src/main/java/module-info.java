module ac.umons.be.firstg.segmentintersection {
    requires javafx.controls;
    requires javafx.fxml;


    opens ac.umons.be.firstg.segmentintersection to javafx.fxml;
    exports ac.umons.be.firstg.segmentintersection;
}