package epf.workflow.service;

import epf.workflow.schema.Call;

public interface CallService<T> {

	void call(final Call<T> call) throws Exception;
}
