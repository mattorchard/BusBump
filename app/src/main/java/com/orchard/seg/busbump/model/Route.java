package com.orchard.seg.busbump.model;


import java.util.Locale;

public class Route {

    private final int mRouteNo;
    private final int mDirectionId;
    private final String mDirection;
    private final String mRouteHeading;

    public Route(int mRouteNo, int mDirectionId, String mDirection, String mRouteHeading) {
        this.mRouteNo = mRouteNo;
        this.mDirectionId = mDirectionId;
        this.mDirection = mDirection;
        this.mRouteHeading = mRouteHeading;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Route(%d, %d, %s, %s)",
                mRouteNo,
                mDirectionId,
                mDirection,
                mRouteHeading);
    }

    public int getRouteNo() {
        return mRouteNo;
    }

    public int getDirectionId() {
        return mDirectionId;
    }

    public String getDirection() {
        return mDirection;
    }

    public String getRouteHeading() {
        return mRouteHeading;
    }
}
