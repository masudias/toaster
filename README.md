## toaster - testable custom toast viewer

I was going through some issues regarding unit testing the toast message which is being displayed. I tried to test the toast message using Robolectric without any luck. [Filed a question in StackOverflow](https://stackoverflow.com/q/59433746/3145960) as well.

However, I could manage to get a workaround for my problem and ended up having a custom layout for showing toast messages. The layout is very simple (only having a `TextView`) and the function for showing the toast can be found in the `ToastUtil` class. 

I was able to use Robolectric to write the tests as follows to verify the content of the toast message. 

``` java
public class MainActivityTest {

    @Before
    public void setup() {
        Robolectric.buildActivity(MainActivity.class).create();
    }

    @Test
    public void testToastMessage() {
        ToastLayoutBinding binding = DataBindingUtil.getBinding(ShadowToast.getLatestToast().getView());
        binding.executePendingBindings();

        assertEquals(binding.toastMsg.getContext().getString(R.string.some_toast),
                binding.toastMsg.getText());
    }

    @Test
    public void testToastDuration() {
        assertEquals(Toast.LENGTH_LONG, ShadowToast.getLatestToast().getDuration());
    }
}
```

The layout for the custom toast is simply as follows. 

``` xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <import type="android.text.TextUtils" />

        <variable
            name="msg"
            type="String" />
    </data>

    <FrameLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:elevation="4dp">

        <TextView
            android:id="@+id/toast_msg"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:fontFamily="sans-serif"
            android:gravity="center"
            android:text="@{TextUtils.isEmpty(msg) ? @string/no_msg : msg}" />
    </FrameLayout>
</layout>
```

And the function for displaying the toast message is as follows. 

``` java
/**
* Display a custom toast with some message provided as a function parameter
*
* @param activity An {@link Activity} where the toast message will be shown
* @param msg      a {@link String} that holds the message to be shown as a Toast
* @throws IllegalArgumentException if a null activity is passed to the function
* @apiNote If a null String is passed as a #msg parameter, then this function shows a default text (no_toast)
*/
public static void showToast(Activity activity, String msg) {

    if (activity == null) {
        throw new IllegalArgumentException("The passed in activity cannot be null");
    }

    if (msg == null) {
        msg = activity.getString(R.string.no_msg);
    }

    ToastLayoutBinding toastLayout = DataBindingUtil.inflate(
            activity.getLayoutInflater(), R.layout.toast_layout, null, false);
    toastLayout.setMsg(msg); // Set the toast message here

    Toast toast = new Toast(activity);
    toast.setView(toastLayout.getRoot());
    toast.setGravity(Gravity.TOP, 0, 200);
    toast.setDuration(Toast.LENGTH_LONG);
    toast.show();
}
```

The project has a working example. Please feel free to clone and run the tests. 

I hope that helps other developers having the same problem. 
