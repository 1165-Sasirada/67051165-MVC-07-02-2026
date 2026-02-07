package database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvDatabase {
	public static final String OFFICERS_FILE = "officers.csv";
	public static final String CLAIMANTS_FILE = "claimants.csv";
	public static final String CLAIMS_FILE = "claims.csv";
	public static final String COMPENSATIONS_FILE = "compensations.csv";
	public static final String POLICIES_FILE = "policies.csv";

	public static void init() {
		createFileIfNotExists(OFFICERS_FILE, "id,name,surname,email.password\n" + 
			"99001,Admin,Admin,admin@gov.com,admin123\n" +
			"99002,Officer,Officer,officer@gov.com,officer123\n"
		);
		createFileIfNotExists(CLAIMANTS_FILE, "id,name,income,type\n" +
			"00000,Somchai,Jaidee,somchai@mail.com,1234,5000.0,LOW\n" + 
			"00001,Somsri,Meejai,somsri@mail.com,12345,25000.0,GENERAL\n" +
			"00002,Somyot,Ruy,somyot@mail.com,12345,60000.0,HIGH\n" +
			"00003,Somsak,Meesuk,somsak@mail.com,12345,5000.0,LOW\n"
		);
		createFileIfNotExists(CLAIMS_FILE, "claim_id,claimant_id,date,status\n");
		createFileIfNotExists(COMPENSATIONS_FILE, "claim_id,amount,data\n");
		createFileIfNotExists(POLICIES_FILE, "policy_id,description,max_amount,min_income,max_income\n" +
			"P01,Low Income Aid,6500,0,6499\n" +
			"P02,General Aid,20000,6500,50000\n" +
			"P03,High Income Aid,20000,50001,99999999"
		);
	}

	public static String generateNextId(String filename) {
		List<String[]> records = readAll(filename);
		if (records.isEmpty())
			return "00000";

		String lastId = records.get(records.size() - 1)[0];
		try {
			int id = Integer.parseInt(lastId);
			return String.format("%05d", id + 1);
		} catch (NumberFormatException e) {
		  return "00000";  
		}
	}

	private static void createFileIfNotExists(String filename, String header) {
		File file = new File(filename);
		if (!file.exists()) {
			try (FileWriter writer = new FileWriter(filename)) {
				writer.write(header);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void appendRecord(String filename, String record) {
		try (FileWriter writer = new FileWriter(filename, true)) {
			writer.write(record + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String[]> readAll(String filename) {
		List<String[]> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			br.readLine(); // skip header
			while ((line = br.readLine()) != null) {
				records.add(line.split(","));
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + filename);
		}
		return records;
	}
}