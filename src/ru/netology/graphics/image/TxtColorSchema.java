package ru.netology.graphics.image;

public class TxtColorSchema implements TextColorSchema{
    @Override
    public char convert(int color) {
        if (color < 32) return '#';
        if (color > 31 && color < 64) return '$';
        if (color > 63 && color < 96) return '@';
        if (color > 95 && color < 128) return '%';
        if (color > 127 && color < 160) return '*';
        if (color > 159 && color < 192) return '+';
        if (color > 191 && color < 224) return '-';
        return '\'';
    }
}
