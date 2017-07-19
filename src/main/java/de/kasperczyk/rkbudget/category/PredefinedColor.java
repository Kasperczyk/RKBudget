package de.kasperczyk.rkbudget.category;

import java.awt.*;

public enum PredefinedColor {

    BLACK(Color.BLACK, "black"),
    GRAY(Color.GRAY, "gray"),
    RED(Color.RED, "red"),
    ORANGE(Color.ORANGE, "orange"),
    YELLOW(Color.YELLOW, "yellow"),
    GREEN(Color.GREEN, "green"),
    BLUE(Color.BLUE, "blue"),
    WHITE(Color.WHITE, "white");

    private static final String MESSAGE_PREFIX = "category_predefinedColor_";

    private Color color;
    private String key;

    PredefinedColor(Color color, String key) {
        this.color = color;
        this.key = key;
    }

    public Color getColor() {
        return color;
    }

    public String getMessageKey() {
        return MESSAGE_PREFIX + key;
    }

    public String getKey() {
        return key;
    }
}
