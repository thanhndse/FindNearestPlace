package thanhnd.crawler;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import thanhnd.checker.XmlSyntaxChecker;
import thanhnd.utils.HttpUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thanh
 */
public class MainTest {

    public static void main(String[] args) {
        try {
            //1. Run crawler
            DOMResult rs = CrawlUltimate.crawl("src/java/thanhnd/test/demoCrawl.xml", "src/java/thanhnd/test/demoCrawl.xsl");
            //2. Init transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            StreamResult sr = new StreamResult(new FileOutputStream("src/java/thanhnd/test/output-crawl.xml"));
            //3. Transform to XML file
            transformer.transform(new DOMSource(rs.getNode()), sr);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
