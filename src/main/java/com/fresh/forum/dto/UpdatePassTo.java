package com.fresh.forum.dto;

/**
 * @author guowenyu
 * @date 2020/6/1
 */
public class UpdatePassTo {

    private Integer id;
    private String oldPass;
    private String newPass;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
