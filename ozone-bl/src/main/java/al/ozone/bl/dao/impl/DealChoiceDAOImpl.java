package al.ozone.bl.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import al.ozone.bl.bean.SearchDealChoiceBean;
import al.ozone.bl.dao.DealChoiceDAO;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.Purchase;

@SuppressWarnings("serial")
public class DealChoiceDAOImpl extends GenericDAOImpl<Deal, Integer> implements DealChoiceDAO{

	public DealChoiceDAOImpl(Class<Deal> persistentClass) {
		super(persistentClass);
	}

	@Override
	public DealChoice get(Integer dealId, Integer choiceNr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("choiceNr", choiceNr);
		DealChoice record = (DealChoice) getSqlSession().selectOne("DEAL_CHOICE.get", map);
		return record;
	}
	
	@Override
	public List<DealChoice> getChoicesForDeal(int dealId) {
		@SuppressWarnings("unchecked")
		List<DealChoice> list = (List<DealChoice>) getSqlSession().selectList("DEAL_CHOICE.getChoicesForDeal", dealId);
		return list;
	}

	@Override
	public void insert(DealChoice dc) {
		getSqlSession().insert("DEAL_CHOICE.insert", dc);
	}

	@Override
	public List<DealChoice> searchDealChoice(SearchDealChoiceBean sb) {
		@SuppressWarnings("unchecked")
		List<DealChoice> list = getSqlSession().selectList("DEAL_CHOICE.searchDealChoice", sb);
        return list;
	}

	@Override
	public List<DealChoice> getActiveDealsChoice() {
		@SuppressWarnings("unchecked")
		List<DealChoice> list = getSqlSession().selectList("DEAL_CHOICE.getActiveDealsChoice", null);
        return list;
	}

	@Override
	public void increasePurchases(Purchase purchase) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", purchase.getDealChoice().getDealId());
		map.put("choiceNr", purchase.getDealChoice().getChoiceNr());
		map.put("totPurchase", purchase.getQuantity());
		getSqlSession().update("DEAL_CHOICE.increasePurchases", map);
	}

	@Override
	public DealChoice getFullDealChoice(Integer dealId, Integer choiceNr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("choiceNr", choiceNr);
		DealChoice record = (DealChoice) getSqlSession().selectOne("DEAL_CHOICE.getFull", map);
		return record;
	}

	@Override
	public void update(DealChoice dc) {
		getSqlSession().update("DEAL_CHOICE.update", dc);
	}
	
	@Override
	public void delete(DealChoice dc) {
		getSqlSession().delete("DEAL_CHOICE.delete", dc);
	}

	@Override
	public void deleteDealChoicesForDeal(Integer dealId) {
		getSqlSession().delete("DEAL_CHOICE.deleteDealChoicesForDeal", dealId);
	}

}
