package runner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import classes.PrefixMatcher;

public class Runner {

	public static void main(String[] args) {
		Runner runner = new Runner();
		String str = runner.readTestFile();
		PrefixMatcher prefMatcher = new PrefixMatcher();
		prefMatcher.add(str);
		System.out.println(prefMatcher.size());
	}

	private String readTestFile() {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			FileReader fr = new FileReader("src\\main\\resources\\TestFile.txt");
			br = new BufferedReader(fr);

			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		return sb.toString();
	}
}
