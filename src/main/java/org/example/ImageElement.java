package org.example;

public class ImageElement implements ContentElement<byte[]> {

    private byte[] imageData;

    public ImageElement(byte[] raw_data) {
        imageData = transformFromRaw(raw_data);
    }

    @Override
    public byte[] getElement() {
        return imageData;
    }

    @Override
    public void setElement(byte[] raw_data) {
        imageData = transformFromRaw(raw_data);
    }

    public byte[] transformFromRaw(byte[] raw_data) {
        // nothing for now ig, i dont even know if imageData = raw_data works at all
        return raw_data;
    }
}
