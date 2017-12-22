package com.orchard.seg.busbump.model;

public class BusInfo {
    private long mId = -1;

    private final int mStopNumber;
    private final int mBusNumber;
    private int mDirectionNumber;

    private final String mName;
    private final String mColor;

    private Arrivals mArrivals;


    public BusInfo(int stopNumber,
                   int busNumber,
                   int directionNumber,
                   String name,
                   String color) {
        this.mStopNumber = stopNumber;
        this.mBusNumber = busNumber;
        this.mDirectionNumber = directionNumber;
        this.mName = name;
        this.mColor = color;
    }

    public BusInfo(int stopNumber, int busNumber, int directionNumber) {
        this(stopNumber, busNumber, directionNumber, null, null);
    }

    public BusInfo(int stopNumber, int busNumber) {
        this(stopNumber, busNumber, 0);
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

    public String getName() {
        return mName;
    }

    public String getColor() {
        return mColor;
    }

    public Arrivals getArrivals() {
        return mArrivals;
    }
}
