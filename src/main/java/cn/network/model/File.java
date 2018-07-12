package cn.network.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class File {
    @Id
    private int fid;
    @Column
    private String fname;
    @Column
    private Date fuploadtime;
    @Column
    private String path;
    @Column
    private int ftid;
    @Column
    private int fdid;
    @Column
    private int uid;
    @Column
    private int fsize;
    public File(){

    }

    public File(int fid, String fname, Date fuploadtime, String path, int ftid, int fdid, int uid, int fsize) {
        this.fid = fid;
        this.fname = fname;
        this.fuploadtime = fuploadtime;
        this.path = path;
        this.ftid = ftid;
        this.fdid = fdid;
        this.uid = uid;
        this.fsize = fsize;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Date getFuploadtime() {
        return fuploadtime;
    }

    public void setFuploadtime(Date fuploadtime) {
        this.fuploadtime = fuploadtime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getFtid() {
        return ftid;
    }

    public void setFtid(int ftid) {
        this.ftid = ftid;
    }

    public int getFdid() {
        return fdid;
    }

    public void setFdid(int fdid) {
        this.fdid = fdid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFsize() {
        return fsize;
    }

    public void setFsize(int fsize) {
        this.fsize = fsize;
    }
}
