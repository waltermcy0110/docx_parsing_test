package org.example;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import javax.lang.model.util.Elements;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    // this should act like a facade and only expose the init and parse methods
    // not a singleton? idk if concurrent users means we need multiple instances;
    // for each user, we can discard the used parser and instantiate a new one i guess, prob no overhead

    private List<ContentElement> elements;
    String file_path;

    public Parser(String path) {
        file_path = path;
        elements = new ArrayList<>();
    }

    public List<ContentElement> getElements() {
        // if element is empty, try parsing once and return regardless of result
        if (elements.isEmpty()) {
            parseDocument();
        }

        return elements;
    }

    public void parseDocument() {

        try (XWPFDocument document = new XWPFDocument(
                Files.newInputStream(Paths.get(file_path)))) {

            for (IBodyElement element: document.getBodyElements()) {
                // paragraphs: text, equations, etc
                if (element instanceof XWPFParagraph) {

                    XWPFParagraph paragraph = (XWPFParagraph) element; // for readability

                    // skip empty paragraphs
                    // if (paragraph.runsIsEmpty()) continue;
                    // commented in case it skips images or sth

                    // now iterate over each CTP(paragraph) object using xmlcursor
                    // to preserve inline equation order
                    XmlCursor cursor = paragraph.getCTP().newCursor();
                    cursor.toFirstChild();

                    do {
                        XmlObject xmlobj = cursor.getObject();
                        // System.out.println(xmlobj.xmlText());

                        XmlCursor.TokenType tokenType = cursor.currentTokenType();

                        // math (inline)
                        if (xmlobj instanceof CTOMath) {
                            System.out.println("Equation: " + xmlobj.xmlText());
//                            EquationElement eq = new EquationElement(xmlobj.xmlText());
                            EquationElement eq = new EquationElement((CTOMath) xmlobj);
                            System.out.println("MathML: " + eq.getPrintable());
                            // obsolete: System.out.println("LaTeX: " + eq.getPrintable(true));
                            elements.add(eq);

                        }

                        // run (might have to do this conversion to preserve ordering if i use xmlobj with eqs)
                        // credit: "apache poi can i cast ctr to a run"
                        else if (xmlobj instanceof CTR) {
                            XWPFRun run = new XWPFRun((CTR)xmlobj, paragraph);

                            // Look for embedded pictures in this specific run
                            if (!run.getEmbeddedPictures().isEmpty()) {
                                for (XWPFPicture picture : run.getEmbeddedPictures()) {

                                    // Access metadata and binary source data
                                    String description = picture.getDescription();
                                    byte[] rawData = picture.getPictureData().getData();

                                    System.out.println("Found image inline! Description: " + description);
                                    System.out.println("Image byte size: " + rawData.length);

                                    ImageElement img = new ImageElement(picture);
                                    elements.add(img);
                                }
                            } else {
                                // Print ordinary text if no image is in the run
                                // System.out.print(run.getText(0));
                                TextElement text = new TextElement(run.getText(0));
                                elements.add(text);
                            }

                        }

                    } while (cursor.toNextSibling());

                    // commit checkpoint: got text runs working at least
                    // planning to trim off option numbers, since the convenient way actually retains them which is
                    // different from the newline way
                    // just check beginning of string against 3 enums for a), 1. i: i guess

                    // CTOMathPara might be useful for inline equations

                    // }


                    // missing newline after paragraphs
                    System.out.println("");

                }


                // tables
                else if (element instanceof XWPFTable) {
                    // System.out.println("Table idk: " + ((XWPFTable) element).getText());


                    TableElement table = new TableElement((XWPFTable) element);

                    elements.add(table);
                }

                else {
                    // System.out.println("Other type: " + element.getElementType());
                    TextElement other = new TextElement("Other");
                    elements.add(other);
                }
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
