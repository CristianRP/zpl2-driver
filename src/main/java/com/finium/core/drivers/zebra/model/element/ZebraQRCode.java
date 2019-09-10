/*
 * Copyright © 2016, Finium Sdn Bhd, All Rights Reserved
 *
 * ZebraQRCode.java
 * Modification History
 * *************************************************************
 * Date			    Author		                    Comment
 * 31-Jan-2016		Venkaiah Chowdary Koneru		Created
 * 02-Sept-2019     Cristian Ramírez                Modified
 * *************************************************************
 */
package com.finium.core.drivers.zebra.model.element;

import com.finium.core.drivers.zebra.model.ZebraElement;
import com.finium.core.drivers.zebra.zpl.enums.QRCodeCharacterMode;
import com.finium.core.drivers.zebra.zpl.enums.QRCodeDataInputMode;
import com.finium.core.drivers.zebra.zpl.enums.QRCodeErrorCorrection;
import com.finium.core.drivers.zebra.zpl.enums.QRCodeModel;
import com.finium.core.drivers.zebra.zpl.enums.ZebraRotation;
import com.finium.core.drivers.zebra.zpl.support.ZplUtils;

import static com.finium.core.drivers.zebra.zpl.command.ZebraCommonCodes.FIELD_DATA;
import static com.finium.core.drivers.zebra.zpl.command.ZebraCommonCodes.FIELD_SEPERATOR;
import static com.finium.core.drivers.zebra.zpl.command.ZebraCommonCodes.QR_CODE;
import static com.finium.core.drivers.zebra.zpl.enums.QRCodeCharacterMode.A;
import static com.finium.core.drivers.zebra.zpl.enums.QRCodeDataInputMode.M;
import static com.finium.core.drivers.zebra.zpl.enums.QRCodeErrorCorrection.STANDARD;
import static com.finium.core.drivers.zebra.zpl.enums.QRCodeModel.MODEL2;
import static com.finium.core.drivers.zebra.zpl.enums.ZebraRotation.NORMAL;

/**
 * This command produces a matrix symbology consisting of an array of nominally
 * square modules arranged in an overall square pattern. A unique pattern at
 * three of the symbol’s four corners assists in determining bar code size,
 * position, and inclination. <br>
 * <br>
 * A wide range of symbol sizes is possible, along with four levels of error
 * correction. User- specified module dimensions provide a wide variety of
 * symbol production techniques.<br>
 * <br>
 * QR Code Model 1 is the original specification, while QR Code Model 2 is an
 * enhanced form of the symbology. Model 2 provides additional features and can
 * be automatically differentiated from Model 1.<br>
 * <br>
 * Model 2 is the recommended model and should normally be used.<br>
 * <br>
 * This bar code is printed using field data specified in a subsequent
 * <code>^FD</code> string.<br>
 * <br>
 * Encodable character sets include numeric data, alphanumeric data, 8-bit byte
 * data, and Kanji characters.<br>
 * <br>
 * <b>Format: <code>^BQa,b,c,d,e</b><br>
 * a = field orientation. Fixed Value: normal (<code>^FW</code> has no effect on
 * rotation)<br>
 * b = model. Accepted Values: <b>1</b> (original) and <b>2</b> (enhanced –
 * recommended). Default Value: <b>2</b><br>
 * c = magnification factor. Accepted Values: <b>1</b> to <b>10</b>.
 * <ul>
 * Default Value:
 * <li>1 on 150 dpi printers</li>
 * <li>2 on 200 dpi printers</li>
 * <li>3 on 300 dpi printers</li>
 * <li>6 on 600 dpi printers</li>
 * </ul>
 * d = error correction.
 * <ul>
 * Accepted Values:
 * <li><b>H</b> = ultra-high reliability level</li>
 * <li><b>Q</b> = high reliability level</li>
 * <li><b>M</b> = standard level</li>
 * <li><b>L</b> = high density level.</li>
 * </ul>
 * <ul>
 * Default Value:
 * <li><b>Q</b> = if empty</li>
 * <li><b>M</b> = invalid values</li>
 * </ul>
 * e = mask value. Accepted Values: <b>0</b> - <b>7</b>. Default Value:
 * <b>7</b> </code>
 *
 * @author Venkaiah Chowdary Koneru
 * <p>
 * Modified and reformatted by Cristian Ramírez
 */
public class ZebraQRCode extends ZebraElement {
    private ZebraRotation zebraRotation;
    private QRCodeModel model;
    private QRCodeDataInputMode inputMode;
    private QRCodeCharacterMode characterMode;
    private QRCodeErrorCorrection errorCorrection;
    private String magnification; // Values for magnification are from 0 to 10, check the Zebra docs

    private String text;

    /**
     * @param text and load default config
     */
    public ZebraQRCode(String text) {
        this.zebraRotation = NORMAL;
        this.model = MODEL2;
        this.inputMode = M;
        this.characterMode = A;
        this.errorCorrection = STANDARD;
        this.magnification = "";
        this.text = text;
    }

    /**
     * @param text and load default config
     * @param magnification values are from 0 to 10, check the Zebra docs
     */
    public ZebraQRCode(String text, String magnification) {
        this.zebraRotation = NORMAL;
        this.model = MODEL2;
        this.inputMode = M;
        this.characterMode = A;
        this.errorCorrection = STANDARD;
        this.magnification = magnification;
        this.text = text;
    }

    public ZebraQRCode(ZebraRotation zebraRotation, QRCodeModel model, QRCodeDataInputMode inputMode, QRCodeCharacterMode characterMode, QRCodeErrorCorrection errorCorrection, String magnification, String text) {
        this.zebraRotation = zebraRotation;
        this.model = model;
        this.inputMode = inputMode;
        this.characterMode = characterMode;
        this.errorCorrection = errorCorrection;
        this.magnification = magnification;
        this.text = text;
    }

    /**
     * Return Zpl code for this Element
     *
     * @return
     */
    @Override
    public String getZplCode() {
        StringBuilder zpl = new StringBuilder(ZplUtils.zplCommand(QR_CODE.getCode()));
        zpl.append(zebraRotation.getLetter());
        zpl.append(",");
        zpl.append(model.getModel());
        zpl.append(",");
        zpl.append(magnification +",");
        zpl.append(errorCorrection.getLetter());
        zpl.append("\n");
        zpl.append(ZplUtils.zplCommand(FIELD_DATA.getCode()));
        zpl.append(errorCorrection.getLetter());
        zpl.append(inputMode.name());
        zpl.append(",");

        if (inputMode.equals(QRCodeDataInputMode.M)) {
            zpl.append(characterMode.name());
        }

        zpl.append(text);
        zpl.append(ZplUtils.zplCommandSautLigne(FIELD_SEPERATOR.getCode()));

        return zpl.toString();
    }


    public ZebraRotation getZebraRotation() {
        return zebraRotation;
    }

    public void setZebraRotation(ZebraRotation zebraRotation) {
        this.zebraRotation = zebraRotation;
    }

    public QRCodeModel getModel() {
        return model;
    }

    public void setModel(QRCodeModel model) {
        this.model = model;
    }

    public QRCodeDataInputMode getInputMode() {
        return inputMode;
    }

    public void setInputMode(QRCodeDataInputMode inputMode) {
        this.inputMode = inputMode;
    }

    public QRCodeCharacterMode getCharacterMode() {
        return characterMode;
    }

    public void setCharacterMode(QRCodeCharacterMode characterMode) {
        this.characterMode = characterMode;
    }

    public QRCodeErrorCorrection getErrorCorrection() {
        return errorCorrection;
    }

    public void setErrorCorrection(QRCodeErrorCorrection errorCorrection) {
        this.errorCorrection = errorCorrection;
    }

    public String getMagnification() {
        return magnification;
    }

    public void setMagnification(String magnification) {
        this.magnification = magnification;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
