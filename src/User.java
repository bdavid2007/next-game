import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class User {
    public String name;
    public int highscore;
    private static final String FILE_NAME = "users.csv";
    private static final String FILE_HEADER = "name,highscore";
    public static String[][] users = new String[0][2]; // Initialize safely

    public User(String name) {
        this.name = name;
        this.highscore = Integer.MAX_VALUE; // Start high so lower scores replace it
    }

    private int getCSVLength() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return 0;

            Scanner countSc = new Scanner(file);
            if (countSc.hasNextLine()) countSc.nextLine(); // Skip header

            int fileLength = 0;
            while (countSc.hasNextLine()) {
                String line = countSc.nextLine().trim();
                if (!line.isEmpty()) fileLength++;
            }
            countSc.close();
            return fileLength;
        }
		catch (Exception exception) {
            System.err.println("Error counting rows: " + exception);
        }
        return 0;
    }

    public void updateUsersArray() {
        try {
            int fileLength = getCSVLength();
            // Allocate space for existing users + 1 potential new entry
            String[][] allUsers = new String[fileLength + 1][2];
            int actualStoredRows = 0;
            boolean userExists = false;

            File file = new File(FILE_NAME);
            if (file.exists() && fileLength > 0) {
                Scanner readSc = new Scanner(file);
                readSc.nextLine(); // Skip header
                
                while (readSc.hasNextLine()) {
                    String line = readSc.nextLine().trim();
                    if (line.isEmpty()) continue;
                    
                    String[] data = line.split(",");
                    if (this.name.equalsIgnoreCase(data[0])) {
                        userExists = true;
                        allUsers[actualStoredRows][0] = data[0];
                        // Fewest turns wins: Keep the LOWER score
                        int finalHighscore = Math.min(Integer.parseInt(data[1]), this.highscore);
                        allUsers[actualStoredRows][1] = String.valueOf(finalHighscore);
                    } else {
                        allUsers[actualStoredRows][0] = data[0];
                        allUsers[actualStoredRows][1] = data[1];
                    }
                    actualStoredRows++;
                }
                readSc.close();
            }
            
            // Append if user is completely new
            if (!userExists) {
                allUsers[actualStoredRows][0] = this.name;
                allUsers[actualStoredRows][1] = String.valueOf(this.highscore);
                actualStoredRows++;
            }

            // Trim out unused rows safely
            String[][] cleanUsers = new String[actualStoredRows][2];
            System.arraycopy(allUsers, 0, cleanUsers, 0, actualStoredRows);

            // Sort from lowest turns to highest turns
            Arrays.sort(cleanUsers, (a, b) -> Integer.compare(Integer.parseInt(a[1]), Integer.parseInt(b[1])));
            
            // Commit to global users variable
            users = cleanUsers;
        }
		catch (Exception exception) {
            System.err.println("Error updating users array: " + exception);
        }
    }

    public void updateUsersCSV() {
        try {
            PrintWriter writer = new PrintWriter(new File(FILE_NAME));
            writer.println(FILE_HEADER);

            // Dynamically safeguard bounds: write up to a maximum of 5 lines
            int rowsToWrite = Math.min(5, users.length);
            for (int i = 0; i < rowsToWrite; i++) {
                if (users[i][0] != null) {
                    writer.println(users[i][0] + "," + users[i][1]);
                }
            }
            writer.close();
        }
		catch (Exception exception) {
            System.err.println("Error writing to CSV: " + exception);
        }
    }

    public void printUsersData() {
        System.out.println("\nHigh Scores\n---------------");
        int rowsToPrint = Math.min(5, users.length);
        for (int index = 0; index < rowsToPrint; index++) {
            System.out.printf("%2s - %s\n", users[index][1], users[index][0]);
        }
    }

    public void endGame(int score) {
        if (score < this.highscore) {
            this.highscore = score;
        }
        updateUsersArray();
        updateUsersCSV();
        printUsersData();
    }
}