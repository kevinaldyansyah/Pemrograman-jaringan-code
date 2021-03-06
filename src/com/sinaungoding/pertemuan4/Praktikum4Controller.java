/*
 * Pemrograman-jaringan

 * Copyright (c) 2019
 * All rights reserved.
 * Written by od3ng created on Sep 8, 2019 11:45:55 PM
 * Blog    : sinaungoding.com
 * Email   : lepengdados@gmail.com
 * Github  : 0d3ng
 * Hp      : 085878554150
 */
package com.sinaungoding.pertemuan4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author od3ng
 */
public class Praktikum4Controller {

    private Praktikum4 view;

    public Praktikum4Controller(Praktikum4 view) {
        this.view = view;
        this.view.setLocationRelativeTo(null);
        this.view.getBtBaca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proses();
            }
        });
        this.view.getBtSimpan().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    private void proses() {
        JFileChooser loadFile = view.getLoadFile();
        StyledDocument doc = view.getTxtPane().getStyledDocument();
        if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view)) {
            BufferedInputStream reader = null;
            try {
                reader = new BufferedInputStream(new FileInputStream(loadFile.getSelectedFile()));
                doc.insertString(0, "", null);
                int temp = 0;
                List<Integer> list = new ArrayList<>();
                while ((temp=reader.read()) != -1) {                    
                    list.add(temp);
                }
                if (!list.isEmpty()) {
                    byte[] dt = new byte[list.size()];
                    int i = 0;
                    for (Integer integer : list) {
                        dt[i]=integer.byteValue();
                        i++;
                    }
                    doc.insertString(doc.getLength(), new String(dt), null);
                    JOptionPane.showMessageDialog(view, "File berhasil dibaca.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Praktikum4Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | BadLocationException ex) {
                Logger.getLogger(Praktikum4Controller.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Praktikum4Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void save() {
        JFileChooser loadFile = view.getLoadFile();
        if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view)) {
            BufferedOutputStream writer = null;
            try {
                String contents = view.getTxtPane().getText();
                if (contents != null && !contents.isEmpty()) {
                    writer = new BufferedOutputStream(new FileOutputStream(loadFile.getSelectedFile()));
                    writer.write(contents.getBytes());
                    JOptionPane.showMessageDialog(view, "File berhasil ditulis.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Praktikum4Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Praktikum4Controller.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        view.getTxtPane().setText("");
                    } catch (IOException ex) {
                        Logger.getLogger(Praktikum4Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

}
