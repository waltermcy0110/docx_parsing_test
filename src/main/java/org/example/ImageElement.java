package org.example;

import org.apache.poi.xwpf.usermodel.XWPFPicture;

public class ImageElement implements ContentElement<XWPFPicture> {

    private XWPFPicture image;

    public ImageElement(XWPFPicture img) {
        image = transformFromRaw(img);
    }

    @Override
    public XWPFPicture getElement() {
        return image;
    }

    @Override
    public void setElement(XWPFPicture img) {
        image = transformFromRaw(img);
    }

    @Override
    public String getPrintable() {
        // just print out info about the image, like the eg code in main
        // Access metadata and binary source data
        String description = image.getDescription();
        byte[] rawData = image.getPictureData().getData();
        description += "\nImage byte size: " + rawData.length;
        return description;
    }

    public XWPFPicture transformFromRaw(XWPFPicture img) {
        // nothing for now ig, i dont even know if image = raw_data works at all
        return img;
    }
}
