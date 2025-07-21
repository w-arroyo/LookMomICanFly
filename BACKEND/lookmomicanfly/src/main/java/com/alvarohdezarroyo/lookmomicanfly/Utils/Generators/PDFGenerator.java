package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.PDFGeneratorErrorException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import com.alvarohdezarroyo.lookmomicanfly.Models.TrackingNumber;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Component
public class PDFGenerator {

    public static String generateSalePDF(Sale sale, TrackingNumber trackingNumber, String shippingLabelPath) {
        final String pdfPath=AppConfig.getShippingLabelsPath()+File.separator+sale.getId()+".pdf";

        try (PDDocument pdfDocument = new PDDocument()) {
            PDPage page=new PDPage(PDRectangle.A4); // paper sheet size (A4)
            pdfDocument.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page)) {
                PDType0Font font=PDType0Font.load(pdfDocument,
                        new File("src/main/resources/static/fonts/EBGaramond-Regular.ttf")
                );

                contentStream.setFont(font,14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50,750);
                contentStream.showText("Sale #"+sale.getReference());
                contentStream.endText();

                contentStream.setStrokingColor(Color.BLACK);
                contentStream.setLineWidth(1f);
                contentStream.addRect(50,650,500,80);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(font,12);
                contentStream.newLineAtOffset(60,710);
                contentStream.showText("Product: "+sale.getAsk().getProduct().getName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Size: "+sale.getAsk().getSize().getValue());
                contentStream.newLineAtOffset(0,-20);
                contentStream.showText("Amount: "+sale.getAsk().getAmount());
                contentStream.endText();

                PDImageXObject shippingLabel = PDImageXObject.createFromFile(shippingLabelPath, pdfDocument);
                float imageWidth=shippingLabel.getWidth()*0.5f;
                float imageHeight=shippingLabel.getHeight()*0.5f;
                float xPos=(PDRectangle.A4.getWidth()-imageWidth)/2; // horizontally centered

                contentStream.drawImage(shippingLabel, xPos, 400, imageWidth, imageHeight);

                contentStream.beginText();
                contentStream.setFont(font,16);
                float textWidth=font.getStringWidth(trackingNumber.getCode())/1000*16;
                float textXPos=(PDRectangle.A4.getWidth()-textWidth)/2; // centered
                contentStream.newLineAtOffset(textXPos,380);
                contentStream.showText(trackingNumber.getCode());
                contentStream.endText();
            }
            pdfDocument.save(pdfPath);
            return pdfPath;
        } catch (IOException e) {
            throw new PDFGeneratorErrorException("Error generating PDF: " + e.getMessage());
        }
    }

}
