package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.ShippingLabelGeneratorErrorException;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.FileValidator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class ShippingLabelGenerator {

    public static String generateShippingLabel(String trackingNumber) {
        FileValidator.checkIfADirectoryExistsAndCreateIt(AppConfig.getShippingLabelsPath());
        try {
            Map<EncodeHintType, Object> hints =new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 2); // margin around code
            final BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    trackingNumber,
                    BarcodeFormat.CODE_128,
                    600,
                    200,
                    hints
            );
            final String filePath=AppConfig.getShippingLabelsPath()+File.separator+trackingNumber+".png";
            final Path path=Paths.get(filePath);
            // this just for improving image quality
            MatrixToImageConfig config=new MatrixToImageConfig(MatrixToImageConfig.BLACK,MatrixToImageConfig.WHITE);
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path,config);
            return filePath;
        } catch(IOException | WriterException ex) {
            throw new ShippingLabelGeneratorErrorException("Error generating shipping label: " + ex.getMessage());
        }
    }

}
