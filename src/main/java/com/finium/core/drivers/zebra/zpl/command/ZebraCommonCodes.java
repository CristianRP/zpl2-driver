/*
 * Copyright © 2016, Finium Sdn Bhd, All Rights Reserved
 *
 * ZebraCommonCodes.java
 * Modification History
 * *************************************************************
 * Date			Author		Comment
 * 31-Jan-2016		Venkaiah Chowdary Koneru		Created
 * *************************************************************
 */
package com.finium.core.drivers.zebra.zpl.command;

/**
 * @author Venkaiah Chowdary Koneru
 */
public enum ZebraCommonCodes {
    /**
     *
     */
    START_FMT("XA", "Start Format"),
    /**
     *
     */
    END_FMT("XZ", "End Format"),
    /**
     *
     */
    FIELD_DATA("FD", "Field Data"),
    /**
     *
     */
    FIELD_SEPERATOR("FS", "Field Seperator"),
    /**
     *
     */
    DOWNLOAD_FMT("DF", "Download Format"),
    /**
     *
     */
    RECALL_FORMAT("XF",
            "Recall Format"),
    /**
     * A matrix symbology consisting of an array of
     * nominally square modules arranged in an overall
     * square pattern. A unique pattern at three of
     * the symbol’s four corners assists in
     * determining bar code size, position, and
     * inclination.
     */
    QR_CODE("BQ", "QR Code BarCode"),

    /**
     * ^FO Field Origin command sets a field origin, relative to the label home (^LH) position.
     * ^FO sets the upper-left corner of the field area by defining points along
     * the x-axis and y-axis independent of the rotation
     * Format: ^FOx,y,z
     *
     * @see <a href="https://www.zebra.com/content/dam/zebra/manuals/printers/common/programming/zpl-zbi2-pm-en.pdf">Zebra Doc</a>
     */
    FIELD_ORIGIN("FO", "Field Origin");



    /**
     *
     */

    private String description;
    private String code;

    /**
     *
     */
    private ZebraCommonCodes(String Code, String description) {
        this.code = Code;
        this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

}
