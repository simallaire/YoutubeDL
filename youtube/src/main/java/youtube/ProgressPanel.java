package youtube;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private JProgressBar progressBar;
  private JLabel detailsLabel;


  public ProgressPanel(){
    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    progressBar = new JProgressBar(0, 100);
    detailsLabel = new JLabel("speed");
    progressBar.setVisible(false);
    detailsLabel.setVisible(false);
    progressBar.setStringPainted(true);
    add(progressBar);
    add(detailsLabel);
  }
  public void updateProgress(String details,String pourcentage,int progress){
    progressBar.setValue(progress);
    progressBar.setString(pourcentage);
    detailsLabel.setText(details);
  }
  public void showHidePane(Boolean show){
    progressBar.setVisible(show);
    detailsLabel.setVisible(show);

  }
  /**
   * @return the progressBar
   */
  public JProgressBar getProgressBar() {
    return progressBar;
  }

  /**
   * @param progressBar the progressBar to set
   */
  public void setProgressBar(JProgressBar progressBar) {
    this.progressBar = progressBar;
  }

  /**
   * @return the detailsLabel
   */
  public JLabel getDetailsLabel() {
    return detailsLabel;
  }

  /**
   * @param detailsLabel the detailsLabel to set
   */
  public void setDetailsLabel(JLabel detailsLabel) {
    this.detailsLabel = detailsLabel;
  }
  

  
}