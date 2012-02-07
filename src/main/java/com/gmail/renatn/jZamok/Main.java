package com.gmail.renatn.jZamok;

import com.gmail.renatn.jZamok.gui.MainFrame;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

/**
 *
 * @author renat
 */
public class Main {

    private static MainFrame frame = null;
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String args[]) {

        if (!AppProperties.checkProfile()) {
            System.err.println("Unable to create profile directory, "+ AppProperties.profilePath);
            return;
        }

        AppProperties conf = AppProperties.getInstance();
        try {
            conf.load();
        } catch (IOException e) {
            System.out.println("File properties not opened, use defaults.");
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {

                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    
                    frame = new MainFrame();
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    
                } catch (ClassNotFoundException ex) {
                    logger.log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    logger.log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    logger.log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}