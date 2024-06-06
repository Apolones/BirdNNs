package com.birds.nn.utils;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;

public class Formatter {
    public static TextFormatter<Double> createDoubleTextFormatter(double min, double max) {
        return new TextFormatter<>(new DoubleStringConverter(), 0.0, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*(\\.\\d*)?")) {
                try {
                    double newValue = Double.parseDouble(newText);
                    if (newValue >= min && newValue <= max) {
                        return change;
                    }
                } catch (NumberFormatException e) {
                    // Ignore, as the value is not a double
                }
            }
            return null;
        });
    }
}
