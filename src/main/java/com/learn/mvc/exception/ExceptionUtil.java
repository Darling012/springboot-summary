package com.learn.mvc.exception;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Darling
 * https://blog.csdn.net/flw8840488/article/details/106625487*
 * https://zhuanlan.zhihu.com/p/339448154
 * https://blog.51cto.com/u_15437298/4694395
 */
@Slf4j
public class ExceptionUtil {

    private ExceptionUtil() {
    }

    /**
     * 打印异常信息
     */
    public static String getMessage(Exception e) {
        String swStr = null;
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            swStr = sw.toString();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return swStr;
    }
}
