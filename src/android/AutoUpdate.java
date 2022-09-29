package cordova.plugin.autoupdate;

import android.app.Activity;
import android.content.Context;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

/**
 * This class echoes a string called from JavaScript.
 */
public class AutoUpdate extends CordovaPlugin {

    private AppUpdateManager appUpdateManager;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("checkUpdate")) {
            try {
                Context context = this.cordova.getContext();
                Activity activity = this.cordova.getActivity();

                appUpdateManager = AppUpdateManagerFactory.create(context);
                Task<AppUpdateInfo> appUpdateInfoTask  = appUpdateManager.getAppUpdateInfo();

                appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                        appUpdateManager.startUpdateFlow(
                                appUpdateInfo,
                                activity,
                                AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                                        .setAllowAssetPackDeletion(false)
                                        .build()
                        );
                    }
                });

                callbackContext.success("Atualização verificada com sucesso!");
                return true;
            } catch (Exception e) {
                callbackContext.error("Não foi possível verificar a atualização");
                return true;
            }
        }
        return false;
    }
}