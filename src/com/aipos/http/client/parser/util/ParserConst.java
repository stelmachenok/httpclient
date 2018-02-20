package com.aipos.http.client.parser.util;

/**
 * @author maksim.stelmachonak
 */
public class ParserConst {
  public static final int RESPONSE_CODE_LENGTH = 3;
  public static final String RESPONSE_CODE = "HTTP\\/1\\.1\\W\\d{3}";
  public static final String RESPONSE_MESSAGE = "\\d{3}.*";
  public static final String RESPONSE_HEADER = "(.)*:\\W(.)*";
  public static final String RESPONSE_DELIMITER = ":\\W";
}
