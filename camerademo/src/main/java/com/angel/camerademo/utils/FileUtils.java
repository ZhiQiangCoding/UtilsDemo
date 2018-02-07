package com.angel.camerademo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.angel.camerademo.utils.CloseUtils.closeID;
import static com.angel.camerademo.utils.ConsUtils.KB;


/**
 * 公司:westsoft
 * 作者:程志强
 * 创建时间:2016/8/19 14:33
 * 描述:文件的工具类
 */
public class FileUtils {
    public FileUtils() {
        throw new UnsupportedOperationException("实例化异常!");
    }

    /**
     * 项目的根目录
     */
    public static final String ROOT_DIR = "Android/data/" + AppUtils.getPackageName();
    /**
     * 下载目录
     */
    public static final String DOWNLOAD_DIR = "download";
    /**
     * 缓存目录
     */
    public static final String CACHE_DIR = "cache";
    /**
     * 图标目录
     */
    public static final String ICON_DIR = "icon";
    /**
     * 下载图片的目录
     */
    public static final String IMAGE_LOADER_DIR = "image";
    /**
     * 缓存map集合的目录
     */
    public static final String MAP_CACHE_DIR = "map";
    /**
     * app存储根目录,一般写项目名
     */
    public static final String APP_STORAGE_ROOT = "cameraDemo";


    /**
     * 获取下载目录
     *
     * @return
     */
    public static String getDownloadDir() {
        return getDir(DOWNLOAD_DIR);
    }

    /**
     * 获取缓存目录
     *
     * @return
     */
    public static String getCacheDir() {
        return getDir(CACHE_DIR);
    }

    /**
     * 获取icon目录
     *
     * @return
     */
    public static String getIconDir() {
        return getDir(ICON_DIR);
    }

    /**
     * 获取应用目录，当SD卡存在时，获取SD卡上的目录，
     * 当SD卡不存在时，获取应用的cache目录
     *
     * @param name
     * @return
     */
    public static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isConnSDcard()) {
            sb.append(getExternalStoragePath());
        } else {
            sb.append(getCachePath());
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * 获取app在外置SD卡的路径
     *
     * @param name
     * @return
     */
    public static String getAppDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isConnSDcard()) {
            sb.append(getAppExternalStoragePath());
        } else {
            sb.append(getCachePath());
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * 获取应用的cache目录
     *
     * @return
     */
    public static String getCachePath() {
        File f = AppUtils.getContext().getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }

    /**
     * 创建文件夹
     *
     * @param dirPath
     * @return
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 获取SD下的应用目录
     *
     * @return
     */
    public static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 获取SD下当前APP的目录
     *
     * @return
     */
    public static String getAppExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(APP_STORAGE_ROOT);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 产生图片的路径，带文件夹和文件名，文件名为当前毫秒数
     *
     * @return
     */
    public static String CreateImagePath() {
        return getAppDir(ICON_DIR) + String.valueOf(System.currentTimeMillis()) + "_img.jpg";
    }

    /*******************************************************************/
    /**
     * 根据路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return StringUtils.isSpace(filePath) ? null : new File(filePath);
    }


    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true 存在 false 不存在
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return true 存在 false 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 判断是否是目录
     *
     * @param dirPath 目录路径
     * @return true 目录 false 不是目录
     */
    public static boolean isDir(String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是否是目录
     *
     * @param dir 文件
     * @return true 目录 false 不是目录
     */
    public static boolean isDir(File dir) {
        return isFileExists(dir) && dir.isDirectory();
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return true 文件 false 不是文件
     */
    public static boolean isFile(String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return true 文件 false 不是文件
     */
    public static boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * 判断目录是否存在,不存在则判断是否创建成功
     *
     * @param dirPath 文件路径
     * @return true 存在或创建成功 false 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在,不存在则判断是否创建成功
     *
     * @param file 文件
     * @return true 存在或创建成功 false 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        //如果存在,是目录返回true 是文件则返回false,不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在,不存在判断是否创建成功!
     *
     * @param filePath 文件路径
     * @return true 存在或创建成功 false 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在,不存在判断是否创建成功!
     *
     * @param file 文件
     * @return true 存在或创建成功 false 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        //如果存在,是文件则返回true,是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否存在,存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return true 创建成功 false 创建失败
     */
    public static boolean createFileByDeleteOdFile(String filePath) {
        return createFileByDeleteOdFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在,存在则在创建之前删除
     *
     * @param file 文件
     * @return true 创建成功 false 创建失败
     */
    public static boolean createFileByDeleteOdFile(File file) {
        if (file == null) return false;
        //文件存在并且删除失败返回 false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        //创建目录失败返回 false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制或移动目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @param isMove      是否移动
     * @return true 复制或移动成功  false 复制或移动失败
     */
    public static boolean copyOrMoveDir(String srcDirPath, String destDirPath, boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), isMove);
    }

    /**
     * 复制或移动目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @param isMove  是否移动
     * @return true 复制或移动成功  false 复制或移动失败
     */
    public static boolean copyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        //如果目标目录在源目录中则返回false ,比如
        //srcDir:F:\\myFile\\android\test\src
        //destDir:F:\\myFile\\android\test\src1
        //为了防止以上这种情况出现误判,须分别在后面加个路径分隔符
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        //源文件不存在或者不是目录 则返回false
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        //目标目录不存在返回false
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                //如果操作失败 返回false
                if (!copyOrMoveFile(file, oneDestFile, isMove)) return false;
            } else if (file.isDirectory()) {
                //如果操作失败 返回false
                if (!copyOrMoveDir(file, oneDestFile, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 复制或移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @param isMove       是否移动
     * @return true 复制移动成功 false 复制后移动失败
     */
    public static boolean copyOrMoveFile(String srcFilePath, String destFilePath, boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove);
    }

    /**
     * 复制或移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param isMove   是否移动
     * @return true 复制移动成功 false 复制后移动失败
     */
    public static boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        //源文件不存在或者不是文件则返回false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        //目标文件存在且是文件则返回false
        if (destFile.exists() && destFile.isFile()) return false;
        //目标目录不存在返回false
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return writeFileFromIs(destFile, new FileInputStream(srcFile), false) && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    //***********************************复制目录或文件**********************************************

    /**
     * 复制目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @return true 复制成功 false 复制失败
     */
    public static boolean copyDir(String srcDirPath, String destDirPath) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 复制目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @return true 复制成功 false 复制失败
     */
    public static boolean copyDir(File srcDir, File destDir) {
        return copyOrMoveDir(srcDir, destDir, false);
    }


    /**
     * 复制文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @return true 复制成功 false 复制失败
     */
    public static boolean copyFile(String srcFilePath, String destFilePath) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 复制文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return true 复制成功 false 复制失败
     */
    public static boolean copyFile(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }
    //***********************************移动目录或文件**********************************************

    /**
     * 移动目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @return true 复制成功 false 复制失败
     */
    public static boolean moveDir(String srcDirPath, String destDirPath) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 移动目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @return true 复制成功 false 复制失败
     */
    public static boolean moveDir(File srcDir, File destDir) {
        return copyOrMoveDir(srcDir, destDir, true);
    }

    /**
     * 移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @return true 复制成功 false 复制失败
     */
    public static boolean moveFile(String srcFilePath, String destFilePath) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return true 复制成功 false 复制失败
     */
    public static boolean moveFile(File srcFile, File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }
    //***********************************删除目录或文件**********************************************

    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @return true 删除成功 false 删除失败
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return true 删除成功 false 删除失败
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) return false;
        //目录不存在返回true
        if (!dir.exists()) return true;
        //不是目录 返回false
        if (!dir.isDirectory()) return false;
        //文件存在 且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) return false;
                } else if (!deleteDir(file)) return false;
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return true 删除成功 false 删除失败
     */
    public static boolean deleteFile(String filePath) {
        return deleteFile(getFileByPath(filePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return true 删除成功 false 删除失败
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    //***********************************删除目录下的所有文件*****************************************

    /**
     * 删除目录下的所有文件
     *
     * @param dirPath 目录文件路径
     * @return true 删除成功 false 删除失败
     */
    public static boolean deleteFileInDir(String dirPath) {
        return deleteFileInDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dir 目录文件
     * @return true 删除成功 false 删除失败
     */
    public static boolean deleteFileInDir(File dir) {
        if (dir == null) return false;
        //目录不存在 返回false
        if (!dir.exists()) return true;
        //不是目录 返回false
        if (!dir.isDirectory()) return false;
        //文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    //***********************************获取目录下的所有文件*****************************************

    /**
     * 获取目录下的所有文件
     *
     * @param dirPath     目录路径
     * @param isReCursive 是否递归进行目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(String dirPath, boolean isReCursive) {
        return listFilesInDir(getFileByPath(dirPath), isReCursive);
    }

    /**
     * 获取目录下的所有文件
     *
     * @param dir         目录
     * @param isReCursive 是否递归进行目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(File dir, boolean isReCursive) {
        if (isReCursive) return listFilesInDir(dir);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        Collections.addAll(list, dir.listFiles());
        return list;
    }

    /**
     * 获取目录下的所有文件包括子目录
     *
     * @param dirPath 目录路径
     * @return 文件链表
     */
    public static List<File> listFilesInDir(String dirPath) {
        return listFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 获取目录下的所有文件包括子目录
     *
     * @param dir 目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(File dir) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    list.addAll(listFilesInDir(file));
                }
            }
        }
        return list;
    }

    //***********************************获取目录下的所有后缀名为suffix的文件**************************

    /**
     * 获取目录下所有后缀名为suffix的文件
     *
     * @param dirPath     目录路径
     * @param suffix      后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, String suffix, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix, isRecursive);
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     *
     * @param dir         目录
     * @param suffix      后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix, boolean isRecursive) {
        if (isRecursive) return listFilesInDirWithFilter(dir, suffix);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     *
     * @param dirPath 目录路径
     * @param suffix  后缀名
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, String suffix) {

        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix);
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     *
     * @param dir    目录
     * @param suffix 后缀名
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.getName().toLowerCase().endsWith(suffix.toUpperCase())) ;
                list.add(file);
                if (file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, suffix));
                }
            }
        }
        return list;
    }
    //***********************************获取目录下的所有符合filter的文件(过滤文件)*********************

    /**
     * 获取目录下的所有符合filter的文件
     *
     * @param dirPath     目录路径
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 获取目录下的所有符合filter的文件
     *
     * @param dir         目录
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter, boolean isRecursive) {
        if (isRecursive) return listFilesInDirWithFilter(dir, filter);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(dir.getParentFile(), file.getName())) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下的所有符合filter的文件包括子目录
     *
     * @param dirPath 目录路径
     * @param filter  过滤器
     * @return
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter) {

        return listFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 获取目录下的所有符合filter的文件包括子目录
     *
     * @param dir    目录
     * @param filter 过滤器
     * @return
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file.getParentFile(), file.getName())) {
                    list.add(file);
                }
                if (file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, filter));
                }

            }
        }
        return list;
    }
    //***********************************获取目录下指定文件名的文件包括子目录(大小写忽略)****************

    /**
     * 获取目录下指定文件名的文件包括子目录(大小写忽略)
     *
     * @param dirPath  目录路径
     * @param fileName 文件名
     * @return 文件链表
     */
    public static List<File> searchFileInDir(String dirPath, String fileName) {
        return searchFileInDir(getFileByPath(dirPath), fileName);
    }

    /**
     * 获取目录下指定文件名的文件包括子目录(大小写忽略)
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return 文件链表
     */
    public static List<File> searchFileInDir(File dir, String fileName) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.getName().toUpperCase().equals(fileName.toUpperCase())) {
                    list.add(file);
                }
                if (file.isDirectory()) {
                    list.addAll(searchFileInDir(file, fileName));
                }

            }
        }
        return list;
    }
    //**********************************将输入流写入文件**********************************************

    /**
     * 将输入流写入文件
     *
     * @param filePath 文件路径
     * @param is       输入流
     * @param append   是否追加在文件末
     * @return true 写入成功 false 写入失败
     */
    public static boolean writeFileFromIs(String filePath, InputStream is, boolean append) {
        return writeFileFromIs(getFileByPath(filePath), is, append);
    }

    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param is     输入流
     * @param append 是否追加在文件末
     * @return true 写入成功 false 写入失败
     */
    public static boolean writeFileFromIs(File file, InputStream is, boolean append) {
        if (file == null || is == null) return false;
        if (!createOrExistsFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[ConsUtils.KB];
            int len;
            while ((len = is.read(data, 0, data.length)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeID(is, os);
        }
    }
    //**********************************将字符串写入文件**********************************************

    /**
     * 将字符串写入文件
     *
     * @param filePath 文件路径
     * @param content  写入内容
     * @param append   是否追加在文件末
     * @return true 写入成功 false 写入失败
     */
    public static boolean writeFileFromString(String filePath, String content, boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @param append  是否追加在文件末
     * @return true 写入成功 false 写入失败
     */
    public static boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeID(fileWriter);
        }
    }
    //**********************************指定编码按行读取文件到list*************************************

    /**
     * 指定编码按行读取文件到list
     *
     * @param filePath    文件路径
     * @param charsetName 编码格式
     * @return 文件行链表
     */
    public static List<String> readFile2list(String filePath, String charsetName) {
        return readFile2list(getFileByPath(filePath), charsetName);
    }

    /**
     * 指定编码按行读取文件到list
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 文件行链表
     */
    public static List<String> readFile2list(File file, String charsetName) {
        //return readFile2list(file, 0, Integer.MAX_VALUE, charsetName);
        return readFile2list(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * 指定编码按行读取文件到list
     *
     * @param filePath    文件路径
     * @param st          需要读取的开始行数
     * @param end         需要读取的结束行数
     * @param charsetName 编码格式
     * @return 包含指定行的文件行链表
     */
    public static List<String> readFile2list(String filePath, int st, int end, String charsetName) {
        return readFile2list(getFileByPath(filePath), 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * 指定编码按行读取文件到list
     *
     * @param file        文件
     * @param st          需要读取的开始行数
     * @param end         需要读取的结束行数
     * @param charsetName 编码格式
     * @return 包含指定行的文件行链表
     */
    public static List<String> readFile2list(File file, int st, int end, String charsetName) {
        if (file == null) return null;
        if (st > end) return null;
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (StringUtils.isSpace(charsetName)) {
                reader = new BufferedReader(new FileReader(file));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) break;
                if (st <= curLine && curLine <= end) {
                    list.add(line);
                }
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeID(reader);
        }
    }
    //**********************************指定编码按行读取文件到字符串***********************************


    /**
     * 指定编码按行读取文件到字符串
     *
     * @param filePath    文件路径
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String readFile2String(String filePath, String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    /**
     * 指定编码按行读取文件到字符串
     *
     * @param file        文件路径
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String readFile2String(File file, String charsetName) {
        if (file == null) return null;
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            if (StringUtils.isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");//windowns系统的换行为\r\n linux为\n
            }
            //要去掉最后的换行符
            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeID(reader);
        }
    }
    //**********************************读取文件到字节数组*******************************************

    /**
     * 读取文件到字节数组
     *
     * @param filePath 文件路径
     * @return 字节数组
     */
    public static byte[] readFile2Bytes(String filePath) {
        return readFile2Bytes(getFileByPath(filePath));
    }

    /**
     * 读取文件到字节数组
     *
     * @param file 文件路径
     * @return 字节数组
     */
    public static byte[] readFile2Bytes(File file) {
        if (file == null) return null;
        try {
            return ConverUtils.inputStream2Bytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    //**********************************简单获取文件编码格式******************************************

    /**
     * 简单获取文件编码格式
     *
     * @param filePath 文件路径
     * @return 文件编码
     */
    public static String getFileCharsetSimple(String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * 简单获取文件编码格式
     *
     * @param file 文件
     * @return 文件编码
     */
    public static String getFileCharsetSimple(File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeID(is);
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }
    //**********************************获取文件的行数***********************************************

    /**
     * 获取文件的行数
     *
     * @param filePath 文件路径
     * @return 文件行数
     */
    public static int getFileLines(String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /**
     * 获取文件的行数
     *
     * @param file 文件
     * @return 文件行数
     */
    public static int getFileLines(File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte buffer[] = new byte[KB];
            int readChars;
            while ((readChars = is.read(buffer, 0, buffer.length)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (buffer[i] == '\n') ++count;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeID(is);
        }
        return count;
    }
    //**********************************获取文件大小************************************************

    /**
     * 获取文件大小
     *
     * @param filePath 文件路径
     * @return 文件大小
     */
    public static String getFileSize(String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    /**
     * 获取文件大小
     * 例如:getFileSize(file,ConstUtils.MB) ;返回文件大小单位为MB
     *
     * @param file 文件
     * @return 文件大小
     */
    public static String getFileSize(File file) {
        if (!isFileExists(file)) return "";
        return ConverUtils.byte2FitSize(file.length());
    }
    /**********************************获取文件的MD5校验码*****************************************/
    /**
     * 获取文件的MD5校验码
     *
     * @param filePath 文件路径
     * @return 文件的MD5校验码
     */
    public static String getFileMD5(String filePath) {
        return getFileMD5(getFileByPath(filePath));
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param file 文件
     * @return 文件的MD5校验码
     */
    public static String getFileMD5(File file) {
        return EncryptUtils.encryptMD5File2String(file);
    }
    //**********************************获取全部路径中的文件名*****************************************

    /**
     * 获取全部路径中的文件名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileName(File file) {
        if (file == null) {
            return null;
        }
        return getFileName(file.getPath());
    }

    /**
     * 获取全部路径中的文件名
     *
     * @param filePath 文件
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isSpace(filePath)) {
            return filePath;
        }
        int lastSep = filePath.lastIndexOf(File.separator);

        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }


    /***********************************************************************************************/
    /**
     * 判断是否有SD卡
     *
     * @return
     */
    public static boolean isConnSDcard() {

//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			return true;//有SD卡
//		}
        //return false;
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建文件夹
     *
     * @param path 文件的路径
     * @return 返回文件对象
     */
    public static File createFileDir(String path) {
        if (isConnSDcard()) {
            File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File defaultShotDir = new File(dcim, path);
            if (!defaultShotDir.exists()) {
                defaultShotDir.mkdirs();
            }
            return defaultShotDir;
        }
        return null;
    }

    /**
     * 将图片保存到SD卡
     *
     * @param buf     图片字节数组
     * @param imgPath 图片路径
     * @return true 保存成功
     */
    public static boolean writeToSDcard(byte[] buf, String imgPath) {
        FileOutputStream fos = null;
        //http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg
        // 从图片地址中截取最后一个分割线后面的部分作为图片名称
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        // 定义一个文件
        File file = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                imgName);
        try {
            // 定义一个FileOutputStream对象
            fos = new FileOutputStream(file);
            // 将字节数组写入FileOutputStream
            fos.write(buf);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                // 关闭文件输出流
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 保存图片,返回图片路径
     *
     * @param context  上下文
     * @param bitmap   图片资源
     * @param fileName 图片文件名
     * @return 图片路径
     */
    public static String saveBitmap(Context context, Bitmap bitmap, String fileName) {
        File dir = context.getFilesDir();
        return saveBitmap(dir, bitmap, fileName);
    }

    /**
     * 保存图片
     *
     * @param dir      父目录
     * @param bitmap   图片资源
     * @param fileName 图片文件名
     * @return 图片路径
     */
    public static String saveBitmap(File dir, Bitmap bitmap, String fileName) {
        if (bitmap == null || TextUtils.isEmpty(fileName) || dir == null) {
            return null;
        }
        FileOutputStream fos = null;
        try {
            //若存视频截图为png格式,则会花屏
            File file = new File(dir, fileName + ".jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            //参数
            // 3
            /**
             * 参数
             * 1.压缩图片的格式 jpeg png webp
             * 2.压缩参数,0-100.0  0小尺寸压缩,100 最高质量的压缩  有些格式,比如png 是无损压缩,将忽略质量设置
             * 3.压缩的数据 FileOutputStream对象
             */
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //释放资源
            bitmap.recycle();
            //调用gc回收
            System.gc();
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 保存文件
     *
     * @param data 文件对应的字节数组
     * @param dir  文件父目录
     * @return 返回文件位置
     */
    public static String saveFile(byte[] data, File dir, String fileName) {
        String filePath = null;
        if (TextUtils.isEmpty(fileName) || dir == null) {
            return null;
        }
        FileOutputStream fos = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        // 定义一个文件
        File file = new File(dir, fileName);
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 定义一个FileOutputStream对象
            fos = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(fos);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(data);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
//            // 将字节数组写入FileOutputStream
//            fos.write(data);
            //获取文件的最终路径
            filePath = file.getPath();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                // 关闭文件输出流
                if (fos != null) {
                    fos.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return filePath;
    }

    /**
     * 计算文件大小
     *
     * @param size 文件的大小
     * @return 返回带单位的文件大小
     */
    public static String sizeAddUnit(long size) {
        char[] unit = new char[]{'B', 'K', 'M', 'G'};
        int index = 0;
        int div = 1;
        for (; index < unit.length; index++) {
            div *= 1024;
            if (size < div) {
                break;
            }
        }
        div /= 1024;
        if (size % div == 0) {
            return String.valueOf(size / div) + unit[index];
        } else {
            DecimalFormat df = new DecimalFormat("#.00");//格式化十进制数字
            return df.format(size * 1.0 / div) + unit[index];
        }
    }

}
