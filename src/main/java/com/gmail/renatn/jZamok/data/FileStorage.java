package com.gmail.renatn.jZamok.data;

import com.gmail.renatn.jZamok.model.PasswordEntry;
import com.gmail.renatn.jZamok.model.PasswordGroup;
import com.gmail.renatn.jZamok.model.ZamokDocument;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: renat
 * Date: 17.01.2006
 * Time: 22:24:45
 */

public class FileStorage {

    private PasswordGroup root;

    protected void load(InputStream in) throws IOException {

        ZamokHandler handler = new ZamokHandler();

        SAXParserFactory factory = SAXParserFactory.newInstance();    //http://www.vogella.de/articles/JavaXML/article.html
        factory.setValidating(false);     // We don't want validation
        factory.setNamespaceAware(false); // No namespaces please

        try {

            SAXParser parser = factory.newSAXParser();
            parser.parse(in, handler);

            root = handler.getRoot();

        } catch (SAXException e) {
            Logger.getLogger(FileStorage.class.getName()).log(Level.SEVERE, null, e);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void loadFromFile(File file) throws IOException {
        load(new FileInputStream(file));
    }

    protected void save(ZamokDocument dataModel, OutputStream out) throws Exception {

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(out, "UTF-8");
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);
        eventWriter.add(end);

        printDataModel(dataModel.getRoot(), eventWriter, eventFactory);

        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();

    }

    public void saveToFile(ZamokDocument model, File file) throws Exception {
        save(model, new FileOutputStream(file));
    }

    public PasswordGroup getRoot() {
        return root;
    }

    private void printElement(String qName, String value, XMLEventWriter eventWriter, XMLEventFactory eventFactory) throws XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");
        StartElement startElement = eventFactory.createStartElement("", "", qName);
        eventWriter.add(startElement);

        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        eventWriter.add(eventFactory.createEndElement("", "", qName));
        eventWriter.add(end);


    }

    private void printEntryXML(PasswordEntry psw, XMLEventWriter eventWriter, XMLEventFactory eventFactory) throws XMLStreamException {
        XMLEvent end = eventFactory.createDTD("\n");

        StartElement entryElement = eventFactory.createStartElement("", "", ZamokHandler.XML_ENTRY_KEY);
        eventWriter.add(entryElement);
        eventWriter.add(end);

        printElement("title", psw.getTitle(), eventWriter, eventFactory);
        printElement("login", psw.getLogin(), eventWriter, eventFactory);
        printElement("password", psw.getPassword(false), eventWriter, eventFactory);
        printElement("url", psw.getURL(), eventWriter, eventFactory);
        printElement("email", psw.getEmail(), eventWriter, eventFactory);
        printElement("notes", psw.getNotes(), eventWriter, eventFactory);
        printElement("updated", Long.toString(psw.getLastUpdated().getTime()), eventWriter, eventFactory);

        eventWriter.add(eventFactory.createEndElement("", "", ZamokHandler.XML_ENTRY_KEY));
        eventWriter.add(end);

    }

    public void printDataModel(PasswordGroup root, XMLEventWriter eventWriter, XMLEventFactory eventFactory) throws XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");
        StartElement rootStartElement = eventFactory.createStartElement("", "", ZamokHandler.XML_GROUP_KEY);
        eventWriter.add(rootStartElement);
        eventWriter.add(eventFactory.createAttribute("name", root.getName()));
        eventWriter.add(end);

        for (PasswordEntry entry : root.getListEntry()) {
            printEntryXML(entry, eventWriter, eventFactory);
        }

        for (PasswordGroup group : root.getListGroup()) {
            printDataModel(group, eventWriter, eventFactory);
        }

        eventWriter.add(eventFactory.createEndElement("", "", ZamokHandler.XML_GROUP_KEY));
        eventWriter.add(end);


    }

}
