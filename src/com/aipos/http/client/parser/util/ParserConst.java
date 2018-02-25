package com.aipos.http.client.parser.util;

/**
 * @author maksim.stelmachonak
 */
public class ParserConst {
  public static final int RESPONSE_CODE_LENGTH = 3;
  public static final String RESPONSE_CODE = "HTTP\\/1\\.1\\s\\d{3}";
  public static final String RESPONSE_MESSAGE = "\\d{3}.*";
  public static final String RESPONSE_HEADER = "(.)*:\\s(.)*";
  public static final String RESPONSE_DELIMITER = ":\\s";
  public static final String HTML_CODE_BEGIN = "<html>";
  public static final String HTML_CODE_END = "</html>";
}
