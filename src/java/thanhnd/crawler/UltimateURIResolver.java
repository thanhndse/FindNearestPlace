package thanhnd.crawler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
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
public class UltimateURIResolver implements URIResolver {

    private int count = 0;

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (href != null && (href.indexOf("https://pasgo.vn") == 0|| href.indexOf("https://www.vietnammm.com") == 0)) {
            String content = HttpUtils.getHttpContent(href);
            XmlSyntaxChecker checker = new XmlSyntaxChecker();
            content = checker.refineHtml(content);
            content = checker.check(content);
            InputStream is = new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println("Count: " + ++count + " Href: " + href);
            return new StreamSource(is);
        }
        return null;

    }

}
