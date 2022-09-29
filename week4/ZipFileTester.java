/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week4;

import java.io.File;

/**
 * This class is used to call the methods of ZippingDirectory class
 */
public class ZipFileTester {
    public static void main(String[] args) {
        ZippingDirectory zp = new ZippingDirectory();
        File files = new File("/Users/azuga/Desktop/b");
        zp.zipper("/Users/azuga/Desktop/b.zip",files);
        zp.unZip("/Users/azuga/Desktop/b.zip","/Users/azuga/Desktop/unZipped",null);
    }
}
