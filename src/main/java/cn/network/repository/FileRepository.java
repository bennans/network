package cn.network.repository;

import cn.network.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Integer>{
    List<File> findByUidAndFdid(int uid,int fdid);
    List<File> findByPath(String Path);
    File findByUidAndFid(int uid,int fid);
}
