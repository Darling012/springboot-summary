package com.learn.mvc.excel.easypoi;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 工具类 -从HttpServletRequest中获取文件数据
 *
 */
@RestController
public class RequestUtil {

    /**
     * 从HttpServletRequest中获取所有的文件
     *
     * @param request
     * @return FileItem对象列表
     * @throws FileUploadException
     */
    @PostMapping("getFileItemList")
    public static List<FileItem> getFileItemList(HttpServletRequest request) throws FileUploadException {
        List<FileItem> fileItemFromRequestList = getDataFromRequest(request);
        if (fileItemFromRequestList != null && !fileItemFromRequestList.isEmpty()) {
            List<FileItem> fileItemList = new ArrayList<>();
            for (FileItem fileItem : fileItemFromRequestList) {
                if (!fileItem.isFormField()) { // 文件
                    fileItemList.add(fileItem);
                }
            }
            return fileItemList;
        }
        return null;
    }

    /**
     * 从request中获取FileItem对象列表
     *
     * @param request
     * @return fileItemFromRequestList
     * @throws FileUploadException
     */
    private static List<FileItem> getDataFromRequest(HttpServletRequest request) throws FileUploadException {
        String tempPath = request.getServletContext().getRealPath("/temp");
        File file = new File(tempPath);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024 * 1024);// 设置缓存大小
        factory.setRepository(file);// 默认情况下 临时文件不会自动删除
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        List<FileItem> fileItemFromRequestList = servletFileUpload.parseRequest(request);
        file.delete(); // 删除临时文件
        return fileItemFromRequestList;
    }
}
