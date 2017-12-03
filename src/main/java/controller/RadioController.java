/**
 * Created on 22/11/17
 * File: RadioController.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing the controller. Handles the communication
 * between the view-classes and the model-classes. Gets information
 * about the channels once an hour or when user uses view to update.
 * When the model has collected the data from the api the controller
 * forwards this to the view which presents it for the user.
 */

package controller;
import model.APIRetriever;
import model.Channel;
import model.Program;
import org.xml.sax.SAXException;
import view.RadioView;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

public class RadioController {

    private RadioView view;
    private APIRetriever model;
    private Boolean isRetrievingChannels = false;

    /**
     * Constructor for the controller. Initializes the model and the
     * view the controller will use. Starts a timer that will update
     * the information once an hour and adds a listener to the
     * update button.
     * @param view the view
     * @param model the model
     */
    public RadioController(RadioView view, APIRetriever model){
        this.view = view;
        this.model = model;

        //Starts a timer that will update content once an hour.
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isRetrievingChannels) {
                    updateView();
                }
            }
        }, 0, 3600 * 1000);

        //Gives the update button a listener that will update content
        // when pressed.
        view.setUpdateListener(e -> {
            if (!isRetrievingChannels) {
                updateView();
            }
        });
    }

    /**
     * Updates the view when executed.
     */
    private void updateView() {
        //Creates a new thread that will update content.
        new SwingWorker<List<JLabel>, Void>(){
            /**
             * Retrieves the channel information from the model and
             * makes the data readable for the view. Prepares the
             * labels that will be used for presenting the channels
             * for the user, to prevent the GUI to freeze.
             *
             * @return a list of labels that will be presented by the
             * view.
             */
            @Override
            protected List<JLabel> doInBackground() {

                isRetrievingChannels = true;
                try {
                    List<Channel> listOfChannels = model.getChannels();

                    List<JLabel> channelLabels = new ArrayList<>();

                    for (Channel channel : listOfChannels) {
                        JLabel channelLabel = view.createChannelLabel(channel.getName(), channel.getImageUrl());
                        List<Object[]> tableObjects = getTableau(channel);

                        view.setViewListener(channelLabel, tableObjects, createProgramListener());
                        channelLabels.add(channelLabel);
                    }

                    return channelLabels;

                } catch (ParserConfigurationException | SAXException | IOException e) {
                    view.showErrorMessage(e);
                    return null;
                }
            }

            /**
             * Sends the labels to the view.
             */
            @Override
            protected void done() {
                try {
                    isRetrievingChannels = false;
                    List<JLabel> channelLabels = get();

                    if(channelLabels != null){
                        view.setChannels(channelLabels);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    view.showErrorMessage(e);
                }
            }

        }.execute();
    }

    /**
     * Returns a listener that is used for retrieving the information
     * about a program when that program is clicked in the tableau.
     * @return the listener.
     */
    private MouseAdapter createProgramListener(){
        return new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                Program clickedProgram = (Program) target.getValueAt(row, 0);

                view.showProgramInfo(clickedProgram.getTitle(),
                        clickedProgram.getSubtitle(),
                        clickedProgram.getImageUrl(),
                        clickedProgram.getDescription());
            }
        };
    }

    /**
     * Retrieves the program information and returns an object
     * containing the information.
     * @param channel the channel containing the tableau.
     * @return a list of objects containing information about a program
     */
    private List<Object[]> getTableau(Channel channel){
        List<Program> programs = channel.getTableau();
        List<Object[]> tableObjects = new ArrayList<>();

        for(Program program: programs){
            Object[] programInfo = {program,
                    program.getStart(),
                    program.getEnd(),};
            tableObjects.add(programInfo);
        }

        return tableObjects;
    }
}
