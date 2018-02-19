package com.aipos.http.client.gui;

import com.aipos.http.client.controller.Client;
import com.aipos.http.client.parser.ResponseParser;

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
    private JLabel passwordLabel;
    private JLabel responseLabel;
    private JLabel requestLabel;
    private JLabel parsedAnswerLabel;
    private JScrollPane logScrollPane;
    private JScrollPane responseScrollPane;
    private JScrollPane requestScrollPane;
    private JTextArea responseTextArea;
    private JTextArea parsedAnswerTextArea;
    private JTextArea requestTextArea;
    private Client client;
    private ResponseParser parser;

    public Gui() {
        client = new Client();
        parser = new ResponseParser();
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
            String request = client.requestGet(URI, header);
            String response = null;
            try {
                response = client.executeGet(URI, header);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            requestTextArea.setText(requestTextArea.getText() +
                    "\n----GET Request----\n"
                    + request);

            responseTextArea.setText(responseTextArea.getText() +
                    "\n----GET Response----\n" +
                    response);
            String parsedResponse = parser.parseGet(response);
            parsedAnswerTextArea.setText(parsedResponse);
        });
        headButton = new JButton("HEAD");
        headButton.addActionListener(e -> {
            String URI = uriTextArea.getText();
            String header = headerTextArea.getText();
            String request = client.requestHead(URI, header);
            String response = null;
            try {
                response = client.executeHead(URI, header);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            requestTextArea.setText(requestTextArea.getText() +
                    "\n----HEAD Request----\n"
                    + request);

            responseTextArea.setText(responseTextArea.getText() +
                    "\n----HEAD Response----\n" +
                    response);
            String parsedResponse = parser.parseHead(response);
            parsedAnswerTextArea.setText(parsedResponse);
        });
        postButton = new JButton("POST");
        postButton.addActionListener(e -> {

            String URI = uriTextArea.getText();
            String header = headerTextArea.getText();
            String body = bodyTextArea.getText();
            String request = client.requestPost(URI, header, body);
            String response = null;
            try {
                response = client.executePost(URI, header, body);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            requestTextArea.setText(requestTextArea.getText() +
                    "\n----POST Request----\n"
                    + request);

            responseTextArea.setText(responseTextArea.getText() +
                    "\n----POST Response----\n" +
                    response);
            String parsedResponse = parser.parsePost(response);
            parsedAnswerTextArea.setText(parsedResponse);
        });
        responseTextArea = new JTextArea(30, 30);
        parsedAnswerTextArea = new JTextArea(30, 30);
        requestTextArea = new JTextArea(10, 10);
        logScrollPane = new JScrollPane(responseTextArea);
        responseScrollPane = new JScrollPane(parsedAnswerTextArea);
        requestScrollPane = new JScrollPane(requestTextArea);
        headerLabel = new JLabel("Header:");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        uriLabel = new JLabel("URI:");
        uriLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bodyLabel = new JLabel("Body:");
        bodyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        responseLabel = new JLabel("Response:");
        responseLabel.setFont(new Font("Arial", Font.BOLD, 20));
        parsedAnswerLabel = new JLabel("Parsed answer:");
        parsedAnswerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        requestLabel = new JLabel("Request:");
        requestLabel.setFont(new Font("Arial", Font.BOLD, 20));

        getButton.setBounds(0, 0, 100, 50);
        headButton.setBounds(0, 50, 100, 50);
        postButton.setBounds(0, 100, 100, 50);
        uriLabel.setBounds(100, 0, 100, 50);
        headerLabel.setBounds(300, 0, 100, 50);
        headerTextArea.setBounds(200, 0, 100, 50);
        uriTextArea.setBounds(400, 0, 100, 50);
        bodyLabel.setBounds(100, 100, 100, 50);
        passwordLabel.setBounds(300, 100, 100, 50);
        bodyTextArea.setBounds(200, 100, 100, 50);
        responseLabel.setBounds(0, 150, 100, 50);
        parsedAnswerLabel.setBounds(900, 150, 200, 50);
        requestLabel.setBounds(0, 700, 200, 50);
        logScrollPane.setBounds(0, 200, 800, 500);
        responseScrollPane.setBounds(900, 200, 800, 500);
        requestScrollPane.setBounds(0, 750, 800, 200);

        panel.add(getButton);
        panel.add(headerTextArea);
        panel.add(uriTextArea);
        panel.add(headButton);
        panel.add(postButton);
        panel.add(bodyTextArea);
        panel.add(logScrollPane);
        panel.add(responseScrollPane);
        panel.add(requestScrollPane);
        panel.add(headerLabel);
        panel.add(uriLabel);
        panel.add(bodyLabel);
        panel.add(passwordLabel);
        panel.add(responseLabel);
        panel.add(parsedAnswerLabel);
        panel.add(requestLabel);

        panel.updateUI();
    }

}
