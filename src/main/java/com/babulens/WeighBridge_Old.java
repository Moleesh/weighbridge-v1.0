package com.babulens;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import com.github.sarxos.webcam.*;
import com.github.sarxos.webcam.ds.buildin.WebcamDefaultDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.github.sarxos.webcam.ds.ipcam.IpCamStorage;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdesktop.swingx.JXDatePicker;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;

class WeighBridge_Old {
    private static final String DB_CONNECTION = "jdbc:h2:./weighdata";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "root";
    static private SerialPort comPort;

    static {
        Webcam.setDriver(new MyCompositeDriver());
    }

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final ButtonGroup buttonGroup_1 = new ButtonGroup();
    private final DateFormat dateAndTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    private final DateFormat dateAndTimeFormatPrint = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private final DateFormat dateAndTimeFormatSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DateFormat dateAndTimeFormatdate = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat dateAndTimeFormatdatep = new SimpleDateFormat("dd/MM/yyyy");
    private final DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    private final JCheckBox a1 = new JCheckBox("Sl.No");
    private final JCheckBox a1a = new JCheckBox("Dc. No");
    private final JCheckBox a1b = new JCheckBox("Dc. Date");
    private final JCheckBox aa = new JCheckBox("Customer's Name");
    private final JCheckBox aaa = new JCheckBox("Transporter's Name");
    private final JCheckBox a2 = new JCheckBox("Vehicle No");
    private final JCheckBox a3 = new JCheckBox("Material");
    private final JCheckBox a4 = new JCheckBox("No of Bags");
    private final JCheckBox a5 = new JCheckBox("Gross Wt");
    private final JCheckBox a6 = new JCheckBox("Gross Date & Time");
    private final JCheckBox a7 = new JCheckBox("Tare Wt");
    private final JCheckBox a8 = new JCheckBox("Tare Date & Time");
    private final JCheckBox a9 = new JCheckBox("Net Wt");
    private final JCheckBox a10 = new JCheckBox("Print Date & Time");
    private final JCheckBox a11 = new JCheckBox("Remarks");
    private final JCheckBox a12 = new JCheckBox("Manual");
    private final JCheckBox b1 = new JCheckBox("Bill.No");
    private final JCheckBox b2 = new JCheckBox("Reference SlNo");
    private final JCheckBox b3 = new JCheckBox("Customer Name");
    private final JCheckBox b4 = new JCheckBox("Customer Address");
    private final JCheckBox b41 = new JCheckBox("Customer Address1");
    private final JCheckBox b5 = new JCheckBox("Billing Date");
    private final JCheckBox b6 = new JCheckBox("Cost Per Unit");
    private final JCheckBox b7 = new JCheckBox("No of Units");
    private final JCheckBox b8 = new JCheckBox("VAT");
    private final JCheckBox b9 = new JCheckBox("Discount");
    private final JCheckBox b10 = new JCheckBox("Total");
    private final JCheckBox b11 = new JCheckBox("Remarks");
    private final JCheckBox b12 = new JCheckBox("Material Name");
    private final Webcam[] webcam = new Webcam[5];
    private Connection dbConnection = null;
    private BufferedImage clickedImage;
    private boolean lock = false;
    private PrintService[] printServices;
    private String[] printers;
    private Calculator calc;
    private JFrame frmBabulensWeighbridgeDesigned;
    private JTextField textFieldCharges;
    private JComboBox<String> comboBoxMaterial;
    private JTextField textFieldVehicleNo;
    private JTextField textFieldDateTime;
    private JTextField textFieldSlNo;
    private JTextField textFieldGrossWt;
    private JTextField textFieldTareWt;
    private JTextField textFieldNetWt;
    private JTextField textFieldGrossDateTime;
    private JTextField textFieldTareDateTime;
    private JTextField textFieldNetDateTime;
    private JRadioButton rdbtnGross;
    private JLabel lblWeight;
    private JTabbedPane tabbedPane;
    private JLabel title2;
    private JLabel title1;
    private JRadioButton rdbtnTare;
    private JButton btnGetGross;
    private JButton btnGetTare;
    private JButton btnTotal;
    private JButton btnGetTareSl;
    private JButton btnGetGrossSl;
    private JButton btnGetWeight;
    private JButton btnSave;
    private JButton btnPrint;
    private JRadioButton rdbtnWeighing;
    private JComboBox<String> comboBox;
    private JRadioButton rdbtnBilling;
    private JTextField textFieldDetail;
    private JTable tableReport;
    private JTextField textFieldTotalCharges;
    private JTextField textFieldtotalNetWt;
    private JXDatePicker datePicker1;
    private JXDatePicker datePicker2;
    private JLabel detail;
    private JComboBox<String> comboBoxMaterialReport;
    private JButton btnGo;
    private JButton btnSaveReport;
    private JButton btnEditReport;
    private JButton btnExportToExcel;
    private JButton btnPrintReport;
    private JTable tableMaterial;
    private JTable tableVehicleTare;
    private JTextField textFieldTitle1;
    private JTextField textFieldTitle2;
    private JTextField textFieldFooter;
    private JTextField textFieldBaudRate;
    private JTextField textFieldPortName;
    private JComboBox<String> comboBoxPrinter2;
    private JTable tableCustomer;
    private JTextField textFieldTax;
    private JButton btnResetBills;
    private JButton btnPassword;
    private JCheckBox chckbxEditEnable;
    private JCheckBox chckbxManualEntry;
    private JCheckBox chckbxExcludeCharges;
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private JTable table4;
    private JTextField textFieldReferenceSlNo;
    private JTextField textFieldBillNo;
    private JTextField textFieldCostPerunit;
    private JTextField textFieldNoOfUnits;
    private JTextField textFieldVat;
    private JTextField textFieldDiscount;
    private JTextField textFieldTotalVat;
    private JTextField textFieldTotal;
    private JTextField textFieldAmountToBePaid;
    private JLabel lblAmount;
    private JLabel lblVat;
    private JTextField labelBillTitle;
    private JComboBox<String> comboBoxCustomerName;
    private JComboBox<String> comboBoxMaterialName;
    private JButton btnSaveBill;
    private JButton btnPrintBill;
    private JComboBox<String> comboBoxPrinter1;
    private JTextField textFieldBillDateTime;
    private JTextField textFieldNoOfCopies;
    private JTextField textFieldCustomerAddress;
    private JButton button6;
    private JButton button7;
    private JXDatePicker datePicker;
    private JButton button5;
    private JButton button4;
    private JButton button3;
    private JButton button2;
    private JButton button1;
    private JTextField textFieldRemarks;
    private JButton btnCalculate;
    private JTextField textFieldCustomerAddress1;
    private JButton button8;
    private JTextField textFieldNoOfCopies1;
    private JComboBox<String> textFieldCustomerName;
    private JCheckBox chckbxRemoveBillinTab;
    private JCheckBox chckbxExcludeCustomer;
    private JCheckBox chckbxExcludeDrivers;
    private JComboBox<String> textFieldDriverName;
    private JTextField textFieldDcNo;
    private JTextField textFieldDcDate;
    private JButton btnGetDcDetails;
    private JLabel labelCamera1;
    private JLabel labelCamera2;
    private JLabel labelCamera3;
    private JLabel labelCamera4;
    private JCheckBox chckbxCamera;
    private JCheckBox chckbxSms;
    private JComboBox<String> comboBoxPrintOptionForBill;
    private JComboBox<String> comboBoxPrintOptionForWeight;
    private JTextField textFieldSMSPortName;
    private JTextField textFieldSMSBaudRate;
    private JPanel panelCameras;
    private JTextField textFieldCropWidth1;
    private JTextField textFieldCropHeight1;
    private JTextField textFieldCropX1;
    private JTextField textFieldCropY1;
    private JTextField textFieldCropWidth3;
    private JTextField textFieldCropHeight3;
    private JTextField textFieldCropX3;
    private JTextField textFieldCropY3;
    private JTextField textFieldCropWidth2;
    private JTextField textFieldCropHeight2;
    private JTextField textFieldCropX2;
    private JTextField textFieldCropY2;
    private JTextField textFieldCropWidth4;
    private JTextField textFieldCropHeight4;
    private JTextField textFieldCropX4;
    private JTextField textFieldCropY4;
    private WebcamPanel panelCamera1;
    private JCheckBox checkBoxCamera1;
    private WebcamPicker webcamPicker1;
    private WebcamPanel panelCamera2;
    private WebcamPicker webcamPicker2;
    private JCheckBox checkBoxCamera2;
    private WebcamPanel panelCamera3;
    private JCheckBox checkBoxCamera3;
    private WebcamPicker webcamPicker3;
    private WebcamPanel panelCamera4;
    private JCheckBox checkBoxCamera4;
    private WebcamPicker webcamPicker4;
    private JButton buttonUnLockCamera;
    private JComboBox<DimensionTemplate> comboBoxResolution1;
    private JComboBox<DimensionTemplate> comboBoxResolution2;
    private JComboBox<DimensionTemplate> comboBoxResolution3;
    private JComboBox<DimensionTemplate> comboBoxResolution4;
    private JButton butttonUpdateCamera4;
    private JButton butttonUpdateCamera2;
    private JButton butttonUpdateCamera3;
    private JButton butttonUpdateCamera1;
    private JButton butttonUpdateCamera;
    private boolean lock1 = false;
    private JFrame jFrame;
    private JTextField textFieldCropX11;
    private JTextField textFieldCropY11;
    private JTextField textFieldCropWidth11;
    private JTextField textFieldCropHeight11;
    private JButton btnClick;
    private JButton btnMinusGross;
    private JButton btnPlusTare;
    private JCheckBox chckbxExcludeRemarks;
    private JTextPane textPaneRemarks;
    private JCheckBox chckbxAutoCharges;
    private JCheckBox chckbxMaterialSl;
    private JCheckBox chckbxCharges;
    private JButton btnAuto;
    private JCheckBox chckbxChargecheck;
    private JTextField textFieldLine1;
    private JTextField textFieldLine2;
    private JTextField textFieldLine3;
    private JTextField textFieldSiteAt;
    private JTextField textFieldDepartmentName;
    private JTextField textFieldNameOfContractor;
    private JTextField textFieldLine4;
    private JCheckBox chckbxenableSettings2;
    private JCheckBox chckbxTareNoSlno;

    /**
     * Create the application.
     */
    private WeighBridge_Old() {
        try {
            int i = 0;
            printServices = PrintServiceLookup.lookupPrintServices(null, null);
            printers = new String[printServices.length];
            for (PrintService printer : printServices)
                printers[i++] = printer.getName();
            try {
                dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "DATABASE ALREADY OPEN\nPLZ CLOSE ALL OPEN SOFTWARE FILES\nLINE :328", "DATABASE ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (dbConnection == null) {
                System.exit(0);
            }
            // TODO start
            initialize();
            setup();
            cameraSetting();
            settings();
            initializeWeights();
            Timer t1 = new Timer(1000, e -> {
                Date date = new Date();
                textFieldDateTime.setText(dateAndTimeFormat.format(date));
                textFieldBillDateTime.setText(dateAndTimeFormat.format(date));
            });
            t1.start();
            //
            // rePrint("3");
            // printPlainSriPathyWeight();
            // close();

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "UI NOT SUPPORTED\nLINE :306", "UI ERROR", JOptionPane.ERROR_MESSAGE);
        }
        EventQueue.invokeLater(() -> {
            WeighBridge_Old window = new WeighBridge_Old();
            window.frmBabulensWeighbridgeDesigned.setVisible(true);
        });
    }

    private void setup() {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM setup");
            rs.absolute(1);
            String id = rs.getString("ID");
            Date endDate = new Date(rs.getTimestamp("ENDDATE").getTime());
            Date lastLogin = new Date(rs.getTimestamp("LASTLOGIN").getTime());
            String tempDetail = rs.getString("TEMP");

            switch (id) {
                case "0":
                    String[] buttons = {"License The Software", "Trial Period(" + endDate + ")", "Close"};
                    switch (JOptionPane.showOptionDialog(null, "Please Select a Option ?",
                            "Welcome to the \"BABULENS WEIGHBRIDGE\" Softwere", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null,
                            buttons, buttons[2])) {
                        case 0:
                            JPasswordField password = new JPasswordField(10);
                            JPanel panel = new JPanel();
                            String[] ConnectOptionNames = {"Enter", "Cancel"};
                            panel.add(new JLabel("Please the Password ? "));
                            panel.add(password);
                            JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                            char[] temp = password.getPassword();
                            boolean isCorrect;
                            boolean isCorrect2;
                            char[] correctPassword = {'0', '5', '0', '1', '1', '1'};
                            char[] correctPassword2 = {'5', '5', '5', '1', '1', '1'};
                            if (temp.length != correctPassword.length) {
                                isCorrect = false;
                            } else {
                                isCorrect = Arrays.equals(temp, correctPassword);
                            }
                            if (temp.length != correctPassword2.length) {
                                isCorrect2 = false;
                            } else {
                                isCorrect2 = Arrays.equals(temp, correctPassword2);
                            }
                            if (isCorrect) {
                                tempDetail = getUUID();
                                rs.updateString("ID", "1");
                                rs.updateString("TEMP", tempDetail);
                                rs.updateRow();
                                JOptionPane.showMessageDialog(null, "Welcome to the \"BABULENS WEIGHBRIDGE\" Softwere",
                                        "Welcome", JOptionPane.INFORMATION_MESSAGE);
                            } else if (isCorrect2) {
                                rs.updateTimestamp("ENDDATE",
                                        new java.sql.Timestamp(new Date().getTime() + 10 * (long) 8.64e+7));
                                rs.updateRow();
                                //endDate = rs.getDate("ENDDATE");
                                JOptionPane.showMessageDialog(null,
                                        "Trial Reset Successfull\n you got 10 days\n Plz Open again", "Reset",
                                        JOptionPane.INFORMATION_MESSAGE);
                                close();
                            } else {
                                JOptionPane.showMessageDialog(null, "Your Lisense is not Valid\nPlease get a Valid Lisense",
                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                close();
                            }
                            break;
                        case 1:
                            if (new Date().getTime() - lastLogin.getTime() > 0) {
                                if (endDate.getTime() - new Date().getTime() > 0) {
                                    JOptionPane.showMessageDialog(null,
                                            "Welcome to the \"BABULENS WEIGHBRIDGE\" Trial Softwere", "Welcome",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    Timer countDown = new Timer((int) (endDate.getTime() - new Date().getTime()),
                                            e -> {
                                                JOptionPane.showMessageDialog(null,
                                                        "Your Trial Lisense is over\nplease buy the lisence", "WARNING",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                                close();
                                            });
                                    countDown.start();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Your Trial Lisense is over\nplease buy the lisence",
                                            "WARNING", JOptionPane.INFORMATION_MESSAGE);
                                    close();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Your have changed your Date\nPlease correct the date to enjoy the trial version",
                                        "ERROR", JOptionPane.ERROR_MESSAGE);
                                close();
                            }
                            break;
                        default:
                            close();
                    }
                    break;
                case "1":
                    if (tempDetail.equals(getUUID())) {
                        JOptionPane.showMessageDialog(null, "Welcome to the \"BABULENS WEIGHBRIDGE\" Softwere", "Welcome",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        rs.updateString("ID", "0");
                        rs.updateRow();
                        JOptionPane.showMessageDialog(null, "Your Lisense is not Valid\nPlease get a Valid Lisense",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                        close();
                    }
                    break;
                default:
                    close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :540", "SQL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private String getUUID() {
        String tempDetail = null;
        Process process;
        try {
            process = Runtime.getRuntime().exec(new String[]{"wmic", "csproduct", "get", "UUID"});
            process.getOutputStream().close();
            Scanner scanner = new Scanner(process.getInputStream());
            scanner.next();
            tempDetail = scanner.next();
            scanner.close();
        } catch (IOException ignored) {
        }
        return tempDetail;
    }

    private void cameraSetting() {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM CAMERA");
            rs.absolute(1);
            for (int index = 0; index < webcamPicker1.getItemCount(); index++) {
                if (rs.getString("NAME").equals(webcamPicker1.getItemAt(index).toString())) {
                    webcamPicker1.setSelectedIndex(index);
                    break;
                }
            }
            try {
                Dimension[] dim = webcamPicker1.getSelectedWebcam().getViewSizes();
                comboBoxResolution1.removeAllItems();
                for (Dimension i : dim) {
                    comboBoxResolution1.addItem(new DimensionTemplate(i));
                }
            } catch (NullPointerException | WebcamException ignored) {
            }
            for (int index = 0; index < comboBoxResolution1.getItemCount(); index++) {
                if (rs.getString("RESOLUTION").equals(comboBoxResolution1.getItemAt(index).toString())) {
                    comboBoxResolution1.setSelectedIndex(index);
                    break;
                }
            }
            textFieldCropX1.setText(Integer.toString(rs.getInt("CROPX")));
            textFieldCropY1.setText(Integer.toString(rs.getInt("CROPY")));
            textFieldCropWidth1.setText(Integer.toString(rs.getInt("CROPWIDTH")));
            textFieldCropHeight1.setText(Integer.toString(rs.getInt("CROPHEIGHT")));

            rs.absolute(2);
            for (int index = 0; index < webcamPicker2.getItemCount(); index++) {
                if (rs.getString("NAME").equals(webcamPicker2.getItemAt(index).toString())) {
                    webcamPicker2.setSelectedIndex(index);
                    break;
                }
            }

            try {
                Dimension[] dim = webcamPicker2.getSelectedWebcam().getViewSizes();
                comboBoxResolution2.removeAllItems();
                for (Dimension i : dim) {
                    comboBoxResolution2.addItem(new DimensionTemplate(i));
                }
            } catch (NullPointerException | WebcamException ignored) {
            }

            for (int index = 0; index < comboBoxResolution2.getItemCount(); index++) {
                if (rs.getString("RESOLUTION").equals(comboBoxResolution2.getItemAt(index).toString())) {
                    comboBoxResolution2.setSelectedIndex(index);
                    break;
                }
            }
            textFieldCropX2.setText(Integer.toString(rs.getInt("CROPX")));
            textFieldCropY2.setText(Integer.toString(rs.getInt("CROPY")));
            textFieldCropWidth2.setText(Integer.toString(rs.getInt("CROPWIDTH")));
            textFieldCropHeight2.setText(Integer.toString(rs.getInt("CROPHEIGHT")));

            rs.absolute(3);
            for (int index = 0; index < webcamPicker3.getItemCount(); index++) {
                if (rs.getString("NAME").equals(webcamPicker3.getItemAt(index).toString())) {
                    webcamPicker3.setSelectedIndex(index);
                    break;
                }
            }
            try {
                Dimension[] dim = webcamPicker3.getSelectedWebcam().getViewSizes();
                comboBoxResolution3.removeAllItems();
                for (Dimension i : dim) {
                    comboBoxResolution3.addItem(new DimensionTemplate(i));
                }
            } catch (NullPointerException | WebcamException ignored) {
            }
            for (int index = 0; index < comboBoxResolution3.getItemCount(); index++) {
                if (rs.getString("RESOLUTION").equals(comboBoxResolution3.getItemAt(index).toString())) {
                    comboBoxResolution3.setSelectedIndex(index);
                    break;
                }
            }
            textFieldCropX3.setText(Integer.toString(rs.getInt("CROPX")));
            textFieldCropY3.setText(Integer.toString(rs.getInt("CROPY")));
            textFieldCropWidth3.setText(Integer.toString(rs.getInt("CROPWIDTH")));
            textFieldCropHeight3.setText(Integer.toString(rs.getInt("CROPHEIGHT")));

            rs.absolute(4);
            for (int index = 0; index < webcamPicker4.getItemCount(); index++) {
                if (rs.getString("NAME").equals(webcamPicker4.getItemAt(index).toString())) {
                    webcamPicker4.setSelectedIndex(index);
                    break;
                }
            }
            try {
                Dimension[] dim = webcamPicker4.getSelectedWebcam().getViewSizes();
                comboBoxResolution4.removeAllItems();
                for (Dimension i : dim) {
                    comboBoxResolution4.addItem(new DimensionTemplate(i));
                }
            } catch (NullPointerException | WebcamException ignored) {
            }
            for (int index = 0; index < comboBoxResolution4.getItemCount(); index++) {
                if (rs.getString("RESOLUTION").equals(comboBoxResolution4.getItemAt(index).toString())) {
                    comboBoxResolution4.setSelectedIndex(index);
                    break;
                }
            }
            textFieldCropX4.setText(Integer.toString(rs.getInt("CROPX")));
            textFieldCropY4.setText(Integer.toString(rs.getInt("CROPY")));
            textFieldCropWidth4.setText(Integer.toString(rs.getInt("CROPWIDTH")));
            textFieldCropHeight4.setText(Integer.toString(rs.getInt("CROPHEIGHT")));

            rs.absolute(5);
            textFieldCropX11.setText(Integer.toString(rs.getInt("CROPX")));
            textFieldCropY11.setText(Integer.toString(rs.getInt("CROPY")));
            textFieldCropWidth11.setText(Integer.toString(rs.getInt("CROPWIDTH")));
            textFieldCropHeight11.setText(Integer.toString(rs.getInt("CROPHEIGHT")));

            rs.absolute(1);
            checkBoxCamera1.setSelected(rs.getBoolean("ENABLE"));
            rs.absolute(2);
            checkBoxCamera2.setSelected(rs.getBoolean("ENABLE"));
            rs.absolute(3);
            checkBoxCamera3.setSelected(rs.getBoolean("ENABLE"));
            rs.absolute(4);
            checkBoxCamera4.setSelected(rs.getBoolean("ENABLE"));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :414", "SQL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void settings() {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
            rs.absolute(1);
            textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
            textFieldReferenceSlNo.setText(Integer.toString(rs.getInt("SLNO")));
            textFieldBillNo.setText(Integer.toString(rs.getInt("BILLNO")));
            textFieldTitle1.setText(rs.getString("TITLE1"));
            title1.setText(rs.getString("TITLE1"));
            textFieldTitle2.setText(rs.getString("TITLE2"));
            title2.setText(rs.getString("TITLE2"));
            textFieldFooter.setText(rs.getString("FOOTER"));
            textFieldBaudRate.setText(Integer.toString(rs.getInt("BAUDRATE")));
            textFieldPortName.setText(rs.getString("PORTNAME"));
            textFieldTax.setText(Double.toString(rs.getDouble("TAX")));
            lblVat.setText("Tax " + rs.getDouble("TAX") + " %");
            textFieldNoOfCopies.setText(Integer.toString(rs.getInt("COPIES")));
            textFieldNoOfCopies1.setText(Integer.toString(rs.getInt("COPIES1")));
            comboBoxPrintOptionForWeight.getModel().setSelectedItem(rs.getString("PRINTOPTIONFORWEIGHT"));
            comboBoxPrintOptionForBill.getModel().setSelectedItem(rs.getString("PRINTOPTIONFORBILL"));
            chckbxExcludeCharges.setSelected(rs.getBoolean("EXCLUDECHARGES"));
            chckbxExcludeDrivers.setSelected(rs.getBoolean("EXCLUDEDRIVER"));
            chckbxExcludeCustomer.setSelected(rs.getBoolean("EXCLUDECUSTOMERS"));
            chckbxExcludeRemarks.setSelected(rs.getBoolean("EXCLUDEREMARKS"));
            chckbxRemoveBillinTab.setSelected(rs.getBoolean("REMOVEBILLING"));
            chckbxAutoCharges.setSelected(rs.getBoolean("AUTOCHARGES"));
            chckbxCharges.setSelected(rs.getBoolean("AUTOCHARGES1"));
            chckbxMaterialSl.setSelected(rs.getBoolean("MATERIALSL"));
            chckbxCamera.setSelected(rs.getBoolean("CAMERA"));
            chckbxSms.setSelected(rs.getBoolean("SMS"));
            textFieldSMSBaudRate.setText(Integer.toString(rs.getInt("SMSBAUDRATE")));
            textFieldSMSPortName.setText(rs.getString("SMSPORTNAME"));
            textFieldLine1.setText(rs.getString("LINE1"));
            textFieldLine2.setText(rs.getString("LINE2"));
            textFieldLine3.setText(rs.getString("LINE3"));
            textFieldLine4.setText(rs.getString("LINE4"));
            textFieldNameOfContractor.setText(rs.getString("NAMEOFCONTRACTOR"));
            textFieldDepartmentName.setText(rs.getString("DEPARTMENTNAME"));
            textFieldSiteAt.setText(rs.getString("SITEAT"));
            chckbxTareNoSlno.setSelected(rs.getBoolean("TARENOSLNO"));

            if (((DefaultComboBoxModel<?>) comboBoxPrinter1.getModel()).getIndexOf(rs.getString("PRINTER1")) == -1)
                JOptionPane.showMessageDialog(null, "Please Check the Printer 1 Settings");
            else
                comboBoxPrinter1.getModel().setSelectedItem(rs.getString("PRINTER1"));
            if (((DefaultComboBoxModel<?>) comboBoxPrinter2.getModel()).getIndexOf(rs.getString("PRINTER2")) == -1)
                JOptionPane.showMessageDialog(null, "Please Check the Printer 2 Settings");
            else
                comboBoxPrinter2.getModel().setSelectedItem(rs.getString("PRINTER2"));
            rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
            DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
            model.setRowCount(0);
            comboBoxCustomerName.removeAllItems();
            textFieldCustomerName.removeAllItems();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("CUSTOMER"), rs.getString("CUSTOMERADDRESS"),
                        rs.getString("CUSTOMERADDRESS1")});
                comboBoxCustomerName.addItem(rs.getString("CUSTOMER"));
                textFieldCustomerName.addItem(rs.getString("CUSTOMER"));
                comboBoxCustomerName.setSelectedIndex(-1);
                textFieldCustomerName.setSelectedIndex(-1);
                textFieldCustomerAddress.setText("");
                textFieldCustomerAddress1.setText("");
            }
            rs = stmt.executeQuery("SELECT * FROM TRANSPORTER");
            textFieldDriverName.removeAllItems();
            while (rs.next()) {
                textFieldDriverName.addItem(rs.getString("TRANSPORTER"));
                textFieldDriverName.setSelectedIndex(-1);
            }
            rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
            model = (DefaultTableModel) tableVehicleTare.getModel();
            model.setRowCount(0);
            while (rs.next())
                model.addRow(new Object[]{rs.getString("VEHICLENO"), rs.getInt("TAREWT"),
                        dateAndTimeFormat.format(new Date(dateAndTimeFormatSql
                                .parse(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME")).getTime()))});
            rs = stmt.executeQuery("SELECT * FROM MATERIALS ORDER BY KEY");
            model = (DefaultTableModel) tableMaterial.getModel();
            model.setRowCount(0);
            comboBoxMaterial.removeAllItems();
            comboBoxMaterialName.removeAllItems();
            comboBoxMaterialReport.removeAllItems();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("KEY"), rs.getString("MATERIALS"), rs.getDouble("COST")});
                comboBoxMaterial.addItem(rs.getString("MATERIALS"));
                comboBoxMaterial.setSelectedIndex(-1);
                comboBoxMaterialName.addItem(rs.getString("MATERIALS"));
                comboBoxMaterialName.setSelectedIndex(-1);
                comboBoxMaterialReport.addItem(rs.getString("MATERIALS"));
                comboBoxMaterialReport.setSelectedIndex(-1);
                textFieldCostPerunit.setText("0");
            }
            rs = stmt.executeQuery("SELECT * FROM BILLPRITER");
            rs.absolute(1);
            model = (DefaultTableModel) table1.getModel();
            for (int i = 1; i <= 5; i++)
                model.setValueAt(rs.getString("A" + i), i - 1, 0);
            model = (DefaultTableModel) table2.getModel();
            for (int i = 1; i <= 5; i++)
                model.setValueAt(rs.getString("B" + i), i - 1, 0);
            model = (DefaultTableModel) table3.getModel();
            for (int i = 1; i <= 6; i++)
                model.setValueAt(rs.getString("C" + i), i - 1, 0);
            model = (DefaultTableModel) table4.getModel();
            for (int i = 1; i <= 6; i++)
                model.setValueAt(rs.getString("D" + i), i - 1, 0);
            labelBillTitle.setText(rs.getString("TITLE"));
            lock1 = true;
            cameraEvent();
            billEvent();
            lock1 = false;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :806", "SQL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSettings() {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
            rs.absolute(1);
            rs.updateString("TITLE1", textFieldTitle1.getText());
            rs.updateString("TITLE2", textFieldTitle2.getText());
            rs.updateString("FOOTER", textFieldFooter.getText());
            rs.updateBoolean("PRINTOPTIONFORWEIGHT", chckbxExcludeCharges.isSelected());
            rs.updateBoolean("PRINTOPTIONFORBILL", chckbxExcludeDrivers.isSelected());
            rs.updateBoolean("EXCLUDECUSTOMERS", chckbxExcludeCustomer.isSelected());
            rs.updateString("PRINTOPTIONFORWEIGHT", (String) comboBoxPrintOptionForWeight.getSelectedItem());
            rs.updateString("PRINTOPTIONFORBILL", (String) comboBoxPrintOptionForBill.getSelectedItem());
            rs.updateBoolean("REMOVEBILLING", chckbxRemoveBillinTab.isSelected());
            rs.updateInt("BAUDRATE", Integer.parseInt("0" + textFieldBaudRate.getText().replaceAll("[^0-9]", "")));
            rs.updateString("PORTNAME", textFieldPortName.getText());
            rs.updateDouble("TAX", Double.parseDouble("0" + textFieldTax.getText().replaceAll("[^.0-9]", "")));
            rs.updateString("PRINTER1", (String) comboBoxPrinter1.getSelectedItem());
            rs.updateString("PRINTER2", (String) comboBoxPrinter2.getSelectedItem());
            rs.updateInt("COPIES", Integer.parseInt("0" + textFieldNoOfCopies.getText().replaceAll("[^0-9]", "")));
            rs.updateInt("COPIES1", Integer.parseInt("0" + textFieldNoOfCopies1.getText().replaceAll("[^0-9]", "")));
            rs.updateBoolean("EXCLUDECHARGES", chckbxExcludeCharges.isSelected());
            rs.updateBoolean("EXCLUDEDRIVER", chckbxExcludeDrivers.isSelected());
            rs.updateBoolean("EXCLUDEREMARKS", chckbxExcludeRemarks.isSelected());
            rs.updateBoolean("AUTOCHARGES", chckbxAutoCharges.isSelected());
            rs.updateBoolean("AUTOCHARGES1", chckbxCharges.isSelected());
            rs.updateBoolean("MATERIALSL", chckbxMaterialSl.isSelected());
            rs.updateBoolean("SMS", chckbxSms.isSelected());
            rs.updateBoolean("CAMERA", chckbxCamera.isSelected());
            rs.updateInt("SMSBAUDRATE",
                    Integer.parseInt("0" + textFieldSMSBaudRate.getText().replaceAll("[^0-9]", "")));
            rs.updateString("SMSPORTNAME", textFieldSMSPortName.getText().toUpperCase());
            rs.updateString("LINE1", textFieldLine1.getText());
            rs.updateString("LINE2", textFieldLine2.getText());
            rs.updateString("LINE3", textFieldLine3.getText());
            rs.updateString("LINE4", textFieldLine4.getText());
            rs.updateString("NAMEOFCONTRACTOR", textFieldNameOfContractor.getText());
            rs.updateString("DEPARTMENTNAME", textFieldDepartmentName.getText());
            rs.updateString("SITEAT", textFieldSiteAt.getText());
            rs.updateBoolean("TARENOSLNO", chckbxTareNoSlno.isSelected());
            rs.updateRow();
            PreparedStatement pstmt = dbConnection.prepareStatement("DELETE FROM CUSTOMER");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
            DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
            for (int i = 1; i <= model.getRowCount(); i++) {
                rs.moveToInsertRow();
                rs.updateString("CUSTOMER", (String) model.getValueAt(i - 1, 0));
                rs.updateString("CUSTOMERADDRESS", (String) model.getValueAt(i - 1, 1));
                rs.updateString("CUSTOMERADDRESS1", (String) model.getValueAt(i - 1, 2));
                rs.updateInt("KEY", i);
                rs.insertRow();
            }
            pstmt = dbConnection.prepareStatement("DELETE FROM VEHICLETARES");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
            model = (DefaultTableModel) tableVehicleTare.getModel();
            for (int i = 1; i <= model.getRowCount(); i++) {
                rs.moveToInsertRow();
                rs.updateString("VEHICLENO", (String) model.getValueAt(i - 1, 0));
                rs.updateInt("TAREWT", Integer.parseInt(("0" + model.getValueAt(i - 1, 1)).replaceAll("[^0-9]", "")));
                Date date = dateAndTimeFormat.parse("" + model.getValueAt(i - 1, 2));
                rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                rs.updateTime("TARETIME", new java.sql.Time(date.getTime()));
                rs.updateInt("KEY", i);
                rs.insertRow();
            }
            pstmt = dbConnection.prepareStatement("DELETE FROM MATERIALS");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM MATERIALS");
            model = (DefaultTableModel) tableMaterial.getModel();
            for (int i = 1; i <= model.getRowCount(); i++) {
                rs.moveToInsertRow();
                rs.updateString("MATERIALS", model.getValueAt(i - 1, 1).toString().toUpperCase());
                rs.updateDouble("COST",
                        Double.parseDouble(("0" + model.getValueAt(i - 1, 2)).replaceAll("[^.0-9]", "")));
                rs.updateInt("KEY", (int) model.getValueAt(i - 1, 0));
                rs.insertRow();
            }
            settings();
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :477", "SQL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    @SuppressWarnings({"unlikely-arg-type", "StatementWithEmptyBody", "UnusedReturnValue"})
    private void initialize() {
        a1.setSelected(true);
        aa.setSelected(true);
        a2.setSelected(true);
        a3.setSelected(true);
        a4.setSelected(true);
        a5.setSelected(true);
        a7.setSelected(true);
        a9.setSelected(true);
        a10.setSelected(true);
        b1.setSelected(true);
        b2.setSelected(true);
        b3.setSelected(true);
        b5.setSelected(true);
        b6.setSelected(true);
        b7.setSelected(true);
        b10.setSelected(true);
        b12.setSelected(true);
        frmBabulensWeighbridgeDesigned = new JFrame();
        frmBabulensWeighbridgeDesigned.getContentPane().setBackground(new Color(0, 255, 127));
        frmBabulensWeighbridgeDesigned.setBounds(new Rectangle(100, 100, 1280, 768));
        frmBabulensWeighbridgeDesigned.setExtendedState(Frame.MAXIMIZED_BOTH);
        frmBabulensWeighbridgeDesigned.setUndecorated(true);
        frmBabulensWeighbridgeDesigned.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/logo.bmp"));
        frmBabulensWeighbridgeDesigned.setTitle("BABULENS WEIGHBRIDGE designed by \"BABULENS ENTERPRISES\"");
        frmBabulensWeighbridgeDesigned.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmBabulensWeighbridgeDesigned.getContentPane().setLayout(null);

        JLabel title = new JLabel("BABULENS WEIGHBRIDGE");
        title.setForeground(new Color(0, 0, 255));
        title.setBounds(10, 11, 300, 30);
        title.setFont(new Font("Algerian", Font.ITALIC, 25));
        frmBabulensWeighbridgeDesigned.getContentPane().add(title);

        JButton close = new JButton("Close");
        close.setFocusable(false);
        close.setBounds(646, 11, 100, 30);
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addActionListener(arg0 -> close());
        close.setFont(new Font("Times New Roman", Font.BOLD, 20));
        frmBabulensWeighbridgeDesigned.getContentPane().add(close);

        title1 = new JLabel("title1");
        title1.setForeground(new Color(0, 0, 255));
        title1.setBackground(new Color(0, 255, 127));
        title1.setBounds(10, 52, 1260, 25);
        title1.setHorizontalAlignment(SwingConstants.CENTER);
        title1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
        frmBabulensWeighbridgeDesigned.getContentPane().add(title1);

        title2 = new JLabel("title2");
        title2.setForeground(new Color(0, 0, 255));
        title2.setBackground(new Color(0, 255, 127));
        title2.setBounds(10, 78, 1260, 25);
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        title2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
        frmBabulensWeighbridgeDesigned.getContentPane().add(title2);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(new Color(0, 255, 127));
        tabbedPane.setFocusable(false);
        tabbedPane.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
        tabbedPane.setBounds(10, 103, 1260, 654);
        frmBabulensWeighbridgeDesigned.getContentPane().add(tabbedPane);

        JPanel panelWeighing = new JPanel();
        panelWeighing.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("          Weighing          ", null, panelWeighing, null);
        panelWeighing.setLayout(null);

        lblWeight = new JLabel("0");
        lblWeight.setForeground(new Color(0, 0, 255));
        lblWeight.setHorizontalAlignment(SwingConstants.CENTER);
        lblWeight.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 200));
        lblWeight.setBounds(452, 11, 666, 164);
        panelWeighing.add(lblWeight);

        JLabel lblKg_1 = new JLabel("Kg");
        lblKg_1.setForeground(new Color(0, 0, 255));
        lblKg_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblKg_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 99));
        lblKg_1.setBounds(1085, 54, 160, 147);
        panelWeighing.add(lblKg_1);

        JLabel lblSlNo = new JLabel("Sl No");
        lblSlNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblSlNo.setBounds(50, 150, 175, 25);
        panelWeighing.add(lblSlNo);

        JLabel lblDateTime = new JLabel("Date & Time");
        lblDateTime.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDateTime.setBounds(50, 250, 175, 25);
        panelWeighing.add(lblDateTime);

        JLabel lblVehicleNo = new JLabel("Vehicle No");
        lblVehicleNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblVehicleNo.setBounds(50, 300, 175, 25);
        panelWeighing.add(lblVehicleNo);

        JLabel lblMaterial = new JLabel("Material");
        lblMaterial.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblMaterial.setBounds(50, 350, 175, 25);
        panelWeighing.add(lblMaterial);

        JLabel lblCharges = new JLabel("No of Bags");
        lblCharges.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCharges.setBounds(50, 400, 90, 25);
        panelWeighing.add(lblCharges);

        JLabel lblGrossWt = new JLabel("Gross Wt");
        lblGrossWt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblGrossWt.setBounds(490, 300, 75, 25);
        panelWeighing.add(lblGrossWt);

        JLabel lblTareWt = new JLabel("Tare Wt");
        lblTareWt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTareWt.setBounds(490, 350, 75, 25);
        panelWeighing.add(lblTareWt);

        JLabel lblNetWt = new JLabel("Nett Wt");
        lblNetWt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblNetWt.setBounds(490, 400, 75, 25);
        panelWeighing.add(lblNetWt);

        rdbtnGross = new JRadioButton("Gross");
        rdbtnGross.setBackground(new Color(0, 255, 127));
        rdbtnGross.addActionListener(arg0 -> {
            comboBoxMaterial.setEnabled(true);
            comboBoxMaterial.setSelectedIndex(-1);
            if (chckbxExcludeCustomer.isSelected())
                if (chckbxExcludeDrivers.isSelected())
                    textFieldVehicleNo.requestFocus();
                else
                    textFieldDriverName.requestFocus();
            else
                textFieldCustomerName.requestFocus();
        });
        rdbtnGross.setSelected(true);
        buttonGroup.add(rdbtnGross);
        rdbtnGross.setFocusable(false);
        rdbtnGross.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnGross.setBounds(75, 25, 100, 25);
        panelWeighing.add(rdbtnGross);

        rdbtnTare = new JRadioButton("Tare");
        rdbtnTare.setBackground(new Color(0, 255, 127));
        rdbtnTare.addActionListener(arg0 -> {
            // comboBoxMaterial.setEnabled(false);
            comboBoxMaterial.getModel().setSelectedItem("EMPTY");
            if (chckbxExcludeCustomer.isSelected())
                if (chckbxExcludeDrivers.isSelected())
                    textFieldVehicleNo.requestFocus();
                else
                    textFieldDriverName.requestFocus();
            else
                textFieldCustomerName.requestFocus();
        });
        buttonGroup.add(rdbtnTare);
        rdbtnTare.setFocusable(false);
        rdbtnTare.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnTare.setBounds(75, 75, 100, 25);
        panelWeighing.add(rdbtnTare);

        textFieldCharges = new JTextField();
        textFieldCharges.addActionListener(e -> {
            if (chckbxExcludeRemarks.isSelected())
                btnGetWeight.requestFocus();
            else
                textPaneRemarks.requestFocus();
        });
        textFieldCharges.setDisabledTextColor(Color.BLACK);
        textFieldCharges.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCharges.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldCharges.setBounds(240, 400, 175, 25);
        panelWeighing.add(textFieldCharges);
        textFieldCharges.setColumns(10);

        textFieldCustomerName = new JComboBox<>();
        textFieldCustomerName.setEditable(true);
        textFieldCustomerName.addActionListener(arg0 -> {
            comboBoxCustomerName.setSelectedItem(textFieldCustomerName.getSelectedItem());
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE CUSTOMER LIKE '"
                        + comboBoxCustomerName.getSelectedItem() + "'");
                if (rs.next()) {
                    textFieldCustomerAddress.setText(rs.getString("CUSTOMERADDRESS"));
                    textFieldCustomerAddress1.setText(rs.getString("CUSTOMERADDRESS1"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1406", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (chckbxExcludeDrivers.isSelected())
                textFieldVehicleNo.requestFocus();
            else
                textFieldDriverName.requestFocus();

        });
        textFieldCustomerName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCustomerName.setBounds(240, 200, 175, 25);
        panelWeighing.add(textFieldCustomerName);

        comboBoxMaterial = new JComboBox<>();
        comboBoxMaterial.addActionListener(e -> {
            if (comboBoxMaterial.getActionCommand().equals("comboBoxEdited")) {
                if (chckbxMaterialSl.isSelected()) {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT MATERIALS FROM MATERIALS where KEY ="
                                + comboBoxMaterial.getEditor().getItem());
                        if (rs.next())
                            comboBoxMaterial.setSelectedItem(rs.getString("MATERIALS"));
                    } catch (SQLException | NumberFormatException ignored) {
                    }
                }
                comboBoxMaterial.setSelectedItem(Objects.requireNonNull(comboBoxMaterial.getSelectedItem()).toString().toUpperCase());

                textFieldCharges.requestFocus();
                if (chckbxExcludeCharges.isSelected())
                    if (chckbxExcludeRemarks.isSelected())
                        btnGetWeight.requestFocus();
                    else
                        textPaneRemarks.requestFocus();
            }
        });
        comboBoxMaterial.setEditable(true);
        comboBoxMaterial.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxMaterial.setBounds(240, 347, 175, 30);
        panelWeighing.add(comboBoxMaterial);

        textFieldVehicleNo = new JTextField();
        textFieldVehicleNo.addActionListener(arg0 -> {
            textFieldVehicleNo.setText(textFieldVehicleNo.getText().toUpperCase().replaceAll(" ", ""));
            if (!chckbxTareNoSlno.isSelected()) {
                if (rdbtnGross.isSelected()) {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLETARES WHERE VEHICLENO LIKE '"
                                + textFieldVehicleNo.getText() + "'");
                        if (rs.next()) {
                            int response = JOptionPane.showConfirmDialog(null,
                                    "Please Select Yes to Enter the Stored tare Weight ?", "Tare Weight Available",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.YES_OPTION) {
                                textFieldTareDateTime
                                        .setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                                if (textFieldTareDateTime.getText().equals("null null"))
                                    textFieldTareDateTime.setText("");
                                else
                                    textFieldTareDateTime
                                            .setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql
                                                    .parse(textFieldTareDateTime.getText()).getTime())));
                                textFieldTareWt.setText(Integer.toString(rs.getInt("TAREWT")));
                            }
                        }
                    } catch (SQLException | ParseException e) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :680",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE VEHICLENO LIKE '"
                                + textFieldVehicleNo.getText() + "'");
                        if (rs.last())
                            if (rs.getInt("TAREWT") == 0) {
                                int response = JOptionPane.showConfirmDialog(null,
                                        "Please Select Yes to Enter the last gross Weight ?",
                                        "Gross Weight Available", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                if (response == JOptionPane.YES_OPTION) {
                                    textFieldGrossDateTime
                                            .setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));
                                    if (textFieldGrossDateTime.getText().equals("null null"))
                                        textFieldGrossDateTime.setText("");
                                    else
                                        textFieldGrossDateTime
                                                .setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql
                                                        .parse(textFieldGrossDateTime.getText()).getTime())));
                                    textFieldGrossWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                                    comboBoxMaterial.setSelectedItem(rs.getString("MATERIAL"));
                                }
                            }
                    } catch (SQLException | ParseException e) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :680",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                if (rdbtnTare.isSelected()) {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE VEHICLENO LIKE '"
                                + textFieldVehicleNo.getText() + "'");
                        if (rs.last())
                            if (rs.getInt("TAREWT") == 0) {
                                int response = JOptionPane.showConfirmDialog(null,
                                        "Please Select Yes to Enter the last gross Weight ?",
                                        "Gross Weight Available", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                if (response == JOptionPane.YES_OPTION) {
                                    textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
                                    textFieldGrossDateTime
                                            .setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));
                                    if (textFieldGrossDateTime.getText().equals("null null"))
                                        textFieldGrossDateTime.setText("");
                                    else
                                        textFieldGrossDateTime
                                                .setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql
                                                        .parse(textFieldGrossDateTime.getText()).getTime())));
                                    textFieldGrossWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                                    comboBoxMaterial.setSelectedItem(rs.getString("MATERIAL"));
                                }
                            }
                    } catch (SQLException | ParseException e) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :680",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {

                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE VEHICLENO LIKE '"
                                + textFieldVehicleNo.getText() + "'");
                        if (rs.last())
                            if (rs.getInt("GROSSWT") == 0) {
                                int response = JOptionPane.showConfirmDialog(null,
                                        "Please Select Yes to Enter the last tare Weight ?",
                                        "Tare Weight Available", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                if (response == JOptionPane.YES_OPTION) {
                                    textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
                                    textFieldTareDateTime
                                            .setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                                    if (textFieldTareDateTime.getText().equals("null null"))
                                        textFieldTareDateTime.setText("");
                                    else
                                        textFieldTareDateTime
                                                .setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql
                                                        .parse(textFieldTareDateTime.getText()).getTime())));
                                    textFieldTareWt.setText(Integer.toString(rs.getInt("TAREWT")));

                                }
                            }
                    } catch (SQLException | ParseException e) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :680",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
            if (comboBoxMaterial.isEditable())
                comboBoxMaterial.requestFocus();
            else {
                textFieldCharges.requestFocus();
                if (chckbxExcludeCharges.isSelected())
                    if (chckbxExcludeRemarks.isSelected())
                        btnGetWeight.requestFocus();
                    else
                        textPaneRemarks.requestFocus();
            }
        });
        textFieldVehicleNo.setDisabledTextColor(Color.BLACK);
        textFieldVehicleNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldVehicleNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldVehicleNo.setColumns(10);
        textFieldVehicleNo.setBounds(240, 297, 175, 30);
        panelWeighing.add(textFieldVehicleNo);

        textFieldDateTime = new JTextField();
        textFieldDateTime.setEnabled(false);
        textFieldDateTime.setDisabledTextColor(Color.BLACK);
        textFieldDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldDateTime.setColumns(10);
        textFieldDateTime.setBounds(240, 250, 175, 25);
        panelWeighing.add(textFieldDateTime);

        textFieldSlNo = new JTextField();
        textFieldSlNo.setEnabled(false);
        textFieldSlNo.setDisabledTextColor(Color.BLACK);
        textFieldSlNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSlNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldSlNo.setColumns(10);
        textFieldSlNo.setBounds(237, 141, 175, 25);
        panelWeighing.add(textFieldSlNo);

        textFieldGrossWt = new JTextField();
        textFieldGrossWt.setDisabledTextColor(Color.BLACK);
        textFieldGrossWt.setText("0");
        textFieldGrossWt.setEnabled(false);
        textFieldGrossWt.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldGrossWt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldGrossWt.setColumns(10);
        textFieldGrossWt.setBounds(619, 300, 100, 25);
        panelWeighing.add(textFieldGrossWt);

        textFieldTareWt = new JTextField();
        textFieldTareWt.setDisabledTextColor(Color.BLACK);
        textFieldTareWt.setText("0");
        textFieldTareWt.setEnabled(false);
        textFieldTareWt.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldTareWt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTareWt.setColumns(10);
        textFieldTareWt.setBounds(619, 350, 100, 25);
        panelWeighing.add(textFieldTareWt);

        textFieldNetWt = new JTextField();
        textFieldNetWt.setText("0");
        textFieldNetWt.setDisabledTextColor(Color.BLACK);
        textFieldNetWt.setEnabled(false);
        textFieldNetWt.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldNetWt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNetWt.setColumns(10);
        textFieldNetWt.setBounds(619, 400, 100, 25);
        panelWeighing.add(textFieldNetWt);

        textFieldGrossDateTime = new JTextField();
        textFieldGrossDateTime.setDisabledTextColor(Color.BLACK);
        textFieldGrossDateTime.setEnabled(false);
        textFieldGrossDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldGrossDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldGrossDateTime.setColumns(10);
        textFieldGrossDateTime.setBounds(775, 300, 175, 25);
        panelWeighing.add(textFieldGrossDateTime);

        textFieldTareDateTime = new JTextField();
        textFieldTareDateTime.setDisabledTextColor(Color.BLACK);
        textFieldTareDateTime.setEnabled(false);
        textFieldTareDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTareDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldTareDateTime.setColumns(10);
        textFieldTareDateTime.setBounds(775, 350, 175, 25);
        panelWeighing.add(textFieldTareDateTime);

        textFieldNetDateTime = new JTextField();
        textFieldNetDateTime.setDisabledTextColor(Color.BLACK);
        textFieldNetDateTime.setEnabled(false);
        textFieldNetDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNetDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldNetDateTime.setColumns(10);
        textFieldNetDateTime.setBounds(775, 400, 175, 25);
        panelWeighing.add(textFieldNetDateTime);

        btnGetGross = new JButton("Get Gross Details");
        btnGetGross.setFocusable(false);
        btnGetGross.setEnabled(false);
        btnGetGross.addActionListener(e -> {

            String[] ConnectOptionNames = {"Set Gross", "Cancel"};
            JTextField userid = new JTextField(10);
            JXDatePicker datePicker = new JXDatePicker();
            datePicker.setFormats("dd-MM-yyyy");
            datePicker.setDate(new Date());
            datePicker.getEditor().setEditable(false);
            JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner,
                    ((SimpleDateFormat) timeFormat).toPattern());
            timeSpinner.setEditor(timeEditor);
            timeSpinner.setValue(new Date());
            ((DefaultEditor) timeSpinner.getEditor()).getTextField().setEditable(false);
            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Gross Wt "));
            panel.add(userid);
            panel.add(new JLabel("Gross Date "));
            panel.add(datePicker);
            panel.add(new JLabel("Gross Time "));
            panel.add(timeSpinner);
            if (JOptionPane.showOptionDialog(null, panel, "Enter Gross Wt ", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, "") != 0)
                ;
            else {
                try {
                    textFieldGrossWt.setText(Integer.toString(Integer.parseInt(userid.getText())));
                    Date dateTemp = datePicker.getDate();
                    Date dateTemp1 = (Date) timeSpinner.getModel().getValue();
                    textFieldGrossDateTime
                            .setText(dateAndTimeFormatdate.format(dateTemp) + " " + timeFormat.format(dateTemp1));
                    btnGetGross.setEnabled(false);
                    if (rdbtnGross.isSelected())
                        btnTotal.setEnabled(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Plz check the Value Entered\n\nLINE :922", "Value ERROR",
                            JOptionPane.ERROR_MESSAGE);

                }
            }

        });
        btnGetGross.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetGross.setBounds(990, 300, 225, 25);
        panelWeighing.add(btnGetGross);

        btnGetTare = new JButton("Get Tare Details");
        btnGetTare.setFocusable(false);
        btnGetTare.setEnabled(false);
        btnGetTare.addActionListener(e -> {
            String[] ConnectOptionNames = {"Set Tare", "Cancel"};
            JTextField userid = new JTextField(10);
            JXDatePicker datePicker = new JXDatePicker();
            datePicker.setFormats("dd-MM-yyyy");
            datePicker.setDate(new Date());
            datePicker.getEditor().setEditable(false);
            JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner,
                    ((SimpleDateFormat) timeFormat).toPattern());
            timeSpinner.setEditor(timeEditor);
            timeSpinner.setValue(new Date());
            ((DefaultEditor) timeSpinner.getEditor()).getTextField().setEditable(false);
            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Tare Wt "));
            panel.add(userid);
            panel.add(new JLabel("Tare Date "));
            panel.add(datePicker);
            panel.add(new JLabel("Tare Time "));
            panel.add(timeSpinner);
            if (JOptionPane.showOptionDialog(null, panel, "Enter Tare Wt ", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null) != 0)
                ;
            else {
                try {
                    textFieldTareWt.setText(Integer.toString(Integer.parseInt(userid.getText())));
                    Date dateTemp = datePicker.getDate();
                    Date dateTemp1 = (Date) timeSpinner.getModel().getValue();
                    textFieldTareDateTime
                            .setText(dateAndTimeFormatdate.format(dateTemp) + " " + timeFormat.format(dateTemp1));
                    btnGetTare.setEnabled(false);
                    if (rdbtnTare.isSelected())
                        btnTotal.setEnabled(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Plz check the Value Entered\n\nLINE :969", "Value ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnGetTare.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetTare.setBounds(990, 350, 225, 25);
        panelWeighing.add(btnGetTare);

        btnTotal = new JButton("Total");
        btnTotal.setFocusable(false);
        btnTotal.addActionListener(e -> {

            textFieldVehicleNo.setText(textFieldVehicleNo.getText().toUpperCase().replaceAll(" ", ""));
            if (rdbtnGross.isSelected()) {
                textFieldNetDateTime.setText(textFieldGrossDateTime.getText());
            } else {
                textFieldNetDateTime.setText(textFieldTareDateTime.getText());
            }
            if (Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) > 0
                    && !textFieldTareWt.getText().equals("0")) {
                textFieldNetWt.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText())
                        - Integer.parseInt(textFieldTareWt.getText())));
                textFieldNoOfUnits.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText())
                        - Integer.parseInt(textFieldTareWt.getText())));
            }
            if (chckbxAutoCharges.isSelected() || chckbxChargecheck.isSelected()) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIALS ='"
                            + comboBoxMaterial.getEditor().getItem() + "'");
                    if (rs.next())
                        textFieldCharges.setText(
                                "" + (int) (rs.getDouble("COST") * Double.parseDouble(textFieldNetWt.getText())));
                } catch (SQLException | NumberFormatException ignored) {
                }
            }
            btnTotal.setEnabled(false);
            btnGetGross.setEnabled(false);
            btnGetTare.setEnabled(false);
            btnGetDcDetails.setEnabled(false);
            textFieldCustomerName.setEnabled(false);
            textFieldDriverName.setEnabled(false);
            rdbtnGross.setEnabled(false);
            button6.setEnabled(false);
            btnGetTareSl.setEnabled(false);
            rdbtnTare.setEnabled(false);
            btnGetGrossSl.setEnabled(false);
            textFieldVehicleNo.setEnabled(false);
            comboBoxMaterial.setEnabled(false);
            textFieldCharges.setEnabled(false);
            btnAuto.setEnabled(false);
            chckbxChargecheck.setEnabled(false);
            btnSave.setEnabled(true);
            btnGetWeight.setEnabled(false);
            btnMinusGross.setEnabled(false);
            btnPlusTare.setEnabled(false);
            btnSave.requestFocus();

        });
        btnTotal.setEnabled(false);
        btnTotal.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnTotal.setBounds(990, 400, 225, 25);
        panelWeighing.add(btnTotal);

        btnGetTareSl = new JButton("Get Tare Wt");
        btnGetTareSl.setFocusable(false);
        btnGetTareSl.addActionListener(arg0 -> {
            rdbtnGross.setSelected(true);
            JComboBox<String> comboBoxa = new JComboBox<>();
            comboBoxa.setModel(
                    new DefaultComboBoxModel<>(new String[]{"Tare Sl.no", "Gross Sl.no", "Net Sl.no"}));
            Object[] params = {"Select the field type for Tare Wt ?", comboBoxa,
                    "Enter the Sl.no To Get Tare Wt ?"};
            String response = JOptionPane.showInputDialog(null, params, "Getting the Sl.no for Tare Wt ",
                    JOptionPane.QUESTION_MESSAGE);
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1550", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (response == null || ("".equals(response))
                    || Integer.parseInt(response.replaceAll("[^0-9]", "")) >= serialNo
                    || Integer.parseInt(response.replaceAll("[^0-9]", "")) <= 0)
                ;
            else {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + response);
                    rs.next();
                    textFieldDcNo.setText(rs.getString("DCNO"));
                    textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? ""
                            : "" + dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                    textFieldCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    textFieldDriverName.setSelectedItem(rs.getString("DRIVERNAME"));
                    textFieldVehicleNo.setText(rs.getString("VEHICLENO"));
                    textFieldTareWt.setText(Integer.toString(
                            rs.getInt(Objects.requireNonNull(comboBoxa.getSelectedItem()).toString().replace("Sl.no", "").trim() + "WT")));
                    textFieldTareDateTime.setText(rs
                            .getDate(comboBoxa.getSelectedItem().toString().replace("Sl.no", "").trim() + "DATE")
                            + " " + rs.getTime(
                            comboBoxa.getSelectedItem().toString().replace("Sl.no", "").trim() + "TIME"));
                    if (textFieldTareDateTime.getText().equals("null null"))
                        textFieldTareDateTime.setText("");
                    else
                        textFieldTareDateTime.setText(dateAndTimeFormat.format(
                                new Date(dateAndTimeFormatSql.parse(textFieldTareDateTime.getText()).getTime())));
                } catch (SQLException | ParseException e) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :820",
                            "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                textFieldVehicleNo.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                textFieldDcNo.setEnabled(false);
                textFieldDcDate.setEnabled(false);
                textFieldCustomerName.setEnabled(false);
                textFieldDriverName.setEnabled(false);
                btnGetDcDetails.setEnabled(false);
                comboBoxMaterial.setEnabled(true);
                comboBoxMaterial.setSelectedIndex(-1);
                comboBoxMaterial.requestFocus();
            }

        });
        btnGetTareSl.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetTareSl.setBounds(216, 25, 141, 25);
        panelWeighing.add(btnGetTareSl);

        btnGetGrossSl = new JButton("Get Gross Wt");
        btnGetGrossSl.setFocusable(false);
        btnGetGrossSl.addActionListener(arg0 -> {

            rdbtnTare.setSelected(true);
            JComboBox<String> comboBoxa = new JComboBox<>();
            comboBoxa.setModel(
                    new DefaultComboBoxModel<>(new String[]{"Gross Sl.no", "Tare Sl.no", "Net Sl.no"}));
            Object[] params = {"Select the field type for Gross Wt ?", comboBoxa,
                    "Enter the Sl.no To Get Gross Wt ?"};
            String response = JOptionPane.showInputDialog(null, params, "Getting the Sl.no for Gross Wt ",
                    JOptionPane.QUESTION_MESSAGE);
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :847", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (response == null || ("".equals(response)) || Integer.parseInt(response) >= serialNo
                    || Integer.parseInt(response) <= 0)
                ;
            else {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + response);
                    rs.next();
                    textFieldDcNo.setText(rs.getString("DCNO"));
                    textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? ""
                            : "" + dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                    textFieldCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    textFieldDriverName.setSelectedItem(rs.getString("DRIVERNAME"));
                    textFieldVehicleNo.setText(rs.getString("VEHICLENO"));
                    textFieldGrossWt.setText(Integer.toString(
                            rs.getInt(Objects.requireNonNull(comboBoxa.getSelectedItem()).toString().replace("Sl.no", "").trim() + "WT")));
                    textFieldGrossDateTime.setText(rs
                            .getDate(comboBoxa.getSelectedItem().toString().replace("Sl.no", "").trim() + "DATE")
                            + " " + rs.getTime(
                            comboBoxa.getSelectedItem().toString().replace("Sl.no", "").trim() + "TIME"));
                    if (textFieldGrossDateTime.getText().equals("null null"))
                        textFieldGrossDateTime.setText("");
                    else
                        textFieldGrossDateTime.setText(dateAndTimeFormat.format(
                                new Date(dateAndTimeFormatSql.parse(textFieldGrossDateTime.getText()).getTime())));
                } catch (SQLException | ParseException e) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :861",
                            "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                textFieldVehicleNo.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                textFieldDcNo.setEnabled(false);
                textFieldDcDate.setEnabled(false);
                textFieldCustomerName.setEnabled(false);
                textFieldDriverName.setEnabled(false);
                btnGetDcDetails.setEnabled(false);
                comboBoxMaterial.setEnabled(true);
                comboBoxMaterial.setSelectedIndex(-1);
                comboBoxMaterial.requestFocus();
            }
        });
        btnGetGrossSl.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetGrossSl.setBounds(216, 75, 141, 25);
        panelWeighing.add(btnGetGrossSl);

        btnGetWeight = new JButton("Get Weight");
        btnGetWeight.addActionListener(e -> {
            if (chckbxCamera.isSelected()) {
                if (checkBoxCamera1.isSelected())
                    try {

                        panelCameras.remove(panelCamera1);
                        Runnable stuffToDo = new Thread(() -> clickedImage = webcam[1].getImage());
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Future<?> future = executor.submit(stuffToDo);
                        executor.shutdown();
                        try {
                            future.get(5, TimeUnit.SECONDS);
                        } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                        }
                        if (!executor.isTerminated()) {
                            clickedImage = null;
                            executor.shutdownNow();
                        }
                        labelCamera1 = new JLabel(
                                new ImageIcon(
                                        clickedImage
                                                .getScaledInstance(
                                                        (int) (((double) 240
                                                                / ((Dimension) Objects.requireNonNull(comboBoxResolution1
                                                                .getSelectedItem())).height
                                                                * ((Dimension) comboBoxResolution1
                                                                .getSelectedItem()).width)),
                                                        240, Image.SCALE_SMOOTH)));
                        labelCamera1.setBounds(10, 11,
                                (int) ((double) 240 / labelCamera1.getHeight() * labelCamera1.getWidth()), 240);
                        panelCameras.add(labelCamera1);
                    } catch (NullPointerException ignored) {
                    }

                if (checkBoxCamera2.isSelected())
                    try {
                        panelCameras.remove(panelCamera2);
                        Runnable stuffToDo = new Thread(() -> clickedImage = webcam[2].getImage());
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Future<?> future = executor.submit(stuffToDo);
                        executor.shutdown();
                        try {
                            future.get(5, TimeUnit.SECONDS);
                        } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                        }
                        if (!executor.isTerminated()) {
                            clickedImage = null;
                            executor.shutdownNow();
                        }
                        labelCamera2 = new JLabel(
                                new ImageIcon(
                                        clickedImage
                                                .getScaledInstance(
                                                        (int) (((double) 240
                                                                / ((Dimension) Objects.requireNonNull(comboBoxResolution2
                                                                .getSelectedItem())).height
                                                                * ((Dimension) comboBoxResolution2
                                                                .getSelectedItem()).width)),
                                                        240, Image.SCALE_SMOOTH)));
                        panelCameras.add(labelCamera2);
                        labelCamera2.setBounds(10, 11,
                                (int) (((double) 240 / ((Dimension) comboBoxResolution2.getSelectedItem()).height
                                        * ((Dimension) comboBoxResolution2.getSelectedItem()).width)),
                                240);
                    } catch (NullPointerException ignored) {
                    }

                if (checkBoxCamera3.isSelected())
                    try {
                        panelCameras.remove(panelCamera3);
                        Runnable stuffToDo = new Thread(() -> clickedImage = webcam[3].getImage());
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Future<?> future = executor.submit(stuffToDo);
                        executor.shutdown();
                        try {
                            future.get(5, TimeUnit.SECONDS);
                        } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                        }
                        if (!executor.isTerminated()) {
                            clickedImage = null;
                            executor.shutdownNow();
                        }
                        labelCamera3 = new JLabel(
                                new ImageIcon(
                                        clickedImage
                                                .getScaledInstance(
                                                        (int) (((double) 240
                                                                / ((Dimension) Objects.requireNonNull(comboBoxResolution3
                                                                .getSelectedItem())).height
                                                                * ((Dimension) comboBoxResolution3
                                                                .getSelectedItem()).width)),
                                                        240, Image.SCALE_SMOOTH)));
                        panelCameras.add(labelCamera3);
                        labelCamera3.setBounds(10, 11,
                                (int) (((double) 240 / ((Dimension) comboBoxResolution3.getSelectedItem()).height
                                        * ((Dimension) comboBoxResolution3.getSelectedItem()).width)),
                                240);
                    } catch (NullPointerException ignored) {
                    }

                if (checkBoxCamera4.isSelected())
                    try {
                        panelCameras.remove(panelCamera4);
                        Runnable stuffToDo = new Thread(() -> clickedImage = webcam[4].getImage());
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Future<?> future = executor.submit(stuffToDo);
                        executor.shutdown();
                        try {
                            future.get(5, TimeUnit.SECONDS);
                        } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                        }
                        if (!executor.isTerminated()) {
                            clickedImage = null;
                            executor.shutdownNow();
                        }
                        labelCamera4 = new JLabel(
                                new ImageIcon(
                                        clickedImage
                                                .getScaledInstance(
                                                        (int) (((double) 240
                                                                / ((Dimension) Objects.requireNonNull(comboBoxResolution4
                                                                .getSelectedItem())).height
                                                                * ((Dimension) comboBoxResolution4
                                                                .getSelectedItem()).width)),
                                                        240, Image.SCALE_SMOOTH)));
                        panelCameras.add(labelCamera4);
                        labelCamera4.setBounds(10, 11,
                                (int) (((double) 240 / ((Dimension) comboBoxResolution4.getSelectedItem()).height
                                        * ((Dimension) comboBoxResolution4.getSelectedItem()).width)),
                                240);
                    } catch (NullPointerException ignored) {
                    }

            }
            textFieldVehicleNo.setText(textFieldVehicleNo.getText().toUpperCase().replaceAll(" ", ""));
            if (rdbtnGross.isSelected()) {
                textFieldGrossWt.setText(lblWeight.getText());
                textFieldGrossDateTime.setText(textFieldDateTime.getText());
            } else {
                textFieldTareWt.setText(lblWeight.getText());
                textFieldTareDateTime.setText(textFieldDateTime.getText());
            }
            if (Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) > 0
                    && !textFieldTareWt.getText().equals("0")) {
                textFieldNetWt.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText())
                        - Integer.parseInt(textFieldTareWt.getText())));
                textFieldNoOfUnits.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText())
                        - Integer.parseInt(textFieldTareWt.getText())));
            }
            if (chckbxAutoCharges.isSelected() || chckbxChargecheck.isSelected()) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIALS ='"
                            + comboBoxMaterial.getEditor().getItem() + "'");
                    if (rs.next())
                        textFieldCharges.setText(
                                "" + (int) (rs.getDouble("COST") * Double.parseDouble(textFieldNetWt.getText())));
                } catch (SQLException | NumberFormatException ignored) {
                }
            }
            textFieldNetDateTime.setText(textFieldDateTime.getText());
            textFieldCustomerName.setEnabled(false);
            textFieldDriverName.setEnabled(false);
            rdbtnGross.setEnabled(false);
            button6.setEnabled(false);
            btnGetTareSl.setEnabled(false);
            rdbtnTare.setEnabled(false);
            btnGetGrossSl.setEnabled(false);
            textFieldVehicleNo.setEnabled(false);
            comboBoxMaterial.setEnabled(false);
            textFieldCharges.setEnabled(false);
            btnSave.setEnabled(true);
            btnGetDcDetails.setEnabled(false);
            btnGetWeight.setEnabled(false);
            btnGetGross.setEnabled(false);
            btnGetTare.setEnabled(false);
            btnAuto.setEnabled(false);
            chckbxChargecheck.setEnabled(false);
            btnTotal.setEnabled(false);
            btnMinusGross.setEnabled(false);
            btnPlusTare.setEnabled(false);
            textPaneRemarks.setEnabled(false);
            btnSave.requestFocus();
        });
        btnGetWeight.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetWeight.setBounds(40, 500, 162, 25);
        panelWeighing.add(btnGetWeight);

        btnSave = new JButton("Save");
        btnSave.addActionListener(arg0 -> {
            if (chckbxCamera.isSelected()) {
                if (checkBoxCamera1.isSelected()) {
                    File outputfile = new File("CameraOutput/" + textFieldSlNo.getText() + "_1.jpg");
                    try {
                        ImageIO.write(webcam[1].getImage(), "jpg", outputfile);
                    } catch (IOException | IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null,
                                "CAMERA ERROR\nCHECK THE CAMERA IN SETTINGS\nLINE :1370", "CAMERA ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (checkBoxCamera2.isSelected()) {
                    File outputfile = new File("CameraOutput/" + textFieldSlNo.getText() + "_2.jpg");
                    try {
                        ImageIO.write(webcam[2].getImage(), "jpg", outputfile);
                    } catch (IOException | IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null,
                                "CAMERA ERROR\nCHECK THE CAMERA IN SETTINGS\nLINE :1370", "CAMERA ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (checkBoxCamera3.isSelected()) {
                    File outputfile = new File("CameraOutput/" + textFieldSlNo.getText() + "_3.jpg");
                    try {
                        ImageIO.write(webcam[3].getImage(), "jpg", outputfile);
                    } catch (IOException | IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null,
                                "CAMERA ERROR\nCHECK THE CAMERA IN SETTINGS\nLINE :1370", "CAMERA ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (checkBoxCamera4.isSelected()) {
                    File outputfile = new File("CameraOutput/" + textFieldSlNo.getText() + "_4.jpg");
                    try {
                        ImageIO.write(webcam[4].getImage(), "jpg", outputfile);
                    } catch (IOException | IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null,
                                "CAMERA ERROR\nCHECK THE CAMERA IN SETTINGS\nLINE :1370", "CAMERA ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs;
                boolean update = false;
                if (chckbxTareNoSlno.isSelected()) {
                    rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO=" + textFieldSlNo.getText());
                    if (rs.next()) {
                        rs.absolute(1);
                        update = true;
                    } else {
                        rs = stmt.executeQuery("SELECT * FROM WEIGHING");
                        rs.moveToInsertRow();
                    }
                } else {
                    rs = stmt.executeQuery("SELECT * FROM WEIGHING");
                    rs.moveToInsertRow();
                }
                rs.updateInt("SLNO", Integer.parseInt(textFieldSlNo.getText()));
                rs.updateString("DCNO", textFieldDcNo.getText());
                if (textFieldDcDate.getText().equals(""))
                    ;
                else {
                    Date date = dateAndTimeFormatdate.parse(textFieldDcDate.getText());
                    rs.updateDate("DCNODATE", new java.sql.Date(date.getTime()));
                }
                String tempp = ("" + textFieldCustomerName.getSelectedItem()).toUpperCase();
                if (tempp.equals("NULL"))
                    tempp = "";
                rs.updateString("CUSTOMERNAME", tempp);
                tempp = ("" + textFieldDriverName.getSelectedItem()).toUpperCase();
                if (tempp.equals("NULL"))
                    tempp = "";
                rs.updateString("DRIVERNAME", tempp);
                rs.updateString("VEHICLENO", textFieldVehicleNo.getText());
                rs.updateString("MATERIAL", (String) comboBoxMaterial.getSelectedItem());
                rs.updateInt("CHARGES",
                        Integer.parseInt("0" + textFieldCharges.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("GROSSWT", Integer.parseInt("0" + textFieldGrossWt.getText()));
                rs.updateString("REMARKS", textPaneRemarks.getText());

                if (textFieldGrossDateTime.getText().equals(""))
                    ;
                else {
                    Date date = dateAndTimeFormat.parse(textFieldGrossDateTime.getText());
                    rs.updateDate("GROSSDATE", new java.sql.Date(date.getTime()));
                    rs.updateTime("GROSSTIME", new Time(date.getTime()));
                }
                rs.updateInt("TAREWT", Integer.parseInt("0" + textFieldTareWt.getText()));
                if (textFieldTareDateTime.getText().equals(""))
                    ;
                else {
                    Date date = dateAndTimeFormat.parse(textFieldTareDateTime.getText());
                    rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                    rs.updateTime("TARETIME", new Time(date.getTime()));
                }
                rs.updateInt("NETWT", Integer.parseInt("0" + textFieldNetWt.getText()));
                if (textFieldNetDateTime.getText().equals(""))
                    ;
                else {
                    Date date = dateAndTimeFormat.parse(textFieldNetDateTime.getText());
                    rs.updateDate("NETDATE", new java.sql.Date(date.getTime()));
                    rs.updateTime("NETTIME", new Time(date.getTime()));
                }
                rs.updateBoolean("MANUAL", chckbxManualEntry.isSelected());
                if (update) {
                    rs.updateRow();
                } else {
                    rs.insertRow();
                    rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                    rs.absolute(1);
                    rs.updateInt("SLNO", Integer.parseInt(textFieldSlNo.getText()) + 1);
                    rs.updateRow();
                }
                if (rdbtnTare.isSelected()) {
                    stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    rs = stmt.executeQuery("SELECT * FROM VEHICLETARES WHERE VEHICLENO LIKE '"
                            + textFieldVehicleNo.getText() + "'");
                    if (rs.next()) {
                        rs.updateInt("TAREWT", Integer.parseInt("0" + textFieldTareWt.getText()));
                        Date date = dateAndTimeFormat.parse(textFieldTareDateTime.getText());
                        rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                        rs.updateTime("TARETIME", new Time(date.getTime()));
                        rs.updateRow();
                    } else {
                        rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
                        int key = 1;
                        if (rs.last())
                            key = rs.getInt("KEY");
                        rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
                        rs.moveToInsertRow();
                        rs.updateString("VEHICLENO", textFieldVehicleNo.getText());
                        rs.updateInt("TAREWT", Integer.parseInt("0" + textFieldTareWt.getText()));
                        Date date = dateAndTimeFormat.parse(textFieldTareDateTime.getText());
                        rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                        rs.updateTime("TARETIME", new Time(date.getTime()));
                        rs.updateInt("TAREWT", Integer.parseInt("0" + textFieldTareWt.getText()));
                        rs.updateInt("KEY", key + 1);
                        rs.insertRow();
                    }
                }
            } catch (SQLException | ParseException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :990", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM TRANSPORTER ");
                rs.moveToInsertRow();
                String tempp = ("" + textFieldDriverName.getSelectedItem()).toUpperCase();
                if (tempp.equals("NULL"))
                    tempp = "";
                rs.updateString("TRANSPORTER", tempp);
                rs.insertRow();
            } catch (SQLException ignored) {
            }
            btnSave.setEnabled(false);
            btnPrint.setEnabled(true);
            btnPrint.requestFocus();
        });
        btnSave.setEnabled(false);
        btnSave.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnSave.setBounds(252, 500, 150, 25);
        panelWeighing.add(btnSave);

        btnPrint = new JButton("Print");
        btnPrint.addActionListener(arg0 -> {
            try {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to Print ?", "Print",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION)
                    for (int i = 0; i < Integer.parseInt(textFieldNoOfCopies.getText()); i++) {
                        if (Objects.requireNonNull(comboBoxPrintOptionForWeight.getSelectedItem()).equals("Pre Print")) {
                            printPreWeight();
                            break;
                        } else if (comboBoxPrintOptionForWeight.getSelectedItem().equals("Camera"))
                            printCameraWeight();
                        else if (comboBoxPrintOptionForWeight.getSelectedItem().equals("Plain Camera"))
                            printPlainCameraWeight();
                        else if (comboBoxPrintOptionForWeight.getSelectedItem().equals("Sri Pathy"))
                            printPlainSriPathyWeight();
                        else {
                            printPlainWeight();
                        }
                    }

                while (chckbxSms.isSelected()) {
                    String temp = JOptionPane.showInputDialog(null, "Please Enter the Phone No ?");
                    if (temp != null)
                        sentSMS(temp);
                    else
                        break;
                }
                // int response = JOptionPane.showConfirmDialog(null, "Do
                // you want to Continue to Billing ?",
                // "Continue",JOptionPane.YES_NO_OPTION,
                // JOptionPane.QUESTION_MESSAGE);
                // if (response == JOptionPane.YES_OPTION)
                // tabbedPane.setSelectedComponent(panelBilling);
                // else
                clear();
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Print ERROR\nPlease Use another Printer Option", "Print ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPrint.setEnabled(false);
        btnPrint.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnPrint.setBounds(452, 500, 150, 25);
        panelWeighing.add(btnPrint);

        JButton btnReprint = new JButton("RePrint");
        btnReprint.setFocusable(false);
        btnReprint.addActionListener(arg0 -> {
            String response = JOptionPane.showInputDialog(null, "Please Enter the Sl.no to Reprint ?", "Reprint",
                    JOptionPane.QUESTION_MESSAGE);
            if (response != null)
                response = response.replaceAll("[^0-9]", "");
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1039", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (response == null || ("".equals(response)) || Integer.parseInt(response) >= serialNo
                    || Integer.parseInt(response) <= 0)
                ;
            else {
                rePrint(response);
            }
        });
        btnReprint.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnReprint.setBounds(252, 550, 150, 25);
        panelWeighing.add(btnReprint);

        JButton btnClear = new JButton("Clear");
        btnClear.setFocusable(false);
        btnClear.addActionListener(e -> clear());
        btnClear.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnClear.setBounds(452, 550, 150, 25);
        panelWeighing.add(btnClear);
        JLabel contact = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("resources/contact.bmp")));
        contact.setBounds(945, 505, 300, 100);
        panelWeighing.add(contact);

        JLabel lblKg = new JLabel("Kg");
        lblKg.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblKg.setBounds(726, 300, 25, 25);
        panelWeighing.add(lblKg);

        JLabel label = new JLabel("Kg");
        label.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label.setBounds(729, 350, 25, 25);
        panelWeighing.add(label);

        JLabel label_1 = new JLabel("Kg");
        label_1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label_1.setBounds(729, 400, 25, 25);
        panelWeighing.add(label_1);

        JLabel lblCustmerName = new JLabel("Custmer's Name");
        lblCustmerName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCustmerName.setBounds(50, 200, 175, 25);
        panelWeighing.add(lblCustmerName);

        textFieldDriverName = new JComboBox<>();
        // AutoCompleteDecorator.decorate(textFieldDriverName);// For Auto
        // completion
        textFieldDriverName.addActionListener(arg0 -> textFieldVehicleNo.requestFocus());
        textFieldDriverName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldDriverName.setEditable(true);
        textFieldDriverName.setBounds(775, 200, 175, 25);
        panelWeighing.add(textFieldDriverName);

        JLabel lblDriversName = new JLabel("Transporter's Name");
        lblDriversName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDriversName.setBounds(490, 200, 175, 25);
        panelWeighing.add(lblDriversName);

        JLabel lblDcNo = new JLabel("Dc. No");
        lblDcNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDcNo.setBounds(490, 250, 75, 25);
        panelWeighing.add(lblDcNo);

        textFieldDcNo = new JTextField();
        textFieldDcNo.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldDcNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldDcNo.setEnabled(false);
        textFieldDcNo.setDisabledTextColor(Color.BLACK);
        textFieldDcNo.setColumns(10);
        textFieldDcNo.setBounds(619, 250, 100, 25);
        panelWeighing.add(textFieldDcNo);

        textFieldDcDate = new JTextField();
        textFieldDcDate.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldDcDate.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldDcDate.setEnabled(false);
        textFieldDcDate.setDisabledTextColor(Color.BLACK);
        textFieldDcDate.setColumns(10);
        textFieldDcDate.setBounds(775, 250, 175, 25);
        panelWeighing.add(textFieldDcDate);

        btnGetDcDetails = new JButton("Get Dc. Details");
        btnGetDcDetails.setFocusable(false);
        btnGetDcDetails.addActionListener(arg0 -> {
            String[] ConnectOptionNames = {"Set Dc. No", "Clear", "Cancel"};
            JTextField userid = new JTextField(10);
            JXDatePicker datePicker = new JXDatePicker();
            datePicker.setFormats("dd-MM-yyyy");
            datePicker.setDate(new Date());
            datePicker.getEditor().setEditable(false);
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Dc. No "));
            panel.add(userid);
            panel.add(new JLabel("Dc. Date "));
            panel.add(datePicker);
            int opt = JOptionPane.showOptionDialog(null, panel, "Enter Dc. No ", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, "");
            if (opt == 0) {
                try {
                    textFieldDcNo.setText(userid.getText().trim());
                    Date dateTemp = datePicker.getDate();
                    textFieldDcDate.setText(dateAndTimeFormatdate.format(dateTemp));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Plz check the Value Entered\n\nLINE :1485", "Value ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (opt == 1) {
                textFieldDcNo.setText("");
                textFieldDcDate.setText("");
            }
        });
        btnGetDcDetails.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetDcDetails.setBounds(990, 250, 225, 25);
        panelWeighing.add(btnGetDcDetails);

        btnClick = new JButton("Click");
        btnClick.addActionListener(e -> {
            try {
                jFrame.dispose();
            } catch (NullPointerException ignored) {
            }
            jFrame = new JFrame();
            jFrame.addFocusListener(new FocusListener() {
                private boolean gained = false;

                @Override
                public void focusGained(FocusEvent e) {
                    gained = true;
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (gained) {
                        jFrame.dispose();
                    }
                }
            });
            jFrame.setTitle("Preview");
            if (checkBoxCamera1.isSelected())
                try {
                    Runnable stuffToDo = new Thread(() -> {
                        BufferedImage previewImage = webcam[1].getImage();
                        BufferedImage cropImage = previewImage.getSubimage(
                                Integer.parseInt("0" + textFieldCropX11.getText().replaceAll("[^0-9]", "")),
                                Integer.parseInt("0" + textFieldCropY11.getText().replaceAll("[^0-9]", "")),
                                Integer.parseInt("0" + textFieldCropWidth11.getText().replaceAll("[^0-9]", "")),
                                Integer.parseInt(
                                        "0" + textFieldCropHeight11.getText().replaceAll("[^0-9]", "")));
                        JLabel jLabel = new JLabel(
                                new ImageIcon(cropImage.getScaledInstance(1280, 768, Image.SCALE_DEFAULT)));// 1280,
                        // 768
                        jLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e12) {
                                try {
                                    jFrame.dispose();
                                } catch (NullPointerException ignored) {
                                }
                            }
                        });
                        jFrame.getContentPane().add(jLabel);
                        jFrame.setSize(1280, 768);
                        jFrame.setLocationRelativeTo(null);
                        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        jFrame.setVisible(true);
                    });
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Future<?> future = executor.submit(stuffToDo);
                    executor.shutdown();
                    try {
                        future.get(5, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                    }
                    if (!executor.isTerminated()) {
                        executor.shutdownNow();
                    }
                } catch (NullPointerException ignored) {
                }

        });
        btnClick.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnClick.setFocusable(false);
        btnClick.setBounds(127, 550, 75, 25);
        panelWeighing.add(btnClick);

        JButton btnCalc = new JButton("Calc");
        btnCalc.addActionListener(e -> {
            if (calc == null) {
                calc = new Calculator();
                calc.setTitle("Calculator");
                calc.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                calc.pack();
                calc.setLocation(50, 50);
                calc.setVisible(true);
                calc.setResizable(false);
                calc.setAlwaysOnTop(true);
            } else
                calc.setVisible(true);
            calc.setState(Frame.NORMAL);
        });
        btnCalc.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnCalc.setFocusable(false);
        btnCalc.setBounds(40, 550, 75, 25);
        panelWeighing.add(btnCalc);

        btnMinusGross = new JButton("-");
        btnMinusGross.addActionListener(e1 -> {
            rdbtnTare.setSelected(true);
            Object[] options = {"New", "Ok", "Cancel"};
            JPanel panel = new JPanel();
            panel.add(new JLabel("Please Enter the Sl.no To Get Tare Wt ?"));
            JTextField text = new JTextField(10);
            panel.add(text);
            int result = JOptionPane.showOptionDialog(null, panel, "Get Gross Sl No",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            String response = text.getText();
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :806", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (result == 0)
                rdbtnGross.setSelected(true);
            else if (response == null || ("".equals(response)) || Integer.parseInt(response) >= serialNo
                    || Integer.parseInt(response) <= 0 || result != 1)
                ;

            else {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + response);
                    rs.next();
                    textFieldDcNo.setText(rs.getString("DCNO"));
                    textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? ""
                            : "" + dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                    textFieldCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    textFieldDriverName.setSelectedItem(rs.getString("DRIVERNAME"));
                    textFieldVehicleNo.setText(rs.getString("VEHICLENO"));
                    textFieldGrossWt.setText(Integer.toString(rs.getInt("TAREWT")));
                    if (textFieldGrossWt.getText().equals("0")) {
                        textFieldGrossWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                        textFieldGrossDateTime.setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));

                    } else
                        textFieldGrossDateTime.setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                    if (textFieldGrossDateTime.getText().equals("null null"))
                        textFieldGrossDateTime.setText("");
                    else
                        textFieldGrossDateTime.setText(dateAndTimeFormat.format(
                                new Date(dateAndTimeFormatSql.parse(textFieldGrossDateTime.getText()).getTime())));
                } catch (SQLException | ParseException e) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :820",
                            "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                textFieldVehicleNo.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                textFieldDcNo.setEnabled(false);
                textFieldDcDate.setEnabled(false);
                textFieldCustomerName.setEnabled(false);
                textFieldDriverName.setEnabled(false);
                btnGetDcDetails.setEnabled(false);
                comboBoxMaterial.setEnabled(true);
                comboBoxMaterial.setSelectedIndex(-1);
                comboBoxMaterial.requestFocus();
            }
        });
        btnMinusGross.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnMinusGross.setFocusable(false);
        btnMinusGross.setBounds(369, 75, 62, 25);
        panelWeighing.add(btnMinusGross);

        btnPlusTare = new JButton("+");
        btnPlusTare.addActionListener(e1 -> {
            rdbtnGross.setSelected(true);
            Object[] options = {"New", "Ok", "Cancel"};
            JPanel panel = new JPanel();
            panel.add(new JLabel("Please Enter the Sl.no To Get Tare Wt ?"));
            JTextField text = new JTextField(10);

            panel.add(text);

            int result = JOptionPane.showOptionDialog(null, panel, "Get Gross Sl No",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            String response = text.getText();
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :847", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (result == 0)
                rdbtnTare.setSelected(true);
            else if (response == null || ("".equals(response)) || Integer.parseInt(response) >= serialNo
                    || Integer.parseInt(response) <= 0 || result != 1)
                ;
            else {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + response);
                    rs.next();
                    textFieldDcNo.setText(rs.getString("DCNO"));
                    textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? ""
                            : "" + dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                    textFieldCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    textFieldDriverName.setSelectedItem(rs.getString("DRIVERNAME"));
                    textFieldVehicleNo.setText(rs.getString("VEHICLENO"));
                    textFieldTareWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                    if (textFieldTareWt.getText().equals("0")) {
                        textFieldTareWt.setText(Integer.toString(rs.getInt("TAREWT")));
                        textFieldTareDateTime.setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                    } else
                        textFieldTareDateTime.setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));
                    if (textFieldTareDateTime.getText().equals("null null"))
                        textFieldTareDateTime.setText("");
                    else
                        textFieldTareDateTime.setText(dateAndTimeFormat.format(
                                new Date(dateAndTimeFormatSql.parse(textFieldTareDateTime.getText()).getTime())));
                } catch (SQLException | ParseException e) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :861",
                            "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                textFieldVehicleNo.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                textFieldDcNo.setEnabled(false);
                textFieldDcDate.setEnabled(false);
                textFieldCustomerName.setEnabled(false);
                textFieldDriverName.setEnabled(false);
                btnGetDcDetails.setEnabled(false);
                comboBoxMaterial.setEnabled(true);
                comboBoxMaterial.setSelectedIndex(-1);
                comboBoxMaterial.requestFocus();
            }
        });
        btnPlusTare.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnPlusTare.setFocusable(false);
        btnPlusTare.setBounds(369, 25, 62, 25);
        panelWeighing.add(btnPlusTare);

        JLabel lblRemarks_1 = new JLabel("Remarks");
        lblRemarks_1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblRemarks_1.setBounds(50, 450, 175, 25);
        panelWeighing.add(lblRemarks_1);

        JScrollPane scrollPane_4 = new JScrollPane();
        scrollPane_4.setBounds(240, 438, 175, 50);
        panelWeighing.add(scrollPane_4);

        textPaneRemarks = new JTextPane(new DefaultStyledDocument() {
            private static final long serialVersionUID = 1L;

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if ((getLength() + str.length()) <= 100) {
                    super.insertString(offs, str, a);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "LIMIT REACHED\nLimit is 100 character\nPlease cutshot your Remarks\nLINE :2606",
                            "LIMIT REACHED", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        textPaneRemarks.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnGetWeight.requestFocus();
                }
            }
        });
        scrollPane_4.setViewportView(textPaneRemarks);
        textPaneRemarks.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textPaneRemarks.setDisabledTextColor(Color.BLACK);

        btnAuto = new JButton("Auto");
        btnAuto.addActionListener(e -> {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIALS ='"
                        + comboBoxMaterial.getEditor().getItem() + "'");
                if (rs.next())
                    textFieldCharges.setText(
                            "" + (int) (rs.getDouble("COST") * Double.parseDouble(textFieldNetWt.getText())));
            } catch (SQLException | NumberFormatException ignored) {
            }
            chckbxChargecheck.setSelected(true);
        });
        btnAuto.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnAuto.setFocusable(false);
        btnAuto.setBounds(153, 400, 75, 25);
        panelWeighing.add(btnAuto);

        chckbxChargecheck = new JCheckBox("Chargecheck");
        chckbxChargecheck.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxChargecheck.setFocusable(false);
        chckbxChargecheck.setEnabled(false);
        chckbxChargecheck.setBackground(new Color(0, 255, 127));
        chckbxChargecheck.setBounds(417, 402, 25, 25);
        panelWeighing.add(chckbxChargecheck);

        panelCameras = new JPanel();
        panelCameras.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("          Cameras          ", null, panelCameras, null);
        panelCameras.setLayout(null);

        checkBoxCamera1 = new JCheckBox("");
        checkBoxCamera1.addActionListener(e -> {
            if (checkBoxCamera1.isSelected()) {
                butttonUpdateCamera1.setEnabled(true);
                panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1,
                        textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 240, 0);
            } else {
                butttonUpdateCamera1.setSelected(false);
                butttonUpdateCamera1.setEnabled(false);
                if (butttonUpdateCamera1.getText().equals("Lock"))
                    butttonUpdateCamera1.doClick();
                try {
                    webcam[1].close();
                } catch (NullPointerException ignored) {
                }
                try {
                    panelCameras.remove(panelCamera1);
                } catch (NullPointerException ignored) {
                }
                try {
                    panelCameras.remove(labelCamera1);
                } catch (NullPointerException ignored) {
                }
            }
        });
        checkBoxCamera1.setEnabled(false);
        checkBoxCamera1.setBounds(10, 258, 22, 25);
        panelCameras.add(checkBoxCamera1);

        webcamPicker1 = new WebcamPicker();
        webcamPicker1.setEnabled(false);
        webcamPicker1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        webcamPicker1.setFocusable(false);
        webcamPicker1.addItemListener(e -> {
            if (checkBoxCamera1.isSelected())
                panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1,
                        textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 240, 0);
        });
        webcamPicker1.setBounds(41, 258, 270, 25);
        panelCameras.add(webcamPicker1);

        comboBoxResolution1 = new JComboBox<>();
        comboBoxResolution1.setEnabled(false);
        comboBoxResolution1.addActionListener(e -> {
            if (lock)
                panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1,
                        textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 240, 1);
        });
        comboBoxResolution1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBoxResolution1.setFocusable(false);
        comboBoxResolution1.setBounds(103, 281, 208, 25);
        panelCameras.add(comboBoxResolution1);

        textFieldCropX1 = new JTextField();
        textFieldCropX1.setEnabled(false);
        textFieldCropX1.setText("0");
        textFieldCropX1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropX1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropX1.setDisabledTextColor(Color.BLACK);
        textFieldCropX1.setColumns(10);
        textFieldCropX1.setBounds(316, 258, 50, 25);
        panelCameras.add(textFieldCropX1);

        textFieldCropY1 = new JTextField();
        textFieldCropY1.setEnabled(false);
        textFieldCropY1.setText("0");
        textFieldCropY1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropY1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropY1.setDisabledTextColor(Color.BLACK);
        textFieldCropY1.setColumns(10);
        textFieldCropY1.setBounds(367, 258, 50, 25);
        panelCameras.add(textFieldCropY1);

        textFieldCropWidth1 = new JTextField();
        textFieldCropWidth1.setEnabled(false);
        textFieldCropWidth1.setText("0");
        textFieldCropWidth1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropWidth1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropWidth1.setDisabledTextColor(Color.BLACK);
        textFieldCropWidth1.setColumns(10);
        textFieldCropWidth1.setBounds(418, 258, 50, 25);
        panelCameras.add(textFieldCropWidth1);

        textFieldCropHeight1 = new JTextField();
        textFieldCropHeight1.setEnabled(false);
        textFieldCropHeight1.setText("0");
        textFieldCropHeight1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropHeight1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropHeight1.setDisabledTextColor(Color.BLACK);
        textFieldCropHeight1.setColumns(10);
        textFieldCropHeight1.setBounds(469, 258, 50, 25);
        panelCameras.add(textFieldCropHeight1);

        webcamPicker2 = new WebcamPicker();
        webcamPicker2.setEnabled(false);
        webcamPicker2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        webcamPicker2.setFocusable(false);
        webcamPicker2.addItemListener(e -> {
            if (checkBoxCamera2.isSelected())
                panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2,
                        textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 240, 0);
        });

        butttonUpdateCamera1 = new JButton("Unlock");
        butttonUpdateCamera1.setEnabled(false);
        butttonUpdateCamera1.addActionListener(e -> {
            if (checkBoxCamera1.isSelected())
                if (Objects.equals(butttonUpdateCamera1.getText(), "Unlock")) {
                    webcamPicker1.setEnabled(true);
                    textFieldCropX1.setEnabled(true);
                    textFieldCropY1.setEnabled(true);
                    textFieldCropWidth1.setEnabled(true);
                    textFieldCropHeight1.setEnabled(true);
                    textFieldCropX11.setEnabled(true);
                    textFieldCropY11.setEnabled(true);
                    textFieldCropWidth11.setEnabled(true);
                    textFieldCropHeight11.setEnabled(true);
                    comboBoxResolution1.setEnabled(true);
                    butttonUpdateCamera1.setText("Lock");
                } else {
                    webcamPicker1.setEnabled(false);
                    textFieldCropX1.setEnabled(false);
                    textFieldCropY1.setEnabled(false);
                    textFieldCropWidth1.setEnabled(false);
                    textFieldCropHeight1.setEnabled(false);
                    textFieldCropX11.setEnabled(false);
                    textFieldCropY11.setEnabled(false);
                    textFieldCropWidth11.setEnabled(false);
                    textFieldCropHeight11.setEnabled(false);
                    comboBoxResolution1.setEnabled(false);
                    butttonUpdateCamera1.setText("Unlock");
                }
        });
        butttonUpdateCamera1.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        butttonUpdateCamera1.setFocusable(false);
        butttonUpdateCamera1.setBounds(10, 284, 90, 25);
        panelCameras.add(butttonUpdateCamera1);
        webcamPicker2.setBounds(648, 258, 270, 25);
        panelCameras.add(webcamPicker2);

        comboBoxResolution2 = new JComboBox<>();
        comboBoxResolution2.setEnabled(false);
        comboBoxResolution2.addActionListener(e -> {
            if (lock)
                panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2,
                        textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 240, 1);
        });
        comboBoxResolution2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBoxResolution2.setFocusable(false);
        comboBoxResolution2.setBounds(710, 281, 208, 25);
        panelCameras.add(comboBoxResolution2);

        checkBoxCamera2 = new JCheckBox("");
        checkBoxCamera2.addActionListener(e -> {
            if (checkBoxCamera2.isSelected()) {
                butttonUpdateCamera2.setEnabled(true);
                panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2,
                        textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 240, 0);
            } else {
                butttonUpdateCamera2.setSelected(false);
                butttonUpdateCamera2.setEnabled(false);
                if (butttonUpdateCamera2.getText().equals("Lock"))
                    butttonUpdateCamera2.doClick();
                try {
                    webcam[2].close();
                } catch (NullPointerException ignored) {
                }
                try {
                    panelCameras.remove(panelCamera2);
                } catch (NullPointerException ignored) {
                }
                try {
                    panelCameras.remove(labelCamera2);
                } catch (NullPointerException ignored) {
                }
            }
        });
        checkBoxCamera2.setEnabled(false);
        checkBoxCamera2.setBounds(617, 258, 22, 25);
        panelCameras.add(checkBoxCamera2);

        textFieldCropX2 = new JTextField();
        textFieldCropX2.setText("0");
        textFieldCropX2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropX2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropX2.setEnabled(false);
        textFieldCropX2.setDisabledTextColor(Color.BLACK);
        textFieldCropX2.setColumns(10);
        textFieldCropX2.setBounds(922, 258, 50, 25);
        panelCameras.add(textFieldCropX2);

        textFieldCropY2 = new JTextField();
        textFieldCropY2.setText("0");
        textFieldCropY2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropY2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropY2.setEnabled(false);
        textFieldCropY2.setDisabledTextColor(Color.BLACK);
        textFieldCropY2.setColumns(10);
        textFieldCropY2.setBounds(972, 258, 50, 25);
        panelCameras.add(textFieldCropY2);

        textFieldCropWidth2 = new JTextField();
        textFieldCropWidth2.setText("0");
        textFieldCropWidth2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropWidth2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropWidth2.setEnabled(false);
        textFieldCropWidth2.setDisabledTextColor(Color.BLACK);
        textFieldCropWidth2.setColumns(10);
        textFieldCropWidth2.setBounds(1022, 258, 50, 25);
        panelCameras.add(textFieldCropWidth2);

        textFieldCropHeight2 = new JTextField();
        textFieldCropHeight2.setText("0");
        textFieldCropHeight2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropHeight2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropHeight2.setEnabled(false);
        textFieldCropHeight2.setDisabledTextColor(Color.BLACK);
        textFieldCropHeight2.setColumns(10);
        textFieldCropHeight2.setBounds(1072, 258, 50, 25);
        panelCameras.add(textFieldCropHeight2);

        checkBoxCamera3 = new JCheckBox("");
        checkBoxCamera3.addActionListener(e -> {
            if (checkBoxCamera3.isSelected()) {
                butttonUpdateCamera3.setEnabled(true);
                panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3,
                        textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 240, 0);
            } else {
                butttonUpdateCamera3.setSelected(false);
                butttonUpdateCamera3.setEnabled(false);
                if (butttonUpdateCamera3.getText().equals("Lock"))
                    butttonUpdateCamera3.doClick();
                try {
                    webcam[3].close();
                } catch (NullPointerException ignored) {
                }
                try {
                    panelCameras.remove(panelCamera3);
                } catch (NullPointerException ignored) {
                }
                try {
                    panelCameras.remove(labelCamera3);
                } catch (NullPointerException ignored) {
                }
            }
        });

        butttonUpdateCamera2 = new JButton("Unlock");
        butttonUpdateCamera2.setEnabled(false);
        butttonUpdateCamera2.addActionListener(e -> {
            if (checkBoxCamera2.isSelected())
                if (Objects.equals(butttonUpdateCamera2.getText(), "Unlock")) {
                    webcamPicker2.setEnabled(true);
                    textFieldCropX2.setEnabled(true);
                    textFieldCropY2.setEnabled(true);
                    textFieldCropWidth2.setEnabled(true);
                    textFieldCropHeight2.setEnabled(true);
                    comboBoxResolution2.setEnabled(true);
                    butttonUpdateCamera2.setText("Lock");
                } else {
                    webcamPicker2.setEnabled(false);
                    textFieldCropX2.setEnabled(false);
                    textFieldCropY2.setEnabled(false);
                    textFieldCropWidth2.setEnabled(false);
                    textFieldCropHeight2.setEnabled(false);
                    comboBoxResolution2.setEnabled(false);
                    butttonUpdateCamera2.setText("Unlock");
                }

        });
        butttonUpdateCamera2.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        butttonUpdateCamera2.setFocusable(false);
        butttonUpdateCamera2.setBounds(617, 284, 90, 25);
        panelCameras.add(butttonUpdateCamera2);
        checkBoxCamera3.setEnabled(false);
        checkBoxCamera3.setBounds(10, 557, 22, 25);
        panelCameras.add(checkBoxCamera3);

        webcamPicker3 = new WebcamPicker();
        webcamPicker3.setEnabled(false);
        webcamPicker3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        webcamPicker3.setFocusable(false);
        webcamPicker3.addItemListener(e -> {
            if (checkBoxCamera3.isSelected())
                panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3,
                        textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 240, 0);
        });
        webcamPicker3.setBounds(41, 557, 270, 25);
        panelCameras.add(webcamPicker3);

        comboBoxResolution3 = new JComboBox<>();
        comboBoxResolution3.setEnabled(false);
        comboBoxResolution3.addActionListener(e -> {
            if (lock)
                panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3,
                        textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 240, 1);
        });
        comboBoxResolution3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBoxResolution3.setFocusable(false);
        comboBoxResolution3.setBounds(103, 580, 208, 25);
        panelCameras.add(comboBoxResolution3);

        textFieldCropX3 = new JTextField();
        textFieldCropX3.setText("0");
        textFieldCropX3.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropX3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropX3.setEnabled(false);
        textFieldCropX3.setDisabledTextColor(Color.BLACK);
        textFieldCropX3.setColumns(10);
        textFieldCropX3.setBounds(316, 557, 50, 25);
        panelCameras.add(textFieldCropX3);

        textFieldCropY3 = new JTextField();
        textFieldCropY3.setText("0");
        textFieldCropY3.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropY3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropY3.setEnabled(false);
        textFieldCropY3.setDisabledTextColor(Color.BLACK);
        textFieldCropY3.setColumns(10);
        textFieldCropY3.setBounds(367, 557, 50, 25);
        panelCameras.add(textFieldCropY3);

        textFieldCropWidth3 = new JTextField();
        textFieldCropWidth3.setText("0");
        textFieldCropWidth3.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropWidth3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropWidth3.setEnabled(false);
        textFieldCropWidth3.setDisabledTextColor(Color.BLACK);
        textFieldCropWidth3.setColumns(10);
        textFieldCropWidth3.setBounds(418, 557, 50, 25);
        panelCameras.add(textFieldCropWidth3);

        textFieldCropHeight3 = new JTextField();
        textFieldCropHeight3.setText("0");
        textFieldCropHeight3.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropHeight3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropHeight3.setEnabled(false);
        textFieldCropHeight3.setDisabledTextColor(Color.BLACK);
        textFieldCropHeight3.setColumns(10);
        textFieldCropHeight3.setBounds(469, 557, 50, 25);
        panelCameras.add(textFieldCropHeight3);

        checkBoxCamera4 = new JCheckBox("");
        checkBoxCamera4.addActionListener(e -> {
            if (checkBoxCamera4.isSelected()) {
                butttonUpdateCamera4.setEnabled(true);
                panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4,
                        textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 240, 0);
            } else {
                butttonUpdateCamera4.setSelected(false);
                butttonUpdateCamera4.setEnabled(false);
                if (butttonUpdateCamera4.getText().equals("Lock"))
                    butttonUpdateCamera4.doClick();
                try {
                    webcam[1].close();
                } catch (NullPointerException ignored) {
                }
                try {
                    panelCameras.remove(panelCamera4);
                } catch (NullPointerException ignored) {
                }
                try {
                    panelCameras.remove(labelCamera4);
                } catch (NullPointerException ignored) {
                }
            }
        });

        butttonUpdateCamera3 = new JButton("Unlock");
        butttonUpdateCamera3.setEnabled(false);
        butttonUpdateCamera3.addActionListener(e -> {
            if (Objects.equals(butttonUpdateCamera3.getText(), "Unlock")) {
                webcamPicker3.setEnabled(true);
                textFieldCropX3.setEnabled(true);
                textFieldCropY3.setEnabled(true);
                textFieldCropWidth3.setEnabled(true);
                textFieldCropHeight3.setEnabled(true);
                comboBoxResolution3.setEnabled(true);
                butttonUpdateCamera3.setText("Lock");
            } else {
                webcamPicker3.setEnabled(false);
                textFieldCropX3.setEnabled(false);
                textFieldCropY3.setEnabled(false);
                textFieldCropWidth3.setEnabled(false);
                textFieldCropHeight3.setEnabled(false);
                comboBoxResolution3.setEnabled(false);
                butttonUpdateCamera3.setText("Unlock");
            }

        });
        butttonUpdateCamera3.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        butttonUpdateCamera3.setFocusable(false);
        butttonUpdateCamera3.setBounds(10, 583, 90, 25);
        panelCameras.add(butttonUpdateCamera3);
        checkBoxCamera4.setEnabled(false);
        checkBoxCamera4.setBounds(617, 557, 22, 25);
        panelCameras.add(checkBoxCamera4);

        webcamPicker4 = new WebcamPicker();
        webcamPicker4.setEnabled(false);
        webcamPicker4.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        webcamPicker4.setFocusable(false);
        webcamPicker4.addItemListener(e -> {
            if (checkBoxCamera4.isSelected())
                panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4,
                        textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 240, 0);
        });
        webcamPicker4.setBounds(648, 557, 270, 25);
        panelCameras.add(webcamPicker4);

        comboBoxResolution4 = new JComboBox<>();
        comboBoxResolution4.setEnabled(false);
        comboBoxResolution4.addActionListener(e -> {
            if (lock)
                panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4,
                        textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 240, 1);
        });
        comboBoxResolution4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBoxResolution4.setFocusable(false);
        comboBoxResolution4.setBounds(710, 580, 208, 25);
        panelCameras.add(comboBoxResolution4);

        textFieldCropX4 = new JTextField();
        textFieldCropX4.setText("0");
        textFieldCropX4.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropX4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropX4.setEnabled(false);
        textFieldCropX4.setDisabledTextColor(Color.BLACK);
        textFieldCropX4.setColumns(10);
        textFieldCropX4.setBounds(922, 557, 50, 25);
        panelCameras.add(textFieldCropX4);

        textFieldCropY4 = new JTextField();
        textFieldCropY4.setText("0");
        textFieldCropY4.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropY4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropY4.setEnabled(false);
        textFieldCropY4.setDisabledTextColor(Color.BLACK);
        textFieldCropY4.setColumns(10);
        textFieldCropY4.setBounds(972, 557, 50, 25);
        panelCameras.add(textFieldCropY4);

        textFieldCropWidth4 = new JTextField();
        textFieldCropWidth4.setText("0");
        textFieldCropWidth4.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropWidth4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropWidth4.setEnabled(false);
        textFieldCropWidth4.setDisabledTextColor(Color.BLACK);
        textFieldCropWidth4.setColumns(10);
        textFieldCropWidth4.setBounds(1022, 557, 50, 25);
        panelCameras.add(textFieldCropWidth4);

        textFieldCropHeight4 = new JTextField();
        textFieldCropHeight4.setText("0");
        textFieldCropHeight4.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropHeight4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropHeight4.setEnabled(false);
        textFieldCropHeight4.setDisabledTextColor(Color.BLACK);
        textFieldCropHeight4.setColumns(10);
        textFieldCropHeight4.setBounds(1072, 557, 50, 25);
        panelCameras.add(textFieldCropHeight4);

        buttonUnLockCamera = new JButton("Unlock");
        buttonUnLockCamera.setEnabled(false);
        buttonUnLockCamera.addActionListener(e -> {
            if (Objects.equals(buttonUnLockCamera.getText(), "Unlock")) {
                JPasswordField password = new JPasswordField(10);
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {"Enter", "Cancel"};
                panel.add(new JLabel("Please the Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = {'1', '2', '3', '4', '5', '6'};
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    checkBoxCamera1.setEnabled(true);
                    checkBoxCamera2.setEnabled(true);
                    checkBoxCamera3.setEnabled(true);
                    checkBoxCamera4.setEnabled(true);

                    if (checkBoxCamera1.isSelected()) {
                        butttonUpdateCamera1.setEnabled(true);
                    }
                    if (checkBoxCamera2.isSelected()) {
                        butttonUpdateCamera2.setEnabled(true);
                    }
                    if (checkBoxCamera3.isSelected()) {
                        butttonUpdateCamera3.setEnabled(true);
                    }
                    if (checkBoxCamera4.isSelected()) {
                        butttonUpdateCamera4.setEnabled(true);
                    }
                    if (checkBoxCamera1.isSelected())
                        butttonUpdateCamera1.setEnabled(true);
                    if (checkBoxCamera2.isSelected())
                        butttonUpdateCamera2.setEnabled(true);
                    if (checkBoxCamera3.isSelected())
                        butttonUpdateCamera3.setEnabled(true);
                    if (checkBoxCamera4.isSelected())
                        butttonUpdateCamera4.setEnabled(true);
                    buttonUnLockCamera.setText("Lock");
                }
            } else {
                butttonUpdateCamera1.setEnabled(false);
                butttonUpdateCamera2.setEnabled(false);
                butttonUpdateCamera3.setEnabled(false);
                butttonUpdateCamera4.setEnabled(false);
                checkBoxCamera1.setEnabled(false);
                if (butttonUpdateCamera1.getText().equals("Lock"))
                    butttonUpdateCamera1.doClick();
                checkBoxCamera2.setEnabled(false);
                if (butttonUpdateCamera2.getText().equals("Lock"))
                    butttonUpdateCamera2.doClick();
                checkBoxCamera3.setEnabled(false);
                if (butttonUpdateCamera3.getText().equals("Lock"))
                    butttonUpdateCamera3.doClick();
                checkBoxCamera4.setEnabled(false);
                if (butttonUpdateCamera4.getText().equals("Lock"))
                    butttonUpdateCamera4.doClick();
                buttonUnLockCamera.setText("Unlock");
            }
        });

        butttonUpdateCamera4 = new JButton("Unlock");
        butttonUpdateCamera4.setEnabled(false);
        butttonUpdateCamera4.addActionListener(e -> {
            if (Objects.equals(butttonUpdateCamera4.getText(), "Unlock")) {
                webcamPicker4.setEnabled(true);
                textFieldCropX4.setEnabled(true);
                textFieldCropY4.setEnabled(true);
                textFieldCropWidth4.setEnabled(true);
                textFieldCropHeight4.setEnabled(true);
                comboBoxResolution4.setEnabled(true);
                butttonUpdateCamera4.setText("Lock");
            } else {
                webcamPicker4.setEnabled(false);
                textFieldCropX4.setEnabled(false);
                textFieldCropY4.setEnabled(false);
                textFieldCropWidth4.setEnabled(false);
                textFieldCropHeight4.setEnabled(false);
                comboBoxResolution4.setEnabled(false);
                butttonUpdateCamera4.setText("Unlock");
            }

        });
        butttonUpdateCamera4.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        butttonUpdateCamera4.setFocusable(false);
        butttonUpdateCamera4.setBounds(617, 583, 90, 25);
        panelCameras.add(butttonUpdateCamera4);

        butttonUpdateCamera = new JButton("Update");
        butttonUpdateCamera.setEnabled(false);
        butttonUpdateCamera.addActionListener(e -> {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM CAMERA");
                rs.absolute(1);
                rs.updateBoolean("ENABLE", checkBoxCamera1.isSelected());
                rs.updateString("NAME", webcamPicker1.getSelectedWebcam().toString());
                rs.updateString("RESOLUTION",
                        comboBoxResolution1.getSelectedItem() != null
                                ? comboBoxResolution1.getSelectedItem().toString()
                                : "");
                rs.updateInt("CROPX", Integer.parseInt("0" + textFieldCropX1.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPY", Integer.parseInt("0" + textFieldCropY1.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPWIDTH",
                        Integer.parseInt("0" + textFieldCropWidth1.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPHEIGHT",
                        Integer.parseInt("0" + textFieldCropHeight1.getText().replaceAll("[^0-9]", "")));
                rs.updateRow();

                rs.absolute(2);
                rs.updateBoolean("ENABLE", checkBoxCamera2.isSelected());
                rs.updateString("NAME", webcamPicker2.getSelectedWebcam().toString());
                rs.updateString("RESOLUTION",
                        comboBoxResolution2.getSelectedItem() != null
                                ? comboBoxResolution2.getSelectedItem().toString()
                                : "");
                rs.updateInt("CROPX", Integer.parseInt("0" + textFieldCropX2.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPY", Integer.parseInt("0" + textFieldCropY2.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPWIDTH",
                        Integer.parseInt("0" + textFieldCropWidth2.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPHEIGHT",
                        Integer.parseInt("0" + textFieldCropHeight2.getText().replaceAll("[^0-9]", "")));
                rs.updateRow();

                rs.absolute(3);
                rs.updateBoolean("ENABLE", checkBoxCamera3.isSelected());
                rs.updateString("NAME", webcamPicker3.getSelectedWebcam().toString());
                rs.updateString("RESOLUTION",
                        comboBoxResolution3.getSelectedItem() != null
                                ? comboBoxResolution3.getSelectedItem().toString()
                                : "");
                rs.updateInt("CROPX", Integer.parseInt("0" + textFieldCropX3.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPY", Integer.parseInt("0" + textFieldCropY3.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPWIDTH",
                        Integer.parseInt("0" + textFieldCropWidth3.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPHEIGHT",
                        Integer.parseInt("0" + textFieldCropHeight3.getText().replaceAll("[^0-9]", "")));
                rs.updateRow();

                rs.absolute(4);
                rs.updateBoolean("ENABLE", checkBoxCamera4.isSelected());
                rs.updateString("NAME", webcamPicker4.getSelectedWebcam().toString());
                rs.updateString("RESOLUTION",
                        comboBoxResolution4.getSelectedItem() != null
                                ? comboBoxResolution4.getSelectedItem().toString()
                                : "");
                rs.updateInt("CROPX", Integer.parseInt("0" + textFieldCropX4.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPY", Integer.parseInt("0" + textFieldCropY4.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPWIDTH",
                        Integer.parseInt("0" + textFieldCropWidth4.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPHEIGHT",
                        Integer.parseInt("0" + textFieldCropHeight4.getText().replaceAll("[^0-9]", "")));
                rs.updateRow();

                rs.absolute(5);
                rs.updateInt("CROPX", Integer.parseInt("0" + textFieldCropX11.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPY", Integer.parseInt("0" + textFieldCropY11.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPWIDTH",
                        Integer.parseInt("0" + textFieldCropWidth11.getText().replaceAll("[^0-9]", "")));
                rs.updateInt("CROPHEIGHT",
                        Integer.parseInt("0" + textFieldCropHeight11.getText().replaceAll("[^0-9]", "")));
                rs.updateRow();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :3259", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        butttonUpdateCamera.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        butttonUpdateCamera.setFocusable(false);
        butttonUpdateCamera.setBounds(1155, 557, 90, 25);
        panelCameras.add(butttonUpdateCamera);
        buttonUnLockCamera.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        buttonUnLockCamera.setFocusable(false);
        buttonUnLockCamera.setBounds(1155, 583, 90, 25);
        panelCameras.add(buttonUnLockCamera);

        JLabel lblXYWt = new JLabel("    x        y        wt       ht");
        lblXYWt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblXYWt.setBounds(316, 309, 203, 25);
        panelCameras.add(lblXYWt);

        JLabel lblXYWt_1 = new JLabel("    x        y        wt       ht");
        lblXYWt_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblXYWt_1.setBounds(316, 580, 203, 25);
        panelCameras.add(lblXYWt_1);

        JLabel lblXYWt_2 = new JLabel("    x        y        wt       ht");
        lblXYWt_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblXYWt_2.setBounds(923, 281, 199, 25);
        panelCameras.add(lblXYWt_2);

        JLabel lblXYWt_3 = new JLabel("    x        y        wt       ht");
        lblXYWt_3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblXYWt_3.setBounds(923, 580, 199, 25);
        panelCameras.add(lblXYWt_3);

        JButton btnRefreshCamera = new JButton("Refresh");
        btnRefreshCamera.addActionListener(e -> {
            lock1 = true;
            cameraEvent();
            lock1 = false;
        });
        btnRefreshCamera.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        btnRefreshCamera.setFocusable(false);
        btnRefreshCamera.setBounds(1155, 529, 90, 25);
        panelCameras.add(btnRefreshCamera);

        textFieldCropX11 = new JTextField();
        textFieldCropX11.setText("0");
        textFieldCropX11.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropX11.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropX11.setEnabled(false);
        textFieldCropX11.setDisabledTextColor(Color.BLACK);
        textFieldCropX11.setColumns(10);
        textFieldCropX11.setBounds(316, 281, 50, 25);
        panelCameras.add(textFieldCropX11);

        textFieldCropY11 = new JTextField();
        textFieldCropY11.setText("0");
        textFieldCropY11.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropY11.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropY11.setEnabled(false);
        textFieldCropY11.setDisabledTextColor(Color.BLACK);
        textFieldCropY11.setColumns(10);
        textFieldCropY11.setBounds(367, 281, 50, 25);
        panelCameras.add(textFieldCropY11);

        textFieldCropWidth11 = new JTextField();
        textFieldCropWidth11.setText("0");
        textFieldCropWidth11.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropWidth11.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropWidth11.setEnabled(false);
        textFieldCropWidth11.setDisabledTextColor(Color.BLACK);
        textFieldCropWidth11.setColumns(10);
        textFieldCropWidth11.setBounds(418, 281, 50, 25);
        panelCameras.add(textFieldCropWidth11);

        textFieldCropHeight11 = new JTextField();
        textFieldCropHeight11.setText("0");
        textFieldCropHeight11.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCropHeight11.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldCropHeight11.setEnabled(false);
        textFieldCropHeight11.setDisabledTextColor(Color.BLACK);
        textFieldCropHeight11.setColumns(10);
        textFieldCropHeight11.setBounds(469, 281, 50, 25);
        panelCameras.add(textFieldCropHeight11);
        JPanel panelBilling = new JPanel();
        panelBilling.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("           Billing          ", null, panelBilling, null);
        panelBilling.setLayout(null);

        lblAmount = new JLabel("0");
        lblAmount.setForeground(new Color(0, 0, 255));
        lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmount.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 85));
        lblAmount.setBounds(420, 95, 415, 100);
        panelBilling.add(lblAmount);

        lblVat = new JLabel("");
        lblVat.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblVat.setBounds(452, 475, 150, 25);
        panelBilling.add(lblVat);

        JLabel lblBillNo = new JLabel("Bill No");
        lblBillNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblBillNo.setBounds(20, 275, 175, 25);
        panelBilling.add(lblBillNo);

        JLabel lblReferenceSlno = new JLabel("Reference Sl.No");
        lblReferenceSlno.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblReferenceSlno.setBounds(20, 325, 175, 25);
        panelBilling.add(lblReferenceSlno);

        JLabel lblCustomerName = new JLabel("Customer Name");
        lblCustomerName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCustomerName.setBounds(20, 375, 175, 25);
        panelBilling.add(lblCustomerName);

        JLabel lblDateTime1 = new JLabel("Date & Time");
        lblDateTime1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDateTime1.setBounds(452, 275, 150, 25);
        panelBilling.add(lblDateTime1);

        JLabel lblBillingDate = new JLabel("Billing Date");
        lblBillingDate.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblBillingDate.setBounds(840, 275, 175, 25);
        panelBilling.add(lblBillingDate);

        JLabel lblCustiomerAddress = new JLabel("Custiomer Address");
        lblCustiomerAddress.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCustiomerAddress.setBounds(20, 420, 175, 25);
        panelBilling.add(lblCustiomerAddress);

        JLabel lblCustiomerAddress_1 = new JLabel("Custiomer Address1");
        lblCustiomerAddress_1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCustiomerAddress_1.setBounds(19, 470, 175, 25);
        panelBilling.add(lblCustiomerAddress_1);

        JLabel lblMaterialName = new JLabel("Material Name");
        lblMaterialName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblMaterialName.setBounds(452, 325, 150, 25);
        panelBilling.add(lblMaterialName);

        JLabel lblCostPerUnit = new JLabel("Cost Per Unit");
        lblCostPerUnit.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCostPerUnit.setBounds(452, 375, 150, 25);
        panelBilling.add(lblCostPerUnit);

        JLabel lblTaxNoOfUnits = new JLabel("Weight");
        lblTaxNoOfUnits.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTaxNoOfUnits.setBounds(452, 425, 150, 25);
        panelBilling.add(lblTaxNoOfUnits);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTotal.setBounds(840, 330, 175, 25);
        panelBilling.add(lblTotal);

        JLabel lblTotalVat = new JLabel("Total + VAT");
        lblTotalVat.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTotalVat.setBounds(840, 380, 175, 25);
        panelBilling.add(lblTotalVat);

        JLabel lblDiscount = new JLabel("Discount");
        lblDiscount.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDiscount.setBounds(840, 430, 175, 25);
        panelBilling.add(lblDiscount);

        JLabel lblAmountToBe = new JLabel("Amount to be Paid");
        lblAmountToBe.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblAmountToBe.setBounds(840, 480, 175, 25);
        panelBilling.add(lblAmountToBe);

        JLabel lblRemarks = new JLabel("Remarks");
        lblRemarks.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblRemarks.setBounds(840, 556, 175, 25);
        panelBilling.add(lblRemarks);

        table1 = new JTable(5, 1);
        table1.setToolTipText("Only 35 letters");
        table1.setRowSelectionAllowed(false);
        for (int i = 0; i < table1.getColumnCount(); i++) {
            TableColumn col = table1.getColumnModel().getColumn(i);
            col.setCellEditor(new MyTableCellEditor());
        }
        table1.setBackground(new Color(0, 255, 127));
        table1.setEnabled(false);
        table1.setShowVerticalLines(false);
        table1.setShowHorizontalLines(false);
        table1.setShowGrid(false);
        table1.setRowHeight(table1.getRowHeight() + 3);
        table1.setFont(new Font("Monospaced", Font.PLAIN, 15));
        table1.setBounds(10, 11, 400, 115);
        panelBilling.add(table1);

        table2 = new JTable(5, 1);
        table2.setToolTipText("Only 35 letters");
        table2.setRowSelectionAllowed(false);
        for (int i = 0; i < table2.getColumnCount(); i++) {
            TableColumn col = table2.getColumnModel().getColumn(i);
            col.setCellEditor(new MyTableCellEditor());
        }
        table2.setBackground(new Color(0, 255, 127));
        table2.setEnabled(false);
        table2.setShowVerticalLines(false);
        table2.setShowHorizontalLines(false);
        table2.setShowGrid(false);
        table2.setRowHeight(table1.getRowHeight() + 3);
        table2.setFont(new Font("Monospaced", Font.PLAIN, 15));
        table2.setBounds(845, 10, 400, 115);
        panelBilling.add(table2);

        table3 = new JTable(6, 1);
        table3.setToolTipText("Only 35 letters");
        table3.setRowSelectionAllowed(false);
        for (int i = 0; i < table3.getColumnCount(); i++) {
            TableColumn col = table3.getColumnModel().getColumn(i);
            col.setCellEditor(new MyTableCellEditor());
        }
        table3.setBackground(new Color(0, 255, 127));
        table3.setEnabled(false);
        table3.setShowVerticalLines(false);
        table3.setShowHorizontalLines(false);
        table3.setShowGrid(false);
        table3.setRowHeight(19);
        table3.setFont(new Font("Monospaced", Font.PLAIN, 15));
        table3.setBounds(10, 128, 400, 143);
        panelBilling.add(table3);

        table4 = new JTable(6, 1);
        table4.setToolTipText("Only 35 letters");
        table4.setRowSelectionAllowed(false);
        for (int i = 0; i < table4.getColumnCount(); i++) {
            TableColumn col = table4.getColumnModel().getColumn(i);
            col.setCellEditor(new MyTableCellEditor());
        }
        table4.setBackground(new Color(0, 255, 127));
        table4.setEnabled(false);
        table4.setShowVerticalLines(false);
        table4.setShowHorizontalLines(false);
        table4.setShowGrid(false);
        table4.setRowHeight(22);
        table4.setFont(new Font("Monospaced", Font.PLAIN, 15));
        table4.setBounds(845, 127, 400, 143);
        panelBilling.add(table4);

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> {
            table1.setEnabled(true);
            table2.setEnabled(true);
            table3.setEnabled(true);
            table4.setEnabled(true);
            labelBillTitle.setEnabled(true);
        });
        btnEdit.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnEdit.setFocusable(false);
        btnEdit.setBounds(452, 11, 150, 25);
        panelBilling.add(btnEdit);

        JButton btnUpdateBill = new JButton("Update");
        btnUpdateBill.addActionListener(arg0 -> {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM BILLPRITER");
                rs.absolute(1);
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                for (int i = 1; i <= 5; i++)
                    rs.updateString("A" + i, (String) model.getValueAt(i - 1, 0));
                model = (DefaultTableModel) table2.getModel();
                for (int i = 1; i <= 5; i++)
                    rs.updateString("B" + i, (String) model.getValueAt(i - 1, 0));
                model = (DefaultTableModel) table3.getModel();
                for (int i = 1; i <= 6; i++)
                    rs.updateString("C" + i, (String) model.getValueAt(i - 1, 0));
                model = (DefaultTableModel) table4.getModel();
                for (int i = 1; i <= 6; i++)
                    rs.updateString("D" + i, (String) model.getValueAt(i - 1, 0));
                rs.updateString("TITLE", labelBillTitle.getText());
                rs.updateRow();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1334", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            table1.setEnabled(false);
            table2.setEnabled(false);
            table3.setEnabled(false);
            table4.setEnabled(false);
            labelBillTitle.setEnabled(false);
        });
        btnUpdateBill.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnUpdateBill.setFocusable(false);
        btnUpdateBill.setBounds(669, 11, 150, 25);
        panelBilling.add(btnUpdateBill);

        labelBillTitle = new JTextField("title1");
        labelBillTitle.setBorder(null);
        labelBillTitle.setInheritsPopupMenu(true);
        labelBillTitle.setOpaque(false);
        labelBillTitle.setAutoscrolls(false);
        labelBillTitle.setEnabled(false);
        labelBillTitle.setDisabledTextColor(new Color(0, 0, 255));
        labelBillTitle.setBackground(new Color(0, 255, 127));
        labelBillTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelBillTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
        labelBillTitle.setBounds(420, 59, 415, 25);
        panelBilling.add(labelBillTitle);

        textFieldBillNo = new JTextField();
        textFieldBillNo.setEnabled(false);
        textFieldBillNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBillNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldBillNo.setDisabledTextColor(Color.BLACK);
        textFieldBillNo.setColumns(10);
        textFieldBillNo.setBounds(210, 275, 175, 30);
        panelBilling.add(textFieldBillNo);

        textFieldReferenceSlNo = new JTextField();
        textFieldReferenceSlNo.addActionListener(arg0 -> {
            if (rdbtnGross.isSelected()) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery(
                            "SELECT * FROM WEIGHING WHERE SLNO = " + textFieldReferenceSlNo.getText());
                    if (rs.next())
                        textFieldNoOfUnits.setText(Integer.toString(rs.getInt("NETWT")));
                    comboBoxMaterialName.setSelectedItem(rs.getString("MATERIAL"));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1381",
                            "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        textFieldReferenceSlNo.setEnabled(false);
        textFieldReferenceSlNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldReferenceSlNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldReferenceSlNo.setDisabledTextColor(Color.BLACK);
        textFieldReferenceSlNo.setColumns(10);
        textFieldReferenceSlNo.setBounds(210, 325, 175, 30);
        panelBilling.add(textFieldReferenceSlNo);

        comboBoxCustomerName = new JComboBox<>();
        comboBoxCustomerName.addActionListener(arg0 -> {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE CUSTOMER LIKE '"
                        + comboBoxCustomerName.getSelectedItem() + "'");
                if (rs.next()) {
                    textFieldCustomerAddress.setText(rs.getString("CUSTOMERADDRESS"));
                    textFieldCustomerAddress1.setText(rs.getString("CUSTOMERADDRESS1"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1406", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        comboBoxCustomerName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxCustomerName.setEditable(true);
        comboBoxCustomerName.setBounds(210, 375, 175, 30);
        panelBilling.add(comboBoxCustomerName);

        comboBoxMaterialName = new JComboBox<>();
        comboBoxMaterialName.setEnabled(false);
        comboBoxMaterialName.addActionListener(arg0 -> {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM MATERIALS WHERE MATERIALS LIKE '"
                        + comboBoxMaterialName.getSelectedItem() + "'");
                if (rs.next())
                    textFieldCostPerunit.setText(rs.getString("COST"));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1425", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        textFieldCustomerAddress = new JTextField();
        textFieldCustomerAddress.setEnabled(false);
        textFieldCustomerAddress.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldCustomerAddress.setBounds(210, 420, 175, 30);
        panelBilling.add(textFieldCustomerAddress);

        textFieldCustomerAddress1 = new JTextField();
        textFieldCustomerAddress1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldCustomerAddress1.setEnabled(false);
        textFieldCustomerAddress1.setBounds(209, 470, 175, 30);
        panelBilling.add(textFieldCustomerAddress1);
        comboBoxMaterialName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxMaterialName.setEditable(true);
        comboBoxMaterialName.setBounds(606, 325, 175, 30);
        panelBilling.add(comboBoxMaterialName);

        textFieldBillDateTime = new JTextField();
        textFieldBillDateTime.setEditable(false);
        textFieldBillDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBillDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        textFieldBillDateTime.setDisabledTextColor(Color.BLACK);
        textFieldBillDateTime.setColumns(10);
        textFieldBillDateTime.setBounds(606, 275, 175, 30);
        panelBilling.add(textFieldBillDateTime);

        textFieldCostPerunit = new JTextField();
        textFieldCostPerunit.setText("0");
        textFieldCostPerunit.setEnabled(false);
        textFieldCostPerunit.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldCostPerunit.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldCostPerunit.setDisabledTextColor(Color.BLACK);
        textFieldCostPerunit.setColumns(10);
        textFieldCostPerunit.setBounds(606, 375, 175, 30);
        panelBilling.add(textFieldCostPerunit);

        textFieldNoOfUnits = new JTextField();
        textFieldNoOfUnits.setText("0");
        textFieldNoOfUnits.setEnabled(false);
        textFieldNoOfUnits.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldNoOfUnits.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNoOfUnits.setDisabledTextColor(Color.BLACK);
        textFieldNoOfUnits.setColumns(10);
        textFieldNoOfUnits.setBounds(606, 425, 175, 30);
        panelBilling.add(textFieldNoOfUnits);

        textFieldVat = new JTextField();
        textFieldVat.setText("0");
        textFieldVat.setEnabled(false);
        textFieldVat.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldVat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldVat.setDisabledTextColor(Color.BLACK);
        textFieldVat.setColumns(10);
        textFieldVat.setBounds(606, 475, 175, 30);
        panelBilling.add(textFieldVat);

        datePicker = new JXDatePicker();
        datePicker.setEnabled(false);
        datePicker.setFormats("dd-MM-yyyy");
        datePicker.getEditor().setFont(new Font("Times New Roman", Font.PLAIN, 20));
        datePicker.getEditor().setEditable(false);
        datePicker.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        datePicker.setBounds(1030, 275, 175, 30);
        panelBilling.add(datePicker);

        textFieldTotal = new JTextField();
        textFieldTotal.setText("0");
        textFieldTotal.setEnabled(false);
        textFieldTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldTotal.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTotal.setDisabledTextColor(Color.BLACK);
        textFieldTotal.setColumns(10);
        textFieldTotal.setBounds(1030, 330, 175, 30);
        panelBilling.add(textFieldTotal);

        textFieldTotalVat = new JTextField();
        textFieldTotalVat.setText("0");
        textFieldTotalVat.setEnabled(false);
        textFieldTotalVat.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldTotalVat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTotalVat.setDisabledTextColor(Color.BLACK);
        textFieldTotalVat.setColumns(10);
        textFieldTotalVat.setBounds(1030, 380, 175, 30);
        panelBilling.add(textFieldTotalVat);

        textFieldDiscount = new JTextField();
        textFieldDiscount.setEnabled(false);
        textFieldDiscount.setText("0");
        textFieldDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldDiscount.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldDiscount.setDisabledTextColor(Color.BLACK);
        textFieldDiscount.setColumns(10);
        textFieldDiscount.setBounds(1030, 430, 175, 30);
        panelBilling.add(textFieldDiscount);

        textFieldAmountToBePaid = new JTextField();
        textFieldAmountToBePaid.setText("0");
        textFieldAmountToBePaid.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldAmountToBePaid.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldAmountToBePaid.setEnabled(false);
        textFieldAmountToBePaid.setDisabledTextColor(Color.BLACK);
        textFieldAmountToBePaid.setColumns(10);
        textFieldAmountToBePaid.setBounds(1030, 480, 175, 30);
        panelBilling.add(textFieldAmountToBePaid);

        textFieldRemarks = new JTextField();
        textFieldRemarks.setHorizontalAlignment(SwingConstants.LEFT);
        textFieldRemarks.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldRemarks.setDisabledTextColor(Color.BLACK);
        textFieldRemarks.setColumns(10);
        textFieldRemarks.setBounds(1030, 553, 175, 30);
        panelBilling.add(textFieldRemarks);

        btnCalculate = new JButton("Calculate");
        btnCalculate.addActionListener(e -> {
            String[] temp = textFieldBillDateTime.getText().split(" ");
            try {
                if (datePicker.getDate() == null)
                    ;
            } catch (NullPointerException e1) {
                try {
                    datePicker.setDate(dateAndTimeFormatdate.parse(temp[0]));
                } catch (ParseException ignored) {
                }
            }
            textFieldCostPerunit.setText(textFieldCostPerunit.getText().replaceAll("[^.0-9]", ""));
            textFieldNoOfUnits.setText(textFieldNoOfUnits.getText().replaceAll("[^0-9]", ""));
            textFieldDiscount.setText(textFieldDiscount.getText().replaceAll("[^0-9]", ""));
            textFieldTotal.setText(Integer.toString((int) (Double.parseDouble(textFieldCostPerunit.getText())
                    * Double.parseDouble(textFieldNoOfUnits.getText()))));
            textFieldVat.setText(Integer.toString((int) (Double.parseDouble(textFieldTotal.getText())
                    * Double.parseDouble(textFieldTax.getText()) / 100)));
            textFieldTotalVat.setText(Integer.toString((int) (Double.parseDouble(textFieldVat.getText())
                    + Double.parseDouble(textFieldTotal.getText()))));
            textFieldAmountToBePaid.setText(Integer.toString((int) Double.parseDouble(textFieldTotalVat.getText())
                    - (int) Double.parseDouble(textFieldDiscount.getText())));
            lblAmount.setText(textFieldAmountToBePaid.getText());
            textFieldReferenceSlNo.setEnabled(false);
            button1.setEnabled(false);
            comboBoxCustomerName.setEnabled(false);
            textFieldCustomerAddress.setEnabled(false);
            button2.setEnabled(false);
            comboBoxMaterialName.setEnabled(false);
            button3.setEnabled(false);
            textFieldCostPerunit.setEnabled(false);
            button4.setEnabled(false);
            textFieldNoOfUnits.setEnabled(false);
            button5.setEnabled(false);
            datePicker.setEnabled(false);
            button6.setEnabled(false);
            textFieldDiscount.setEnabled(false);
            button7.setEnabled(false);
            textFieldRemarks.setEnabled(false);
            button8.setEnabled(false);
            textFieldCustomerAddress1.setEnabled(false);
            btnCalculate.setEnabled(false);
            btnSaveBill.setEnabled(true);
        });
        btnCalculate.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnCalculate.setFocusable(false);
        btnCalculate.setBounds(77, 530, 150, 25);
        panelBilling.add(btnCalculate);

        btnSaveBill = new JButton("Save");
        btnSaveBill.addActionListener(arg0 -> {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM BILLING");
                rs.moveToInsertRow();
                rs.updateInt("BILLNO", Integer.parseInt(textFieldBillNo.getText()));
                rs.updateInt("REFERENCESLNO", Integer.parseInt(textFieldReferenceSlNo.getText()));
                rs.updateString("CUSTOMERNAME", (String) comboBoxCustomerName.getSelectedItem());
                rs.updateString("CUSTOMERADDRESS", textFieldCustomerAddress.getText());
                rs.updateString("CUSTOMERADDRESS1", textFieldCustomerAddress1.getText());
                rs.updateString("MATERIALNAME", (String) comboBoxMaterialName.getSelectedItem());
                rs.updateDouble("COSTPERUNIT", Double.parseDouble("0" + textFieldCostPerunit.getText()));
                rs.updateInt("NOOFUNITS", (int) Double.parseDouble("0" + textFieldNoOfUnits.getText()));
                rs.updateInt("VAT", Integer.parseInt("0" + textFieldVat.getText()));
                rs.updateInt("DISCOUNT", (int) Double.parseDouble("0" + textFieldDiscount.getText()));
                rs.updateInt("TOTAL", Integer.parseInt("0" + textFieldAmountToBePaid.getText()));
                Date date = datePicker.getDate();
                rs.updateDate("BILLDATE", new java.sql.Date(date.getTime()));
                rs.updateString("REMARK", textFieldRemarks.getText());
                rs.insertRow();
                rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                rs.updateInt("BILLNO", Integer.parseInt(textFieldBillNo.getText()) + 1);
                rs.updateRow();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1613", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            btnSaveBill.setEnabled(false);
            btnPrintBill.setEnabled(true);
        });
        btnSaveBill.setEnabled(false);
        btnSaveBill.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnSaveBill.setFocusable(false);
        btnSaveBill.setBounds(277, 530, 150, 25);
        panelBilling.add(btnSaveBill);

        btnPrintBill = new JButton("Print");
        btnPrintBill.addActionListener(e -> {
            for (int i = 0; i < Integer.parseInt(textFieldNoOfCopies1.getText()); i++) {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to Print ?", "Print",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION)
                    printPlainBill();
                else
                    break;
            }
            // int response = JOptionPane.showConfirmDialog(null, "Do you
            // want to Continue to Weighing ?",
            // "Continue",JOptionPane.YES_NO_OPTION,
            // JOptionPane.QUESTION_MESSAGE);
            // if (response == JOptionPane.YES_OPTION)
            // tabbedPane.setSelectedComponent(panelWeighing);
            clear();
        });
        btnPrintBill.setEnabled(false);
        btnPrintBill.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnPrintBill.setFocusable(false);
        btnPrintBill.setBounds(477, 530, 150, 25);
        panelBilling.add(btnPrintBill);

        JButton btnRePrintBill = new JButton("RePrint");
        btnRePrintBill.addActionListener(arg0 -> {
            String response = JOptionPane.showInputDialog(null, "Please Enter the Bill.no to Reprint ?", "Reprint",
                    JOptionPane.QUESTION_MESSAGE);
            if (response != null)
                response = response.replaceAll("[^0-9]", "");
            int billNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                billNo = rs.getInt("BILLNO");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1662", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (response == null || ("".equals(response)) || Integer.parseInt(response) >= billNo
                    || Integer.parseInt(response) <= 0)
                ;
            else {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM billing");
                    rs.absolute(Integer.parseInt(response));
                    textFieldBillNo.setText(Integer.toString(rs.getInt("BILLNO")));
                    textFieldReferenceSlNo.setText(Integer.toString(rs.getInt("REFERENCESLNO")));
                    comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    textFieldCustomerAddress.setText(rs.getString("CUSTOMERADDRESS"));
                    textFieldCustomerAddress1.setText(rs.getString("CUSTOMERADDRESS1"));
                    comboBoxMaterialName.setSelectedItem(rs.getString("MATERIALNAME"));
                    textFieldCostPerunit.setText(String.format("%.2f", rs.getDouble("COSTPERUNIT")));
                    textFieldNoOfUnits.setText(Integer.toString(rs.getInt("NOOFUNITS")));
                    textFieldVat.setText(Integer.toString(rs.getInt("VAT")));
                    textFieldDiscount.setText(Integer.toString(rs.getInt("DISCOUNT")));
                    textFieldAmountToBePaid.setText(Integer.toString(rs.getInt("TOTAL")));
                    datePicker.setDate(rs.getDate("BILLDATE"));
                    textFieldRemarks.setText(rs.getString("REMARK"));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1684",
                            "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                textFieldTotal.setText(Integer.toString((int) (Double.parseDouble(textFieldCostPerunit.getText())
                        * Double.parseDouble(textFieldNoOfUnits.getText()))));
                textFieldTotalVat.setText(Integer.toString((int) (Double.parseDouble(textFieldVat.getText())
                        + Double.parseDouble(textFieldTotal.getText()))));
                textFieldReferenceSlNo.setEnabled(false);
                button1.setEnabled(false);
                comboBoxCustomerName.setEnabled(false);
                textFieldCustomerAddress.setEnabled(false);
                button2.setEnabled(false);
                textFieldCustomerAddress1.setEnabled(false);
                button8.setEnabled(false);
                comboBoxMaterialName.setEnabled(false);
                button3.setEnabled(false);
                textFieldCostPerunit.setEnabled(false);
                button4.setEnabled(false);
                textFieldNoOfUnits.setEnabled(false);
                button5.setEnabled(false);
                datePicker.setEnabled(false);
                button6.setEnabled(false);
                textFieldDiscount.setEnabled(false);
                button7.setEnabled(false);
                textFieldRemarks.setEnabled(false);
                btnCalculate.setEnabled(false);
                btnSaveBill.setEnabled(false);
                btnPrintBill.setEnabled(true);
            }
        });
        btnRePrintBill.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnRePrintBill.setFocusable(false);
        btnRePrintBill.setBounds(277, 580, 150, 25);
        panelBilling.add(btnRePrintBill);

        JButton btnClearBill = new JButton("Clear");
        btnClearBill.addActionListener(e -> clear());
        btnClearBill.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnClearBill.setFocusable(false);
        btnClearBill.setBounds(477, 580, 150, 25);
        panelBilling.add(btnClearBill);

        button1 = new JButton("");
        button1.addActionListener(e -> textFieldReferenceSlNo.setEnabled(true));
        button1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button1.setFocusable(false);
        button1.setBounds(385, 325, 26, 30);
        panelBilling.add(button1);

        button2 = new JButton("");
        button2.addActionListener(e -> textFieldCustomerAddress.setEnabled(true));
        button2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button2.setFocusable(false);
        button2.setBounds(385, 420, 26, 30);
        panelBilling.add(button2);

        button3 = new JButton("");
        button3.addActionListener(e -> comboBoxMaterialName.setEnabled(true));
        button3.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button3.setFocusable(false);
        button3.setBounds(781, 325, 26, 30);
        panelBilling.add(button3);

        button4 = new JButton("");
        button4.addActionListener(e -> textFieldCostPerunit.setEnabled(true));
        button4.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button4.setFocusable(false);
        button4.setBounds(781, 375, 26, 30);
        panelBilling.add(button4);

        button6 = new JButton("");
        button6.addActionListener(e -> datePicker.setEnabled(true));

        button5 = new JButton("");
        button5.addActionListener(e -> textFieldNoOfUnits.setEnabled(true));
        button5.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button5.setFocusable(false);
        button5.setBounds(781, 425, 26, 30);
        panelBilling.add(button5);
        button6.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button6.setFocusable(false);
        button6.setBounds(1206, 275, 26, 30);
        panelBilling.add(button6);

        button7 = new JButton("");
        button7.addActionListener(e -> textFieldDiscount.setEnabled(true));
        button7.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button7.setFocusable(false);
        button7.setBounds(1206, 429, 26, 30);
        panelBilling.add(button7);

        button8 = new JButton("");
        button8.addActionListener(e -> textFieldCustomerAddress1.setEnabled(true));
        button8.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button8.setFocusable(false);
        button8.setBounds(384, 470, 26, 30);
        panelBilling.add(button8);

        JPanel panelReport = new JPanel();
        panelReport.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("           Report           ", null, panelReport, null);
        panelReport.setLayout(null);

        rdbtnWeighing = new JRadioButton("Weighing Report");
        rdbtnWeighing.setBackground(new Color(0, 255, 127));
        rdbtnWeighing.addActionListener(e -> {
            comboBox.removeAllItems();
            comboBox.addItem("Full Report");
            comboBox.addItem("Daily Report");
            comboBox.addItem("Datewise Report");
            comboBox.addItem("Serialwise Report");
            comboBox.addItem("Vehiclewise Report");
            comboBox.addItem("Materialwise Report");
            comboBox.addItem("Customerwise Report");
            comboBox.addItem("Transporterwise Report");
        });
        buttonGroup_1.add(rdbtnWeighing);
        rdbtnWeighing.setSelected(true);
        rdbtnWeighing.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnWeighing.setFocusable(false);
        rdbtnWeighing.setBounds(75, 25, 200, 25);
        panelReport.add(rdbtnWeighing);

        rdbtnBilling = new JRadioButton("Billing Report");
        rdbtnBilling.setBackground(new Color(0, 255, 127));
        rdbtnBilling.addActionListener(e -> {
            comboBox.removeAllItems();
            comboBox.addItem("Full Report");
            comboBox.addItem("Daily Report");
            comboBox.addItem("Datewise Report");
            comboBox.addItem("BillNowise Report");
            comboBox.addItem("Customerwise Report");
            comboBox.addItem("Materialwise Report");
        });
        buttonGroup_1.add(rdbtnBilling);
        rdbtnBilling.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnBilling.setFocusable(false);
        rdbtnBilling.setBounds(75, 75, 200, 25);
        panelReport.add(rdbtnBilling);

        JLabel lblPleaseSelectThe = new JLabel("Please Select the Type of Report");
        lblPleaseSelectThe.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblPleaseSelectThe.setBounds(520, 51, 300, 25);
        panelReport.add(lblPleaseSelectThe);

        comboBox = new JComboBox<>();
        comboBox.addItemListener(e -> {
            if (comboBox.getSelectedItem() == null)
                ;
            else {
                if (rdbtnWeighing.isSelected()) {
                    switch (comboBox.getSelectedItem().toString()) {
                        case "Full Report":
                            datePicker1.setEnabled(false);
                            datePicker2.setEnabled(false);
                            textFieldDetail.setEnabled(false);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Daily Report":
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(false);
                            textFieldDetail.setEnabled(false);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Datewise Report":
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldDetail.setEnabled(false);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Serialwise Report":
                            detail.setText("Serial No");
                            datePicker1.setEnabled(false);
                            datePicker2.setEnabled(false);
                            textFieldDetail.setEnabled(true);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Vehiclewise Report":
                            detail.setText("Vehicle No");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldDetail.setEnabled(true);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Materialwise Report":
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldDetail.setEnabled(false);
                            comboBoxMaterialReport.setEnabled(true);
                            break;
                        case "Customerwise Report":
                            detail.setText("Customer Name");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldDetail.setEnabled(true);
                            comboBoxMaterialReport.setEnabled(true);
                            break;
                        case "Transporterwise Report":
                            detail.setText("Transporter Name");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldDetail.setEnabled(true);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                    }
                } else {
                    switch (comboBox.getSelectedItem().toString()) {
                        case "Full Report":
                            datePicker1.setEnabled(false);
                            datePicker2.setEnabled(false);
                            textFieldDetail.setEnabled(false);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Daily Report":
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(false);
                            textFieldDetail.setEnabled(false);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Datewise Report":
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldDetail.setEnabled(false);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "BillNowise Report":
                            detail.setText("Bill No");
                            datePicker1.setEnabled(false);
                            datePicker2.setEnabled(false);
                            textFieldDetail.setEnabled(true);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Customerwise Report":
                            detail.setText("Customer Name");
                            datePicker1.setEnabled(false);
                            datePicker2.setEnabled(false);
                            textFieldDetail.setEnabled(true);
                            comboBoxMaterialReport.setEnabled(false);
                            break;
                        case "Materialwise Report":
                            datePicker1.setEnabled(false);
                            datePicker2.setEnabled(false);
                            textFieldDetail.setEnabled(false);
                            comboBoxMaterialReport.setEnabled(true);
                            break;
                    }
                }
            }
        });
        comboBox.setFocusable(false);
        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Full Report", "Daily Report",
                "Datewise Report", "Serialwise Report", "Vehiclewise Report", "Materialwise Report",
                "Customerwise Report", "Transporterwise Report"}));
        comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBox.setBounds(840, 51, 350, 25);
        panelReport.add(comboBox);

        Date dateTemp = new Date();
        datePicker1 = new JXDatePicker();
        datePicker1.setEnabled(false);
        datePicker1.setFormats("dd-MM-yyyy");
        datePicker1.setDate(dateTemp);
        datePicker1.getEditor().setEditable(false);
        datePicker1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        datePicker1.getEditor().setFont(new Font("Times New Roman", Font.PLAIN, 20));
        datePicker1.setBounds(118, 121, 150, 30);
        panelReport.add(datePicker1);

        JLabel lblDate = new JLabel("Date");
        lblDate.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDate.setBounds(44, 124, 50, 25);
        panelReport.add(lblDate);

        JLabel lblTo = new JLabel("to");
        lblTo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTo.setBounds(278, 124, 25, 25);
        panelReport.add(lblTo);

        datePicker2 = new JXDatePicker();
        datePicker2.setEnabled(false);
        datePicker2.setFormats("dd-MM-yyyy");
        datePicker2.setDate(dateTemp);
        datePicker2.getEditor().setEditable(false);
        datePicker2.getEditor().setFont(new Font("Times New Roman", Font.PLAIN, 20));
        datePicker2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        datePicker2.setBounds(308, 121, 150, 30);
        panelReport.add(datePicker2);

        detail = new JLabel("Vehicle No");
        detail.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        detail.setBounds(520, 87, 150, 25);
        panelReport.add(detail);

        textFieldDetail = new JTextField();
        textFieldDetail.setEnabled(false);
        textFieldDetail.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldDetail.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldDetail.setDisabledTextColor(Color.BLACK);
        textFieldDetail.setColumns(10);
        textFieldDetail.setBounds(520, 121, 269, 30);
        panelReport.add(textFieldDetail);

        comboBoxMaterialReport = new JComboBox<>();
        comboBoxMaterialReport.setEnabled(false);
        comboBoxMaterialReport.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxMaterialReport.setBounds(840, 121, 270, 30);
        panelReport.add(comboBoxMaterialReport);

        JLabel lblMaterialReport = new JLabel("Material Name");
        lblMaterialReport.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblMaterialReport.setBounds(838, 87, 163, 25);
        panelReport.add(lblMaterialReport);

        btnGo = new JButton("Go");
        btnGo.addActionListener(arg0 -> {
            int charges = 0, netWt = 0;
            String message = "Plz Choose The Column To Show In Report ?";
            int n;
            if (rdbtnWeighing.isSelected()) {
                Object[] params = {message, a1, a1a, a1b, aa, aaa, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12};
                n = JOptionPane.showConfirmDialog(null, params, "Choose The Columns", JOptionPane.OK_CANCEL_OPTION);
            } else {
                Object[] params = {message, b1, b2, b3, b4, b41, b12, b5, b6, b7, b8, b9, b10, b11};
                n = JOptionPane.showConfirmDialog(null, params, "Choose The Columns", JOptionPane.OK_CANCEL_OPTION);
            }
            if (n == 0) {
                String date1, date2,
                        vehicleNo, material;
                int serialNo;
                Date dateTemp12;
                if (rdbtnWeighing.isSelected()) {
                    String temp = "SELECT * FROM WEIGHING";
                    switch (Objects.requireNonNull(comboBox.getSelectedItem()).toString()) {
                        case "Full Report":
                            temp = "SELECT * FROM WEIGHING";
                            break;
                        case "Daily Report":
                            dateTemp12 = datePicker1.getDate();
                            date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            temp = "SELECT * FROM WEIGHING WHERE NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                            break;
                        case "Datewise Report":
                            dateTemp12 = datePicker1.getDate();
                            date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            dateTemp12 = datePicker2.getDate();
                            date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            temp = "SELECT * FROM WEIGHING WHERE NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                            break;
                        case "Serialwise Report":
                            serialNo = Integer.parseInt(0 + textFieldDetail.getText().replaceAll("[^0-9]", ""));
                            temp = "SELECT * FROM WEIGHING WHERE SLNO >= " + serialNo;
                            break;
                        case "Vehiclewise Report":
                            vehicleNo = textFieldDetail.getText();
                            dateTemp12 = datePicker1.getDate();
                            date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            dateTemp12 = datePicker2.getDate();
                            date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            temp = "SELECT * FROM WEIGHING WHERE upper(VEHICLENO) LIKE UPPER('%" + vehicleNo
                                    + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                            break;
                        case "Materialwise Report":
                            dateTemp12 = datePicker1.getDate();
                            date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            dateTemp12 = datePicker2.getDate();
                            date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            material = (String) comboBoxMaterialReport.getSelectedItem();
                            if (material == null)
                                material = "";
                            temp = "SELECT * FROM WEIGHING WHERE upper(MATERIAL) LIKE UPPER('%" + material
                                    + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                            break;
                        case "Customerwise Report":
                            dateTemp12 = datePicker1.getDate();
                            date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            dateTemp12 = datePicker2.getDate();
                            date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            vehicleNo = textFieldDetail.getText();
                            material = "" + comboBoxMaterialReport.getSelectedItem();
                            if ("null".contains(material.trim()) || "".contains(material.trim()))
                                material = "";
                            else
                                material = "AND MATERIAL LIKE '" + material + "'";
                            temp = "SELECT * FROM WEIGHING WHERE upper(CUSTOMERNAME) LIKE UPPER('%" + vehicleNo
                                    + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'" + material;
                            break;
                        case "Transporterwise Report":
                            dateTemp12 = datePicker1.getDate();
                            date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            dateTemp12 = datePicker2.getDate();
                            date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            vehicleNo = textFieldDetail.getText();
                            temp = "SELECT * FROM WEIGHING WHERE upper(DRIVERNAME) LIKE UPPER('%" + vehicleNo
                                    + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                            break;
                    }
                    try {
                        tableReport.setModel(new DefaultTableModel(new Object[][]{},
                                new String[]{"Sl.No", "Dc. No", "Dc. Date", "Customer's Name",
                                        "Transporter's Name", "Vehicle No", "Material", "No of Bags", "Gross Wt",
                                        "Gross Date & Time", "Tare Wt", "Tare Date & Time", "Net Wt",
                                        "Print Date & Time", "Remarks", "Manual"}) {
                            private static final long serialVersionUID = 1L;
                            final boolean[] columnEditables = new boolean[]{false, false, false, false, false, false,
                                    false, false, false, false, false, false, false, false, false, false};

                            public boolean isCellEditable(int row, int column) {
                                return columnEditables[column];
                            }
                        });
                        DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery(temp + " ORDER BY SLNO");
                        while (rs.next()) {
                            String date, time, gross,
                                    tare, net;
                            date = "" + rs.getDate("GROSSDATE");
                            if (date.equals("null"))
                                date = "";
                            else
                                date = dateAndTimeFormatdate.format(rs.getDate("GROSSDATE"));
                            time = "" + rs.getTime("GROSSTIME");
                            if (time.equals("null"))
                                time = "";
                            else
                                time = timeFormat.format(rs.getTime("GROSSTIME"));
                            gross = date + " " + time;
                            date = "" + rs.getDate("TAREDATE");
                            if (date.equals("null"))
                                date = "";
                            else
                                date = dateAndTimeFormatdate.format(rs.getDate("TAREDATE"));
                            time = "" + rs.getTime("TARETIME");
                            if (time.equals("null"))
                                time = "";
                            else
                                time = timeFormat.format(rs.getTime("TARETIME"));
                            tare = date + " " + time;
                            date = "" + rs.getDate("NETDATE");
                            if (date.equals("null"))
                                date = "";
                            else
                                date = dateAndTimeFormatdate.format(rs.getDate("NETDATE"));
                            time = "" + rs.getTime("NETTIME");
                            if (time.equals("null"))
                                time = "";
                            else
                                time = timeFormat.format(rs.getTime("NETTIME"));
                            net = date + " " + time;

                            model.addRow(new Object[]{rs.getInt("SLNO"), rs.getString("DCNO"),
                                    ("" + rs.getDate("DCNODATE")).equals("null") ? ""
                                            : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")),
                                    rs.getString("CUSTOMERNAME"), rs.getString("DRIVERNAME"),
                                    rs.getString("VEHICLENO"), rs.getString("MATERIAL"), rs.getInt("CHARGES"),
                                    rs.getInt("GROSSWT"), gross, rs.getInt("TAREWT"), tare, rs.getInt("NETWT"), net,
                                    rs.getString("REMARKS"), rs.getBoolean("MANUAL")});
                            charges += rs.getInt("CHARGES");
                            netWt += rs.getInt("NETWT");
                        }
                        if (!a1.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Sl.No"));
                        if (!a1a.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Dc. No"));
                        if (!a1b.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Dc. Date"));
                        if (!aa.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Customer's Name"));
                        if (!aaa.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Transporter's Name"));
                        if (!a2.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Vehicle No"));
                        if (!a3.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Material"));
                        if (!a4.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("No of Bags"));
                        if (!a5.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Gross Wt"));
                        if (!a6.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Gross Date & Time"));
                        if (!a7.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Tare Wt"));
                        if (!a8.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Tare Date & Time"));
                        if (!a9.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Net Wt"));
                        if (!a10.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Print Date & Time"));
                        if (!a11.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Remarks"));
                        if (!a12.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Manual"));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2174",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    String temp = "SELECT * FROM BILLING";
                    switch (Objects.requireNonNull(comboBox.getSelectedItem()).toString()) {
                        case "Full Report":
                            temp = "SELECT * FROM BILLING";
                            break;
                        case "Daily Report":
                            dateTemp12 = datePicker1.getDate();
                            date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            temp = "SELECT * FROM BILLING WHERE BILLDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                            break;
                        case "Datewise Report":
                            dateTemp12 = datePicker1.getDate();
                            date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            dateTemp12 = datePicker2.getDate();
                            date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                            temp = "SELECT * FROM BILLING WHERE BILLDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                            break;
                        case "BillNowise Report":
                            serialNo = Integer.parseInt(0 + textFieldDetail.getText());
                            temp = "SELECT * FROM BILLING WHERE BILLNO >= " + serialNo;
                            break;
                        case "Customerwise Report":
                            vehicleNo = textFieldDetail.getText();
                            temp = "SELECT * FROM BILLING WHERE CUSTOMERNAME LIKE '" + vehicleNo + "'";
                            break;
                        case "Materialwise Report":
                            material = (String) comboBoxMaterialReport.getSelectedItem();
                            temp = "SELECT * FROM BILLING WHERE MATERIALNAME LIKE '" + material + "'";
                            break;
                    }

                    try {
                        tableReport.setModel(new DefaultTableModel(new Object[][]{},
                                new String[]{"Bill.No", "Reference SlNo", "Customer Name", "Customer Address",
                                        "Customer Address1", "Material Name", "Billing Date", "Cost Per Unit",
                                        "No of Units", "VAT", "Discount", "Total", "Remarks"}) {
                            /**
                             *
                             */
                            private static final long serialVersionUID = 1L;
                            final boolean[] columnEditables = new boolean[]{false, false, false, false, false, false,
                                    false, false, false, false, false, false, false};

                            public boolean isCellEditable(int row, int column) {
                                return columnEditables[column];
                            }
                        });
                        DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery(temp);
                        while (rs.next()) {
                            String date;
                            date = "" + rs.getDate("BILLDATE");
                            if (date.equals("null"))
                                date = "";
                            else
                                date = dateAndTimeFormatdate.format(rs.getDate("BILLDATE"));
                            model.addRow(new Object[]{rs.getInt("BILLNO"), rs.getInt("REFERENCESLNO"),
                                    rs.getString("CUSTOMERNAME"), rs.getString("CUSTOMERADDRESS"),
                                    rs.getString("CUSTOMERADDRESS"), rs.getString("MATERIALNAME"), date,
                                    rs.getDouble("COSTPERUNIT"), rs.getInt("NOOFUNITS"), rs.getInt("VAT"),
                                    rs.getInt("DISCOUNT"), rs.getInt("TOTAL"), rs.getString("REMARK")});
                            charges += rs.getInt("TOTAL");
                            netWt += rs.getInt("NOOFUNITS");
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2259",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    if (!b1.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Bill.No"));
                    if (!b2.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Reference SlNo"));
                    if (!b3.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Customer Name"));
                    if (!b4.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Customer Address"));
                    if (!b41.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Customer Address1"));
                    if (!b5.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Billing Date"));
                    if (!b6.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Cost Per Unit"));
                    if (!b7.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("No of Units"));
                    if (!b8.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("VAT"));
                    if (!b9.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Discount"));
                    if (!b10.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Total"));
                    if (!b11.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Remarks"));
                    if (!b12.isSelected())
                        tableReport.removeColumn(tableReport.getColumn("Material Name"));
                }
            }
            textFieldTotalCharges.setText("" + charges);
            textFieldtotalNetWt.setText(netWt + " Kg");
        });
        btnGo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGo.setFocusable(false);
        btnGo.setBounds(1153, 124, 60, 25);
        panelReport.add(btnGo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);
        scrollPane.setFocusable(false);
        scrollPane.setBounds(10, 162, 1235, 362);
        panelReport.add(scrollPane);

        tableReport = new JTable();
        tableReport.putClientProperty("terminateEditOnFocusLost", true);
        tableReport.setFocusable(false);
        tableReport.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableReport.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 15));
        scrollPane.setViewportView(tableReport);

        textFieldTotalCharges = new JTextField();
        textFieldTotalCharges.setText("0");
        textFieldTotalCharges.setEditable(false);
        textFieldTotalCharges.setHorizontalAlignment(SwingConstants.LEFT);
        textFieldTotalCharges.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTotalCharges.setDisabledTextColor(Color.BLACK);
        textFieldTotalCharges.setColumns(10);
        textFieldTotalCharges.setBounds(162, 535, 175, 30);
        panelReport.add(textFieldTotalCharges);

        JLabel lblTotalCharges = new JLabel("Total No of Bags");
        lblTotalCharges.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTotalCharges.setBounds(20, 540, 150, 25);
        panelReport.add(lblTotalCharges);

        textFieldtotalNetWt = new JTextField();
        textFieldtotalNetWt.setText("0 Kg");
        textFieldtotalNetWt.setEditable(false);
        textFieldtotalNetWt.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldtotalNetWt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldtotalNetWt.setDisabledTextColor(Color.BLACK);
        textFieldtotalNetWt.setColumns(10);
        textFieldtotalNetWt.setBounds(162, 576, 175, 30);
        panelReport.add(textFieldtotalNetWt);

        JLabel lblTotalNetWt = new JLabel("Total Net Wt");
        lblTotalNetWt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTotalNetWt.setBounds(20, 581, 120, 25);
        panelReport.add(lblTotalNetWt);

        btnSaveReport = new JButton("Save");
        btnSaveReport.addActionListener(e -> {
            try {
                if (rdbtnWeighing.isSelected()) {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    PreparedStatement pstmt = dbConnection.prepareStatement("DELETE FROM WEIGHINGTEMP ");
                    pstmt.executeUpdate();

                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHINGTEMP ");
                    DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
                    for (int i = 1; i <= model.getRowCount(); i++) {
                        rs.moveToInsertRow();
                        rs.updateInt("SLNO", Integer.parseInt("" + model.getValueAt(i - 1, 0)));
                        rs.updateString("DCNO", (String) model.getValueAt(i - 1, 1));

                        if (("" + model.getValueAt(i - 1, 2)).trim().equals(""))
                            ;
                        else {
                            Date date = dateAndTimeFormatdate.parse("" + model.getValueAt(i - 1, 2));
                            rs.updateDate("DCNODATE", new java.sql.Date(date.getTime()));
                        }

                        rs.updateString("CUSTOMERNAME", (String) model.getValueAt(i - 1, 3));
                        rs.updateString("DRIVERNAME", (String) model.getValueAt(i - 1, 4));
                        rs.updateString("VEHICLENO", (String) model.getValueAt(i - 1, 5));
                        rs.updateString("MATERIAL", (String) model.getValueAt(i - 1, 6));
                        rs.updateInt("CHARGES", Integer.parseInt("" + model.getValueAt(i - 1, 7)));
                        rs.updateInt("GROSSWT", Integer.parseInt("" + model.getValueAt(i - 1, 8)));

                        if (("" + model.getValueAt(i - 1, 9)).trim().equals(""))
                            ;
                        else {
                            Date date = dateAndTimeFormat.parse("" + model.getValueAt(i - 1, 9));
                            rs.updateDate("GROSSDATE", new java.sql.Date(date.getTime()));
                            rs.updateTime("GROSSTIME", new Time(date.getTime()));
                        }

                        rs.updateInt("TAREWT", Integer.parseInt("" + model.getValueAt(i - 1, 10)));

                        if (("" + model.getValueAt(i - 1, 11)).trim().equals(""))
                            ;
                        else {
                            Date date = dateAndTimeFormat.parse("" + model.getValueAt(i - 1, 11));
                            rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                            rs.updateTime("TARETIME", new Time(date.getTime()));
                        }
                        rs.updateInt("NETWT", Integer.parseInt("" + model.getValueAt(i - 1, 12)));
                        if (("" + model.getValueAt(i - 1, 13)).trim().equals(""))
                            ;
                        else {
                            Date date = dateAndTimeFormat.parse("" + model.getValueAt(i - 1, 13));
                            rs.updateDate("NETDATE", new java.sql.Date(date.getTime()));
                            rs.updateTime("NETTIME", new Time(date.getTime()));
                        }
                        rs.updateString("REMARKS", (String) model.getValueAt(i - 1, 14));
                        rs.updateBoolean("MANUAL", (boolean) model.getValueAt(i - 1, 15));
                        rs.insertRow();
                    }
                    pstmt = dbConnection.prepareStatement("DELETE FROM WEIGHING ");
                    pstmt.executeUpdate();
                    rs = stmt.executeQuery("SELECT * FROM WEIGHING ");
                    for (int i = 1; i <= model.getRowCount(); i++) {
                        rs.moveToInsertRow();
                        rs.updateInt("SLNO", Integer.parseInt("" + model.getValueAt(i - 1, 0)));

                        rs.updateString("DCNO", (String) model.getValueAt(i - 1, 1));

                        if (("" + model.getValueAt(i - 1, 2)).trim().equals(""))
                            ;
                        else {
                            Date date = dateAndTimeFormatdate.parse("" + model.getValueAt(i - 1, 2));
                            rs.updateDate("DCNODATE", new java.sql.Date(date.getTime()));
                        }

                        rs.updateString("CUSTOMERNAME", (String) model.getValueAt(i - 1, 3));
                        rs.updateString("DRIVERNAME", (String) model.getValueAt(i - 1, 4));
                        rs.updateString("VEHICLENO", (String) model.getValueAt(i - 1, 5));
                        rs.updateString("MATERIAL", (String) model.getValueAt(i - 1, 6));
                        rs.updateInt("CHARGES", Integer.parseInt("" + model.getValueAt(i - 1, 7)));
                        rs.updateInt("GROSSWT", Integer.parseInt("" + model.getValueAt(i - 1, 8)));

                        if (("" + model.getValueAt(i - 1, 9)).trim().equals(""))
                            ;
                        else {
                            Date date = dateAndTimeFormat.parse("" + model.getValueAt(i - 1, 9));
                            rs.updateDate("GROSSDATE", new java.sql.Date(date.getTime()));
                            rs.updateTime("GROSSTIME", new Time(date.getTime()));
                        }

                        rs.updateInt("TAREWT", Integer.parseInt("" + model.getValueAt(i - 1, 10)));

                        if (("" + model.getValueAt(i - 1, 11)).trim().equals(""))
                            ;
                        else {
                            Date date = dateAndTimeFormat.parse("" + model.getValueAt(i - 1, 11));
                            rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                            rs.updateTime("TARETIME", new Time(date.getTime()));
                        }
                        rs.updateInt("NETWT", Integer.parseInt("" + model.getValueAt(i - 1, 12)));
                        if (("" + model.getValueAt(i - 1, 13)).trim().equals(""))
                            ;
                        else {
                            Date date = dateAndTimeFormat.parse("" + model.getValueAt(i - 1, 13));
                            rs.updateDate("NETDATE", new java.sql.Date(date.getTime()));
                            rs.updateTime("NETTIME", new Time(date.getTime()));
                        }
                        rs.updateString("REMARKS", (String) model.getValueAt(i - 1, 14));
                        rs.updateBoolean("MANUAL", (boolean) model.getValueAt(i - 1, 15));
                        rs.insertRow();
                    }
                    tableReport.setEnabled(false);

                }
                btnEditReport.setEnabled(true);
                btnSaveReport.setEnabled(false);
                comboBox.setEnabled(true);
                datePicker1.setEnabled(false);
                datePicker2.setEnabled(false);
                textFieldDetail.setEnabled(false);
                comboBoxMaterialReport.setEnabled(false);
                btnPrintReport.setEnabled(true);
                btnGo.setEnabled(true);
                btnExportToExcel.setEnabled(true);
                tabbedPane.setEnabledAt(0, true);
                tabbedPane.setEnabledAt(1, true);
                tabbedPane.setEnabledAt(3, true);
                rdbtnWeighing.setEnabled(true);
                rdbtnBilling.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "SAVE FAILED\nPLEASE CHECK THE VALUES ENTERED\nLINE :2769",
                        "SAVE ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEditReport = new JButton("Edit");
        btnEditReport.addActionListener(e -> {

            int charges = 0, netWt = 0;
            String message = "Plz Choose The Column To Show In Report ?";
            int n;
            if (rdbtnWeighing.isSelected()) {
                Object[] params = {message, a1, a1a, a1b, aa, aaa, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12};
                n = JOptionPane.showConfirmDialog(null, params, "Choose The Columns", JOptionPane.OK_CANCEL_OPTION);
            } else {
                Object[] params = {message, b1, b2, b3, b4, b41, b12, b5, b6, b7, b8, b9, b10, b11};
                n = JOptionPane.showConfirmDialog(null, params, "Choose The Columns", JOptionPane.OK_CANCEL_OPTION);
            }
            if (n == 0) {
                if (rdbtnWeighing.isSelected()) {
                    rdbtnBilling.setEnabled(false);
                    String temp = "SELECT * FROM WEIGHING ORDER BY SLNO";
                    try {
                        tableReport.setModel(new DefaultTableModel(new Object[][]{},
                                new String[]{"Sl.No", "Dc. No", "Dc. Date", "Customer's Name",
                                        "Transporter's Name", "Vehicle No", "Material", "No of Bags", "Gross Wt",
                                        "Gross Date & Time", "Tare Wt", "Tare Date & Time", "Net Wt",
                                        "Print Date & Time", "Remarks", "Manual"}) {
                            /**
                             *
                             */
                            private static final long serialVersionUID = 1L;
                            final boolean[] columnEditables = new boolean[]{false, true, true, true, true, true, true,
                                    true, true, true, true, true, true, true, true, false};

                            public boolean isCellEditable(int row, int column) {
                                return columnEditables[column];
                            }
                        });
                        DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery(temp);
                        while (rs.next()) {
                            String date, time, gross,
                                    tare, net;
                            date = "" + rs.getDate("GROSSDATE");
                            if (date.equals("null"))
                                date = "";
                            else
                                date = dateAndTimeFormatdate.format(rs.getDate("GROSSDATE"));
                            time = "" + rs.getTime("GROSSTIME");
                            if (time.equals("null"))
                                time = "";
                            else
                                time = timeFormat.format(rs.getTime("GROSSTIME"));
                            gross = date + " " + time;
                            date = "" + rs.getDate("TAREDATE");
                            if (date.equals("null"))
                                date = "";
                            else
                                date = dateAndTimeFormatdate.format(rs.getDate("TAREDATE"));
                            time = "" + rs.getTime("TARETIME");
                            if (time.equals("null"))
                                time = "";
                            else
                                time = timeFormat.format(rs.getTime("TARETIME"));
                            tare = date + " " + time;
                            date = "" + rs.getDate("NETDATE");
                            if (date.equals("null"))
                                date = "";
                            else
                                date = dateAndTimeFormatdate.format(rs.getDate("NETDATE"));
                            time = "" + rs.getTime("NETTIME");
                            if (time.equals("null"))
                                time = "";
                            else
                                time = timeFormat.format(rs.getTime("NETTIME"));
                            net = date + " " + time;
                            model.addRow(new Object[]{rs.getInt("SLNO"), rs.getString("DCNO"),
                                    ("" + rs.getDate("DCNODATE")).equals("null") ? ""
                                            : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")),
                                    rs.getString("CUSTOMERNAME"), rs.getString("DRIVERNAME"),
                                    rs.getString("VEHICLENO"), rs.getString("MATERIAL"), rs.getInt("CHARGES"),
                                    rs.getInt("GROSSWT"), gross, rs.getInt("TAREWT"), tare, rs.getInt("NETWT"), net,
                                    rs.getString("REMARKS"), rs.getBoolean("MANUAL")});
                            charges += rs.getInt("CHARGES");
                            netWt += rs.getInt("NETWT");
                        }
                        if (!a1.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Sl.No"));
                        if (!a1a.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Dc. No"));
                        if (!a1b.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Dc. Date"));
                        if (!aa.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Customer's Name"));
                        if (!aaa.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Transporter's Name"));
                        if (!a2.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Vehicle No"));
                        if (!a3.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Material"));
                        if (!a4.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("No of Bags"));
                        if (!a5.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Gross Wt"));
                        if (!a6.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Gross Date & Time"));
                        if (!a7.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Tare Wt"));
                        if (!a8.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Tare Date & Time"));
                        if (!a9.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Net Wt"));
                        if (!a10.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Print Date & Time"));
                        if (!a11.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Remarks"));
                        if (!a12.isSelected())
                            tableReport.removeColumn(tableReport.getColumn("Manual"));
                        tableReport.setEnabled(true);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2174",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // {
                // rdbtnWeighing.setEnabled(false);
                // String temp = "SELECT * FROM BILLING";
                // try {
                // tableReport.setModel(new DefaultTableModel(
                // new Object[][] {
                // },
                // new String[] {
                // "Bill.No", "Reference SlNo", "Customer Name", "Customer
                // Address","Customer Address1","Material Name","Billing
                // Date","Cost Per Unit","No of
                // Units","VAT","Discount","Total","Remarks"
                // }
                // ) {
                // /**
                // *
                // */
                // private static final long serialVersionUID = 1L;
                // boolean[] columnEditables = new boolean[] {
                // false, true, true, true,true, true, true,
                // true,true,true,true,true,true
                // };
                // public boolean isCellEditable(int row, int column) {
                // return columnEditables[column];
                // }
                // });
                // DefaultTableModel model = (DefaultTableModel)
                // tableReport.getModel();
                // Statement stmt =
                // dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                // ResultSet.CONCUR_UPDATABLE);
                // ResultSet rs = stmt.executeQuery(temp);
                // while(rs.next())
                // {
                // String date = new String();
                // date = "" + rs.getDate("BILLDATE");
                // if(date.equals("null"))
                // date = "";
                // else
                // date =
                // dateAndTimeFormatdate.format(rs.getDate("BILLDATE"));
                // model.addRow(new
                // Object[]{rs.getInt("BILLNO"),rs.getInt("REFERENCESLNO"),rs.getString("CUSTOMERNAME"),rs.getString("CUSTOMERADDRESS"),rs.getString("CUSTOMERADDRESS"),rs.getString("MATERIALNAME"),date,rs.getDouble("COSTPERUNIT"),rs.getInt("NOOFUNITS"),rs.getInt("VAT"),rs.getInt("DISCOUNT"),rs.getInt("TOTAL"),rs.getString("REMARK")});
                // charges += rs.getInt("TOTAL");
                // netWt += rs.getInt("NOOFUNITS");
                // }
                // }catch (SQLException ex) {
                // JOptionPane.showMessageDialog(null,"SQL ERROR\nCHECK THE
                // VALUES ENTERED\nLINE :2259","SQL
                // ERROR",JOptionPane.ERROR_MESSAGE);
                // }
                // if(!b1.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Bill.No"));
                // if(!b2.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Reference
                // SlNo"));
                // if(!b3.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Customer
                // Name"));
                // if(!b4.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Customer
                // Address"));
                // if(!b41.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Customer
                // Address1"));
                // if(!b5.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Billing
                // Date"));
                // if(!b6.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Cost Per
                // Unit"));
                // if(!b7.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("No of
                // Units"));
                // if(!b8.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("VAT"));
                // if(!b9.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Discount"));
                // if(!b10.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Total"));
                // if(!b11.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Remarks"));
                // if(!b12.isSelected())
                // tableReport.removeColumn(tableReport.getColumn("Material
                // Name"));
                // }
            }
            textFieldTotalCharges.setText("" + charges);
            textFieldtotalNetWt.setText(netWt + " Kg");
            btnEditReport.setEnabled(false);
            btnSaveReport.setEnabled(true);
            comboBox.setEnabled(false);
            datePicker1.setEnabled(false);
            datePicker2.setEnabled(false);
            textFieldDetail.setEnabled(false);
            comboBoxMaterialReport.setEnabled(false);
            btnPrintReport.setEnabled(false);
            btnGo.setEnabled(false);
            btnExportToExcel.setEnabled(false);
            tabbedPane.setEnabledAt(0, false);
            tabbedPane.setEnabledAt(1, false);
            tabbedPane.setEnabledAt(3, false);
        });
        btnEditReport.setEnabled(false);
        btnEditReport.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnEditReport.setFocusable(false);
        btnEditReport.setBounds(439, 559, 150, 25);
        panelReport.add(btnEditReport);
        btnSaveReport.setEnabled(false);
        btnSaveReport.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnSaveReport.setBounds(639, 559, 150, 25);
        panelReport.add(btnSaveReport);

        btnExportToExcel = new JButton("Export to Excel");
        btnExportToExcel.addActionListener(arg0 -> {
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String fname = fileToSave.getAbsolutePath();
                try {
                    toExcel(fname);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Plz Close the Excel file\nLINE :3027", "FILE ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnExportToExcel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnExportToExcel.setFocusable(false);
        btnExportToExcel.setBounds(1040, 559, 186, 25);
        panelReport.add(btnExportToExcel);

        btnPrintReport = new JButton("Print");
        btnPrintReport.addActionListener(e -> {
            if (rdbtnWeighing.isSelected())
                printReportWeight();
            else
                printReportBill();
        });
        btnPrintReport.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnPrintReport.setFocusable(false);
        btnPrintReport.setBounds(840, 559, 150, 25);
        panelReport.add(btnPrintReport);

        JPanel panelSettings = new JPanel();
        panelSettings.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("          Settings          ", null, panelSettings, null);
        panelSettings.setLayout(null);

        JLabel lblMaterials = new JLabel("Materials");
        lblMaterials.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblMaterials.setBounds(10, 319, 111, 25);
        panelSettings.add(lblMaterials);

        JLabel lblVehicleTares = new JLabel("Vehicle Tares");
        lblVehicleTares.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblVehicleTares.setBounds(320, 319, 175, 25);
        panelSettings.add(lblVehicleTares);

        JLabel lblCustomer = new JLabel("Customer");
        lblCustomer.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCustomer.setBounds(780, 327, 111, 25);
        panelSettings.add(lblCustomer);

        JLabel lblGeneralSettings = new JLabel("General Settings");
        lblGeneralSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblGeneralSettings.setBounds(10, 11, 150, 25);
        panelSettings.add(lblGeneralSettings);

        JLabel lblTitle1 = new JLabel("Title 1");
        lblTitle1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTitle1.setBounds(10, 75, 75, 25);
        panelSettings.add(lblTitle1);

        JLabel lblTitle2 = new JLabel("Title 2");
        lblTitle2.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTitle2.setBounds(10, 125, 75, 25);
        panelSettings.add(lblTitle2);

        JLabel lblFooter = new JLabel("Footer");
        lblFooter.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblFooter.setBounds(10, 175, 75, 25);
        panelSettings.add(lblFooter);

        JLabel lblWeighbridgeSettings = new JLabel("com.babulens.WeighBridge_Old Settings");
        lblWeighbridgeSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblWeighbridgeSettings.setBounds(336, 11, 200, 25);
        panelSettings.add(lblWeighbridgeSettings);

        JLabel lblBaudRate = new JLabel("Baud Rate");
        lblBaudRate.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblBaudRate.setBounds(336, 45, 100, 25);
        panelSettings.add(lblBaudRate);

        JLabel lblPortName = new JLabel("Port Details");
        lblPortName.setToolTipText("<Port Name>;<Data Bit>;<Parity>;<Pattern>\r\n");
        lblPortName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblPortName.setBounds(336, 81, 100, 25);
        panelSettings.add(lblPortName);

        JLabel lblAdministratorSettings = new JLabel("Administrator Settings");
        lblAdministratorSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblAdministratorSettings.setBounds(599, 11, 200, 25);
        panelSettings.add(lblAdministratorSettings);

        JLabel lblPrinterSettings = new JLabel("Printer Settings");
        lblPrinterSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblPrinterSettings.setBounds(824, 11, 200, 25);
        panelSettings.add(lblPrinterSettings);

        JLabel lblPrinter1 = new JLabel("Printer 1");
        lblPrinter1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblPrinter1.setBounds(824, 75, 100, 25);
        panelSettings.add(lblPrinter1);

        JLabel lblPrinter2 = new JLabel("Printer 2");
        lblPrinter2.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblPrinter2.setBounds(824, 125, 100, 25);
        panelSettings.add(lblPrinter2);

        JLabel lblNoOfCopies = new JLabel("No Of Copies");
        lblNoOfCopies.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblNoOfCopies.setBounds(824, 170, 114, 25);
        panelSettings.add(lblNoOfCopies);

        JLabel lblBillingSettings = new JLabel("Billing Settings");
        lblBillingSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblBillingSettings.setBounds(336, 242, 200, 25);
        panelSettings.add(lblBillingSettings);

        JLabel lblTax = new JLabel("Vat");
        lblTax.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTax.setBounds(336, 278, 100, 25);
        panelSettings.add(lblTax);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 355, 300, 250);
        panelSettings.add(scrollPane_1);

        tableMaterial = new JTable();
        tableMaterial.putClientProperty("terminateEditOnFocusLost", true);
        tableMaterial.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableMaterial.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 15));
        tableMaterial.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Sl.No", "Materials", "Cost"}) {
            private static final long serialVersionUID = 1L;
            final boolean[] columnEditables = new boolean[]{false, true, true};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        tableMaterial.getColumnModel().getColumn(0).setResizable(false);
        tableMaterial.getColumnModel().getColumn(0).setPreferredWidth(43);
        tableMaterial.getColumnModel().getColumn(1).setResizable(false);
        tableMaterial.getColumnModel().getColumn(2).setResizable(false);
        scrollPane_1.setViewportView(tableMaterial);

        JButton btnAddMaterialRow = new JButton("+");
        btnAddMaterialRow.addActionListener(arg0 -> {
            DefaultTableModel model = (DefaultTableModel) tableMaterial.getModel();
            model.addRow(new Object[]{model.getRowCount() + 1});
        });
        btnAddMaterialRow.setFocusable(false);
        btnAddMaterialRow.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnAddMaterialRow.setBounds(221, 319, 41, 38);
        panelSettings.add(btnAddMaterialRow);

        JButton btnDeleteMaterialRow = new JButton("-");
        btnDeleteMaterialRow.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) tableMaterial.getModel();
            if (tableMaterial.getSelectedRow() != -1)
                model.removeRow(tableMaterial.getSelectedRow());
            for (int i = 1; i <= model.getRowCount(); i++)
                model.setValueAt(i, i - 1, 0);
        });
        btnDeleteMaterialRow.setFocusable(false);
        btnDeleteMaterialRow.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteMaterialRow.setBounds(269, 319, 41, 38);
        panelSettings.add(btnDeleteMaterialRow);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(320, 355, 450, 250);
        panelSettings.add(scrollPane_2);

        tableVehicleTare = new JTable();
        tableVehicleTare.putClientProperty("terminateEditOnFocusLost", true);
        tableVehicleTare.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableVehicleTare.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 15));
        tableVehicleTare.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{"Vehicle No", "Tare Wt", "Tare Date & Time "}) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            final Class<?>[] columnTypes = new Class[]{Object.class, Integer.class, Object.class};
            final boolean[] columnEditables = new boolean[]{false, false, false};

            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane_2.setViewportView(tableVehicleTare);

        JButton btnDeleteVehicleRow = new JButton("-");
        btnDeleteVehicleRow.addActionListener(e -> {
            if (tableVehicleTare.getSelectedRow() != -1)
                ((DefaultTableModel) tableVehicleTare.getModel()).removeRow(tableVehicleTare.getSelectedRow());
        });
        btnDeleteVehicleRow.setFocusable(false);
        btnDeleteVehicleRow.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteVehicleRow.setBounds(729, 319, 41, 38);
        panelSettings.add(btnDeleteVehicleRow);

        JScrollPane scrollPane_3 = new JScrollPane();
        scrollPane_3.setBounds(780, 355, 465, 250);
        panelSettings.add(scrollPane_3);

        tableCustomer = new JTable();
        tableCustomer.putClientProperty("terminateEditOnFocusLost", true);
        tableCustomer.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableCustomer.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 15));
        tableCustomer.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{"Customer Name", "Customer Address", "Customer Address1"}));
        tableCustomer.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        scrollPane_3.setViewportView(tableCustomer);

        JButton btnAddCustomer = new JButton("+");
        btnAddCustomer.addActionListener(arg0 -> {
            DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
            model.addRow(new Object[]{});
        });
        btnAddCustomer.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnAddCustomer.setFocusable(false);
        btnAddCustomer.setBounds(1156, 319, 41, 38);
        panelSettings.add(btnAddCustomer);

        JButton btnDeleteCustomer = new JButton("-");
        btnDeleteCustomer.addActionListener(e -> {
            if (tableCustomer.getSelectedRow() != -1)
                ((DefaultTableModel) tableCustomer.getModel()).removeRow(tableCustomer.getSelectedRow());
        });
        btnDeleteCustomer.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteCustomer.setFocusable(false);
        btnDeleteCustomer.setBounds(1204, 319, 41, 38);
        panelSettings.add(btnDeleteCustomer);

        textFieldTitle1 = new JTextField();
        textFieldTitle1.setToolTipText("Only 30 letters");
        textFieldTitle1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                title1.setText(textFieldTitle1.getText());
            }
        });
        textFieldTitle1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTitle1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTitle1.setDisabledTextColor(Color.BLACK);
        textFieldTitle1.setColumns(10);
        textFieldTitle1.setBounds(101, 75, 200, 30);
        panelSettings.add(textFieldTitle1);

        textFieldTitle2 = new JTextField();
        textFieldTitle2.setToolTipText("Only 45 letters");
        textFieldTitle2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                title2.setText(textFieldTitle2.getText());
            }
        });
        textFieldTitle2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTitle2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTitle2.setDisabledTextColor(Color.BLACK);
        textFieldTitle2.setColumns(10);
        textFieldTitle2.setBounds(101, 125, 200, 30);
        panelSettings.add(textFieldTitle2);

        textFieldFooter = new JTextField();
        textFieldFooter.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldFooter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldFooter.setDisabledTextColor(Color.BLACK);
        textFieldFooter.setColumns(10);
        textFieldFooter.setBounds(101, 175, 200, 30);
        panelSettings.add(textFieldFooter);

        chckbxExcludeCharges = new JCheckBox("Exclude Charges");
        chckbxExcludeCharges.setEnabled(false);
        chckbxExcludeCharges.setFocusable(false);
        chckbxExcludeCharges.setBackground(new Color(0, 255, 127));
        chckbxExcludeCharges.addChangeListener(arg0 -> {
            textFieldCharges.setEnabled(!chckbxExcludeCharges.isSelected());
            textFieldCharges.setText("");
        });
        chckbxExcludeCharges.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeCharges.setBounds(25, 251, 145, 25);
        panelSettings.add(chckbxExcludeCharges);

        textFieldBaudRate = new JTextField();
        textFieldBaudRate.setEnabled(false);
        textFieldBaudRate.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBaudRate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldBaudRate.setDisabledTextColor(Color.BLACK);
        textFieldBaudRate.setColumns(10);
        textFieldBaudRate.setBounds(460, 45, 100, 30);
        panelSettings.add(textFieldBaudRate);

        textFieldPortName = new JTextField();
        textFieldPortName.setToolTipText("<Port Name>;<Data Bit>;<Parity>;<Pattern>");
        textFieldPortName.setEnabled(false);
        textFieldPortName.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPortName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldPortName.setDisabledTextColor(Color.BLACK);
        textFieldPortName.setColumns(10);
        textFieldPortName.setBounds(460, 81, 100, 30);
        panelSettings.add(textFieldPortName);

        chckbxManualEntry = new JCheckBox("Manual Entry");
        chckbxManualEntry.setFocusable(false);
        chckbxManualEntry.addActionListener(e -> {
            if (chckbxManualEntry.isSelected()) {
                JPasswordField password = new JPasswordField(10);
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {"Enter", "Cancel"};
                panel.add(new JLabel("Please the Manual Entry Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = {'6', '5', '4', '3', '2', '1'};
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    btnGetGross.setEnabled(true);
                    btnGetTare.setEnabled(true);
                    clear();
                } else {
                    chckbxManualEntry.setSelected(false);
                }
            } else {
                btnGetGross.setEnabled(false);
                btnGetTare.setEnabled(false);
            }

        });
        chckbxManualEntry.setBackground(new Color(0, 255, 127));
        chckbxManualEntry.setEnabled(false);
        chckbxManualEntry.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxManualEntry.setBounds(600, 50, 200, 25);
        panelSettings.add(chckbxManualEntry);

        chckbxEditEnable = new JCheckBox("Edit Enable");
        chckbxEditEnable.setFocusable(false);
        chckbxEditEnable.addActionListener(e -> {
            if (chckbxEditEnable.isSelected()) {
                JPasswordField password = new JPasswordField(10);
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {"Enter", "Cancel"};
                panel.add(new JLabel("Please the Editing Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = {'m', 'o', 's', 'e', 's', 'd', 'h', 'a', 's'};
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    btnEditReport.setEnabled(true);
                } else {
                    chckbxEditEnable.setSelected(false);
                }
            } else {
                btnEditReport.setEnabled(false);
            }
        });
        chckbxEditEnable.setBackground(new Color(0, 255, 127));
        chckbxEditEnable.setEnabled(false);
        chckbxEditEnable.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxEditEnable.setBounds(600, 75, 200, 25);
        panelSettings.add(chckbxEditEnable);

        comboBoxPrinter1 = new JComboBox<>();
        comboBoxPrinter1.setFocusable(false);
        comboBoxPrinter1.setModel(new DefaultComboBoxModel<>(printers));
        comboBoxPrinter1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxPrinter1.setBounds(948, 75, 297, 30);
        panelSettings.add(comboBoxPrinter1);

        comboBoxPrinter2 = new JComboBox<>();
        comboBoxPrinter2.setFocusable(false);
        comboBoxPrinter2.setModel(new DefaultComboBoxModel<>(printers));
        comboBoxPrinter2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxPrinter2.setBounds(948, 125, 297, 30);
        panelSettings.add(comboBoxPrinter2);

        textFieldTax = new JTextField();
        textFieldTax.setEnabled(false);
        textFieldTax.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                lblVat.setText("Tax " + textFieldTax.getText() + " %");
            }
        });

        textFieldNoOfCopies = new JTextField();
        textFieldNoOfCopies.setText("0");
        textFieldNoOfCopies.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNoOfCopies.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNoOfCopies.setDisabledTextColor(Color.BLACK);
        textFieldNoOfCopies.setColumns(10);
        textFieldNoOfCopies.setBounds(948, 170, 35, 30);
        panelSettings.add(textFieldNoOfCopies);

        textFieldNoOfCopies1 = new JTextField();
        textFieldNoOfCopies1.setText("0");
        textFieldNoOfCopies1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNoOfCopies1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNoOfCopies1.setDisabledTextColor(Color.BLACK);
        textFieldNoOfCopies1.setColumns(10);
        textFieldNoOfCopies1.setBounds(1103, 172, 35, 30);
        panelSettings.add(textFieldNoOfCopies1);
        textFieldTax.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTax.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTax.setDisabledTextColor(Color.BLACK);
        textFieldTax.setColumns(10);
        textFieldTax.setBounds(460, 278, 100, 30);
        panelSettings.add(textFieldTax);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFocusable(false);
        btnUpdate.addActionListener(arg0 -> updateSettings());
        btnUpdate.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnUpdate.setBounds(664, 228, 150, 25);
        panelSettings.add(btnUpdate);

        JButton btnResetWeights = new JButton("Reset Weights");
        btnResetWeights.setFocusable(false);
        btnResetWeights.addActionListener(arg0 -> {
            JPasswordField password = new JPasswordField(10);
            JPanel panel = new JPanel();
            String[] ConnectOptionNames = {"Enter", "Cancel"};
            panel.add(new JLabel("Please the Password ? "));
            panel.add(password);
            JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
            char[] temp = password.getPassword();
            boolean isCorrect;
            char[] correctPassword = {'1', '2', '3', '4', '5', '6'};
            if (temp.length != correctPassword.length) {
                isCorrect = false;
            } else {
                isCorrect = Arrays.equals(temp, correctPassword);
            }
            if (isCorrect) {
                String response;
                response = JOptionPane.showInputDialog(null, "Please Enter the Starting Sl No ?", "Sl No",
                        JOptionPane.QUESTION_MESSAGE);
                if (response == null || Integer.parseInt("0" + response.replaceAll("[^0-9]", "")) == 0)
                    JOptionPane.showMessageDialog(null, "Reset Failed ", "Value Entered is not correct",
                            JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        PreparedStatement stmts = dbConnection.prepareStatement("DELETE FROM WEIGHING");
                        stmts.executeUpdate();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                        rs.absolute(1);
                        rs.updateInt("SLNO", Integer.parseInt(response.replaceAll("[^0-9]", "")));
                        rs.updateRow();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2836",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    settings();
                    JOptionPane.showMessageDialog(null, "Reset Successful ", "Reset Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else
                JOptionPane.showMessageDialog(null, "Wrong Password ", "Value Entered the Correct Password",
                        JOptionPane.ERROR_MESSAGE);
        });

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFocusable(false);
        btnRefresh.addActionListener(e -> settings());
        btnRefresh.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnRefresh.setBounds(865, 228, 150, 25);
        panelSettings.add(btnRefresh);
        btnResetWeights.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnResetWeights.setBounds(865, 273, 150, 25);
        panelSettings.add(btnResetWeights);

        btnPassword = new JButton("Unlock");
        btnPassword.setFocusable(false);
        btnPassword.addActionListener(e -> {

            if (Objects.equals(btnPassword.getText(), "Unlock")) {
                JPasswordField password = new JPasswordField(10);
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {"Enter", "Cancel"};
                panel.add(new JLabel("Please the Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = {'1', '2', '3', '4', '5', '6'};
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    chckbxManualEntry.setEnabled(true);
                    chckbxEditEnable.setEnabled(true);
                    chckbxRemoveBillinTab.setEnabled(true);
                    chckbxCamera.setEnabled(true);
                    chckbxSms.setEnabled(true);
                    chckbxExcludeCustomer.setEnabled(true);
                    chckbxExcludeCharges.setEnabled(true);
                    chckbxExcludeDrivers.setEnabled(true);
                    chckbxExcludeRemarks.setEnabled(true);
                    chckbxAutoCharges.setEnabled(true);
                    chckbxCharges.setEnabled(true);
                    chckbxMaterialSl.setEnabled(true);
                    textFieldBaudRate.setEnabled(true);
                    textFieldPortName.setEnabled(true);
                    textFieldSMSBaudRate.setEnabled(true);
                    textFieldSMSPortName.setEnabled(true);
                    chckbxenableSettings2.setEnabled(true);
                    btnPassword.setText("Lock");
                }
            } else {
                chckbxManualEntry.setEnabled(false);
                chckbxEditEnable.setEnabled(false);
                chckbxRemoveBillinTab.setEnabled(false);
                chckbxCamera.setEnabled(false);
                chckbxSms.setEnabled(false);
                chckbxExcludeCustomer.setEnabled(false);
                chckbxExcludeCharges.setEnabled(false);
                chckbxExcludeDrivers.setEnabled(false);
                chckbxExcludeRemarks.setEnabled(false);
                chckbxAutoCharges.setEnabled(false);
                chckbxCharges.setEnabled(false);
                chckbxMaterialSl.setEnabled(false);
                textFieldBaudRate.setEnabled(false);
                textFieldPortName.setEnabled(false);
                textFieldSMSBaudRate.setEnabled(false);
                textFieldSMSPortName.setEnabled(false);
                chckbxenableSettings2.setSelected(false);
                chckbxenableSettings2.setEnabled(false);
                btnPassword.setText("Unlock");
            }
        });
        btnPassword.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnPassword.setBounds(664, 273, 150, 25);
        panelSettings.add(btnPassword);

        btnResetBills = new JButton("Reset Bills");
        btnResetBills.setFocusable(false);
        btnResetBills.addActionListener(arg0 -> {
            JPasswordField password = new JPasswordField(10);
            JPanel panel = new JPanel();
            String[] ConnectOptionNames = {"Enter", "Cancel"};
            panel.add(new JLabel("Please the Password ? "));
            panel.add(password);
            JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
            char[] temp = password.getPassword();
            boolean isCorrect;
            char[] correctPassword = {'1', '2', '3', '4', '5', '6'};
            if (temp.length != correctPassword.length) {
                isCorrect = false;
            } else {
                isCorrect = Arrays.equals(temp, correctPassword);
            }
            if (isCorrect) {
                String response;
                response = JOptionPane.showInputDialog(null, "Please Enter the Starting Bill No ?", "Bill No",
                        JOptionPane.QUESTION_MESSAGE);
                if (response == null || Integer.parseInt("0" + response.replaceAll("[^0-9]", "")) == 0)
                    JOptionPane.showMessageDialog(null, "Reset Failed ", "Value Entered is not correct",
                            JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        PreparedStatement stmts = dbConnection.prepareStatement("DELETE FROM BILLING");
                        stmts.executeUpdate();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                        rs.absolute(1);
                        rs.updateInt("BILLNO", Integer.parseInt(response.replaceAll("[^0-9]", "")));
                        rs.updateRow();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2836",
                                "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    settings();
                    JOptionPane.showMessageDialog(null, "Reset Successful ", "Reset Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else
                JOptionPane.showMessageDialog(null, "Wrong Password ", "Value Entered the Correct Password",
                        JOptionPane.ERROR_MESSAGE);
        });
        btnResetBills.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnResetBills.setBounds(1063, 273, 150, 25);
        panelSettings.add(btnResetBills);

        label = new JLabel("%");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        label.setBounds(570, 278, 25, 25);
        panelSettings.add(label);

        chckbxExcludeCustomer = new JCheckBox("Exclude Customer");
        chckbxExcludeCustomer.setEnabled(false);
        chckbxExcludeCustomer.setFocusable(false);
        chckbxExcludeCustomer.addChangeListener(e -> {
            if (chckbxExcludeCustomer.isSelected())
                textFieldCustomerName.setEnabled(false);
            else
                textFieldCustomerName.setEnabled(true);
        });
        chckbxExcludeCustomer.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeCustomer.setBackground(new Color(0, 255, 127));
        chckbxExcludeCustomer.setBounds(25, 223, 145, 25);
        panelSettings.add(chckbxExcludeCustomer);

        chckbxRemoveBillinTab = new JCheckBox("Remove Billing");
        chckbxRemoveBillinTab.setFocusable(false);
        chckbxRemoveBillinTab.setEnabled(false);
        chckbxRemoveBillinTab.addActionListener(e -> billEvent());
        chckbxRemoveBillinTab.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxRemoveBillinTab.setBackground(new Color(0, 255, 127));
        chckbxRemoveBillinTab.setBounds(600, 100, 200, 25);
        panelSettings.add(chckbxRemoveBillinTab);

        chckbxExcludeDrivers = new JCheckBox("Exclude Drivers");
        chckbxExcludeDrivers.setEnabled(false);
        chckbxExcludeDrivers.setFocusable(false);
        chckbxExcludeDrivers.addChangeListener(e -> {
            if (chckbxExcludeDrivers.isSelected())
                textFieldDriverName.setEnabled(false);
            else
                textFieldDriverName.setEnabled(true);
        });
        chckbxExcludeDrivers.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeDrivers.setBackground(new Color(0, 255, 127));
        chckbxExcludeDrivers.setBounds(25, 279, 145, 23);
        panelSettings.add(chckbxExcludeDrivers);

        chckbxCamera = new JCheckBox("Camera");
        chckbxCamera.setSelected(true);
        chckbxCamera.addActionListener(e -> cameraEvent());

        chckbxCamera.setFocusable(false);
        chckbxCamera.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxCamera.setEnabled(false);
        chckbxCamera.setBackground(new Color(0, 255, 127));
        chckbxCamera.setBounds(600, 150, 199, 25);
        panelSettings.add(chckbxCamera);

        comboBoxPrintOptionForWeight = new JComboBox<>();
        comboBoxPrintOptionForWeight.setModel(new DefaultComboBoxModel<>(
                new String[]{"Pre Print", "Plain Paper", "Camera", "Plain Camera", "Sri Pathy"}));
        comboBoxPrintOptionForWeight.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxPrintOptionForWeight.setFocusable(false);
        comboBoxPrintOptionForWeight.setBounds(988, 170, 105, 30);
        panelSettings.add(comboBoxPrintOptionForWeight);

        comboBoxPrintOptionForBill = new JComboBox<>();
        comboBoxPrintOptionForBill.setModel(new DefaultComboBoxModel<>(new String[]{"Pre Print", "Plain Paper"}));
        comboBoxPrintOptionForBill.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxPrintOptionForBill.setFocusable(false);
        comboBoxPrintOptionForBill.setBounds(1144, 170, 101, 30);
        panelSettings.add(comboBoxPrintOptionForBill);

        chckbxSms = new JCheckBox("SMS");
        chckbxSms.addActionListener(e -> {
            if (chckbxSms.isSelected()) {
                JPasswordField password = new JPasswordField(10);
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {"Enter", "Cancel"};
                panel.add(new JLabel("Please the SMS 		Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = {'d', 'e', 'v', 'j', 'i', 's', 'h'};
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                } else {
                    chckbxSms.setSelected(false);
                }
            }
        });
        chckbxSms.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxSms.setFocusable(false);
        chckbxSms.setEnabled(false);
        chckbxSms.setBackground(new Color(0, 255, 127));
        chckbxSms.setBounds(600, 175, 200, 25);
        panelSettings.add(chckbxSms);

        textFieldSMSPortName = new JTextField();
        textFieldSMSPortName.setEnabled(false);
        textFieldSMSPortName.setText(null);
        textFieldSMSPortName.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSMSPortName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldSMSPortName.setDisabledTextColor(Color.BLACK);
        textFieldSMSPortName.setColumns(10);
        textFieldSMSPortName.setBounds(460, 195, 100, 30);
        panelSettings.add(textFieldSMSPortName);

        textFieldSMSBaudRate = new JTextField();
        textFieldSMSBaudRate.setEnabled(false);
        textFieldSMSBaudRate.setText("0");
        textFieldSMSBaudRate.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSMSBaudRate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldSMSBaudRate.setDisabledTextColor(Color.BLACK);
        textFieldSMSBaudRate.setColumns(10);
        textFieldSMSBaudRate.setBounds(460, 159, 100, 30);
        panelSettings.add(textFieldSMSBaudRate);

        JLabel lblSmsSettings = new JLabel("SMS Settings");
        lblSmsSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblSmsSettings.setBounds(336, 125, 200, 25);
        panelSettings.add(lblSmsSettings);

        JLabel label_2 = new JLabel("Baud Rate");
        label_2.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label_2.setBounds(336, 159, 100, 25);
        panelSettings.add(label_2);

        JLabel label_3 = new JLabel("Port Name");
        label_3.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label_3.setBounds(336, 195, 100, 25);
        panelSettings.add(label_3);

        JButton btnResetTrasporter = new JButton("Reset Driver");
        btnResetTrasporter.addActionListener(e -> {

            JPasswordField password = new JPasswordField(10);
            JPanel panel = new JPanel();
            String[] ConnectOptionNames = {"Enter", "Cancel"};
            panel.add(new JLabel("Please the Password ? "));
            panel.add(password);
            JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
            char[] temp = password.getPassword();
            boolean isCorrect;
            char[] correctPassword = {'1', '2', '3', '4', '5', '6'};
            if (temp.length != correctPassword.length) {
                isCorrect = false;
            } else {
                isCorrect = Arrays.equals(temp, correctPassword);
            }
            if (isCorrect) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    stmt.executeUpdate("truncate table TRANSPORTER");
                    ResultSet rs = stmt.executeQuery("SELECT * FROM TRANSPORTER");
                    textFieldDriverName.removeAllItems();
                    while (rs.next()) {
                        textFieldDriverName.addItem(rs.getString("TRANSPORTER"));
                        textFieldDriverName.setSelectedIndex(-1);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnResetTrasporter.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnResetTrasporter.setFocusable(false);
        btnResetTrasporter.setBounds(1063, 228, 150, 25);
        panelSettings.add(btnResetTrasporter);

        chckbxExcludeRemarks = new JCheckBox("Exclude Remarks");
        chckbxExcludeRemarks.setEnabled(false);
        chckbxExcludeRemarks.addChangeListener(e -> {
            if (chckbxExcludeRemarks.isSelected())
                textPaneRemarks.setEnabled(false);
            else
                textPaneRemarks.setEnabled(true);
        });
        chckbxExcludeRemarks.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeRemarks.setFocusable(false);
        chckbxExcludeRemarks.setBackground(new Color(0, 255, 127));
        chckbxExcludeRemarks.setBounds(175, 223, 145, 25);
        panelSettings.add(chckbxExcludeRemarks);

        chckbxAutoCharges = new JCheckBox("Auto Charges");
        chckbxAutoCharges.addChangeListener(e -> {
            if (chckbxAutoCharges.isSelected()) {
                btnAuto.setEnabled(true);
                chckbxExcludeCharges.setEnabled(false);
                chckbxExcludeCharges.setSelected(true);
            } else {
                if (chckbxCharges != null && !chckbxCharges.isSelected())
                    btnAuto.setEnabled(false);
                chckbxExcludeCharges.setEnabled(true);
            }
        });

        chckbxAutoCharges.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxAutoCharges.setFocusable(false);
        chckbxAutoCharges.setEnabled(false);
        chckbxAutoCharges.setBackground(new Color(0, 255, 127));
        chckbxAutoCharges.setBounds(174, 251, 115, 25);
        panelSettings.add(chckbxAutoCharges);

        chckbxMaterialSl = new JCheckBox("Material Sl");
        chckbxMaterialSl.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxMaterialSl.setFocusable(false);
        chckbxMaterialSl.setEnabled(false);
        chckbxMaterialSl.setBackground(new Color(0, 255, 127));
        chckbxMaterialSl.setBounds(174, 276, 139, 25);
        panelSettings.add(chckbxMaterialSl);

        chckbxCharges = new JCheckBox("Charges2");
        chckbxCharges.addChangeListener(e -> {
            if (chckbxCharges.isSelected()) {
                chckbxAutoCharges.setSelected(false);
                chckbxExcludeCharges.setEnabled(true);
                chckbxExcludeCharges.setSelected(false);
                btnAuto.setEnabled(true);
                chckbxChargecheck.setEnabled(true);
            } else {
                if (!chckbxAutoCharges.isSelected())
                    btnAuto.setEnabled(false);
                chckbxChargecheck.setEnabled(false);
            }
        });
        chckbxCharges.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxCharges.setFocusable(false);
        chckbxCharges.setEnabled(false);
        chckbxCharges.setBackground(new Color(0, 255, 127));
        chckbxCharges.setBounds(286, 251, 25, 25);
        panelSettings.add(chckbxCharges);

        chckbxenableSettings2 = new JCheckBox("");
        chckbxenableSettings2.setEnabled(false);
        chckbxenableSettings2.addChangeListener(e -> {
            if (chckbxenableSettings2.isSelected()) {
                tabbedPane.setEnabledAt(5, true);
                tabbedPane.setTitleAt(5, "          Settings2          ");
            } else {
                tabbedPane.setEnabledAt(5, false);
                tabbedPane.setTitleAt(5, "");
            }
        });
        chckbxenableSettings2.setBackground(new Color(0, 255, 127));
        chckbxenableSettings2.setBounds(917, 195, 25, 25);
        panelSettings.add(chckbxenableSettings2);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("", null, panel, null);
        tabbedPane.setEnabledAt(5, false);
        panel.setLayout(null);

        JLabel lblLine1 = new JLabel("Line 1");
        lblLine1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblLine1.setBounds(40, 52, 75, 25);
        panel.add(lblLine1);

        textFieldLine1 = new JTextField();
        textFieldLine1.setToolTipText("");
        textFieldLine1.setText(null);
        textFieldLine1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLine1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldLine1.setDisabledTextColor(Color.BLACK);
        textFieldLine1.setColumns(10);
        textFieldLine1.setBounds(131, 52, 200, 30);
        panel.add(textFieldLine1);

        JLabel lblLine2 = new JLabel("Line 2");
        lblLine2.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblLine2.setBounds(40, 102, 75, 25);
        panel.add(lblLine2);

        textFieldLine2 = new JTextField();
        textFieldLine2.setToolTipText("");
        textFieldLine2.setText(null);
        textFieldLine2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLine2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldLine2.setDisabledTextColor(Color.BLACK);
        textFieldLine2.setColumns(10);
        textFieldLine2.setBounds(131, 102, 200, 30);
        panel.add(textFieldLine2);

        JLabel lblLine3 = new JLabel("Line 3");
        lblLine3.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblLine3.setBounds(40, 152, 75, 25);
        panel.add(lblLine3);

        textFieldLine3 = new JTextField();
        textFieldLine3.setText(null);
        textFieldLine3.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLine3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldLine3.setDisabledTextColor(Color.BLACK);
        textFieldLine3.setColumns(10);
        textFieldLine3.setBounds(131, 152, 200, 30);
        panel.add(textFieldLine3);

        textFieldSiteAt = new JTextField();
        textFieldSiteAt.setText(null);
        textFieldSiteAt.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSiteAt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldSiteAt.setDisabledTextColor(Color.BLACK);
        textFieldSiteAt.setColumns(10);
        textFieldSiteAt.setBounds(576, 147, 200, 30);
        panel.add(textFieldSiteAt);

        textFieldDepartmentName = new JTextField();
        textFieldDepartmentName.setToolTipText("");
        textFieldDepartmentName.setText(null);
        textFieldDepartmentName.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldDepartmentName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldDepartmentName.setDisabledTextColor(Color.BLACK);
        textFieldDepartmentName.setColumns(10);
        textFieldDepartmentName.setBounds(576, 97, 200, 30);
        panel.add(textFieldDepartmentName);

        textFieldNameOfContractor = new JTextField();
        textFieldNameOfContractor.setToolTipText("");
        textFieldNameOfContractor.setText(null);
        textFieldNameOfContractor.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNameOfContractor.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNameOfContractor.setDisabledTextColor(Color.BLACK);
        textFieldNameOfContractor.setColumns(10);
        textFieldNameOfContractor.setBounds(576, 47, 200, 30);
        panel.add(textFieldNameOfContractor);

        JLabel lblNameOfContractor = new JLabel("Name Of Contractor");
        lblNameOfContractor.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblNameOfContractor.setBounds(385, 47, 193, 25);
        panel.add(lblNameOfContractor);

        JLabel lblDepartmentName = new JLabel("Department Name");
        lblDepartmentName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDepartmentName.setBounds(385, 97, 179, 25);
        panel.add(lblDepartmentName);

        JLabel lblSiteAt = new JLabel("Site At");
        lblSiteAt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblSiteAt.setBounds(385, 147, 179, 25);
        panel.add(lblSiteAt);

        textFieldLine4 = new JTextField();
        textFieldLine4.setText(null);
        textFieldLine4.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLine4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldLine4.setDisabledTextColor(Color.BLACK);
        textFieldLine4.setColumns(10);
        textFieldLine4.setBounds(131, 205, 200, 30);
        panel.add(textFieldLine4);

        JLabel lblLine = new JLabel("Line 4");
        lblLine.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblLine.setBounds(40, 205, 75, 25);
        panel.add(lblLine);

        JLabel lblSriPathySettings = new JLabel("Sri Pathy Settings");
        lblSriPathySettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblSriPathySettings.setBounds(40, 13, 150, 25);
        panel.add(lblSriPathySettings);

        chckbxTareNoSlno = new JCheckBox("Tare no SlNo");
        chckbxTareNoSlno.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxTareNoSlno.setFocusable(false);
        chckbxTareNoSlno.setBackground(new Color(0, 255, 127));
        chckbxTareNoSlno.setBounds(462, 208, 200, 25);
        panel.add(chckbxTareNoSlno);

        JButton button = new JButton("Minimize");
        button.addActionListener(e -> frmBabulensWeighbridgeDesigned.setState(Frame.ICONIFIED));
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setFocusable(false);
        button.setBounds(518, 11, 117, 30);
        frmBabulensWeighbridgeDesigned.getContentPane().add(button);

    }

    private void rePrint(String response) {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + response);
            if (rs.next()) {
                // rs.absolute(Integer.parseInt(response));
                textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
                textFieldDcNo.setText(rs.getString("DCNO"));
                textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? ""
                        : "" + dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                textFieldCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                textFieldDriverName.setSelectedItem(rs.getString("DRIVERNAME"));
                textFieldVehicleNo.setText(rs.getString("VEHICLENO"));
                comboBoxMaterial.setSelectedItem(rs.getString("MATERIAL"));
                textFieldCharges.setText(Integer.toString(rs.getInt("CHARGES")));
                textFieldGrossWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                textFieldGrossDateTime.setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));
                if (textFieldGrossDateTime.getText().equals("null null"))
                    textFieldGrossDateTime.setText("");
                else
                    textFieldGrossDateTime.setText(dateAndTimeFormat
                            .format(new Date(dateAndTimeFormatSql.parse(textFieldGrossDateTime.getText()).getTime())));
                textFieldTareWt.setText(Integer.toString(rs.getInt("TAREWT")));
                textFieldTareDateTime.setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                if (textFieldTareDateTime.getText().equals("null null"))
                    textFieldTareDateTime.setText("");
                else
                    textFieldTareDateTime.setText(dateAndTimeFormat
                            .format(new Date(dateAndTimeFormatSql.parse(textFieldTareDateTime.getText()).getTime())));
                textFieldNetWt.setText(Integer.toString(rs.getInt("NETWT")));
                textFieldNoOfUnits.setText(Integer.toString(rs.getInt("NETWT")));
                textFieldNetDateTime.setText(rs.getDate("NETDATE") + " " + rs.getTime("NETTIME"));
                if (textFieldNetDateTime.getText().equals("null null"))
                    textFieldNetDateTime.setText("");
                else
                    textFieldNetDateTime.setText(dateAndTimeFormat
                            .format(new Date(dateAndTimeFormatSql.parse(textFieldNetDateTime.getText()).getTime())));
                textPaneRemarks.setText(rs.getString("REMARKS"));
            } else {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nRECORD NOT FOUND\nLINE :1085", "SQL ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1085", "SQL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
        rdbtnGross.setEnabled(false);
        btnGetTareSl.setEnabled(false);
        rdbtnTare.setEnabled(false);
        btnGetGrossSl.setEnabled(false);
        textFieldVehicleNo.setEnabled(false);
        comboBoxMaterial.setEnabled(false);
        textFieldCharges.setEnabled(false);
        btnGetWeight.setEnabled(false);
        btnSave.setEnabled(false);
        btnPrint.setEnabled(true);
        btnGetDcDetails.setEnabled(false);
        btnGetGross.setEnabled(false);
        btnGetTare.setEnabled(false);
        btnTotal.setEnabled(false);
        btnMinusGross.setEnabled(false);
        btnPlusTare.setEnabled(false);
        textPaneRemarks.setEnabled(false);
        btnPrint.requestFocus();
    }

    private void clear() {
        if (chckbxCamera.isSelected()) {
            if (checkBoxCamera1.isSelected()) {
                try {
                    panelCameras.remove(labelCamera1);
                    panelCameras.add(panelCamera1);
                } catch (NullPointerException ignored) {
                }
            }
            if (checkBoxCamera2.isSelected()) {
                try {
                    panelCameras.remove(labelCamera2);
                    panelCameras.add(panelCamera2);
                } catch (NullPointerException ignored) {
                }
            }
            if (checkBoxCamera3.isSelected()) {
                try {
                    panelCameras.remove(labelCamera3);
                    panelCameras.add(panelCamera3);
                } catch (NullPointerException ignored) {
                }
            }
            if (checkBoxCamera4.isSelected()) {
                try {
                    panelCameras.remove(labelCamera4);
                    panelCameras.add(panelCamera4);
                } catch (NullPointerException ignored) {
                }
            }
        }
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
            rs.absolute(1);
            textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
            textFieldReferenceSlNo.setText(Integer.toString(rs.getInt("SLNO")));
            textFieldBillNo.setText(Integer.toString(rs.getInt("BILLNO")));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2862", "SQL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM TRANSPORTER");
            textFieldDriverName.removeAllItems();
            while (rs.next()) {
                textFieldDriverName.addItem(rs.getString("TRANSPORTER"));
                textFieldDriverName.setSelectedIndex(-1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2862", "SQL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

        textFieldCustomerName.setSelectedIndex(-1);
        textFieldDriverName.setSelectedIndex(-1);
        rdbtnGross.setEnabled(true);
        rdbtnGross.setSelected(true);
        btnGetTareSl.setEnabled(true);
        rdbtnTare.setEnabled(true);
        btnGetGrossSl.setEnabled(true);
        btnMinusGross.setEnabled(true);
        btnPlusTare.setEnabled(true);
        textFieldVehicleNo.setEnabled(true);
        textFieldVehicleNo.setText("");
        comboBoxMaterial.setEnabled(true);
        comboBoxMaterial.setSelectedIndex(-1);
        textFieldCharges.setEnabled(!chckbxExcludeCharges.isSelected());
        textFieldCharges.setText("");
        textFieldGrossWt.setText("0");
        textFieldTareWt.setText("0");
        textFieldNetWt.setText("0");
        textFieldNoOfUnits.setText("0");
        textFieldGrossDateTime.setText("");
        textFieldTareDateTime.setText("");
        textFieldNetDateTime.setText("");
        btnSave.setEnabled(false);
        btnPrint.setEnabled(false);
        btnGetWeight.setEnabled(true);

        textFieldReferenceSlNo.setEnabled(false);
        button1.setEnabled(true);
        comboBoxCustomerName.setEnabled(true);
        comboBoxCustomerName.setSelectedIndex(-1);

        textFieldCustomerAddress.setEnabled(false);
        textFieldCustomerAddress.setText("");
        button2.setEnabled(true);
        textFieldCustomerAddress1.setEnabled(false);
        textFieldCustomerAddress1.setText("");
        button8.setEnabled(true);
        comboBoxMaterialName.setEnabled(false);
        comboBoxMaterialName.setSelectedIndex(-1);
        button3.setEnabled(true);
        textFieldCostPerunit.setEnabled(false);
        textFieldCostPerunit.setText("0");
        button4.setEnabled(true);
        textFieldNoOfUnits.setEnabled(false);
        textFieldNoOfUnits.setText("0");
        button5.setEnabled(true);
        datePicker.setEnabled(false);
        button6.setEnabled(true);
        textFieldDiscount.setEnabled(false);
        textFieldDiscount.setText("0");
        button7.setEnabled(true);
        textFieldRemarks.setEnabled(true);
        textFieldRemarks.setText("");
        textFieldVat.setText("0");
        textFieldTotal.setText("0");
        textFieldTotalVat.setText("0");
        textFieldAmountToBePaid.setText("0");
        datePicker.setDate(null);
        btnCalculate.setEnabled(true);
        btnSaveBill.setEnabled(false);
        btnPrintBill.setEnabled(false);
        lblAmount.setText("0");
        if (chckbxExcludeCustomer.isSelected())
            if (chckbxExcludeDrivers.isSelected())
                textFieldVehicleNo.requestFocus();
            else
                textFieldDriverName.requestFocus();
        else
            textFieldCustomerName.requestFocus();
        if (!chckbxExcludeCustomer.isSelected())
            textFieldCustomerName.setEnabled(true);
        else
            textFieldCustomerName.setEnabled(false);
        if (!chckbxExcludeDrivers.isSelected())
            textFieldDriverName.setEnabled(true);
        else
            textFieldDriverName.setEnabled(false);
        if (!chckbxExcludeCharges.isSelected())
            textFieldCharges.setEnabled(true);
        else
            textFieldCharges.setEnabled(false);
        if (!chckbxExcludeRemarks.isSelected())
            textPaneRemarks.setEnabled(true);
        else
            textPaneRemarks.setEnabled(false);

        if (chckbxCharges.isSelected())
            chckbxChargecheck.setEnabled(true);

        if (chckbxAutoCharges.isSelected() || chckbxCharges.isSelected())
            btnAuto.setEnabled(true);

        chckbxChargecheck.setSelected(false);

        textPaneRemarks.setText("");
        textFieldDcNo.setText("");
        textFieldDcDate.setText("");
        btnGetDcDetails.setEnabled(true);
        if (chckbxManualEntry.isSelected()) {
            btnGetGross.setEnabled(true);
            btnGetTare.setEnabled(true);
        }
    }

    private void printPlainWeight() {
        JTextPane textPane = createTextPane1();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();

        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 4d * 72d;
        double widthmargin = .50d * 72d;
        double heightmargin = .25d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter1.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createTextPane1() {
        String format = " %1$-13s: %2$-15s%3$-12s: %4$-20s\n";
        String format1 = "     %1$-9s: %2$-7s Kg               %3$-20s\n";
        String format2 = " %1$-18s: %2$-30s\n";
        String format3 = "     %1$-9s: %2$s";
        String dc = "";
        String driver = "";
        //noinspection StatementWithEmptyBody
        if (textFieldDcNo.getText().trim().equals("") || textFieldDcDate.getText().trim().equals(""))
            ;
        else {
            dc = String.format(format, "Dc. No", textFieldDcNo.getText(), "Dc. Date", textFieldDcDate.getText());
        }
        if (chckbxExcludeDrivers.isSelected()
                || !textFieldDriverName.getEditor().getItem().toString().trim().equals("")) {
            driver = String.format(format2, "Transpoter's Name", textFieldDriverName.getEditor().getItem());
        }
        String[] initString = {"\n" + StringUtils.center(title1.getText(), 39) + "\n",
                StringUtils.center(title2.getText(), 65) + "\n",
                "-----------------------------------------------------------------\n", // 65
                String.format(format, "Sl.No", textFieldSlNo.getText(), "Date & Time", textFieldNetDateTime.getText()),
                dc, String.format(format2, "Customer's Name", textFieldCustomerName.getEditor().getItem()), driver,
                String.format(format, "Vehicle No", textFieldVehicleNo.getText(), "Material",
                        comboBoxMaterial.getEditor().getItem()),
                "-----------------------------------------------------------------\n",
                String.format(format1, "Gross Wt", StringUtils.leftPad(textFieldGrossWt.getText(), 7, " "),
                        textFieldGrossDateTime.getText()),
                String.format(format1, "Tare Wt", StringUtils.leftPad(textFieldTareWt.getText(), 7, " "),
                        textFieldTareDateTime.getText()),
                String.format(format1, "Net Wt", StringUtils.leftPad(textFieldNetWt.getText(), 7, " "),
                        "No of Bags : " + textFieldCharges.getText()),
                chckbxExcludeRemarks.isEnabled() && !Objects.equals(textPaneRemarks.getText(), "") ? ""
                        : String.format(format3, "Remarks", textPaneRemarks.getText()) + "\n",
                "-----------------------------------------------------------------\n",
                StringUtils.rightPad(textFieldFooter.getText(), 50, " ") + "Signature"};
        String[] initStyles = {"1", "2", "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "4"};
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument1(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void addStylesToDocument1(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "Courier New");

        Style s = doc.addStyle("1", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 20);

        s = doc.addStyle("2", regular);
        StyleConstants.setItalic(s, true);
        StyleConstants.setFontSize(s, 12);

        s = doc.addStyle("3", regular);
        StyleConstants.setFontSize(s, 12);

        s = doc.addStyle("4", regular);
        StyleConstants.setItalic(s, true);
        StyleConstants.setFontSize(s, 12);
    }

    private void printPreWeight() {
        JTextPane textPane = createTextPane5();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 6d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 1.25d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter1.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createTextPane5() {
        String format = "%1$-6s%2$-30s%3$-30s%4$-12s";
        String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");
        String[] initString = {String.format(format, "", temp[0], temp[0], temp[0]), "\n\n",
                String.format(format, "", temp[1] + " " + temp[2], temp[1] + " " + temp[2], temp[1] + " " + temp[2]),
                "\n\n",
                String.format(format, "", textFieldSlNo.getText(), textFieldSlNo.getText(), textFieldSlNo.getText()),
                "\n\n",
                String.format(format, "", comboBoxMaterial.getEditor().getItem(),
                        comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem()),
                "\n\n",
                String.format(format, "", textFieldVehicleNo.getText(), textFieldVehicleNo.getText(),
                        textFieldVehicleNo.getText()),
                "\n\n",
                String.format(
                        format, "", textFieldCharges.getText(), textFieldCharges.getText(), textFieldCharges.getText()),
                "\n\n",
                String.format(format, "", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg",
                        textFieldGrossWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg",
                        textFieldTareWt.getText() + " Kg"),
                "\n\n", String.format(format, "", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg",
                textFieldNetWt.getText() + " Kg")};

        String[] initStyles = {"1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "3", "1", "3", "1",

        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument4(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void addStylesToDocument4(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "Courier New");

        Style s = doc.addStyle("1", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 12);

        s = doc.addStyle("2", regular);
        StyleConstants.setFontSize(s, 8);

        s = doc.addStyle("3", regular);
        StyleConstants.setFontSize(s, 14);
    }

    private void printPlainBill() {
        JTextPane textPane = createTextPane2();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = .75d * 72d;
        double heightmargin = .5d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter2.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private JTextPane createTextPane2() {
        String format = " %1$-8s: %2$-36s%3$-5s: %4$-11s\n";
        String formata = " %1$-4s %2$-58s\n";
        String formatb = "%1$-38s %2$-12s %3$-12s\n";
        String formatc = "%1$-30s%2$-35s\n";
        String format2 = "%1$-42s%2$-36s\n";

        String[] initString = {String.format(format2, table1.getValueAt(0, 0), table2.getValueAt(0, 0)),
                String.format(format2, table1.getValueAt(1, 0), table2.getValueAt(1, 0)),
                String.format(format2, table1.getValueAt(2, 0), table2.getValueAt(2, 0)),
                String.format(format2, table1.getValueAt(3, 0), table2.getValueAt(3, 0)),
                String.format(format2, table1.getValueAt(4, 0), table2.getValueAt(4, 0)),
                StringUtils.center(labelBillTitle.getText(), 39) + "\n",
                String.format(format2, table3.getValueAt(0, 0), table4.getValueAt(0, 0)),
                String.format(format2, table3.getValueAt(1, 0), table4.getValueAt(1, 0)),
                String.format(format2, table3.getValueAt(2, 0), table4.getValueAt(2, 0)),
                String.format(format2, table3.getValueAt(3, 0), table4.getValueAt(3, 0)),
                String.format(format2, table3.getValueAt(4, 0), table4.getValueAt(4, 0)),
                String.format(format2, table3.getValueAt(5, 0), table4.getValueAt(5, 0)),
                "-----------------------------------------------------------------\n", // 65
                String.format(format, "Bill.No", textFieldBillNo.getText(), "Date",
                        datePicker.getDate() != null ? dateAndTimeFormatdatep.format(datePicker.getDate()) : ""), // dateAndTimeFormatdatep.format(datePicker.getDate())),
                String.format(formata, "M/s.", comboBoxCustomerName.getSelectedItem()),
                String.format(formata, "", textFieldCustomerAddress.getText()),
                String.format(formata, "", textFieldCustomerAddress1.getText()),
                "\n-----------------------------------------------------------------\n",
                String.format(formatb, StringUtils.center("Particulars", 38), StringUtils.center("Rate", 12),
                        StringUtils.center("Amount", 12)),
                "-----------------------------------------------------------------\n\n\n\n\n\n",
                String.format(formatb, StringUtils.rightPad("  " + comboBoxMaterialName.getSelectedItem(), 38), "", ""),
                String.format(formatb, StringUtils.rightPad("  " + textFieldNoOfUnits.getText() + " Kg", 38),
                        StringUtils.center(textFieldCostPerunit.getText() + ".00", 9),
                        StringUtils.leftPad(textFieldTotal.getText(), 9) + ".00"),
                "\n\n\n\n\n\n\n\n\n\n\n\n\n" + toWord(Integer.parseInt(textFieldAmountToBePaid.getText()))
                        + "\n\n-----------------------------------------------------------------\n",
                String.format(formatb, "", StringUtils.rightPad("Sub Total", 11),
                        StringUtils.leftPad(textFieldTotal.getText(), 9) + ".00"),
                String.format(formatb, "", StringUtils.rightPad("VAT " + textFieldTax.getText() + "%", 11) + "+",
                        StringUtils.leftPad(textFieldVat.getText(), 9) + ".00"),
                String.format(formatb, "", StringUtils.rightPad("Dicount", 11) + "-",
                        StringUtils.leftPad(textFieldDiscount.getText(), 9) + ".00"),
                String.format(formatb, "", StringUtils.rightPad("Total", 12),
                        StringUtils.leftPad(textFieldAmountToBePaid.getText(), 9) + ".00"),
                "-----------------------------------------------------------------\n",
                String.format(formatc, "", StringUtils.center("For " + labelBillTitle.getText(), 35)), "\n",
                String.format(formatc, "", StringUtils.center("Authorised Signature", 35)),
                "-----------------------------------------------------------------\n"};
        String[] initStyles = {"2", "2", "2", "2", "2", "1", "2", "2", "2", "2", "2", "2", "3", "3", "3", "3", "3",
                "3", "4", "3", "3", "3", "3", "4", "4", "4", "4", "3", "4", "3", "3", "3",};
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument2(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void addStylesToDocument2(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "Courier New");

        Style s = doc.addStyle("1", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 20);

        s = doc.addStyle("2", regular);
        StyleConstants.setItalic(s, true);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("3", regular);
        StyleConstants.setFontSize(s, 12);

        s = doc.addStyle("4", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 12);
    }

    private void printCameraWeight() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 6d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = .25d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(new Printable() {
            private Coordinates drawString(Graphics g, String text, int x, int y) {
                int length = 0;
                for (String line : text.split("\n")) {
                    g.drawString(line, x, y += g.getFontMetrics().getHeight() - 1);
                    length = g.getFontMetrics().stringWidth(line);
                }
                return new Coordinates(length, y + g.getFontMetrics().getHeight() - 1);
            }

            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                String format = "%1$-5s%2$-20s  ";

                String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");
                String initString = "\n\n" + StringUtils.center("", 62);
                graphics.setFont(new Font("Courier New", Font.BOLD, 15));

                Coordinates coordinates = drawString(graphics, initString, 0, 0);
                initString = StringUtils.center("", 73);
                graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 13));
                coordinates = drawString(graphics, initString, 0, coordinates.y);

                initString = StringUtils.center("", 79) + "\n";
                graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 12));
                coordinates = drawString(graphics, initString, 0, coordinates.y);

                initString = String.format(format, "", "") + textFieldSlNo.getText() + "\n\n"
                        + String.format(format, "", "") + temp[0] + "\n\n" + String.format(format, "", "") + temp[1]
                        + "\n\n" + String.format(format, "", "") + textFieldVehicleNo.getText() + "\n\n"
                        + String.format(format, "", "") + comboBoxMaterial.getEditor().getItem() + "\n\n"
                        + String.format(format, "", "") + comboBoxCustomerName.getEditor().getItem() + "\n\n"
                        + String.format(format, "", "") + textFieldCharges.getText() + "\n\n";
                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                coordinates = drawString(graphics, initString, 0, coordinates.y);

                initString = String.format(format, "", "");
                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                int yTemp = coordinates.y;
                coordinates = drawString(graphics, initString, 0, coordinates.y);
                int y = coordinates.y;

                initString = StringUtils.rightPad(textFieldGrossWt.getText(), 7) + "Kg";
                graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                drawString(graphics, initString, coordinates.x, yTemp);

                initString = String.format(format, "", "");
                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                yTemp = y;
                coordinates = drawString(graphics, initString, 0, y);
                y = coordinates.y;

                initString = StringUtils.rightPad(textFieldTareWt.getText(), 7) + "Kg";
                graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                drawString(graphics, initString, coordinates.x, yTemp);

                initString = String.format(format, "", "");
                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                yTemp = y;
                coordinates = drawString(graphics, initString, 0, y);

                initString = StringUtils.rightPad(textFieldNetWt.getText(), 7) + "Kg";
                graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                drawString(graphics, initString, coordinates.x, yTemp);

                try {
                    BufferedImage printImage = ImageIO
                            .read(new File("CameraOutput/" + textFieldSlNo.getText() + "_1.jpg"));
                    BufferedImage cropImage = printImage.getSubimage(
                            Integer.parseInt("0" + textFieldCropX1.getText().replaceAll("[^0-9]", "")),
                            Integer.parseInt("0" + textFieldCropY1.getText().replaceAll("[^0-9]", "")),
                            Integer.parseInt("0" + textFieldCropWidth1.getText().replaceAll("[^0-9]", "")),
                            Integer.parseInt("0" + textFieldCropHeight1.getText().replaceAll("[^0-9]", "")));
                    graphics.drawImage(cropImage, 250, 125, 300,
                            (int) (300.00 / cropImage.getWidth() * cropImage.getHeight()), null);
                } catch (IOException | NullPointerException ignored) {
                }

                return PAGE_EXISTS;
            }
        }, pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter1.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printPlainCameraWeight() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 6d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = .25d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(new Printable() {
            private Coordinates drawString(Graphics g, String text, int x, int y) {
                int length = 0;
                for (String line : text.split("\n")) {
                    g.drawString(line, x + 10, y += g.getFontMetrics().getHeight() - 1);
                    length = g.getFontMetrics().stringWidth(line);
                }
                return new Coordinates(length, y + g.getFontMetrics().getHeight() - 1);
            }

            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                String format = "%1$-5s%2$-20s: ";

                String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");
                String initString = "\n\n" + StringUtils.center(title1.getText(), 62);
                graphics.setFont(new Font("Courier New", Font.BOLD, 15));

                Coordinates coordinates = drawString(graphics, initString, 0, 0);
                initString = StringUtils.center(title2.getText(), 73);
                graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 13));
                coordinates = drawString(graphics, initString, 0, coordinates.y);

                initString = StringUtils.center("WEIGHMENT RECEIPT", 79) + "\n";
                graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 12));
                coordinates = drawString(graphics, initString, 0, coordinates.y);

                initString = String.format(format, "", "Sl.No") + textFieldSlNo.getText() + "\n\n"
                        + String.format(format, "", "Date") + temp[0] + "\n\n" + String.format(format, "", "Time")
                        + temp[1] + "\n\n" + String.format(format, "", "Vehicle No") + textFieldVehicleNo.getText()
                        + "\n\n" + String.format(format, "", "Material") + comboBoxMaterial.getEditor().getItem()
                        + "\n\n" + String.format(format, "", "Customer Name")
                        + comboBoxCustomerName.getEditor().getItem() + "\n\n" + String.format(format, "", "No of Bags")
                        + textFieldCharges.getText() + "\n\n";
                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                coordinates = drawString(graphics, initString, 0, coordinates.y);

                initString = String.format(format, "", "Gross Wt");
                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                int yTemp = coordinates.y;
                coordinates = drawString(graphics, initString, 0, coordinates.y);
                int y = coordinates.y;

                initString = StringUtils.rightPad(textFieldGrossWt.getText(), 7) + "Kg";
                graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                drawString(graphics, initString, coordinates.x, yTemp);

                initString = String.format(format, "", "Tare Wt");
                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                yTemp = y;
                coordinates = drawString(graphics, initString, 0, y);
                y = coordinates.y;

                initString = StringUtils.rightPad(textFieldTareWt.getText(), 7) + "Kg";
                graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                drawString(graphics, initString, coordinates.x, yTemp);

                initString = String.format(format, "", "Net Wt");
                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                yTemp = y;
                coordinates = drawString(graphics, initString, 0, y);

                initString = StringUtils.rightPad(textFieldNetWt.getText(), 7) + "Kg";
                graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                coordinates = drawString(graphics, initString, coordinates.x, yTemp);

                initString = "\n\n\n" + "     " + StringUtils.rightPad(textFieldFooter.getText(), 70, " ")
                        + "Signature";
                graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 10));
                drawString(graphics, initString, 0, coordinates.y);

                try {
                    BufferedImage printImage = ImageIO
                            .read(new File("CameraOutput/" + textFieldSlNo.getText() + "_1.jpg"));
                    BufferedImage cropImage = printImage.getSubimage(
                            Integer.parseInt("0" + textFieldCropX1.getText().replaceAll("[^0-9]", "")),
                            Integer.parseInt("0" + textFieldCropY1.getText().replaceAll("[^0-9]", "")),
                            Integer.parseInt("0" + textFieldCropWidth1.getText().replaceAll("[^0-9]", "")),
                            Integer.parseInt("0" + textFieldCropHeight1.getText().replaceAll("[^0-9]", "")));
                    graphics.drawImage(cropImage, 250, 125, 300,
                            (int) (300.00 / cropImage.getWidth() * cropImage.getHeight()), null);
                } catch (IOException | NullPointerException ignored) {
                }

                return PAGE_EXISTS;
            }
        }, pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter1.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    // TODO print
    @SuppressWarnings("UnusedReturnValue")
    private void printPlainSriPathyWeight() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 11.5d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(new Printable() {
            @SuppressWarnings("SameParameterValue")
            private void drawString(Graphics g, String text, int y) {
                int length = 0;
                for (String line : text.split("\n")) {
                    g.drawString(line, 0, y += g.getFontMetrics().getHeight() - 1);
                    length = g.getFontMetrics().stringWidth(line);
                }
                new Coordinates(length, y + g.getFontMetrics().getHeight() - 1);
            }

            @SuppressWarnings("SameReturnValue")
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                String format1 = "           %-19s: %-25s   %-10s : %s\n";
                String format2 = "           %-10s:%7s Kg   %-10s : %-12s   %-10s : %s\n";
                String format3 = "           %-10s:%7s Kg \n";
                String[] temp1 = new String[2];
                String[] temp2 = new String[2];
                try {
                    temp1 = dateAndTimeFormatPrint.format(dateAndTimeFormat.parse(textFieldGrossDateTime.getText()))
                            .split(" ");

                } catch (ParseException pe) {
                    temp1[0] = "";
                    temp1[1] = "";
                }
                try {
                    temp2 = dateAndTimeFormatPrint.format(dateAndTimeFormat.parse(textFieldTareDateTime.getText()))
                            .split(" ");

                } catch (ParseException pe) {
                    temp2[0] = "";
                    temp2[1] = "";
                }

                String initString = "\n\n\n\n\n\n\n\n\n\n" + "         "
                        + String.format("%72s", "Weighment Slip No : " + textFieldSlNo.getText()) + "\n\n" + "         "
                        + StringUtils.center(textFieldLine1.getText(), 82) + "\n" + "          "
                        + StringUtils.center(textFieldLine2.getText(), 82) + "\n" + "         "
                        + StringUtils.center(textFieldLine3.getText(), 82) + "\n\n" + "           Name of Contractor : "
                        + textFieldNameOfContractor.getText() + "\n\n"
                        + String.format(format1, "Department Name", textFieldDepartmentName.getText(), "Vehicle No",
                        textFieldVehicleNo.getText())
                        + "\n"
                        + String.format(format1, "Site At", textFieldSiteAt.getText(), "Product",
                        comboBoxMaterial.getEditor().getItem())
                        + "\n"
                        + String.format(
                        format2, "Gross Wt.", textFieldGrossWt.getText(), "Date", temp1[0], "Time", temp1[1])
                        + "\n"
                        + String.format(format2, "Tare Wt.", textFieldTareWt.getText(), "Date", temp2[0], "Time",
                        temp2[1])
                        + "\n" + String.format(format3, "Nett Wt.", textFieldNetWt.getText()) + "\n\n\n" + "         "
                        + textFieldLine4.getText() + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + "         "
                        + String.format("%72s", "Weighment Slip No : " + textFieldSlNo.getText()) + "\n\n" + "         "
                        + StringUtils.center(textFieldLine1.getText(), 82) + "\n" + "          "
                        + StringUtils.center(textFieldLine2.getText(), 82) + "\n" + "         "
                        + StringUtils.center(textFieldLine3.getText(), 82) + "\n\n" + "           Name of Contractor : "
                        + textFieldNameOfContractor.getText() + "\n\n"
                        + String.format(format1, "Department Name", textFieldDepartmentName.getText(), "Vehicle No",
                        textFieldVehicleNo.getText())
                        + "\n"
                        + String.format(format1, "Site At", textFieldSiteAt.getText(), "Product",
                        comboBoxMaterial.getEditor().getItem())
                        + "\n"
                        + String.format(
                        format2, "Gross Wt.", textFieldGrossWt.getText(), "Date", temp1[0], "Time", temp1[1])
                        + "\n"
                        + String.format(format2, "Tare Wt.", textFieldTareWt.getText(), "Date", temp2[0], "Time",
                        temp2[1])
                        + "\n" + String.format(format3, "Nett Wt.", textFieldNetWt.getText()) + "\n\n\n" + "         "
                        + textFieldLine4.getText();

                graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                drawString(graphics, initString, 0);
                graphics.drawLine(56, 129, 544, 129);
                graphics.drawLine(56, 173, 544, 173);
                graphics.drawLine(56, 195, 544, 195);
                graphics.drawLine(351, 195, 351, 239);
                graphics.drawLine(56, 239, 544, 239);
                graphics.drawLine(201, 239, 201, 283);
                graphics.drawLine(369, 239, 369, 283);
                graphics.drawLine(56, 283, 544, 283);
                graphics.drawLine(56, 305, 544, 305);
                graphics.drawLine(56, 129, 56, 305);
                graphics.drawLine(544, 129, 544, 305);

                graphics.drawLine(56, 547, 544, 547);
                graphics.drawLine(56, 591, 544, 591);
                graphics.drawLine(56, 613, 544, 613);
                graphics.drawLine(351, 613, 351, 657);
                graphics.drawLine(56, 657, 544, 657);
                graphics.drawLine(201, 657, 201, 702);
                graphics.drawLine(369, 657, 369, 702);
                graphics.drawLine(56, 702, 544, 702);
                graphics.drawLine(56, 724, 544, 724);
                graphics.drawLine(56, 547, 56, 724);
                graphics.drawLine(544, 547, 544, 724);

                return PAGE_EXISTS;
            }
        }, pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter1.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printReportWeight() {
        JTextPane textPane = createTextPane3();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = .75d * 72d;
        double heightmargin = 1d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf, 99);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter1.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createTextPane3() {
        TableModel model = tableReport.getModel();
        String format = " %1$-5s %2$-19s %3$-15s %4$-15s %5$-8s %6$-8s %7$-8s\n";
        String temp = "\n";
        for (int i = 0; i < model.getRowCount(); i++) {
            temp = temp.concat(String.format(format,
                    StringUtils.center(model.getValueAt(i, 0) != null ? model.getValueAt(i, 0).toString() : "", 5),
                    StringUtils.center(model.getValueAt(i, 13) != null ? model.getValueAt(i, 13).toString() : "", 10),
                    StringUtils.center(model.getValueAt(i, 5) != null ? model.getValueAt(i, 5).toString() : "", 15),
                    StringUtils.center(model.getValueAt(i, 6) != null ? model.getValueAt(i, 6).toString() : "", 15),
                    StringUtils.leftPad(model.getValueAt(i, 8) != null ? model.getValueAt(i, 8).toString() : "", 8,
                            " "),
                    StringUtils.leftPad(model.getValueAt(i, 10) != null ? model.getValueAt(i, 10).toString() : "", 8,
                            " "),
                    StringUtils.leftPad(model.getValueAt(i, 12) != null ? model.getValueAt(i, 12).toString() : "", 8,
                            " ")));
            temp = temp.concat("\n");
        }

        String[] initString = {StringUtils.center(title1.getText(), 39) + "\n",
                StringUtils.center(title2.getText(), 65) + "\n", StringUtils.center(getTitle(), 65) + "\n",
                "==================================================================================================\n",
                String.format(format, StringUtils.center("Sl.no", 5), StringUtils.center("Date & Time", 19),
                        StringUtils.center("Vehicle No", 15), StringUtils.center("Material", 15),
                        StringUtils.center("Gross Wt", 8), StringUtils.center("Tare Wt", 8),
                        StringUtils.center("Net Wt", 8)),
                "==================================================================================================\n",
                temp,
                "==================================================================================================\n",
                " ", "\n\tTotal Net Wt   " + textFieldtotalNetWt.getText(),
                "\n\tNo of Bags   " + textFieldTotalCharges.getText(), "\n\t\t\t\t\tSignature"};

        String[] initStyles = {"1", "2", "2", "3", "3", "3", "3", "3", "5", "5", "5", "5"};

        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument3(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private String getTitle() {

        if (rdbtnWeighing.isSelected()) {
            switch (Objects.requireNonNull(comboBox.getSelectedItem()).toString()) {
                case "Full Report":
                    return "Full Report";
                case "Daily Report":
                    return "Daily Report - " + dateAndTimeFormatdatep.format(datePicker1.getDate());
                case "Datewise Report":
                    return "Datewise Report - " + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to "
                            + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Serialwise Report":
                    return "Serialwise Report";
                case "Vehiclewise Report":
                    return "Vehiclewise Report (" + textFieldDetail.getText() + ") - "
                            + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to "
                            + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Materialwise Report":
                    return "Materialwise Report (" + comboBoxMaterialReport.getSelectedItem() + ") - "
                            + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to "
                            + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Customerwise Report":
                    return "Customerwise Report (" + textFieldDetail.getText() + ") - "
                            + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to "
                            + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Transporterwise Report":
                    return "Transporterwise Report (" + textFieldDetail.getText() + ") - "
                            + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to "
                            + dateAndTimeFormatdatep.format(datePicker2.getDate());
            }
        }  // switch(comboBox.getSelectedItem().toString())
        // {
        // case "Full Report":
        // datePicker1.setEnabled(false);
        // datePicker2.setEnabled(false);
        // textFieldDetail.setEnabled(false);
        // comboBoxMaterialReport.setEnabled(false);
        // break;
        // case "Daily Report":
        // datePicker1.setEnabled(true);
        // datePicker2.setEnabled(false);
        // textFieldDetail.setEnabled(false);
        // comboBoxMaterialReport.setEnabled(false);
        // break;
        // case "Datewise Report":
        // datePicker1.setEnabled(true);
        // datePicker2.setEnabled(true);
        // textFieldDetail.setEnabled(false);
        // comboBoxMaterialReport.setEnabled(false);
        // break;
        // case "BillNowise Report":
        // detail.setText("Bill No");
        // datePicker1.setEnabled(false);
        // datePicker2.setEnabled(false);
        // textFieldDetail.setEnabled(true);
        // comboBoxMaterialReport.setEnabled(false);
        // break;
        // case "Customerwise Report":
        // detail.setText("Customer Name");
        // datePicker1.setEnabled(false);
        // datePicker2.setEnabled(false);
        // textFieldDetail.setEnabled(true);
        // comboBoxMaterialReport.setEnabled(false);
        // break;
        // case "Materialwise Report":
        // datePicker1.setEnabled(false);
        // datePicker2.setEnabled(false);
        // textFieldDetail.setEnabled(false);
        // comboBoxMaterialReport.setEnabled(true);
        // break;
        // }

        return null;
    }

    private void printReportBill() {
        JTextPane textPane = createTextPane4();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = .75d * 72d;
        double heightmargin = 1d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf, 99);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter1.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createTextPane4() {
        TableModel model = tableReport.getModel();
        String format = " %1$-7s %2$-10s %3$-22s %4$-15s %5$-8s %6$-8s %7$-8s\n";
        String temp = "\n";
        for (int i = 0; i < model.getRowCount(); i++) {
            temp = temp.concat(String.format(format,
                    StringUtils.center(model.getValueAt(i, 0) != null ? model.getValueAt(i, 0).toString() : "", 7),
                    StringUtils.center(model.getValueAt(i, 6) != null ? model.getValueAt(i, 6).toString() : "", 10),
                    StringUtils.center(model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "", 22),
                    StringUtils.center(model.getValueAt(i, 5) != null ? model.getValueAt(i, 5).toString() : "", 15),
                    StringUtils.leftPad(model.getValueAt(i, 7) != null ? model.getValueAt(i, 7).toString() : "", 8,
                            " "),
                    StringUtils.leftPad(model.getValueAt(i, 8) != null ? model.getValueAt(i, 8).toString() : "", 8,
                            " "),
                    StringUtils.leftPad(model.getValueAt(i, 11) != null ? model.getValueAt(i, 11).toString() : "", 8,
                            " ")));
            temp = temp.concat("\n");
        }
        String[] initString = {StringUtils.center(title1.getText(), 39) + "\n",
                StringUtils.center(title2.getText(), 65) + "\n",
                "==================================================================================================\n",
                String.format(format, StringUtils.center("Bill.No", 7), StringUtils.center("Bill Date", 10),
                        StringUtils.center("Customer Name", 22), StringUtils.center("Material Name", 15),
                        StringUtils.center("Cost", 8), StringUtils.center("Weight", 8),
                        StringUtils.center("Amount", 8)),
                "==================================================================================================\n",
                temp,
                "==================================================================================================\n",
                " ", "\n\tTotal No of Units    " + textFieldtotalNetWt.getText(),
                "\n\tNet Total            " + textFieldTotalCharges.getText(), "\n\t\t\t\t\tSignature"};

        String[] initStyles = {"1", "2", "3", "3", "3", "3", "3", "5", "5", "5", "5"};

        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument3(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void addStylesToDocument3(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "Courier New");

        Style s = doc.addStyle("1", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 20);

        s = doc.addStyle("2", regular);
        StyleConstants.setItalic(s, true);
        StyleConstants.setFontSize(s, 12);

        s = doc.addStyle("3", regular);
        StyleConstants.setFontSize(s, 9);

        s = doc.addStyle("4", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 8);

        s = doc.addStyle("5", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 10);
    }

    private String toNum(int n, String ch) {
        String temp = "";
        String[] one = {"", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten",
                " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen",
                " Nineteen"};
        String[] ten = {"", "", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"};
        if (n > 19)
            temp = temp + ten[n / 10] + "" + one[n % 10];
        else
            temp = temp + one[n];
        if (n > 0)
            temp = temp + ch;
        return temp;
    }

    private String toWord(int n) {
        String temp = "(Rupees.";
        temp = temp + toNum((n / 10000000) % 100, " Crore");
        temp = temp + toNum(((n / 100000) % 100), " Lakh");
        temp = temp + toNum(((n / 1000) % 100), " Thousand");
        temp = temp + toNum(((n / 100) % 10), " Hundred");
        temp = temp + toNum((n % 100), "");
        temp = temp + " Only)";
        return temp;
    }

    private void toExcel(String excelFilePath) throws IOException {
        Workbook workbook;
        if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        String safeName = WorkbookUtil.createSafeSheetName("Weighing - " + Objects.requireNonNull(comboBox.getSelectedItem()).toString());
        Sheet sheet = workbook.createSheet(safeName);
        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle cellStyleStringCenter = sheet.getWorkbook().createCellStyle();
        cellStyleStringCenter.setAlignment(HorizontalAlignment.CENTER);
        Cell cell;
        cell = row.createCell(0);
        cell.setCellValue(title1.getText());
        cell.setCellStyle(cellStyleStringCenter);
        rowNum++;
        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue(title2.getText());
        cell.setCellStyle(cellStyleStringCenter);
        rowNum++;
        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue(getTitle());
        cell.setCellStyle(cellStyleStringCenter);
        rowNum++;
        row = sheet.createRow(rowNum);
        int j = 0;
        if (a1.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Sl.No");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a1a.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Dc. No");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a1b.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Dc. Date");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (aa.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Customer's Name");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (aaa.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Transporter's Name");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a2.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Vehicle No");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a3.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Material");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a4.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("No Of Bags");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a5.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Gross Wt");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a6.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Gross Date & Time");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a7.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Tare Wt");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a8.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Tare Date & Time");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a9.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Net Wt");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a10.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Print Date & Time");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a11.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Remarks");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (a12.isSelected()) {
            cell = row.createCell(j);
            cell.setCellValue("Manual");
            cell.setCellStyle(cellStyleStringCenter);
        }
        TableModel model = tableReport.getModel();

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setDataFormat(
                creationHelper.createDataFormat().getFormat(((SimpleDateFormat) dateAndTimeFormat).toPattern()));
        int charge = -1;
        int grossWt = -1;
        int tareWt = -1;
        int netWt = -1;
        for (int i = 0; i < model.getRowCount(); i++) {
            rowNum++;
            row = sheet.createRow(rowNum);
            int c = 0;
            j = 0;
            if (a1.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
            }
            j++;
            if (a1a.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyleStringCenter);
            }
            j++;
            if (a1b.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyle);
            }
            j++;
            if (aa.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (aaa.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (a2.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (a3.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (a4.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
                charge = c - 1;
            }
            j++;
            if (a5.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
                grossWt = c - 1;
            }
            j++;
            if (a6.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyle);
            }
            j++;
            if (a7.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
                tareWt = c - 1;
            }
            j++;
            if (a8.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyle);
            }
            j++;
            if (a9.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
                netWt = c - 1;
            }
            j++;
            if (a10.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyle);
            }
            j++;
            if (a11.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (a12.isSelected()) {
                cell = row.createCell(c);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
        }
        rowNum++;
        String getColumn = "ABCDEFGHIJKLMNOPQ";
        row = sheet.createRow(rowNum);
        if (charge != -1) {
            cell = row.createCell(charge);
            cell.setCellFormula(
                    "SUM(" + getColumn.charAt(charge) + "3:" + getColumn.charAt(charge) + "" + rowNum + ")");
        }
        if (grossWt != -1) {
            cell = row.createCell(grossWt);
            cell.setCellFormula(
                    "SUM(" + getColumn.charAt(grossWt) + "3:" + getColumn.charAt(grossWt) + "" + rowNum + ")");
        }
        if (tareWt != -1) {
            cell = row.createCell(tareWt);
            cell.setCellFormula(
                    "SUM(" + getColumn.charAt(tareWt) + "3:" + getColumn.charAt(tareWt) + "" + rowNum + ")");
        }
        if (netWt != -1) {
            cell = row.createCell(netWt);
            cell.setCellFormula("SUM(" + getColumn.charAt(netWt) + "3:" + getColumn.charAt(netWt) + "" + rowNum + ")");
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sheet.getRow(3).getLastCellNum() - 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, sheet.getRow(3).getLastCellNum() - 1));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, sheet.getRow(3).getLastCellNum() - 1));

        for (short i = sheet.getRow(3).getFirstCellNum(), end = sheet.getRow(3).getLastCellNum(); i < end; i++) {
            sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOut;
        if (excelFilePath.endsWith("xls")) {
            fileOut = new FileOutputStream(excelFilePath);
        } else if (excelFilePath.endsWith("xlsx")) {
            fileOut = new FileOutputStream(excelFilePath);
        } else {
            fileOut = new FileOutputStream(excelFilePath + ".xlsx");
        }
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    private void initializeWeights() {
        // TODO weight
        for (SerialPort serialPort : SerialPort.getCommPorts()) {
            if (serialPort.getSystemPortName().equals(textFieldPortName.getText().split(";")[0].toUpperCase())) {
                comPort = serialPort;
                break;
            }
        }
        String[] temp = {"8", "0", "10"};
        try {
            temp[0] = textFieldPortName.getText().split(";")[1];
            if (Objects.equals(temp[0], ""))
                temp[0] = "8";
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            temp[1] = textFieldPortName.getText().split(";")[2];
            if (Objects.equals(temp[1], ""))
                temp[1] = "0";
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            temp[2] = textFieldPortName.getText().split(";")[3];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        if (comPort != null) {
            comPort.setComPortParameters(Integer.parseInt(textFieldBaudRate.getText()),
                    Integer.parseInt("0" + temp[0]), SerialPort.ONE_STOP_BIT, Integer.parseInt("0" + temp[1]));
            comPort.openPort();
            comPort.addDataListener(new SerialPortMessageListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                }

                @Override
                public byte[] getMessageDelimiter() {
                    return new byte[]{(byte) (Integer.parseInt(0 + temp[2]) % 128)};
                }

                @Override
                public boolean delimiterIndicatesEndOfMessage() {
                    return true;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    lblWeight.setText("" + Integer.parseInt(0 + new String(event.getReceivedData()).replaceAll("[^-0-9]", "")));
                }
            });
        }
    }

    @SuppressWarnings("SameParameterValue")
    private WebcamPanel webcamStarter(WebcamPicker webcamPicker, int i, WebcamPanel panelCamera,
                                      JComboBox<DimensionTemplate> comboBoxResolution, JTextField textFieldCropX12, JTextField textFieldCropY12,
                                      JTextField textFieldCropWidth12, JTextField textFieldCropHeight12, int x, int y, int z, int l) {
        if (chckbxCamera.isSelected())
            try {
                if (webcamPicker.getSelectedWebcam() != null) {
                    if (webcam[i] != null) {
                        Runnable stuffToDo = new Thread(() -> webcam[i].close());
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Future<?> future = executor.submit(stuffToDo);
                        executor.shutdown();
                        try {
                            future.get(1, TimeUnit.SECONDS);
                        } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                        }
                        if (!executor.isTerminated()) {
                            executor.shutdownNow();
                        }
                    }

                    webcam[i] = webcamPicker.getSelectedWebcam();
                    try {
                        panelCameras.remove(panelCamera);
                    } catch (NullPointerException ignored) {
                    }

                    switch (l) {
                        case 0:
                            lock = false;
                            Dimension[] dim = webcam[i].getViewSizes();
                            comboBoxResolution.removeAllItems();
                            for (Dimension ii : dim) {
                                comboBoxResolution.addItem(new DimensionTemplate(ii));
                            }
                            comboBoxResolution.setSelectedIndex(comboBoxResolution.getItemCount() - 1);
                        case 1:
                            textFieldCropX12.setText("0");
                            textFieldCropY12.setText("0");
                            textFieldCropWidth12
                                    .setText(Integer.toString(((Dimension) Objects.requireNonNull(comboBoxResolution.getSelectedItem())).width));
                            textFieldCropHeight12
                                    .setText(Integer.toString(((Dimension) comboBoxResolution.getSelectedItem()).height));
                    }

                    if (!webcam[i].isOpen())
                        webcam[i].setViewSize((Dimension) Objects.requireNonNull(comboBoxResolution.getSelectedItem()));
                    panelCamera = new WebcamPanel(webcam[i]);
                    panelCamera.setBounds(x, y,
                            (int) (((double) z / ((Dimension) Objects.requireNonNull(comboBoxResolution.getSelectedItem())).height
                                    * ((Dimension) comboBoxResolution.getSelectedItem()).width)),
                            z);
                    panelCameras.add(panelCamera);
                    lock = true;
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | WebcamException | NullPointerException
                    | ClassCastException ex) {
                JOptionPane.showMessageDialog(null,
                        "CAMERA ERROR\nCamera has beed removed are resolution missmatch\nLINE :1547", "CAMERA ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        return panelCamera;
    }

    private void webcamdispose() {
        if (webcam[1] != null) {
            Runnable stuffToDo = new Thread(() -> webcam[1].close());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(stuffToDo);
            executor.shutdown();
            try {
                future.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
            }
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
        }
        if (webcam[2] != null) {
            Runnable stuffToDo = new Thread(() -> webcam[2].close());

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(stuffToDo);
            executor.shutdown();
            try {
                future.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
            }
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
        }
        if (webcam[3] != null) {
            Runnable stuffToDo = new Thread(() -> webcam[3].close());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(stuffToDo);
            executor.shutdown();
            try {
                future.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
            }
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
        }
        if (webcam[4] != null) {
            Runnable stuffToDo = new Thread(() -> webcam[4].close());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(stuffToDo);
            executor.shutdown();
            try {
                future.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
            }
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
        }
        try {
            panelCameras.remove(panelCamera1);
        } catch (NullPointerException ignored) {
        }
        try {
            panelCameras.remove(panelCamera2);
        } catch (NullPointerException ignored) {
        }
        try {
            panelCameras.remove(panelCamera3);
        } catch (NullPointerException ignored) {
        }
        try {
            panelCameras.remove(panelCamera4);
        } catch (NullPointerException ignored) {
        }
        try {
            panelCameras.remove(labelCamera1);
        } catch (NullPointerException ignored) {
        }
        try {
            panelCameras.remove(labelCamera2);
        } catch (NullPointerException ignored) {
        }
        try {
            panelCameras.remove(labelCamera3);
        } catch (NullPointerException ignored) {
        }
        try {
            panelCameras.remove(labelCamera4);
        } catch (NullPointerException ignored) {
        }
    }

    @SuppressWarnings("unused")
    private void sentSMS(String mobileNo) {
        String smsMessage = "Sl.No : " + textFieldSlNo.getText() + "\nDate & Time : " + textFieldNetDateTime.getText()
                + "\nVehicle No : " + textFieldVehicleNo.getText() + "\nMaterial : "
                + comboBoxMaterial.getEditor().getItem() + "\nGross Wt : " + textFieldGrossWt.getText() + " Kg"
                + "\nTare Wt : " + textFieldTareWt.getText() + " Kg" + "\nNet Wt : " + textFieldNetWt.getText() + " Kg"
                + "\nFrom " + textFieldTitle1.getText();
        // TODO: 15-07-2019 Message

//        try {
//            CommPortIdentifier ports;
//            Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
//            while (portEnum.hasMoreElements()) {
//                ports = (CommPortIdentifier) portEnum.nextElement();
//                if (ports.getPortType() == CommPortIdentifier.PORT_SERIAL && ports.getName().equals("COM2")) {
//                    comPort = ports;
//                    break;
//                }
//            }
//            SerialPort serialPortSms = (SerialPort) comPort.open(textFieldSMSPortName.getText(), 2000);
//            OutputStream outputStream = serialPortSms.getOutputStream();
//            serialPortSms.getInputStream();
//            serialPortSms.setSerialPortParams(Integer.parseInt(textFieldSMSBaudRate.getText()), SerialPort.DATABITS_8,
//                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//            char enter = 13;
//            outputStream.write(("AT+CMGS=\"" + mobileNo + "\"" + enter).getBytes());
//            Thread.sleep(100);
//            outputStream.flush();
//            char CTRLZ = 26;
//            outputStream.write((smsMessage + CTRLZ).getBytes());
//            outputStream.flush();
//            outputStream.close();
//            serialPortSms.close();
//        } catch (PortInUseException | IOException | UnsupportedCommOperationException | InterruptedException
//                | NullPointerException e) {
//            JOptionPane.showMessageDialog(null,
//                    "SMS ERROR\nSMS Funtion not working please check the connection 0or check the number entered",
//                    "SMS ERROR", JOptionPane.ERROR_MESSAGE);
//        }
    }

    private void close() {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM setup");
            rs.absolute(1);
            rs.updateTimestamp("LASTLOGIN", new java.sql.Timestamp(new Date().getTime()));
            rs.updateRow();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :7720", "SQL ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
        try {
            dbConnection.close();
        } catch (SQLException ignored) {
        }
        if (comPort != null) {
            comPort.removeDataListener();
            comPort.closePort();
        }
        webcamdispose();
        System.exit(0);
    }

    private void billEvent() {
        if (chckbxRemoveBillinTab.isSelected()) {
            tabbedPane.setEnabledAt(2, false);
            tabbedPane.setTitleAt(2, "");
            comboBoxPrinter2.setEnabled(false);
            textFieldNoOfCopies1.setEnabled(false);
            comboBoxPrintOptionForBill.setEnabled(false);
            btnResetBills.setEnabled(false);
            rdbtnBilling.setEnabled(false);
            rdbtnWeighing.setSelected(true);
            textFieldTax.setEnabled(false);
        } else {
            if (lock1) {
                tabbedPane.setEnabledAt(2, true);
                tabbedPane.setTitleAt(2, "           Billing          ");
                comboBoxPrinter2.setEnabled(true);
                textFieldNoOfCopies1.setEnabled(true);
                comboBoxPrintOptionForBill.setEnabled(true);
                btnResetBills.setEnabled(true);
                rdbtnBilling.setEnabled(true);
                textFieldTax.setEnabled(true);
            } else {
                JPasswordField password = new JPasswordField(10);
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {"Enter", "Cancel"};
                panel.add(new JLabel("Please the Billing Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = {'l', 'e', 'n', 's', 'm', 'o', 's', 'e', 's', 'd', 'h', 'a', 's'};
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    tabbedPane.setEnabledAt(2, true);
                    tabbedPane.setTitleAt(2, "           Billing          ");
                    comboBoxPrinter2.setEnabled(true);
                    textFieldNoOfCopies1.setEnabled(true);
                    comboBoxPrintOptionForBill.setEnabled(true);
                    btnResetBills.setEnabled(true);
                    rdbtnBilling.setEnabled(true);
                    textFieldTax.setEnabled(true);
                } else {
                    chckbxRemoveBillinTab.setSelected(true);
                }
            }
        }
    }

    private void cameraEvent() {
        if (chckbxCamera.isSelected()) {
            if (lock1) {
                tabbedPane.setEnabledAt(1, true);
                tabbedPane.setTitleAt(1, "          Cameras          ");
                if (checkBoxCamera1.isSelected())
                    panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1,
                            textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 240, 2);
                if (checkBoxCamera2.isSelected())
                    panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2,
                            textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 240, 2);
                if (checkBoxCamera3.isSelected())
                    panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3,
                            textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 240, 2);
                if (checkBoxCamera4.isSelected())
                    panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4,
                            textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 240, 2);
                btnClick.setEnabled(true);
                butttonUpdateCamera.setEnabled(true);
                buttonUnLockCamera.setEnabled(true);

            } else {
                JPasswordField password = new JPasswordField(10);
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {"Enter", "Cancel"};
                panel.add(new JLabel("Please the Camera Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = {'m', 'o', 'l', 'e', 'e', 's', 'h'};
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    tabbedPane.setEnabledAt(1, true);
                    tabbedPane.setTitleAt(1, "          Cameras          ");
                    if (checkBoxCamera1.isSelected())
                        panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1,
                                textFieldCropX1, textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11,
                                240, 2);
                    if (checkBoxCamera2.isSelected())
                        panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2,
                                textFieldCropX2, textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11,
                                240, 2);
                    if (checkBoxCamera3.isSelected())
                        panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3,
                                textFieldCropX3, textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310,
                                240, 2);
                    if (checkBoxCamera4.isSelected())
                        panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4,
                                textFieldCropX4, textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310,
                                240, 2);
                    btnClick.setEnabled(true);
                    butttonUpdateCamera.setEnabled(true);
                    buttonUnLockCamera.setEnabled(true);

                } else {
                    chckbxCamera.setSelected(false);
                }
            }
        } else {
            webcamdispose();
            tabbedPane.setEnabledAt(1, false);
            tabbedPane.setTitleAt(1, "");
            btnClick.setEnabled(false);
            butttonUpdateCamera.setEnabled(false);
            buttonUnLockCamera.setEnabled(false);
        }
    }

    static class MyIpCam extends IpCamDriver {
        MyIpCam() {
            try {
                super.register(new IpCamDevice("No Camera Available", "http:", IpCamMode.PULL));
            } catch (MalformedURLException ignored) {
            }
        }
    }

    static class MyCompositeDriver extends WebcamCompositeDriver {

        MyCompositeDriver() {
            try {
                add(new IpCamDriver(new IpCamStorage("cameras.xml")));

            } catch (NullPointerException | WebcamException e) {
                add(new WeighBridge_Old.MyIpCam());
            }
            add(new WebcamDefaultDriver());
        }
    }

    class Coordinates {

        final int x;
        final int y;

        Coordinates(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }
    }

    class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {

        private static final long serialVersionUID = 1L;
        private final JTextField component = new JTextField();
        private final Font font = new Font("Monospaced", Font.PLAIN, 15);

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex,
                                                     int vColIndex) {
            component.setText((String) value);
            component.setFont(font);
            return component;
        }

        public Object getCellEditorValue() {
            return component.getText();
        }

    }

    class DimensionTemplate extends Dimension {
        private static final long serialVersionUID = 1L;

        public DimensionTemplate() {
            super();
        }

        DimensionTemplate(Dimension d) {
            super(d);
        }

        public DimensionTemplate(int width, int height) {
            super(width, height);
        }

        public DimensionTemplate(String string) {
            super(Integer.parseInt(string.trim().split(" ")[0]), Integer.parseInt(string.trim().split(" ")[2]));
        }

        public String toString() {
            return "  " + width + " * " + height;
        }
    }

    class Calculator extends JFrame implements ActionListener {
        private static final long serialVersionUID = 1L;
        final int MAX_INPUT_LENGTH = 20;
        final int INPUT_MODE = 0;
        final int RESULT_MODE = 1;
        final int ERROR_MODE = 2;
        private final JLabel jlOutput;
        private final JButton[] jbButtons;
        int displayMode;
        boolean clearOnNextDigit;
        double lastNumber;
        String lastOperator;

        Calculator() {
            setBackground(Color.gray);
            JPanel jpMaster = new JPanel();
            jlOutput = new JLabel("0");
            jlOutput.setHorizontalTextPosition(JLabel.LEFT);
            jlOutput.setBackground(Color.white);
            jlOutput.setHorizontalAlignment(SwingConstants.RIGHT);
            jlOutput.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            jlOutput.setOpaque(true);

            getContentPane().add(jlOutput, BorderLayout.NORTH);
            jbButtons = new JButton[27];

            JPanel jpButtons = new JPanel();

            for (int i = 0; i <= 9; i++) {
                jbButtons[i] = new JButton(String.valueOf(i));
            }

            jbButtons[10] = new JButton("+/-");
            jbButtons[11] = new JButton(".");
            jbButtons[12] = new JButton("=");
            jbButtons[13] = new JButton("/");
            jbButtons[14] = new JButton("*");
            jbButtons[15] = new JButton("-");
            jbButtons[16] = new JButton("+");
            jbButtons[17] = new JButton("sqrt");
            jbButtons[18] = new JButton("%");
            jbButtons[19] = new JButton("1/x");

            jbButtons[20] = new JButton("MC");
            jbButtons[21] = new JButton("MR");
            jbButtons[22] = new JButton("MS");
            jbButtons[23] = new JButton("M+");
            jbButtons[24] = new JButton("Backspace");
            jbButtons[25] = new JButton("CE");
            jbButtons[26] = new JButton("C");

            JPanel jpControl = new JPanel();
            jpControl.setLayout(new GridLayout(1, 3, 2, 2));
            jpControl.add(jbButtons[24]);
            jpControl.add(jbButtons[25]);
            jpControl.add(jbButtons[26]);

            for (int i = 0; i < jbButtons.length; i++) {
                if (i < 10)
                    jbButtons[i].setForeground(Color.blue);
                else
                    jbButtons[i].setForeground(Color.red);
                jbButtons[i].setFont(new Font("Times New Roman", Font.PLAIN, 15));
                jbButtons[i].setFocusable(false);
            }

            jpButtons.setLayout(new GridLayout(4, 6, 2, 2));

            jpButtons.add(jbButtons[20]);
            for (int i = 7; i <= 9; i++) {
                jpButtons.add(jbButtons[i]);
            }
            jpButtons.add(jbButtons[13]);
            jpButtons.add(jbButtons[17]);

            jpButtons.add(jbButtons[21]);
            for (int i = 4; i <= 6; i++) {
                jpButtons.add(jbButtons[i]);
            }
            jpButtons.add(jbButtons[14]);
            jpButtons.add(jbButtons[18]);

            jpButtons.add(jbButtons[22]);
            for (int i = 1; i <= 3; i++) {
                jpButtons.add(jbButtons[i]);
            }
            jpButtons.add(jbButtons[15]);
            jpButtons.add(jbButtons[19]);

            jpButtons.add(jbButtons[23]);
            jpButtons.add(jbButtons[0]);
            jpButtons.add(jbButtons[10]);
            jpButtons.add(jbButtons[11]);
            jpButtons.add(jbButtons[16]);
            jpButtons.add(jbButtons[12]);

            jpMaster.setLayout(new BorderLayout());
            jpMaster.add(jpControl, BorderLayout.EAST);
            jpMaster.add(jpButtons, BorderLayout.SOUTH);

            getContentPane().add(jpMaster, BorderLayout.SOUTH);
            requestFocus();

            for (JButton jbButton : jbButtons) {
                jbButton.addActionListener(this);
            }

            clearAll();

            addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    System.exit(0);
                }
            });
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public void actionPerformed(ActionEvent e) {
            double result;

            for (int i = 0; i < jbButtons.length; i++) {
                if (e.getSource() == jbButtons[i]) {
                    switch (i) {
                        case 0:

                        case 9:

                        case 8:

                        case 7:

                        case 6:

                        case 5:

                        case 4:

                        case 3:

                        case 2:

                        case 1:
                            addDigitToDisplay(i);
                            break;

                        case 10:
                            processSignChange();
                            break;

                        case 11:
                            addDecimalPoint();
                            break;

                        case 12:
                            processEquals();
                            break;

                        case 13:
                            processOperator("/");
                            break;

                        case 14:
                            processOperator("*");
                            break;

                        case 15:
                            processOperator("-");
                            break;

                        case 16:
                            processOperator("+");
                            break;

                        case 17:
                            if (displayMode != ERROR_MODE) {
                                try {
                                    if (getDisplayString().indexOf("-") == 0)
                                        displayError("Invalid input for function.");

                                    result = Math.sqrt(getNumberInDisplay());
                                    displayResult(result);
                                } catch (Exception ex) {
                                    displayError("Invalid input for function.");
                                    displayMode = ERROR_MODE;
                                }
                            }
                            break;

                        case 18:
                            if (displayMode != ERROR_MODE) {
                                try {
                                    result = getNumberInDisplay() / 100;
                                    displayResult(result);
                                } catch (Exception ex) {
                                    displayError("Invalid input for function.");
                                    displayMode = ERROR_MODE;
                                }
                            }
                            break;

                        case 19:
                            if (displayMode != ERROR_MODE) {
                                try {
                                    if (getNumberInDisplay() == 0)
                                        displayError("Cannot divide by zero.");
                                    result = 1 / getNumberInDisplay();
                                    displayResult(result);
                                } catch (Exception ex) {
                                    displayError("Cannot divide by zero.");
                                    displayMode = ERROR_MODE;
                                }
                            }
                            break;

                        case 20:
                            clearMemory();
                            break;

                        case 21:
                            recallMemory();
                            break;

                        case 22:
                            storeInMemory();
                            break;

                        case 23:
                            addToMemory();
                            break;

                        case 24:
                            if (displayMode != ERROR_MODE) {
                                setDisplayString(getDisplayString().substring(0, getDisplayString().length() - 1));
                                if (getDisplayString().length() < 1)
                                    setDisplayString("0");
                            }
                            break;

                        case 25:
                            clearExisting();
                            break;

                        case 26:
                            clearAll();
                            break;
                    }
                }
            }
        }

        @SuppressWarnings("EmptyMethod")
        private void addToMemory() {
            // needs code

        }

        @SuppressWarnings("EmptyMethod")
        private void storeInMemory() {
            // needs code
        }

        @SuppressWarnings("EmptyMethod")
        private void recallMemory() {
            // needs code
        }

        @SuppressWarnings("EmptyMethod")
        private void clearMemory() {
            // needs code
        }

        String getDisplayString() {
            return jlOutput.getText();
        }

        void setDisplayString(String s) {
            jlOutput.setText(s);
        }

        void addDigitToDisplay(int digit) {
            if (clearOnNextDigit) {
                setDisplayString("");
            }
            String inputString = getDisplayString();
            if (inputString.indexOf("0") == 0) {
                inputString = inputString.substring(1);
            }
            if ((!inputString.equals("0") || digit > 0) && inputString.length() < MAX_INPUT_LENGTH) {
                setDisplayString(inputString + digit);
            }
            displayMode = INPUT_MODE;
            clearOnNextDigit = false;
        }

        void addDecimalPoint() {
            displayMode = INPUT_MODE;
            if (clearOnNextDigit) {
                setDisplayString("");
            }
            String inputString = getDisplayString();
            if (!inputString.contains(".")) {
                setDisplayString(inputString + ".");
            }
        }

        void processSignChange() {
            if (displayMode == INPUT_MODE) {
                String input = getDisplayString();
                if (input.length() > 0 && !input.equals("0")) {
                    if (input.indexOf("-") == 0) {
                        setDisplayString(input.substring(1));
                    } else {
                        setDisplayString("-" + input);
                    }
                }
            }
        }

        void clearAll() {
            setDisplayString("0");
            lastOperator = "0";
            lastNumber = 0;
            displayMode = INPUT_MODE;
            clearOnNextDigit = true;
        }

        void clearExisting() {
            setDisplayString("0");
            clearOnNextDigit = true;
            displayMode = INPUT_MODE;
        }

        double getNumberInDisplay() {
            String input = jlOutput.getText();
            return Double.parseDouble(input);
        }

        void processOperator(String op) {
            if (displayMode != ERROR_MODE) {
                double numberInDisplay = getNumberInDisplay();
                if (!lastOperator.equals("0")) {
                    try {
                        double result = processLastOperator();
                        displayResult(result);
                        lastNumber = result;
                    } catch (DivideByZeroException e) {
                        displayError("Cannot divide by sero.");
                    }
                } else {
                    lastNumber = numberInDisplay;
                }
                clearOnNextDigit = true;
                lastOperator = op;
            }
        }

        void processEquals() {
            double result;
            if (displayMode != ERROR_MODE) {
                try {
                    result = processLastOperator();
                    displayResult(result);
                } catch (DivideByZeroException e) {
                    displayError("Cannot divide by sero.");
                }
                lastOperator = "0";
            }
        }

        double processLastOperator() throws DivideByZeroException {
            double result = 0;
            double numberInDisplay = getNumberInDisplay();
            if (lastOperator.equals("/")) {
                if (numberInDisplay == 0) {
                    throw (new DivideByZeroException());
                }
                result = lastNumber / numberInDisplay;
            }
            if (lastOperator.equals("*")) {
                result = lastNumber * numberInDisplay;
            }
            if (lastOperator.equals("-")) {
                result = lastNumber - numberInDisplay;
            }
            if (lastOperator.equals("+")) {
                result = lastNumber + numberInDisplay;
            }
            return result;
        }

        void displayResult(double result) {
            setDisplayString(Double.toString(result));
            lastNumber = result;
            displayMode = RESULT_MODE;
            clearOnNextDigit = true;
        }

        void displayError(String errorMessage) {
            setDisplayString(errorMessage);
            lastNumber = 0;
            displayMode = ERROR_MODE;
            clearOnNextDigit = true;
        }
    }

    class DivideByZeroException extends Exception {
        private static final long serialVersionUID = 1L;

        DivideByZeroException() {
            super();
        }

        public DivideByZeroException(String s) {
            super(s);
        }
    }
}
