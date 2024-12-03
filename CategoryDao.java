import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Category";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("CategoryID"), rs.getString("CategoryName")));
            }
        }
        return categories;
    }

    public void addCategory(String categoryName) throws SQLException {
        String query = "INSERT INTO Category (CategoryName) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, categoryName);
            pstmt.executeUpdate();
        }
    }

    public void updateCategory(int categoryId, String categoryName) throws SQLException {
        String query = "UPDATE Category SET CategoryName = ? WHERE CategoryID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, categoryName);
            pstmt.setInt(2, categoryId);
            pstmt.executeUpdate();
        }
    }

    public void deleteCategory(int categoryId) throws SQLException {
        String query = "DELETE FROM Category WHERE CategoryID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            pstmt.executeUpdate();
        }
    }
}