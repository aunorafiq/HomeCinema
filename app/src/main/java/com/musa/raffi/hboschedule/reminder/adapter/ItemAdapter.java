package com.musa.raffi.hboschedule.reminder.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.musa.raffi.hboschedule.R;
import com.musa.raffi.hboschedule.Utility;
import com.musa.raffi.hboschedule.models.scheduledb.DataManager;
import com.musa.raffi.hboschedule.schedule.PageFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Asus on 9/16/2016.
 */

public class ItemAdapter extends RecyclerViewCursorAdapter<ItemAdapter.ItemViewHolder> {
    ReminderClickListener mListener;

    public ItemAdapter(Context context, ReminderClickListener reminderClickListener) {
        super(context);
        setupCursorAdapter(null, 0, R.layout.reminder_item, false);
        mListener = reminderClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        mCursorAdapter.getCursor().moveToPosition(position);
        setViewHolder(holder);
        mCursorAdapter.bindView(null, mContext, mCursorAdapter.getCursor());
    }

    public class ItemViewHolder extends RecyclerViewCursorViewHolder implements View.OnClickListener{
        @Bind(R.id.time_and_channel) TextView mTime;
        @Bind(R.id.film_name) TextView mFilm;
        @Bind(R.id.channel) TextView mChannel;
        int idSchedule;
        String film;
        String channel;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void bindCursor(Cursor cursor) {
            String dateReq = cursor.getString(cursor.getColumnIndexOrThrow(DataManager.TABLE_ROW_DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DataManager.TABLE_ROW_SHOW_TIME)).substring(0,5) + " WIB, ";
            channel = cursor.getString(cursor.getColumnIndexOrThrow(DataManager.TABLE_ROW_CHANNEL));
            film = cursor.getString(cursor.getColumnIndexOrThrow(DataManager.TABLE_ROW_FILM_NAME));
            idSchedule = cursor.getInt(cursor.getColumnIndexOrThrow(DataManager.TABLE_ROW_ID));

            dateReq = Utility.generalizeDateToShow(dateReq);

            mChannel.setText(channel);
            mTime.setText(time + dateReq);
            mFilm.setText(film);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(idSchedule, film, channel);
        }
    }

    public interface ReminderClickListener{
        void onClick(int idSchedule, String film, String channel);
    }
}
