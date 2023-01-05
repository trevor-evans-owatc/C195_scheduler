package control;

import javafx.scene.control.Alert;

/**
 * Lert class was created and is implemented to reduce redundancies handling Alert objects
 */
public class Lert {

    /**
     * Constructor
     * @param typ The Alert type object for Alert to be implemented
     * @param ttl String object to hold the title object for the alert
     * @param txt String object to hold the contentText of the alert
     */
    Lert(Alert.AlertType typ, String ttl, String txt){

        Alert al = new Alert(typ);
        al.setTitle(ttl);
        al.setContentText(txt);
        al.showAndWait();
    }
    /**
     * Overloaded Constructor, added parameter to resize
     *@param typ The Alert type object for Alert to be implemented
     *@param ttl String object to hold the title object for the alert
     *@param txt String object to hold the contentText of the alert
     *@param resize boolean variable to flag whether the alert window should be resizable
     */
    Lert(Alert.AlertType typ, String ttl, String txt, boolean resize){
        Alert al = new Alert(typ);
        al.setTitle(ttl);
        al.setContentText(txt);
        al.setResizable(resize);
        al.showAndWait();
    }
}
