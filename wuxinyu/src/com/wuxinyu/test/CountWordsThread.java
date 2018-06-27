package com.wuxinyu.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CountWordsThread implements Runnable {

	private FileChannel fileChannel;
	private FileLock lock;
	private MappedByteBuffer mbBuf;
	private Map<String, Integer> hashMap;

	public CountWordsThread(File file, long start, long end) {
		try {
			// 得到当前文件的通道
			fileChannel = new RandomAccessFile(file, "rw").getChannel();
			// 锁定当前文件的部分
			lock = fileChannel.lock(start, end, false);
			// 对当前文件片段建立内存映射，如果文件过大需要切割成多个片段
			mbBuf = fileChannel.map(FileChannel.MapMode.READ_ONLY, start, end);
			// 创建HashMap实例存放处理结果
			hashMap = new HashMap<String, Integer>();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		String str = Charset.forName("UTF-8").decode(mbBuf).toString();
		StringTokenizer token = new StringTokenizer(str);
		String word = null;
		while (token.hasMoreTokens()) {
			//  将处理结果放到一个HashMap中
			word = token.nextToken().toString().trim();
			if (null != hashMap.get(word)) {
				hashMap.put(word, hashMap.get(word) + 1);
			} else {
				hashMap.put(word, 1);
			}
		}
		try {
			//  释放文件锁
			lock.release();
			//  关闭文件通道
			fileChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	// 获取当前线程的执行结果
	public Map<String, Integer> getResultMap() {
		return hashMap;
	}
	
	
}