package com.orchard.seg.busbump.parser;

import com.orchard.seg.busbump.model.Route;

import java.io.IOException;
import java.util.List;

public interface RouteParser {
    List<Route> readRoutes(String message) throws IOException;
}
