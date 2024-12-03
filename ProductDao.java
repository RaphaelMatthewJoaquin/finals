import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getInt("Price"),
                        rs.getInt("CategoryID")
                ));
            }
        }
        return products;
    }

    public void addProduct(String productName, int price, int categoryId) throws SQLException {
        String query = "INSERT INTO Product (ProductName, Price, CategoryID) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, productName);
            pstmt.setInt(2, price);
            pstmt.setInt(3, categoryId);
            pstmt.executeUpdate();
        }
    }

    public void updateProduct(int productId, String productName, int price, int categoryId) throws SQLException {
        String query = "UPDATE Product SET ProductName = ?, Price = ?, CategoryID = ? WHERE ProductID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, productName);
            pstmt.setInt(2, price);
            pstmt.setInt(3, categoryId);
            pstmt.setInt(4, productId);
            pstmt.executeUpdate();
        }
    }

    public void deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM Product WHERE ProductID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        }
    }
}