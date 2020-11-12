package Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CensusAnalyser {
	public int loadStateCode(String indiaCensusCSVFilePath) throws CensusAnalyserException {
		int namOfEateries = 0;
		try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
			CsvToBeanBuilder<IndiaCodeCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(IndiaCodeCSV.class);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<IndiaCodeCSV> csvToBean = csvToBeanBuilder.build();
			Iterator<IndiaCodeCSV> censusCSVIterator = csvToBean.iterator();
			Iterable<IndiaCodeCSV> iterable = () -> censusCSVIterator;
			namOfEateries = (int) StreamSupport.stream(iterable.spliterator(), false).count();
			return namOfEateries;
		} catch (NoSuchFileException e) {
			if (!indiaCensusCSVFilePath.contains(".csv")) {
				throw new CensusAnalyserException(e.getMessage(),
						CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
			}
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (IllegalStateException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}

		return namOfEateries;
	}

}
