package com.mdd.admin.controller;

import com.mdd.admin.LikeAdminThreadLocal;
import com.mdd.admin.config.aop.Log;
import com.mdd.admin.config.aop.RequestType;
import com.mdd.admin.service.IAlbumsService;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.enums.AlbumEnum;
import com.mdd.common.exception.OperateException;
import com.mdd.common.plugin.storage.StorageDriver;
import com.mdd.common.plugin.storage.UploadFilesVo;
import com.mdd.common.util.MessageUtils;
import com.mdd.common.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 上传管理
 */
@RestController
@RequestMapping("api/upload")
public class UploadController {

    @Resource
    IAlbumsService iAlbumsService;

    /**
     * 上传图片
     *
     * @author fzr
     * @param request 请求对象
     * @return AjaxResult<UploadFilesVo>
     */
    @Log(title = "上传图片", requestType = RequestType.File)
    @PostMapping("/image")
    public AjaxResult<UploadFilesVo> image(HttpServletRequest request) {
        MultipartFile multipartFile;
        try {
            multipartFile = ((MultipartRequest) request).getFile("file");
        } catch (Exception e) {
            throw new OperateException(MessageUtils.message("please.select.the.correct.upload.image"));
        }

        if (multipartFile == null) {
            throw new OperateException(MessageUtils.message("please.select.upload.image"));
        }

        StorageDriver storageDriver = new StorageDriver();
        UploadFilesVo vo = storageDriver.upload(multipartFile, "image", AlbumEnum.IMAGE.getCode());
        String cid = StringUtils.isNotEmpty(request.getParameter("cid")) ? request.getParameter("cid") : "0";

        Map<String, String> album = new LinkedHashMap<>();
        album.put("aid", String.valueOf(LikeAdminThreadLocal.getAdminId()));
        album.put("cid", cid);
        album.put("type", String.valueOf(AlbumEnum.IMAGE.getCode()));
        album.put("size", vo.getSize().toString());
        album.put("ext", vo.getExt());
        album.put("url", vo.getUrl());
        album.put("name", vo.getName());
        Integer id = iAlbumsService.albumAdd(album);

        vo.setId(id);
        return AjaxResult.success(vo);
    }

    /**
     * 上传视频
     *
     * @author fzr
     * @param request 请求对象
     * @return AjaxResult<UploadFilesVo>
     */
    @Log(title = "上传视频", requestType = RequestType.File)
    @PostMapping("/video")
    public AjaxResult<UploadFilesVo> video(HttpServletRequest request) {
        MultipartFile multipartFile;
        try {
            multipartFile = ((MultipartRequest) request).getFile("file");
        } catch (Exception e) {
            throw new OperateException(MessageUtils.message("Please.upload.the.video"));
        }

        if (multipartFile == null) {
            throw new OperateException(MessageUtils.message("Please.upload.the.video"));
        }

        StorageDriver storageDriver = new StorageDriver();
        UploadFilesVo vo = storageDriver.upload(multipartFile, "video", AlbumEnum.Video.getCode());
        String cid = StringUtils.isNotEmpty(request.getParameter("cid")) ? request.getParameter("cid") : "0";

        Map<String, String> album = new LinkedHashMap<>();
        album.put("cid", cid);
        album.put("aid", String.valueOf(LikeAdminThreadLocal.getAdminId()));
        album.put("type", String.valueOf(AlbumEnum.Video.getCode()));
        album.put("ext", vo.getExt());
        album.put("size", vo.getSize().toString());
        album.put("url", vo.getUrl());
        album.put("name", vo.getName());
        Integer id = iAlbumsService.albumAdd(album);

        vo.setId(id);
        return AjaxResult.success(vo);
    }

}
