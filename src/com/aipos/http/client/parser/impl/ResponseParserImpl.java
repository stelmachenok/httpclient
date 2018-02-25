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
        System.out.println(str);
        HttpResponse response = new HttpResponse();

        Pattern p = Pattern.compile(RESPONSE_CODE);
        Matcher m = p.matcher(str);
        if (m.find()) {
            int code = Integer.valueOf(str.substring(m.end() - RESPONSE_CODE_LENGTH, m.end()));
            response.setCode(code);
        }

        p = Pattern.compile(RESPONSE_MESSAGE);
        m = p.matcher(str);
        if (m.find()) {
            String message = String.valueOf(str.substring(m.start() + RESPONSE_CODE_LENGTH + 1, m.end()));
            response.setMessage(message);
        }

        Matcher htmlBeginMatcher = Pattern.compile(HTML_CODE_BEGIN).matcher(str);
        Matcher htmlEndMatcher = Pattern.compile(HTML_CODE_END).matcher(str);

        Integer htmlBeginIndex = str.length();
        Integer htmlEndIndex;

        if (htmlBeginMatcher.find() && htmlEndMatcher.find()) {
            htmlBeginIndex = htmlBeginMatcher.start();
            htmlEndIndex = htmlEndMatcher.end();
            response.setHtmlCode(str.substring(htmlBeginIndex, htmlEndIndex));
        }


        p = Pattern.compile(RESPONSE_HEADER);
        m = p.matcher(str);
        while (m.find()) {
            int headerStartIndex = m.start();
            int headerEndIndex = m.end();
            if (headerEndIndex < htmlBeginIndex) {
                String header = String.valueOf(str.substring(headerStartIndex, headerEndIndex));
                Pattern headerPattern = Pattern.compile(RESPONSE_DELIMITER);
                Matcher headerMatcher = headerPattern.matcher(header);
                String headerKey = "";
                String headerValue = "";
                if (headerMatcher.find()) {
                    headerKey = header.substring(0, headerMatcher.start());
                    headerValue = header.substring(headerMatcher.end(), header.length());
                }
                response.addHeader(headerKey, headerValue);
            }
        }
        return response;
    }
}
