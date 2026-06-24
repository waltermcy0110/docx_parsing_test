package org.example;

import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.util.List;

public class TableElement implements ContentElement<XWPFTable> {

    // conceptually this should be an intermediate repr of a table
    // maybe i rly should store xwpf and only convert to nested list if needed
    // but does that violate any design principle??
    // tbf i dont even know what the final format should be so lets just do xwpf for now

    // private List<List<Float>> table;
    private XWPFTable table;
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

    @Override
    public String getPrintable() {
        int rowNum = table.getNumberOfRows();
        int colNum = table.getRow(0).getTableCells().size();
        String result = rowNum + "x" + colNum + " table: " + table.getText();
        return result;
    }

    public XWPFTable transformFromRaw(XWPFTable raw_table) {
        // add any necessary transformations here
        return raw_table;
    }
}
