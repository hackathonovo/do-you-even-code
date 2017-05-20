package eu.hackathonovo.ui.filter;

public interface FilterPresenter {

    void setView(FilterView view);

    void filterData(String name, int buffer);

    void updateUser();
}
