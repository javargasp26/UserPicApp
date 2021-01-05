package com.example.userpicapp.models;

import com.activeandroid.query.Delete;
import com.example.userpicapp.util.ImageDownloader;

public class ClearDB {
    public ClearDB(){
        //borra la base de datos y la carpeta de las imagenes
        new Delete().from(UserDB.class).execute();
        new Delete().from(PostDB.class).execute();
        new Delete().from(PicDB.class).execute();

        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.deletePicFolder();
    }

}
