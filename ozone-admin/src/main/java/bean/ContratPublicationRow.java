package bean;

import java.util.List;

import org.primefaces.model.chart.PieChartModel;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.utils.ZUtils;

public class ContratPublicationRow {
	private String month;
	private Integer totContrats;
	private Integer stillWaiting;
	
	private PieChartModel pieModel;

	public ContratPublicationRow(String month, Integer totContrats,
			Integer stillWaiting, List<ResultStatisticBean> list) {
		super();
		this.month = month;
		this.totContrats = totContrats;
		this.stillWaiting = stillWaiting;
		pieModel = new PieChartModel();
		if(!ZUtils.isEmptyList(list)){
	    	for (ResultStatisticBean res : list) {
	    		pieModel.set(res.getKey(), res.getValue()); 
			}
		}
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getTotContrats() {
		return totContrats;
	}

	public void setTotContrats(Integer totContrats) {
		this.totContrats = totContrats;
	}

	public Integer getStillWaiting() {
		return stillWaiting;
	}

	public void setStillWaiting(Integer stillWaiting) {
		this.stillWaiting = stillWaiting;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	} 

}
