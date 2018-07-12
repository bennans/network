package cn.network.repository;

import cn.network.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.font.FontRenderContext;
import java.util.List;

public interface FolderRepository extends JpaRepository<Folder,Integer> {
    List<Folder> findByUidAndParentfdid(int uid,int parentfdid);
    List<Folder> findByUidAndParentfdidAndFdname(int uid,int parentfdid,String fdname);
    Folder findByFdid(int fdid);
}
