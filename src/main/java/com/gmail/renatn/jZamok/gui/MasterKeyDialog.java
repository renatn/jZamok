package com.gmail.renatn.jZamok.gui;

import com.gmail.renatn.jZamok.AppProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User: renatn
 * Date: 09.09.13
 * Time: 23:15
 */
public class MasterKeyDialog extends JDialog {

    private boolean modalResult = false;

    private final JPasswordField tfCurrentPassword;
    private final JPasswordField tfNewPassword;
    private final JPasswordField tfConfirmPassword;

    public MasterKeyDialog(JFrame f) {
        super(f, true);

        JButton btnChange = new JButton("Change");
        btnChange.setMinimumSize(AppProperties.DEF_BTN_SIZE);
        btnChange.setPreferredSize(AppProperties.DEF_BTN_SIZE);
        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalResult = true;
                closeDialog();
            }
        });

        tfCurrentPassword = new JPasswordField(20);
        tfNewPassword = new JPasswordField(20);
        tfConfirmPassword = new JPasswordField(20);

        GridBagPanel mainPanel = new GridBagPanel();
        mainPanel.setAnchor(GridBagConstraints.WEST);
        mainPanel.setInsets(8, 8, 8, 8);
        mainPanel.place(new JLabel("Current password:"), 1, 1, 1, 1);
        mainPanel.place(tfCurrentPassword, 2, 1, 1, 1);
        mainPanel.place(new JLabel("New password:"), 1, 2, 1, 1);
        mainPanel.place(tfNewPassword, 2, 2, 1, 1);
        mainPanel.place(new JLabel("Confirm password:"), 1, 3, 1, 1);
        mainPanel.place(tfConfirmPassword, 2, 3, 1, 1);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnChange);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeDialog();
            }
        });

        setTitle("Master key setup");

        setContentPane(panel);
        pack();
        setLocationRelativeTo(f);
    }

    public boolean showDialog() {
        modalResult = false;
        setVisible(true);
        return modalResult;
    }

    public char[] getCurrentPassword() {
        return tfCurrentPassword.getPassword();
    }

    public char[] getNewPassword() {
        return tfNewPassword.getPassword();
    }

    public char[] getConfirmPassword() {
        return tfConfirmPassword.getPassword();
    }

    private void closeDialog() {
        setVisible(false);
        dispose();
    }

}
