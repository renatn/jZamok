/*
 * MasterKeyAction.java
 *
 * Created on 28 Август 2007 г., 19:18
 *
 */

package com.gmail.renatn.jZamok.actions;

/**
 *
 * @author renat
 */

import com.gmail.renatn.jZamok.gui.MainFrame;
import com.gmail.renatn.jZamok.gui.MasterKeyDialog;
import com.gmail.renatn.jZamok.gui.UIHelper;
import com.gmail.renatn.jZamok.model.ZamokDocument;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class MasterKeyAction extends AbstractAction {

    private MainFrame app;   
    
    public MasterKeyAction(MainFrame app) {
        
        this.app = app;
        
        putValue(Action.NAME, "Master key");
        putValue(Action.LARGE_ICON_KEY, UIHelper.getImageIcon("jzamok24.png"));
        putValue(Action.SHORT_DESCRIPTION, "Set master key");

        setEnabled(false);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        MasterKeyDialog dlg = new MasterKeyDialog(app);

        if (dlg.showDialog()) {

            String msg = validatePassword(dlg.getCurrentPassword(), dlg.getNewPassword(), dlg.getConfirmPassword());
            if (msg != null) {
                app.showError(msg);
                return;
            }

            ZamokDocument model = app.getModel();
            model.setPhrase(dlg.getNewPassword());
            model.setChanged(true);
            app.updateTab();

        }

    }

    private String validatePassword(char[] currentPassword, char[] newPassword, char[] confirmPassword) {
        char[] currentMasterKey = app.getModel().getPhrase();
        if (!Arrays.equals(currentPassword, currentMasterKey)) {
            return "Wrong current master key";
        }

        if (newPassword == null || newPassword.length == 0) {
            return "Master key cannot be empty";
        }

        if (!Arrays.equals(newPassword, confirmPassword)) {
            return "Passswords doesn't match";
        }

        return null;
    }
    
}
