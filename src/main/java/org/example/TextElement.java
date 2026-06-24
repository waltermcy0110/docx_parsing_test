package org.example;

public class TextElement implements ContentElement<String> {

    private String text;

    public TextElement(String str) {
        text = str;
    }

    @Override
    public String getElement() {
        return text;
    }

    @Override
    public void setElement(String str) {
        text = str.trim();
    }

    @Override
    public String getPrintable() {
        return "Text: " + text;
    }
}
