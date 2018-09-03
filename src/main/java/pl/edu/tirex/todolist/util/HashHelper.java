package pl.edu.tirex.todolist.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper
{
    public static String sha1(String password)
    {
        try
        {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            sha1.update(password.getBytes(StandardCharsets.UTF_8));
            return byteArrayToHexString(sha1.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5(String password)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes(StandardCharsets.UTF_8));
            return byteArrayToHexString(md5.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] b)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++)
        {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
