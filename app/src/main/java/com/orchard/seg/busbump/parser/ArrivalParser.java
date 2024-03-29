package com.orchard.seg.busbump.parser;

import com.orchard.seg.busbump.model.Arrivals;
import com.orchard.seg.busbump.model.BusInfo;

import java.io.IOException;

public interface ArrivalParser {
    Arrivals readArrivals(String message) throws IOException;
}
