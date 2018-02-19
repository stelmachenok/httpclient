package com.aipos.http.client.parser;

import static com.aipos.http.client.parser.util.ParserConst.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author maksim.stelmachonak
 */
public class ResponseParser {

  public String parseGet(String response) {
    StringBuilder sb = new StringBuilder();

    Pattern pattern = Pattern.compile(RESPONSE_CODE);

    Matcher matcher = pattern.matcher(response);

    if (matcher.find()) {
      sb.append(response.substring(matcher.end() - RESPONSE_CODE_LENGTH, matcher.end()));
    }

    String responseCode = sb.toString();

    String answer = "";

    if (responseCode.equals(CODE_301)) {
      answer = CODE_301_MESSAGE;
    }

    if (responseCode.equals(CODE_404)) {
      answer = CODE_404_MESSAGE;
    }

    if (!answer.equals("")) {
      return answer;
    }
    sb = new StringBuilder();

    Pattern patternBegin = Pattern.compile(TITLE_BEGIN);
    Pattern patternEnd = Pattern.compile(TITLE_END);

    Matcher matcherBegin = patternBegin.matcher(response);
    Matcher matcherEnd = patternEnd.matcher(response);

    if (matcherBegin.find() && matcherEnd.find()) {
      sb.append(response.substring(matcherBegin.end(), matcherEnd.start()));
      sb.append("\n");
    }

    patternBegin = Pattern.compile(TEXT_BEGIN);
    patternEnd = Pattern.compile(TEXT_END);

    matcherBegin = patternBegin.matcher(response);
    matcherEnd = patternEnd.matcher(response);

    while (matcherBegin.find() && matcherEnd.find()) {
      int startPosition = matcherBegin.end();
      int endPosition = matcherEnd.start();

      while (startPosition > endPosition) {
        int oldEndPosition = endPosition;
        endPosition = matcherEnd.start();
        if (oldEndPosition == endPosition)
          break;
      }
      if (startPosition < endPosition) {
        sb.append(response.substring(startPosition, endPosition));
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  public String parseHead(String response){
    StringBuilder sb = new StringBuilder();

    Pattern pattern = Pattern.compile(RESPONSE_CODE);

    Matcher matcher = pattern.matcher(response);

    if (matcher.find()) {
      sb.append(response.substring(matcher.end() - RESPONSE_CODE_LENGTH, matcher.end()));
    }

    String responseCode = sb.toString();

    String answer = "";

    if (responseCode.equals(CODE_301)) {
      answer = CODE_301_MESSAGE;
    }

    if (responseCode.equals(CODE_404)) {
      answer = CODE_404_MESSAGE;
    }

    if (responseCode.equals(CODE_200)) {
      answer = CODE_200_MESSAGE;
    }
    return answer;
  }

  public String parsePost(String response){
    StringBuilder sb = new StringBuilder();

    String answer = EMPTY_FIELD_MESSAGE;

    Pattern pattern = Pattern.compile(LOGIN_ERROR_STRING);
    Matcher matcher = pattern.matcher(response);
    if (matcher.find()) {
      answer = LOGIN_ERROR_MESSAGE;
    }

    pattern = Pattern.compile(PASSWORD_ERROR_STRING);
    matcher = pattern.matcher(response);
    if (matcher.find()) {
      answer = PASSWORD_ERROR_MESSAGE;
    }

    pattern = Pattern.compile(COCKIE_STRING);
    matcher = pattern.matcher(response);
    if (matcher.find()) {
      answer = LOGIN_SUCCESS_MESSAGE;
    }
    return answer;
  }
}
