package com.pastamania.service;

import com.lowagie.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Pasindu Lakmal
 */
public interface HTMLService {


    ByteArrayOutputStream generatePdfOutputStreamFromHtml(String html) throws IOException, DocumentException ;
}
