import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ProductPanel extends JPanel {
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private DefaultListModel<Product> listModel;
    private JList<Product> productList;
    private JTextField productNameField, priceField;
    private JComboBox<Category> categoryComboBox;

    public ProductPanel(ProductDao productDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;

        setLayout(new BorderLayout());

      
        listModel = new DefaultListModel<>();
        productList = new JList<>(listModel);
        refreshProductList();
        add(new JScrollPane(productList), BorderLayout.CENTER);

  
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JPanel formPanel2 = new JPanel(new GridLayout(1,3));
        productNameField = new JTextField();
        priceField = new JTextField();
        categoryComboBox = new JComboBox<>();
        refreshCategoryComboBox();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(productNameField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryComboBox);
        formPanel2.add(addButton);
        formPanel2.add(updateButton);
        formPanel2.add(deleteButton);
        add(formPanel, BorderLayout.WEST);
        add(formPanel2, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
    }

    private void refreshProductList() {
        try {
            listModel.clear();
            List<Product> products = productDao.getAllProducts();
            for (Product product : products) {
                listModel.addElement(product);
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void refreshCategoryComboBox() {
        try {
            categoryComboBox.removeAllItems();
            List<Category> categories = categoryDao.getAllCategories();
            for (Category category : categories) {
                categoryComboBox.addItem(category);
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void addProduct() {
        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();
        Category selectedCategory = (Category) categoryComboBox.getSelectedItem();

        if (!name.isEmpty() && !priceText.isEmpty() && selectedCategory != null) {
            try {
                int price = Integer.parseInt(priceText);
                productDao.addProduct(name, price, selectedCategory.getCategoryId());
                refreshProductList();
                productNameField.setText("");
                priceField.setText("");
            } catch (NumberFormatException e) {
                showError("Price must be a number.");
            } catch (SQLException e) {
                showError(e.getMessage());
            }
        }
    }

    private void updateProduct() {
        Product selected = productList.getSelectedValue();
        if (selected != null) {
            String name = productNameField.getText().trim();
            String priceText = priceField.getText().trim();
            Category selectedCategory = (Category) categoryComboBox.getSelectedItem();

            if (!name.isEmpty() && !priceText.isEmpty() && selectedCategory != null) {
                try {
                    int price = Integer.parseInt(priceText);
                    productDao.updateProduct(selected.getProductId(), name, price, selectedCategory.getCategoryId());
                    refreshProductList();
                    productNameField.setText("");
                    priceField.setText("");
                } catch (NumberFormatException e) {
                    showError("Price must be a number.");
                } catch (SQLException e) {
                    showError(e.getMessage());
                }
            }
        }
    }

    private void deleteProduct() {
        Product selected = productList.getSelectedValue();
        if (selected != null) {
            try {
                productDao.deleteProduct(selected.getProductId());
                refreshProductList();
            } catch (SQLException e) {
                showError(e.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}