/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pesegato.MonkeySheet;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Pesegato
 */
public class MSGlobals {

     static Logger log = LoggerFactory.getLogger(MSGlobals.class);

    public static final int SPRITE_SIZE=256;

    public static final int MS_WIDTH_480P = 720;
    public static final int MS_HEIGHT_480P = 480;

    public static final int MS_WIDTH_576P = 720;
    public static final int MS_HEIGHT_576P = 576;

    public static final int MS_WIDTH_720P = 1280;
    public static final int MS_HEIGHT_720P = 720;

    public static final int MS_WIDTH_1080P = 1920;
    public static final int MS_HEIGHT_1080P = 1080;

    public static int MS_WIDTH;
    public static int MS_HEIGHT;

    public static void setResolution(String res) {
        switch (res) {
            case "480p":
                MS_WIDTH = MS_WIDTH_480P;
                MS_HEIGHT = MS_HEIGHT_480P;
                break;
            case "576p":
                MS_WIDTH = MS_WIDTH_576P;
                MS_HEIGHT = MS_HEIGHT_576P;
                break;
            case "720p":
                MS_WIDTH = MS_WIDTH_720P;
                MS_HEIGHT = MS_HEIGHT_720P;
                break;
            case "1080p":
                MS_WIDTH = MS_WIDTH_1080P;
                MS_HEIGHT = MS_HEIGHT_1080P;
                break;
            default:
                log.error("Resolution unsupported: {}", res);
                System.exit(1);
        }
        log.info("Resolution set to: {}",res);
    }

    /*
    per quando ci sarà gradle...
    */

    protected void logBuildInfo() {
        try {
            java.net.URL u = Resources.getResource("monkeysheet.build.date");
            String build = Resources.toString(u, Charsets.UTF_8);
            log.info("MonkeySheett build date:" + build);
        } catch( java.io.IOException e ) {
            log.error( "Error reading build info", e );
        }
    }
}
