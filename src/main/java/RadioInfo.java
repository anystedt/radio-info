import Controller.RadioController;
import Model.APIRetriever;
import View.RadioView;

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
