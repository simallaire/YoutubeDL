package youtube;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JTextField;

public class TextFieldEnterListener implements KeyListener {
    Frame frame;
    public TextFieldEnterListener(Frame frame){
        this.frame = frame;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (((JTextField) e.getSource()).equals(frame.urlText)) {
            frame.enableInfoBtn();

            if (e.getKeyCode() == 10) {
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent arg0) {

    }

}