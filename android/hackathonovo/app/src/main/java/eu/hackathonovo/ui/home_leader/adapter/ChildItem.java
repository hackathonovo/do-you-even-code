package eu.hackathonovo.ui.home_leader.adapter;



import java.io.Serializable;

import eu.hackathonovo.data.api.models.request.SearchDetailsData;

/**
 * Created by krunoslavtill on 09/08/16.
 */
public class ChildItem extends Item implements Serializable{



    private SearchDetailsData searchDetailsData;
    public ChildItem(SearchDetailsData searchDetailsData) {
       this.searchDetailsData=searchDetailsData;

    }

    public SearchDetailsData getSearchDetailsData() {
        return searchDetailsData;
    }

    public void setSearchDetailsData(final SearchDetailsData searchDetailsData) {
        this.searchDetailsData = searchDetailsData;
    }

    @Override
    public int getTypeItem() {
        return TYPE_ITEM;
    }

}
