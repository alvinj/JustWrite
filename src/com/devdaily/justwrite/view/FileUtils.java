package com.devdaily.justwrite.view;

import java.io.*;
import java.util.*;

/**
 * Copyright 2010, Alvin Alexander, http://devdaily.com.
 * This software is distributed under the terms of the 
 * GNU General Public License.
 *
 * This file is part of an application named JustWrite.
 *
 * JustWrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JustWrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JustWrite. If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class FileUtils
{

  /**
   * Opens and reads a file, and returns the contents as one String.
   */
  public static String readFileAsString(String filename) 
  throws IOException
  {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    StringBuilder sb = new StringBuilder();
    while ((line = reader.readLine()) != null)
    {
      sb.append(line + "\n");
    }
    reader.close();
    return sb.toString();
  }
  
  /**
   * Open and read a file, and return the lines in the file as a list of
   * Strings.
   */
  public static List<String> readFileAsListOfStrings(String filename) throws Exception
  {
    List<String> records = new ArrayList<String>();
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null)
    {
      records.add(line);
    }
    reader.close();
    return records;
  }

  /**
   * Reads a "properties" file, and returns it as a Map (a collection of key/value pairs).
   * 
   * @param filename  The properties filename to read.
   * @param delimiter The string (or character) that separates the key from the value in the properties file.
   * @return The Map that contains the key/value pairs.
   * @throws Exception
   */
  public static Map<String, String> readPropertiesFileAsMap(String filename, String delimiter)
  throws Exception
  {
    Map<String, String> map = new HashMap();
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null)
    {
      if (line.trim().length()==0) continue;
      if (line.charAt(0)=='#') continue;
      // assumption here is that proper lines are like "String : http://xxx.yyy.zzz/foo/bar",
      // and the ":" is the delimiter
      int delimPosition = line.indexOf(delimiter);
      String key = line.substring(0, delimPosition-1).trim();
      String value = line.substring(delimPosition+1).trim();
      map.put(key, value);
    }
    reader.close();
    return map;
  }

  /**
   * Read a Java properties file and return it as a Properties object.
   */
  public static Properties readPropertiesFile(String canonicalFilename)
  throws IOException
  {
    Properties properties = new Properties();
    properties.load(new FileInputStream(canonicalFilename));
    return properties;
  }

  /**
   * Save the given text to the given filename.
   * @param canonicalFilename Like /Users/al/foo/bar.txt
   * @param text All the text you want to save to the file as one String.
   * @throws IOException
   */
  public static void writeFile(String canonicalFilename, String text) 
  throws IOException
  {
    File file = new File (canonicalFilename);
    BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
    out.write(text);
    out.close();
  }

  /**
   * Write an array of bytes to a file. Presumably this is binary data; for plain text
   * use the writeFile method.
   */
  public static void writeFileAsBytes(String fullPath, byte[] bytes) throws IOException
  {
    OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fullPath));
    InputStream inputStream = new ByteArrayInputStream(bytes);
    int token = -1;

    while((token = inputStream.read()) != -1)
    {
      bufferedOutputStream.write(token);
    }
    bufferedOutputStream.flush();
    bufferedOutputStream.close();
    inputStream.close();
  }

  public static String createFullPath(String path, String fileName)
  {
    StringBuffer buffer = new StringBuffer(path);

    if(!path.endsWith("/"))
    {
      buffer.append('/');
    }

    buffer.append(fileName);

    return buffer.toString();
  }

  public static void copyFile(File source, File destination) throws IOException
  {
    //if (!source.isFile() || !dest.isFile()) return false;

    byte[] buffer = new byte[100000];

    BufferedInputStream bufferedInputStream = null;
    BufferedOutputStream bufferedOutputStream = null;
    try
    {
      bufferedInputStream = new BufferedInputStream(new FileInputStream(source));
      bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destination));
      int size;
      while ((size = bufferedInputStream.read(buffer)) > -1)
      {
        bufferedOutputStream.write(buffer, 0, size);
      }
    }
    catch (IOException e)
    {
      // TODO may want to do something more here
      throw e;
    }
    finally
    {
      try
      {
        if (bufferedInputStream != null)
        {
          bufferedInputStream.close();
        }
        if (bufferedOutputStream != null)
        {
          bufferedOutputStream.flush();
          bufferedOutputStream.close();
        }
      }
      catch (IOException ioe)
      {
        // TODO may want to do something more here
        throw ioe;
      }
    }
  }

  
}










