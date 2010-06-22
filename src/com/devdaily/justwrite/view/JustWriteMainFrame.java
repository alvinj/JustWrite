package com.devdaily.justwrite.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import com.apple.mrj.MRJAboutHandler;
import com.apple.mrj.MRJApplicationUtils;
import com.apple.mrj.MRJPrefsHandler;
import com.apple.mrj.MRJQuitHandler;
import com.devdaily.justwrite.JustWrite;
import com.devdaily.justwrite.controller.MainFrameController;
import com.devdaily.justwrite.view.actions.CloseFileAction;
import com.devdaily.justwrite.view.actions.ColorChooserAction;
import com.devdaily.justwrite.view.actions.CompleteWordAction;
import com.devdaily.justwrite.view.actions.LargerFontSizeAction;
import com.devdaily.justwrite.view.actions.OpenFileAction;
import com.devdaily.justwrite.view.actions.OpenRecentFileAction;
import com.devdaily.justwrite.view.actions.QuitAction;
import com.devdaily.justwrite.view.actions.SaveFileAction;
import com.devdaily.justwrite.view.actions.SmallerFontSizeAction;

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
public class JustWriteMainFrame extends FullScreenEditorFrame implements KeyListener, MRJAboutHandler, MRJQuitHandler,
    MRJPrefsHandler
{
  private MainFrameController mainFrameController;
  
  private static final Color DEFAULT_EDITOR_BG_COLOR = new Color(5, 5, 5);
  
  private int height;
  private int width;
  
  private JMenu dirtyMenu = new JMenu("");

  private Action openRecentFileAction;
  private Action openFileAction;
  private Action saveFileAction;
  private Action closeFileAction;
  private Action colorChooserAction;
  private Action quitAction;
  private Action smallerFontSizeAction;
  private Action largerFontSizeAction;
  private Action completeWordAction;

  // don't really want a keystroke here:
  private KeyStroke openRecentFileKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.META_MASK);

  private KeyStroke openFileKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.META_MASK);
  private KeyStroke saveFileKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.META_MASK);
  private KeyStroke closeFileKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.META_MASK);
  private KeyStroke colorChooserKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.META_MASK);
  private KeyStroke quitKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.META_MASK);
  private KeyStroke undoKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.META_MASK);
  private KeyStroke redoKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.META_MASK);
  private KeyStroke smallerFontSizeKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Event.META_MASK);
  
  // handle [meta][equals], [meta][equals][shift], and [meta][plus]
  private KeyStroke largerFontSizeKeystroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.META_MASK);
  private KeyStroke largerFontSizeKeystroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.META_MASK + Event.SHIFT_MASK);
  private KeyStroke largerFontSizeKeystroke3 = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Event.META_MASK);

  // the auto-complete feature
  private KeyStroke completeWordKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

  // undo and redo
  // can jack this up to a StyledDocument or DefaultStyledDocument as long as we use
  // a jtextpane:
  private StyledDocument document;
  protected UndoableEditListener undoHandler = new UndoHandler();
  protected UndoManager undoManager = new UndoManager();
  private UndoAction undoAction = null;
  private RedoAction redoAction = null;
  private boolean dirty;

  public JustWriteMainFrame(MainFrameController mainFrameController)
  {
    this.mainFrameController = mainFrameController;

    // no adornments on the mainframe
    this.setUndecorated(true);
    this.setResizable(false);
    this.getContentPane().setBackground(Color.black);

    handleStandardMacMenuActions();
    configureEditorPane();
    configureVerticalScrollbar();
    configureActionsAndKeystrokes();

    // add our menubar (after creating actions)
    setJMenuBar(createMenuBar());

    makeFrameFillScreen();
  }

  private void makeFrameFillScreen()
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    height = screenSize.height;
    width = screenSize.width;
    setPreferredSize(new Dimension(width, height));
    pack();
  }

  private void handleStandardMacMenuActions()
  {
    boolean isMacOS = System.getProperty("mrj.version") != null;
    if (isMacOS)
    {
      MRJApplicationUtils.registerAboutHandler(this);
      MRJApplicationUtils.registerPrefsHandler(this);
      MRJApplicationUtils.registerQuitHandler(this);
    }
  }

  /**
   * Note: Changing from a JEditorPane to a JTextPane solved my word-wrapping
   * problem. Until this switch, the JEditorPane was wrapping words mid-word,
   * i.e., any time I hit the end of the line.
   */
  private void configureEditorPane()
  {
    getEditorPane().setBackground(DEFAULT_EDITOR_BG_COLOR);
    getEditorPane().setMargin(new Insets(12, 12, 12, 12));
    getEditorPane().updateUI();

    // add a listener to the document, mostly so we can tell if it is dirty
    document = (StyledDocument)getEditorPane().getDocument();
    document.addUndoableEditListener(undoHandler);

    document.addDocumentListener(new javax.swing.event.DocumentListener()
    {
      public void insertUpdate(DocumentEvent e)
      {
        documentInsertUpdate(e);
      }

      public void removeUpdate(DocumentEvent e)
      {
        documentRemoveUpdate(e);
      }

      public void changedUpdate(DocumentEvent e)
      {
        documentChangedUpdate(e);
      }
    });
  }

  private void configureActionsAndKeystrokes()
  {
    openFileAction = new OpenFileAction(mainFrameController, this, "Open", openFileKeystroke.getKeyCode());
    getEditorPane().getInputMap().put(openFileKeystroke, "openFile");
    getEditorPane().getActionMap().put("openFile", openFileAction);

    openRecentFileAction = new OpenRecentFileAction(mainFrameController, this, "Open Recent ...", openRecentFileKeystroke.getKeyCode());
    getEditorPane().getInputMap().put(openRecentFileKeystroke, "openRecentFile");
    getEditorPane().getActionMap().put("openRecentFile", openRecentFileAction);

    saveFileAction = new SaveFileAction(mainFrameController, "Save", null);
    getEditorPane().getInputMap().put(saveFileKeystroke, "saveFile");
    getEditorPane().getActionMap().put("saveFile", saveFileAction);

    closeFileAction = new CloseFileAction(mainFrameController, this, "Close", closeFileKeystroke.getKeyCode());
    getEditorPane().getInputMap().put(closeFileKeystroke, "closeFile");
    getEditorPane().getActionMap().put("closeFile", closeFileAction);

    colorChooserAction = new ColorChooserAction(mainFrameController, this, "Select Colors", colorChooserKeystroke.getKeyCode());
    getEditorPane().getInputMap().put(colorChooserKeystroke, "colorChooser");
    getEditorPane().getActionMap().put("colorChooser", colorChooserAction);

    quitAction = new QuitAction(this, "Quit", null);
    getEditorPane().getInputMap().put(quitKeystroke, "quitKeystroke");
    getEditorPane().getActionMap().put("quitKeystroke", quitAction);

    undoAction = new UndoAction();
    getEditorPane().getInputMap().put(undoKeystroke, "undoKeystroke");
    getEditorPane().getActionMap().put("undoKeystroke", undoAction);

    redoAction = new RedoAction();
    getEditorPane().getInputMap().put(redoKeystroke, "redoKeystroke");
    getEditorPane().getActionMap().put("redoKeystroke", redoAction);

    smallerFontSizeAction = new SmallerFontSizeAction(mainFrameController, this, "Select Colors", smallerFontSizeKeystroke.getKeyCode());
    getEditorPane().getInputMap().put(smallerFontSizeKeystroke, "smallerFontSizeKeystroke");
    getEditorPane().getActionMap().put("smallerFontSizeKeystroke", smallerFontSizeAction);

    largerFontSizeAction = new LargerFontSizeAction(mainFrameController, this, "Select Colors", largerFontSizeKeystroke1.getKeyCode());
    getEditorPane().getInputMap().put(largerFontSizeKeystroke1, "largerFontSizeKeystroke");
    getEditorPane().getActionMap().put("largerFontSizeKeystroke", largerFontSizeAction);
    getEditorPane().getInputMap().put(largerFontSizeKeystroke2, "largerFontSizeKeystroke2");
    getEditorPane().getActionMap().put("largerFontSizeKeystroke2", largerFontSizeAction);
    getEditorPane().getInputMap().put(largerFontSizeKeystroke3, "largerFontSizeKeystroke3");
    getEditorPane().getActionMap().put("largerFontSizeKeystroke3", largerFontSizeAction);

    completeWordAction = new CompleteWordAction(mainFrameController, this, "Complete Word", completeWordKeystroke.getKeyCode());
    getEditorPane().getInputMap().put(completeWordKeystroke, "completeWord");
    getEditorPane().getActionMap().put("completeWord", completeWordAction);
  }

  private JMenuBar createMenuBar()
  {
    // create the menubar
    JMenuBar menuBar = new JMenuBar();

    // File menu
    JMenu fileMenu = new JMenu("File");
    JMenuItem openFileMenuItem = new JMenuItem(openFileAction);
    JMenuItem openRecentFileMenuItem = new JMenuItem(openRecentFileAction);
    JMenuItem saveFileMenuItem = new JMenuItem(saveFileAction);
    JMenuItem closeFileMenuItem = new JMenuItem(closeFileAction);
    fileMenu.add(openFileMenuItem);
    fileMenu.add(openRecentFileMenuItem);
    fileMenu.add(saveFileMenuItem);
    fileMenu.add(closeFileMenuItem);

    // Edit menu
    JMenu editMenu = new JMenu("Edit");
    JMenuItem colorChooserMenuItem = new JMenuItem(colorChooserAction);
    JMenuItem undoMenuItem = new JMenuItem(undoAction);
    JMenuItem redoMenuItem = new JMenuItem(redoAction);
    editMenu.add(undoMenuItem);
    editMenu.add(redoMenuItem);
    editMenu.add(colorChooserMenuItem);

    // add the menus to the menubar
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    
    menuBar.add(dirtyMenu);

    return menuBar;
  }

  /**
   * TODO this doesn't actually work
   */
  private void configureVerticalScrollbar()
  {
    JScrollBar verticalScrollBar = getScrollPane().createVerticalScrollBar();
    verticalScrollBar.setBackground(Color.black);
    verticalScrollBar.setForeground(Color.black);

    // see
    // http://developer.apple.com/mac/library/technotes/tn2007/tn2196.html#//apple_ref/doc/uid/DTS10004439
    verticalScrollBar.putClientProperty("JComponent.sizeVariant", "mini");

    getScrollPane().setVerticalScrollBar(verticalScrollBar);
    getScrollPane().updateUI();
  }

  /**
   * Places the given text in our text-editing area.
   */
  public void setEditorText(String text)
  {
    // TODO need to do this with SwingWorker or SwingUtils?
    getEditorPane().setText(text);
    getEditorPane().setCaretPosition(0);
    setFocusInEditingArea();
    dirty = false;
  }
  
  /**
   * Returns the text that is currently in our text-editing area.
   */
  public String getEditorText()
  {
    return getEditorPane().getText();
  }
  
  public StyledDocument getDocument()
  {
    return this.document;
  }

  // /////////// handle the key-related events //////////
  
  public void keyPressed(KeyEvent e)
  {
    KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
  }

  public void keyReleased(KeyEvent e)
  {
  }

  public void keyTyped(KeyEvent e)
  {
  }

  // ////////// code to handle the "dirty" state of our document  ///////////

  public boolean isDirty()
  {
    return dirty;
  }
  
  /**
   * Update the UI widget that tells the user the current editor content is dirty.
   */
  private void updateUIDirtyWidget(String s)
  {
    dirtyMenu.setText(s);
    dirtyMenu.updateUI();
  }
  
  /**
   * Mark the editor content as dirty, and update the UI appropriately.
   */
  public void setDirty(boolean b)
  {
    this.dirty = b;
    String filename = mainFrameController.getCurrentFilename();
    
    if (filename == null)
    {
      filename = "";
    }
    else
    {
      filename = new File(filename).getName();
    }
    if (dirty)
    {
      updateUIDirtyWidget("* " + filename);
    }
    else
    {
      updateUIDirtyWidget(" " + filename);
    }
  }

  void documentChangedUpdate(DocumentEvent e)
  {
    setDirty(true);
  }

  void documentInsertUpdate(DocumentEvent e)
  {
    setDirty(true);
  }

  void documentRemoveUpdate(DocumentEvent e)
  {
    setDirty(true);
  }

  // /////////// handle undo and redo actions //////////////////

  class UndoHandler implements UndoableEditListener
  {

    /**
     * Messaged when the Document has created an edit, the edit is added to
     * <code>undoManager</code>, an instance of UndoManager.
     */
    public void undoableEditHappened(UndoableEditEvent e)
    {
      undoManager.addEdit(e.getEdit());
      undoAction.update();
      redoAction.update();
    }
  }

  class UndoAction extends AbstractAction
  {
    public UndoAction()
    {
      super("Undo");
      setEnabled(false);
    }

    public void actionPerformed(ActionEvent e)
    {
      try
      {
        undoManager.undo();
      }
      catch (CannotUndoException ex)
      {
        // TODO log this or ignore it
        //ex.printStackTrace();
      }
      update();
      redoAction.update();
    }

    protected void update()
    {
      if (undoManager.canUndo())
      {
        setEnabled(true);
        putValue(Action.NAME, undoManager.getUndoPresentationName());
      }
      else
      {
        setEnabled(false);
        putValue(Action.NAME, "Undo");
      }
    }
  }

  class RedoAction extends AbstractAction
  {
    public RedoAction()
    {
      super("Redo");
      setEnabled(false);
    }

    public void actionPerformed(ActionEvent e)
    {
      try
      {
        undoManager.redo();
      }
      catch (CannotRedoException ex)
      {
        // TODO log this or ignore it
        //ex.printStackTrace();
      }
      update();
      undoAction.update();
    }

    protected void update()
    {
      if (undoManager.canRedo())
      {
        setEnabled(true);
        putValue(Action.NAME, undoManager.getRedoPresentationName());
      }
      else
      {
        setEnabled(false);
        putValue(Action.NAME, "Redo");
      }
    }
  }

  // ////////////////  code to handle the standard mac os x menu actions  ////////////////

  public void handleAbout()
  {
    JOptionPane.showMessageDialog(null, "JustWrite, a devdaily.com creation", "About", JOptionPane.INFORMATION_MESSAGE);
  }

  public void handlePrefs() throws IllegalStateException
  {
    JOptionPane.showMessageDialog(null, "Sorry, no prefs yet", "Sorry, no prefs yet", JOptionPane.INFORMATION_MESSAGE);
  }

  public void handleQuit() throws IllegalStateException
  {
    quitAction.actionPerformed(null);
  }

  public void setFocusInEditingArea()
  {
    // TODO do i need to do this in SwingUtilities???
    // this did not work
    getEditorPane().requestFocusInWindow();
    // this did work
    getEditorPane().requestFocus();
  }

}




