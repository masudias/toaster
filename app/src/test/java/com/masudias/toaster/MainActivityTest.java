package com.masudias.toaster;

import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.masudias.toaster.databinding.ToastLayoutBinding;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link MainActivity}
 */
@RunWith(RobolectricTestRunner.class)
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
