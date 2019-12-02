
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
public class Test {

    public static void main(String[] args) {
//        String content = HttpUtils.getHttpContent("https://www.vietnammm.com/dat-mon-giao-tan-noi-quan-9");
//        XmlSyntaxChecker checker = new XmlSyntaxChecker();
//        content = checker.refineHtml(content);
//        content = checker.check(content);
//        System.out.println("a");
            String str = "Món pháp, món ý và món việt";
            String[] arr = str.split(",|(và)");
            for (String a: arr){
                System.out.println(a.trim());
            }
    }
}
