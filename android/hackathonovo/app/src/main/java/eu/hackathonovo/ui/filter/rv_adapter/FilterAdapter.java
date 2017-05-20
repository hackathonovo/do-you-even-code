package eu.hackathonovo.ui.filter.rv_adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.hackathonovo.R;
import eu.hackathonovo.data.api.models.response.FilterUsers;

public class FilterAdapter extends RecyclerView.Adapter<Holder>{
    private final List<Item> mItemList = new ArrayList<>();

    public FilterAdapter(final GridLayoutManager gridLayoutManager) {
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return isHeaderType(position) ? 1 : 1;
            }
        });
    }

    public void addItem(Item item) {
        mItemList.add(item);
        notifyDataSetChanged();
    }




    private boolean isHeaderType(int position) {
        return mItemList.get(position).getTypeItem() == Item.TYPE_HEADER;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_filter_child, parent, false);
        /*
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_edit_action_child, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_filter_child, parent, false);
        }
        */
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (isHeaderType(position)) {
            bindHeaderItem(holder, position);
        } else {
            bindGridItem(holder, position);
        }
    }

    private void bindHeaderItem(final Holder holder, final int position) {

    }

    private void bindGridItem(final Holder holder, final int position) {
        View container = holder.itemView;
        ChildItem item = (ChildItem) mItemList.get(position);

        TextView tv = (TextView) container.findViewById(R.id.tv_name);
        tv.setText(item.getFilterUsers().name);

        container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                int userID= item.getFilterUsers().id;
                listener.getPlanIdAtPosition(userID);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).getTypeItem() == Item.TYPE_HEADER ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void refreashAdapter(final List<FilterUsers> filterUserses){
        mItemList.clear();
        for (int i = 0; i < filterUserses.size(); i++) {
            mItemList.add(new ChildItem(filterUserses.get(i)));
        }
        notifyDataSetChanged();
    }

    private static final class EmptyListener implements Listener {

        @Override
        public void getPlanIdAtPosition(long userId) {
            //NO OP
        }
    }

    public interface Listener {

        Listener EMPTY = new EmptyListener();

        void getPlanIdAtPosition(long userId);
    }

    private Listener listener = Listener.EMPTY;

    public void setListener(final Listener listener) {
        this.listener = listener != null ? listener : Listener.EMPTY;
    }
}
