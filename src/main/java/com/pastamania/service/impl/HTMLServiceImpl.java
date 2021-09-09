package com.pastamania.service.impl;

import com.lowagie.text.DocumentException;
import com.pastamania.service.HTMLService;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Pasindu Lakmal
 */
@Service
public class HTMLServiceImpl implements HTMLService {
    @Override
    public ByteArrayOutputStream generatePdfOutputStreamFromHtml(String html) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
        return outputStream;
    }
}
