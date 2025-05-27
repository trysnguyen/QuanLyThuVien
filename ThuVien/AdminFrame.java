package ThuVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminFrame extends JFrame {

    private JPanel Rpanel, Lpanel;
    private JTable Ordertable, Booktable;
    private DefaultTableModel Ordermodel, Bookmodel;
    private JButton SearchB, AcceptB, RefuseB, DeleteB, AddB;
    private JLabel nameL, authorL, cateL, quantityL, idL;
    private JTextField nameF, authorF, cateF, SearchF, quantityF, idF;
    private int clickCount = 0;
    private static final int DOUBLE_CLICK_THRESHOLD = 500;
    private static Timer resetTimer;
    private JButton goToOrderManagementB;


    public AdminFrame() {
        setTitle("Admin");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);

        JPanel mainP = new JPanel(new GridLayout(1, 2));

        Rpanel = new JPanel(new GridBagLayout());

        JLabel SearchL = new JLabel("Search Book (Name/Author/Category): ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        Rpanel.add(SearchL, gbc);

        SearchF = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        Rpanel.add(SearchF, gbc);

        String[] column = {"BookID", "Bookname", "Author", "Category", "Quantity"};
        Bookmodel = new DefaultTableModel(column, 0);
        Booktable = new JTable(Bookmodel);
        JScrollPane scrollPane = new JScrollPane(Booktable);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        Rpanel.add(scrollPane, gbc);

        idL = new JLabel("Book ID");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        Rpanel.add(idL, gbc);
        idF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        Rpanel.add(idF, gbc);

        nameL = new JLabel("Bookname");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        Rpanel.add(nameL, gbc);
        nameF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        Rpanel.add(nameF, gbc);

        authorL = new JLabel("Author");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        Rpanel.add(authorL, gbc);
        authorF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        Rpanel.add(authorF, gbc);

        cateL = new JLabel("Category");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        Rpanel.add(cateL, gbc);
        cateF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        Rpanel.add(cateF, gbc);

        quantityL = new JLabel("Quantity");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        Rpanel.add(quantityL, gbc);

        quantityF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        Rpanel.add(quantityF, gbc);

        SearchB = new JButton("Search Book");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        Rpanel.add(SearchB, gbc);

        AddB = new JButton("Add Book");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.CENTER;
        Rpanel.add(AddB, gbc);

        DeleteB = new JButton("Delete Book");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        Rpanel.add(DeleteB, gbc);

        gbc.insets = new Insets(65, 5, 33, 5);
        Lpanel = new JPanel(new GridBagLayout());

        String[] column1 = {"username", "Studentname", "StudentID", "Bookname", "Status"};
        Ordermodel = new DefaultTableModel(column1, 0);
        Ordertable = new JTable(Ordermodel);
        JScrollPane orderScrollPane = new JScrollPane(Ordertable);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        Lpanel.add(orderScrollPane, gbc);

        AcceptB = new JButton("Accept");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        Lpanel.add(AcceptB, gbc);

        RefuseB = new JButton("Refuse");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        Lpanel.add(RefuseB, gbc);

        goToOrderManagementB = new JButton("Go to Order Management");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        Lpanel.add(goToOrderManagementB, gbc);

        mainP.add(Rpanel);
        mainP.add(Lpanel);

        add(mainP);
        loadingbook();
        loadorder();

        SearchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;

                if (clickCount == 1) {
                    if (resetTimer != null) {
                        resetTimer.stop();
                    }
                    resetTimer = new Timer(DOUBLE_CLICK_THRESHOLD, evt -> {
                        clickCount = 0;
                    });
                    resetTimer.setRepeats(false);
                    resetTimer.start();
                }

                if (clickCount == 1) {
                    Searchbook();
                } else if (clickCount == 2) {
                    Bookmodel.setRowCount(0);
                    loadingbook();
                    clickCount = 0;
                    if (resetTimer != null) {
                        resetTimer.stop();
                    }
                }
            }
        });

        AddB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add();
            }
        });

        DeleteB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete();
            }
        });

        AcceptB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request("Accept");
            }
        });

        RefuseB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request("Refuse");
            }
        });

        goToOrderManagementB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderManagementFrame().setVisible(true);
                setVisible(false);
            }
        });
    }

    private void loadingbook() {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM book ORDER BY BookID ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("BookID");
                String bookname = rs.getString("Bookname");
                String author = rs.getString("Author");
                String category = rs.getString("Category");
                int quantity = rs.getInt("Quantity");
                Bookmodel.addRow(new Object[]{id, bookname, author, category, quantity});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading book");
        }
    }

    private void loadorder() {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "select * from OrderBook";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return;
            }
            do {
                String user = rs.getString("username");
                String name = rs.getString("Studentname");
                String id = rs.getString("StudentID");
                String book = rs.getString("Bookname");
                String status = rs.getString("Status");
                Ordermodel.addRow(new Object[]{user, name, id, book, status});
            } while (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred during loading order");
        }
    }

    private void Searchbook() {
        String keyword = SearchF.getText().trim();

        if (keyword.isEmpty()) {
            Bookmodel.setRowCount(0);
            loadingbook();
            return;
        }

        String[] searchWords = keyword.split("\\s+");

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM book WHERE ");
        for (int i = 0; i < searchWords.length; i++) {
            if (i > 0) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append("(");
            sqlBuilder.append("Bookname LIKE ? OR Author LIKE ? OR Category LIKE ?");
            sqlBuilder.append(")");
        }

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlBuilder.toString());

            for (int i = 0; i < searchWords.length; i++) {
                String word = "%" + searchWords[i] + "%";
                ps.setString(i * 3 + 1, word); // Bookname
                ps.setString(i * 3 + 2, word); // Author
                ps.setString(i * 3 + 3, word); // Category
            }

            ResultSet rs = ps.executeQuery();
            Bookmodel.setRowCount(0);

            while (rs.next()) {
                int bookID = rs.getInt("BookID");
                String bookname = rs.getString("Bookname");
                String author = rs.getString("Author");
                String category = rs.getString("Category");
                int quantity = rs.getInt("Quantity");
                Bookmodel.addRow(new Object[]{bookID, bookname, author, category, quantity});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while searching for the book.");
        }
    }

    private void Add() {
        String book = nameF.getText();
        String author = authorF.getText();
        String cate = cateF.getText();
        int quantity = Integer.parseInt(quantityF.getText());
        int id = Integer.parseInt(idF.getText().trim());

        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO book (BookID, Bookname, Author, Category, Quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, book);
            ps.setString(3, author);
            ps.setString(4, cate);
            ps.setInt(5, quantity);

            Bookmodel.addRow(new Object[]{id, book, author, cate, quantity});

            int rowAffected = ps.executeUpdate();
            if (rowAffected > 0) {
                JOptionPane.showMessageDialog(this, "Added successfully");
                Bookmodel.setRowCount(0);
                loadingbook();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while adding the book.");
        }
    }

    private void Delete() {
        int row = Booktable.getSelectedRow();
        if (row != -1) {
            int id = (int) Bookmodel.getValueAt(row, 0);

            try {
                Connection con = DatabaseConnection.getConnection();
                String sql = "delete from book where BookID = ?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, id);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    String updateQuery = "UPDATE book SET BookID = BookID - 1 WHERE BookID > ?";
                    PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                    updateStmt.setInt(1, id);
                    updateStmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "deleted successfully");
                    Bookmodel.setRowCount(0);
                    loadingbook();
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void Request(String option) {
        int row = Ordertable.getSelectedRow();

        if (row != -1) {
            String stid = (String) Ordermodel.getValueAt(row, 2); // StudentID
            String book = Ordermodel.getValueAt(row, 3).toString(); // Bookname

            try {
                Connection con = DatabaseConnection.getConnection();

                String quantityQuery = "SELECT Quantity FROM book WHERE Bookname = ?";
                PreparedStatement quantityStmt = con.prepareStatement(quantityQuery);
                quantityStmt.setString(1, book);
                ResultSet rsQuantity = quantityStmt.executeQuery();

                if (rsQuantity.next()) {
                    int quantity = rsQuantity.getInt("Quantity");

                    if (quantity <= 0 && option.equals("Accept")) {
                        JOptionPane.showMessageDialog(this, "Cannot accept the order. The book is out of stock.");
                        return;
                    }
                }

                String statusQuery = "SELECT Status FROM OrderBook WHERE StudentID = ? AND Bookname = ? ORDER BY OrderID DESC LIMIT 1";
                PreparedStatement statusStmt = con.prepareStatement(statusQuery);
                statusStmt.setString(1, stid);
                statusStmt.setString(2, book);
                ResultSet rsStatus = statusStmt.executeQuery();

                if (rsStatus.next()) {
                    String currentStatus = rsStatus.getString("Status");

                    if ("Accepted".equals(currentStatus) || "Refused".equals(currentStatus)) {
                        JOptionPane.showMessageDialog(this, "This request has already been processed.");
                        return;
                    }

                    if ("Pending".equals(currentStatus)) {
                        String sql = "UPDATE OrderBook SET Status = ?, OrderedDate = NOW(), ReturnedDate = DATE_ADD(NOW(), INTERVAL 3 DAY) WHERE StudentID = ? AND Bookname = ? AND Status = 'Pending'";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setString(1, option);
                        ps.setString(2, stid);
                        ps.setString(3, book);

                        int rowaffected = ps.executeUpdate();

                        if (rowaffected > 0) {
                            if (option.equals("Accept")) {
                                String updateBookQuantitySql = "UPDATE book SET Quantity = Quantity - 1 WHERE Bookname = ?";
                                PreparedStatement updateStmt = con.prepareStatement(updateBookQuantitySql);
                                updateStmt.setString(1, book);
                                int quantityUpdated = updateStmt.executeUpdate();

                                if (quantityUpdated > 0) {
                                    JOptionPane.showMessageDialog(this, "Accepted Request and decreased the quantity of the book.");
                                } else {
                                    JOptionPane.showMessageDialog(this, "Error updating the book quantity.");
                                }
                            } else if (option.equals("Refuse")) {
                                JOptionPane.showMessageDialog(this, "Refused Request");
                            }

                            Ordermodel.setRowCount(0);
                            loadorder();
                            Bookmodel.setRowCount(0);
                            loadingbook();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Only requests with 'Pending' status can be updated.");
                    }
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error handling request");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new AdminFrame().setVisible(true);
    }
}
