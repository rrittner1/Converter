import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Converter {
    //utility test method, currently unused
    public static void test() {;

    }

    //main method
    public static void main(String[] args) {
        //test();
        windowSetUp();
    }

    //sets up window, program will not go anywhere after this until ActionListeners activate
    public static void windowSetUp() {
        //main frame
        window = new MainFrame("Converter");

        //labels
        Label inTypeLabel = new Label("Input Type:");//Component 0
        inTypeLabel.setup(20, 0, 120, 30);
        window.getPanel().add(inTypeLabel);

        Label outTypeLabel = new Label("Output Type:");//Component 1
        outTypeLabel.setup(140, 0, 120, 30);
        window.getPanel().add(outTypeLabel);

        Label inLabel = new Label("Input:");//Component 2
        inLabel.setup(260, 0, 120, 30);
        window.getPanel().add(inLabel);

        Label inDelimLabel = new Label("Input Delimiter:");//Component 3
        inDelimLabel.setup(260, 60, 120, 30);
        window.getPanel().add(inDelimLabel);

        Label outDelimLabel = new Label("Output Delimiter:");//Component 4
        outDelimLabel.setup(260, 120, 120,30);
        window.getPanel().add(outDelimLabel);

        Label outLabel = new Label("Output:");//Component 5
        outLabel.setup(260,240,120,30);
        window.getPanel().add(outLabel);

        Label warningLabel = new Label("Inputs of more than 4 bytes" +
                " must be handled in byte form");//Component 6
        warningLabel.setup(0, 330, 400, 30);
        window.getPanel().add(warningLabel);

        //Entry Fields
        EntryField inTypeField = new EntryField(0);//Component 7
        inTypeField.setBounds(20, 30, 120, 25);
        window.getPanel().add(inTypeField);

        EntryField outTypeField = new EntryField(1);//Component 8
        outTypeField.setBounds(140, 30, 120, 25);
        window.getPanel().add(outTypeField);

        EntryField inField = new EntryField(2);//Component 9
        inField.setBounds(260, 30, 120, 25);
        window.getPanel().add(inField);

        EntryField inDelimField = new EntryField(3);//Component 10
        inDelimField.setBounds(260, 90, 120, 25);
        window.getPanel().add(inDelimField);

        EntryField outDelimField = new EntryField(4);//Component 11
        outDelimField.setBounds(260, 150, 120,25);
        window.getPanel().add(outDelimField);

        EntryField outField = new EntryField(5);//Component 12
        outField.setBounds(260, 270, 120, 25);
        window.getPanel().add(outField);

        //buttons
        String[] labels = {"Binary (b2)", "Decimal (b10)", "Hex (b16)", "Base 64",
                "Binary bytes", "Hex bytes", "Base 64 bytes", "String"};
        Button[] inputTypeButtons = new Button[8];
        Button[] outputTypeButtons = new Button[8];
        for (int i = 0; i < inputTypeButtons.length; i++) {//Components 13-20
            inputTypeButtons[i] = new Button(labels[i], 0, inTypeField);
            window.getPanel().add(inputTypeButtons[i]);
            inputTypeButtons[i].setBounds(20, 70 + 32*i, 120, 32);
        }
        for (int i = 0; i < outputTypeButtons.length; i++) {//Components 21-28
            outputTypeButtons[i] = new Button(labels[i], 1, outTypeField);
            window.getPanel().add(outputTypeButtons[i]);
            outputTypeButtons[i].setBounds(140, 70 + 32*i, 120, 32);
        }

        window.setVisible(true);
    }

    //static variables
    private static final String B64CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static String inputDelimiter = "";
    private static String outputDelimiter = "";
    private static MainFrame window;
    private static String inputType;
    private static String outputType;
    private static String in;

    //setting input/output types and handling  -n-  command
    public static void setInputType(String str) {
        if (str.contains(" -n- ")) {
            inputType = str.substring(0, str.indexOf(" -n- "));
            setOutputType(str.substring(str.indexOf(" -n- ") + 5));
            ((EntryField)window.getPanel().getComponents()[5]).setText(inputType);
        } else {
            inputType = str;
        }
    }
    public static void setOutputType(String str) {
        if (str.contains(" -n- ")) {
            outputType = str.substring(0, str.indexOf(" -n- "));
            setInput(str.substring(str.indexOf(" -n- ") + 5));
            ((EntryField)window.getPanel().getComponents()[6]).setText(outputType);
        } else {
            outputType = str;
        }
    }

    //setting input and calling runConversions()
    public static void setInput(String str) {
        in = str;
        Component[] arr = window.getPanel().getComponents();
        ((EntryField)arr[9]).setText(in);
        String out = runConversions();
        ((EntryField)arr[12]).setText(out);
    }

    //setting delimiters from entry field input
    public static void setInputDelimiter(String str) {
        inputDelimiter = str;
    }
    public static void setOutputDelimiter(String str) {
        outputDelimiter = str;
    }

    //calls necessary conversion methods to carry out conversion
    public static String runConversions() {
        /*Scanner scan = new Scanner(System.in);
        System.out.println("input type:");
        String inputType = scan.nextLine();
        System.out.println("output type:");
        String outputType = scan.nextLine();
        System.out.println("input:");
        String in = scan.nextLine();*/
        String out = "";
        String bitString = "";
        String[] byteArray = new String[0];
        if (inputType.equals(outputType)) {
            return in;
        }
        //delimiter handling
        if (inputType.contains(" -d=")) {
            inputDelimiter = inputType.substring(inputType.indexOf(" -d=") + 4);
            inputType = inputType.substring(0, inputType.indexOf(" -d="));
        }
        if (!inputDelimiter.equals("")) {
            for (int i = 0; i < in.length() - inputDelimiter.length() + 1; i++) {
                if (in.substring(i, i + inputDelimiter.length()).equals(inputDelimiter)) {
                    in = in.substring(0, i) + in.substring(i + inputDelimiter.length());
                    i--;
                }
            }
        }
        if (outputType.contains(" -d=")) {
            outputDelimiter = outputType.substring(outputType.indexOf(" -d=") + 4);
            outputType = outputType.substring(0, outputType.indexOf(" -d="));
        }
        //input to bitstring
        switch (inputType) {
            case "b2": case "binary": case "2": case "b": case "Binary (b2)":
                bitString = in;
                break;
            case "b10": case "decimal": case "10": case "d": case "dec": case "Decimal (b10)":
                bitString = intToBitString(Integer.parseInt(in));
                break;
            case "b16": case "hex": case "16": case "h": case "x": case "Hex (b16)":
                bitString = hexToBitString(in);
                break;
            case "b64": case "64": case "Base 64":
                bitString = b64ToBitString(in);
                break;
            case "b2bytes": case "b2b": case "2b": case "2bytes": case "bytes": case "bb":
            case "Binary bytes":
                bitString = in;
                break;
            case "b16bytes": case "hexbytes": case "xbytes": case "xb": case "hb": case "16b":
            case "Hex bytes":
                bitString = hexByteStringToBitString(in);
                break;
            case "b64byteTriplets": case "64byteset": case "64b": case "b64b": case "Base 64 bytes":
                bitString = b64byteStringToBitString(in);
                break;
            case "String": case "s": case "string": case "S": case "chars": case "c":
                bitString = stringToBitString(in);
                break;
        }
        //bitstring to output
        switch (outputType) {
            case "b2": case "binary": case "2": case "b": case "Binary (b2)":
                out = bitString;
                break;
            case "b10": case "decimal": case "10": case "d": case "dec": case "Decimal (b10)":
                out = "" + bitStringToInt(bitString);
                break;
            case "b16": case "hex": case "16": case "h": case "x": case "Hex (b16)":
                out = bitStringToHex(bitString);
                break;
            case "b64": case "64": case "Base 64":
                out = bitStringToB64(bitString);
                break;
            case "b2bytes": case "b2b": case "2b": case "2bytes": case "bytes": case "bb":
            case "Binary bytes":
                out = arrayToListString(bitStringToByteArray(bitString));
                break;
            case "b16bytes": case "hexbytes": case "xbytes": case "xb": case "hb": case "16b":
            case "Hex bytes":
                out = arrayToListString(bitStringToHexBytes(bitString));
                break;
            case "b64byteTriplets": case "64byteset": case "64b": case "b64b": case "Base 64 bytes":
                out = arrayToListString(bitStringToB64ByteTriplets(bitString));
                break;
            case "String": case "s": case "string": case "S": case "chars": case "c":
                out = bitStringToString(bitString);
                break;
        }
        return out;
    }
    //Conversion methods, some are redundant/unused
    //Methods converting input to bit String or byte Array
    public static String[] bitStringToByteArray(String bits) {
        while (bits.length() % 8 != 0) {
            bits = "0" + bits;
        }
        String[] out = new String[bits.length()/8];
        for (int i = 0; i < bits.length(); i += 8) {
            out[i/8] = bits.substring(i, i + 8);
        }
        return out;
    }
    public static String intToBitString(int n) {
        return Integer.toBinaryString(n);
    }
    public static String hexToBitString(String hex) {
        return Integer.toBinaryString(Integer.parseInt(hex,16));
    }
    public static String hexByteStringToBitString(String hexBytes) {
        String out = "";
        for (int i = 0; i < hexBytes.length(); i += 2) {
            String temp = hexToBitString(hexBytes.substring(i, i + 2));
            while (temp.length() % 8 != 0) {
                temp = "0" + temp;
            }
            out += temp;
        }
        return out;
    }
    public static String b64ToBitString(String str) {
        String out = "";
        for (int i = 0; i < str.length(); i++) {
            String temp = Integer.toBinaryString(B64CHARS.indexOf(str.charAt(i)));
            while (temp.length() < 6) {
                temp = "0" + temp;
            }
            out += temp;
        }
        return out;
    }
    public static String b64byteStringToBitString(String str) {
        String out = "";
        for (int i = 0; i < str.length(); i += 4) {
            out += b64ToBitString(str.substring(i, i + 4));
        }
        return out;
    }
    public static String stringToBitString(String str) {
        String out = "";
        for (int i = 0; i < str.length(); i++) {
            String temp = Integer.toBinaryString(str.charAt(i));
            while(temp.length() % 8 != 0) {
                temp = "0" + temp;
            }
            out += temp;
        }
        return out;
    }

    //Methods converting bit String or byte Array to desired output type
    public static String byteArrayToBitString(String[] arr) {
        String out = "";
        for (int i = 0; i < arr.length; i++) {
            while (arr[i].length() % 8 != 0) {
                arr[i] = "0" + arr[i];
            }
            out += arr[i];
        }
        return out;
    }
    public static String bitStringToInt(String bits) {
        return "" + Integer.parseInt(bits,2);
    }
    public static String byteArrayToInt(String[] arr) {
        String out = "";
        for (int i = 0; i < arr.length; i++) {
            while (arr[i].length() % 8 != 0) {
                arr[i] = "0" + arr[i];
            }
            out += arr[i];
        }
        return bitStringToInt(out);
    }
    public static String bitStringToHex(String bits) {
        return Integer.toHexString(Integer.parseInt(bits,2));
    }
    public static String byteArrayToHex(String[] arr) {
        String out = "";
        for (int i = 0; i < arr.length; i++) {
            while (arr[i].length() % 8 != 0) {
                arr[i] = "0" + arr[i];
            }
            out += arr[i];
        }
        return bitStringToHex(out);
    }
    public static String bitStringToB64(String bits) {
        String out = "";
        while (bits.length() % 6 != 0) {
            bits = "0" + bits;
        }
        for (int i = 0; i < bits.length(); i += 6) {
            out += B64CHARS.charAt(Integer.parseInt(bits.substring(i, i + 6),2));
        }
        return out;
    }
    public static String[] bitStringToHexBytes(String bits) {
        while (bits.length() % 8 != 0) {
            bits = "0" + bits;
        }
        String[] out = new String[bits.length()/8];
        for (int i = 0; i < out.length; i++) {
            String temp =Integer.toHexString(Integer.parseInt(bits.substring(i*8, i*8 + 8),2));
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            out[i] = temp;
        }
        return out;
    }
    public static String[] bitStringToB64ByteTriplets(String bits) {
        while (bits.length() % 24 != 0) {
            bits = "0" + bits;
        }
        String[] out = new String[bits.length()/24];
        for (int i = 0; i < out.length; i++) {
            out[i] = bitStringToB64(bits.substring(i*24, i*24 + 24));
        }
        return out;
    }
    public static String bitStringToString(String bits) {
        String out = "";
        while (bits.length() % 8 != 0) {
            bits = "0" + bits;
        }
        for (int i = 0; i < bits.length(); i += 8) {
            out += (char)Integer.parseInt(bits.substring(i, i + 8),2);
        }
        return out;
    }

    //formatting arrays into String list
    public static String arrayToListString(String[] arr) {
        String out = "";
        for (int i = 0; i < arr.length; i++) {
            out += arr[i] + outputDelimiter;
        }
        return out.substring(0,out.length() - outputDelimiter.length());
    }
}

