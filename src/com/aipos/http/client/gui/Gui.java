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
    private JTextField pageField;
    private JTextField bookIdField;
    private JTextField loginField;
    private JTextField passwordField;
    private JButton getButton;
    private JButton headButton;
    private JButton postButton;
    private JLabel pageLabel;
    private JLabel bookIdLabel;
    private JLabel loginLabel;
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

    public Gui(){
        client = new Client();
        parser = new ResponseParser();
        frame = new JFrame("HTTP Client");
        frame.setSize(1920, 1030);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel(null);
        frame.add(panel);
        pageField = new JTextField(5);
        pageField.setFont(new Font("Arial", Font.BOLD, 20));
        bookIdField = new JTextField(5);
        bookIdField.setFont(new Font("Arial", Font.BOLD, 20));
        loginField = new JTextField(20);
        loginField.setFont(new Font("Arial", Font.BOLD, 20));
        passwordField = new JTextField(20);
        passwordField.setFont(new Font("Arial", Font.BOLD, 20));
        getButton = new JButton("GET");
        getButton.addActionListener(e -> {
            String bookId = bookIdField.getText();
            String page = pageField.getText();
            String request = client.requestGet(bookId, page);
            String response = null;
            try {
                response = client.executeGet(bookId, page);
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
            String bookId = bookIdField.getText();
            String page = pageField.getText();
            String request = client.requestHead(bookId, page);
            String response = null;
            try {
                response = client.executeHead(bookId, page);
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
            String login = loginField.getText();
            String password = passwordField.getText();
            String request = client.requestPost(login, password);
            String response = null;
            try {
                response = client.executePost(login, password);
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
        requestTextArea = new JTextArea(10,10);
        logScrollPane = new JScrollPane(responseTextArea);
        responseScrollPane = new JScrollPane(parsedAnswerTextArea);
        requestScrollPane = new JScrollPane(requestTextArea);
        pageLabel = new JLabel("Page:");
        pageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel = new JLabel("Login:");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
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
        bookIdLabel.setBounds(100, 0, 100, 50);
        pageLabel.setBounds(300, 0, 100, 50);
        bookIdField.setBounds(200, 0, 100, 50);
        pageField.setBounds(400, 0, 100, 50);
        loginLabel.setBounds(100, 100, 100, 50);
        passwordLabel.setBounds(300, 100, 100, 50);
        loginField.setBounds(200, 100, 100, 50);
        passwordField.setBounds(400, 100, 100, 50);
        responseLabel.setBounds(0, 150, 100, 50);
        parsedAnswerLabel.setBounds(900, 150, 200, 50);
        requestLabel.setBounds(0, 700, 200, 50);
        logScrollPane.setBounds(0, 200, 800, 500);
        responseScrollPane.setBounds(900, 200, 800, 500);
        requestScrollPane.setBounds(0, 750, 800, 200);

        panel.add(getButton);
        panel.add(bookIdField);
        panel.add(pageField);
        panel.add(headButton);
        panel.add(postButton);
        panel.add(loginField);
        panel.add(passwordField);
        panel.add(logScrollPane);
        panel.add(responseScrollPane);
        panel.add(requestScrollPane);
        panel.add(pageLabel);
        panel.add(bookIdLabel);
        panel.add(loginLabel);
        panel.add(passwordLabel);
        panel.add(responseLabel);
        panel.add(parsedAnswerLabel);
        panel.add(requestLabel);


        panel.updateUI();
    }

}
