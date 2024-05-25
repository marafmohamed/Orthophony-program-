module esi.tp.tp_poo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.jetbrains.annotations;

    opens esi.tp.tp_poo to javafx.fxml;
    exports esi.tp.tp_poo;
    exports esi.tp.tp_poo.Controllers;
    opens esi.tp.tp_poo.Controllers to javafx.fxml;
    opens esi.tp.tp_poo.Models to javafx.base;

}