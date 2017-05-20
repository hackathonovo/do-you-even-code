package eu.hackathonovo.ui.filter.rv_adapter;



import java.io.Serializable;

import eu.hackathonovo.data.api.models.response.FilterUsers;

/**
 * Created by krunoslavtill on 09/08/16.
 */
public class ChildItem extends Item implements Serializable{




    private FilterUsers filterUsers;
    public ChildItem(FilterUsers obj) {

        this.filterUsers = obj;

    }

    public FilterUsers getFilterUsers() {
        return filterUsers;
    }

    public void setFilterUsers(final FilterUsers filterUsers) {
        this.filterUsers = filterUsers;
    }

    @Override
    public int getTypeItem() {
        return TYPE_ITEM;
    }

}
