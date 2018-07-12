package cn.network.controller;

import cn.network.model.User;
import cn.network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    private String userName;
    private int userId;
    @Autowired
    private UserRepository userRepository;

    //跳转登录页
    @RequestMapping(value = "tologin")
    public String toLoginHtml(){
        return "login";
    }

    //进行登录验证
    @RequestMapping(value = "login")
    @ResponseBody
    public int login(String email, String pwd,String code,HttpSession session){
        String seesioncode = (String)session.getAttribute("CODE");
        if(!seesioncode.equals(code)){
            return 0;
        }
        List<User> suser = userRepository.findByEmailAndPwd(email,pwd);
        if(suser.size() == 0){
            return 1;
        }else{
            userName=suser.get(0).getName();
            userId=suser.get(0).getUid();
            session.setAttribute("USERNAME",userName);
            session.setAttribute("USERID",userId);
        }
        return 2;
    }
}
