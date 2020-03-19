import java.io.*;
import java.net.URL;
import java.util.regex.Pattern;

public class JarFileReader {
    public static void Test(String jarName,String classname) throws IOException {
        URL url1 = new URL("jar:file:"+jarName.replaceAll("\\","/")+"!"+classname);
        URL url2 = new URL("jar:file:"+jarName.replaceAll("\\","/")+"!"+classname);
// 标准输入流
        try {
            InputStream is1 = url1.openStream();
            InputStream is2 = url2.openStream();
            if (processEvilPackage(is1)&&processReadObject(is2)) {
                System.out.println(classname + "       this class maybe have XXE!!!!!!!!!");
            }
        }catch (Exception e)
        {
            System.out.println(classname + "  java.io.FileNotFoundException");
        }
    }

    private static boolean processEvilPackage(InputStream input) throws IOException {
        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        //遍历查找库
        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
              if (SearchEvilPackage(line))
              {
                    return true;
              }
        }
        reader.close();
        return false;
    }
    private static boolean processReadObject(InputStream input) throws IOException {
        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        //遍历查找库
        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
            if (SearchReadObject(line))
            {
                return true;
            }
        }
        reader.close();
        return false;
    }
    private static boolean SearchEvilPackage(String line)
    {
        //表达式
        String XXE_Regex = ".*javax.xml.parsers.DocumentBuilderFactory.*|.*javax.xml.parsers.SAXParser.*|.*javax.xml.transform.TransformerFactory.*|.*javax.xml.validation.Validator.*|.*javax.xml.validation.SchemaFactory.*|.*javax.xml.transform.sax.SAXValidator.*|.*javax.xml.transform.sax.SAXSource.*|.*org.xml.sax.XMLReader.*|.*org.xml.sax.helpers.XMLReaderFactory.*|.*org.dom4j.io.SAXReader.*|.*org.jdom.input.SAXBuilder.*|.*org.jdom2.input.SAXBuilder.*|.*javax.xml.bind.Unmarshaller.*|.*javax.xml.xpath.XpathExpression.*|.*javax.xml.stream.XMLStreamReader.*|.*org.apache.commons.digester3.Digester.*|.*javax.xml.transform.stream.StreamSource.*|.*javax.xml.parsers.SAXParserFactory.*|.*javax.xml.ws.EndpointReference.*";
        boolean b = Pattern.matches(XXE_Regex, line);
        if(b){
            return true;
        }else
        {
            return false;
        }
    }
    private static boolean SearchReadObject(String line)
    {
        //表达式
        String Ser_Regex = ".*Externalizable.*|.*Serializable.*|.*readObject.*|.*readExternal.*";
        boolean b = Pattern.matches(Ser_Regex, line);
        if(b){
            return true;
        }else
        {
            return false;
        }
    }
}