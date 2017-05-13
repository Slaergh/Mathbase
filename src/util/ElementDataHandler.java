package util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasse, welche sich um die Verwaltung der Element-Daten handelt;
 * mit Hilfe der XMLFileHandler
 */

//!!!!!!FEHLERVERARBEITUNG MUSS NOCH STATTFINDEN!!!!!!!

public class ElementDataHandler {
    private XMLFileHandler xmlHandler;

    public static final String ATTRIBUTE_TITLE = "title";
    public static final String FILE_TYPE_PICTURE = "picture";
    public static final String FILE_TYPE_DESCRIPTION = "description";
    public static final String FILE_TYPE_MOVIE = "movie";

    private static String originfile="src/topics.xml";
    private static String targetfile="src/topics.xml"; //FILEPATHS MÜSSEN NOCH HINZUGEFÜGT WERDEN
    private static ElementDataHandler ELEMENT_DATA_HANDLER=new ElementDataHandler(originfile); //Hält die Reference zum einzigen existierenden Objekt der Klasse ElementDataHandler

    private ElementDataHandler(String filePath){
        try {
            xmlHandler=new XMLFileHandler(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ElementDataHandler getElementDataHandler(){
        return ELEMENT_DATA_HANDLER;
    }

    /**
     * Methode, die ein bestimmtes Attribut eines Elements zurückgibt
     * @param key Eindeutiger Schlüssel des Elements
     * @param attributeName Name des Attributs
     * @return Wert des Attributs
     */
    public String getElementAttribute(String key, String attributeName) {
        try {
            NodeList nodelist=xmlHandler.getNodeListXPath("//elementlist/element[@key=\""+key+"\"]");
            Node inode=nodelist.item(0);
            if(inode.getNodeType()==Node.ELEMENT_NODE){
                Element listelement=(Element)inode;
                return listelement.getAttribute(attributeName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Methode, die alle Filepaths eines bestimmten Elements mit dem bestimmten Typ zurückgibt
     * @param key Eindeutiger Schlüssel des Elements
     * @param type Welche typen zurückgegeben werden sollen
     * @return Array mit allen Filepaths vom Typ type
     */
    public String[] getElementFilePathsByType(String key, String type){
        String[] pathslist=new String[0];
        try {
            NodeList nodelist=xmlHandler.getNodeListXPath("//topiclist/theme/element[@key=\""+key+"\"]/file[@type=\""+type+"\"]");
            pathslist=new String[nodelist.getLength()];
            for(int i=0;i<nodelist.getLength();i++){
                Node inode=nodelist.item(i);
                pathslist[i]=inode.getTextContent();
            }
            return pathslist;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathslist;
    }

    /**
     * Methode, die eine Liste aller Keys der Elemente eines bestimmten Themas zurückgibt
     * @param themekey Key des gewünschten Themas
     * @return Array mit allen Keys
     */
    public String[] getElementKeyList(String themekey){
        String[] keylist=new String[0];
        try {
            NodeList nodelist=xmlHandler.getNodeListXPath("//topiclist/theme[@key=\""+themekey+"\"]/element");
            keylist=new String[nodelist.getLength()];
            for(int i=0;i<nodelist.getLength();i++){
                Node inode=nodelist.item(i);
                if(inode.getNodeType()==Node.ELEMENT_NODE){
                    Element ielement=(Element)inode;
                    keylist[i]=ielement.getAttribute("key");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keylist;
    }

    public String[] getThemeKeyList(){
        String[] keylist=new String[0];
        try {
            NodeList nodelist=xmlHandler.getNodeListXPath("//topiclist/theme");
            keylist=new String[nodelist.getLength()];
            for(int i=0;i<nodelist.getLength();i++){
                Node inode=nodelist.item(i);
                if(inode.getNodeType()==Node.ELEMENT_NODE){
                    Element ielement=(Element)inode;
                    keylist[i]=ielement.getAttribute("key");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keylist;
    }

    public String getThemeName(String key){
        try {
            NodeList nodelist=xmlHandler.getNodeListXPath("//topiclist/theme");
            Node inode=nodelist.item(0);
            if(inode.getNodeType()==Node.ELEMENT_NODE){
                Element topicelement=(Element)inode;
                return topicelement.getAttribute("name");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Methode, mit der man ein Element hinzufügen kann.
     * @param themekey Key des Themas
     * @param title Titel des Elements
     * @param pathmap Map mit allen Datei-Pfaden
     */
    public void addElement(String themekey, String title, Map<String,String> pathmap){
        Element element=xmlHandler.createElement("element");
        element.setAttribute("key",String.valueOf(System.currentTimeMillis()));
        element.setAttribute(ATTRIBUTE_TITLE,title);
        for (Map.Entry<String, String> entry : pathmap.entrySet()) {
            Element pathelement=xmlHandler.createElement("file");
            pathelement.setAttribute("type",entry.getValue()); //Map-Value = Typ
            pathelement.setTextContent(entry.getKey()); //Map-Key = Pfad
            element.appendChild(pathelement);
        }
        try {
            NodeList nodelist=xmlHandler.getNodeListXPath("//topiclist/theme[@key=\""+themekey+"\"]");
            Node inode=nodelist.item(0);
            if(inode.getNodeType()==Node.ELEMENT_NODE){
                Element topicelement=(Element)inode;
                topicelement.appendChild(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Methode, mit der man ein Thema hinzufügen kann
     * @param theme Name des neuen Themas
     */
    public void addTheme(String theme){
        Element element=xmlHandler.createElement("theme");
        element.setAttribute("key", String.valueOf(System.nanoTime()));
        element.setAttribute("name",theme);
        xmlHandler.getRoot().appendChild(element);
    }

    /**
     * Methode, mit der man ein bestimmtes Element löschen kann.
     * @param key Eindeutiger Schlüssel des Elements
     */
    public void deleteElement(String key){
        try {
            NodeList nodelist=xmlHandler.getNodeListXPath("//topiclist/theme/element[@key=\""+key+"\"]");
            Node inode=nodelist.item(0);
            if(inode.getNodeType()==Node.ELEMENT_NODE){
                Element element=(Element)inode;
                element.getParentNode().removeChild(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode, mit der man ein bestimmtes Thema löschen kann.
     * @param key Eindeutiger Schlüssel des Themas
     */
    public void deleteTheme(String key){
        try {
            NodeList nodelist=xmlHandler.getNodeListXPath("//topiclist/theme[@key=\""+key+"\"]");
            Element element=(Element)nodelist.item(0);
            element.getParentNode().removeChild(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode, die die Daten in einer .xml Datei speichert
     */
    public void safeElementData(){
        try {
            xmlHandler.saveDocToXml(targetfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //ALLES ZUM TESTEN
    public static void main(String[] args){
        ElementDataHandler test=ELEMENT_DATA_HANDLER;
        test.test();
        try {
            test.xmlHandler.saveDocToXml(targetfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void test(){
        addTheme("Thema1");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addTheme("Thema2");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addTheme("Thema3");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(String i:getThemeKeyList()){
            for(int temp=0;temp<3;temp++){
                String title="Test"+temp;
                Map<String,String> map=new HashMap<>();
                map.put("src/"+getThemeName(i)+"/"+title+"/description.txt",FILE_TYPE_DESCRIPTION);
                map.put("src/"+getThemeName(i)+"/"+title+"/movie.txt",FILE_TYPE_MOVIE);
                map.put("src/"+getThemeName(i)+"/"+title+"/picture.txt",FILE_TYPE_PICTURE);
                addElement(i,title,map);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}