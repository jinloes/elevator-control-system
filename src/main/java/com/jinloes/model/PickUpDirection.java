package com.jinloes.model;

import org.apache.commons.lang3.StringUtils;

/**
 * The direction a user wishes to move after being picked up.
 */
public enum PickUpDirection {
    UP,
    DOWN;

    public static PickUpDirection fromString(String str) {
        return StringUtils.isNotEmpty(str) ? valueOf(str.toUpperCase()) : null;
    }
}
