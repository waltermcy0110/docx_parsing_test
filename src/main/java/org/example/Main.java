package org.example;
// https://www.baeldung.com/java-microsoft-word-with-apache-poi
// https://mkyong.com/java/java-read-and-write-microsoft-word-with-apache-poi/

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    // static WordDocument wordDocument;
    XWPFDocument doc;


    public static void main(String[] args) throws IOException {

        String filename = "dummy_mcq.docx";
                // "C:\\Users\\walte\\Desktop\\Programming\\Projects\\Intellij_projects\\docx_parsing_test\\dummy_mcq.docx";

        System.out.printf("Parsing test. Sample MCQs with different formats (not exhaustive) included.");

        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(filename)))) {

            List<XWPFParagraph> list = doc.getParagraphs();
            for (XWPFParagraph paragraph : list) {
                System.out.print("Paragraph: ");
                System.out.println(paragraph.getText());
            }

        }
    }
}