package com.example.youtube;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FormatListListener implements ListSelectionListener {
    private Frame frame;

    public FormatListListener(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList source = (JList) e.getSource();
        if (source.equals(frame.formatList)) {
            frame.dlBtn.setEnabled(true);
        }
    }

}
