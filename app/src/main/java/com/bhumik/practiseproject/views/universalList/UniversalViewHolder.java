package com.bhumik.practiseproject.views.universalList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bhumik on 17/5/16.
 */
public class UniversalViewHolder {

    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private UniversalViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // SetTag
        mConvertView.setTag(this);
    }

    /**
     * Get a ViewHolder objects
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static UniversalViewHolder get(Context context, View convertView,
                                          ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new UniversalViewHolder(context, parent, layoutId, position);
        } else {
            UniversalViewHolder holder = (UniversalViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * Get to  control by Id control, if not n join views
     *
     * @param viewId
     * @return
     */

    @SuppressWarnings("An unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * Set a string to a TextView
     *
     * @param viewId
     * @param text
     * @return
     */
    public UniversalViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * Set picture as ImageView
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public UniversalViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * Set picture as ImageView
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    public UniversalViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

}
