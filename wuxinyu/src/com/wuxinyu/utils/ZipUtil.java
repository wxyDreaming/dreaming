package com.wuxinyu.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class ZipUtil {

	public ZipUtil() {
	}

	/**
	 * ZIP文件解压缩,支持中文文件名,支持多层目录解压,此方法解压出的文件目录结构和文件数据都与原来的一样
	 * @param zipFileName ZIP压缩文件的文件名,如:E:\\ok.zip
	 * @param targetDir 解压到目标文件夹,如:E:\\deal\\
	 */
	public boolean unZip(String zipFileName, String targetDir) {
		return unZip(zipFileName, targetDir, null, true);
	}

	/**
	 * ZIP文件解压缩,支持中文文件名,支持多层目录解压
	 * @param zipFileName ZIP压缩文件的文件名,如:E:\\ok.zip
	 * @param targetDir 解压到目标文件夹,如:E:\\deal\\
	 * @param filter 如果filter不为空，则只解压某此文件，filter支持简单文件名（my.txt）和*.扩展名(*.xml)
	 * @param keepDir 是否保持原来的目录结构不变，如果是false，则解压出来的文件都在目标文件夹下，没有子目录
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean unZip(String zipFileName, String targetDir, Set filter, boolean keepDir) {
		return unZip(new File(zipFileName), targetDir, filter, keepDir);
	}

	/**
	 * ZIP文件解压缩,支持中文文件名,支持多层目录解压
	 * @param zipfile ZIP压缩文件的文件
	 * @param targetDir 解压到目标文件夹,如:E:\\deal\\
	 * @param filter 如果filter不为空，则只解压某此文件，filter支持简单文件名（my.txt）和*.扩展名(*.xml)
	 * @param keepDir 是否保持原来的目录结构不变，如果是false，则解压出来的文件都在目标文件夹下，没有子目录
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean unZip(File zipfile, String targetDir, Set filter, boolean keepDir) {
		boolean result = false;
		try {
			ZipFile zipFile = new ZipFile(zipfile);
			Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					if (keepDir) {
						String name = zipEntry.getName();
						File f = new File(targetDir + File.separator + name);
						f.mkdirs();
					}
				} else {
					String fullFileName = zipEntry.getName();
					fullFileName = fullFileName.replaceAll("\\\\", "/");
					String dirName = targetDir;
					if (keepDir) {
						dirName += fullFileName.substring(0, fullFileName.lastIndexOf("/") > 0 ? fullFileName.lastIndexOf("/") : fullFileName.length());
					}
					String fileName = fullFileName.substring(fullFileName.lastIndexOf("/") + 1);
					if (!isNeedUnzip(fileName, filter)) {
						continue;
					}
					// 先判断文件的目录是否存在，如果不存在，则先创建目录
					File file = new File(dirName);
					if (!file.exists()) {
						file.mkdirs();
					}
					// 创建文件
					File f = new File(dirName + File.separator + fileName);
					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);
					int c;
					byte[] by = new byte[1024];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			}
			if (zipFile != null) {
				zipFile.close();
			}
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	private boolean isNeedUnzip(String fileName, Set filters) {
		if (filters == null || filters.isEmpty()) {
			return true;
		}
		boolean result = false;
		String extName = (fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1));
		Iterator iterator = filters.iterator();
		while (iterator.hasNext()) {
			String filter = (String) iterator.next();
			if (filter.indexOf("*") == -1 && filter.equalsIgnoreCase(fileName)) {
				result = true;
				break;
			}
			if (filter.indexOf("*") != -1 && filter.substring(filter.indexOf(".") + 1).equalsIgnoreCase(extName)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 压缩文件或者目录
	 * @param baseDirName 压缩的根目录
	 * @param fileName 根目录下待压缩的文件或文件夹名， 星号*表示压缩根目录下的全部文件。
	 * @param targetFileName 目标ZIP文件
	 */
	public static void zipFile(String baseDirName, String targetFileName) {
		// 检测根目录是否存在
		if (baseDirName == null) {
			return;
		}
		File file = new File(baseDirName);
		if (!file.exists()) {
			return;
		}

		String baseDirPath = file.getAbsolutePath();
		// 目标文件
		File targetFile = new File(targetFileName);

		try {
			// 创建一个ZIP输出流来压缩数据并写入到ZIP文件
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile));
			if (file.isFile()) {
				fileToZip(baseDirPath, file, out);
			} else {
				dirToZip(baseDirPath, file, out);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
	 * @param zipName 待解压缩的ZIP文件名
	 * @param targetBaseDirName 目标目录
	 */
	@SuppressWarnings("rawtypes")
	public static void upzipFile(String zipFileName, String targetBaseDirName) {
		if (!targetBaseDirName.endsWith(File.separator)) {
			targetBaseDirName += File.separator;
		}
		try {
			// 根据ZIP文件创建ZipFile对象
			ZipFile zipFile = new ZipFile(zipFileName);
			ZipEntry entry = null;
			String entryName = null;
			String targetFileName = null;
			byte[] buffer = new byte[1024 * 5];
			int bytes_read;
			// 获取ZIP文件里所有的entry
			Enumeration entrys = zipFile.getEntries();
			// 遍历所有entry
			// int i = 33;
			while (entrys.hasMoreElements()) {
				entry = (ZipEntry) entrys.nextElement();
				// 获得entry的名字
				entryName = new String(entry.getName().getBytes("GBK"));
				targetFileName = targetBaseDirName + entryName;

				if (entry.isDirectory()) {
					// 如果entry是一个目录，则创建目录
					new File(targetFileName).mkdirs();
					continue;
				} else {
					// 如果entry是一个文件，则创建父目录
					new File(targetFileName).getParentFile().mkdirs();
				}
				File targetFile = new File(targetFileName);
				OutputStream os = new BufferedOutputStream(new FileOutputStream(targetFile));
				InputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
				while (is != null && (bytes_read = is.read(buffer)) != -1) {
					os.write(buffer, 0, bytes_read);
				}
				// 关闭流
				if(os != null)
					os.close();
				if(is != null)
					is.close();
			}
			if(zipFile != null){
				zipFile.close();
			}
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	/**
	 * 将目录压缩到ZIP输出流。
	 * @param baseDirPath
	 * @param dir
	 * @param out
	 */
	private static void dirToZip(String baseDirPath, File dir, ZipOutputStream out) {
		if (dir.isDirectory()) {
			// 列出dir目录下所有文件
			File[] files = dir.listFiles();
			// 如果是空文件夹
			if (files.length == 0) {
				ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));
				// 存储目录信息
				try {
					out.putNextEntry(entry);
					out.closeEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isFile()) {
					// 如果是文件，调用fileToZip方法
					fileToZip(baseDirPath, file, out);
				} else {
					// 如果是目录，递归调用
					dirToZip(baseDirPath, file, out);
				}
			}
		}
	}

	/**
	 * 将文件压缩到ZIP输出流
	 * @param baseDirPath
	 * @param file
	 * @param out
	 */
	private static void fileToZip(String baseDirPath, File file, ZipOutputStream out) {
		FileInputStream in = null;
		ZipEntry entry = null;
		// 创建复制缓冲区
		byte[] buffer = new byte[1024 * 5];
		int bytes_read;
		if (file.isFile()) {
			try {
				// 创建一个文件输入流
				in = new FileInputStream(file);
				// 做一个ZipEntry
				entry = new ZipEntry(getEntryName(baseDirPath, file));
				// 存储项信息到压缩文件
				out.putNextEntry(entry);
				// 复制字节到压缩文件
				while ((bytes_read = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytes_read);
				}
				out.closeEntry();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取待压缩文件在ZIP文件中entry的名字。即相对于跟目录的相对路径名
	 * @param baseDirPath
	 * @param file
	 * @return
	 */
	private static String getEntryName(String baseDirPath, File file) {
		if (!baseDirPath.endsWith(File.separator)) {
			baseDirPath += File.separator;
		}
		String filePath = file.getAbsolutePath();
		// 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储。
		if (file.isDirectory()) {
			filePath += "/";
		}
		int index = filePath.indexOf(baseDirPath);
		return filePath.substring(index + baseDirPath.length());
	}

}
