package eu.hackathonovo.ui.home_leader.adapter;

/**
 * Created by krunoslavtill on 09/08/16.
 */
public class HeaderItem extends Item {
    private String HeaderText;

    public HeaderItem(String HeaderText) {
        this.HeaderText=HeaderText;
    }

    public String getHeaderText() {
        return HeaderText;
    }

    public void setHeaderText(String position) {
        this.HeaderText = position;
    }

    @Override
    public int getTypeItem() {
        return TYPE_HEADER;
    }
}
