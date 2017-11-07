package com.jiangkang.ktools.system;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jiangkang.ktools.R;
import com.jiangkang.tools.struct.JsonGenerator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 利用Loader获取通讯录列表
 *
 * @author jiangkang
 */
public class ContactsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOAD_ID = 0;
    private static final String TAG = ContactsActivity.class.getSimpleName();

    private JSONObject result;

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ContactsActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private LoaderManager loaderManager;

    private QueryListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_contacts);
        initVar();
    }

    private void initVar() {
        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOAD_ID, null, this);
        listener = new QueryListener() {
            @Override
            public void success(final JSONObject object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(ContactsActivity.this)
                                .setTitle("通讯录")
                                .setMessage(object.toString())
                                .show();
                    }
                });
            }
        };
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonArray = new JSONArray();
                while (data.moveToNext()) {
                    String name;
                    name = data.getString(
                            data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    );
                    int id = data.getInt(
                            data.getColumnIndex(ContactsContract.Contacts._ID)
                    );

                    Cursor cursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                            null,
                            null
                    );
                    while (cursor.moveToNext()) {
                        String number = cursor.getString(
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        );

                        jsonArray.put(
                                new JsonGenerator()
                                        .put("name", name)
                                        .put("tel", number)
                                        .gen()
                        );
                    }
                    cursor.close();
                }
                result = new JsonGenerator()
                        .put("list", jsonArray)
                        .gen();

                data.close();

                if (listener != null) {
                    listener.success(result);
                }

                Log.d(TAG, "onLoadFinished: result =\n" + result.toString());
            }
        }).start();


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void setQueryListener(QueryListener listener) {
        this.listener = listener;
    }

    interface QueryListener {
        void success(JSONObject object);
    }

}
