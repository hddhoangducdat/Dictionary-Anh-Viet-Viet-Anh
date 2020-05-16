import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.*;

public class FrameSearchDic extends Dictionary {

    private int dicType = 1;
    private String form_title = "Anh_Viet";
    static JTextArea translationArea;
    private JTextField searchArea;
    private String noTextSubmit = "Don't leave the box empty !!!";
    private String failedSearch = "We can't translate this word !!!";
    private String isSubmit = "You haven't search anything yet !!!";
    private Word currentSearch;

    JButton searchButton = new JButton();
    JButton changeDictionary = new JButton();
    JButton addFavorite = new JButton();

    private final int form_width = 400;
    private final int form_heigth = 400;

    public void setDicType() {
        if (this.dicType == 1) {
            this.dicType = 2;
            this.form_title = "Viet_Anh";
            this.noTextSubmit = "Đừng để trống ô tìm kiếm !!!";
            this.failedSearch = "Không thể dịch từ này !!!";
            this.isSubmit = "Bạn chưa tìm kiếm từ nào !!!";
        } else {
            this.dicType = 1;
            this.form_title = "Anh_Viet";
            this.noTextSubmit = "Don't leave the box empty !!!";
            this.failedSearch = "We can't translate this word !!!";
            this.isSubmit = "You haven't search anything yet !!!";
        }
    }

    public static void setTranslationArea(String text, int Type) {
        if (Type == 1)
            translationArea.setText(AnhViet.searchVocab(text));
        else
            translationArea.setText(VietAnh.searchVocab(text));
    }

    public void searchHandeler() {
        String word = searchArea.getText().trim().toLowerCase();
        if (word.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(new JFrame(), noTextSubmit);
            searchArea.requestFocus();
        } else {
            String result;
            if (dicType == 1) {
                result = AnhViet.searchVocab(word);
            } else
                result = VietAnh.searchVocab(word);
            if (result == "") {
                JOptionPane.showMessageDialog(new JFrame(), failedSearch, "Warning", JOptionPane.WARNING_MESSAGE);
                searchArea.requestFocus();
            } else {
                translationArea.setText(result + "\n\n");
                currentSearch = new Word(word, result);
                if (dicType == 1) {
                    AnhViet.addHistoryRecord(currentSearch,
                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), "xml/History.xml");
                } else
                    VietAnh.addHistoryRecord(currentSearch,
                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), "xml/Lichsu.xml");
            }
        }
    }

    public void draw(JFrame frame) {
        translationArea = new JTextArea(form_heigth / 19, form_width);
        searchArea = new JTextField();

        JScrollPane scrollPaneMain = new JScrollPane(translationArea);
        scrollPaneMain.setLocation(0, 0);

        translationArea.setLineWrap(true);
        translationArea.setEditable(false);
        searchArea.hasFocus();

        searchButton.setText("Search");
        changeDictionary.setText("Change Dictionary");
        addFavorite.setText("Set current search word at Favorite");

        searchButton.addActionListener(e -> {
            try {
                translationArea.setText("");
                searchHandeler();
                searchArea.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        changeDictionary.addActionListener(e -> {
            try {
                setDicType();
                if (dicType == 1) {
                    searchButton.setText("Search");
                    changeDictionary.setText("Change Dictionary");
                    addFavorite.setText("Add current word at Favorite");
                    frame.setTitle(form_title);
                    searchArea.setText("");
                } else {
                    searchButton.setText("Dịch");
                    changeDictionary.setText("Đổi kiểu tra cứu");
                    addFavorite.setText("Thêm từ vừa dịch vào yêu thích");
                    frame.setTitle(form_title);
                    searchArea.setText("");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        addFavorite.addActionListener(e -> {
            try {
                if (currentSearch == null) {
                    JOptionPane.showMessageDialog(new JFrame(), isSubmit);
                    searchArea.requestFocus();
                } else {
                    if (dicType == 1)
                        FavoriteList.addVocab(currentSearch, "xml/Favorite_List.xml");
                    else
                        DanhSachYeuThich.addVocab(currentSearch, "xml/Danh_Sach_Yeu_Thich.xml");
                }
                FrameFavoriteList.setListFavorite();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(form_title);
        frame.setSize(form_width, form_heigth);
        frame.setResizable(false);

        frame.getContentPane().add(BorderLayout.NORTH, scrollPaneMain);
        frame.getContentPane().add(BorderLayout.CENTER, searchArea);
        frame.getContentPane().add(BorderLayout.EAST, changeDictionary);
        frame.getContentPane().add(BorderLayout.WEST, searchButton);
        frame.getContentPane().add(BorderLayout.SOUTH, addFavorite);

        frame.setVisible(true);

        searchArea.requestFocus();
    }
}