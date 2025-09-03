package com.iurac.recruit.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.iurac.recruit.entity.Job;
import com.iurac.recruit.entity.User;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.security.RedisCacheManager;
import com.iurac.recruit.service.UserService;
import com.iurac.recruit.util.ImageUtil;
import com.iurac.recruit.util.Result;
import com.iurac.recruit.util.SaltUtil;
import com.iurac.recruit.util.TableResult;
import com.iurac.recruit.vo.PageResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private UserService userService;

    @GetMapping({ "/", "/index" })
    public String toIndex(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (ObjectUtil.isNotNull(user)) {
            model.addAttribute("userInfo", user);
        }

        return "index";
    }

    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password, String code, HttpSession session) {
        String codes = (String) session.getAttribute("KAPTCHA_SESSION_KEY");
        if (StrUtil.hasBlank(codes)) {
            throw new RuntimeException("验证码已超时!");
        }

        String msg = "";
        try {
            if (codes.equalsIgnoreCase(code)) {
                // 获取主体对象
                Subject subject = SecurityUtils.getSubject();
                subject.login(new UsernamePasswordToken(username, password));
                return Result.succ("登录成功");
            } else {
                throw new RuntimeException("验证码错误!");
            }
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            msg = "用户名错误!";
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            msg = "密码错误!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "连接超时！";
        }
        return Result.fail(msg);
    }

    @GetMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(User user, @RequestParam("role") String role) throws Exception {
        user.setImg("default.png");
        userService.register(user, role);
        return Result.succ("操作成功");
    }

    @PostMapping("/user/update")
    @ResponseBody
    public Result updateUserInfo(@RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "imgFile", required = false) MultipartFile file) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        redisCacheManager.getCache("authenticationCacheName").remove(user);
        if (!StrUtil.hasBlank(phone)) {
            if (ReUtil.isMatch("(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}", phone)) {
                user.setPhone(phone);
            } else {
                return Result.fail("请输入正确的手机号");
            }
        }
        if (!StrUtil.hasBlank(email)) {
            if (ReUtil.isMatch("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", email)) {
                user.setEmail(email);
            } else {
                return Result.fail("请输入正确的邮箱地址");
            }
        }

        if (ObjectUtil.isNotNull(file) && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String imgName = user.getImg().equals("default.png")
                    ? ImageUtil.getFileName(originalFileName.substring(originalFileName.lastIndexOf(".")))
                    : user.getImg();
            user.setImg(imgName);
            ImageUtil.saveImage(file, user.getImg(), "userIcon");
        }

        if (!userService.updateById(user)) {
            return Result.fail("系统错误");
        }
        redisCacheManager.getCache("authenticationCacheName").put(user.getUsername(), user);
        return Result.succ("操作成功");
    }

    @PutMapping("/user/updatePassword/{password}")
    @ResponseBody
    public Result updateUserInfo(@PathVariable("password") String password) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        if (ReUtil.isMatch("[\\S]{6,12}", password)) {
            Md5Hash md5Hash = new Md5Hash(password, user.getSalt(), 1024);
            if (md5Hash.toHex().equals(user.getPassword())) {
                return Result.fail("请输入新密码");
            }
            redisCacheManager.getCache("authorizationCacheName").remove(user.toString());
            redisCacheManager.getCache("authenticationCacheName").remove(user);
            user.setPassword(md5Hash.toHex());
            System.out.println(user.getPassword());
        } else {
            return Result.fail("密码必须6到12位，且不能出现空格");
        }

        if (!userService.updateById(user)) {
            return Result.fail("系统错误");
        }
        redisCacheManager.getCache("authenticationCacheName").put(user.getUsername(), user);
        return Result.succ("操作成功");
    }

    @RequiresRoles("admin")
    @ResponseBody
    @GetMapping("/user/getByCondition")
    public TableResult<User> getByCondition(@RequestParam("page") Long page, @RequestParam("limit") Long limit,
            @RequestParam("username") String username, @RequestParam("role") String role,
            @RequestParam("startDate") String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam("locked") String locked) {
        PageResultVo<User> pageResultVo = userService.getByCondition(page, limit, username, role, locked, startDate,
                endDate);
        return new TableResult(0, "", pageResultVo.getTotal(), pageResultVo.getRecords());
    }

    @RequiresRoles("admin")
    @ResponseBody
    @PostMapping("/user/save")
    public Result saveUser(@RequestParam("username") String username, @RequestParam("role") String[] role,
            @RequestParam("password") String password,
            @RequestParam(value = "imgFile", required = false) MultipartFile file,
            @RequestParam("phone") String phone, @RequestParam("email") String email)
            throws IOException, ManageException {
        if (StrUtil.hasEmpty(username, password, phone, email) || role.length <= 0) {
            return Result.fail("请输入完整信息,避免输入空格");
        }
        if (!ReUtil.isMatch("[\\S]{6,12}", password)) {
            return Result.fail("密码必须6到12位，且不能出现空格");
        }
        if (!ReUtil.isMatch("(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}", phone)) {
            return Result.fail("请输入正确的手机号");
        }
        if (!ReUtil.isMatch("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", email)) {
            return Result.fail("请输入正确的邮箱地址");
        }

        User user = new User();
        user.setId(IdUtil.simpleUUID());
        user.setUsername(username);
        user.setCreateTime(DateUtil.now());
        user.setSalt(SaltUtil.getSalt(8));
        Md5Hash md5Hash = new Md5Hash(password, user.getSalt(), 1024);
        user.setPassword(md5Hash.toHex());
        user.setLocked("0");
        user.setPhone(phone);
        user.setEmail(email);

        if (ObjectUtil.isNotNull(file) && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            user.setImg(ImageUtil.getFileName(originalFileName.substring(originalFileName.lastIndexOf("."))));
            ImageUtil.saveImage(file, user.getImg(), "userIcon");
        } else {
            user.setImg("default.png");
        }

        userService.saveUser(user, role);
        return Result.succ("操作成功");
    }

    @RequiresRoles("admin")
    @ResponseBody
    @PostMapping("/user/update/{id}")
    public Result updateUser(@PathVariable("id") String id, @RequestParam("role") String[] role,
            @RequestParam("password") String password,
            @RequestParam(value = "imgFile", required = false) MultipartFile file,
            @RequestParam("phone") String phone, @RequestParam("email") String email)
            throws IOException, ManageException {
        if (StrUtil.hasEmpty(password, phone, email) || role.length <= 0) {
            return Result.fail("请输入完整信息,避免输入空格");
        }
        if (!ReUtil.isMatch("[\\S]{6,12}", password)) {
            return Result.fail("密码必须6到12位，且不能出现空格");
        }
        if (!ReUtil.isMatch("(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}", phone)) {
            return Result.fail("请输入正确的手机号");
        }
        if (!ReUtil.isMatch("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", email)) {
            return Result.fail("请输入正确的邮箱地址");
        }

        User user = userService.getById(id);
        redisCacheManager.getCache("authorizationCacheName").remove(user.toString());
        redisCacheManager.getCache("authenticationCacheName").remove(user);

        user.setSalt(SaltUtil.getSalt(8));
        Md5Hash md5Hash = new Md5Hash(password, user.getSalt(), 1024);
        user.setPassword(md5Hash.toHex());
        user.setPhone(phone);
        user.setEmail(email);

        if (ObjectUtil.isNotNull(file) && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            user.setImg(ImageUtil.getFileName(originalFileName.substring(originalFileName.lastIndexOf("."))));
            ImageUtil.saveImage(file, user.getImg(), "userIcon");
        }

        userService.updateUser(user, role);
        return Result.succ("操作成功");
    }

    @RequiresRoles("admin")
    @ResponseBody
    @PostMapping("/user/delete/{id}")
    public Result deleteUser(@PathVariable("id") String id) throws ManageException {
        userService.deleteUser(id);
        return Result.succ("操作成功");
    }

    @RequiresRoles("admin")
    @ResponseBody
    @PostMapping("/user/lock/{id}")
    public Result lockUser(@PathVariable("id") String id) throws ManageException {
        User user = userService.getById(id);
        user.setLocked("1".equals(user.getLocked()) ? "0" : "1");
        userService.updateById(user);
        return Result.succ("操作成功");
    }
}
