package gr.meerkat.aeneas.Utils;

import android.graphics.drawable.Drawable;

/**
 * Created by w00dh3n on 29/11/2014.
 */
public class PackageItem {
    private Drawable icon;
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
