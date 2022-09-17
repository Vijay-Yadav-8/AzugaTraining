/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week1;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This class mimics pipe(|) commands of linux
 */
public class Pipe {
	/**
	 * It prints all folders/files one by onr in new line
	 * @param path - used to take folder path
	 * @return -returns all folders data as a string
	 */
	public static String ls_1(String path){
		File f = new File(!path.equalsIgnoreCase("hi") ? path : System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
	StringBuilder ans= new StringBuilder();
		String[] arr = f.list();
		assert arr != null;
		for (String s : arr)
			ans.append(s).append("\n");
		return ans.toString();
	}

	/**
	 * -used to check the folders/files which are hidden
	 * @param path -used to take folder path
	 *@return -returns all folders data as a string
	 */
	public static String ls_a(String path) {
		File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		StringBuilder ans= new StringBuilder();
		File[] str = f.listFiles();
		if(str!=null) {
			for (File f1 : str) {
				if (f1.isHidden())
					ans.append(f1).append("\n");
			}
		}
		return ans.toString();
	}

	/**
	 * used to print the files ordered by time in ascending order
	 * @param path -used to take folder path
	 * @return -returns all folders data as a string
	 */
	public static String ls_t(String path) {
		File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		AtomicReference<String> ans= new AtomicReference<>("");
		Map<Long, String> mp = new TreeMap<>();
		File[] str = f.listFiles();
		if(str!=null) {
			for (File obj : str) {
				mp.put(obj.lastModified(), obj.getName());
			}
		}
			SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			mp.forEach((key, value) -> ans.set(ans + (pdf.format(key) + " " + value + "\n")));
		return ans.get();
	}
	/**
	 * used to print the files ordered by time in ascending order
	 * @param path -used to take folder path
	 * @return -returns all folders data as a string
	 */
	public static String ls_T(String path) {
		File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		AtomicReference<String> ans= new AtomicReference<>("");
		Map<Long, String> mp = new TreeMap<>(Collections.reverseOrder());
		File[] str = f.listFiles();
		if(str!=null) {
			for (File obj : str) {
				mp.put(obj.lastModified(), obj.getName());
			}
		}

		SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		mp.forEach((key, value) -> ans.set(ans + (pdf.format(key) + " " + value + "\n")));
		return ans.get();
	}
	/**
	 * used to print a long the files ordered by size in descending order
	 * @param path -used to take folder path
	 * @return -returns all folders data as a string
	 */
	public static String ls_lS(String path) {
		File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		AtomicReference<String> ans= new AtomicReference<>("");
		Map<Long, File> mp = new TreeMap<>(Collections.reverseOrder());
		File[] str = f.listFiles();
		if(str!=null) {
			for (File obj : str) {
				mp.put(obj.length(), obj);
			}
		}

		SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		mp.forEach((key, value) -> {

			try {
				if (!value.isHidden()) {
					Path p = Path.of(value.getPath());
					PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
					ans.set(ans + (PosixFilePermissions.toString(ats.permissions()) + " ")
							+ (value.isFile() ? 1 : (Objects.requireNonNull(value.list()).length) + " ")
							+ (ats.owner().getName() + " ")
							+ (ats.group().getName() + " ")
							+ (ats.size() / 1024 + "kb ")
							+ (pdf.format(value.lastModified()) + " ")
							+ (value.getName()) + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
		return ans.get();
	}
	/**
	 * used to print the files ordered by name in ascending order
	 * @param path -used to take folder path
	 * @return -returns all folders data as a string
	 */
	public static String ls_X(String path) {
		File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		StringBuilder ans= new StringBuilder();
		try{
			String[] arr = f.list();
			if(arr != null) {
				Arrays.sort(arr);
				for (String s : arr) {
					if (s.charAt(0) != '.')
						ans.append(s).append("\n");
				}
			}
		}catch(Exception e) {
			System.out.println("An error occurred");
		}
		return ans.toString();
	}
	/**
	 * used to check the files which are hidden by printing a long list one by one
	 * @param path -used to take folder path
	 * @return -returns all folders data as a string
	 */
	public static String ls_la(String path) {
		File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		StringBuilder ans= new StringBuilder();
		try {
			SimpleDateFormat pdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			File[] str = f.listFiles();
			if(str!=null) {
				for (File f1 : str) {
					Path p = Path.of(f1.getPath());
					PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
					ans.append(PosixFilePermissions.toString(ats.permissions())).append(" ").append(ats.owner().getName()).append(" ").append(ats.group().getName()).append(" ").append(ats.size()).append(" ").append(pdf.format(f1.lastModified())).append(" ").append(f1.getName()).append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans.toString();
	}

	/**
	 * used to print the file's all details ordered by time in ascending order
	 * @param path -used to take folder path
	 * @return -returns all folders data as a string
	 */
	public static String ls_LRT(String path) {
		File f = new File(!path.equals("hi")?path:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
		AtomicReference<String> ans= new AtomicReference<>("");
		Map<Long, File> mp = new TreeMap<>(Collections.reverseOrder());
		File[] str = f.listFiles();
		if(str!=null) {
			for (File obj : str) {
				mp.put(obj.lastModified(), obj);
			}
		}
		SimpleDateFormat pdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		mp.forEach((key, value) -> {
			try {
				Path p = Path.of(value.getPath());
				PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
				ans.set(ans + (PosixFilePermissions.toString(ats.permissions()) + " ")
						+ (ats.owner().getName() + " ")
						+ (ats.group().getName() + " ")
						+ (ats.size() + " ")
						+ (pdf.format(value.lastModified()) + " ")
						+ (value.getName() + "\n"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return ans.get();
	}

	/**
	 * It prints all folders/files in single line
	 * @param path1 - used to take folder path
	 * @return -returns all folders data as a string
	 */
	public static String ls(String path1){
	 File f = new File(!path1.equalsIgnoreCase("hi")?path1:System.getProperty("user.dir")); //it takes given path,if not provided it takes the default user directory as path
	 String[] arr = f.list();
	 StringBuilder path= new StringBuilder();
	 if(arr!=null) {
		 for (String s : arr) {
			 if (s.charAt(0) != '.')
				 path.append(s).append("\n");
		 }
	 }
	 return path.toString();
 }

	/**
	 * It prints the data which is present in the given file on to the console
	 * @param path -It is used to take the input file path
	 * @return -returns the whole file content as a string
	 */
	public static String cat(String path) {
		try {
			Path of = Path.of(path);
			if(Files.isRegularFile(of))
			return Files.readString(of); //it takes given path
			else
				return "path";
		} catch (IOException e) {
			System.out.println("exception raised");
			e.printStackTrace();
		}
		return "path";
	}
	/**
	 * This method takes the string as an input and counts the number of lines including Line count and character count
	 * @param str -It is used to take the String  aas input
	 */
	public static String wc(String str) {
		String ans=" ";
		String[] lines = str.split("\n\r|\r|\n");
		ans=ans+(lines.length + "\t");
		int j = 0, k = 0;
		while (j < lines.length) {
			for (String ignored : lines[j].split(" ")) {
				k++;
			}
			j++;
		}
		ans=ans+(k + "\t");
		int i = 0;
		for (byte ignored : str.getBytes(StandardCharsets.UTF_8)) {
			i++;
		}
		ans=ans+(i+"\t");
		return ans;
	}
	/**
	 * This method is used sort the given input and prints the output to the console
	 * @param str -this is used to take the input string
	 */
	public static String sort(String str) {
		String[] lines = str.split("\n\r|\r|\n");
		StringBuilder ans= new StringBuilder();
		List<String> ls = new ArrayList<>(Arrays.asList(lines));
		Collections.sort(ls);
		for (String e:ls) {
			ans.append(e).append("\n");
		}
		return ans.toString();
	}

	/**
	 * This method is used find a word inside the given line and returns the line as output
	 * @param str -used to take the whole line as input
	 * @param mark -used to check whether this word is present in it or not
	 * @return returns the line if the word is present in the line str
	 */
	public static String grep(String str, String mark) {
		String[] lines = str.split("\n\r|\r|\n");
		ArrayList<String> ar = new ArrayList<>(Arrays.asList(lines));
		StringBuilder ans = new StringBuilder();
		for(String s:ar) {
		if (s.contains(mark)) {
			ans.append(s).append("\n");
		}
		}
		return ans.toString();
	}

	/**
	 * This method returns the no of selected lines from the top lines
	 *
	 * @param str -used to take the lines as a single string
	 * @return the no of selected lines
	 */
	public static String head(String str,int len) {
		String[] lines = str.split("\n\r|\r|\n");
		ArrayList<String> ar = new ArrayList<>(Arrays.asList(lines));
		StringBuilder ans = new StringBuilder();
		int i=0;
		for(String s:ar) {
			if (i>=len){ 
				break;
}
			ans.append(s).append("\n");
			i++;
		}
		return ans.toString();
	}
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
			Path path1 = Path.of(path);
			System.out.print(Files.size(path1)+" ");
			System.out.println(path1.getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * This method returns the no of selected lines from the bottom lines
	 * @param str -used to take the lines as a single string
	 * @param len -used to take that how many lines should be taken as the input
	 * @return the no of selected lines
	 */
	public static String tail(String str,int len) {
		String[] lines = str.split("\n\r|\r|\n");
		ArrayList<String> ar = new ArrayList<>(Arrays.asList(lines));
		Collections.reverse(ar);
		StringBuilder ans = new StringBuilder();
		int i=0;
		for(String s:ar) {
			if (i>=len){
				break;
}
			ans.append(s).append("\n");
			i++;
		}
		return ans.toString();
	}

	/**
	 * This method checks whether the command given matches with the internal commands and works accordingly
	 * @param path - used to take the input from the user through command line
	 */
	public static void ptrMcr(String path){
	String[] pipeSplit = path.split(Pattern.quote("|")); //splitting and storing the file into string array
		String output="";
		int i=0;
		for (int j=0;j< pipeSplit.length;j++){
		String[] wsSplit = pipeSplit[i].split(" ");
		Matcher mt = Pattern.compile("(cat)|(wc)|(sort)|(head)|(tail)|" +
				"(grep)|(ls-1)|(ls-a)|(ls-t)|(ls-T)|(ls-lS)" +
				"|(ls-X)|(ls-la)|(ls-LRT)|(ls)").matcher(pipeSplit[i]);//comparing the pipe commands with regular expressions
		if(i==0&&wsSplit.length>1)
			output=wsSplit[1];
		if(i==0&&wsSplit.length>1&&wsSplit[0].equals("wc")) {
			wc_all(output);
			break;
		}
		i++;
		if (mt.find()) {
			if(mt.group().equals("cat")&&wsSplit.length>1)
				output = cat(output);
			if(mt.group().equals("ls"))
				output = ls(output.isEmpty()?"hi":output);
			if(mt.group().equals("wc"))
				output = wc(output);
			if(mt.group().equals("head")&&wsSplit.length>1)
				output = head(output, Integer.parseInt(wsSplit[1]));
			if(mt.group().equals("sort"))
				output = sort(output);
			if(mt.group().equals("tail")&&wsSplit.length>1)
				output = tail(output,Integer.parseInt(wsSplit[1]));
			if(mt.group().equals("grep")&&wsSplit.length>1)
				output = grep(output,wsSplit[1]);
			if(mt.group().equals("ls-1"))
				output = ls_1(output.isEmpty()?"hi":output);
			if(mt.group().equals("ls-a"))
				output = ls_a(output.isEmpty()?"hi":output);
			if(mt.group().equals("ls-t"))
				output = ls_t(output.isEmpty()?"hi":output);
			if(mt.group().equals("ls-T"))
				output = ls_T(output.isEmpty()?"hi":output);
			if(mt.group().equals("ls-lS"))
				output = ls_lS(output.isEmpty()?"hi":output);
			if(mt.group().equals("ls-X"))
				output = ls_X(output.isEmpty()?"hi":output);
			if(mt.group().equals("ls-la"))
				output = ls_la(output.isEmpty()?"hi":output);
			if(mt.group().equals("ls-LRT"))
				output = ls_LRT(output.isEmpty()?"hi":output);
		} else
			System.out.println("command not found");
	}
		System.out.println(output);
}
	/**
	 *
	 * @param args - it is used to take the input from the user as command line argument
	 */
	public static void main(String[] args) {
		ptrMcr(args[0]);
	}

}