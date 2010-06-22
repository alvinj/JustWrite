package com.devdaily.justwrite.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import java.util.TreeSet;
import javax.swing.text.Document;

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
public class DocumentLookAhead implements LookAheadTextPane.TextLookAhead
{
  // TODO need to fix this for use with jar files (work thru eclipse and as a jar)
  private static final String DICTIONARY_FILE = "resources/english.0";
  private TreeSet setOfWords = new TreeSet();
  private String text;
  private List dictionaryWords;

  public DocumentLookAhead()
  {
    try
    {
      dictionaryWords = FileUtils.readFileAsListOfStrings(DICTIONARY_FILE);
    } 
    catch (Exception e)
    {
      // TODO fix this
      System.err.println("IOException occurred trying to read dictionary.");
      System.err.println(e.getMessage());
    }
  }

  public String doLookAhead(String key)
  {
    if (key==null) return null;
    if (key.trim().equals("")) return null;
    
    Iterator it = setOfWords.iterator();
    while (it.hasNext())
    {
      String s = (String)it.next();
      if (s.startsWith(key) == true)
      {
        return s;
      }
    }
    
    // No match? Try the dictionary.
//    Iterator dit = dictionaryWords.iterator();
//    while (dit.hasNext())
//    {
//      String s = (String)dit.next();
//      if (s.startsWith(key) == true)
//      {
//        return s;
//      }
//    }
    
    // No match found - return null
    return null;
  }

  public String getText()
  {
    return text;
  }
  public void setText(final String document)
  {
    setOfWords = new TreeSet();
    this.text = document;
    text = text.replaceAll("\\s", " ");
    text = text.replaceAll("\\."," ");
    text = text.replaceAll("\\?"," ");
    text = text.replaceAll("\\("," ");
    text = text.replaceAll("\\)", " ");
    StringTokenizer st = new StringTokenizer(text," ",false);
    int numTokens = st.countTokens();
    int i=0;
    // never add the last token
    for (i=0; i<numTokens-1; i++)
    {
      String s = st.nextToken();
      setOfWords.add(s);
    }
  }

  public void addWord(String word)
  {
    setOfWords.add(word);
  }
}






