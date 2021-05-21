package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.SearchDealChoiceBean;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.Purchase;

public interface DealChoiceDAO {
	
	public DealChoice get(Integer dealId, Integer choiceNr);
	
	public List<DealChoice> getChoicesForDeal(int dealId);

	public void insert(DealChoice dealChoice);

	public List<DealChoice> searchDealChoice(SearchDealChoiceBean sb);

	public List<DealChoice> getActiveDealsChoice();

	public void increasePurchases(Purchase purchase);

	public DealChoice getFullDealChoice(Integer dealId, Integer choiceNr);

	public void update(DealChoice dc);

	public void delete(DealChoice dc);

	public void deleteDealChoicesForDeal(Integer dealId);
	
}
