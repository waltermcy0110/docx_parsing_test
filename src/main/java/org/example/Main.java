package org.example;
// https://www.baeldung.com/java-microsoft-word-with-apache-poi
// https://mkyong.com/java/java-read-and-write-microsoft-word-with-apache-poi/

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

import org.example.EquationParser;
// import org.openxmlformats.schemas.officeDocument.x2006.math.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
// above import is different and results in text runs not getting detected by instanceof CTR
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    XWPFDocument doc;


    public static void main(String[] args) throws IOException {

        String filename = "dummy_mcq.docx";
        System.out.printf("Parsing test. Sample MCQs with different formats (not exhaustive) included.");

        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(filename)))) {

            List<XWPFParagraph> list = doc.getParagraphs();
            for (XWPFParagraph paragraph : list) {

                // skip empty paragraphs
                if (paragraph.getText().trim().isEmpty()) continue;


                System.out.println("\nParagraph: " + paragraph.getText());

                // works as expected
//                System.out.println("Underlying XML:");
//                System.out.println(paragraph.getCTP().xmlText());

                // parsing of word equations:
                // gpt and https://stackoverflow.com/questions/44748712/reading-equations-formula-from-word-docx-to-html-and-save-database-using-jav
                // https://developer.adobe.com/experience-manager/reference-materials/cloud-service/javadoc/org/openxmlformats/schemas/wordprocessingml/x2006/main/CTRow.html



                // now iterate over each CTP object
                XmlCursor cursor = paragraph.getCTP().newCursor();
                // System.out.println("Type before toFirstChild: " + cursor.getName());
                cursor.toFirstChild(); // goes down from paragraph to run? nope, to p prop for all occurrences
                // System.out.println("Type after: " + cursor.getName());
                do {
                    XmlObject xmlobj = cursor.getObject();
                    // System.out.println(xmlobj.xmlText());

                    XmlCursor.TokenType tokenType = cursor.currentTokenType();
                    // System.out.println("Type: " + cursor.getName());
                    // System.out.println("is run? " + (cursor.getObject() instanceof CTR));
                    // runs showed up as non-runs, made me look into import statements

                    // determine object type
                    // text run
                    if (xmlobj instanceof CTR) {
                        for (CTText ctText: ((CTR) xmlobj).getTList()) {
                            System.out.println("Text run: " + ctText.getStringValue());
                        }
                    }
                    // math (idk if inline or block)
                    else if (xmlobj instanceof CTOMath) {
                        System.out.println("Equation: " + xmlobj.xmlText());
                    }

                } while (cursor.toNextSibling());

                // commit checkpoint: got text runs working at least
                // planning to trim off option numbers, since the convenient way actually retains them which is
                // different from the newline way
                // just check beginning of string against 3 enums for a), 1. i: i guess

            }

        }

    }
}