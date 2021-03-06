package com.mauriciotogneri.common.widgets;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class Fonts
{
    private static final Map<String, Typeface> fonts = new HashMap<>();
    private static final String FONTS_PATH = "fonts/";

    private static final String OPEN_SANS = "0";
    private static final String GLYPHICONS = "1";

    public static void init(Context context)
    {
        Fonts.loadFont(context.getAssets(), "opensans", Fonts.OPEN_SANS);
        Fonts.loadFont(context.getAssets(), "glyphicons", Fonts.GLYPHICONS);
    }

    private static void loadFont(AssetManager assets, String name, String key)
    {
        Fonts.fonts.put(key, Typeface.createFromAsset(assets, Fonts.FONTS_PATH + name + ".ttf"));
    }

    private static Typeface getFont(String name, int style)
    {
        Typeface fontFamily = Fonts.fonts.get(name);

        return Typeface.create(fontFamily, style);
    }

    public static Typeface getDefaultFont()
    {
        return Fonts.getFont(OPEN_SANS, Typeface.NORMAL);
    }

    public static Typeface getFont(String name)
    {
        return Fonts.getFont(name, Typeface.NORMAL);
    }
}