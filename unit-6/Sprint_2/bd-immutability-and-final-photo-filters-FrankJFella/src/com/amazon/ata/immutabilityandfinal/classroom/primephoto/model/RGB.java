package com.amazon.ata.immutabilityandfinal.classroom.primephoto.model;

import java.util.Objects;

/**
 * An object that represent colors. Each object represents the color as three integers that stand for primary color
 * values.
 */
// Make the immutable so it can be safely used by concurrent, multiple threads
//      adding final to class and member, making any other changes required by doing that
public final class RGB {

    private final int red;
    private final int green;
    private final int blue;
    private final int transparency;

    private void check(int red, int green, int blue, int transparency) {
        if (red < 0 || red > 255 ||
            green < 0 || green > 255 ||
            blue < 0 || blue > 255) {
            throw new IllegalArgumentException(String.format("Invalid values color values. Red, green, " +
                "and blue must be between 0 and 255: {red: %d , green: %d, blue: %d}", red, green, blue));
        }
        if (transparency < 0 || transparency > 255) {
            throw new IllegalArgumentException("Invalid transparency value: " + transparency);
        }
    }

    // since there are no references passed to this ctor - defensive copy is not needed
    public RGB(int red, int green, int blue, int transparency) {
        check(red, green, blue, transparency);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.transparency = transparency;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getTransparency() {
        return transparency;
    }

    /**
     * Averages the red, blue, and green components, producing a grey color.
     */
    //public void toGreyScale( ){  // return type changed from void to object of the class
    public RGB toGreyScale( ){
        int avg = (red + green + blue) / 3;

        // Since marking member data as final, we can no change it
        // we need to return a new object with the values we want changed
        //    and also the unchanged values
        //red   = avg;
        //green = avg;
        //blue  = avg;
        return new RGB(avg, avg, avg, this.transparency);
        //return new RGB(avg, avg, avg, transparency);  // this. is optional on transparency
    }

    /**
     * Converts the color to a reddish-brown color.
     */
    // public void toSepia( ) {
    public RGB toSepia( ) { // return type changed from void to object of the class
        int newRed = (int)(0.393 * red + 0.769 * green + 0.189 * blue);
        int newGreen = (int)(0.349 * red + 0.686 * green + 0.168 * blue);
        int newBlue = (int)(0.272 * red + 0.534 * green + 0.131 * blue);

        // Since marking member data as final, we can no change it
        // we need to return a new object with the values we want changed
        //    and also the unchanged values
        //red   = Math.min(255, newRed);
        //green = Math.min(255, newGreen);
        //blue  = Math.min(255, newBlue);

        return new RGB(Math.min(255, newRed)
                     , Math.min(255, newGreen)
                     , Math.min(255, newBlue)
                     , this.transparency);  // this. is optional - provided for documentation
    }

    /**
     * "Dark mode" - Assigns red, green, and blue, their current value subtracted from their max value (255).
     * This will turn white to black.
     */

    // public void invert() {  // return type changed from void to object of the class
    public RGB invert() {
        //red   = 255 - red;
        //green = 255 - green;
        //blue  = 255 - blue;
        // Since marking member data as final, we can no change it
        // we need to return a new object with the values we want changed
        //    and also the unchanged values
        return new RGB(255 - red, 255 - green, 255 - blue, this.transparency);  // this. is optional
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue, transparency);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        RGB rgb = (RGB) obj;

        return rgb.red == this.red && rgb.green == this.green &&
            rgb.blue == this.blue && rgb.transparency == this.transparency;
    }
}
