/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is used to mimic the linux basic commands
 */
public class Task1 {
	/**
	 * This method is used to make the directory in the given path
	 * @param path -used to take the path of the directory
	 */
	public static void mkdir(String path) {
		File f = new File(path);
		if (f.mkdir())
			System.out.println("File created successfully");
		else
			System.out.println("Error in creating file");
	}
	/**
	 * This method is used to remove the directory in the given path
	 * @param path -used to take the path of the directory
	 */

	public static void rmdir(String path) {
		File f = new File(path);
		String[] arr = f.list();
		if (arr != null)
			System.out.println("Cannot be Deleted,it contains some files");
		else {
			if(f.delete())
				System.out.println("File deleted successfully");
		}
	}
	/**
	 * This method is used to make the file in the given path
	 * @param path -used to take the path of the directory
	 */

	public static void touch(String path) {
		try {
			File f = new File(path);
			if (!f.exists() && f.createNewFile()) {
				System.out.println("File created: " + f.getName());
			} else
				System.out.println("File already exists");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}
	/**
	 * This method is used to delete the file in the given path
	 * @param path -used to take the path of the directory
	 */
	public static void remFile(String path) {
		File f = new File(path);
		if (f.delete())
			System.out.println("File Deleted successfully");
		else
			System.out.println("Error in deleting file or File does not exist");
	}

	/**
	 * This method is used to move the file from specified path to the given path
	 * @param p1 -used to take path of file to be moved from
	 * @param p2 -used to take path of file to be moved to
	 */

	public static void mv(String p1, String p2) {
		try {
			Files.move(Paths.get(p1), Paths.get(p2));
			System.out.println("File moved successfully");
		} catch (Exception e) {
			System.out.println("File cannot be moved");
			System.out.println(e.getMessage());

		}
	}
	/**
	 * This method is used to change the file last modified time to current system time in the given path
	 * @param path -used to take the path of the directory
	 */

	public static void touch_am(String path) {
		File f = new File(path);
		if (f.exists()) {
			if(f.setLastModified(System.currentTimeMillis()))
				System.out.println("time changed");
		} else {
			touch(path);
			boolean b =f.setLastModified(System.currentTimeMillis());
			System.out.println(b);
		}
	}
	/**
	 * The Execution of programs starts from here i.e, main() method
	 * @param args - it is used to take the input from the user as command line argument
	 */
	public static void main(String[] args) {

		switch (args[0]) {

			case "touch":
				touch(args[1]);
				break;
			case "touch-a":
			case "touch-m":
				touch_am(args[1]);
				break;
			case "mkdir":
				mkdir(args[1]);
				break;
			case "rmdir":
				rmdir(args[1]);
				break;
			case "mv":
				mv(args[1], args[2]);
				break;
			case "rm":
				remFile(args[1]);
				break;
			case "help":
				help();
				break;
			default:
				System.out.println("command doesn't match");
		}

	}

	public static void help() {
		System.out.println("mkdir: -used to make folder");
		System.out.println("rmdir: -used to delete folder");
		System.out.println("touch: -used to make file");
		System.out.println("rm:    -used to delete file");
		System.out.println("mv:    -used to move file");
		System.out.println("touch-a:-used to change files access time");
		System.out.println("touch-m:-used to change files access time");
	}
}