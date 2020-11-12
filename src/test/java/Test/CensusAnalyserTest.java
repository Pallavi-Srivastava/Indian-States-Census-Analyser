package Test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

	private static final String INDIA_CODE_CSV_FILE_PATH = "E:\\Fellowship\\STS\\IndiaStateCode.csv";
	private static final String CODE_WRONG_CSV_FILE_PATH = "C:\\Fellowship\\STS\\IndiaStateCode.csv";
	private static final String CODE_WRONG_FILE_TYPE_PATH = "E:\\Fellowship\\STS\\IndiaStateCode.pdf";
	private static final String CODE_WRONG_DELIMITER_FILE_PATH = "E:\\Fellowship\\STS\\IndiaStateCode.csv";
	private static final String CODE_WITHOUT_HEADER_FILE_PATH = "E:\\Fellowship\\STS\\IndiaStateCode.csv";

	private static CensusAnalyser censusAnalyser;

	@BeforeClass
	public static void createcensusAnalyser() {
		censusAnalyser = new CensusAnalyser();
		System.out.println("Welcome to the Indian States Census Analyser Program.. ");
	}

	@Test
	public void givenIndianCodeCSVFileReturnsCorrectRecords() throws IOException {
		try {
			int numOfRecords = censusAnalyser.loadStateCode(INDIA_CODE_CSV_FILE_PATH);
			Assert.assertEquals(37, numOfRecords);
		} catch (CensusAnalyserException CensusAnalyserException) {
		}
	}

	@Test
	public void givenIndiaCodeData_WithWrongFile_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadStateCode(CODE_WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException censusAnalyserException) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,
					censusAnalyserException.type);
		}
	}

	@Test
	public void givenIndiaCodeData_WithWrongTypeFile_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadStateCode(CODE_WRONG_FILE_TYPE_PATH);
		} catch (CensusAnalyserException censusAnalyserException) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, censusAnalyserException.type);
		}
	}

	@Test
	public void givenIndiaCodeData_WithWrongDelimeter_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadStateCode(CODE_WRONG_DELIMITER_FILE_PATH);
		} catch (CensusAnalyserException censusAnalyserException) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, censusAnalyserException.type);
		}
	}

	@Test
	public void givenIndiaCodeData_WithoutHeader_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CensusAnalyserException.class);
			censusAnalyser.loadStateCode(CODE_WITHOUT_HEADER_FILE_PATH);
		} catch (CensusAnalyserException censusAnalyserException) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, censusAnalyserException.type);
		}
	}

}
