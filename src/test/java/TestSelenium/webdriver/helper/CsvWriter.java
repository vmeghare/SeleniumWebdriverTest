package TestSelenium.webdriver.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CsvWriter {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss,SSS");
	private static final String FILE_HEADER = "Identifier, ResponseTime(ms), Timestamp";

	/**
	 * To write data in output csv file
	 * 
	 * @param sFileName
	 */
	public static void generateCsvFile(String sFileName,
			List<StatsData> listData) {
		try {
			File file = new File(sFileName);
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			StringBuilder content = new StringBuilder();
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				content.append(FILE_HEADER);
				content.append('\n');
			}
 
			for (StatsData statsData : listData) {
				content.append(statsData.getIdentifier());
				content.append(',');
				content.append(Long.toString(statsData.getResponseTime()));
				content.append(',');
				// writer.append(statsData.getDateCaptured());
				content.append(dateFormat.format(new Date()));
				content.append('\n');

			}
			bw.write(content.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
