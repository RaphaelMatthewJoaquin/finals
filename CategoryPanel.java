import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CategoryPanel extends JPanel {
    private CategoryDao categoryDao;
    private DefaultListModel<Category> listModel;
    private JList<Category> categoryList;
    private JTextField categoryNameField;

    public CategoryPanel(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        setLayout(new BorderLayout());

        // List
        listModel = new DefaultListModel<>();
        categoryList = new JList<>(listModel);
        refreshCategoryList();
        add(new JScrollPane(categoryList), BorderLayout.CENTER);

        // Form
        JPanel formPanel = new JPanel(new FlowLayout());
        categoryNameField = new JTextField(20);
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        formPanel.add(new JLabel("Category Name:"));
        formPanel.add(categoryNameField);
        formPanel.add(addButton);
        formPanel.add(updateButton);
        formPanel.add(deleteButton);
        add(formPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addCategory());
        updateButton.addActionListener(e -> updateCategory());
        deleteButton.addActionListener(e -> deleteCategory());
    }

    private void refreshCategoryList() {
        try {
            listModel.clear();
            List<Category> categories = categoryDao.getAllCategories();
            for (Category category : categories) {
                listModel.addElement(category);
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private void addCategory() {
        String name = categoryNameField.getText().trim();
        if (!name.isEmpty()) {
            try {
                categoryDao.addCategory(name);
                refreshCategoryList();
                categoryNameField.setText("");
            } catch (SQLException e) {
                showError(e.getMessage());
            }
        }
    }

    private void updateCategory() {
        Category selected = categoryList.getSelectedValue();
        if (selected != null) {
            String name = categoryNameField.getText().trim();
            if (!name.isEmpty()) {
                try {
                    categoryDao.updateCategory(selected.getCategoryId(), name);
                    refreshCategoryList();
                    categoryNameField.setText("");
                } catch (SQLException e) {
                    showError(e.getMessage());
                }
            }
        }
    }

    private void deleteCategory() {
        Category selected = categoryList.getSelectedValue();
        if (selected != null) {
            try {
                categoryDao.deleteCategory(selected.getCategoryId());
                refreshCategoryList();
            } catch (SQLException e) {
                showError(e.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}