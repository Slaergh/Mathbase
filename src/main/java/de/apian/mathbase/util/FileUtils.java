/*
 * Copyright (c) 2017 MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.Normalizer;

/**
 * Nützlichkeiten für Dateioperationen
 *
 * @author Nikolas Kirschstein
 * @author Benedikt Mödl
 * @version 1.0
 * @since 1.0
 */
public class FileUtils {

    private FileUtils() {
    }

    /**
     * Normalisierung eines beliebigen {@code String} zu einem unseren Standards entsprechenden Datei-/Ordnernamen
     *
     * @param s Ausgangs-{@code String}
     * @return Normalisierter {@code String}
     * @since 1.0
     */
    public static String normalize(String s) {
        // Ersetzung der Leerzeichen und Umlaute
        s = s.toLowerCase().replace(" ", "_")
                .replace("ä", "ae")
                .replace("ö", "oe")
                .replace("ü", "ue")
                .replace("ß", "ss");
        // Entfernung aller Sonderzeichen, Akzente etc.
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^a-z_0-9.\\-]", "");
    }

    /**
     * Verschieben eines kompletten Verzeichnisses
     *
     * @param from Urpfad
     * @param to   Zielpfad
     * @throws IOException wenn das Verschieben fehlschlägt
     * @since 1.0
     */
    public static void move(Path from, Path to) throws IOException {
        copy(from, to);
        delete(from);
    }

    /**
     * Kopieren eines kompletten Verzeichnisses
     *
     * @param from Urpfad
     * @param to   Zielpfad
     * @throws IOException wenn das Kopieren fehlschlägt
     * @since 1.0
     */
    public static void copy(Path from, Path to) throws IOException {
        Files.walkFileTree(from, new CopyFileVisitor(from, to));
    }

    /**
     * Löschen eines kompletten Verzeichnises samt Inhalt
     *
     * @param path Pfad
     * @throws IOException wenn das Löschen fehlschlägt
     * @since 1.0
     */
    public static void delete(Path path) throws IOException {
        Files.walkFileTree(path, new DeleteFileVisitor());
    }

    /**
     * Herausfiltern einer Dateiendung
     *
     * @param path Pfad der Datei
     * @return Dateiendung (mit Punkt!) oder nichts, wenn es sich um einen Ordner handelt
     * @since 1.0
     */
    public static String getFileExtension(Path path) {
        String fileName = path.getFileName().toString(); //Dateiname mit Dateiendung
        return fileName.lastIndexOf('.') == -1 ? "" : fileName.substring(fileName.lastIndexOf('.'));
    }
}
