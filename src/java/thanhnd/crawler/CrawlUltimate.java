package thanhnd.crawler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thanh
 */
public class CrawlUltimate {

    public static DOMResult crawl(String configPath, String xslPath) throws FileNotFoundException, TransformerConfigurationException, TransformerException {
        //1. Init file
        StreamSource xslCate = new StreamSource(xslPath);
        InputStream is = new FileInputStream(configPath);
        //2. Init transfomer api
        TransformerFactory factory = TransformerFactory.newInstance();
        DOMResult domRs = new DOMResult();
        UltimateURIResolver resolver = new UltimateURIResolver();
        //3. Apply uri resolver
        factory.setURIResolver(resolver);
        Transformer transformer = factory.newTransformer(xslCate);
        //4. Transform xml config by input xsl
        transformer.transform(new StreamSource(is), domRs);
        return domRs;
    }
}
