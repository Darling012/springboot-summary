package com.learn.mvc.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * 那是因为流对应的是数据，数据放在内存中，有的是部分放在内存中。read 一次标记一次当前位置（mark position），第二次read就从标记位置继续读（从内存中copy）数据。 所以这就是为什么读了一次第二次是空了。 怎么让它不为空呢？只要inputstream 中的pos 变成0就可以重写读取当前内存中的数据。javaAPI中有一个方法public void reset() 这个方法就是可以重置pos为起始位置，但是不是所有的IO读取流都可以调用该方法！ServletInputStream是不能调用reset方法，这就导致了只能调用一次getInputStream()
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private final String body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // StringBuilder stringBuilder = new StringBuilder();
        // BufferedReader bufferedReader = null;
        // InputStream inputStream = null;
        // try {
        //     inputStream = request.getInputStream();
        //     if (inputStream != null) {
        //         bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //         char[] charBuffer = new char[128];
        //         int bytesRead = -1;
        //         while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
        //             stringBuilder.append(charBuffer, 0, bytesRead);
        //         }
        //     } else {
        //         stringBuilder.append("");
        //     }
        // } catch (IOException ex) {
        //
        // } finally {
        //     if (inputStream != null) {
        //         try {
        //             inputStream.close();
        //         }
        //         catch (IOException e) {
        //             e.printStackTrace();
        //         }
        //     }
        //     if (bufferedReader != null) {
        //         try {
        //             bufferedReader.close();
        //         }
        //         catch (IOException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }
        // body = stringBuilder.toString();
        body = new BufferedReader(new InputStreamReader(request.getInputStream()))
                .lines().collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
            }
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }

}
