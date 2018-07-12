package cn.network.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ModifyFolder {
    @RequestMapping(value = "deletefolder")
    @ResponseBody
    public String deleteFolder(String folderId, HttpSession session){
        //递归先找出要删除目录下的目录
        //
        return "dfs";
    }
}
