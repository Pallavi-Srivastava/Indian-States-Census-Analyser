package censusanalyser;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

	private static final String INDIA_CENSUS_CSV_FILE_PATH = "E:\\Fellowship\\STS\\CensusAnalyserFile\\IndiaStateCensusData.csv";
	private static final String WRONG_CSV_FILE_PATH = "C:\\Fellowship\\STS\\CensusAnalyserFile\\IndiaStateCensusData.csv";
	private static final String WRONG_FILE_TYPE_PATH = "E:\\Fellowship\\STS\\CensusAnalyserFile\\IndiaStateCensusData.pdf";
	private static final String WRONG_DELIMITER_FILE_PATH = "E:\\Fellowship\\STS\\CensusAnalyserFile\\IndiaStateCensusData.csv";
	private static final String WITHOUT_HEADER_FILE_PATH = "E:\\Fellowship\\STS\\CensusAnalyserFile\\IndiaStateCensusData.csv";

	private static CensusAnalyser censusAnalyser;

	@BeforeClass
	public static void createcensusAnalyser() {
		censusAnalyser = new CensusAnalyser();
		System.out.println("Welcome to the Indian States Census Analyser Program.. ");
	}

	@Test
	public void givenIndianCensusCSVFileReturnsCorrectRecords() {
		try {
			int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			Assert.assertEquals(29, numOfRecords);
		} catch (CensusAnalyserException censusAnalyserException) {
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException censusAnalyserException) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,
					censusAnalyserException.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongTypeFile_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusData(WRONG_FILE_TYPE_PATH);
		} catch (CensusAnalyserException censusAnalyserException) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, censusAnalyserException.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongDelimeter_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_FILE_PATH);
		} catch (CensusAnalyserException censusAnalyserException) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_SUCH_FILE, censusAnalyserException.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithoutHeader_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadIndiaCensusData(WITHOUT_HEADER_FILE_PATH);
		} catch (CensusAnalyserException censusAnalyserException) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_SUCH_FILE, censusAnalyserException.type);
		}
	}
}
