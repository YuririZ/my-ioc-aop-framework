package indi.yuri.xf.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author yurizhang
 * @date 2021/4/27 15:19
 */
public class XmlReaderUtils {
    private static final String XML_FILE_NAME = "beans.xml";

    private static Document BEANS_DOCUMENT;

    static {
        InputStream inputStream = XmlReaderUtils.class.getClassLoader().getResourceAsStream(XML_FILE_NAME);
        if (inputStream == null) {
            System.out.println(XML_FILE_NAME + " not found.");
        }
        SAXReader saxReader = new SAXReader();
        try {
            BEANS_DOCUMENT = saxReader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回所有的bean标签元素
     * @return
     */
    public static List<Element> getBeanElements() {
        Element rootElement = BEANS_DOCUMENT.getRootElement();
        return rootElement.selectNodes("//bean");
    }

    /**
     * 返回所有的property标签元素
     * @return
     */
    public static List<Element> getPropertyElements() {
        Element rootElement = BEANS_DOCUMENT.getRootElement();
        return rootElement.selectNodes("//property");
    }
}
