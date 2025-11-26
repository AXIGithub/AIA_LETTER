/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aia_letter_java;


import aia.controller.Directory;
import aia.controller.FileChooser;
import aia.controller.PathDirectory;
import aia.controller.Processing;
import aia.model.CreateDirectoryModel;
import com.itextpdf.text.DocumentException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author Ratino
 */
public class View_AIA extends javax.swing.JFrame {
    
    Directory dir;
    private FileChooser opn = new FileChooser();
    private PathDirectory pd = new PathDirectory();
//    CreateDirectoryModel createDir = new CreateDirectoryModel(".");
//    BsController bsc = new BsController(createDir);
    private String inputDir = new String();
    private String fileName = new String();
    private String currenctDir = new String();
    private String cycle = new String();
    private String category = new String();
    public String paperDirectory = new String();
    public String fontsDirectory = new String();
    public String tempDirectory = new String();
    public String readyToPrintCyDirectory = new String();
    public String readyToPrintCyPrintDirectory = new String();
    public String readyToPrintCyLogScanDirectory = new String();
    public String readyToPrintCyLogProdDirectory = new String();
    public String readyToPrintCyReportDirectory = new String();
    public String readyToPrintCySortingDirectory = new String();

    /**
     * Creates new form View_BNI
     */
    public View_AIA() {
        initComponents();
        this.setLocationRelativeTo(null);
        init();
        try {
            currenctDir = ""+new java.io.File(".").getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(View_AIA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void init(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/Icon/LOGO AIA.png"));
        Image img = icon.getImage().getScaledInstance(jLabel2Icon.getWidth(), jLabel2Icon.getHeight(), Image.SCALE_SMOOTH);
        jLabel2Icon.setIcon(new ImageIcon(img)); jLabel2Icon3.setIcon(new ImageIcon(img));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/Icon/iconDate.png"));
        Image img2 = icon2.getImage().getScaledInstance(jButton4.getWidth(), jButton4.getHeight(), Image.SCALE_SMOOTH);
        jButton4.setIcon(new ImageIcon(img2));
        jButton7.setIcon(new ImageIcon(img2));
        jComboBox1SA.setEnabled(true);  jButton2.setEnabled(true); //jComboBox2Reg.setEnabled(false);
        jButton3.setEnabled(false);jButton2.setEnabled(false); 
//        jComboBox1SA.setVisible(false);
        SetTime();
        initializeShortcuts();
    }
    
    public void initializeShortcuts() {
        JPanel panel = (JPanel) this.getContentPane();
        KeyStroke exitKeyStroke = KeyStroke.getKeyStroke("control Q"); // CTRL Q
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(exitKeyStroke, "exitApp");

        ActionMap actionMap = panel.getActionMap();
        actionMap.put("exitApp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });

        KeyStroke buttonKeyStroke = KeyStroke.getKeyStroke("control B"); // CTRL B
        inputMap.put(buttonKeyStroke, "buttonAction");
        actionMap.put("buttonAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed(e); 
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jComboBox1SA = new javax.swing.JComboBox<>();
        jComboBox2Reg = new javax.swing.JComboBox<>();
        jLabel2Icon = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextDate = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jComboBox2Type = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jTextDateConvert = new javax.swing.JTextField();
        jLabelDate1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2Icon3 = new javax.swing.JLabel();
        textArea1 = new java.awt.TextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        dateChooser1.setTextRefernce(jTextDate);

        dateChooser2.setTextRefernce(jTextDateConvert);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Icon/BNI LOGO.png")).getImage());

        jButton1.setText("BROWSE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("PROCESS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox1SA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AGEN", "BTN", "PPWM", "SCB" }));
        jComboBox1SA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1SAActionPerformed(evt);
            }
        });

        jComboBox2Reg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ANST", "DTST" }));
        jComboBox2Reg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2RegActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(230, 230, 230));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("AIA LETTER");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabelDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jButton3.setText("SAMPLE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextDate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTextDate.setBorder(null);
        jTextDate.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDateActionPerformed(evt);
            }
        });

        jButton4.setText("...");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox2Type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=type=", "DVP", "RVP", "COUPON", "MATURE", " " }));
        jComboBox2Type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2TypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextDate, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox2Reg, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1SA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2Type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(143, 143, 143))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))
                        .addGap(7, 7, 7)
                        .addComponent(jComboBox2Reg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1SA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 38, Short.MAX_VALUE))
                    .addComponent(jLabel2Icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("CUSTODY", jPanel1);

        jButton5.setText("BROWSE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("PROCESS");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2IN1 BIRU", "2IN1 BIRU EMAS", "2IN1 EMAS", "2IN1 PREMIUM", "AMEX ( AX3 )", "BANK DKI EMAS ( B3 )", "BANK DKI PLATINUM ( B4 )", "BATIK AIR PLATINUM (BP)", "BATIK AIR WORLD (BW)", "BJB EMAS ( B1 )", "BJB PLATINUM ( B2 )", "BNI - XL PRIORITAS", "BNI AMEX GOLD REGULER ( GZ )", "CLIENT ( F3 )", "CORP INDIVIDU EMAS ( F )", "CORP INDIVIDU PLATINUM ( F5 )", "GARUDA PLATINUM ( J1 )", "GARUDA SIGNATURE ( J1 )", "GASOLINE ( F2 )", "INFINITE ( G9 )", "JCB EMAS ( JG )", "JCB PRECIOUS ( JC )", "JCB ULTIMATE (JU)", "KARTU KREDIT PEMERINTAH DOMESTIK (KKPD)", "KARTU TUNAI ( PC )", "LOTTE ( G1 )", "LOTTE PLATINUM ( L5 )", "MASTER WORLD ( MW )", "PERTAMINA EMAS ( PG )", "PERTAMINA PLATINUM ( PP )", "PLATINUM ( E )", "REGULER MB ( D )", "SILOAM HOSPITALS PLATINUM (SH)", "TELKOMSEL EMAS (C1)", "TELKOMSEL PLATINUM (C2)", "TITANIUM ( A1 )", "VISA SIGNATURE (VS)", "WORLDCUP BIRU ( N )", "WORLDCUP EMAS ( O )" }));

        jButton7.setText("...");
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextDateConvert.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextDateConvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDateConvertActionPerformed(evt);
            }
        });

        jLabelDate1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel4.setBackground(new java.awt.Color(230, 230, 230));
        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("BNI BILLING STATEMENT");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2Icon3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabelDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField3)
                        .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextDateConvert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDateConvert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2Icon3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("CONVERT PDF", jPanel2);

        jMenu2.setText("File");

        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Edit");

        jMenuItem2.setText("Undo");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Help");

        jMenuItem3.setText("About");
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            jTextField1.setText(""+opn.getDirectory());
//            jTextField1.setText(""+opn.selectDirectory());
            inputDir = pd.configurePath(jTextField1.getText());
            fileName = opn.getFileName(); // Get Name File
            jTextArea1.setText("============================================\n" + "Ready To Process...\n");
            jTextArea1.setText(jTextArea1.getText() + jTextDate.getText());
            jButton2.setEnabled(true); 
//            jComboBox1cycle.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(View_AIA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            // TODO add your handling code here:
            CreateDirectoryModel createDir = new CreateDirectoryModel();
            Processing processing = new Processing();
            this.category = jComboBox1SA.getSelectedItem().toString();
            String department = fileName.contains("SYARIAH") ? "SYARIAH" : "KONVEN";
            String type = fileName.contains("DVP") ? "DVP" : fileName.contains("KUPON") ? "KUPON" : fileName.contains("MATURITY") ? "MATURITY" : "RVP";
            String[] argumens = {inputDir + fileName, inputDir, department, type, category};
            processing.createCustody(argumens);
        } catch (DocumentException ex) {
            Logger.getLogger(View_AIA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(View_AIA.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1SAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1SAActionPerformed
        // TODO add your handling code here:
        
        jTextArea1.setText(jTextArea1.getText() + "SALING AGENT : " + jComboBox1SA.getSelectedItem().toString()+"\n");
        jButton2.setEnabled(true);
    }//GEN-LAST:event_jComboBox1SAActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        CreateDirectoryModel createDir = new CreateDirectoryModel();
        this.cycle = jTextDate.getText().substring(3, 5) + jTextDate.getText().substring(0, 2);
        jButton3.setText("FINISH");
        jButton3.setEnabled(false);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDateActionPerformed
        // TODO add your handling code here:
        
        jComboBox1SA.setSelectedItem(jTextDate.getText().substring(0, 2));
    }//GEN-LAST:event_jTextDateActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        dateChooser1.showPopup();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        dateChooser2.showPopup();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextDateConvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDateConvertActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDateConvertActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            // TODO add your handling code here:
            jTextField3.setText(""+opn.getDirectory());
            inputDir = pd.configurePath(jTextField3.getText());
            fileName = opn.getFileName();
        } catch (IOException ex) {
            Logger.getLogger(View_AIA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String getCycle = jTextDateConvert.getText().replace("-", "");
        String product = (String) jComboBox1.getSelectedItem();
        jButton6.setText("FINISH");
        jButton6.setEnabled(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox2RegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2RegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2RegActionPerformed

    private void jComboBox2TypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2TypeActionPerformed
        // TODO add your handling code here:
        jTextArea1.setText(jTextArea1.getText() + "TYPE  : " + jComboBox2Type.getSelectedItem().toString()+"\n");
    }//GEN-LAST:event_jComboBox2TypeActionPerformed

    public void SetTime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        Thread.sleep(1000);
                        Date date = new Date();
                        SimpleDateFormat tf = new SimpleDateFormat("H:mm:ss");
                        SimpleDateFormat  df = new SimpleDateFormat("EEEE, dd-MM-yyyy");
                        String time = tf.format(date);
                        String dateFormat = df.format(date);
                        //jLabel3.setText(time.split(" ")[0] + " " + time.split(" ")[1]);
                        jLabel3.setText(time); jLabel5.setText(time);
                        jLabelDate.setText(dateFormat);
                        jLabelDate1.setText(dateFormat);
                        jLabel2.setText(jLabel2.getText());
                    }                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(View_AIA.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }).start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View_AIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View_AIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View_AIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View_AIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View_AIA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox1SA;
    private javax.swing.JComboBox<String> jComboBox2Reg;
    private javax.swing.JComboBox<String> jComboBox2Type;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel2Icon;
    private javax.swing.JLabel jLabel2Icon3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelDate1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextDate;
    private javax.swing.JTextField jTextDateConvert;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private java.awt.TextArea textArea1;
    // End of variables declaration//GEN-END:variables
}
