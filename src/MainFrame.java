import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainFrame extends JFrame {//main window frame
    private final int width = 400;
    private final int height = 400;
    private JPanel panel;
    public MainFrame(String title) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(this.panel);
    }
    //returns the panel with everything on it;
    public JPanel getPanel() {
        return panel;
    }


}