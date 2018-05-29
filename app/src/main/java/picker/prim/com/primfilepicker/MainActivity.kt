package picker.prim.com.primfilepicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import picker.prim.com.primpicker_core.PrimPicker
import picker.prim.com.primpicker_core.engine.ImageEngine
import picker.prim.com.primpicker_core.entity.MimeType

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_video.setOnClickListener({
            goVideo()
        })

        btn_img.setOnClickListener({
            goImg()
        })

        btn_all.setOnClickListener({
            goAll()
        })
    }

    class ImageLoader : ImageEngine {
        override fun loadImage(context: Context?, resize: Int, placeholder: Drawable?, view: ImageView?, uri: Uri?) {
            Glide.with(context).load(uri).asBitmap().placeholder(placeholder).override(resize, resize).centerCrop().into(view)
        }

        override fun loadImage(context: Context?, resizeX: Int, resizeY: Int, placeholder: Drawable?, view: ImageView?, uri: Uri?) {
            Glide.with(context).load(uri).asBitmap().placeholder(placeholder).override(resizeX, resizeY).centerCrop().into(view)
        }

        override fun loadGifImage(context: Context?, resize: Int, placeholder: Drawable?, view: ImageView?, uri: Uri?) {
            Glide.with(context).load(uri).asGif().placeholder(placeholder).override(resize, resize).centerCrop().into(view)
        }

        override fun loadGifImage(context: Context?, resizeX: Int, resizeY: Int, placeholder: Drawable?, view: ImageView?, uri: Uri?) {
            Glide.with(context).load(uri).asGif().placeholder(placeholder).override(resizeX, resizeY).centerCrop().into(view)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val str = StringBuffer()
            str.append("返回结果:").append("Uri:").append("\n")
            for (uri in PrimPicker.obtainUriResult(data)) {
                str.append(uri).append("\n")
            }
            str.append("Path:").append("\n")
            for (s in PrimPicker.obtainPathResult(data)) {
                str.append(s).append("\n")
            }
            if (PrimPicker.obtainCompressResult(data)) {
                str.append("压缩视频")
            } else {
                str.append("不压缩视频")
            }
            result.text = str.toString()
        }
    }

    fun goImg() {
        var span = et_spanCount.text.toString()
        var max = et_maxCount.text.toString()
        if (TextUtils.isEmpty(span)) {
            span = "3"
        }

        if (TextUtils.isEmpty(max)) {
            max = "1"
        }
        PrimPicker
                .with(this)
                .choose(MimeType.ofImage())
                .setSpanCount(Integer.parseInt(span))
                .setMaxSelected(Integer.parseInt(max))
                .setImageLoader(ImageLoader())
                .showSingleMediaType(true)
                .setCapture(true)
                .forResult(1001)
    }

    fun goVideo() {
        var span = et_spanCount.text.toString()
        var max = et_maxCount.text.toString()
        if (TextUtils.isEmpty(span)) {
            span = "3"
        }

        if (TextUtils.isEmpty(max)) {
            max = "1"
        }
        PrimPicker
                .with(this)
                .choose(MimeType.ofVideo())
                .setSpanCount(Integer.parseInt(span))
                .setMaxSelected(Integer.parseInt(max))
                .setImageLoader(ImageLoader())
                .showSingleMediaType(true)
                .setCapture(true)
                .forResult(1001)
    }

    fun goAll() {
        var span = et_spanCount.text.toString()
        var max = et_maxCount.text.toString()
        if (TextUtils.isEmpty(span)) {
            span = "3"
        }

        if (TextUtils.isEmpty(max)) {
            max = "1"
        }
        PrimPicker
                .with(this)
                .choose(MimeType.ofAll())
                .setCapture(true)
                .setSpanCount(Integer.parseInt(span))
                .setImageLoader(ImageLoader())
                .setMaxSelected(Integer.parseInt(max))
                .showSingleMediaType(true)
                .forResult(1001)
    }
}
