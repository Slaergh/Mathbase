/*
 * Copyright (c) 2017. MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.util;

/**
 * Festgelegte Kontent-Typen
 *
 * @author Benedikt Mödl
 * @version 1.0
 * @since 1.0
 */
public enum ContentType {
    IMAGE("image"),
    WORKSHEET("worksheet"),
    VIDEO("video"),
    GEOGEBRA("geogebra"),
    DESCRIPTION("description"),
    OTHER("other");

    /**
     * Wert des Kontent-Typs in der XML-Datei
     *
     * @since 1.0
     */
    public final String ATTR_VALUE;

    /**
     * Konstruktor
     *
     * @since 1.0
     */
    ContentType(String ATTR_VALUE) {
        this.ATTR_VALUE = ATTR_VALUE;
    }
}
