package youtube;

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
        // Download & Save Button
        if (source.equals(frame.dlBtn)) {
            handleDownload();
        }
        // Browse button
        if (source.equals(frame.browseBtn)) {
            folderBrowse();

        }
        // Get info button
        if (source.equals(frame.infoBtn)) {
            getInfos();
        }
    }
    public void handleDownload(){
        Format format = this.frame.formatList.getSelectedValue(); 
        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    Terminal.dlFile(frame.urlText.getText(), frame.folderText.getText(), format, frame);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        t.start();
    }
    public void folderBrowse(){
        JFileChooser folder = new JFileChooser();
        folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folder.setVisible(true);
        int confirmed = folder.showOpenDialog(frame);
        if (confirmed == JFileChooser.APPROVE_OPTION) {
            frame.folderText.setText(folder.getCurrentDirectory().toString());
        }
    }
    public void getInfos(){
            frame.infoBtn.setEnabled(false);
            Thread t = new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                    Terminal.getFormats(frame.urlText.getText());
                    frame.setThumbnail();
                    frame.titleText.setText(Terminal.getTitle(frame.urlText.getText()));
                    System.out.println("Show Formats");
                    frame.initFormatList();
                    } catch (IOException e1) {
                    }
                }

            });
            t.start();
            frame.infoBtn.setEnabled(false);

  

    }


}
