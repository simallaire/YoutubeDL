package youtube;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class TextFieldListener implements FocusListener {
    Frame frame;
    public TextFieldListener(Frame frame){
        this.frame = frame;
    }
    @Override
    public void focusGained(FocusEvent e) {
        frame.urlText.selectAll();
        frame.enableInfoBtn();

    }

    @Override
    public void focusLost(FocusEvent e) {
        frame.enableInfoBtn();
    }


}