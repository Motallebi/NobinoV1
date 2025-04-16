package com.smcdeveloper.nobinoapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES {
    fun encryptAES(value: String, key: String, iv: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec: SecretKey = SecretKeySpec(key.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }


    fun decryptAES(value: String, key: String, iv: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec: SecretKey = SecretKeySpec(key.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decode = Base64.decode(value , Base64.DEFAULT)
        val decrypted = cipher.doFinal(decode)
        return String(decrypted)
    }




    fun base64ToBitmap(base64Str: String): Bitmap? {
        return try {



          //  var decodedString: Byte[]   = Base64.decode(base64Image, Base64.DEFAULT);

            // Decode the Base64 string into a byte array
            val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
            // Convert the byte array to a Bitmap
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
        catch (e: Exception) {

            Log.e("image",e.toString())
            e.printStackTrace()
            null
        }
    }


    fun base64ToImageBitmap(base64Str: String): ImageBitmap? {
        return base64ToBitmap(base64Str)?.asImageBitmap()
    }


























}