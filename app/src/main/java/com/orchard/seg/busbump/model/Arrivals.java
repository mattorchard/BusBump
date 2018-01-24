package com.orchard.seg.busbump.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Arrivals {
    private final Date mAcquiredAt;
    private final Date[] mArrivals;

    public Arrivals(Date acquiredAt, Date[] arrivals) {
        if (arrivals.length == 0) {
            throw new IllegalArgumentException("Arrivals must have at least one arrival");
        } else if (arrivals.length > 3) {
            throw new IllegalArgumentException("Arrivals cannot have more than three arrivals");
        }
        this.mArrivals = arrivals;
        this.mAcquiredAt = acquiredAt;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Arrivals(%s, %s)",
                mAcquiredAt.toString(), Arrays.toString(mArrivals));
    }

    public Date getAcquiredAt() {
        return this.mAcquiredAt;
    }

    public Date getArrival(int index) {
        return mArrivals[index];
    }

    public int getNumArrivals() {
        return this.mArrivals.length;
    }
}
