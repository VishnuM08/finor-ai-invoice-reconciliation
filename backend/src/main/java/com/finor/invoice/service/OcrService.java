package com.finor.invoice.service;

import org.apache.pdfbox.Loader;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class OcrService {

    @Value("${tesseract.datapath}")
    private String datapath;

    @Value("${tesseract.language:eng}")
    private String language;

    public String extractTextFromFile(String filePath) throws Exception {
        File file = new File(filePath);

        if (filePath.toLowerCase().endsWith(".pdf")) {
            return extractTextFromPdf(file);
        } else {
            return extractTextFromImage(file);
        }
    }

    private String extractTextFromImage(File imageFile) throws Exception {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);

        return tesseract.doOCR(imageFile);
    }

    private String extractTextFromPdf(File pdfFile) throws Exception {
    	try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(document);

            // first page only (invoice usually is page 1)
            BufferedImage image = renderer.renderImageWithDPI(0, 300);

            File temp = new File("uploads/temp_invoice.png");
            ImageIO.write(image, "png", temp);

            return extractTextFromImage(temp);
        }
    }
}
