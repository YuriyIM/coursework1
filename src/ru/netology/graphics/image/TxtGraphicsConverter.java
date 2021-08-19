package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TxtGraphicsConverter implements TextGraphicsConverter {

    private double maxRatio = 1000000;
    private int maxHeight = 1000000;
    private int maxWidth = 1000000;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));

        double ratio = (img.getWidth() > img.getHeight() ? img.getWidth() / img.getHeight() :
                img.getHeight() / img.getWidth());

        if (ratio > maxRatio) {
            new BadImageSizeException(ratio, maxRatio);
        }

        int newWidth = img.getWidth();
        int newHeight = img.getHeight();

        if (img.getWidth() > maxWidth || img.getHeight() > maxHeight) {

            double scaledRatio = (img.getHeight() / maxHeight > img.getWidth() / maxWidth ?
                    img.getHeight() / maxHeight : img.getWidth() / maxWidth);

            newHeight = (int) (img.getHeight() / scaledRatio);
            newWidth = (int) (img.getWidth() / scaledRatio);
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();

        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();

        char[][] charImages = new char[newWidth][newHeight];

        String stringImages = null;

        TextColorSchema schema = new TxtColorSchema();

        for (int w = 0; w < newWidth; w++) {
            for (int h = 0; h < newHeight; h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                charImages[w][h] = c;
            }
        }

        for (int w = 0; w < newWidth; w++) {
            for (int h = 0; h < newHeight; h++) {
                stringImages = stringImages + charImages[w][h] + charImages[w][h];
            }
            System.out.println();
        }

        return stringImages;
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }
}
