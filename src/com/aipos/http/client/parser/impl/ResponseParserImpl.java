package com.aipos.http.client.parser.impl;

import static com.aipos.http.client.parser.util.ParserConst.*;

import com.aipos.http.client.entity.HttpResponse;
import com.aipos.http.client.parser.ResponseParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author maksim.stelmachonak
 */
public class ResponseParserImpl implements ResponseParser {

  @Override
  public HttpResponse stringToResponse(String str) {
    HttpResponse request = new HttpResponse();

    Pattern p = Pattern.compile(RESPONSE_CODE);
    Matcher m = p.matcher(str);
    if (m.find()) {
      int code = Integer.valueOf(str.substring(m.end() - RESPONSE_CODE_LENGTH, m.end()));
      request.setCode(code);
    }

    p = Pattern.compile(RESPONSE_MESSAGE);
    m = p.matcher(str);
    if (m.find()) {
      String message = String.valueOf(str.substring(m.start() + RESPONSE_CODE_LENGTH + 1, m.end()));
      request.setMessage(message);
    }

    p = Pattern.compile(RESPONSE_HEADER);
    m = p.matcher(str);
    while (m.find()) {
      String header = String.valueOf(str.substring(m.start(), m.end()));
      Pattern headerPattern = Pattern.compile(RESPONSE_DELIMITER);
      Matcher headerMatcher = headerPattern.matcher(header);
      String headerKey = header.substring(0, headerMatcher.start());
      String headerValue = header.substring(headerMatcher.start(), header.length());
      request.addHeader(headerKey, headerValue);
    }

    return request;
  }
}
