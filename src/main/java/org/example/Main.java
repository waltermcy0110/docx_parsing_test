package org.example;
// https://www.baeldung.com/java-microsoft-word-with-apache-poi
// https://mkyong.com/java/java-read-and-write-microsoft-word-with-apache-poi/

import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// import org.openxmlformats.schemas.officeDocument.x2006.math.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
// above import is different and results in text runs not getting detected by instanceof CTR


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    // Parsing helpers


    public static void main(String[] args) throws IOException {

        String filename = "dummy_mcq.docx";
        System.out.printf("Parsing test. Sample MCQs with different formats (not exhaustive) included.");
//
//        try (XWPFDocument document = new XWPFDocument(
//                Files.newInputStream(Paths.get(filename)))) {
//
//            for (IBodyElement element: document.getBodyElements()) {
//                // paragraphs: text, equations, etc
//                if (element instanceof XWPFParagraph) {
//
//                    XWPFParagraph paragraph = (XWPFParagraph) element; // for readability
//
//                    // skip empty paragraphs
//                    // if (paragraph.runsIsEmpty()) continue;
//                    // commented in case it skips images or sth
//
//                    // now iterate over each CTP object
//                    XmlCursor cursor = paragraph.getCTP().newCursor();
//                    // System.out.println("Type before toFirstChild: " + cursor.getName());
//                    cursor.toFirstChild(); // goes down from paragraph to run? nope, to p prop for all occurrences
//                    // System.out.println("Type after: " + cursor.getName());
//                    do {
//                        XmlObject xmlobj = cursor.getObject();
//                        // System.out.println(xmlobj.xmlText());
//
//                        XmlCursor.TokenType tokenType = cursor.currentTokenType();
//
//                        // System.out.println("Type: " + cursor.getName());
//                        // System.out.println("is run? " + (cursor.getObject() instanceof CTR));
//                        // runs showed up as non-runs, made me look into import statements
//
//                        // determine object type
//                        // Runs (text, images)
////                        if (xmlobj instanceof CTR) {
////                            handleCTR(xmlobj);
////                        }
//
//
//                        // math (inline)
//                        if (xmlobj instanceof CTOMath) {
//                            System.out.println("Equation: " + xmlobj.xmlText());
//                        }
//
//                        // run (might have to do this conversion to preserve ordering if i use xmlobj with eqs)
//                        // credit: "apache poi can i cast ctr to a run"
//                        else if (xmlobj instanceof CTR) {
//                            XWPFRun run = new XWPFRun((CTR)xmlobj, paragraph);
//
//                            // also google ai code, looks fine and non-destructive(?)
//                            // Look for embedded pictures in this specific run
//                            if (!run.getEmbeddedPictures().isEmpty()) {
//                                for (XWPFPicture picture : run.getEmbeddedPictures()) {
//
//                                    // Access metadata and binary source data
//                                    String description = picture.getDescription();
//                                    byte[] rawData = picture.getPictureData().getData();
//
//                                    System.out.println("Found image inline! Description: " + description);
//                                    System.out.println("Image byte size: " + rawData.length);
//                                }
//                            } else {
//                                // Print ordinary text if no image is in the run
//                                System.out.print(run.getText(0));
//                            }
//
//                        }
//
//                    } while (cursor.toNextSibling());
//
//                    // commit checkpoint: got text runs working at least
//                    // planning to trim off option numbers, since the convenient way actually retains them which is
//                    // different from the newline way
//                    // just check beginning of string against 3 enums for a), 1. i: i guess
//
//                    // CTOMathPara might be useful for inline equations
//
//                    // }
//
//
//                    // missing newline after paragraphs
//                    System.out.println("");
//
//                }
//
//
//                // tables
//                else if (element instanceof XWPFTable) {
//                    System.out.println("Table idk: " + ((XWPFTable) element).getText());
//                }
//
//                else {
//                    System.out.println("Other type: " + element.getElementType());
//                }
//            }
//
//
//
//        }

        Parser parser = new Parser(filename);
        // parser.parseDocument();
        List<ContentElement> parsed_elements;
        parsed_elements = parser.getElements();
        System.out.println("Element count: " + parsed_elements.size());

        for (ContentElement element: parsed_elements) {
            // print element
            System.out.println(element.getPrintable());
        }

    }
}