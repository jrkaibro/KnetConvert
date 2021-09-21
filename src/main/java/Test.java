import com.lowagie.text.DocumentException;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws DocumentException, IOException {

        KnetConvert convert = new KnetConvert();
        convert.Stringtopdf("<!DOCTYPE><title>Knetconvert</title><h1>OlaMundo!</h1>","site.pdf");
        convert.Urltopdf("index.html","web.pdf");
    }
}
