/**
 * Created on 17/11/17
 * File: RadioInfo.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * The main class of the program. Starts an initiate necessary parts.
 */

import controller.RadioController;
import model.APIRetriever;
import view.RadioView;

import javax.swing.*;

public class RadioInfo {
    public static void main(String []argv) {
        APIRetriever model = new APIRetriever();

        SwingUtilities.invokeLater(() -> {
            RadioView view = new RadioView();
            new RadioController(view, model);
        });
    }
}
