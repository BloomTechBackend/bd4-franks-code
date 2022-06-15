package com.amazon.ata.immutabilityandfinal.classroom.primephoto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class representing a PrimePhoto - contains dimensions, and a list of Pixels that make up the image.
 */
// Make the immutable so it can be safely used by concurrent, multiple threads
//      adding final to class and member, making any other changes required by doing that
//      and use defensive copy in the ctor and defensive return on getter for List member
public final class PrimePhoto {
    private final List<Pixel> pixels;
    private final int height;
    private final int width;
    // used when saving to a buffered image
    private final int type;

    // Since List is an interface, objects for a List are passed by reference
    //       so we should use defensive copy to initialize our object List parameter
    public PrimePhoto(List<Pixel> pixelList, int height, int width, int type) {
        if (pixelList.size() != (height * width)) {
            throw new IllegalArgumentException("Not enough pixels for the dimensions of the image.");
        }
        // this.pixels = pixelList;  // replaced by defensive copy
        this.pixels = new ArrayList<>(pixelList);  // copy the paramter to our class
        this.height = height;
        this.width = width;
        this.type = type;
    }

    // since pixels is a reference to a List - use defensive return
    public List<Pixel> getPixels() {
        return new ArrayList<>(pixels);  // return a copy of the member reference
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pixels, height, width, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        PrimePhoto photo = (PrimePhoto) obj;

        return photo.height == this.height && photo.width == this.width &&
            photo.type == photo.type && Objects.equals(photo.pixels, this.pixels);
    }

}
