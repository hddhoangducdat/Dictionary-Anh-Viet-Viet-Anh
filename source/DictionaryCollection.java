import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DictionaryCollection extends FrameSearchDic {
    private HashSet<Word> list = new HashSet<Word>();
    private HashSet<Word> history = new HashSet<Word>();

    public HashSet<Word> createCollection(String file) {
        HashSet<Word> arr = new HashSet<Word>();
        try {
            ClassLoader c1 = this.getClass().getClassLoader();

            XMLInputFactory factory = XMLInputFactory.newInstance();

            XMLStreamReader tudien = factory.createXMLStreamReader(c1.getResourceAsStream(file));

            while (tudien.hasNext()) {
                String result = "";
                String word = "";
                if (tudien.next() == XMLStreamConstants.START_ELEMENT
                        && tudien.getLocalName().equalsIgnoreCase("word")) {
                    tudien.next();
                    word = tudien.getText();
                    tudien.next();
                    tudien.next();
                    while (tudien.next() != XMLStreamConstants.END_ELEMENT) {

                        if (tudien.hasText()) {
                            result += tudien.getText();
                        }
                    }

                    if (file.equalsIgnoreCase("xml/History.xml") || file.equalsIgnoreCase("xml/Lichsu.xml")) {
                        arr.add(new Word(new Word(word, ""), result));
                    } else {
                        result += "\n";
                        arr.add(new Word(word, result));
                    }
                }
            }
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        }

        return arr;
    }

    public DictionaryCollection(final String file) {
        list = createCollection(file);
    }

    public DictionaryCollection(final String file1, final String file2) {
        list = createCollection(file1);
        history = createCollection(file2);

    }

    public String searchVocab(String text) {
        Iterator itr = list.iterator();
        String result = "";
        while (itr.hasNext()) {
            Word find = (Word) itr.next();
            if (find.text.equalsIgnoreCase(text)) {
                result += find.meaning;
            }
        }
        return result;
    }

    public List<Word> getDic() {
        List<Word> listDic = new ArrayList<Word>(list);
        Collections.sort(listDic, (a, b) -> {
            return a.text.compareTo(b.text);
        });
        return listDic;
    }

    public Boolean isAlreadyHave(Word vocab) {
        if (searchVocab(vocab.text).equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    public static HashSet<Node> searchByWord(String word, Document doc) {
        HashSet<Node> arrayNode = new HashSet<Node>();
        NodeList lst = doc.getElementsByTagName("word");
        for (int i = 0; i < lst.getLength(); i++) {
            String content = lst.item(i).getTextContent();

            if (content.equalsIgnoreCase(word)) {
                Node p = lst.item(i).getParentNode();
                arrayNode.add(p);
            }
        }
        return arrayNode;
    }

    public static void saveData(Document doc, String file) {
        try {
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(file));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void deleteVocab(String vocab, String file) {

        list.removeIf(e -> e.text.equalsIgnoreCase(vocab));

        try {
            ClassLoader c1 = this.getClass().getClassLoader();
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(c1.getResourceAsStream(file));

            HashSet<Node> deleteNode = searchByWord(vocab, document);
            Iterator itr = deleteNode.iterator();
            if (!itr.hasNext()) {
                translationArea.setText(vocab + " doesn't exist in our Database");
                return;
            }
            while (itr.hasNext()) {
                document.getDocumentElement().removeChild((Node) itr.next());
            }
            saveData(document, file);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        translationArea.setText(vocab + " has been removed from Data");
    }

    public void addVocab(Word vocab, String file) {
        if (!isAlreadyHave(vocab)) {
            list.add(vocab);
            translationArea.setText("New Vocab: " + "\n" + vocab.meaning.trim());

            try {
                ClassLoader c1 = this.getClass().getClassLoader();
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(c1.getResourceAsStream(file));

                Element root = document.getDocumentElement();

                Element record = document.createElement("record");
                root.appendChild(record);

                Element word = document.createElement("word");
                word.appendChild(document.createTextNode(vocab.text.trim()));
                record.appendChild(word);

                Element meaning = document.createElement("meaning");
                meaning.appendChild(document.createTextNode(vocab.meaning.trim()));
                record.appendChild(meaning);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File(file));

                transformer.transform(domSource, streamResult);

            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else
            translationArea.setText("Already have that vocab");
    }

    public void addHistoryRecord(Word vocab, String now, String file) {

        try {
            history.add(new Word(vocab, now));
            ClassLoader c1 = this.getClass().getClassLoader();
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(c1.getResourceAsStream(file));

            Element root = document.getDocumentElement();

            Element record = document.createElement("record");
            root.appendChild(record);

            Element word = document.createElement("word");
            word.appendChild(document.createTextNode(vocab.text));
            record.appendChild(word);

            Element meaning = document.createElement("time");
            meaning.appendChild(document.createTextNode(now));
            record.appendChild(meaning);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(file));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Word> getFrequency(Date date1, Date date2) {
        HashSet<String> wordOnly = new HashSet<String>();
        List<String> duplicateWord = new ArrayList<String>();
        Iterator itr = history.iterator();

        while (itr.hasNext()) {
            try {
                Word value = (Word) itr.next();
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(value.time);
                wordOnly.add(value.text);
                if ((date.after(date1) && date.before(date2)) || date.equals(date1) || date.equals(date2)) {
                    duplicateWord.add(value.text);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        List<Word> result = new ArrayList<Word>();
        Iterator itrtor = wordOnly.iterator();
        while (itrtor.hasNext()) {
            Word value = new Word((String) itrtor.next(), "");
            value.setCount(Collections.frequency(duplicateWord, value.text));
            if (value.count != 0)
                result.add(value);
        }
        return result;
    }
}