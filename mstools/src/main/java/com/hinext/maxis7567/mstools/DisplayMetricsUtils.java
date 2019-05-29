package com.hinext.maxis7567.mstools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DisplayMetricsUtils {
    private Context context;

    public DisplayMetricsUtils(Context context) {
        this.context = context;
    }

    /**
     * Converts the given device independent pixels (DIP) value into the corresponding pixels
     * value for the current screen.
     *

     * @param dip The DIP value to convert
     *
     * @return The pixels value for the current screen of the given DIP value.
     */
    public int convertDIPToPixels(int dip) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }

    /**
     * Converts the given device independent pixels (DIP) value into the corresponding pixels
     * value for the current screen.
     *
     * @param dip The DIP value to convert
     *
     * @return The pixels value for the current screen of the given DIP value.
     */
    public int convertDIPToPixels( float dip) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }

    /**
     * Converts the given pixels value into the corresponding device independent pixels (DIP)
     * value for the current screen.
     *

     * @param pixels The pixels value to convert
     *
     * @return The DIP value for the current screen of the given pixels value.
     */
    public float convertPixelsToDIP( int pixels) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return pixels / (displayMetrics.densityDpi / 160f);
    }

    /**
     * Returns the current screen dimensions in device independent pixels (DIP) as a {@link Point} object where
     * {@link Point#x} is the screen width and {@link Point#y} is the screen height.
     *
     *
     * @return The current screen dimensions in DIP.
     */
    public Point getScreenDimensionsInDIP() {
        Configuration configuration = context.getResources().getConfiguration();
        return new Point(configuration.screenWidthDp, configuration.screenHeightDp);

    }

    /**
     *
     * @return [true] if the device is in landscape orientation, [false] otherwise.
     */
    public boolean isInLandscapeOrientation() {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * @return [true] if the device has a small screen, [false] otherwise.
     */
    public boolean hasSmallScreen() {
        return getScreenSize() == Configuration.SCREENLAYOUT_SIZE_SMALL;
    }

    /**
     *
     * @return [true] if the device has a normal screen, [false] otherwise.
     */
    public boolean hasNormalScreen() {
        return getScreenSize() == Configuration.SCREENLAYOUT_SIZE_NORMAL;
    }

    /**
     *
     * @return [true] if the device has a large screen, [false] otherwise.
     */
    public  boolean hasLargeScreen() {
        return getScreenSize() == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     *
     * @return [true] if the device has an extra large screen, [false] otherwise.
     */
    public boolean hasXLargeScreen() {
        return getScreenSize() == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * The size of the screen, one of 4 possible values:
     *
     * <ul>
     *     <li>http://developer.android.com/reference/android/content/res/Configuration.html#SCREENLAYOUT_SIZE_SMALL</li>
     *     <li>http://developer.android.com/reference/android/content/res/Configuration.html#SCREENLAYOUT_SIZE_NORMAL</li>
     *     <li>http://developer.android.com/reference/android/content/res/Configuration.html#SCREENLAYOUT_SIZE_LARGE</li>
     *     <li>http://developer.android.com/reference/android/content/res/Configuration.html#SCREENLAYOUT_SIZE_XLARGE</li>
     * </ul>
     *
     * See http://developer.android.com/reference/android/content/res/Configuration.html#screenLayout for more details.
     *
     *
     * @return The size of the screen
     */
    public int getScreenSize() {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    public  Point getDeviceScreenSize(){
        Point size = new Point();
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(size);
        return size;
    }

}