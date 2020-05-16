import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FrameFavoriteList extends Dictionary {
    private static int dicType = 1;
    private static int sortType = 1;
    JButton changeDictionary = new JButton();
    JButton changeSortButton = new JButton();
    static JList<String> list = new JList<String>();

    private final int form_width = 400;
    private final int form_heigth = 400;

    public static void setListFavorite() {
        DefaultListModel<String> model = new DefaultListModel<>();

        List<Word> listDic;
        if (dicType == 1)
            listDic = FavoriteList.getDic();
        else
            listDic = DanhSachYeuThich.getDic();

        if (sortType == 2)
            Collections.reverse(listDic);

        Iterator itr = listDic.iterator();
        while (itr.hasNext()) {
            Word find = (Word) itr.next();
            model.addElement(find.text);
        }
        list.setModel(model);
    }

    public void draw(final JFrame frame) {
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(20);
        setListFavorite();

        changeDictionary.setText("Change Dictionary");
        changeSortButton.setText("A - Z");

        final JScrollPane scrollPaneMain = new JScrollPane(list);
        scrollPaneMain.setLocation(0, 0);

        changeDictionary.addActionListener(e -> {
            try {
                if (dicType == 1) {
                    dicType = 2;
                    changeDictionary.setText("Chuyển sang Anh-Việt");
                    frame.setTitle("Danh sách yêu thích");
                    setListFavorite();
                } else {
                    dicType = 1;
                    changeDictionary.setText("Change to VietAnh");
                    frame.setTitle("Your favorite vocabs");
                    setListFavorite();
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        });

        changeSortButton.addActionListener(e -> {
            try {
                if (sortType == 1) {
                    sortType = 2;
                    changeSortButton.setText("Z - A");
                    setListFavorite();
                } else {
                    sortType = 1;
                    changeSortButton.setText("A - Z");
                    setListFavorite();
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        });

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList<?> theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        FrameSearchDic.setTranslationArea(o.toString(), dicType);
                    }
                }
            }
        };
        list.addMouseListener(mouseListener);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Your favorite vocabs");
        frame.setSize(form_width, form_heigth);
        frame.setResizable(false);

        frame.getContentPane().add(BorderLayout.CENTER, scrollPaneMain);
        frame.getContentPane().add(BorderLayout.SOUTH, changeDictionary);
        frame.getContentPane().add(BorderLayout.NORTH, changeSortButton);

        frame.setVisible(true);
    }

}
