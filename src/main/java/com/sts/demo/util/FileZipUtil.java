package com.sts.demo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.ibatis.builder.BuilderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/*
 *   FileZipUtil.zipToFile(saveDirectoryPath+"/zips/"+fileName,saveDirectoryPath+"/projects/"+saveUnZipPath);
 * */

public class FileZipUtil {

	private static final Logger LOG = LogManager.getLogger(FileZipUtil.class);
		
    /**
     * 压缩文件和文件夹
     * 
     * @param srcPathname 需要被压缩的文件或文件夹路径
     * @param zipFilepath 将要生成的zip文件路径
     * @throws BuildException
     * @throws RuntimeException
     */
    public static void zip(String srcPathname, String zipFilepath) {
    	try {
            File file = new File(srcPathname);
            if (!file.exists()) {
                throw new RuntimeException("source file or directory " + srcPathname + " does not exist.");
            }

            Project proj = new Project();
            FileSet fileSet = new FileSet();
            fileSet.setProject(proj);
            // 判断是目录还是文件
            if (file.isDirectory()) {
                fileSet.setDir(file);
                // ant中include/exclude规则在此都可以使用
                // 比如:
                // fileSet.setExcludes("**/*.txt");
                // fileSet.setIncludes("**/*.xls");
            } else {
                fileSet.setFile(file);
            }

            Zip zip = new Zip();
            zip.setProject(proj);
            zip.setDestFile(new File(zipFilepath));
            zip.addFileset(fileSet);
            zip.setEncoding("UTF-8");
            zip.execute();
            
            LOG.info("compress successed.");
    	} catch (BuilderException b) {
    		LOG.error(b.getMessage(), b);
    	} catch (RuntimeException r) {
    		LOG.error(r.getMessage(), r);
    	} catch (Exception e) {
    		LOG.error(e.getMessage(), e);
    	}

    }
	
    /**
     * 解压缩文件和文件夹
     * 
     * @param zipFilepath 需要被解压的zip文件路径
     * @param destDir 将要被解压到哪个文件夹
     * @throws BuildException
     * @throws RuntimeException
     */
    public static void unzip(String zipFilepath, String destDir) {
    	try {
            if (!new File(zipFilepath).exists()) {
                throw new RuntimeException("zip file " + zipFilepath + " does not exist.");
            }

            Project proj = new Project();
            Expand expand = new Expand();
            expand.setProject(proj);
            expand.setTaskType("unzip");
            expand.setTaskName("unzip");
            expand.setEncoding("UTF-8");

            expand.setSrc(new File(zipFilepath));
            expand.setDest(new File(destDir));
            expand.execute();
            
            LOG.info("uncompress successed.");
    	} catch (BuilderException b) {
    		LOG.error(b.getMessage(), b);
    	} catch (RuntimeException r) {
    		LOG.error(r.getMessage(), r);
    	} catch (Exception e) {
    		LOG.error(e.getMessage(), e);
    	}
    }
      
	/*
	 * 解压zip文件
	 * 
	 * @param sourceFile, 待解压的zip文件;
	 *          toFolder, 解压后的存放路径
	 * @throws Exception
	 */
	public static void zipToFile(String sourceFile, String toFolder) throws Exception {
		String toDisk = toFolder;// 接收解压后的存放路径
		ZipFile zfile = new ZipFile(sourceFile, "utf-8");// 连接待解压文件
		Enumeration<ZipEntry> zList = zfile.getEntries();// 得到zip包里的所有元素
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				LOG.info("打开zip文件里的文件夹:" + ze.getName() + "skipped...");
				continue;
			}
			OutputStream outputStream = null;
			InputStream inputStream = null;
			try {
				// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
				outputStream = new BufferedOutputStream(new FileOutputStream(getRealFileName(toDisk, ze.getName())));
				inputStream = new BufferedInputStream(zfile.getInputStream(ze));
				int readLen = 0;
				while ((readLen = inputStream.read(buf, 0, 1024)) != -1) {
					outputStream.write(buf, 0, readLen);
				}
				inputStream.close();
				outputStream.close();
			} catch (Exception e) {
				LOG.error("解压失败：" + e.toString());
				throw new IOException("解压失败：" + e.toString());
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException ex) {

					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				inputStream = null;
				outputStream = null;
			}
		}
		zfile.close();
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param zippath 指定根目录
	 * @param absFileName 相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */

	private static File getRealFileName(String zippath, String absFileName) {
		LOG.info("文件名：" + absFileName);
		String[] dirs = absFileName.split("/", absFileName.length());
		File ret = new File(zippath);// 创建文件对象
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists()) {// 检测文件是否存在
			ret.mkdirs();// 创建此抽象路径名指定的目录
		}
		ret = new File(ret, dirs[dirs.length - 1]);// 根据 ret 抽象路径名和 child 路径名字符串创建一个新 File 实例		
		return ret;
	}
	
}
