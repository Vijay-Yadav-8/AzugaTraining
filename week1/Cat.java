/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyz.
 */

package com.azuga.training.week1;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * This class mimics the cat wc and sort commands of linux
 */
public class Cat{

	private static final Logger logger = LogManager.getLogger(Cat.class);
	/**
	 * It prints the data which is present in the given file on to the console
	 * @param path -It is used to take the input file path
	 * @return -returns the whole file content as a string
	 */
		public static String cat(String path) {
			try {
				return  Files.readString(Path.of(path));//-used to read the text in the given path
			} catch (IOException e) {
				logger.warn("Exception"+e.getMessage());
			}
			logger.info("cat is executed");
			return null;
		}

	/**
	 * This method takes the string as an input and counts the number of lines including Line count and character count
	 * @param str -It is used to take the String  aas input
	 */
		public static void wc(String str) {
			String[] lines = str.split("\n\r|\r|\n");//split()nis splitting the given string
			System.out.print(lines.length+"\t");
			int j=0,k=0;
			while(j<lines.length){
			for(String ignored :lines[j].split(" ")) {
				k++;
			}
				j++;
			}
			System.out.print(k+"\t");
			int i=0;
			for(char ignored :str.toCharArray()) {
				i++;
			}
			System.out.println(i+"\t");
			logger.info("cat is executed");
		}

	/**
	 * This method is used sort the given input and prints the output to the console
	 * @param str -this is used to take the input string
	 */
		public static void sort(String str) {
			String[] lines = str.split("\n\r|\r|\n");
			List<String> ls = new ArrayList<>(Arrays.asList(lines));
			Collections.sort(ls);
			ls.forEach(System.out::println);
			logger.info("cat is executed");
		}

	/**
	 * The Execution of programs starts from here i.e, main() method
	 * @param args - it is used to take the input from the user as command line argument
	 */
		public static void main(String[] args) {
			String str = null;
			try {
				str= args[0];
			}catch (ArrayIndexOutOfBoundsException e){
				logger.fatal("command line arguments are not provided");
				}
			String []s = str.split(" ");
			if(s.length<=1) {
				System.out.println(cat(s[0]));
			}
			else if(s.length>=3){
			 if(s[0].equals("cat")&&s[2].equals("|")){
			switch(s[3]) {
			case "wc":
				wc(Objects.requireNonNull(cat(s[1])));
				break;
			case "sort":
				sort(Objects.requireNonNull(cat(s[1])));
				break;
			default:
				System.out.println("option does not match");
			}
			}
			else
			System.out.println("| is not used");
			}
			else
			System.out.println("arguments are not sufficient");
	}
}
