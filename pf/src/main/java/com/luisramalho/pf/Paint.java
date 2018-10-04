package com.luisramalho.pf;

/**
 * A class representing a Paint, in other words a pair of color and type.
 *
 * Created by Luís Ramalho on 19/11/16.
 * <info@luisramalho.com>
 */

class Paint {
    private int color;
    private int type;

    Paint(String color, String type) {
        this.color = Integer.parseInt(color);
        this.type = Integer.parseInt(type);
    }

    int getColorIndex() {
        return color - 1;
    }

    int getType() {
        return type;
    }
}
