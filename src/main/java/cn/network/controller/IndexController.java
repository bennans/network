package cn.network.controller;

import cn.network.model.File;
import cn.network.model.Folder;
import cn.network.repository.FileRepository;
import cn.network.repository.FolderRepository;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    private Integer userId;

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private FileRepository fileRepository;
    @RequestMapping(value = "toindex")
    public String toIndex(HttpSession session, Model model, @RequestParam("parentfdid") int parentfdid){
       /* userId = (Integer)session.getAttribute("USERID");

        String userName = (String)session.getAttribute("USERNAME");*/
        userId = 1;
        String userName = "罗磊";
        List<Folder> folderList = folderRepository.findByUidAndParentfdid(userId,parentfdid);
        List<File> fileList = fileRepository.findByUidAndFdid(userId,parentfdid);


        model.addAttribute("folderlist",folderList);
        model.addAttribute("filelist",fileList);
        model.addAttribute("parentfdid",parentfdid);

        model.addAttribute("USERNAME",userName);

        return "index";
    }

    @RequestMapping(value = "createfolder")
    @ResponseBody
    public String creategolder(String foldername,String parentfdid,HttpSession session){

        /*userId = (Integer)session.getAttribute("USERID");*/
        userId = 1;
        List<Folder> folderList = folderRepository.findByUidAndParentfdidAndFdname(userId,Integer.parseInt(parentfdid),foldername);
        if(folderList.size()>0){
            return "0";//文件夹已存在
        }else {
            Folder createFolder = new Folder();
            createFolder.setFdname(foldername);
            Date date = new Date();
            createFolder.setFdcreatedate(date);
            createFolder.setParentfdid(Integer.parseInt(parentfdid));
            createFolder.setUid(userId);
            folderRepository.save(createFolder);
            System.out.println(date+foldername+parentfdid);
        }
        folderList = folderRepository.findByUidAndParentfdidAndFdname(userId,Integer.parseInt(parentfdid),foldername);
        int maxid = folderList.get(0).getFdid();
        System.out.println(maxid);
        return ""+maxid;//添加成功
    }

}
