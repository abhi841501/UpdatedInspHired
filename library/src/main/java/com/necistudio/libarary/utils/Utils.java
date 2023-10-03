package com.necistudio.libarary.utils;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by droidNinja on 29/07/16.
 */
public class Utils {
/*    public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element: target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : target) {
                if (predicate.test(element)) {
                    result.add(element);
                }

        }
        return result;
    }


    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean contains(String[] types, String path) {
        for (String string : types) {
            if (path.toLowerCase().endsWith(string)) return true;
        }
        return false;
    }
}
