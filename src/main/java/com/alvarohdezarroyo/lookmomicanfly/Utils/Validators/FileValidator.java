package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class FileValidator {

    public static void checkIfADirectoryExistsAndCreateIt(String path){
        GlobalValidator.checkIfAFieldIsEmpty(path);
        final File directory=new File(path);
        if(!directory.exists() || !directory.isDirectory()){
            if(!directory.mkdir())
                throw new RuntimeException("Could not create the directory.");
        }
    }

    public static void removeShippingLabelDirectory(){
        final File shippingLabelFolder=new File(AppConfig.getShippingLabelsPath());
            if(shippingLabelFolder.exists() && shippingLabelFolder.isDirectory()){
                for(File file: Objects.requireNonNull(shippingLabelFolder.listFiles())){
                    if(!file.delete())
                        System.out.println("Unable to remove file: "+file.getAbsolutePath());
                }
            }
    }

}
