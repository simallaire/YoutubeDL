package youtube;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class BoundedChangeListener implements ChangeListener {
  public void stateChanged(ChangeEvent changeEvent) {
    Object source = changeEvent.getSource();
    if (source instanceof JProgressBar) {
      JProgressBar theJProgressBar = (JProgressBar) source;
      System.out.println("ProgressBar changed: " + theJProgressBar.getValue());
    } else {
      System.out.println("Something changed: " + source);
    }
  }
}

public class Main {

  public static void main(String args[]) throws Exception {
    JFrame frame = new JFrame("Stepping Progress");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final JProgressBar aJProgressBar = new JProgressBar(JProgressBar.VERTICAL);
    aJProgressBar.setStringPainted(true);

    aJProgressBar.addChangeListener(new BoundedChangeListener());

    frame.add(aJProgressBar, BorderLayout.NORTH);
    frame.setSize(300, 200);
    frame.setVisible(true);
    
    for (int i = 0; i < 100; i++) {
      aJProgressBar.setValue(i++);
      Thread.sleep(100);
    }


  }
}