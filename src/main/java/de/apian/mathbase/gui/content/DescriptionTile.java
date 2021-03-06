/*
 * Copyright (c) 2017 MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.gui.content;

import de.apian.mathbase.gui.MainPane;
import de.apian.mathbase.gui.dialog.WarningAlert;
import de.apian.mathbase.util.Constants;
import de.apian.mathbase.util.Images;
import de.apian.mathbase.util.Logging;
import de.apian.mathbase.xml.Content;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;


/**
 * Beschreibungskachel.
 *
 * @author Nikolas Kirschstein
 * @version 1.0
 * @since 1.0
 */
public class DescriptionTile extends AbstractTile {
    public DescriptionTile(Content content, String directoryPath, ContentPane contentPane, MainPane mainPane) {
        super(content, directoryPath, contentPane, mainPane);

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);

        try {
            int position = textArea.getCaretPosition();
            for (String line : Files.readAllLines(Paths.get(directoryPath, content.getFilename())))
                textArea.appendText(line + "\n");
            textArea.positionCaret(position);
        } catch (IOException e) {
            textArea.setText(Constants.BUNDLE.getString("text_load_fail"));
        }

        editButton.setOnAction(a -> {
            if (editButton.getText() == null) {
                editButton.setText(Constants.BUNDLE.getString("done"));
                textArea.setEditable(true);
            } else {
                editButton.setText(null);
                textArea.setEditable(false);
                try {
                    Files.write(Paths.get(directoryPath, content.getFilename()), textArea.getText().getBytes("utf-8"));
                } catch (IOException e) {
                    Logging.log(Level.WARNING, "Text abspeichern fehlgeschlagen!", e);
                    new WarningAlert().showAndWait();
                }
            }
        });

        setCenter(textArea);
    }
}
