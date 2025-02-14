/*
 * Copyright © 2016, Finium Sdn Bhd, All Rights Reserved
 *
 * ZebraLabel.java
 * Modification History
 * *************************************************************
 * Date			Author		Comment
 * Jan 26, 2016		Venkaiah Chowdary Koneru		Created
 * *************************************************************
 */
package com.finium.core.drivers.zebra.model;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.finium.core.drivers.zebra.zpl.command.ZebraPrintMode;
import com.finium.core.drivers.zebra.zpl.enums.ZebraFont;
import com.finium.core.drivers.zebra.zpl.support.ZplUtils;

/**
 * @author Venkaiah Chowdary Koneru
 */
public class ZebraLabel {

    /**
     * Width explain in dots
     */
    private Integer widthDots;

    /**
     * # 6 dots = 1 mm, 152 dots = 1 inch <br>
     * # 8 dots = 1 mm, 203 dots = 1 inch <br>
     * # 11.8 dots = 1 mm, 300 dots = 1 inch <br>
     * # 24 dots = 1 mm, 608 dots = 1 inch
     * <p>
     * Height explain in dots
     */
    private Integer heightDots;

    private ZebraPrintMode zebraPrintMode = ZebraPrintMode.TEAR_OFF;

    private PrinterOptions printerOptions = new PrinterOptions();

    private List<ZebraElement> zebraElements = new ArrayList<ZebraElement>();

    public ZebraLabel() {
        super();
        zebraElements = new ArrayList<ZebraElement>();
    }

    public ZebraLabel(PrinterOptions printerOptions) {
        super();
        this.printerOptions = printerOptions;
    }

    /**
     * Create label with size
     *
     * @param heightDots height explain in dots
     * @param widthDots  width explain in dots
     */
    public ZebraLabel(int widthDots, int heightDots) {
        super();
        this.widthDots = widthDots;
        this.heightDots = heightDots;
    }

    /**
     * @param heightDots     height explain in dots
     * @param widthDots      width explain in dots
     * @param printerOptions
     */
    public ZebraLabel(int widthDots, int heightDots, PrinterOptions printerOptions) {
        super();
        this.widthDots = widthDots;
        this.heightDots = heightDots;
        this.printerOptions = printerOptions;
    }

    /**
     * Function to add Element on etiquette.
     * <p>
     * Element is abstract, you should use one of child Element( ZebraText,
     * ZebraBarcode, etc)
     *
     * @param zebraElement
     * @return
     */
    public ZebraLabel addElement(ZebraElement zebraElement) {
        zebraElements.add(zebraElement);
        return this;
    }

    /**
     * Use to define a default Zebra font on the label
     *
     * @param defaultZebraFont the defaultZebraFont to set
     */
    public ZebraLabel setDefaultZebraFont(ZebraFont defaultZebraFont) {
        printerOptions.setDefaultZebraFont(defaultZebraFont);
        return this;
    }

    /**
     * Use to define a default Zebra font size on the label (11,13,14). Not
     * explain in dots (convertion is processed by library)
     *
     * @param defaultFontSize the defaultFontSize to set
     */
    public ZebraLabel setDefaultFontSize(Integer defaultFontSize) {
        printerOptions.setDefaultFontSize(defaultFontSize);
        return this;
    }

    public Integer getWidthDots() {
        return widthDots;
    }

    /**
     * # 6 dots = 1 mm, 152 dots = 1 inch <br>
     * # 8 dots = 1 mm, 203 dots = 1 inch <br>
     * # 11.8 dots = 1 mm, 300 dots = 1 inch <br>
     * # 24 dots = 1 mm, 608 dots = 1 inch
     *
     * @param widthDots width explain in dots
     * @return
     */
    public ZebraLabel setWidthDots(Integer widthDots) {
        this.widthDots = widthDots;
        return this;
    }

    public Integer getHeightDots() {
        return heightDots;
    }

    /**
     * # 6 dots = 1 mm, 152 dots = 1 inch <br>
     * # 8 dots = 1 mm, 203 dots = 1 inch <br>
     * # 11.8 dots = 1 mm, 300 dots = 1 inch <br>
     * # 24 dots = 1 mm, 608 dots = 1 inch
     *
     * @param heightDots height explain in dots
     * @return
     */
    public ZebraLabel setHeightDots(Integer heightDots) {
        this.heightDots = heightDots;
        return this;
    }

    public PrinterOptions getPrinterOptions() {
        return printerOptions;
    }

    public void setPrinterOptions(PrinterOptions printerOptions) {
        this.printerOptions = printerOptions;
    }

    /**
     * @return the zebraPrintMode
     */
    public ZebraPrintMode getZebraPrintMode() {
        return zebraPrintMode;
    }

    /**
     * @param zebraPrintMode the zebraPrintMode to set
     */
    public ZebraLabel setZebraPrintMode(ZebraPrintMode zebraPrintMode) {
        this.zebraPrintMode = zebraPrintMode;
        return this;
    }

    /**
     * @return the zebraElements
     */
    public List<ZebraElement> getZebraElements() {
        return zebraElements;
    }

    /**
     * @param zebraElements the zebraElements to set
     */
    public void setZebraElements(List<ZebraElement> zebraElements) {
        this.zebraElements = zebraElements;
    }

    public String getZplCode() {
        StringBuilder zpl = new StringBuilder();

        zpl.append(ZplUtils.zplCommandSautLigne("XA"));// Start Label
        zpl.append(zebraPrintMode.getZplCode());

        if (widthDots != null) {
            // Define width for label
            zpl.append(ZplUtils.zplCommandSautLigne("PW", widthDots));
        }

        if (heightDots != null) {
            zpl.append(ZplUtils.zplCommandSautLigne("LL", heightDots));
        }

        // Default Font and Size
        if (printerOptions.getDefaultZebraFont() != null && printerOptions.getDefaultFontSize() != null) {
            zpl.append(ZplUtils.zplCommandSautLigne("CF",
                    (Object[]) ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(),
                            printerOptions.getDefaultFontSize(), printerOptions.getZebraPPP())));
        }

        for (ZebraElement zebraElement : zebraElements) {
            zebraElement.setPrinterOptions(printerOptions);
            zpl.append(zebraElement.getZplCode());
        }
        zpl.append(ZplUtils.zplCommandSautLigne("XZ"));// End Label
        return zpl.toString();
    }

    /**
     * Function use to have a preview of label rendering (not reflects reality).
     * <p>
     * Use it just to see disposition on label
     *
     * @return Graphics2D
     */
    public BufferedImage getImagePreview() {
        if (widthDots != null && heightDots != null) {
            int widthPx = ZplUtils.convertPointInPixel(widthDots);
            int heightPx = ZplUtils.convertPointInPixel(heightDots);
            BufferedImage image = new BufferedImage(widthPx, heightPx, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphic.setComposite(AlphaComposite.Src);
            graphic.fillRect(0, 0, widthPx, heightPx);

            graphic.setColor(Color.BLACK);
            graphic.setFont(new Font("Arial", Font.BOLD, 11));
            for (ZebraElement zebraElement : zebraElements) {
                zebraElement.drawPreviewGraphic(printerOptions, graphic);
            }
            return image;
        } else {
            throw new UnsupportedOperationException("Graphics Preview is only available ont label sized");
        }
    }
}
