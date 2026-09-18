import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/*
 * ================================================================
 * DIGITAL FOOTPRINT ANALYZER
 * Privacy Risk Assessment System
 *
 * Single-file Java project
 *
 * Features:
 * 1. Welcome Screen
 * 2. Privacy Assessment
 * 3. Risk Calculation Engine
 * 4. Category-wise Risk Analysis
 * 5. Privacy Score
 * 6. Risk Level
 * 7. Personalized Recommendations
 * 8. Dashboard
 * 9. Risk Report
 * 10. Assessment History
 * 11. Before/After Simulation
 * 12. Privacy Education
 * 13. Export Report
 *
 * No passwords or account credentials are collected.
 * ================================================================
 */

public class DigitalFootprintAnalyzer extends JFrame {

        // ================================================================
        // COLORS
        // ================================================================

        private final Color BG = new Color(245, 247, 250);
        private final Color CARD = Color.WHITE;
        private final Color PRIMARY = new Color(55, 90, 150);
        private final Color DARK = new Color(35, 40, 50);
        private final Color TEXT = new Color(55, 60, 70);
        private final Color LIGHT_TEXT = new Color(110, 115, 125);
        private final Color BORDER = new Color(220, 224, 230);

        // ================================================================
        // MAIN COMPONENTS
        // ================================================================

        private CardLayout cardLayout;
        private JPanel mainPanel;

        private AssessmentPanel assessmentPanel;
        private DashboardPanel dashboardPanel;
        private ReportPanel reportPanel;
        private HistoryPanel historyPanel;
        private EducationPanel educationPanel;
        private SimulatorPanel simulatorPanel;

        // ================================================================
        // DATA
        // ================================================================

        private Assessment currentAssessment;
        private RiskResult currentResult;

        private List<Recommendation> currentRecommendations = new ArrayList<>();

        // ================================================================
        // CONSTRUCTOR
        // ================================================================

        public DigitalFootprintAnalyzer() {

                setTitle("Digital Footprint Analyzer");
                setSize(1100, 720);
                setMinimumSize(new Dimension(950, 650));

                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                cardLayout = new CardLayout();
                mainPanel = new JPanel(cardLayout);

                assessmentPanel = new AssessmentPanel();
                dashboardPanel = new DashboardPanel();
                reportPanel = new ReportPanel();
                historyPanel = new HistoryPanel();
                educationPanel = new EducationPanel();
                simulatorPanel = new SimulatorPanel();

                mainPanel.add(
                                new WelcomePanel(),
                                "WELCOME");

                mainPanel.add(
                                assessmentPanel,
                                "ASSESSMENT");

                mainPanel.add(
                                dashboardPanel,
                                "DASHBOARD");

                mainPanel.add(
                                reportPanel,
                                "REPORT");

                mainPanel.add(
                                historyPanel,
                                "HISTORY");

                mainPanel.add(
                                educationPanel,
                                "EDUCATION");

                mainPanel.add(
                                simulatorPanel,
                                "SIMULATOR");

                add(mainPanel);

                setVisible(true);
        }

        // ================================================================
        // NAVIGATION
        // ================================================================

        private void showPage(String page) {

                if (page.equals("DASHBOARD") && currentResult == null) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "Please complete an assessment first.",
                                        "No Assessment",
                                        JOptionPane.INFORMATION_MESSAGE);
                        return;
                }

                if (page.equals("REPORT") && currentResult == null) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "Please complete an assessment first.",
                                        "No Assessment",
                                        JOptionPane.INFORMATION_MESSAGE);
                        return;
                }

                if (page.equals("SIMULATOR") && currentAssessment == null) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "Please complete an assessment first.",
                                        "No Assessment",
                                        JOptionPane.INFORMATION_MESSAGE);
                        return;
                }

                if (page.equals("HISTORY")) {
                        historyPanel.refresh();
                }

                if (page.equals("SIMULATOR")) {
                        simulatorPanel.loadData();
                }

                cardLayout.show(mainPanel, page);
        }

        // ================================================================
        // START ASSESSMENT
        // ================================================================

        private void startAssessment() {
                showPage("ASSESSMENT");
        }

        // ================================================================
        // COMPLETE ASSESSMENT
        // ================================================================

        private void completeAssessment(Assessment assessment) {

                currentAssessment = assessment;

                RiskAnalyzer analyzer = new RiskAnalyzer();

                currentResult = analyzer.analyze(assessment);

                RecommendationEngine engine = new RecommendationEngine();

                currentRecommendations = engine.generate(
                                assessment,
                                currentResult);

                HistoryManager.save(
                                currentResult);

                dashboardPanel.updateDashboard(
                                currentResult);

                reportPanel.updateReport(
                                currentAssessment,
                                currentResult,
                                currentRecommendations);

                showPage("DASHBOARD");
        }

        // ================================================================
        // WELCOME PANEL
        // ================================================================

        class WelcomePanel extends JPanel {

                WelcomePanel() {

                        setLayout(new BorderLayout());
                        setBackground(BG);

                        JPanel center = new JPanel();

                        center.setLayout(
                                        new BoxLayout(
                                                        center,
                                                        BoxLayout.Y_AXIS));

                        center.setBackground(BG);

                        JLabel title = new JLabel(
                                        "Digital Footprint Analyzer");

                        title.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        38));

                        title.setForeground(DARK);
                        title.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel subtitle = new JLabel(
                                        "Privacy Risk Assessment System");

                        subtitle.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.PLAIN,
                                                        21));

                        subtitle.setForeground(PRIMARY);
                        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JTextArea description = new JTextArea();

                        description.setText(
                                        "Understand how much personal information you expose online.\n\n"
                                                        + "The system evaluates your digital footprint across:\n"
                                                        + "Social Media • Account Security • Location Privacy\n"
                                                        + "Personal Information • Account Hygiene • App Privacy\n\n"
                                                        + "No passwords, OTPs or private account credentials are required.");

                        description.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.PLAIN,
                                                        16));

                        description.setForeground(TEXT);
                        description.setBackground(BG);
                        description.setEditable(false);
                        description.setAlignmentX(Component.CENTER_ALIGNMENT);
                        description.setBorder(
                                        BorderFactory.createEmptyBorder(
                                                        20, 10, 20, 10));

                        JButton start = createButton(
                                        "Start Privacy Assessment");

                        start.setAlignmentX(Component.CENTER_ALIGNMENT);

                        start.addActionListener(
                                        e -> startAssessment());

                        center.add(Box.createVerticalGlue());
                        center.add(title);
                        center.add(Box.createVerticalStrut(8));
                        center.add(subtitle);
                        center.add(Box.createVerticalStrut(15));
                        center.add(description);
                        center.add(start);
                        center.add(Box.createVerticalGlue());

                        add(center, BorderLayout.CENTER);
                }
        }

        // ================================================================
        // ASSESSMENT PANEL
        // ================================================================

        class AssessmentPanel extends JPanel {

                JSpinner publicProfiles;
                JSpinner unusedAccounts;

                JCheckBox sameUsername;
                JCheckBox unknownFollowers;
                JCheckBox identifiablePhotos;

                JCheckBox publicEmail;
                JCheckBox publicPhone;
                JCheckBox publicBirthday;
                JCheckBox publicEducation;

                JCheckBox locationSharing;
                JCheckBox liveLocation;
                JCheckBox travelPlans;

                JCheckBox passwordReuse;
                JCheckBox twoFactor;
                JCheckBox privacyReview;

                JCheckBox appPermissions;

                AssessmentPanel() {

                        setLayout(new BorderLayout());
                        setBackground(BG);

                        JPanel header = createHeader(
                                        "Privacy Assessment",
                                        "Answer the following questions honestly.");

                        add(header, BorderLayout.NORTH);

                        JPanel form = new JPanel();

                        form.setLayout(
                                        new BoxLayout(
                                                        form,
                                                        BoxLayout.Y_AXIS));

                        form.setBackground(BG);

                        form.setBorder(
                                        BorderFactory.createEmptyBorder(
                                                        10, 30, 20, 30));

                        // --------------------------------------------------------
                        // SOCIAL MEDIA
                        // --------------------------------------------------------

                        form.add(sectionTitle(
                                        "1. SOCIAL MEDIA EXPOSURE"));

                        publicProfiles = new JSpinner(
                                        new SpinnerNumberModel(
                                                        2,
                                                        0,
                                                        20,
                                                        1));

                        form.add(
                                        createRow(
                                                        "Number of public social media profiles:",
                                                        publicProfiles));

                        sameUsername = createCheckBox(
                                        "I use the same username on multiple platforms.");

                        unknownFollowers = createCheckBox(
                                        "I accept followers/friend requests from unknown people.");

                        identifiablePhotos = createCheckBox(
                                        "My photos may reveal identifiable personal information.");

                        form.add(sameUsername);
                        form.add(unknownFollowers);
                        form.add(identifiablePhotos);

                        // --------------------------------------------------------
                        // PERSONAL INFORMATION
                        // --------------------------------------------------------

                        form.add(sectionTitle(
                                        "2. PERSONAL INFORMATION EXPOSURE"));

                        publicEmail = createCheckBox(
                                        "My email address is publicly visible.");

                        publicPhone = createCheckBox(
                                        "My phone number is publicly visible.");

                        publicBirthday = createCheckBox(
                                        "My complete birthday/date of birth is publicly visible.");

                        publicEducation = createCheckBox(
                                        "My school/college information is publicly visible.");

                        form.add(publicEmail);
                        form.add(publicPhone);
                        form.add(publicBirthday);
                        form.add(publicEducation);

                        // --------------------------------------------------------
                        // LOCATION
                        // --------------------------------------------------------

                        form.add(sectionTitle(
                                        "3. LOCATION PRIVACY"));

                        locationSharing = createCheckBox(
                                        "My location information is publicly visible.");

                        liveLocation = createCheckBox(
                                        "I sometimes share my live/current location.");

                        travelPlans = createCheckBox(
                                        "I publicly share travel plans before/during travel.");

                        form.add(locationSharing);
                        form.add(liveLocation);
                        form.add(travelPlans);

                        // --------------------------------------------------------
                        // SECURITY
                        // --------------------------------------------------------

                        form.add(sectionTitle(
                                        "4. ACCOUNT SECURITY"));

                        passwordReuse = createCheckBox(
                                        "I reuse passwords across multiple accounts.");

                        twoFactor = createCheckBox(
                                        "I use two-factor authentication (2FA).");

                        privacyReview = createCheckBox(
                                        "I regularly review privacy/security settings.");

                        form.add(passwordReuse);
                        form.add(twoFactor);
                        form.add(privacyReview);

                        // --------------------------------------------------------
                        // ACCOUNT HYGIENE
                        // --------------------------------------------------------

                        form.add(sectionTitle(
                                        "5. ACCOUNT HYGIENE"));

                        unusedAccounts = new JSpinner(
                                        new SpinnerNumberModel(
                                                        1,
                                                        0,
                                                        20,
                                                        1));

                        form.add(
                                        createRow(
                                                        "Approximate number of unused accounts:",
                                                        unusedAccounts));

                        // --------------------------------------------------------
                        // APP PRIVACY
                        // --------------------------------------------------------

                        form.add(sectionTitle(
                                        "6. APP & DEVICE PRIVACY"));

                        appPermissions = createCheckBox(
                                        "I regularly review application permissions.");

                        form.add(appPermissions);

                        JScrollPane scroll = new JScrollPane(form);

                        scroll.setBorder(null);

                        add(
                                        scroll,
                                        BorderLayout.CENTER);

                        JButton analyze = createButton(
                                        "Analyze My Digital Footprint");

                        analyze.addActionListener(
                                        e -> analyzeAssessment());

                        JButton reset = createSecondaryButton(
                                        "Reset");

                        reset.addActionListener(
                                        e -> resetForm());

                        JPanel bottom = new JPanel(
                                        new FlowLayout(
                                                        FlowLayout.CENTER));

                        bottom.setBackground(BG);

                        bottom.add(reset);
                        bottom.add(analyze);

                        add(
                                        bottom,
                                        BorderLayout.SOUTH);
                }

                private void analyzeAssessment() {

                        Assessment a = new Assessment();

                        a.publicProfiles = (Integer) publicProfiles.getValue();

                        a.sameUsername = sameUsername.isSelected();

                        a.unknownFollowers = unknownFollowers.isSelected();

                        a.identifiablePhotos = identifiablePhotos.isSelected();

                        a.publicEmail = publicEmail.isSelected();

                        a.publicPhone = publicPhone.isSelected();

                        a.publicBirthday = publicBirthday.isSelected();

                        a.publicEducation = publicEducation.isSelected();

                        a.locationSharing = locationSharing.isSelected();

                        a.liveLocation = liveLocation.isSelected();

                        a.travelPlans = travelPlans.isSelected();

                        a.passwordReuse = passwordReuse.isSelected();

                        a.twoFactor = twoFactor.isSelected();

                        a.privacyReview = privacyReview.isSelected();

                        a.unusedAccounts = (Integer) unusedAccounts.getValue();

                        a.appPermissions = appPermissions.isSelected();

                        completeAssessment(a);
                }

                private void resetForm() {

                        publicProfiles.setValue(2);
                        unusedAccounts.setValue(1);

                        sameUsername.setSelected(false);
                        unknownFollowers.setSelected(false);
                        identifiablePhotos.setSelected(false);

                        publicEmail.setSelected(false);
                        publicPhone.setSelected(false);
                        publicBirthday.setSelected(false);
                        publicEducation.setSelected(false);

                        locationSharing.setSelected(false);
                        liveLocation.setSelected(false);
                        travelPlans.setSelected(false);

                        passwordReuse.setSelected(false);
                        twoFactor.setSelected(false);
                        privacyReview.setSelected(false);

                        appPermissions.setSelected(false);
                }
        }

        // ================================================================
        // DASHBOARD PANEL
        // ================================================================

        class DashboardPanel extends JPanel {

                JLabel riskScoreLabel;
                JLabel privacyScoreLabel;
                JLabel levelLabel;

                JPanel barsPanel;

                DashboardPanel() {

                        setLayout(new BorderLayout());
                        setBackground(BG);

                        add(
                                        createHeader(
                                                        "Privacy Risk Dashboard",
                                                        "Overview of your current digital footprint."),
                                        BorderLayout.NORTH);

                        JPanel content = new JPanel(
                                        new BorderLayout(
                                                        20,
                                                        20));

                        content.setBackground(BG);

                        content.setBorder(
                                        BorderFactory.createEmptyBorder(
                                                        20, 30, 20, 30));

                        // Score card

                        JPanel scoreCard = createCard();

                        scoreCard.setPreferredSize(
                                        new Dimension(
                                                        300,
                                                        350));

                        scoreCard.setLayout(
                                        new BoxLayout(
                                                        scoreCard,
                                                        BoxLayout.Y_AXIS));

                        JLabel scoreTitle = new JLabel(
                                        "OVERALL RISK");

                        scoreTitle.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        15));

                        scoreTitle.setAlignmentX(
                                        Component.CENTER_ALIGNMENT);

                        riskScoreLabel = new JLabel("0");

                        riskScoreLabel.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        65));

                        riskScoreLabel.setForeground(PRIMARY);

                        riskScoreLabel.setAlignmentX(
                                        Component.CENTER_ALIGNMENT);

                        JLabel outOf = new JLabel(
                                        "out of 100");

                        outOf.setForeground(
                                        LIGHT_TEXT);

                        outOf.setAlignmentX(
                                        Component.CENTER_ALIGNMENT);

                        privacyScoreLabel = new JLabel(
                                        "Privacy Score: 0/100");

                        privacyScoreLabel.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        18));

                        privacyScoreLabel.setAlignmentX(
                                        Component.CENTER_ALIGNMENT);

                        levelLabel = new JLabel(
                                        "Risk Level: -");

                        levelLabel.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        20));

                        levelLabel.setAlignmentX(
                                        Component.CENTER_ALIGNMENT);

                        scoreCard.add(Box.createVerticalGlue());
                        scoreCard.add(scoreTitle);
                        scoreCard.add(Box.createVerticalStrut(15));
                        scoreCard.add(riskScoreLabel);
                        scoreCard.add(outOf);
                        scoreCard.add(Box.createVerticalStrut(20));
                        scoreCard.add(privacyScoreLabel);
                        scoreCard.add(Box.createVerticalStrut(10));
                        scoreCard.add(levelLabel);
                        scoreCard.add(Box.createVerticalGlue());

                        content.add(
                                        scoreCard,
                                        BorderLayout.WEST);

                        // Category panel

                        JPanel categoryCard = createCard();

                        categoryCard.setLayout(
                                        new BorderLayout());

                        JLabel categoryTitle = new JLabel(
                                        "CATEGORY-WISE RISK");

                        categoryTitle.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        16));

                        categoryTitle.setBorder(
                                        BorderFactory.createEmptyBorder(
                                                        15, 20, 10, 20));

                        categoryCard.add(
                                        categoryTitle,
                                        BorderLayout.NORTH);

                        barsPanel = new JPanel();

                        barsPanel.setLayout(
                                        new BoxLayout(
                                                        barsPanel,
                                                        BoxLayout.Y_AXIS));

                        barsPanel.setBackground(Color.WHITE);

                        barsPanel.setBorder(
                                        BorderFactory.createEmptyBorder(
                                                        10, 20, 20, 20));

                        categoryCard.add(
                                        barsPanel,
                                        BorderLayout.CENTER);

                        content.add(
                                        categoryCard,
                                        BorderLayout.CENTER);

                        add(
                                        content,
                                        BorderLayout.CENTER);

                        JPanel bottom = new JPanel(
                                        new FlowLayout(
                                                        FlowLayout.CENTER,
                                                        10,
                                                        10));

                        bottom.setBackground(BG);

                        JButton report = createButton(
                                        "Detailed Report");

                        report.addActionListener(
                                        e -> showPage("REPORT"));

                        JButton simulator = createSecondaryButton(
                                        "Risk Simulator");

                        simulator.addActionListener(
                                        e -> showPage("SIMULATOR"));

                        JButton newAssessment = createSecondaryButton(
                                        "New Assessment");

                        newAssessment.addActionListener(
                                        e -> showPage("ASSESSMENT"));

                        bottom.add(newAssessment);
                        bottom.add(simulator);
                        bottom.add(report);

                        add(
                                        bottom,
                                        BorderLayout.SOUTH);
                }

                void updateDashboard(RiskResult result) {

                        riskScoreLabel.setText(
                                        String.valueOf(
                                                        result.riskScore));

                        privacyScoreLabel.setText(
                                        "Privacy Score: "
                                                        + result.privacyScore
                                                        + "/100");

                        levelLabel.setText(
                                        "Risk Level: "
                                                        + result.riskLevel);

                        barsPanel.removeAll();

                        addRiskBar(
                                        "Social Media Exposure",
                                        result.socialRisk);

                        addRiskBar(
                                        "Account Security",
                                        result.securityRisk);

                        addRiskBar(
                                        "Location Privacy",
                                        result.locationRisk);

                        addRiskBar(
                                        "Personal Information",
                                        result.personalRisk);

                        addRiskBar(
                                        "Account Hygiene",
                                        result.accountRisk);

                        addRiskBar(
                                        "App Privacy",
                                        result.appRisk);

                        barsPanel.revalidate();
                        barsPanel.repaint();
                }

                private void addRiskBar(
                                String name,
                                int value) {

                        JPanel row = new JPanel(
                                        new BorderLayout(
                                                        10,
                                                        5));

                        row.setBackground(Color.WHITE);

                        JLabel label = new JLabel(
                                        name);

                        label.setPreferredSize(
                                        new Dimension(
                                                        180,
                                                        30));

                        JProgressBar bar = new JProgressBar(
                                        0,
                                        100);

                        bar.setValue(value);
                        bar.setString(
                                        value + "/100");
                        bar.setStringPainted(true);

                        row.add(
                                        label,
                                        BorderLayout.WEST);

                        row.add(
                                        bar,
                                        BorderLayout.CENTER);

                        row.setMaximumSize(
                                        new Dimension(
                                                        Integer.MAX_VALUE,
                                                        45));

                        barsPanel.add(row);
                        barsPanel.add(
                                        Box.createVerticalStrut(8));
                }
        }

        // ================================================================
        // REPORT PANEL
        // ================================================================

        class ReportPanel extends JPanel {

                JTextArea area;

                ReportPanel() {

                        setLayout(new BorderLayout());
                        setBackground(BG);

                        add(
                                        createHeader(
                                                        "Detailed Privacy Report",
                                                        "Identified risks and personalized recommendations."),
                                        BorderLayout.NORTH);

                        area = new JTextArea();

                        area.setFont(
                                        new Font(
                                                        "Monospaced",
                                                        Font.PLAIN,
                                                        14));

                        area.setEditable(false);
                        area.setLineWrap(true);
                        area.setWrapStyleWord(true);

                        JScrollPane scroll = new JScrollPane(area);

                        scroll.setBorder(
                                        BorderFactory.createEmptyBorder(
                                                        15, 25, 15, 25));

                        add(
                                        scroll,
                                        BorderLayout.CENTER);

                        JButton export = createButton(
                                        "Export Report");

                        export.addActionListener(
                                        e -> exportReport());

                        JButton back = createSecondaryButton(
                                        "Back to Dashboard");

                        back.addActionListener(
                                        e -> showPage("DASHBOARD"));

                        JPanel bottom = new JPanel();

                        bottom.setBackground(BG);

                        bottom.add(back);
                        bottom.add(export);

                        add(
                                        bottom,
                                        BorderLayout.SOUTH);
                }

                void updateReport(
                                Assessment assessment,
                                RiskResult result,
                                List<Recommendation> recommendations) {

                        StringBuilder sb = new StringBuilder();

                        sb.append(
                                        "============================================================\n");

                        sb.append(
                                        "             DIGITAL FOOTPRINT ANALYSIS REPORT\n");

                        sb.append(
                                        "============================================================\n\n");

                        sb.append(
                                        "Date: "
                                                        + new SimpleDateFormat(
                                                                        "dd-MM-yyyy HH:mm").format(new Date())
                                                        + "\n\n");

                        sb.append(
                                        "OVERALL RESULT\n");

                        sb.append(
                                        "------------------------------------------------------------\n");

                        sb.append(
                                        "Risk Score     : "
                                                        + result.riskScore
                                                        + "/100\n");

                        sb.append(
                                        "Privacy Score  : "
                                                        + result.privacyScore
                                                        + "/100\n");

                        sb.append(
                                        "Risk Level     : "
                                                        + result.riskLevel
                                                        + "\n\n");

                        sb.append(
                                        "CATEGORY ANALYSIS\n");

                        sb.append(
                                        "------------------------------------------------------------\n");

                        sb.append(
                                        String.format(
                                                        "Social Media Exposure    : %3d/100%n",
                                                        result.socialRisk));

                        sb.append(
                                        String.format(
                                                        "Account Security         : %3d/100%n",
                                                        result.securityRisk));

                        sb.append(
                                        String.format(
                                                        "Location Privacy         : %3d/100%n",
                                                        result.locationRisk));

                        sb.append(
                                        String.format(
                                                        "Personal Information     : %3d/100%n",
                                                        result.personalRisk));

                        sb.append(
                                        String.format(
                                                        "Account Hygiene          : %3d/100%n",
                                                        result.accountRisk));

                        sb.append(
                                        String.format(
                                                        "Application Privacy     : %3d/100%n%n",
                                                        result.appRisk));

                        sb.append(
                                        "IDENTIFIED RISKS\n");

                        sb.append(
                                        "------------------------------------------------------------\n");

                        if (result.risks.isEmpty()) {

                                sb.append(
                                                "No major risks were identified.\n");

                        } else {

                                int number = 1;

                                for (String risk : result.risks) {

                                        sb.append(
                                                        number++
                                                                        + ". "
                                                                        + risk
                                                                        + "\n");
                                }
                        }

                        sb.append(
                                        "\nPERSONALIZED RECOMMENDATIONS\n");

                        sb.append(
                                        "------------------------------------------------------------\n");

                        if (recommendations.isEmpty()) {

                                sb.append(
                                                "Your current privacy practices are strong.\n");

                        } else {

                                int number = 1;

                                for (Recommendation r : recommendations) {

                                        sb.append(
                                                        "\n"
                                                                        + number++
                                                                        + ". "
                                                                        + r.title
                                                                        + "\n");

                                        sb.append(
                                                        "Priority: "
                                                                        + r.priority
                                                                        + "\n");

                                        sb.append(
                                                        r.description
                                                                        + "\n");
                                }
                        }

                        sb.append(
                                        "\n============================================================\n");

                        sb.append(
                                        "PRIVACY NOTICE\n");

                        sb.append(
                                        "============================================================\n");

                        sb.append(
                                        "This application uses self-reported information.\n");

                        sb.append(
                                        "It does not request passwords, OTPs or account credentials.\n");

                        sb.append(
                                        "No social-media scraping is performed.\n");

                        area.setText(
                                        sb.toString());

                        area.setCaretPosition(0);
                }

                private void exportReport() {

                        if (currentResult == null) {
                                return;
                        }

                        JFileChooser chooser = new JFileChooser();

                        chooser.setSelectedFile(
                                        new File(
                                                        "Digital_Footprint_Report.txt"));

                        int choice = chooser.showSaveDialog(
                                        DigitalFootprintAnalyzer.this);

                        if (choice != JFileChooser.APPROVE_OPTION) {
                                return;
                        }

                        try {

                                File file = chooser.getSelectedFile();

                                FileWriter writer = new FileWriter(file);

                                writer.write(
                                                area.getText());

                                writer.close();

                                JOptionPane.showMessageDialog(
                                                DigitalFootprintAnalyzer.this,
                                                "Report exported successfully.",
                                                "Export Complete",
                                                JOptionPane.INFORMATION_MESSAGE);

                        } catch (IOException ex) {

                                JOptionPane.showMessageDialog(
                                                DigitalFootprintAnalyzer.this,
                                                "Unable to export report.",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                }
        }

        // ================================================================
        // HISTORY PANEL
        // ================================================================

        class HistoryPanel extends JPanel {

                JTextArea historyArea;

                HistoryPanel() {

                        setLayout(new BorderLayout());
                        setBackground(BG);

                        add(
                                        createHeader(
                                                        "Assessment History",
                                                        "Previous privacy assessments stored locally."),
                                        BorderLayout.NORTH);

                        historyArea = new JTextArea();

                        historyArea.setFont(
                                        new Font(
                                                        "Monospaced",
                                                        Font.PLAIN,
                                                        14));

                        historyArea.setEditable(false);

                        add(
                                        new JScrollPane(
                                                        historyArea),
                                        BorderLayout.CENTER);

                        JButton back = createSecondaryButton(
                                        "Back");

                        back.addActionListener(
                                        e -> showPage(
                                                        currentResult == null
                                                                        ? "WELCOME"
                                                                        : "DASHBOARD"));

                        JPanel bottom = new JPanel();

                        bottom.setBackground(BG);

                        bottom.add(back);

                        add(
                                        bottom,
                                        BorderLayout.SOUTH);
                }

                void refresh() {

                        historyArea.setText(
                                        HistoryManager.readHistory());

                        historyArea.setCaretPosition(0);
                }
        }

        // ================================================================
        // EDUCATION PANEL
        // ================================================================

        class EducationPanel extends JPanel {

                EducationPanel() {

                        setLayout(new BorderLayout());
                        setBackground(BG);

                        add(
                                        createHeader(
                                                        "Privacy Education",
                                                        "Simple practices for reducing your digital footprint."),
                                        BorderLayout.NORTH);

                        JTextArea tips = new JTextArea();

                        tips.setEditable(false);
                        tips.setLineWrap(true);
                        tips.setWrapStyleWord(true);

                        tips.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.PLAIN,
                                                        15));

                        tips.setText(
                                        "DIGITAL PRIVACY BASICS\n\n"

                                                        + "1. USE TWO-FACTOR AUTHENTICATION\n"
                                                        + "Enable 2FA on important accounts such as email and social media.\n\n"

                                                        + "2. USE UNIQUE PASSWORDS\n"
                                                        + "Avoid using the same password for multiple accounts.\n\n"

                                                        + "3. LIMIT PUBLIC PERSONAL INFORMATION\n"
                                                        + "Avoid publicly displaying phone numbers, complete birthdays and sensitive information.\n\n"

                                                        + "4. REVIEW LOCATION SETTINGS\n"
                                                        + "Check which applications can access your location.\n\n"

                                                        + "5. BE CAREFUL WITH LIVE LOCATION\n"
                                                        + "Sharing your current location can reveal information about your movements.\n\n"

                                                        + "6. REMOVE UNUSED ACCOUNTS\n"
                                                        + "Old accounts may contain personal information that you no longer need online.\n\n"

                                                        + "7. REVIEW APPLICATION PERMISSIONS\n"
                                                        + "Check whether apps really need access to your camera, microphone, contacts or location.\n\n"

                                                        + "8. BE CAREFUL WITH UNKNOWN FOLLOWERS\n"
                                                        + "Not every online connection is trustworthy.\n\n"

                                                        + "9. AVOID REAL-TIME OVERSHARING\n"
                                                        + "Think before publicly sharing travel plans or daily routines.\n\n"

                                                        + "10. REGULARLY CHECK PRIVACY SETTINGS\n"
                                                        + "Privacy settings can change when platforms introduce new features.\n\n"

                                                        + "REMEMBER\n"
                                                        + "Your digital footprint is the collection of information that exists about your online activity.\n"
                                                        + "Reducing unnecessary exposure can improve your privacy and security.");

                        tips.setBorder(
                                        BorderFactory.createEmptyBorder(
                                                        25, 35, 25, 35));

                        add(
                                        new JScrollPane(tips),
                                        BorderLayout.CENTER);

                        JButton back = createSecondaryButton(
                                        "Back");

                        back.addActionListener(
                                        e -> showPage(
                                                        currentResult == null
                                                                        ? "WELCOME"
                                                                        : "DASHBOARD"));

                        JPanel bottom = new JPanel();

                        bottom.setBackground(BG);

                        bottom.add(back);

                        add(
                                        bottom,
                                        BorderLayout.SOUTH);
                }
        }

        // ================================================================
        // SIMULATOR PANEL
        // ================================================================

        class SimulatorPanel extends JPanel {

                JCheckBox enable2FA;
                JCheckBox stopLocation;
                JCheckBox hidePhone;
                JCheckBox stopPasswordReuse;

                JLabel simulatedScore;
                JLabel simulatedLevel;

                SimulatorPanel() {

                        setLayout(new BorderLayout());
                        setBackground(BG);

                        add(
                                        createHeader(
                                                        "Privacy Risk Simulator",
                                                        "See how privacy improvements could affect your score."),
                                        BorderLayout.NORTH);

                        JPanel center = new JPanel();

                        center.setLayout(
                                        new BoxLayout(
                                                        center,
                                                        BoxLayout.Y_AXIS));

                        center.setBackground(BG);

                        center.setBorder(
                                        BorderFactory.createEmptyBorder(
                                                        30, 80, 30, 80));

                        JLabel heading = new JLabel(
                                        "Select improvements:");

                        heading.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        18));

                        enable2FA = createCheckBox(
                                        "Enable Two-Factor Authentication");

                        stopLocation = createCheckBox(
                                        "Stop unnecessary location sharing");

                        hidePhone = createCheckBox(
                                        "Hide public phone number");

                        stopPasswordReuse = createCheckBox(
                                        "Stop reusing passwords");

                        simulatedScore = new JLabel(
                                        "Simulated Risk Score: -");

                        simulatedScore.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        25));

                        simulatedLevel = new JLabel(
                                        "Simulated Risk Level: -");

                        simulatedLevel.setFont(
                                        new Font(
                                                        "SansSerif",
                                                        Font.BOLD,
                                                        18));

                        JButton calculate = createButton(
                                        "Calculate Improved Score");

                        calculate.addActionListener(
                                        e -> simulate());

                        center.add(heading);
                        center.add(Box.createVerticalStrut(15));
                        center.add(enable2FA);
                        center.add(stopLocation);
                        center.add(hidePhone);
                        center.add(stopPasswordReuse);
                        center.add(Box.createVerticalStrut(25));
                        center.add(simulatedScore);
                        center.add(Box.createVerticalStrut(10));
                        center.add(simulatedLevel);
                        center.add(Box.createVerticalStrut(25));
                        center.add(calculate);

                        add(
                                        center,
                                        BorderLayout.CENTER);

                        JButton back = createSecondaryButton(
                                        "Back to Dashboard");

                        back.addActionListener(
                                        e -> showPage("DASHBOARD"));

                        JPanel bottom = new JPanel();

                        bottom.setBackground(BG);
                        bottom.add(back);

                        add(
                                        bottom,
                                        BorderLayout.SOUTH);
                }

                void loadData() {

                        if (currentResult == null) {
                                return;
                        }

                        enable2FA.setSelected(false);
                        stopLocation.setSelected(false);
                        hidePhone.setSelected(false);
                        stopPasswordReuse.setSelected(false);

                        simulatedScore.setText(
                                        "Current Risk Score: "
                                                        + currentResult.riskScore
                                                        + "/100");

                        simulatedLevel.setText(
                                        "Current Risk Level: "
                                                        + currentResult.riskLevel);
                }

                private void simulate() {

                        if (currentAssessment == null) {
                                return;
                        }

                        Assessment copy = currentAssessment.copy();

                        if (enable2FA.isSelected()) {
                                copy.twoFactor = true;
                        }

                        if (stopLocation.isSelected()) {
                                copy.locationSharing = false;
                                copy.liveLocation = false;
                        }

                        if (hidePhone.isSelected()) {
                                copy.publicPhone = false;
                        }

                        if (stopPasswordReuse.isSelected()) {
                                copy.passwordReuse = false;
                        }

                        RiskResult result = new RiskAnalyzer().analyze(copy);

                        simulatedScore.setText(
                                        "Simulated Risk Score: "
                                                        + result.riskScore
                                                        + "/100");

                        simulatedLevel.setText(
                                        "Simulated Risk Level: "
                                                        + result.riskLevel
                                                        + "   |   Privacy Score: "
                                                        + result.privacyScore
                                                        + "/100");
                }
        }

        // ================================================================
        // ASSESSMENT MODEL
        // ================================================================

        static class Assessment {

                int publicProfiles;
                int unusedAccounts;

                boolean sameUsername;
                boolean unknownFollowers;
                boolean identifiablePhotos;

                boolean publicEmail;
                boolean publicPhone;
                boolean publicBirthday;
                boolean publicEducation;

                boolean locationSharing;
                boolean liveLocation;
                boolean travelPlans;

                boolean passwordReuse;
                boolean twoFactor;
                boolean privacyReview;

                boolean appPermissions;

                Assessment copy() {

                        Assessment a = new Assessment();

                        a.publicProfiles = publicProfiles;

                        a.unusedAccounts = unusedAccounts;

                        a.sameUsername = sameUsername;

                        a.unknownFollowers = unknownFollowers;

                        a.identifiablePhotos = identifiablePhotos;

                        a.publicEmail = publicEmail;

                        a.publicPhone = publicPhone;

                        a.publicBirthday = publicBirthday;

                        a.publicEducation = publicEducation;

                        a.locationSharing = locationSharing;

                        a.liveLocation = liveLocation;

                        a.travelPlans = travelPlans;

                        a.passwordReuse = passwordReuse;

                        a.twoFactor = twoFactor;

                        a.privacyReview = privacyReview;

                        a.appPermissions = appPermissions;

                        return a;
                }
        }

        // ================================================================
        // RISK RESULT MODEL
        // ================================================================

        static class RiskResult {

                int riskScore;
                int privacyScore;

                String riskLevel;

                int socialRisk;
                int securityRisk;
                int locationRisk;
                int personalRisk;
                int accountRisk;
                int appRisk;

                List<String> risks = new ArrayList<>();
        }

        // ================================================================
        // RECOMMENDATION MODEL
        // ================================================================

        static class Recommendation {

                String category;
                String title;
                String description;
                String priority;

                Recommendation(
                                String category,
                                String title,
                                String description,
                                String priority) {

                        this.category = category;
                        this.title = title;
                        this.description = description;
                        this.priority = priority;
                }
        }

        // ================================================================
        // RISK ANALYZER
        // ================================================================

        static class RiskAnalyzer {

                RiskResult analyze(
                                Assessment a) {

                        RiskResult result = new RiskResult();

                        int social = 0;
                        int security = 0;
                        int location = 0;
                        int personal = 0;
                        int account = 0;
                        int app = 0;

                        // --------------------------------------------------------
                        // SOCIAL
                        // --------------------------------------------------------

                        if (a.publicProfiles >= 5) {

                                social += 25;

                                result.risks.add(
                                                "Large number of public social media profiles.");

                        } else if (a.publicProfiles >= 3) {

                                social += 15;

                                result.risks.add(
                                                "Multiple public social media profiles.");
                        }

                        if (a.sameUsername) {

                                social += 15;

                                result.risks.add(
                                                "Same username is reused across platforms.");
                        }

                        if (a.unknownFollowers) {

                                social += 15;

                                result.risks.add(
                                                "Unknown people can access your social profile.");
                        }

                        if (a.identifiablePhotos) {

                                social += 10;

                                result.risks.add(
                                                "Photos may reveal identifiable personal information.");
                        }

                        // --------------------------------------------------------
                        // SECURITY
                        // --------------------------------------------------------

                        if (!a.twoFactor) {

                                security += 30;

                                result.risks.add(
                                                "Two-factor authentication is not enabled.");
                        }

                        if (a.passwordReuse) {

                                security += 30;

                                result.risks.add(
                                                "Passwords are reused across multiple accounts.");
                        }

                        if (!a.privacyReview) {

                                security += 10;

                                result.risks.add(
                                                "Privacy/security settings are not regularly reviewed.");
                        }

                        // --------------------------------------------------------
                        // LOCATION
                        // --------------------------------------------------------

                        if (a.locationSharing) {

                                location += 25;

                                result.risks.add(
                                                "Location information is publicly visible.");
                        }

                        if (a.liveLocation) {

                                location += 35;

                                result.risks.add(
                                                "Live location sharing can reveal current movements.");
                        }

                        if (a.travelPlans) {

                                location += 20;

                                result.risks.add(
                                                "Travel plans are publicly shared.");
                        }

                        // --------------------------------------------------------
                        // PERSONAL INFORMATION
                        // --------------------------------------------------------

                        if (a.publicEmail) {

                                personal += 15;

                                result.risks.add(
                                                "Email address is publicly visible.");
                        }

                        if (a.publicPhone) {

                                personal += 20;

                                result.risks.add(
                                                "Phone number is publicly visible.");
                        }

                        if (a.publicBirthday) {

                                personal += 15;

                                result.risks.add(
                                                "Complete birthday is publicly visible.");
                        }

                        if (a.publicEducation) {

                                personal += 10;

                                result.risks.add(
                                                "Educational information is publicly visible.");
                        }

                        // --------------------------------------------------------
                        // ACCOUNT HYGIENE
                        // --------------------------------------------------------

                        if (a.unusedAccounts >= 5) {

                                account += 30;

                                result.risks.add(
                                                "Many unused online accounts exist.");

                        } else if (a.unusedAccounts >= 2) {

                                account += 20;

                                result.risks.add(
                                                "Several unused online accounts exist.");

                        } else if (a.unusedAccounts == 1) {

                                account += 5;
                        }

                        // --------------------------------------------------------
                        // APPLICATION PRIVACY
                        // --------------------------------------------------------

                        if (!a.appPermissions) {

                                app += 25;

                                result.risks.add(
                                                "Application permissions have not been reviewed.");
                        }

                        // --------------------------------------------------------
                        // LIMIT CATEGORY SCORES
                        // --------------------------------------------------------

                        social = Math.min(
                                        social,
                                        100);

                        security = Math.min(
                                        security,
                                        100);

                        location = Math.min(
                                        location,
                                        100);

                        personal = Math.min(
                                        personal,
                                        100);

                        account = Math.min(
                                        account,
                                        100);

                        app = Math.min(
                                        app,
                                        100);

                        // --------------------------------------------------------
                        // WEIGHTED SCORE
                        // --------------------------------------------------------

                        double weightedRisk = social * 0.20
                                        + security * 0.25
                                        + location * 0.20
                                        + personal * 0.15
                                        + account * 0.10
                                        + app * 0.10;

                        int finalRisk = (int) Math.round(
                                        weightedRisk);

                        finalRisk = Math.max(
                                        0,
                                        Math.min(
                                                        100,
                                                        finalRisk));

                        int privacyScore = 100 - finalRisk;

                        String level;

                        if (finalRisk <= 20) {

                                level = "LOW";

                        } else if (finalRisk <= 40) {

                                level = "MODERATE";

                        } else if (finalRisk <= 60) {

                                level = "MEDIUM";

                        } else if (finalRisk <= 80) {

                                level = "HIGH";

                        } else {

                                level = "CRITICAL";
                        }

                        result.riskScore = finalRisk;

                        result.privacyScore = privacyScore;

                        result.riskLevel = level;

                        result.socialRisk = social;

                        result.securityRisk = security;

                        result.locationRisk = location;

                        result.personalRisk = personal;

                        result.accountRisk = account;

                        result.appRisk = app;

                        return result;
                }
        }

        // ================================================================
        // RECOMMENDATION ENGINE
        // ================================================================

        static class RecommendationEngine {

                List<Recommendation> generate(
                                Assessment a,
                                RiskResult result) {

                        List<Recommendation> list = new ArrayList<>();

                        if (!a.twoFactor) {

                                list.add(
                                                new Recommendation(
                                                                "Account Security",
                                                                "Enable Two-Factor Authentication",
                                                                "Enable 2FA on important accounts such as email and social media.",
                                                                "HIGH"));
                        }

                        if (a.passwordReuse) {

                                list.add(
                                                new Recommendation(
                                                                "Account Security",
                                                                "Stop Password Reuse",
                                                                "Use unique strong passwords for important accounts.",
                                                                "HIGH"));
                        }

                        if (a.sameUsername) {

                                list.add(
                                                new Recommendation(
                                                                "Social Media",
                                                                "Reduce Username Reuse",
                                                                "Consider using different usernames on different platforms.",
                                                                "MEDIUM"));
                        }

                        if (a.publicProfiles >= 3) {

                                list.add(
                                                new Recommendation(
                                                                "Social Media",
                                                                "Reduce Public Profiles",
                                                                "Review old public profiles and make unnecessary information private.",
                                                                "MEDIUM"));
                        }

                        if (a.unknownFollowers) {

                                list.add(
                                                new Recommendation(
                                                                "Social Media",
                                                                "Review Unknown Connections",
                                                                "Remove unknown followers and avoid accepting unfamiliar requests.",
                                                                "MEDIUM"));
                        }

                        if (a.identifiablePhotos) {

                                list.add(
                                                new Recommendation(
                                                                "Social Media",
                                                                "Review Public Photos",
                                                                "Check whether public photos reveal locations, documents or other personal details.",
                                                                "MEDIUM"));
                        }

                        if (a.locationSharing) {

                                list.add(
                                                new Recommendation(
                                                                "Location",
                                                                "Limit Location Visibility",
                                                                "Avoid making your current or regular location publicly visible.",
                                                                "HIGH"));
                        }

                        if (a.liveLocation) {

                                list.add(
                                                new Recommendation(
                                                                "Location",
                                                                "Limit Live Location",
                                                                "Use live-location sharing only when necessary and with trusted people.",
                                                                "HIGH"));
                        }

                        if (a.travelPlans) {

                                list.add(
                                                new Recommendation(
                                                                "Location",
                                                                "Avoid Real-Time Travel Updates",
                                                                "Consider sharing travel information after the trip instead of in real time.",
                                                                "MEDIUM"));
                        }

                        if (a.publicPhone) {

                                list.add(
                                                new Recommendation(
                                                                "Personal Information",
                                                                "Hide Phone Number",
                                                                "Avoid displaying your personal phone number on public profiles.",
                                                                "HIGH"));
                        }

                        if (a.publicEmail) {

                                list.add(
                                                new Recommendation(
                                                                "Personal Information",
                                                                "Protect Your Email",
                                                                "Use a separate public contact email where appropriate.",
                                                                "MEDIUM"));
                        }

                        if (a.publicBirthday) {

                                list.add(
                                                new Recommendation(
                                                                "Personal Information",
                                                                "Limit Birthday Visibility",
                                                                "Avoid exposing your complete date of birth publicly.",
                                                                "MEDIUM"));
                        }

                        if (a.publicEducation) {

                                list.add(
                                                new Recommendation(
                                                                "Personal Information",
                                                                "Review Education Details",
                                                                "Only expose educational information that is useful for your intended audience.",
                                                                "LOW"));
                        }

                        if (a.unusedAccounts >= 2) {

                                list.add(
                                                new Recommendation(
                                                                "Account Hygiene",
                                                                "Delete Unused Accounts",
                                                                "Close old accounts that you no longer use.",
                                                                "MEDIUM"));
                        }

                        if (!a.appPermissions) {

                                list.add(
                                                new Recommendation(
                                                                "App Privacy",
                                                                "Review App Permissions",
                                                                "Check camera, microphone, contacts and location permissions regularly.",
                                                                "HIGH"));
                        }

                        if (!a.privacyReview) {

                                list.add(
                                                new Recommendation(
                                                                "Privacy",
                                                                "Review Privacy Settings",
                                                                "Perform a privacy and security check every few months.",
                                                                "MEDIUM"));
                        }

                        if (list.isEmpty()) {

                                list.add(
                                                new Recommendation(
                                                                "General",
                                                                "Maintain Good Privacy Practices",
                                                                "Your current answers indicate relatively strong privacy practices. Continue reviewing them regularly.",
                                                                "LOW"));
                        }

                        return list;
                }
        }

        // ================================================================
        // HISTORY MANAGER
        // ================================================================

        static class HistoryManager {

                private static final String FILE = "digital_footprint_history.csv";

                static void save(
                                RiskResult result) {

                        try {

                                File file = new File(FILE);

                                boolean newFile = !file.exists();

                                FileWriter writer = new FileWriter(
                                                file,
                                                true);

                                if (newFile) {

                                        writer.write(
                                                        "Date,Risk Score,Privacy Score,Risk Level\n");
                                }

                                String date = new SimpleDateFormat(
                                                "dd-MM-yyyy HH:mm").format(
                                                                new Date());

                                writer.write(
                                                date
                                                                + ","
                                                                + result.riskScore
                                                                + ","
                                                                + result.privacyScore
                                                                + ","
                                                                + result.riskLevel
                                                                + "\n");

                                writer.close();

                        } catch (IOException ignored) {
                        }
                }

                static String readHistory() {

                        File file = new File(FILE);

                        if (!file.exists()) {

                                return "No previous assessments found.\n";
                        }

                        StringBuilder sb = new StringBuilder();

                        sb.append(
                                        "ASSESSMENT HISTORY\n");

                        sb.append(
                                        "====================================================\n\n");

                        try {

                                BufferedReader reader = new BufferedReader(
                                                new FileReader(file));

                                String line;

                                boolean first = true;

                                while ((line = reader.readLine()) != null) {

                                        if (first) {

                                                first = false;

                                                sb.append(
                                                                String.format(
                                                                                "%-20s %-12s %-15s %-12s%n",
                                                                                "DATE",
                                                                                "RISK",
                                                                                "PRIVACY",
                                                                                "LEVEL"));

                                                sb.append(
                                                                "----------------------------------------------------\n");

                                                continue;
                                        }

                                        String[] parts = line.split(",");

                                        if (parts.length >= 4) {

                                                sb.append(
                                                                String.format(
                                                                                "%-20s %-12s %-15s %-12s%n",
                                                                                parts[0],
                                                                                parts[1],
                                                                                parts[2],
                                                                                parts[3]));
                                        }
                                }

                                reader.close();

                        } catch (IOException e) {

                                return "Unable to read history.";
                        }

                        return sb.toString();
                }
        }

        // ================================================================
        // UI HELPERS
        // ================================================================

        private JLabel sectionTitle(
                        String text) {

                JLabel label = new JLabel(text);

                label.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                16));

                label.setForeground(PRIMARY);

                label.setBorder(
                                BorderFactory.createEmptyBorder(
                                                15, 0, 8, 0));

                return label;
        }

        private JCheckBox createCheckBox(
                        String text) {

                JCheckBox box = new JCheckBox(text);

                box.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                14));

                box.setBackground(BG);
                box.setForeground(TEXT);

                box.setFocusPainted(false);

                return box;
        }

        private JPanel createRow(
                        String text,
                        JComponent component) {

                JPanel row = new JPanel(
                                new FlowLayout(
                                                FlowLayout.LEFT));

                row.setBackground(BG);

                JLabel label = new JLabel(text);

                label.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                14));

                row.add(label);
                row.add(component);

                return row;
        }

        private JPanel createHeader(
                        String title,
                        String subtitle) {

                JPanel panel = new JPanel();

                panel.setLayout(
                                new BoxLayout(
                                                panel,
                                                BoxLayout.Y_AXIS));

                panel.setBackground(Color.WHITE);

                panel.setBorder(
                                BorderFactory.createCompoundBorder(
                                                new MatteBorder(
                                                                0,
                                                                0,
                                                                1,
                                                                0,
                                                                BORDER),
                                                BorderFactory.createEmptyBorder(
                                                                18,
                                                                30,
                                                                18,
                                                                30)));

                JLabel titleLabel = new JLabel(title);

                titleLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                27));

                titleLabel.setForeground(DARK);

                JLabel subtitleLabel = new JLabel(subtitle);

                subtitleLabel.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                14));

                subtitleLabel.setForeground(
                                LIGHT_TEXT);

                panel.add(titleLabel);
                panel.add(
                                Box.createVerticalStrut(4));
                panel.add(subtitleLabel);

                return panel;
        }

        private JPanel createCard() {

                JPanel panel = new JPanel();

                panel.setBackground(CARD);

                panel.setBorder(
                                BorderFactory.createCompoundBorder(
                                                new LineBorder(
                                                                BORDER,
                                                                1,
                                                                true),
                                                BorderFactory.createEmptyBorder(
                                                                10,
                                                                10,
                                                                10,
                                                                10)));

                return panel;
        }

        private JButton createButton(
                        String text) {

                JButton button = new JButton(text);

                button.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.BOLD,
                                                14));

                button.setForeground(Color.WHITE);
                button.setBackground(PRIMARY);

                button.setFocusPainted(false);

                button.setBorder(
                                BorderFactory.createEmptyBorder(
                                                10,
                                                20,
                                                10,
                                                20));

                return button;
        }

        private JButton createSecondaryButton(
                        String text) {

                JButton button = new JButton(text);

                button.setFont(
                                new Font(
                                                "SansSerif",
                                                Font.PLAIN,
                                                14));

                button.setFocusPainted(false);

                button.setBorder(
                                BorderFactory.createCompoundBorder(
                                                new LineBorder(
                                                                BORDER,
                                                                1,
                                                                true),
                                                BorderFactory.createEmptyBorder(
                                                                9,
                                                                18,
                                                                9,
                                                                18)));

                return button;
        }

        // ================================================================
        // MAIN METHOD
        // ================================================================

        public static void main(
                        String[] args) {

                SwingUtilities.invokeLater(
                                () -> {

                                        try {

                                                UIManager.setLookAndFeel(
                                                                UIManager
                                                                                .getSystemLookAndFeelClassName());

                                        } catch (Exception ignored) {
                                        }

                                        new DigitalFootprintAnalyzer();
                                });
        }
}