package com.elvis.test.file;

import com.elvis.commons.utils.IOUtil;
import newproject.utils.StaticUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/8/21 11:08
 */
public class FileTest {
    public static void main(String[] args) throws Exception {
//        testOne();
        testTwo();
    }

    private static void testTwo() throws Exception {
        File srcDir = new File("D:/TDDOWN/SrcDir/");
        File[] files = srcDir.listFiles();
        if (null == files || files.length == 0) {
            System.out.println("源文件夹为空,结束运行！");
            return;
        }
        System.out.println("原始文件数量：" + files.length);
        String aimDir = "D:/TDDOWN/AimDir/";
        System.out.println("运行中,请稍等。。。");
        int index = 0;
        List<String> nameChangeList = new ArrayList<>();
        for (File item : files) {
            String name = item.getName();
            String newName = constrNewName(name);

            nameChangeList.add(name + "----------------->" + newName);

            FileInputStream fis = new FileInputStream(item);
            FileOutputStream fos = new FileOutputStream(aimDir + newName);
            IOUtil.inToOut(fis, fos);
            index++;
        }
        System.out.println("运行结束！");
        System.out.println("改名后文件数量：" + index);

        PrintStream printStream = new PrintStream("D:/TDDOWN/改名信息.txt");
        nameChangeList.forEach(printStream::println);
        printStream.flush();
        printStream.close();
        System.out.println("文件改名信息打印结束！");
    }

    private static String constrNewName(String name) {
        List<String> aimList = Arrays.asList("(18禁アニメ)", "(無修正)", "(有修正)", "(中文无码)","(最新99bb)",
                "(最新Queen8)","(最新虎虎虎)","(最新一本道)","[1月新番]","[次元字幕组]","[2月新番]","[魔穗字幕组]",
                "[TMA]","[lancarter][sex8.cc]");
        int index = 0;
        while (true) {
            String aimStr = aimList.get(index);
            index++;
            if (name.startsWith(aimStr)) {
                name = name.substring(aimStr.length());
                continue;
            }
            if (name.contains(aimStr)) {
                name = name.substring(0, name.indexOf(aimStr))
                        + name.substring(name.indexOf(aimStr) + aimStr.length());
                continue;
            }
            break;
        }
        return name;
    }

    public static void testOne() throws Exception {
        File srcDir = new File("D:/TDDOWN/SrcDir/");
        File[] files = srcDir.listFiles();
        if (null == files || files.length == 0) {
            System.out.println("源文件夹为空,结束运行！");
            return;
        }
        System.out.println("原始文件数量：" + files.length);
        String aimDir = "D:/TDDOWN/AimDir/";
        System.out.println("运行中,请稍等。。。");
        int index = 0;
        List<String> cfList = new ArrayList<>();
        for (File item : files) {
            String name = item.getName();
            if (name.endsWith("(1).torrent") || name.endsWith("(2).torrent") || name.endsWith("(3).torrent")) {
                cfList.add(name);
                continue;
            }
            FileInputStream fis = new FileInputStream(item);
            FileOutputStream fos = new FileOutputStream(aimDir + name);
            IOUtil.inToOut(fis, fos);
            index++;
        }
        System.out.println("运行结束！");
        System.out.println("去重后文件数量：" + index);
        if (StaticUtil.isNotEmpty(cfList)) {
            PrintStream printStream = new PrintStream("D:/TDDOWN/重复文件.txt");
            cfList.forEach(printStream::println);
            printStream.flush();
            printStream.close();
        }
        System.out.println("重复文件信息打印结束！");
    }
}
