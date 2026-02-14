package epf.query.schema;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("the query results")
public class ResultList {

	@JsonPropertyDescription("a list of the results, or an empty list if there are no results")
	private List<?> resultList;

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}
}
