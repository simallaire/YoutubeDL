package com.example.youtube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class ButtonListener implements ActionListener {
    private final Frame frame;

    public ButtonListener(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(frame.dlBtn)) {
            Format format = this.frame.formatList.getSelectedValue();
            try {
                Terminal.dlFile(frame.urlText.getText(), frame.folderText.getText(), format);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (source.equals(frame.browseBtn)) {
            JFileChooser folder = new JFileChooser();
            folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            folder.setVisible(true);
            int confirmed = folder.showOpenDialog(frame);
            if (confirmed == JFileChooser.APPROVE_OPTION) {
                frame.folderText.setText(folder.getCurrentDirectory().getPath());
            }

        }
        if (source.equals(frame.infoBtn)) {
            try {
                Terminal.getFormats(frame.urlText.getText());
                frame.setThumbnail();
            } catch (IOException e1) {
            }
            System.out.println("Show Formats");
            frame.initFormatList();
        }
    }

}
