package test.word;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;

public class WordTest {
    public static void main(String[] args) throws Exception {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText("WordTest标题");
        run.setFontFamily("黑体");
        run.setFontSize(29);
        run.setBold(true);

        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("WordTest正文");
        run.setText("宋体");
        run.setFontSize(15);

        ex(document);
    }

    public static void ex(XWPFDocument document) throws Exception {
        FileOutputStream fos = new FileOutputStream("D:/TDDOWN/PjoTest/WordTest.docx");
        document.write(fos);
    }
}