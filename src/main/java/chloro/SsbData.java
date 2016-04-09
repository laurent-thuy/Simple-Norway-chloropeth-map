package chloro;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class SsbData {	
	private String lastQuarter;
	private String datasetSource;
	private Date datasetUpdated;
	private int numberOfCounties;
	private int numberOfVariables;
	private List<String> variablesNames = new ArrayList<>();
	private List<String> countiesNames = new ArrayList<>();
	private List<String> colorCodes = new ArrayList<>();
	private List<List<Double>> percentiles = new ArrayList<>();
	private List<List<Number>> variablesValues = new ArrayList<>();
	
	public String getLastQuarter() {
		return lastQuarter;
	}

	public void setLastQuarter(String lastQuarter) {
		this.lastQuarter = lastQuarter;
	}

	public String getDatasetSource() {
		return datasetSource;
	}

	public void setDatasetSource(String datasetSource) {
		this.datasetSource = datasetSource;
	}

	public Date getDatasetUpdated() {
		return datasetUpdated;
	}

	public void setDatasetUpdated(Date datasetUpdated) {
		this.datasetUpdated = datasetUpdated;
	}

	public List<String> getCountiesNames() {
		return countiesNames;
	}

	public void setCountiesNames(List<String> countiesNames) {
		this.countiesNames = countiesNames;
	}

	public int getNumberOfCounties() {
		return numberOfCounties;
	}

	public void setNumberOfCounties(int numberOfCounties) {
		this.numberOfCounties = numberOfCounties;
	}

	public int getNumberOfVariables() {
		return numberOfVariables;
	}

	public void setNumberOfVariables(int numberOfVariables) {
		this.numberOfVariables = numberOfVariables;
	}

	public List<String> getVariablesNames() {
		return variablesNames;
	}

	public void setVariablesNames(List<String> variablesNames) {
		this.variablesNames = variablesNames;
	}

	public List<String> getColorCodes() {
		return colorCodes;
	}

	public void setColorCodes(List<String> colorCodes) {
		this.colorCodes = colorCodes;
	}

	public List<List<Double>> getPercentiles() {
		return percentiles;
	}

	public void setPercentiles(List<List<Double>> percentiles) {
		this.percentiles = percentiles;
	}

	public List<List<Number>> getVariablesValues() {
		return variablesValues;
	}

	public void setVariablesValues(List<List<Number>> variablesValues) {
		this.variablesValues = variablesValues;
	}

}
