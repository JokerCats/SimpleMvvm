package net.jkcats.simplemvvm.basics;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setPageResID());
        initViews();
    }

    /**
     * 短暂提示信息
     *
     * @param content 提示内容
     */
    protected void toast(String content) {
        // todo：替换为 SnackBar 并追加自定义元素设置方法。
        if (content != null) {
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        }
    }

    @LayoutRes
    public abstract int setPageResID();

    protected abstract void initViews();
}
