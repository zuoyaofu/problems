import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Logger;

public class ProblematicCode {
    private static final Logger logger = Logger.getLogger(ProblematicCode.class.getName());
    private Connection conn;
    private String password = "secretPassword123"; // Hard-coded credentials

    public void connectToDatabase(String userInput) {
        try {
            // SQL Injection vulnerability
            String query = "SELECT * FROM users WHERE name = '" + userInput + "'";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (Exception e) {
            // Bad exception handling
            e.printStackTrace();
        }
    }

    public boolean readSensitiveFile() {
        FileReader reader = null;
        try {
            // Potential path traversal vulnerability
            File file = new File(System.getProperty("user.home") + "/secret.txt");
            reader = new FileReader(file);
            return true;
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return false;
        }
        // Resource leak - reader not closed
    }

    public void insecureHash(String input) {
        try {
            // Using weak MD5 hash
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
        } catch (Exception e) {
            // Empty catch block
        }
    }

    public synchronized void potentialDeadlock(Object lock1, Object lock2) {
        // Nested synchronization - potential deadlock
        synchronized(lock1) {
            synchronized(lock2) {
                // Do something
            }
        }
    }

    private void unsafeDeserialization(String filename) {
        try {
            // Unsafe deserialization vulnerability
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(
                new java.io.FileInputStream(filename));
            Object obj = ois.readObject();
        } catch (Exception e) {
            logger.warning(e.toString());
        }
    }
}
