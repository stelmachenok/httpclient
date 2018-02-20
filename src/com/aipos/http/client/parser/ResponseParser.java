package com.aipos.http.client.parser;

import com.aipos.http.client.entity.HttpResponse;

/**
 * @author maksim.stelmachonak
 */
public interface ResponseParser {
  HttpResponse stringToResponse(String str);
}
