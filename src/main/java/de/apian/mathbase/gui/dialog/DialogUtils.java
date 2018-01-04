/*
 * Copyright (c) 2017 MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.gui.dialog;


import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;

import java.util.Optional;

public class DialogUtils {
    private DialogUtils() {
    }

    public static Optional<ButtonType> showAlert(Window window, Alert.AlertType alertType, String title,
                                                 String headerText, String contentText, ButtonType... buttonTypes) {

        Alert alert = new Alert(alertType, contentText, buttonTypes);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(headerText);

        for (Node node : alert.getDialogPane().getChildren())
            if (node instanceof Label)
                ((Label) node).setTextAlignment(TextAlignment.JUSTIFY);

        return alert.showAndWait();
    }
}