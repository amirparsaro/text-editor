package editor;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;

public class FileManager {
    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find file");
                return;
            }

            String content = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                content += line + "\n";
            }

            textArea.setText(content);
        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        if (textEditor.currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                PrintStream output;
                try {
                    output = new PrintStream(file);
                    output.print(textArea.getText());
                } catch (FileNotFoundException e) {
                    System.out.println("Cannot find file");
                    return;
                }
            }
        } else {
            File file = textEditor.currentFile;
            try (PrintStream output = new PrintStream(file)) {
                output.print(textArea.getText());
            } catch (FileNotFoundException e) {
                System.out.println("Cannot save the file");
            }
        }
    }

    public static void newFile(TextEditor textEditor, JTextArea textArea) {
        textArea.setText("");
        textEditor.currentFile = null;
    }
}
