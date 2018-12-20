package com.example.youtube;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class Frame extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    private Container contenu;
    private JPanel urlPanel;
    private JPanel imgPanel;
    private JPanel outputPanel;
    private JLabel imgLabel;
    public JTextField urlText;
    public JTextField folderText;
    public JButton dlBtn;
    public JButton infoBtn;
    public JButton browseBtn;
    public JList<Format> formatList;
    private DefaultListModel<Format> listModelFormat;
    private ArrayList<Format> formatArray = new ArrayList<>();

    public void run() {

        contenu = getContentPane();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        // contenu.setLayout(new FlowLayout());
        urlPanel = new JPanel();
        outputPanel = new JPanel();
        urlText = new JTextField("https://www.youtube.com/watch?v=XXX");
        urlText.setColumns(30);
        folderText = new JTextField("text");
        folderText.setColumns(25);
        dlBtn = new JButton("Download & save");
        infoBtn = new JButton("Get video infos");
        infoBtn.setEnabled(false);
        browseBtn = new JButton("Browse...");
        dlBtn.addActionListener(new ButtonListener(this));
        infoBtn.addActionListener(new ButtonListener(this));
        browseBtn.addActionListener(new ButtonListener(this));
        dlBtn.setEnabled(false);
        urlText.addFocusListener(new TextFieldListener());
        urlText.addKeyListener(new TextFieldEnterListener());
        urlPanel.add(urlText);
        urlPanel.add(infoBtn);

        imgPanel = new JPanel();
        imgPanel.setLayout(new FlowLayout());

        imgLabel = new JLabel();
        imgLabel.setIcon(new ImageIcon("youtube/src/main/java/com/example/youtube/image.jpg"));

        imgLabel.setSize(1000, 1000);
        listModelFormat = new DefaultListModel<>();
        listModelFormat.add(0, new Format());

        formatList = new JList<>(listModelFormat);
        formatList.addListSelectionListener(new FormatListListener(this));
        imgPanel.setLayout(new FlowLayout());
        imgPanel.add(formatList);
        imgPanel.add(imgLabel);

        outputPanel.add(folderText);
        outputPanel.add(browseBtn);
        outputPanel.add(dlBtn);

        contenu.add(urlPanel);
        contenu.add(imgPanel);
        contenu.add(outputPanel);
        imgPanel.grabFocus();
        setSize(600, 200);
        setVisible(true);
        pack();

    }

    public void setThumbnail() throws IOException {
        String imgUrl = Terminal.getThumbnail(urlText.getText());
        BufferedImage bImg = ImageIO.read(new URL(imgUrl));
        ImageIcon iIcon = new ImageIcon(bImg);

        imgLabel.setIcon(new ImageIcon(getScaledImage(iIcon.getImage(), 480, 270)));
        setSize(800, 600);
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public void initFormatList() {
        listModelFormat.removeAllElements();
        int i = 0;
        for (String temp : Terminal.formatsArray) {
            System.out.println(temp);
            Format f = new Format();
            f.setId(Integer.parseInt(temp.substring(0, temp.indexOf(" ") - 1)));
            String audioVideo = temp.substring(24);
            audioVideo = audioVideo.substring(0, audioVideo.indexOf(" "));
            if (audioVideo.equals("audio")) {
                f.setAudioOnly(true);
            } else {
                int xPos = audioVideo.indexOf("x");
                int width = Integer.parseInt(audioVideo.substring(0, xPos).toString());
                int height = Integer.parseInt(audioVideo.substring(xPos + 1).toString());
                f.setWidth(width);
                f.setHeight(height);
            }
            f.setExtension(temp.substring(13, 17));
            formatArray.add(f);
            listModelFormat.add(i, f);
            i++;
        }

    }

    public void enableInfoBtn() {
        if (urlText.getText().contains("https://www.youtube.com/watch?v=")) {
            infoBtn.setEnabled(true);
        } else {
            infoBtn.setEnabled(false);
        }
    }

    public class TextFieldListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            urlText.selectAll();
            enableInfoBtn();

        }

        @Override
        public void focusLost(FocusEvent e) {
            enableInfoBtn();
        }

    }

    public class TextFieldEnterListener implements KeyListener {
        @Override
        public void keyReleased(KeyEvent e) {
            if (((JTextField) e.getSource()).equals(urlText)) {
                enableInfoBtn();

                if (e.getKeyCode() == 10) {
                    try {
                        Terminal.getFormats(urlText.getText());
                        setThumbnail();
                    } catch (IOException e1) {
                    }
                    System.out.println("Show Formats");
                    initFormatList();

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
}