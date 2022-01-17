package com.learn.mvc.body;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.mvc.body.pojo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Darling
 */
public class StringToResultHttpMessageConverter extends AbstractHttpMessageConverter<String> {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private ObjectMapper mapper;

    public StringToResultHttpMessageConverter() {
        this(DEFAULT_CHARSET);
    }

    public StringToResultHttpMessageConverter(Charset charset) {
        super(charset, MediaType.APPLICATION_JSON_UTF8, MediaType.ALL);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return String.class == clazz;
    }

    @Override
    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        return StreamUtils.copyToString(inputMessage.getBody(), charset);
    }

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
        StreamUtils.copy(mapper.writeValueAsString(RespResult.success(s)), charset, outputMessage.getBody());
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        } else {
            return getDefaultCharset();
        }
    }

    @Autowired
    public StringToResultHttpMessageConverter setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
        return this;
    }
}
