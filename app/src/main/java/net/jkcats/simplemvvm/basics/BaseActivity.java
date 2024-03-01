package net.jkcats.simplemvvm.basics;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public abstract class BaseActivity extends AppCompatActivity {

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
}
