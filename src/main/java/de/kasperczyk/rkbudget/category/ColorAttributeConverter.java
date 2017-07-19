package de.kasperczyk.rkbudget.category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.awt.*;

@Converter
public class ColorAttributeConverter implements AttributeConverter<Color, String> {

    private static final String SEPERATOR = "|";

    @Override
    public String convertToDatabaseColumn(Color color) {
        return "" + color.getRed() + SEPERATOR +
                color.getGreen() + SEPERATOR +
                color.getBlue() + SEPERATOR +
                color.getAlpha();
    }

    @Override
    public Color convertToEntityAttribute(String colorString) {
        String[] rgba = colorString.split("\\|");
        return new Color(Integer.parseInt(rgba[0]),
                Integer.parseInt(rgba[1]),
                Integer.parseInt(rgba[2]),
                Integer.parseInt(rgba[3]));
    }
}
