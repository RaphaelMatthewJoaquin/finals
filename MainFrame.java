import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private CategoryDao categoryDao;
    private ProductDao productDao;

    public MainFrame() {
        categoryDao = new CategoryDao();
        productDao = new ProductDao();

        setTitle("MATT'S STORE");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Categories", new CategoryPanel(categoryDao));
        tabbedPane.add("Products", new ProductPanel(productDao, categoryDao));

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}