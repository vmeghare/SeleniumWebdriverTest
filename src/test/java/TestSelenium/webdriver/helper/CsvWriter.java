package TestSelenium.webdriver.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CsvWriter {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss,SSS");

	/**
	 * To write data in output csv file
	 * 
	 * @param sFileName
	 */
	public static void generateCsvFile(String sFileName,
			List<StatsData> listData) {
		try {
			FileWriter writer = new FileWriter(sFileName,true);

			for (StatsData statsData : listData) {
				writer.append(statsData.getIdentifier());
				writer.append(',');
				writer.append(Long.toString(statsData.getResponseTime()));
				writer.append(',');
				// writer.append(statsData.getDateCaptured());
				writer.append(dateFormat.format(new Date()));
				writer.append('\n');

			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
