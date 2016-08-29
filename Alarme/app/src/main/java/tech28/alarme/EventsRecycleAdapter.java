package tech28.alarme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by hack on 8/28/2016.
 */
public class EventsRecycleAdapter extends RecyclerView.Adapter<CustomViewHolder> {
        private List<EventItem> eventsItemList;
        private Context mContext;

        public EventsRecycleAdapter(Context context, List<EventItem> eventsItemList) {
            this.eventsItemList = eventsItemList;
            this.mContext = context;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
            EventItem eventItem = eventsItemList.get(i);

            //Download image using picasso library
//            Picasso.with(mContext).load(feedItem.getThumbnail())
//                    .error(R.drawable.placeholder)
//                    .placeholder(R.drawable.placeholder)
//                    .into(customViewHolder.imageView);

            //Setting text view title
            //Html.fromHtml(
            customViewHolder.textView.setText(eventItem.getMessage());
        }

        @Override
        public int getItemCount() {
            return (null != eventsItemList ? eventsItemList.size() : 0);
        }
    }
