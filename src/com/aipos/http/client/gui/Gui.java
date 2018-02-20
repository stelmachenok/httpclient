package com.aipos.http.client.gui;

import com.aipos.http.client.controller.Client;

import com.aipos.http.client.entity.CommandType;
import com.aipos.http.client.entity.HttpRequest;
import com.aipos.http.client.entity.HttpResponse;
import com.aipos.http.client.parser.impl.ResponseParserImpl;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by y50-70 on 18.02.2018.
 */
public class Gui {
    private JFrame frame;
    private JPanel panel;
    private JTextArea uriTextArea;
    private JTextArea headerTextArea;
    private JTextArea bodyTextArea;
    private JScrollPane uriScrollPane;
    private JScrollPane headerScrollPane;
    private JScrollPane bodyScrollPane;

    private JButton getButton;
    private JButton headButton;
    private JButton postButton;
    private JLabel headerLabel;
    private JLabel uriLabel;
    private JLabel bodyLabel;
    private JLabel responseLabel;
    private JLabel requestLabel;
    private JScrollPane logScrollPane;
    private JScrollPane requestScrollPane;
    private JTextArea responseTextArea;
    private JTextArea requestTextArea;
    private Client client;
    private ResponseParserImpl responseParser;

    public Gui() {
        client = new Client();
        responseParser = new ResponseParserImpl();
        frame = new JFrame("HTTP Client");
        frame.setSize(1920, 1030);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel(null);
        frame.add(panel);
        uriTextArea = new JTextArea(15, 15);
        uriTextArea.setFont(new Font("Arial", Font.BOLD, 15));
        uriScrollPane = new JScrollPane(uriTextArea);
        headerTextArea = new JTextArea(15, 15);
        headerTextArea.setFont(new Font("Arial", Font.BOLD, 15));
        headerScrollPane = new JScrollPane(headerTextArea);
        bodyTextArea = new JTextArea(15, 15);
        bodyTextArea.setFont(new Font("Arial", Font.BOLD, 15));
        bodyScrollPane = new JScrollPane(bodyTextArea);
        getButton = new JButton("GET");
        getButton.addActionListener(e -> {
            String URI = this.uriTextArea.getText();
            String header = headerTextArea.getText();
            HttpRequest request = new HttpRequest(CommandType.GET, URI, header);
            HttpResponse response = null;
            try {
                response = client.executeRequest(request);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            requestTextArea.setText(requestTextArea.getText() +
                    "\n----GET Request----\n"
                    + request.toString());

            responseTextArea.setText(responseTextArea.getText() +
                    "\n----GET Response----\n" +
                (response != null ? response.toString() : null));
        });
        headButton = new JButton("HEAD");
        headButton.addActionListener(e -> {
            String URI = this.uriTextArea.getText();
            String header = headerTextArea.getText();
            HttpRequest request = new HttpRequest(CommandType.HEAD, URI, header);
            HttpResponse response = null;
            try {
                response = client.executeRequest(request);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            requestTextArea.setText(requestTextArea.getText() +
                    "\n----HEAD Request----\n"
                    + request.toString());

            responseTextArea.setText(responseTextArea.getText() +
                    "\n----HEAD Response----\n" +
                (response != null ? response.toString() : null));
        });
        postButton = new JButton("POST");
        postButton.addActionListener(e -> {

            String URI = this.uriTextArea.getText();
            String header = headerTextArea.getText();
            String body = bodyTextArea.getText();
            HttpRequest request = new HttpRequest(CommandType.POST, URI, header, body);
            HttpResponse response = null;
            try {
                response = client.executeRequest(request);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            requestTextArea.setText(requestTextArea.getText() +
                "\n----HEAD Request----\n"
                + request.toString());

            responseTextArea.setText(responseTextArea.getText() +
                "\n----HEAD Response----\n" +
                (response != null ? response.toString() : null));
        });
        responseTextArea = new JTextArea(30, 30);
        requestTextArea = new JTextArea(10, 10);
        logScrollPane = new JScrollPane(responseTextArea);
        requestScrollPane = new JScrollPane(requestTextArea);
        headerLabel = new JLabel("Header:");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        uriLabel = new JLabel("URI:");
        uriLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bodyLabel = new JLabel("Body:");
        bodyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        requestLabel = new JLabel("Request:");
        requestLabel.setFont(new Font("Arial", Font.BOLD, 20));
        responseLabel = new JLabel("Response:");
        responseLabel.setFont(new Font("Arial", Font.BOLD, 20));

        getButton.setBounds(0, 0, 100, 50);
        headButton.setBounds(0, 50, 100, 50);
        postButton.setBounds(0, 100, 100, 50);
        uriLabel.setBounds(800, 150, 100, 50);
        headerLabel.setBounds(800, 400, 100, 50);
        bodyLabel.setBounds(800, 700, 100, 50);
        uriScrollPane.setBounds(800, 200, 800, 200);
        headerScrollPane.setBounds(800, 450, 800, 250);
        bodyScrollPane.setBounds(800, 750, 800, 200);
        responseLabel.setBounds(0, 150, 100, 50);
        requestLabel.setBounds(0, 700, 200, 50);
        logScrollPane.setBounds(0, 200, 800, 500);
        requestScrollPane.setBounds(0, 750, 800, 200);

        panel.add(getButton);
        panel.add(headButton);
        panel.add(postButton);
        panel.add(logScrollPane);
        panel.add(requestScrollPane);
        panel.add(headerLabel);
        panel.add(uriLabel);
        panel.add(bodyLabel);
        panel.add(requestLabel);
        panel.add(responseLabel);
        panel.add(uriScrollPane);
        panel.add(headerScrollPane);
        panel.add(bodyScrollPane);

        panel.updateUI();
    }

}
