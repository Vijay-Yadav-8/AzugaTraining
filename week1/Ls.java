/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyz.
 */

package com.azuga.training.week1;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class mimics ls commands of linux
 */
public class Ls {

	/**
	 * It prints all folders/files one by onr in new line
	 * @param path - used to take folder path
	 */
	public static void ls_1(String path){
	File f = new File(!path.equalsIgnoreCase("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it uses the default user directory as path

String[] arr = f.list();
	assert arr != null;
	for (String s : arr)
		System.out.println(s);
}

	/**
	 * -used to check the folders/files which are hidden
	 * @param path -used to take folder path
	 */
	public static void ls_a(String path) {
File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		File[] f1 = f.listFiles();
		if(f1!=null) {
			for (File f2 : f1) {
				if (f2.isHidden())
					System.out.println(f2);
			}
		}
}

	/**
	 * used to print the files ordered by time in ascending order
	 * @param path -used to take folder path
	 */
	public static void ls_t(String path) {
File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
Map<Long, String> mp = new TreeMap<>();
		File[] f1 = f.listFiles();
		if(f1!=null) {
			for (File obj : f1) {
				mp.put(obj.lastModified(), obj.getName());
			}
		}
SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
mp.forEach((key, value) -> System.out.println(pdf.format(key) + " " + value));
}
	/**
	 * used to print the files ordered by time in ascending order
	 * @param path -used to take folder path
	 */
	public static void ls_T(String path) {
		File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		Map<Long, String> mp = new TreeMap<>(Collections.reverseOrder());
		File[] f1 = f.listFiles();
		if(f1!=null) {
			for (File obj : f1) {
				mp.put(obj.lastModified(), obj.getName());
			}
		}

		SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		mp.forEach((key, value) -> System.out.println(pdf.format(key) + " " + value));
	}
	/**
	 * used to print a long the files ordered by size in descending order
	 * @param path -used to take folder path
	 */
public static void ls_lS(String path) {
	File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
Map<Long, File> mp = new TreeMap<>(Collections.reverseOrder());
	File[] f1 = f.listFiles();
	if(f1!=null) {
		for (File obj : f1) {
			mp.put(obj.length(), obj);
		}
	}
	SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	mp.forEach((key, value) -> {

		try {
			if (!value.isHidden()) {
				Path p = Path.of(value.getPath());
				PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
				System.out.print(PosixFilePermissions.toString(ats.permissions()) + " ");
				System.out.print(value.isFile() ? 1 : (Objects.requireNonNull(value.list()).length) + " ");
				System.out.print(ats.owner().getName() + " ");
				System.out.print(ats.group().getName() + " ");
				System.out.print(ats.size() / 1024 + "kb ");
				System.out.print(pdf.format(value.lastModified()) + " ");
				System.out.print(value.getName());
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	});
}
	/**
	 * used to print the files ordered by name in ascending order
	 * @param path -used to take folder path
	 */
public static void ls_X(String path) {
File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
try{
String[] arr = f.list();
if(arr!=null) {
	Arrays.sort(arr);
	for (String s : arr) {
		if (s.charAt(0) != '.')
			System.out.println(s);
	}
}
}catch(Exception e) {
	System.out.println("An error occurred");
}
}
	/**
	 * used to check the files which are hidden by printing a long list one by one
	 * @param path -used to take folder path
	 */
public static void ls_la(String path) {
	File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
try {
	SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	File[] farr = f.listFiles();
	if(farr!=null) {
		for (File f1 : farr) {
			Path p = Path.of(f1.getPath());
			PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
			System.out.print(PosixFilePermissions.toString(ats.permissions()) + " ");
			System.out.print(ats.owner().getName() + " ");
			System.out.print(ats.group().getName() + " ");
			System.out.print(ats.size() / 1024 + "kb ");
			System.out.print(pdf.format(f1.lastModified()) + " ");
			System.out.print(f1.getName());
			System.out.println();
		}
	}

} catch (Exception e) {
	e.printStackTrace();
}
}

	/**
	 * used to print the file's all details ordered by time in ascending order
	 * @param path -used to take folder path
	 */
public static void ls_LRT(String path) {
	File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
Map<Long, File> mp = new TreeMap<>(Collections.reverseOrder());
	File[] f1 = f.listFiles();
	if(f1!=null) {
		for (File obj : f1) {
			mp.put(obj.lastModified(), obj);
		}
	}
SimpleDateFormat pdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
mp.forEach((key, value) -> {
	try {
		Path p = Path.of(value.getPath());
		PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
		System.out.print(PosixFilePermissions.toString(ats.permissions()) + " ");
		System.out.print(ats.owner().getName() + " ");
		System.out.print(ats.group().getName() + " ");
		System.out.print(ats.size() / 1024 + "kb ");
		System.out.print(pdf.format(value.lastModified()) + " ");
		System.out.print(value.getName() + " ");
		System.out.println();

	} catch (Exception e) {
		e.printStackTrace();
	}
});
}

	/**
	 * The Execution of programs starts from here i.e, main() method
	 * @param args - it is used to take the input from the user as command line argument
	 */
	public static void main(String[] args) {

		String[] s =new String[2];
		s[0]=args[0];
		
		if(args.length == 1) {
			s[1]="hi"; //if user does not provide any path as argument it gets automatically replaced the "hi" with s[1]
		}
		else{
		s[1]=args[1];
		}
		switch (s[0]) {
		case "-l":
			case "-1":
				ls_1(s[1]);
			break;
			case "-a":
			ls_a(s[1]);
			break;
		
		case "-X":
			ls_X(s[1]);
			break;
		case "-t":
			ls_t(s[1]);
			break;
		case "-T":
			ls_T(s[1]);
			break;
		case "-lS":
			ls_lS(s[1]);
			break;
		case "-la":
			ls_la(s[1]);
			break;
		case "-lrt":
			ls_LRT(s[1]);
			break;
		case "help":
			help();
			break;
		default:
			System.out.println("command doesn't match");
		}


	}

	/**
	 * it is like a small man for the ls class
	 */
	public static void help() {
	
	System.out.println("ls-1:	-used to check the files one by one");
	System.out.println("ls-a:	-used to check the files which are hidden");
	System.out.println("ls-la:	-used to check the files which are hidden by printing one by one");
	System.out.println("ls-t:	-used to print the files ordered by time in ascending order");
	System.out.println("ls-T:	-used to print the files ordered by time in descending order");
	System.out.println("ls-X:	-used to print the files ordered by name in ascending order");
	System.out.println("ls-lS:	-used to print the files ordered by size in descending order");
	System.out.println("ls-la:	-used to print the file's all details");
	System.out.println("ls-lrt:	-used to print the file's all details ordered by name in ascending order");
	
}

}
