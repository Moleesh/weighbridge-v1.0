package com.babulens;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamCompositeDriver;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.ds.buildin.WebcamDefaultDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.github.sarxos.webcam.ds.ipcam.IpCamStorage;
import com.ibatis.common.jdbc.ScriptRunner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.NumberFormatter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serial;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class WeighBridge {
    private static final String DB_CONNECTION = "jdbc:h2:./weighdata;DEFRAG_ALWAYS=TRUE;TRACE_LEVEL_FILE=2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "toor";
    static DecimalFormat decimalFormat = new DecimalFormat("0");
    static Set<String> vehicleNoSet = new HashSet<>();
    static Set<String> materialSet = new HashSet<>();
    static Set<String> transportSet = new HashSet<>();
    static Set<String> customerSet = new HashSet<>();
    static Set<String> vehicleTypeSet = new HashSet<>();
    static Set<String> operatorSet = new HashSet<>();
    static private SerialPort comPort;

    static {
        Webcam.setDriver(new CompositeDriver());
    }

    private final ButtonGroup buttonGroupWeight = new ButtonGroup();
    private final ButtonGroup buttonGroupInvoice = new ButtonGroup();
    private final ButtonGroup buttonGroupReport = new ButtonGroup();
    private final DateFormat dateAndTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    private final DateFormat dateAndTimeFormatPrint = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private final DateFormat dateAndTimeFormatSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DateFormat dateAndTimeFormatdate = new SimpleDateFormat("dd-MM-yyyy");
    private final DateFormat dateAndTimeFormatdatep = new SimpleDateFormat("dd/MM/yyyy");
    private final DateFormat dateAndTimeFormattime = new SimpleDateFormat("hh:mm a");
    private final JCheckBox chckbxSlNo = new JCheckBox("Sl.No");
    private final JCheckBox chckbxDCNo = new JCheckBox("Dc. No");
    private final JCheckBox chckbxDCDate = new JCheckBox("Dc. Date");
    private final JCheckBox chckbxCustomerName = new JCheckBox("Customer's Name");
    private final JCheckBox chckbxTransporterName = new JCheckBox("Transporter's Name");
    private final JCheckBox chckbxVehicleNo = new JCheckBox("Vehicle No");
    private final JCheckBox chckbxVehicleType = new JCheckBox("Vehicle Type");
    private final JCheckBox chckbxOperator = new JCheckBox("Operator");
    private final JCheckBox chckbxPlace = new JCheckBox("Place");
    private final JCheckBox chckbxPhoneNo = new JCheckBox("Phone No");
    private final JCheckBox chckbxMaterial = new JCheckBox("Material");
    private final JCheckBox chckbxNoOfBags = new JCheckBox("No OF Bags");
    private final JCheckBox chckbxCharges = new JCheckBox("Charges");
    private final JCheckBox chckbxCredit = new JCheckBox("Credit");
    private final JCheckBox chckbxGrossWeight = new JCheckBox("Gross Wt");
    private final JCheckBox chckbxGrossDateAndTime = new JCheckBox("Gross Date & Time");
    private final JCheckBox chckbxTareWeight = new JCheckBox("Tare Wt");
    private final JCheckBox chckbxTareDateAndTime = new JCheckBox("Tare Date & Time");
    private final JCheckBox chckbxBagDeduction = new JCheckBox("Bag Deduction");
    private final JCheckBox chckbxRoundOffColumn = new JCheckBox("Round off");
    private final JCheckBox chckbxNettWeight = new JCheckBox("Nett Wt");
    private final JCheckBox chckbxNettDateAndTime = new JCheckBox("Nett Date & Time");
    private final JCheckBox chckbxFinalWt = new JCheckBox("Final Wt");
    private final JCheckBox chckbxFinalAmount = new JCheckBox("Final Amount");
    private final JCheckBox chckbxRemarks = new JCheckBox("Remarks");
    private final JCheckBox chckbxManual = new JCheckBox("Manual");
    private final Webcam[] webcam = new Webcam[5];
    private boolean ranModifiedScript = true;
    private String TRIAL_LICENSE_PASSWORD = "147085";
    private String LICENSE_PASSWORD = "147085aA";
    private String UNLOCK_PASSWORD = "147085";
    private String CAMERA_PASSWORD = "147085";
    private String SMS_PASSWORD = "147085";
    private String INVOICE_PASSWORD = "147085";
    private String MANUAL_ENTRY_PASSWORD = "147085";
    private String EDIT_ENABLE_PASSWORD = "147085";
    private String RESET_PASSWORD = "147085";
    private String LOGIN_PASSWORD = "123";
    private int noOfCopies = 0;
    private boolean takeBackup = false;
    private boolean valueEntered = false;
    private boolean afterStart = false;
    private Connection dbConnection;
    private BufferedImage clickedImage;
    private boolean lock = false;
    private PrintService[] printServices;
    private Vector<String> printers;
    private Vector<String> invoiceProperties;
    private Calculator calc;
    private JFrame babulensWeighbridgeDesigned;
    private JTextField textFieldCharges;
    private JComboBox<String> comboBoxMaterial;
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
    private JComboBox<String> comboBoxReportType;
    private JTextField textFieldReportTextBox;
    private JTable tableReport;
    private JTextField textFieldTotalCharges;
    private JTextField textFieldtotalNetWt;
    private JXDatePicker datePicker1;
    private JXDatePicker datePicker2;
    private JLabel lblReportTextBox;
    private JComboBox<String> comboBoxMaterialName;
    private JTable tableMaterial;
    private JTable tableVehicleTare;
    private JTextField textFieldTitle1;
    private JTextField textFieldTitle2;
    private JTextField textFieldFooter;
    private JTextField textFieldBaudRate;
    private JTextField textFieldPortName;
    private JTable tableCustomer;
    private JCheckBox chckbxEditEnable;
    private JCheckBox chckbxManualEntry;
    private JCheckBox chckbxExcludeCharges;
    private JComboBox<String> comboBoxPrinter;
    private JTextField textFieldNoOfCopies;
    private JComboBox<String> comboBoxCustomerName;
    private JCheckBox chckbxExcludeCustomer;
    private JCheckBox chckbxExcludeDrivers;
    private JComboBox<String> comboBoxTransporterName;
    private JTextField textFieldDcNo;
    private JTextField textFieldDcDate;
    private JButton btnGetDcDetails;
    private JLabel labelCamera1;
    private JLabel labelCamera2;
    private JLabel labelCamera3;
    private JLabel labelCamera4;
    private JCheckBox chckbxCamera;
    private JCheckBox chckbxSms;
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
    private JCheckBox chckbxAutoChargecheck;
    private JTextField textFieldLine1;
    private JTextField textFieldLine2;
    private JTextField textFieldLine3;
    private JTextField textFieldSiteAt;
    private JTextField textFieldNameOfDepartment;
    private JTextField textFieldNameOfContractor;
    private JTextField textFieldLine4;
    private JCheckBox chckbxenableSettings2;
    private JCheckBox chckbxTareNoSlno;
    private JTextField textFieldNoOfBags;
    private JTextField textFieldDeductionOrPerCost;
    private JTextField textFieldBagWeight;
    private JCheckBox chckbxExcludeNoOfBags;
    private JCheckBox chckbxManualStatus;
    private boolean reportOpened = false;
    private JCheckBox chckbxExcludeDcNo;
    private JTextField textFieldFinalWt;
    private JTextField textFieldFinalAmount;
    private JCheckBox chckbxIceWater;
    private JCheckBox chckbxNeedLogin;
    private JCheckBox chckbxPrinterCopyDialog;
    private JButton btnReprint;
    private JCheckBox chckbxTakeBackup;
    private JButton btnInsertRow;
    private JButton btnDeleteRow;
    private JButton btnImportFromExcel;
    private JButton btnMassPrint;
    private JTextField textFieldTitle3;
    private JComboBox<String> comboBoxReport;
    private JCheckBox chckbxTareToken;
    private JCheckBox chckbxExitPass;
    private JTextField textFieldPlace;
    private JTextField textFieldPhoneNo;
    private JTextField textFieldRoundOff;
    private JCheckBox chckbxExcludePlaceAndPhoneNumber;
    private JCheckBox chckbxRoundOff;
    private JComboBox<String> comboBoxVehicleNo;
    private JTextField textFieldRoundOffDecimals;
    private JCheckBox chckbxIsCredit;
    private JCheckBox chckbxExcludeCredit;
    private JCheckBox chckbxExcludeVehicleType;
    private JButton btnPrintToken;
    private JButton btnPrintExitPass;
    private JTable tableTransporter;
    private JTextField textFieldNameOfWork;
    private JTextField textFieldAgreementNo;
    private JTextField textFieldEstimateNo;
    private JTable tableOperators;
    private JTable tableVehicleTypes;
    private JComboBox<String> comboBoxVehicleType;
    private JComboBox<String> comboBoxOperator;
    private JLabel lblOperatorName;
    private JLabel lblReportComboBox;
    private JCheckBox chckbxInvoice;
    private JComboBox<String> comboBoxInvoiceProperty;
    private int invoiceNo;
    private JPanel panelInvoice;
    private JPanel panelInvoiceLeft;
    private JPanel panelInvoiceRight;
    private Map<String, JComponent> invoiceFields;
    private JButton btnGetTotal;
    private JButton btnInvoiceSave;
    private JButton btnInvoicePrint;
    private JRadioButton rdbtnLocal;
    private JRadioButton rdbtnOtherStates;

    /**
     * Create the application.
     */
    private WeighBridge() {
        decimalFormat.setMaximumFractionDigits(340);
        try {
            printServices = PrinterJob.lookupPrintServices();
            printers = new Vector<>();
            for (PrintService printer : printServices) {
                printers.add(printer.getName());
            }
            invoiceProperties = new Vector<>();
            try (Stream<Path> paths = Files.walk(Paths.get("Reports"))) {
                invoiceProperties.addAll(paths
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".property"))
                        .map(path -> path.getFileName().toString())
                        .toList());
            } catch (IOException ignored) {
            }
            try {
                if (new File("weighdata.mv.db").exists()) {
                    dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                } else {
                    dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                    ScriptRunner scriptExecutor = new ScriptRunner(dbConnection, true, false);
                    scriptExecutor.runScript(new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResource("data.sql")).openStream())));
                }
            } catch (SQLException | NullPointerException | IOException ignored) {
                JOptionPane.showMessageDialog(null, "DATABASE ALREADY OPEN\nPLZ CLOSE ALL OPEN SOFTWARE FILES\nLINE :328", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (dbConnection == null) {
                System.exit(0);
            }

            // TODO: start
            initialize();
            settings();
            setup();
            cameraSetting();
            initializeWeights();
            new Timer(1000, _ -> {
                Date date = new Date();
                textFieldDateTime.setText(dateAndTimeFormat.format(date));
            }).start();
            getTimer();
            afterStart = true;
            clear();
        } catch (Error | Exception ignored) {
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(null, "UI NOT SUPPORTED\nLINE :306", "UI ERROR", JOptionPane.ERROR_MESSAGE);
        }
        EventQueue.invokeLater(() -> {
            WeighBridge window = new WeighBridge();
            window.babulensWeighbridgeDesigned.setVisible(true);
        });
    }

    private void getTimer() {
        Timer timer = new Timer(21600000, _ -> {
            try {
                if (takeBackup) {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.execute("BACKUP TO 'backup/backup_" + DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss").format(LocalDateTime.now()) + ".zip'");
                }
            } catch (SQLException ignored) {
            }
        });
        timer.setInitialDelay(300000);
        timer.start();
    }

    private void setup() {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM setup");
            rs.absolute(1);
            String id = rs.getString("ID");
            Date endDate,
                    lastLogin;
            String UID;
            switch (id) {
                case "0":
                    endDate = new Date(rs.getTimestamp("ENDDATE").getTime());
                    lastLogin = new Date(rs.getTimestamp("LASTLOGIN").getTime());
                    String[] buttons = {
                            "License The Software",
                            "Trial Period(" + endDate + ")",
                            "Close"
                    };
                    switch (JOptionPane.showOptionDialog(null, "Please Select a Option ?", "Welcome to the \"BABULENS WEIGHBRIDGE\" Software", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, buttons, buttons[2])) {
                        case 0:
                            startup(rs);
                            break;
                        case 1:
                            if (new Date().getTime() - lastLogin.getTime() > 0) {
                                if (endDate.getTime() - new Date().getTime() > 0) {
                                    JOptionPane.showMessageDialog(null, "Welcome to the \"BABULENS WEIGHBRIDGE\" Trial Software", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                                    Timer countDown = new Timer((int) (endDate.getTime() - new Date().getTime()), _ -> {
                                        JOptionPane.showMessageDialog(null, "Your Trial License is over\nplease buy the licence", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                                        close();
                                    });
                                    countDown.start();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Your Trial License is over\nplease buy the licence", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                                    close();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Your have changed your Date\nPlease correct the date to enjoy the trial version", "ERROR", JOptionPane.ERROR_MESSAGE);
                                close();
                            }
                            break;
                        default:
                            close();
                    }
                    break;
                case "1":
                    UID = rs.getString("UID");
                    if (UID.equals(getUUID())) {
                        if (chckbxNeedLogin.isSelected()) {
                            JPasswordField password = new JPasswordField(10);
                            valueEntered = false;
                            password.addActionListener(_ -> {
                                valueEntered = true;
                                JOptionPane.getRootFrame().dispose();
                            });
                            JPanel panel = new JPanel();
                            String[] ConnectOptionNames = {
                                    "Enter",
                                    "Cancel"
                            };
                            password.setActionCommand(ConnectOptionNames[0]);
                            panel.add(new JLabel("<html>Welcome to the \"BABULENS WEIGHBRIDGE\" Software<br/><br/>Please Enter the Login Password ? </html>"));
                            panel.add(password);
                            while (true) {
                                if (JOptionPane.showOptionDialog(null, panel, "Welcome ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null) != JOptionPane.YES_OPTION && !valueEntered) {
                                    //noinspection ConstantConditions
                                    valueEntered = false;
                                    close();
                                }
                                char[] temp = password.getPassword();
                                boolean isCorrect;
                                char[] correctPassword = LOGIN_PASSWORD.toCharArray();
                                if (temp.length != correctPassword.length) {
                                    isCorrect = false;
                                } else {
                                    isCorrect = Arrays.equals(temp, correctPassword);
                                }
                                if (isCorrect) {
                                    break;
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Welcome to the \"BABULENS WEIGHBRIDGE\" Software", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } else {
                        rs.updateString("ID", "0");
                        rs.updateRow();
                        JOptionPane.showMessageDialog(null, "Your License is not Valid\nPlease get a Valid License", "ERROR", JOptionPane.ERROR_MESSAGE);
                        close();
                    }
                    break;
                case "2":
                    rs.updateString("ID", "0");
                    rs.updateString("UID", getUUID());
                    rs.updateTimestamp("ENDDATE", new Timestamp(new Date().getTime() + 10 * (long) 8.64e+7));
                    rs.updateTimestamp("ENDDATE", new Timestamp(new Date().getTime()));
                    rs.updateRow();
                    startup(rs);
                    break;
                default:
                    close();
            }
        } catch (SQLException ignored) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :540", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void startup(ResultSet rs) throws SQLException {
        JPasswordField password = new JPasswordField(10);
        password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
        JPanel panel = new JPanel();
        String[] ConnectOptionNames = {
                "Enter",
                "Cancel"
        };
        panel.add(new JLabel("Please the Password to Continue ? "));
        panel.add(password);
        JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
        char[] temp = password.getPassword();
        boolean isCorrect;
        boolean isCorrect2;
        char[] correctPassword = LICENSE_PASSWORD.toCharArray();
        char[] correctPassword2 = TRIAL_LICENSE_PASSWORD.toCharArray();
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
            rs.updateString("ID", "1");
            rs.updateString("UID", getUUID());
            rs.updateRow();
            JOptionPane.showMessageDialog(null, "Welcome to the \"BABULENS WEIGHBRIDGE\" Software", "Welcome", JOptionPane.INFORMATION_MESSAGE);
        } else if (isCorrect2) {
            rs.updateTimestamp("ENDDATE", new Timestamp(new Date().getTime() + 10 * (long) 8.64e+7));
            rs.updateRow();
            JOptionPane.showMessageDialog(null, "Trial Reset Successfully\n you got 10 days\n Plz Open again", "Reset", JOptionPane.INFORMATION_MESSAGE);
            close();
        } else {
            JOptionPane.showMessageDialog(null, "Your License is not Valid\nPlease get a Valid License", "ERROR", JOptionPane.ERROR_MESSAGE);
            close();
        }
    }

    private String getUUID() {
        String tempDetail = "";
        Process process;
        try {
            process = Runtime.getRuntime().exec(new String[]{
                    "wmic",
                    "csproduct",
                    "get",
                    "UUID"
            });
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
            lock1 = true;
            cameraEvent();
            lock1 = false;
        } catch (SQLException ignored) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :414", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void settings(ActionEvent... ae) {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
            rs.absolute(1);
            textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
            invoiceNo = rs.getInt("INVOICE_NO");
            textFieldTitle1.setText(rs.getString("TITLE1"));
            title1.setText(rs.getString("TITLE1"));
            textFieldTitle2.setText(rs.getString("TITLE2"));
            title2.setText(rs.getString("TITLE2"));
            textFieldTitle3.setText(rs.getString("TITLE3"));
            textFieldFooter.setText(rs.getString("FOOTER"));
            textFieldBaudRate.setText(Integer.toString(rs.getInt("BAUDRATE")));
            textFieldPortName.setText(rs.getString("PORTNAME"));
            textFieldNoOfCopies.setText(Integer.toString(rs.getInt("COPIES")));
            noOfCopies = Integer.parseInt(textFieldNoOfCopies.getText());
            comboBoxPrintOptionForWeight.getModel().setSelectedItem(rs.getString("PRINTOPTIONFORWEIGHT"));
            comboBoxInvoiceProperty.getModel().setSelectedItem(rs.getString("INVOICE_PROPERTY"));
            comboBoxReport.getModel().setSelectedItem(rs.getString("REPORT"));
            chckbxExcludeCharges.setSelected(rs.getBoolean("EXCLUDECHARGES"));
            chckbxExcludeDrivers.setSelected(rs.getBoolean("EXCLUDEDRIVER"));
            chckbxExcludeCustomer.setSelected(rs.getBoolean("EXCLUDECUSTOMERS"));
            chckbxExcludeRemarks.setSelected(rs.getBoolean("EXCLUDEREMARKS"));
            chckbxExcludePlaceAndPhoneNumber.setSelected(rs.getBoolean("EXCLUDE_PLACE_AND_PHONE_NUMBER"));
            chckbxAutoCharges.setSelected(rs.getBoolean("AUTOCHARGES"));
            chckbxExcludeDcNo.setSelected(rs.getBoolean("EXCLUDEDCNO"));
            chckbxExcludeCredit.setSelected(rs.getBoolean("EXCLUDE_CREDIT"));
            chckbxExcludeVehicleType.setSelected(rs.getBoolean("EXCLUDE_VEHICLE_TYPE"));
            chckbxMaterialSl.setSelected(rs.getBoolean("MATERIALSL"));
            chckbxIceWater.setSelected(rs.getBoolean("ICEWATER"));
            chckbxRoundOff.setSelected(rs.getBoolean("ROUND_OFF"));
            chckbxTareToken.setSelected(rs.getBoolean("TARE_TOKEN"));
            chckbxExitPass.setSelected(rs.getBoolean("EXIT_PASS"));
            chckbxNeedLogin.setSelected(rs.getBoolean("NEED_LOGIN"));
            chckbxPrinterCopyDialog.setSelected(rs.getBoolean("NEED_PRINT_COPY_DIALOG"));
            chckbxManualStatus.setSelected(rs.getBoolean("SHOW_STATUS"));
            chckbxTakeBackup.setSelected(rs.getBoolean("TAKE_BACKUP"));
            takeBackup = chckbxTakeBackup.isSelected();
            TRIAL_LICENSE_PASSWORD = rs.getString("TRIAL_LICENSE_PASSWORD");
            LICENSE_PASSWORD = rs.getString("LICENSE_PASSWORD");
            UNLOCK_PASSWORD = rs.getString("UNLOCK_PASSWORD");
            CAMERA_PASSWORD = rs.getString("CAMERA_PASSWORD");
            SMS_PASSWORD = rs.getString("SMS_PASSWORD");
            INVOICE_PASSWORD = rs.getString("INVOICE_PASSWORD");
            MANUAL_ENTRY_PASSWORD = rs.getString("MANUAL_ENTRY_PASSWORD");
            EDIT_ENABLE_PASSWORD = rs.getString("EDIT_ENABLE_PASSWORD");
            RESET_PASSWORD = rs.getString("RESET_PASSWORD");
            LOGIN_PASSWORD = rs.getString("LOGIN_PASSWORD");
            chckbxCamera.setSelected(rs.getBoolean("CAMERA"));
            chckbxSms.setSelected(rs.getBoolean("SMS"));
            chckbxInvoice.setSelected(rs.getBoolean("SHOW_INVOICE"));
            textFieldSMSBaudRate.setText(Integer.toString(rs.getInt("SMSBAUDRATE")));
            textFieldSMSPortName.setText(rs.getString("SMSPORTNAME"));
            textFieldLine1.setText(rs.getString("LINE1"));
            textFieldLine2.setText(rs.getString("LINE2"));
            textFieldLine3.setText(rs.getString("LINE3"));
            textFieldLine4.setText(rs.getString("LINE4"));
            textFieldNameOfContractor.setText(rs.getString("NAME_OF_CONTRACTOR"));
            textFieldNameOfWork.setText(rs.getString("NAME_OF_WORK"));
            textFieldAgreementNo.setText(rs.getString("AGREEMENT_NO"));
            textFieldEstimateNo.setText(rs.getString("ESTIMATE_NO"));
            String operator = rs.getString("OPERATOR");
            textFieldNameOfDepartment.setText(rs.getString("DEPARTMENT_NAME"));
            textFieldSiteAt.setText(rs.getString("SITE_AT"));
            chckbxTareNoSlno.setSelected(rs.getBoolean("TARENOSLNO"));
            chckbxExcludeNoOfBags.setSelected(rs.getBoolean("EXCLUDEBAGS"));
            textFieldBagWeight.setText(decimalFormat.format(rs.getDouble("BAGWEIGHT")));
            textFieldRoundOffDecimals.setText(Integer.toString(rs.getInt("ROUND_OFF_DECIMALS")));

            if (((DefaultComboBoxModel<?>) comboBoxPrinter.getModel()).getIndexOf(rs.getString("PRINTER")) == -1) {
                JOptionPane.showMessageDialog(null, "Please Check the Printer Settings");
            } else {
                comboBoxPrinter.getModel().setSelectedItem(rs.getString("PRINTER"));
            }
            rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
            DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
            model.setRowCount(0);
            customerSet.clear();
            comboBoxCustomerName.removeAllItems();
            while (rs.next()) {
                if (customerSet.add(rs.getString("CUSTOMER"))) {
                    model.addRow(new Object[]{
                            rs.getString("CUSTOMER"),
                    });
                    comboBoxCustomerName.addItem(rs.getString("CUSTOMER"));
                }
            }
            rs = stmt.executeQuery("SELECT * FROM TRANSPORTER");
            model = (DefaultTableModel) tableTransporter.getModel();
            model.setRowCount(0);
            comboBoxTransporterName.removeAllItems();
            transportSet.clear();
            while (rs.next()) {
                if (transportSet.add(rs.getString("TRANSPORTER"))) {
                    model.addRow(new Object[]{
                            rs.getString("TRANSPORTER"),
                    });
                    comboBoxTransporterName.addItem(rs.getString("TRANSPORTER"));
                }
            }
            rs = stmt.executeQuery("SELECT * FROM VEHICLE_TYPES");
            model = (DefaultTableModel) tableVehicleTypes.getModel();
            model.setRowCount(0);
            comboBoxVehicleType.removeAllItems();
            vehicleTypeSet.clear();
            while (rs.next()) {
                if (vehicleTypeSet.add(rs.getString("VEHICLE_TYPE"))) {
                    model.addRow(new Object[]{
                            rs.getString("VEHICLE_TYPE"),
                            rs.getInt("TARE_COST"),
                            rs.getInt("GROSS_COST"),
                    });
                    comboBoxVehicleType.addItem(rs.getString("VEHICLE_TYPE"));
                }
            }
            rs = stmt.executeQuery("SELECT * FROM OPERATORS");
            model = (DefaultTableModel) tableOperators.getModel();
            model.setRowCount(0);
            comboBoxOperator.removeAllItems();
            operatorSet.clear();
            while (rs.next()) {
                if (operatorSet.add(rs.getString("OPERATOR"))) {
                    model.addRow(new Object[]{
                            rs.getString("OPERATOR"),
                    });
                    comboBoxOperator.addItem(rs.getString("OPERATOR"));
                }
            }
            comboBoxOperator.setSelectedItem(operator);
            rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
            model = (DefaultTableModel) tableVehicleTare.getModel();
            model.setRowCount(0);
            vehicleNoSet.clear();
            comboBoxVehicleNo.removeAllItems();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("VEHICLENO"),
                        rs.getString("PLACE"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getInt("TAREWT"),
                        dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME")).getTime()))
                });
                if (vehicleNoSet.add(rs.getString("VEHICLENO"))) {
                    comboBoxVehicleNo.addItem(rs.getString("VEHICLENO"));
                }
            }
            rs = stmt.executeQuery("SELECT * FROM MATERIALS ORDER BY SQNO");
            model = (DefaultTableModel) tableMaterial.getModel();
            model.setRowCount(0);
            comboBoxMaterial.removeAllItems();
            comboBoxMaterialName.removeAllItems();
            materialSet.clear();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("SQNO"),
                        rs.getString("MATERIAL"),
                        rs.getDouble("COST")
                });
                if (materialSet.add(rs.getString("MATERIAL"))) {
                    comboBoxMaterial.addItem(rs.getString("MATERIAL"));
                    comboBoxMaterialName.addItem(rs.getString("MATERIAL"));
                }
            }
            clear();
            refreshInvoice();
        } catch (SQLException | ParseException ignored) {
            if (runUpdateSQL()) {
                settings();
            }
        }
    }

    private ObjectNode getInvoiceData() {
        ObjectNode invoiceData = new ObjectMapper().createObjectNode();
        invoiceFields.forEach((key, component) -> {
            if (component instanceof JTextField) {
                invoiceData.put(key, ((JTextField) component).getText());
            }
        });

        return invoiceData;
    }

    private void saveInvoice(ActionEvent... ae) {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM INVOICES");
            rs.moveToInsertRow();
            rs.updateInt("INVOICE_NO", invoiceNo);
            rs.updateString("INVOICE_DATA", getInvoiceData().toString());
            rs.insertRow();
            rs = stmt.executeQuery("SELECT * FROM SETTINGS");
            rs.absolute(1);
            rs.updateInt("INVOICE_NO", ++invoiceNo);
            rs.updateRow();
        } catch (SQLException ignored) {
            if (runUpdateSQL()) {
                saveInvoice();
            } else {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :5656", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        btnInvoiceSave.setEnabled(false);
        btnInvoicePrint.setEnabled(true);
        btnInvoicePrint.requestFocus();
    }

    private void printInvoice(ActionEvent... ae) {
        ObjectNode invoiceData = getInvoiceData();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            JsonNode invoiceProperty = new ObjectMapper().readTree(new File("Reports/" + comboBoxInvoiceProperty.getSelectedItem()));
            String htmlContent = new String(Files.readAllBytes(Paths.get("Reports/html/" + invoiceProperty.path("html").asText("") + (rdbtnLocal.isSelected() ? "-local" : "-otherState") + ".html")));

            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = invoiceData.fields();
            while (fieldsIterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = fieldsIterator.next();
                String key = entry.getKey();
                String value = StringEscapeUtils.escapeHtml4(entry.getValue().asText());
                htmlContent = htmlContent.replaceAll("\\$\\{" + key + "}", value);
            }
            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocumentFromString(htmlContent);
            iTextRenderer.layout();
            iTextRenderer.createPDF(byteArrayOutputStream);
            iTextRenderer.finishPDF();

            PDDocument document = Loader.loadPDF(byteArrayOutputStream.toByteArray());
            while (document.getNumberOfPages() > 1) {
                document.removePage(document.getNumberOfPages() - 1);
            }
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.setPrintService(PrintServiceLookup.lookupDefaultPrintService());
            job.print();
            document.close();
        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(null, "Print Error Contact System Admin", "DB ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        clearInvoice();
    }

    private void clearInvoice(ActionEvent... ae) {
        invoiceFields.forEach((_, component) -> {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
            component.setEnabled(true);
        });
        btnGetTotal.setEnabled(true);
        btnInvoiceSave.setEnabled(false);
        btnInvoicePrint.setEnabled(false);
        rdbtnLocal.setEnabled(true);
        rdbtnOtherStates.setEnabled(true);
    }

    private void refreshInvoice() {
        if (chckbxInvoice.isSelected()) {
            try {
                panelInvoiceLeft.removeAll();
                panelInvoiceRight.removeAll();
                invoiceFields = new HashMap<>();

                JsonNode invoiceProperty = new ObjectMapper().readTree(new File("Reports/" + comboBoxInvoiceProperty.getSelectedItem()));
                addInvoiceFields(invoiceProperty.get("leftFields"), panelInvoiceLeft);
                addInvoiceFields(invoiceProperty.get("rightFields"), panelInvoiceRight);
                addInvoiceFields(invoiceProperty.get("hiddenFields"));

                panelInvoice.repaint();
                clearInvoice();
            } catch (Exception ignored) {
            }
        }
    }

    private void addInvoiceFields(JsonNode fields) {
        if (fields != null && fields.isArray()) {
            for (JsonNode field : fields) {
                getInvoiceField(field);
            }
        }
    }

    private void addInvoiceFields(JsonNode fields, JPanel panelToAdd) {
        if (fields != null && fields.isArray()) {
            for (JsonNode field : fields) {
                panelToAdd.add(Box.createRigidArea(new Dimension(0, 20)));
                JPanel panel = new JPanel();
                panel.setMaximumSize(new Dimension(400, 25));
                panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
                panel.setBackground(new Color(0, 255, 127));

                JLabel label = new JLabel(field.path("name").asText(""));
                label.setPreferredSize(new Dimension(150, 25));
                label.setFont(new Font("Times New Roman", Font.ITALIC, 20));
                panel.add(label);
                panel.add(getInvoiceField(field));

                panelToAdd.add(panel);
            }
        }

        panelToAdd.add(Box.createHorizontalGlue());
    }

    private String getInvoicePrefix(JsonNode field) {
        return field.path(rdbtnLocal.isSelected() ? "localPrefix" : "invoicePrefix").asText("");
    }

    private JComponent getInvoiceField(JsonNode field) {
        JTextField jTextField;

        switch (field.path("type").asText("")) {
            case "blank": {
                return new JLabel();
            }
            case "material": {
                JComboBox<String> comboBox = new JComboBox<>();
                comboBox.setEditable(true);
                AutoCompleteDecorator.decorate(comboBox);
                comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                materialSet.forEach(comboBox::addItem);
                jTextField = (JTextField) comboBox.getEditor().getEditorComponent();
                jTextField.addPropertyChangeListener(_ -> comboBox.setEnabled(jTextField.isEnabled()));
                invoiceFields.put("material", jTextField);
                return comboBox;
            }
            case "vehicleNo": {
                JComboBox<String> comboBox = new JComboBox<>();
                comboBox.setEditable(true);
                AutoCompleteDecorator.decorate(comboBox);
                comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                vehicleTypeSet.forEach(comboBox::addItem);
                jTextField = (JTextField) comboBox.getEditor().getEditorComponent();
                jTextField.addPropertyChangeListener(_ -> comboBox.setEnabled(jTextField.isEnabled()));
                invoiceFields.put("vehicleNo", jTextField);
                return comboBox;
            }
            case "buyer": {
                JComboBox<String> comboBox = new JComboBox<>();
                comboBox.setEditable(true);
                AutoCompleteDecorator.decorate(comboBox);
                comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                customerSet.forEach(comboBox::addItem);
                jTextField = (JTextField) comboBox.getEditor().getEditorComponent();
                jTextField.addPropertyChangeListener(_ -> comboBox.setEnabled(jTextField.isEnabled()));
                invoiceFields.put("buyer", jTextField);
                return comboBox;
            }
            case "invoiceNo": {
                jTextField = new JTextField(getInvoicePrefix(field) + invoiceNo + field.path("invoiceSuffix").asText(""));
                jTextField.setEnabled(false);
                jTextField.setDisabledTextColor(Color.BLACK);
                jTextField.setHorizontalAlignment(SwingConstants.CENTER);
                jTextField.addPropertyChangeListener(_ -> {
                    if (Objects.equals(jTextField.getText(), "")) {
                        jTextField.setText(getInvoicePrefix(field) + invoiceNo + field.path("invoiceSuffix").asText(""));
                    }
                    if (jTextField.isEnabled()) {
                        jTextField.setEnabled(false);
                    }
                });
                break;
            }
            case "invoiceDate": {
                jTextField = new JTextField(new SimpleDateFormat(field.path("dateFormat").asText("dd-MM-yyyy")).format(new Date()));
                jTextField.setEnabled(false);
                jTextField.setDisabledTextColor(Color.BLACK);
                jTextField.setHorizontalAlignment(SwingConstants.CENTER);
                jTextField.addPropertyChangeListener(_ -> {
                    if (Objects.equals(jTextField.getText(), "")) {
                        jTextField.setText(new SimpleDateFormat(field.path("dateFormat").asText("dd-MM-yyyy")).format(new Date()));
                    }
                    if (jTextField.isEnabled()) {
                        jTextField.setEnabled(false);
                    }
                });
                break;
            }
            case "disabled": {
                jTextField = new JTextField();
                jTextField.setEnabled(false);
                jTextField.setDisabledTextColor(Color.BLACK);
                jTextField.setHorizontalAlignment(SwingConstants.RIGHT);
                jTextField.addPropertyChangeListener(_ -> {
                    if (jTextField.isEnabled()) {
                        jTextField.setEnabled(false);
                    }
                });
                break;
            }
            case "number": {
                jTextField = new JTextField();
                jTextField.setDisabledTextColor(Color.BLACK);
                jTextField.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            }
            default: {
                jTextField = new JTextField();
                jTextField.setHorizontalAlignment(SwingConstants.CENTER);
                jTextField.addPropertyChangeListener(_ -> {
                    if (Objects.equals(jTextField.getText(), "")) {
                        jTextField.setText(field.path("value").asText(""));
                    }
                });
            }
        }

        jTextField.setColumns(10);
        jTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        invoiceFields.put(field.path("key").asText(""), jTextField);
        return jTextField;
    }

    private boolean runUpdateSQL() {
        if (ranModifiedScript) {
            ranModifiedScript = false;
            try {
                ScriptRunner scriptExecutor = new ScriptRunner(dbConnection, true, false);
                scriptExecutor.runScript(new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResource("update.sql")).openStream())));
                return true;
            } catch (IOException | SQLException ignored) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "DB Corrupted Contact System Admin", "DB ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private void updateSettings(ActionEvent... ae) {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
            rs.absolute(1);
            rs.updateString("TITLE1", textFieldTitle1.getText());
            rs.updateString("TITLE2", textFieldTitle2.getText());
            rs.updateString("TITLE3", textFieldTitle3.getText());
            rs.updateString("FOOTER", textFieldFooter.getText());
            rs.updateBoolean("EXCLUDECUSTOMERS", chckbxExcludeCustomer.isSelected());
            rs.updateString("PRINTOPTIONFORWEIGHT", (String) comboBoxPrintOptionForWeight.getSelectedItem());
            rs.updateString("INVOICE_PROPERTY", (String) comboBoxInvoiceProperty.getSelectedItem());
            rs.updateString("REPORT", (String) comboBoxReport.getSelectedItem());
            rs.updateInt("BAUDRATE", Integer.parseInt(0 + textFieldBaudRate.getText().replaceAll("\\D", "")));
            rs.updateString("PORTNAME", textFieldPortName.getText());
            rs.updateString("PRINTER", (String) comboBoxPrinter.getSelectedItem());
            rs.updateInt("COPIES", Integer.parseInt(0 + textFieldNoOfCopies.getText().replaceAll("\\D", "")));
            rs.updateBoolean("EXCLUDECHARGES", chckbxExcludeCharges.isSelected());
            rs.updateBoolean("EXCLUDEDRIVER", chckbxExcludeDrivers.isSelected());
            rs.updateBoolean("EXCLUDEREMARKS", chckbxExcludeRemarks.isSelected());
            rs.updateBoolean("EXCLUDE_PLACE_AND_PHONE_NUMBER", chckbxExcludePlaceAndPhoneNumber.isSelected());
            rs.updateBoolean("AUTOCHARGES", chckbxAutoCharges.isSelected());
            rs.updateBoolean("EXCLUDEDCNO", chckbxExcludeDcNo.isSelected());
            rs.updateBoolean("EXCLUDE_CREDIT", chckbxExcludeCredit.isSelected());
            rs.updateBoolean("EXCLUDE_VEHICLE_TYPE", chckbxExcludeVehicleType.isSelected());
            rs.updateBoolean("MATERIALSL", chckbxMaterialSl.isSelected());
            rs.updateBoolean("ICEWATER", chckbxIceWater.isSelected());
            rs.updateBoolean("ROUND_OFF", chckbxRoundOff.isSelected());
            rs.updateBoolean("TARE_TOKEN", chckbxTareToken.isSelected());
            rs.updateBoolean("EXIT_PASS", chckbxExitPass.isSelected());
            rs.updateBoolean("NEED_LOGIN", chckbxNeedLogin.isSelected());
            rs.updateBoolean("NEED_PRINT_COPY_DIALOG", chckbxPrinterCopyDialog.isSelected());
            rs.updateBoolean("SHOW_STATUS", chckbxManualStatus.isSelected());
            rs.updateBoolean("TAKE_BACKUP", chckbxTakeBackup.isSelected());
            rs.updateBoolean("SMS", chckbxSms.isSelected());
            rs.updateBoolean("SHOW_INVOICE", chckbxInvoice.isSelected());
            rs.updateBoolean("CAMERA", chckbxCamera.isSelected());
            rs.updateInt("SMSBAUDRATE", Integer.parseInt(0 + textFieldSMSBaudRate.getText().replaceAll("\\D", "")));
            rs.updateString("SMSPORTNAME", textFieldSMSPortName.getText().toUpperCase());
            rs.updateString("LINE1", textFieldLine1.getText());
            rs.updateString("LINE2", textFieldLine2.getText());
            rs.updateString("LINE3", textFieldLine3.getText());
            rs.updateString("LINE4", textFieldLine4.getText());
            rs.updateString("NAME_OF_CONTRACTOR", textFieldNameOfContractor.getText());
            rs.updateString("NAME_OF_WORK", textFieldNameOfWork.getText());
            rs.updateString("AGREEMENT_NO", textFieldAgreementNo.getText());
            rs.updateString("ESTIMATE_NO", textFieldEstimateNo.getText());
            rs.updateString("OPERATOR", comboBoxOperator.getEditor().getItem().toString());
            rs.updateString("DEPARTMENT_NAME", textFieldNameOfDepartment.getText());
            rs.updateString("SITE_AT", textFieldSiteAt.getText());
            rs.updateBoolean("TARENOSLNO", chckbxTareNoSlno.isSelected());
            rs.updateBoolean("EXCLUDEBAGS", chckbxExcludeNoOfBags.isSelected());
            rs.updateDouble("BAGWEIGHT", Double.parseDouble(0 + textFieldBagWeight.getText().replaceAll("[^.\\d]", "")));
            rs.updateInt("ROUND_OFF_DECIMALS", Integer.parseInt(0 + textFieldRoundOffDecimals.getText().replaceAll("\\D", "")));
            rs.updateRow();
            PreparedStatement pstmt = dbConnection.prepareStatement("TRUNCATE TABLE CUSTOMER");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
            if (tableCustomer.isEditing()) {
                tableCustomer.getCellEditor().stopCellEditing();
            }
            DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
            customerSet.clear();
            for (int i = 0; i < model.getRowCount(); i++) {
                String temp = (String) model.getValueAt(i, 0);
                if (!temp.isEmpty() && customerSet.add(temp)) {
                    rs.moveToInsertRow();
                    rs.updateString("CUSTOMER", temp);
                    rs.insertRow();
                }
            }
            pstmt = dbConnection.prepareStatement("TRUNCATE TABLE TRANSPORTER");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM TRANSPORTER");
            if (tableTransporter.isEditing()) {
                tableTransporter.getCellEditor().stopCellEditing();
            }
            model = (DefaultTableModel) tableTransporter.getModel();
            transportSet.clear();
            for (int i = 0; i < model.getRowCount(); i++) {
                String temp = (String) model.getValueAt(i, 0);
                if (!temp.isEmpty() && transportSet.add(temp)) {
                    rs.moveToInsertRow();
                    rs.updateString("TRANSPORTER", temp);
                    rs.insertRow();
                }
            }
            pstmt = dbConnection.prepareStatement("TRUNCATE TABLE VEHICLE_TYPES");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM VEHICLE_TYPES");
            if (tableVehicleTypes.isEditing()) {
                tableVehicleTypes.getCellEditor().stopCellEditing();
            }
            model = (DefaultTableModel) tableVehicleTypes.getModel();
            vehicleTypeSet.clear();
            for (int i = 0; i < model.getRowCount(); i++) {
                String temp = (String) model.getValueAt(i, 0);
                if (!temp.isEmpty() && vehicleTypeSet.add(temp)) {
                    rs.moveToInsertRow();
                    rs.updateString("VEHICLE_TYPE", temp);
                    rs.updateInt("TARE_COST", Integer.parseInt(("0" + model.getValueAt(i, 1)).replaceAll("\\D", "")));
                    rs.updateInt("GROSS_COST", Integer.parseInt(("0" + model.getValueAt(i, 2)).replaceAll("\\D", "")));
                    rs.insertRow();
                }
            }
            pstmt = dbConnection.prepareStatement("TRUNCATE TABLE OPERATORS");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM OPERATORS");
            if (tableOperators.isEditing()) {
                tableOperators.getCellEditor().stopCellEditing();
            }
            model = (DefaultTableModel) tableOperators.getModel();
            operatorSet.clear();
            for (int i = 0; i < model.getRowCount(); i++) {
                String temp = (String) model.getValueAt(i, 0);
                if (!temp.isEmpty() && operatorSet.add(temp)) {
                    rs.moveToInsertRow();
                    rs.updateString("OPERATOR", temp);
                    rs.insertRow();
                }
            }
            pstmt = dbConnection.prepareStatement("TRUNCATE TABLE VEHICLETARES");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
            if (tableVehicleTare.isEditing()) {
                tableVehicleTare.getCellEditor().stopCellEditing();
            }
            model = (DefaultTableModel) tableVehicleTare.getModel();
            for (int i = 1; i <= model.getRowCount(); i++) {
                rs.moveToInsertRow();
                rs.updateString("VEHICLENO", (String) model.getValueAt(i - 1, 0));
                rs.updateString("PLACE", (String) model.getValueAt(i - 1, 1));
                rs.updateString("PHONE_NUMBER", (String) model.getValueAt(i - 1, 2));
                rs.updateInt("TAREWT", Integer.parseInt(("0" + model.getValueAt(i - 1, 3)).replaceAll("\\D", "")));
                Date date = dateAndTimeFormat.parse(String.valueOf(model.getValueAt(i - 1, 4)));
                rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                rs.updateTime("TARETIME", new Time(date.getTime()));
                rs.updateInt("SQNO", i);
                rs.insertRow();
            }
            pstmt = dbConnection.prepareStatement("TRUNCATE TABLE MATERIALS");
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT * FROM MATERIALS");
            if (tableMaterial.isEditing()) {
                tableMaterial.getCellEditor().stopCellEditing();
            }
            model = (DefaultTableModel) tableMaterial.getModel();
            for (int i = 1; i <= model.getRowCount(); i++) {
                rs.moveToInsertRow();
                rs.updateString("MATERIAL", model.getValueAt(i - 1, 1).toString().toUpperCase());
                rs.updateDouble("COST", Double.parseDouble(("0" + model.getValueAt(i - 1, 2)).replaceAll("[^.\\d]", "")));
                rs.updateInt("SQNO", (int) model.getValueAt(i - 1, 0));
                rs.insertRow();
            }
            settings();
        } catch (SQLException | ParseException ignored) {
            if (runUpdateSQL()) {
                updateSettings();
            }
        }
    }

    private void initialize() {
        chckbxSlNo.setSelected(true);
        chckbxCustomerName.setSelected(true);
        chckbxVehicleNo.setSelected(true);
        chckbxMaterial.setSelected(true);
        chckbxCharges.setSelected(true);
        chckbxGrossWeight.setSelected(true);
        chckbxTareWeight.setSelected(true);
        chckbxNettWeight.setSelected(true);
        chckbxNettDateAndTime.setSelected(true);

        babulensWeighbridgeDesigned = new JFrame();
        babulensWeighbridgeDesigned.getContentPane().setBackground(new Color(0, 255, 127));
        babulensWeighbridgeDesigned.setBounds(new Rectangle(100, 100, 1280, 768));
        babulensWeighbridgeDesigned.setExtendedState(Frame.MAXIMIZED_BOTH);
        babulensWeighbridgeDesigned.setUndecorated(true);
        try {
            babulensWeighbridgeDesigned.setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/logo.bmp"))));
        } catch (IOException ignored) {
        }
        babulensWeighbridgeDesigned.setTitle("BABULENS WEIGHBRIDGE designed by \"BABULENS ENTERPRISES\"");
        babulensWeighbridgeDesigned.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        babulensWeighbridgeDesigned.getContentPane().setLayout(null);

        JLabel title = new JLabel("BABULENS WEIGHBRIDGE");
        title.setForeground(new Color(0, 0, 255));
        title.setBounds(10, 11, 325, 30);
        title.setFont(new Font("Algerian", Font.ITALIC, 25));
        babulensWeighbridgeDesigned.getContentPane().add(title);

        JButton close = new JButton("Close");
        close.setFocusable(false);
        close.setBounds(646, 11, 100, 30);
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addActionListener(this::close);
        close.setFont(new Font("Times New Roman", Font.BOLD, 20));
        babulensWeighbridgeDesigned.getContentPane().add(close);

        title1 = new JLabel("title1");
        title1.setForeground(new Color(0, 0, 255));
        title1.setBackground(new Color(0, 255, 127));
        title1.setBounds(10, 52, 1260, 25);
        title1.setHorizontalAlignment(SwingConstants.CENTER);
        title1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
        babulensWeighbridgeDesigned.getContentPane().add(title1);

        title2 = new JLabel("title2");
        title2.setForeground(new Color(0, 0, 255));
        title2.setBackground(new Color(0, 255, 127));
        title2.setBounds(10, 78, 1260, 25);
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        title2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
        babulensWeighbridgeDesigned.getContentPane().add(title2);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(new Color(0, 255, 127));
        tabbedPane.setFocusable(false);
        tabbedPane.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
        tabbedPane.setBounds(10, 103, 1260, 654);
        babulensWeighbridgeDesigned.getContentPane().add(tabbedPane);

        JPanel panelWeighing = new JPanel();
        panelWeighing.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                if (afterStart) {
                    settings();
                }
            }
        });
        panelWeighing.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("          Weighing          ", panelWeighing);
        panelWeighing.setLayout(null);

        lblWeight = new JLabel("-1");
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
        lblSlNo.setBounds(50, 110, 175, 25);
        panelWeighing.add(lblSlNo);

        JLabel lblDateTime = new JLabel("Date & Time");
        lblDateTime.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDateTime.setBounds(50, 190, 175, 25);
        panelWeighing.add(lblDateTime);

        JLabel lblVehicleNo = new JLabel("Vehicle No");
        lblVehicleNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblVehicleNo.setBounds(50, 230, 175, 25);
        panelWeighing.add(lblVehicleNo);

        JLabel lblMaterial = new JLabel("Material");
        lblMaterial.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblMaterial.setBounds(50, 310, 175, 25);
        panelWeighing.add(lblMaterial);

        JLabel lblCharges = new JLabel("Charges");
        lblCharges.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCharges.setBounds(50, 390, 90, 25);
        panelWeighing.add(lblCharges);

        JLabel lblGrossWt = new JLabel("Gross Wt");
        lblGrossWt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblGrossWt.setBounds(475, 310, 90, 25);
        panelWeighing.add(lblGrossWt);

        JLabel lblTareWt = new JLabel("Tare Wt");
        lblTareWt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTareWt.setBounds(475, 350, 75, 25);
        panelWeighing.add(lblTareWt);

        JLabel lblNetWt = new JLabel("Nett Wt");
        lblNetWt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblNetWt.setBounds(475, 430, 75, 25);
        panelWeighing.add(lblNetWt);

        rdbtnGross = new JRadioButton("Gross");
        rdbtnGross.setBackground(new Color(0, 255, 127));
        rdbtnGross.addActionListener(_ -> requestFocus(""));
        rdbtnGross.setSelected(true);
        buttonGroupWeight.add(rdbtnGross);
        rdbtnGross.setFocusable(false);
        rdbtnGross.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnGross.setBounds(75, 25, 100, 25);
        panelWeighing.add(rdbtnGross);

        rdbtnTare = new JRadioButton("Tare");
        rdbtnTare.setBackground(new Color(0, 255, 127));
        rdbtnTare.addActionListener(_ -> {
            comboBoxMaterial.getModel().setSelectedItem("EMPTY");
            requestFocus("");
        });
        buttonGroupWeight.add(rdbtnTare);
        rdbtnTare.setFocusable(false);
        rdbtnTare.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnTare.setBounds(75, 54, 100, 25);
        panelWeighing.add(rdbtnTare);

        textFieldCharges = new JTextField();
        textFieldCharges.addActionListener(_ -> requestFocus("Charges"));
        textFieldCharges.setDisabledTextColor(Color.BLACK);
        textFieldCharges.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldCharges.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldCharges.setBounds(240, 390, 175, 25);
        panelWeighing.add(textFieldCharges);
        textFieldCharges.setColumns(10);

        comboBoxCustomerName = new JComboBox<>();
        comboBoxCustomerName.setEditable(true);
        AutoCompleteDecorator.decorate(comboBoxCustomerName);
        comboBoxCustomerName.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                comboBoxCustomerName.setSelectedItem(Objects.toString(comboBoxCustomerName.getSelectedItem(), "").toUpperCase().trim());
            }
        });
        comboBoxCustomerName.addActionListener(l -> {
            if (l.getActionCommand().equals("comboBoxEdited")) {
                requestFocus("CustomerName");
            }
        });
        comboBoxCustomerName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBoxCustomerName.setBounds(240, 150, 175, 25);
        panelWeighing.add(comboBoxCustomerName);

        comboBoxMaterial = new JComboBox<>();
        comboBoxMaterial.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (chckbxRoundOff.isSelected()) {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIAL = '" + comboBoxMaterial.getEditor().getItem().toString() + "'");
                        if (rs.next()) {
                            textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("COST")));
                        } else {
                            textFieldDeductionOrPerCost.setText("0");
                        }
                    } catch (SQLException ignored) {
                    }
                }
                comboBoxMaterial.setSelectedItem(Objects.toString(comboBoxMaterial.getSelectedItem(), "").toUpperCase());
            }
        });
        comboBoxMaterial.addActionListener(l -> {
            if (l.getActionCommand().equals("comboBoxEdited")) {
                if (chckbxMaterialSl.isSelected()) {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT MATERIAL FROM MATERIALS where SQNO =" + Integer.parseInt(comboBoxMaterial.getEditor().getItem().toString()));
                        if (rs.next()) {
                            comboBoxMaterial.setSelectedItem(rs.getString("MATERIAL"));
                        }
                    } catch (SQLException | NumberFormatException ignored) {
                    }
                }
                if (chckbxIceWater.isSelected()) {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIAL like '" + comboBoxMaterial.getEditor().getItem().toString() + "'");
                        if (rs.next()) {
                            textFieldCharges.setText(decimalFormat.format(rs.getDouble("COST")));
                        }
                    } catch (SQLException | NumberFormatException ignored) {
                    }
                }
                if (chckbxRoundOff.isSelected()) {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIAL = '" + comboBoxMaterial.getEditor().getItem().toString() + "'");
                        if (rs.next()) {
                            textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("COST")));
                        } else {
                            textFieldDeductionOrPerCost.setText("0");
                        }
                    } catch (SQLException ignored) {
                    }
                }
                comboBoxMaterial.setSelectedItem(Objects.toString(comboBoxMaterial.getSelectedItem(), "").toUpperCase());
                requestFocus("Material");
            }
        });
        comboBoxMaterial.setEditable(true);
        AutoCompleteDecorator.decorate(comboBoxMaterial);
        comboBoxMaterial.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxMaterial.setBounds(240, 310, 175, 25);
        panelWeighing.add(comboBoxMaterial);

        textFieldDateTime = new JTextField();
        textFieldDateTime.setEnabled(false);
        textFieldDateTime.setDisabledTextColor(Color.BLACK);
        textFieldDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textFieldDateTime.setColumns(10);
        textFieldDateTime.setBounds(240, 190, 175, 25);
        panelWeighing.add(textFieldDateTime);

        textFieldSlNo = new JTextField();
        textFieldSlNo.setEnabled(false);
        textFieldSlNo.setDisabledTextColor(Color.BLACK);
        textFieldSlNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSlNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldSlNo.setColumns(10);
        textFieldSlNo.setBounds(240, 110, 175, 25);
        panelWeighing.add(textFieldSlNo);

        textFieldGrossWt = new JTextField();
        textFieldGrossWt.setDisabledTextColor(Color.BLACK);
        textFieldGrossWt.setText("0");
        textFieldGrossWt.setEnabled(false);
        textFieldGrossWt.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldGrossWt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldGrossWt.setColumns(10);
        textFieldGrossWt.setBounds(619, 310, 100, 25);
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
        textFieldNetWt.setBounds(619, 430, 100, 25);
        panelWeighing.add(textFieldNetWt);

        textFieldGrossDateTime = new JTextField();
        textFieldGrossDateTime.setDisabledTextColor(Color.BLACK);
        textFieldGrossDateTime.setEnabled(false);
        textFieldGrossDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldGrossDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textFieldGrossDateTime.setColumns(10);
        textFieldGrossDateTime.setBounds(775, 310, 175, 25);
        panelWeighing.add(textFieldGrossDateTime);

        textFieldTareDateTime = new JTextField();
        textFieldTareDateTime.setDisabledTextColor(Color.BLACK);
        textFieldTareDateTime.setEnabled(false);
        textFieldTareDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTareDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textFieldTareDateTime.setColumns(10);
        textFieldTareDateTime.setBounds(775, 350, 175, 25);
        panelWeighing.add(textFieldTareDateTime);

        textFieldNetDateTime = new JTextField();
        textFieldNetDateTime.setDisabledTextColor(Color.BLACK);
        textFieldNetDateTime.setEnabled(false);
        textFieldNetDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNetDateTime.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textFieldNetDateTime.setColumns(10);
        textFieldNetDateTime.setBounds(775, 430, 175, 25);
        panelWeighing.add(textFieldNetDateTime);

        btnGetGross = new JButton("Get Gross Details");
        btnGetGross.setVisible(false);
        btnGetGross.setFocusable(false);
        btnGetGross.setEnabled(false);
        btnGetGross.addActionListener(_ -> {
            String[] ConnectOptionNames = {
                    "Set Gross",
                    "Cancel"
            };
            JTextField jTextField = new JTextField(10);
            valueEntered = false;
            DatePickerSettings datePickerSettings = new DatePickerSettings();
            datePickerSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
            datePickerSettings.setFormatForDatesBeforeCommonEra("dd-MM-yyyy");
            TimePickerSettings timePickerSettings = new TimePickerSettings();
            timePickerSettings.setFormatForDisplayTime("hh:mm a");
            DateTimePicker dateTimePicker = new DateTimePicker(datePickerSettings, timePickerSettings);
            dateTimePicker.setDateTimeStrict(LocalDateTime.now());
            jTextField.addActionListener(_ -> {
                dateTimePicker.datePicker.getComponentDateTextField().selectAll();
                dateTimePicker.datePicker.getComponentDateTextField().requestFocus();
            });

            dateTimePicker.datePicker.getComponentDateTextField().addActionListener(_ -> {
                dateTimePicker.timePicker.getComponentTimeTextField().selectAll();
                dateTimePicker.timePicker.getComponentTimeTextField().requestFocus();
            });

            dateTimePicker.timePicker.getComponentTimeTextField().addActionListener(_ -> {
                valueEntered = true;
                JOptionPane.getRootFrame().dispose();
            });

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Gross Wt "));
            panel.add(jTextField);
            panel.add(new JLabel("Gross Date & time"));
            panel.add(dateTimePicker);

            if (JOptionPane.showOptionDialog(null, panel, "Enter Gross Wt ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, "") == 0 || valueEntered) {
                try {
                    textFieldGrossWt.setText(Integer.toString(Integer.parseInt(jTextField.getText())));
                    textFieldGrossDateTime.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a").format(dateTimePicker.getDateTimeStrict()));
                    btnGetGross.setEnabled(false);
                    if (rdbtnGross.isSelected()) {
                        btnTotal.setEnabled(true);
                    }
                } catch (NumberFormatException ignored) {
                    JOptionPane.showMessageDialog(null, "Plz check the Value Entered\n\nLINE :922", "Value ERROR", JOptionPane.ERROR_MESSAGE);

                }
            }

        });
        btnGetGross.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetGross.setBounds(990, 310, 225, 25);
        panelWeighing.add(btnGetGross);

        btnGetTare = new JButton("Get Tare Details");
        btnGetTare.setVisible(false);
        btnGetTare.setFocusable(false);
        btnGetTare.setEnabled(false);
        btnGetTare.addActionListener(_ -> {
            String[] ConnectOptionNames = {
                    "Set Tare",
                    "Cancel"
            };
            JTextField jTextField = new JTextField(10);
            valueEntered = false;
            DatePickerSettings datePickerSettings = new DatePickerSettings();
            datePickerSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
            datePickerSettings.setFormatForDatesBeforeCommonEra("dd-MM-yyyy");
            TimePickerSettings timePickerSettings = new TimePickerSettings();
            timePickerSettings.setFormatForDisplayTime("hh:mm a");
            DateTimePicker dateTimePicker = new DateTimePicker(datePickerSettings, timePickerSettings);
            dateTimePicker.setDateTimeStrict(LocalDateTime.now());

            jTextField.addActionListener(_ -> {
                dateTimePicker.datePicker.getComponentDateTextField().selectAll();
                dateTimePicker.datePicker.getComponentDateTextField().requestFocus();
            });

            dateTimePicker.datePicker.getComponentDateTextField().addActionListener(_ -> {
                dateTimePicker.timePicker.getComponentTimeTextField().selectAll();
                dateTimePicker.timePicker.getComponentTimeTextField().requestFocus();
            });

            dateTimePicker.timePicker.getComponentTimeTextField().addActionListener(_ -> {
                valueEntered = true;
                JOptionPane.getRootFrame().dispose();
            });

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Tare Wt "));
            panel.add(jTextField);
            panel.add(new JLabel("Tare  Date & time"));
            panel.add(dateTimePicker);
            if (JOptionPane.showOptionDialog(null, panel, "Enter Tare Wt ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null) == 0 || valueEntered) {
                try {
                    textFieldTareWt.setText(Integer.toString(Integer.parseInt(jTextField.getText())));
                    textFieldTareDateTime.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a").format(dateTimePicker.getDateTimeStrict()));
                    btnGetTare.setEnabled(false);
                    if (rdbtnTare.isSelected()) {
                        btnTotal.setEnabled(true);
                    }
                } catch (NumberFormatException ignored) {
                    JOptionPane.showMessageDialog(null, "Plz check the Value Entered\n\nLINE :969", "Value ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnGetTare.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetTare.setBounds(990, 350, 225, 25);
        panelWeighing.add(btnGetTare);

        btnTotal = new JButton("Total");
        btnTotal.setVisible(false);
        btnTotal.setFocusable(false);
        btnTotal.addActionListener(_ -> {
            comboBoxVehicleNo.setSelectedItem(((String) comboBoxVehicleNo.getEditor().getItem()).toUpperCase().replaceAll(" ", ""));
            if (rdbtnGross.isSelected()) {
                textFieldNetDateTime.setText(textFieldGrossDateTime.getText());
            } else {
                textFieldNetDateTime.setText(textFieldTareDateTime.getText());
            }
            if (chckbxIceWater.isSelected()) {
                textFieldDeductionOrPerCost.setText(Integer.toString(Integer.parseInt(0 + textFieldDeductionOrPerCost.getText().replaceAll("\\D", ""))));

                if (Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) > 0 && !textFieldTareWt.getText().equals("0")) {
                    textFieldNetWt.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText())));
                }
            } else if (chckbxRoundOff.isSelected()) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIAL = '" + comboBoxMaterial.getEditor().getItem().toString() + "'");
                    if (rs.next()) {
                        textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("COST")));
                    } else {
                        textFieldDeductionOrPerCost.setText("0");
                    }
                    if (Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) > 0 && !textFieldTareWt.getText().equals("0")) {
                        textFieldNetWt.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText())));
                    }
                } catch (SQLException ignored) {
                }
            } else {
                textFieldDeductionOrPerCost.setText(Integer.toString((int) (Integer.parseInt(0 + textFieldNoOfBags.getText().replaceAll("\\D", "")) * Double.parseDouble(0 + textFieldBagWeight.getText().replaceAll("[^.\\d]", "")))));

                if (Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) - Integer.parseInt(textFieldDeductionOrPerCost.getText()) > 0 && !textFieldTareWt.getText().equals("0")) {
                    textFieldNetWt.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) - Integer.parseInt(textFieldDeductionOrPerCost.getText())));
                }
            }
            if (chckbxAutoChargecheck.isSelected()) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIAL = '" + comboBoxMaterial.getEditor().getItem() + "'");
                    if (rs.next()) {
                        textFieldCharges.setText(String.valueOf((int) (rs.getDouble("COST") * Double.parseDouble(textFieldNetWt.getText()))));
                    } else {
                        textFieldCharges.setText("0");
                    }
                } catch (SQLException | NumberFormatException ignored) {
                }
            } else {
                String[] temp = ("0" + textFieldCharges.getText() + ".0").replaceAll("[^.\\d]", "").split("\\.");
                textFieldCharges.setText(decimalFormat.format(Double.parseDouble(temp[0] + "." + temp[1])));
            }
            if (chckbxRoundOff.isSelected()) {
                textFieldRoundOff.setText(decimalFormat.format(-1 * Double.parseDouble(0 + textFieldCharges.getText()) % Math.pow(10, Double.parseDouble(textFieldRoundOffDecimals.getText().replaceAll("\\D", "")))).replaceAll("-0", "0"));
                textFieldCharges.setText(decimalFormat.format(Double.parseDouble(0 + textFieldCharges.getText()) + Double.parseDouble(textFieldRoundOff.getText())));
            }

            textFieldNoOfBags.setText(Integer.toString(Integer.parseInt(0 + textFieldNoOfBags.getText().replaceAll("\\D", ""))));

            if (chckbxIceWater.isSelected() && Integer.parseInt(textFieldNetWt.getText()) > 0) {
                textFieldFinalWt.setText(Integer.toString(Integer.parseInt(textFieldNetWt.getText()) - Integer.parseInt(0 + textFieldDeductionOrPerCost.getText().replaceAll("\\D", ""))));
                textFieldFinalAmount.setText(Integer.toString((int) (Integer.parseInt(textFieldFinalWt.getText()) * Double.parseDouble(0 + textFieldCharges.getText().replaceAll("[^.\\d]", ""))) - Integer.parseInt(0 + textFieldNoOfBags.getText().replaceAll("\\D", ""))));
            }
            comboBoxCustomerName.setEnabled(false);
            comboBoxTransporterName.setEnabled(false);
            rdbtnGross.setEnabled(false);
            btnGetTareSl.setEnabled(false);
            rdbtnTare.setEnabled(false);
            btnGetGrossSl.setEnabled(false);
            comboBoxVehicleNo.setEnabled(false);
            comboBoxMaterial.setEnabled(false);
            textFieldNoOfBags.setEnabled(false);
            textFieldCharges.setEnabled(false);
            textFieldDeductionOrPerCost.setEnabled(false);
            btnSave.setEnabled(true);
            textFieldPlace.setEnabled(false);
            textFieldPhoneNo.setEnabled(false);
            btnGetDcDetails.setEnabled(false);
            btnGetWeight.setEnabled(false);
            btnGetGross.setEnabled(false);
            btnGetTare.setEnabled(false);
            chckbxAutoChargecheck.setEnabled(false);
            btnTotal.setEnabled(false);
            btnMinusGross.setEnabled(false);
            btnPlusTare.setEnabled(false);
            textPaneRemarks.setEnabled(false);
            chckbxIsCredit.setEnabled(false);
            btnSave.requestFocus();
        });
        btnTotal.setEnabled(false);
        btnTotal.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnTotal.setBounds(990, 430, 225, 25);
        panelWeighing.add(btnTotal);

        btnGetTareSl = new JButton("Get Tare Wt");
        btnGetTareSl.setFocusable(false);
        btnGetTareSl.addActionListener(_ -> {
            rdbtnGross.setSelected(true);
            JComboBox<String> jComboBox = new JComboBox<>();
            jComboBox
                    .setModel(
                            new DefaultComboBoxModel<>(new String[]{
                                    "Tare Sl.no",
                                    "Gross Sl.no",
                                    "Net Sl.no"
                            }));
            Object[] params = {
                    "Select the field type for Tare Wt ?",
                    jComboBox,
                    "Enter the Sl.no To Get Tare Wt ?"
            };
            String response = JOptionPane.showInputDialog(null, params, "Getting the Sl.no for Tare Wt ", JOptionPane.QUESTION_MESSAGE);
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException ignored) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1550", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (!(response == null || (response.isEmpty()) || Integer.parseInt(response.replaceAll("\\D", "")) >= serialNo || Integer.parseInt(response.replaceAll("\\D", "")) <= 0)) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + response);
                    rs.next();
                    if (chckbxTareNoSlno.isSelected()) {
                        if (rs.getInt("GROSSWT") != 0) {
                            JOptionPane.showMessageDialog(null, "Record already updated.\nPlease try a new Entry.", "RECORD ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
                    }
                    textFieldDcNo.setText(rs.getString("DCNO"));
                    textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                    comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    comboBoxTransporterName.setSelectedItem(rs.getString("DRIVERNAME"));
                    comboBoxVehicleNo.setSelectedItem(rs.getString("VEHICLENO"));
                    textFieldPlace.setText(rs.getString("PLACE"));
                    textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                    textFieldNoOfBags.setText(Integer.toString(rs.getInt("NOOFBAGS")));
                    textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")));
                    textFieldTareWt.setText(Integer.toString(rs.getInt(Objects.toString(jComboBox.getSelectedItem(), "").replace("Sl.no", "").trim() + "WT")));
                    textFieldTareDateTime.setText(rs.getDate(jComboBox.getSelectedItem().toString().replace("Sl.no", "").trim() + "DATE") + " " + rs.getTime(jComboBox.getSelectedItem().toString().replace("Sl.no", "").trim() + "TIME"));
                    if (chckbxTareNoSlno.isSelected()) {
                        textFieldCharges.setText(decimalFormat.format(rs.getDouble("CHARGES")));
                    }
                    chckbxIsCredit.setSelected(rs.getBoolean("CREDIT"));
                    if (textFieldTareDateTime.getText().equals("null null")) {
                        textFieldTareDateTime.setText("");
                    } else {
                        textFieldTareDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldTareDateTime.getText()).getTime())));
                    }
                } catch (SQLException | ParseException ignored) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :820", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                comboBoxVehicleNo.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                comboBoxMaterial.setEnabled(true);
                comboBoxMaterial.setSelectedItem("");
                requestFocus("");
            }

        });
        btnGetTareSl.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetTareSl.setBounds(207, 25, 150, 25);
        panelWeighing.add(btnGetTareSl);

        btnGetGrossSl = new JButton("Get Gross Wt");
        btnGetGrossSl.setFocusable(false);
        btnGetGrossSl.addActionListener(_ -> {

            rdbtnTare.setSelected(true);
            JComboBox<String> jComboBox = new JComboBox<>();
            jComboBox.setModel(
                    new DefaultComboBoxModel<>(new String[]{
                            "Gross Sl.no",
                            "Tare Sl.no",
                            "Net Sl.no"
                    }));
            Object[] params = {
                    "Select the field type for Gross Wt ?",
                    jComboBox,
                    "Enter the Sl.no To Get Gross Wt ?"
            };
            String response = JOptionPane.showInputDialog(null, params, "Getting the Sl.no for Gross Wt ", JOptionPane.QUESTION_MESSAGE);
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException ignored) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :847", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (!(response == null || (response.isEmpty()) || Integer.parseInt(response) >= serialNo || Integer.parseInt(response) <= 0)) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + response);
                    rs.next();
                    if (chckbxTareNoSlno.isSelected()) {
                        if (rs.getInt("TAREWT") != 0) {
                            JOptionPane.showMessageDialog(null, "Record already updated.\nPlease try a new Entry.", "RECORD ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
                    }
                    textFieldDcNo.setText(rs.getString("DCNO"));
                    textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                    comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    comboBoxTransporterName.setSelectedItem(rs.getString("DRIVERNAME"));
                    comboBoxVehicleNo.setSelectedItem(rs.getString("VEHICLENO"));
                    textFieldPlace.setText(rs.getString("PLACE"));
                    textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                    textFieldNoOfBags.setText(Integer.toString(rs.getInt("NOOFBAGS")));
                    textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")));
                    textFieldGrossWt.setText(Integer.toString(rs.getInt(Objects.toString(jComboBox.getSelectedItem(), "").replace("Sl.no", "").trim() + "WT")));
                    textFieldGrossDateTime.setText(rs.getDate(jComboBox.getSelectedItem().toString().replace("Sl.no", "").trim() + "DATE") + " " + rs.getTime(jComboBox.getSelectedItem().toString().replace("Sl.no", "").trim() + "TIME"));
                    if (chckbxTareNoSlno.isSelected()) {
                        textFieldCharges.setText(decimalFormat.format(rs.getDouble("CHARGES")));
                    }
                    chckbxIsCredit.setSelected(rs.getBoolean("CREDIT"));
                    if (textFieldGrossDateTime.getText().equals("null null")) {
                        textFieldGrossDateTime.setText("");
                    } else {
                        textFieldGrossDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldGrossDateTime.getText()).getTime())));
                    }
                } catch (SQLException | ParseException ignored) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :861", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                comboBoxVehicleNo.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                comboBoxMaterial.setEnabled(true);
                comboBoxMaterial.setSelectedItem("");
                requestFocus("");
            }
        });
        btnGetGrossSl.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetGrossSl.setBounds(207, 54, 150, 25);
        panelWeighing.add(btnGetGrossSl);

        btnGetWeight = new JButton("Get Weight");
        btnGetWeight.addActionListener(_ -> {
            if (chckbxCamera.isSelected()) {
                if (checkBoxCamera1.isSelected()) {
                    try {
                        panelCameras.remove(panelCamera1);
                        Runnable stuffToDo = new Thread(() -> clickedImage = webcam[1].getImage());
                        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
                            Future<?> future = executor.submit(stuffToDo);
                            executor.shutdown();
                            try {
                                future.get(1, TimeUnit.SECONDS);
                            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                            }
                            if (!executor.isTerminated()) {
                                clickedImage = null;
                                executor.shutdownNow();
                            }
                        }
                        labelCamera1 = new JLabel(new ImageIcon(clickedImage.getScaledInstance((int) (((double) 240 / ((Dimension) Objects.requireNonNull(comboBoxResolution1.getSelectedItem())).height * ((Dimension) comboBoxResolution1.getSelectedItem()).width)), 240, Image.SCALE_SMOOTH)));
                        labelCamera1.setBounds(10, 11, (int) ((double) 240 / labelCamera1.getHeight() * labelCamera1.getWidth()), 240);
                        panelCameras.add(labelCamera1);
                    } catch (NullPointerException ignored) {
                    }
                }

                if (checkBoxCamera2.isSelected()) {
                    try {
                        panelCameras.remove(panelCamera2);
                        Runnable stuffToDo = new Thread(() -> clickedImage = webcam[2].getImage());
                        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
                            Future<?> future = executor.submit(stuffToDo);
                            executor.shutdown();
                            try {
                                future.get(1, TimeUnit.SECONDS);
                            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                            }
                            if (!executor.isTerminated()) {
                                clickedImage = null;
                                executor.shutdownNow();
                            }
                        }
                        labelCamera2 = new JLabel(new ImageIcon(clickedImage.getScaledInstance((int) (((double) 240 / ((Dimension) Objects.requireNonNull(comboBoxResolution2.getSelectedItem())).height * ((Dimension) comboBoxResolution2.getSelectedItem()).width)), 240, Image.SCALE_SMOOTH)));
                        panelCameras.add(labelCamera2);
                        labelCamera2.setBounds(10, 11, (int) (((double) 240 / ((Dimension) comboBoxResolution2.getSelectedItem()).height * ((Dimension) comboBoxResolution2.getSelectedItem()).width)), 240);
                    } catch (NullPointerException ignored) {
                    }
                }

                if (checkBoxCamera3.isSelected()) {
                    try {
                        panelCameras.remove(panelCamera3);
                        Runnable stuffToDo = new Thread(() -> clickedImage = webcam[3].getImage());
                        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
                            Future<?> future = executor.submit(stuffToDo);
                            executor.shutdown();
                            try {
                                future.get(1, TimeUnit.SECONDS);
                            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                            }
                            if (!executor.isTerminated()) {
                                clickedImage = null;
                                executor.shutdownNow();
                            }
                        }
                        labelCamera3 = new JLabel(new ImageIcon(clickedImage.getScaledInstance((int) (((double) 240 / ((Dimension) Objects.requireNonNull(comboBoxResolution3.getSelectedItem())).height * ((Dimension) comboBoxResolution3.getSelectedItem()).width)), 240, Image.SCALE_SMOOTH)));
                        panelCameras.add(labelCamera3);
                        labelCamera3.setBounds(10, 11, (int) (((double) 240 / ((Dimension) comboBoxResolution3.getSelectedItem()).height * ((Dimension) comboBoxResolution3.getSelectedItem()).width)), 240);
                    } catch (NullPointerException ignored) {
                    }
                }

                if (checkBoxCamera4.isSelected()) {
                    try {
                        panelCameras.remove(panelCamera4);
                        Runnable stuffToDo = new Thread(() -> clickedImage = webcam[4].getImage());
                        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
                            Future<?> future = executor.submit(stuffToDo);
                            executor.shutdown();
                            try {
                                future.get(1, TimeUnit.SECONDS);
                            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                            }
                            if (!executor.isTerminated()) {
                                clickedImage = null;
                                executor.shutdownNow();
                            }
                        }
                        labelCamera4 = new JLabel(new ImageIcon(clickedImage.getScaledInstance((int) (((double) 240 / ((Dimension) Objects.requireNonNull(comboBoxResolution4.getSelectedItem())).height * ((Dimension) comboBoxResolution4.getSelectedItem()).width)), 240, Image.SCALE_SMOOTH)));
                        panelCameras.add(labelCamera4);
                        labelCamera4.setBounds(10, 11, (int) (((double) 240 / ((Dimension) comboBoxResolution4.getSelectedItem()).height * ((Dimension) comboBoxResolution4.getSelectedItem()).width)), 240);
                    } catch (NullPointerException ignored) {
                    }
                }

            }
            comboBoxVehicleNo.setSelectedItem(((String) comboBoxVehicleNo.getEditor().getItem()).toUpperCase().replaceAll(" ", ""));
            if (rdbtnGross.isSelected()) {
                textFieldGrossWt.setText(lblWeight.getText());
                textFieldGrossDateTime.setText(textFieldDateTime.getText());
            } else {
                textFieldTareWt.setText(lblWeight.getText());
                textFieldTareDateTime.setText(textFieldDateTime.getText());
            }
            if (chckbxIceWater.isSelected()) {
                textFieldDeductionOrPerCost.setText(Integer.toString(Integer.parseInt(0 + textFieldDeductionOrPerCost.getText().replaceAll("\\D", ""))));

                if (Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) > 0 && !textFieldTareWt.getText().equals("0")) {
                    textFieldNetWt.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText())));
                }
            } else if (chckbxRoundOff.isSelected()) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIAL = '" + comboBoxMaterial.getEditor().getItem().toString() + "'");
                    if (rs.next()) {
                        textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("COST")));
                    } else {
                        textFieldDeductionOrPerCost.setText("0");
                    }
                    if (Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) > 0 && !textFieldTareWt.getText().equals("0")) {
                        textFieldNetWt.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText())));
                    }
                } catch (SQLException ignored) {
                }
            } else {
                textFieldDeductionOrPerCost.setText(Integer.toString((int) (Integer.parseInt(0 + textFieldNoOfBags.getText().replaceAll("\\D", "")) * Double.parseDouble(0 + textFieldBagWeight.getText().replaceAll("[^.\\d]", "")))));

                if (Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) - Integer.parseInt(textFieldDeductionOrPerCost.getText()) > 0 && !textFieldTareWt.getText().equals("0")) {
                    textFieldNetWt.setText(Integer.toString(Integer.parseInt(textFieldGrossWt.getText()) - Integer.parseInt(textFieldTareWt.getText()) - Integer.parseInt(textFieldDeductionOrPerCost.getText())));
                }
            }
            if (chckbxAutoChargecheck.isSelected()) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT COST FROM MATERIALS where MATERIAL = '" + comboBoxMaterial.getEditor().getItem() + "'");
                    if (rs.next()) {
                        textFieldCharges.setText(String.valueOf((int) (rs.getDouble("COST") * Double.parseDouble(textFieldNetWt.getText()))));
                    } else {
                        textFieldCharges.setText("0");
                    }
                } catch (SQLException | NumberFormatException ignored) {
                }
            } else {
                String[] temp = ("0" + textFieldCharges.getText() + ".0").replaceAll("[^.\\d]", "").split("\\.");
                textFieldCharges.setText(decimalFormat.format(Double.parseDouble(temp[0] + "." + temp[1])));
            }
            if (chckbxRoundOff.isSelected()) {
                textFieldRoundOff.setText(decimalFormat.format(-1 * Double.parseDouble(0 + textFieldCharges.getText()) % Math.pow(10, Double.parseDouble(textFieldRoundOffDecimals.getText().replaceAll("\\D", "")))).replaceAll("-0", "0"));
                textFieldCharges.setText(decimalFormat.format(Double.parseDouble(0 + textFieldCharges.getText()) + Double.parseDouble(textFieldRoundOff.getText())));
            }

            textFieldNoOfBags.setText(Integer.toString(Integer.parseInt(0 + textFieldNoOfBags.getText().replaceAll("\\D", ""))));

            if (chckbxIceWater.isSelected() && Integer.parseInt(textFieldNetWt.getText()) > 0) {
                textFieldFinalWt.setText(Integer.toString(Integer.parseInt(textFieldNetWt.getText()) - Integer.parseInt(0 + textFieldDeductionOrPerCost.getText().replaceAll("\\D", ""))));
                textFieldFinalAmount.setText(Integer.toString((int) (Integer.parseInt(textFieldFinalWt.getText()) * Double.parseDouble(0 + textFieldCharges.getText().replaceAll("[^.\\d]", ""))) - Integer.parseInt(0 + textFieldNoOfBags.getText().replaceAll("\\D", ""))));
            }
            textFieldNetDateTime.setText(textFieldDateTime.getText());
            comboBoxCustomerName.setEnabled(false);
            comboBoxTransporterName.setEnabled(false);
            rdbtnGross.setEnabled(false);
            btnGetTareSl.setEnabled(false);
            rdbtnTare.setEnabled(false);
            btnGetGrossSl.setEnabled(false);
            comboBoxVehicleNo.setEnabled(false);
            comboBoxMaterial.setEnabled(false);
            textFieldNoOfBags.setEnabled(false);
            textFieldCharges.setEnabled(false);
            textFieldDeductionOrPerCost.setEnabled(false);
            btnSave.setEnabled(true);
            textFieldPlace.setEnabled(false);
            textFieldPhoneNo.setEnabled(false);
            btnGetDcDetails.setEnabled(false);
            btnGetWeight.setEnabled(false);
            btnGetGross.setEnabled(false);
            btnGetTare.setEnabled(false);
            chckbxAutoChargecheck.setEnabled(false);
            btnTotal.setEnabled(false);
            btnMinusGross.setEnabled(false);
            btnPlusTare.setEnabled(false);
            textPaneRemarks.setEnabled(false);
            chckbxIsCredit.setEnabled(false);
            btnSave.requestFocus();
        });
        btnGetWeight.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetWeight.setBounds(33, 515, 162, 25);
        panelWeighing.add(btnGetWeight);

        btnSave = new JButton("Save");
        btnSave.addActionListener(this::onSave);
        btnSave.setEnabled(false);
        btnSave.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnSave.setBounds(245, 515, 150, 25);
        panelWeighing.add(btnSave);

        btnPrint = new JButton("Print");
        btnPrint.addActionListener(_ -> {
            try {
                boolean skipPrint = false;

                if (chckbxTareToken.isSelected() && textFieldGrossWt.getText().equals("0")) {
                    switch (JOptionPane.showOptionDialog(null, "Do you want to print token ?", "Print Token", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{
                                    "Print Token & skip further print",
                                    "Print Token",
                                    "Cancel"
                            },
                            null)) {
                        case 0:
                            skipPrint = true;
                        case 1:
                            printStandardToken();
                    }
                }
                if (!skipPrint) {
                    if (chckbxPrinterCopyDialog.isSelected()) {
                        JSpinner spinner = new JSpinner(new SpinnerNumberModel(noOfCopies, 0, 100, 1));
                        valueEntered = false;
                        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(final KeyEvent e) {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    valueEntered = true;
                                    JOptionPane.getRootFrame().dispose();
                                }
                            }
                        });
                        JPanel panel = new JPanel();
                        String[] ConnectOptionNames = {
                                "Print",
                                "Cancel"
                        };
                        panel.add(new JLabel("No of Copies ?"));
                        panel.add(spinner);

                        if (JOptionPane.showOptionDialog(null, panel, "Print ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null) != JOptionPane.YES_OPTION && !valueEntered) {
                            skipPrint = true;
                        } else {
                            noOfCopies = (Integer) spinner.getValue();
                        }
                    } else {
                        if (JOptionPane.showConfirmDialog(null, "Do you want to Print ?", "Print", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                            noOfCopies = Integer.parseInt(textFieldNoOfCopies.getText());
                        } else {
                            skipPrint = true;
                        }
                    }
                }
                if (!skipPrint) {
                    print();
                }
                if (chckbxExitPass.isSelected() && !textFieldNetWt.getText().equals("0")) {
                    if (JOptionPane.showOptionDialog(null, "Do you want to print Exit Pass ?", "Print Token", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{
                                    "Print Exit Pass",
                                    "Cancel"
                            },
                            null) == JOptionPane.YES_OPTION) {
                        printStandardExitPass();
                    }
                }
                if (chckbxSms.isSelected()) {
                    String mobileNumber = JOptionPane.showInputDialog(null, "Please Enter the Phone No ?", "SMS", JOptionPane.INFORMATION_MESSAGE);
                    if (mobileNumber != null) {
                        sentSMS(mobileNumber);
                    }
                }
                clear();
            } catch (NullPointerException ignored) {
                JOptionPane.showMessageDialog(null, "Print ERROR\nPlease Use another Printer Option", "Print ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPrint.setEnabled(false);
        btnPrint.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnPrint.setBounds(415, 515, 150, 25);
        panelWeighing.add(btnPrint);

        btnReprint = new JButton("RePrint");
        btnReprint.setFocusable(false);
        btnReprint.addActionListener(_ -> {
            String response = JOptionPane.showInputDialog(null, "Please Enter the Sl.no to Reprint ?", "Reprint", JOptionPane.QUESTION_MESSAGE);
            if (response != null) {
                rePrint(response);
            }
        });
        btnReprint.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnReprint.setBounds(245, 565, 150, 25);
        panelWeighing.add(btnReprint);

        JButton btnClear = new JButton("Clear");
        btnClear.setFocusable(false);
        btnClear.addActionListener(this::clear);
        btnClear.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnClear.setBounds(415, 565, 150, 25);
        panelWeighing.add(btnClear);
        try {
            JLabel contact = new JLabel(new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResource("/contact.bmp")))));
            contact.setBounds(945, 505, 300, 100);
            panelWeighing.add(contact);
        } catch (IOException ignored) {
        }

        JLabel lblKg = new JLabel("Kg");
        lblKg.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblKg.setBounds(726, 310, 25, 25);
        panelWeighing.add(lblKg);

        JLabel label = new JLabel("Kg");
        label.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label.setBounds(729, 350, 25, 25);
        panelWeighing.add(label);

        JLabel label_1 = new JLabel("Kg");
        label_1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label_1.setBounds(729, 430, 25, 25);
        panelWeighing.add(label_1);

        JLabel lblCustomerName = new JLabel("Customer's Name");
        lblCustomerName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblCustomerName.setBounds(50, 150, 175, 25);
        panelWeighing.add(lblCustomerName);

        comboBoxTransporterName = new JComboBox<>();
        comboBoxTransporterName.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                comboBoxTransporterName.setSelectedItem(Objects.toString(comboBoxTransporterName.getSelectedItem(), "").toUpperCase().trim());
            }
        });
        comboBoxTransporterName.addActionListener(l -> {
            if (l.getActionCommand().equals("comboBoxEdited")) {
                requestFocus("DriverName");
            }
        });
        comboBoxTransporterName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBoxTransporterName.setEditable(true);
        AutoCompleteDecorator.decorate(comboBoxTransporterName);
        comboBoxTransporterName.setBounds(775, 190, 175, 25);
        panelWeighing.add(comboBoxTransporterName);

        JLabel lblDriversName = new JLabel("Transporter's Name");
        lblDriversName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDriversName.setBounds(475, 190, 175, 25);
        panelWeighing.add(lblDriversName);

        JLabel lblDcNo = new JLabel("Dc. No");
        lblDcNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDcNo.setBounds(475, 230, 75, 25);
        panelWeighing.add(lblDcNo);

        textFieldDcNo = new JTextField();
        textFieldDcNo.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldDcNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldDcNo.setEnabled(false);
        textFieldDcNo.setDisabledTextColor(Color.BLACK);
        textFieldDcNo.setColumns(10);
        textFieldDcNo.setBounds(619, 230, 146, 25);
        panelWeighing.add(textFieldDcNo);

        textFieldDcDate = new JTextField();
        textFieldDcDate.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldDcDate.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textFieldDcDate.setEnabled(false);
        textFieldDcDate.setDisabledTextColor(Color.BLACK);
        textFieldDcDate.setColumns(10);
        textFieldDcDate.setBounds(775, 230, 175, 25);
        panelWeighing.add(textFieldDcDate);

        btnGetDcDetails = new JButton("Get Dc. Details");
        btnGetDcDetails.setFocusable(false);
        btnGetDcDetails.addActionListener(_ -> {
            String[] ConnectOptionNames = {
                    "Set Dc. No",
                    "Clear",
                    "Cancel"
            };
            JTextField jTextField = new JTextField(10);
            valueEntered = false;
            jTextField.addActionListener(_ -> {
                valueEntered = true;
                JOptionPane.getRootFrame().dispose();
            });
            JXDatePicker datePicker = new JXDatePicker();
            datePicker.setFormats("dd-MM-yyyy");
            datePicker.setDate(new Date());
            datePicker.getEditor().setEditable(false);
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Dc. No "));
            panel.add(jTextField);
            panel.add(new JLabel("Dc. Date "));
            panel.add(datePicker);
            int response = JOptionPane.showOptionDialog(null, panel, "Enter Dc. No ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, "");
            if (response == JOptionPane.YES_OPTION || valueEntered) {
                try {
                    textFieldDcNo.setText(jTextField.getText().trim());
                    Date dateTemp = datePicker.getDate();
                    textFieldDcDate.setText(dateAndTimeFormatdate.format(dateTemp));
                } catch (NumberFormatException ignored) {
                    JOptionPane.showMessageDialog(null, "Plz check the Value Entered\n\nLINE :1485", "Value ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else if (response == JOptionPane.NO_OPTION) {
                textFieldDcNo.setText("");
                textFieldDcDate.setText("");
            }
        });
        btnGetDcDetails.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetDcDetails.setBounds(990, 230, 225, 25);
        panelWeighing.add(btnGetDcDetails);

        btnClick = new JButton("Click");
        btnClick.addActionListener(_ -> {
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
            if (checkBoxCamera1.isSelected()) {
                try {
                    Runnable stuffToDo = new Thread(() -> {
                        BufferedImage previewImage = webcam[1].getImage();
                        BufferedImage cropImage = previewImage.getSubimage(
                                Integer.parseInt(0 + textFieldCropX11.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropY11.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropWidth11.getText().replaceAll("\\D", "")), Integer.parseInt("0" + textFieldCropHeight11.getText().replaceAll("\\D", "")));
                        JLabel jLabel = new JLabel(
                                new ImageIcon(cropImage.getScaledInstance(1280, 768, Image.SCALE_DEFAULT)));
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
                    try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
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
                } catch (NullPointerException ignored) {
                }
            }

        });
        btnClick.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnClick.setFocusable(false);
        btnClick.setBounds(120, 565, 75, 25);
        panelWeighing.add(btnClick);

        JButton btnCalc = new JButton("Calc");
        btnCalc.addActionListener(_ -> {
            if (calc == null) {
                calc = new Calculator();
                calc.setTitle("Calculator");
                calc.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                calc.pack();
                calc.setLocation(50, 50);
                calc.setVisible(true);
                calc.setResizable(false);
                calc.setAlwaysOnTop(true);
            } else {
                calc.setVisible(true);
            }
            calc.setState(Frame.NORMAL);
        });
        btnCalc.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnCalc.setFocusable(false);
        btnCalc.setBounds(33, 565, 75, 25);
        panelWeighing.add(btnCalc);

        btnMinusGross = new JButton("-");
        btnMinusGross.addActionListener(_ -> {
            rdbtnTare.setSelected(true);
            Object[] options = {
                    "New",
                    "Ok",
                    "Cancel"
            };
            JPanel panel = new JPanel();
            panel.add(new JLabel("Please Enter the Sl.no To Get Tare Wt ?"));
            JTextField text = new JTextField(10);
            panel.add(text);
            int response = JOptionPane.showOptionDialog(null, panel, "Get Gross Sl No", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            String result = text.getText();
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException ignored) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :806", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (response == JOptionPane.YES_OPTION) {
                rdbtnGross.setSelected(true);
            } else if (!(result == null || (result.isEmpty()) || Integer.parseInt(result) >= serialNo || Integer.parseInt(result) <= 0 || response != JOptionPane.NO_OPTION)) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + result);
                    rs.next();
                    textFieldDcNo.setText(rs.getString("DCNO"));
                    textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                    comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    comboBoxTransporterName.setSelectedItem(rs.getString("DRIVERNAME"));
                    comboBoxVehicleNo.setSelectedItem(rs.getString("VEHICLENO"));
                    textFieldPlace.setText(rs.getString("PLACE"));
                    textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                    chckbxIsCredit.setSelected(rs.getBoolean("CREDIT"));
                    textFieldNoOfBags.setText(Integer.toString(rs.getInt("NOOFBAGS")));
                    textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")));
                    textFieldGrossWt.setText(Integer.toString(rs.getInt("TAREWT")));
                    if (textFieldGrossWt.getText().equals("0")) {
                        textFieldGrossWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                        textFieldGrossDateTime.setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));

                    } else {
                        textFieldGrossDateTime.setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                    }
                    if (textFieldGrossDateTime.getText().equals("null null")) {
                        textFieldGrossDateTime.setText("");
                    } else {
                        textFieldGrossDateTime.setText(dateAndTimeFormat.format(
                                new Date(dateAndTimeFormatSql.parse(textFieldGrossDateTime.getText()).getTime())));
                    }
                } catch (SQLException | ParseException ignored) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :820", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                comboBoxVehicleNo.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                comboBoxMaterial.setEnabled(true);
                comboBoxMaterial.setSelectedItem("");
                requestFocus("");
            }
        });
        btnMinusGross.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnMinusGross.setFocusable(false);
        btnMinusGross.setBounds(369, 54, 62, 25);
        panelWeighing.add(btnMinusGross);

        btnPlusTare = new JButton("+");
        btnPlusTare.addActionListener(_ -> {
            rdbtnGross.setSelected(true);
            Object[] options = {
                    "New",
                    "Ok",
                    "Cancel"
            };
            JPanel panel = new JPanel();
            panel.add(new JLabel("Please Enter the Sl.no To Get Tare Wt ?"));
            JTextField text = new JTextField(10);

            panel.add(text);

            int response = JOptionPane.showOptionDialog(null, panel, "Get Gross Sl No", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

            String result = text.getText();
            int serialNo = 0;
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } catch (SQLException ignored) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :847", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (response == JOptionPane.YES_OPTION) {
                rdbtnTare.setSelected(true);
            } else if (!(result == null || (result.isEmpty()) || Integer.parseInt(result) >= serialNo || Integer.parseInt(result) <= 0 || response != JOptionPane.NO_OPTION)) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + result);
                    rs.next();
                    textFieldDcNo.setText(rs.getString("DCNO"));
                    textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                    comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                    comboBoxTransporterName.setSelectedItem(rs.getString("DRIVERNAME"));
                    comboBoxVehicleNo.setSelectedItem(rs.getString("VEHICLENO"));
                    textFieldPlace.setText(rs.getString("PLACE"));
                    textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                    chckbxIsCredit.setSelected(rs.getBoolean("CREDIT"));
                    textFieldNoOfBags.setText(Integer.toString(rs.getInt("NOOFBAGS")));
                    textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")));
                    textFieldTareWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                    if (textFieldTareWt.getText().equals("0")) {
                        textFieldTareWt.setText(Integer.toString(rs.getInt("TAREWT")));
                        textFieldTareDateTime.setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                    } else {
                        textFieldTareDateTime.setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));
                    }
                    if (textFieldTareDateTime.getText().equals("null null")) {
                        textFieldTareDateTime.setText("");
                    } else {
                        textFieldTareDateTime.setText(dateAndTimeFormat.format(
                                new Date(dateAndTimeFormatSql.parse(textFieldTareDateTime.getText()).getTime())));
                    }
                } catch (SQLException | ParseException ignored) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :861", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                comboBoxVehicleNo.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                comboBoxMaterial.setEnabled(true);
                comboBoxMaterial.setSelectedItem("");
                requestFocus("");
            }
        });
        btnPlusTare.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnPlusTare.setFocusable(false);
        btnPlusTare.setBounds(369, 25, 62, 25);
        panelWeighing.add(btnPlusTare);

        JLabel lblRemarks = new JLabel("Remarks");
        lblRemarks.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblRemarks.setBounds(50, 430, 100, 25);
        panelWeighing.add(lblRemarks);

        chckbxAutoChargecheck = new JCheckBox("Auto");
        chckbxAutoChargecheck.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        chckbxAutoChargecheck.setFocusable(false);
        chckbxAutoChargecheck.setBackground(new Color(0, 255, 127));
        chckbxAutoChargecheck.setBounds(415, 390, 57, 25);
        chckbxAutoChargecheck.addChangeListener(_ -> {
            if (btnGetWeight.isEnabled()) {
                textFieldCharges.setEnabled(!chckbxAutoChargecheck.isSelected());
            }
        });
        panelWeighing.add(chckbxAutoChargecheck);

        JLabel lblNoOfBags = new JLabel("No Of Bags");
        lblNoOfBags.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblNoOfBags.setBounds(50, 350, 175, 25);
        panelWeighing.add(lblNoOfBags);

        textFieldNoOfBags = new JTextField();
        textFieldNoOfBags.addActionListener(_ -> requestFocus("NoOfBags"));
        textFieldNoOfBags.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNoOfBags.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNoOfBags.setDisabledTextColor(Color.BLACK);
        textFieldNoOfBags.setColumns(10);
        textFieldNoOfBags.setBounds(240, 350, 175, 25);
        panelWeighing.add(textFieldNoOfBags);

        textPaneRemarks = new JTextPane(new DefaultStyledDocument() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if ((getLength() + str.length()) <= 100) {
                    super.insertString(offs, str, a);
                } else {
                    JOptionPane.showMessageDialog(null, "LIMIT REACHED\nLimit is 100 character\nPlease cut shot your Remarks\nLINE :2606", "LIMIT REACHED", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        textPaneRemarks.setBounds(242, 427, 173, 48);
        panelWeighing.add(textPaneRemarks);
        textPaneRemarks.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    requestFocus("Remarks");
                }
            }
        });
        textPaneRemarks.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textPaneRemarks.setDisabledTextColor(Color.BLACK);

        JLabel lblBagDeductionOrReductionCost = new JLabel("Bag Deduction");
        lblBagDeductionOrReductionCost.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblBagDeductionOrReductionCost.setBounds(475, 390, 141, 25);
        panelWeighing.add(lblBagDeductionOrReductionCost);

        textFieldDeductionOrPerCost = new JTextField();
        textFieldDeductionOrPerCost.addActionListener(_ -> requestFocus("DeductionOrPerCost"));
        textFieldDeductionOrPerCost.setText("0");
        textFieldDeductionOrPerCost.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldDeductionOrPerCost.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldDeductionOrPerCost.setEnabled(false);
        textFieldDeductionOrPerCost.setDisabledTextColor(Color.BLACK);
        textFieldDeductionOrPerCost.setColumns(10);
        textFieldDeductionOrPerCost.setBounds(619, 390, 100, 25);
        panelWeighing.add(textFieldDeductionOrPerCost);

        JLabel label_5 = new JLabel("Kg");
        label_5.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label_5.setBounds(729, 390, 25, 25);
        panelWeighing.add(label_5);

        JLabel lblFinalWt = new JLabel("Final Wt");
        lblFinalWt.setVisible(false);
        lblFinalWt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblFinalWt.setBounds(475, 468, 75, 25);
        panelWeighing.add(lblFinalWt);

        textFieldFinalWt = new JTextField();
        textFieldFinalWt.setVisible(false);
        textFieldFinalWt.setText("0");
        textFieldFinalWt.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldFinalWt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldFinalWt.setEnabled(false);
        textFieldFinalWt.setDisabledTextColor(Color.BLACK);
        textFieldFinalWt.setColumns(10);
        textFieldFinalWt.setBounds(619, 468, 100, 25);
        panelWeighing.add(textFieldFinalWt);

        JLabel label_6 = new JLabel("Kg");
        label_6.setVisible(false);
        label_6.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label_6.setBounds(729, 468, 25, 25);
        panelWeighing.add(label_6);

        textFieldFinalAmount = new JTextField();
        textFieldFinalAmount.setText("0");
        textFieldFinalAmount.setVisible(false);
        textFieldFinalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldFinalAmount.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldFinalAmount.setEnabled(false);
        textFieldFinalAmount.setDisabledTextColor(Color.BLACK);
        textFieldFinalAmount.setColumns(10);
        textFieldFinalAmount.setBounds(775, 468, 175, 25);
        panelWeighing.add(textFieldFinalAmount);

        JLabel lblPlaceAndPhoneNumber = new JLabel("Place & Phone#");
        lblPlaceAndPhoneNumber.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblPlaceAndPhoneNumber.setBounds(475, 270, 141, 25);
        panelWeighing.add(lblPlaceAndPhoneNumber);

        textFieldPlace = new JTextField();
        textFieldPlace.addActionListener(_ -> requestFocus("Place"));
        textFieldPlace.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPlace.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldPlace.setDisabledTextColor(Color.BLACK);
        textFieldPlace.setColumns(10);
        textFieldPlace.setBounds(619, 270, 146, 25);
        panelWeighing.add(textFieldPlace);

        textFieldPhoneNo = new JTextField();
        textFieldPhoneNo.addActionListener(_ -> requestFocus("PhoneNo"));
        textFieldPhoneNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPhoneNo.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textFieldPhoneNo.setDisabledTextColor(Color.BLACK);
        textFieldPhoneNo.setColumns(10);
        textFieldPhoneNo.setBounds(775, 270, 175, 25);
        panelWeighing.add(textFieldPhoneNo);

        textFieldRoundOff = new JTextField();
        textFieldRoundOff.setText("0");
        textFieldRoundOff.setEnabled(false);
        textFieldRoundOff.setVisible(false);
        textFieldRoundOff.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldRoundOff.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldRoundOff.setDisabledTextColor(Color.BLACK);
        textFieldRoundOff.setColumns(10);
        textFieldRoundOff.setBounds(775, 390, 175, 25);
        panelWeighing.add(textFieldRoundOff);

        comboBoxVehicleNo = new JComboBox<>();
        comboBoxVehicleNo.setEditable(true);
        AutoCompleteDecorator.decorate(comboBoxVehicleNo);
        comboBoxVehicleNo.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                comboBoxVehicleNo.setSelectedItem(Objects.toString(comboBoxVehicleNo.getSelectedItem(), "").toUpperCase().replaceAll(" ", ""));
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLETARES WHERE VEHICLENO LIKE '" + comboBoxVehicleNo.getEditor().getItem() + "'");
                    if (rs.next()) {
                        textFieldPlace.setText(rs.getString("PLACE"));
                        textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                    }
                } catch (SQLException ignored) {
                }
            }
        });
        comboBoxVehicleNo.addActionListener(l -> {
            if (l.getActionCommand().equals("comboBoxEdited")) {
                comboBoxVehicleNo.setSelectedItem(((String) comboBoxVehicleNo.getEditor().getItem()).toUpperCase().replaceAll(" ", ""));
                if (!chckbxTareNoSlno.isSelected()) {
                    if (rdbtnGross.isSelected()) {
                        try {
                            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLETARES WHERE VEHICLENO LIKE '" + comboBoxVehicleNo.getEditor().getItem() + "'");
                            if (rs.next()) {
                                if (JOptionPane.showConfirmDialog(null, "Please Select Yes to Enter the Stored tare Weight ?", "Tare Weight Available", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                    textFieldTareDateTime.setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                                    if (textFieldTareDateTime.getText().equals("null null")) {
                                        textFieldTareDateTime.setText("");
                                    } else {
                                        textFieldTareDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldTareDateTime.getText()).getTime())));
                                    }
                                    textFieldTareWt.setText(Integer.toString(rs.getInt("TAREWT")));
                                    textFieldPlace.setText(rs.getString("PLACE"));
                                    textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                                }
                            }
                        } catch (SQLException | ParseException ignored) {
                            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :680", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        try {
                            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE VEHICLENO LIKE '" + comboBoxVehicleNo.getEditor().getItem() + "'");
                            if (rs.last()) {
                                if (rs.getInt("TAREWT") == 0) {
                                    if (JOptionPane.showConfirmDialog(null, "Please Select Yes to Enter the last gross Weight ?", "Gross Weight Available", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                        textFieldDcNo.setText(rs.getString("DCNO"));
                                        textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                                        comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                                        comboBoxTransporterName.setSelectedItem(rs.getString("DRIVERNAME"));
                                        textFieldPlace.setText(rs.getString("PLACE"));
                                        textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                                        chckbxIsCredit.setSelected(rs.getBoolean("CREDIT"));
                                        textFieldNoOfBags.setText(Integer.toString(rs.getInt("NOOFBAGS")));
                                        textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")));
                                        textFieldGrossDateTime.setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));
                                        if (textFieldGrossDateTime.getText().equals("null null")) {
                                            textFieldGrossDateTime.setText("");
                                        } else {
                                            textFieldGrossDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldGrossDateTime.getText()).getTime())));
                                        }
                                        textFieldGrossWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                                        comboBoxMaterial.setSelectedItem(rs.getString("MATERIAL"));
                                    }
                                }
                            }
                        } catch (SQLException | ParseException ignored) {
                            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :680", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    if (rdbtnTare.isSelected()) {
                        try {
                            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE VEHICLENO LIKE '" + comboBoxVehicleNo.getEditor().getItem() + "'");
                            if (rs.last()) {
                                if (rs.getInt("TAREWT") == 0) {
                                    if (JOptionPane.showConfirmDialog(null, "Please Select Yes to Enter the last gross Weight ?", "Gross Weight Available", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                        textFieldDcNo.setText(rs.getString("DCNO"));
                                        textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                                        comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                                        comboBoxTransporterName.setSelectedItem(rs.getString("DRIVERNAME"));
                                        textFieldPlace.setText(rs.getString("PLACE"));
                                        textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                                        chckbxIsCredit.setSelected(rs.getBoolean("CREDIT"));
                                        textFieldNoOfBags.setText(Integer.toString(rs.getInt("NOOFBAGS")));
                                        textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")));
                                        textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
                                        textFieldGrossDateTime.setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));
                                        if (textFieldGrossDateTime.getText().equals("null null")) {
                                            textFieldGrossDateTime.setText("");
                                        } else {
                                            textFieldGrossDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldGrossDateTime.getText()).getTime())));
                                        }
                                        textFieldGrossWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                                        comboBoxMaterial.setSelectedItem(rs.getString("MATERIAL"));
                                    }
                                }
                            }
                        } catch (SQLException | ParseException ignored) {
                            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :680", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        try {
                            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE VEHICLENO LIKE '" + comboBoxVehicleNo.getEditor().getItem() + "'");
                            if (rs.last()) {
                                if (rs.getInt("GROSSWT") == 0) {
                                    if (JOptionPane.showConfirmDialog(null, "Please Select Yes to Enter the last tare Weight ?", "Tare Weight Available", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                        textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
                                        textFieldDcNo.setText(rs.getString("DCNO"));
                                        textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                                        comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                                        comboBoxTransporterName.setSelectedItem(rs.getString("DRIVERNAME"));
                                        comboBoxVehicleNo.setSelectedItem(rs.getString("VEHICLENO"));
                                        textFieldPlace.setText(rs.getString("PLACE"));
                                        textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                                        chckbxIsCredit.setSelected(rs.getBoolean("CREDIT"));
                                        textFieldNoOfBags.setText(Integer.toString(rs.getInt("NOOFBAGS")));
                                        textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")));
                                        textFieldTareDateTime.setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                                        if (textFieldTareDateTime.getText().equals("null null")) {
                                            textFieldTareDateTime.setText("");
                                        } else {
                                            textFieldTareDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldTareDateTime.getText()).getTime())));
                                        }
                                        textFieldTareWt.setText(Integer.toString(rs.getInt("TAREWT")));
                                        comboBoxMaterial.setSelectedItem(rs.getString("MATERIAL").equals("EMPTY") ? "" : rs.getString("MATERIAL"));
                                    }
                                }
                            }
                        } catch (SQLException | ParseException ignored) {
                            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :680", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
                requestFocus("VehicleNo");
            }
        });
        comboBoxVehicleNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxVehicleNo.setBounds(240, 230, 175, 25);
        panelWeighing.add(comboBoxVehicleNo);

        chckbxIsCredit = new JCheckBox("Credit");
        chckbxIsCredit.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        chckbxIsCredit.setFocusable(false);
        chckbxIsCredit.setBackground(new Color(0, 255, 127));
        chckbxIsCredit.setBounds(146, 393, 75, 20);
        panelWeighing.add(chckbxIsCredit);

        btnPrintToken = new JButton("Print Token");
        btnPrintToken.setVisible(false);
        btnPrintToken.addActionListener(this::printStandardToken);
        btnPrintToken.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnPrintToken.setEnabled(false);
        btnPrintToken.setBounds(586, 515, 165, 25);
        panelWeighing.add(btnPrintToken);

        btnPrintExitPass = new JButton("Print Exit Pass");
        btnPrintExitPass.setVisible(false);
        btnPrintExitPass.addActionListener(this::printStandardExitPass);
        btnPrintExitPass.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnPrintExitPass.setEnabled(false);
        btnPrintExitPass.setBounds(586, 565, 165, 25);
        panelWeighing.add(btnPrintExitPass);

        JLabel lblVehicleType = new JLabel("Vehicle Type");
        lblVehicleType.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblVehicleType.setBounds(50, 270, 175, 25);
        panelWeighing.add(lblVehicleType);

        comboBoxVehicleType = new JComboBox<>();
        comboBoxVehicleType.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                comboBoxVehicleType.setSelectedItem(Objects.toString(comboBoxVehicleType.getSelectedItem(), "").toUpperCase().trim());
            }
        });
        comboBoxVehicleType.addActionListener(l -> {
            if (l.getActionCommand().equals("comboBoxEdited")) {
                requestFocus("VehicleType");
            }
        });
        comboBoxVehicleType.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxVehicleType.setEditable(true);
        comboBoxVehicleType.setBounds(240, 270, 175, 25);
        panelWeighing.add(comboBoxVehicleType);

        lblOperatorName = new JLabel("");
        lblOperatorName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblOperatorName.setBounds(775, 565, 175, 25);
        panelWeighing.add(lblOperatorName);

        panelCameras = new JPanel();
        panelCameras.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("          Cameras          ", panelCameras);
        panelCameras.setLayout(null);

        checkBoxCamera1 = new JCheckBox("");
        checkBoxCamera1.addActionListener(_ -> {
            if (checkBoxCamera1.isSelected()) {
                butttonUpdateCamera1.setEnabled(true);
                panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1, textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 0);
            } else {
                butttonUpdateCamera1.setSelected(false);
                butttonUpdateCamera1.setEnabled(false);
                if (butttonUpdateCamera1.getText().equals("Lock")) {
                    butttonUpdateCamera1.doClick();
                }
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
        webcamPicker1.addItemListener(_ -> {
            if (checkBoxCamera1.isSelected()) {
                panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1, textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 0);
            }
        });
        webcamPicker1.setBounds(41, 258, 270, 25);
        panelCameras.add(webcamPicker1);

        comboBoxResolution1 = new JComboBox<>();
        comboBoxResolution1.setEnabled(false);
        comboBoxResolution1.addActionListener(_ -> {
            if (lock) {
                panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1, textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 1);
            }
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
        webcamPicker2.addItemListener(_ -> {
            if (checkBoxCamera2.isSelected()) {
                panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2, textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 0);
            }
        });

        butttonUpdateCamera1 = new JButton("Unlock");
        butttonUpdateCamera1.setEnabled(false);
        butttonUpdateCamera1.addActionListener(_ -> {
            if (checkBoxCamera1.isSelected()) {
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
        comboBoxResolution2.addActionListener(_ -> {
            if (lock) {
                panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2, textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 1);
            }
        });
        comboBoxResolution2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBoxResolution2.setFocusable(false);
        comboBoxResolution2.setBounds(710, 281, 208, 25);
        panelCameras.add(comboBoxResolution2);

        checkBoxCamera2 = new JCheckBox("");
        checkBoxCamera2.addActionListener(_ -> {
            if (checkBoxCamera2.isSelected()) {
                butttonUpdateCamera2.setEnabled(true);
                panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2, textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 0);
            } else {
                butttonUpdateCamera2.setSelected(false);
                butttonUpdateCamera2.setEnabled(false);
                if (butttonUpdateCamera2.getText().equals("Lock")) {
                    butttonUpdateCamera2.doClick();
                }
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
        checkBoxCamera3.addActionListener(_ -> {
            if (checkBoxCamera3.isSelected()) {
                butttonUpdateCamera3.setEnabled(true);
                panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3, textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 0);
            } else {
                butttonUpdateCamera3.setSelected(false);
                butttonUpdateCamera3.setEnabled(false);
                if (butttonUpdateCamera3.getText().equals("Lock")) {
                    butttonUpdateCamera3.doClick();
                }
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
        butttonUpdateCamera2.addActionListener(_ -> {
            if (checkBoxCamera2.isSelected()) {
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
        webcamPicker3.addItemListener(_ -> {
            if (checkBoxCamera3.isSelected()) {
                panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3, textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 0);
            }
        });
        webcamPicker3.setBounds(41, 557, 270, 25);
        panelCameras.add(webcamPicker3);

        comboBoxResolution3 = new JComboBox<>();
        comboBoxResolution3.setEnabled(false);
        comboBoxResolution3.addActionListener(_ -> {
            if (lock) {
                panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3, textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 1);
            }
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
        checkBoxCamera4.addActionListener(_ -> {
            if (checkBoxCamera4.isSelected()) {
                butttonUpdateCamera4.setEnabled(true);
                panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4, textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 0);
            } else {
                butttonUpdateCamera4.setSelected(false);
                butttonUpdateCamera4.setEnabled(false);
                if (butttonUpdateCamera4.getText().equals("Lock")) {
                    butttonUpdateCamera4.doClick();
                }
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
        butttonUpdateCamera3.addActionListener(_ -> {
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
        webcamPicker4.addItemListener(_ -> {
            if (checkBoxCamera4.isSelected()) {
                panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4, textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 0);
            }
        });
        webcamPicker4.setBounds(648, 557, 270, 25);
        panelCameras.add(webcamPicker4);

        comboBoxResolution4 = new JComboBox<>();
        comboBoxResolution4.setEnabled(false);
        comboBoxResolution4.addActionListener(_ -> {
            if (lock) {
                panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4, textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 1);
            }
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
        buttonUnLockCamera.addActionListener(_ -> {
            if (Objects.equals(buttonUnLockCamera.getText(), "Unlock")) {
                JPasswordField password = new JPasswordField(10);
                password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {
                        "Enter",
                        "Cancel"
                };
                panel.add(new JLabel("Please the Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = CAMERA_PASSWORD.toCharArray();
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
                    buttonUnLockCamera.setText("Lock");
                }
            } else {
                butttonUpdateCamera1.setEnabled(false);
                butttonUpdateCamera2.setEnabled(false);
                butttonUpdateCamera3.setEnabled(false);
                butttonUpdateCamera4.setEnabled(false);
                checkBoxCamera1.setEnabled(false);
                if (butttonUpdateCamera1.getText().equals("Lock")) {
                    butttonUpdateCamera1.doClick();
                }
                checkBoxCamera2.setEnabled(false);
                if (butttonUpdateCamera2.getText().equals("Lock")) {
                    butttonUpdateCamera2.doClick();
                }
                checkBoxCamera3.setEnabled(false);
                if (butttonUpdateCamera3.getText().equals("Lock")) {
                    butttonUpdateCamera3.doClick();
                }
                checkBoxCamera4.setEnabled(false);
                if (butttonUpdateCamera4.getText().equals("Lock")) {
                    butttonUpdateCamera4.doClick();
                }
                buttonUnLockCamera.setText("Unlock");
            }
        });

        butttonUpdateCamera4 = new JButton("Unlock");
        butttonUpdateCamera4.setEnabled(false);
        butttonUpdateCamera4.addActionListener(_ -> {
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
        butttonUpdateCamera.addActionListener(_ -> {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM CAMERA");
                rs.absolute(1);
                rs.updateBoolean("ENABLE", checkBoxCamera1.isSelected());
                rs.updateString("NAME", webcamPicker1.getSelectedWebcam().toString());
                rs.updateString("RESOLUTION", comboBoxResolution1.getSelectedItem() != null ? comboBoxResolution1.getSelectedItem().toString() : "");
                rs.updateInt("CROPX", Integer.parseInt(0 + textFieldCropX1.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPY", Integer.parseInt(0 + textFieldCropY1.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPWIDTH", Integer.parseInt(0 + textFieldCropWidth1.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPHEIGHT", Integer.parseInt(0 + textFieldCropHeight1.getText().replaceAll("\\D", "")));
                rs.updateRow();

                rs.absolute(2);
                rs.updateBoolean("ENABLE", checkBoxCamera2.isSelected());
                rs.updateString("NAME", webcamPicker2.getSelectedWebcam().toString());
                rs.updateString("RESOLUTION", comboBoxResolution2.getSelectedItem() != null ? comboBoxResolution2.getSelectedItem().toString() : "");
                rs.updateInt("CROPX", Integer.parseInt(0 + textFieldCropX2.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPY", Integer.parseInt(0 + textFieldCropY2.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPWIDTH", Integer.parseInt(0 + textFieldCropWidth2.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPHEIGHT", Integer.parseInt(0 + textFieldCropHeight2.getText().replaceAll("\\D", "")));
                rs.updateRow();

                rs.absolute(3);
                rs.updateBoolean("ENABLE", checkBoxCamera3.isSelected());
                rs.updateString("NAME", webcamPicker3.getSelectedWebcam().toString());
                rs.updateString("RESOLUTION", comboBoxResolution3.getSelectedItem() != null ? comboBoxResolution3.getSelectedItem().toString() : "");
                rs.updateInt("CROPX", Integer.parseInt(0 + textFieldCropX3.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPY", Integer.parseInt(0 + textFieldCropY3.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPWIDTH", Integer.parseInt(0 + textFieldCropWidth3.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPHEIGHT", Integer.parseInt(0 + textFieldCropHeight3.getText().replaceAll("\\D", "")));
                rs.updateRow();

                rs.absolute(4);
                rs.updateBoolean("ENABLE", checkBoxCamera4.isSelected());
                rs.updateString("NAME", webcamPicker4.getSelectedWebcam().toString());
                rs.updateString("RESOLUTION", comboBoxResolution4.getSelectedItem() != null ? comboBoxResolution4.getSelectedItem().toString() : "");
                rs.updateInt("CROPX", Integer.parseInt(0 + textFieldCropX4.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPY", Integer.parseInt(0 + textFieldCropY4.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPWIDTH", Integer.parseInt(0 + textFieldCropWidth4.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPHEIGHT", Integer.parseInt(0 + textFieldCropHeight4.getText().replaceAll("\\D", "")));
                rs.updateRow();

                rs.absolute(5);
                rs.updateInt("CROPX", Integer.parseInt(0 + textFieldCropX11.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPY", Integer.parseInt(0 + textFieldCropY11.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPWIDTH", Integer.parseInt(0 + textFieldCropWidth11.getText().replaceAll("\\D", "")));
                rs.updateInt("CROPHEIGHT", Integer.parseInt(0 + textFieldCropHeight11.getText().replaceAll("\\D", "")));
                rs.updateRow();

            } catch (SQLException ignored) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :3259", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
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
        btnRefreshCamera.addActionListener(_ -> {
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

        panelInvoice = new JPanel();
        panelInvoice.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("", panelInvoice);
        tabbedPane.setEnabledAt(2, true);
        panelInvoice.setLayout(null);

        panelInvoiceLeft = new JPanel();
        panelInvoiceLeft.setBounds(45, 39, 400, 480);
        panelInvoiceLeft.setBackground(new Color(0, 255, 127));
        panelInvoiceLeft.setLayout(new BoxLayout(panelInvoiceLeft, BoxLayout.Y_AXIS));
        panelInvoice.add(panelInvoiceLeft);

        panelInvoiceRight = new JPanel();
        panelInvoiceRight.setBounds(460, 40, 400, 480);
        panelInvoiceRight.setBackground(new Color(0, 255, 127));
        panelInvoiceRight.setLayout(new BoxLayout(panelInvoiceRight, BoxLayout.Y_AXIS));
        panelInvoice.add(panelInvoiceRight);

        JPanel panelFooter = new JPanel();
        panelFooter.setBounds(45, 539, 500, 60);
        panelFooter.setBackground(new Color(0, 255, 127));
        panelFooter.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelInvoice.add(panelFooter);

        btnGetTotal = new JButton("Get Total");
        btnGetTotal.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGetTotal.addActionListener(_ -> {
            invoiceFields.forEach((_, component) -> component.setEnabled(false));
            DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
            DecimalFormat quantityFormat = new DecimalFormat("##,##,##0.000");
            double quantity = 0;
            double rate = 0;
            try {
                String[] temp = ("0" + ((JTextField) invoiceFields.get("quantity")).getText() + ".0").replaceAll("[^.\\d]", "").split("\\.");
                quantity = Double.parseDouble(temp[0] + "." + temp[1]);
                ((JTextField) invoiceFields.get("quantity")).setText(String.valueOf(quantityFormat.format(quantity)));
            } catch (Exception ignored) {
            }
            try {
                String[] temp = ("0" + ((JTextField) invoiceFields.get("rate")).getText() + ".0").replaceAll("[^.\\d]", "").split("\\.");
                rate = Double.parseDouble(temp[0] + "." + temp[1]);
                ((JTextField) invoiceFields.get("rate")).setText(decimalFormat.format(rate));
            } catch (Exception ignored) {
            }
            double amount = quantity * rate;
            try {
                ((JTextField) invoiceFields.get("amount")).setText(decimalFormat.format(amount));
            } catch (Exception ignored) {
            }
            double cgstPercent = 0;
            try {
                String[] temp = ("0" + ((JTextField) invoiceFields.get("cgstPercent")).getText() + ".0").replaceAll("[^.\\d]", "").split("\\.");
                cgstPercent = Double.parseDouble(temp[0] + "." + temp[1]);
            } catch (Exception ignored) {
            }
            double cgst = amount * cgstPercent * (rdbtnLocal.isSelected() ? 1 : 0);
            try {
                ((JTextField) invoiceFields.get("cgst")).setText(decimalFormat.format(cgst));
            } catch (Exception ignored) {
            }
            double sgstPercent = 0;
            try {
                String[] temp = ("0" + ((JTextField) invoiceFields.get("sgstPercent")).getText() + ".0").replaceAll("[^.\\d]", "").split("\\.");
                sgstPercent = Double.parseDouble(temp[0] + "." + temp[1]);
            } catch (Exception ignored) {
            }
            double sgst = amount * sgstPercent * (rdbtnLocal.isSelected() ? 1 : 0);
            try {
                ((JTextField) invoiceFields.get("sgst")).setText(decimalFormat.format(sgst));
            } catch (Exception ignored) {
            }
            double igstPercent = 0;
            try {
                String[] temp = ("0" + ((JTextField) invoiceFields.get("igstPercent")).getText() + ".0").replaceAll("[^.\\d]", "").split("\\.");
                igstPercent = Double.parseDouble(temp[0] + "." + temp[1]);
                ((JTextField) invoiceFields.get("igstPercent")).setText(String.valueOf(decimalFormat.format(quantity)));
            } catch (Exception ignored) {
            }
            double igst = amount * igstPercent * (rdbtnOtherStates.isSelected() ? 1 : 0);
            try {
                ((JTextField) invoiceFields.get("igst")).setText(decimalFormat.format(igst));
            } catch (Exception ignored) {
            }
            double total = amount + cgst + sgst + igst;
            double roundOff = total - Math.floor(total);
            try {
                ((JTextField) invoiceFields.get("roundOff")).setText(decimalFormat.format(roundOff));
            } catch (Exception ignored) {
            }
            total = amount + cgst + sgst + igst - roundOff;
            try {
                ((JTextField) invoiceFields.get("total")).setText(decimalFormat.format(total));
            } catch (Exception ignored) {
            }
            double taxAmount = cgst + sgst + igst;
            try {
                ((JTextField) invoiceFields.get("taxAmount")).setText(decimalFormat.format(taxAmount));
            } catch (Exception ignored) {
            }
            try {
                ((JTextField) invoiceFields.get("taxInWords")).setText(ConvertNumberToWord.format(taxAmount));
            } catch (Exception ignored) {
            }
            try {
                ((JTextField) invoiceFields.get("totalInWords")).setText(ConvertNumberToWord.format(total));
            } catch (Exception ignored) {
            }
            try {
                ((JTextField) invoiceFields.get("isLocal")).setText(String.valueOf(rdbtnLocal.isSelected()));
            } catch (Exception ignored) {
            }
            btnGetTotal.setEnabled(false);
            btnInvoiceSave.setEnabled(true);
            rdbtnLocal.setEnabled(false);
            rdbtnOtherStates.setEnabled(false);
            btnInvoiceSave.requestFocus();
        });
        btnGetTotal.setPreferredSize(new Dimension(150, 25));
        panelFooter.add(btnGetTotal);

        btnInvoiceSave = new JButton("Save");
        btnInvoiceSave.addActionListener(this::saveInvoice);
        btnInvoiceSave.setEnabled(false);
        btnInvoiceSave.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnInvoiceSave.setPreferredSize(new Dimension(150, 25));
        panelFooter.add(btnInvoiceSave);

        btnInvoicePrint = new JButton("Print");
        btnInvoicePrint.addActionListener(this::printInvoice);
        btnInvoicePrint.setEnabled(false);
        btnInvoicePrint.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnInvoicePrint.setPreferredSize(new Dimension(150, 25));
        panelFooter.add(btnInvoicePrint);

        panelFooter.add(Box.createRigidArea(new Dimension(500, 10)));
        panelFooter.add(Box.createRigidArea(new Dimension(150, 25)));

        JButton btnInvoiceRePrint = new JButton("Re Print");
        btnInvoiceRePrint.setFocusable(false);
        btnInvoiceRePrint.addActionListener(_ -> {
            String response = JOptionPane.showInputDialog(null, "Please Enter the Invoice No to Reprint ?", "Reprint", JOptionPane.QUESTION_MESSAGE);
            if (response != null) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM INVOICES WHERE INVOICE_NO = " + response);
                    if (rs.next()) {
                        JsonNode invoiceData = new ObjectMapper().readTree(rs.getString("INVOICE_DATA"));
                        boolean isLocal = invoiceData.path("isLocal").asBoolean(false);
                        rdbtnLocal.setSelected(isLocal);
                        rdbtnOtherStates.setSelected(!isLocal);
                        btnGetTotal.setEnabled(false);
                        btnInvoicePrint.setEnabled(true);
                        btnInvoiceSave.setEnabled(false);
                        btnInvoicePrint.requestFocus();
                        invoiceFields.forEach((_, component) -> component.setEnabled(false));
                        rdbtnLocal.setEnabled(false);
                        rdbtnOtherStates.setEnabled(false);
                        invoiceFields.forEach((key, component) -> {
                            if (component instanceof JTextField) {
                                ((JTextField) component).setText(invoiceData.path(key).asText(""));
                            }
                        });
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nRECORD NOT FOUND", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException | JsonProcessingException ignored) {
                    JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                }
                btnInvoiceRePrint.requestFocus();
            }
        });
        btnInvoiceRePrint.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnInvoiceRePrint.setPreferredSize(new Dimension(150, 25));
        panelFooter.add(btnInvoiceRePrint);

        JButton btnInvoiceClear = new JButton("Clear");
        btnInvoiceClear.setFocusable(false);
        btnInvoiceClear.addActionListener(this::clearInvoice);
        btnInvoiceClear.setPreferredSize(new Dimension(150, 25));
        btnInvoiceClear.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        panelFooter.add(btnInvoiceClear);

        rdbtnLocal = new JRadioButton("Local");
        rdbtnLocal.setSelected(true);
        rdbtnLocal.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnLocal.addChangeListener(_ -> {
            try {
                JTextField invoiceNo = (JTextField) invoiceFields.get("invoiceNo");
                invoiceNo.setText("");
                invoiceNo.setEnabled(true);
            } catch (Exception ignored) {
            }
        });
        rdbtnLocal.setFocusable(false);
        rdbtnLocal.setBackground(new Color(0, 255, 127));
        rdbtnLocal.setBounds(75, 7, 100, 25);
        buttonGroupInvoice.add(rdbtnLocal);
        panelInvoice.add(rdbtnLocal);

        rdbtnOtherStates = new JRadioButton("Other States");
        rdbtnOtherStates.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnOtherStates.setFocusable(false);
        rdbtnOtherStates.setBackground(new Color(0, 255, 127));
        rdbtnOtherStates.setBounds(206, 7, 143, 25);
        buttonGroupInvoice.add(rdbtnOtherStates);
        panelInvoice.add(rdbtnOtherStates);

        try {
            JLabel contact = new JLabel(new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResource("/contact.bmp")))));
            contact.setBounds(945, 505, 300, 100);
            panelInvoice.add(contact);
        } catch (Exception ignored) {
        }

        JPanel panelReport = new JPanel();
        panelReport.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("           Report           ", panelReport);
        panelReport.setLayout(null);

        rdbtnWeighing = new JRadioButton("Weighing Report (max 1000 rows)");
        buttonGroupReport.add(rdbtnWeighing);
        rdbtnWeighing.setBackground(new Color(0, 255, 127));
        rdbtnWeighing.addActionListener(_ -> {
            comboBoxReportType.removeAllItems();
            comboBoxReportType.addItem("Full Report");
            comboBoxReportType.addItem("Daily Report");
            comboBoxReportType.addItem("Datewise Report");
            comboBoxReportType.addItem("Serialwise Report");
            comboBoxReportType.addItem("Vehiclewise Report");
            comboBoxReportType.addItem("Materialwise Report");
            comboBoxReportType.addItem("Customerwise Report");
            comboBoxReportType.addItem("Customer - Materialwise Report");
            comboBoxReportType.addItem("Transporterwise Report");
            comboBoxReportType.addItem("Operatorwise Report");
        });
        rdbtnWeighing.setSelected(true);
        rdbtnWeighing.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnWeighing.setFocusable(false);
        rdbtnWeighing.setBounds(44, 25, 328, 25);
        panelReport.add(rdbtnWeighing);

        JLabel lblPleaseSelectThe = new JLabel("Please Select the Type of Report");
        lblPleaseSelectThe.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblPleaseSelectThe.setBounds(398, 51, 300, 25);
        panelReport.add(lblPleaseSelectThe);

        comboBoxReportType = new JComboBox<>();
        comboBoxReportType.addItemListener(_ -> {
            if (comboBoxReportType.getSelectedItem() != null) {
                if (rdbtnWeighing.isSelected()) {
                    switch (comboBoxReportType.getSelectedItem().toString()) {
                        case "Full Report":
                            datePicker1.setEnabled(false);
                            datePicker2.setEnabled(false);
                            textFieldReportTextBox.setEnabled(false);
                            comboBoxMaterialName.setEnabled(false);
                            break;
                        case "Daily Report":
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(false);
                            textFieldReportTextBox.setEnabled(false);
                            comboBoxMaterialName.setEnabled(false);
                            break;
                        case "Datewise Report":
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldReportTextBox.setEnabled(false);
                            comboBoxMaterialName.setEnabled(false);
                            break;
                        case "Serialwise Report":
                            lblReportTextBox.setText("Serial No");
                            datePicker1.setEnabled(false);
                            datePicker2.setEnabled(false);
                            textFieldReportTextBox.setEnabled(true);
                            comboBoxMaterialName.setEnabled(false);
                            break;
                        case "Vehiclewise Report":
                            lblReportTextBox.setText("Vehicle No");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldReportTextBox.setEnabled(true);
                            comboBoxMaterialName.setEnabled(false);
                            break;
                        case "Materialwise Report":
                            lblReportComboBox.setText("Material Name");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldReportTextBox.setEnabled(false);
                            comboBoxMaterialName.setEnabled(true);
                            break;
                        case "Customerwise Report":
                            lblReportTextBox.setText("Customer Name");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldReportTextBox.setEnabled(true);
                            comboBoxMaterialName.setEnabled(false);
                            break;
                        case "Customer - Materialwise Report":
                            lblReportTextBox.setText("Customer Name");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldReportTextBox.setEnabled(true);
                            comboBoxMaterialName.setEnabled(true);
                            break;
                        case "Transporterwise Report":
                            lblReportTextBox.setText("Transporter Name");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldReportTextBox.setEnabled(true);
                            comboBoxMaterialName.setEnabled(false);
                            break;
                        case "Operatorwise Report":
                            lblReportTextBox.setText("Operator");
                            datePicker1.setEnabled(true);
                            datePicker2.setEnabled(true);
                            textFieldReportTextBox.setEnabled(false);
                            comboBoxMaterialName.setEnabled(true);
                            break;
                    }
                }
            }
        });
        comboBoxReportType.setFocusable(false);
        comboBoxReportType.setModel(new DefaultComboBoxModel<>(new String[]{
                "Full Report",
                "Daily Report",
                "Datewise Report",
                "Serialwise Report",
                "Vehiclewise Report",
                "Materialwise Report",
                "Customerwise Report",
                "Customer - Materialwise Report",
                "Transporterwise Report",
                "Operatorwise Report"
        }));
        comboBoxReportType.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxReportType.setBounds(721, 51, 350, 25);
        panelReport.add(comboBoxReportType);

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

        lblReportTextBox = new JLabel("Vehicle No");
        lblReportTextBox.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblReportTextBox.setBounds(520, 87, 150, 25);
        panelReport.add(lblReportTextBox);

        textFieldReportTextBox = new JTextField();
        textFieldReportTextBox.setEnabled(false);
        textFieldReportTextBox.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldReportTextBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldReportTextBox.setDisabledTextColor(Color.BLACK);
        textFieldReportTextBox.setColumns(10);
        textFieldReportTextBox.setBounds(520, 121, 269, 30);
        panelReport.add(textFieldReportTextBox);

        comboBoxMaterialName = new JComboBox<>();
        comboBoxMaterialName.setEnabled(false);
        comboBoxMaterialName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxMaterialName.setBounds(840, 121, 270, 30);
        panelReport.add(comboBoxMaterialName);

        lblReportComboBox = new JLabel("Material Name");
        lblReportComboBox.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblReportComboBox.setBounds(838, 87, 163, 25);
        panelReport.add(lblReportComboBox);

        JButton btnGo = new JButton("Go");
        btnGo.addActionListener(this::findReport);
        btnGo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnGo.setFocusable(false);
        btnGo.setBounds(1153, 124, 60, 25);
        panelReport.add(btnGo);

        JScrollPane scrollPaneReport = new JScrollPane();
        scrollPaneReport.setAutoscrolls(true);
        scrollPaneReport.setFocusable(false);
        scrollPaneReport.setBounds(10, 162, 1235, 362);
        panelReport.add(scrollPaneReport);

        tableReport = new JTable();
        tableReport.putClientProperty("terminateEditOnFocusLost", true);
        tableReport.setFocusable(false);
        tableReport.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableReport.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 15));
        tableReport.getTableHeader().setReorderingAllowed(false);
        tableReport.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPaneReport.setViewportView(tableReport);

        textFieldTotalCharges = new JTextField();
        textFieldTotalCharges.setText("Rs. 0");
        textFieldTotalCharges.setEditable(false);
        textFieldTotalCharges.setHorizontalAlignment(SwingConstants.LEFT);
        textFieldTotalCharges.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTotalCharges.setDisabledTextColor(Color.BLACK);
        textFieldTotalCharges.setColumns(10);
        textFieldTotalCharges.setBounds(162, 535, 175, 30);
        panelReport.add(textFieldTotalCharges);

        JLabel lblTotalCharges = new JLabel("Total Charges");
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

        JButton btnExportToExcel = new JButton("Export to Excel");
        btnExportToExcel.addActionListener(_ -> {
            JFrame jFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + File.separator + "Desktop");
            fileChooser.setDialogTitle("Specify a file name to save your report");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Documents", "xls", "xlsx"));
            fileChooser.setSelectedFile(new File("report.xlsx"));
            if (fileChooser.showSaveDialog(jFrame) == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String name = fileToSave.getAbsolutePath();
                try {
                    if (Objects.equals(comboBoxReport.getSelectedItem(), "Mani & Co")) {
                        toExcelManiAndCo(name);
                    } else {
                        toExcel(name);
                    }
                } catch (IOException ignored) {
                    JOptionPane.showMessageDialog(null, "Plz Close the Excel file\nLINE :3027", "FILE ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnExportToExcel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnExportToExcel.setFocusable(false);
        btnExportToExcel.setBounds(1027, 550, 186, 25);
        panelReport.add(btnExportToExcel);

        JButton btnPrintReport = new JButton("Print");
        btnPrintReport.addActionListener(_ -> {
            if (rdbtnWeighing.isSelected()) {
                if (chckbxIceWater.isSelected()) {
                    printReportWeightIceWater();
                } else {
                    printReportWeight();
                }
            }
        });
        btnPrintReport.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnPrintReport.setFocusable(false);
        btnPrintReport.setBounds(912, 550, 105, 25);
        panelReport.add(btnPrintReport);

        btnInsertRow = new JButton("Insert row(s)");
        btnInsertRow.addActionListener(_ -> {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                int serialNo = rs.getInt("SLNO");
                valueEntered = false;
                String[] ConnectOptionNames = {
                        "Insert Row(s)",
                        "Cancel"
                };
                NumberFormatter numberFormatterAt = new NumberFormatter();
                numberFormatterAt.setValueClass(Integer.class);
                numberFormatterAt.setMaximum(serialNo);
                ((NumberFormat) numberFormatterAt.getFormat()).setGroupingUsed(false);
                numberFormatterAt.setMinimum(1);
                numberFormatterAt.setAllowsInvalid(false);
                numberFormatterAt.setCommitsOnValidEdit(true);

                JTextField jTextFieldAt = new JFormattedTextField(numberFormatterAt);
                jTextFieldAt.setText("1");
                NumberFormatter numberFormatterRows = new NumberFormatter();
                numberFormatterRows.setValueClass(Integer.class);
                numberFormatterRows.setMaximum(100);
                numberFormatterRows.setMinimum(1);
                numberFormatterRows.setAllowsInvalid(false);
                numberFormatterRows.setCommitsOnValidEdit(true);
                JTextField jTextFieldRows = new JFormattedTextField(numberFormatterRows);
                jTextFieldRows.setText("1");
                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Insert at"));
                jTextFieldAt.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(final KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            jTextFieldRows.requestFocus();
                        }
                    }
                });
                jTextFieldRows.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(final KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            valueEntered = true;
                            JOptionPane.getRootFrame().dispose();
                        }
                    }
                });
                panel.add(jTextFieldAt);
                panel.add(jTextFieldRows);
                panel.add(new JLabel(" Row(s) 100 max"));

                if (JOptionPane.showOptionDialog(null, panel, "Insert Row(s) in Report", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, "") == 0 || valueEntered) {
                    int rows = Integer.parseInt("0" + jTextFieldRows.getText());
                    int at = Integer.parseInt("0" + jTextFieldAt.getText());
                    stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.execute("UPDATE WEIGHING SET SLNO = SLNO + " + rows + " WHERE SLNO >= " + at);
                    rs = stmt.executeQuery("SELECT * FROM WEIGHING");
                    for (int i = 0; i < rows; i++) {
                        rs.moveToInsertRow();
                        rs.updateInt("SLNO", at + i);
                        rs.updateBoolean("MANUAL", true);
                        rs.insertRow();
                    }
                    if (reportOpened) {
                        getReport();
                    }
                    refreshSlNo();
                }
            } catch (SQLException ignored) {
            }
        });
        btnInsertRow.setVisible(false);
        btnInsertRow.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnInsertRow.setFocusable(false);
        btnInsertRow.setBounds(765, 550, 137, 25);
        panelReport.add(btnInsertRow);

        btnDeleteRow = new JButton("Delete row");
        btnDeleteRow.addActionListener(_ -> {
            try {
                String response = JOptionPane.showInputDialog(null, "Please Enter the Sl.no to Delete ?", "Delete Row", JOptionPane.QUESTION_MESSAGE);
                if (response != null) {
                    int serialNo = Integer.parseInt(response);
                    if (serialNo > 0) {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        stmt.executeUpdate("DELETE FROM WEIGHING WHERE SLNO = " + response);
                        stmt.executeUpdate("UPDATE WEIGHING SET SLNO = SLNO - 1 WHERE SLNO >= " + response);
                        if (reportOpened) {
                            getReport();
                        }
                        refreshSlNo();
                    }
                }
            } catch (NumberFormatException | SQLException ignored) {
            }
        });
        btnDeleteRow.setVisible(false);
        btnDeleteRow.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnDeleteRow.setFocusable(false);
        btnDeleteRow.setBounds(617, 550, 137, 25);
        panelReport.add(btnDeleteRow);

        btnImportFromExcel = new JButton("Import from Excel");
        btnImportFromExcel.addActionListener(_ -> {
            JFrame jFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + File.separator + "Desktop");
            fileChooser.setDialogTitle("Please select a file to import");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Documents", "xls", "xlsx"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            if (fileChooser.showSaveDialog(jFrame) == JFileChooser.APPROVE_OPTION) {
                try {
                    List<Integer> failedSlNo = fromExcel(fileChooser.getSelectedFile());
                    if (!failedSlNo.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Following row no(s) had some errors\n" + failedSlNo + "\n Please correct them", "Excel File Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Import Successful", "Excel Import", JOptionPane.INFORMATION_MESSAGE);
                    }
                    refreshSlNo();
                    if (reportOpened) {
                        getReport();
                    }
                } catch (Exception ignored) {
                    JOptionPane.showMessageDialog(null, "Plz Close the Excel file\nLINE :3831", "FILE ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnImportFromExcel.setVisible(false);
        btnImportFromExcel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnImportFromExcel.setFocusable(false);
        btnImportFromExcel.setBounds(429, 550, 180, 25);
        panelReport.add(btnImportFromExcel);

        btnMassPrint = new JButton("Mass Print");
        btnMassPrint.addActionListener(_ -> {
            if (JOptionPane.showConfirmDialog(null, "Do you want to Print ?", "Print", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                noOfCopies = Integer.parseInt(textFieldNoOfCopies.getText());
                getReport();
                TableModel model = tableReport.getModel();

                for (int i = 0; i < model.getRowCount(); i++) {
                    rePrint(model.getValueAt(i, 1).toString());
                    print();

                    if (i % 50 == 0 && !(JOptionPane.showConfirmDialog(null, "Do you want to continue print from (" + model.getValueAt(i, 1) + ") ?", "Continue Print", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)) {
                        break;
                    }
                }
                clear();
            }
        });
        btnMassPrint.setVisible(false);
        btnMassPrint.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnMassPrint.setFocusable(false);
        btnMassPrint.setBounds(901, 579, 128, 25);
        panelReport.add(btnMassPrint);

        JRadioButton rdbtnInvoiceReport = new JRadioButton("Invoice Report (max 1000 rows)");
        buttonGroupReport.add(rdbtnInvoiceReport);
        rdbtnInvoiceReport.addActionListener(_ -> {
            comboBoxReportType.removeAllItems();
            comboBoxReportType.addItem("Full Report");
            comboBoxReportType.addItem("Daily Report");
            comboBoxReportType.addItem("Datewise Report");
            comboBoxReportType.addItem("Serialwise Report");
        });
        rdbtnInvoiceReport.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        rdbtnInvoiceReport.setFocusable(false);
        rdbtnInvoiceReport.setBackground(new Color(0, 255, 127));
        rdbtnInvoiceReport.setBounds(44, 53, 328, 25);
        panelReport.add(rdbtnInvoiceReport);

        JPanel panelSettings1 = new JPanel();
        panelSettings1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
                    DefaultTableModel model = (DefaultTableModel) tableVehicleTare.getModel();
                    model.setRowCount(0);
                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getString("VEHICLENO"),
                                rs.getString("PLACE"),
                                rs.getString("PHONE_NUMBER"),
                                rs.getInt("TAREWT"),
                                dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME")).getTime()))
                        });
                    }
                } catch (SQLException | ParseException ignored) {
                }
            }
        });
        panelSettings1.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("          Settings          ", panelSettings1);
        panelSettings1.setLayout(null);

        JLabel lblMaterials = new JLabel("Materials");
        lblMaterials.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        lblMaterials.setBounds(10, 319, 111, 25);
        panelSettings1.add(lblMaterials);

        JLabel lblVehicleTares = new JLabel("Vehicle Tares");
        lblVehicleTares.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        lblVehicleTares.setBounds(320, 319, 175, 25);
        panelSettings1.add(lblVehicleTares);

        JLabel lblCustomer = new JLabel("Customer");
        lblCustomer.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        lblCustomer.setBounds(877, 325, 111, 25);
        panelSettings1.add(lblCustomer);

        JLabel lblGeneralSettings = new JLabel("General Settings");
        lblGeneralSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblGeneralSettings.setBounds(10, 11, 150, 25);
        panelSettings1.add(lblGeneralSettings);

        JLabel lblTitle1 = new JLabel("Title 1");
        lblTitle1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTitle1.setBounds(10, 47, 75, 25);
        panelSettings1.add(lblTitle1);

        JLabel lblTitle2 = new JLabel("Title 2");
        lblTitle2.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTitle2.setBounds(10, 97, 75, 25);
        panelSettings1.add(lblTitle2);

        JLabel lblFooter = new JLabel("Footer");
        lblFooter.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblFooter.setBounds(10, 147, 75, 25);
        panelSettings1.add(lblFooter);

        JLabel lblWeighbridgeSettings = new JLabel("Weighbridge Settings");
        lblWeighbridgeSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblWeighbridgeSettings.setBounds(336, 11, 200, 25);
        panelSettings1.add(lblWeighbridgeSettings);

        JLabel lblBaudRate = new JLabel("Baud Rate");
        lblBaudRate.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblBaudRate.setBounds(336, 45, 100, 25);
        panelSettings1.add(lblBaudRate);

        JLabel lblPortName = new JLabel("Port Details");
        lblPortName.setToolTipText("<Port Name>;<Data Bit>;<Parity>;<Pattern>;<split>\r\n");
        lblPortName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblPortName.setBounds(336, 81, 100, 25);
        panelSettings1.add(lblPortName);

        JLabel lblAdministratorSettings = new JLabel("Administrator Settings");
        lblAdministratorSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblAdministratorSettings.setBounds(638, 11, 200, 25);
        panelSettings1.add(lblAdministratorSettings);

        JLabel lblPrinterSettings = new JLabel("Printer Settings");
        lblPrinterSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblPrinterSettings.setBounds(845, 11, 200, 25);
        panelSettings1.add(lblPrinterSettings);

        JLabel lblPrinter = new JLabel("Printer");
        lblPrinter.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblPrinter.setBounds(845, 50, 100, 25);
        panelSettings1.add(lblPrinter);

        JLabel lblNoOfCopies = new JLabel("No Of Copies");
        lblNoOfCopies.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblNoOfCopies.setBounds(845, 80, 114, 25);
        panelSettings1.add(lblNoOfCopies);

        JScrollPane scrollPaneMaterial = new JScrollPane();
        scrollPaneMaterial.setBounds(10, 355, 300, 250);
        panelSettings1.add(scrollPaneMaterial);

        tableMaterial = new JTable();
        tableMaterial.putClientProperty("terminateEditOnFocusLost", true);
        tableMaterial.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableMaterial.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 15));
        tableMaterial.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{
                        "Sl.No",
                        "Materials",
                        "Cost"
                }) {
            @Serial
            private static final long serialVersionUID = 1L;
            final boolean[] columnEditable = new boolean[]{
                    false,
                    true,
                    true
            };

            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditable[column];
            }
        });
        tableMaterial.getColumnModel().getColumn(0).setResizable(false);
        tableMaterial.getColumnModel().getColumn(0).setPreferredWidth(43);
        tableMaterial.getColumnModel().getColumn(1).setResizable(false);
        tableMaterial.getColumnModel().getColumn(2).setResizable(false);
        scrollPaneMaterial.setViewportView(tableMaterial);

        JButton btnAddMaterialRow = new JButton("+");
        btnAddMaterialRow.addActionListener(_ -> {
            DefaultTableModel model = (DefaultTableModel) tableMaterial.getModel();
            model.addRow(new Object[]{
                    model.getRowCount() + 1,
                    "",
                    0.0
            });
        });
        btnAddMaterialRow.setFocusable(false);
        btnAddMaterialRow.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnAddMaterialRow.setBounds(229, 319, 41, 38);
        panelSettings1.add(btnAddMaterialRow);

        JButton btnDeleteMaterialRow = new JButton("-");
        btnDeleteMaterialRow.addActionListener(_ -> {
            DefaultTableModel model = (DefaultTableModel) tableMaterial.getModel();
            if (tableMaterial.getSelectedRow() != -1) {
                model.removeRow(tableMaterial.getSelectedRow());
            }
            for (int i = 1; i <= model.getRowCount(); i++) {
                model.setValueAt(i, i - 1, 0);
            }
        });
        btnDeleteMaterialRow.setFocusable(false);
        btnDeleteMaterialRow.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteMaterialRow.setBounds(269, 319, 41, 38);
        panelSettings1.add(btnDeleteMaterialRow);

        JScrollPane scrollPaneVehicleTare = new JScrollPane();
        scrollPaneVehicleTare.setBounds(320, 355, 547, 250);
        panelSettings1.add(scrollPaneVehicleTare);

        tableVehicleTare = new JTable();
        tableVehicleTare.putClientProperty("terminateEditOnFocusLost", true);
        tableVehicleTare.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableVehicleTare.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 15));
        tableVehicleTare.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{
                        "Vehicle No",
                        "Place",
                        "Phone No",
                        "Tare Wt",
                        "Tare Date & Time "
                }) {
            @Serial
            private static final long serialVersionUID = 1L;
            final Class<?>[] columnTypes = new Class[]{
                    Object.class,
                    Object.class,
                    Object.class,
                    Integer.class,
                    Object.class
            };
            final boolean[] columnEditables = new boolean[]{
                    false,
                    false,
                    false,
                    false,
                    false
            };

            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPaneVehicleTare.setViewportView(tableVehicleTare);

        JButton btnDeleteVehicleRow = new JButton("-");
        btnDeleteVehicleRow.addActionListener(_ -> {
            if (tableVehicleTare.getSelectedRow() != -1) {
                ((DefaultTableModel) tableVehicleTare.getModel()).removeRow(tableVehicleTare.getSelectedRow());
            }
        });
        btnDeleteVehicleRow.setFocusable(false);
        btnDeleteVehicleRow.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteVehicleRow.setBounds(826, 319, 41, 38);
        panelSettings1.add(btnDeleteVehicleRow);

        JScrollPane scrollPaneCustomer = new JScrollPane();
        scrollPaneCustomer.setBounds(877, 355, 179, 250);
        panelSettings1.add(scrollPaneCustomer);

        tableCustomer = new JTable();
        tableCustomer.putClientProperty("terminateEditOnFocusLost", true);
        tableCustomer.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tableCustomer.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 15));
        tableCustomer.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{
                        "Customer Name"
                }));
        tableCustomer.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        scrollPaneCustomer.setViewportView(tableCustomer);

        JButton btnAddCustomer = new JButton("+");
        btnAddCustomer.addActionListener(_ -> {
            DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
            model.addRow(new Object[]{
                    ""
            });
        });
        btnAddCustomer.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnAddCustomer.setFocusable(false);
        btnAddCustomer.setBounds(975, 319, 41, 38);
        panelSettings1.add(btnAddCustomer);

        JButton btnDeleteCustomer = new JButton("-");
        btnDeleteCustomer.addActionListener(_ -> {
            if (tableCustomer.getSelectedRow() != -1) {
                ((DefaultTableModel) tableCustomer.getModel()).removeRow(tableCustomer.getSelectedRow());
            }
        });
        btnDeleteCustomer.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteCustomer.setFocusable(false);
        btnDeleteCustomer.setBounds(1015, 319, 41, 38);
        panelSettings1.add(btnDeleteCustomer);

        textFieldTitle1 = new JTextField();
        textFieldTitle1.setToolTipText("Only 30 letters");
        textFieldTitle1.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                title1.setText(textFieldTitle1.getText());
            }
        });
        textFieldTitle1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTitle1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTitle1.setDisabledTextColor(Color.BLACK);
        textFieldTitle1.setColumns(10);
        textFieldTitle1.setBounds(101, 47, 209, 30);
        panelSettings1.add(textFieldTitle1);

        textFieldTitle2 = new JTextField();
        textFieldTitle2.setToolTipText("Only 45 letters");
        textFieldTitle2.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                title2.setText(textFieldTitle2.getText());
            }
        });
        textFieldTitle2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTitle2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTitle2.setDisabledTextColor(Color.BLACK);
        textFieldTitle2.setColumns(10);
        textFieldTitle2.setBounds(101, 97, 209, 30);
        panelSettings1.add(textFieldTitle2);

        textFieldFooter = new JTextField();
        textFieldFooter.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldFooter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldFooter.setDisabledTextColor(Color.BLACK);
        textFieldFooter.setColumns(10);
        textFieldFooter.setBounds(101, 147, 209, 30);
        panelSettings1.add(textFieldFooter);

        chckbxExcludeCharges = new JCheckBox("Exclude Charges");
        chckbxExcludeCharges.setEnabled(false);
        chckbxExcludeCharges.setFocusable(false);
        chckbxExcludeCharges.setBackground(new Color(0, 255, 127));
        chckbxExcludeCharges.addChangeListener(_ -> {
            textFieldCharges.setVisible(!chckbxExcludeCharges.isSelected());
            lblCharges.setVisible(!chckbxExcludeCharges.isSelected());
            clear();
        });
        chckbxExcludeCharges.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeCharges.setBounds(10, 270, 145, 25);
        panelSettings1.add(chckbxExcludeCharges);

        textFieldBaudRate = new JTextField();
        textFieldBaudRate.setEnabled(false);
        textFieldBaudRate.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBaudRate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldBaudRate.setDisabledTextColor(Color.BLACK);
        textFieldBaudRate.setColumns(10);
        textFieldBaudRate.setBounds(446, 45, 175, 30);
        panelSettings1.add(textFieldBaudRate);

        textFieldPortName = new JTextField();
        textFieldPortName.setToolTipText("<Port Name>;<Data Bit(8)>;<Parity(0)>;<delimiter(10)>;<Pattern(~~~);<reverse(f)>");
        textFieldPortName.setEnabled(false);
        textFieldPortName.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPortName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldPortName.setDisabledTextColor(Color.BLACK);
        textFieldPortName.setColumns(10);
        textFieldPortName.setBounds(446, 81, 175, 30);
        panelSettings1.add(textFieldPortName);

        chckbxManualEntry = new JCheckBox("Manual Entry");
        chckbxManualEntry.setFocusable(false);
        chckbxManualEntry.setVisible(false);
        chckbxManualEntry.addActionListener(_ -> {
            if (chckbxManualEntry.isSelected()) {
                JPasswordField password = new JPasswordField(10);
                password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {
                        "Enter",
                        "Cancel"
                };
                panel.add(new JLabel("Please the Manual Entry Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = MANUAL_ENTRY_PASSWORD.toCharArray();
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    btnGetGross.setEnabled(true);
                    btnGetTare.setEnabled(true);
                    btnGetGross.setVisible(true);
                    btnGetTare.setVisible(true);
                    btnTotal.setVisible(true);
                    clear();
                    return;
                }
            }
            chckbxManualEntry.setSelected(false);
            btnGetGross.setEnabled(false);
            btnGetTare.setEnabled(false);
            btnGetGross.setVisible(false);
            btnGetTare.setVisible(false);
            btnTotal.setVisible(false);
        });
        chckbxManualEntry.setBackground(new Color(0, 255, 127));
        chckbxManualEntry.setEnabled(false);
        chckbxManualEntry.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxManualEntry.setBounds(638, 147, 200, 25);
        panelSettings1.add(chckbxManualEntry);

        chckbxEditEnable = new JCheckBox("Edit Enable");
        chckbxEditEnable.setFocusable(false);
        chckbxEditEnable.setVisible(false);
        chckbxEditEnable.addActionListener(_ -> {
            if (chckbxEditEnable.isSelected()) {
                JPasswordField password = new JPasswordField(10);
                password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {
                        "Enter",
                        "Cancel"
                };
                panel.add(new JLabel("Please the Editing Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = EDIT_ENABLE_PASSWORD.toCharArray();
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    if (reportOpened) {
                        getReport();
                    }
                    btnInsertRow.setVisible(true);
                    btnImportFromExcel.setVisible(true);
                    btnDeleteRow.setVisible(true);
                    btnMassPrint.setVisible(true);
                    return;
                }
            }
            try {
                tableReport.removeColumn(tableReport.getColumn("Edit/Save"));
            } catch (IllegalArgumentException ignored) {
            }
            btnInsertRow.setVisible(false);
            btnImportFromExcel.setVisible(false);
            btnDeleteRow.setVisible(false);
            chckbxEditEnable.setSelected(false);
            btnMassPrint.setVisible(false);
        });
        chckbxEditEnable.setBackground(new Color(0, 255, 127));
        chckbxEditEnable.setEnabled(false);
        chckbxEditEnable.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxEditEnable.setBounds(638, 172, 200, 25);
        panelSettings1.add(chckbxEditEnable);

        comboBoxPrinter = new JComboBox<>();
        comboBoxPrinter.setFocusable(false);
        comboBoxPrinter.setModel(new DefaultComboBoxModel<>(printers));
        comboBoxPrinter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxPrinter.setBounds(970, 50, 276, 30);
        panelSettings1.add(comboBoxPrinter);

        textFieldNoOfCopies = new JTextField();
        textFieldNoOfCopies.setText("0");
        textFieldNoOfCopies.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNoOfCopies.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNoOfCopies.setDisabledTextColor(Color.BLACK);
        textFieldNoOfCopies.setColumns(10);
        textFieldNoOfCopies.setBounds(970, 80, 76, 30);
        panelSettings1.add(textFieldNoOfCopies);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFocusable(false);
        btnUpdate.addActionListener(this::updateSettings);
        btnUpdate.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnUpdate.setBounds(580, 240, 150, 25);
        panelSettings1.add(btnUpdate);

        JButton btnResetWeights = new JButton("Reset Sl No");
        btnResetWeights.setFocusable(false);
        btnResetWeights.addActionListener(_ -> {
            JPasswordField password = new JPasswordField(10);
            password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
            JPanel panel = new JPanel();
            String[] ConnectOptionNames = {
                    "Enter",
                    "Cancel"
            };
            panel.add(new JLabel("Please the Password ? "));
            panel.add(password);
            JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
            char[] temp = password.getPassword();
            boolean isCorrect;
            char[] correctPassword = RESET_PASSWORD.toCharArray();
            if (temp.length != correctPassword.length) {
                isCorrect = false;
            } else {
                isCorrect = Arrays.equals(temp, correctPassword);
            }
            if (isCorrect) {
                String response = JOptionPane.showInputDialog(null, "Please Enter the Starting Sl No ?", "Sl No", JOptionPane.QUESTION_MESSAGE);
                if (response == null || Integer.parseInt(0 + response.replaceAll("\\D", "")) == 0) {
                    JOptionPane.showMessageDialog(null, "Reset Failed ", "Value Entered is not correct", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        PreparedStatement stmts = dbConnection.prepareStatement("CREATE TABLE WEIGHING_" + DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss").format(LocalDateTime.now()) + " AS TABLE WEIGHING");
                        stmts.executeUpdate();
                        stmts = dbConnection.prepareStatement("TRUNCATE TABLE WEIGHING");
                        stmts.executeUpdate();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                        rs.absolute(1);
                        rs.updateInt("SLNO", Integer.parseInt(response.replaceAll("\\D", "")));
                        rs.updateRow();
                    } catch (SQLException ignored) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2836", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    settings();
                    JOptionPane.showMessageDialog(null, "Reset Successful ", "Reset Successful", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Password ", "Value Entered the Correct Password", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFocusable(false);
        btnRefresh.addActionListener(this::settings);
        btnRefresh.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnRefresh.setBounds(740, 240, 150, 25);
        panelSettings1.add(btnRefresh);
        btnResetWeights.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnResetWeights.setBounds(740, 270, 150, 25);
        panelSettings1.add(btnResetWeights);

        JButton btnUnlock = new JButton("Unlock");
        btnUnlock.setFocusable(false);
        btnUnlock.addActionListener(_ -> {
            if (Objects.equals(btnUnlock.getText(), "Unlock")) {
                JPasswordField password = new JPasswordField(10);
                password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {
                        "Enter",
                        "Cancel"
                };
                panel.add(new JLabel("Please the Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = UNLOCK_PASSWORD.toCharArray();
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    btnUnlock.setText("Lock");
                    chckbxManualEntry.setEnabled(true);
                    chckbxEditEnable.setEnabled(true);
                    chckbxCamera.setEnabled(true);
                    chckbxSms.setEnabled(true);
                    chckbxInvoice.setEnabled(true);
                    chckbxExcludeCustomer.setEnabled(true);
                    chckbxExcludeCharges.setEnabled(true);
                    chckbxExcludeDrivers.setEnabled(true);
                    chckbxExcludeRemarks.setEnabled(true);
                    chckbxExcludeCredit.setEnabled(true);
                    chckbxExcludeVehicleType.setEnabled(true);
                    chckbxAutoCharges.setEnabled(true);
                    chckbxMaterialSl.setEnabled(true);
                    textFieldBaudRate.setEnabled(true);
                    textFieldPortName.setEnabled(true);
                    textFieldSMSBaudRate.setEnabled(true);
                    textFieldSMSPortName.setEnabled(true);
                    chckbxExcludeNoOfBags.setEnabled(true);
                    chckbxExcludePlaceAndPhoneNumber.setEnabled(true);
                    chckbxenableSettings2.setEnabled(true);
                    chckbxExcludeDcNo.setEnabled(true);
                    chckbxPrinterCopyDialog.setEnabled(true);
                    chckbxIceWater.setEnabled(true);
                    chckbxRoundOff.setEnabled(true);
                }
            } else {
                btnUnlock.setText("Unlock");
                chckbxIceWater.setEnabled(false);
                chckbxRoundOff.setEnabled(false);
                chckbxManualEntry.setEnabled(false);
                chckbxEditEnable.setEnabled(false);
                chckbxCamera.setEnabled(false);
                chckbxSms.setEnabled(false);
                chckbxInvoice.setEnabled(false);
                chckbxExcludeCustomer.setEnabled(false);
                chckbxExcludeCharges.setEnabled(false);
                chckbxExcludeDrivers.setEnabled(false);
                chckbxExcludeRemarks.setEnabled(false);
                chckbxAutoCharges.setEnabled(false);
                chckbxMaterialSl.setEnabled(false);
                textFieldBaudRate.setEnabled(false);
                textFieldPortName.setEnabled(false);
                textFieldSMSBaudRate.setEnabled(false);
                textFieldSMSPortName.setEnabled(false);
                chckbxExcludeNoOfBags.setEnabled(false);
                chckbxenableSettings2.setSelected(false);
                chckbxenableSettings2.setEnabled(false);
                chckbxExcludeDcNo.setEnabled(false);
                chckbxPrinterCopyDialog.setEnabled(false);
                chckbxExcludePlaceAndPhoneNumber.setEnabled(false);
                chckbxExcludeCredit.setEnabled(false);
                chckbxExcludeVehicleType.setEnabled(false);
            }
        });
        btnUnlock.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnUnlock.setBounds(580, 270, 150, 25);
        panelSettings1.add(btnUnlock);

        chckbxExcludeCustomer = new JCheckBox("Exclude Customer");
        chckbxExcludeCustomer.setEnabled(false);
        chckbxExcludeCustomer.setFocusable(false);
        chckbxExcludeCustomer.addChangeListener(_ -> {
            comboBoxCustomerName.setEnabled(!chckbxExcludeCustomer.isSelected());
            comboBoxCustomerName.setVisible(!chckbxExcludeCustomer.isSelected());
            lblCustomerName.setVisible(!chckbxExcludeCustomer.isSelected());
            clear();
        });
        chckbxExcludeCustomer.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeCustomer.setBackground(new Color(0, 255, 127));
        chckbxExcludeCustomer.setBounds(10, 190, 145, 25);
        panelSettings1.add(chckbxExcludeCustomer);

        chckbxExcludeDrivers = new JCheckBox("Exclude Transporter");
        chckbxExcludeDrivers.setEnabled(false);
        chckbxExcludeDrivers.setFocusable(false);
        chckbxExcludeDrivers.addChangeListener(_ -> {
            comboBoxTransporterName.setEnabled(!chckbxExcludeDrivers.isSelected());
            comboBoxTransporterName.setVisible(!chckbxExcludeDrivers.isSelected());
            lblDriversName.setVisible(!chckbxExcludeDrivers.isSelected());
            clear();
        });
        chckbxExcludeDrivers.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeDrivers.setBackground(new Color(0, 255, 127));
        chckbxExcludeDrivers.setBounds(10, 210, 160, 23);
        panelSettings1.add(chckbxExcludeDrivers);

        chckbxCamera = new JCheckBox("Camera");
        chckbxCamera.setSelected(true);
        chckbxCamera.addActionListener(this::cameraEvent);

        chckbxCamera.setFocusable(false);
        chckbxCamera.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxCamera.setEnabled(false);
        chckbxCamera.setBackground(new Color(0, 255, 127));
        chckbxCamera.setBounds(638, 50, 199, 25);
        panelSettings1.add(chckbxCamera);

        comboBoxPrintOptionForWeight = new JComboBox<>();
        comboBoxPrintOptionForWeight.setModel(new DefaultComboBoxModel<>(new String[]{
                "Camera",
                "Camera * 2",
                "EMJAY",
                "EMJAY 1",
                "Ice Water",
                "Mani & Co",
                "Mani & Co 1",
                "Mani & Co 2",
                "No Of Bags",
                "Plain Camera",
                "Plain Camera * 2",
                "Plain Paper",
                "Plain Paper A4",
                "Plain Paper A4 without header",
                "Pre Print",
                "Pre Print 2",
                "Pre Print 3",
                "Pre Print 4",
                "Pre Print 5",
                "Quotation",
                "Quotation KJJ",
                "Sri Pathy",
                "Standard"
        }));
        comboBoxPrintOptionForWeight.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        comboBoxPrintOptionForWeight.setFocusable(false);
        comboBoxPrintOptionForWeight.setBounds(1055, 80, 190, 30);
        panelSettings1.add(comboBoxPrintOptionForWeight);

        chckbxSms = new JCheckBox("SMS");
        chckbxSms.addActionListener(_ -> {
            if (chckbxSms.isSelected()) {
                chckbxSms.setSelected(false);
                JPasswordField password = new JPasswordField(10);
                password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {
                        "Enter",
                        "Cancel"
                };
                panel.add(new JLabel("Please Enter the SMS Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = SMS_PASSWORD.toCharArray();
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                chckbxSms.setSelected(isCorrect);
            }
        });
        chckbxSms.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxSms.setFocusable(false);
        chckbxSms.setEnabled(false);
        chckbxSms.setBackground(new Color(0, 255, 127));
        chckbxSms.setBounds(638, 75, 200, 25);
        panelSettings1.add(chckbxSms);

        textFieldSMSPortName = new JTextField();
        textFieldSMSPortName.setEnabled(false);
        textFieldSMSPortName.setText(null);
        textFieldSMSPortName.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSMSPortName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldSMSPortName.setDisabledTextColor(Color.BLACK);
        textFieldSMSPortName.setColumns(10);
        textFieldSMSPortName.setBounds(446, 195, 175, 30);
        panelSettings1.add(textFieldSMSPortName);

        textFieldSMSBaudRate = new JTextField();
        textFieldSMSBaudRate.setEnabled(false);
        textFieldSMSBaudRate.setText("0");
        textFieldSMSBaudRate.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSMSBaudRate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldSMSBaudRate.setDisabledTextColor(Color.BLACK);
        textFieldSMSBaudRate.setColumns(10);
        textFieldSMSBaudRate.setBounds(446, 159, 175, 30);
        panelSettings1.add(textFieldSMSBaudRate);

        JLabel lblSmsSettings = new JLabel("SMS Settings");
        lblSmsSettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        lblSmsSettings.setBounds(336, 125, 200, 25);
        panelSettings1.add(lblSmsSettings);

        JLabel label_2 = new JLabel("Baud Rate");
        label_2.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label_2.setBounds(336, 159, 100, 25);
        panelSettings1.add(label_2);

        JLabel label_3 = new JLabel("Port Name");
        label_3.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        label_3.setBounds(336, 195, 100, 25);
        panelSettings1.add(label_3);

        JButton btnResetTrasporter = new JButton("Reset Tares");
        btnResetTrasporter.addActionListener(_ -> {
            JPasswordField password = new JPasswordField(10);
            password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
            JPanel panel = new JPanel();
            String[] ConnectOptionNames = {
                    "Enter",
                    "Cancel"
            };
            panel.add(new JLabel("Please the Password ? "));
            panel.add(password);
            JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
            char[] temp = password.getPassword();
            boolean isCorrect;
            char[] correctPassword = RESET_PASSWORD.toCharArray();
            if (temp.length != correctPassword.length) {
                isCorrect = false;
            } else {
                isCorrect = Arrays.equals(temp, correctPassword);
            }
            if (isCorrect) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.executeUpdate("truncate table TRANSPORTER");
                    comboBoxTransporterName.removeAllItems();
                    transportSet.clear();
                } catch (SQLException ignored) {
                }
            }
            clear();
        });
        btnResetTrasporter.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnResetTrasporter.setFocusable(false);
        btnResetTrasporter.setBounds(1085, 270, 150, 25);
        panelSettings1.add(btnResetTrasporter);

        chckbxExcludeRemarks = new JCheckBox("Exclude Remarks");
        chckbxExcludeRemarks.setEnabled(false);
        chckbxExcludeRemarks.addChangeListener(_ -> {
            textPaneRemarks.setVisible(!chckbxExcludeRemarks.isSelected());
            lblRemarks.setVisible(!chckbxExcludeRemarks.isSelected());
            clear();
        });
        chckbxExcludeRemarks.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeRemarks.setFocusable(false);
        chckbxExcludeRemarks.setBackground(new Color(0, 255, 127));
        chckbxExcludeRemarks.setBounds(170, 190, 145, 25);
        panelSettings1.add(chckbxExcludeRemarks);

        chckbxAutoCharges = new JCheckBox("Auto Charges");
        chckbxAutoCharges.addChangeListener(_ -> {
            if (chckbxAutoCharges.isSelected()) {
                chckbxAutoChargecheck.setEnabled(true);
                chckbxAutoChargecheck.setVisible(true);
                chckbxExcludeCharges.setEnabled(false);
                chckbxExcludeCharges.setSelected(false);
            } else {
                if (Objects.equals(btnUnlock.getText(), "Lock") && !chckbxIceWater.isSelected()) {
                    chckbxExcludeCharges.setEnabled(true);
                }
                chckbxAutoChargecheck.setSelected(false);
                chckbxAutoChargecheck.setVisible(false);
            }
            clear();
        });

        chckbxAutoCharges.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxAutoCharges.setFocusable(false);
        chckbxAutoCharges.setEnabled(false);
        chckbxAutoCharges.setBackground(new Color(0, 255, 127));
        chckbxAutoCharges.setBounds(195, 270, 115, 25);
        panelSettings1.add(chckbxAutoCharges);

        chckbxMaterialSl = new JCheckBox("Material Sl");
        chckbxMaterialSl.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxMaterialSl.setFocusable(false);
        chckbxMaterialSl.setEnabled(false);
        chckbxMaterialSl.setBackground(new Color(0, 255, 127));
        chckbxMaterialSl.setBounds(195, 290, 115, 25);
        panelSettings1.add(chckbxMaterialSl);

        chckbxenableSettings2 = new JCheckBox("Enable Settings Page 2");
        chckbxenableSettings2.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxenableSettings2.setEnabled(false);
        chckbxenableSettings2.addChangeListener(_ -> {
            if (chckbxenableSettings2.isSelected()) {
                tabbedPane.setEnabledAt(5, true);
                tabbedPane.setTitleAt(5, "          Settings 2          ");
            } else {
                tabbedPane.setEnabledAt(5, false);
                tabbedPane.setTitleAt(5, "");
            }
        });
        chckbxenableSettings2.setBackground(new Color(0, 255, 127));
        chckbxenableSettings2.setBounds(1029, 190, 180, 25);
        panelSettings1.add(chckbxenableSettings2);

        chckbxExcludeNoOfBags = new JCheckBox("Exclude Bags");
        chckbxExcludeNoOfBags.addChangeListener(_ -> {
            textFieldNoOfBags.setEnabled(!chckbxExcludeNoOfBags.isSelected());
            lblNoOfBags.setVisible(!chckbxExcludeNoOfBags.isSelected());
            textFieldNoOfBags.setVisible(!chckbxExcludeNoOfBags.isSelected());
            lblBagDeductionOrReductionCost.setVisible(!chckbxExcludeNoOfBags.isSelected());
            textFieldDeductionOrPerCost.setVisible(!chckbxExcludeNoOfBags.isSelected());
            label_5.setVisible(!chckbxExcludeNoOfBags.isSelected());
            clear();
        });
        chckbxExcludeNoOfBags.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeNoOfBags.setFocusable(false);
        chckbxExcludeNoOfBags.setEnabled(false);
        chckbxExcludeNoOfBags.setBackground(new Color(0, 255, 127));
        chckbxExcludeNoOfBags.setBounds(10, 290, 145, 25);
        panelSettings1.add(chckbxExcludeNoOfBags);

        chckbxExcludeDcNo = new JCheckBox("Exclude DC No");
        chckbxExcludeDcNo.addChangeListener(_ -> {
            lblDcNo.setVisible(!chckbxExcludeDcNo.isSelected());
            textFieldDcNo.setVisible(!chckbxExcludeDcNo.isSelected());
            textFieldDcDate.setVisible(!chckbxExcludeDcNo.isSelected());
            btnGetDcDetails.setVisible(!chckbxExcludeDcNo.isSelected());
            clear();
        });
        chckbxExcludeDcNo.setEnabled(false);
        chckbxExcludeDcNo.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeDcNo.setFocusable(false);
        chckbxExcludeDcNo.setBackground(new Color(0, 255, 127));
        chckbxExcludeDcNo.setBounds(10, 250, 145, 25);
        panelSettings1.add(chckbxExcludeDcNo);

        chckbxPrinterCopyDialog = new JCheckBox("Printer Copy Dialog");
        chckbxPrinterCopyDialog.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxPrinterCopyDialog.setEnabled(false);
        chckbxPrinterCopyDialog.setBackground(new Color(0, 255, 127));
        chckbxPrinterCopyDialog.setBounds(865, 190, 160, 25);
        chckbxPrinterCopyDialog.addChangeListener(_ -> textFieldNoOfCopies.setEnabled(!chckbxPrinterCopyDialog.isSelected()));
        panelSettings1.add(chckbxPrinterCopyDialog);

        JButton btnRefreshWeight = new JButton("Refresh Weight");
        btnRefreshWeight.addActionListener(_ -> {
            if (comPort != null) {
                comPort.removeDataListener();
                comPort.closePort();
                comPort = null;
            }
            initializeWeights();
        });
        btnRefreshWeight.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnRefreshWeight.setFocusable(false);
        btnRefreshWeight.setBounds(900, 240, 175, 25);
        panelSettings1.add(btnRefreshWeight);

        JLabel lblReport = new JLabel("Report");
        lblReport.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblReport.setBounds(845, 110, 100, 25);
        panelSettings1.add(lblReport);

        comboBoxReport = new JComboBox<>();
        comboBoxReport.setModel(new DefaultComboBoxModel<>(new String[]{
                "Mani & Co",
                "Standard"
        }));
        comboBoxReport.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxReport.setFocusable(false);
        comboBoxReport.setBounds(969, 110, 276, 30);
        panelSettings1.add(comboBoxReport);

        chckbxExcludePlaceAndPhoneNumber = new JCheckBox("Exclude Place & Phone#");
        chckbxExcludePlaceAndPhoneNumber.setBounds(10, 230, 165, 25);
        chckbxExcludePlaceAndPhoneNumber.setEnabled(false);
        chckbxExcludePlaceAndPhoneNumber.addChangeListener(_ -> {
            textFieldPlace.setEnabled(!chckbxExcludePlaceAndPhoneNumber.isSelected());
            textFieldPlace.setVisible(!chckbxExcludePlaceAndPhoneNumber.isSelected());
            textFieldPhoneNo.setEnabled(!chckbxExcludePlaceAndPhoneNumber.isSelected());
            textFieldPhoneNo.setVisible(!chckbxExcludePlaceAndPhoneNumber.isSelected());
            lblPlaceAndPhoneNumber.setVisible(!chckbxExcludePlaceAndPhoneNumber.isSelected());
            clear();
        });
        chckbxExcludePlaceAndPhoneNumber.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludePlaceAndPhoneNumber.setFocusable(false);
        chckbxExcludePlaceAndPhoneNumber.setBackground(new Color(0, 255, 127));
        panelSettings1.add(chckbxExcludePlaceAndPhoneNumber);

        chckbxExcludeCredit = new JCheckBox("Exclude Credit");
        chckbxExcludeCredit.setFocusable(false);
        chckbxExcludeCredit.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeCredit.setEnabled(false);
        chckbxExcludeCredit.addChangeListener(_ -> {
            chckbxIsCredit.setVisible(!chckbxExcludeCredit.isSelected());
            clear();
        });
        chckbxExcludeCredit.setBackground(new Color(0, 255, 127));
        chckbxExcludeCredit.setBounds(170, 210, 139, 25);
        panelSettings1.add(chckbxExcludeCredit);

        JLabel lblTransporter = new JLabel("Transporter");
        lblTransporter.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        lblTransporter.setBounds(1066, 327, 111, 25);
        panelSettings1.add(lblTransporter);

        JScrollPane scrollPaneTransporter = new JScrollPane();
        scrollPaneTransporter.setBounds(1066, 355, 179, 250);
        panelSettings1.add(scrollPaneTransporter);

        tableTransporter = new JTable();
        tableTransporter.putClientProperty("terminateEditOnFocusLost", true);
        tableTransporter.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{
                        "Transporter Name",
                }));
        tableTransporter.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        scrollPaneTransporter.setViewportView(tableTransporter);

        JButton btnDeleteTransporter = new JButton("-");
        btnDeleteTransporter.addActionListener(_ -> {
            if (tableTransporter.getSelectedRow() != -1) {
                ((DefaultTableModel) tableTransporter.getModel()).removeRow(tableTransporter.getSelectedRow());
            }
        });
        btnDeleteTransporter.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteTransporter.setFocusable(false);
        btnDeleteTransporter.setBounds(1204, 319, 41, 38);
        panelSettings1.add(btnDeleteTransporter);

        JButton btnAddTransporter = new JButton("+");
        btnAddTransporter.addActionListener(_ -> {
            DefaultTableModel model = (DefaultTableModel) tableTransporter.getModel();
            model.addRow(new Object[]{
                    ""
            });
        });
        btnAddTransporter.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnAddTransporter.setFocusable(false);
        btnAddTransporter.setBounds(1152, 319, 41, 38);
        panelSettings1.add(btnAddTransporter);

        chckbxExcludeVehicleType = new JCheckBox("Exclude Vehicle Type");
        chckbxExcludeVehicleType.addChangeListener(_ -> {
            lblVehicleType.setVisible(!chckbxExcludeVehicleType.isSelected());
            comboBoxVehicleType.setVisible(!chckbxExcludeVehicleType.isSelected());
            clear();
        });
        chckbxExcludeVehicleType.setFocusable(false);
        chckbxExcludeVehicleType.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        chckbxExcludeVehicleType.setEnabled(false);
        chckbxExcludeVehicleType.setBackground(new Color(0, 255, 127));
        chckbxExcludeVehicleType.setBounds(170, 230, 175, 23);
        panelSettings1.add(chckbxExcludeVehicleType);

        JLabel lblOperator = new JLabel("Operator");
        lblOperator.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblOperator.setBounds(845, 140, 100, 25);
        panelSettings1.add(lblOperator);

        comboBoxOperator = new JComboBox<>();
        comboBoxOperator.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxOperator.setEditable(true);
        comboBoxOperator.setBounds(969, 140, 276, 30);
        panelSettings1.add(comboBoxOperator);

        chckbxInvoice = new JCheckBox("Invoice");
        chckbxInvoice.addActionListener(_ -> {
            if (chckbxInvoice.isSelected()) {
                chckbxInvoice.setSelected(false);
                JPasswordField password = new JPasswordField(10);
                password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {
                        "Enter",
                        "Cancel"
                };
                panel.add(new JLabel("Please Enter the Invoice Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = INVOICE_PASSWORD.toCharArray();
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                chckbxInvoice.setSelected(isCorrect);
            }
        });
        chckbxInvoice.addChangeListener(_ -> {
            if (chckbxInvoice.isSelected()) {
                tabbedPane.setEnabledAt(2, true);
                tabbedPane.setTitleAt(2, "         Invoice         ");
            } else {
                tabbedPane.setEnabledAt(2, false);
                tabbedPane.setTitleAt(2, "");
            }
        });
        chckbxInvoice.setEnabled(false);
        chckbxInvoice.setActionCommand("Invoice");
        chckbxInvoice.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxInvoice.setFocusable(false);
        chckbxInvoice.setBackground(new Color(0, 255, 127));
        chckbxInvoice.setBounds(638, 100, 200, 25);
        panelSettings1.add(chckbxInvoice);

        JButton btnResetInvoiceNo = new JButton("Reset Invoice No");
        btnResetInvoiceNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        btnResetInvoiceNo.setFocusable(false);
        btnResetInvoiceNo.setBounds(900, 270, 175, 25);
        btnResetInvoiceNo.addActionListener(_ -> {
            JPasswordField password = new JPasswordField(10);
            password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
            JPanel panel = new JPanel();
            String[] ConnectOptionNames = {
                    "Enter",
                    "Cancel"
            };
            panel.add(new JLabel("Please the Password ? "));
            panel.add(password);
            JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
            char[] temp = password.getPassword();
            boolean isCorrect;
            char[] correctPassword = RESET_PASSWORD.toCharArray();
            if (temp.length != correctPassword.length) {
                isCorrect = false;
            } else {
                isCorrect = Arrays.equals(temp, correctPassword);
            }
            if (isCorrect) {
                String response = JOptionPane.showInputDialog(null, "Please Enter the Starting Invoice No ?", "Invoice No", JOptionPane.QUESTION_MESSAGE);
                if (response == null || Integer.parseInt(0 + response.replaceAll("\\D", "")) == 0) {
                    JOptionPane.showMessageDialog(null, "Reset Failed ", "Value Entered is not correct", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        PreparedStatement stmts = dbConnection.prepareStatement("CREATE TABLE INVOICES_" + DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss").format(LocalDateTime.now()) + " AS TABLE INVOICES");
                        stmts.executeUpdate();
                        stmts = dbConnection.prepareStatement("TRUNCATE TABLE INVOICES");
                        stmts.executeUpdate();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                        rs.absolute(1);
                        rs.updateInt("INVOICE_NO", Integer.parseInt(response.replaceAll("\\D", "")));
                        rs.updateRow();
                    } catch (SQLException ignored) {
                        JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2836", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    settings();
                    JOptionPane.showMessageDialog(null, "Reset Successful ", "Reset Successful", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Password ", "Value Entered the Correct Password", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelSettings1.add(btnResetInvoiceNo);

        JPanel panelSettings2 = new JPanel();
        panelSettings2.setBackground(new Color(0, 255, 127));
        tabbedPane.addTab("", panelSettings2);
        tabbedPane.setEnabledAt(5, false);
        panelSettings2.setLayout(null);

        JLabel lblLine1 = new JLabel("Line 1");
        lblLine1.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblLine1.setBounds(40, 50, 75, 25);
        panelSettings2.add(lblLine1);

        textFieldLine1 = new JTextField();
        textFieldLine1.setToolTipText("");
        textFieldLine1.setText(null);
        textFieldLine1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLine1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldLine1.setDisabledTextColor(Color.BLACK);
        textFieldLine1.setColumns(10);
        textFieldLine1.setBounds(141, 50, 200, 30);
        panelSettings2.add(textFieldLine1);

        JLabel lblLine2 = new JLabel("Line 2");
        lblLine2.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblLine2.setBounds(40, 90, 75, 25);
        panelSettings2.add(lblLine2);

        textFieldLine2 = new JTextField();
        textFieldLine2.setToolTipText("");
        textFieldLine2.setText(null);
        textFieldLine2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLine2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldLine2.setDisabledTextColor(Color.BLACK);
        textFieldLine2.setColumns(10);
        textFieldLine2.setBounds(141, 90, 200, 30);
        panelSettings2.add(textFieldLine2);

        JLabel lblLine3 = new JLabel("Line 3");
        lblLine3.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblLine3.setBounds(40, 130, 75, 25);
        panelSettings2.add(lblLine3);

        textFieldLine3 = new JTextField();
        textFieldLine3.setText(null);
        textFieldLine3.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLine3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldLine3.setDisabledTextColor(Color.BLACK);
        textFieldLine3.setColumns(10);
        textFieldLine3.setBounds(141, 130, 200, 30);
        panelSettings2.add(textFieldLine3);

        textFieldSiteAt = new JTextField();
        textFieldSiteAt.setText(null);
        textFieldSiteAt.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSiteAt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldSiteAt.setDisabledTextColor(Color.BLACK);
        textFieldSiteAt.setColumns(10);
        textFieldSiteAt.setBounds(584, 250, 200, 30);
        panelSettings2.add(textFieldSiteAt);

        textFieldNameOfDepartment = new JTextField();
        textFieldNameOfDepartment.setToolTipText("");
        textFieldNameOfDepartment.setText(null);
        textFieldNameOfDepartment.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNameOfDepartment.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNameOfDepartment.setDisabledTextColor(Color.BLACK);
        textFieldNameOfDepartment.setColumns(10);
        textFieldNameOfDepartment.setBounds(584, 90, 200, 30);
        panelSettings2.add(textFieldNameOfDepartment);

        textFieldNameOfContractor = new JTextField();
        textFieldNameOfContractor.setToolTipText("");
        textFieldNameOfContractor.setText(null);
        textFieldNameOfContractor.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNameOfContractor.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNameOfContractor.setDisabledTextColor(Color.BLACK);
        textFieldNameOfContractor.setColumns(10);
        textFieldNameOfContractor.setBounds(584, 130, 200, 30);
        panelSettings2.add(textFieldNameOfContractor);

        JLabel lblNameOfContractor = new JLabel("Name Of Contractor");
        lblNameOfContractor.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblNameOfContractor.setBounds(393, 130, 193, 25);
        panelSettings2.add(lblNameOfContractor);

        JLabel lblDepartmentName = new JLabel("Name Of Department");
        lblDepartmentName.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblDepartmentName.setBounds(395, 90, 179, 25);
        panelSettings2.add(lblDepartmentName);

        JLabel lblSiteAt = new JLabel("Site At");
        lblSiteAt.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblSiteAt.setBounds(393, 250, 179, 25);
        panelSettings2.add(lblSiteAt);

        textFieldLine4 = new JTextField();
        textFieldLine4.setText(null);
        textFieldLine4.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldLine4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldLine4.setDisabledTextColor(Color.BLACK);
        textFieldLine4.setColumns(10);
        textFieldLine4.setBounds(141, 170, 200, 30);
        panelSettings2.add(textFieldLine4);

        JLabel lblLine = new JLabel("Line 4");
        lblLine.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblLine.setBounds(40, 170, 75, 25);
        panelSettings2.add(lblLine);

        JLabel lblSriPathySettings = new JLabel("Contractor Settings");
        lblSriPathySettings.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        lblSriPathySettings.setBounds(40, 10, 227, 25);
        panelSettings2.add(lblSriPathySettings);

        chckbxTareNoSlno = new JCheckBox("Tare no SlNo");
        chckbxTareNoSlno.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxTareNoSlno.setFocusable(false);
        chckbxTareNoSlno.setBackground(new Color(0, 255, 127));
        chckbxTareNoSlno.setBounds(1051, 50, 200, 25);
        panelSettings2.add(chckbxTareNoSlno);

        JLabel lblBagsSetting = new JLabel("Settings");
        lblBagsSetting.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        lblBagsSetting.setBounds(40, 270, 150, 25);
        panelSettings2.add(lblBagsSetting);

        JLabel lblBagWeight = new JLabel("Bag Weight (Kg)");
        lblBagWeight.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblBagWeight.setBounds(40, 315, 150, 25);
        panelSettings2.add(lblBagWeight);

        textFieldBagWeight = new JTextField();
        textFieldBagWeight.setText("0");
        textFieldBagWeight.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldBagWeight.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldBagWeight.setDisabledTextColor(Color.BLACK);
        textFieldBagWeight.setColumns(10);
        textFieldBagWeight.setBounds(215, 310, 168, 30);
        panelSettings2.add(textFieldBagWeight);

        chckbxManualStatus = new JCheckBox("Show Status");
        chckbxManualStatus.addChangeListener(_ -> {
            chckbxManualEntry.setVisible(chckbxManualStatus.isSelected());
            chckbxEditEnable.setVisible(chckbxManualStatus.isSelected());
        });
        chckbxManualStatus.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxManualStatus.setFocusable(false);
        chckbxManualStatus.setBackground(new Color(0, 255, 127));
        chckbxManualStatus.setBounds(1051, 531, 198, 25);
        panelSettings2.add(chckbxManualStatus);

        chckbxNeedLogin = new JCheckBox("Need Login");
        chckbxNeedLogin.setSelected(true);
        chckbxNeedLogin.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxNeedLogin.setFocusable(false);
        chckbxNeedLogin.setBackground(new Color(0, 255, 127));
        chckbxNeedLogin.setBounds(1051, 504, 200, 25);
        panelSettings2.add(chckbxNeedLogin);

        chckbxTakeBackup = new JCheckBox("Take Backup");
        chckbxTakeBackup.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxTakeBackup.setFocusable(false);
        chckbxTakeBackup.setBackground(new Color(0, 255, 127));
        chckbxTakeBackup.setBounds(1051, 558, 200, 25);
        chckbxTakeBackup.addActionListener(_ -> takeBackup = chckbxTakeBackup.isSelected());
        panelSettings2.add(chckbxTakeBackup);

        JLabel lbleveryhrs = new JLabel("(every 6hrs)");
        lbleveryhrs.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lbleveryhrs.setBounds(1076, 581, 179, 25);
        panelSettings2.add(lbleveryhrs);

        textFieldTitle3 = new JTextField();
        textFieldTitle3.setToolTipText("Only 30 letters");
        textFieldTitle3.setText(null);
        textFieldTitle3.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldTitle3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldTitle3.setDisabledTextColor(Color.BLACK);
        textFieldTitle3.setColumns(10);
        textFieldTitle3.setBounds(141, 210, 200, 30);
        panelSettings2.add(textFieldTitle3);

        JLabel lblTitle = new JLabel("Title 3");
        lblTitle.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblTitle.setBounds(40, 210, 75, 25);
        panelSettings2.add(lblTitle);

        JLabel lblOptions = new JLabel("Options");
        lblOptions.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        lblOptions.setBounds(1051, 10, 150, 25);
        panelSettings2.add(lblOptions);

        chckbxIceWater = new JCheckBox("Ice water/Freight");
        chckbxIceWater.setEnabled(false);
        chckbxIceWater.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxIceWater.addChangeListener(_ -> {
            chckbxRoundOff.setEnabled(!chckbxIceWater.isSelected());
            if (chckbxIceWater.isSelected()) {
                chckbxRoundOff.setSelected(false);
                lblDriversName.setText("Party's City");
                lblCustomerName.setText("Party's Name");
                lblBagDeductionOrReductionCost.setText("Ice/Water Less");
                lblCharges.setText("Rate");
                lblNoOfBags.setText("Freight Charges");
                chckbxExcludeCustomer.setEnabled(false);
                chckbxExcludeCustomer.setSelected(false);
                chckbxExcludeCharges.setEnabled(false);
                chckbxExcludeCharges.setSelected(false);
                chckbxAutoCharges.setEnabled(false);
                chckbxAutoCharges.setSelected(false);
                chckbxExcludeNoOfBags.setEnabled(false);
                chckbxExcludeNoOfBags.setSelected(false);
                chckbxExcludeDrivers.setEnabled(false);
                chckbxExcludeDrivers.setSelected(false);
            } else {
                lblDriversName.setText("Transporter's Name");
                lblCustomerName.setText("Customer's Name");
                lblBagDeductionOrReductionCost.setText(chckbxRoundOff.isSelected() ? "Price (per kg)" : "Bag Deduction");
                lblCharges.setText("Charges");
                lblNoOfBags.setText("No Of Bags");
                if (Objects.equals(btnUnlock.getText(), "Lock")) {
                    chckbxExcludeCustomer.setEnabled(true);
                    chckbxExcludeCharges.setEnabled(true);
                    chckbxAutoCharges.setEnabled(true);
                    chckbxExcludeNoOfBags.setEnabled(!chckbxRoundOff.isSelected());
                    chckbxExcludeDrivers.setEnabled(true);
                }
            }
            lblFinalWt.setVisible(chckbxIceWater.isSelected());
            textFieldFinalWt.setVisible(chckbxIceWater.isSelected());
            label_6.setVisible(chckbxIceWater.isSelected());
            textFieldFinalAmount.setVisible(chckbxIceWater.isSelected());
            textFieldDeductionOrPerCost.setEnabled(chckbxIceWater.isSelected());
            clear();
        });
        chckbxIceWater.setFocusable(false);
        chckbxIceWater.setBackground(new Color(0, 255, 127));
        chckbxIceWater.setBounds(1051, 75, 200, 25);
        panelSettings2.add(chckbxIceWater);

        chckbxTareToken = new JCheckBox("Tare Token");
        chckbxTareToken.addChangeListener(_ -> {
            btnPrintToken.setVisible(chckbxTareToken.isSelected());
            if (chckbxTareToken.isSelected()) {
                rdbtnTare.setText("Token");
            } else {
                rdbtnTare.setText("Tare");
            }
        });
        chckbxTareToken.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxTareToken.setFocusable(false);
        chckbxTareToken.setBackground(new Color(0, 255, 127));
        chckbxTareToken.setBounds(1051, 125, 200, 25);
        panelSettings2.add(chckbxTareToken);

        chckbxExitPass = new JCheckBox("Exit Pass");
        chckbxExitPass.addChangeListener(_ -> btnPrintExitPass.setVisible(chckbxExitPass.isSelected()));
        chckbxExitPass.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxExitPass.setFocusable(false);
        chckbxExitPass.setBackground(new Color(0, 255, 127));
        chckbxExitPass.setBounds(1051, 150, 200, 25);
        panelSettings2.add(chckbxExitPass);

        JLabel lblOperations = new JLabel("Operations");
        lblOperations.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        lblOperations.setBounds(1051, 467, 150, 25);
        panelSettings2.add(lblOperations);

        chckbxRoundOff = new JCheckBox("Round Off");
        chckbxRoundOff.setEnabled(false);
        chckbxRoundOff.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        chckbxRoundOff.setFocusable(false);
        chckbxRoundOff.addChangeListener(_ -> {
            chckbxIceWater.setEnabled(!chckbxRoundOff.isSelected());
            chckbxExcludeNoOfBags.setEnabled(!chckbxRoundOff.isSelected());
            chckbxTareToken.setEnabled(!chckbxRoundOff.isSelected());
            chckbxExitPass.setEnabled(!chckbxRoundOff.isSelected());
            textFieldRoundOff.setVisible(chckbxRoundOff.isSelected());
            chckbxAutoCharges.setEnabled(!chckbxRoundOff.isSelected());
            chckbxExcludePlaceAndPhoneNumber.setEnabled(!chckbxRoundOff.isSelected());
            if (chckbxRoundOff.isSelected()) {
                lblBagDeductionOrReductionCost.setText("Price (per kg)");
                chckbxExcludePlaceAndPhoneNumber.setSelected(false);
                chckbxExcludePlaceAndPhoneNumber.setEnabled(false);
                chckbxIceWater.setSelected(false);
                chckbxExcludeNoOfBags.setSelected(true);
                chckbxAutoCharges.setSelected(true);
                chckbxTareToken.setSelected(true);
                chckbxExitPass.setSelected(true);
                lblBagDeductionOrReductionCost.setVisible(true);
                textFieldDeductionOrPerCost.setVisible(true);
            }
            clear();
        });
        chckbxRoundOff.setBackground(new Color(0, 255, 127));
        chckbxRoundOff.setBounds(1051, 100, 200, 25);
        panelSettings2.add(chckbxRoundOff);

        JLabel lblRoundOffDecimals = new JLabel("Round Off decimals");
        lblRoundOffDecimals.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblRoundOffDecimals.setBounds(40, 355, 173, 25);
        panelSettings2.add(lblRoundOffDecimals);

        textFieldRoundOffDecimals = new JTextField();
        textFieldRoundOffDecimals.setText("0");
        textFieldRoundOffDecimals.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldRoundOffDecimals.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldRoundOffDecimals.setDisabledTextColor(Color.BLACK);
        textFieldRoundOffDecimals.setColumns(10);
        textFieldRoundOffDecimals.setBounds(215, 350, 168, 30);
        panelSettings2.add(textFieldRoundOffDecimals);

        JLabel lblNameOfWork = new JLabel("Name Of Work");
        lblNameOfWork.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblNameOfWork.setBounds(393, 50, 193, 25);
        panelSettings2.add(lblNameOfWork);

        textFieldNameOfWork = new JTextField();
        textFieldNameOfWork.setToolTipText("");
        textFieldNameOfWork.setText(null);
        textFieldNameOfWork.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNameOfWork.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldNameOfWork.setDisabledTextColor(Color.BLACK);
        textFieldNameOfWork.setColumns(10);
        textFieldNameOfWork.setBounds(584, 50, 200, 30);
        panelSettings2.add(textFieldNameOfWork);

        JLabel lblAgreementNo = new JLabel("Agreement No");
        lblAgreementNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblAgreementNo.setBounds(393, 170, 193, 25);
        panelSettings2.add(lblAgreementNo);

        textFieldAgreementNo = new JTextField();
        textFieldAgreementNo.setToolTipText("");
        textFieldAgreementNo.setText(null);
        textFieldAgreementNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldAgreementNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldAgreementNo.setDisabledTextColor(Color.BLACK);
        textFieldAgreementNo.setColumns(10);
        textFieldAgreementNo.setBounds(584, 170, 200, 30);
        panelSettings2.add(textFieldAgreementNo);

        JLabel lblEstimateNo = new JLabel("Estimate No");
        lblEstimateNo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblEstimateNo.setBounds(393, 210, 179, 25);
        panelSettings2.add(lblEstimateNo);

        textFieldEstimateNo = new JTextField();
        textFieldEstimateNo.setText(null);
        textFieldEstimateNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldEstimateNo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldEstimateNo.setDisabledTextColor(Color.BLACK);
        textFieldEstimateNo.setColumns(10);
        textFieldEstimateNo.setBounds(584, 210, 200, 30);
        panelSettings2.add(textFieldEstimateNo);

        JLabel lblOperators = new JLabel("Operators");
        lblOperators.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        lblOperators.setBounds(708, 328, 111, 25);
        panelSettings2.add(lblOperators);

        JButton btnAddOperators = new JButton("+");
        btnAddOperators.addActionListener(_ -> {
            DefaultTableModel model = (DefaultTableModel) tableOperators.getModel();
            model.addRow(new Object[]{
                    ""
            });
        });
        btnAddOperators.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnAddOperators.setFocusable(false);
        btnAddOperators.setBounds(806, 320, 41, 38);
        panelSettings2.add(btnAddOperators);

        JButton btnDeleteOperators = new JButton("-");
        btnDeleteOperators.addActionListener(_ -> {
            if (tableOperators.getSelectedRow() != -1) {
                ((DefaultTableModel) tableOperators.getModel()).removeRow(tableOperators.getSelectedRow());
            }
        });
        btnDeleteOperators.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteOperators.setFocusable(false);
        btnDeleteOperators.setBounds(846, 320, 41, 38);
        panelSettings2.add(btnDeleteOperators);

        JScrollPane scrollPaneOperators = new JScrollPane();
        scrollPaneOperators.setBounds(708, 356, 179, 250);
        panelSettings2.add(scrollPaneOperators);

        tableOperators = new JTable();
        tableOperators.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Operator"
                }
        ));
        tableOperators.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        scrollPaneOperators.setViewportView(tableOperators);

        JLabel lblVehicleTypes = new JLabel("Vehicle Types");
        lblVehicleTypes.setFont(new Font("Times New Roman", Font.ITALIC, 17));
        lblVehicleTypes.setBounds(395, 328, 111, 25);
        panelSettings2.add(lblVehicleTypes);

        JButton btnAddVehicleType = new JButton("+");
        btnAddVehicleType.addActionListener(_ -> {
            DefaultTableModel model = (DefaultTableModel) tableVehicleTypes.getModel();
            model.addRow(new Object[]{
                    "", 0, 0
            });
        });
        btnAddVehicleType.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnAddVehicleType.setFocusable(false);
        btnAddVehicleType.setBounds(600, 320, 41, 38);
        panelSettings2.add(btnAddVehicleType);

        JButton btnDeleteVehicleType = new JButton("-");
        btnDeleteVehicleType.addActionListener(_ -> {
            if (tableVehicleTypes.getSelectedRow() != -1) {
                ((DefaultTableModel) tableVehicleTypes.getModel()).removeRow(tableVehicleTypes.getSelectedRow());
            }
        });
        btnDeleteVehicleType.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDeleteVehicleType.setFocusable(false);
        btnDeleteVehicleType.setBounds(640, 320, 41, 38);
        panelSettings2.add(btnDeleteVehicleType);

        JScrollPane scrollPaneVehicleType = new JScrollPane();
        scrollPaneVehicleType.setBounds(395, 356, 287, 250);
        panelSettings2.add(scrollPaneVehicleType);

        tableVehicleTypes = new JTable();
        tableVehicleTypes.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Vehicle Type", "Tare", "Gross"
                }
        ));
        tableVehicleTypes.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        scrollPaneVehicleType.setViewportView(tableVehicleTypes);

        JLabel lblInvoiceProperty = new JLabel("Invoice Property");
        lblInvoiceProperty.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        lblInvoiceProperty.setBounds(40, 391, 173, 25);
        panelSettings2.add(lblInvoiceProperty);

        comboBoxInvoiceProperty = new JComboBox<>();
        comboBoxInvoiceProperty.setModel(new DefaultComboBoxModel<>(invoiceProperties));
        comboBoxInvoiceProperty.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        comboBoxInvoiceProperty.setFocusable(false);
        comboBoxInvoiceProperty.setBounds(215, 391, 168, 30);
        panelSettings2.add(comboBoxInvoiceProperty);

        JButton button = new JButton("Minimize");
        button.addActionListener(_ -> babulensWeighbridgeDesigned.setState(Frame.ICONIFIED));
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setFocusable(false);
        button.setBounds(518, 11, 117, 30);
        babulensWeighbridgeDesigned.getContentPane().add(button);
    }

    private void print() {
        for (int i = 0; i < noOfCopies; i++) {
            switch (Objects.toString(comboBoxPrintOptionForWeight.getSelectedItem(), "")) {
                case "Pre Print":
                    printPreWeight(createTextPanePreWeight(), 1.25d);
                    break;
                case "Pre Print 2":
                    printPreWeight(createTextPanePreWeight2(), 1.25d);
                    break;
                case "Pre Print 3":
                    printPreWeight(createTextPanePreWeight3(), 1.25d);
                    break;
                case "Pre Print 4":
                    printPreWeight(createTextPanePreWeight4(), 1.18d);
                    break;
                case "Pre Print 5":
                    printPreWeight(createTextPanePreWeight5(), 1.18d);
                    break;
                case "Camera":
                    printCameraWeight();
                    continue;
                case "Camera * 2":
                    printCameraWeight2();
                    continue;
                case "Plain Camera":
                    printPlainCameraWeight();
                    continue;
                case "Plain Camera * 2":
                    printPlainCameraWeight2();
                    continue;
                case "Sri Pathy":
                    printPlainSriPathyWeight();
                    continue;
                case "No Of Bags":
                    printPlainNoOfBagsWeight();
                    continue;
                case "Standard":
                    printStandard();
                    continue;
                case "Ice Water":
                    printIceWater();
                    continue;
                case "EMJAY":
                    printEmjay();
                    break;
                case "EMJAY 1":
                    printEmjay1();
                    break;
                case "Mani & Co":
                    printManiAndCo();
                    break;
                case "Mani & Co 1":
                    printManiAndCo1();
                    continue;
                case "Mani & Co 2":
                    printManiAndCo2();
                    break;
                case "Plain Paper A4":
                    printPlainWeightA4();
                    continue;
                case "Plain Paper A4 without header":
                    printPlainWeightA4WithoutHeader();
                    continue;
                case "Quotation":
                    printQuotation();
                    continue;
                case "Quotation KJJ":
                    printQuotationKJJ();
                    continue;
                default:
                    printPlainWeight();
            }
        }
    }

    private void refreshSlNo() {
        try {
            int serialNo;
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING ORDER BY SLNO DESC limit 1");
            if (rs.isBeforeFirst()) {
                rs.absolute(1);
                serialNo = rs.getInt("SLNO");
            } else {
                serialNo = 0;
            }
            rs = stmt.executeQuery("SELECT * FROM SETTINGS");
            rs.absolute(1);
            rs.updateInt("SLNO", serialNo + 1);
            rs.updateRow();
            clear();
        } catch (SQLException ignored) {
        }
    }

    private void getReport() {
        String date1,
                date2,
                textField,
                material;
        int charges = 0,
                netWt = 0,
                serialNo;
        Date dateTemp12;
        if (rdbtnWeighing.isSelected()) {
            String temp = "SELECT * FROM WEIGHING";
            switch (Objects.toString(comboBoxReportType.getSelectedItem(), "")) {
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
                    serialNo = Integer.parseInt(0 + textFieldReportTextBox.getText().replaceAll("\\D", ""));
                    temp = "SELECT * FROM WEIGHING WHERE SLNO >= " + serialNo;
                    break;
                case "Vehiclewise Report":
                    textField = textFieldReportTextBox.getText();
                    dateTemp12 = datePicker1.getDate();
                    date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    dateTemp12 = datePicker2.getDate();
                    date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    temp = "SELECT * FROM WEIGHING WHERE UPPER(VEHICLENO) LIKE UPPER('%" + textField + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                    break;
                case "Materialwise Report":
                    dateTemp12 = datePicker1.getDate();
                    date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    dateTemp12 = datePicker2.getDate();
                    date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    material = (String) comboBoxMaterialName.getSelectedItem();
                    if (material == null) {
                        material = "";
                    }
                    temp = "SELECT * FROM WEIGHING WHERE UPPER(MATERIAL) LIKE UPPER('%" + material + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                    break;
                case "Customer - Materialwise Report":
                    dateTemp12 = datePicker1.getDate();
                    date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    dateTemp12 = datePicker2.getDate();
                    date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    textField = textFieldReportTextBox.getText();
                    material = (String) comboBoxMaterialName.getSelectedItem();
                    if (material == null) {
                        material = "";
                    }
                    temp = "SELECT * FROM WEIGHING WHERE UPPER(CUSTOMERNAME) LIKE UPPER('%" + textField + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "' AND UPPER(MATERIAL) LIKE UPPER('%" + material + "%')";
                    break;
                case "Customerwise Report":
                    dateTemp12 = datePicker1.getDate();
                    date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    dateTemp12 = datePicker2.getDate();
                    date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    textField = textFieldReportTextBox.getText();
                    temp = "SELECT * FROM WEIGHING WHERE UPPER(CUSTOMERNAME) LIKE UPPER('%" + textField + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                    break;
                case "Transporterwise Report":
                    dateTemp12 = datePicker1.getDate();
                    date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    dateTemp12 = datePicker2.getDate();
                    date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    textField = textFieldReportTextBox.getText();
                    temp = "SELECT * FROM WEIGHING WHERE UPPER(DRIVERNAME) LIKE UPPER('%" + textField + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                    break;
                case "Operatorwise Report":
                    dateTemp12 = datePicker1.getDate();
                    date1 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    dateTemp12 = datePicker2.getDate();
                    date2 = (new java.sql.Date(dateTemp12.getTime())).toString();
                    textField = textFieldReportTextBox.getText();
                    temp = "SELECT * FROM WEIGHING WHERE UPPER(OPERATOR) LIKE UPPER('%" + textField + "%') AND NETDATE BETWEEN '" + date1 + "' AND '" + date2 + "'";
                    break;
            }
            try {
                tableReport.setModel(new TableReport(
                        new Object[][]{},
                        new String[]{
                                "Edit/Save",
                                "Sl.No",
                                "Dc. No",
                                "Dc. Date",
                                chckbxIceWater.isSelected() ? "Party's Name" : "Customer's Name",
                                chckbxIceWater.isSelected() ? "Party's City" : "Transporter's Name",
                                "Vehicle No",
                                "Vehicle Type",
                                "Operator",
                                "Place",
                                "Phone No",
                                "Material",
                                chckbxIceWater.isSelected() ? "Freight Charges" : "No Of Bags",
                                chckbxIceWater.isSelected() ? "Rate" : "Charges",
                                "Credit",
                                "Gross Wt",
                                "Gross Date & Time",
                                "Tare Wt",
                                "Tare Date & Time",
                                chckbxIceWater.isSelected() ? "Ice/Water Less" : chckbxRoundOff.isSelected() ? "Price (per kg)" : "Bag Deduction",
                                "Round off",
                                "Nett Wt",
                                "Nett Date & Time",
                                "Final Wt",
                                "Final Amount",
                                "Remarks",
                                "Manual"
                        }));
                DefaultTableModel model = (DefaultTableModel) tableReport.getModel();
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery(temp + " ORDER BY SLNO LIMIT 1000");
                while (rs.next()) {
                    String date,
                            time,
                            gross,
                            tare,
                            net;
                    date = String.valueOf(rs.getDate("GROSSDATE"));
                    if (date.equals("null")) {
                        date = "";
                    } else {
                        date = dateAndTimeFormatdate.format(rs.getDate("GROSSDATE"));
                    }
                    time = String.valueOf(rs.getTime("GROSSTIME"));
                    if (time.equals("null")) {
                        time = "";
                    } else {
                        time = dateAndTimeFormattime.format(rs.getTime("GROSSTIME"));
                    }
                    gross = date + " " + time;
                    date = String.valueOf(rs.getDate("TAREDATE"));
                    if (date.equals("null")) {
                        date = "";
                    } else {
                        date = dateAndTimeFormatdate.format(rs.getDate("TAREDATE"));
                    }
                    time = String.valueOf(rs.getTime("TARETIME"));
                    if (time.equals("null")) {
                        time = "";
                    } else {
                        time = dateAndTimeFormattime.format(rs.getTime("TARETIME"));
                    }
                    tare = date + " " + time;
                    date = String.valueOf(rs.getDate("NETDATE"));
                    if (date.equals("null")) {
                        date = "";
                    } else {
                        date = dateAndTimeFormatdate.format(rs.getDate("NETDATE"));
                    }
                    time = String.valueOf(rs.getTime("NETTIME"));
                    if (time.equals("null")) {
                        time = "";
                    } else {
                        time = dateAndTimeFormattime.format(rs.getTime("NETTIME"));
                    }
                    net = date + " " + time;

                    model.addRow(new Object[]{
                            /*00*/
                            "Edit",
                            /*01*/
                            rs.getInt("SLNO"),
                            /*02*/
                            rs.getString("DCNO"),
                            /*03*/
                            (String.valueOf(rs.getDate("DCNODATE"))).equals("null") ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")),
                            /*04*/
                            rs.getString("CUSTOMERNAME"),
                            /*05*/
                            rs.getString("DRIVERNAME"),
                            /*06*/
                            rs.getString("VEHICLENO"),
                            /*07*/
                            rs.getString("VEHICLE_TYPE"),
                            /*08*/
                            rs.getString("OPERATOR"),
                            /*09*/
                            rs.getString("PLACE"),
                            /*10*/
                            rs.getString("PHONE_NUMBER"),
                            /*11*/
                            rs.getString("MATERIAL"),
                            /*12*/
                            rs.getInt("NOOFBAGS"),
                            /*13*/
                            decimalFormat.format(rs.getDouble("CHARGES")),
                            /*14*/
                            rs.getBoolean("CREDIT"),
                            /*15*/
                            rs.getInt("GROSSWT"),
                            /*16*/
                            gross,
                            /*17*/
                            rs.getInt("TAREWT"),
                            /*18*/
                            tare,
                            /*19*/
                            decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")),
                            /*20*/
                            decimalFormat.format(rs.getDouble("ROUND_OFF")),
                            /*21*/
                            rs.getInt("NETWT"),
                            /*22*/
                            net,
                            /*23*/
                            rs.getInt("FINALWT"),
                            /*24*/
                            rs.getInt("FINALAMOUNT"),
                            /*25*/
                            rs.getString("REMARKS"),
                            /*26*/
                            rs.getBoolean("MANUAL")
                    });
                    charges += (int) rs.getDouble("CHARGES");
                    netWt += rs.getInt("NETWT");
                }
                tableReport.getColumnModel().getColumn(0).setCellRenderer(new TableButtonRenderer());
                tableReport.getColumnModel().getColumn(0).setCellEditor(new TableRenderer(new JCheckBox()));
                if (!chckbxEditEnable.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Edit/Save"));
                }
                if (!chckbxSlNo.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Sl.No"));
                }
                if (!chckbxDCNo.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Dc. No"));
                }
                if (!chckbxDCDate.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Dc. Date"));
                }
                if (!chckbxCustomerName.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn(chckbxIceWater.isSelected() ? "Party's Name" : "Customer's Name"));
                }
                if (!chckbxTransporterName.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn(chckbxIceWater.isSelected() ? "Party's City" : "Transporter's Name"));
                }
                if (!chckbxVehicleNo.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Vehicle No"));
                }
                if (!chckbxVehicleType.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Vehicle Type"));
                }
                if (!chckbxOperator.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Operator"));
                }
                if (!chckbxPlace.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Place"));
                }
                if (!chckbxPhoneNo.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Phone No"));
                }
                if (!chckbxMaterial.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Material"));
                }
                if (!chckbxNoOfBags.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn(chckbxIceWater.isSelected() ? "Freight Charges" : "No Of Bags"));
                }
                if (!chckbxCharges.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn(chckbxIceWater.isSelected() ? "Rate" : "Charges"));
                }
                if (!chckbxCredit.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Credit"));
                }
                if (!chckbxGrossWeight.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Gross Wt"));
                }
                if (!chckbxGrossDateAndTime.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Gross Date & Time"));
                }
                if (!chckbxTareWeight.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Tare Wt"));
                }
                if (!chckbxTareDateAndTime.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Tare Date & Time"));
                }
                if (!chckbxBagDeduction.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn(chckbxIceWater.isSelected() ? "Ice/Water Less" : chckbxRoundOff.isSelected() ? "Price (per kg)" : "Bag Deduction"));
                }
                if (!chckbxRoundOffColumn.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Round off"));
                }
                if (!chckbxNettWeight.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Nett Wt"));
                }
                if (!chckbxNettDateAndTime.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Nett Date & Time"));
                }
                if (!chckbxFinalWt.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Final Wt"));
                }
                if (!chckbxFinalAmount.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Final Amount"));
                }
                if (!chckbxRemarks.isSelected()) {
                    tableReport.removeColumn(tableReport.getColumn("Remarks"));
                }
                if (!(chckbxManual.isSelected() && chckbxManualStatus.isSelected())) {
                    tableReport.removeColumn(tableReport.getColumn("Manual"));
                }
                for (int column = 0; column < tableReport.getColumnCount(); column++) {
                    int width = 100;
                    for (int row = 0; row < tableReport.getRowCount(); row++) {
                        TableCellRenderer renderer = tableReport.getCellRenderer(row, column);
                        Component comp = tableReport.prepareRenderer(renderer, row, column);
                        width = Math.max(comp.getPreferredSize().width + 50, width);
                    }
                    if (width > 250) {
                        width = 250;
                    }
                    tableReport.getColumnModel().getColumn(column).setPreferredWidth(width);
                }
            } catch (SQLException ignored) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2174", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        textFieldTotalCharges.setText("Rs. " + charges);
        textFieldtotalNetWt.setText(netWt + " Kg");
        reportOpened = true;
    }

    private void rePrint(String response) {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + response);
            if (rs.next()) {
                textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
                textFieldDcNo.setText(rs.getString("DCNO"));
                textFieldDcDate.setText(rs.getDate("DCNODATE") == null ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")));
                comboBoxCustomerName.setSelectedItem(rs.getString("CUSTOMERNAME"));
                comboBoxTransporterName.setSelectedItem(rs.getString("DRIVERNAME"));
                comboBoxVehicleNo.setSelectedItem(rs.getString("VEHICLENO"));
                comboBoxVehicleType.setSelectedItem(rs.getString("VEHICLE_TYPE"));
                lblOperatorName.setText(rs.getString("OPERATOR"));
                textFieldPlace.setText(rs.getString("PLACE"));
                textFieldPhoneNo.setText(rs.getString("PHONE_NUMBER"));
                comboBoxMaterial.setSelectedItem(rs.getString("MATERIAL"));
                textFieldNoOfBags.setText(Integer.toString(rs.getInt("NOOFBAGS")));
                textFieldCharges.setText(decimalFormat.format(rs.getDouble("CHARGES")));
                chckbxIsCredit.setSelected(rs.getBoolean("CREDIT"));
                textFieldGrossWt.setText(Integer.toString(rs.getInt("GROSSWT")));
                textFieldGrossDateTime.setText(rs.getDate("GROSSDATE") + " " + rs.getTime("GROSSTIME"));
                if (textFieldGrossDateTime.getText().equals("null null")) {
                    textFieldGrossDateTime.setText("");
                } else {
                    textFieldGrossDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldGrossDateTime.getText()).getTime())));
                }
                textFieldTareWt.setText(Integer.toString(rs.getInt("TAREWT")));
                textFieldTareDateTime.setText(rs.getDate("TAREDATE") + " " + rs.getTime("TARETIME"));
                if (textFieldTareDateTime.getText().equals("null null")) {
                    textFieldTareDateTime.setText("");
                } else {
                    textFieldTareDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldTareDateTime.getText()).getTime())));
                }
                textFieldDeductionOrPerCost.setText(decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")));
                textFieldRoundOff.setText(decimalFormat.format(rs.getDouble("ROUND_OFF")));
                textFieldNetWt.setText(Integer.toString(rs.getInt("NETWT")));
                textFieldNetDateTime.setText(rs.getDate("NETDATE") + " " + rs.getTime("NETTIME"));
                textFieldFinalWt.setText(Integer.toString(rs.getInt("FINALWT")));
                textFieldFinalAmount.setText(Integer.toString(rs.getInt("FINALAMOUNT")));
                if (textFieldNetDateTime.getText().equals("null null")) {
                    textFieldNetDateTime.setText("");
                } else {
                    textFieldNetDateTime.setText(dateAndTimeFormat.format(new Date(dateAndTimeFormatSql.parse(textFieldNetDateTime.getText()).getTime())));
                }
                textPaneRemarks.setText(rs.getString("REMARKS"));
                rdbtnGross.setEnabled(false);
                btnGetTareSl.setEnabled(false);
                rdbtnTare.setEnabled(false);
                btnGetGrossSl.setEnabled(false);
                comboBoxVehicleNo.setEnabled(false);
                comboBoxMaterial.setEnabled(false);
                textFieldCharges.setEnabled(false);
                btnGetWeight.setEnabled(false);
                btnSave.setEnabled(false);
                btnPrint.setEnabled(true);
                btnPrintToken.setEnabled(true);
                btnPrintExitPass.setEnabled(true);
                btnGetDcDetails.setEnabled(false);
                btnGetGross.setEnabled(false);
                btnGetTare.setEnabled(false);
                btnTotal.setEnabled(false);
                btnMinusGross.setEnabled(false);
                btnPlusTare.setEnabled(false);
                textPaneRemarks.setEnabled(false);
                comboBoxCustomerName.setEnabled(false);
                comboBoxTransporterName.setEnabled(false);
                textFieldNoOfBags.setEnabled(false);
                textFieldDeductionOrPerCost.setEnabled(false);
                textFieldPlace.setEnabled(false);
                textFieldPhoneNo.setEnabled(false);
                chckbxIsCredit.setEnabled(false);
                btnPrint.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nRECORD NOT FOUND", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
                btnReprint.requestFocus();
            }
        } catch (SQLException | ParseException ignored) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            btnReprint.requestFocus();
        }
    }

    private void requestFocus(String field) {
        switch (field) {
            default:
                if (comboBoxCustomerName.isEnabled() && comboBoxCustomerName.isVisible()) {
                    comboBoxCustomerName.requestFocus();
                    break;
                }
            case "CustomerName":
                if (comboBoxTransporterName.isEnabled() && comboBoxTransporterName.isVisible()) {
                    comboBoxTransporterName.requestFocus();
                    break;
                }
            case "DriverName":
                if (comboBoxVehicleNo.isEnabled() && comboBoxVehicleNo.isVisible()) {
                    comboBoxVehicleNo.requestFocus();
                    break;
                }
            case "VehicleNo":
                if (comboBoxVehicleType.isEnabled() && comboBoxVehicleType.isVisible()) {
                    comboBoxVehicleType.requestFocus();
                    break;
                }
            case "VehicleType":
                if (textFieldPlace.isEnabled() && textFieldPlace.isVisible()) {
                    textFieldPlace.requestFocus();
                    break;
                }
            case "Place":
                if (textFieldPhoneNo.isEnabled() && textFieldPhoneNo.isVisible()) {
                    textFieldPhoneNo.requestFocus();
                    break;
                }
            case "PhoneNo":
                if (comboBoxMaterial.isEnabled() && comboBoxMaterial.isVisible()) {
                    comboBoxMaterial.requestFocus();
                    break;
                }
            case "Material":
                if (textFieldNoOfBags.isEnabled() && textFieldNoOfBags.isVisible()) {
                    textFieldNoOfBags.requestFocus();
                    break;
                }
            case "NoOfBags":
                if (textFieldCharges.isEnabled() && textFieldCharges.isVisible()) {
                    textFieldCharges.requestFocus();
                    break;
                }
            case "Charges":
                if (textFieldDeductionOrPerCost.isEnabled() && textFieldDeductionOrPerCost.isVisible()) {
                    textFieldDeductionOrPerCost.requestFocus();
                    break;
                }
            case "DeductionOrPerCost":
                if (textPaneRemarks.isEnabled() && textPaneRemarks.isVisible()) {
                    textPaneRemarks.requestFocus();
                    break;
                }
            case "Remarks":
                btnGetWeight.requestFocus();
        }
    }

    private void clear(ActionEvent... ae) {
        if (afterStart) {
            try {
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                textFieldSlNo.setText(Integer.toString(rs.getInt("SLNO")));
            } catch (SQLException ignored) {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :2862", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            rdbtnGross.setEnabled(true);
            rdbtnGross.setSelected(true);
            btnGetTareSl.setEnabled(true);
            rdbtnTare.setEnabled(true);
            btnGetGrossSl.setEnabled(true);
            btnMinusGross.setEnabled(true);
            btnPlusTare.setEnabled(true);
            comboBoxVehicleNo.setEnabled(true);
            comboBoxVehicleType.setEnabled(true);
            comboBoxMaterial.setEnabled(true);
            textFieldNoOfBags.setEnabled(!chckbxExcludeNoOfBags.isSelected());
            textFieldNoOfBags.setText("");
            textFieldCharges.setText("");
            textFieldDeductionOrPerCost.setText("0");
            textFieldGrossWt.setText("0");
            textFieldTareWt.setText("0");
            textFieldNetWt.setText("0");
            textFieldFinalWt.setText("0");
            textFieldFinalAmount.setText("0");
            textFieldRoundOff.setText("0");
            textFieldGrossDateTime.setText("");
            textFieldTareDateTime.setText("");
            textFieldNetDateTime.setText("");
            textFieldPlace.setEnabled(true);
            textFieldPlace.setText("");
            textFieldPhoneNo.setEnabled(true);
            textFieldPhoneNo.setText("");
            chckbxIsCredit.setEnabled(true);
            btnSave.setEnabled(false);
            btnPrint.setEnabled(false);
            btnPrintToken.setEnabled(false);
            btnPrintExitPass.setEnabled(false);
            btnGetWeight.setEnabled(true);
            comboBoxCustomerName.setEnabled(!chckbxExcludeCustomer.isSelected());
            comboBoxTransporterName.setEnabled(!chckbxExcludeDrivers.isSelected());
            textPaneRemarks.setEnabled(true);
            chckbxAutoChargecheck.setEnabled(true);
            chckbxAutoChargecheck.setSelected(chckbxAutoCharges.isSelected());
            textFieldCharges.setEnabled(!(chckbxExcludeCharges.isSelected() || chckbxAutoChargecheck.isSelected()));
            textPaneRemarks.setText("");
            textFieldDcNo.setText("");
            textFieldDcDate.setText("");
            btnGetDcDetails.setEnabled(true);
            chckbxIsCredit.setSelected(false);
            if (chckbxManualEntry.isSelected()) {
                btnGetGross.setEnabled(true);
                btnGetTare.setEnabled(true);
            }
            if (chckbxIceWater.isSelected()) {
                textFieldDeductionOrPerCost.setEnabled(true);
            }
            comboBoxCustomerName.setSelectedItem("");
            comboBoxTransporterName.setSelectedItem("");
            comboBoxVehicleNo.setSelectedItem("");
            comboBoxVehicleType.setSelectedItem("");
            comboBoxMaterial.setSelectedItem("");
            requestFocus("");
            lblOperatorName.setText(comboBoxOperator.getEditor().getItem().toString());
        }
    }

    private void printPlainWeightA4() {
        JTextPane textPane = createTextPanePlainWeight();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();

        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 11d * 72d;
        double widthmargin = .50d * 72d;
        double heightmargin = .25d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private void printPlainWeightA4WithoutHeader() {
        JTextPane textPane = createTextPanePlainWeightA4WithoutHeader();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();

        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8.5d * 72d;
        double height = 11d * 72d;
        double widthmargin = 72d;
        double heightmargin = .5d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createTextPanePlainWeightA4WithoutHeader() {
        String format = " %1$-13s: %2$-15s%3$-12s: %4$-20s\n";
        String format11 = "     %1$-9s: ";
        String format12 = "%2$-7s Kg";
        String format13 = "               %3$-20s\n";
        String format2 = " %1$-18s: %2$-30s\n";
        String format3 = "     %1$-9s: %2$s";
        String dc = "";
        String driver = "";
        if (!(textFieldDcNo.getText().trim().isEmpty() && textFieldDcDate.getText().trim().isEmpty())) {
            dc = String.format(format, "Dc. No", textFieldDcNo.getText(), "Dc. Date", textFieldDcDate.getText());
        }
        if (chckbxExcludeDrivers.isSelected() || !comboBoxTransporterName.getEditor().getItem().toString().trim().isEmpty()) {
            driver = String.format(format2, "Transporter's Name", comboBoxTransporterName.getEditor().getItem());
        }
        String[] initString = {
                "\n\n\n\n",
                "-----------------------------------------------------------------\n",
                String.format(format, "Sl.No", textFieldSlNo.getText(), "Date & Time", textFieldNetDateTime.getText()),
                dc,
                String.format(format2, "Customer's Name", comboBoxCustomerName.getEditor().getItem()),
                driver,
                String.format(format, "Vehicle No", comboBoxVehicleNo.getEditor().getItem(), "Material", comboBoxMaterial.getEditor().getItem()),
                "-----------------------------------------------------------------\n",
                String.format(format11, "Gross Wt", StringUtils.leftPad(textFieldGrossWt.getText(), 7, " "), textFieldGrossDateTime.getText()),
                String.format(format12, "Gross Wt", StringUtils.leftPad(textFieldGrossWt.getText(), 7, " "), textFieldGrossDateTime.getText()),
                String.format(format13, "Gross Wt", StringUtils.leftPad(textFieldGrossWt.getText(), 7, " "), textFieldGrossDateTime.getText()),
                String.format(format11, "Tare Wt", StringUtils.leftPad(textFieldTareWt.getText(), 7, " "), textFieldTareDateTime.getText()),
                String.format(format12, "Tare Wt", StringUtils.leftPad(textFieldTareWt.getText(), 7, " "), textFieldTareDateTime.getText()),
                String.format(format13, "Tare Wt", StringUtils.leftPad(textFieldTareWt.getText(), 7, " "), textFieldTareDateTime.getText()),
                String.format(format11, "Net Wt", StringUtils.leftPad(textFieldNetWt.getText(), 7, " "), chckbxExcludeCharges.isSelected() && textFieldCharges.getText().equals("0") ? "" : "Charges : Rs. " + (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                String.format(format12, "Net Wt", StringUtils.leftPad(textFieldNetWt.getText(), 7, " "), chckbxExcludeCharges.isSelected() && textFieldCharges.getText().equals("0") ? "" : "Charges : Rs. " + (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                String.format(format13, "Net Wt", StringUtils.leftPad(textFieldNetWt.getText(), 7, " "), chckbxExcludeCharges.isSelected() && textFieldCharges.getText().equals("0") ? "" : "Charges : Rs. " + (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                chckbxExcludeRemarks.isEnabled() && !Objects.equals(textPaneRemarks.getText(), "") ? "" : String.format(format3, "Remarks", textPaneRemarks.getText()) + "\n",
                "-----------------------------------------------------------------\n",
                StringUtils.rightPad(textFieldFooter.getText(), 50, " ") + "Signature"
        };
        String[] initStyles = {
                "1",
                "2",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "5",
                "3",
                "3",
                "5",
                "3",
                "3",
                "5",
                "3",
                "3",
                "3",
                "4"
        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPlainWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void printPlainWeight() {
        JTextPane textPane = createTextPanePlainWeight();
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
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createTextPanePlainWeight() {
        String format = " %1$-13s: %2$-15s%3$-12s: %4$-20s\n";
        String format1 = "     %1$-9s: %2$-7s Kg               %3$-20s\n";
        String format2 = " %1$-18s: %2$-30s\n";
        String format3 = "     %1$-9s: %2$s";
        String dc = "";
        String driver = "";
        if (!(textFieldDcNo.getText().trim().isEmpty() && textFieldDcDate.getText().trim().isEmpty())) {
            dc = String.format(format, "Dc. No", textFieldDcNo.getText(), "Dc. Date", textFieldDcDate.getText());
        }
        if (chckbxExcludeDrivers.isSelected() || !comboBoxTransporterName.getEditor().getItem().toString().trim().isEmpty()) {
            driver = String.format(format2, "Transporter's Name", comboBoxTransporterName.getEditor().getItem());
        }
        String[] initString = {
                "\n" + StringUtils.center(title1.getText(), 39) + "\n",
                StringUtils.center(title2.getText(), 65) + "\n",
                "-----------------------------------------------------------------\n",
                String.format(format, "Sl.No", textFieldSlNo.getText(), "Date & Time", textFieldNetDateTime.getText()),
                dc,
                String.format(format2, "Customer's Name", comboBoxCustomerName.getEditor().getItem()),
                driver,
                String.format(format, "Vehicle No", comboBoxVehicleNo.getEditor().getItem(), "Material", comboBoxMaterial.getEditor().getItem()),
                "-----------------------------------------------------------------\n",
                String.format(format1, "Gross Wt", StringUtils.leftPad(textFieldGrossWt.getText(), 7, " "), textFieldGrossDateTime.getText()),
                String.format(format1, "Tare Wt", StringUtils.leftPad(textFieldTareWt.getText(), 7, " "), textFieldTareDateTime.getText()),
                String.format(format1, "Net Wt", StringUtils.leftPad(textFieldNetWt.getText(), 7, " "), chckbxExcludeCharges.isSelected() && textFieldCharges.getText().equals("0") ? "" : "Charges : Rs. " + (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                chckbxExcludeRemarks.isEnabled() && !Objects.equals(textPaneRemarks.getText(), "") ? "" : String.format(format3, "Remarks", textPaneRemarks.getText()) + "\n",
                "-----------------------------------------------------------------\n",
                StringUtils.rightPad(textFieldFooter.getText(), 50, " ") + "Signature"
        };
        String[] initStyles = {
                "1",
                "2",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "4"
        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPlainWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void addStylesToDocumentPlainWeight(StyledDocument doc) {
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

        s = doc.addStyle("5", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 12);
    }

    private void printIceWater() {
        JTextPane textPane = createTextPaneIceWater();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();

        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 6d * 72d;
        double widthmargin = .50d * 72d;
        double heightmargin = .25d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createTextPaneIceWater() {
        String format = " %1$-13s: %2$-15s\n";
        String format2 = " %1$-13s: %2$-20s%3$-12s: %4$-20s\n";
        String format1 = "     %1$-15s: %2$-7s Kg               %3$-20s\n";
        String format3 = "     %1$-15s: %2$s\n";

        String[] initString = {
                "\n" + StringUtils.center(title1.getText(), 39) + "\n",
                StringUtils.center(title2.getText(), 65) + "\n",
                "----------------------------------------------------------------------\n",
                String.format(format, "Ticket No", textFieldSlNo.getText()),
                String.format(format2, "Party Name", comboBoxCustomerName.getEditor().getItem(), "Part City", comboBoxTransporterName.getEditor().getItem()),
                String.format(format2, "Vehicle No", comboBoxVehicleNo.getEditor().getItem(), "Material", comboBoxMaterial.getEditor().getItem()),
                "----------------------------------------------------------------------\n",
                String.format(format1, "Gross Wt", StringUtils.leftPad(textFieldGrossWt.getText(), 7, " "), textFieldGrossDateTime.getText()),
                String.format(format1, "Tare Wt", StringUtils.leftPad(textFieldTareWt.getText(), 7, " "), textFieldTareDateTime.getText()),
                String.format(format1, "Nett Wt", StringUtils.leftPad(textFieldNetWt.getText(), 7, " "), ""),
                String.format(format1, "Ice/Water Less", StringUtils.leftPad(textFieldDeductionOrPerCost.getText(), 7, " "), textFieldNetDateTime.getText()),
                String.format(format1, "Final Wt", StringUtils.leftPad(textFieldFinalWt.getText(), 7, " "), ""),
                String.format(format3, "Rate", textFieldCharges.getText()),
                String.format(format3, "Total Amount", (int) (Integer.parseInt(textFieldFinalWt.getText()) * Double.parseDouble(0 + textFieldCharges.getText().replaceAll("[^.\\d]", "")))),
                String.format(format3, "Freight Charges", textFieldNoOfBags.getText()),
                String.format(format3, "Final Amount", textFieldFinalAmount.getText()),
                String.format(format3, "Remarks", textPaneRemarks.getText()) + "\n\n",
                "----------------------------------------------------------------------\n",
                StringUtils.rightPad(textFieldFooter.getText(), 50, " ") + "Signature"
        };
        String[] initStyles = {
                "1",
                "2",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "4"
        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPlainWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void printEmjay() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();

        pBook.append((graphics, _, _) -> {
                    int margin = 30;
                    int len = 40;
                    int space = 20;

                    for (int i = 0; i < 2; i++) {
                        int spacing = 400;
                        String[] temp = (textFieldNetDateTime.getText() + " . . ").split(" ");

                        graphics.setFont(new Font("Courier New", Font.BOLD, 15));
                        graphics.drawString(StringUtils.center(title1.getText(), 60), margin, len += space);

                        graphics.setFont(new Font("Courier New", Font.ITALIC, 13));
                        graphics.drawString(StringUtils.center(title2.getText(), 71), margin, len += space);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 13));
                        graphics.drawString(StringUtils.center("WEIGHMENT SLIP", 71), margin, len += space + 5);
                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        graphics.setFont(new Font("Courier New", Font.PLAIN, 12));
                        graphics.drawString(" CHIT No         : " + textFieldSlNo.getText(), margin, len += space + space);
                        graphics.drawString(" Date  : " + temp[0].replaceAll("-", "/"), margin + spacing, len);
                        graphics.drawString(" Vehicle No      : " + comboBoxVehicleNo.getEditor().getItem(), margin, len += space);
                        graphics.drawString(" Time  : " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString(" Material Name   : " + comboBoxMaterial.getEditor().getItem(), margin, len += space);

                        spacing = 170;
                        temp = (textFieldGrossDateTime.getText() + " . . ").split(" ");
                        graphics.drawString(" Date  : ", margin, len += space + space + space);
                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawString(" Time  : ", margin + spacing, len);
                        graphics.drawString("First Weight  : ", margin + spacing + spacing, len);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                        graphics.drawString("         " + temp[0].replaceAll("-", "/"), margin, len);
                        graphics.drawString("         " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString("                " + StringUtils.leftPad(textFieldGrossWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        temp = (textFieldTareDateTime.getText() + " . . ").split(" ");
                        graphics.drawString("         " + temp[0].replaceAll("-", "/"), margin, len += space);
                        graphics.drawString("         " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString("                " + StringUtils.leftPad(textFieldTareWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        graphics.setFont(new Font("Courier New", Font.PLAIN, 12));
                        graphics.drawString(" Date  : ", margin, len);
                        graphics.drawString(" Time  : ", margin + spacing, len);
                        graphics.drawString("Second Weight : ", margin + spacing + spacing, len);

                        graphics.drawString("Net Weight    : ", margin + spacing + spacing, len += space + space);
                        graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                        graphics.drawString("                " + StringUtils.leftPad(textFieldNetWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 13));
                        graphics.drawString(" Department", margin, len += space + space + space);
                        graphics.drawString(StringUtils.leftPad(textFieldFooter.getText(), 70, " "), margin, len);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        len += space + space + space;
                    }
                    return Printable.PAGE_EXISTS;

                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printEmjay1() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();

        pBook.append((graphics, _, _) -> {
                    int margin = 30;
                    int len = 40;
                    int space = 20;

                    for (int i = 0; i < 2; i++) {
                        int spacing = 400;
                        String[] temp = (textFieldNetDateTime.getText() + " . . ").split(" ");

                        graphics.setFont(new Font("Courier New", Font.BOLD, 15));
                        graphics.drawString(StringUtils.center(title1.getText(), 60), margin, len += space);

                        graphics.setFont(new Font("Courier New", Font.ITALIC, 13));
                        graphics.drawString(StringUtils.center(title2.getText(), 71), margin, len += space);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 13));
                        graphics.drawString(StringUtils.center("WEIGHMENT SLIP", 71), margin, len += space + 5);
                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        graphics.setFont(new Font("Courier New", Font.PLAIN, 12));
                        graphics.drawString(" CHIT No         : " + textFieldSlNo.getText(), margin, len += space + space);
                        graphics.drawString(" Date  : " + temp[0].replaceAll("-", "/"), margin + spacing, len);
                        graphics.drawString(" Vehicle No      : " + comboBoxVehicleNo.getEditor().getItem(), margin, len += space);
                        graphics.drawString(" Time  : " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString(" Material Name   : " + comboBoxMaterial.getEditor().getItem(), margin, len += space);

                        spacing = 170;
                        temp = (textFieldGrossDateTime.getText() + " . . ").split(" ");
                        graphics.drawString(" Date  : ", margin, len += space + space + space);
                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawString(" Time  : ", margin + spacing, len);
                        graphics.drawString("Gross Weight  : ", margin + spacing + spacing, len);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                        graphics.drawString("         " + temp[0].replaceAll("-", "/"), margin, len);
                        graphics.drawString("         " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString("                " + StringUtils.leftPad(textFieldGrossWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        temp = (textFieldTareDateTime.getText() + " . . ").split(" ");
                        graphics.drawString("         " + temp[0].replaceAll("-", "/"), margin, len += space);
                        graphics.drawString("         " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString("                " + StringUtils.leftPad(textFieldTareWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        graphics.setFont(new Font("Courier New", Font.PLAIN, 12));
                        graphics.drawString(" Date  : ", margin, len);
                        graphics.drawString(" Time  : ", margin + spacing, len);
                        graphics.drawString("Tare Weight   : ", margin + spacing + spacing, len);

                        graphics.drawString("Net Weight    : ", margin + spacing + spacing, len += space + space);
                        graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                        graphics.drawString("                " + StringUtils.leftPad(textFieldNetWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 13));
                        graphics.drawString(" Department", margin, len += space + space + space);
                        graphics.drawString(StringUtils.leftPad(textFieldFooter.getText(), 70, " "), margin, len);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        len += space + space + space;
                    }
                    return Printable.PAGE_EXISTS;

                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printManiAndCo() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();

        pBook.append((graphics, _, _) -> {
                    int margin = 30;
                    int len = 20;
                    int space = 20;

                    for (int i = 0; i < 2; i++) {
                        int spacing = 400;
                        String[] temp = (textFieldNetDateTime.getText() + " . . ").split(" ");

                        graphics.setFont(new Font("Courier New", Font.BOLD, 15));
                        graphics.drawString(StringUtils.center(title1.getText(), 60), margin, len += space);

                        graphics.setFont(new Font("Courier New", Font.PLAIN, 13));
                        graphics.drawString(StringUtils.center(title2.getText(), 71), margin, len += space);

                        graphics.setFont(new Font("Courier New", Font.ITALIC, 13));
                        graphics.drawString(StringUtils.center(textFieldTitle3.getText(), 71), margin, len += space);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 13));
                        graphics.drawString(StringUtils.center("WEIGHMENT SLIP", 71), margin, len += space + 5);
                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        graphics.setFont(new Font("Courier New", Font.PLAIN, 12));
                        graphics.drawString(" Sl. No          : " + textFieldSlNo.getText(), margin, len += space + space);
                        graphics.drawString(" Date  : " + temp[0].replaceAll("-", "/"), margin + spacing, len);
                        graphics.drawString(" Vehicle No      : " + comboBoxVehicleNo.getEditor().getItem(), margin, len += space);
                        graphics.drawString(" Time  : " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString(" Material Name   : " + comboBoxMaterial.getEditor().getItem(), margin, len += space);

                        spacing = 170;
                        temp = (textFieldGrossDateTime.getText() + " . . ").split(" ");
                        graphics.drawString(" Date  : ", margin, len += space + space + space);
                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawString(" Time  : ", margin + spacing, len);
                        graphics.drawString("Laden Weight  : ", margin + spacing + spacing, len);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                        graphics.drawString("         " + temp[0].replaceAll("-", "/"), margin, len);
                        graphics.drawString("         " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString("                " + StringUtils.leftPad(textFieldGrossWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        temp = (textFieldTareDateTime.getText() + " . . ").split(" ");
                        graphics.drawString("         " + temp[0].replaceAll("-", "/"), margin, len += space);
                        graphics.drawString("         " + temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), margin + spacing, len);
                        graphics.drawString("                " + StringUtils.leftPad(textFieldTareWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        graphics.setFont(new Font("Courier New", Font.PLAIN, 12));
                        graphics.drawString(" Date  : ", margin, len);
                        graphics.drawString(" Time  : ", margin + spacing, len);
                        graphics.drawString("Unladen Weight: ", margin + spacing + spacing, len);

                        graphics.drawString("Net Weight    : ", margin + spacing + spacing, len += space + space);
                        graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                        graphics.drawString("                " + StringUtils.leftPad(textFieldNetWt.getText(), 7) + " Kg", margin + spacing + spacing, len);

                        graphics.drawLine(margin, len - 18, 574, len - 18);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        graphics.setFont(new Font("Courier New", Font.BOLD, 13));
                        graphics.drawString(" Department", margin, len += space + space + space);
                        graphics.drawString(StringUtils.leftPad(textFieldFooter.getText(), 70, " "), margin, len);
                        graphics.drawLine(margin, len + 10, 574, len + 10);

                        len += space + space + space;
                    }
                    return Printable.PAGE_EXISTS;

                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printManiAndCo1() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 6d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();

        pBook.append((graphics, _, _) -> {
                    int margin = 33;
                    int printArea = 534;
                    int len = 30;
                    int space = 20;
                    int lineSpace = 10;

                    graphics.drawLine(margin - 5, len, margin - 5, len + 380);
                    graphics.drawLine(560, len, 560, len + 380);
                    graphics.drawLine(margin - 5, len, 560, len);
                    graphics.drawLine(margin - 5, len + 380, 560, len + 380);

                    graphics.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 11));
                    String[] temp = (textFieldNetDateTime.getText() + " . . ").split(" ");
                    String text = "WEIGHBRIDGE SLIP";
                    graphics.drawString(text, margin + (printArea - graphics.getFontMetrics().stringWidth(text)) / 2, len += space);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);
                    graphics.setFont(new Font("Verdana", Font.PLAIN, 11));
                    graphics.drawString("Weighbridge Slip No", margin, len += space);
                    graphics.drawString(": " + textFieldSlNo.getText(), margin + 130, len);
                    graphics.drawString("Date     :  " + temp[0].replaceAll("-", " - "), 400, len);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                    graphics.drawString("Name of Work", margin, len += space);
                    temp = (WordUtils.wrap(textFieldNameOfWork.getText().trim(), 62, "\n", true) + " \n ").split("\n");
                    graphics.drawString(": " + temp[0], margin + 130, len);
                    text = Arrays.stream(temp).skip(1).collect(Collectors.joining(""));
                    graphics.drawString(text, margin + (printArea - graphics.getFontMetrics().stringWidth(text)) / 2, len += space);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                    graphics.drawString("Name of Department", margin, len += space);
                    graphics.drawString(": " + textFieldNameOfDepartment.getText(), margin + 130, len);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                    graphics.drawString("Name of Contractor", margin, len += space);
                    graphics.drawString(": " + textFieldNameOfContractor.getText(), margin + 130, len);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                    graphics.drawString("Agreement No", margin, len += space);
                    graphics.drawString(": " + textFieldAgreementNo.getText(), margin + 90, len);
                    graphics.drawString("Estimate No", 300, len);
                    graphics.drawString(": " + textFieldEstimateNo.getText(), 300 + 90, len);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                    graphics.drawString("Vehicle No", margin, len += space);
                    graphics.drawString(": " + comboBoxVehicleNo.getEditor().getItem(), margin + 90, len);
                    graphics.drawString("Material Name", 300, len);
                    graphics.drawString(": " + comboBoxMaterial.getEditor().getItem(), 300 + 90, len);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                    temp = (textFieldGrossDateTime.getText() + " . . ").split(" ");
                    graphics.drawString("Date    :", margin, len += space);
                    graphics.drawString("Time    :", 200, len);
                    graphics.drawString("Laden Weight", 350, len);
                    graphics.drawString(":", 350 + 100, len);
                    graphics.setFont(new Font("Verdana", Font.BOLD, 11));
                    graphics.drawString(temp[0].replaceAll("-", " - "), margin + 50, len);
                    graphics.drawString(temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), 200 + 50, len);
                    text = textFieldGrossWt.getText() + " Kg";
                    graphics.drawString(text, 350 + 100 + (100 - graphics.getFontMetrics().stringWidth(text)), len);

                    graphics.setFont(new Font("Verdana", Font.PLAIN, 11));
                    temp = (textFieldTareDateTime.getText() + " . . ").split(" ");
                    graphics.drawString("Date    :", margin, len += space);
                    graphics.drawString("Time    :", 200, len);
                    graphics.drawString("UnLaden Weight", 350, len);
                    graphics.drawString(":", 350 + 100, len);
                    graphics.setFont(new Font("Verdana", Font.BOLD, 11));
                    graphics.drawString(temp[0].replaceAll("-", " - "), margin + 50, len);
                    graphics.drawString(temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), 200 + 50, len);
                    text = textFieldTareWt.getText() + " Kg";
                    graphics.drawString(text, 350 + 100 + (100 - graphics.getFontMetrics().stringWidth(text)), len);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                    graphics.drawString("Net Weight", 350, len += space);
                    graphics.drawString(":", 350 + 100, len);
                    text = textFieldNetWt.getText() + " Kg";
                    graphics.drawString(text, 350 + 100 + (100 - graphics.getFontMetrics().stringWidth(text)), len);
                    graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                    graphics.drawString("Signature of the Weigh Bridge office            Signature of the Weigh Bridge office", margin + 15, len += 40);
                    graphics.drawString("In charge (Contractor)                                 In charge (Department)", margin + 55, len + space);

                    return Printable.PAGE_EXISTS;
                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printManiAndCo2() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();

        pBook.append((graphics, _, _) -> {
                    int margin = 33;
                    int printArea = 534;
                    int len = 30;
                    int space = 20;
                    int lineSpace = 10;

                    for (int i = 0; i < 2; i++) {
                        graphics.drawLine(margin - 5, len, margin - 5, len + 380);
                        graphics.drawLine(560, len, 560, len + 380);
                        graphics.drawLine(margin - 5, len, 560, len);
                        graphics.drawLine(margin - 5, len + 380, 560, len + 380);

                        graphics.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 11));
                        String[] temp = (textFieldNetDateTime.getText() + " . . ").split(" ");
                        String text = "WEIGHBRIDGE SLIP";
                        graphics.drawString(text, margin + (printArea - graphics.getFontMetrics().stringWidth(text)) / 2, len += space);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);
                        graphics.setFont(new Font("Verdana", Font.PLAIN, 11));
                        graphics.drawString("Weighbridge Slip No", margin, len += space);
                        graphics.drawString(": " + textFieldSlNo.getText(), margin + 130, len);
                        graphics.drawString("Date     :  " + temp[0].replaceAll("-", " - "), 400, len);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                        graphics.drawString("Name of Work", margin, len += space);
                        temp = (WordUtils.wrap(textFieldNameOfWork.getText().trim(), 62, "\n", true) + " \n ").split("\n");
                        graphics.drawString(": " + temp[0], margin + 130, len);
                        text = Arrays.stream(temp).skip(1).collect(Collectors.joining(""));
                        graphics.drawString(text, margin + (printArea - graphics.getFontMetrics().stringWidth(text)) / 2, len += space);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                        graphics.drawString("Name of Department", margin, len += space);
                        graphics.drawString(": " + textFieldNameOfDepartment.getText(), margin + 130, len);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                        graphics.drawString("Name of Contractor", margin, len += space);
                        graphics.drawString(": " + textFieldNameOfContractor.getText(), margin + 130, len);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                        graphics.drawString("Agreement No", margin, len += space);
                        graphics.drawString(": " + textFieldAgreementNo.getText(), margin + 90, len);
                        graphics.drawString("Estimate No", 300, len);
                        graphics.drawString(": " + textFieldEstimateNo.getText(), 300 + 90, len);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                        graphics.drawString("Vehicle No", margin, len += space);
                        graphics.drawString(": " + comboBoxVehicleNo.getEditor().getItem(), margin + 90, len);
                        graphics.drawString("Material Name", 300, len);
                        graphics.drawString(": " + comboBoxMaterial.getEditor().getItem(), 300 + 90, len);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                        temp = (textFieldGrossDateTime.getText() + " . . ").split(" ");
                        graphics.drawString("Date    :", margin, len += space);
                        graphics.drawString("Time    :", 200, len);
                        graphics.drawString("Laden Weight", 350, len);
                        graphics.drawString(":", 350 + 100, len);
                        graphics.setFont(new Font("Verdana", Font.BOLD, 11));
                        graphics.drawString(temp[0].replaceAll("-", " - "), margin + 50, len);
                        graphics.drawString(temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), 200 + 50, len);
                        text = textFieldGrossWt.getText() + " Kg";
                        graphics.drawString(text, 350 + 100 + (100 - graphics.getFontMetrics().stringWidth(text)), len);

                        graphics.setFont(new Font("Verdana", Font.PLAIN, 11));
                        temp = (textFieldTareDateTime.getText() + " . . ").split(" ");
                        graphics.drawString("Date    :", margin, len += space);
                        graphics.drawString("Time    :", 200, len);
                        graphics.drawString("UnLaden Weight", 350, len);
                        graphics.drawString(":", 350 + 100, len);
                        graphics.setFont(new Font("Verdana", Font.BOLD, 11));
                        graphics.drawString(temp[0].replaceAll("-", " - "), margin + 50, len);
                        graphics.drawString(temp[1].replaceAll("\\.", "") + " " + temp[2].replaceAll("\\.", ""), 200 + 50, len);
                        text = textFieldTareWt.getText() + " Kg";
                        graphics.drawString(text, 350 + 100 + (100 - graphics.getFontMetrics().stringWidth(text)), len);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                        graphics.drawString("Net Weight", 350, len += space);
                        graphics.drawString(":", 350 + 100, len);
                        text = textFieldNetWt.getText() + " Kg";
                        graphics.drawString(text, 350 + 100 + (100 - graphics.getFontMetrics().stringWidth(text)), len);
                        graphics.drawLine(margin - 5, len += lineSpace, 560, len);

                        graphics.drawString("Signature of the Weigh Bridge office            Signature of the Weigh Bridge office", margin + 15, len += 40);
                        graphics.drawString("In charge (Contractor)                                 In charge (Department)", margin + 55, len + space);

                        len = 425;
                    }
                    return Printable.PAGE_EXISTS;

                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printPreWeight(JTextPane textPane, double topMargin) {
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 10d * 72d;
        double height = 6d * 72d;
        double widthmargin = 0;
        double heightmargin = topMargin * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private JTextPane createTextPanePreWeight() {
        String format = "%1$-7s%2$-30s%3$-30s%4$-12s";
        String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");
        String[] initString = {
                String.format(format, "", temp[0], temp[0], temp[0]),
                "\n\n",
                String.format(format, "", temp[1] + " " + temp[2], temp[1] + " " + temp[2], temp[1] + " " + temp[2]),
                "\n\n",
                String.format(format, "", textFieldSlNo.getText(), textFieldSlNo.getText(), textFieldSlNo.getText()),
                "\n\n",
                String.format(format, "", comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem()),
                "\n\n",
                String.format(format, "", comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem()),
                "\n\n",
                String.format(
                        format, "", (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                "\n\n",
                String.format(format, "", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg")
        };

        String[] initStyles = {
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "4",
                "1",
                "4",
                "1",

        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPreWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void addStylesToDocumentPreWeight(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "Courier New");

        Style s = doc.addStyle("1", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 12);

        s = doc.addStyle("2", regular);
        StyleConstants.setFontSize(s, 8);

        s = doc.addStyle("3", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 8);

        s = doc.addStyle("4", regular);
        StyleConstants.setFontSize(s, 14);

        s = doc.addStyle("5", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setItalic(s, true);
        StyleConstants.setFontSize(s, 12);

        s = doc.addStyle("6", regular);
        StyleConstants.setFontSize(s, 9);
    }

    private JTextPane createTextPanePreWeight2() {
        String format = "%1$-7s%2$-30s%3$-30s%4$-12s";
        String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");
        String[] initString = {
                String.format(format, "", textFieldSlNo.getText(), textFieldSlNo.getText(), textFieldSlNo.getText()),
                "\n\n",
                String.format(format, "", comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem()),
                "\n\n",
                String.format(format, "", temp[0], temp[0], temp[0]),
                "\n\n",
                String.format(format, "", temp[1] + " " + temp[2], temp[1] + " " + temp[2], temp[1] + " " + temp[2]),
                "\n\n",
                String.format(format, "", comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem()),
                "\n\n",
                String.format(
                        format, "", (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                "\n\n",
                String.format(format, "", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg")
        };

        String[] initStyles = {
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "3",
                "1",
                "3",
                "1",
        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPreWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private JTextPane createTextPanePreWeight3() {
        String format = "%1$-7s%2$-30s%3$-30s%4$-12s";
        String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");
        String[] initString = {
                String.format(format, "", textFieldSlNo.getText(), textFieldSlNo.getText(), textFieldSlNo.getText()),
                "\n\n",
                String.format(format, "", temp[0], temp[0], temp[0]),
                "\n\n",
                String.format(format, "", temp[1] + " " + temp[2], temp[1] + " " + temp[2], temp[1] + " " + temp[2]),
                "\n\n",
                String.format(format, "", comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem()),
                "\n\n",
                String.format(format, "", comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem()),
                "\n\n",
                String.format(format, "", comboBoxCustomerName.getEditor().getItem(), comboBoxCustomerName.getEditor().getItem(), comboBoxCustomerName.getEditor().getItem()),
                "\n\n",
                String.format(
                        format, "", (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                "\n\n",
                String.format(format, "", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg")
        };

        String[] initStyles = {
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "3",
                "1",
                "3",
                "1",

        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPreWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private JTextPane createTextPanePreWeight4() {
        String format = "%1$-7s%2$-30s%3$-30s%4$-12s\n";
        String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");
        String[] initString = {
                String.format(format, "", temp[0], temp[0], temp[0]),
                "\n",
                String.format(format, "", temp[1] + " " + temp[2], temp[1] + " " + temp[2], temp[1] + " " + temp[2]),
                "\n",
                String.format(format, "", textFieldSlNo.getText(), textFieldSlNo.getText(), textFieldSlNo.getText()),
                "\n",
                String.format(format, "", comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem()),
                "\n",
                String.format(format, "", comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem()),
                "\n",
                String.format(format, "", comboBoxVehicleType.getEditor().getItem(), comboBoxVehicleType.getEditor().getItem(), comboBoxVehicleType.getEditor().getItem()),
                "\n",
                String.format(format, "", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg"),
                "\n",
                String.format(format, "", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg"),
                "\n",
                String.format(format, "", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg"),
                "\n",
                String.format(format, "", lblOperatorName.getText(), lblOperatorName.getText(), lblOperatorName.getText()),
        };

        String[] initStyles = {
                "1",
                "6",
                "1",
                "6",
                "1",
                "6",
                "1",
                "6",
                "1",
                "6",
                "1",
                "6",
                "5",
                "6",
                "5",
                "6",
                "5",
                "6",
                "1",

        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPreWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private JTextPane createTextPanePreWeight5() {
        String format = "%1$-9s%2$-32s%3$-31s%4$-12s";
        String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");
        String[] initString = {
                String.format(format, "", textFieldSlNo.getText(), textFieldSlNo.getText(), textFieldSlNo.getText()),
                "\n\n",
                String.format(format, "", comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem(), comboBoxVehicleNo.getEditor().getItem()),
                "\n\n",
                String.format(format, "", temp[0], temp[0], temp[0]),
                "\n\n",
                String.format(format, "", temp[1] + " " + temp[2], temp[1] + " " + temp[2], temp[1] + " " + temp[2]),
                "\n\n",
                String.format(format, "", comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem(), comboBoxMaterial.getEditor().getItem()),
                "\n\n",
                String.format(
                        format, "", (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                "\n\n",
                String.format(format, "", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg", textFieldGrossWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg", textFieldTareWt.getText() + " Kg"),
                "\n\n",
                String.format(format, "", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg", textFieldNetWt.getText() + " Kg")
        };

        String[] initStyles = {
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "2",
                "1",
                "3",
                "1",
                "3",
                "1",
        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPreWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
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
                             int xCoordinate = 164;
                             String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");

                             graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                             Coordinates coordinates = drawString(graphics, textFieldSlNo.getText() + "\n\n" + temp[0] + "\n\n" + temp[1] + "\n\n" + comboBoxVehicleNo.getEditor().getItem() + "\n\n" + comboBoxMaterial.getEditor().getItem() + "\n\n" + comboBoxCustomerName.getEditor().getItem() + "\n\n" + (textFieldCharges.getText().equals("0") ? " " : textFieldCharges.getText()), xCoordinate, 115);

                             graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                             coordinates = drawString(graphics, StringUtils.rightPad(textFieldGrossWt.getText(), 7) + "Kg", xCoordinate, coordinates.y);

                             graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                             coordinates = drawString(graphics, StringUtils.rightPad(textFieldTareWt.getText(), 7) + "Kg", xCoordinate, coordinates.y - 4);

                             graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                             drawString(graphics, StringUtils.rightPad(textFieldNetWt.getText(), 7) + "Kg", xCoordinate, coordinates.y - 4);

                             try {
                                 BufferedImage printImage = ImageIO.read(new File("CameraOutput/" + textFieldSlNo.getText() + "_1.jpg"));
                                 BufferedImage cropImage = printImage.getSubimage(
                                         Integer.parseInt(0 + textFieldCropX1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropY1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropWidth1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropHeight1.getText().replaceAll("\\D", "")));
                                 graphics.drawImage(cropImage, 250, 115, 300, (int) (300.00 / cropImage.getWidth() * cropImage.getHeight()), null);
                             } catch (IOException | RasterFormatException | NullPointerException ignored) {
                             }

                             return PAGE_EXISTS;
                         }
                     },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printCameraWeight2() {
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
                             int xCoordinate = 164;
                             String[] temp = (textFieldNetDateTime.getText() + " . ").split(" ");

                             graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                             Coordinates coordinates = drawString(graphics, textFieldSlNo.getText() + "\n\n" + temp[0] + "\n\n" + temp[1] + "\n\n" + comboBoxVehicleNo.getEditor().getItem() + "\n\n" + comboBoxMaterial.getEditor().getItem() + "\n\n" + comboBoxCustomerName.getEditor().getItem() + "\n\n" + (textFieldCharges.getText().equals("0") ? " " : textFieldCharges.getText()), xCoordinate, 115);

                             graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                             coordinates = drawString(graphics, StringUtils.rightPad(textFieldGrossWt.getText(), 7) + "Kg", xCoordinate, coordinates.y);

                             graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                             coordinates = drawString(graphics, StringUtils.rightPad(textFieldTareWt.getText(), 7) + "Kg", xCoordinate, coordinates.y - 4);

                             graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                             drawString(graphics, StringUtils.rightPad(textFieldNetWt.getText(), 7) + "Kg", xCoordinate, coordinates.y - 4);

                             try {
                                 BufferedImage cropImage1 = null, cropImage2 = null;

                                 try {
                                     BufferedImage printImage1 = ImageIO.read(new File("CameraOutput/" + textFieldSlNo.getText() + "_1.jpg"));
                                     cropImage1 = printImage1.getSubimage(
                                             Integer.parseInt(0 + textFieldCropX1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropY1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropWidth1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropHeight1.getText().replaceAll("\\D", "")));
                                 } catch (IOException ignored) {
                                 }

                                 try {
                                     BufferedImage printImage2 = ImageIO.read(new File("CameraOutput/" + textFieldSlNo.getText() + "_2.jpg"));
                                     cropImage2 = printImage2.getSubimage(
                                             Integer.parseInt(0 + textFieldCropX2.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropY2.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropWidth2.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropHeight2.getText().replaceAll("\\D", "")));
                                 } catch (IOException ignored) {
                                 }

                                 BufferedImage printImage = joinBufferedImage(cropImage1, cropImage2);
                                 if (printImage != null) {
                                     graphics.drawImage(printImage, 250, 115, 300, (int) (300.00 / printImage.getWidth() * printImage.getHeight()), null);
                                 }
                             } catch (NullPointerException ignored) {
                             }

                             return PAGE_EXISTS;
                         }
                     },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
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

                             String[] temp = (textFieldNetDateTime.getText() + " . . ").split(" ");
                             String initString = "\n\n" + StringUtils.center(title1.getText(), 62);
                             graphics.setFont(new Font("Courier New", Font.BOLD, 15));

                             Coordinates coordinates = drawString(graphics, initString, 0, 0);
                             initString = StringUtils.center(title2.getText(), 73);
                             graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 13));
                             coordinates = drawString(graphics, initString, 0, coordinates.y);

                             initString = StringUtils.center("WEIGHMENT RECEIPT", 79) + "\n";
                             graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 12));
                             coordinates = drawString(graphics, initString, 0, coordinates.y);

                             initString = String.format(format, "", "Sl.No") + textFieldSlNo.getText() + "\n\n" + String.format(format, "", "Date") + temp[0] + "\n\n" + String.format(format, "", "Time") + temp[1] + " " + temp[2] + "\n\n" + String.format(format, "", "Vehicle No") + comboBoxVehicleNo.getEditor().getItem() + "\n\n" + String.format(format, "", "Material") + comboBoxMaterial.getEditor().getItem() + "\n\n" + String.format(format, "", "Customer/Supplier") + comboBoxCustomerName.getEditor().getItem() + "\n\n" + (chckbxExcludeCharges.isSelected() && textFieldCharges.getText().equals("0") ? "" : String.format(format, "", "Charges") + "Rs. " + (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()) + "\n\n");
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

                             initString = "\n\n\n" + "     " + StringUtils.rightPad(textFieldFooter.getText(), 60, " ") + "Authorised Signature";
                             graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 10));
                             drawString(graphics, initString, 0, coordinates.y);

                             try {
                                 BufferedImage printImage = ImageIO.read(new File("CameraOutput/" + textFieldSlNo.getText() + "_1.jpg"));
                                 BufferedImage cropImage = printImage.getSubimage(
                                         Integer.parseInt(0 + textFieldCropX1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropY1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropWidth1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropHeight1.getText().replaceAll("\\D", "")));
                                 graphics.drawImage(cropImage, 250, 125, 300, (int) (300.00 / cropImage.getWidth() * cropImage.getHeight()), null);
                             } catch (IOException | NullPointerException ignored) {
                             }

                             return PAGE_EXISTS;
                         }
                     },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printPlainCameraWeight2() {
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

                             String[] temp = (textFieldNetDateTime.getText() + " . . ").split(" ");
                             String initString = "\n\n" + StringUtils.center(title1.getText(), 62);
                             graphics.setFont(new Font("Courier New", Font.BOLD, 15));

                             Coordinates coordinates = drawString(graphics, initString, 0, 0);
                             initString = StringUtils.center(title2.getText(), 73);
                             graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 13));
                             coordinates = drawString(graphics, initString, 0, coordinates.y);

                             initString = StringUtils.center("WEIGHMENT RECEIPT", 79) + "\n";
                             graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 12));
                             coordinates = drawString(graphics, initString, 0, coordinates.y);

                             initString = String.format(format, "", "Sl.No") + textFieldSlNo.getText() + "\n\n" + String.format(format, "", "Date") + temp[0] + "\n\n" + String.format(format, "", "Time") + temp[1] + " " + temp[2] + "\n\n" + String.format(format, "", "Vehicle No") + comboBoxVehicleNo.getEditor().getItem() + "\n\n" + String.format(format, "", "Material") + comboBoxMaterial.getEditor().getItem() + "\n\n" + String.format(format, "", "Customer/Supplier") + comboBoxCustomerName.getEditor().getItem() + "\n\n" + (chckbxExcludeCharges.isSelected() && textFieldCharges.getText().equals("0") ? "" : String.format(format, "", "Charges") + "Rs. " + (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()) + "\n\n");
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

                             initString = "\n\n\n" + "     " + StringUtils.rightPad(textFieldFooter.getText(), 60, " ") + "Authorised Signature";
                             graphics.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 10));
                             drawString(graphics, initString, 0, coordinates.y);

                             try {
                                 BufferedImage cropImage1 = null, cropImage2 = null;

                                 try {
                                     BufferedImage printImage1 = ImageIO.read(new File("CameraOutput/" + textFieldSlNo.getText() + "_1.jpg"));
                                     cropImage1 = printImage1.getSubimage(
                                             Integer.parseInt(0 + textFieldCropX1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropY1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropWidth1.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropHeight1.getText().replaceAll("\\D", "")));
                                 } catch (IOException ignored) {
                                 }

                                 try {
                                     BufferedImage printImage2 = ImageIO.read(new File("CameraOutput/" + textFieldSlNo.getText() + "_2.jpg"));
                                     cropImage2 = printImage2.getSubimage(
                                             Integer.parseInt(0 + textFieldCropX2.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropY2.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropWidth2.getText().replaceAll("\\D", "")), Integer.parseInt(0 + textFieldCropHeight2.getText().replaceAll("\\D", "")));
                                 } catch (IOException ignored) {
                                 }

                                 BufferedImage printImage = joinBufferedImage(cropImage1, cropImage2);
                                 if (printImage != null) {
                                     graphics.drawImage(printImage, 250, 125, 300, (int) (300.00 / printImage.getWidth() * printImage.getHeight()), null);
                                 }
                             } catch (NullPointerException ignored) {
                             }

                             return PAGE_EXISTS;
                         }
                     },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printQuotation() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 2.8d * 72d;
        double height = 5d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append((graphics, _, _) -> {
                    int y = 10,
                            space = 20;
                    graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                    graphics.drawString(StringUtils.center("QUOTATION", 29), 0, y);
                    graphics.drawString(StringUtils.center(title1.getText(), 29), 0, y += space);
                    graphics.drawString(StringUtils.center(chckbxIsCredit.isSelected() ? "CREDIT" : "CASH", 29), 0, y += space);
                    graphics.drawString("    " + textFieldSlNo.getText(), 0, y += space);
                    graphics.drawString("     " + comboBoxCustomerName.getEditor().getItem(), 0, y += space);
                    graphics.drawString("       " + textFieldPlace.getText(), 0, y += space);
                    graphics.drawString("       " + textFieldPhoneNo.getText(), 0, y += space);
                    graphics.drawString("            " + comboBoxVehicleNo.getEditor().getItem(), 0, y += space);
                    graphics.drawString("          " + comboBoxMaterial.getEditor().getItem(), 0, y += space);
                    graphics.drawString(StringUtils.leftPad(textFieldTareWt.getText(), 27), 0, y += 30);
                    graphics.drawString(StringUtils.leftPad(textFieldGrossWt.getText(), 27), 0, y += space);
                    graphics.drawString(StringUtils.leftPad(textFieldNetWt.getText(), 27), 0, y += 30);
                    graphics.drawString(StringUtils.leftPad(textFieldDeductionOrPerCost.getText(), 27), 0, y += space);
                    graphics.drawString(StringUtils.leftPad(textFieldRoundOff.getText(), 27), 0, y += space);
                    graphics.drawString(StringUtils.leftPad(textFieldCharges.getText(), 27), 0, y + space);

                    graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                    graphics.drawString("No:", 0, y = 70);
                    graphics.drawString("M/s:", 0, y += space);
                    graphics.drawString("Place:", 0, y += space);
                    graphics.drawString("Phone:", 0, y += space);
                    graphics.drawString("Vehicle No:", 0, y += space);
                    graphics.drawString("Material:", 0, y += space);
                    graphics.drawString("Tare Wt.", 0, y += 30);
                    graphics.drawString("Gross Wt.", 0, y += space);
                    graphics.drawString("Net Wt.", 0, y += 30);
                    graphics.drawString("Rate", 0, y += space);
                    graphics.drawString("R.Off", 0, y += space);
                    graphics.drawString("Total", 0, y += space);

                    graphics.drawString(StringUtils.leftPad("Driver Sign", 27), 0, y + 30);

                    graphics.setFont(new Font("Courier New", Font.BOLD, 9));
                    graphics.drawString("                " + textFieldNetDateTime.getText(), 0, 70);

                    return Printable.PAGE_EXISTS;
                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printQuotationKJJ() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 2.8d * 72d;
        double height = 4d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append((graphics, _, _) -> {
                    int y = 10,
                            space = 20;
                    graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                    graphics.drawString(StringUtils.center(title1.getText(), 29), 0, y += space);
                    graphics.drawString("    " + textFieldSlNo.getText(), 0, y += 2 * space);
                    graphics.drawString("     " + comboBoxCustomerName.getEditor().getItem(), 0, y += space);
                    graphics.drawString("       " + textFieldPlace.getText(), 0, y += space);
                    graphics.drawString("       " + textFieldPhoneNo.getText(), 0, y += space);
                    graphics.drawString("            " + comboBoxVehicleNo.getEditor().getItem(), 0, y += space);
                    graphics.drawString("          " + comboBoxMaterial.getEditor().getItem(), 0, y += space);
                    graphics.drawString(StringUtils.leftPad(textFieldTareWt.getText(), 27), 0, y += 30);
                    graphics.drawString(StringUtils.leftPad(textFieldGrossWt.getText(), 27), 0, y += space);
                    graphics.drawString(StringUtils.leftPad(textFieldNetWt.getText(), 27), 0, y + 30);

                    graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                    graphics.drawString("No:", 0, y = 70);
                    graphics.drawString("M/s:", 0, y += space);
                    graphics.drawString("Place:", 0, y += space);
                    graphics.drawString("Phone:", 0, y += space);
                    graphics.drawString("Vehicle No:", 0, y += space);
                    graphics.drawString("Material:", 0, y += space);
                    graphics.drawString("Tare Wt.", 0, y += 30);
                    graphics.drawString("Gross Wt.", 0, y += space);
                    graphics.drawString("Net Wt.", 0, y += 30);

                    graphics.drawString(StringUtils.leftPad("Driver Sign", 27), 0, y + 30);

                    graphics.setFont(new Font("Courier New", Font.BOLD, 9));
                    graphics.drawString("                          " + (textFieldNetDateTime.getText() + " . ").split(" ")[0], 0, 70);

                    return Printable.PAGE_EXISTS;
                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printStandardToken(ActionEvent... ae) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 2.8d * 72d;
        double height = 3.1d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append((graphics, _, _) -> {
                    int y = 10,
                            space = 20;
                    graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                    graphics.drawString(StringUtils.center("MATERIAL TOKEN", 29), 0, y);
                    graphics.drawString(StringUtils.center(title1.getText(), 29), 0, y += space);
                    graphics.drawString(StringUtils.center(chckbxIsCredit.isSelected() ? "CREDIT" : "CASH", 29), 0, y += space);
                    graphics.drawString("    " + textFieldSlNo.getText(), 0, y += space);
                    graphics.drawString("     " + comboBoxCustomerName.getEditor().getItem(), 0, y += space);
                    graphics.drawString("       " + textFieldPlace.getText(), 0, y += space);
                    graphics.drawString("       " + textFieldPhoneNo.getText(), 0, y += space);
                    graphics.drawString("            " + comboBoxVehicleNo.getEditor().getItem(), 0, y += space);
                    graphics.drawString("          " + comboBoxMaterial.getEditor().getItem(), 0, y += space);
                    graphics.drawString("          " + textFieldTareWt.getText(), 0, y + space);
                    graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                    graphics.drawString("No:", 0, y = 70);
                    graphics.drawString("M/s:", 0, y += space);
                    graphics.drawString("Place:", 0, y += space);
                    graphics.drawString("Phone:", 0, y += space);
                    graphics.drawString("Vehicle No:", 0, y += space);
                    graphics.drawString("Material:", 0, y += space);
                    graphics.drawString("Tare Wt.:", 0, y + space);
                    graphics.setFont(new Font("Courier New", Font.BOLD, 9));
                    graphics.drawString("                " + textFieldTareDateTime.getText(), 0, 70);

                    return Printable.PAGE_EXISTS;
                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printStandardExitPass(ActionEvent... ae) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 2.8d * 72d;
        double height = 3.1d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append((graphics, _, _) -> {
                    int y = 10,
                            space = 20;
                    graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                    graphics.drawString(StringUtils.center("GATE PASS", 29), 0, y);
                    graphics.drawString(StringUtils.center(title1.getText(), 29), 0, y += space);
                    graphics.drawString(StringUtils.center(chckbxIsCredit.isSelected() ? "CREDIT" : "CASH", 29), 0, y += space);
                    graphics.drawString("    " + textFieldSlNo.getText(), 0, y += space);
                    graphics.drawString("     " + comboBoxCustomerName.getEditor().getItem(), 0, y += space);
                    graphics.drawString("       " + textFieldPlace.getText(), 0, y += space);
                    graphics.drawString("       " + textFieldPhoneNo.getText(), 0, y += space);
                    graphics.drawString("            " + comboBoxVehicleNo.getEditor().getItem(), 0, y += space);
                    graphics.drawString("          " + comboBoxMaterial.getEditor().getItem(), 0, y += space);
                    graphics.drawString("         " + textFieldNetWt.getText(), 0, y + space);
                    graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                    graphics.drawString("No:", 0, y = 70);
                    graphics.drawString("M/s:", 0, y += space);
                    graphics.drawString("Place:", 0, y += space);
                    graphics.drawString("Phone:", 0, y += space);
                    graphics.drawString("Vehicle No:", 0, y += space);
                    graphics.drawString("Material:", 0, y += space);
                    graphics.drawString("Net Wt.:", 0, y + space);
                    graphics.setFont(new Font("Courier New", Font.BOLD, 9));
                    graphics.drawString("                " + textFieldNetDateTime.getText(), 0, 70);

                    return Printable.PAGE_EXISTS;
                },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printStandard() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 6d * 72d;
        double widthmargin = 0d * 72d;
        double heightmargin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(new Printable() {
                         private void drawString(Graphics graphics, String text, int y, int x, int size) {
                             for (String line : text.split("\n")) {
                                 y += graphics.getFontMetrics().getHeight() - 1;
                                 String temp = line;
                                 if (size > 0) {
                                     temp = StringUtils.center(temp, size);
                                 }
                                 graphics.drawString(temp, 23 + x, y);
                                 graphics.drawString(temp, 209 + x, y);
                                 graphics.drawString(temp, 395 + x, y);
                             }
                         }

                         public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                             String[] temp = (textFieldNetDateTime.getText() + " . . ").split(" ");
                             graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                             drawString(graphics, StringUtils.center(title1.getText().toUpperCase().split(" WEIGH")[0], 22), 20, 0, 0);
                             drawString(graphics, StringUtils.center("WEIGH BRIDGE", 22), 33, 0, 0);
                             graphics.setFont(new Font("Courier New", Font.ITALIC, 10));
                             drawString(graphics, WordUtils.wrap(title2.getText(), 27), 48, 0, 27);
                             graphics.drawLine(17, 75, 193, 75);
                             graphics.drawLine(203, 75, 379, 75);
                             graphics.drawLine(389, 75, 565, 75);
                             graphics.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 10));
                             graphics.drawString(StringUtils.center("ORIGINAL", 26), 23, 84);
                             graphics.drawString(StringUtils.center("DUPLICATE", 26), 209, 84);
                             graphics.drawString(StringUtils.center("TRIPLICATE", 26), 395, 84);
                             graphics.drawLine(17, 88, 193, 88);
                             graphics.drawLine(203, 88, 379, 88);
                             graphics.drawLine(389, 88, 565, 88);
                             graphics.setFont(new Font("Courier New", Font.BOLD, 8));
                             drawString(graphics, "Slip No       : " + textFieldSlNo.getText(), 90, 0, 0);
                             drawString(graphics, "Date          : " + temp[0], 110, 0, 0);
                             drawString(graphics, "Time          : " + temp[1], 130, 0, 0);
                             drawString(graphics, "Vehicle No    : " + comboBoxVehicleNo.getEditor().getItem(), 150, 0, 0);
                             drawString(graphics, "Material      : " + comboBoxMaterial.getEditor().getItem(), 170, 0, 0);
                             drawString(graphics, "Customer Name : ", 190, 0, 0);
                             drawString(graphics, "Charges       : " + (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText()), 230, 0, 0);
                             drawString(graphics, "Gross Wt      : ", 250, 0, 0);
                             drawString(graphics, "Tare Wt       : ", 270, 0, 0);
                             drawString(graphics, "Net Wt        : ", 290, 0, 0);
                             drawString(graphics, WordUtils.wrap(comboBoxCustomerName.getEditor().getItem().toString(), 17), 190, 77, 0);
                             graphics.setFont(new Font("Courier New", Font.BOLD, 12));
                             drawString(graphics, StringUtils.leftPad(textFieldGrossWt.getText(), 7) + " Kg", 248, 77, 0);
                             drawString(graphics, StringUtils.leftPad(textFieldTareWt.getText(), 7) + " Kg", 268, 77, 0);
                             drawString(graphics, StringUtils.leftPad(textFieldNetWt.getText(), 7) + " Kg", 288, 77, 0);
                             graphics.drawLine(17, 305, 193, 305);
                             graphics.drawLine(203, 305, 379, 305);
                             graphics.drawLine(389, 305, 565, 305);
                             graphics.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 10));
                             drawString(graphics, StringUtils.center("Thanks you visit again...", 26), 310, 0, 0);

                             graphics.drawLine(17, 15, 17, 330);
                             graphics.drawLine(193, 15, 193, 330);
                             graphics.drawLine(203, 15, 203, 330);
                             graphics.drawLine(379, 15, 379, 330);
                             graphics.drawLine(389, 15, 389, 330);
                             graphics.drawLine(565, 15, 565, 330);
                             graphics.drawLine(17, 15, 193, 15);
                             graphics.drawLine(203, 15, 379, 15);
                             graphics.drawLine(389, 15, 565, 15);
                             graphics.drawLine(17, 330, 193, 330);
                             graphics.drawLine(203, 330, 379, 330);
                             graphics.drawLine(389, 330, 565, 330);
                             return PAGE_EXISTS;
                         }
                     },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

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
                         private void drawString(Graphics g, String text) {
                             int y = 0;
                             for (String line : text.split("\n")) {
                                 g.drawString(line, 0, y += g.getFontMetrics().getHeight() - 1);
                             }
                         }

                         public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                             String format1 = "           %-19s: %-25s   %-10s : %s\n";
                             String format2 = "           %-10s:%7s Kg   %-10s : %-12s   %-10s : %s\n";
                             String format3 = "           %-10s:%7s Kg \n";
                             String[] temp1 = new String[2];
                             String[] temp2 = new String[2];
                             try {
                                 temp1 = dateAndTimeFormatPrint.format(dateAndTimeFormat.parse(textFieldGrossDateTime.getText())).split(" ");

                             } catch (ParseException pe) {
                                 temp1[0] = "";
                                 temp1[1] = "";
                             }
                             try {
                                 temp2 = dateAndTimeFormatPrint.format(dateAndTimeFormat.parse(textFieldTareDateTime.getText())).split(" ");

                             } catch (ParseException pe) {
                                 temp2[0] = "";
                                 temp2[1] = "";
                             }

                             String initString = "\n\n\n\n\n\n\n\n\n\n" + String.format("%85s", "Weighment Slip No : " + textFieldSlNo.getText()) + "\n\n" + StringUtils.center(textFieldLine1.getText(), 82) + "\n" + StringUtils.center(textFieldLine2.getText(), 82) + "\n" + StringUtils.center(textFieldLine3.getText(), 82) + "\n\n" + "           Name of Contractor : " + textFieldNameOfContractor.getText() + "\n\n" + String.format(format1, "Department Name", textFieldNameOfDepartment.getText(), "Vehicle No", comboBoxVehicleNo.getEditor().getItem()) + "\n" + String.format(format1, "Site At", textFieldSiteAt.getText(), "Product", comboBoxMaterial.getEditor().getItem()) + "\n" + String.format(
                                     format2, "Gross Wt.", textFieldGrossWt.getText(), "Date", temp1[0], "Time", temp1[1]) + "\n" + String.format(format2, "Tare Wt.", textFieldTareWt.getText(), "Date", temp2[0], "Time", temp2[1]) + "\n" + String.format(format3, "Nett Wt.", textFieldNetWt.getText()) + "\n\n\n" + textFieldLine4.getText() + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + String.format("%85s", "Weighment Slip No : " + textFieldSlNo.getText()) + "\n\n" + StringUtils.center(textFieldLine1.getText(), 82) + "\n" + StringUtils.center(textFieldLine2.getText(), 82) + "\n" + StringUtils.center(textFieldLine3.getText(), 82) + "\n\n" + "           Name of Contractor : " + textFieldNameOfContractor.getText() + "\n\n" + String.format(format1, "Department Name", textFieldNameOfDepartment.getText(), "Vehicle No", comboBoxVehicleNo.getEditor().getItem()) + "\n" + String.format(format1, "Site At", textFieldSiteAt.getText(), "Product", comboBoxMaterial.getEditor().getItem()) + "\n" + String.format(
                                     format2, "Gross Wt.", textFieldGrossWt.getText(), "Date", temp1[0], "Time", temp1[1]) + "\n" + String.format(format2, "Tare Wt.", textFieldTareWt.getText(), "Date", temp2[0], "Time", temp2[1]) + "\n" + String.format(format3, "Nett Wt.", textFieldNetWt.getText()) + "\n\n\n" + textFieldLine4.getText();

                             graphics.setFont(new Font("Courier New", Font.BOLD, 10));
                             drawString(graphics, initString);
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
                     },
                pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private void printPlainNoOfBagsWeight() {
        JTextPane textPane = createTextPanePlainNoOfBagsWeight();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();

        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 5d * 72d;
        double widthmargin = .50d * 72d;
        double heightmargin = .25d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }
    }

    private JTextPane createTextPanePlainNoOfBagsWeight() {
        String format = " %1$-13s%2$-17s%3$-12s: %4$-20s\n";
        String format1 = "     %1$-14s: %2$-7s Kg          %3$-20s\n";
        String format2 = " %1$-18s: %2$-30s\n";
        String format3 = "     %1$-14s: %2$s";
        String dc = "";
        String driver = "";
        if (!(textFieldDcNo.getText().trim().isEmpty() && textFieldDcDate.getText().trim().isEmpty())) {
            dc = String.format(format, "Dc. No", textFieldDcNo.getText(), "Dc. Date", textFieldDcDate.getText());
        }
        if (chckbxExcludeDrivers.isSelected() || !comboBoxTransporterName.getEditor().getItem().toString().trim().isEmpty()) {
            driver = String.format(format2, "Transporter's Name", comboBoxTransporterName.getEditor().getItem());
        }
        String[] initString = {
                "\n" + StringUtils.center(title1.getText(), 39) + "\n",
                StringUtils.center(title2.getText(), 65) + "\n",
                "-----------------------------------------------------------------\n",
                String.format(format, "Sl.No", ": " + textFieldSlNo.getText(), "Date & Time", textFieldNetDateTime.getText()),
                dc,
                String.format(format2, "Customer's Name", comboBoxCustomerName.getEditor().getItem()),
                driver,
                String.format(format, "Vehicle No", ": " + comboBoxVehicleNo.getEditor().getItem(), "Material", comboBoxMaterial.getEditor().getItem()),
                String.format(format, "", "", "No Of Bags", textFieldNoOfBags.getText()),
                "-----------------------------------------------------------------\n",
                String.format(format1, "Gross Wt", StringUtils.leftPad(textFieldGrossWt.getText(), 7, " "), textFieldGrossDateTime.getText()),
                String.format(format1, "Tare Wt", StringUtils.leftPad(textFieldTareWt.getText(), 7, ""), textFieldTareDateTime.getText()),
                String.format(format1, "Bag Deduction", StringUtils.leftPad(textFieldDeductionOrPerCost.getText(), 7, " "), ""),
                String.format(format1, "Net Wt", StringUtils.leftPad(textFieldNetWt.getText(), 7, " "), "Charges : Rs. " + (textFieldCharges.getText().equals("0") ? "" : textFieldCharges.getText())),
                chckbxExcludeRemarks.isEnabled() && !Objects.equals(textPaneRemarks.getText(), "") ? "" : String.format(format3, "Remarks", textPaneRemarks.getText()) + "\n",
                "-----------------------------------------------------------------\n",
                StringUtils.rightPad(textFieldFooter.getText(), 50, " ") + "Signature"
        };
        String[] initStyles = {
                "1",
                "2",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "4"
        };
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentPlainWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void printReportWeight() {
        JTextPane textPane = createReportWeight();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = .6d * 72d;
        double heightmargin = 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf, 99);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createReportWeight() {
        TableModel model = tableReport.getModel();
        String format = " %1$-5s %2$-19s %3$-15s %4$-15s %5$-8s %6$-8s %7$-8s\n";
        String temp = "\n";
        for (int i = 0; i < model.getRowCount(); i++) {
            temp = temp.concat(String.format(format, StringUtils.center(model.getValueAt(i, 1) != null ? model.getValueAt(i, 1).toString() : "", 5), StringUtils.center(model.getValueAt(i, 22) != null ? model.getValueAt(i, 22).toString() : "", 10), StringUtils.center(model.getValueAt(i, 6) != null ? model.getValueAt(i, 6).toString() : "", 15), StringUtils.center(model.getValueAt(i, 11) != null ? model.getValueAt(i, 11).toString() : "", 15), StringUtils.leftPad(model.getValueAt(i, 15) != null ? model.getValueAt(i, 15).toString() : "", 8, " "), StringUtils.leftPad(model.getValueAt(i, 17) != null ? model.getValueAt(i, 17).toString() : "", 8, " "), StringUtils.leftPad(model.getValueAt(i, 21) != null ? model.getValueAt(i, 21).toString() : "", 8, " ")));
            temp = temp.concat("\n");
        }

        String[] initString = {
                StringUtils.center(title1.getText(), 39) + "\n",
                StringUtils.center(title2.getText(), 65) + "\n",
                StringUtils.center(getTitle(), 65) + "\n",
                "==================================================================================================\n",
                String.format(format, StringUtils.center("Sl.no", 5), StringUtils.center("Date & Time", 19), StringUtils.center("Vehicle No", 15), StringUtils.center("Material", 15), StringUtils.leftPad("Gross Wt", 8), StringUtils.leftPad("Tare Wt", 8), StringUtils.leftPad("Net Wt", 8)),
                "==================================================================================================\n",
                temp,
                "==================================================================================================\n",
                " ",
                "\n\tTotal Net Wt   " + textFieldtotalNetWt.getText(),
                "\n\tCharges   " + textFieldTotalCharges.getText(),
                "\n\t\t\t\t\tSignature"
        };

        String[] initStyles = {
                "1",
                "2",
                "2",
                "3",
                "3",
                "3",
                "3",
                "3",
                "5",
                "5",
                "5",
                "5"
        };

        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesPrintReportWeight(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void addStylesPrintReportWeight(StyledDocument doc) {
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

    private void printReportWeightIceWater() {
        JTextPane textPane = createTextPaneReportWeightIceWater();
        textPane.setBackground(Color.white);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        Paper paper = pf.getPaper();
        double width = 8d * 72d;
        double height = 12d * 72d;
        double widthmargin = .25d * 72d;
        double heightmargin = .6d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(widthmargin, heightmargin, width - (2 * widthmargin), height - (2 * heightmargin));
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.REVERSE_LANDSCAPE);
        Book pBook = new Book();
        pBook.append(textPane.getPrintable(null, null), pf, 99);
        pj.setPageable(pBook);
        try {
            pj.setPrintService(printServices[comboBoxPrinter.getSelectedIndex()]);
            pj.print();
        } catch (PrinterException ignored) {
        }

    }

    private JTextPane createTextPaneReportWeightIceWater() {
        TableModel model = tableReport.getModel();
        String format = " %1$-17s %2$-11s %3$-4s %4$-12s %5$-9s %6$-9s %7$-8s %8$-8s %9$-8s %10$-8s %11$-10s %12$s\n";
        String temp = "\n";
        for (int i = 0; i < model.getRowCount(); i++) {
            int test = (int) (Integer.parseInt(0 + (String.valueOf(model.getValueAt(i, 19))).replaceAll("[^.\\d]", "")) * Double.parseDouble(0 + (String.valueOf(model.getValueAt(i, 12))).replaceAll("[^.\\d]", "")));
            temp = temp.concat(String.format(format, model.getValueAt(i, 4) != null ? model.getValueAt(i, 4).toString() : "", model.getValueAt(i, 6) != null ? model.getValueAt(i, 6).toString() : "", StringUtils.leftPad(model.getValueAt(i, 1) != null ? model.getValueAt(i, 1).toString() : "", 4, " "), model.getValueAt(i, 5) != null ? model.getValueAt(i, 5).toString() : "", StringUtils.center(model.getValueAt(i, 9) != null ? model.getValueAt(i, 9).toString() : "", 9), StringUtils.leftPad(model.getValueAt(i, 19) != null ? model.getValueAt(i, 19).toString() : "", 8, " "), StringUtils.leftPad(model.getValueAt(i, 17) != null ? model.getValueAt(i, 17).toString() : "", 8, " "), StringUtils.leftPad(model.getValueAt(i, 11) != null ? model.getValueAt(i, 11).toString() : "", 8, " "), StringUtils.leftPad(String.valueOf(test), 8, " "), StringUtils.leftPad(model.getValueAt(i, 10) != null ? model.getValueAt(i, 10).toString() : "", 8, " "), StringUtils.leftPad(model.getValueAt(i, 22) != null ? model.getValueAt(i, 22).toString() : "", 8, " "), model.getValueAt(i, 23) != null ? model.getValueAt(i, 23).toString().replaceAll(".{24}(?=.)", "$0\n                                                                                                                    ") : ""));
        }

        String[] initString = {
                StringUtils.center(title1.getText(), 64) + "\n",
                StringUtils.center(title2.getText(), 106) + "\n",
                StringUtils.center(getTitle(), 106) + "\n",
                "==============================================================================================================================================\n",
                String.format(format, "Supplier", "Vehicle No", StringUtils.leftPad("Slip", 4, " "), "Supplier", "Item", StringUtils.leftPad("Purchase", 9, " "), StringUtils.leftPad("Ice", 9, " "), StringUtils.leftPad("Rate", 8, " "), StringUtils.leftPad("Value", 8, " "), StringUtils.leftPad("Freight", 8, " "), StringUtils.leftPad("Nett", 8, " "), "Comments"),
                String.format(format, "", "", StringUtils.leftPad("No", 4, " "), "City", "", "", StringUtils.leftPad("Reduced", 9, " "), "", "", StringUtils.leftPad("Charges", 8, " "), StringUtils.leftPad("Amount", 8, " "), ""),
                "==============================================================================================================================================\n",
                temp,
                "==============================================================================================================================================\n",
                " ",
                "\n\tTotal Final Wt     : " + textFieldtotalNetWt.getText(),
                "\n\tTotal Final Amount : " + textFieldTotalCharges.getText(),
                "\n\t\t\t\t\t\t\t\t\tSignature"
        };

        String[] initStyles = {
                "1",
                "2",
                "2",
                "3",
                "3",
                "3",
                "3",
                "3",
                "3",
                "5",
                "5",
                "5",
                "5"
        };

        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocumentReportWeightIceWater(doc);

        try {
            for (int i = 0; i < initString.length; i++) {
                doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
            }
        } catch (BadLocationException ignored) {
        }
        return textPane;
    }

    private void addStylesToDocumentReportWeightIceWater(StyledDocument doc) {
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

    private String getTitle() {
        if (rdbtnWeighing.isSelected()) {
            switch (Objects.toString(comboBoxReportType.getSelectedItem(), "")) {
                case "Full Report":
                    return "Full Report";
                case "Daily Report":
                    return "Daily Report - " + dateAndTimeFormatdatep.format(datePicker1.getDate());
                case "Datewise Report":
                    return "Datewise Report - " + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to " + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Serialwise Report":
                    return "Serialwise Report";
                case "Vehiclewise Report":
                    return "Vehiclewise Report (" + textFieldReportTextBox.getText() + ") - " + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to " + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Materialwise Report":
                    return "Materialwise Report (" + comboBoxMaterialName.getSelectedItem() + ") - " + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to " + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Customerwise Report":
                    return "Customerwise Report (" + textFieldReportTextBox.getText() + ") - " + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to " + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Customer - Materialwise Report":
                    return "Report (" + textFieldReportTextBox.getText() + " " + comboBoxMaterialName.getSelectedItem() + ") - " + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to " + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Transporterwise Report":
                    return "Transporterwise Report (" + textFieldReportTextBox.getText() + ") - " + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to " + dateAndTimeFormatdatep.format(datePicker2.getDate());
                case "Operatorwise Report":
                    return "Operatorwise Report (" + textFieldReportTextBox.getText() + ") - " + dateAndTimeFormatdatep.format(datePicker1.getDate()) + " to " + dateAndTimeFormatdatep.format(datePicker2.getDate());
            }
        }
        return "Report";
    }

    private void toExcel(String excelFilePath) throws IOException {
        Workbook workbook;
        if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        String safeName = WorkbookUtil.createSafeSheetName("Weighing - " + Objects.requireNonNull(comboBoxReportType.getSelectedItem()));
        Sheet sheet = workbook.createSheet(safeName);
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle cellStyleStringCenter = sheet.getWorkbook().createCellStyle();
        cellStyleStringCenter.setAlignment(HorizontalAlignment.CENTER);
        Cell cell;
        cell = row.createCell(0);
        cell.setCellValue(title1.getText());
        cell.setCellStyle(cellStyleStringCenter);
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue(title2.getText());
        cell.setCellStyle(cellStyleStringCenter);
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue(getTitle());
        cell.setCellStyle(cellStyleStringCenter);
        row = sheet.createRow(rowNum++);
        int j = 0;
        if (chckbxSlNo.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Sl.No");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxDCNo.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Dc. No");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxDCDate.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Dc. Date");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxCustomerName.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue(chckbxIceWater.isSelected() ? "Party's Name" : "Customer's Name");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxTransporterName.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue(chckbxIceWater.isSelected() ? "Party's City" : "Transporter's Name");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxVehicleNo.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Vehicle No");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxVehicleType.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Vehicle Type");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxOperator.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Operator");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxPlace.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Place");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxPhoneNo.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Phone No");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxMaterial.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Material");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxNoOfBags.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue(chckbxIceWater.isSelected() ? "Freight Charges" : "No Of Bags");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxCharges.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue(chckbxIceWater.isSelected() ? "Rate" : "Charges");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxCredit.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Credit");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxGrossWeight.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Gross Wt");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxGrossDateAndTime.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Gross Date & Time");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxTareWeight.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Tare Wt");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxTareDateAndTime.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Tare Date & Time");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxBagDeduction.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue(chckbxIceWater.isSelected() ? "Ice/Water Less" : chckbxRoundOff.isSelected() ? "Price (per kg)" : "Bag Deduction");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxRoundOffColumn.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Round off");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxNettWeight.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Nett Wt");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxNettDateAndTime.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Nett Date & Time");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxFinalWt.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Final Wt");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxFinalAmount.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Final Amount");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxRemarks.isSelected()) {
            cell = row.createCell(j++);
            cell.setCellValue("Remarks");
            cell.setCellStyle(cellStyleStringCenter);
        }
        if (chckbxManual.isSelected()) {
            cell = row.createCell(j);
            cell.setCellValue("Manual");
            cell.setCellStyle(cellStyleStringCenter);
        }
        TableModel model = tableReport.getModel();

        CellStyle cellStyleDateTime = sheet.getWorkbook().createCellStyle();
        cellStyleDateTime.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy hh:mm AM/PM"));

        CellStyle cellStyleDate = sheet.getWorkbook().createCellStyle();
        cellStyleDate.setDataFormat(creationHelper.createDataFormat().getFormat(((SimpleDateFormat) dateAndTimeFormatdate).toPattern()));
        int charge = -1,
                grossWt = -1,
                tareWt = -1,
                roundOff = -1,
                netWt = -1,
                finalWt = -1,
                finalAmount = -1;
        for (int i = 0; i < model.getRowCount(); i++) {
            row = sheet.createRow(rowNum++);
            int c = 0;
            j = 1;
            if (chckbxSlNo.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
            }
            j++;
            if (chckbxDCNo.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyleStringCenter);
            }
            j++;
            if (chckbxDCDate.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyleDate);
            }
            j++;
            if (chckbxCustomerName.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxTransporterName.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxVehicleNo.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxVehicleType.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxOperator.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxPlace.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxPhoneNo.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxMaterial.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxNoOfBags.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
            }
            j++;
            if (chckbxCharges.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Double.parseDouble(0 + model.getValueAt(i, j).toString()));
                charge = c - 1;
            }
            j++;
            if (chckbxCredit.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "false");
            }
            j++;
            if (chckbxGrossWeight.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(model.getValueAt(i, j).toString()));
                grossWt = c - 1;
            }
            j++;
            if (chckbxGrossDateAndTime.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyleDateTime);
            }
            j++;
            if (chckbxTareWeight.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(model.getValueAt(i, j).toString()));
                tareWt = c - 1;
            }
            j++;
            if (chckbxTareDateAndTime.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyleDateTime);
            }
            j++;
            if (chckbxBagDeduction.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Double.parseDouble(0 + model.getValueAt(i, j).toString()));
            }
            j++;
            if (chckbxRoundOffColumn.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Double.parseDouble(0 + model.getValueAt(i, j).toString()));
                roundOff = c - 1;
            }
            j++;
            if (chckbxNettWeight.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
                netWt = c - 1;
            }
            j++;
            if (chckbxNettDateAndTime.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
                cell.setCellStyle(cellStyleDateTime);
            }
            j++;
            if (chckbxFinalWt.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
                finalWt = c - 1;
            }
            j++;
            if (chckbxFinalAmount.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, j).toString()));
                finalAmount = c - 1;
            }
            j++;
            if (chckbxRemarks.isSelected()) {
                cell = row.createCell(c++);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
            j++;
            if (chckbxManual.isSelected()) {
                cell = row.createCell(c);
                cell.setCellValue(model.getValueAt(i, j) != null ? model.getValueAt(i, j).toString() : "");
            }
        }
        String getColumn = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        row = sheet.createRow(rowNum);

        if (charge != -1) {
            cell = row.createCell(charge);
            cell.setCellFormula("SUM(" + getColumn.charAt(charge) + "4:" + getColumn.charAt(charge) + rowNum + ")");
        }
        if (grossWt != -1) {
            cell = row.createCell(grossWt);
            cell.setCellFormula("SUM(" + getColumn.charAt(grossWt) + "4:" + getColumn.charAt(grossWt) + rowNum + ")");
        }
        if (tareWt != -1) {
            cell = row.createCell(tareWt);
            cell.setCellFormula("SUM(" + getColumn.charAt(tareWt) + "4:" + getColumn.charAt(tareWt) + rowNum + ")");
        }
        if (roundOff != -1) {
            cell = row.createCell(roundOff);
            cell.setCellFormula("SUM(" + getColumn.charAt(roundOff) + "4:" + getColumn.charAt(roundOff) + rowNum + ")");
        }
        if (netWt != -1) {
            cell = row.createCell(netWt);
            cell.setCellFormula("SUM(" + getColumn.charAt(netWt) + "4:" + getColumn.charAt(netWt) + rowNum + ")");
        }
        if (finalWt != -1) {
            cell = row.createCell(finalWt);
            cell.setCellFormula("SUM(" + getColumn.charAt(finalWt) + "4:" + getColumn.charAt(finalWt) + rowNum + ")");
        }
        if (finalAmount != -1) {
            cell = row.createCell(finalAmount);
            cell.setCellFormula("SUM(" + getColumn.charAt(finalAmount) + "4:" + getColumn.charAt(finalAmount) + rowNum + ")");
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

    private void toExcelManiAndCo(String excelFilePath) throws IOException {
        Workbook workbook;
        if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        String safeName = WorkbookUtil.createSafeSheetName("Weighing - " + Objects.requireNonNull(comboBoxReportType.getSelectedItem()));
        Sheet sheet = workbook.createSheet(safeName);
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        CellStyle cellStyleStringCenter = sheet.getWorkbook().createCellStyle();
        cellStyleStringCenter.setAlignment(HorizontalAlignment.CENTER);
        Cell cell;
        cell = row.createCell(0);
        cell.setCellValue(title1.getText());
        cell.setCellStyle(cellStyleStringCenter);
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue(title2.getText());
        cell.setCellStyle(cellStyleStringCenter);
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue(textFieldTitle3.getText());
        cell.setCellStyle(cellStyleStringCenter);
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue(getTitle());
        cell.setCellStyle(cellStyleStringCenter);
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("Date");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(1);
        cell.setCellValue("Sl.No");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(2);
        cell.setCellValue("Material");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(3);
        cell.setCellValue("Vehicle No");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(4);
        cell.setCellValue("Laden");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(6);
        cell.setCellValue("Unladen");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(8);
        cell.setCellValue("Nett weight");
        cell.setCellStyle(cellStyleStringCenter);
        row = sheet.createRow(rowNum++);
        cell = row.createCell(4);
        cell.setCellValue("Time");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(5);
        cell.setCellValue("Weight");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(6);
        cell.setCellValue("Time");
        cell.setCellStyle(cellStyleStringCenter);
        cell = row.createCell(7);
        cell.setCellValue("Weight");
        cell.setCellStyle(cellStyleStringCenter);
        TableModel model = tableReport.getModel();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        for (int i = 0; i < model.getRowCount(); i++) {
            row = sheet.createRow(rowNum++);

            cell = row.createCell(0);
            try {
                cell.setCellValue(model.getValueAt(i, 20) != null ? dateFormat.format(new Date(dateAndTimeFormat.parse(model.getValueAt(i, 20).toString()).getTime())) : "");
            } catch (ParseException ignored) {
            }

            cell = row.createCell(1);
            cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, 1).toString()));

            cell = row.createCell(2);
            cell.setCellValue(model.getValueAt(i, 9) != null ? model.getValueAt(i, 9).toString() : "");

            cell = row.createCell(3);
            cell.setCellValue(model.getValueAt(i, 6) != null ? model.getValueAt(i, 6).toString() : "");

            cell = row.createCell(4);
            try {
                cell.setCellValue(model.getValueAt(i, 14) != null ? timeFormat.format(new Date(dateAndTimeFormat.parse(model.getValueAt(i, 14).toString()).getTime())) : "");
            } catch (ParseException ignored) {
            }

            cell = row.createCell(5);
            cell.setCellValue(Integer.parseInt(model.getValueAt(i, 13).toString()));

            cell = row.createCell(6);
            try {
                cell.setCellValue(model.getValueAt(i, 16) != null ? timeFormat.format(new Date(dateAndTimeFormat.parse(model.getValueAt(i, 16).toString()).getTime())) : "");
            } catch (ParseException ignored) {
            }

            cell = row.createCell(7);
            cell.setCellValue(Integer.parseInt(model.getValueAt(i, 15).toString()));

            cell = row.createCell(8);
            cell.setCellValue(Integer.parseInt(0 + model.getValueAt(i, 19).toString()));

        }
        String getColumn = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        row = sheet.createRow(rowNum);

        cell = row.createCell(5);
        cell.setCellFormula("SUM(" + getColumn.charAt(5) + "6:" + getColumn.charAt(5) + rowNum + ")");

        cell = row.createCell(7);
        cell.setCellFormula("SUM(" + getColumn.charAt(7) + "6:" + getColumn.charAt(7) + rowNum + ")");

        cell = row.createCell(8);
        cell.setCellFormula("SUM(" + getColumn.charAt(8) + "6:" + getColumn.charAt(8) + rowNum + ")");

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));

        for (short i = 0; i < 10; ) {
            sheet.autoSizeColumn(i++);
        }
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 7));

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

    private List<Integer> fromExcel(File excelFilePath) throws IOException {
        Workbook workbook;
        FileInputStream file = new FileInputStream(excelFilePath);
        if (excelFilePath.getName().endsWith("xls")) {
            workbook = new HSSFWorkbook(file);
        } else {
            workbook = new XSSFWorkbook(file);
        }
        Sheet sheet = workbook.getSheetAt(0);
        List<Integer> failedSlNo = new ArrayList<>();

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            int colNum = 0;
            try {
                int slNo = (int) row.getCell(colNum).getNumericCellValue();
                boolean update = false;
                if (slNo <= 0) {
                    failedSlNo.add(rowNum);
                    continue;
                }
                Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + slNo);
                if (rs.next()) {
                    rs.absolute(1);
                    update = true;
                } else {
                    rs = stmt.executeQuery("SELECT * FROM WEIGHING");
                    rs.moveToInsertRow();
                }

                rs.updateInt("SLNO", slNo);
                rs.updateString("DCNO", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                if (row.getCell(++colNum) != null && !row.getCell(colNum).toString().trim().isEmpty()) {
                    if (row.getCell(colNum).getCellType() == CellType.STRING) {
                        rs.updateDate("DCNODATE", new java.sql.Date(dateAndTimeFormat.parse(row.getCell(colNum).toString().trim()).getTime()));
                    } else {
                        rs.updateDate("DCNODATE", new java.sql.Date(row.getCell(colNum).getDateCellValue().getTime()));
                    }
                } else {
                    rs.updateDate("DCNODATE", null);
                }

                rs.updateString("CUSTOMERNAME", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                rs.updateString("DRIVERNAME", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                rs.updateString("VEHICLENO", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                rs.updateString("VEHICLE_TYPE", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                rs.updateString("OPERATOR", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                rs.updateString("PLACE", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                rs.updateString("PHONE_NUMBER", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                rs.updateString("MATERIAL", row.getCell(++colNum) != null ? row.getCell(colNum).toString().trim() : "");
                rs.updateInt("NOOFBAGS", row.getCell(++colNum) != null ? (int) row.getCell(colNum).getNumericCellValue() : 0);
                rs.updateDouble("CHARGES", row.getCell(++colNum) != null ? row.getCell(colNum).getNumericCellValue() : 0);
                rs.updateBoolean("CREDIT", row.getCell(++colNum) != null && row.getCell(colNum).toString().equalsIgnoreCase("true"));
                rs.updateInt("GROSSWT", row.getCell(++colNum) != null ? (int) row.getCell(colNum).getNumericCellValue() : 0);
                if (row.getCell(++colNum) != null && !row.getCell(colNum).toString().trim().isEmpty()) {
                    if (row.getCell(colNum).getCellType() == CellType.STRING) {
                        rs.updateDate("GROSSDATE", new java.sql.Date(dateAndTimeFormat.parse(row.getCell(colNum).toString().trim()).getTime()));
                        rs.updateTime("GROSSTIME", new Time(dateAndTimeFormat.parse(row.getCell(colNum).toString().trim()).getTime()));
                    } else {
                        rs.updateDate("GROSSDATE", new java.sql.Date(row.getCell(colNum).getDateCellValue().getTime()));
                        rs.updateTime("GROSSTIME", new Time(row.getCell(colNum).getDateCellValue().getTime()));
                    }
                } else {
                    rs.updateDate("GROSSDATE", null);
                    rs.updateTime("GROSSTIME", null);
                }
                rs.updateInt("TAREWT", row.getCell(++colNum) != null ? (int) row.getCell(colNum).getNumericCellValue() : 0);
                if (row.getCell(++colNum) != null && !row.getCell(colNum).toString().trim().isEmpty()) {
                    if (row.getCell(colNum).getCellType() == CellType.STRING) {
                        rs.updateDate("TAREDATE", new java.sql.Date(dateAndTimeFormat.parse(row.getCell(colNum).toString().trim()).getTime()));
                        rs.updateTime("TARETIME", new Time(dateAndTimeFormat.parse(row.getCell(colNum).toString().trim()).getTime()));
                    } else {
                        rs.updateDate("TAREDATE", new java.sql.Date(row.getCell(colNum).getDateCellValue().getTime()));
                        rs.updateTime("TARETIME", new Time(row.getCell(colNum).getDateCellValue().getTime()));
                    }
                } else {
                    rs.updateDate("TAREDATE", null);
                    rs.updateTime("TARETIME", null);
                }
                rs.updateDouble("DEDUCTION_OR_PER_COST", row.getCell(++colNum) != null ? row.getCell(colNum).getNumericCellValue() : 0);
                rs.updateDouble("ROUND_OFF", row.getCell(++colNum) != null ? row.getCell(colNum).getNumericCellValue() : 0);
                rs.updateInt("NETWT", row.getCell(++colNum) != null ? (int) row.getCell(colNum).getNumericCellValue() : 0);
                if (row.getCell(++colNum) != null && !row.getCell(colNum).toString().trim().isEmpty()) {
                    if (row.getCell(colNum).getCellType() == CellType.STRING) {
                        rs.updateDate("NETDATE", new java.sql.Date(dateAndTimeFormat.parse(row.getCell(colNum).toString().trim()).getTime()));
                        rs.updateTime("NETTIME", new Time(dateAndTimeFormat.parse(row.getCell(colNum).toString().trim()).getTime()));
                    } else {
                        rs.updateDate("NETDATE", new java.sql.Date(row.getCell(colNum).getDateCellValue().getTime()));
                        rs.updateTime("NETTIME", new Time(row.getCell(colNum).getDateCellValue().getTime()));
                    }
                } else {
                    rs.updateDate("NETDATE", null);
                    rs.updateTime("NETTIME", null);
                }
                rs.updateInt("FINALWT", row.getCell(++colNum) != null ? (int) row.getCell(colNum).getNumericCellValue() : 0);
                rs.updateInt("FINALAMOUNT", row.getCell(++colNum) != null ? (int) row.getCell(colNum).getNumericCellValue() : 0);
                rs.updateString("REMARKS", row.getCell(colNum) != null ? row.getCell(colNum).toString().trim().trim() : "");
                rs.updateBoolean("MANUAL", true);
                if (!update) {
                    rs.insertRow();
                } else {
                    rs.updateRow();
                }
            } catch (IllegalStateException | NumberFormatException | SQLException | ParseException |
                     NullPointerException ignored) {
                failedSlNo.add(rowNum + 1);
            }
        }
        workbook.close();
        file.close();
        return failedSlNo;
    }

    private synchronized void initializeWeights() {
        for (SerialPort serialPort : SerialPort.getCommPorts()) {
            if (serialPort.getSystemPortName().equals(textFieldPortName.getText().split(";")[0].toUpperCase())) {
                comPort = serialPort;
                break;
            }
        }
        String[] temp = {
                "8",
                "0",
                "10",
                "~~~",
                "f"
        };
        try {
            temp[0] = textFieldPortName.getText().split(";")[1];
            if (Objects.equals(temp[0], "")) {
                temp[0] = "8";
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            temp[1] = textFieldPortName.getText().split(";")[2];
            if (Objects.equals(temp[1], "")) {
                temp[1] = "0";
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            temp[2] = textFieldPortName.getText().split(";")[3];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            temp[3] = textFieldPortName.getText().split(";")[4];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            temp[4] = textFieldPortName.getText().split(";")[5];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        if (comPort != null) {
            comPort.setComPortParameters(Integer.parseInt(textFieldBaudRate.getText()), Integer.parseInt(0 + temp[0]), SerialPort.ONE_STOP_BIT, Integer.parseInt(0 + temp[1]));
            comPort.openPort();
            comPort.addDataListener(new SerialPortMessageListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                }

                @Override
                public byte[] getMessageDelimiter() {
                    return new byte[]{(byte) (Integer.parseInt(0 + temp[2]) % 128)
                    };
                }

                @Override
                public boolean delimiterIndicatesEndOfMessage() {
                    return true;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (temp[4].equals("t")) {
                        lblWeight.setText(String.valueOf(Integer.parseInt("0" + new StringBuilder(new String(event.getReceivedData())).reverse().toString().replaceAll("[^\\d" + temp[3] + "]", "").split(temp[3])[0])));
                    } else {
                        lblWeight.setText(String.valueOf(Integer.parseInt("0" + new String(event.getReceivedData()).replaceAll("[^\\d" + temp[3] + "]", "").split(temp[3])[0])));
                    }
                }
            });
        }
    }

    private WebcamPanel webcamStarter(WebcamPicker webcamPicker, int i, WebcamPanel panelCamera, JComboBox<DimensionTemplate> comboBoxResolution, JTextField textFieldCropX12, JTextField textFieldCropY12, JTextField textFieldCropWidth12, JTextField textFieldCropHeight12, int x, int y, int l) {
        if (chckbxCamera.isSelected()) {
            try {
                if (webcamPicker.getSelectedWebcam() != null) {
                    if (webcam[i] != null) {
                        Runnable stuffToDo = new Thread(() -> webcam[i].close());
                        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
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
                            textFieldCropWidth12.setText(Integer.toString(((Dimension) Objects.requireNonNull(comboBoxResolution.getSelectedItem())).width));
                            textFieldCropHeight12.setText(Integer.toString(((Dimension) comboBoxResolution.getSelectedItem()).height));
                    }

                    if (!webcam[i].isOpen()) {
                        webcam[i].setViewSize((Dimension) Objects.requireNonNull(comboBoxResolution.getSelectedItem()));
                    }
                    try {
                        webcam[i].open(true);
                    } catch (WebcamException ignored) {
                    }
                    panelCamera = new WebcamPanel(webcam[i]);
                    panelCamera.setBounds(x, y, (int) (((double) 240 / ((Dimension) Objects.requireNonNull(comboBoxResolution.getSelectedItem())).height * ((Dimension) comboBoxResolution.getSelectedItem()).width)), 240);
                    panelCameras.add(panelCamera);
                    lock = true;
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | WebcamException |
                     NullPointerException | ClassCastException ignored) {
                JOptionPane.showMessageDialog(null, "CAMERA ERROR\nCamera has been removed are resolution mismatch\nLINE :1547", "CAMERA ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return panelCamera;
    }

    private void webcamDispose() {
        if (webcam[1] != null) {
            Runnable stuffToDo = new Thread(() -> webcam[1].close());
            try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
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
        }
        if (webcam[2] != null) {
            Runnable stuffToDo = new Thread(() -> webcam[2].close());

            try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
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
        }
        if (webcam[3] != null) {
            Runnable stuffToDo = new Thread(() -> webcam[3].close());
            try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
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
        }
        if (webcam[4] != null) {
            Runnable stuffToDo = new Thread(() -> webcam[4].close());
            try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
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

    private void sentSMS(String mobileNo) {
        String smsMessage = "Sl.No : " + textFieldSlNo.getText() + "\nDate & Time : " + textFieldNetDateTime.getText() + "\nVehicle No : " + comboBoxVehicleNo.getEditor().getItem() + "\nMaterial : " + comboBoxMaterial.getEditor().getItem() + "\nGross Wt : " + textFieldGrossWt.getText() + " Kg" + "\nTare Wt : " + textFieldTareWt.getText() + " Kg" + "\nNet Wt : " + textFieldNetWt.getText() + " Kg" + "\nFrom " + textFieldTitle1.getText() + "\r";

        SerialPort serialPortSms = null;
        for (SerialPort serialPort : SerialPort.getCommPorts()) {
            if (serialPort.getSystemPortName().equals(textFieldSMSPortName.getText())) {
                serialPortSms = serialPort;
                break;
            }
        }
        if (serialPortSms != null) {
            serialPortSms.setComPortParameters(Integer.parseInt(textFieldSMSBaudRate.getText()), 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            serialPortSms.openPort();

            List<byte[]> sendData = new ArrayList<>();
            sendData.add(("AT\r").getBytes());
            sendData.add(("AT+CMGF=1\r").getBytes());
            sendData.add(("AT+CMGS=\"" + mobileNo + "\"\r").getBytes());
            sendData.add((smsMessage.getBytes()));
            sendData.add(new byte[]{
                    0x1A
            });

            serialPortSms.writeBytes(sendData.get(0), sendData.get(0).length);

            try {
                SerialPort finalSerialPortSms = serialPortSms;
                serialPortSms.addDataListener(new SerialPortDataListener() {
                    int i = 0;

                    @Override
                    public int getListeningEvents() {
                        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                    }

                    @Override
                    public void serialEvent(SerialPortEvent event) {
                        switch (i) {
                            case 0:
                            case 1:
                                if (event.getReceivedData()[0] == 75) {
                                    break;
                                }
                                return;
                            case 2:
                            case 3:
                                if (event.getReceivedData()[0] == 62) {
                                    break;
                                }
                                return;
                            case 4:
                                if (event.getReceivedData()[0] == 75) {
                                    //noinspection InstantiatingAThreadWithDefaultRunMethod
                                    new Thread().interrupt();
                                }
                                return;
                        }
                        i++;
                        finalSerialPortSms.writeBytes(sendData.get(i), sendData.get(i).length);
                    }
                });
                Thread.sleep(1000);
                serialPortSms.writeBytes(sendData.get(4), sendData.get(4).length);
            } catch (InterruptedException ignored) {
            } finally {
                serialPortSms.closePort();
            }
        } else {
            JOptionPane.showMessageDialog(null, "SMS ERROR\nSMS Function not working please check the connection 0or check the number entered", "SMS ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void close(ActionEvent... ae) {
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM setup");
            rs.absolute(1);
            rs.updateTimestamp("LASTLOGIN", new Timestamp(new Date().getTime()));
            rs.updateRow();
        } catch (SQLException ignored) {
            JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :7720", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
        }
        try {
            dbConnection.close();
        } catch (SQLException ignored) {
        }
        if (comPort != null) {
            comPort.removeDataListener();
            comPort.closePort();
        }
        webcamDispose();
        System.exit(0);
    }

    private void onSave(ActionEvent... ae) {
        if (chckbxCamera.isSelected()) {
            if (checkBoxCamera1.isSelected()) {
                File outputfile = new File("CameraOutput/" + textFieldSlNo.getText() + "_1.jpg");
                try {
                    ImageIO.write(webcam[1].getImage(), "jpg", outputfile);
                } catch (IOException | IllegalArgumentException ignored) {
                    JOptionPane.showMessageDialog(null, "CAMERA ERROR\nCHECK THE CAMERA IN SETTINGS\nLINE :1370", "CAMERA ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (checkBoxCamera2.isSelected()) {
                File outputfile = new File("CameraOutput/" + textFieldSlNo.getText() + "_2.jpg");
                try {
                    ImageIO.write(webcam[2].getImage(), "jpg", outputfile);
                } catch (IOException | IllegalArgumentException ignored) {
                    JOptionPane.showMessageDialog(null, "CAMERA ERROR\nCHECK THE CAMERA IN SETTINGS\nLINE :1370", "CAMERA ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (checkBoxCamera3.isSelected()) {
                File outputfile = new File("CameraOutput/" + textFieldSlNo.getText() + "_3.jpg");
                try {
                    ImageIO.write(webcam[3].getImage(), "jpg", outputfile);
                } catch (IOException | IllegalArgumentException ignored) {
                    JOptionPane.showMessageDialog(null, "CAMERA ERROR\nCHECK THE CAMERA IN SETTINGS\nLINE :1370", "CAMERA ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (checkBoxCamera4.isSelected()) {
                File outputfile = new File("CameraOutput/" + textFieldSlNo.getText() + "_4.jpg");
                try {
                    ImageIO.write(webcam[4].getImage(), "jpg", outputfile);
                } catch (IOException | IllegalArgumentException ignored) {
                    JOptionPane.showMessageDialog(null, "CAMERA ERROR\nCHECK THE CAMERA IN SETTINGS\nLINE :1370", "CAMERA ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            String vehicleNo = (String) comboBoxVehicleNo.getEditor().getItem();
            boolean update = false;
            if (chckbxTareNoSlno.isSelected()) {
                rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + textFieldSlNo.getText());
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
            if (!textFieldDcDate.getText().isEmpty()) {
                Date date = dateAndTimeFormatdate.parse(textFieldDcDate.getText());
                rs.updateDate("DCNODATE", new java.sql.Date(date.getTime()));
            }
            String temp = (String.valueOf(comboBoxCustomerName.getSelectedItem())).toUpperCase();
            if (temp.equals("NULL")) {
                temp = "";
            }
            rs.updateString("CUSTOMERNAME", temp);
            temp = (String.valueOf(comboBoxTransporterName.getSelectedItem())).toUpperCase();
            if (temp.equals("NULL")) {
                temp = "";
            }
            rs.updateString("DRIVERNAME", temp);
            rs.updateString("VEHICLENO", vehicleNo);
            rs.updateString("VEHICLE_TYPE", (String) comboBoxVehicleType.getSelectedItem());
            rs.updateString("OPERATOR", lblOperatorName.getText());
            rs.updateString("PLACE", textFieldPlace.getText());
            rs.updateString("PHONE_NUMBER", textFieldPhoneNo.getText());
            rs.updateString("MATERIAL", (String) comboBoxMaterial.getSelectedItem());
            rs.updateInt("NOOFBAGS", Integer.parseInt(0 + textFieldNoOfBags.getText().replaceAll("\\D", "")));
            rs.updateDouble("CHARGES", Double.parseDouble(0 + textFieldCharges.getText().replaceAll("[^.\\d]", "")));
            rs.updateBoolean("CREDIT", chckbxIsCredit.isSelected());
            rs.updateInt("GROSSWT", Integer.parseInt(textFieldGrossWt.getText()));
            rs.updateString("REMARKS", textPaneRemarks.getText());

            if (!textFieldGrossDateTime.getText().isEmpty()) {
                Date date = dateAndTimeFormat.parse(textFieldGrossDateTime.getText());
                rs.updateDate("GROSSDATE", new java.sql.Date(date.getTime()));
                rs.updateTime("GROSSTIME", new Time(date.getTime()));
            }
            rs.updateInt("TAREWT", Integer.parseInt(textFieldTareWt.getText()));
            if (!textFieldTareDateTime.getText().isEmpty()) {
                Date date = dateAndTimeFormat.parse(textFieldTareDateTime.getText());
                rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                rs.updateTime("TARETIME", new Time(date.getTime()));
            }
            rs.updateDouble("DEDUCTION_OR_PER_COST", Double.parseDouble(0 + textFieldDeductionOrPerCost.getText().replaceAll("[^.\\d]", "")));
            rs.updateDouble("ROUND_OFF", Double.parseDouble(0 + textFieldRoundOff.getText().replaceAll("[^-.\\d]", "")));
            rs.updateInt("NETWT", Integer.parseInt(0 + textFieldNetWt.getText()));
            if (!textFieldNetDateTime.getText().isEmpty()) {
                Date date = dateAndTimeFormat.parse(textFieldNetDateTime.getText());
                rs.updateDate("NETDATE", new java.sql.Date(date.getTime()));
                rs.updateTime("NETTIME", new Time(date.getTime()));
            }
            rs.updateInt("FINALWT", Integer.parseInt(0 + textFieldFinalWt.getText()));
            rs.updateInt("FINALAMOUNT", Integer.parseInt(0 + textFieldFinalAmount.getText()));
            rs.updateBoolean("MANUAL", chckbxManualEntry.isSelected());
            if (!update) {
                rs.insertRow();
                rs = stmt.executeQuery("SELECT * FROM SETTINGS");
                rs.absolute(1);
                rs.updateInt("SLNO", Integer.parseInt(textFieldSlNo.getText()) + 1);
            }
            if (vehicleNoSet.add(vehicleNo)) {
                comboBoxVehicleNo.addItem(vehicleNo);
            }
            rs.updateRow();
            if (rdbtnTare.isSelected() && Integer.parseInt(textFieldTareWt.getText()) > 0) {
                stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery("SELECT * FROM VEHICLETARES WHERE VEHICLENO LIKE '" + comboBoxVehicleNo.getEditor().getItem() + "'");
                if (rs.next()) {
                    rs.updateInt("TAREWT", Integer.parseInt(textFieldTareWt.getText()));
                    Date date = dateAndTimeFormat.parse(textFieldTareDateTime.getText());
                    rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                    rs.updateTime("TARETIME", new Time(date.getTime()));
                    rs.updateString("PLACE", textFieldPlace.getText());
                    rs.updateString("PHONE_NUMBER", textFieldPhoneNo.getText());
                    rs.updateRow();
                } else {
                    rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
                    int sqNo = -1;
                    if (rs.last()) {
                        sqNo = rs.getInt("SQNO");
                    }
                    rs = stmt.executeQuery("SELECT * FROM VEHICLETARES");
                    rs.moveToInsertRow();
                    rs.updateString("VEHICLENO", vehicleNo);
                    rs.updateInt("TAREWT", Integer.parseInt(textFieldTareWt.getText()));
                    Date date = dateAndTimeFormat.parse(textFieldTareDateTime.getText());
                    rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                    rs.updateTime("TARETIME", new Time(date.getTime()));
                    rs.updateString("PLACE", textFieldPlace.getText());
                    rs.updateString("PHONE_NUMBER", textFieldPhoneNo.getText());
                    rs.updateInt("SQNO", sqNo + 1);
                    rs.insertRow();
                }
            }
        } catch (NumberFormatException ignored) {
            JOptionPane.showMessageDialog(null, "WEIGHT ERROR\nWeight is incorrect or negative\nLINE :1851", "WEIGHT ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (SQLException | ParseException ignored) {
            if (runUpdateSQL()) {
                onSave();
            } else {
                JOptionPane.showMessageDialog(null, "SQL ERROR\nCHECK THE VALUES ENTERED\nLINE :1854", "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM TRANSPORTER");
            rs.moveToInsertRow();
            String temp = (String) comboBoxTransporterName.getSelectedItem();
            rs.updateString("TRANSPORTER", temp);
            if (temp != null && !temp.isEmpty() && transportSet.add(temp)) {
                comboBoxTransporterName.addItem(temp);
                rs.insertRow();
            }
        } catch (SQLException ignored) {
        }
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
            rs.moveToInsertRow();
            String temp = (String) comboBoxCustomerName.getSelectedItem();
            rs.updateString("CUSTOMER", temp);
            if (temp != null && !temp.isEmpty() && customerSet.add(temp)) {
                comboBoxCustomerName.addItem(temp);
                rs.insertRow();
            }
        } catch (SQLException ignored) {
        }
        try {
            Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLE_TYPES");
            rs.moveToInsertRow();
            String temp = (String) comboBoxVehicleType.getSelectedItem();
            rs.updateString("VEHICLE_TYPE", temp);
            rs.updateInt("TARE_COST", 0);
            rs.updateInt("GROSS_COST", 0);
            if (temp != null && !temp.isEmpty() && vehicleTypeSet.add(temp)) {
                comboBoxVehicleType.addItem(temp);
                rs.insertRow();
            }
        } catch (SQLException ignored) {
        }
        btnSave.setEnabled(false);
        btnPrint.setEnabled(true);
        btnPrintToken.setEnabled(true);
        btnPrintExitPass.setEnabled(true);
        btnPrint.requestFocus();
    }

    private void cameraEvent(ActionEvent... ae) {
        if (chckbxCamera.isSelected()) {
            if (lock1) {
                tabbedPane.setEnabledAt(1, true);
                tabbedPane.setTitleAt(1, "          Cameras          ");
                if (checkBoxCamera1.isSelected()) {
                    panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1, textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 2);
                }
                if (checkBoxCamera2.isSelected()) {
                    panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2, textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 2);
                }
                if (checkBoxCamera3.isSelected()) {
                    panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3, textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 2);
                }
                if (checkBoxCamera4.isSelected()) {
                    panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4, textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 2);
                }
                btnClick.setEnabled(true);
                btnClick.setVisible(true);
                butttonUpdateCamera.setEnabled(true);
                buttonUnLockCamera.setEnabled(true);

            } else {
                JPasswordField password = new JPasswordField(10);
                password.addActionListener(_ -> JOptionPane.getRootFrame().dispose());
                JPanel panel = new JPanel();
                String[] ConnectOptionNames = {
                        "Enter",
                        "Cancel"
                };
                panel.add(new JLabel("Please Enter the Camera Password ? "));
                panel.add(password);
                JOptionPane.showOptionDialog(null, panel, "Password ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ConnectOptionNames, null);
                char[] temp = password.getPassword();
                boolean isCorrect;
                char[] correctPassword = CAMERA_PASSWORD.toCharArray();
                if (temp.length != correctPassword.length) {
                    isCorrect = false;
                } else {
                    isCorrect = Arrays.equals(temp, correctPassword);
                }
                if (isCorrect) {
                    tabbedPane.setEnabledAt(1, true);
                    tabbedPane.setTitleAt(1, "          Cameras          ");
                    if (checkBoxCamera1.isSelected()) {
                        panelCamera1 = webcamStarter(webcamPicker1, 1, panelCamera1, comboBoxResolution1, textFieldCropX1, textFieldCropY1, textFieldCropWidth1, textFieldCropHeight1, 10, 11, 2);
                    }
                    if (checkBoxCamera2.isSelected()) {
                        panelCamera2 = webcamStarter(webcamPicker2, 2, panelCamera2, comboBoxResolution2, textFieldCropX2, textFieldCropY2, textFieldCropWidth2, textFieldCropHeight2, 617, 11, 2);
                    }
                    if (checkBoxCamera3.isSelected()) {
                        panelCamera3 = webcamStarter(webcamPicker3, 3, panelCamera3, comboBoxResolution3, textFieldCropX3, textFieldCropY3, textFieldCropWidth3, textFieldCropHeight3, 10, 310, 2);
                    }
                    if (checkBoxCamera4.isSelected()) {
                        panelCamera4 = webcamStarter(webcamPicker4, 4, panelCamera4, comboBoxResolution4, textFieldCropX4, textFieldCropY4, textFieldCropWidth4, textFieldCropHeight4, 617, 310, 2);
                    }
                    btnClick.setEnabled(true);
                    btnClick.setVisible(false);
                    butttonUpdateCamera.setEnabled(true);
                    buttonUnLockCamera.setEnabled(true);
                } else {
                    chckbxCamera.setSelected(false);
                }
            }
        } else {
            webcamDispose();
            tabbedPane.setEnabledAt(1, false);
            tabbedPane.setTitleAt(1, "");
            btnClick.setEnabled(false);
            btnClick.setVisible(false);
            butttonUpdateCamera.setEnabled(false);
            buttonUnLockCamera.setEnabled(false);
        }
    }

    private void findReport(ActionEvent... ae) {
        String message = "Plz Choose The Column To Show In Report ?";
        int n = -1;
        if (rdbtnWeighing.isSelected()) {
            Object[] params;
            chckbxTransporterName.setText(chckbxIceWater.isSelected() ? "Party's City" : "Transporter's Name");
            chckbxCustomerName.setText(chckbxIceWater.isSelected() ? "Party's Name" : "Customer's Name");
            chckbxBagDeduction.setText(chckbxIceWater.isSelected() ? "Ice/Water Less" : chckbxRoundOff.isSelected() ? "Price (per kg)" : "Bag Deduction");
            chckbxCharges.setText(chckbxIceWater.isSelected() ? "Rate" : "Charges");
            chckbxNoOfBags.setText(chckbxIceWater.isSelected() ? "Freight Charges" : "No Of Bags");
            if (chckbxManualStatus.isSelected()) {
                params = new Object[]{
                        message,
                        chckbxSlNo,
                        chckbxDCNo,
                        chckbxDCDate,
                        chckbxCustomerName,
                        chckbxTransporterName,
                        chckbxVehicleNo,
                        chckbxVehicleType,
                        chckbxOperator,
                        chckbxPlace,
                        chckbxPhoneNo,
                        chckbxMaterial,
                        chckbxNoOfBags,
                        chckbxCharges,
                        chckbxCredit,
                        chckbxGrossWeight,
                        chckbxGrossDateAndTime,
                        chckbxTareWeight,
                        chckbxTareDateAndTime,
                        chckbxBagDeduction,
                        chckbxRoundOffColumn,
                        chckbxNettWeight,
                        chckbxNettDateAndTime,
                        chckbxFinalWt,
                        chckbxFinalAmount,
                        chckbxRemarks,
                        chckbxManual
                };
            } else {
                params = new Object[]{
                        message,
                        chckbxSlNo,
                        chckbxDCNo,
                        chckbxDCDate,
                        chckbxCustomerName,
                        chckbxTransporterName,
                        chckbxVehicleNo,
                        chckbxVehicleType,
                        chckbxOperator,
                        chckbxPlace,
                        chckbxPhoneNo,
                        chckbxMaterial,
                        chckbxNoOfBags,
                        chckbxCharges,
                        chckbxCredit,
                        chckbxGrossWeight,
                        chckbxGrossDateAndTime,
                        chckbxTareWeight,
                        chckbxTareDateAndTime,
                        chckbxBagDeduction,
                        chckbxRoundOffColumn,
                        chckbxNettWeight,
                        chckbxNettDateAndTime,
                        chckbxFinalWt,
                        chckbxFinalAmount,
                        chckbxRemarks
                };
            }
            n = JOptionPane.showConfirmDialog(null, params, "Choose The Columns", JOptionPane.OK_CANCEL_OPTION);
        }
        if (n == 0) {
            getReport();
        }
    }

    private BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {
        if (img1 == null) {
            return img2;
        }

        if (img2 == null) {
            return img1;
        }

        int offset = 2;
        int width = Math.max(img1.getWidth(), img2.getWidth());
        int height = img1.getHeight() + img2.getHeight() + offset;
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, 0, img1.getHeight() + offset);
        g2.dispose();

        return newImage;
    }

    static class IpCam extends IpCamDriver {
        IpCam() {
            try {
                super.register(new IpCamDevice("No Camera Available", "http:", IpCamMode.PULL));
            } catch (MalformedURLException ignored) {
            }
        }
    }

    static class CompositeDriver extends WebcamCompositeDriver {

        CompositeDriver() {
            try {
                add(new IpCamDriver(new IpCamStorage("cameras.xml")));

            } catch (NullPointerException | WebcamException ignored) {
                add(new IpCam());
            }
            add(new WebcamDefaultDriver());
        }
    }

    record Coordinates(int x, int y) {
    }

    static class DimensionTemplate extends Dimension {
        @Serial
        private static final long serialVersionUID = 1L;

        DimensionTemplate(Dimension d) {
            super(d);
        }

        @Override
        public String toString() {
            return "  " + width + " * " + height;
        }
    }

    static class DivideByZeroException extends Exception {
        @Serial
        private static final long serialVersionUID = 1L;

        DivideByZeroException() {
            super();
        }

    }

    static class TableButtonRenderer extends JButton implements TableCellRenderer {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setForeground(Color.black);
            setBackground(UIManager.getColor("Button.background"));
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    static class Calculator extends JFrame implements ActionListener {
        @Serial
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
                if (i < 10) {
                    jbButtons[i].setForeground(Color.blue);
                } else {
                    jbButtons[i].setForeground(Color.red);
                }
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

        @Override
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
                                    if (getDisplayString().indexOf("-") == 0) {
                                        displayError("Invalid input for function.");
                                    }

                                    result = Math.sqrt(getNumberInDisplay());
                                    displayResult(result);
                                } catch (Exception ignored) {
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
                                } catch (Exception ignored) {
                                    displayError("Invalid input for function.");
                                    displayMode = ERROR_MODE;
                                }
                            }
                            break;

                        case 19:
                            if (displayMode != ERROR_MODE) {
                                try {
                                    if (getNumberInDisplay() == 0) {
                                        displayError("Cannot divide by zero.");
                                    }
                                    result = 1 / getNumberInDisplay();
                                    displayResult(result);
                                } catch (Exception ignored) {
                                    displayError("Cannot divide by zero.");
                                    displayMode = ERROR_MODE;
                                }
                            }
                            break;

                        case 24:
                            if (displayMode != ERROR_MODE) {
                                setDisplayString(getDisplayString().substring(0, getDisplayString().length() - 1));
                                if (getDisplayString().isEmpty()) {
                                    setDisplayString("0");
                                }
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
                if (!input.isEmpty() && !input.equals("0")) {
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
                    } catch (DivideByZeroException ignored) {
                        displayError("Cannot divide by zero.");
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
                } catch (DivideByZeroException ignored) {
                    displayError("Cannot divide by zero.");
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
            setDisplayString(decimalFormat.format(result));
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

    static class TableReport extends DefaultTableModel {
        @Serial
        private static final long serialVersionUID = 1L;

        private final Set<Integer> editableRow = new HashSet<>();

        public TableReport(Object[][] objects, String[] strings) {
            super(objects, strings);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return switch (column) {
                case 0 -> true;
                case 1, 24 -> false;
                default -> this.editableRow.contains(row);
            };
        }

        public void removeEditableRow(int row) {
            this.editableRow.remove(row);
        }

        public void addEditableRow(int row) {
            this.editableRow.add(row);
        }
    }

    static class ConvertNumberToWord {

        private static final String[] units = {
                "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        };
        private static final String[] tens = {
                "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
        };

        public static String format(double number) {
            if (number == 0) {
                return "Zero";
            }
            if (number < 0) {
                return "Minus " + format(Math.abs(number));
            }
            if (number < 20) {
                return units[(int) number];
            }
            if (number < 100) {
                return tens[(int) (number / 10)] + ((number % 10 != 0) ? " " : "") + units[(int) (number % 10)];
            }
            if (number < 1000) {
                return units[(int) (number / 100)] + " Hundred" + ((number % 100 != 0) ? " " : "") + format(number % 100);
            }
            if (number < 100000) {
                return format(number / 1000) + " Thousand" + ((number % 1000 != 0) ? " " : "") + format(number % 1000);
            }
            if (number < 10000000) {
                return format(number / 100000) + " Lakh" + ((number % 100000 != 0) ? " " : "") + format(number % 100000);
            }
            if (number < 1000000000) {
                return format(number / 10000000) + " Crore" + ((number % 10000000 != 0) ? " " : "") + format(number % 10000000);
            }
            if (number < 100000000000d) {
                return format(number / 1000000000) + " Arab" + ((number % 1000000000 != 0) ? " " : "") + format(number % 1000000000);
            }
            if (number < 10000000000000d) {
                return format(number / 100000000000d) + " Kharab" + ((number % 100000000000d != 0) ? " " : "") + format(number % 100000000000d);
            }
            return "Number out of range";
        }

    }

    private class TableRenderer extends DefaultCellEditor {

        @Serial
        private static final long serialVersionUID = 1L;
        private final JButton button = new JButton();
        private String label;
        private boolean clicked;
        private int row;

        public TableRenderer(JCheckBox checkBox) {
            super(checkBox);
            this.button.addActionListener(_ -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;

            button.setForeground(Color.black);
            button.setBackground(UIManager.getColor("Button.background"));
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                if (label.equals("Save")) {
                    Statement stmt;
                    try {
                        stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        DefaultTableModel model = (DefaultTableModel) tableReport.getModel();

                        ResultSet rs = stmt.executeQuery("SELECT * FROM WEIGHING WHERE SLNO = " + model.getValueAt(row, 1));

                        if (rs.next()) {
                            int col = 1;
                            rs.updateString("DCNO", (String) model.getValueAt(row, ++col));

                            if (!(String.valueOf(model.getValueAt(row, ++col))).trim().isEmpty()) {
                                Date date = dateAndTimeFormatdate.parse(String.valueOf(model.getValueAt(row, col)));
                                rs.updateDate("DCNODATE", new java.sql.Date(date.getTime()));
                            } else {
                                rs.updateDate("DCNODATE", null);
                            }

                            rs.updateString("CUSTOMERNAME", (String) model.getValueAt(row, ++col));
                            rs.updateString("DRIVERNAME", (String) model.getValueAt(row, ++col));
                            rs.updateString("VEHICLENO", (String) model.getValueAt(row, ++col));
                            rs.updateString("VEHICLE_TYPE", (String) model.getValueAt(row, ++col));
                            rs.updateString("OPERATOR", (String) model.getValueAt(row, ++col));
                            rs.updateString("PLACE", (String) model.getValueAt(row, ++col));
                            rs.updateString("PHONE_NUMBER", (String) model.getValueAt(row, ++col));
                            rs.updateString("MATERIAL", (String) model.getValueAt(row, ++col));
                            rs.updateInt("NOOFBAGS", Integer.parseInt("0" + model.getValueAt(row, ++col)));
                            rs.updateDouble("CHARGES", Double.parseDouble("0" + model.getValueAt(row, ++col)));
                            rs.updateBoolean("CREDIT", model.getValueAt(row, ++col).toString().equalsIgnoreCase("true"));
                            rs.updateInt("GROSSWT", Integer.parseInt(String.valueOf(model.getValueAt(row, ++col))));

                            if (!(String.valueOf(model.getValueAt(row, ++col))).trim().isEmpty()) {
                                Date date = dateAndTimeFormat.parse(String.valueOf(model.getValueAt(row, col)));
                                rs.updateDate("GROSSDATE", new java.sql.Date(date.getTime()));
                                rs.updateTime("GROSSTIME", new Time(date.getTime()));
                            } else {
                                rs.updateDate("GROSSDATE", null);
                                rs.updateTime("GROSSTIME", null);
                            }

                            rs.updateInt("TAREWT", Integer.parseInt(String.valueOf(model.getValueAt(row, ++col))));

                            if (!(String.valueOf(model.getValueAt(row, ++col))).trim().isEmpty()) {
                                Date date = dateAndTimeFormat.parse(String.valueOf(model.getValueAt(row, col)));
                                rs.updateDate("TAREDATE", new java.sql.Date(date.getTime()));
                                rs.updateTime("TARETIME", new Time(date.getTime()));
                            } else {
                                rs.updateDate("TAREDATE", null);
                                rs.updateTime("TARETIME", null);
                            }
                            rs.updateDouble("DEDUCTION_OR_PER_COST", Double.parseDouble("0" + model.getValueAt(row, ++col)));
                            rs.updateDouble("ROUND_OFF", Double.parseDouble("0" + model.getValueAt(row, ++col)));
                            rs.updateInt("NETWT", Integer.parseInt("0" + model.getValueAt(row, ++col)));
                            if (!(String.valueOf(model.getValueAt(row, ++col))).trim().isEmpty()) {
                                Date date = dateAndTimeFormat.parse(String.valueOf(model.getValueAt(row, col)));
                                rs.updateDate("NETDATE", new java.sql.Date(date.getTime()));
                                rs.updateTime("NETTIME", new Time(date.getTime()));
                            } else {
                                rs.updateDate("NETDATE", null);
                                rs.updateTime("NETTIME", null);
                            }

                            rs.updateInt("FINALWT", Integer.parseInt("0" + model.getValueAt(row, ++col)));
                            rs.updateInt("FINALAMOUNT", Integer.parseInt("0" + model.getValueAt(row, ++col)));

                            rs.updateString("REMARKS", model.getValueAt(row, ++col) != null ? String.valueOf(model.getValueAt(row, col)) : "");
                            rs.updateBoolean("MANUAL", true);
                            rs.updateRow();

                            String date,
                                    time,
                                    gross,
                                    tare,
                                    net;
                            date = String.valueOf(rs.getDate("GROSSDATE"));
                            if (date.equals("null")) {
                                date = "";
                            } else {
                                date = dateAndTimeFormatdate.format(rs.getDate("GROSSDATE"));
                            }
                            time = String.valueOf(rs.getTime("GROSSTIME"));
                            if (time.equals("null")) {
                                time = "";
                            } else {
                                time = dateAndTimeFormattime.format(rs.getTime("GROSSTIME"));
                            }
                            gross = date + " " + time;
                            date = String.valueOf(rs.getDate("TAREDATE"));
                            if (date.equals("null")) {
                                date = "";
                            } else {
                                date = dateAndTimeFormatdate.format(rs.getDate("TAREDATE"));
                            }
                            time = String.valueOf(rs.getTime("TARETIME"));
                            if (time.equals("null")) {
                                time = "";
                            } else {
                                time = dateAndTimeFormattime.format(rs.getTime("TARETIME"));
                            }
                            tare = date + " " + time;
                            date = String.valueOf(rs.getDate("NETDATE"));
                            if (date.equals("null")) {
                                date = "";
                            } else {
                                date = dateAndTimeFormatdate.format(rs.getDate("NETDATE"));
                            }
                            time = String.valueOf(rs.getTime("NETTIME"));
                            if (time.equals("null")) {
                                time = "";
                            } else {
                                time = dateAndTimeFormattime.format(rs.getTime("NETTIME"));
                            }
                            net = date + " " + time;
                            col = 1;
                            for (Object cell : new Object[]{
                                    rs.getString("DCNO"),
                                    (String.valueOf(rs.getDate("DCNODATE"))).equals("null") ? "" : dateAndTimeFormatdate.format(rs.getDate("DCNODATE")),
                                    rs.getString("CUSTOMERNAME"),
                                    rs.getString("DRIVERNAME"),
                                    rs.getString("VEHICLENO"),
                                    rs.getString("VEHICLE_TYPE"),
                                    rs.getString("OPERATOR"),
                                    rs.getString("PLACE"),
                                    rs.getString("PHONE_NUMBER"),
                                    rs.getString("MATERIAL"),
                                    rs.getInt("NOOFBAGS"),
                                    decimalFormat.format(rs.getDouble("CHARGES")),
                                    rs.getBoolean("CREDIT"),
                                    rs.getInt("GROSSWT"),
                                    gross,
                                    rs.getInt("TAREWT"),
                                    tare,
                                    decimalFormat.format(rs.getDouble("DEDUCTION_OR_PER_COST")),
                                    decimalFormat.format(rs.getDouble("ROUND_OFF")),
                                    rs.getInt("NETWT"),
                                    net,
                                    rs.getInt("FINALWT"),
                                    rs.getInt("FINALAMOUNT"),
                                    rs.getString("REMARKS"),
                                    rs.getBoolean("MANUAL")
                            }) {
                                model.setValueAt(cell, row, ++col);
                            }

                            label = "Edit";
                            ((TableReport) tableReport.getModel()).removeEditableRow(row);
                        }
                    } catch (SQLException | ParseException | NumberFormatException | NullPointerException |
                             ClassCastException ignored) {
                        JOptionPane.showMessageDialog(null, "DATA ERROR\nCHECK THE VALUES ENTERED IN ALL FIELDS\nLINE :7037", "DATA ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    label = "Save";
                    ((TableReport) tableReport.getModel()).addEditableRow(row);
                }
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
}
