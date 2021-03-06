package team04.project2.ui;

import team04.project2.constants.ColorConstants;
import team04.project2.constants.TextConstants;
import team04.project2.util.Log;
import team04.project2.util.Terminal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * ConsoleView, UI element to display system messages through a console
 * @author  David Henderson (dchende2@asu.edu)
 */
public class ConsoleView extends JPanel {
    private JLabel labelHeader;
    private JScrollPane scrollpaneLog;
    private JTextArea textareaLog;
    private JTextField textfieldInput;

    /**
     * Constructor for the view containing the console
     */
    public ConsoleView() {
        // Create transparent border around class
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Actual content pane (which the transparent border encompasses)
        JPanel panelBuffer = new JPanel(new BorderLayout());
        panelBuffer.setBackground(ColorConstants.BACKGROUND_GRAY);
        panelBuffer.setBorder(BorderFactory.createLineBorder(Color.black));

        labelHeader = new JLabel("Console:");
        labelHeader.setFont(TextConstants.DEFAULT_FONT);
        panelBuffer.add(labelHeader, BorderLayout.PAGE_START);

        textareaLog = new JTextArea();
        textareaLog.setEditable(false);
        textareaLog.setRows(6);
        textareaLog.setOpaque(false);
        textareaLog.setFont(TextConstants.SMALL_FONT);
        textareaLog.setLineWrap(true);
        textareaLog.setWrapStyleWord(true);
        panelBuffer.add(textareaLog, BorderLayout.CENTER);

        textfieldInput = new JTextField();
        textfieldInput.setOpaque(false);
        textfieldInput.setBorder(null);
        textfieldInput.setFont(TextConstants.DEFAULT_FONT);
        textfieldInput.addActionListener(e -> {
            // Upon taking input, append the input to the console and send it to the terminal to handle
            textareaLog.append("> " + textfieldInput.getText() + "\n");
            Terminal.handle(textfieldInput.getText());
            textareaLog.setCaretPosition(textareaLog.getText().length());
            textfieldInput.setText("");
        });
        panelBuffer.add(textfieldInput, BorderLayout.PAGE_END);

        scrollpaneLog = new JScrollPane(textareaLog);
        scrollpaneLog.setOpaque(false);
        scrollpaneLog.getViewport().setOpaque(false);
        scrollpaneLog.setBorder(null);
        panelBuffer.add(scrollpaneLog, BorderLayout.CENTER);

        this.add(panelBuffer, BorderLayout.CENTER);

        // Subscribe and listen to new records
        Log.addRecordListener(record -> {
            textareaLog.append(record.getFormattedMessage() + "\n");
            textareaLog.setCaretPosition(textareaLog.getText().length());
        });
    }
}
