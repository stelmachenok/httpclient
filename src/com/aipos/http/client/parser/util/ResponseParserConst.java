package com.aipos.http.client.parser.util;

/**
 * @author maksim.stelmachonak
 */
public class ResponseParserConst {
  public static final String TITLE_BEGIN = "<title>";
  public static final String TITLE_END = "</title>";
  public static final String TEXT_BEGIN = "<p class=MsoNormal>";
  public static final String TEXT_END = "</p>";
  public static final String RESPONSE_CODE = "HTTP/1\\.1\\W\\d{3}";
  public static final int RESPONSE_CODE_LENGTH = 3;
  public static final String CODE_301 = "301";
  public static final String CODE_301_MESSAGE = "Moved Permanently";
  public static final String CODE_404 = "404";
  public static final String CODE_404_MESSAGE = "Not Found";
  public static final String CODE_200 = "200";
  public static final String CODE_200_MESSAGE = "OK";
  public static final String LOGIN_ERROR_MESSAGE = "Incorrect login";
  public static final String PASSWORD_ERROR_MESSAGE = "Incorrect password";
  public static final String EMPTY_FIELD_MESSAGE = "Login or password is empty";
  public static final String LOGIN_SUCCESS_MESSAGE = "Login successfully";
  public static final String LOGIN_ERROR_STRING = "login_error";
  public static final String PASSWORD_ERROR_STRING = "password_error";
  public static final String COCKIE_STRING = "Set-Cookie:";
}
