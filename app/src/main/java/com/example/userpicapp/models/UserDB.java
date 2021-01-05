package com.example.userpicapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "UserDB")
public class UserDB extends Model {
    @Column(name = "uid")
    public String uid;

    @Column(name = "name")
    public String name;

    @Column(name = "email")
    public String email;

    @Column(name = "profile_pic")
    public String profile_pic;

    public void saveUser(String uid, String name, String email, String profile_pic){

        UserDB user  = new Select().from(UserDB.class).where("uid =  ?", uid).executeSingle();
        if(user == null) {
           user = new UserDB();
           user.uid = uid;
           user.name = name;
           user.email = email;
           user.profile_pic = profile_pic;
           user.save();
        }
    }

    public List<UserDB> getUserList(){
        return new Select().from(UserDB.class).execute();
    }

    public UserDB getUserById(String uid){
        return new Select().from(UserDB.class).where("uid = ?", uid).executeSingle();
    }

}
