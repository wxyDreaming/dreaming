package com.wuxinyu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class ZipUtil {

	private static final int BUFFEREDSIZE = 1024;

	private static Logger log = Logger.getLogger(ZipUtil.class);

	/**
	 * zip文件解压缩,支持中文文件名,支持多层目录解压,此方法解压出的文件目录结构和文件数据都与原来的一样
	 * 
	 * @param zipFileName zip压缩文件的文件名,如:E:\\ok.zip
	 * @param targetDir 解压到目标文件夹,如:E:\\deal\\
	 * @throws IOException 
	 */
	public static void unZip(String zipFileName, String targetDir) throws IOException {
		log.info("unZip file :" + zipFileName + ",target dir is:" + targetDir);
		unZip(zipFileName, targetDir, null, true);
	}

	/**
	 * zip文件解压缩,支持中文文件名,支持多层目录解压
	 * 
	 * @param zipFileName zip压缩文件的文件名,如:E:\\ok.zip
	 * @param targetDir 解压到目标文件夹,如:E:\\deal\\
	 * @param filter 如果filter不为空，则只解压某此文件，filter支持简单文件名（my.txt）和*.扩展名(*.xml)
	 * @param keepDir 是否保持原来的目录结构不变，如果是false，则解压出来的文件都在目标文件夹下，没有子目录
	 * @return
	 * @throws IOException 
	 */
	public static void unZip(String zipFileName, String targetDir, Set<String> filter, boolean keepDir) {
		try {
			unZip(new File(zipFileName), targetDir, filter, keepDir);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("zip file error ,the exception is -->{}", e);
		}
	}

	/**
	 * zip文件解压缩,支持中文文件名,支持多层目录解压
	 * 
	 * @param zipfile zip压缩文件的文件
	 * @param targetDir 解压到目标文件夹,如:E:\\deal\\
	 * @param filter 如果filter不为空，则只解压某此文件，filter支持简单文件名（my.txt）和*.扩展名(*.xml)
	 * @param keepDir 是否保持原来的目录结构不变，如果是false，则解压出来的文件都在目标文件夹下，没有子目录
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static synchronized void unZip(File zipfile, String targetDir, Set<String> filter, boolean keepDir) throws IOException {
		InputStream in = null;
		FileOutputStream out = null;
		ZipFile zipFile = new ZipFile(zipfile);
		try {
			Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					if (keepDir) {
						File file = new File(targetDir + File.separator + zipEntry.getName());
						file.mkdirs();
					}
				} else {
					String fullFileName = zipEntry.getName();
					String dirName = targetDir;
					if (keepDir)
						dirName += fullFileName.substring(0, fullFileName.lastIndexOf("/"));
					String fileName = fullFileName.substring(fullFileName.lastIndexOf("/") + 1);
					// 先判断文件的目录是否存在，如果不存在，则先创建目录
					File file = new File(dirName);
					if (!file.exists()) {
						file.mkdirs();
					}
					// 创建文件
					File f = new File(dirName + File.separator + fileName);
					f.createNewFile();
					in = zipFile.getInputStream(zipEntry);
					out = new FileOutputStream(f);
					int c;
					byte[] by = new byte[1024];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			zipFile.close();
		}
	}

	/**
	 * 解压缩文件到指定的目录
	 * 
	 * @param zipFileName
	 * @param extPlace
	 * @throws IOException 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void unzip(String zipFileName, String extPlace) throws IOException {
		InputStream in = null;
		OutputStream os = null;
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				String entryName = zipEntry.getName();
				String names[] = entryName.split("/");
				int length = names.length;
				String path = extPlace;
				for (int v = 0; v < length; v++) {
					if (v < length - 1) {
						path += names[v] + "/";
						new File(path).mkdir();
					} else { // 最后一个
						if (entryName.endsWith("/")) { // 为目录,则创建文件夹
							new File(extPlace + entryName).mkdir();
						} else {
							in = zipFile.getInputStream(zipEntry);
							os = new FileOutputStream(new File(extPlace + entryName));
							byte[] buf = new byte[BUFFEREDSIZE];
							int len;
							while ((len = in.read(buf)) > 0) {
								os.write(buf, 0, len);
							}
						}
					}
				}
			}
			zipFile.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null)
				in.close();

			if (os != null)
				os.close();
		}
	}

	/**
	 * 压缩指定的多个文件
	 * 
	 * @param file
	 *            文件绝对路径列表
	 * @param zipfile
	 * @return
	 */
	public static synchronized void zipFiles(List<String> files, String zipfile) throws IOException {
		File ff = new File(zipfile);
		if (!ff.exists()) {
			ff.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(zipfile); // 1
		// 创建ZIP数据输出流对象
		ZipOutputStream zipOut = new ZipOutputStream(out);
		FileInputStream in = null;
		for (int i = 0; i < files.size(); i++) {
			String filepath = files.get(i);
			File f = new File(filepath);
			if (!f.exists()) {
				continue;
			}
			try {
				// 创建文件输入流对象
				in = new FileInputStream(filepath);
				// 创建指向压缩原始文件的入口
				ZipEntry entry = new ZipEntry(filepath.substring(filepath.lastIndexOf('/') + 1, filepath.length())); // 0
				zipOut.putNextEntry(entry);
				// 向压缩文件中输出数据
				int nNumber;
				byte[] buffer = new byte[BUFFEREDSIZE];
				while ((nNumber = in.read(buffer)) != -1) {
					zipOut.write(buffer, 0, nNumber);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					in.close();
				}
				if (zipOut != null) {
					zipOut.close();
				}
				if (out != null) {
					out.close();
				}
			}
		}
	}

	/**
	 * 压缩zip格式的压缩文件
	 * @param inputFile 需压缩文件
	 * @param zipFilename 输出文件及详细路径
	 * @throws IOException
	 */
	public static synchronized void zip(File inputFile, String zipFilename, Set<String> filter) throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilename));
		try {
			zip(inputFile, out, inputFile.getName(), filter);
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}
	}
    
	public static synchronized void zip(String inputFilePath, String zipFilename, Set<String> filter) throws IOException {
		File file = new File(inputFilePath);
		zip(file, zipFilename, filter);
	}
    
    /**
     * 压缩zip格式的压缩文件
     * @param inputFile
     * @param out 
     * @throws IOException
     */
	public static synchronized void zip(File inputFile, OutputStream out, Set<String> filter) throws IOException {
		ZipOutputStream zipout = new ZipOutputStream(out);
		try {
			zip(inputFile, zipout, inputFile.getName(), filter);
		} catch (IOException e) {
			throw e;
		} finally {
			zipout.close();
		}
	}
    
	/**
	 * 压缩zip格式的压缩文件，结果用OutputStream向外面输出，不出现过渡文件
	 * @param inputFileName
	 * @param out
	 */
	public static synchronized void zip(String inputFileName, OutputStream out, Set<String> filter) throws IOException {
		File file = new File(inputFileName);
		zip(file, out, filter);
	}

	/**
	 * 压缩zip格式的压缩文件
	 * @param inputFile 需压缩文件
	 * @param out 输出压缩文件
	 * @param base 结束标识
	 * @throws IOException
	 */
	private static synchronized void zip(File inputFile, ZipOutputStream out, String base, Set<String> filter) throws IOException {
		if (filter.contains(base)) {
			return;
		}

		if (inputFile.isDirectory()) {
			File[] inputFiles = inputFile.listFiles();
			if (inputFiles.length == 0) {
				out.putNextEntry(new ZipEntry(base + File.separator));
			} else {
				for (int i = 0; i < inputFiles.length; i++) {
					zip(inputFiles[i], out, base + File.separator + inputFiles[i].getName(), filter);
				}
			}
		} else {
			if (base.length() > 0) {
				out.putNextEntry(new ZipEntry(base));
			} else {
				out.putNextEntry(new ZipEntry(inputFile.getName()));
			}

			FileInputStream in = new FileInputStream(inputFile);
			try {
				int c;
				byte[] by = new byte[BUFFEREDSIZE];
				while ((c = in.read(by)) != -1) {
					out.write(by, 0, c);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				in.close();
			}
		}
	}
	
	/**
	 * 压缩文件或者目录
	 * @param baseDirName 压缩的根目录
	 * @param fileName 根目录下待压缩的文件或文件夹名， 星号*表示压缩根目录下的全部文件。
	 * @param targetFileName 目标ZIP文件
	 */
	public static void zipFile(String baseDirName, String targetFileName) {
		try {
			zip(new File(baseDirName), targetFileName, new HashSet<String>());
		} catch (IOException e) {
			e.printStackTrace();
			log.error("zip file error ,the exception is -->{}", e);
		}
	}
	
	public static void main(String[] args) {
		String filePath = "E:\\123\\v13-7";
		
		try {
			Set<String>  filters = new HashSet<String>();
			filters.add("v13-7"+File.separator+"_bin");
			filters.add("v13-7"+File.separator+"_old");
			filters.add("v13-7"+File.separator+"CANECM"+File.separator+"workunit.lws.cc.db3");
			ZipUtil.zip(filePath, "e:\\11.zip",filters);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
