package org.nico.codegenerator.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class FileUtils {

    public static final String separator = File.separator;
    
    public static String read(String filePath) throws IOException {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line + System.lineSeparator());
            }
            return builder.toString();
        }finally {
            if(inputStream != null) inputStream.close();
            if(reader != null) reader.close();
        }
    }
    
    public static String parseFileName(String filePath) {
        int startIndex = filePath.lastIndexOf(separator);
        int endIndex = filePath.lastIndexOf(".");
        startIndex += separator.length();
        if(endIndex < 0) endIndex = filePath.length();
        return filePath.substring(startIndex, endIndex);
    }
    
    public static String parseFileNameWithSuffix(String filePath) {
        int startIndex = filePath.lastIndexOf(separator);
        startIndex += separator.length();
        return filePath.substring(startIndex);
    }
    
    public static String parseUrlAndFileSuffix(String path) {
        int endIndex = path.indexOf("?");
        int startIndex = path.lastIndexOf(".", endIndex);
        if(startIndex == -1) return null;
        if(endIndex == -1) endIndex = path.length();
        return path.substring(startIndex + 1, endIndex);
    }
    
    public static boolean containsFile(String dirPath, String fileName) {
        File dir = new File(dirPath);
        if(dir.exists() && dir.isDirectory()) {
            return dir.listFiles((d, name) -> name.equalsIgnoreCase(fileName)).length > 0;
        }
        return false;
    }
    
    public static void createDirIfAbsent(String dir) throws IOException {
        File targetDir = new File(dir);
        if(! targetDir.exists() || ! targetDir.isDirectory()) {
            if(! targetDir.mkdirs()) {
                throw new IOException("Create [" + dir + "] failure !");
            }
        }
    }
    
    public static File createFile(String filePath) {
        File file = new File(filePath);
        if(! file.exists()) {
            try {
                file.createNewFile();
                file.setReadable(true, false);
                file.setExecutable(true, false);
                file.setWritable(true, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    public static void createFileAndWrite(String filePath, String content) {
        File file = createFile(filePath);
        if(file.exists()) {
            write(file, content);
        }
    }
    
    public static void write(File file, String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);    
            writer.write(content);
            writer.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public static void write(File file, byte[] datas) {
    	BufferedOutputStream out = null;
        try {
        	out = new BufferedOutputStream(new FileOutputStream(file));
        	out.write(datas);
        	out.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if(out != null) {
                try {
                	out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public static boolean isRelative(String file) {
        if(file.startsWith("/")) return false;
        if(file.matches("[a-zA-Z]:\\\\(.*)")) return false;
        return true;
    }
    
    public static String combination(String pre, String after) {
        if(after.startsWith(separator)) {
            after = after.substring(separator.length());
        }
        if(! pre.endsWith(separator)) {
            pre += separator;
        }
        return pre + after;
    }
    
    public static boolean isYaml(String fileName) {
        return fileName.endsWith(".yml") || fileName.endsWith(".yaml");
    }
    
    public static String read2Str(String url) throws FileNotFoundException{ 
        BufferedReader br = new BufferedReader(new FileReader(url));  
        StringBuilder reqStr = new StringBuilder();  
        char[] buf = new char[2048];  
        int len = -1;
        try {
            while ((len = br.read(buf)) != -1) {  
                reqStr.append(new String(buf, 0, len));  
            }  
            br.close();
        }catch(IOException e) {
            return null;
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reqStr.toString();
    }
    
	/**
	 * 拼接文件名
	 * 
	 * @param name 名称
	 * @param suffix 后缀
	 * @return 文件全称
	 */
	public static String joint(String name, String suffix) {
		if(StringUtils.isBlank(suffix)) {
			return name;
		}
		return name + (suffix.startsWith(".") ? "" : ".") + suffix;
	}
	
	/**
	 * 获取随机名
	 * 
	 * @param suffix 后缀
	 * @return 文件全称
	 */
	public static String randomFileName(String suffix) {
		return joint("nico" + new Date().getTime(), suffix);
	}
	
	public static String specificFileName(String name, String suffix) {
		return joint(name, suffix);
	}
    
}
