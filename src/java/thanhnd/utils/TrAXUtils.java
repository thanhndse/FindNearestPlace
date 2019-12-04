/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import thanhnd.helper.crawler.UltimateURIResolver;

/**
 *
 * @author thanh
 */
public class TrAXUtils {
    public static ByteArrayOutputStream transform(String xmlPath, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TransformerFactory tf = TransformerFactory.newInstance();
        UltimateURIResolver resolver = new UltimateURIResolver();
        tf.setURIResolver(resolver);

        StreamSource source = new StreamSource(new FileInputStream(xmlPath));
        StreamResult result = new StreamResult(outputStream);

        Transformer trans = tf.newTransformer(new StreamSource(new File(xslPath)));
        trans.transform(source, result);

        return outputStream;
    }
}
