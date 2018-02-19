package com.aipos.http.client.parser;

import com.aipos.http.client.entity.HttpRequest;

/**
 * Created by y50-70 on 19.02.2018.
 */
public interface RequestParser {
    HttpRequest stringToRequest(String str);

    String RequestToString(HttpRequest request);
}
