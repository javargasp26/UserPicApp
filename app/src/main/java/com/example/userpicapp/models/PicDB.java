package com.example.userpicapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "PicDB")

public class PicDB extends Model {
    @Column(name = "idPost")
    public int idPost;

    @Column(name = "pic")
    public String pic;

    public void savePic(int idPost, String pic_link){

        PicDB pic  = new Select().from(PicDB.class).where("(idPost =  ? and pic = ?)", idPost, pic_link).executeSingle();
        if(pic == null) {
            pic = new PicDB();
            pic.idPost = idPost;
            pic.pic = pic_link;
            pic.save();
        }
    }

    public List<PicDB> getPicList(){
        return new Select().from(PicDB.class).execute();
    }

    public List<PicDB> getPicListByPostId(int idPost){
        return new Select().from(PicDB.class).where("idPost = ?", idPost).execute();
    }

}
