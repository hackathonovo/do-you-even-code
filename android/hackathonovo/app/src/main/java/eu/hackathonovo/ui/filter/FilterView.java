package eu.hackathonovo.ui.filter;

import java.util.List;

import eu.hackathonovo.data.api.models.response.FilterUsers;

public interface FilterView {

    void renderView(List<FilterUsers> filterUserses);
}
