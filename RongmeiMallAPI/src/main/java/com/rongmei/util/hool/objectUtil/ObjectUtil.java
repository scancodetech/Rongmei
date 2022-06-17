package com.rongmei.util.hool.objectUtil;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class ObjectUtil {
    public ObjectUtil() {
    }

    public static boolean equal(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    public static boolean notEqual(Object obj1, Object obj2) {
        return !equal(obj1, obj2);
    }

    public static int length(Object obj) {
        if (obj == null) {
            return 0;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        } else if (obj instanceof Collection) {
            return ((Collection) obj).size();
        } else if (obj instanceof Map) {
            return ((Map) obj).size();
        } else {
            int count;
            if (obj instanceof Iterator) {
                Iterator<?> iter = (Iterator) obj;
                count = 0;

                while (iter.hasNext()) {
                    ++count;
                    iter.next();
                }

                return count;
            } else if (!(obj instanceof Enumeration)) {
                return obj.getClass().isArray() ? Array.getLength(obj) : -1;
            } else {
                Enumeration<?> enumeration = (Enumeration) obj;
                count = 0;

                while (enumeration.hasMoreElements()) {
                    ++count;
                    enumeration.nextElement();
                }

                return count;
            }
        }
    }

    public static boolean contains(Object obj, Object element) {
        if (obj == null) {
            return false;
        } else if (obj instanceof String) {
            return element == null ? false : ((String) obj).contains(element.toString());
        } else if (obj instanceof Collection) {
            return ((Collection) obj).contains(element);
        } else if (obj instanceof Map) {
            return ((Map) obj).containsValue(element);
        } else {
            Object o;
            if (obj instanceof Iterator) {
                Iterator iter = (Iterator) obj;

                do {
                    if (!iter.hasNext()) {
                        return false;
                    }

                    o = iter.next();
                } while (!equal(o, element));

                return true;
            } else if (obj instanceof Enumeration) {
                Enumeration enumeration = (Enumeration) obj;

                do {
                    if (!enumeration.hasMoreElements()) {
                        return false;
                    }

                    o = enumeration.nextElement();
                } while (!equal(o, element));

                return true;
            } else {
                if (obj.getClass().isArray()) {
                    int len = Array.getLength(obj);

                    for (int i = 0; i < len; ++i) {
                        Object o1 = Array.get(obj, i);
                        if (equal(o1, element)) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }
    }

    public static boolean isNull(Object obj) {
        return null == obj || obj.equals((Object) null);
    }

    public static boolean isNotNull(Object obj) {
        return null != obj && !obj.equals((Object) null);
    }

    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            CharSequence charSequence = (CharSequence) obj;
            return charSequence == null || charSequence.length() == 0;
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            return null == map || map.isEmpty();
        } else if (obj instanceof Iterable) {
            Iterable iterable = (Iterable) obj;
            return null == iterable || isEmpty(iterable.iterator());
        } else if (obj instanceof Iterator) {
            Iterator Iterator = (Iterator) obj;
            return null == Iterator || !Iterator.hasNext();
        } else {
            return ArrisEmpty(obj);
        }
    }

    public static boolean ArrisEmpty(Object array) {
        if (null == array) {
            return true;
        } else if (null == array ? false : array.getClass().isArray()) {
            return 0 == Array.getLength(array);
        } else {
            throw new RuntimeException("obj is not array");
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
