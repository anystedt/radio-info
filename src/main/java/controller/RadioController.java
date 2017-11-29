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
import view.RadioView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

public class RadioController {

    private RadioView view;
    private APIRetriever model;

    /**
     * Constructor for the controller. Initializes the model and the
     * view the controller will use.
     * @param view the view
     * @param model the model
     */
    public RadioController(RadioView view, APIRetriever model){
        this.view = view;
        this.model = model;
        updateView();
    }

    /**
     * Updates the view once an hour (start counting from when the
     * program starts) or when the user uses the gui to update.
     */
    private void updateView() {

        /**
         * Inner class that handles the additional thread that collects
         * information from the SR-api and then forwards the result
         * to the view.
         */
        class worker extends SwingWorker<List<JLabel>, Void> {

            /**
             * Retrieves the channel information from the model and
             * makes the data readable for the view. Prepares the
             * labels that will be used for presenting the channels
             * for the user, to prevent the GUI to froze.
             * @return a list of labels that will be presented by the
             * view.
             */
            @Override
            protected List<JLabel> doInBackground() {

                List<Channel> listOfChannels = model.getChannels();
                List<JLabel> channelLabels = new ArrayList<>();

                for(Channel channel: listOfChannels){
                    JLabel channelLabel = view.createChannelLabel(channel.getName(), channel.getImageUrl());
                    List<Object[]> tableObjects = getTableau(channel);

                    view.setChannelLabelListener(channelLabel, tableObjects);
                    channelLabels.add(channelLabel);
                }

                return channelLabels;
            }

            /**
             * Sends the labels to the view.
             */
            @Override
            protected void done() {
                try {
                    List<JLabel> channelLabels = get();
                    view.setChannels(channelLabels);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        //Starts a timer that will execute the methods of the inner
        //class once an hour.
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new worker().execute();
            }
        }, 0, 3600 * 1000);

        //Gives the update button a listener that will execute the
        //methods of the inner class when pressed.
        view.setUpdateListener(e -> new worker().execute());
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
            Object[] programInfo = {program.getTitle(),
                    program.getStart(),
                    program.getEnd(),
                    program.getSubtitle(),
                    program.getImageUrl(),
                    program.getDescription()};
            tableObjects.add(programInfo);
        }

        return tableObjects;
    }
}
