package al.ozone.admin.report;

import java.util.Collection;

public class ReportDatasource extends GenericDatasource {

	AbstractParams params;
	
	public ReportDatasource(Collection<?> data) {
		super(data);
	}

	public ReportDatasource(Collection<?> data, AbstractParams params) {
		super(data);
		this.params = params;
	}
}
