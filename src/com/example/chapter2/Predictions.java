package com.example.chapter2;

import javax.servlet.ServletContext;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author
 * @Date 2018/7/26 18:11
 * @package_name com.example.chapter2
 * @user Administrator
 */
public class Predictions {
    private ConcurrentHashMap<Integer, Prediction> predictions;
    private ServletContext sctx;
    private AtomicInteger mapKey;

    public Predictions() {
        predictions = new ConcurrentHashMap<>();
        mapKey = new AtomicInteger();
    }

    public ServletContext getServletContext() {
        return sctx;
    }

    public void setServletContext(ServletContext sctx) {
        this.sctx = sctx;
    }

    public void setMap(ConcurrentHashMap<String, Prediction> predictions) {
        // no-op for now
    }

    public ConcurrentHashMap<Integer, Prediction> getMap() {
        // Has the ServletContext been set?
        if (getServletContext() == null) {
            return null;
        }
        // Has the data been read already?
        if (predictions.size() < 1) {
            populate();
        }
        return this.predictions;
    }

    public int addPrediction(Prediction p) {
        int id = mapKey.incrementAndGet();
        p.setId(id);
        predictions.put(id, p);
        return id;
    }

    private void populate() {
        String filename = "/WEB-INF/data/predictions.db";
        InputStream in = sctx.getResourceAsStream(filename);
        // Read the data into the arra of Predictions.
        if (in != null) {
            try {
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(isr);
                int i = 0;
                String record = null;
                while ((record = reader.readLine()) != null) {
                    String[] parts = record.split("!");
                    Prediction p = new Prediction();
                    p.setWho(parts[0]);
                    p.setWhat(parts[1]);
                    addPrediction(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String toXML(Object obj) {
        String xml = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(out);
            encoder.close();
            xml = out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }
}
