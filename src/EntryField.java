import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryField extends JTextField implements ActionListener{//text entry fields
    private MainFrame frame;
    private int position;
    public EntryField(int i) {
        super();
        addActionListener(this);
        this.frame = frame;
        position = i;
    }
    @Override public void actionPerformed(ActionEvent e) {//activates on ENTER while typing in field
        String str = this.getText();
        if (position == 0) {//inputType field
            Converter.setInputType(str);
        } else if (position == 1) {//outputType field
            Converter.setOutputType(str);
        } else if (position == 2) {//input field
            Converter.setInput(str);
        } else if (position == 3) {//inputDelimiter field
            Converter.setInputDelimiter(str);
        } else if (position == 4) {//outputDelimiter field
            Converter.setOutputDelimiter(str);
        }
    }
}
