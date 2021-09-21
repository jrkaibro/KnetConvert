import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.Base64;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class KnetConvert
{
    public KnetConvert() {

    }

    public static void Stringtopdf(String input, String out)  {

        try {

            InputStream input2 = new ByteArrayInputStream(input.getBytes("ISO-8859-1"));
            OutputStream out2 = new FileOutputStream(out);

            Tidy tidy = new Tidy();
            tidy.setXHTML(true);
            tidy.setQuiet(true);

            Document doc = tidy.parseDOM(input2, null);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(doc, null);
            renderer.layout();
            renderer.createPDF(out2);
            out2.close();

    } catch (DocumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void Urltopdf(String url, String target)  {

        try {

            Reader htmlreader;

            if ((url.startsWith("http://")) || (url.startsWith("https://")))
            {
                URL site = new URL(url);
                htmlreader = new InputStreamReader(site.openStream());
            }
            else
            {
                htmlreader = new InputStreamReader(new FileInputStream(url));
            }
            com.lowagie.text.Document pdfDocument = new com.lowagie.text.Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(pdfDocument, baos);
            pdfDocument.open();
            StyleSheet styles = new StyleSheet();
            styles.loadTagStyle("body", "font", "Arial");

            ArrayList arrayElementList = HTMLWorker.parseToList(htmlreader, styles);


            for (int i = 0; i < arrayElementList.size(); i++)
            {
                Element e = (Element)arrayElementList.get(i);
                pdfDocument.add(e);
            }
            pdfDocument.close();
            byte[] bs = baos.toByteArray();

            String pdfBase64 = Base64.encodeBytes(bs);
            File pdfFile = new File(target);
            FileOutputStream out = new FileOutputStream(pdfFile);
            out.write(bs);
            out.close();

        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
