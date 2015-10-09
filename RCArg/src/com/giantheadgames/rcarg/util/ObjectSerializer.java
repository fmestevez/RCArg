package com.giantheadgames.rcarg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer<E> {
    
    private E obj;
    private String path;
    
    public ObjectSerializer(String path) {
        super();
        this.path = path;
    }

    public void write(E obj){
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public E read(){
        
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(path));
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = (E) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            return null;
        }
        
        return obj;   
    }

}
