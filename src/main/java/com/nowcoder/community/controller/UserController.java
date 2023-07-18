package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.FollowService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.OSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Autowired
    private OSSUtil ossUtil;

    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

    //
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){
        if (headerImage == null){
            model.addAttribute("error", "您还没有选择图片!");
            return "/site/setting";
        }
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error", "文件格式不正确!");
            return "/site/setting";
        }
        // 生成随机文件名
        filename = CommunityUtil.generateUUID() + suffix;
        // 确定文件存放路径
        File dest = new File(uploadPath + "/" + filename);
        // 存入本地
        try {
            // 将文件存入指定位置
            headerImage.transferTo(dest);
        }catch (IOException e) {
            logger.error("上传文件失败：" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常!", e);
        }
        // 上传到阿里云OSS
        String headerUrl = ossUtil.uploadFile(filename, dest);
        System.out.println(headerUrl);
        if (headerUrl == null) {
            logger.error("文件上传至云服务器失败！");
            throw new RuntimeException("文件上传至云服务器失败！");
        }
        // 更新当前用户头像的路径
        // http://localhost:8088/community/user/header/xxx.png
        User user = hostHolder.getUser();
//        String headerUrl = domain + contextPath + "/user/header/" + filename;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

//    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
//    public String changePassword(Model model, String oldPassword, String newPassword, String checkNewPassword){
//        if (oldPassword == null){
//            model.addAttribute("oldPasswordError", "原始密码不能为空!");
//            return "/site/setting";
//        }
//        User user = hostHolder.getUser();
//        if (!user.getPassword().equals(CommunityUtil.md5(oldPassword + user.getSalt()))){
//            model.addAttribute("oldPasswordError", "原始密码不正确!");
//            return "/site/setting";
//        }
//        if (newPassword == null){
//            model.addAttribute("newPasswordError", "新密码不能为空!");
//            return "/site/setting";
//        }
//        if (!newPassword.equals(checkNewPassword)){
//            model.addAttribute("checkNewPasswordError", "两次输入的密码不相同!");
//            return "/site/setting";
//        }
//        newPassword = CommunityUtil.md5(newPassword + user.getSalt());
//        userService.updatePassword(user.getId(), newPassword);
//        return "redirect:/logout";
//    }

    // 废弃
    // 获取头像。http://localhost:8088/community/user/header/xxx.xx，需要写一个方法用于处理该请求
    @RequestMapping(path = "/header/{filename}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response){
        // 服务器存放路径
        filename = uploadPath + "/" + filename;
        // 文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/" + suffix);

        try(
            FileInputStream fis = new FileInputStream(filename);
            OutputStream os = response.getOutputStream();
                )
        {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b=fis.read(buffer)) != -1){
                os.write(buffer,0, b);
            }
        }catch (IOException e){
            logger.error("读取头像失败：" + e.getMessage());
        }
    }

    // 个人主页
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model){
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }

        // 用户
        model.addAttribute("user", user);
        // 点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        // 关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        // 粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);
        // 是否已关注
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);

        return "/site/profile";
    }
}
