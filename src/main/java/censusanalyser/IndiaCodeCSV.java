package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCodeCSV {

	@CsvBindByName(column = "SrNo", required = true)
	public String srNo;

<<<<<<< HEAD
	@CsvBindByName(column = "StateName", required = true)
=======
	@CsvBindByName(column = "State Name", required = true)
>>>>>>> 52815a2fda5f34789d147adf61e5b8e8f1577eee
	public String stateName;

	@CsvBindByName(column = "TIN", required = true)
	public int tin;

	@CsvBindByName(column = "StateCode", required = true)
	public String stateCode;

	@Override
	public String toString() {
		return "IndiaCodeCSV [srNo=" + srNo + ", stateName=" + stateName + ", tin=" + tin + ", stateCode=" + stateCode
				+ "]";
	}
}
