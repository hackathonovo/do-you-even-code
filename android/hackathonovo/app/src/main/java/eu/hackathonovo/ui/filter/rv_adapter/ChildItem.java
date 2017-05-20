package eu.hackathonovo.ui.filter.rv_adapter;



import java.io.Serializable;

import eu.hackathonovo.data.api.models.request.SearchDetailsData;

/**
 * Created by krunoslavtill on 09/08/16.
 */
public class ChildItem extends Item implements Serializable{



    String name;
    public ChildItem(String name) {
       this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int getTypeItem() {
        return TYPE_ITEM;
    }

}
