package com.devdaily.justwrite.view;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

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
 * This is a very heavily-modified version of a class found in the Core
 * Swing book.
 * 
 */
public class LookAheadTextPane extends JTextPane
{
  public LookAheadTextPane()
  {
    this(null);
  }

  public LookAheadTextPane(int columns)
  {
    this(null);
  }

  public LookAheadTextPane(TextLookAhead lookAhead)
  {
    setLookAhead(lookAhead);
    this.setPreferredSize(new Dimension(300, 200));
    this.getDocument().addDocumentListener(new DocumentListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        // Remove any existing selection
        setCaretPosition(getDocument().getLength());
      }

      public void changedUpdate(DocumentEvent e)
      {
      }

      public void insertUpdate(DocumentEvent e)
      {
      }

      public void removeUpdate(DocumentEvent e)
      {
      }
    });

    addFocusListener(new FocusListener()
    {
      public void focusGained(FocusEvent evt)
      {
      }

      public void focusLost(FocusEvent evt)
      {
        if (evt.isTemporary() == false)
        {
          // Remove any existing selection
          setCaretPosition(getDocument().getLength());
        }
      }
    });
  }

  public void setLookAhead(TextLookAhead lookAhead)
  {
    this.lookAhead = lookAhead;
  }

  public TextLookAhead getLookAhead()
  {
    return lookAhead;
  }
  
  public String getDocumentText()
  {
    try
    {
      return getDocument().getText(0, getDocument().getLength());
    }
    catch (BadLocationException e)
    {
      e.printStackTrace();
      return null;
    }
  }
  


  public String getCurrentWordBeforeCursor()
  {
    Document doc = getDocument();
    if (doc == null)
      return null;

    int charsToLookBack = 10;
    int docLength = doc.getLength();
    if (docLength < charsToLookBack)
      charsToLookBack = docLength - 1;
    String allTextBeforeCaretPosition = null;
    String oldContent = null;

    String charsSinceLastWhitespace = null;
    try
    {
      allTextBeforeCaretPosition = doc.getText(0, getCaretPosition());
      LastWhitespaceInfo info = findLastWhitespaceLocation(allTextBeforeCaretPosition);

      if (info.lastWhitespaceLocation > 0 && doc.getLength() > (charsToLookBack - 1))
      {
        // get caret position
        int caretPosition = getCaretPosition();
        // look at last 10 characters
        int scanBackPosition = caretPosition - charsToLookBack;
        if (scanBackPosition <= 0)
          return null;
        String recentChars = doc.getText(scanBackPosition, charsToLookBack);
        // if any characters are blanks, get the characters since the last blank
        int lastWhitespacePosition = recentChars.lastIndexOf(info.lastWhitespaceString);
        if (lastWhitespacePosition <= 0)
          return null;
        charsSinceLastWhitespace = recentChars.substring(lastWhitespacePosition + 1, charsToLookBack);
        return charsSinceLastWhitespace;
      }
      else
      {
        return null;
      }
    }
    catch (BadLocationException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Just use this to pass information back from the findLastWhitespaceLocation method.
   */
  class LastWhitespaceInfo
  {
    int lastWhitespaceLocation;
    String lastWhitespaceString;
  }
  
  private LastWhitespaceInfo findLastWhitespaceLocation(String string)
  {
    // find last whitespace character, and make sure I keep looking for that
    // same character throughout the rest of the code
    // REFACTOR THIS SECTION
    int lastBlank = string.lastIndexOf(" ");
    int lastTab = string.lastIndexOf("\t");
    int lastNewline = string.lastIndexOf("\n");
    int lastFF = string.lastIndexOf("\f");
    int lastR = string.lastIndexOf("\r");

    // get the last whitespace location
    int lastWhitespaceLoc = 0;
    String lastWhitespaceString = "";
    if (lastBlank > lastTab && lastBlank > lastNewline)
    {
      lastWhitespaceLoc = lastBlank;
      lastWhitespaceString = " ";
    }
    else if (lastTab > lastBlank && lastTab > lastNewline)
    {
      lastWhitespaceLoc = lastTab;
      lastWhitespaceString = "\t";
    }
    else if (lastNewline > lastBlank && lastNewline > lastTab)
    {
      lastWhitespaceLoc = lastNewline;
      lastWhitespaceString = "\n";
    }
    LastWhitespaceInfo info = new LastWhitespaceInfo();
    info.lastWhitespaceLocation = lastWhitespaceLoc;
    info.lastWhitespaceString = lastWhitespaceString;
    return info;
  }

  public void replaceSelection(String content)
  {
    super.replaceSelection(content);
    
    // TODO delete this commented-out block once i'm sure i don't need it any more.

//    if (isEditable() == false || isEnabled() == false)
//    {
//      return;
//    }
//
//    Document doc = getDocument();
//
//    int charsToLookBack = 10;
//    if (doc != null && lookAhead != null)
//    {
//      try
//      {
//        // go back to previous whitespace
//        int docLength = doc.getLength();
//        if (docLength < charsToLookBack)
//          charsToLookBack = docLength - 1;
//        String recentDocText = null;
//        String oldContent = null;
//
//        recentDocText = doc.getText(0, getCaretPosition());
//
//        // pass the look-ahead algorithm all of the doc except the
//        // partial word you're currently working on
//        // this may be a bad approach; need to look for whitespace at beginning
//        // and
//        // end of words, periods, etc.
//        if (doc.getLength() > charsToLookBack)
//        {
//          lookAhead.setText(doc.getText(0, doc.getLength() - charsToLookBack));
//        }
//
//        // find last whitespace character, and make sure I keep looking for that
//        // same character throughout the rest of the code
//        // REFACTOR THIS SECTION
//        int lastBlank = recentDocText.lastIndexOf(" ");
//        int lastTab = recentDocText.lastIndexOf("\t");
//        int lastNewline = recentDocText.lastIndexOf("\n");
//        int lastFF = recentDocText.lastIndexOf("\f");
//        int lastR = recentDocText.lastIndexOf("\r");
//
//        int lastWhitespaceLoc = 0;
//        String lastWhitespaceString = "";
//        if (lastBlank > lastTab && lastBlank > lastNewline)
//        {
//          lastWhitespaceLoc = lastBlank;
//          lastWhitespaceString = " ";
//        }
//        else if (lastTab > lastBlank && lastTab > lastNewline)
//        {
//          lastWhitespaceLoc = lastTab;
//          lastWhitespaceString = "\t";
//        }
//        else if (lastNewline > lastBlank && lastNewline > lastTab)
//        {
//          lastWhitespaceLoc = lastNewline;
//          lastWhitespaceString = "\n";
//        }
//
//        if (lastWhitespaceLoc > 0 && doc.getLength() > (charsToLookBack - 1))
//        {
//          // get caret position
//          int caretPosition = getCaretPosition();
//          // look at last 10 characters
//          int scanBackPosition = caretPosition - charsToLookBack;
//          if (scanBackPosition <= 0)
//            return;
//          String recentChars = doc.getText(scanBackPosition, charsToLookBack);
//          // if any characters are blanks, get the characters since the last
//          // blank
//          int lastWhitespacePosition = recentChars.lastIndexOf(lastWhitespaceString);
//          if (lastWhitespacePosition <= 0)
//            return;
//          String charsSinceLastBlank = recentChars.substring(lastWhitespacePosition + 1, charsToLookBack);
//
//          String newContent = lookAhead.doLookAhead(charsSinceLastBlank);
//          if (newContent != null)
//          {
//            int lengthOfAddedContent = newContent.length() - charsSinceLastBlank.length();
//            String newContentSubstring = newContent.substring(charsSinceLastBlank.length(), newContent.length());
//            doc.insertString(getCaretPosition(), newContentSubstring, null);
//
//            // Highlight the added text
//            setCaretPosition(caretPosition + lengthOfAddedContent);
//            moveCaretPosition(caretPosition);
//          }
//        }
//        else
//        {
//          oldContent = recentDocText;
//          String newContent = lookAhead.doLookAhead(oldContent);
//          if (newContent != null)
//          {
//            int lengthOld = oldContent.length();
//            String newContentSubstring = newContent.substring(lengthOld);
//            doc.insertString(getCaretPosition(), newContentSubstring, null);
//
//            // Highlight the added text
//            setCaretPosition(newContent.length());
//            moveCaretPosition(oldContent.length());
//          }
//        }
//
//      }
//      catch (BadLocationException e)
//      {
//        System.err.println(e.getMessage());
//      }
//    }
  }

  protected TextLookAhead lookAhead;

  // Move this out of here.
  // The TextLookAhead interface
  public interface TextLookAhead
  {
    public String doLookAhead(String key);

    public void setText(String text);

    public void addWord(String word);
  }

}
