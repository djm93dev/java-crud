import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

// creating classes for the form
public class JavaCrud {
    private JPanel Main;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField textName;
    private JTextField textPrice;
    private JTextField textQty;
    private JTextField textpid;
    private JButton searchButton;

    // main
    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    //db connection
    Connection con;
    PreparedStatement pst;


    public JavaCrud() {

        //db connection
        Connect();


        // save product to database
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,price,qty;

                name = textName.getText();
                price = textPrice.getText();
                qty = textQty.getText();


                try {
                    pst = con.prepareStatement("insert into products(name,price,qty)values(?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record added!");


                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();

                }


                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        // search and display from database
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String pid = textpid.getText();

                    pst = con.prepareStatement("select name,price,qty from products where pid = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {

                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        textName.setText(name);
                        textPrice.setText(price);
                        textQty.setText(qty);

                    }
                    else
                    {
                        textName.setText("");
                        textPrice.setText("");
                        textQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }
                catch (SQLException ex) {
                        ex.printStackTrace();
                }

            }
        });

        // update product in database
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            String pid,name,price,qty;

            name = textName.getText();
            price = textPrice.getText();
            qty = textQty.getText();
            pid = textpid.getText();

            try {
                pst = con.prepareStatement("update products set name = ?, price = ?,qty = ? where pid = ?");
                pst.setString(1, name);
                pst.setString(2, price);
                pst.setString(3, qty);
                pst.setString(4, pid);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Updated!");

                textName.setText("");
                textPrice.setText("");
                textQty.setText("");
                textName.requestFocus();
                textpid.setText("");

            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            }
        });

        // delete product from database
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid;

                pid = textpid.getText();

                try {
                    pst = con.prepareStatement("Delete from products where pid = ?");

                    pst.setString(1, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record deleted!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                    textpid.setText("");
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }


    //db connection
    public void Connect()
    {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/DMProducts", "root", "");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
                ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }
}
