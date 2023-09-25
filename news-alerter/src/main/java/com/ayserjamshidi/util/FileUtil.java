package com.ayserjamshidi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();

    @Autowired
    private static ResourceLoader resourceLoader;

//    @Autowired
//    public FileUtil(ResourceLoader resourceLoader) {
//        FileUtil.resourceLoader = resourceLoader;
//    }

    public static File getFile(String fileName) {
        Resource resource = null;
        File output = null;

        if (DEBUG) {
            resource = resourceLoader.getResource("classpath:" + fileName);
        } else {
            resource = resourceLoader.getResource("file:" + fileName);
        }



//        File output = null;
//        URI preOutputt = null;
//        if (DEBUG) {
//            ClassPathResource preOutput = new ClassPathResource("./" + fileName);
//
//            if (preOutput.exists()) {
//                try {
//                    output = preOutput.getFile();
//                } catch (IOException e) {
//                    LOG.error("An exception occurred while attempting to acquire the file '{}'", fileName, e);
//                }
//            }
//        } else {
//            try {
//                preOutputt = new URI("file:" + fileName);
//                output = new File(preOutputt);
//            } catch (URISyntaxException e) {
//                LOG.error("An error occurred for file '{}'", preOutputt, e);
//            }
//        }

//        try {
//            output = DEBUG ? new ClassPathResource("./" + fileName).getFile() : new File(new URI("file:" + fileName));
//        } catch (Exception e) { // The file does not exist
//            return null;
//            //LOG.info("An exception occurred while attempting to acquire the file '{}'", fileName, e);
//        }

        if (resource.isFile()) {
            try {
                output = resource.getFile();
            } catch (IOException e) {
                LOG.error("An exception occurred while trying to get the resource '{}'", output);
            }
        }
        return output;
    }

    public static File getConfigFile(String fileName) {
        return getFile("config/" + fileName);
    }
}
