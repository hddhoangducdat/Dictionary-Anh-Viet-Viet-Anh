public class Word {
    public String meaning = "";
    public String text = "";
    public String time;
    public int count = 1;

    public Word(String text, String meaning) {
        this.meaning = meaning;
        this.text = text;
    }

    public Word(Word w, String time) {
        this.meaning = w.meaning;
        this.text = w.text;
        this.time = time;
    }

    public Word(Word index) {
        this.meaning = index.meaning;
        this.text = index.text;
    }

    public Boolean isEmpty() {
        if (text == "")
            return true;
        return false;
    }

    public void setCount(int count) {
        this.count = count;
    }

}