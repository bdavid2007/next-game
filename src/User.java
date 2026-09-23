import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;

public class User {
	public String name;
	public int highscore;
	private static String fileName = "users.csv";
	private static String fileHeader = "name,highscore";
	public static String[][] users;

	public User(String name) {
		this.name = name;
		this.highscore = 0;
	}

	private int getCSVLength() {
		try {
			File file = new File(fileName);
			if (!file.exists()) return 0;

			Scanner countSc = new Scanner(file);
			if (countSc.hasNextLine()) {
				countSc.nextLine(); // Skip header
			}

			int fileLength = 0;
			while (countSc.hasNextLine()) {
				countSc.nextLine();
				fileLength++;
			}
			countSc.close();
			return fileLength;
		}
		catch (Exception exception) {
			System.err.println("Error counting rows: " + exception);
		}
		return 0;
	}

	private void updateUsersArray() {
		try {
			int index;
			int fileLength = getCSVLength();
			users = new String[fileLength + 1][2];

			File file = new File(fileName);
			boolean userExists = false;
			int actualStoredRows = 0;

			if (file.exists() && fileLength > 0) {
				Scanner readSc = new Scanner(file);
				readSc.nextLine(); // Skip header
				
				for (index = 0; index < fileLength; index++) {
					if (!readSc.hasNextLine()) break;
					
					String line = readSc.nextLine();
					String[] data = line.split(",");

					if (this.name.equals(data[0])) {
						userExists = true;
						users[actualStoredRows][0] = data[0];
						// Keep the higher score between existing and current
						int finalHighscore = Math.max(Integer.parseInt(data[1]), this.highscore);
						users[actualStoredRows][1] = String.valueOf(finalHighscore);
					}
					else {
						users[actualStoredRows][0] = data[0];
						users[actualStoredRows][1] = data[1];
					}
					actualStoredRows++;
				}
				readSc.close();
			}
			
			// If it's a completely new user, push them to the end
			if (!userExists) {
				users[actualStoredRows][0] = this.name;
				users[actualStoredRows][1] = String.valueOf(this.highscore);
				actualStoredRows++;
			}

			// Shrink array in case the user was already found in the array
			if (actualStoredRows < users.length) {
				String[][] tempUsers = new String[actualStoredRows][2];
				System.arraycopy(users, 0, tempUsers, 0, actualStoredRows);
				users = tempUsers;
			}
		}
		catch (Exception exception) {
			System.err.println("Error updating users array: " + exception);
		}
	}

	private void updateUsersCSV() {
		try {
			PrintWriter writer = new PrintWriter(new File(fileName));
			writer.println(fileHeader);

			int index;
			for (index = 0; index < users.length; index++) {
				if (users[index][0] != null) {
					writer.println(users[index][0] + "," + users[index][1]);
				}
			}
			writer.close();
		}
		catch (Exception exception) {
			System.err.println("Error writing to CSV: " + exception);
		}
	}

	public void addUserToCSV() {
		updateUsersArray();
		updateUsersCSV();
	}

	public void printUsersData() {
		System.out.println("High Scores\n---------------");
		int index;
		for (index = 0; index < users.length; index++) {
			System.out.printf("%2d - %s\n", users[index][1], users[index][0]);
		}
	}
}