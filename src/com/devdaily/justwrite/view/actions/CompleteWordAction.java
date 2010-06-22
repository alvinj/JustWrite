package com.devdaily.justwrite.view.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import com.devdaily.justwrite.controller.MainFrameController;
import com.devdaily.justwrite.view.JustWriteMainFrame;
import com.devdaily.justwrite.view.LookAheadTextPane;

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
 * This class implement the "auto complete" feature.
 *
 */
public class CompleteWordAction extends AbstractAction
{
  MainFrameController mainFrameController;
  JustWriteMainFrame mainFrame;
  LookAheadTextPane textPane;
  boolean alreadyHandlingWordCompletion;
  
  // variables i need to keep around in case the user hits [esc] repeatedly
  String currentPartialWord;                // the partial word fragment the user has typed
  List<String> matchingWordCandidates;      // word candidates, based on the current partial word fragment the user has typed
  String wordCandidate0;                    // the 0th element from the wordCandidate array
  int partialWordLength;
  int lastWordCandidateLength;              // length of the last word candidate
  int originalCaretPosition;
  int lastCaretPosition = -99;
  int lastArrayPosition = 0;
  Document document;
  
  public CompleteWordAction(MainFrameController mainFrameController, JustWriteMainFrame mainFrame, String name, Integer mnemonic)
  {
    super(name, null);
    this.mainFrameController = mainFrameController;
    this.mainFrame = mainFrame;
    this.textPane = mainFrame.getEditorPane();
    putValue(MNEMONIC_KEY, mnemonic);
  }

  public void actionPerformed(ActionEvent e)
  {
//    System.err.println("WordArray: " + matchingWordCandidates);
    
    // my test for now to show that the user is still in the same place,
    // hitting the [esc] key, looking for more matches
    int currentCaretPosition = textPane.getCaretPosition();
    if (currentCaretPosition == lastCaretPosition)
    {
      // i think the user has hit the [esc] key more than once for the current word fragment;
      // assume our word array is already populated, and get the next element from
      // that array, if there is a next element to be had
      int numArrayElems = matchingWordCandidates.size();  // ex: 4
      int nextArrayElem = lastArrayPosition + 1;
      if (nextArrayElem < numArrayElems)
      {
        // we still have another element in the array, let's show it
        String nextWordCandidate = matchingWordCandidates.get(nextArrayElem);
        
        // undo the effects of the last wordCandidate we tried;
        // delete the last characters that were added (from originalCaretPos to last CaretPos)
        try
        {
          document.remove(originalCaretPosition, lastCaretPosition-originalCaretPosition);

          // caret should be at the originalCaretPosition; try the next word
          nextWordCandidate = removeUndesiredCharacters(nextWordCandidate);

          partialWordLength = currentPartialWord.length();           // "th" == 2
          int wordCandidateLength = nextWordCandidate.length();      // "thought" == 7
          lastWordCandidateLength = wordCandidateLength;
          String endingWordFragment = nextWordCandidate.substring(partialWordLength);

          document.insertString(originalCaretPosition, endingWordFragment, null);
          lastCaretPosition = textPane.getCaretPosition();
          
          // reset all of our pointers
          lastArrayPosition = nextArrayElem;
          
          return;

        }
        catch (BadLocationException e1)
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      else
      {
        // still in the out 'if' loop, meaning the caret locations are the same,
        // and the user is hitting the [esc] key; but here, we want to go back to 
        // showing the original word fragment by itself
        // we still have another element in the array, let's show it
        String fakeWordCandidate = "";
        
        try
        {
          // undo the effects of the last wordCandidate we tried;
          // delete the last characters that were added (from originalCaretPos to last CaretPos)
          document.remove(originalCaretPosition, lastCaretPosition-originalCaretPosition);

          // caret should be at the originalCaretPosition; try the next word
          fakeWordCandidate = removeUndesiredCharacters(fakeWordCandidate);

          lastCaretPosition = textPane.getCaretPosition();
          
          // reset all of our pointers; this is a trick/kludge to get back to the 0th array element
          lastArrayPosition = -1;
          
          return;
        }
        catch (BadLocationException ble)
        {
          // TODO
        }
      }
    }
    
    // come down here when the currentCaretPosition != lastCaretPosition.
    // this means that we are starting from ground zero with a totally new word

    // reset our carryover references
    lastCaretPosition = -1;
    originalCaretPosition = -99;
    matchingWordCandidates = null;
    currentPartialWord = null;
    wordCandidate0 = null;
    partialWordLength = 0;
    lastWordCandidateLength = 0;
    lastArrayPosition = 0;

    // re-get the document reference (technically not needed until later)
    document = textPane.getDocument();

    // get the current word
    currentPartialWord = textPane.getCurrentWordBeforeCursor();
    
    // get potential matching words from the document
    String documentText = textPane.getDocumentText();
    matchingWordCandidates = getMatchingWords(currentPartialWord, documentText);
    
    if (matchingWordCandidates==null || matchingWordCandidates.size()==0) return;

    // get the 0th word candidate
    wordCandidate0 = matchingWordCandidates.get(0);

    // remove any poopy characters from this word fragment (", ', comma, -, ;, parens, etc.)
    wordCandidate0 = removeUndesiredCharacters(wordCandidate0);

    partialWordLength = currentPartialWord.length();        // "th" == 2
    int wordCandidateLength = wordCandidate0.length();      // "thought" == 7
    lastWordCandidateLength = wordCandidateLength;
    String endingWordFragment = wordCandidate0.substring(partialWordLength);

    // add the difference to the end of the word fragment in the editor;
    // position the cursor at the end of the completed word
    originalCaretPosition = textPane.getCaretPosition();
    try
    {
      document.insertString(originalCaretPosition, endingWordFragment, null);
      lastCaretPosition = textPane.getCaretPosition();
    }
    catch (BadLocationException e1)
    {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    // if the user press [Esc] again, show the next wordCandidate
    
  }

  
  private String removeUndesiredCharacters(final String s)
  {
    String string = null; 
    string = s.replaceAll("-", "");
    string = string.replaceAll(";", "");
    string = string.replaceAll(":", "");
    string = string.replaceAll("\\."," ");
    string = string.replaceAll("\\?"," ");
    string = string.replaceAll("\\("," ");
    string = string.replaceAll("\\)", " ");
    return string;
  }


  /**
   * Gets a List of all the Strings in the documentText that begin with 
   * the partialWord.
   */
  private List<String> getMatchingWords(String partialWord, String documentText)
  {
    if (partialWord==null) return null;
    if (partialWord.trim().equals("")) return null;
    
    List<String> wordCandidates = new ArrayList<String>();
    Set<String> wordSet = getDocumentWords(documentText);
    
    for (String s : wordSet)
    {
      if (s.startsWith(partialWord) == true)
      {
        wordCandidates.add(s);
      }
    }
    return wordCandidates;
  }
  
  /**
   * Get a Set of all the words in the current document.
   */
  private Set<String> getDocumentWords(final String documentText)
  {
    Set<String> wordSet = new TreeSet<String>();
    // TODO there's probably a much more efficient way to do this
    String docText = documentText;
    docText = docText.replaceAll("\\s", " ");
    docText = docText.replaceAll("\\."," ");
    docText = docText.replaceAll("\\?"," ");
    docText = docText.replaceAll("\\("," ");
    docText = docText.replaceAll("\\)", " ");
    StringTokenizer st = new StringTokenizer(docText," ",false);
    int numTokens = st.countTokens();
    int i=0;
    // never add the last token
    for (i=0; i<numTokens-1; i++)
    {
      String s = st.nextToken();
      wordSet.add(s);
    }
    return wordSet;
  }
  


}




