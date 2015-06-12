package com.iuni.data.common;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtils {
	
	private static final byte[] blankadding = {0x00};
	private static final int ipstrSize = 512;

	public static void writeToFile(String str, String filePath) {
		try {
			RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw");
			// 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeUTF(str);
            randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateFile(long value, long offset, String filePath){
		try {
			RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw");
            randomFile.seek(offset);
            randomFile.writeLong(value);
            randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readFromFile(String filePath){
		 try {
			DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
			String result = null;
			while((result = dis.readUTF()) != null)
				System.out.println("aa:" + result);
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
	}

}
