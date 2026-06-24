package org.example;

import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;

public class EquationElement implements ContentElement<CTOMath> {

    // following table's footsteps
    private CTOMath eqobj;

    public EquationElement(CTOMath mathobj) {
        eqobj = mathobj;
    }

    @Override
    public CTOMath getElement() {
        return eqobj;
    }

    @Override
    public void setElement(CTOMath mathobj) {
        eqobj = mathobj;
    }

    @Override
    public String getPrintable() {
        /*
         nvm just returns MathML
         returns the latex form of the equation if isLatex is set to true
         returns MathML otherwise
        */
//        String result = "Something went wrong during conversion; check code";
//        result = (isLaTeX) ? this.toLaTeX() : this.toMathML();
//        return result;
        // forgot to comply to interface
        try {
            return this.toMathML();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private String toMathML() throws Exception {
        // credit: https://stackoverflow.com/questions/44748712/reading-equations-formula-from-word-docx-to-html-and-save-database-using-jav

        File stylesheet = new File("OMML2MML.XSL");
        TransformerFactory tFactory = TransformerFactory.newInstance();
        StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = tFactory.newTransformer(stylesource); // "unhandled exception"
        // ij recommends adding throws exception as above
        // uses general Exception since transform below hv a different exception

        // define source for transformer
        DOMSource source = new DOMSource(eqobj.getDomNode());

        StringWriter stringwriter = new StringWriter();
        StreamResult result = new StreamResult(stringwriter);
        transformer.setOutputProperty("omit-xml-declaration", "yes");
        transformer.transform(source, result);

        String mathML = stringwriter.toString();
        stringwriter.close();

        return mathML;
    }


    private String toLaTeX() {
        // Stage 1: OMML -> MathML

        // both toMathML and this gives this error:
        // java: unreported exception java.lang.Exception; must be caught or declared to be thrown
        // so added try catch to handle runtime exception without changing method signature
        try {
            String mathML = this.toMathML();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        // Stage 2: MathML -> LaTeX
        // hmm actually we may not need latex since we're building a web app
        // incomplete
        return "";
    }
}
