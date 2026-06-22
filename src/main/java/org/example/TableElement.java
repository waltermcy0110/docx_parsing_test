package org.example;

import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.util.List;

public class TableElement implements ContentElement<XWPFTable> {

    private List<List<Float>> table;
    // or just use XWPFTable?

    public TableElement(XWPFTable raw_table) {
        table = transformFromRaw(raw_table);
    }

    @Override
    public XWPFTable getElement() {
        return table;
    }

    @Override
    public void setElement(XWPFTable raw_table) {
        table = transformFromRaw(raw_table);
    }

    public List<List<Float>> transformFromRaw(XWPFTable raw_table) {
        // sth
        return null;
    }
}
