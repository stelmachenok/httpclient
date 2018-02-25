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

/**
 * Created by maksim.stelmachonak.
 */
public class Gui {
    private JFrame frame;
    private JPanel panel;
    private JComboBox comboBox;
    private JButton executeButton;
    private JLabel parametersLabel;
    private JTextField parametersTextField;
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

        GridBagConstraints c = new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);
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
            int rowCount = requestParamTable.getModel().getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String value = String.valueOf(tableModel.getValueAt(i, 1));
                if (!value.equals("")) {
                    String key = String.valueOf(tableModel.getValueAt(i, 0));
                    request.addHeader(key, value);
                }
            }
            request.setBody(parametersTextField.getText());

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

            logTextArea.setText(logTextArea.getText() + request + response);
        });
        panel.add(executeButton, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        uriLabel = new JLabel("URI: ");
        panel.add(uriLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        uriTextField = new JTextField();
        panel.add(uriTextField, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        c.anchor = GridBagConstraints.LINE_START;
        parametersLabel = new JLabel("Paramaters:");
        panel.add(parametersLabel, c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        parametersTextField = new JTextField(20);
        panel.add(parametersTextField, c);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 4;
        c.gridheight = 8;
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
        c.fill = GridBagConstraints.HORIZONTAL;
        logLabel = new JLabel("Log: ");
        panel.add(logLabel, c);
        c.gridx = 4;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridheight = 12;
        c.fill = GridBagConstraints.BOTH;
//        c.weightx = 0.9;
        c.ipadx = 500;
        logTextArea = new JTextArea();
        logScrollPane = new JScrollPane(logTextArea);
        panel.add(logScrollPane, c);


        c.gridx = 8;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.ipadx = 0;
        statusCodeHeaderLabel = new JLabel("Status code: ");
        panel.add(statusCodeHeaderLabel, c);
        c.gridx = 10;
        c.gridy = 0;
        statusCodeLabel = new JLabel();
        panel.add(statusCodeLabel, c);
        c.gridx = 8;
        c.gridy = 1;
        messageHeaderLabel = new JLabel("Message: ");
        panel.add(messageHeaderLabel, c);
        c.gridx = 10;
        c.gridy = 1;
        messageHeader = new JLabel();
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
/*
 *       gridx, gridy - Coordinates of left upper component's corner. The leftmost column has address gridx=0 and the top row has address gridy=0.
 *       gridwidth, gridheight - the number of cells the component uses. The default value is 1
 *       fill - for fill space horizontally (GridBagConstraints.HORIZONTAL ) or vertically (GridBagConstraints.VERTICAL). Default NONE
 *       weightx, weighty - how much to add to the size of the component. Width - minimum width plus ipadx*2 pixels. Height - minimum height plus ipady*2 pixels
 *       insets - indent in pixels. new Insets(top, left, bottom, right)
 *       anchor - determine where (within the area) to place the component. GridBagConstraints.
 *       FIRST_LINE_START    PAGE_START  FIRST_LINE_END
 *       LINE_START          CENTER      LINE_END
 *       LAST_LINE_START     PAGE_END    LAST_LINE_END
 *       weightx, weighty - stretch coefficient. The greater the coefficient, the greater the stretching. Default 0.0, max 1.0. If dont want dispose all components on center
 * */
