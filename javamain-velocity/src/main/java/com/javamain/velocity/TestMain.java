package com.javamain.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;
import java.util.Properties;

/**
 * @author yzhou
 * @date 2021/11/19
 */
public class TestMain {

    public static void main(String[] args) throws IOException {

        // Create a new Velocity Engine
        VelocityEngine velocityEngine = new VelocityEngine();        // Set properties that allow reading vm file from classpath.
        Properties p = new Properties();
        velocityEngine.setProperty("resource.loaders", "file,class,classpath");
        velocityEngine.setProperty("resource.loader.class.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("resource.loader.file.path", "classpath");
        try {
            velocityEngine.init(p);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /* lets make a Context and put data into it */
        VelocityContext context = new VelocityContext();
        context.put("k8snamespace", "Velocity");
        context.put("key", "Engine");

        Template template = null;
        try {
            template = velocityEngine.getTemplate("/templates/test.vm");
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String path = TestMain.class.getClassLoader().getResource("kafka_deployment_template.yaml")
                .getPath();
        System.out.println(path);
        FileOutputStream fos = new FileOutputStream(path);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(fos, "UTF-8"));

        if (template != null)
            template.merge(context, writer);

        writer.flush();
        writer.close();

        System.out.println("success");
    }
}
