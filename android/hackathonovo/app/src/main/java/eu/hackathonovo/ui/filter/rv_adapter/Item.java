package eu.hackathonovo.ui.filter.rv_adapter;

/**
 * Created by krunoslavtill on 09/08/16.
 */
abstract class Item {
    public static final int TYPE_HEADER=0;
    static final int TYPE_ITEM=1;
    private String itemTitle;

    public abstract int getTypeItem();

}
