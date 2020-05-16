import javax.swing.*;
import javax.swing.JSpinner.DateEditor;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.chrono.JapaneseDate;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class FrameFrequencySearch extends Dictionary {
    private static int dicType = 1;
    static JList<String> list = new JList<String>();
    JButton changeDictionary = new JButton();
    JButton submitButton = new JButton();

    private JTextField Date1 = new JTextField();
    private JTextField Date2 = new JTextField();

    private final int form_width = 400;
    private final int form_heigth = 400;

    public static void setListHistory(Date date1, Date date2) {
        DefaultListModel<String> model = new DefaultListModel<>();

        List<Word> listDic;
        if (dicType == 1)
            listDic = AnhViet.getFrequency(date1, date2);
        else
            listDic = VietAnh.getFrequency(date1, date2);

        Iterator itr = listDic.iterator();
        while (itr.hasNext()) {
            Word value = (Word) itr.next();
            model.addElement(value.text + " - " + value.count + " times");
        }
        list.setModel(model);
    }

    public void draw(final JFrame frame) {
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

        changeDictionary.setText("Change to VietAnh");
        submitButton.setText("Search's history");

        Date1.hasFocus();
        Date2.hasFocus();

        try {
            Date1.setText("01/01/2020");
            Date2.setText("31/12/2020");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final JScrollPane scrollPaneMain = new JScrollPane(list);
        scrollPaneMain.setLocation(0, 0);

        changeDictionary.addActionListener(e -> {
            try {
                if (dicType == 1) {
                    dicType = 2;
                    changeDictionary.setText("Chuyển sang Anh-Việt");
                    submitButton.setText("Lịch sử tìm kiếm");
                    frame.setTitle("Lịch sử tìm kiếm");
                } else {
                    dicType = 1;
                    changeDictionary.setText("Change to VietAnh");
                    submitButton.setText("Search's history");
                    frame.setTitle("Search's history");
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        });

        submitButton.addActionListener(e -> {
            try {
                if (Date1.getText().equalsIgnoreCase("") || Date2.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "You haven't type date yet");
                    Date1.requestFocus();
                    Date2.requestFocus();
                } else {
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(Date1.getText().trim());
                    Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(Date2.getText().trim());
                    if (date2.before(date1)) {
                        JOptionPane.showMessageDialog(new JFrame(), "date2 before date1");
                        Date1.requestFocus();
                        Date2.requestFocus();
                    }

                    setListHistory(date1, date2);

                }
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        });

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(5, 5));

        content.setBorder(new EmptyBorder(6, 6, 6, 6));

        final MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(final MouseEvent mouseEvent) {
                final JList<?> theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    final int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        final Object o = theList.getModel().getElementAt(index);
                        StringTokenizer st = new StringTokenizer(o.toString(), " - ");

                        FrameSearchDic.setTranslationArea(st.nextToken(), dicType);
                    }
                }
            }
        };
        list.addMouseListener(mouseListener);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Search's history");
        frame.setSize(form_width, form_heigth);
        frame.setResizable(false);

        content.add(BorderLayout.CENTER, scrollPaneMain);
        content.add(BorderLayout.NORTH, changeDictionary);
        content.add(BorderLayout.SOUTH, submitButton);
        content.add(BorderLayout.EAST, Date2);
        content.add(BorderLayout.WEST, Date1);
        frame.setContentPane(content);

        frame.setVisible(true);
    }
}
