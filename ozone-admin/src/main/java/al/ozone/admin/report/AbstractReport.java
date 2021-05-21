package al.ozone.admin.report;


public abstract class AbstractReport {
	protected String name = "";

	public abstract byte[] getStreamReport();

	public String getName() {
		return name;
	}
}