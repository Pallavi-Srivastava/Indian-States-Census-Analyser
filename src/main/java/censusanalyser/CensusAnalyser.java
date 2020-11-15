package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import com.google.gson.Gson;

public class CensusAnalyser {

	List<IndiaCensusCSV> csvFileList = null;
	List<IndiaCodeCSV> codeCsvFileList = null;

	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		int noOfEateries = 0;
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactoy.createCsvBuilder();
			csvFileList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			return csvFileList.size();
		} catch (NoSuchFileException e) {
			if (!csvFilePath.contains(".csv")) {
				throw new CensusAnalyserException(e.getMessage(),
						CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
			}
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (CSVBuilderException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
		return noOfEateries;
	}

	public int loadIndiaCodeData(String csvFilePath) throws CensusAnalyserException {
		int noOfEateries = 0;
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactoy.createCsvBuilder();
			List<IndiaCensusCSV> codeCsvFileList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			return csvFileList.size();
		} catch (NoSuchFileException e) {
			if (!csvFilePath.contains(".csv")) {
				throw new CensusAnalyserException(e.getMessage(),
						CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
			}
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (CSVBuilderException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}

		return noOfEateries;
	}

	public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
		loadIndiaCensusData(csvFilePath);
		if (csvFileList == null || csvFileList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}

		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(csvFileList, censusComparator);
		String toJson = new Gson().toJson(csvFileList);
		return toJson;
	}

	public String getPopulousStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
		loadIndiaCensusData(csvFilePath);
		if (csvFileList == null || csvFileList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}

		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
		this.sort(csvFileList, censusComparator);
		String toJson = new Gson().toJson(csvFileList);
		return toJson;
	}

	private void sort(List<IndiaCensusCSV> csvFileList, Comparator<IndiaCensusCSV> censusComparator) {
		for (int i = 0; i < csvFileList.size(); i++) {
			for (int j = 0; j < csvFileList.size() - i - 1; j++) {
				IndiaCensusCSV census1 = csvFileList.get(j);
				IndiaCensusCSV census2 = csvFileList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					csvFileList.set(j, census2);
					csvFileList.set(j + 1, census1);
				}
			}
		}
	}

	public String getStateCodeWiseSortedData(String csvFilePath) throws CensusAnalyserException {
		loadIndiaCodeData(csvFilePath);
		if (codeCsvFileList == null || codeCsvFileList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}

		Comparator<IndiaCodeCSV> codeComparator = Comparator.comparing(census -> census.stateCode);
		this.codeSort(codeCsvFileList, codeComparator);
		String toJson = new Gson().toJson(codeCsvFileList);
		return toJson;
	}

	private void codeSort(List<IndiaCodeCSV> codeCsvFileList, Comparator<IndiaCodeCSV> codeComparator) {
		for (int i = 0; i < codeCsvFileList.size(); i++) {
			for (int j = 0; j < codeCsvFileList.size() - i - 1; j++) {
				IndiaCodeCSV census1 = codeCsvFileList.get(j);
				IndiaCodeCSV census2 = codeCsvFileList.get(j + 1);
				if (codeComparator.compare(census1, census2) > 0) {
					codeCsvFileList.set(j, census2);
					codeCsvFileList.set(j + 1, census1);
				}
			}
		}
	}
}
