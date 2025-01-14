package ThuVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class OrderManagementFrame extends JFrame {
    private JTable Ordertable;
    private DefaultTableModel ordermodel;
    private JButton Search, openAdminFrameB, Returned;
    private JTextField searchfield;

    public OrderManagementFrame() {
        setTitle("Order Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainpanel = new JPanel(new BorderLayout());
        JPanel searchpanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel searchlabel = new JLabel("Search by Book Name");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchpanel.add(searchlabel, gbc);

        searchfield = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchpanel.add(searchfield, gbc);

        Search = new JButton("Search");
        gbc.gridx = 2;
        gbc.gridy = 0;
        searchpanel.add(Search, gbc);

        openAdminFrameB = new JButton("Go to Admin");
        gbc.gridx = 3;
        gbc.gridy = 0;
        searchpanel.add(openAdminFrameB, gbc);

        // Add Returned button
        Returned = new JButton("Returned");
        gbc.gridx = 4;
        gbc.gridy = 0;
        searchpanel.add(Returned, gbc);

        String[] columns = {"Username", "Student Name", "Student ID", "Book Name", "Status", "Ordered Date", "Returned Date"};
        ordermodel = new DefaultTableModel(columns, 0);
        Ordertable = new JTable(ordermodel);
        JScrollPane orderScrollPane = new JScrollPane(Ordertable);

        mainpanel.add(searchpanel, BorderLayout.NORTH);
        mainpanel.add(orderScrollPane, BorderLayout.CENTER);

        add(mainpanel, BorderLayout.CENTER);

        loadAcceptedOrders();

        openAdminFrameB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminFrame().setVisible(true);
                setVisible(false);
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAcceptedOrders();
            }
        });

        Returned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAsReturned();
            }
        });
    }

    private void loadAcceptedOrders() {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM OrderBook WHERE Status = 'Accept'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ordermodel.setRowCount(0);

            while (rs.next()) {
                String username = rs.getString("username");
                String studentName = rs.getString("Studentname");
                String studentId = rs.getString("StudentID");
                String bookName = rs.getString("Bookname");
                String status = rs.getString("Status");
                Date orderedDate = rs.getDate("OrderedDate");
                Date returnedDate = rs.getDate("ReturnedDate");

                if (returnedDate == null && orderedDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(orderedDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 3);
                    returnedDate = new Date(calendar.getTimeInMillis());
                }

                ordermodel.addRow(new Object[]{username, studentName, studentId, bookName, status, orderedDate, returnedDate});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while loading the orders.");
        }
    }

    private void searchAcceptedOrders() {
        String bookName = searchfield.getText().trim();

        try {
            Connection con = DatabaseConnection.getConnection();
            String sql;
            PreparedStatement ps;

            if (bookName.isEmpty()) {
                sql = "SELECT * FROM OrderBook WHERE Status = 'Accept'";
                ps = con.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM OrderBook WHERE Bookname LIKE ? AND Status = 'Accept'";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + bookName + "%");
            }

            ResultSet rs = ps.executeQuery();

            ordermodel.setRowCount(0);

            while (rs.next()) {
                String username = rs.getString("username");
                String studentName = rs.getString("Studentname");
                String studentId = rs.getString("StudentID");
                String orderBookName = rs.getString("Bookname");
                String status = rs.getString("Status");
                Date orderedDate = rs.getDate("OrderedDate");
                Date returnedDate = rs.getDate("ReturnedDate");

                if (returnedDate == null && orderedDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(orderedDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 3);
                    returnedDate = new Date(calendar.getTimeInMillis());
                }

                ordermodel.addRow(new Object[]{username, studentName, studentId, orderBookName, status, orderedDate, returnedDate});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while searching for orders.");
        }
    }

    private void markAsReturned() {
        int selectedRow = Ordertable.getSelectedRow();
        if (selectedRow != -1) {
            String studentId = ordermodel.getValueAt(selectedRow, 2).toString();
            String bookName = ordermodel.getValueAt(selectedRow, 3).toString();

            try {
                Connection con = DatabaseConnection.getConnection();

                String updateStatusSql = "UPDATE OrderBook SET Status = 'Returned' WHERE StudentID = ? AND Bookname = ?";
                PreparedStatement psUpdateStatus = con.prepareStatement(updateStatusSql);
                psUpdateStatus.setString(1, studentId);
                psUpdateStatus.setString(2, bookName);
                psUpdateStatus.executeUpdate();

                String updateQuantitySql = "UPDATE book SET Quantity = Quantity + 1 WHERE Bookname = ?";
                PreparedStatement psUpdateQuantity = con.prepareStatement(updateQuantitySql);
                psUpdateQuantity.setString(1, bookName);
                psUpdateQuantity.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book returned successfully.");
                ordermodel.removeRow(selectedRow);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while updating the order status.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to mark as returned.");
        }
    }

    public static void main(String[] args) {
        new OrderManagementFrame().setVisible(true);
    }
}
