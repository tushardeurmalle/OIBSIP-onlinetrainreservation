import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OnlineReservationSystem {

    static Connection con;

    public static void main(String[] args) {

        setLookAndFeel();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/online_reservation",
                    "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        loginForm();
    }

    
    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LOGIN FORM =================
    public static void loginForm() {

        JFrame frame = new JFrame("Online Reservation System - Login");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("ONLINE RESERVATION SYSTEM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField userText = new JTextField(15);
        panel.add(userText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField passText = new JPasswordField(15);
        panel.add(passText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(0, 123, 255));
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            try {
                PreparedStatement pst = con.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=?");
                pst.setString(1, userText.getText());
                pst.setString(2, new String(passText.getPassword()));

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    frame.dispose();
                    mainMenu();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Credentials!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    // ================= MAIN MENU =================
    public static void mainMenu() {

        JFrame frame = new JFrame("Dashboard");
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(Color.WHITE);

        JLabel heading = new JLabel("Welcome to Online Reservation System", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setForeground(new Color(0, 102, 204));
        panel.add(heading, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        JButton reserveBtn = new JButton("Make Reservation");
        JButton cancelBtn = new JButton("Cancel Reservation");

        styleButton(reserveBtn, new Color(40, 167, 69));
        styleButton(cancelBtn, new Color(220, 53, 69));

        buttonPanel.add(reserveBtn);
        buttonPanel.add(cancelBtn);

        panel.add(buttonPanel, BorderLayout.CENTER);

        reserveBtn.addActionListener(e -> reservationForm());
        cancelBtn.addActionListener(e -> cancellationForm());

        frame.add(panel);
        frame.setVisible(true);
    }

    // ================= RESERVATION FORM =================
    public static void reservationForm() {

        JFrame frame = new JFrame("Make Reservation");
        frame.setSize(550, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(Color.WHITE);

        JTextField name = new JTextField();
        JTextField trainNo = new JTextField();
        JTextField trainName = new JTextField();
        JComboBox<String> classType = new JComboBox<>(new String[]{"Sleeper", "AC", "General"});
        JTextField date = new JTextField("YYYY-MM-DD");
        JTextField source = new JTextField();
        JTextField dest = new JTextField();

        panel.add(new JLabel("Name:")); panel.add(name);
        panel.add(new JLabel("Train No:")); panel.add(trainNo);
        panel.add(new JLabel("Train Name:")); panel.add(trainName);
        panel.add(new JLabel("Class Type:")); panel.add(classType);
        panel.add(new JLabel("Journey Date:")); panel.add(date);
        panel.add(new JLabel("From:")); panel.add(source);
        panel.add(new JLabel("To:")); panel.add(dest);

        JButton insertBtn = new JButton("Confirm Reservation");
        styleButton(insertBtn, new Color(0, 123, 255));
        panel.add(new JLabel());
        panel.add(insertBtn);

        insertBtn.addActionListener(e -> {
            try {
                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO reservations(name,train_no,train_name,class_type,journey_date,source,destination) VALUES(?,?,?,?,?,?,?)");

                pst.setString(1, name.getText());
                pst.setString(2, trainNo.getText());
                pst.setString(3, trainName.getText());
                pst.setString(4, classType.getSelectedItem().toString());
                pst.setString(5, date.getText());
                pst.setString(6, source.getText());
                pst.setString(7, dest.getText());

                pst.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Reservation Successful!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    // ================= CANCELLATION FORM =================
    public static void cancellationForm() {

        JFrame frame = new JFrame("Cancel Reservation");
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(Color.WHITE);

        JTextField pnr = new JTextField();

        panel.add(new JLabel("Enter PNR:"));
        panel.add(pnr);

        JButton cancelBtn = new JButton("Cancel Ticket");
        styleButton(cancelBtn, new Color(220, 53, 69));

        panel.add(new JLabel());
        panel.add(cancelBtn);

        cancelBtn.addActionListener(e -> {
            try {
                PreparedStatement pst = con.prepareStatement(
                        "DELETE FROM reservations WHERE pnr=?");
                pst.setInt(1, Integer.parseInt(pnr.getText()));

                int rows = pst.executeUpdate();

                if (rows > 0)
                    JOptionPane.showMessageDialog(frame, "Ticket Cancelled!");
                else
                    JOptionPane.showMessageDialog(frame, "PNR Not Found!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    // ================= BUTTON STYLING METHOD =================
    public static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}
