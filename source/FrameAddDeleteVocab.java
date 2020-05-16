import java.awt.*;

import javax.swing.*;

public class FrameAddDeleteVocab extends Dictionary {
    private int dicType = 1;
    private String form_title = "Add & Delete vocab Anh_Viet";
    JTextField wordField;
    JTextArea meaningField;
    private String failedAdd = "Don't leave the box empty !!!";
    private String failedDelete = "You haven't type the vocab yet !!!";

    JButton searchButton = new JButton();
    JButton changeDictionary = new JButton();
    JButton addButton = new JButton();
    JButton deleteButton = new JButton();

    private final int form_width = 500;
    private final int form_heigth = 300;

    public void setDicType() {
        if (this.dicType == 1) {
            this.dicType = 2;
            this.form_title = "Thêm và xóa từ vựng Viet_Anh";
            this.failedAdd = "Đừng để trống !!!";
            this.failedDelete = "Chưa viết từ vựng muốn xóa !!!";
            addButton.setText("Thêm");
            changeDictionary.setText("Chuyển sang Anh-Việt");
            deleteButton.setText("Xóa");
        } else {
            this.dicType = 1;
            this.form_title = "Add & Delete vocab Anh_Viet";
            this.failedAdd = "Don't leave the box empty !!!";
            this.failedDelete = "You haven't type the vocab yet !!!";
            addButton.setText("Add");
            changeDictionary.setText("Change to VietAnh");
            deleteButton.setText("Delete");

        }
    }

    public void draw(JFrame frame) {
        wordField = new JTextField();
        meaningField = new JTextArea(9, 20);

        JScrollPane scrollpane = new JScrollPane(meaningField);

        changeDictionary.setText("Change Dictionary");

        addButton.setText("Add");

        deleteButton.setText("Delete");

        wordField.hasFocus();
        meaningField.hasFocus();

        wordField.setText("What's up");
        meaningField.setText("*  Câu hỏi thăm" + "\n" + "- Dạo này sao rồi" + "\n" + "- Đang làm gì thế");

        changeDictionary.addActionListener(e -> {
            try {
                setDicType();
                frame.setTitle(form_title);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        addButton.addActionListener(e -> {
            try {

                if (wordField.getText().isEmpty() || meaningField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(), failedAdd);
                    wordField.hasFocus();
                    meaningField.hasFocus();
                } else {
                    if (dicType == 1)
                        AnhViet.addVocab(new Word(wordField.getText(),
                                "@" + wordField.getText() + "\n" + meaningField.getText()), "xml/Anh_Viet.xml");
                    else
                        VietAnh.addVocab(new Word(wordField.getText(),
                                "@" + wordField.getText() + "\n" + meaningField.getText()), "xml/Viet_Anh.xml");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        deleteButton.addActionListener(e -> {
            try {

                if (wordField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(), failedDelete);
                    wordField.hasFocus();
                    meaningField.hasFocus();
                } else {
                    if (dicType == 1)
                        AnhViet.deleteVocab(wordField.getText(), "xml/Anh_Viet.xml");
                    else
                        VietAnh.deleteVocab(wordField.getText(), "xml/Viet_Anh.xml");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Add or Delete vocab");
        frame.setSize(form_width, form_heigth);
        frame.setResizable(false);

        frame.getContentPane().add(BorderLayout.CENTER, wordField);
        frame.getContentPane().add(BorderLayout.EAST, deleteButton);
        frame.getContentPane().add(BorderLayout.SOUTH, scrollpane);
        frame.getContentPane().add(BorderLayout.NORTH, changeDictionary);
        frame.getContentPane().add(BorderLayout.WEST, addButton);

        frame.setVisible(true);
    }
}
