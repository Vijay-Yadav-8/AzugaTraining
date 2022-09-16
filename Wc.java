/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;

/**
 * This class mimics the wc command of linux
 */

public class Wc {
	/**
	 * This method is used to count all the lines ,words and characters
	 * @param path -used to take the input path of the file
	 */
	public static void wc_all(String path) {
		try {
			int cnt = 0;
			int lc = 0;
			FileReader f = new FileReader(path);
			BufferedReader bf = new BufferedReader(f);
			String line;
			while ((line = bf.readLine()) != null) {
				lc++;
				String[] w = line.split(" ");
				cnt += w.length;
			}
			System.out.print(lc == 1 ? "0 " : lc+" ");
			System.out.print(cnt+" ");
			bf.close();
			Path of = Path.of(path);
			System.out.print(Files.size(of)+" ");
			System.out.println(of.getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * This method is used to count all the characters
	 * @param path -used to take the input path of the file
	 */
	public static void wc_cm(String path) {
		
			try {
				Path of = Path.of(path);
				System.out.print(Files.size(of)+" ");
				System.out.println(of.getFileName());
			} catch (IOException e) {
				System.out.println("AN ERROR OCCURRED");
				e.printStackTrace();
			}
	}
	/**
	 * This method is used to count all the lines
	 * @param path -used to take the input path of the file
	 */
	public static void wc_l(String path) {
		
		int lc = 0;
		BufferedReader bf;
		try {
			FileReader f = new FileReader(path);
			 bf= new BufferedReader(f);
			while ( bf.readLine() != null) {
				lc++;
			}
			System.out.print(lc == 1 ? "0 " : lc+" ");
			System.out.println(Path.of(path).getFileName());
		} catch (IOException e) {
			System.out.println("AN ERROR OCCURRED");
			e.printStackTrace();
		}
}
	/**
	 * This method is used to count all the words
	 * @param path -used to take the input path of the file
	 */
	public static void wc_w(String path) {
		try {
			int cnt = 0;
			FileReader f = new FileReader(path);
			BufferedReader bf = new BufferedReader(f);
			String line;
			while ((line = bf.readLine()) != null) {
				String[] w = line.split(" ");
				cnt += w.length;
			}

			System.out.print(cnt+" ");
			bf.close();
			System.out.println(Path.of(path).getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The Execution of programs starts from here i.e, main() method
	 * @param args - it is used to take the input from the user as command line argument
	 */
	public static void main(String[] args) {
		
		if(args.length>1) {
		switch(args[0]) {
		case ".":
			wc_all(args[1]);
			break;
		case "-c":
			case "-m":
				wc_cm(args[1]);
			break;
			case "-l":
			wc_l(args[1]);
			break;
		case "-w":
			wc_w(args[1]);
			break;
		default:
			System.out.println("command does not match");
		}
		}
		else
			System.err.println("file name cannot be empty");
		
	}

}

