package eu.hackathonovo.ui.photo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.hackathonovo.R;

public class TakeorPickaAPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOCUSED_PHOTO = 1;
    private static final int NOT_FOCUSED_PHOTO = 2;

    private List<String> photoList;
    private Context context;
    SelectedPhotoListener listener;
    int positionInList;

    public TakeorPickaAPhotoAdapter(Context context, List<String> photoList, SelectedPhotoListener listener, int positionInList) {
        this.photoList = photoList;
        this.context = context;
        this.listener = listener;
        this.positionInList = positionInList;
    }

    public void changePosition(int pos) {
        this.positionInList = pos;
    }

    @Override
    public int getItemViewType(int position) {
        if (positionInList == position) {
            return FOCUSED_PHOTO;
        } else {
            return NOT_FOCUSED_PHOTO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case NOT_FOCUSED_PHOTO:
                View layoutViewNotFocused = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_not_focused_item, null);
                PhotoViewHolder pvh = new PhotoViewHolder(layoutViewNotFocused);
                holder = pvh;
                break;
            case FOCUSED_PHOTO:
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_focused_item, null);
                FocusedPhotoViewHolder fpvh = new FocusedPhotoViewHolder(layoutView);
                holder = fpvh;
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case FOCUSED_PHOTO:
                FocusedPhotoViewHolder focusHolder = (FocusedPhotoViewHolder) holder;
                showItemsFocusHolder(focusHolder, position);
                break;
            case NOT_FOCUSED_PHOTO:
                PhotoViewHolder notFocusHolder = (PhotoViewHolder) holder;
                showItemsNotFocusHolder(notFocusHolder, position);
                break;
        }
    }

    private void showItemsFocusHolder(final FocusedPhotoViewHolder holder, int position) {
//        Picasso.with(context).load(new File(photoList.get(position))).placeholder(R.drawable.ic_gallery_placeholder).into(holder.libPhotoFocused);
//        Picasso.with(context).load(new File(photoList.get(position))).into(holder.libPhotoFocused);
        Glide.with(context).load(photoList.get(position))
             .placeholder(R.drawable.ic_gallery_placeholder).centerCrop()
             .into(holder.libPhotoFocused);
        holder.wholeLayout.setTag(position);
    }

    private void showItemsNotFocusHolder(final PhotoViewHolder holder, final int position) {
//        Picasso.with(context).load(new File(photoList.get(position))).placeholder(R.drawable.ic_gallery_placeholder).into(holder.libPhotoNotFocused);
        Glide.with(context).load(photoList.get(position))
             .placeholder(R.drawable.ic_gallery_placeholder).centerCrop()
             .into(holder.libPhotoNotFocused);
        holder.wholeLayout.setTag(position);
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Integer clickedPicture = (Integer) holder.wholeLayout.getTag();
                positionInList = clickedPicture;
                listener.onClicked(clickedPicture);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.photoList.size();
    }

    public class FocusedPhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lib_photo)
        ImageView libPhotoFocused;

        @BindView(R.id.whole_layout)
        RelativeLayout wholeLayout;

        public FocusedPhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lib_photo)
        ImageView libPhotoNotFocused;

        @BindView(R.id.whole_layout)
        RelativeLayout wholeLayout;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
