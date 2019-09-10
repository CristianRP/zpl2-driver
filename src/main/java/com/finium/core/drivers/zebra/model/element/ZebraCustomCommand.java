/**
 * Zebra custom command with pos x,y,z
 *
 * This supports all command with that attributes
 * Ex. ^FO ^BY
 *
 * @author Cristian Ramírez
 *
 */

package com.finium.core.drivers.zebra.model.element;

import static com.finium.core.drivers.zebra.zpl.command.ZebraCommonCodes.FIELD_ORIGIN;

public class ZebraCustomCommand {
    private String command;
    private Integer posX;
    private Integer posY;
    private Integer posZ;

    public ZebraCustomCommand() {
    }

    public ZebraCustomCommand(String command, Integer posX, Integer posY, Integer posZ) {
        this.command = command;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getPosZ() {
        return posZ;
    }

    public void setPosZ(Integer posZ) {
        this.posZ = posZ;
    }

    public String getZplCode() {
        StringBuilder zpl = new StringBuilder(ZplUtils.zplCommand(FIELD_ORIGIN.getCode()));
        zpl.append(posX);
        zpl.append(",");
        zpl.append(posY);
        zpl.append(",");
        zpl.append(posZ);

        return zpl.toString();
    }
}