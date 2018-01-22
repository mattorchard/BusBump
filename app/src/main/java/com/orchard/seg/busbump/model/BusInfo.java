package com.orchard.seg.busbump.model;



import java.util.Locale;

public class BusInfo {
    private static final int DEFAULT_COLOR = 0xFF000000;

    private long mId = -1;

    private final int mStopNumber;
    private final int mBusNumber;
    private int mDirectionNumber;

    private final boolean mLoadOnLaunch;
    private final String mName;
    private final int mColor;

    private Arrivals mArrivals;


    public BusInfo(int stopNumber,
                   int busNumber,
                   int directionNumber,
                   boolean loadOnLaunch,
                   String name,
                   int color) {
        this.mStopNumber = stopNumber;
        this.mBusNumber = busNumber;
        this.mDirectionNumber = directionNumber;
        this.mLoadOnLaunch = loadOnLaunch;
        this.mName = name;
        this.mColor = color;
    }

    public BusInfo(int stopNumber, int busNumber, int directionNumber) {
        this(stopNumber, busNumber, directionNumber, false, null, DEFAULT_COLOR);
    }


    @Override
    public String toString() {
        return String.format(Locale.US, "BusInfo(%d %d, %d, %d, %b, %s, %d)",
                mId,
                mStopNumber,
                mBusNumber,
                mDirectionNumber,
                mLoadOnLaunch,
                mName,
                mColor);
    }

    public void setId(long id) {
        this.mId = id;
    }

    public void setArrivals(Arrivals arrivals) {
        this.mArrivals = arrivals;
    }

    public long getId() {
        return mId;
    }

    public int getStopNumber() {
        return mStopNumber;
    }

    public int getBusNumber() {
        return mBusNumber;
    }

    public int getDirectionNumber() {
        return mDirectionNumber;
    }

    public boolean loadOnLaunch() {
        return mLoadOnLaunch;
    }

    public String getName() {
        return mName;
    }

    public int getColor() {
        return mColor;
    }

    public Arrivals getArrivals() {
        return mArrivals;
    }
}
