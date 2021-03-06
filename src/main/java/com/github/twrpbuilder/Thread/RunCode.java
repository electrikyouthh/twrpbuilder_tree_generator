package com.github.twrpbuilder.Thread;

import com.github.twrpbuilder.Interface.Tools;
import com.github.twrpbuilder.mkTree.MakeTree;
import com.github.twrpbuilder.util.ExtractBackup;

public class RunCode extends Tools implements Runnable {

    public static boolean extract;
    public static boolean AndroidImageKitchen;
    private static String type;
    private boolean mrvl;
    private boolean mtk;
    private boolean samsung;

    public RunCode(String name) {
        cp(name,"build.tar.gz");
        if (AndroidImageKitchen) {
            System.out.println("Using Android Image Kitchen to extract " + name);
            extract("bin");
            extract("unpackimg.sh");
        } else {
            extract("umkbootimg");
            extract("magic");
        }
    }

    public RunCode(String name, String type) {
        RunCode.type = type;
        cp(name,"build.tar.gz");
        if (type.equals("mrvl")) {
            extract("degas-umkbootimg");
            command("mv degas-umkbootimg umkbootimg ");
            mrvl = true;
        } else if (type.equals("mt") || type.equals("mtk")) {
            extract("unpack-MTK.pl");
            command("mv unpack-MTK.pl umkbootimg");
            mtk = true;
        } else if (type.equals("samsung")) {
            extract("umkbootimg");
            samsung = true;
        }
        if (AndroidImageKitchen) {
            extract("bin");
            extract("unpackimg.sh");
        }
    }

    @Override
    public void run() {
        if (extract) {
            new ExtractBackup("build.tar.gz");
        }
        if (AndroidImageKitchen) {
            MakeTree.AndroidImageKitchen = true;
        }
        if (mtk == true) {
            new MakeTree(true, type);
        } else if (mrvl == true || samsung == true) {
            new MakeTree(false, type);
        } else {
            new MakeTree(false, "none");
        }
    }

}
