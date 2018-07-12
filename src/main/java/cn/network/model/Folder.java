package cn.network.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Folder {
    @Id
    private int fdid;
    @Column
    private String fdname;
    @Column
    private Date fdcreatedate;
    @Column
    private int uid;
    @Column
    private int parentfdid;
    public Folder(){

    }
    public Folder(int fdid, String fdname, Date fdcreatedate, int uid, int parentfdid) {
        this.fdid = fdid;
        this.fdname = fdname;
        this.fdcreatedate = fdcreatedate;
        this.uid = uid;
        this.parentfdid = parentfdid;
    }

    public int getFdid() {
        return fdid;
    }

    public void setFdid(int fdid) {
        this.fdid = fdid;
    }

    public String getFdname() {
        return fdname;
    }

    public void setFdname(String fdname) {
        this.fdname = fdname;
    }

    public Date getFdcreatedate() {
        return fdcreatedate;
    }

    public void setFdcreatedate(Date fdcreatedate) {
        this.fdcreatedate = fdcreatedate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getParentfdid() {
        return parentfdid;
    }

    public void setParentfdid(int parentfdid) {
        this.parentfdid = parentfdid;
    }
}
