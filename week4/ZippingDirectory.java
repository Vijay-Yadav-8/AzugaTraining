/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week4;

import com.azuga.training.week3.CarbonIntensity;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * This class is used to make zip and unzip files from given file
 */
public class ZippingDirectory {
    private static final Logger logger = LogManager.getLogger(CarbonIntensity.class);

    /**
     * It is used to unzip the file and save to some other directory
     * @param targetZipFilePath zip path which needed to be unzipped
     * @param destinationFolderPath path to save the unzipped files
     * @param password used if it has password set to access it.
     */
    public void unZip(String targetZipFilePath, String destinationFolderPath,String password) {
        long start = System.currentTimeMillis();
        try (ZipFile zipFile = new ZipFile(targetZipFilePath)){
            logger.info("Searching zip file");
            if (zipFile.isEncrypted()) {
                logger.info("File Encrypted, enter password");
                zipFile.setPassword(password.toCharArray());
                logger.info("File opened with password");
            }

            zipFile.extractAll(destinationFolderPath);
            logger.info("Unzip at location {}",destinationFolderPath);

        } catch (IOException e) {
            logger.error("{} occurred while reading file from {}", e, destinationFolderPath);
            long end = System.currentTimeMillis();
            logger.info("hello sendEmail() is executed in {} ms", (end - start));
        }
        long end = System.currentTimeMillis();
        logger.info("hello sendEmail() is executed in {} ms", (end - start));
    }

    /**
     * It is used to convert the normal folder to .zip folder
     * @param zipPath path required to save converted zip file
     * @param file path of folder that needed to be converted
     */
        public void zipper(String zipPath,File file){
            long start = System.currentTimeMillis();
            try {
                if(file.isDirectory()) {
                    logger.info("{} is a directory",file.getName());
                    new ZipFile(zipPath).addFolder(file);
                }
                if(file.isFile()){
                    logger.info("{} is a directory",file.getName());
                    new ZipFile(zipPath).addFile(file);
                }
            }catch(ZipException e){
                logger.error("{} occurred while reading file with name {}", e, file.getName());
                long end = System.currentTimeMillis();
                logger.info("hello sendEmail() is executed in {} ms", (end - start));
            }
            long end = System.currentTimeMillis();
            logger.info("hello sendEmail() is executed in {} ms", (end - start));
        }
}