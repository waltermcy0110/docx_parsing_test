package org.example;

public class EquationElement implements ContentElement<String> {

    private String latex;

    public EquationElement(String str) {
        latex = transformFromRaw(str);
    }

    @Override
    public String getElement() {
        return latex;
    }

    @Override
    public void setElement(String str) {
        latex = transformFromRaw(str);
    }

    public String transformFromRaw(String raw_str) {
        // raw_str is xmlobj.xmltext()
        String transformed_str = raw_str;

        // cast raw to the equation format? idk

        return transformed_str;
    }
}
