package com.tom.learncoroutinexroom.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class BlurTransformation(context: Context) : BitmapTransformation() {
    private val rs = RenderScript.create(context)
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {

        val blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true)

        val input = Allocation.createFromBitmap(
            rs,
            blurredBitmap,
            Allocation.MipmapControl.MIPMAP_FULL,
            Allocation.USAGE_SHARED
        )
        val output = Allocation.createTyped(rs, input.type)

        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.setInput(input)
        script.setRadius(10f)
        script.forEach(output)
        output.copyTo(blurredBitmap)
        toTransform.recycle()

        return blurredBitmap
    }

    fun getId(): String? {
        return "blur"
    }
}