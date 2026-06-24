package org.example;

public interface ContentElement<T> {
    // content in mc question/options should be retrievable, editable and printable
    // add methods corresponding to those functionalities (and more) here

    public T getElement();
    public void setElement(T new_content);
    public String getPrintable();
}
