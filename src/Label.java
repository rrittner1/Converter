import javax.swing.*;

public class Label extends JLabel{//labels
    public Label(String content) {
        super(content);
    }
    public void setup(int x, int y, int w, int h) {//makes setup in Converter class more readable
        this.setBounds(x, y, w, h);
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
