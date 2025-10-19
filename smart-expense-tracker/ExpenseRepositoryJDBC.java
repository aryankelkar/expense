// This is what your repository would look like with raw JDBC
// (This is just for demonstration - don't actually use this!)

@Repository
public class ExpenseRepositoryJDBC {
    
    @Autowired
    private DataSource dataSource;
    
    // Add Expense - 25+ lines of code
    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (title, category, amount) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, expense.getTitle());
            ps.setString(2, expense.getCategory());
            ps.setDouble(3, expense.getAmount());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        expense.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding expense", e);
        }
    }
    
    // Get All Expenses - 30+ lines of code
    public List<Expense> getAllExpenses() {
        String sql = "SELECT id, title, category, amount FROM expenses";
        List<Expense> expenses = new ArrayList<>();
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("id"));
                expense.setTitle(rs.getString("title"));
                expense.setCategory(rs.getString("category"));
                expense.setAmount(rs.getDouble("amount"));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching expenses", e);
        }
        
        return expenses;
    }
    
    // Delete Expense - 15+ lines of code
    public void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new RuntimeException("No expense found with id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting expense", e);
        }
    }
    
    // Get Total Amount - 20+ lines of code
    public double getTotalAmount() {
        String sql = "SELECT COALESCE(SUM(amount), 0) as total FROM expenses";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating total", e);
        }
    }
}
