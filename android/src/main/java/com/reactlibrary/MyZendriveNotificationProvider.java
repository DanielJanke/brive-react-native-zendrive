import com.zendrive.sdk.*;

public class MyZendriveNotificationProvider extends ZendriveNotificationProvider {

    // Must have a default constructor

    @Override
    @RequiresApi(Build.VERSION_CODES.O)
    @NonNull
    public ZendriveNotificationContainer getMaybeInDriveNotificationContainer(@NonNull Context context) { 
    
    }

    @Override
    @NonNull
    public ZendriveNotificationContainer getInDriveNotificationContainer(@NonNull Context context) {

    }

}