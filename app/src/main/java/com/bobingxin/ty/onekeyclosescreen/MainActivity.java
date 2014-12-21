package com.bobingxin.ty.onekeyclosescreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;
    private static final int REQUEST_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, AdminReceiver.class);
        if (mDevicePolicyManager.isAdminActive(mComponentName)) {
            mDevicePolicyManager.lockNow();
            finish();
        } else {
            activeManage();
        }
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mDevicePolicyManager.lockNow();
            finish();
        } else {
            activeManage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void activeManage() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);        // 权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);

        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"激活后您就可以一键锁屏了，让你的电源键寿命更长");


        startActivityForResult(intent, REQUEST_CODE);

    }
}
