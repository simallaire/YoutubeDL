package youtube;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class Frame extends JFrame implements Runnable {

    /**
     *
     */

    private static final String DEFAULT_PATH = "/";
    private static final String BROWSE = "Browse...";
    private static final String GET_VIDEO_INFOS = "Get video infos";
    private static final String DOWNLOAD_SAVE = "Download & save";
    private static final String IMG_PATH = "youtube/src/main/java/youtube/image.jpg";
    private static final String YOUTUBE_DEFAULT = "https://www.youtube.com/watch?v=";

    private static final long serialVersionUID = 1L;

    private Container contenu;
    private JPanel urlPanel;
    private JPanel imgPanel;
    private JPanel outputPanel;
    private JLabel imgLabel;
    public JLabel titleText;
    public JTextField urlText;
    public JTextField folderText;
    public JButton dlBtn;
    public JButton infoBtn;
    public JButton browseBtn;
    private JScrollPane formatScrollPane;
    public JList<Format> formatList;
    private DefaultListModel<Format> listModelFormat;
    private ArrayList<Format> formatArray;
    private ProgressPanel progressPanel;

    public void run() {
        contenu = getContentPane();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        
        initComponents();
        
        fillPanes();
        
        addListeners();
        
        imgPanel.grabFocus();
        setSize(600, 200);
        setVisible(true);
        pack();
        setTitle("Download & Convert Youtube videos.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void fillPanes() {

        urlPanel.add(urlText);
        urlPanel.add(infoBtn);

        imgPanel.setLayout(new FlowLayout());
        imgPanel.add(formatScrollPane);
        imgPanel.add(imgLabel);

        outputPanel.add(folderText);
        outputPanel.add(browseBtn);
        outputPanel.add(dlBtn);
        outputPanel.add(progressPanel);

        contenu.add(urlPanel);
        contenu.add(titleText);
        contenu.add(imgPanel);
        contenu.add(outputPanel);

    }

    private void initComponents() {

        progressPanel = new ProgressPanel();
        urlPanel = new JPanel();
        outputPanel = new JPanel();
        imgPanel = new JPanel();
        urlText = new JTextField(YOUTUBE_DEFAULT);
        folderText = new JTextField(DEFAULT_PATH);
        titleText = new JLabel();
        imgLabel = new JLabel();
        dlBtn = new JButton(DOWNLOAD_SAVE);
        infoBtn = new JButton(GET_VIDEO_INFOS);
        browseBtn = new JButton(BROWSE);
        listModelFormat = new DefaultListModel<>();
        formatArray = new ArrayList<>();
        formatList = new JList<>(listModelFormat);


        urlText.setColumns(30);
        folderText.setColumns(25);
        dlBtn.setEnabled(false);
        infoBtn.setEnabled(false);
        imgPanel.setLayout(new FlowLayout());

        imgLabel.setIcon(new ImageIcon(IMG_PATH));

        imgLabel.setSize(1000, 1000);
        listModelFormat.add(0, new Format());

        formatList.setPreferredSize(new Dimension(200, 1000));
        formatScrollPane = new JScrollPane(formatList);
    }

    private void addListeners() {

        dlBtn.addActionListener(new ButtonListener(this));
        infoBtn.addActionListener(new ButtonListener(this));
        browseBtn.addActionListener(new ButtonListener(this));
        urlText.addFocusListener(new TextFieldListener(this));
        urlText.addKeyListener(new TextFieldEnterListener(this));
        formatList.addListSelectionListener(new FormatListListener(this));


    }

    public void setThumbnail() throws IOException {
        String imgUrl = Terminal.getThumbnailUrl(urlText.getText());
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
            if (!temp.contains("info") && !temp.contains("format")) {
                Format f = new Format(temp);
                formatArray.add(f);
                listModelFormat.add(i, f);
                i++;
            }
        }
        
        formatList.setPreferredSize(new Dimension(200, 1000));

    }

    public void progress(String pourcentage,String details) {

        try {
            double pourcent = Double.parseDouble(pourcentage.substring(0, pourcentage.indexOf("%")));
            progressPanel.showHidePane(true);
            progressPanel.updateProgress(details, pourcentage, (int)pourcent);
            Thread.sleep(1000);
            if (pourcent >= 99.9) {
                progressPanel.showHidePane(false);
            }
        } catch (Exception ex) {
        
        }
    }

    public void enableInfoBtn() {
        if (urlText.getText().contains(YOUTUBE_DEFAULT)) {
            infoBtn.setEnabled(true);
        } else {
            infoBtn.setEnabled(false);
        }
    }

    /**
     * @param contenu the contenu to set
     */
    public void setContenu(Container contenu) {
        this.contenu = contenu;
    }

    /**
     * @return the urlPanel
     */
    public JPanel getUrlPanel() {
        return urlPanel;
    }

    /**
     * @param urlPanel the urlPanel to set
     */
    public void setUrlPanel(JPanel urlPanel) {
        this.urlPanel = urlPanel;
    }

    /**
     * @return the imgPanel
     */
    public JPanel getImgPanel() {
        return imgPanel;
    }

    /**
     * @param imgPanel the imgPanel to set
     */
    public void setImgPanel(JPanel imgPanel) {
        this.imgPanel = imgPanel;
    }

    /**
     * @return the outputPanel
     */
    public JPanel getOutputPanel() {
        return outputPanel;
    }

    /**
     * @param outputPanel the outputPanel to set
     */
    public void setOutputPanel(JPanel outputPanel) {
        this.outputPanel = outputPanel;
    }

    /**
     * @return the imgLabel
     */
    public JLabel getImgLabel() {
        return imgLabel;
    }

    /**
     * @param imgLabel the imgLabel to set
     */
    public void setImgLabel(JLabel imgLabel) {
        this.imgLabel = imgLabel;
    }

    /**
     * @return the urlText
     */
    public JTextField getUrlText() {
        return urlText;
    }

    /**
     * @param urlText the urlText to set
     */
    public void setUrlText(JTextField urlText) {
        this.urlText = urlText;
    }

    /**
     * @return the folderText
     */
    public JTextField getFolderText() {
        return folderText;
    }

    /**
     * @param folderText the folderText to set
     */
    public void setFolderText(JTextField folderText) {
        this.folderText = folderText;
    }

    /**
     * @return the dlBtn
     */
    public JButton getDlBtn() {
        return dlBtn;
    }

    /**
     * @param dlBtn the dlBtn to set
     */
    public void setDlBtn(JButton dlBtn) {
        this.dlBtn = dlBtn;
    }

    /**
     * @return the infoBtn
     */
    public JButton getInfoBtn() {
        return infoBtn;
    }

    /**
     * @param infoBtn the infoBtn to set
     */
    public void setInfoBtn(JButton infoBtn) {
        this.infoBtn = infoBtn;
    }

    /**
     * @return the browseBtn
     */
    public JButton getBrowseBtn() {
        return browseBtn;
    }

    /**
     * @param browseBtn the browseBtn to set
     */
    public void setBrowseBtn(JButton browseBtn) {
        this.browseBtn = browseBtn;
    }

    /**
     * @return the formatScrollPane
     */
    public JScrollPane getFormatScrollPane() {
        return formatScrollPane;
    }

    /**
     * @param formatScrollPane the formatScrollPane to set
     */
    public void setFormatScrollPane(JScrollPane formatScrollPane) {
        this.formatScrollPane = formatScrollPane;
    }

    /**
     * @return the formatList
     */
    public JList<Format> getFormatList() {
        return formatList;
    }

    /**
     * @param formatList the formatList to set
     */
    public void setFormatList(JList<Format> formatList) {
        this.formatList = formatList;
    }

    /**
     * @return the listModelFormat
     */
    public DefaultListModel<Format> getListModelFormat() {
        return listModelFormat;
    }

    /**
     * @param listModelFormat the listModelFormat to set
     */
    public void setListModelFormat(DefaultListModel<Format> listModelFormat) {
        this.listModelFormat = listModelFormat;
    }

    /**
     * @return the formatArray
     */
    public ArrayList<Format> getFormatArray() {
        return formatArray;
    }

    /**
     * @param formatArray the formatArray to set
     */
    public void setFormatArray(ArrayList<Format> formatArray) {
        this.formatArray = formatArray;
    }




}
