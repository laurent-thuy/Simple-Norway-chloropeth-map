package chloro;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.math.stat.StatUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssb.SSBDataset;
import ssb.SSBDatasetException;

@RestController
public class SsbDataController {

	@RequestMapping("/ssbdata")
	public SsbData chloro(Model model) throws IOException, SSBDatasetException {

		List<String> colorCodes = Arrays.asList("Oranges", "Blues", "Reds", "Greys", "Greens", "Purples", "Oranges",
				"Blues", "Reds", "Greys", "Greens", "Purples", "Oranges", "Blues", "Reds", "Greys", "Greens",
				"Purples");

		SSBDataset ds = new SSBDataset(new URL("https://data.ssb.no/api/v0/dataset/1102.json?lang=en"));

		int variableIndex = 1;
		int regionIndex = 0;
		int regionSize = ds.getDimensionsSizes().get(regionIndex);
		int variableSize = ds.getDimensionsSizes().get(variableIndex);

		SsbData ssbData = new SsbData();
		ssbData.setNumberOfCounties(regionSize);
		ssbData.setNumberOfVariables(variableSize);
		ssbData.setLastQuarter(ds.getDimensionCategoryLabels("Tid").get(0).toString());
		ssbData.setDatasetSource(ds.source());
		ssbData.setDatasetUpdated(ds.updated());

		List<Integer> selectionList = Arrays.asList(0, 0, 0);

		// variables names
		for (int i = 0; i < variableSize; i++) {
			selectionList.set(variableIndex, i);
			String variableName = (String) ds.cells().get(selectionList).getLabels().get(variableIndex);
			ssbData.getVariablesNames().add(variableName);
			ssbData.getColorCodes().add(colorCodes.get(i));
		}

		// variables values
		SortedMap<String, List<Number>> sortedMap = new TreeMap<>();
		selectionList = Arrays.asList(0, 0, 0);
		for (int i = 0; i < regionSize; i++) {
			selectionList.set(regionIndex, i);
			String countyName = (String) ds.cells().get(selectionList).getLabels().get(regionIndex);
			List<Number> valuesForOneCounty = new ArrayList<>();
			for (int j = 0; j < variableSize; j++) {
				selectionList.set(variableIndex, j);
				Number value = (Number) ds.cells().get(selectionList).getValue();
				valuesForOneCounty.add(value);
			}
			sortedMap.put(countyName, valuesForOneCounty);
		}

		sortedMap.keySet().stream().forEach(name -> ssbData.getCountiesNames().add(name));
		sortedMap.values().stream().forEach(valueList -> ssbData.getVariablesValues().add(valueList));

		// get percentiles for colors in chloropeth map
		int ctrCounties = 0;
		int ctrVariables = 0;
		while (ctrCounties < ssbData.getNumberOfCounties() && ctrVariables < ssbData.getNumberOfVariables()) {

			double[] dArray = new double[ssbData.getNumberOfCounties()];
			List<Double> percentilesForOneVariable = new ArrayList<>();
			for (int i = 0; i < ssbData.getNumberOfCounties(); i++) {
				dArray[i] = ssbData.getVariablesValues().get(i).get(ctrVariables).doubleValue();
			}

			percentilesForOneVariable.add(StatUtils.percentile(dArray, 20));
			percentilesForOneVariable.add(StatUtils.percentile(dArray, 40));
			percentilesForOneVariable.add(StatUtils.percentile(dArray, 60));
			percentilesForOneVariable.add(StatUtils.percentile(dArray, 80));
			ssbData.getPercentiles().add(percentilesForOneVariable);

			ctrVariables++;
			if (ctrVariables < ssbData.getNumberOfVariables()) {
				ctrCounties = 0;
			} else {
				ctrCounties++;
			}

		}

		return ssbData;
	}
}
