package com.abapi.cloud.common.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author  gavine
 * @version $Id: StringUtils.java,
 * $
 * @see org.apache.commons.lang3.StringUtils
 */
public final class StringUtils {

    public static final String EMPTY = org.apache.commons.lang3.StringUtils.EMPTY;

    public static String trimToEmpty(final String str) {
        return org.apache.commons.lang3.StringUtils.trimToEmpty(str);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isEmpty(cs);
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotEmpty(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(cs);
    }

    /**
     * trimToEmpty 包含"null"不区分大小写
     *
     * @param str
     * @return
     */
    public static String trimToEmptyAndNull(String str) {
        return str == null ? EMPTY : ("null".equalsIgnoreCase(str) ? EMPTY : str.trim());
    }

    /**
     * 判读不否为空 包含"null"
     *
     * @param src
     * @return
     */
    public static boolean isEmptyAndNull(final CharSequence cs) {
        return cs == null || cs.length() == 0 || "null".equalsIgnoreCase(cs.toString());
    }

    public static <T> String convertList2String(List<T> list, char sep) {
        StringBuilder builder = new StringBuilder();
        if (null != list && list.size() > 0) {
            boolean first = true;
            for (T item : list) {
                if (first == false) {
                    builder.append(sep);
                } else {
                    first = false;
                }
                builder.append(item);
            }
        }
        return builder.toString();
    }

    public static List<String> convertString2List(String str, String sep) {
        List<String> list = new ArrayList<String>();
        if (!StringUtils.isBlank(str)) {
            String strs[] = str.split(sep);
            for (String item : strs) {
                list.add(item);
            }
        }
        return list;
    }

    public static String encryptionString(String str, int pre, int last) {
        String eString = "";
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (i >= pre - 1 && i < last) {
                ch = '*';
            }
            eString = eString + ch;
        }
        return eString;
    }

    public static List<Long> convertString2LongList(String str, String sep) {
        List<Long> list = new ArrayList<Long>();
        if (!StringUtils.isBlank(str)) {
            String strs[] = str.split(sep);
            for (String item : strs) {
                try {
                    list.add(Long.parseLong(item));
                } catch (Exception e) {

                }
            }
        }
        return list;
    }

    public static List<Integer> convertString2IntList(String str, String sep) {
        List<Integer> list = new ArrayList<Integer>();
        if (!StringUtils.isBlank(str)) {
            String strs[] = str.split(sep);
            for (String item : strs) {
                try {
                    list.add(Integer.parseInt(item));
                } catch (Exception e) {

                }
            }
        }
        return list;
    }

    public static String getEncoding(String str) throws Exception {
        String encode = null;
        if (str.equals(new String(str.getBytes("unicode"), "unicode"))) {
            encode = "unicode";
        }
        if (str.equals(new String(str.getBytes("ISO-8859-1"), "ISO-8859-1"))) {
            encode = "ISO-8859-1";
        }
        if (str.equals(new String(str.getBytes("GBK"), "GBK"))) {
            encode = "GBK";
        }
        if (StringUtils.isEmpty(encode)) {
            throw new Exception();
        }
        return new String(str.getBytes(encode), "utf-8");
    }

}
