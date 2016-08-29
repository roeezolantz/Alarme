package tech28.alarme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hack on 8/28/2016.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {
    protected TextView textView;

    public CustomViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.title);
    }
}
