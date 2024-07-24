package me.alpha432.oyvey.manager;

import me.alpha432.oyvey.features.gui.Component;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.util.ColorUtil;

import java.awt.*;

public class ColorManager {
    private float red = 1.0f;
    private float green = 1.0f;
    private float blue = 1.0f;
    private float alpha = 1.0f;
    private Color color = new Color(this.red, this.green, this.blue, this.alpha);

    /**
     * Initializes the color manager with the color settings from the ClickGui module.
     */
    public void init() {
        ClickGui ui = ClickGui.getInstance();
        setColor(ui.red.getValue(), ui.green.getValue(), ui.blue.getValue(), ui.hoverAlpha.getValue());
    }

    /**
     * Returns the current color.
     *
     * @return the current color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color directly.
     *
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the current color as an integer RGBA value.
     *
     * @return the current color as an integer RGBA value
     */
    public int getColorAsInt() {
        return ColorUtil.toRGBA(this.color);
    }


    /**
     * Returns the current color with full alpha as an integer RGBA value.
     *
     * @return the current color with full alpha as an integer RGBA value
     */
    public int getColorAsIntFullAlpha() {
        return ColorUtil.toRGBA(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 255));
    }

    /**
     * Returns the color with the specified alpha value.
     * If rainbow mode is enabled, it returns a rainbow color.
     *
     * @param alpha the alpha value to use
     * @return the color with the specified alpha value or a rainbow color if enabled
     */
    public int getColorWithAlpha(int alpha) {
        if (ClickGui.getInstance().rainbow.getValue()) {
            return ColorUtil.rainbow(Component.counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB();
        }
        return ColorUtil.toRGBA(new Color(this.red, this.green, this.blue, (float) alpha / 255.0f));
    }

    /**
     * Sets the color using float values for red, green, blue, and alpha.
     *
     * @param red   the red component
     * @param green the green component
     * @param blue  the blue component
     * @param alpha the alpha component
     */
    public void setColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.updateColor();
    }

    /**
     * Updates the color based on the current red, green, blue, and alpha values.
     */
    public void updateColor() {
        this.setColor(new Color(this.red, this.green, this.blue, this.alpha));
    }

    /**
     * Sets the color using integer values for red, green, blue, and alpha.
     *
     * @param red   the red component
     * @param green the green component
     * @param blue  the blue component
     * @param alpha the alpha component
     */
    public void setColor(int red, int green, int blue, int alpha) {
        this.red = (float) red / 255.0f;
        this.green = (float) green / 255.0f;
        this.blue = (float) blue / 255.0f;
        this.alpha = (float) alpha / 255.0f;
        this.updateColor();
    }

    /**
     * Sets the red component of the color.
     *
     * @param red the red component
     */
    public void setRed(float red) {
        this.red = red;
        this.updateColor();
    }

    /**
     * Sets the green component of the color.
     *
     * @param green the green component
     */
    public void setGreen(float green) {
        this.green = green;
        this.updateColor();
    }

    /**
     * Sets the blue component of the color.
     *
     * @param blue the blue component
     */
    public void setBlue(float blue) {
        this.blue = blue;
        this.updateColor();
    }

    /**
     * Sets the alpha component of the color.
     *
     * @param alpha the alpha component
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        this.updateColor();
    }

}