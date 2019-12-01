/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.utils;

import java.io.InputStream;
import java.util.Optional;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author thanh
 */
public class StaxUtils {

    public static XMLStreamReader parseFileToStAXCursor(InputStream is)
            throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(is);

        return reader;
    }

    public static Optional<String> getTextContentStAXCursor(String elementName, XMLStreamReader reader)
            throws XMLStreamException {
        if (reader == null) {
            return Optional.empty();
        }
        if (elementName == null) {
            return Optional.empty();
        }
        if (elementName.trim().isEmpty()) {
            return Optional.empty();
        }

        while (reader.hasNext()) {
            int currentCursor = reader.getEventType();
            if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName();
                if (tagName.equals(elementName)) {
                    reader.next();//value or end tag
                    if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
                        String result = reader.getText();
                        reader.next();//end value
                        return Optional.of(result);
                    }
                    else if (reader.getEventType() == XMLStreamConstants.END_ELEMENT) {
                        return Optional.empty();
                    }

                }//end if tagName is elementName
            }//end if cursor is startElement
            reader.next();
        }//end if reader

        return Optional.empty();
    }
}
