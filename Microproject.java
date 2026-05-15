package microproject;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;

// LOGIN PAGE CLASS
class Loginpg extends Frame implements ActionListener {

    TextField tf1, tf2;
    Button b1, b2;
    boolean showpass = false;
    Image img;

    public Loginpg() {

        // Load Image
        try {
            img = new ImageIcon(getClass().getResource("cafe.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("Image not found!");
        }

        setTitle("Cafe Login Page");
        setSize(1150, 650);
        setLayout(null);
        setBackground(new Color(255, 248, 220));

        // Center Frame
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - 1150) / 2, (d.height - 650) / 2);

        Label l1 = new Label("THE URBAN CAFE LOGIN", Label.CENTER);
        l1.setFont(new Font("Serif", Font.BOLD, 42));
        l1.setForeground(new Color(120, 40, 0));
        l1.setBounds(350, 80, 550, 60);
        add(l1);

        Label l2 = new Label("Phone Number");
        l2.setFont(new Font("Arial", Font.BOLD, 20));
        l2.setBounds(330, 220, 200, 35);
        add(l2);

        tf1 = new TextField();
        tf1.setFont(new Font("Arial", Font.PLAIN, 18));
        tf1.setBounds(550, 220, 250, 35);
        add(tf1);

        Label l3 = new Label("Password");
        l3.setFont(new Font("Arial", Font.BOLD, 20));
        l3.setBounds(330, 290, 200, 35);
        add(l3);

        tf2 = new TextField();
        tf2.setFont(new Font("Arial", Font.PLAIN, 18));
        tf2.setEchoChar('*');
        tf2.setBounds(550, 290, 250, 35);
        add(tf2);

        b2 = new Button("Show");
        b2.setBounds(820, 290, 70, 35);
        add(b2);

        Label l4 = new Label("Forgot Password?");
        l4.setForeground(Color.blue);
        l4.setBounds(670, 335, 150, 25);
        add(l4);

        b1 = new Button("Login");
        b1.setFont(new Font("Arial", Font.BOLD, 20));
        b1.setBounds(450, 420, 200, 50);
        add(b1);

        b1.addActionListener(this);

        // Show/Hide Password
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showpass) {
                    tf2.setEchoChar('*');
                    b2.setLabel("Show");
                    showpass = false;
                } else {
                    tf2.setEchoChar((char) 0);
                    b2.setLabel("Hide");
                    showpass = true;
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    // Background Image
    public void paint(Graphics g) {
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void actionPerformed(ActionEvent e) {

        String password = tf2.getText();
        String correctPass = "cafe123";

        if (password.equals(correctPass)) {

            dispose();
            new FoodGUI();

        } else {

            final Dialog error = new Dialog(this, "Login Error", true);

            error.setLayout(new FlowLayout());

            Label msg = new Label("Incorrect Password");
            Button ok = new Button("OK");

            error.add(msg);
            error.add(ok);

            error.setSize(260, 120);
            error.setLocation(500, 350);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    error.dispose();
                }
            });

            error.setVisible(true);
        }
    }
}

// FOOD GUI CLASS
class FoodGUI extends Frame {

    Checkbox[] items = new Checkbox[24];
    TextField[] tf = new TextField[24];
    Button[] plus = new Button[24];
    Button[] minus = new Button[24];

    Menu dm;
    MenuItem billHistoryItem, totalOrdersItem;

    String billHistory = "";
    double totalRevenue = 0;

    String[] names = {
            "Sandwich", "Pasta", "Burger", "Momos", "Shawarma", "Pizza",
            "Tea", "Coke", "Coffee", "Lemon Iced Tea", "Cold Coffee", "Chocolate Shake",
            "Nachos", "Garlic Bread", "Salad", "French Fries", "Onion Rings", "Cheese Sticks",
            "Cupcake", "Donut", "Ice Cream", "Cheesecake", "Brownie", "Waffle"
    };

    int[] prices = {
            180, 220, 250, 190, 240, 350,
            50, 50, 90, 100, 130, 180,
            130, 140, 150, 160, 170, 200,
            70, 90, 100, 120, 150, 160
    };

    Button button1, button2;
    TextArea billA;

    static int billNo = 0;

    public FoodGUI() {

        setTitle("Cafe Management System");
        setSize(1150, 650);
        setLayout(new BorderLayout(15, 15));

        MenuBar mb = new MenuBar();

        Menu m = new Menu("System");
        MenuItem mi = new MenuItem("Log out");

        dm = new Menu("Dashboard");

        billHistoryItem = new MenuItem("Bill History");
        totalOrdersItem = new MenuItem("Total Orders");

        dm.add(billHistoryItem);
        dm.add(totalOrdersItem);

        m.add(mi);

        mb.add(m);
        mb.add(dm);

        setMenuBar(mb);

        mi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Loginpg();
            }
        });

        Color bg = new Color(255, 248, 220);

        setBackground(bg);

        Label lb = new Label("THE URBAN CAFE", Label.CENTER);
        lb.setFont(new Font("Serif", Font.BOLD, 34));
        lb.setForeground(new Color(120, 40, 0));

        add(lb, BorderLayout.NORTH);

        Panel p = new Panel();

        p.setLayout(new GridLayout(0, 3, 20, 10));
        p.setBackground(bg);

        addSection(p, "MAIN MENU", 0, 5);
        addSection(p, "BEVERAGES", 6, 11);
        addSection(p, "SIDERS", 12, 17);
        addSection(p, "DESSERT", 18, 23);

        ScrollPane sp = new ScrollPane();
        sp.add(p);

        add(sp, BorderLayout.CENTER);

        billA = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);

        billA.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billA.setEditable(false);

        add(billA, BorderLayout.EAST);

        Panel bottom = new Panel();

        button1 = new Button("Create Bill");
        button2 = new Button("Clear");

        bottom.add(button1);
        bottom.add(button2);

        add(bottom, BorderLayout.SOUTH);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createBill();
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearBill();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void addSection(Panel p, String title, int start, int end) {

        Label section = new Label("---- " + title + " ----");

        section.setFont(new Font("Serif", Font.BOLD, 18));

        p.add(section);
        p.add(new Label(""));
        p.add(new Label(""));

        for (int i = start; i <= end; i++) {

            final int index = i;

            items[i] = new Checkbox(names[i]);

            Label price = new Label("Rs " + prices[i], Label.CENTER);

            tf[i] = new TextField("0", 3);
            tf[i].setEditable(false);

            plus[i] = new Button("+");
            minus[i] = new Button("-");

            Panel qty = new Panel();

            qty.add(minus[i]);
            qty.add(tf[index]);
            qty.add(plus[index]);

            items[i].addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (items[index].getState())
                        tf[index].setText("1");
                    else
                        tf[index].setText("0");
                }
            });

            plus[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    int q = Integer.parseInt(tf[index].getText());

                    tf[index].setText(String.valueOf(q + 1));

                    items[index].setState(true);
                }
            });

            minus[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    int q = Integer.parseInt(tf[index].getText());

                    if (q > 0) {

                        q--;

                        tf[index].setText(String.valueOf(q));

                        if (q == 0)
                            items[index].setState(false);
                    }
                }
            });

            p.add(items[i]);
            p.add(price);
            p.add(qty);
        }
    }

    public void createBill() {

        billA.setText("");

        int subtotal = 0;

        billNo++;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String date = sdf.format(new Date());

        billA.append("THE URBAN CAFE\n");
        billA.append("Bill No : " + billNo + "\n");
        billA.append("Date : " + date + "\n");

        billA.append("--------------------------------------\n");

        for (int i = 0; i < 24; i++) {

            int quantity = Integer.parseInt(tf[i].getText());

            if (quantity > 0) {

                int amount = quantity * prices[i];

                subtotal += amount;

                billA.append(names[i] + " x " + quantity + " = Rs " + amount + "\n");
            }
        }

        double gst = subtotal * 0.05;
        double total = subtotal + gst;

        billA.append("--------------------------------------\n");
        billA.append("Subtotal : Rs " + subtotal + "\n");
        billA.append("GST (5%) : Rs " + gst + "\n");
        billA.append("Total : Rs " + total + "\n");

        paymentFrame(total);
    }

    public void clearBill() {

        for (int i = 0; i < 24; i++) {

            items[i].setState(false);
            tf[i].setText("0");
        }

        billA.setText("");
    }

    public void paymentFrame(final double totalAmount) {

        final Frame pay = new Frame("Payment Method");

        pay.setSize(400, 300);

        pay.setLayout(new GridLayout(6, 1));

        final CheckboxGroup cg = new CheckboxGroup();

        Checkbox cash = new Checkbox("Cash", cg, false);
        Checkbox card = new Checkbox("Card", cg, false);
        Checkbox upi = new Checkbox("UPI", cg, false);

        Button payNow = new Button("Proceed");

        pay.add(new Label("Select Payment Method", Label.CENTER));
        pay.add(new Label("Total Amount: Rs " + totalAmount, Label.CENTER));

        pay.add(cash);
        pay.add(card);
        pay.add(upi);

        pay.add(payNow);

        payNow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Checkbox selected = cg.getSelectedCheckbox();

                if (selected != null) {

                    billA.append("\nPayment Method : " + selected.getLabel());

                    totalRevenue += totalAmount;

                    pay.dispose();
                }
            }
        });

        pay.setVisible(true);
    }
}

// MAIN CLASS
public class Microproject {

    public static void main(String[] args) {

        new Loginpg();
    }
}