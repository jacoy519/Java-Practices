package com.demo.resource;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Semaphore;

@Path("demo")
public class DemoResource {

    @Path("hello-world")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "hello world";
    }

    @Path("single-file-upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String singleFileUpload(@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {
        String fileName = contentDispositionHeader.getFileName();
        return "single file upload success: " + fileName;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/multi-file-upload")
    public String multiFileUpload(FormDataMultiPart form, @Context HttpServletRequest request,
                                  @Context HttpServletResponse response) throws IOException {

        List<FormDataBodyPart> l= form.getFields("file");
        String fileNames = "";
        for (FormDataBodyPart p : l) {
            String currentFileName = p.getFormDataContentDisposition().getFileName();
            fileNames = fileNames + currentFileName + " ";
            System.out.println(currentFileName);
        }
        return "multi file upload success: " + fileNames;
    }

}

