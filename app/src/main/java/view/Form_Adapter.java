package view;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.andre.aguaclarapost.FilePickerPopUp;
import com.andre.aguaclarapost.R;
import com.andre.aguaclarapost.Upload;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Optional;

public class Form_Adapter extends RecyclerView.Adapter<Form_Adapter.MyViewHolder1> {

    private Context context;
    private int counter = 0;
    private List<Upload> uploadList;
    private onItemClickListener mListener;


    public class MyViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private ImageView imageView;
        private ImageView imageView2;
        private TextView descr;
        private TextView descr2;
        private CardView cardViewfirst;
        private CardView cardViewsec;

        public MyViewHolder1(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.postImage);
            imageView2 = (ImageView)view.findViewById(R.id.postImage2);
            descr = (TextView)view.findViewById(R.id.filedescr);
            descr2 = (TextView)view.findViewById(R.id.filedescr2);
            cardViewfirst = (CardView)view.findViewById(R.id.cardview1);
            cardViewsec = (CardView)view.findViewById(R.id.cardview2);

            view.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        //Create Menu to be Displayed
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");

            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                  switch(item.getItemId()){
                      case 1:
                          mListener.onDeleteClick(position);
                          return true;
                    }
                }
            }
            return false;
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
        void onDeleteClick (int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;

    }

    //Constructor
    public Form_Adapter(Context context, List<Upload> uploads){
        this.context = context;
        this.uploadList = uploads;
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardrow, parent, false);

        return new MyViewHolder1(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder1 holder, int position) {
        Upload uploadCurrent = uploadList.get(position);
        counter += 1;

        /*if(uploadList.size() == 1){
            holder.cardViewsec.setVisibility(View.INVISIBLE);
        }
        else{
            holder.cardViewsec.setVisibility((View.VISIBLE));
        }*/

        System.out.println(uploadList.size());

        //if(uploadList.size() % 2 != 0 || counter == 1)
            String s = uploadCurrent.getmImageUrl().substring(uploadCurrent.getmImageUrl().lastIndexOf("."),
                    uploadCurrent.getmImageUrl().lastIndexOf("?"));
            System.out.println("Resullt " + s);
            //holder.descr.setText(uploadCurrent.getmName());
            if(s.equals(".jpg")|| s.equals(".png")) {
                if(uploadList.size() % 2 != 0) {
                    holder.descr.setText(uploadCurrent.getmName());
                    Picasso.get()
                            //.load(uploadCurrent.getmImageUrl())
                            .load(R.drawable.imageicon)
                            .placeholder(R.drawable.docbig)
                            .fit()
                            .centerCrop()
                            .into(holder.imageView);
                    //holder.cardViewsec.setVisibility(View.INVISIBLE);
                }
                else{
                    holder.descr2.setText(uploadCurrent.getmName());
                    Picasso.get()
                            //.load(uploadCurrent.getmImageUrl())
                            .load(R.drawable.imageicon)
                            .placeholder(R.drawable.docbig)
                            .fit()
                            .centerCrop()
                            .into(holder.imageView2);
                }
            }
            else if (s.equals(".pdf")){
                Picasso.get()
                        .load(R.drawable.pdficon)
                        .placeholder(R.drawable.pdficon)
                        .fit()
                        .centerCrop()
                        .into(holder.imageView);
                //holder.cardViewsec.setVisibility(View.INVISIBLE);
            }
            else {
                Picasso.get()
                        //.load(uploadCurrent.getmImageUrl())
                        .load(R.drawable.docbig)
                        .placeholder(R.drawable.docbig)
                        .fit()
                        .centerCrop()
                        .into(holder.imageView);
                //holder.cardViewsec.setVisibility(View.INVISIBLE);
            }


    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }
}

