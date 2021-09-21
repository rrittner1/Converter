import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton implements ActionListener {//buttons
    private String label;
    private int column;
    private EntryField field;
    public Button(String label, int i, EntryField f) {
        super(label);
        this.label = label;
        addActionListener(this);
        column = i;
        field = f;
    }
    @Override public void actionPerformed(ActionEvent e) {//Activates on click
        String str = this.label;
        if (column == 0) {//inputType buttons
            Converter.setInputType(str);

        } else if (column == 1) {//outputType buttons
            Converter.setOutputType(str);
        }
        field.setText(label);
    }
}