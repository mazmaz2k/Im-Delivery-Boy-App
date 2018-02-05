package axeleration.com.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ClientCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;
    private Context context;

    public ClientCursorAdapter(Context context, Cursor c) {

        super(context, c);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.client_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView text = view.findViewById(R.id.name);
        TextView phone = view.findViewById(R.id.phone);
        Button newTask = view.findViewById(R.id.addTaskBtn);
        Button showAllTasks = view.findViewById(R.id.showTskBtn);
        final int id = cursor.getInt(cursor.getColumnIndex(Constants.CLIENTS._ID));
        final String phoneNumber = cursor.getString(cursor.getColumnIndex(Constants.CLIENTS.PHONE_NUMBER));
        showAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ShowAllTasks.class);
                i.putExtra("client_id", id);
                Log.d("temp", " send " + id);
                context.startActivity(i);
            }
        });
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewTask.class);
                i.putExtra("client_id", id);
                Log.d("temp", " send " + id);
                context.startActivity(i);
            }
        });
        Button callBtm = view.findViewById(R.id.call);
        callBtm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    //ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.CALL_PHONE},MainActivity.REQUEST_CODE);
                    Toast.makeText(context,"Permmition not allowed",Toast.LENGTH_SHORT).show();
                    return;
                }
                context.startActivity(intent);
            }
        });
        text.setText(cursor.getString(cursor.getColumnIndex(Constants.CLIENTS.FULL_NAME)));
        phone.setText(cursor.getString(cursor.getColumnIndex(Constants.CLIENTS.PHONE_NUMBER)));

    }

//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.showTskBtn:
//                Intent i = new Intent(context,NewTask.class);
//                i.putExtra("client_id",id);
//                Log.d("temp"," send "+id);
//                context.startActivity(i);
//              break;
//            case R.id.call:
//             //   call();
//                break;
//        }
//    }
}