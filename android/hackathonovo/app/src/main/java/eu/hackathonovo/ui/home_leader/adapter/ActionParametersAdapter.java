package eu.hackathonovo.ui.home_leader.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.hackathonovo.R;

public class ActionParametersAdapter extends RecyclerView.Adapter<Holder>{
    private final List<Item> mItemList = new ArrayList<>();

    public ActionParametersAdapter(final GridLayoutManager gridLayoutManager) {
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
        View view=null;

        if (viewType == 0) {
            //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_live_game, viewGroup, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_edit_action_child, parent, false);
        }

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
        TextView tv = (TextView) container.findViewById(R.id.tv_action_edit);
    }



    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
