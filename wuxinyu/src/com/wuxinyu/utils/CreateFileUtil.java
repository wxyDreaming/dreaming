package com.wuxinyu.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author wxy
 * @time 2017-11-6 创建新文件和目录
 */
public class CreateFileUtil {

	protected static final Log logger = LogFactory.getLog(CreateFileUtil.class);

	/**
	 * 创建单个文件
	 * 
	 * @param destFileName
	 *            目标文件名
	 * @return 创建成功，返回true，否则返回false
	 */
	public static boolean createFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			logger.error("创建单个文件" + destFileName + "失败，目标文件已存在！");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			logger.error("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
			return false;
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的文件夹不存在，则创建父文件夹
			logger.error("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {
				logger.error("创建目标文件所在的目录失败！");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				logger.info("创建单个文件" + destFileName + "成功！");
				return true;
			} else {
				logger.error("创建单个文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("创建单个文件" + destFileName + "失败！" + e.getMessage());
			return false;
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param destDirName
	 *            目标目录名
	 * @return 目录创建成功放回true，否则返回false
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			logger.error(destDirName + "目标目录已存在！");
			return true;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目标目录
		if (dir.mkdirs()) {
			logger.info("创建目录" + destDirName + "成功！");
			return true;
		} else {
			logger.error("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	/**
	 * 创建临时文件
	 * 
	 * @param prefix
	 *            临时文件名的前缀
	 * @param suffix
	 *            临时文件名的后缀
	 * @param dirName
	 *            临时文件所在的目录，如果输入null，则在用户的文档目录下创建临时文件
	 * @return 临时文件创建成功返回true，否则返回false
	 */
	public static String createTempFile(String prefix, String suffix, String dirName) {
		File tempFile = null;
		if (dirName == null) {
			try {
				// 在默认文件夹下创建临时文件
				tempFile = File.createTempFile(prefix, suffix);
				// 返回临时文件的路径
				return tempFile.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("创建临时文件失败!" + e.getMessage());
				return null;
			}
		} else {
			File dir = new File(dirName);
			// 如果临时文件所在目录不存在，首先创建
			if (!dir.exists()) {
				if (CreateFileUtil.createDir(dirName)) {
					logger.error("创建临时文件失败，不能创建临时文件所在的目录！");
					return null;
				}
			}
			try {
				// 在指定目录下创建临时文件
				tempFile = File.createTempFile(prefix, suffix, dir);
				return tempFile.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("创建临时文件失败!" + e.getMessage());
				return null;
			}
		}
	}

	public static void main(String[] args) {
		// 创建目录
		String dirName = "C:/temp/temp0/temp1";
		CreateFileUtil.createDir(dirName);
		// 创建文件
		String fileName = dirName + "/temp2/tempFile.txt";
		CreateFileUtil.createFile(fileName);
		// 创建临时文件
		String prefix = "temp";
		String surfix = ".txt";
		for (int i = 0; i < 10; i++) {
			logger.error("创建了临时文件: " + CreateFileUtil.createTempFile(prefix, surfix, dirName));
		}
	}
}
