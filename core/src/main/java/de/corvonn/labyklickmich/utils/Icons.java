package de.corvonn.labyklickmich.utils;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;

public class Icons {
    private static final String TEXTURES_PATH = "textures/";
    private static final String NAMESPACE = "labyklickmich";

    public static final Icon KLICKMICH_LOGO = Icon.texture(ResourceLocation.create(NAMESPACE,  TEXTURES_PATH + "klickmich_icon.png"));
    public static final Icon WARNING = Icon.texture(ResourceLocation.create(NAMESPACE, TEXTURES_PATH + "warning.png"));
    public static final Icon ERROR = Icon.texture(ResourceLocation.create(NAMESPACE, TEXTURES_PATH + "error.png"));
    public static final Icon INFO = Icon.texture(ResourceLocation.create(NAMESPACE, TEXTURES_PATH + "info.png"));
}
