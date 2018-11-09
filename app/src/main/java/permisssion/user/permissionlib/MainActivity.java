package permisssion.user.permissionlib;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends Activity implements PermissionListener {
private PermissionUtils permissionUtils;
    public final static String[] MULTI_PERMISSIONS = new String[]{
            PermissionUtils.Companion.getREAD_PHONE_STATE(),
            PermissionUtils.Companion.getGET_ACCOUNTS(), PermissionUtils.Companion.getACCESS_FINE_LOCATION(),
            PermissionUtils.Companion.getREAD_CONTACTS(), PermissionUtils.Companion.getREAD_EXTERNAL_STORAGE(),
            PermissionUtils.Companion.getWRITE_EXTERNAL_STORAGE(), PermissionUtils.Companion.getCAMERA(),
            PermissionUtils.Companion.getSEND_SMS(), PermissionUtils.Companion.getREAD_SMS(),
            PermissionUtils.Companion.getRECEIVE_SMS()};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionUtils.with(this).setListener(this).requestPermission(MULTI_PERMISSIONS);
    }

    @Override
    protected void onResume() {
        super.onResume();
     }

    @Override
    public void onRequestPermissionsResult(int requestCode,  @NonNull String[] permissions,   @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtils.onPermissionRequestResult(requestCode, permissions, grantResults);
    }

    @Override
    public void allPermissionGranted() {

    }

    @Override
    public void onNeverAskAgainPermission(@NotNull String[] string) {

    }

    @Override
    public void onDenied(@NotNull String[] string) {

    }
}
