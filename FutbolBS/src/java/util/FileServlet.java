/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controller.DocumentController;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "FileServlet", urlPatterns = {"/file/*"})
public class FileServlet extends HttpServlet {

    @Inject
    private DocumentController dc;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String file = request.getPathInfo();
        File f = new File(dc.getUploadTo() + file);

        Files.copy(f.toPath(), response.getOutputStream()); // xhtml sayfamıza outputStream ile dosyalarımızı yüklüyoruz.
    }

}
