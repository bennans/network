package cn.network.controller;

import cn.network.model.Filetype;
import cn.network.model.Folder;
import cn.network.repository.FileRepository;
import cn.network.repository.FiletypeRepository;
import cn.network.repository.FolderRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.List;

@Controller
public class UploadController {
    @Autowired
    private FiletypeRepository filetypeRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FolderRepository folderRepository;
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("pid") String pid,
                      @RequestParam("filename") String filename, HttpSession session) {
        Integer userId = 1;
        String userName="luolei";
        //判断文件是否空
        if (file.isEmpty()) {
            System.out.println("null");
            return "0";
        }
        //获取文件类型
        String nameAndType[] = filename.split("\\.");
        String typeString = nameAndType[1];
        int typeid = -1;//类型id
        String typename ="";//类型图片名

        //获得类型列表
        List<Filetype> filetypeList = filetypeRepository.findAll();

        for(int i = 0;i<filetypeList.size();i++){
            String typeNoSplit = filetypeList.get(i).getTypecontent();
            String typeList [] = typeNoSplit.split("\\-");
            for(String x : typeList){
                if(x.equals(typeString)){
                    typeid = filetypeList.get(i).getFtid();
                    typename = filetypeList.get(i).getTypename();
                    break;
                }
            }
        }
        if(typeid==-1){
            return "1";
        }
        //取得文件名
        String fileName = filename;
        //取得文件大小
        int size = (int) file.getSize();
        //用户目录
        String userpath = userName+"-";
        //获得父目录
        System.out.println(pid);
        int parentFdId = Integer.parseInt(pid);
        while(parentFdId != 0){
            Folder folder =   folderRepository.findByFdid(parentFdId);
            parentFdId =  folder.getParentfdid();
            userpath = userpath +folder.getFdname()+"-";
        }
        System.out.println(userpath);
        //上传文件目录
        String path = "E:\\ideaWeb\\network\\target\\classes\\static\\upload\\";
        //创建文件夹
        String userPathArr[] = userpath.split("\\-");
        for(String x: userPathArr){
            path = path +x+"\\";
            File files =new File(path);
            if  (!files .exists()  && !files .isDirectory())
            {
                System.out.println("创建");
                files .mkdir();
            }
        }
        File dest = new File(path  + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            //文件id
            int fileId=0;
            List<cn.network.model.File> fileList = fileRepository.findByPath(path+fileName);
            int addhtml = 0;
            if(fileList.size()==0){
                cn.network.model.File savefile = new cn.network.model.File();
                savefile.setFdid(Integer.parseInt(pid));
                savefile.setFname(fileName);
                Date date = new Date();
                savefile.setFuploadtime(date);
                savefile.setUid(userId);
                savefile.setFtid(typeid);
                savefile.setFsize(size);
                savefile.setPath(path  + fileName);
                fileRepository.save(savefile);
                fileList = fileRepository.findByPath(path+fileName);
                fileId = fileList.get(0).getFid();
                addhtml = 1;
            }
            file.transferTo(dest); //保存文件
            return typename+"-"+fileId+"-"+addhtml;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "2";
        }

    }

    @RequestMapping(value = "downloadfile")
    @ResponseBody
    public String  downloadFile(@RequestParam("fileid") String fileid, HttpServletResponse res) {
        // Get your file stream from wherever.
        Integer userId = 1;
        System.out.println(fileid);
        cn.network.model.File file = fileRepository.findByUidAndFid(userId,Integer.parseInt(fileid));
        String fullPath = file.getPath();
        String fileName = file.getFname();
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        try{
            res.setHeader("Content-Disposition", "attachment;fileName=" +new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
        }catch (IOException e){

        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(fullPath)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
        return "succe";
    }
}
