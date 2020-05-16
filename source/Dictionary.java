import javax.swing.JFrame;

class Dictionary extends JFrame {
    static DictionaryCollection AnhViet = new DictionaryCollection("xml/Anh_Viet.xml", "xml/History.xml");
    static DictionaryCollection VietAnh = new DictionaryCollection("xml/Viet_Anh.xml", "xml/Lichsu.xml");
    static DictionaryCollection FavoriteList = new DictionaryCollection("xml/Favorite_List.xml");
    static DictionaryCollection DanhSachYeuThich = new DictionaryCollection("xml/Danh_Sach_Yeu_Thich.xml");
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void searchDic() {
        FrameSearchDic frame = new FrameSearchDic();
        frame.draw(new Dictionary());
    }

    public void addDeleteVocab() {
        FrameAddDeleteVocab frame = new FrameAddDeleteVocab();
        frame.draw(new Dictionary());
    }

    public void favoriteList() {
        FrameFavoriteList frame = new FrameFavoriteList();
        frame.draw(new Dictionary());
    }

    public void frequencySearch() {
        FrameFrequencySearch frame = new FrameFrequencySearch();
        frame.draw(new Dictionary());
    }

    public static void main(String[] args) {
        new Dictionary().searchDic();
        new Dictionary().addDeleteVocab();
        new Dictionary().favoriteList();
        new Dictionary().frequencySearch();
    }
}