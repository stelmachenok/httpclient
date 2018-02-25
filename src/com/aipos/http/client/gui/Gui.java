package com.aipos.http.client.gui;

import com.aipos.http.client.controller.Client;

import com.aipos.http.client.entity.CommandType;
import com.aipos.http.client.entity.HttpRequest;
import com.aipos.http.client.entity.HttpResponse;
import com.aipos.http.client.exception.EmptyHostException;
import com.aipos.http.client.parser.impl.ResponseParserImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

/**
 * @author maksim.stelmachonak
 */
public class Gui {
    private JFrame frame;
    private JPanel panel;
    private JComboBox comboBox;
    private JButton executeButton;
    private JLabel bodyLabel;
    private JTextField bodyTextField;
    private JLabel uriLabel;
    private JTextField uriTextField;
    private JScrollPane requestParamScrollPane;
    private JTable requestParamTable;
    private JLabel logLabel;
    private JTextArea logTextArea;
    private JScrollPane logScrollPane;
    private JLabel statusCodeHeaderLabel;
    private JLabel statusCodeLabel;
    private JLabel messageHeaderLabel;
    private JLabel messageHeader;
    private JScrollPane responseParamScrollPane;
    private JTable responseParamTable;


    private Client client;
    private ResponseParserImpl responseParser;

    public Gui() {
        client = new Client();
        responseParser = new ResponseParserImpl();
        frame = new JFrame("HTTP Client");
        frame.setSize(1920, 1030);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel(new GridBagLayout());
        frame.add(panel);

        GridBagConstraints c = new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0);
        comboBox = new JComboBox(CommandType.values());
        panel.add(comboBox, c);
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        executeButton = new JButton("Execute");
        executeButton.addActionListener(e -> {
            HttpRequest request = new HttpRequest();
            request.setCommand(CommandType.valueOf(String.valueOf(comboBox.getSelectedItem())));
            request.setUri(uriTextField.getText());
            TableModel tableModel = requestParamTable.getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String value = String.valueOf(tableModel.getValueAt(i, 1));
                if (!value.equals("")) {
                    String key = String.valueOf(tableModel.getValueAt(i, 0));
                    request.addHeader(key, value);
                }
            }

            request.setBody(bodyTextField.getText());

            HttpResponse response = null;
            try {
                response = client.executeRequest(request);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (EmptyHostException e1) {
                JOptionPane.showMessageDialog(frame,
                        e1.getMessage(),
                        "Empty host",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] responseColumnNames = {"Header", "Value"};
            DefaultTableModel responseTableModel = new DefaultTableModel(null, responseColumnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column != 0;
                }
            };
            Map<String, String> headers = response.getHeaders();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                Object[] responseHeaders = {entry.getKey(), entry.getValue()};
                responseTableModel.addRow(responseHeaders);
            }
            responseParamTable.setModel(responseTableModel);

            logTextArea.append(request.toString());
            logTextArea.append(response.toString());

            statusCodeLabel.setText(String.valueOf(response.getCode()));
            messageHeader.setText(String.valueOf(response.getMessage()));
        });
        panel.add(executeButton, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.anchor = GridBagConstraints.LINE_START;
        uriLabel = new JLabel("URI: ");
        panel.add(uriLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        uriTextField = new JTextField();
        panel.add(uriTextField, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        bodyLabel = new JLabel("Body:");
        panel.add(bodyLabel, c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        bodyTextField = new JTextField();
        panel.add(bodyTextField, c);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 4;
        c.gridheight = 8;
        c.fill = GridBagConstraints.BOTH;
        String[] requestColumnNames = {"Header", "Value"};
        String[][] data = {
                {"Accept", ""},
                {"Accept-Charset", ""},
                {"Accept-Encoding", ""},
                {"Accept-Language", ""},
                {"Accept-Datetime", ""},
                {"Authorization", ""},
                {"Cache-Control", ""},
                {"Connection", ""},
                {"Cookie", ""},
                {"Content-Length", ""},
                {"Content-MD5", ""},
                {"Content-Type", ""},
                {"Date", ""},
                {"Expect", ""},
                {"Forwarded", ""},
                {"From", ""},
                {"Host", ""},
                {"If-Match", ""},
                {"If-Modified-Since", ""},
                {"If-None-Match", ""},
                {"If-Range", ""},
                {"If-Unmodified-Since", ""},
                {"Max-Forwards", ""},
                {"Pragma", ""},
                {"Proxy-Authorization", ""},
                {"Range", ""},
                {"Referer", ""},
                {"TE", ""},
                {"User-Agent", ""},
                {"Upgrade", ""}
        };
        DefaultTableModel tableModel = new DefaultTableModel(data, requestColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        requestParamTable = new JTable(tableModel);
        requestParamScrollPane = new JScrollPane(requestParamTable);
        panel.add(requestParamScrollPane, c);


        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        logLabel = new JLabel("Log: ");
        panel.add(logLabel, c);
        c.gridx = 4;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridheight = 12;
        c.fill = GridBagConstraints.BOTH;
//        c.weightx = 0.9;
        c.ipadx = 500;
        logTextArea = new JTextArea(20,20);
        logScrollPane = new JScrollPane(logTextArea);
        panel.add(logScrollPane, c);


        c.gridx = 8;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.ipadx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        statusCodeHeaderLabel = new JLabel("Status code: ");
        panel.add(statusCodeHeaderLabel, c);
        c.gridx = 10;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        statusCodeLabel = new JLabel("");
        panel.add(statusCodeLabel, c);
        c.gridx = 8;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        messageHeaderLabel = new JLabel("Message: ");
        panel.add(messageHeaderLabel, c);
        c.gridx = 10;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        messageHeader = new JLabel("");
        panel.add(messageHeader, c);


        c.gridx = 8;
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridheight = 11;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        String[] responseColumnNames = {"Header", "Value"};
        tableModel = new DefaultTableModel(null, responseColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        responseParamTable = new JTable(tableModel);
        responseParamScrollPane = new JScrollPane(responseParamTable);
        panel.add(responseParamScrollPane, c);


        panel.updateUI();
    }
}
