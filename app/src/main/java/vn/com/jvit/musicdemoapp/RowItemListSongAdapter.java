package vn.com.jvit.musicdemoapp;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RowItemListSongAdapter<T> extends BaseAdapter {
    private PlayServiceListener mListener;


    public void setListener(PlayServiceListener listener) {
        mListener = listener;
    }

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public RowItemListSongAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<T> data) {
        if (data != null) {
            objects = data;
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_item_list_song, null);
            convertView.setTag(new ViewHolder(convertView));

        }
        initializeViews((Song) getItem(position), (ViewHolder) convertView.getTag());


        return convertView;
    }

    private void initializeViews(final Song object, ViewHolder holder) {


        holder.txtNameSong.setText(object.getSongName());
        holder.btnPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                if (mListener != null) {
//                    mListener.sendData(object.getSongPath());


                Intent intent = new Intent(context, BackGroundSoundService.class);
                intent.putExtra("songPath", object.getSongPath());
                intent.putExtra("songName",object.getSongName());
                context.startService(intent);



                //}

            }
        });
        if (object.isFlag() == false) {
            holder.btnPlayStop.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);

        } else {
            holder.btnPlayStop.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
        }

        notifyDataSetChanged();

    }


    protected class ViewHolder {
        private TextView txtNameSong;
        private ImageView btnPlayStop;

        public ViewHolder(View view) {
            txtNameSong = view.findViewById(R.id.txtNameSong);
            btnPlayStop = view.findViewById(R.id.btn_play_stop);


        }
    }

    public interface PlayServiceListener {
        void sendData(String path);
    }


}
