package Controller;
import Model.APIRetriever;
import Model.Channel;
import View.RadioView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

public class RadioController {

    private RadioView view;
    private APIRetriever model;

    public RadioController(RadioView view, APIRetriever model){
        this.view = view;
        this.model = model;
        updateView();
    }

    private void updateView() {

        /*Inner class that handles the additional thread that collects
        * information from the SR-api and then forwards the result
        * to the view.
        */
        class worker extends SwingWorker<List<JLabel>, Void> {

            @Override
            protected List<JLabel> doInBackground() {

                List<Channel> listOfChannels = model.getChannels();
                List<JLabel> channelLabels = new ArrayList<>();

                for(Channel channel: listOfChannels){
                    JLabel channelLabel = view.createChannelLabel(channel.getName(), channel.getImageUrl());
                    view.addListenerToLabel(channelLabel, channel);

                    channelLabels.add(channelLabel);
                }

                return channelLabels;
            }

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
}
