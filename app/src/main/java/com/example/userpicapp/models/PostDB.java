package com.example.userpicapp.models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "PostDB")
public class PostDB extends Model {

    @Column(name = "idPost")
    public int idPost;

    @Column(name = "date")
    public String date;

    @Column(name = "uid")
    public String uid;

    public void savePost(int idPost, String date, String uid){

        PostDB postT  = new Select().from(PostDB.class).where("idPost =  ?", idPost).executeSingle();
        if(postT == null) {
            postT = new PostDB();
            postT.idPost = idPost;
            postT.date = date;
            postT.uid = uid;
            postT.save();
        }
    }

    public List<PostDB> getPostList(){
        return new Select().from(PostDB.class).execute();
    }

}
