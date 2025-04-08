package com.nebula.Http.HttpRequest.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.nebula.Http.HttpRequest.Exceptions.HttpBodyParserException;
import com.nebula.Http.HttpRequest.Exceptions.HttpHeadersParserException;
import com.nebula.Http.HttpRequest.Exceptions.HttpParserException;
import com.nebula.Http.HttpRequest.Exceptions.RequestLineParserException;
import com.nebula.Logger.NebulaLogger;
import com.nebula.Logger.NebulaLoggerFactory;
import com.nebula.Http.Constants.HttpMethod;
import com.nebula.Http.Constants.HttpVersion;
import com.nebula.Http.HttpRequest.Request;

public class HttpParser {
    private final int CR = 13; // \r
    private final int LF = 10; // \n
    private final int SP = 32; // space
    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(HttpParser.class);

    public Request parseHttpRequest(InputStream requestInputStream) {
        Request request = new Request();
        try {
            parseHttpRequestLine(requestInputStream, request);
            parseHttpHeaders(requestInputStream, request);
            String length = request.getHeader("content-length");
            if(length != null){
                int contentLength = Integer.parseInt(length);
                if(contentLength > 0) {
                     parseHttpBody(requestInputStream, request, contentLength);
                }
            }
        } catch (HttpParserException httpParserException) {
            logger.severe(httpParserException.getMessage());
        } catch (IOException ioException) {
            logger.severe(ioException.getMessage());
        }
        return request;
    }

    private void parseHttpRequestLine(InputStream requestInputStream, Request request)
            throws IOException, RequestLineParserException {
        int _byte;
        StringBuilder stringBuilder = new StringBuilder();
        List<String> parts = new ArrayList<>();
        while ((_byte = requestInputStream.read()) != -1) {
            if (_byte == SP) {
                parts.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            } else if (_byte != CR && _byte != LF) {
                stringBuilder.append((char) _byte);
            } else {
                if (_byte == CR) {
                    _byte = requestInputStream.read();
                    boolean b = _byte == LF;
                    if (b) {
                        parts.add(stringBuilder.toString());
                        break;
                    }
                }
            }
        }
        if (parts.size() != 3) {
            throw new RequestLineParserException("Invalid Request Line");
        }
        resolveHttpVersion(parts.getLast(), request);
        request.setUrl(parts.get(1));
        resolveHttpMethod(parts.getFirst(), request);
    }

    private void parseHttpHeaders(InputStream requestInputStream, Request request)
            throws HttpHeadersParserException, IOException {
        int _byte;
        String key, value;
        StringBuilder buffer = new StringBuilder();
        boolean crlf = false;
        while ((_byte = requestInputStream.read()) >= 0) {
            if (_byte == CR) {
                _byte = requestInputStream.read();
                if (_byte == LF) {
                    if (crlf) {
                        return;
                    }
                    crlf = true;
                    String header = buffer.toString();
                    String[] parts = header.split(": ", 2);
                    if (parts.length < 2) {
                        throw new HttpHeadersParserException("Invalid Header");
                    }
                    key = parts[0];
                    value = parts[1];
                    request.putInHeaders(key, value);
                    buffer = new StringBuilder();
                }
            } else {
                crlf = false;
                buffer.append((char) _byte);
            }
        }
    }

    private void parseHttpBody(InputStream requestInputStream, Request request, int contentLength)
            throws HttpBodyParserException, IOException {
        List<Byte> bytes = new ArrayList<>();
        byte _int = 0;
        while ((_int = (byte) requestInputStream.read()) != -1) {
            if (_int == CR) {
                continue;
            }

            bytes.add(_int);
        }
        if (bytes.size() != contentLength) {
            throw new HttpBodyParserException("Invalid Content Length");
        }
        byte[] byteArr = new byte[bytes.size()];
        toPrimitveByteArray(bytes, byteArr);
        String resultantBody = new String(byteArr);
        request.setBody(resultantBody);
    }

    private void resolveHttpVersion(String version, Request request) throws RequestLineParserException {
        HttpVersion httpVersion;
        if (version.equals(HttpVersion.V1.getVersion())) {
            httpVersion = HttpVersion.V1;
        } else if (version.equals(HttpVersion.V2.getVersion())) {
            httpVersion = HttpVersion.V2;
        } else {
            throw new RequestLineParserException("Invalid Protocol Version");
        }
        request.setHttpVersion(httpVersion);
    }

    private void resolveHttpMethod(String method, Request request) throws RequestLineParserException {
        switch (method) {
            case "GET":
                request.setMethod(HttpMethod.GET);
                break;
            case "POST":
                request.setMethod(HttpMethod.POST);
                break;
            case "DELETE":
                request.setMethod(HttpMethod.DELETE);
                break;
            case "UPDATE":
                request.setMethod(HttpMethod.UPDATE);
                break;
            case "PATCH":
                request.setMethod(HttpMethod.PATCH);
                break;
            case "HEAD":
                request.setMethod(HttpMethod.HEAD);
                break;
            case "PUT":
                request.setMethod(HttpMethod.PUT);
                break;
            default:
                throw new RequestLineParserException("Invalid Method , Received : " + method);
        }
    }

    private void toPrimitveByteArray(List<Byte> bytes , byte[] bytePrimitive) {
        if(bytes.size() == bytePrimitive.length) {
            int itr= 0;
            for(Byte _byte : bytes) {
                bytePrimitive[itr++] = _byte;
            }
        }
    }

}
