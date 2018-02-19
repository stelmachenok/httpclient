package com.aipos.http.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel logLabel;
    private JLabel responseLabel;
    private JScrollPane logScrollPane;
    private JScrollPane responseScrollPane;
    private JTextArea logTextArea;
    private JTextArea responseTextArea;
    private Client client;


    public Gui(){
        client = new Client();
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
        passwordField = new JTextField(20);
        getButton = new JButton("GET");
        getButton.addActionListener(e -> {
            String bookId = bookIdField.getText();
            String page = pageField.getText();
            String response = null;
            try {
                response = client.executeGet(bookId, page);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            logTextArea.setText(logTextArea.getText() +
                    "\n----GET----\n" +
                    response);
            String parsedResponse = client.parseGet(response);
            responseTextArea.setText(parsedResponse);
        });
        headButton = new JButton("HEAD");
        headButton.addActionListener(e -> {
            String bookId = bookIdField.getText();
            String page = pageField.getText();
            String response = null;
            try {
                response = client.executeHead(bookId, page);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            logTextArea.setText(logTextArea.getText() +
                "\n----HEAD----\n" +
                response);
            String parsedResponse = client.parseHead(response);
            responseTextArea.setText(parsedResponse);
        });
        postButton = new JButton("POST");
        postButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = passwordField.getText();
            String response = null;
            try {
                response = client.executePost(login, password);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            logTextArea.setText(logTextArea.getText() +
                "\n----POST----\n" +
                response);
            String parsedResponse = client.parsePost(response);
            responseTextArea.setText(parsedResponse);
        });
        logTextArea = new JTextArea(30, 30);
        responseTextArea = new JTextArea(30, 30);
        logScrollPane = new JScrollPane(logTextArea);
        responseScrollPane = new JScrollPane(responseTextArea);
        pageLabel = new JLabel("Page:");
        pageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel = new JLabel("Login:");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logLabel = new JLabel("Log:");
        logLabel.setFont(new Font("Arial", Font.BOLD, 20));
        responseLabel = new JLabel("Response:");
        responseLabel.setFont(new Font("Arial", Font.BOLD, 20));

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
        logLabel.setBounds(0, 150, 100, 50);
        responseLabel.setBounds(900, 150, 200, 50);
        logScrollPane.setBounds(0, 200, 800, 700);
        responseScrollPane.setBounds(900, 200, 800, 700);

        panel.add(getButton);
        panel.add(bookIdField);
        panel.add(pageField);
        panel.add(headButton);
        panel.add(postButton);
        panel.add(loginField);
        panel.add(passwordField);
        panel.add(logScrollPane);
        panel.add(responseScrollPane);
        panel.add(pageLabel);
        panel.add(bookIdLabel);
        panel.add(loginLabel);
        panel.add(passwordLabel);
        panel.add(logLabel);
        panel.add(responseLabel);

        panel.updateUI();
    }

}
